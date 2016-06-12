package com.autosite.struts.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.autosite.db.AutositeUser;
import com.autosite.db.Site;
import com.autosite.ds.CleanerRegisterStartDS;
import com.autosite.ds.SiteDS;
import com.autosite.holder.CleanerRegisterStartDataHolder;
import com.autosite.holder.DataHolderObject;
import com.autosite.service.AutositeAccountService;
import com.autosite.service.AutositeSubsiteService;
import com.autosite.service.AutositeSubsiteServiceFactory;
import com.autosite.service.AutositeUserService;
import com.autosite.service.AutositeUserServiceFactory;
import com.autosite.service.impl.DefaultAutositeAccountServiceImpl;
import com.autosite.session.AutositeSessionContextUtil;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.access.AccessDef.SystemRole;
import com.jtrend.session.SessionContext;
import com.jtrend.struts.core.DefaultPageForwardManager;
import com.jtrend.util.WebUtil;


public class CleanerRegisterStartActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


	//========================================================================================
	//========================================================================================
    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#CleanerRegisterStartAction#xtent.beforeAdd");
        HttpSession session = request.getSession();

        CleanerRegisterStartDataHolder _CleanerRegisterStart = (CleanerRegisterStartDataHolder)holderObject;
        Site currentBaseSite = SiteDS.getInstance().getById(_CleanerRegisterStart.getSiteId());
        SessionContext sessionContext = AutositeSessionContextUtil.getSessionContextFromHttpSession(session);
        
        if ( !WebUtil.isTrue(currentBaseSite.getSubdomainEnable()) ) {
            throw new ActionExtentException("System Error", new Exception("This site doesn't not allow subdomain. " + currentBaseSite.getSiteUrl()));
        }
        
        if ( WebUtil.isNull(_CleanerRegisterStart.getSiteName())){
            m_logger.debug("siteName is missing in this requres " + CleanerRegisterStartDS.objectToString(_CleanerRegisterStart));
            throw new ActionExtentException("Site Name is missing.");
        }
        if ( WebUtil.isNull(_CleanerRegisterStart.getEmail())){
            m_logger.debug("email is missing in this requres " + CleanerRegisterStartDS.objectToString(_CleanerRegisterStart));
            throw new ActionExtentException("Email is missing.");
        }
        if ( WebUtil.isNull(_CleanerRegisterStart.getPassword())){
            m_logger.debug("password is missing in this requres " + CleanerRegisterStartDS.objectToString(_CleanerRegisterStart));
            throw new ActionExtentException("Password is missing.");
        }
        if ( sessionContext.getSessionType() == SessionContext.SESSION_TYPE_MOBILE_PAGELESS) {
            m_logger.debug("no password checking for pageless session");
        } else {
            if ( WebUtil.isNull(_CleanerRegisterStart.getPasswordRepeat())){
                m_logger.debug("passwordRepeat is missing in this requres " + CleanerRegisterStartDS.objectToString(_CleanerRegisterStart));
                throw new ActionExtentException("Password Repeat is missing.");
            }
            
            if ( !_CleanerRegisterStart.getPassword().equals(_CleanerRegisterStart.getPasswordRepeat())){
                m_logger.debug("Password don't match with re-typed password " + _CleanerRegisterStart.getPassword() + " re=" + _CleanerRegisterStart.getPasswordRepeat());
                throw new ActionExtentException("Password don't match with re-typed password.");
            }
        }
        
        AutositeSubsiteService  subsiteService = null;
        AutositeUserService     userService = null;
        AutositeAccountService accountService = null; 
        try {
            subsiteService = AutositeSubsiteServiceFactory.getInstance().getSubSiteService(currentBaseSite.getSiteUrl());
            userService = AutositeUserServiceFactory.getInstance().getUserService();
            accountService = new DefaultAutositeAccountServiceImpl();
        }
        catch (Exception e1) {
            m_logger.error(e1.getMessage(),e1);
            throw new ActionExtentException("System error", e1);
        }

        if (subsiteService == null) {
            m_logger.error("subsiteService is not initialized. Please check applicationContext-subSiteService.xml for the service definition");
            throw new ActionExtentException("System error");
        }
        
        
        try {
            //Create Site
            Site createdSubSite = subsiteService.createSubSite(currentBaseSite,_CleanerRegisterStart.getSiteName().toLowerCase());
            
            //Create Autosite User
//            AutositeUser createdUser = userService.createUser(createdSubSite, _CleanerRegisterStart.getEmail(), _CleanerRegisterStart.getPassword());
            AutositeUser createdUser = userService.createUser(createdSubSite, _CleanerRegisterStart.getUsername(), _CleanerRegisterStart.getEmail(), _CleanerRegisterStart.getPassword(), SystemRole.SiteAdmin.level(), true);
            
            // Account
            accountService.createAccountFor(createdSubSite, _CleanerRegisterStart.getSiteTitle(), createdUser);

            //Save it
            request.getSession().setAttribute("autosite.session.cleaner.register.user", createdUser);
            request.getSession().setAttribute("autosite.session.cleaner.register.subsiteObj", createdSubSite);

        }
        catch (ActionExtentException e) {
            m_logger.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(), e);
            throw new ActionExtentException("System error", e);
        }
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject)  throws Exception {
		
        m_logger.debug("#CleanerRegisterStartAction#xtent.afterAdd. id=" + holderObject.getId());       
        afterAdd(request, response, holderObject.getDataObject());

        CleanerRegisterStartDataHolder _CleanerRegisterStart = (CleanerRegisterStartDataHolder)holderObject;

        HttpSession session = request.getSession();

        session.setAttribute("autosite.session.cleaner.register.subsite",  _CleanerRegisterStart.getSiteName());
        
        Site subSiteObj = (Site) session.getAttribute("autosite.session.cleaner.register.subsiteObj");

        java.net.URL url = new java.net.URL(request.getRequestURL().toString());
        
        String fullURL = "http://" + subSiteObj.getSiteUrl();
        
        if ( url.getPort() != 80 && url.getPort() > 0 )
            fullURL += ":" + url.getPort();
        
            _CleanerRegisterStart.setCreatedSiteUrl(fullURL);
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#CleanerRegisterStartAction#xtent.beforeUpdate. id=" + holderObject.getId());		
		beforeUpdate(request, response, holderObject.getDataObject());
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#CleanerRegisterStartAction#xtent.afterUpdate. id=" + holderObject.getId());		
		afterUpdate(request, response, holderObject.getDataObject());
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#CleanerRegisterStartAction#xtent.beforeDelete. id=" + holderObject.getId());		
		beforeDelete(request, response, holderObject.getDataObject());
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#CleanerRegisterStartAction#xtent.afterDelete. id=" + holderObject.getId());		
		afterDelete(request, response, holderObject.getDataObject());
    }

	//========================================================================================
	// Propprietary Commands For This Action
	//========================================================================================

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
	}



	//========================================================================================
	// AJAX
	//========================================================================================
    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map ret = new HashMap();
        return ret;
    }

	//========================================================================================
	// Configuration Option
	//========================================================================================

//   public  String getErrorPage()			{return "cleaner_register_start_home";}
//   public  ected String getWarningPage()		{return "cleaner_register_start_home";}
//   public  String getAfterAddPage()		{return "cleaner_register_start_form";}
//   public  String getAfterEditPage()		{return "cleaner_register_start_form";}
//   public  String getAfterEditFieldPage()	{return "cleaner_register_start_form";}
//   public  String getAfterDeletePage()	{return "cleaner_register_start_home";}
//   public  String getDefaultPage()		{return "cleaner_register_start_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerRegisterStart", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerRegisterStart", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerRegisterStart", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerRegisterStart", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerRegisterStart", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerRegisterStart", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerRegisterStart", "action_default_forward_page", "cleaner_register_start_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private CleanerRegisterStartDS m_ds = CleanerRegisterStartDS.getInstance();
   private static Logger m_logger = Logger.getLogger( CleanerRegisterStartActionExtent.class);
    
}
