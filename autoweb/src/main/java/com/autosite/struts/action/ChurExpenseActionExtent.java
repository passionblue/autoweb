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

import com.autosite.db.ChurExpense;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ChurExpenseDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ChurExpenseForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;



public class ChurExpenseActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseAction#xtent.beforeAdd");		
        ChurExpense _ChurExpense = (ChurExpense)baseDbOject;


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ChurExpenseAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ChurExpense _ChurExpense = (ChurExpense)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ChurExpense _ChurExpense = (ChurExpense)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ChurExpense _ChurExpense = (ChurExpense)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ChurExpense _ChurExpense = (ChurExpense)baseDbOject;
    }

    public  void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ChurExpense _ChurExpense = (ChurExpense)baseDbOject;
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

//   public String getErrorPage()			{return "chur_expense_home";}
//   public String getWarningPage()		{return "chur_expense_home";}
//   public String getAfterAddPage()		{return "chur_expense_form";}
//   public String getAfterEditPage()		{return "chur_expense_form";}
//   public String getAfterEditFieldPage()	{return "chur_expense_form";}
//   public String getAfterDeletePage()	{return "chur_expense_home";}
//   public String getDefaultPage()		{return "chur_expense_home";}

   public String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurExpense", "action_error_forward_page", "chur_expense_home");}
   public String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurExpense", "action_warning_forward_page", "chur_expense_home");}
   public String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurExpense", "action_normal_add_forward_page", "chur_expense_form");}
   public String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurExpense", "action_normal_edit_forward_page", "chur_expense_form");}
   public String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurExpense", "action_normal_editfield_forward_page", "chur_expense_form");}
   public String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurExpense", "action_normal_delete_forward_page", "chur_expense_home");}
   public String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurExpense", "action_default_forward_page", "chur_expense_home");}
   public String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private ChurExpenseDS m_ds = ChurExpenseDS.getInstance();
   private static Logger m_logger = Logger.getLogger( ChurExpenseActionExtent.class);
    
}
