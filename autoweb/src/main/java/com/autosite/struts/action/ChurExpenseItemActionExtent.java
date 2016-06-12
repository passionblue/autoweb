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

import com.autosite.db.ChurExpenseItem;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ChurExpenseItemDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ChurExpenseItemForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;



public class ChurExpenseItemActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseItemAction#xtent.beforeAdd");		
        ChurExpenseItem _ChurExpenseItem = (ChurExpenseItem)baseDbOject;


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ChurExpenseItemAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ChurExpenseItem _ChurExpenseItem = (ChurExpenseItem)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseItemAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ChurExpenseItem _ChurExpenseItem = (ChurExpenseItem)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseItemAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ChurExpenseItem _ChurExpenseItem = (ChurExpenseItem)baseDbOject;
    }

    public  void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseItemAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ChurExpenseItem _ChurExpenseItem = (ChurExpenseItem)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurExpenseItemAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ChurExpenseItem _ChurExpenseItem = (ChurExpenseItem)baseDbOject;
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

   public String getErrorPage()			{return "chur_expense_item_home";}
   public String getWarningPage()		{return "chur_expense_item_home";}
   public String getAfterAddPage()		{return "chur_expense_item_form";}
   public String getAfterEditPage()		{return "chur_expense_item_home";}
   public String getAfterEditFieldPage()	{return "chur_expense_item_home";}
   public String getAfterDeletePage()	{return "chur_expense_item_home";}
   public String getDefaultPage()		{return "chur_expense_item_home";}

   private ChurExpenseItemDS m_ds = ChurExpenseItemDS.getInstance();
   private static Logger m_logger = Logger.getLogger( ChurExpenseItemActionExtent.class);
    
}
