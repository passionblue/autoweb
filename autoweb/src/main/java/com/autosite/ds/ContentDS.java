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
import com.autosite.db.Content;
import com.jtrend.service.DomainStore;

import com.autosite.db.ContentDAO;

public class ContentDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PageIdToMap;
    protected Map m_PanelIdToMap;

	protected Map m_PageIdCategoryIdToMap;

	protected Map m_PageIdCategoryToOneMap;

    protected Map m_SiteIdToShortcutUrlToOneMap;


    private static Logger m_logger = Logger.getLogger(ContentDS.class);
    private static ContentDS m_ContentDS = new ContentDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static ContentDS getInstance() {
        return m_ContentDS;
    }

    public static synchronized ContentDS getInstance(long id) {
        ContentDS ret = (ContentDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ContentDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ContentDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ContentDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("Content loaded from DB. num=" + m_idToMap.size());
        
    }

    protected ContentDS() {
        m_dao = new ContentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToMap = new ConcurrentHashMap();
        m_PanelIdToMap = new ConcurrentHashMap();
		m_PageIdCategoryIdToMap = new ConcurrentHashMap();
		m_PageIdCategoryToOneMap = new ConcurrentHashMap();
        m_SiteIdToShortcutUrlToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ContentDS(long id) {
        m_dao = new ContentDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToMap = new ConcurrentHashMap();
        m_PanelIdToMap = new ConcurrentHashMap();
        m_SiteIdToShortcutUrlToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public Content getById(Long id) {
        return (Content) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        Content o = (Content)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("Content removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("Content added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updatePageIdMap(obj, del);
        updatePanelIdMap(obj, del);
		updatePageIdCategoryIdMap(obj, del);
		updatePageIdCategoryOneMap(obj, del);
        updateSiteIdToShortcutUrlOneMap(obj, del);
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
        Content o = (Content)obj;

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
            if (m_debug) m_logger.debug("Content removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("Content added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        Content o = (Content)obj;

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
            if (m_debug) m_logger.debug("Content removed from PageIdToMap" + o.getId() + " from " + o.getPageId());
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
            if (m_debug) m_logger.debug("Content added to PageIdToMap " + o.getId() + " to " + o.getPageId());
        }
    }
	// ListKeys - PanelId
    public List getByPanelId(long PanelId) {
        
        Long _PanelId  = new Long(PanelId);
        if (m_PanelIdToMap.containsKey(_PanelId)) {
            return new ArrayList( ((Map)m_PanelIdToMap.get(_PanelId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updatePanelIdMap(Object obj, boolean del) {
        Content o = (Content)obj;

		if ( o.getPanelId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from PanelIdToMap
            Map map  = (Map) m_PanelIdToMap.get(new Long(o.getPanelId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("Content removed from PanelIdToMap" + o.getId() + " from " + o.getPanelId());
        }
        else {
            
            // add to PanelIdToMap
            Map map  = (Map) m_PanelIdToMap.get(new Long(o.getPanelId()));
            if ( map == null ) {
                map = new TreeMap();
                m_PanelIdToMap.put(new Long(o.getPanelId()), map);
                if (m_debug) m_logger.debug("created new   PanelIdToMap for " + o.getPanelId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("Content added to PanelIdToMap " + o.getId() + " to " + o.getPanelId());
        }
    }


    




        
    public List getByPageIdCategoryId(long PageId, long CategoryId) {

        Long keyPageId  = new Long(PageId);
        if (m_PageIdCategoryIdToMap.containsKey(keyPageId)) {
            
            Map mapPageId = (Map)m_PageIdCategoryIdToMap.get(keyPageId);

     	    Long keyCategoryId = new Long(CategoryId);
            
            if ( mapPageId.containsKey(keyCategoryId)){
                return new ArrayList( ((Map)mapPageId.get(keyCategoryId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapByPageIdCategoryId(long PageId, long CategoryId) {

        Long keyPageId  = new Long(PageId);
        if (m_PageIdCategoryIdToMap.containsKey(keyPageId)) {
            
            Map mapPageId = (Map)m_PageIdCategoryIdToMap.get(keyPageId);

     	    Long keyCategoryId = new Long(CategoryId);
            
            if ( mapPageId.containsKey(keyCategoryId)){
                return (Map)mapPageId.get(keyCategoryId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updatePageIdCategoryIdMap(Object obj, boolean del) {
        Content o = (Content)obj;

   	    Long keyCategoryId = new Long(o.getCategoryId());

        if (del) {
            // delete from PageIdCategoryIdToMap
            Map mapPageId  = (Map) m_PageIdCategoryIdToMap.get(new Long(o.getPageId()));
            if ( mapPageId != null ) {
                Map mapCategoryId = (Map) mapPageId.get(keyCategoryId);
                if (mapCategoryId != null){
                    mapCategoryId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed Content from m_PageIdCategoryIdToMap" + o.getId() + " from " + o.getPageId() + " # " + o.getCategoryId());
        }
        else {
            
            // add to PageIdCategoryIdToMap
            Map mapPageId  = (Map) m_PageIdCategoryIdToMap.get(new Long(o.getPageId()));
            if ( mapPageId == null ) {
                mapPageId = new TreeMap();
                m_PageIdCategoryIdToMap.put(new Long(o.getPageId()), mapPageId);
                if (m_debug) m_logger.debug("created new   mapPageId for " + o.getPageId());
            }
            Map mapCategoryId = (Map) mapPageId.get(keyCategoryId);
            
            if ( mapCategoryId == null) {
                mapCategoryId = new TreeMap();
                mapPageId.put(keyCategoryId, mapCategoryId);
                if (m_debug) m_logger.debug("created new   mapPageId for " + o.getPageId());
            }
            mapCategoryId.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to PageIdCategoryIdToMap " + o.getId() + " to " + o.getPageId());
        }
        
    }    
        

        
    public  Content getByPageIdCategory(long PageId, String Category) {

        Long keyPageId  = new Long(PageId);
        if (m_PageIdCategoryToOneMap.containsKey(keyPageId)) {
            
            Map mapPageId = (Map)m_PageIdCategoryToOneMap.get(keyPageId);

     	    String keyCategory =  Category;
            if (  keyCategory == null || keyCategory.isEmpty()) return null;
            
            if ( mapPageId.containsKey(keyCategory)){
                return ( Content)mapPageId.get(keyCategory);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updatePageIdCategoryOneMap(Object obj, boolean del) {
        Content o = (Content)obj;

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
            m_logger.debug("removed Content from m_PageIdCategoryToOneMap" + o.getId() + " from " + o.getPageId() + " # " + o.getCategory());
        }
        else {
            
            // add to PageIdCategoryToOneMap
            Map mapPageId  = (Map) m_PageIdCategoryToOneMap.get(new Long(o.getPageId()));
            if ( mapPageId == null ) {
                mapPageId = new TreeMap();
                m_PageIdCategoryToOneMap.put(new Long(o.getPageId()), mapPageId);
                if (m_debug) m_logger.debug("created new mapPageId for " + o.getPageId());
            }
            
            
			Content replaced = (Content) mapPageId.put(keyCategory,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: PageIdCategoryOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Panel added to PageIdCategoryToOneMap " + o.getId() + " to " + o.getPageId());
        }
        
    }    
        





	// listOneToOneStringToSiteIdKey - ShortcutUrl

    public  Content getBySiteIdToShortcutUrl(long siteId, String ShortcutUrl) {

        Long keySiteIdTo  = new Long(siteId);
        if (m_SiteIdToShortcutUrlToOneMap.containsKey(keySiteIdTo)) {
            
            Map mapSiteIdTo = (Map)m_SiteIdToShortcutUrlToOneMap.get(keySiteIdTo);

     	    String keyShortcutUrl = ShortcutUrl;
            
            if ( keyShortcutUrl!= null && mapSiteIdTo.containsKey(keyShortcutUrl)){
                return ( Content)mapSiteIdTo.get(keyShortcutUrl);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    public List getByOrderShortcutUrl(){
        List ret = new ArrayList();
    
        List contentsByShortcutUrl = new ArrayList( m_SiteIdToShortcutUrlToOneMap.values());
        Map map  = new TreeMap();
        
        for (Iterator iterator = contentsByShortcutUrl.iterator(); iterator.hasNext();) {
            Content obj = (Content) iterator.next();
            if ( obj.getShortcutUrl() == null ) continue;
            map.put(obj.getShortcutUrl(), obj);
        }
        
        return new ArrayList(map.values());
    }

    private void updateSiteIdToShortcutUrlOneMap(Object obj, boolean del) {
        Content o = (Content)obj;

   	    String keyShortcutUrl = o.getShortcutUrl();

		if ( keyShortcutUrl == null || keyShortcutUrl.isEmpty() ) {
			m_logger.warn("Passed key is null in updateSiteIdToShortcutUrlOneMap for the Content object. ABORTED id = " + o.getId());
            return;
        }

        if (del) {
            // delete from PageIdShortcutUrlToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToShortcutUrlToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo != null ) {
                if (mapSiteIdTo.containsKey(keyShortcutUrl)){
                    mapSiteIdTo.remove(keyShortcutUrl);
                }
            }
            m_logger.debug("removed Content from m_SiteIdToShortcutUrlToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getShortcutUrl());
        }
        else {
            
            // add to SiteIdToShortcutUrlToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToShortcutUrlToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo == null ) {
                mapSiteIdTo = new TreeMap();
                m_SiteIdToShortcutUrlToOneMap.put(new Long(o.getSiteId()), mapSiteIdTo);
                if (m_debug) m_logger.debug("created new   mapSiteIdTo for " + o.getSiteId());
            }
            
			Content replaced = (Content) mapSiteIdTo.put(keyShortcutUrl,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdToShortcutUrlOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Content added to SiteIdToShortcutUrlToOneMap " + o.getId() + " to " + o.getSiteId());
        }
    }    




    public static void main(String[] args) throws Exception {

        ContentDS ds = new ContentDS();
        Content obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static Content createDefault(){

        Content ret = new Content();        
//      ret.setPageId("");           
//      ret.setSubPageId("");           
//      ret.setPanelId("");           
//      ret.setCategoryId("");           
//      ret.setContentSubject("");           
//      ret.setContent("");           
//      ret.setAuthorName("");           
//      ret.setCategory("");           
//      ret.setCreatedTime("");           
//      ret.setUpdatedTime("");           
//      ret.setContentType("");           
//      ret.setSourceName("");           
//      ret.setSourceUrl("");           
//      ret.setHide("");           
//      ret.setShowHome("");           
//      ret.setImageUrl("");           
//      ret.setImageHeight("");           
//      ret.setImageWidth("");           
//      ret.setInHtml("");           
//      ret.setTags("");           
//      ret.setExtraKeywords("");           
//      ret.setShortcutUrl("");           
//      ret.setColumnNum("");           
        return ret;
    }

    public static Content copy(Content org){

    	Content ret = new Content();

		ret.setPageId(org.getPageId());
		ret.setSubPageId(org.getSubPageId());
		ret.setPanelId(org.getPanelId());
		ret.setCategoryId(org.getCategoryId());
		ret.setContentSubject(org.getContentSubject());
		ret.setContent(org.getContent());
		ret.setAuthorName(org.getAuthorName());
		ret.setCategory(org.getCategory());
		ret.setCreatedTime(org.getCreatedTime());
		ret.setUpdatedTime(org.getUpdatedTime());
		ret.setContentType(org.getContentType());
		ret.setSourceName(org.getSourceName());
		ret.setSourceUrl(org.getSourceUrl());
		ret.setHide(org.getHide());
		ret.setShowHome(org.getShowHome());
		ret.setImageUrl(org.getImageUrl());
		ret.setImageHeight(org.getImageHeight());
		ret.setImageWidth(org.getImageWidth());
		ret.setInHtml(org.getInHtml());
		ret.setTags(org.getTags());
		ret.setExtraKeywords(org.getExtraKeywords());
		ret.setShortcutUrl(org.getShortcutUrl());
		ret.setColumnNum(org.getColumnNum());

        return ret;
    }

	public static void objectToLog(Content content, Logger logger){
		logger.debug("Content [" + content.getId() + "]" + objectToString(content));		
    }

	public static String objectToString(Content content){
		StringBuffer buf = new StringBuffer();
        buf.append("Content=");
		buf.append("Id=").append(content.getId()).append(", ");
		buf.append("SiteId=").append(content.getSiteId()).append(", ");
		buf.append("PageId=").append(content.getPageId()).append(", ");
		buf.append("Category=").append(content.getCategory()).append(", ");
		return buf.toString();    
    }
}
