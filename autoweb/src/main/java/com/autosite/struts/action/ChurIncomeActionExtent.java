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

import com.autosite.db.ChurIncome;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ChurIncomeDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ChurIncomeForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;

import com.autosite.holder.ChurIncomeDataHolder;
import com.autosite.holder.GenTableDataHolder;
import com.autosite.holder.DataHolderObject;


public class ChurIncomeActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurIncomeAction#xtent.beforeAdd");		
        ChurIncome _ChurIncome = (ChurIncome)baseDbOject;


        
    }

    public  void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ChurIncomeAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ChurIncome _ChurIncome = (ChurIncome)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurIncomeAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ChurIncome _ChurIncome = (ChurIncome)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurIncomeAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ChurIncome _ChurIncome = (ChurIncome)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurIncomeAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ChurIncome _ChurIncome = (ChurIncome)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurIncomeAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ChurIncome _ChurIncome = (ChurIncome)baseDbOject;
    }

	//========================================================================================
	//========================================================================================
/*    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurIncomeAction#xtent.beforeAdd");		
		beforeAdd(request, response, holderObject.getDataObject());
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject)  throws Exception {
		m_logger.debug("#ChurIncomeAction#xtent.afterAdd. id=" + holderObject.getId());		
		afterAdd(request, response, holderObject.getDataObject());
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurIncomeAction#xtent.beforeUpdate. id=" + holderObject.getId());		
		beforeUpdate(request, response, holderObject.getDataObject());
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurIncomeAction#xtent.afterUpdate. id=" + holderObject.getId());		
		afterUpdate(request, response, holderObject.getDataObject());
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurIncomeAction#xtent.beforeDelete. id=" + holderObject.getId());		
		beforeDelete(request, response, holderObject.getDataObject());
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurIncomeAction#xtent.afterDelete. id=" + holderObject.getId());		
		afterDelete(request, response, holderObject.getDataObject());
    }*/

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

//   public String getErrorPage()			{return "chur_income_home";}
//   public String getWarningPage()		{return "chur_income_home";}
//   public String getAfterAddPage()		{return "chur_income_form";}
//   public String getAfterEditPage()		{return "chur_income_form";}
//   public String getAfterEditFieldPage()	{return "chur_income_form";}
//   public String getAfterDeletePage()	{return "chur_income_home";}
//   public String getDefaultPage()		{return "chur_income_home";}

   public String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncome", "action_error_forward_page", "chur_income_home");}
   public String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncome", "action_warning_forward_page", "chur_income_home");}
   public String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncome", "action_normal_add_forward_page", "chur_income_form");}
   public String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncome", "action_normal_edit_forward_page", "chur_income_form");}
   public String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncome", "action_normal_editfield_forward_page", "chur_income_form");}
   public String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncome", "action_normal_delete_forward_page", "chur_income_home");}
   public String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurIncome", "action_default_forward_page", "chur_income_home");}
   public String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private ChurIncomeDS m_ds = ChurIncomeDS.getInstance();
   private static Logger m_logger = Logger.getLogger( ChurIncomeActionExtent.class);
    
}
