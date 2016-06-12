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
import com.autosite.db.ContentPageRelation;
import com.autosite.db.ContentPageRelationDAO;
import com.jtrend.service.DomainStore;

public class ContentPageRelationDS extends AbstractDS implements DomainStore {

    protected Map m_ContentIdToMap;
    protected Map m_PageIdToMap;

    private static Logger m_logger = Logger.getLogger(ContentPageRelationDS.class);
    private static ContentPageRelationDS m_ContentPageRelationDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized ContentPageRelationDS getInstance() {
        if (m_ContentPageRelationDS == null) {
            m_ContentPageRelationDS = new ContentPageRelationDS();
        }
        return m_ContentPageRelationDS;
    }

    public static synchronized ContentPageRelationDS getInstance(long id) {
        ContentPageRelationDS ret = (ContentPageRelationDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ContentPageRelationDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ContentPageRelationDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

        } else {
            super.loadFromDB();
        }
    }

    protected ContentPageRelationDS() {
        m_dao = new ContentPageRelationDAO();
        m_idToMap = new ConcurrentHashMap();

        m_ContentIdToMap = new ConcurrentHashMap();
        m_PageIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ContentPageRelationDS(long id) {
        m_loadById = id;
        m_dao = new ContentPageRelationDAO();
        m_idToMap = new ConcurrentHashMap();

        m_ContentIdToMap = new ConcurrentHashMap();
        m_PageIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ContentPageRelation getById(Long id) {
        return (ContentPageRelation) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ContentPageRelation o = (ContentPageRelation)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ContentPageRelation removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ContentPageRelation added to DS " + o.getId());
        }

        updateContentIdMap(obj, del);
        updatePageIdMap(obj, del);
    }


    public List getByContentId(long ContentId) {
        
        Long _ContentId  = new Long(ContentId);
        if (m_ContentIdToMap.containsKey(_ContentId)) {
            return new ArrayList( ((Map)m_ContentIdToMap.get(_ContentId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateContentIdMap(Object obj, boolean del) {
        ContentPageRelation o = (ContentPageRelation)obj;

        if (del) {

            // delete from ContentIdToMap
            Map map  = (Map) m_ContentIdToMap.get(new Long(o.getContentId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("ContentPageRelation removed from ContentIdToMap" + o.getId() + " from " + o.getContentId());
        }
        else {
            
            // add to ContentIdToMap
            Map map  = (Map) m_ContentIdToMap.get(new Long(o.getContentId()));
            if ( map == null ) {
                map = new TreeMap();
                m_ContentIdToMap.put(new Long(o.getContentId()), map);
                if (m_debug) m_logger.debug("created new   ContentIdToMap for " + o.getContentId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("ContentPageRelation added to ContentIdToMap " + o.getId() + " to " + o.getContentId());
        }
    }

    public List getByPageId(long PageId) {
        
        Long _PageId  = new Long(PageId);
        if (m_PageIdToMap.containsKey(_PageId)) {
            return new ArrayList( ((Map)m_PageIdToMap.get(_PageId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updatePageIdMap(Object obj, boolean del) {
        ContentPageRelation o = (ContentPageRelation)obj;

        if (del) {

            // delete from PageIdToMap
            Map map  = (Map) m_PageIdToMap.get(new Long(o.getPageId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("ContentPageRelation removed from PageIdToMap" + o.getId() + " from " + o.getPageId());
        }
        else {
            
            // add to PageIdToMap
            Map map  = (Map) m_PageIdToMap.get(new Long(o.getPageId()));
            if ( map == null ) {
                map = new TreeMap();
                m_PageIdToMap.put(new Long(o.getPageId()), map);
                if (m_debug) m_logger.debug("created new   PageIdToMap for " + o.getPageId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("ContentPageRelation added to PageIdToMap " + o.getId() + " to " + o.getPageId());
        }
    }


    



    public static void main(String[] args) throws Exception {

        ContentPageRelationDS ds = new ContentPageRelationDS();
        ContentPageRelation obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static ContentPageRelation createDefault(){

        ContentPageRelation ret = new ContentPageRelation();        
//      ret.setContentId("");           
//      ret.setPageId("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static ContentPageRelation copy(ContentPageRelation org){

    	ContentPageRelation ret = new ContentPageRelation();

		ret.setContentId(org.getContentId());
		ret.setPageId(org.getPageId());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(ContentPageRelation contentPageRelation, Logger logger){
		logger.debug("ContentPageRelation [" + contentPageRelation.getId() + "]" + objectToString(contentPageRelation));		
    }

	public static String objectToString(ContentPageRelation contentPageRelation){
		StringBuffer buf = new StringBuffer();

		return buf.toString();    
    }
}
