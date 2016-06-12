package com.autosite.session.devicesynch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.AutositeRemoteDevice;
import com.autosite.db.AutositeSynchLedger;
import com.autosite.ds.AutositeSynchLedgerDS;

// This class tracks synch-up status of each ojbect for a single device. Trackers are registered to 
// DS of SynchLedger DS by AutositeLedgerSynchTrackingManager. 
//

public class DeviceSynchTracker {

    private static Logger m_logger = LoggerFactory.getLogger(DeviceSynchTracker.class);
    
    AutositeRemoteDevice m_device;
    Map <String, TreeMap<Long, SynchTrackingEntry>> m_pool = new ConcurrentHashMap();
    
    public DeviceSynchTracker(AutositeRemoteDevice device){
        m_device = device;
    }
    
    public void process(AutositeSynchLedger synchLedger){

        //If it is processing ledger caused by another device. Should try to minimize the update
        // In case there is new object and updates follows, other devices dont have to care the following update if original object is not synched up yet. 
        
        SynchTrackingEntry virtuallyDuplicate = null;
        if ( synchLedger.getDeviceId() != m_device.getId() && SynchTrackingUtil.isUpdateAction(synchLedger.getScope())) {
            String forCreateScope = synchLedger.getScope().replaceAll("-update", "-create");

            TreeMap<Long, SynchTrackingEntry> mapsForCreateAction = m_pool.get(forCreateScope);
        
            if ( mapsForCreateAction != null) {
                SynchTrackingEntry entry = mapsForCreateAction.get(new Long(synchLedger.getObjectId()));
                if ( entry != null && entry.getSynchId() == null) {
                    m_logger.debug("Original create SynchLeder was not synched yet for device " + m_device.getId() + " will be ignored");
                    return;
                }
            }
        }        

        // Assume that an object was created and delted before getting synched with me. This device wouldn't want to bother. 
        // In that case, create should not be sent not to confuse the device. 20130513
        
        else if ( synchLedger.getDeviceId() != m_device.getId() && SynchTrackingUtil.isDeleteAction(synchLedger.getScope())) {
            
            String forCreateScope = synchLedger.getScope().replaceAll("-delete", "-create");
            TreeMap<Long, SynchTrackingEntry> mapsForCreateAction = m_pool.get(forCreateScope);
            
            if ( mapsForCreateAction != null) {
                SynchTrackingEntry entry = mapsForCreateAction.get(new Long(synchLedger.getObjectId()));
                if ( entry != null && (entry.getSynchId() == null || entry.getRemoteTok() == null) ) {
                    m_logger.debug("Original create SynchLeder was not synched yet for device " + m_device.getId() + " will be removed and delete synch will be sent in the end");
                    mapsForCreateAction.remove(new Long(synchLedger.getObjectId()));
                }
            }
        }        
        
        else if ( synchLedger.getDeviceId() != m_device.getId() && SynchTrackingUtil.isCreateAction(synchLedger.getScope())) {
            String forDeletedScope = synchLedger.getScope().replaceAll("-create", "-delete");
            TreeMap<Long, SynchTrackingEntry> mapsForDeleteAction = m_pool.get(forDeletedScope);
            
            if ( mapsForDeleteAction != null) {
                SynchTrackingEntry entry = mapsForDeleteAction.get(new Long(synchLedger.getObjectId()));
                if ( entry != null ) {
                    m_logger.debug("Original object seems deleted. create will not entered for device " + m_device.getId());
                }
            }
        }        
        
        TreeMap<Long, SynchTrackingEntry> mapObjectIdToEntry = m_pool.get(synchLedger.getScope());
        if (mapObjectIdToEntry == null){
            mapObjectIdToEntry = new TreeMap();
            m_pool.put(synchLedger.getScope(), mapObjectIdToEntry);
        }
        
        // Search existing entry. 
        SynchTrackingEntry entry = mapObjectIdToEntry.get(new Long(synchLedger.getObjectId()));
        
        if ( entry == null) {
        
            entry = new SynchTrackingEntry();
            
            entry.setTarget(synchLedger.getTarget());
            entry.setObjectId(synchLedger.getObjectId());
            entry.setSynchObjectId(synchLedger.getId());
            entry.setSynchScope(synchLedger.getScope());

            if ( synchLedger.getDeviceId() == m_device.getId()) {
                entry.setSynchId(synchLedger.getSynchId());
                entry.setTimestamp(synchLedger.getTimeCreated().getTime());
                entry.setRemoteTok(synchLedger.getRemoteToken());
            }

            mapObjectIdToEntry.put( new Long(entry.getObjectId()), entry);

        } else {
            
            if (  synchLedger.getDeviceId() == m_device.getId() ) {
                if ( entry.getRemoteTok() == null )
                    entry.setRemoteTok(synchLedger.getRemoteToken());
                if ( entry.getSynchId() == null)
                    entry.setSynchId(synchLedger.getSynchId());
            }
            
            // Originally created by another source but when synchLedger comes in when this device synched up. 
            // the synchLedger should be updated. Otherwise, synchLedger keep creating if the remote device repeat the synch request. 
            if ( entry.getSynchObjectId() != synchLedger.getId() && synchLedger.getDeviceId() == m_device.getId()) {
                entry.setSynchObjectId(synchLedger.getId());
            }
        }
        
        //m_logger.debug("SynchLedger processed " + synchLedger.getId() + " source device " + synchLedger.getDeviceId() + " for target device " + m_device.getId());
    }

