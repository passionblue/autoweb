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

import com.autosite.db.CleanerProduct;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.CleanerProductDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.CleanerProductForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;



public class CleanerProductActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerProductAction#xtent.beforeAdd");		
        CleanerProduct _CleanerProduct = (CleanerProduct)baseDbOject;


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#CleanerProductAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        CleanerProduct _CleanerProduct = (CleanerProduct)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerProductAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        CleanerProduct _CleanerProduct = (CleanerProduct)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerProductAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        CleanerProduct _CleanerProduct = (CleanerProduct)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerProductAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        CleanerProduct _CleanerProduct = (CleanerProduct)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerProductAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        CleanerProduct _CleanerProduct = (CleanerProduct)baseDbOject;
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

//   public  String getErrorPage()			{return "cleaner_product_home";}
//   public  ected String getWarningPage()		{return "cleaner_product_home";}
//   public  String getAfterAddPage()		{return "cleaner_product_form";}
//   public  String getAfterEditPage()		{return "cleaner_product_form";}
//   public  String getAfterEditFieldPage()	{return "cleaner_product_form";}
//   public  String getAfterDeletePage()	{return "cleaner_product_home";}
//   public  String getDefaultPage()		{return "cleaner_product_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerProduct", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerProduct", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerProduct", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerProduct", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerProduct", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerProduct", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerProduct", "action_default_forward_page", "cleaner_product_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private CleanerProductDS m_ds = CleanerProductDS.getInstance();
   private static Logger m_logger = Logger.getLogger( CleanerProductActionExtent.class);
    
}
