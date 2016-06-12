package com.autosite.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.Constants;
import com.autosite.db.AutositeUser;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.RegisterSimple;
import com.autosite.db.Site;
import com.autosite.ds.AutositeUserDS;
import com.autosite.ds.SiteDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

public class ChurRegisterSimpleActionExtent extends AutositeActionExtent {

    AutositeUserDS m_autositeUserDS = AutositeUserDS.getInstance();
    
    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#RegisterSimpleAction#xtent.beforeAdd");		
        RegisterSimple _RegisterSimple = (RegisterSimple)baseDbOject;

        if (WebUtil.isNull(_RegisterSimple.getEmail())){
            throw new ActionExtentException("Email is missing", "chur_register_add");
        }
        
        if (WebUtil.isNull(_RegisterSimple.getUsername())){
            _RegisterSimple.setUsername(_RegisterSimple.getEmail());
        }
        
        String userName = _RegisterSimple.getUsername();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        
        AutositeUser user = m_autositeUserDS.getBySiteIdUsername(site.getId(), userName);
        
        if (user != null){
            throw new ActionExtentException("User " + userName + " alrady exists.", "chur_register_add");
        }
        
        AutositeUser _AutositeUser = new AutositeUser();
        _AutositeUser.setSiteId(site.getId());
        _AutositeUser.setUsername(WebParamUtil.getStringValue(_RegisterSimple.getUsername()));
        _AutositeUser.setPassword(WebParamUtil.getStringValue(_RegisterSimple.getPassword()));
        _AutositeUser.setEmail(WebParamUtil.getStringValue(_RegisterSimple.getEmail()));
        _AutositeUser.setFirstName("");
        _AutositeUser.setLastName("");
        _AutositeUser.setUserType(Constants.UserSiteUser);
        _AutositeUser.setTimeCreated(WebParamUtil.getDateValue(_RegisterSimple.getTimeCreated()));
        _AutositeUser.setTimeUpdated(WebParamUtil.getDateValue(_RegisterSimple.getTimeUpdated()));
        
        
        if (!m_autositeUserDS.put(_AutositeUser)){
            throw new Exception("Failed to create a new user");
        }
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

    private static Logger m_logger = Logger.getLogger( ChurRegisterSimpleActionExtent.class);
    
}