    public AutositeRemoteDevice getDevice() {
        return m_device;
    }

    public void setDevice(AutositeRemoteDevice device) {
        m_device = device;
    }

    public List<SynchTrackingEntry> findNotSynchedForAllScopes(String scopePrefix){
        
        List allByScopes = new ArrayList();
        
        allByScopes.addAll(findNotSynchedBySynchScope(scopePrefix+"-create"));
        allByScopes.addAll(findNotSynchedBySynchScope(scopePrefix+"-update"));
        allByScopes.addAll(findNotSynchedBySynchScope(scopePrefix+"-delete"));
        
        return allByScopes;
    }
    

    public List<SynchTrackingEntry> findNotSynchedBySynchScope(String scope){
        return findNotSynchedBySynchScope(scope, null);
    }
    
    public List<SynchTrackingEntry> findNotSynchedBySynchScope(String scope, String action){
        
        if (! m_pool.containsKey(scope))
            return new ArrayList();
        
        List byScopes = new ArrayList(m_pool.get(scope).values());
        List<SynchTrackingEntry> returnList = new ArrayList();
        
        // Only returns that dont have synchId or remote Tok. even there is synchId, if there is not remote tok, means it wasn't confirmed by the remote yet.
        // To be confirmed through PagelessSessionSynch action. 
        for (Iterator iterator = byScopes.iterator(); iterator.hasNext();) {
            SynchTrackingEntry entry = (SynchTrackingEntry) iterator.next();
            
            if (entry.getSynchId() == null || entry.getRemoteTok() == null) {
                returnList.add(entry);
            }
        }
        
        return returnList;
    }
    
    public SynchTrackingEntry findSynchEntryForTheObject(String scope, long id){
        
        if (! m_pool.containsKey(scope))
            return null;
        
        TreeMap<Long, SynchTrackingEntry> entriesForScope = m_pool.get(scope);
        
        return entriesForScope.get(new Long(id));
    }    

