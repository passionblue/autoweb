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
import com.autosite.db.PageContentConfig;
import com.jtrend.service.DomainStore;

import com.autosite.db.PageContentConfigDAO;

public class PageContentConfigDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_ContentIdToOneMap;


	protected Map m_ContentTypeContentIdToOneMap;





    private static Logger m_logger = Logger.getLogger(PageContentConfigDS.class);
    private static PageContentConfigDS m_PageContentConfigDS = new PageContentConfigDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static PageContentConfigDS getInstance() {
        return m_PageContentConfigDS;
    }

    public static synchronized PageContentConfigDS getInstance(long id) {
        PageContentConfigDS ret = (PageContentConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PageContentConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PageContentConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PageContentConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("PageContentConfig loaded from DB. num=" + m_idToMap.size());
        
    }

    protected PageContentConfigDS() {
        m_dao = new PageContentConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_ContentIdToOneMap = new ConcurrentHashMap();
		m_ContentTypeContentIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PageContentConfigDS(long id) {
        m_dao = new PageContentConfigDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_ContentIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public PageContentConfig getById(Long id) {
        return (PageContentConfig) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        PageContentConfig o = (PageContentConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PageContentConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("PageContentConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateContentIdOneMap(obj, del);
		updateContentTypeContentIdOneMap(obj, del);
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
        PageContentConfig o = (PageContentConfig)obj;

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
            if (m_debug) m_logger.debug("PageContentConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("PageContentConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneIdKey - ContentId

    public PageContentConfig getObjectByContentId(long keyContentId) {
        return (PageContentConfig)m_ContentIdToOneMap.get(new Long(keyContentId));
    }

    private void updateContentIdOneMap(Object obj, boolean del) {
        PageContentConfig o = (PageContentConfig)obj;
        Long _ContentId = new Long(o.getContentId());

        if (del) {
            // delete from ContentIdToOneMap

            if (m_ContentIdToOneMap.containsKey(_ContentId)){
                m_ContentIdToOneMap.remove(_ContentId);
                if (m_debug) m_logger.debug("PageContentConfig removed from ContentIdToMap" + o.getId() + " for [" + _ContentId+ "]");
            } else {
                if (m_debug) m_logger.debug("PageContentConfig not removed from ContentIdToMap" + o.getId() + " for [" + _ContentId+ "]. Does not exist");
            } 
        }
        else {
            if (m_ContentIdToOneMap.containsKey(_ContentId)){
                if (m_debug) m_logger.debug("PageContentConfig repalced ContentIdToMap" + o.getId() + " for [" + _ContentId+ "]");
            } else {
                if (m_debug) m_logger.debug("PageContentConfig added to ContentIdToMap" + o.getId() + " for [" + _ContentId+ "]");
            } 
            m_ContentIdToOneMap.put(_ContentId, o);
        }
    }



        
    public  PageContentConfig getByContentTypeContentId(int ContentType, long ContentId) {

     	    Integer keyContentType = new Integer(ContentType);

        if (m_ContentTypeContentIdToOneMap.containsKey(keyContentType)) {
            
            Map mapContentType = (Map)m_ContentTypeContentIdToOneMap.get(keyContentType);

     	    Long keyContentId = new Long(ContentId);
            
            if ( mapContentType.containsKey(keyContentId)){
                return ( PageContentConfig)mapContentType.get(keyContentId);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateContentTypeContentIdOneMap(Object obj, boolean del) {
        PageContentConfig o = (PageContentConfig)obj;

     	    Integer keyContentType = new Integer(o.getContentType());


     	    Long keyContentId = new Long(o.getContentId());

        if (del) {
            // delete from ContentTypeContentIdToOneMap
            Map mapContentType  = (Map) m_ContentTypeContentIdToOneMap.get(keyContentType);
            if ( mapContentType != null ) {
                if (mapContentType.containsKey(keyContentId)){
                    mapContentType.remove(keyContentId);
                }
            }
            m_logger.debug("removed PageContentConfig from m_ContentTypeContentIdToOneMap" + o.getId() + " from " + o.getContentType() + " # " + o.getContentId());
        }
        else {
            
            // add to ContentTypeContentIdToOneMap
            Map mapContentType  = (Map) m_ContentTypeContentIdToOneMap.get(keyContentType);
            if ( mapContentType == null ) {
                mapContentType = new TreeMap();
                m_ContentTypeContentIdToOneMap.put(keyContentType, mapContentType);
                if (m_debug) m_logger.debug("created new mapContentType for " + o.getContentType());
            }
            
            
			PageContentConfig replaced = (PageContentConfig) mapContentType.put(keyContentId,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: ContentTypeContentIdOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Panel added to ContentTypeContentIdToOneMap " + o.getId() + " to " + o.getContentType());
        }
        
    }    
        









    public static void main(String[] args) throws Exception {

        PageContentConfigDS ds = new PageContentConfigDS();
        PageContentConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static PageContentConfig createDefault(){

        PageContentConfig _PageContentConfig = new PageContentConfig();        
	    _PageContentConfig = new PageContentConfig();// PageContentConfigDS.getInstance().getDeafult();
        return _PageContentConfig;
    }

    public static PageContentConfig copy(PageContentConfig org){

    	PageContentConfig ret = new PageContentConfig();

		ret.setContentId(org.getContentId());
		ret.setContentType(org.getContentType());
		ret.setSortType(org.getSortType());
		ret.setArrangeType(org.getArrangeType());
		ret.setPageCss(org.getPageCss());
		ret.setPageScript(org.getPageScript());
		ret.setPageCssImports(org.getPageCssImports());
		ret.setPageScriptImports(org.getPageScriptImports());
		ret.setHideMenu(org.getHideMenu());
		ret.setHideMid(org.getHideMid());
		ret.setHideAd(org.getHideAd());
		ret.setStyleId(org.getStyleId());
		ret.setContentStyleSetId(org.getContentStyleSetId());
		ret.setHeaderMeta(org.getHeaderMeta());
		ret.setHeaderLink(org.getHeaderLink());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(PageContentConfig pageContentConfig, Logger logger){
		logger.debug("PageContentConfig [" + pageContentConfig.getId() + "]" + objectToString(pageContentConfig));		
    }

	public static String objectToString(PageContentConfig pageContentConfig){
		StringBuffer buf = new StringBuffer();
        buf.append("PageContentConfig=");
		buf.append("Id=").append(pageContentConfig.getId()).append(", ");
		buf.append("SiteId=").append(pageContentConfig.getSiteId()).append(", ");
		return buf.toString();    
    }
}
