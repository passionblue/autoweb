package com.autosite.struts.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.autosite.Constants;
import com.autosite.concur2.AutositeEventHandlerManager;
import com.autosite.db.AutositeAccount;
import com.autosite.db.AutositeDataObject;
import com.autosite.db.AutositeRemoteDevice;
import com.autosite.db.AutositeSynchLedger;
import com.autosite.db.AutositeDataObject;
import com.autosite.db.CleanerCustomer;
import com.autosite.db.CleanerPickupDelivery;
import com.autosite.db.Site;
import com.autosite.ds.AutositeAccountDS;
import com.autosite.ds.AutositeRemoteDeviceDS;
import com.autosite.ds.AutositeSynchLedgerDS;
import com.autosite.ds.CleanerCustomerDS;
import com.autosite.ds.CleanerPickupDeliveryDS;
import com.autosite.ds.SiteDS;
import com.autosite.notification.NotificationManager;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.AutositeSessionContextUtil;
import com.autosite.session.devicesynch.AutositeLedgerSynchTrackingManager;
import com.autosite.session.devicesynch.DeviceSynchTracker;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.PhoneNumberUtil;
import com.autosite.util.SiteConfigUtil;
import com.autosite.util.cleaner.CleanerDef;
import com.autosite.util.cleaner.CleanerUtil;
import com.jtrend.struts.core.DefaultPageForwardManager;
import com.jtrend.util.TimeFormatUtil;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;



public class CleanerPickupDeliveryActionExtent extends AutositeActionExtent {

	//========================================================================================
	//========================================================================================


    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerPickupDeliveryAction#xtent.beforeAdd");		
        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery)baseDbOject;

        _CleanerPickupDelivery.setPickupTicket(CleanerUtil.generatePickupDeliveryTicketSerial());

        if ( WebUtil.isNull(_CleanerPickupDelivery.getCustomerName())) {
            throw new ActionExtentException("Name is required.");
        }
        
        if ( WebUtil.isNull(_CleanerPickupDelivery.getAddress())) {
            throw new ActionExtentException("Address is required");
        }
        
        switch(_CleanerPickupDelivery.getAckReceiveMethod()) {
        case CleanerDef.CustomerNotificationMethodTypeSMS: 
            if (!PhoneNumberUtil.isValidFormat(_CleanerPickupDelivery.getPhone())){
                throw new ActionExtentException("Phone number seems invalid format.");
            }
            
            _CleanerPickupDelivery.setPhone(PhoneNumberUtil.convertToStandard(_CleanerPickupDelivery.getPhone()));
            break;
        case CleanerDef.CustomerNotificationMethodTypeEmail: 
            if (WebUtil.isNull(_CleanerPickupDelivery.getEmail())){
                throw new ActionExtentException("Email is required to notify you.");
            }
            break;

        default:
        
            
            break;
        }
     
