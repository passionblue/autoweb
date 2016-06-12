package com.autosite.synch.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.AutositeDataObject;
import com.autosite.db.SynkNamespaceRecord;
import com.autosite.db.SynkNodeTracker;
import com.autosite.db.SynkNodeTrackerTx;
import com.autosite.ds.SynkNamespaceRecordDS;
import com.autosite.ds.SynkNodeTrackerDS;
import com.autosite.ds.SynkNodeTrackerTxDS;
import com.autosite.synch.SynkModHandler;
import com.autosite.synch.SynkUpdateHandler;

public class SynkHandlerImpl implements SynkUpdateHandler, SynkModHandler {

    private static Logger m_logger = LoggerFactory.getLogger(SynkHandlerImpl.class);
    
    private String namespace;
    private String deviceId;
    private String remoteIpAddress;
    
    private long lastConfirmedStamp;
    
    private Map<String, AtomicLong> indexTracker;
    
    public SynkHandlerImpl(String namespace, String deviceId) {
        super();
        this.namespace = namespace;
        this.deviceId = deviceId;
        
        // Get the lastConfirm for this
        List<SynkNodeTracker> list = SynkNodeTrackerDS.getInstance().getBySiteIdDeviceIdList(0, deviceId);
        
        SynkNodeTracker existing = null;
        for (SynkNodeTracker synkNodeTracker : list) {
            if( synkNodeTracker.getNamespace().equalsIgnoreCase(namespace)) {
                existing = synkNodeTracker;
            }
        }
        
        if (existing != null) {
            lastConfirmedStamp = existing.getStamp();
            m_logger.info("Initialized SynkUpdateHandlerImpl for " + deviceId  + " np:" + namespace + " lastConfirm:" + lastConfirmedStamp );
        }
    }

    @Override
    public String getNamesapce() {
        return namespace;
    }

    @Override
    public List<SynkNamespaceRecord> getAllRecords() {
        return getUpdatesFromAllRecords(0, false);
    }

    @Override
    public List<SynkNamespaceRecord> getUpdates(long stamp) {

        m_logger.info("Getting non-synked updates from " + stamp);

        return getUpdatesFromAllRecords(stamp, false); // TODO should be updated
    }

    
    @Override
    public List<SynkNamespaceRecord> getUpdatesFromLastConfirm() {
        return getUpdatesFromAllRecords(lastConfirmedStamp, false);
    }

    @Override
    public void confirm(long stamp, String txToken, int numRecords, String remoteId) throws Exception {
        
        confirmRecords(stamp, txToken, numRecords, remoteId, false, false);
    }
    
    public void confirmToSelf(long stamp, String txToken, int numRecords, String remoteId) throws Exception {
        confirmRecords(stamp, txToken, numRecords, remoteId, true, false);
    }
    
    public void confirmRecords(long stamp, String txToken, int numRecords, String remoteId, boolean selfConfirm, boolean retrievalReceit) throws Exception {
        
        /*
         *  get the existing tracker for the namespace and deviceid
         */
        
        if ( stamp <=0 && numRecords == 0 ) {
            return;
        }
        
        List<SynkNodeTracker> list = SynkNodeTrackerDS.getInstance().getBySiteIdDeviceIdList(0, deviceId);
        SynkNodeTracker existing = null;
        for (SynkNodeTracker synkNodeTracker : list) {
            if( synkNodeTracker.getNamespace().equalsIgnoreCase(namespace)) {
                existing = synkNodeTracker;
                break;
            }
        }
        
        long oldStamp = 0;
        if ( existing != null) {
            
            oldStamp = existing.getStamp(); //save before replace to put into tx

            if ( retrievalReceit ){
                existing.setStampRetrieved(stamp);
                existing.setTimeRetrieved(new Timestamp(System.currentTimeMillis()));
            } else {
                existing.setStamp(stamp);
            }
            
            SynkNodeTrackerDS.getInstance().update(existing);
            
        } else {
        
            SynkNodeTracker tracker = new SynkNodeTracker();
            tracker.setSiteId(0);
            tracker.setDeviceId(deviceId);
            tracker.setNamespace(namespace);
            tracker.setTimeCreated(new Timestamp(System.currentTimeMillis()));
            tracker.setTimeUpdated(tracker.getTimeCreated());

            if ( retrievalReceit ){
                tracker.setStampRetrieved(stamp);
                tracker.setTimeRetrieved(tracker.getTimeCreated());
            } else {
                tracker.setStamp(stamp);
            }
            
            
            SynkNodeTrackerDS.getInstance().put(tracker);
        }        
        
        /*
         * Add to TX journals. This is moving object. make sure no other place create tx log
         */
        
        SynkNodeTrackerTx tx = new SynkNodeTrackerTx();
        tx.setSiteId(0);
        tx.setDeviceId(deviceId);
        tx.setNamespace(namespace);
        tx.setStampAcked(stamp);
        tx.setStampLast(oldStamp);
        tx.setTxToken(txToken);
        tx.setIp(remoteIpAddress);
        tx.setNumRecords(numRecords);
        
        tx.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        
        /*
         * for retrieve record keeping, do not update confirmedStamp 
         */
        if (!retrievalReceit)
            lastConfirmedStamp = stamp;
        
        m_logger.info("Confirm stamp set  " + deviceId + "/" + namespace + ":" + stamp + " token:" + txToken + " self:" + selfConfirm);
        SynkNodeTrackerTxDS.getInstance().put(tx);

    }

    

