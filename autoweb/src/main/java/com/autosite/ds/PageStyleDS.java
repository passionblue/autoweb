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
import com.autosite.db.PageStyle;
import com.jtrend.service.DomainStore;

import com.autosite.db.PageStyleDAO;

public class PageStyleDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PageIdToOneMap;



    protected Map m_SiteIdToStyleIdToMap;




    private static Logger m_logger = Logger.getLogger(PageStyleDS.class);
    private static PageStyleDS m_PageStyleDS = new PageStyleDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static PageStyleDS getInstance() {
        return m_PageStyleDS;
    }

    public static synchronized PageStyleDS getInstance(long id) {
        PageStyleDS ret = (PageStyleDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PageStyleDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PageStyleDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PageStyleDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("PageStyle loaded from DB. num=" + m_idToMap.size());
        
    }

    protected PageStyleDS() {
        m_dao = new PageStyleDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToOneMap = new ConcurrentHashMap();
        m_SiteIdToStyleIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PageStyleDS(long id) {
        m_dao = new PageStyleDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToOneMap = new ConcurrentHashMap();
        m_SiteIdToStyleIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public PageStyle getById(Long id) {
        return (PageStyle) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        PageStyle o = (PageStyle)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PageStyle removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("PageStyle added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updatePageIdOneMap(obj, del);
        updateSiteIdToStyleIdMap(obj, del);
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
        PageStyle o = (PageStyle)obj;

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
            if (m_debug) m_logger.debug("PageStyle removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("PageStyle added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneIdKey - PageId

    public PageStyle getObjectByPageId(long keyPageId) {
        return (PageStyle)m_PageIdToOneMap.get(new Long(keyPageId));
    }

    private void updatePageIdOneMap(Object obj, boolean del) {
        PageStyle o = (PageStyle)obj;
        Long _PageId = new Long(o.getPageId());

        if (del) {
            // delete from PageIdToOneMap

            if (m_PageIdToOneMap.containsKey(_PageId)){
                m_PageIdToOneMap.remove(_PageId);
                if (m_debug) m_logger.debug("PageStyle removed from PageIdToMap" + o.getId() + " for [" + _PageId+ "]");
            } else {
                if (m_debug) m_logger.debug("PageStyle not removed from PageIdToMap" + o.getId() + " for [" + _PageId+ "]. Does not exist");
            } 
        }
        else {
            if (m_PageIdToOneMap.containsKey(_PageId)){
                if (m_debug) m_logger.debug("PageStyle repalced PageIdToMap" + o.getId() + " for [" + _PageId+ "]");
            } else {
                if (m_debug) m_logger.debug("PageStyle added to PageIdToMap" + o.getId() + " for [" + _PageId+ "]");
            } 
            m_PageIdToOneMap.put(_PageId, o);
        }
    }





    public List getBySiteIdToStyleIdList (long SiteId, long StyleId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToStyleIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToStyleIdToMap.get(keySiteId);

     	    Long keyStyleId = StyleId;
            
            if ( mapSiteId.containsKey(keyStyleId)){
                return new ArrayList( ((Map)mapSiteId.get(keyStyleId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdStyleId(long SiteId, long StyleId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToStyleIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToStyleIdToMap.get(keySiteId);

     	    Long keyStyleId = new Long(StyleId);
            
            if ( mapSiteId.containsKey(keyStyleId)){
                return (Map)mapSiteId.get(keyStyleId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToStyleIdMap(Object obj, boolean del) {
        PageStyle o = (PageStyle)obj;

   	    Long keyStyleId = new Long(o.getStyleId());

        if (del) {
            // delete from SiteIdStyleIdToMap
            Map mapSiteId  = (Map) m_SiteIdToStyleIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapStyleId = (Map) mapSiteId.get(keyStyleId);
                if (mapStyleId != null){
                    mapStyleId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed PageStyle from m_SiteIdToStyleIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getStyleId());
        }
        else {
            
            // add to SiteIdStyleIdToMap
            Map mapSiteId  = (Map) m_SiteIdToStyleIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToStyleIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapStyleId = (Map) mapSiteId.get(keyStyleId);
            
            if ( mapStyleId == null) {
                mapStyleId = new TreeMap();
                mapSiteId.put(keyStyleId, mapStyleId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapStyleId.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdStyleIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    








    public static void main(String[] args) throws Exception {

        PageStyleDS ds = new PageStyleDS();
        PageStyle obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static PageStyle createDefault(){

        PageStyle ret = new PageStyle();        
//      ret.setPageId("");           
//      ret.setStyleId("");           
        return ret;
    }

    public static PageStyle copy(PageStyle org){

    	PageStyle ret = new PageStyle();

		ret.setPageId(org.getPageId());
		ret.setStyleId(org.getStyleId());

        return ret;
    }

	public static void objectToLog(PageStyle pageStyle, Logger logger){
		logger.debug("PageStyle [" + pageStyle.getId() + "]" + objectToString(pageStyle));		
    }

	public static String objectToString(PageStyle pageStyle){
		StringBuffer buf = new StringBuffer();
        buf.append("PageStyle=");
		buf.append("Id=").append(pageStyle.getId()).append(", ");
		buf.append("SiteId=").append(pageStyle.getSiteId()).append(", ");
		buf.append("PageId=").append(pageStyle.getPageId()).append(", ");
		buf.append("StyleId=").append(pageStyle.getStyleId()).append(", ");
		return buf.toString();    
    }
}
