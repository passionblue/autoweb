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
import com.autosite.db.ContentFeedRel;
import com.jtrend.service.DomainStore;

import com.autosite.db.ContentFeedRelDAO;

public class ContentFeedRelDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_ContentFeedIdToMap;


	protected Map m_ContentFeedIdContentIdToOneMap;




    private static Logger m_logger = Logger.getLogger(ContentFeedRelDS.class);
    private static ContentFeedRelDS m_ContentFeedRelDS = new ContentFeedRelDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static ContentFeedRelDS getInstance() {
        return m_ContentFeedRelDS;
    }

    public static synchronized ContentFeedRelDS getInstance(long id) {
        ContentFeedRelDS ret = (ContentFeedRelDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ContentFeedRelDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ContentFeedRelDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ContentFeedRelDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ContentFeedRel loaded from DB. num=" + m_idToMap.size());
        
    }

    protected ContentFeedRelDS() {
        m_dao = new ContentFeedRelDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_ContentFeedIdToMap = new ConcurrentHashMap();
		m_ContentFeedIdContentIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ContentFeedRelDS(long id) {
        m_dao = new ContentFeedRelDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_ContentFeedIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ContentFeedRel getById(Long id) {
        return (ContentFeedRel) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ContentFeedRel o = (ContentFeedRel)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ContentFeedRel removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ContentFeedRel added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateContentFeedIdMap(obj, del);
		updateContentFeedIdContentIdOneMap(obj, del);
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
        ContentFeedRel o = (ContentFeedRel)obj;

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
            if (m_debug) m_logger.debug("ContentFeedRel removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ContentFeedRel added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }
	// ListKeys - ContentFeedId
    public List getByContentFeedId(long ContentFeedId) {
        
        Long _ContentFeedId  = new Long(ContentFeedId);
        if (m_ContentFeedIdToMap.containsKey(_ContentFeedId)) {
            return new ArrayList( ((Map)m_ContentFeedIdToMap.get(_ContentFeedId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateContentFeedIdMap(Object obj, boolean del) {
        ContentFeedRel o = (ContentFeedRel)obj;

		if ( o.getContentFeedId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from ContentFeedIdToMap
            Map map  = (Map) m_ContentFeedIdToMap.get(new Long(o.getContentFeedId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("ContentFeedRel removed from ContentFeedIdToMap" + o.getId() + " from " + o.getContentFeedId());
        }
        else {
            
            // add to ContentFeedIdToMap
            Map map  = (Map) m_ContentFeedIdToMap.get(new Long(o.getContentFeedId()));
            if ( map == null ) {
                map = new TreeMap();
                m_ContentFeedIdToMap.put(new Long(o.getContentFeedId()), map);
                if (m_debug) m_logger.debug("created new   ContentFeedIdToMap for " + o.getContentFeedId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("ContentFeedRel added to ContentFeedIdToMap " + o.getId() + " to " + o.getContentFeedId());
        }
    }


    





        
    public  ContentFeedRel getByContentFeedIdContentId(long ContentFeedId, long ContentId) {

        Long keyContentFeedId  = new Long(ContentFeedId);
        if (m_ContentFeedIdContentIdToOneMap.containsKey(keyContentFeedId)) {
            
            Map mapContentFeedId = (Map)m_ContentFeedIdContentIdToOneMap.get(keyContentFeedId);

     	    Long keyContentId = new Long(ContentId);
            
            if ( mapContentFeedId.containsKey(keyContentId)){
                return ( ContentFeedRel)mapContentFeedId.get(keyContentId);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateContentFeedIdContentIdOneMap(Object obj, boolean del) {
        ContentFeedRel o = (ContentFeedRel)obj;

     	    Long keyContentId = new Long(o.getContentId());

        if (del) {
            // delete from ContentFeedIdContentIdToOneMap
            Map mapContentFeedId  = (Map) m_ContentFeedIdContentIdToOneMap.get(new Long(o.getContentFeedId()));
            if ( mapContentFeedId != null ) {
                if (mapContentFeedId.containsKey(keyContentId)){
                    mapContentFeedId.remove(keyContentId);
                }
            }
            m_logger.debug("removed ContentFeedRel from m_ContentFeedIdContentIdToOneMap" + o.getId() + " from " + o.getContentFeedId() + " # " + o.getContentId());
        }
        else {
            
            // add to ContentFeedIdContentIdToOneMap
            Map mapContentFeedId  = (Map) m_ContentFeedIdContentIdToOneMap.get(new Long(o.getContentFeedId()));
            if ( mapContentFeedId == null ) {
                mapContentFeedId = new TreeMap();
                m_ContentFeedIdContentIdToOneMap.put(new Long(o.getContentFeedId()), mapContentFeedId);
                if (m_debug) m_logger.debug("created new mapContentFeedId for " + o.getContentFeedId());
            }
            
            
			ContentFeedRel replaced = (ContentFeedRel) mapContentFeedId.put(keyContentId,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: ContentFeedIdContentIdOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Panel added to ContentFeedIdContentIdToOneMap " + o.getId() + " to " + o.getContentFeedId());
        }
        
    }    
        






    public static void main(String[] args) throws Exception {

        ContentFeedRelDS ds = new ContentFeedRelDS();
        ContentFeedRel obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static ContentFeedRel createDefault(){

        ContentFeedRel ret = new ContentFeedRel();        
//      ret.setContentFeedId("");           
//      ret.setContentId("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static ContentFeedRel copy(ContentFeedRel org){

    	ContentFeedRel ret = new ContentFeedRel();

		ret.setContentFeedId(org.getContentFeedId());
		ret.setContentId(org.getContentId());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(ContentFeedRel contentFeedRel, Logger logger){
		logger.debug("ContentFeedRel [" + contentFeedRel.getId() + "]" + objectToString(contentFeedRel));		
    }

	public static String objectToString(ContentFeedRel contentFeedRel){
		StringBuffer buf = new StringBuffer();
        buf.append("ContentFeedRel=");
		buf.append("Id=").append(contentFeedRel.getId()).append(", ");
		buf.append("SiteId=").append(contentFeedRel.getSiteId()).append(", ");
		buf.append("ContentFeedId=").append(contentFeedRel.getContentFeedId()).append(", ");
		return buf.toString();    
    }
}
