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

import com.autosite.db.AutositeDataObject;
import com.autosite.ds.GenFormFlowDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.GenFormFlowForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;

import com.autosite.holder.GenFormFlowDataHolder;
import com.autosite.holder.DataHolderObject;


public class GenFormFlowActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


	//========================================================================================
	//========================================================================================


/*
    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenFormFlowAction#xtent.beforeAdd");		
        GenFormFlowDataHolder _GenFormFlow = (GenFormFlowDataHolder)holderObject;


    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject)  throws Exception {
		m_logger.debug("#GenFormFlowAction#xtent.afterAdd. id=" + holderObject.getId());		
		afterAdd(request, response, holderObject.getDataObject());
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenFormFlowAction#xtent.beforeUpdate. id=" + holderObject.getId());		
		beforeUpdate(request, response, holderObject.getDataObject());
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenFormFlowAction#xtent.afterUpdate. id=" + holderObject.getId());		
		afterUpdate(request, response, holderObject.getDataObject());
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenFormFlowAction#xtent.beforeDelete. id=" + holderObject.getId());		
		beforeDelete(request, response, holderObject.getDataObject());
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenFormFlowAction#xtent.afterDelete. id=" + holderObject.getId());		
		afterDelete(request, response, holderObject.getDataObject());
    }
*/

	//========================================================================================
	// Propprietary Commands For This Action
	//========================================================================================

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("command1")) {
			processCommand_command1(request, response, cmd, null);
			return;
		}
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("command2")) {
			processCommand_command2(request, response, cmd, null);
			return;
		}
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("command3")) {
			processCommand_command3(request, response, cmd, null);
			return;
		}
	}

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd, AutositeDataObject _GenFormFlow) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("command1")) {
			processCommand_command1(request, response, cmd, (GenFormFlowDataHolder) _GenFormFlow);
			return;
		}
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("command2")) {
			processCommand_command2(request, response, cmd, (GenFormFlowDataHolder) _GenFormFlow);
			return;
		}
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("command3")) {
			processCommand_command3(request, response, cmd, (GenFormFlowDataHolder) _GenFormFlow);
			return;
		}
	}



	private void processCommand_command1(HttpServletRequest request, HttpServletResponse response, String cmd,  GenFormFlowDataHolder _GenFormFlow) throws Exception{
        HttpSession session = request.getSession();
		m_logger.debug("Running command  processCommand_command1 " );
	}

	private void processCommand_command2(HttpServletRequest request, HttpServletResponse response, String cmd,  GenFormFlowDataHolder _GenFormFlow) throws Exception{
        HttpSession session = request.getSession();
		m_logger.debug("Running command  processCommand_command2 " );
	}

	private void processCommand_command3(HttpServletRequest request, HttpServletResponse response, String cmd,  GenFormFlowDataHolder _GenFormFlow) throws Exception{
        HttpSession session = request.getSession();
		m_logger.debug("Running command  processCommand_command3 " );
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

//   public  String getErrorPage()			{return "gen_form_flow_home";}
//   public  ected String getWarningPage()		{return "gen_form_flow_home";}
//   public  String getAfterAddPage()		{return "gen_form_flow_form";}
//   public  String getAfterEditPage()		{return "gen_form_flow_form";}
//   public  String getAfterEditFieldPage()	{return "gen_form_flow_form";}
//   public  String getAfterDeletePage()	{return "gen_form_flow_home";}
//   public  String getDefaultPage()		{return "gen_form_flow_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFormFlow", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFormFlow", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFormFlow", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFormFlow", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFormFlow", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFormFlow", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenFormFlow", "action_default_forward_page", "gen_form_flow_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private GenFormFlowDS m_ds = GenFormFlowDS.getInstance();
   private static Logger m_logger = Logger.getLogger( GenFormFlowActionExtent.class);
    
}
