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

import com.autosite.db.ThemeAggregator;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ThemeAggregatorDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ThemeAggregatorForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;



public class ThemeAggregatorActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ThemeAggregatorAction#xtent.beforeAdd");		
        ThemeAggregator _ThemeAggregator = (ThemeAggregator)baseDbOject;


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ThemeAggregatorAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ThemeAggregator _ThemeAggregator = (ThemeAggregator)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ThemeAggregatorAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ThemeAggregator _ThemeAggregator = (ThemeAggregator)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ThemeAggregatorAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ThemeAggregator _ThemeAggregator = (ThemeAggregator)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ThemeAggregatorAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ThemeAggregator _ThemeAggregator = (ThemeAggregator)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ThemeAggregatorAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ThemeAggregator _ThemeAggregator = (ThemeAggregator)baseDbOject;
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

//   public  String getErrorPage()			{return "theme_aggregator_home";}
//   public  ected String getWarningPage()		{return "theme_aggregator_home";}
//   public  String getAfterAddPage()		{return "theme_aggregator_form";}
//   public  String getAfterEditPage()		{return "theme_aggregator_form";}
//   public  String getAfterEditFieldPage()	{return "theme_aggregator_form";}
//   public  String getAfterDeletePage()	{return "theme_aggregator_home";}
//   public  String getDefaultPage()		{return "theme_aggregator_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("ThemeAggregator", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ThemeAggregator", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ThemeAggregator", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ThemeAggregator", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("ThemeAggregator", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("ThemeAggregator", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ThemeAggregator", "action_default_forward_page", "theme_aggregator_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private ThemeAggregatorDS m_ds = ThemeAggregatorDS.getInstance();
   private static Logger m_logger = Logger.getLogger( ThemeAggregatorActionExtent.class);
    
}
