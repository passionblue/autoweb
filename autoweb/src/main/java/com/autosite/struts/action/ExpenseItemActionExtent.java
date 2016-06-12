/* 
Template last modification history:


Source Generated: Sat Jul 11 13:13:02 EDT 2015
*/

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

import com.autosite.db.ExpenseItem;
import com.autosite.db.AutositeDataObject;
import com.autosite.ds.ExpenseItemDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ExpenseItemForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;



public class ExpenseItemActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ExpenseItemActionExtent.beforeAdd");		
        ExpenseItem _ExpenseItem = (ExpenseItem)baseDbOject;


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ExpenseItemActionExtent.afterAdd. id=" + baseDbOject.getId());		
        ExpenseItem _ExpenseItem = (ExpenseItem)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ExpenseItemActionExtent.beforeUpdate. id=" + baseDbOject.getId());		
        ExpenseItem _ExpenseItem = (ExpenseItem)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ExpenseItemActionExtent.afterUpdate. id=" + baseDbOject.getId());		
        ExpenseItem _ExpenseItem = (ExpenseItem)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ExpenseItemActionExtent.beforeDelete. id=" + baseDbOject.getId());		
        ExpenseItem _ExpenseItem = (ExpenseItem)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ExpenseItemActionExtent.afterDelete. id=" + baseDbOject.getId());		
        ExpenseItem _ExpenseItem = (ExpenseItem)baseDbOject;
    }

	//========================================================================================
	//========================================================================================

	//========================================================================================
	// Propprietary Commands For This Action
	//========================================================================================

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
	}

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd, AutositeDataObject _ExpenseItem) throws Exception{
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

//   public  String getErrorPage()			{return "expense_item_home";}
//   public  ected String getWarningPage()		{return "expense_item_home";}
//   public  String getAfterAddPage()		{return "expense_item_form";}
//   public  String getAfterEditPage()		{return "expense_item_form";}
//   public  String getAfterEditFieldPage()	{return "expense_item_form";}
//   public  String getAfterDeletePage()	{return "expense_item_home";}
//   public  String getDefaultPage()		{return "expense_item_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseItem", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseItem", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseItem", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseItem", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseItem", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseItem", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseItem", "action_default_forward_page", "expense_item_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private ExpenseItemDS m_ds = ExpenseItemDS.getInstance();
   private static Logger m_logger = Logger.getLogger( ExpenseItemActionExtent.class);
    
}
