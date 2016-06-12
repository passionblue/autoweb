package com.autosite.db;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.mongodb.core.mapping.Document;


abstract public class BaseAutositeDataObject  implements AutositeDataObject{
    protected boolean m_skipPersist = false;
    
    public boolean skipPersist() {
        return m_skipPersist;
    }
    
    public void setSkipPersist(boolean val){
        m_skipPersist = val;
    }
    
    public long getSiteId(){
        return 0;
    }
    public void setSiteId(long id){
    }

    public boolean getSkipPersist() {
        return m_skipPersist;
    }
    
    public String getKey() {
        if ( getId() == 0 )
            return null;
        
        return String.valueOf( getId());
    }
}
