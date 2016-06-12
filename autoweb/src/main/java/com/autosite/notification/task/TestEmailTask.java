package com.autosite.notification.task;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.CleanerCustomerNotification;
import com.autosite.db.Site;
import com.autosite.ds.CleanerCustomerNotificationDS;
import com.autosite.mail.AutositeMailService;
import com.autosite.mail.AutositeMailServiceFactory;
import com.autosite.notification.NotificationMessage;
import com.autosite.notification.NotificationRecipient;
import com.autosite.test.TwilioTest;
import com.autosite.util.cleaner.CleanerDef;
import com.jtrend.concur.SelfControlTask;
import com.jtrend.concur.WorkBasket;
import com.jtrend.util.TimeNow;

public class TestEmailTask  implements SelfControlTask{

    private static Logger m_logger = LoggerFactory.getLogger(TestEmailTask.class);

    private Site m_site;
    private NotificationMessage m_notificationMessage;
    
    public TestEmailTask(Site site, NotificationMessage notificationMeg) {
        m_site = site;
        m_notificationMessage = notificationMeg;
    }
    
    @Override
    public WorkBasket execute() {

        while(true) {
        
            try {

                AutositeMailService service = AutositeMailServiceFactory.getInstance().getAutositeService("joshuayoo@yahoo.com");
                service.sendMail(null, "joshuayoo@yahoo.com", "Nofitication from Tester", "This is Only Test from Test Loop");

                m_logger.info("########### Customer Notification Sent ############");
            
                
                Thread.sleep(10000);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
            }
            
        }
        
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
