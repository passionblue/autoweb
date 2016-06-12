package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.AutositeUser;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.RegisterSimple;
import com.autosite.db.Site;
import com.autosite.ds.AutositeUserDS;
import com.autosite.ds.SiteDS;
import com.autosite.service.AutositeUserService;
import com.autosite.service.AutositeUserServiceFactory;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

public class RegisterSimpleActionExtent extends AutositeActionExtent {

    AutositeUserDS m_autositeUserDS = AutositeUserDS.getInstance();
    
    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#RegisterSimpleAction#xtent.beforeAdd");		
        RegisterSimple _RegisterSimple = (RegisterSimple)baseDbOject;

        //simple register takes email and password and user type. 
        
        if (WebUtil.isNull(_RegisterSimple.getEmail())){
            throw new Exception("Email is missing");
        }
        
        if (WebUtil.isNull(_RegisterSimple.getUsername())){
            _RegisterSimple.setUsername(_RegisterSimple.getEmail());
        }

        //Check the password
        
        //Check the User Type
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        AutositeUserServiceFactory factory = AutositeUserServiceFactory.getInstance();
        AutositeUserService userService = factory.getUserService(site, "simpleRegisterUserService");
        
        m_logger.debug("UserService using " + userService.getClass().getSimpleName());
        AutositeUser user = null;
        try {
            user = userService.createUser(site, _RegisterSimple);
            m_logger.info("** User created at " + site.getSiteUrl() + " id " + user.getId() + " username " + user.getUsername());
        }
        catch (Exception e) {
            new ActionExtentException("Error occurred", "error");
        }
        
        
        
/*        
        
        String userName = _RegisterSimple.getUsername();
        
        
        AutositeUser user = m_autositeUserDS.getBySiteIdUsername(site.getId(), userName);
        
        if (user != null){
            throw new Exception("User " + userName + " alrady exists.");
        }
        
        AutositeUser _AutositeUser = new AutositeUser();
        _AutositeUser.setSiteId(site.getId());
        _AutositeUser.setUsername(WebParamUtil.getStringValue(_RegisterSimple.getUsername()));
        _AutositeUser.setPassword(WebParamUtil.getStringValue(_RegisterSimple.getPassword()));
        _AutositeUser.setEmail(WebParamUtil.getStringValue(_RegisterSimple.getEmail()));
//        _AutositeUser.setUserType(Constants.UserSiteUser);
        _AutositeUser.setUserType(_RegisterSimple.getUserType());
        _AutositeUser.setTimeCreated(WebParamUtil.getDateValue(_RegisterSimple.getTimeCreated()));
        _AutositeUser.setTimeUpdated(WebParamUtil.getDateValue(_RegisterSimple.getTimeUpdated()));
        
        if (!m_autositeUserDS.put(_AutositeUser)){
            throw new Exception("Failed to create a new user");
        }
        
        
        
        AutositeUserService userService = factory.getUserService("chur");
        userService.createUser(site, null);
*/        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#RegisterSimpleAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        RegisterSimple _RegisterSimple = (RegisterSimple)baseDbOject;
        
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#RegisterSimpleAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        RegisterSimple _RegisterSimple = (RegisterSimple)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#RegisterSimpleAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        RegisterSimple _RegisterSimple = (RegisterSimple)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#RegisterSimpleAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        RegisterSimple _RegisterSimple = (RegisterSimple)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#RegisterSimpleAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        RegisterSimple _RegisterSimple = (RegisterSimple)baseDbOject;
    }

    private static Logger m_logger = Logger.getLogger( RegisterSimpleActionExtent.class);
    
}
