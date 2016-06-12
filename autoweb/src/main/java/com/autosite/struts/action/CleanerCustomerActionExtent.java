package com.autosite.struts.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.autosite.db.AutositeSynchLedger;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.CleanerCustomer;
import com.autosite.ds.AutositeSynchLedgerDS;
import com.autosite.ds.CleanerCustomerDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.struts.core.DefaultPageForwardManager;



public class CleanerCustomerActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerCustomerAction#xtent.beforeAdd");		
        CleanerCustomer _CleanerCustomer = (CleanerCustomer)baseDbOject;

        // If this is remote synching session,  have to check "dup"
        if (m_action.isSynchRequired() && "remote-create".equals(request.getParameter("_synchCmd"))){
            
            String tokenFromRemote = request.getParameter("_synchRemoteToken");
            AutositeSynchLedger ledger = AutositeSynchLedgerDS.getInstance().getBySiteIdToRemoteToken(_CleanerCustomer.getSiteId(), tokenFromRemote);
            if ( ledger != null) {
                //TODO this has to just send synch code instead of processing the all.
                throw new ActionExtentDuplicateAttemptException("Duplicate synch " + ledger.getId() + " synchId " + ledger.getSynchId());
            }
        }
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#CleanerCustomerAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        CleanerCustomer _CleanerCustomer = (CleanerCustomer)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerCustomerAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        CleanerCustomer _CleanerCustomer = (CleanerCustomer)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerCustomerAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        CleanerCustomer _CleanerCustomer = (CleanerCustomer)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerCustomerAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        CleanerCustomer _CleanerCustomer = (CleanerCustomer)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerCustomerAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        CleanerCustomer _CleanerCustomer = (CleanerCustomer)baseDbOject;
    }

	//========================================================================================
	//========================================================================================

	//========================================================================================
	// Propprietary Commands For This Action
	//========================================================================================


    public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
        if ( cmd == null || cmd.length() == 0) return;
        //-----------------------------------------------------------------------------------
        if (cmd.equalsIgnoreCase("synch")) {
            processCommand_synch(request, response, cmd, null);
            return;
        }
    }
    public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd, BaseAutositeDataObject _CleanerCustomer) throws Exception{
        if ( cmd == null || cmd.length() == 0) return;
        //-----------------------------------------------------------------------------------
        if (cmd.equalsIgnoreCase("synch")) {
            processCommand_synch(request, response, cmd, (CleanerCustomer) _CleanerCustomer);
            return;
        }
    }



    private void processCommand_synch(HttpServletRequest request, HttpServletResponse response, String cmd,  CleanerCustomer _CleanerCustomer) throws Exception{
        HttpSession session = request.getSession();
        
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

//   public  String getErrorPage()			{return "cleaner_customer_home";}
//   public  ected String getWarningPage()		{return "cleaner_customer_home";}
//   public  String getAfterAddPage()		{return "cleaner_customer_form";}
//   public  String getAfterEditPage()		{return "cleaner_customer_form";}
//   public  String getAfterEditFieldPage()	{return "cleaner_customer_form";}
//   public  String getAfterDeletePage()	{return "cleaner_customer_home";}
//   public  String getDefaultPage()		{return "cleaner_customer_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerCustomer", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerCustomer", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerCustomer", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerCustomer", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerCustomer", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerCustomer", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerCustomer", "action_default_forward_page", "cleaner_customer_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private CleanerCustomerDS m_ds = CleanerCustomerDS.getInstance();
   private static Logger m_logger = Logger.getLogger( CleanerCustomerActionExtent.class);
    
}
