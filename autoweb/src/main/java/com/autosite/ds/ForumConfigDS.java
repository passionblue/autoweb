package com.autosite.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.autosite.db.ForumConfig;
import com.autosite.db.ForumConfigDAO;
import com.jtrend.service.DomainStore;

public class ForumConfigDS extends AbstractDS implements DomainStore {





    private static Logger m_logger = Logger.getLogger(ForumConfigDS.class);
    private static ForumConfigDS m_ForumConfigDS = null;

    public static synchronized ForumConfigDS getInstance() {
        if (m_ForumConfigDS == null) {
            m_ForumConfigDS = new ForumConfigDS();
        }
        return m_ForumConfigDS;
    }

    protected ForumConfigDS() {
        m_dao = new ForumConfigDAO();
        m_idToMap = new HashMap();

        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e);
        }
    }


    public ForumConfig getById(Long id) {
        return (ForumConfig) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ForumConfig o = (ForumConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            m_logger.debug("ForumConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            m_logger.debug("ForumConfig added to DS " + o.getId());
        }

    }



    

    public static void main(String[] args) throws Exception {

        ForumConfigDS ds = new ForumConfigDS();
        ForumConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }
}
