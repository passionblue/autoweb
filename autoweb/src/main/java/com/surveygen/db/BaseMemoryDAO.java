package com.surveygen.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.autosite.db.BlockListDAO;
import com.autosite.holder.DataHolderObject;
import com.seox.db.IBaseHibernateDAO;

public class BaseMemoryDAO extends BaseHibernateDAO  {
    private static final Log m_logger = LogFactory.getLog(BlockListDAO.class);

    private Map m_memoryMapById = new ConcurrentHashMap(1000);
    
    @Override
    public Session getSession() {
        return null;
    }
    
    public void save(Object transientInstance) {
        m_logger.debug("saving instance");
        if (transientInstance instanceof DataHolderObject){
            DataHolderObject obj = (DataHolderObject) transientInstance;
            if (obj.getId() <= 0) {
                m_logger.debug("Adding instance");
                obj.setId(System.currentTimeMillis());
                m_memoryMapById.put(new Long(obj.getId()), obj);
            } else {
                Long key = new Long(obj.getId());
                if (!m_memoryMapById.containsKey(key)){
                    m_memoryMapById.put(new Long(obj.getId()), obj);
                }
            }
        }
    }

    public void delete(Object persistentInstance) {
        if (persistentInstance instanceof DataHolderObject){
            DataHolderObject obj = (DataHolderObject) persistentInstance;
            if (obj.getId() <= 0) {
                m_logger.debug("Deleting instance");
                m_memoryMapById.remove(new Long(obj.getId()));
            }
        }
    }

    public Object findById(Long id) {
        m_logger.debug("getting instance with id: " + id);
        return m_memoryMapById.get(id);
    }
    
    // Overriding BaseHibernateDAO

    public List findAll() {
        return new ArrayList(m_memoryMapById.values());
    }
    
    public void add(Object transientInstance) {
        save(transientInstance);
    }

    public void remove(Object persistentInstance) {
        save(persistentInstance);
    }
    public void update(Object instance) {
        save(instance);
    }
}
