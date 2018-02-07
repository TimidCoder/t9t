package com.arvatosystems.t9t.bpmn2.be.deployer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.auth.jpa.persistence.ITenantEntityResolver;
import com.arvatosystems.t9t.base.T9tConstants;
import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO;
import com.arvatosystems.t9t.bpmn.pojo.IProcessDefinitionsProvider;
import com.arvatosystems.t9t.bpmn.pojo.ProcessDefinition;
import com.arvatosystems.t9t.bpmn.services.IBPMService;
import com.arvatosystems.t9t.bpmn.services.IBpmnPersistenceAccess;

import de.jpaw.bonaparte.pojos.apiw.DataWithTrackingW;
import de.jpaw.dp.Jdp;
import de.jpaw.dp.Startup;
import de.jpaw.dp.StartupOnly;


/**
 * Responsible for detecting global process definitions that needs to be
 * deployed by leveraging metadata defined by other modules through
 * {@linkplain IProcessDefinitionsProvider}.
 * <p>
 * It will then deploy these global process definitions into Activiti
 * repository.
 *
 * @author LIEE001
 */
@Startup(50090)
public class BPMProcessDeployer implements StartupOnly {
    private static final Logger LOGGER = LoggerFactory.getLogger(BPMProcessDeployer.class);

    private ITenantEntityResolver tenantResolver = Jdp.getRequired(ITenantEntityResolver.class);

    private static Set<ProcessDefinition> collectGlobalProcessDefinitions() {
        List<IProcessDefinitionsProvider> processDefinitionsProviders = Jdp.getAll(IProcessDefinitionsProvider.class);
        if (processDefinitionsProviders != null) {
            final Set<ProcessDefinition> foundProcessDefinitions = new HashSet<>();
            for (IProcessDefinitionsProvider processDefinitionProvider: processDefinitionsProviders)
                foundProcessDefinitions.addAll(processDefinitionProvider.getProcessDefinitions());

            return foundProcessDefinitions;
        }

        return Collections.<ProcessDefinition>emptySet();
    }

    @Override
    public void onStartup() {

        final IBPMService bpmService = Jdp.getRequired(IBPMService.class);

        // collect global process definitions (defined in JARs)
        final Set<ProcessDefinition> globalProcessDefinitions = collectGlobalProcessDefinitions();
        if (!globalProcessDefinitions.isEmpty()) {
            Set<String> globalProcessDefinitionIds = globalProcessDefinitions.stream().map((processDefinition) -> processDefinition.getId())
                    .collect(Collectors.toSet());

            LOGGER.info("Found {} global process definitions", globalProcessDefinitions.size());
            LOGGER.info("The following global process definitions are found: {}",
                    Arrays.toString(globalProcessDefinitionIds.toArray()));
        }

        final Map<String, ProcessDefinition> globalProcessDefinitionsMap = new HashMap<>();
        for (ProcessDefinition processDefinition: globalProcessDefinitions)
            globalProcessDefinitionsMap.put(processDefinition.getId(), processDefinition);

        // get all stored process definition configurations
        final IBpmnPersistenceAccess bpmnPersistenceAccess = Jdp.getRequired(IBpmnPersistenceAccess.class);
        try {
            List<DataWithTrackingW<ProcessDefinitionDTO, FullTrackingWithVersion>> allProcessDefinitions = bpmnPersistenceAccess.getAllProcessDefinitionsForEngine(IBPMService.ENGINE_ACTIVITI);
            LOGGER.info("Found {} (tenant + global) process definition configurations for engine {}.", allProcessDefinitions.size(), IBPMService.ENGINE_ACTIVITI);

            for (DataWithTrackingW<ProcessDefinitionDTO, FullTrackingWithVersion> dwt : allProcessDefinitions) {
                final ProcessDefinitionDTO dto = dwt.getData();
                // if it's a global process, deploy it (use latest JAR based content)
                if (T9tConstants.GLOBAL_TENANT_REF42.equals(dwt.getTenantRef())) {
                    ProcessDefinition processDefinition = globalProcessDefinitionsMap.get(dto.getProcessDefinitionId());
                    if (processDefinition != null) {
                        bpmService.deployGlobalProcess(processDefinition);
                    } else {
                        throw new RuntimeException("An error occured during BPMN 2 auto-deployment. Found global process definition configuration (processDefinitionId=" +
                                dto.getProcessDefinitionId() + "), but no such process definition available (in any JARs)!");
                    }
                } else {
                    // if it's a tenant specific process, load the process content into engine
                    bpmService.loadTenantProcess(tenantResolver.find(dwt.getTenantRef()).getTenantId(), dto.getProcessDefinitionId()); // FIXME: temp workaround
                }
            }
        } catch (T9tException ex) {
            throw new RuntimeException("An error occured during BPMN auto-deployment.", ex);
        }
    }
}
