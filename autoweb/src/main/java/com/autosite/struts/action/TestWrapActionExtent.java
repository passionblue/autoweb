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

import com.autosite.db.TestCore;
import com.autosite.db.AutositeDataObject;
import com.autosite.ds.TestWrapDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.TestWrapForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;

import com.autosite.holder.TestWrapDataHolder;
import com.autosite.holder.DataHolderObject;


public class TestWrapActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#TestWrapAction#xtent.beforeAdd");		
        TestCore _TestCore = (TestCore)((DataHolderObject)baseDbOject).getDataObject();


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#TestWrapAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        TestCore _TestCore = (TestCore)((DataHolderObject)baseDbOject).getDataObject();
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#TestWrapAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        TestCore _TestCore = (TestCore)((DataHolderObject)baseDbOject).getDataObject();
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#TestWrapAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        TestCore _TestCore = (TestCore)((DataHolderObject)baseDbOject).getDataObject();
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#TestWrapAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        TestCore _TestCore = (TestCore)((DataHolderObject)baseDbOject).getDataObject();
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#TestWrapAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        TestCore _TestCore = (TestCore)((DataHolderObject)baseDbOject).getDataObject();
    }

	//========================================================================================
	//========================================================================================


/*
    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#TestWrapAction#xtent.beforeAdd");		
        TestWrapDataHolder _TestCore = (TestWrapDataHolder)holderObject;


    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject)  throws Exception {
		m_logger.debug("#TestWrapAction#xtent.afterAdd. id=" + holderObject.getId());		
		afterAdd(request, response, holderObject.getDataObject());
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#TestWrapAction#xtent.beforeUpdate. id=" + holderObject.getId());		
		beforeUpdate(request, response, holderObject.getDataObject());
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#TestWrapAction#xtent.afterUpdate. id=" + holderObject.getId());		
		afterUpdate(request, response, holderObject.getDataObject());
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#TestWrapAction#xtent.beforeDelete. id=" + holderObject.getId());		
		beforeDelete(request, response, holderObject.getDataObject());
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, DataHolderObject holderObject) throws Exception{
		m_logger.debug("#TestWrapAction#xtent.afterDelete. id=" + holderObject.getId());		
		afterDelete(request, response, holderObject.getDataObject());
    }
*/

	//========================================================================================
	// Propprietary Commands For This Action
	//========================================================================================

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
	}

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd, AutositeDataObject _TestWrap) throws Exception{
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

//   public  String getErrorPage()			{return "test_wrap_home";}
//   public  ected String getWarningPage()		{return "test_wrap_home";}
//   public  String getAfterAddPage()		{return "test_wrap_form";}
//   public  String getAfterEditPage()		{return "test_wrap_form";}
//   public  String getAfterEditFieldPage()	{return "test_wrap_form";}
//   public  String getAfterDeletePage()	{return "test_wrap_home";}
//   public  String getDefaultPage()		{return "test_wrap_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("TestCore", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("TestCore", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("TestCore", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("TestCore", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("TestCore", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("TestCore", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("TestCore", "action_default_forward_page", "test_wrap_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private TestWrapDS m_ds = TestWrapDS.getInstance();
   private static Logger m_logger = Logger.getLogger( TestWrapActionExtent.class);
    
}
