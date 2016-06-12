package com.autosite.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.CleanerCustomer;
import com.autosite.db.CleanerCustomerNotification;
import com.autosite.db.Site;
import com.autosite.ds.CleanerCustomerNotificationDS;
import com.autosite.notification.task.CustomerNotificationTask;
import com.autosite.notification.task.NotificationTask;
import com.autosite.util.cleaner.CleanerDef;
import com.jtrend.concur.WorkHandle;
import com.jtrend.concur.impl.DefaultTaskServicer;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebUtil;

public class NotificationManager {

    private static Logger m_logger = LoggerFactory.getLogger(NotificationManager.class);
    
    
    private static NotificationManager m_instance = new NotificationManager();

    public static NotificationManager getInstance() {
        return m_instance;
    }

    
    DefaultTaskServicer m_servicer = new DefaultTaskServicer();
    
    private NotificationManager() {
    }
    
    public void sendPushNotificationToDevice(Site site, String message){
        
//        WorkHandle handle = m_servicer.dropAndGo(new NotificationTask(site, message));
    }
    
    public void sendNoficationToPhoneNumber ( Site site, String phoneNumber,  String message, String senderTitle ) {
        
        NotificationMessage notifMsg = new NotificationMessage(message);
        
        notifMsg.setMessage(message);
        notifMsg.addRecipients(NotificationUtil.createSmsRecipient(phoneNumber));
        notifMsg.setSenderTitle(senderTitle);
        
        sendNotificationToCustomer(site, notifMsg);
    }    

    public void sendNoficationToEmail ( Site site, String email,  String message , String senderAddress, String senderAddressForReply, String senderTitle ) {
        
        NotificationMessage notifMsg = new NotificationMessage(message);
        
        notifMsg.setMessage(message);
        notifMsg.addRecipients(NotificationUtil.createEmailRecipient(email));
        notifMsg.setSenderTitle(senderTitle);
        
        notifMsg.setSenderAddress(senderAddress); // This is the key for service. see applicationContext-mail.xml
        notifMsg.setSenderAddressForReply(senderAddressForReply); // This will be display in the notification email
        
        sendNotificationToCustomer(site, notifMsg);
    }    
    
    // 0510 imcomplete yet. 
    public void sendNoficationToCustomer ( Site site, CleanerCustomer customer, String message ) {

        m_logger.info("Sending notification to customer " + customer + " message:" + message);
        
        NotificationMessage notifMsg = new NotificationMessage(message);
        
        notifMsg.setMessage(message);
        
        if ( WebUtil.isTrue(customer.getPhonePreferred()) && WebUtil.isNotNull(customer.getPhone()) ){
            sendNoficationToPhoneNumber(site, customer.getEmail(), message, "");
            m_logger.info("Sending notification to customer " + customer.getId() + " message:" + message + " via " + customer.getEmail() );
        } else {
            sendNoficationToEmail(site, customer.getPhone(), message, "","", "");
            m_logger.info("Sending notification to customer " + customer.getId() + " message:" + message + " via " + customer.getPhone());
        }
    }
    

    private void sendNotificationToCustomer(Site site, NotificationMessage message) {
        
//        CleanerCustomerNotification notification  = new CleanerCustomerNotification();
//        notification.setSiteId(site.getId());
//        notification.setNotificationType(CleanerDef.CustomerNotificationTypeNormal);
//        notification.setContent(message.getMessage());
//        notification.setTimeCreated(new TimeNow());
      
  
        
        WorkHandle handle = m_servicer.dropAndGo(new CustomerNotificationTask(site, message));        
        
//        CleanerCustomerNotificationDS.getInstance().put(notification);
    }
}
