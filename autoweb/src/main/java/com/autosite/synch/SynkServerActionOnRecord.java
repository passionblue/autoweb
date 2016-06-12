package com.autosite.synch;

public enum SynkServerActionOnRecord {

    RecordChangeConfirmed(1), // server confirming the changes from remote
    RecordChangeException(2), // need ? synch was received but not completed due
                              // to an exceptin.
    RecordSynchRetrieval(3), 
    RecordNoSynch(4);

    private int value;

    private SynkServerActionOnRecord(int value) {
        this.value = value;
    }
    
    
    
}
