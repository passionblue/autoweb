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

import com.autosite.db.ChurExpenseCategory;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ChurExpenseCategoryDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ChurExpenseCategoryForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;



public class ChurExpenseCategoryActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseCategoryAction#xtent.beforeAdd");		
        ChurExpenseCategory _ChurExpenseCategory = (ChurExpenseCategory)baseDbOject;


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ChurExpenseCategoryAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ChurExpenseCategory _ChurExpenseCategory = (ChurExpenseCategory)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseCategoryAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ChurExpenseCategory _ChurExpenseCategory = (ChurExpenseCategory)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseCategoryAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ChurExpenseCategory _ChurExpenseCategory = (ChurExpenseCategory)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseCategoryAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ChurExpenseCategory _ChurExpenseCategory = (ChurExpenseCategory)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseCategoryAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ChurExpenseCategory _ChurExpenseCategory = (ChurExpenseCategory)baseDbOject;
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
    public  Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map ret = new HashMap();
        return ret;
    }

	//========================================================================================
	// Configuration Option
	//========================================================================================

   public String getErrorPage()			{return "chur_expense_category_home";}
   public String getWarningPage()		{return "chur_expense_category_home";}
   public String getAfterAddPage()		{return "chur_expense_category_form";}
   public String getAfterEditPage()		{return "chur_expense_category_home";}
   public String getAfterEditFieldPage()	{return "chur_expense_category_home";}
   public String getAfterDeletePage()	{return "chur_expense_category_home";}
   public String getDefaultPage()		{return "chur_expense_category_home";}

   private ChurExpenseCategoryDS m_ds = ChurExpenseCategoryDS.getInstance();
   private static Logger m_logger = Logger.getLogger( ChurExpenseCategoryActionExtent.class);
    
}