    @Override
    public long insert(AutositeDataObject obj, String user) {
        
        SynkNamespaceRecord existing = findRecordById(obj.getId());
        
        /*
         * Check whether any record not confirmed for this namespace/device
         * then, should not update stamp, if so, some records may never be synked.
         *  
         */
        List anyNonSynked = getUpdatesFromAllRecords(lastConfirmedStamp, true);
        boolean doNotSelfConfirmForTheRecord = false;
        if (anyNonSynked != null && anyNonSynked.size() > 0)
            doNotSelfConfirmForTheRecord = true;
            
        long stamped = 0;
        if ( existing != null) {
            
            existing.setStamp(getStamp(namespace)); //TODO
            existing.setTimeUpdated(new Timestamp(System.currentTimeMillis()));

            existing.setUpdatedByDeviceId(deviceId);
            existing.setUpdatedByIp(remoteIpAddress);
            existing.setUpdatedByUser(user);
            
            SynkNamespaceRecordDS.getInstance().update(existing);
            
            stamped =  existing.getStamp();
        } else {
        
            SynkNamespaceRecord record = new SynkNamespaceRecord();
            
            record.setNamespace(namespace);
            record.setRecordId(obj.getId()); 
            record.setOrgStamp(getStamp(namespace)); //TODO        
            record.setStamp(record.getOrgStamp());
            record.setTimeCreated(new Timestamp(System.currentTimeMillis()));
            record.setTimeUpdated(record.getTimeCreated());
            
            record.setCreatedByDeviceId(deviceId);
            record.setCreatedByIp(remoteIpAddress);
            record.setCreatedByUser(user);
            
            SynkNamespaceRecordDS.getInstance().put(record);
            stamped =  record.getStamp();
        }
        
        try {
            /*
             * remote device would not conform to the latest records,  in case, it has a chance that 
             * it hasn't synched up everything before. It will spcifically synchedup with jsonsynch 
             */
            if (!doNotSelfConfirmForTheRecord)
                confirmToSelf(stamped, "originated", 1, null);
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(),e);
        }
        
        return stamped;
    }

    @Override
    public long delete(long recordId, String user) {
        
        SynkNamespaceRecord record = findRecordById(recordId);
        
        if ( record == null) return -1;

        
        List anyNonSynked = getUpdatesFromAllRecords(lastConfirmedStamp, true);
        boolean doNotSelfConfirmForTheRecord = false;
        if (anyNonSynked != null && anyNonSynked.size() > 0)
            doNotSelfConfirmForTheRecord = true;
        
        record.setDeleted(1);
        record.setStamp(getStamp(namespace));
        record.setTimeDeleted(new Timestamp(System.currentTimeMillis()));
        record.setTimeUpdated(new Timestamp(System.currentTimeMillis()));

        record.setUpdatedByDeviceId(deviceId);
        record.setUpdatedByIp(remoteIpAddress);
        record.setUpdatedByUser(user);
        
        SynkNamespaceRecordDS.getInstance().update(record);
        
        try {
            if (!doNotSelfConfirmForTheRecord)
                confirmToSelf(record.getStamp(), "originated-deleted", 1, null);
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(),e);
        }
                
        
        return record.getStamp();
    }
    
    
    public SynkNamespaceRecord findRecordById(long id) {
        
//        Map<String, SynkNamespaceRecord> map = SynkNamespaceRecordDS.getInstance().getMapBySiteIdNamespaceList(0, namespace);
//        SynkNamespaceRecord existing = map.get(new Long(id));

        List<SynkNamespaceRecord> list = SynkNamespaceRecordDS.getInstance().getBySiteIdNamespaceList(0,  namespace);
        for (SynkNamespaceRecord synkNamespaceRecord : list) {
            if (synkNamespaceRecord.getRecordId() == id)
                return synkNamespaceRecord;
        }
        
        return null;
    }
    
    private long getStamp(String namespace){
        
        if ( !indexTracker.containsKey(namespace)) {
            AtomicLong newCounter = new AtomicLong(getNewBase());
            indexTracker.put(namespace, newCounter);
        }
        
        return indexTracker.get(namespace).incrementAndGet();
    }
    
    private List<SynkNamespaceRecord> getUpdatesFromAllRecords(long stamp, boolean noConfirmRecords){
        /*
         * This is brutal way of finding updates. Should be only done once during the init time
         */
        
        List<SynkNamespaceRecord> allRecords = SynkNamespaceRecordDS.getInstance().getBySiteIdNamespaceList(0, namespace);
        List<SynkNamespaceRecord> ret = new ArrayList<SynkNamespaceRecord>();
        
        long lastesetStamp = stamp;
        for (SynkNamespaceRecord synkNamespaceRecord : allRecords) {
            if ( synkNamespaceRecord.getStamp() > stamp) {
                ret.add(synkNamespaceRecord);
                
                if (synkNamespaceRecord.getStamp() >lastesetStamp)
                    lastesetStamp = synkNamespaceRecord.getStamp();
            }
        }
        
        try {
            if ( ret.size() > 0 && !noConfirmRecords) {
                // putting retrieve receipts
                confirmRecords(lastesetStamp, "retrieved."+ stamp+ "->" + lastesetStamp + "#" + ret.size()+ "@" + LocalDateTime.now().toString("yyyyMMddHHmmss"), ret.size(), null, false, true);
            }
        }
        catch (Exception e) {
            m_logger.error("Failed while confirmRecords." + e.getMessage(),e);
        }        
        
        return ret;
    }
    
    private long getNewBase(){
        return Long.parseLong(LocalDateTime.now().toString("yyyyMMddHHmm000000"));

    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIp() {
        return remoteIpAddress;
    }

    public void setIp(String ip) {
        this.remoteIpAddress = ip;
    }

    public Map<String, AtomicLong> getIndexTracker() {
        return indexTracker;
    }

    public void setIndexTracker(Map<String, AtomicLong> indexTracker) {
        this.indexTracker = indexTracker;
    }

    
    
    
    
}
