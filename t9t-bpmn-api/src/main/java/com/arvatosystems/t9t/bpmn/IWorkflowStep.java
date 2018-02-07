package com.arvatosystems.t9t.bpmn;

import java.util.Map;

import de.jpaw.util.ApplicationException;

/** Defines the interface of an abstract base class used for all kinds of generation 2 workflows (workflows which do not persist the object as a first step). */
public interface IWorkflowStep<T> {

    /** Every step must return the factory to read objects from the DB. This serves as a plausi to validate
     * that the composition of steps is consistent (i.e. no SalesOrder step linked to a DeliveryOrderStep).
     * If this method returns null, then the factory doesn't matter to the step (for example because it's a just an unspecific logger). */
    String getFactoryName();
    /** Executes the workflow step on data of type T.
     * @param data the object to work on
     * @param a JSON object of parameters provided by the BPMN workflow. This for example holds the documentTemplateId for user documents.
     *
     * @return true if the execution should terminate now (does not imply the workflow is complete).
     * @throws ApplicationException
     */
    WorkflowReturnCode execute(T data, Map<String, Object> parameters);

    /** method to determine if this step may be executed now.
        Called before execute() is invoked. */
    WorkflowRunnableCode mayRun(T data, Map<String, Object> parameters);
}