    // just find and return
    public AutositeSynchLedger findSynchLedger(String scope, long objectId){
        
        TreeMap<Long, SynchTrackingEntry> mapObjectIdToEntry = m_pool.get(scope);
        if (mapObjectIdToEntry == null){
            return null;
        }
        
        Long longId = new Long(objectId);
        
        if ( mapObjectIdToEntry.containsKey(longId) ) {

            SynchTrackingEntry entry = mapObjectIdToEntry.get(longId);
            AutositeSynchLedger synchLedger = AutositeSynchLedgerDS.getInstance().getById(entry.getSynchObjectId());

            return synchLedger;
        }
        
        return null;
    }    
    
    // Return synchLedger for the object ID only if this record has been synched with the device that this tracker is bound to.
    // If not yet, returns null;
    // If the record has something to synch for an update, it should not return any synchLedger. 
    public AutositeSynchLedger findSynchLedgerForTheRecord(String scopePrefix, long objectId){
        
        String scopesToSearch[] = new String[]{scopePrefix+"-create" ,scopePrefix+"-update" ,scopePrefix+"-delete"};
        Long longId = new Long(objectId);
        AutositeSynchLedger currentSynchLedger = null;
        
        for (int i = 0; i < scopesToSearch.length; i++) {
            String scope = scopesToSearch[i];

            SynchTrackingEntry entry = this.findSynchEntryForTheObject(scope, objectId);
            
            if (entry == null) {
                //TODO what todo?
                continue;
            }
            
            AutositeSynchLedger synchLedger = AutositeSynchLedgerDS.getInstance().getById(entry.getSynchObjectId());
            
            if ( synchLedger!= null && m_device.getId() == synchLedger.getDeviceId()) {
                 
                // There is a situation that 
                if ( currentSynchLedger == null)  {
                    if ( entry.getRemoteTok() == null)  {
                        currentSynchLedger = null;
                    } else {
                        currentSynchLedger = synchLedger;
                    }
                } else {
                    if ( synchLedger != null && synchLedger.getTimeCreated().getTime() > currentSynchLedger.getTimeCreated().getTime()) {
                        // The later ledger was not synched-up yet.
                        if ( entry.getRemoteTok() == null)  {
                            currentSynchLedger = null;
                        } else {
                            currentSynchLedger = synchLedger;
                        }
                    }
                }
            }
            
        }
        return currentSynchLedger;
    }
    
    public AutositeSynchLedger findSynchLedgerForTheRecordOriginal(String scopePrefix, long objectId){
        
        String scopesToSearch[] = new String[]{scopePrefix+"-create" ,scopePrefix+"-update" ,scopePrefix+"-delete"};
        Long longId = new Long(objectId);
        AutositeSynchLedger currentSynchLedger = null;
        
        for (int i = 0; i < scopesToSearch.length; i++) {
            String scope = scopesToSearch[i];

            if (! m_pool.containsKey(scope))
                continue;
            
            TreeMap<Long, SynchTrackingEntry> mapObjectIdToEntry = m_pool.get(scope);
            if (mapObjectIdToEntry == null){
                continue;
            }
            
            if ( mapObjectIdToEntry.containsKey(longId) ) {

                SynchTrackingEntry entry = mapObjectIdToEntry.get(longId);
                AutositeSynchLedger synchLedger = AutositeSynchLedgerDS.getInstance().getById(entry.getSynchObjectId());
                
                if ( synchLedger!= null && 
                     m_device.getId() == synchLedger.getDeviceId()) {
                     
                    // There is a situation that 
                    if ( currentSynchLedger == null)  {
                        if ( entry.getRemoteTok() == null)  {
                            currentSynchLedger = null;
                        } else {
                            currentSynchLedger = synchLedger;
                        }
                    } else {
                        if ( synchLedger != null && synchLedger.getTimeCreated().getTime() > currentSynchLedger.getTimeCreated().getTime()) {
                            // The later ledger was not synched-up yet.
                            if ( entry.getRemoteTok() == null)  {
                                currentSynchLedger = null;
                            } else {
                                currentSynchLedger = synchLedger;
                            }
                        }
                    }
                }
            }
        }
        return currentSynchLedger;
    }
}
