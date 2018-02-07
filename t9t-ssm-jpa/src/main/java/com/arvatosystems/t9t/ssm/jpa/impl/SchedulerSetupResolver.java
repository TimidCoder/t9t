package com.arvatosystems.t9t.ssm.jpa.impl;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver;
import com.arvatosystems.t9t.ssm.SchedulerSetupDTO;
import com.arvatosystems.t9t.ssm.SchedulerSetupRecurrenceType;
import com.arvatosystems.t9t.ssm.SchedulerSetupRef;
import com.arvatosystems.t9t.ssm.jpa.entities.SchedulerSetupEntity;
import com.arvatosystems.t9t.ssm.jpa.mapping.ISchedulerSetupDTOMapper;
import com.arvatosystems.t9t.ssm.jpa.persistence.ISchedulerSetupEntityResolver;
import com.arvatosystems.t9t.ssm.services.ISchedulerService;
import com.arvatosystems.t9t.ssm.services.ISchedulerSetupResolver;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class SchedulerSetupResolver extends AbstractJpaResolver<SchedulerSetupRef, SchedulerSetupDTO, FullTrackingWithVersion, SchedulerSetupEntity>
    implements ISchedulerSetupResolver {

    protected final ISchedulerService schedulerService = Jdp.getRequired(ISchedulerService.class);


    public SchedulerSetupResolver() {
        super("SchedulerSetup", Jdp.getRequired(ISchedulerSetupEntityResolver.class), Jdp.getRequired(ISchedulerSetupDTOMapper.class));
    }

    @Override
    public void update(SchedulerSetupDTO dto) {
        if (dto.getRecurrencyType() != SchedulerSetupRecurrenceType.FAST)
            dto.setCronExpression(schedulerService.determineCronExpression(dto));
        super.update(dto);
    }


    @Override
    public void create(SchedulerSetupDTO dto) {
        if (dto.getRecurrencyType() != SchedulerSetupRecurrenceType.FAST)
            dto.setCronExpression(schedulerService.determineCronExpression(dto));
        super.create(dto);
    }


    @Override
    public SchedulerSetupRef createKey(Long ref) {
        return ref == null ? null : new SchedulerSetupRef(ref);
    }
}
