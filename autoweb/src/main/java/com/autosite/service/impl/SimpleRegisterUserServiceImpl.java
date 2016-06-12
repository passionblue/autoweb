package com.autosite.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.AutositeUser;
import com.autosite.db.RegisterSimple;
import com.autosite.db.Site;
import com.autosite.ds.AutositeUserDS;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebParamUtil;


public class SimpleRegisterUserServiceImpl extends AbstractUserServiceImpl{

    private static Logger m_logger = LoggerFactory.getLogger(SimpleRegisterUserServiceImpl.class);

    protected AutositeUserDS m_autositeUserDS = AutositeUserDS.getInstance();

    public AutositeUser createUser(Site site, Object parameterObj) throws Exception {

        m_logger.info("SimpleRegisterUserService.createUser()");
        
        RegisterSimple _RegisterSimple = (RegisterSimple)parameterObj;
        String userName = _RegisterSimple.getUsername();
        
        AutositeUser user = m_autositeUserDS.getBySiteIdUsername(site.getId(), userName);
        
        if (user != null){
            throw new Exception("User " + userName + " alrady exists.");
        }
        
        AutositeUser _AutositeUser = new AutositeUser();
        _AutositeUser.setSiteId(site.getId());
        _AutositeUser.setUsername(WebParamUtil.getStringValue(_RegisterSimple.getEmail()));
        _AutositeUser.setPassword(WebParamUtil.getStringValue(_RegisterSimple.getPassword()));
        _AutositeUser.setEmail(WebParamUtil.getStringValue(_RegisterSimple.getEmail()));
        _AutositeUser.setUserType(_RegisterSimple.getUserType());
        _AutositeUser.setTimeCreated(WebParamUtil.getDateValue(_RegisterSimple.getTimeCreated(), new TimeNow()) );
        _AutositeUser.setTimeUpdated(WebParamUtil.getDateValue(_RegisterSimple.getTimeUpdated(), new TimeNow()));
        
        if (!m_autositeUserDS.put(_AutositeUser)){
            throw new Exception("Failed to create a new user");
        }
        
        return _AutositeUser;
    }
    
}
