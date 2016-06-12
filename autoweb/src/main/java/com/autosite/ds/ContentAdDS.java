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
import com.autosite.db.ContentAd;
import com.jtrend.service.DomainStore;

import com.autosite.db.ContentAdDAO;

public class ContentAdDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;



    protected Map m_SiteIdToContentIdToMap;




    private static Logger m_logger = Logger.getLogger(ContentAdDS.class);
    private static ContentAdDS m_ContentAdDS = new ContentAdDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static ContentAdDS getInstance() {
        return m_ContentAdDS;
    }

    public static synchronized ContentAdDS getInstance(long id) {
        ContentAdDS ret = (ContentAdDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ContentAdDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ContentAdDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ContentAdDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ContentAd loaded from DB. num=" + m_idToMap.size());
        
    }

    protected ContentAdDS() {
        m_dao = new ContentAdDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToContentIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ContentAdDS(long id) {
        m_dao = new ContentAdDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToContentIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ContentAd getById(Long id) {
        return (ContentAd) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ContentAd o = (ContentAd)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ContentAd removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ContentAd added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdToContentIdMap(obj, del);
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
        ContentAd o = (ContentAd)obj;

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
            if (m_debug) m_logger.debug("ContentAd removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ContentAd added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    






    public List getBySiteIdToContentIdList (long SiteId, long ContentId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToContentIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToContentIdToMap.get(keySiteId);

     	    Long keyContentId = ContentId;
            
            if ( mapSiteId.containsKey(keyContentId)){
                return new ArrayList( ((Map)mapSiteId.get(keyContentId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdContentId(long SiteId, long ContentId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToContentIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToContentIdToMap.get(keySiteId);

     	    Long keyContentId = new Long(ContentId);
            
            if ( mapSiteId.containsKey(keyContentId)){
                return (Map)mapSiteId.get(keyContentId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToContentIdMap(Object obj, boolean del) {
        ContentAd o = (ContentAd)obj;

   	    Long keyContentId = new Long(o.getContentId());

        if (del) {
            // delete from SiteIdContentIdToMap
            Map mapSiteId  = (Map) m_SiteIdToContentIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapContentId = (Map) mapSiteId.get(keyContentId);
                if (mapContentId != null){
                    mapContentId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed ContentAd from m_SiteIdToContentIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getContentId());
        }
        else {
            
            // add to SiteIdContentIdToMap
            Map mapSiteId  = (Map) m_SiteIdToContentIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToContentIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapContentId = (Map) mapSiteId.get(keyContentId);
            
            if ( mapContentId == null) {
                mapContentId = new TreeMap();
                mapSiteId.put(keyContentId, mapContentId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapContentId.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdContentIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    








    public static void main(String[] args) throws Exception {

        ContentAdDS ds = new ContentAdDS();
        ContentAd obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static ContentAd createDefault(){

        ContentAd ret = new ContentAd();        
//      ret.setContentId("");           
//      ret.setPositionCode("");           
//      ret.setAdContent("");           
//      ret.setHide("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static ContentAd copy(ContentAd org){

    	ContentAd ret = new ContentAd();

		ret.setContentId(org.getContentId());
		ret.setPositionCode(org.getPositionCode());
		ret.setAdContent(org.getAdContent());
		ret.setHide(org.getHide());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(ContentAd contentAd, Logger logger){
		logger.debug("ContentAd [" + contentAd.getId() + "]" + objectToString(contentAd));		
    }

	public static String objectToString(ContentAd contentAd){
		StringBuffer buf = new StringBuffer();
        buf.append("ContentAd=");
		buf.append("Id=").append(contentAd.getId()).append(", ");
		buf.append("SiteId=").append(contentAd.getSiteId()).append(", ");
		buf.append("ContentId=").append(contentAd.getContentId()).append(", ");
		return buf.toString();    
    }
}
