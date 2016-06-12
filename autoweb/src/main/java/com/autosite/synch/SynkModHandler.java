package com.autosite.synch;

import com.autosite.db.AutositeDataObject;

/*
 * Handler to enter the objects
 */
public interface SynkModHandler {

    /**
     * returns stamp for the lastest update
     * @return
     */
    long insert(AutositeDataObject obj, String user);
    
    long delete(long recordId, String user);
    
}
