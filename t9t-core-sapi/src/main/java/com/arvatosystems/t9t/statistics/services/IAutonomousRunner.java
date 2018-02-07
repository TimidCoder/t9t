package com.arvatosystems.t9t.statistics.services;

import java.util.function.Consumer;
import java.util.function.Function;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.batch.StatisticsDTO;

/** Provides an interface to run a series of tasks as autonomous processes.
 * The loop checks for server shutdowns, increments the progress bar, and finally writes a statistics log entry. */
public interface IAutonomousRunner {
    public <T> void runSingleAutonomousTx(
        RequestContext ctx,
        int expectedTotal,                              // expected total number of records (Collections.size())
        Iterable<T> iterable,                           // provider of loop variables
        Function<T,RequestParameters> requestProvider,  // converts a loop variable to a request
        Consumer<StatisticsDTO> logEnhancer,            // optional (may be null): enhances the statistics output (set info1...)
        String processId                                // processId of the statistics log
    );
}
