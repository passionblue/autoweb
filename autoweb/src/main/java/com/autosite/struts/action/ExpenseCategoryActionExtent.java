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

import com.autosite.db.ExpenseCategory;
import com.autosite.db.AutositeDataObject;
import com.autosite.ds.ExpenseCategoryDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ExpenseCategoryForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;



public class ExpenseCategoryActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ExpenseCategoryActionExtent.beforeAdd");		
        ExpenseCategory _ExpenseCategory = (ExpenseCategory)baseDbOject;


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ExpenseCategoryActionExtent.afterAdd. id=" + baseDbOject.getId());		
        ExpenseCategory _ExpenseCategory = (ExpenseCategory)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ExpenseCategoryActionExtent.beforeUpdate. id=" + baseDbOject.getId());		
        ExpenseCategory _ExpenseCategory = (ExpenseCategory)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ExpenseCategoryActionExtent.afterUpdate. id=" + baseDbOject.getId());		
        ExpenseCategory _ExpenseCategory = (ExpenseCategory)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ExpenseCategoryActionExtent.beforeDelete. id=" + baseDbOject.getId());		
        ExpenseCategory _ExpenseCategory = (ExpenseCategory)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ExpenseCategoryActionExtent.afterDelete. id=" + baseDbOject.getId());		
        ExpenseCategory _ExpenseCategory = (ExpenseCategory)baseDbOject;
    }

	//========================================================================================
	//========================================================================================

	//========================================================================================
	// Propprietary Commands For This Action
	//========================================================================================

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
	}

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd, AutositeDataObject _ExpenseCategory) throws Exception{
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

//   public  String getErrorPage()			{return "expense_category_home";}
//   public  ected String getWarningPage()		{return "expense_category_home";}
//   public  String getAfterAddPage()		{return "expense_category_form";}
//   public  String getAfterEditPage()		{return "expense_category_form";}
//   public  String getAfterEditFieldPage()	{return "expense_category_form";}
//   public  String getAfterDeletePage()	{return "expense_category_home";}
//   public  String getDefaultPage()		{return "expense_category_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseCategory", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseCategory", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseCategory", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseCategory", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseCategory", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseCategory", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ExpenseCategory", "action_default_forward_page", "expense_category_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private ExpenseCategoryDS m_ds = ExpenseCategoryDS.getInstance();
   private static Logger m_logger = Logger.getLogger( ExpenseCategoryActionExtent.class);
    
}
