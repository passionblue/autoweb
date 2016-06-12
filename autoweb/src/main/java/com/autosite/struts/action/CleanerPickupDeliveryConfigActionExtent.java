package com.autosite.struts.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.CleanerPickupDeliveryConfig;
import com.autosite.ds.CleanerPickupDeliveryConfigDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.struts.core.DefaultPageForwardManager;



public class CleanerPickupDeliveryConfigActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerPickupDeliveryConfigAction#xtent.beforeAdd");		
        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig)baseDbOject;


    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#CleanerPickupDeliveryConfigAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerPickupDeliveryConfigAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerPickupDeliveryConfigAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerPickupDeliveryConfigAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerPickupDeliveryConfigAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig)baseDbOject;
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

//   public  String getErrorPage()			{return "cleaner_pickup_delivery_config_home";}
//   public  ected String getWarningPage()		{return "cleaner_pickup_delivery_config_home";}
//   public  String getAfterAddPage()		{return "cleaner_pickup_delivery_config_form";}
//   public  String getAfterEditPage()		{return "cleaner_pickup_delivery_config_form";}
//   public  String getAfterEditFieldPage()	{return "cleaner_pickup_delivery_config_form";}
//   public  String getAfterDeletePage()	{return "cleaner_pickup_delivery_config_home";}
//   public  String getDefaultPage()		{return "cleaner_pickup_delivery_config_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDeliveryConfig", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDeliveryConfig", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDeliveryConfig", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDeliveryConfig", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDeliveryConfig", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDeliveryConfig", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDeliveryConfig", "action_default_forward_page", "cleaner_pickup_delivery_config_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private CleanerPickupDeliveryConfigDS m_ds = CleanerPickupDeliveryConfigDS.getInstance();
   private static Logger m_logger = Logger.getLogger( CleanerPickupDeliveryConfigActionExtent.class);
    
}
