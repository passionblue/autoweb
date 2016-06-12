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
import com.autosite.db.PageStaticAlt;
import com.jtrend.service.DomainStore;

import com.autosite.db.PageStaticAltDAO;

public class PageStaticAltDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PageAliasToMap;


	protected Map m_SiteIdPageAliasToOneMap;




    private static Logger m_logger = Logger.getLogger(PageStaticAltDS.class);
    private static PageStaticAltDS m_PageStaticAltDS = new PageStaticAltDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static PageStaticAltDS getInstance() {
        return m_PageStaticAltDS;
    }

    public static synchronized PageStaticAltDS getInstance(long id) {
        PageStaticAltDS ret = (PageStaticAltDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PageStaticAltDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PageStaticAltDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PageStaticAltDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("PageStaticAlt loaded from DB. num=" + m_idToMap.size());
        
    }

    protected PageStaticAltDS() {
        m_dao = new PageStaticAltDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageAliasToMap = new ConcurrentHashMap();
		m_SiteIdPageAliasToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PageStaticAltDS(long id) {
        m_dao = new PageStaticAltDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageAliasToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public PageStaticAlt getById(Long id) {
        return (PageStaticAlt) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        PageStaticAlt o = (PageStaticAlt)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PageStaticAlt removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("PageStaticAlt added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updatePageAliasMap(obj, del);
		updateSiteIdPageAliasOneMap(obj, del);
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
        PageStaticAlt o = (PageStaticAlt)obj;

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
            if (m_debug) m_logger.debug("PageStaticAlt removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("PageStaticAlt added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


	// ListStringKeys - PageAlias

    public List getByPageAlias(String keyPageAlias) {
        
        if (m_PageAliasToMap.containsKey(keyPageAlias)) {
            return new ArrayList( ((Map)m_PageAliasToMap.get(keyPageAlias)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updatePageAliasMap(Object obj, boolean del) {
        PageStaticAlt o = (PageStaticAlt)obj;

        if (del) {

            // delete from PageAliasToMap
            Map map  = (Map) m_PageAliasToMap.get(o.getPageAlias());
            if ( map != null ) {
                map.remove(new Long(o.getId()));
                if (m_debug) m_logger.debug("PageStaticAlt removed from PageAliasToMap" + o.getId() + " from [" + o.getPageAlias() + "]");
            } else {
                if (m_debug) m_logger.debug("PageStaticAlt NOT removed from PageAliasToMap because no map found for [" + o.getPageAlias() + "]");
            }
        }
        else {
            
            // add to PageAliasToMap
            Map map  = (Map) m_PageAliasToMap.get(o.getPageAlias());
            if ( map == null ) {
                map = new TreeMap();
                m_PageAliasToMap.put(o.getPageAlias(), map);
                if (m_debug) m_logger.debug("created new   PageAliasToMap for [" + o.getPageAlias() + "]");
            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("PageStaticAlt added to PageAliasToMap " + o.getId() + " to [" + o.getPageAlias() + "]");
        }
    }

    





        
    public  PageStaticAlt getBySiteIdPageAlias(long SiteId, String PageAlias) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdPageAliasToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdPageAliasToOneMap.get(keySiteId);

     	    String keyPageAlias =  PageAlias;
            if (  keyPageAlias == null || keyPageAlias.isEmpty()) return null;
            
            if ( mapSiteId.containsKey(keyPageAlias)){
                return ( PageStaticAlt)mapSiteId.get(keyPageAlias);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdPageAliasOneMap(Object obj, boolean del) {
        PageStaticAlt o = (PageStaticAlt)obj;

     	    String keyPageAlias =  o.getPageAlias();
            if ( keyPageAlias == null || keyPageAlias.isEmpty()) return;

        if (del) {
            // delete from SiteIdPageAliasToOneMap
            Map mapSiteId  = (Map) m_SiteIdPageAliasToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                if (mapSiteId.containsKey(keyPageAlias)){
                    mapSiteId.remove(keyPageAlias);
                }
            }
            m_logger.debug("removed PageStaticAlt from m_SiteIdPageAliasToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getPageAlias());
        }
        else {
            
            // add to SiteIdPageAliasToOneMap
            Map mapSiteId  = (Map) m_SiteIdPageAliasToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdPageAliasToOneMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new mapSiteId for " + o.getSiteId());
            }
            
            
			PageStaticAlt replaced = (PageStaticAlt) mapSiteId.put(keyPageAlias,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdPageAliasOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Panel added to SiteIdPageAliasToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        






    public static void main(String[] args) throws Exception {

        PageStaticAltDS ds = new PageStaticAltDS();
        PageStaticAlt obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static PageStaticAlt createDefault(){

        PageStaticAlt ret = new PageStaticAlt();        
//      ret.setPageAlias("");           
//      ret.setPageUrl("");           
//      ret.setMenuPage("");           
//      ret.setErrorPage("");           
//      ret.setLoginRequired("");           
//      ret.setViewProc("");           
//      ret.setDynamicContent("");           
//      ret.setHideMenu("");           
//      ret.setHideMid("");           
//      ret.setHideAd("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static PageStaticAlt copy(PageStaticAlt org){

    	PageStaticAlt ret = new PageStaticAlt();

		ret.setPageAlias(org.getPageAlias());
		ret.setPageUrl(org.getPageUrl());
		ret.setMenuPage(org.getMenuPage());
		ret.setErrorPage(org.getErrorPage());
		ret.setLoginRequired(org.getLoginRequired());
		ret.setViewProc(org.getViewProc());
		ret.setDynamicContent(org.getDynamicContent());
		ret.setHideMenu(org.getHideMenu());
		ret.setHideMid(org.getHideMid());
		ret.setHideAd(org.getHideAd());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(PageStaticAlt pageStaticAlt, Logger logger){
		logger.debug("PageStaticAlt [" + pageStaticAlt.getId() + "]" + objectToString(pageStaticAlt));		
    }

	public static String objectToString(PageStaticAlt pageStaticAlt){
		StringBuffer buf = new StringBuffer();
        buf.append("PageStaticAlt=");
		buf.append("Id=").append(pageStaticAlt.getId()).append(", ");
		buf.append("SiteId=").append(pageStaticAlt.getSiteId()).append(", ");
		buf.append("PageAlias=").append(pageStaticAlt.getPageAlias()).append(", ");
		buf.append("PageUrl=").append(pageStaticAlt.getPageUrl()).append(", ");
		buf.append("TimeCreated=").append(pageStaticAlt.getTimeCreated()).append(", ");
		return buf.toString();    
    }
}
