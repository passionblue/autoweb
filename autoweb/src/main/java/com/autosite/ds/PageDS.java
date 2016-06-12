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
import com.autosite.db.Page;
import com.jtrend.service.DomainStore;

import com.autosite.db.PageDAO;

public class PageDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_ParentIdToMap;
    protected Map m_MenuPanelIdToMap;
    protected Map m_SiteIdToOneMap;


	protected Map m_SiteIdPageNameToOneMap;

    private static Logger m_logger = Logger.getLogger(PageDS.class);
    private static PageDS m_PageDS = new PageDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static PageDS getInstance() {
        return m_PageDS;
    }

    public static synchronized PageDS getInstance(long id) {
        PageDS ret = (PageDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PageDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PageDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PageDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected PageDS() {
        m_dao = new PageDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_ParentIdToMap = new ConcurrentHashMap();
        m_MenuPanelIdToMap = new ConcurrentHashMap();
        m_SiteIdToOneMap = new ConcurrentHashMap();
		m_SiteIdPageNameToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PageDS(long id) {
        m_dao = new PageDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_ParentIdToMap = new ConcurrentHashMap();
        m_MenuPanelIdToMap = new ConcurrentHashMap();
        m_SiteIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public Page getById(Long id) {
        return (Page) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        Page o = (Page)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("Page removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("Page added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateParentIdMap(obj, del);
        updateMenuPanelIdMap(obj, del);
        updateSiteIdOneMap(obj, del);


		updateSiteIdPageNameOneMap(obj, del);

    }


    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        Page o = (Page)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("Page removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("Page added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }

    public List getByParentId(long ParentId) {
        
        Long _ParentId  = new Long(ParentId);
        if (m_ParentIdToMap.containsKey(_ParentId)) {
            return new ArrayList( ((Map)m_ParentIdToMap.get(_ParentId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateParentIdMap(Object obj, boolean del) {
        Page o = (Page)obj;

        if (del) {

            // delete from ParentIdToMap
            Map map  = (Map) m_ParentIdToMap.get(new Long(o.getParentId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("Page removed from ParentIdToMap" + o.getId() + " from " + o.getParentId());
        }
        else {
            
            // add to ParentIdToMap
            Map map  = (Map) m_ParentIdToMap.get(new Long(o.getParentId()));
            if ( map == null ) {
                map = new TreeMap();
                m_ParentIdToMap.put(new Long(o.getParentId()), map);
                if (m_debug) m_logger.debug("created new   ParentIdToMap for " + o.getParentId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("Page added to ParentIdToMap " + o.getId() + " to " + o.getParentId());
        }
    }

    public List getByMenuPanelId(long MenuPanelId) {
        
        Long _MenuPanelId  = new Long(MenuPanelId);
        if (m_MenuPanelIdToMap.containsKey(_MenuPanelId)) {
            return new ArrayList( ((Map)m_MenuPanelIdToMap.get(_MenuPanelId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateMenuPanelIdMap(Object obj, boolean del) {
        Page o = (Page)obj;

        if (del) {

            // delete from MenuPanelIdToMap
            Map map  = (Map) m_MenuPanelIdToMap.get(new Long(o.getMenuPanelId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("Page removed from MenuPanelIdToMap" + o.getId() + " from " + o.getMenuPanelId());
        }
        else {
            
            // add to MenuPanelIdToMap
            Map map  = (Map) m_MenuPanelIdToMap.get(new Long(o.getMenuPanelId()));
            if ( map == null ) {
                map = new TreeMap();
                m_MenuPanelIdToMap.put(new Long(o.getMenuPanelId()), map);
                if (m_debug) m_logger.debug("created new   MenuPanelIdToMap for " + o.getMenuPanelId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("Page added to MenuPanelIdToMap " + o.getId() + " to " + o.getMenuPanelId());
        }
    }


    


    public Page getObjectBySiteId(long keySiteId) {
        return (Page)m_SiteIdToOneMap.get(new Long(keySiteId));
    }

    private void updateSiteIdOneMap(Object obj, boolean del) {
        Page o = (Page)obj;
        Long _SiteId = new Long(o.getSiteId());

        if (del) {
            // delete from SiteIdToOneMap

            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                m_SiteIdToOneMap.remove(_SiteId);
                if (m_debug) m_logger.debug("Page removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("Page not removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]. Does not exist");
            } 
        }
        else {
            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                if (m_debug) m_logger.debug("Page repalced SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("Page added to SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } 
            m_SiteIdToOneMap.put(_SiteId, o);
        }
    }




        
    public  Page getBySiteIdPageName(long SiteId, String PageName) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdPageNameToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdPageNameToOneMap.get(keySiteId);

     	    String keyPageName =  PageName;
            if (  keyPageName == null) return null;
            
            if ( mapSiteId.containsKey(keyPageName)){
                return ( Page)mapSiteId.get(keyPageName);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdPageNameOneMap(Object obj, boolean del) {
        Page o = (Page)obj;

     	    String keyPageName =  o.getPageName();
            if ( keyPageName == null) return;

        if (del) {
            // delete from SiteIdPageNameToOneMap
            Map mapSiteId  = (Map) m_SiteIdPageNameToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                if (mapSiteId.containsKey(keyPageName)){
                    mapSiteId.remove(keyPageName);
                }
            }
            m_logger.debug("removed from m_SiteIdPageNameToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getPageName());
        }
        else {
            
            // add to SiteIdPageNameToOneMap
            Map mapSiteId  = (Map) m_SiteIdPageNameToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdPageNameToOneMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            
            
			Page replaced = (Page) mapSiteId.put(keyPageName,o);			
            if ( replaced != null){
                m_logger.debug("**WARNING**: existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            m_logger.debug("Panel added to SiteIdPageNameToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        




    public static void main(String[] args) throws Exception {

        PageDS ds = new PageDS();
        Page obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static Page createDefault(){

        Page ret = new Page();        
//      ret.setMenuPanelId("");           
//      ret.setParentId("");           
//      ret.setPageName("");           
//      ret.setPageMenuTitle("");           
//      ret.setHide("");           
//      ret.setCreatedTime("");           
//      ret.setSiteUrl("");           
//      ret.setPageColCount("");           
//      ret.setPageKeywords("");           
//      ret.setPageViewType("");           
//      ret.setUnderlyingPage("");           
//      ret.setHeaderPage("");           
//      ret.setShowPageExclusiveOnly("");           
//      ret.setAlt1("");           
//      ret.setAlt2("");           
//      ret.setAlt3("");           
        return ret;
    }

    public static Page copy(Page org){

    	Page ret = new Page();

		ret.setMenuPanelId(org.getMenuPanelId());
		ret.setParentId(org.getParentId());
		ret.setPageName(org.getPageName());
		ret.setPageMenuTitle(org.getPageMenuTitle());
		ret.setHide(org.getHide());
		ret.setCreatedTime(org.getCreatedTime());
		ret.setSiteUrl(org.getSiteUrl());
		ret.setPageColCount(org.getPageColCount());
		ret.setPageKeywords(org.getPageKeywords());
		ret.setPageViewType(org.getPageViewType());
		ret.setUnderlyingPage(org.getUnderlyingPage());
		ret.setHeaderPage(org.getHeaderPage());
		ret.setShowPageExclusiveOnly(org.getShowPageExclusiveOnly());
		ret.setAlt1(org.getAlt1());
		ret.setAlt2(org.getAlt2());
		ret.setAlt3(org.getAlt3());

        return ret;
    }

	public static void objectToLog(Page page, Logger logger){
		logger.debug("Page [" + page.getId() + "]" + objectToString(page));		
    }

	public static String objectToString(Page page){
		StringBuffer buf = new StringBuffer();
        buf.append("Page=");
		buf.append("Id=").append(page.getId()).append(", ");
		buf.append("SiteId=").append(page.getSiteId()).append(", ");
		buf.append("MenuPanelId=").append(page.getMenuPanelId()).append(", ");
		buf.append("ParentId=").append(page.getParentId()).append(", ");
		buf.append("PageName=").append(page.getPageName()).append(", ");
		buf.append("PageMenuTitle=").append(page.getPageMenuTitle()).append(", ");
		buf.append("HeaderPage=").append(page.getHeaderPage()).append(", ");
		return buf.toString();    
    }
}
