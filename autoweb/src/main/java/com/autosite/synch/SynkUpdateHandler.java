package com.autosite.synch;

import java.util.List;

import com.autosite.db.SynkNamespaceRecord;

public interface SynkUpdateHandler {
    
    String getNamesapce();

    List<SynkNamespaceRecord> getAllRecords();
    
    List<SynkNamespaceRecord> getUpdates(long stamp);
    
    List<SynkNamespaceRecord> getUpdatesFromLastConfirm();
    
    void confirm(long stamp, String txToken, int numRecords, String remoteId) throws Exception;
    
}
