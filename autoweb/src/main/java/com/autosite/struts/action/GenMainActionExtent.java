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

import com.autosite.db.GenMain;
import com.autosite.db.AutositeDataObject;
import com.autosite.ds.GenMainDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.GenMainForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;



public class GenMainActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenMainAction#xtent.beforeAdd");		
        GenMain _GenMain = (GenMain)baseDbOject;


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#GenMainAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        GenMain _GenMain = (GenMain)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenMainAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        GenMain _GenMain = (GenMain)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenMainAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        GenMain _GenMain = (GenMain)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenMainAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        GenMain _GenMain = (GenMain)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenMainAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        GenMain _GenMain = (GenMain)baseDbOject;
    }

	//========================================================================================
	//========================================================================================

	//========================================================================================
	// Propprietary Commands For This Action
	//========================================================================================

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("move")) {
			processCommand_move(request, response, cmd, null);
			return;
		}
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("register")) {
			processCommand_register(request, response, cmd, null);
			return;
		}
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("cancel")) {
			processCommand_cancel(request, response, cmd, null);
			return;
		}
	}

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd, AutositeDataObject _GenMain) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("move")) {
			processCommand_move(request, response, cmd, (GenMain) _GenMain);
			return;
		}
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("register")) {
			processCommand_register(request, response, cmd, (GenMain) _GenMain);
			return;
		}
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("cancel")) {
			processCommand_cancel(request, response, cmd, (GenMain) _GenMain);
			return;
		}
	}



	private void processCommand_move(HttpServletRequest request, HttpServletResponse response, String cmd,  GenMain _GenMain) throws Exception{
        HttpSession session = request.getSession();
		m_logger.debug("Running command  processCommand_move " );
	}

	private void processCommand_register(HttpServletRequest request, HttpServletResponse response, String cmd,  GenMain _GenMain) throws Exception{
        HttpSession session = request.getSession();
		m_logger.debug("Running command  processCommand_register " );
	}

	private void processCommand_cancel(HttpServletRequest request, HttpServletResponse response, String cmd,  GenMain _GenMain) throws Exception{
        HttpSession session = request.getSession();
		m_logger.debug("Running command  processCommand_cancel " );
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

//   public  String getErrorPage()			{return "gen_main_home";}
//   public  ected String getWarningPage()		{return "gen_main_home";}
//   public  String getAfterAddPage()		{return "gen_main_form";}
//   public  String getAfterEditPage()		{return "gen_main_form";}
//   public  String getAfterEditFieldPage()	{return "gen_main_form";}
//   public  String getAfterDeletePage()	{return "gen_main_home";}
//   public  String getDefaultPage()		{return "gen_main_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenMain", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenMain", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenMain", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenMain", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenMain", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenMain", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenMain", "action_default_forward_page", "gen_main_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private GenMainDS m_ds = GenMainDS.getInstance();
   private static Logger m_logger = Logger.getLogger( GenMainActionExtent.class);
    
}
