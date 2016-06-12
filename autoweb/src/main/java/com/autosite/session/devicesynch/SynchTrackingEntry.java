package com.autosite.session.devicesynch;

public class SynchTrackingEntry {

    long    synchObjectId; //AutositeSynchLedger.getId()
    String  target;
    long    objectId;
    String  synchId;
    String  remoteTok;
    long    timestamp;
    long    timestampCreation;
    String  synchScope;
    
    public SynchTrackingEntry() {
        timestampCreation = System.nanoTime();
    }

    public SynchTrackingEntry(String target, long objectId, String synchId) {
        this.target = target;
        this.objectId = objectId;
        this.synchId = synchId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getSynchId() {
        return synchId;
    }

    public void setSynchId(String synchId) {
        this.synchId = synchId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getRemoteTok() {
        return remoteTok;
    }

    public void setRemoteTok(String remoteTok) {
        this.remoteTok = remoteTok;
        
        if ( this.remoteTok != null && remoteTok == null) {
            System.out.println("XXXX " + timestampCreation + " " + objectId + " " + this.remoteTok);
        }
        
    }

    public long getSynchObjectId() {
        return synchObjectId;
    }

    public void setSynchObjectId(long synchObjectId) {
        this.synchObjectId = synchObjectId;
    }

    public long getTimestampCreation() {
        return timestampCreation;
    }

    public void setTimestampCreation(long timestampCreation) {
        this.timestampCreation = timestampCreation;
    }

    public String getSynchScope() {
        return synchScope;
    }

    public void setSynchScope(String synchScope) {
        this.synchScope = synchScope;
    }

    
    
}
