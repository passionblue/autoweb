package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.autosite.db.AutositeUser;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.Site;
import com.autosite.db.SweepPassword;
import com.autosite.ds.AutositeUserDS;
import com.autosite.ds.SiteDS;
import com.autosite.ds.SweepPasswordDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.UserUtil;
import com.jtrend.session.SessionContext;
import com.jtrend.util.WebUtil;

public class SweepPasswordActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepPasswordAction#xtent.beforeAdd");		
        SweepPassword _SweepPassword = (SweepPassword)baseDbOject;

        if (WebUtil.isNull(_SweepPassword.getSendPasswordEmail())){
            throw new ActionExtentException("Email is required", "");
        }
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#SweepPasswordAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        SweepPassword _SweepPassword = (SweepPassword)baseDbOject;
        
        m_logger.debug("### sending the mail ##");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        AutositeUser user = AutositeUserDS.getInstance().getBySiteIdUsername(site.getId(), _SweepPassword.getSendPasswordEmail());

        if (user != null){
            m_logger.info("User found " + AutositeUserDS.objectToString(user));
        } else {
            m_logger.info("## Email " + _SweepPassword.getSendPasswordEmail() + " not found");
        }
        HttpSession session = request.getSession();
        session.setAttribute("k_top_text", "Password will be sent to the entered email.");
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepPasswordAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        SweepPassword _SweepPassword = (SweepPassword)baseDbOject;

        if (WebUtil.isNull(_SweepPassword.getOldPassword())){
            throw new ActionExtentException("Old Password is required", "");
        }
        if (WebUtil.isNull(_SweepPassword.getNewPassword())){
            throw new ActionExtentException("New Password is required", "");
        }
        if (WebUtil.isNull(_SweepPassword.getPasswordRetype())){
            throw new ActionExtentException("Retype Password is required", "");
        }

        HttpSession session = request.getSession();
        AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute(SessionContext.getSessionKey());
        
        if (!sessionContext.isLogin()){
            throw new ActionExtentException("Please log in first to change your password", "login_form");
        }
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        AutositeUser loggedInUser = UserUtil.findUser(site, sessionContext.getUsername());

        
        if (!loggedInUser.getPassword().equals(_SweepPassword.getOldPassword())){
            throw new ActionExtentException("Old password does not match. Please try again", "");
        }
        m_logger.info("Password changing from " + _SweepPassword.getOldPassword());
        
        loggedInUser.setPassword(_SweepPassword.getNewPassword());
        AutositeUserDS.getInstance().update(loggedInUser);
        m_logger.info("Password changed to " + _SweepPassword.getOldPassword());
    
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepPasswordAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        SweepPassword _SweepPassword = (SweepPassword)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepPasswordAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        SweepPassword _SweepPassword = (SweepPassword)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepPasswordAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        SweepPassword _SweepPassword = (SweepPassword)baseDbOject;
    }

	private SweepPasswordDS m_ds = SweepPasswordDS.getInstance();
    private static Logger m_logger = Logger.getLogger( SweepPasswordActionExtent.class);
    
}