//        DefaultTaskServicer m_servicer = new DefaultTaskServicer();
//        WorkHandle handle = m_servicer.dropAndGo(new TestEmailTask(null, null));        
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#CleanerPickupDeliveryAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery)baseDbOject;
        
        AutositeEventHandlerManager.getInstance().dropEvent("CustomerRequest", "CleanerPickupDelivery", "New request has been registered"); //TODO
        
        Site site = SiteDS.getInstance().getById(_CleanerPickupDelivery.getSiteId());
        NotificationManager.getInstance().sendPushNotificationToDevice(site, "New Pickup/Delivery request received");
        AutositeEventHandlerManager.getInstance().dropEvent("PushNotification", "CleanerPickupDelivery", "Sent"); //TODO
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerPickupDeliveryAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerPickupDeliveryAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerPickupDeliveryAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, AutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#CleanerPickupDeliveryAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery)baseDbOject;
    }

	//========================================================================================
	//========================================================================================

	//========================================================================================
	// Propprietary Commands For This Action
	//========================================================================================

	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("confirmToRequest")) {
			processCommand_confirmToRequest(request, response, cmd, null);
			return;
		}
	}
	public void processCommand(HttpServletRequest request, HttpServletResponse response, String cmd, AutositeDataObject _CleanerPickupDelivery) throws Exception{
		if ( cmd == null || cmd.length() == 0) return;
		//-----------------------------------------------------------------------------------
		if (cmd.equalsIgnoreCase("confirmToRequest")) {
			processCommand_confirmToRequest(request, response, cmd, (CleanerPickupDelivery) _CleanerPickupDelivery);
			return;
		}
        //-----------------------------------------------------------------------------------
        if (cmd.equalsIgnoreCase("cancelRequest")) {
            processCommand_cancelRequest(request, response, cmd, (CleanerPickupDelivery) _CleanerPickupDelivery);
            return;
        }
        //-----------------------------------------------------------------------------------
        if (cmd.equalsIgnoreCase("setReadyToRequest")) {
            processCommand_setReadyToRequest(request, response, cmd, (CleanerPickupDelivery) _CleanerPickupDelivery);
            return;
        }
        //-----------------------------------------------------------------------------------
        if (cmd.equalsIgnoreCase("setCompletedToRequest")) {
            processCommand_setCompletedToRequest(request, response, cmd, (CleanerPickupDelivery) _CleanerPickupDelivery);
            return;
        }
        //-----------------------------------------------------------------------------------
        if (cmd.equalsIgnoreCase("sendCustomNotification")) {
            processCommand_sendCustomNotification(request, response, cmd, (CleanerPickupDelivery) _CleanerPickupDelivery);
            return;
        }
		
	}



	private void processCommand_confirmToRequest(HttpServletRequest request, HttpServletResponse response, String cmd,  CleanerPickupDelivery _CleanerPickupDelivery) throws Exception{
        HttpSession session = request.getSession();
        
        CleanerPickupDelivery pickupDelivery = CleanerPickupDeliveryDS.getInstance().getById(WebParamUtil.getLongValue(request.getParameter("id")));
        
        if ( pickupDelivery == null) {
            throw new ActionExtentException("Request not found by id " + WebParamUtil.getLongValue(request.getParameter("id")));
        }                          
        
        // send customer a notification 
        m_logger.debug("Sending Method " + pickupDelivery.getAckReceiveMethod() + " email " + pickupDelivery.getEmail() + " phone " + pickupDelivery.getPhone());

        Site                    site            = SiteDS.getInstance().getById(_CleanerPickupDelivery.getSiteId());
        AutositeSessionContext  sessionContext  = (AutositeSessionContext)AutositeSessionContextUtil.getSessionContextFromHttpSession(session);
        AutositeRemoteDevice    device          = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());
        
        if ( pickupDelivery.getTimeNotified() != null ) {
            return;
        }

        sendNotificationFor(site, pickupDelivery, pickupDelivery.getCustomerName() + ",\nYour delivery/pickup request has been received and confirmed at " + TimeFormatUtil.convertFromTime(new Date()) + "\n\nThank you for using our service");
        pickupDelivery.setTimeNotified(new TimeNow());
        pickupDelivery.setTimeAcked(new TimeNow());
        pickupDelivery.setTimeUpdated(new TimeNow());

        if ( sessionContext.getUserObject() != null) {
            pickupDelivery.setAckedByUserId(sessionContext.getUserObject().getId());
            m_logger.info("###### PickupDelivery " + pickupDelivery.getId() + " confirmed by " + sessionContext.getUserObject().getUsername());
        }

        CleanerPickupDeliveryDS.getInstance().update(pickupDelivery);
        createOrUpdateSynchLedger(pickupDelivery, device);
        AutositeEventHandlerManager.getInstance().dropEvent("CustomerNotification", "CleanerPickupDelivery", "CONFIRMED at "); //TODO

	}

	private void processCommand_cancelRequest(HttpServletRequest request, HttpServletResponse response, String cmd,  CleanerPickupDelivery _CleanerPickupDelivery) throws Exception{

	    HttpSession session = request.getSession();
        m_logger.debug("Running command  processCommand_cancelRequest " );

        CleanerPickupDelivery pickupDelivery = CleanerPickupDeliveryDS.getInstance().getById(WebParamUtil.getLongValue(request.getParameter("id")));
        
        if ( pickupDelivery == null) {
            throw new ActionExtentException("Request not found by id " + WebParamUtil.getLongValue(request.getParameter("id")));
        }                          
        
        // send customer a notification 
        m_logger.debug("Sending Method " + pickupDelivery.getAckReceiveMethod() + " email " + pickupDelivery.getEmail() + " phone " + pickupDelivery.getPhone());

        Site                    site            = SiteDS.getInstance().getById(_CleanerPickupDelivery.getSiteId());
        AutositeSessionContext  sessionContext  = (AutositeSessionContext)AutositeSessionContextUtil.getSessionContextFromHttpSession(session);
        AutositeRemoteDevice    device          = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());
        
        if ( pickupDelivery.getTimeCompleted() != null) {  
            return;
        }

        sendNotificationFor(site, pickupDelivery, pickupDelivery.getCustomerName() + ",\n Your delivery/pickup has been cancelled at //time");
        
        pickupDelivery.setTimeUpdated(new TimeNow());
        pickupDelivery.setCancelled(Constants.TRUE);
        CleanerPickupDeliveryDS.getInstance().update(pickupDelivery);

        createOrUpdateSynchLedger(pickupDelivery, device);
        AutositeEventHandlerManager.getInstance().dropEvent("CustomerNotification", "CleanerPickupDelivery", "Cancelled"); //TODO
        
	}

    private void processCommand_setReadyToRequest(HttpServletRequest request, HttpServletResponse response, String cmd,  CleanerPickupDelivery _CleanerPickupDelivery) throws Exception{
        
        HttpSession session = request.getSession();
        m_logger.debug("Running command  processCommand_cancelRequest " );

        CleanerPickupDelivery pickupDelivery = CleanerPickupDeliveryDS.getInstance().getById(WebParamUtil.getLongValue(request.getParameter("id")));
        
        if ( pickupDelivery == null) {
            throw new ActionExtentException("Request not found by id " + WebParamUtil.getLongValue(request.getParameter("id")));
        }                          
        
        // send customer a notification 
        m_logger.debug("Sending Method " + pickupDelivery.getAckReceiveMethod() + " email " + pickupDelivery.getEmail() + " phone " + pickupDelivery.getPhone());

        Site                    site            = SiteDS.getInstance().getById(_CleanerPickupDelivery.getSiteId());
        AutositeSessionContext  sessionContext  = (AutositeSessionContext)AutositeSessionContextUtil.getSessionContextFromHttpSession(session);
        AutositeRemoteDevice    device          = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());
        
        if ( pickupDelivery.getTimeReady() != null) {  
            return;
        }
        sendNotificationFor(site, pickupDelivery, pickupDelivery.getCustomerName() + ",\n Your delivery/pickup has been done at //time");

        pickupDelivery.setTimeUpdated(new TimeNow());
        pickupDelivery.setTimeReady(new TimeNow());
        CleanerPickupDeliveryDS.getInstance().update(pickupDelivery);

        createOrUpdateSynchLedger(pickupDelivery, device);
        AutositeEventHandlerManager.getInstance().dropEvent("CustomerNotification", "CleanerPickupDelivery", "Ready"); //TODO
        
    }

    private void processCommand_setCompletedToRequest(HttpServletRequest request, HttpServletResponse response, String cmd,  CleanerPickupDelivery _CleanerPickupDelivery) throws Exception{
        HttpSession session = request.getSession();
        m_logger.debug("Running command  processCommand_cancelRequest " );

        CleanerPickupDelivery pickupDelivery = CleanerPickupDeliveryDS.getInstance().getById(WebParamUtil.getLongValue(request.getParameter("id")));
        
        if ( pickupDelivery == null) {
            throw new ActionExtentException("Request not found by id " + WebParamUtil.getLongValue(request.getParameter("id")));
        }                          
        
        // send customer a notification 
        m_logger.debug("Sending Method " + pickupDelivery.getAckReceiveMethod() + " email " + pickupDelivery.getEmail() + " phone " + pickupDelivery.getPhone());

        Site                    site            = SiteDS.getInstance().getById(_CleanerPickupDelivery.getSiteId());
        AutositeSessionContext  sessionContext  = (AutositeSessionContext)AutositeSessionContextUtil.getSessionContextFromHttpSession(session);
        AutositeRemoteDevice    device          = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());
        
        if ( pickupDelivery.getTimeCompleted() != null) {  
            return;
        }

        sendNotificationFor(site, pickupDelivery, pickupDelivery.getCustomerName() + ",\n Your delivery/pickup has been done at //time");

        pickupDelivery.setTimeUpdated(new TimeNow());
        pickupDelivery.setTimeCompleted(new TimeNow());
        CleanerPickupDeliveryDS.getInstance().update(pickupDelivery);

        createOrUpdateSynchLedger(pickupDelivery, device);
        AutositeEventHandlerManager.getInstance().dropEvent("CustomerNotification","CleanerPickupDelivery", "Completed"); //TODO
    
    }

    private void processCommand_sendCustomNotification(HttpServletRequest request, HttpServletResponse response, String cmd,  CleanerPickupDelivery _CleanerPickupDelivery) throws Exception{
      
        
        HttpSession session = request.getSession();
        m_logger.debug("Running command  processCommand_sendCustomNotification " );
        String message = request.getParameter("msg");

        CleanerPickupDelivery pickupDelivery = CleanerPickupDeliveryDS.getInstance().getById(WebParamUtil.getLongValue(request.getParameter("id")));
        
        if ( pickupDelivery == null) {
            throw new ActionExtentException("Request not found by id " + WebParamUtil.getLongValue(request.getParameter("id")));
        }                          
        
        // send customer a notification 
        m_logger.debug("Sending Method " + pickupDelivery.getAckReceiveMethod() + " email " + pickupDelivery.getEmail() + " phone " + pickupDelivery.getPhone() + " custom " + message);

        Site                    site            = SiteDS.getInstance().getById(_CleanerPickupDelivery.getSiteId());
        AutositeSessionContext  sessionContext  = (AutositeSessionContext)AutositeSessionContextUtil.getSessionContextFromHttpSession(session);
        AutositeRemoteDevice    device          = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());
        
        if ( pickupDelivery.getTimeReady() != null) {  
            return;
        }

        sendNotificationFor(site, pickupDelivery, pickupDelivery.getCustomerName() + ",\n " + message);
        
        pickupDelivery.setTimeUpdated(new TimeNow());
        pickupDelivery.setTimeReady(new TimeNow());
        CleanerPickupDeliveryDS.getInstance().update(pickupDelivery);

        createOrUpdateSynchLedger(pickupDelivery, device);
        AutositeEventHandlerManager.getInstance().dropEvent("CustomerNotification", "CleanerPickupDelivery", "Custom Message"); //TODO
    }


	private void createOrUpdateSynchLedger(CleanerPickupDelivery pickupDelivery, AutositeRemoteDevice device) {

        DeviceSynchTracker   tracker    = AutositeLedgerSynchTrackingManager.getInstance().getDeviceTracker(device);
        
        
        //Added Synch Ledger for an update just done
        AutositeSynchLedger synchLedger = new AutositeSynchLedger();
        AutositeSynchLedger existingUpdateSynchLedger  = tracker.findSynchLedger("cleaner-pickup-delivery-update", pickupDelivery.getId());

        if ( existingUpdateSynchLedger != null)  {
            existingUpdateSynchLedger.setRemoteToken(null);
            existingUpdateSynchLedger.setSynchId(null);
            AutositeSynchLedgerDS.getInstance().update(existingUpdateSynchLedger);
        } else {
            
            synchLedger.setSiteId(pickupDelivery.getSiteId());
            synchLedger.setScope("cleaner-pickup-delivery-update");
            synchLedger.setObjectId(pickupDelivery.getId());
            synchLedger.setTarget(pickupDelivery.getClass().getSimpleName());
            synchLedger.setTimeCreated(new TimeNow());
            synchLedger.setDeviceId(device.getId());
            AutositeSynchLedgerDS.getInstance().put(synchLedger);
        }        

	}
    
    private void sendNotificationFor(Site site, CleanerPickupDelivery pickupDelivery, String message ) {
        CleanerCustomer customer = CleanerCustomerDS.getInstance().getById(pickupDelivery.getCustomerId());
        
        if ( customer == null) {

            if (pickupDelivery.getAckReceiveMethod() == CleanerDef.CustomerNotificationMethodTypeEmail) {
                
                AutositeAccount account = AutositeAccountDS.getInstance().getObjectBySiteId(site.getId());

                if ( account == null) {
                    m_logger.info("Account not returned for the site " + site.getSiteUrl() + " Notification will be aborted");
                    return;
                }
                
                String senderEmailForReply = account.getEmail(); //By default use the account bound email. 
                
                // In case use, catchAll->Forward mechanism, use this only after the catchAll account or actual account has been set up in case customer replys.  
                // This will create reply purpose email that look like    storeId@registersite.com
                
                if( true)  { //TODO should be configurable some how. But dont know yet. 
                
                    String storeId = SiteConfigUtil.getSitePrefixForSubsite(site);
                    
                    senderEmailForReply = storeId + "@" + site.getSiteRegisterSite();
                    m_logger.info("Sender email for reply replaced from " + account.getEmail() + " to " + senderEmailForReply);
                }
                
                NotificationManager.getInstance().sendNoficationToEmail(site, pickupDelivery.getEmail(), message, account.getEmail(), senderEmailForReply, account.getCompany());
                
            } else if (pickupDelivery.getAckReceiveMethod() == CleanerDef.CustomerNotificationMethodTypeSMS){
                NotificationManager.getInstance().sendNoficationToPhoneNumber(site, PhoneNumberUtil.convertToNumberOnly(pickupDelivery.getPhone()), message, "");
                
            } else {
                m_logger.warn("#### Can't do it for this type " + pickupDelivery.getAckReceiveMethod());
            }
            
        } else {
            NotificationManager.getInstance().sendNoficationToCustomer(site, customer, "Pickup request has been confirmed");
        }
        
        pickupDelivery.setTimeNotified(new TimeNow());

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

//   public  String getErrorPage()			{return "cleaner_pickup_delivery_home";}
//   public  ected String getWarningPage()		{return "cleaner_pickup_delivery_home";}
//   public  String getAfterAddPage()		{return "cleaner_pickup_delivery_form";}
//   public  String getAfterEditPage()		{return "cleaner_pickup_delivery_form";}
//   public  String getAfterEditFieldPage()	{return "cleaner_pickup_delivery_form";}
//   public  String getAfterDeletePage()	{return "cleaner_pickup_delivery_home";}
//   public  String getDefaultPage()		{return "cleaner_pickup_delivery_home";}


//   public  String getErrorPage()			{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDelivery", "action_error_forward_page", getDefaultPage());}
//   public  String getWarningPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDelivery", "action_warning_forward_page", getDefaultPage());}
//   public  String getAfterAddPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDelivery", "action_normal_add_forward_page", getDefaultPage());}
//   public  String getAfterEditPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDelivery", "action_normal_edit_forward_page", getDefaultPage());}
//   public  String getAfterEditFieldPage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDelivery", "action_normal_editfield_forward_page", getDefaultPage());}
//   public  String getAfterDeletePage()	{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDelivery", "action_normal_delete_forward_page", getDefaultPage());}

   public  String getDefaultPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo("CleanerPickupDelivery", "action_default_forward_page", "cleaner_pickup_delivery_home");}
   public  String getConfirmPage()		{return DefaultPageForwardManager.getInstance().getPageForwardTo(((ChurMemberAction)m_action).getActionGroupName(), "confirm_page" , "confirm_required");}


   private CleanerPickupDeliveryDS m_ds = CleanerPickupDeliveryDS.getInstance();
   private static Logger m_logger = Logger.getLogger( CleanerPickupDeliveryActionExtent.class);
    
}
