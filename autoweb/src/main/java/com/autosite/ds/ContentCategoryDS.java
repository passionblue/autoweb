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
import com.autosite.db.ContentCategory;
import com.jtrend.service.DomainStore;

import com.autosite.db.ContentCategoryDAO;

public class ContentCategoryDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PageIdToMap;


	protected Map m_PageIdCategoryToOneMap;

    protected Map m_SiteIdToPageIdToMap;
    protected Map m_SiteIdToCategoryToMap;




    private static Logger m_logger = Logger.getLogger(ContentCategoryDS.class);
    private static ContentCategoryDS m_ContentCategoryDS = new ContentCategoryDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static ContentCategoryDS getInstance() {
        return m_ContentCategoryDS;
    }

    public static synchronized ContentCategoryDS getInstance(long id) {
        ContentCategoryDS ret = (ContentCategoryDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ContentCategoryDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ContentCategoryDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ContentCategoryDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ContentCategory loaded from DB. num=" + m_idToMap.size());
        
    }

    protected ContentCategoryDS() {
        m_dao = new ContentCategoryDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToMap = new ConcurrentHashMap();
		m_PageIdCategoryToOneMap = new ConcurrentHashMap();
        m_SiteIdToPageIdToMap = new ConcurrentHashMap();
        m_SiteIdToCategoryToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ContentCategoryDS(long id) {
        m_dao = new ContentCategoryDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToMap = new ConcurrentHashMap();
        m_SiteIdToPageIdToMap = new ConcurrentHashMap();
        m_SiteIdToCategoryToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ContentCategory getById(Long id) {
        return (ContentCategory) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ContentCategory o = (ContentCategory)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ContentCategory removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ContentCategory added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updatePageIdMap(obj, del);
		updatePageIdCategoryOneMap(obj, del);
        updateSiteIdToPageIdMap(obj, del);
        updateSiteIdToCategoryMap(obj, del);
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
        ContentCategory o = (ContentCategory)obj;

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
            if (m_debug) m_logger.debug("ContentCategory removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ContentCategory added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }
	// ListKeys - PageId
    public List getByPageId(long PageId) {
        
        Long _PageId  = new Long(PageId);
        if (m_PageIdToMap.containsKey(_PageId)) {
            return new ArrayList( ((Map)m_PageIdToMap.get(_PageId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updatePageIdMap(Object obj, boolean del) {
        ContentCategory o = (ContentCategory)obj;

		if ( o.getPageId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from PageIdToMap
            Map map  = (Map) m_PageIdToMap.get(new Long(o.getPageId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("ContentCategory removed from PageIdToMap" + o.getId() + " from " + o.getPageId());
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
            if (m_debug) m_logger.debug("ContentCategory added to PageIdToMap " + o.getId() + " to " + o.getPageId());
        }
    }


    





        
    public  ContentCategory getByPageIdCategory(long PageId, String Category) {

        Long keyPageId  = new Long(PageId);
        if (m_PageIdCategoryToOneMap.containsKey(keyPageId)) {
            
            Map mapPageId = (Map)m_PageIdCategoryToOneMap.get(keyPageId);

     	    String keyCategory =  Category;
            if (  keyCategory == null || keyCategory.isEmpty()) return null;
            
            if ( mapPageId.containsKey(keyCategory)){
                return ( ContentCategory)mapPageId.get(keyCategory);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updatePageIdCategoryOneMap(Object obj, boolean del) {
        ContentCategory o = (ContentCategory)obj;

     	    String keyCategory =  o.getCategory();
            if ( keyCategory == null || keyCategory.isEmpty()) return;

        if (del) {
            // delete from PageIdCategoryToOneMap
            Map mapPageId  = (Map) m_PageIdCategoryToOneMap.get(new Long(o.getPageId()));
            if ( mapPageId != null ) {
                if (mapPageId.containsKey(keyCategory)){
                    mapPageId.remove(keyCategory);
                }
            }
            m_logger.debug("removed ContentCategory from m_PageIdCategoryToOneMap" + o.getId() + " from " + o.getPageId() + " # " + o.getCategory());
        }
        else {
            
            // add to PageIdCategoryToOneMap
            Map mapPageId  = (Map) m_PageIdCategoryToOneMap.get(new Long(o.getPageId()));
            if ( mapPageId == null ) {
                mapPageId = new TreeMap();
                m_PageIdCategoryToOneMap.put(new Long(o.getPageId()), mapPageId);
                if (m_debug) m_logger.debug("created new mapPageId for " + o.getPageId());
            }
            
            
			ContentCategory replaced = (ContentCategory) mapPageId.put(keyCategory,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: PageIdCategoryOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Panel added to PageIdCategoryToOneMap " + o.getId() + " to " + o.getPageId());
        }
        
    }    
        

    public List getBySiteIdToPageIdList (long SiteId, long PageId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToPageIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToPageIdToMap.get(keySiteId);

     	    Long keyPageId = PageId;
            
            if ( mapSiteId.containsKey(keyPageId)){
                return new ArrayList( ((Map)mapSiteId.get(keyPageId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdPageId(long SiteId, long PageId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToPageIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToPageIdToMap.get(keySiteId);

     	    Long keyPageId = new Long(PageId);
            
            if ( mapSiteId.containsKey(keyPageId)){
                return (Map)mapSiteId.get(keyPageId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToPageIdMap(Object obj, boolean del) {
        ContentCategory o = (ContentCategory)obj;

   	    Long keyPageId = new Long(o.getPageId());

        if (del) {
            // delete from SiteIdPageIdToMap
            Map mapSiteId  = (Map) m_SiteIdToPageIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapPageId = (Map) mapSiteId.get(keyPageId);
                if (mapPageId != null){
                    mapPageId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed ContentCategory from m_SiteIdToPageIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getPageId());
        }
        else {
            
            // add to SiteIdPageIdToMap
            Map mapSiteId  = (Map) m_SiteIdToPageIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToPageIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapPageId = (Map) mapSiteId.get(keyPageId);
            
            if ( mapPageId == null) {
                mapPageId = new TreeMap();
                mapSiteId.put(keyPageId, mapPageId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapPageId.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdPageIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    

    public List getBySiteIdCategoryList(long SiteId, String Category) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToCategoryToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToCategoryToMap.get(keySiteId);

     	    String keyCategory =  Category;
			if (  keyCategory == null || keyCategory.isEmpty()) return new ArrayList();
            
            if ( mapSiteId.containsKey(keyCategory)){
                return new ArrayList( ((Map)mapSiteId.get(keyCategory)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdCategoryList(long SiteId, String Category) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToCategoryToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToCategoryToMap.get(keySiteId);

     	    String keyCategory =  Category;
			if (  keyCategory == null || keyCategory.isEmpty()) return new HashMap();
            
            if ( mapSiteId.containsKey(keyCategory)){
                return (Map)mapSiteId.get(keyCategory);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToCategoryMap(Object obj, boolean del) {
        ContentCategory o = (ContentCategory)obj;

   	    String keyCategory =  o.getCategory();
		if (  keyCategory == null || keyCategory.isEmpty()) return;

        if (del) {
            // delete from SiteIdCategoryToMap
            Map mapSiteId  = (Map) m_SiteIdToCategoryToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapCategory = (Map) mapSiteId.get(keyCategory);
                if (mapCategory != null){
                    mapCategory.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed ContentCategory from m_SiteIdToCategoryToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getCategory());
        }
        else {
            
            // add to SiteIdCategoryToMap
            Map mapSiteId  = (Map) m_SiteIdToCategoryToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToCategoryToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapCategory = (Map) mapSiteId.get(keyCategory);
            
            if ( mapCategory == null) {
                mapCategory = new TreeMap();
                mapSiteId.put(keyCategory, mapCategory);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapCategory.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdCategoryToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    







    public static void main(String[] args) throws Exception {

        ContentCategoryDS ds = new ContentCategoryDS();
        ContentCategory obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static ContentCategory createDefault(){

        ContentCategory ret = new ContentCategory();        
//      ret.setPageId("");           
//      ret.setCategory("");           
//      ret.setImageUrl("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static ContentCategory copy(ContentCategory org){

    	ContentCategory ret = new ContentCategory();

		ret.setPageId(org.getPageId());
		ret.setCategory(org.getCategory());
		ret.setImageUrl(org.getImageUrl());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(ContentCategory contentCategory, Logger logger){
		logger.debug("ContentCategory [" + contentCategory.getId() + "]" + objectToString(contentCategory));		
    }

	public static String objectToString(ContentCategory contentCategory){
		StringBuffer buf = new StringBuffer();
        buf.append("ContentCategory=");
		buf.append("Id=").append(contentCategory.getId()).append(", ");
		buf.append("SiteId=").append(contentCategory.getSiteId()).append(", ");
		buf.append("PageId=").append(contentCategory.getPageId()).append(", ");
		buf.append("Category=").append(contentCategory.getCategory()).append(", ");
		return buf.toString();    
    }
}
