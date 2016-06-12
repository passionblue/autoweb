package com.autosite.holder;

import com.autosite.db.BaseAutositeDataObject;

abstract public class AbstractDataHolder implements DataHolderObject {
    protected boolean m_skipPersist = false;
    
    public boolean skipPersist() {
        return m_skipPersist;
    }
    
    public void setSkipPersist(boolean val){
        m_skipPersist = val;
    }

    @Override
    public long getId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object getValue(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getKey() {
        if ( getId() == 0 )
            return null;
        
        return String.valueOf( getId());
    }
    
    public boolean getSkipPersist() {
        return m_skipPersist;
    }
}
