package com.autosite.struts.action;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PollConfig;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.PollConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PollConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;



public class PollConfigActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollConfigAction#xtent.beforeAdd");		
        PollConfig _PollConfig = (PollConfig)baseDbOject;


        if (PollConfigDS.getInstance().getObjectByPollId(_PollConfig.getPollId()) != null) {
            m_logger.debug("The same value already exists." + _PollConfig.getPollId());
            throw new ActionExtentException("The same value already exists.", "linkto_add");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PollConfigAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        PollConfig _PollConfig = (PollConfig)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollConfigAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        PollConfig _PollConfig = (PollConfig)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollConfigAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        PollConfig _PollConfig = (PollConfig)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollConfigAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        PollConfig _PollConfig = (PollConfig)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollConfigAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        PollConfig _PollConfig = (PollConfig)baseDbOject;
    }

	//========================================================================================
	//========================================================================================

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

//   public String getErrorPage()			{return "$action_error_forward_page";}
//   public String getWarningPage()		{return "$action_warning_forward_page";}
//   public String getAfterAddPage()		{return "$action_normal_add_forward_page";}
//   public String getAfterEditPage()		{return "$action_normal_edit_forward_page";}
//   public String getAfterEditFieldPage()	{return "$action_normal_editfield_forward_page";}
//   public String getAfterDeletePage()	{return "$action_normal_delete_forward_page";}
//   public String getDefaultPage()		{return "$action_default_forward_page";}

   public String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("PollConfig", "action_error_forward_page", "$action_error_forward_page");}
   public String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("PollConfig", "action_warning_forward_page", "$action_warning_forward_page");}
   public String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("PollConfig", "action_normal_add_forward_page", "$action_normal_add_forward_page");}
   public String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("PollConfig", "action_normal_edit_forward_page", "$action_normal_edit_forward_page");}
   public String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("PollConfig", "action_normal_editfield_forward_page", "$action_normal_editfield_forward_page");}
   public String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("PollConfig", "action_normal_delete_forward_page", "$action_normal_delete_forward_page");}
   public String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("PollConfig", "action_default_forward_page", "$action_default_forward_page");}
   public String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "$confirm_page");}


   private PollConfigDS m_ds = PollConfigDS.getInstance();
   private static Logger m_logger = Logger.getLogger( PollConfigActionExtent.class);
    
}
