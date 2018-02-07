package com.arvatosystems.t9t.cfg.be;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Startup;

@Startup(0)
public class InitCfgBe {

    // started by Jdp initialization
    public static void onStartup() {
        Jdp.bind(ConfigProvider.getConfiguration());
    }
}
