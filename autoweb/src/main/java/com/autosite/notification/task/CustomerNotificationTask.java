package com.autosite.notification.task;

import java.util.Iterator;
import java.util.List;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.AutositeRemoteDevice;
import com.autosite.db.CleanerCustomerNotification;
import com.autosite.db.Site;
import com.autosite.ds.AutositeRemoteDeviceDS;
import com.autosite.ds.CleanerCustomerNotificationDS;
import com.autosite.mail.AutositeMailService;
import com.autosite.mail.AutositeMailServiceFactory;
import com.autosite.notification.NotificationMessage;
import com.autosite.notification.NotificationRecipient;
import com.autosite.test.NotificationTest;
import com.autosite.test.TwilioTest;
import com.autosite.util.DeviceUtil;
import com.autosite.util.cleaner.CleanerDef;
import com.jtrend.concur.SelfControlTask;
import com.jtrend.concur.WorkBasket;
import com.jtrend.concur.WorkHandle;
import com.jtrend.concur.impl.DefaultWorkBasket;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebUtil;

public class CustomerNotificationTask  implements SelfControlTask{

    private static Logger m_logger = LoggerFactory.getLogger(CustomerNotificationTask.class);

    private Site m_site;
    private NotificationMessage m_notificationMessage;
    
    public CustomerNotificationTask(Site site, NotificationMessage notificationMeg) {
        m_site = site;
        m_notificationMessage = notificationMeg;
    }
    
    @Override
    public WorkBasket execute() {

        List recipients = m_notificationMessage.getRecipients();
        
        for (Iterator iterator = recipients.iterator(); iterator.hasNext();) {
            NotificationRecipient rec = (NotificationRecipient) iterator.next();
        
            try {
                if ( rec.getMethodType() == CleanerDef.CustomerNotificationMethodTypeSMS ) {
                    
                    TwilioTest.sendSMSText(rec.getRecipientAddress(), m_notificationMessage.getMessage(), false);
                    
                } else if (rec.getMethodType() == CleanerDef.CustomerNotificationMethodTypeEmail)  {

                    AutositeMailService service = AutositeMailServiceFactory.getInstance().getAutositeService(m_notificationMessage.getSenderAddress());
                    
                    service.sendMail(m_notificationMessage.getSenderAddressForReply(), rec.getRecipientAddress(), "Nofitication from "  + m_notificationMessage.getSenderTitle(), m_notificationMessage.getMessage());
                    service.sendHtml(m_notificationMessage.getSenderAddressForReply(), rec.getRecipientAddress(), "Nofitication from "  + m_notificationMessage.getSenderTitle(), 
                            "<html><body><img src='http://blogcafe.dreamwiz.com/cafeimage/w/o/woo4366/woo4366_20120213153401_7079012_1.jpg'></body></html>");
                }

                m_logger.info("########### Customer Notification Sent ############");
                
                CleanerCustomerNotification notification  = new CleanerCustomerNotification();
                notification.setSiteId(m_site.getId());
                notification.setNotificationType(CleanerDef.CustomerNotificationTypeNormal);
                notification.setContent(m_notificationMessage.getMessage());
                notification.setTimeCreated(new TimeNow());
                notification.setMethodType(rec.getMethodType());
                notification.setDestination(rec.getRecipientAddress());
                //notification.setReference("");
                
                CleanerCustomerNotificationDS.getInstance().put(notification);
            
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
            }
            
        }
        
        return null;
    }

    @Override
    public long getTimeLimit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getRepeatOnError() {
        // TODO Auto-generated method stub
        return 0;
    } 

}
