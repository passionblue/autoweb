package com.autosite.ds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.log4j.Logger;
import org.springframework.data.repository.CrudRepository;

import com.autosite.db.AutositeDataObject;
import com.autosite.db.CleanerPickupDelivery;
import com.autosite.db.GenMain;
import com.autosite.ds.event.DSEvent;
import com.autosite.ds.event.DSEventListener;
import com.autosite.holder.DataHolderObject;
import com.surveygen.db.BaseHibernateDAO;

abstract public class AbstractDS {

    
    private static Map m_dsMap = new ConcurrentHashMap<String, AbstractDS>();
    
    protected BaseHibernateDAO m_dao;
    protected Map m_idToMap;
    protected Map<String, ListenerContainer> m_listeners = new ConcurrentHashMap<String, ListenerContainer>();
    
    
    abstract public void updateMaps(Object obj, boolean del);
    public List getBySiteId(long id) {
        throw new RuntimeException("Not Supported");
    }
    
    protected AbstractDS() {
        m_dsMap.put(this.getClass().getName(), this);
        m_logger.info("Registered DS [" + this.getClass().getName() + "]");
    }
    
    
    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    
    
    //added while creating AutositeActionBase
    public Object getById(Long id) {
        return  m_idToMap.get(new Long(id));
    }    
    
    public Object getById(Long id, boolean forSynchWithDB) {
        if (persistEnable() && forSynchWithDB ) {
            try {
                Object loaded = (Object) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return (Object) m_idToMap.get(id);
    }    
    
    public Object getById(String id) {
        
        if (id == null) return null;
        
        long idVal = 0;
        
        try {
            idVal = Long.valueOf(id);
        }
        catch (NumberFormatException e) {
            m_logger.error(e);
        }
        
        return  m_idToMap.get(new Long(idVal));
    }
    
    public void loadFromDB() throws Exception {
        if (persistEnable()) {
            m_logger.info("Loading data " + this.getClass().getName());
            List list = m_dao.findAll();
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        }
    }

    public Object synchFromDB(Long id) throws Exception {
        if (persistEnable()) {
            m_logger.info("Loading data " + this.getClass().getName());
            Object loaded = m_dao.findById(id, getEntityClass());
            
            if ( loaded != null) { 
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(loaded, false);
                else 
                    updateMaps(loaded, false);
                return loaded;
            }
        }
        return null;
    }
    
    
    public List getAll() {

        return new ArrayList(m_idToMap.values());
    }

    public boolean delete(Object obj) {
        if (persistEnable()) {
            Object target = obj;
            if (usingHolderObject()) {
                target = ((DataHolderObject)obj).getDataObject();
            }
            m_logger.debug("DELETETING: " + obj);
            m_dao.remove(target);
        } else {
            if (usingHolderObject()) {
                m_dao.remove(obj);
            }
        }
        
        updateMaps(obj, true);
        return true;
    }
    
    public boolean put(Object obj) {
        if (persistEnable()) {
            Object target = obj;
            if (usingHolderObject()) {
                target = ((DataHolderObject)obj).getDataObject();
            }
            m_logger.debug("PUT: " + obj);
            m_dao.add(target);

        } else {
            if (usingHolderObject()) {
                m_dao.add(obj);
            }
        }
        updateMaps(obj, false);
        return true;
    }

    public boolean update(Object obj) {
        if (persistEnable()) {
            Object target = obj;
            if (usingHolderObject()) {
                target = ((DataHolderObject)obj).getDataObject();
            }
            m_logger.debug("UPDATING: " + obj);
            m_dao.update(target);
        } else {
            if (usingHolderObject()) {
                //??
                m_dao.update(obj);
            }
        }
        updateMaps(obj, false);
        return true;
    }
    
    public boolean persistEnable(){
        return true;
    }
    
    public boolean usingHolderObject() {
        return false;
    }
    
    public void reset() throws Exception {
    }

    protected boolean isNullKey(String key){
        if (key == null) return true;
        if (key.isEmpty()) return true;
        return false;
    }
    
    public String objectToString2(AutositeDataObject object){
        return object.toString();
    }
    
    // EVENT
    
    public void addUpdateListener(String name, DSEventListener listener){
        
        if ( listener == null || name == null ) return;
        
        if (!m_listeners.containsKey(name)){
            m_listeners.put(name, new ListenerContainer(name, listener));
            m_logger.info("DS Listner registered " + name + " at [" + this.getClass().getName() + "]");
            for (Iterator iterator = m_idToMap.values().iterator(); iterator.hasNext();) {
                AutositeDataObject obj = (AutositeDataObject) iterator.next();
                listener.updated(new DSEvent(name, this, obj, false));
            }
        }
    }
    
    public void fireEvent(AutositeDataObject obj, boolean deleted){
        
        for (Iterator iterator = m_listeners.values().iterator(); iterator.hasNext();) {
            ListenerContainer listenerContainer = (ListenerContainer) iterator.next();
            try {
                listenerContainer.m_listener.updated(new DSEvent( listenerContainer.m_name, this, obj, deleted));
            }
            catch (Exception e) {
                m_logger.error(e);
            }
        }
    }
    
    
    class ListenerContainer {
        String m_name;
        DSEventListener m_listener;

        ListenerContainer(String name, DSEventListener listener){
            m_name = name;
            m_listener = listener;
        }
    }
    

    // Used for shortcut fields 
    public String getNullStringValue(){
        return null;
    }
    
    public int getNullIntegerValue(){
        return 0;
    }

    public int getNullDoubleValue(){
        return 0;
    }
    
    protected String getEntityClass(){
        return "";
    }
    
    private static Logger m_logger = Logger.getLogger(AbstractDS.class);
}
