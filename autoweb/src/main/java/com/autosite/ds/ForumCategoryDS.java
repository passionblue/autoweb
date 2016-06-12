package com.autosite.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.autosite.db.ForumCategory;
import com.autosite.db.ForumCategoryDAO;
import com.jtrend.service.DomainStore;

public class ForumCategoryDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;

    protected Map m_CategoryToMap;



    private static Logger m_logger = Logger.getLogger(ForumCategoryDS.class);
    private static ForumCategoryDS m_ForumCategoryDS = null;

    public static synchronized ForumCategoryDS getInstance() {
        if (m_ForumCategoryDS == null) {
            m_ForumCategoryDS = new ForumCategoryDS();
        }
        return m_ForumCategoryDS;
    }

    public static synchronized ForumCategoryDS getInstance(long id) {
        ForumCategoryDS ret = (ForumCategoryDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ForumCategoryDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ForumCategoryDS;
    }



    private static Map m_dsMap = new HashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {
            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ForumCategoryDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
            
        } else {
            super.loadFromDB();
        }
    }


    protected ForumCategoryDS() {
        m_dao = new ForumCategoryDAO();
        m_idToMap = new HashMap();

        m_SiteIdToMap = new HashMap();
        m_CategoryToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ForumCategoryDS(long id) {
        m_loadById = id;
        m_dao = new ForumCategoryDAO();
        m_idToMap = new HashMap();

        m_SiteIdToMap = new HashMap();
        m_CategoryToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }



    public ForumCategory getById(Long id) {
        return (ForumCategory) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ForumCategory o = (ForumCategory)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            m_logger.debug("ForumCategory removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            m_logger.debug("ForumCategory added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateCategoryMap(obj, del);
    }


    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        ForumCategory o = (ForumCategory)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("ForumCategory removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
        }
        else {
            
            // add to SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map == null ) {
                map = new TreeMap();
                m_SiteIdToMap.put(new Long(o.getSiteId()), map);
                m_logger.debug("created new   SiteIdToMap for " + o.getSiteId());

            }
            map.put(new Long(o.getId()), o);            
            m_logger.debug("ForumCategory added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

    public ForumCategory getByCategory(String keyCategory) {
        return (ForumCategory)m_CategoryToMap.get(keyCategory);
    }

    private void updateCategoryMap(Object obj, boolean del) {
        ForumCategory o = (ForumCategory)obj;
        String _Category =  o.getCategory();

        if (del) {
            // delete from CategoryToMap

            if (m_CategoryToMap.containsKey(_Category)){
                m_CategoryToMap.remove(_Category);
                m_logger.debug("ForumCategory removed from CategoryToMap" + o.getId() + " for [" + _Category+ "]");
            } else {
                m_logger.debug("ForumCategory removed from CategoryToMap" + o.getId() + " for [" + _Category+ "]");
            } 
        }
        else {
            
            if (m_CategoryToMap.containsKey(_Category)){
                m_logger.debug("ForumCategory repalced CategoryToMap" + o.getId() + " for [" + _Category+ "]");
            } else {
                m_logger.debug("ForumCategory added to CategoryToMap" + o.getId() + " for [" + _Category+ "]");
            } 
            m_CategoryToMap.put(_Category, o);
        }
    }

    public static void main(String[] args) throws Exception {

        ForumCategoryDS ds = new ForumCategoryDS(1);
        ForumCategory obj = ds.getById((long)1);
        System.out.println(obj);

    }
}
