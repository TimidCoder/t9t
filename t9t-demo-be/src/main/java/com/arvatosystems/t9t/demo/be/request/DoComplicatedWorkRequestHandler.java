package com.arvatosystems.t9t.demo.be.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.demo.be.init.IDemo;
import com.arvatosystems.t9t.demo.request.ComplicatedWorkResponse;
import com.arvatosystems.t9t.demo.request.DoComplicatedWorkRequest;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;

import de.jpaw.dp.Jdp;

public class DoComplicatedWorkRequestHandler extends AbstractRequestHandler<DoComplicatedWorkRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoComplicatedWorkRequestHandler.class);

    IDemo justStuff = Jdp.getRequired(IDemo.class);

    @Override
    public boolean isReadOnly(DoComplicatedWorkRequest params) {
        return true;
    }

    @Override
    public ComplicatedWorkResponse execute(DoComplicatedWorkRequest rq) {
        LOGGER.info("Hi, I'm doing complicated work now!");

        ComplicatedWorkResponse response = new ComplicatedWorkResponse();
        response.setReturnCode(0);
        response.setSum(rq.getA() + rq.getB());
        return response;
    }

}
