package com.autosite.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.db.EcCategory;
import com.autosite.db.EcCategoryDAO;
import com.jtrend.service.DomainStore;

public class EcCategoryDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PageIdToOneMap;


    private static Logger m_logger = Logger.getLogger(EcCategoryDS.class);
    private static EcCategoryDS m_EcCategoryDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized EcCategoryDS getInstance() {
        if (m_EcCategoryDS == null) {
            m_EcCategoryDS = new EcCategoryDS();
        }
        return m_EcCategoryDS;
    }

    public static synchronized EcCategoryDS getInstance(long id) {
        EcCategoryDS ret = (EcCategoryDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcCategoryDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcCategoryDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcCategoryDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcCategoryDS() {
        m_dao = new EcCategoryDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcCategoryDS(long id) {
        m_loadById = id;
        m_dao = new EcCategoryDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcCategory getById(Long id) {
        return (EcCategory) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcCategory o = (EcCategory)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcCategory removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcCategory added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updatePageIdOneMap(obj, del);


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
        EcCategory o = (EcCategory)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcCategory removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
        }
        else {
            
            // add to SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map == null ) {
                map = new TreeMap();
                m_SiteIdToMap.put(new Long(o.getSiteId()), map);
                if (m_debug) m_logger.debug("created new   SiteIdToMap for " + o.getSiteId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("EcCategory added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    


    public EcCategory getObjectByPageId(long keyPageId) {
        return (EcCategory)m_PageIdToOneMap.get(new Long(keyPageId));
    }

    private void updatePageIdOneMap(Object obj, boolean del) {
        EcCategory o = (EcCategory)obj;
        Long _PageId = new Long(o.getPageId());

        if (del) {
            // delete from PageIdToOneMap

            if (m_PageIdToOneMap.containsKey(_PageId)){
                m_PageIdToOneMap.remove(_PageId);
                if (m_debug) m_logger.debug("EcCategory removed from PageIdToMap" + o.getId() + " for [" + _PageId+ "]");
            } else {
                if (m_debug) m_logger.debug("EcCategory not removed from PageIdToMap" + o.getId() + " for [" + _PageId+ "]. Does not exist");
            } 
        }
        else {
            if (m_PageIdToOneMap.containsKey(_PageId)){
                if (m_debug) m_logger.debug("EcCategory repalced PageIdToMap" + o.getId() + " for [" + _PageId+ "]");
            } else {
                if (m_debug) m_logger.debug("EcCategory added to PageIdToMap" + o.getId() + " for [" + _PageId+ "]");
            } 
            m_PageIdToOneMap.put(_PageId, o);
        }
    }




    public static void main(String[] args) throws Exception {

        EcCategoryDS ds = new EcCategoryDS();
        EcCategory obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcCategory createDefault(){

        EcCategory ret = new EcCategory();        
//      ret.setParentId("");           
//      ret.setPageId("");           
//      ret.setCategoryName("");           
//      ret.setImageUrl("");           
//      ret.setCategoryDescription("");           
//      ret.setAlt1("");           
//      ret.setAlt2("");           
        return ret;
    }

    public static EcCategory copy(EcCategory org){

    	EcCategory ret = new EcCategory();

		ret.setParentId(org.getParentId());
		ret.setPageId(org.getPageId());
		ret.setCategoryName(org.getCategoryName());
		ret.setImageUrl(org.getImageUrl());
		ret.setCategoryDescription(org.getCategoryDescription());
		ret.setAlt1(org.getAlt1());
		ret.setAlt2(org.getAlt2());

        return ret;
    }

	public static void objectToLog(EcCategory ecCategory, Logger logger){
		logger.debug("EcCategory [" + ecCategory.getId() + "]" + objectToString(ecCategory));		
    }

	public static String objectToString(EcCategory ecCategory){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(ecCategory.getId()).append(", ");
		buf.append("SiteId=").append(ecCategory.getSiteId()).append(", ");
		buf.append("CategoryName=").append(ecCategory.getCategoryName()).append(", ");
		return buf.toString();    
    }
}
