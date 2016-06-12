package com.autosite.session.storable;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



public class DefaultSessionObjectStore extends AutositeSessionObjectStore {
    
    private static Logger m_logger = Logger.getLogger(DefaultSessionObjectStore.class);
    protected List m_storablesList = new ArrayList();
    
    public DefaultSessionObjectStore(String groupName) {
        m_groupName = groupName;
        m_timestamp = System.currentTimeMillis();
        m_logger.info("SessionObjectStore Created " + groupName);
    }

}
