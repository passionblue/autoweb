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

import com.autosite.db.ChurPayee;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ChurPayeeDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ChurPayeeForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;

import com.autosite.holder.ChurPayeeDataHolder;
import com.autosite.holder.GenTableDataHolder;
import com.autosite.holder.DataHolderObject;


public class ChurPayeeActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurPayeeAction#xtent.beforeAdd");		
        ChurPayee _ChurPayee = (ChurPayee)baseDbOject;

        if (WebUtil.isNull(_ChurPayee.getTitle()))
            new ActionExtentException("Title is required field");
        
    }

    public  void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ChurPayeeAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ChurPayee _ChurPayee = (ChurPayee)baseDbOject;
    }

    public  void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurPayeeAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ChurPayee _ChurPayee = (ChurPayee)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurPayeeAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ChurPayee _ChurPayee = (ChurPayee)baseDbOject;
    }

    public  void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurPayeeAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ChurPayee _ChurPayee = (ChurPayee)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ChurPayeeAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ChurPayee _ChurPayee = (ChurPayee)baseDbOject;
    }

	//========================================================================================
	//========================================================================================
    public  void beforeAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurPayeeAction#xtent.beforeAdd");		
		beforeAdd(request, response, holderObject.getDataObject());
    }

    public  void afterAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject)  throws Exception {
		m_logger.debug("#ChurPayeeAction#xtent.afterAdd. id=" + holderObject.getId());		
		afterAdd(request, response, holderObject.getDataObject());
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurPayeeAction#xtent.beforeUpdate. id=" + holderObject.getId());		
		beforeUpdate(request, response, holderObject.getDataObject());
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurPayeeAction#xtent.afterUpdate. id=" + holderObject.getId());		
		afterUpdate(request, response, holderObject.getDataObject());
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurPayeeAction#xtent.beforeDelete. id=" + holderObject.getId());		
		beforeDelete(request, response, holderObject.getDataObject());
    }

    public  void afterDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#ChurPayeeAction#xtent.afterDelete. id=" + holderObject.getId());		
		afterDelete(request, response, holderObject.getDataObject());
    }

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

//   public String getErrorPage()			{return "chur_payee_home";}
//   public String getWarningPage()		{return "chur_payee_home";}
//   public String getAfterAddPage()		{return "chur_payee_form";}
//   public String getAfterEditPage()		{return "chur_payee_form";}
//   public String getAfterEditFieldPage()	{return "chur_payee_form";}
//   public String getAfterDeletePage()	{return "chur_payee_home";}
//   public String getDefaultPage()		{return "chur_payee_home";}

   public String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurPayee", "action_error_forward_page", "chur_payee_home");}
   public String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurPayee", "action_warning_forward_page", "chur_payee_home");}
   public String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurPayee", "action_normal_add_forward_page", "chur_payee_form");}
   public String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurPayee", "action_normal_edit_forward_page", "chur_payee_form");}
   public String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurPayee", "action_normal_editfield_forward_page", "chur_payee_form");}
   public String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurPayee", "action_normal_delete_forward_page", "chur_payee_home");}
   public String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("ChurPayee", "action_default_forward_page", "chur_payee_home");}
   public String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private ChurPayeeDS m_ds = ChurPayeeDS.getInstance();
   private static Logger m_logger = Logger.getLogger( ChurPayeeActionExtent.class);
    
}
