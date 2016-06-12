package com.autosite.site;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.autosite.app.AutositeLoginExtentExecutor;
import com.autosite.db.AutositeUser;
import com.autosite.db.SweepUserConfig;
import com.autosite.ds.SweepUserConfigDS;
import com.autosite.util.SweepUtil;

public class SweepLoginExentExecutor implements AutositeLoginExtentExecutor{

    public void execute(HttpServletRequest request, AutositeUser user) throws Exception {
        // TODO Auto-generated method stub

        m_logger.debug("################# SweepLoginExentExecutor ##############################################");

        if (user == null)
            throw new Exception("User is not specified but required to proceed the extent executor");
        
        SweepUtil.getOrCreateSweepUserConfig(user);
    }
    
    private static Logger m_logger = Logger.getLogger(SweepLoginExentExecutor.class);
}
