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
import com.autosite.db.ContentFeedConfig;
import com.jtrend.service.DomainStore;

import com.autosite.db.ContentFeedConfigDAO;

public class ContentFeedConfigDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_FeedCategoryToOneMap;






    private static Logger m_logger = Logger.getLogger(ContentFeedConfigDS.class);
    private static ContentFeedConfigDS m_ContentFeedConfigDS = new ContentFeedConfigDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static ContentFeedConfigDS getInstance() {
        return m_ContentFeedConfigDS;
    }

    public static synchronized ContentFeedConfigDS getInstance(long id) {
        ContentFeedConfigDS ret = (ContentFeedConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ContentFeedConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ContentFeedConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ContentFeedConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ContentFeedConfig loaded from DB. num=" + m_idToMap.size());
        
    }

    protected ContentFeedConfigDS() {
        m_dao = new ContentFeedConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_FeedCategoryToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ContentFeedConfigDS(long id) {
        m_dao = new ContentFeedConfigDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_FeedCategoryToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ContentFeedConfig getById(Long id) {
        return (ContentFeedConfig) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ContentFeedConfig o = (ContentFeedConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ContentFeedConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ContentFeedConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateFeedCategoryOneMap(obj, del);
    }
	
    //    //    //
	// ListKeys - SiteId
    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        ContentFeedConfig o = (ContentFeedConfig)obj;

		if ( o.getSiteId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("ContentFeedConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ContentFeedConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneStringKey - FeedCategory

    public ContentFeedConfig getObjectByFeedCategory(String keyFeedCategory) {
    	if (keyFeedCategory == null) return null; 
        return (ContentFeedConfig)m_FeedCategoryToOneMap.get(keyFeedCategory);
    }

    private void updateFeedCategoryOneMap(Object obj, boolean del) {
        ContentFeedConfig o = (ContentFeedConfig)obj;
        String _FeedCategory =  o.getFeedCategory();

		if (  _FeedCategory == null || _FeedCategory.isEmpty() ) return;

        if (del) {
            // delete from FeedCategoryToMap

            if (m_FeedCategoryToOneMap.containsKey(_FeedCategory)){
                m_FeedCategoryToOneMap.remove(_FeedCategory);
                 if (m_debug) m_logger.debug("ContentFeedConfig removed from FeedCategoryToMap" + o.getId() + " for [" + _FeedCategory+ "]");
            } else {
                if (m_debug) m_logger.debug("ContentFeedConfig not removed from FeedCategoryToMap" + o.getId() + " for [" + _FeedCategory+ "]");
            } 
        }
        else {
            
            if (m_FeedCategoryToOneMap.containsKey(_FeedCategory)){
                if (m_debug) m_logger.debug("ContentFeedConfig repalced FeedCategoryToMap" + o.getId() + " for [" + _FeedCategory+ "]");
            } else {
                if (m_debug) m_logger.debug("ContentFeedConfig added to FeedCategoryToMap" + o.getId() + " for [" + _FeedCategory+ "]");
            } 
            m_FeedCategoryToOneMap.put(_FeedCategory, o);
        }
    }











    public static void main(String[] args) throws Exception {

        ContentFeedConfigDS ds = new ContentFeedConfigDS();
        ContentFeedConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static ContentFeedConfig createDefault(){

        ContentFeedConfig ret = new ContentFeedConfig();        
//      ret.setFeedCategory("");           
//      ret.setFeedType("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static ContentFeedConfig copy(ContentFeedConfig org){

    	ContentFeedConfig ret = new ContentFeedConfig();

		ret.setFeedCategory(org.getFeedCategory());
		ret.setFeedType(org.getFeedType());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(ContentFeedConfig contentFeedConfig, Logger logger){
		logger.debug("ContentFeedConfig [" + contentFeedConfig.getId() + "]" + objectToString(contentFeedConfig));		
    }

	public static String objectToString(ContentFeedConfig contentFeedConfig){
		StringBuffer buf = new StringBuffer();
        buf.append("ContentFeedConfig=");
		buf.append("Id=").append(contentFeedConfig.getId()).append(", ");
		buf.append("SiteId=").append(contentFeedConfig.getSiteId()).append(", ");
		buf.append("FeedCategory=").append(contentFeedConfig.getFeedCategory()).append(", ");
		buf.append("FeedType=").append(contentFeedConfig.getFeedType()).append(", ");
		return buf.toString();    
    }
}
