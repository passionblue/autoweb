package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.autosite.Constants;
import com.autosite.db.AutositeUser;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.Site;
import com.autosite.db.SweepRegister;
import com.autosite.ds.AutositeUserDS;
import com.autosite.ds.SiteDS;
import com.autosite.ds.SweepRegisterDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.UserUtil;
import com.jtrend.session.SessionContext;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

public class SweepRegisterActionExtent extends AutositeActionExtent {

    AutositeUserDS m_autositeUserDS = AutositeUserDS.getInstance();

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepRegisterAction#xtent.beforeAdd");		
        SweepRegister _SweepRegister = (SweepRegister)baseDbOject;

        if ( WebUtil.isNull(_SweepRegister.getEmail())){
            m_logger.debug("email is missing in this requres " + SweepRegisterDS.objectToString(_SweepRegister));
            throw new Exception("Email is missing.");
        }
        if ( WebUtil.isNull(_SweepRegister.getPassword())){
            m_logger.debug("password is missing in this requres " + SweepRegisterDS.objectToString(_SweepRegister));
            throw new Exception("Password is missing.");
        }
        if ( WebUtil.isNull(_SweepRegister.getAgreeTerms())){
            m_logger.debug("agreeTerms is missing in this requres " + SweepRegisterDS.objectToString(_SweepRegister));
            throw new Exception("Agree Terms is missing.");
        }

        String email = _SweepRegister.getEmail();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        
        AutositeUser user = m_autositeUserDS.getBySiteIdUsername(site.getId(), email);
        
        if (user != null){
            throw new Exception("Email " + email + " alrady being used by another user.");
        }
        
        AutositeUser _AutositeUser = new AutositeUser();
        _AutositeUser.setSiteId(site.getId());
        _AutositeUser.setUsername(WebParamUtil.getStringValue(_SweepRegister.getEmail()));
        _AutositeUser.setPassword(WebParamUtil.getStringValue(_SweepRegister.getPassword()));
        _AutositeUser.setEmail(WebParamUtil.getStringValue(_SweepRegister.getEmail()));
        _AutositeUser.setUserType(Constants.UserSiteUser);
        _AutositeUser.setTimeCreated(WebParamUtil.getDateValue(_SweepRegister.getTimeCreated()));
        _AutositeUser.setTimeUpdated(WebParamUtil.getDateValue(_SweepRegister.getTimeCreated()));
        
        if (!m_autositeUserDS.put(_AutositeUser)){
            throw new Exception("Failed to create a new user");
        }
        
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#SweepRegisterAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        SweepRegister _SweepRegister = (SweepRegister)baseDbOject;

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        
        AutositeUser loggedInUser = UserUtil.dbAuthenticate(site, _SweepRegister.getEmail(), _SweepRegister.getPassword());
        
        if (loggedInUser == null) {
            m_logger.info("Somehow couldnt find the user object just registered=" + _SweepRegister.getEmail());
            throw new ActionExtentException("", "login_form");
        }
        
        HttpSession session = request.getSession();
        
        UserUtil.setLogInToSession(session, loggedInUser);
/*        
        AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute(SessionContext.getSessionKey());

        sessionContext.setLogin(true);
        sessionContext.setUsername(_SweepRegister.getEmail());
        sessionContext.setUserObject(loggedInUser);
*/
        
        m_logger.info("Email " + _SweepRegister.getEmail() + " logged in as type=" + loggedInUser.getUserType());
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepRegisterAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        SweepRegister _SweepRegister = (SweepRegister)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepRegisterAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        SweepRegister _SweepRegister = (SweepRegister)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepRegisterAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        SweepRegister _SweepRegister = (SweepRegister)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SweepRegisterAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        SweepRegister _SweepRegister = (SweepRegister)baseDbOject;
    }

	private SweepRegisterDS m_ds = SweepRegisterDS.getInstance();
    private static Logger m_logger = Logger.getLogger( SweepRegisterActionExtent.class);
    
}
