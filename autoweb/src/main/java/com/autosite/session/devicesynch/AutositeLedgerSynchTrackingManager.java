package com.autosite.session.devicesynch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.AutositeRemoteDevice;
import com.autosite.db.AutositeSynchLedger;
import com.autosite.ds.AutositeRemoteDeviceDS;
import com.autosite.ds.AutositeSynchLedgerDS;
import com.autosite.ds.event.DSEvent;
import com.autosite.ds.event.DSEventListener;

public class AutositeLedgerSynchTrackingManager implements DSEventListener {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeLedgerSynchTrackingManager.class);
    
    private static AutositeLedgerSynchTrackingManager m_instance = new AutositeLedgerSynchTrackingManager();

    private Map<Long, DeviceSynchTracker> m_rootByDeviceId = new ConcurrentHashMap<Long, DeviceSynchTracker>();
    
    public static AutositeLedgerSynchTrackingManager getInstance() {
        return m_instance;
    }

    private AutositeLedgerSynchTrackingManager() {
        
        List allDevices = AutositeRemoteDeviceDS.getInstance().getAll();
        
        for (Iterator iterator = allDevices.iterator(); iterator.hasNext();) {
            AutositeRemoteDevice device = (AutositeRemoteDevice) iterator.next();
            registerDevice(device);
        }
        
        AutositeSynchLedgerDS.getInstance().addUpdateListener(this.getClass().getSimpleName(), this);
        m_logger.info("AutositeLedgerSynchTrackingManager registered to AutositeSynchLedgerDS");
    }
    
    @Override
    public void updated(DSEvent event) {
        List devices  = new ArrayList(m_rootByDeviceId.values());
        
        AutositeSynchLedger ledger = (AutositeSynchLedger)event.getObject();
        
        for (Iterator iterator = devices.iterator(); iterator.hasNext();) {
            DeviceSynchTracker tracker = (DeviceSynchTracker) iterator.next();
            if ( tracker.getDevice().getSiteId()!= ledger.getSiteId() )
                continue;
            tracker.process(ledger);
        }
    }
    
    // Register device into manager. This doesn't create a device
    public void registerDevice(AutositeRemoteDevice device){
        if ( !m_rootByDeviceId.containsKey(new Long(device.getId()))){
            DeviceSynchTracker deviceTracker = new DeviceSynchTracker(device);
            m_rootByDeviceId.put(device.getId(), deviceTracker);
            
            m_logger.info("Device registered " + device.getId());
        }
    }

    public void unregisterDevice(AutositeRemoteDevice device){
        if ( m_rootByDeviceId.containsKey(new Long(device.getId()))){
            m_rootByDeviceId.remove(device.getId());
            m_logger.info("Device unregistered " + device.getId());
        }
    }
    
    public DeviceSynchTracker getDeviceTracker(AutositeRemoteDevice device){
        if ( device == null) return null;
        return  m_rootByDeviceId.get(device.getId());
    }
}
