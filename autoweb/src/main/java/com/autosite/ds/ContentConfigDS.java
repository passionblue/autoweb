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
import com.autosite.db.ContentConfig;
import com.jtrend.service.DomainStore;

import com.autosite.db.ContentConfigDAO;

public class ContentConfigDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;




    protected Map m_SiteIdToContentIdToOneMap;



    private static Logger m_logger = Logger.getLogger(ContentConfigDS.class);
    private static ContentConfigDS m_ContentConfigDS = new ContentConfigDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static ContentConfigDS getInstance() {
        return m_ContentConfigDS;
    }

    public static synchronized ContentConfigDS getInstance(long id) {
        ContentConfigDS ret = (ContentConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ContentConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ContentConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ContentConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ContentConfig loaded from DB. num=" + m_idToMap.size());
        
    }

    protected ContentConfigDS() {
        m_dao = new ContentConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToContentIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ContentConfigDS(long id) {
        m_dao = new ContentConfigDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToContentIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ContentConfig getById(Long id) {
        return (ContentConfig) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ContentConfig o = (ContentConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ContentConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ContentConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdToContentIdOneMap(obj, del);
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
        ContentConfig o = (ContentConfig)obj;

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
            if (m_debug) m_logger.debug("ContentConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ContentConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    









	// listOneToOneIdToSiteIdKey - ContentId

    public  ContentConfig getBySiteIdToContentId(long siteId, long ContentId) {

        Long keySiteIdTo  = new Long(siteId);
        if (m_SiteIdToContentIdToOneMap.containsKey(keySiteIdTo)) {
            
            Map mapSiteIdTo = (Map)m_SiteIdToContentIdToOneMap.get(keySiteIdTo);

     	    Long keyContentId = new Long(ContentId);
            
            if ( mapSiteIdTo.containsKey(keyContentId)){
                return ( ContentConfig)mapSiteIdTo.get(keyContentId);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    public List getByOrderContentId(){
        List ret = new ArrayList();
    
        List contentsByContentId = new ArrayList( m_SiteIdToContentIdToOneMap.values());
        Map map  = new TreeMap();
        
        for (Iterator iterator = contentsByContentId.iterator(); iterator.hasNext();) {
            ContentConfig obj = (ContentConfig) iterator.next();
            map.put(new Long(obj.getContentId()), obj);
        }
        
        return new ArrayList(map.values());
    }

    private void updateSiteIdToContentIdOneMap(Object obj, boolean del) {
        ContentConfig o = (ContentConfig)obj;

   	    Long keyContentId = new Long(o.getContentId());

        if (del) {
            // delete from PollIdContentIdToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToContentIdToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo != null ) {
                if (mapSiteIdTo.containsKey(keyContentId)){
                    mapSiteIdTo.remove(keyContentId);
                }
            }
            m_logger.debug("removed ContentConfig from m_SiteIdToContentIdToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getContentId());
        }
        else {
            
            // add to SiteIdToContentIdToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToContentIdToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo == null ) {
                mapSiteIdTo = new TreeMap();
                m_SiteIdToContentIdToOneMap.put(new Long(o.getSiteId()), mapSiteIdTo);
                if (m_debug) m_logger.debug("created new  mapSiteIdTo for " + o.getSiteId());
            }
            
			ContentConfig replaced = (ContentConfig) mapSiteIdTo.put(keyContentId,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdToContentId existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("ContentConfig added to SiteIdToContentIdToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    





    public static void main(String[] args) throws Exception {

        ContentConfigDS ds = new ContentConfigDS();
        ContentConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static ContentConfig createDefault(){

        ContentConfig _ContentConfig = new ContentConfig();        
	    _ContentConfig = new ContentConfig();// ContentConfigDS.getInstance().getDeafult();
        return _ContentConfig;
    }

    public static ContentConfig copy(ContentConfig org){

    	ContentConfig ret = new ContentConfig();

		ret.setContentId(org.getContentId());
		ret.setKeywords(org.getKeywords());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(ContentConfig contentConfig, Logger logger){
		logger.debug("ContentConfig [" + contentConfig.getId() + "]" + objectToString(contentConfig));		
    }

	public static String objectToString(ContentConfig contentConfig){
		StringBuffer buf = new StringBuffer();
        buf.append("ContentConfig=");
		return buf.toString();    
    }
}
