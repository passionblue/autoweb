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

import com.autosite.db.ChurIncomeItem;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ChurIncomeItemDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ChurIncomeItemForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;



public class ChurIncomeItemActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurIncomeItemAction#xtent.beforeAdd");		
        ChurIncomeItem _ChurIncomeItem = (ChurIncomeItem)baseDbOject;


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ChurIncomeItemAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ChurIncomeItem _ChurIncomeItem = (ChurIncomeItem)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurIncomeItemAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ChurIncomeItem _ChurIncomeItem = (ChurIncomeItem)baseDbOject;
    }

    public  void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurIncomeItemAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ChurIncomeItem _ChurIncomeItem = (ChurIncomeItem)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurIncomeItemAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ChurIncomeItem _ChurIncomeItem = (ChurIncomeItem)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurIncomeItemAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ChurIncomeItem _ChurIncomeItem = (ChurIncomeItem)baseDbOject;
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

   public String getErrorPage()			{return "chur_income_item_home";}
   public String getWarningPage()		{return "chur_income_item_home";}
   public String getAfterAddPage()		{return "chur_income_item_form";}
   public String getAfterEditPage()		{return "chur_income_item_home";}
   public String getAfterEditFieldPage()	{return "chur_income_item_home";}
   public String getAfterDeletePage()	{return "chur_income_item_home";}
   public String getDefaultPage()		{return "chur_income_item_home";}

   private ChurIncomeItemDS m_ds = ChurIncomeItemDS.getInstance();
   private static Logger m_logger = Logger.getLogger( ChurIncomeItemActionExtent.class);
    
}
