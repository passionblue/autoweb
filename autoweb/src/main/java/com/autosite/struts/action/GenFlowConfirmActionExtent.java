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

import com.autosite.ds.GenFlowConfirmDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.GenFlowConfirmForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;

import com.autosite.holder.GenFlowConfirmDataHolder;
import com.autosite.holder.DataHolderObject;


public class GenFlowConfirmActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


	//========================================================================================
	//========================================================================================
    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenFlowConfirmAction#xtent.beforeAdd");		
		beforeAdd(request, response, holderObject.getDataObject());
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject)  throws Exception {
		m_logger.debug("#GenFlowConfirmAction#xtent.afterAdd. id=" + holderObject.getId());		
		afterAdd(request, response, holderObject.getDataObject());
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenFlowConfirmAction#xtent.beforeUpdate. id=" + holderObject.getId());		
		beforeUpdate(request, response, holderObject.getDataObject());
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenFlowConfirmAction#xtent.afterUpdate. id=" + holderObject.getId());		
		afterUpdate(request, response, holderObject.getDataObject());
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenFlowConfirmAction#xtent.beforeDelete. id=" + holderObject.getId());		
		beforeDelete(request, response, holderObject.getDataObject());
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenFlowConfirmAction#xtent.afterDelete. id=" + holderObject.getId());		
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

//   public  String getErrorPage()			{return "gen_flow_confirm_home";}
//   public  ected String getWarningPage()		{return "gen_flow_confirm_home";}
//   public  String getAfterAddPage()		{return "gen_flow_confirm_form";}
//   public  String getAfterEditPage()		{return "gen_flow_confirm_form";}
//   public  String getAfterEditFieldPage()	{return "gen_flow_confirm_form";}
//   public  String getAfterDeletePage()	{return "gen_flow_confirm_home";}
//   public  String getDefaultPage()		{return "gen_flow_confirm_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFlowConfirm", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFlowConfirm", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFlowConfirm", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFlowConfirm", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFlowConfirm", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFlowConfirm", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFlowConfirm", "action_default_forward_page", "gen_flow_confirm_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private GenFlowConfirmDS m_ds = GenFlowConfirmDS.getInstance();
   private static Logger m_logger = Logger.getLogger( GenFlowConfirmActionExtent.class);
    
}
