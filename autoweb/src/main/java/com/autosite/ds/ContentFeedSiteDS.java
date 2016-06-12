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
import com.autosite.db.ContentFeedSite;
import com.jtrend.service.DomainStore;

import com.autosite.db.ContentFeedSiteDAO;

public class ContentFeedSiteDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_ContentFeedIdToMap;
    protected Map m_SiteIdToOneMap;






    private static Logger m_logger = Logger.getLogger(ContentFeedSiteDS.class);
    private static ContentFeedSiteDS m_ContentFeedSiteDS = new ContentFeedSiteDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static ContentFeedSiteDS getInstance() {
        return m_ContentFeedSiteDS;
    }

    public static synchronized ContentFeedSiteDS getInstance(long id) {
        ContentFeedSiteDS ret = (ContentFeedSiteDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ContentFeedSiteDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ContentFeedSiteDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ContentFeedSiteDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ContentFeedSite loaded from DB. num=" + m_idToMap.size());
        
    }

    protected ContentFeedSiteDS() {
        m_dao = new ContentFeedSiteDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_ContentFeedIdToMap = new ConcurrentHashMap();
        m_SiteIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ContentFeedSiteDS(long id) {
        m_dao = new ContentFeedSiteDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_ContentFeedIdToMap = new ConcurrentHashMap();
        m_SiteIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ContentFeedSite getById(Long id) {
        return (ContentFeedSite) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ContentFeedSite o = (ContentFeedSite)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ContentFeedSite removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ContentFeedSite added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateContentFeedIdMap(obj, del);
        updateSiteIdOneMap(obj, del);
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
        ContentFeedSite o = (ContentFeedSite)obj;

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
            if (m_debug) m_logger.debug("ContentFeedSite removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ContentFeedSite added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        ContentFeedSite o = (ContentFeedSite)obj;

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
            if (m_debug) m_logger.debug("ContentFeedSite removed from ContentFeedIdToMap" + o.getId() + " from " + o.getContentFeedId());
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
            if (m_debug) m_logger.debug("ContentFeedSite added to ContentFeedIdToMap " + o.getId() + " to " + o.getContentFeedId());
        }
    }


    

	// listOneToOneIdKey - SiteId

    public ContentFeedSite getObjectBySiteId(long keySiteId) {
        return (ContentFeedSite)m_SiteIdToOneMap.get(new Long(keySiteId));
    }

    private void updateSiteIdOneMap(Object obj, boolean del) {
        ContentFeedSite o = (ContentFeedSite)obj;
        Long _SiteId = new Long(o.getSiteId());

        if (del) {
            // delete from SiteIdToOneMap

            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                m_SiteIdToOneMap.remove(_SiteId);
                if (m_debug) m_logger.debug("ContentFeedSite removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("ContentFeedSite not removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]. Does not exist");
            } 
        }
        else {
            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                if (m_debug) m_logger.debug("ContentFeedSite repalced SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("ContentFeedSite added to SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } 
            m_SiteIdToOneMap.put(_SiteId, o);
        }
    }










    public static void main(String[] args) throws Exception {

        ContentFeedSiteDS ds = new ContentFeedSiteDS();
        ContentFeedSite obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static ContentFeedSite createDefault(){

        ContentFeedSite ret = new ContentFeedSite();        
//      ret.setContentFeedId("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static ContentFeedSite copy(ContentFeedSite org){

    	ContentFeedSite ret = new ContentFeedSite();

		ret.setContentFeedId(org.getContentFeedId());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(ContentFeedSite contentFeedSite, Logger logger){
		logger.debug("ContentFeedSite [" + contentFeedSite.getId() + "]" + objectToString(contentFeedSite));		
    }

	public static String objectToString(ContentFeedSite contentFeedSite){
		StringBuffer buf = new StringBuffer();
        buf.append("ContentFeedSite=");
		buf.append("Id=").append(contentFeedSite.getId()).append(", ");
		buf.append("SiteId=").append(contentFeedSite.getSiteId()).append(", ");
		buf.append("ContentFeedId=").append(contentFeedSite.getContentFeedId()).append(", ");
		return buf.toString();    
    }
}
