package com.autosite.service.impl;

import java.sql.Timestamp;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.AutositeUser;
import com.autosite.db.Site;
import com.autosite.ds.AutositeUserDS;
import com.autosite.util.access.AccessDef.SystemRole;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;


public class DefaultAutositeUserServiceImpl extends AbstractUserServiceImpl{

    private static Logger m_logger = LoggerFactory.getLogger(DefaultAutositeUserServiceImpl.class);

    public AutositeUser createUser(Site site, String email, String password) throws Exception {

        AutositeUser _AutositeUser = new AutositeUser();
        _AutositeUser.setSiteId(site.getId());
        
        
        _AutositeUser.setUsername(email);
        _AutositeUser.setPassword(password);
        _AutositeUser.setEmail(email);
        _AutositeUser.setUserType(SystemRole.SiteAdmin.level());
        _AutositeUser.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        _AutositeUser.setTimeUpdated(new Timestamp(System.currentTimeMillis()));

        if (!AutositeUserDS.getInstance().put(_AutositeUser)){
            throw new Exception("Failed to create a new user");
        }
        
        return _AutositeUser;
    }    
    
    public AutositeUser createUser(Site site, Map parameters) throws Exception {
        
        return null;
    }

    public AutositeUser createUser(Site site, Object parameterObj) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AutositeUser createUser(Site site, String username, String email, String password, int userType, boolean allowPagelessSession) throws Exception {

        AutositeUser _AutositeUser = new AutositeUser();
        _AutositeUser.setSiteId(site.getId());
        
        _AutositeUser.setUsername(username);
        _AutositeUser.setPassword(password);
        _AutositeUser.setEmail(email);
        _AutositeUser.setUserType(userType);
        _AutositeUser.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        _AutositeUser.setTimeUpdated(new Timestamp(System.currentTimeMillis()));
        _AutositeUser.setPagelessSession(WebParamUtil.getIntValue(allowPagelessSession));
        
        if (!AutositeUserDS.getInstance().put(_AutositeUser)){
            throw new Exception("Failed to create a new user");
        }
        
        return _AutositeUser;
    
    
    }
    
    
    
}
