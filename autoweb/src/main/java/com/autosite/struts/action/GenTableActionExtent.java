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

import com.autosite.db.GenTable;
import com.autosite.db.AutositeDataObject;
import com.autosite.ds.GenTableDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.GenTableForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;

import com.autosite.holder.GenTableDataHolder;
import com.autosite.holder.DataHolderObject;


public class GenTableActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenTableAction#xtent.beforeAdd");		
        GenTable _GenTable = (GenTable)((DataHolderObject)baseDbOject).getDataObject();


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#GenTableAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        GenTable _GenTable = (GenTable)((DataHolderObject)baseDbOject).getDataObject();
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenTableAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        GenTable _GenTable = (GenTable)((DataHolderObject)baseDbOject).getDataObject();
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenTableAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        GenTable _GenTable = (GenTable)((DataHolderObject)baseDbOject).getDataObject();
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenTableAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        GenTable _GenTable = (GenTable)((DataHolderObject)baseDbOject).getDataObject();
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#GenTableAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        GenTable _GenTable = (GenTable)((DataHolderObject)baseDbOject).getDataObject();
    }

	//========================================================================================
	//========================================================================================


/*
    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenTableAction#xtent.beforeAdd");		
        GenTableDataHolder _GenTable = (GenTableDataHolder)holderObject;


    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject)  throws Exception {
		m_logger.debug("#GenTableAction#xtent.afterAdd. id=" + holderObject.getId());		
		afterAdd(request, response, holderObject.getDataObject());
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenTableAction#xtent.beforeUpdate. id=" + holderObject.getId());		
		beforeUpdate(request, response, holderObject.getDataObject());
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenTableAction#xtent.afterUpdate. id=" + holderObject.getId());		
		afterUpdate(request, response, holderObject.getDataObject());
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenTableAction#xtent.beforeDelete. id=" + holderObject.getId());		
		beforeDelete(request, response, holderObject.getDataObject());
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#GenTableAction#xtent.afterDelete. id=" + holderObject.getId());		
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

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd, AutositeDataObject _GenTable) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("command1")) {
			processCommand_command1(request, response, cmd, (GenTableDataHolder) _GenTable);
			return;
		}
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("command2")) {
			processCommand_command2(request, response, cmd, (GenTableDataHolder) _GenTable);
			return;
		}
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("command3")) {
			processCommand_command3(request, response, cmd, (GenTableDataHolder) _GenTable);
			return;
		}
	}



	private void processCommand_command1(HttpServletRequest request, HttpServletResponse response, String cmd,  GenTableDataHolder _GenTable) throws Exception{
        HttpSession session = request.getSession();
		m_logger.debug("Running command  processCommand_command1 " );
	}

	private void processCommand_command2(HttpServletRequest request, HttpServletResponse response, String cmd,  GenTableDataHolder _GenTable) throws Exception{
        HttpSession session = request.getSession();
		m_logger.debug("Running command  processCommand_command2 " );
	}

	private void processCommand_command3(HttpServletRequest request, HttpServletResponse response, String cmd,  GenTableDataHolder _GenTable) throws Exception{
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

//   public  String getErrorPage()			{return "gen_table_home";}
//   public  ected String getWarningPage()		{return "gen_table_home";}
//   public  String getAfterAddPage()		{return "gen_table_form";}
//   public  String getAfterEditPage()		{return "gen_table_form";}
//   public  String getAfterEditFieldPage()	{return "gen_table_form";}
//   public  String getAfterDeletePage()	{return "gen_table_home";}
//   public  String getDefaultPage()		{return "gen_table_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenTable", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenTable", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenTable", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenTable", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenTable", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenTable", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("GenTable", "action_default_forward_page", "gen_table_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private GenTableDS m_ds = GenTableDS.getInstance();
   private static Logger m_logger = Logger.getLogger( GenTableActionExtent.class);
    
}
