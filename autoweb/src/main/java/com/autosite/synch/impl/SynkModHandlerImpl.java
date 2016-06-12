package com.autosite.synch.impl;

import java.sql.Timestamp;
import java.util.Map;

import com.autosite.db.AutositeDataObject;
import com.autosite.db.SynkNamespaceRecord;
import com.autosite.ds.SynkNamespaceRecordDS;
import com.autosite.synch.SynkModHandler;

@Deprecated
public class SynkModHandlerImpl implements SynkModHandler{

    private String namespace;
    private String deviceId;

    public SynkModHandlerImpl(String namespace, String deviceId) {
        super();
        this.namespace = namespace;
        this.deviceId = deviceId;
    }


    @Override
    public long insert(AutositeDataObject obj, String user) {
        
        Map<String, SynkNamespaceRecord> map = SynkNamespaceRecordDS.getInstance().getMapBySiteIdNamespaceList(0, namespace);
        
        
        SynkNamespaceRecord existing = map.get(new Long(obj.getId()));
        
        if ( existing != null) {
            
            existing.setStamp(System.currentTimeMillis()); //TODO
            existing.setTimeUpdated(new Timestamp(System.currentTimeMillis()));
            
            SynkNamespaceRecordDS.getInstance().update(existing);
            
            return existing.getStamp();
        } else {
        
            SynkNamespaceRecord record = new SynkNamespaceRecord();
            
            record.setNamespace(namespace);
            record.setRecordId(obj.getId()); 
            record.setOrgStamp(System.currentTimeMillis()); //TODO        
            record.setStamp(record.getOrgStamp());
            record.setTimeCreated(new Timestamp(System.currentTimeMillis()));
            record.setTimeUpdated(record.getTimeCreated());
            
            SynkNamespaceRecordDS.getInstance().put(record);
            return record.getStamp();
        }
    }

    
    
    @Override
    public long delete(long recordId, String user) {
        // TODO Auto-generated method stub
        return 0;
    }


    //TODO
    private boolean isThereNoSynkedRecords() {
        return false;
    }
}
