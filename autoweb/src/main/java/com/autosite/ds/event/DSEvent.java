package com.autosite.ds.event;

import com.autosite.db.AutositeDataObject;
import com.autosite.ds.AbstractDS;

public class DSEvent {

    protected String m_sourceName;
    protected AbstractDS m_sourceDS;
    protected AutositeDataObject m_object;
    protected boolean m_deleted;
    
    
    public DSEvent(String sourceName, AbstractDS sourceDS, AutositeDataObject object, boolean deleted) {
        super();
        m_sourceName = sourceName;
        m_sourceDS = sourceDS;
        m_object = object;
        m_deleted = deleted;
    }

    public AutositeDataObject getObject() {
        return m_object;
    }
    
    public boolean isDeleted() {
        return m_deleted;
    }
    
    
    public String getSourceName() {
        return m_sourceName;
    }

    public AbstractDS getSourceDS() {
        return m_sourceDS;
    }

    public String toString(){
        return m_sourceDS.getClass().getSimpleName() + ":" + m_sourceName + " " + m_object;
    }
}
