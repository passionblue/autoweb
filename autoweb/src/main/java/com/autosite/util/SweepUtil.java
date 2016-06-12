package com.autosite.util;

import com.autosite.db.AutositeUser;
import com.autosite.db.SweepUserConfig;
import com.autosite.ds.SweepUserConfigDS;

public class SweepUtil {

    public static final int SWEEP_WORLDCUP_MAX_INVITATION=5;
    
    
    public static SweepUserConfig getOrCreateSweepUserConfig(AutositeUser user){

        SweepUserConfig userConfig = SweepUserConfigDS.getInstance().getObjectByUserId(user.getId());
        if (userConfig == null) {
            userConfig = new SweepUserConfig();
            userConfig.setSiteId(user.getSiteId());
            userConfig.setMaxSweepAllowed(1);
            userConfig.setUserId(user.getId());
            SweepUserConfigDS.getInstance().put(userConfig);
        }

        return userConfig;
    }
    
}
