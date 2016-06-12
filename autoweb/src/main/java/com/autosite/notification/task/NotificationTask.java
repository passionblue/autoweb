package com.autosite.notification.task;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.AutositeRemoteDevice;
import com.autosite.db.Site;
import com.autosite.ds.AutositeRemoteDeviceDS;
import com.autosite.notification.NotificationMessage;
import com.autosite.test.NotificationTest;
import com.autosite.util.DeviceUtil;
import com.jtrend.concur.SelfControlTask;
import com.jtrend.concur.WorkBasket;
import com.jtrend.util.WebUtil;

public class NotificationTask  implements SelfControlTask{

    private static Logger m_logger = LoggerFactory.getLogger(NotificationTask.class);
    
    private Site m_site;
    private NotificationMessage m_notificationMessage;
    
    public NotificationTask(Site site, NotificationMessage notificationMeg) {
        m_site = site;
        m_notificationMessage = notificationMeg;
    }
    
    @Override
    public WorkBasket execute() {
        // TODO Auto-generated method stub
        m_logger.debug("Sending Push Notification. message: " + m_notificationMessage.getMessage());
        
        
        List devices = AutositeRemoteDeviceDS.getInstance().getBySiteId(m_site.getId());
        
        for (Iterator iterator = devices.iterator(); iterator.hasNext();) {

            AutositeRemoteDevice device = (AutositeRemoteDevice) iterator.next();
            String formattedDeviceId = DeviceUtil.formatIphoneDeviceIdForNotification(device.getDeviceId());

            if ( device.getDeviceType() == 0 )  {
                m_logger.debug("Skipping SIMULATOR " + device.getId() + " deviceId: " + formattedDeviceId);
                continue; // Simulator
            }
        
            if( WebUtil.isTrue(device.getDisable())) {
                m_logger.debug("Skipping Device " + device.getId() + " deviceId: " + formattedDeviceId);
                continue;
            }
            

            m_logger.debug("Sending notification to device " + formattedDeviceId);
    
            try {
                NotificationTest.pushTestForDevice(m_notificationMessage.getMessage(), formattedDeviceId);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
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
