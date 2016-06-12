package com.autosite.session.storable;

import java.util.Date;

import com.autosite.holder.DataHolderObject;
import com.jtrend.session.AbstractSessionStorable;
import com.jtrend.session.SessionStorable;

public class SessionStorableDataHolderWrapper extends AbstractSessionStorable{

    protected DataHolderObject m_dataHolder;
    
    public SessionStorableDataHolderWrapper(DataHolderObject dataHolder){
        super();
        m_dataHolder = dataHolder;
    }
    
    public void setDataHolder(DataHolderObject dataHolder){
        m_dataHolder = dataHolder;
    }

    public DataHolderObject getDataHolder(){
        return m_dataHolder;
    }
    
    public String toString(){
        return "SessionStorableDataHolderWrapper:" + m_dataHolder.getClass().getName() + " ID:" + m_dataHolder.getId(); 
    }

}
