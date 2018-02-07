package com.arvatosystems.t9t.bpmn2.be.activiti;

import org.activiti.bpmn.model.CallActivity;
import org.activiti.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;

/**
 * Extension of Activiti default {@linkplain DefaultActivityBehaviorFactory} which
 * will take process definition customization into account.
 * @author LIEE001
 */
public class T9tActivityBehaviorFactory extends DefaultActivityBehaviorFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public CallActivityBehavior createCallActivityBehavior(final CallActivity callActivity) {
        CallActivityBehavior activityBehavior = super.createCallActivityBehavior(callActivity);
        return new T9tActivityBehavior(activityBehavior);
    }
}
