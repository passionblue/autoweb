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
import com.autosite.db.PageConfig;
import com.jtrend.service.DomainStore;

import com.autosite.db.PageConfigDAO;

public class PageConfigDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PageIdToOneMap;







    private static Logger m_logger = Logger.getLogger(PageConfigDS.class);
    private static PageConfigDS m_PageConfigDS = new PageConfigDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static PageConfigDS getInstance() {
        return m_PageConfigDS;
    }

    public static synchronized PageConfigDS getInstance(long id) {
        PageConfigDS ret = (PageConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PageConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PageConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PageConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("PageConfig loaded from DB. num=" + m_idToMap.size());
        
    }

    protected PageConfigDS() {
        m_dao = new PageConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PageConfigDS(long id) {
        m_dao = new PageConfigDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public PageConfig getById(Long id) {
        return (PageConfig) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        PageConfig o = (PageConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PageConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("PageConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updatePageIdOneMap(obj, del);
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
        PageConfig o = (PageConfig)obj;

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
            if (m_debug) m_logger.debug("PageConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("PageConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneIdKey - PageId

    public PageConfig getObjectByPageId(long keyPageId) {
        return (PageConfig)m_PageIdToOneMap.get(new Long(keyPageId));
    }

    private void updatePageIdOneMap(Object obj, boolean del) {
        PageConfig o = (PageConfig)obj;
        Long _PageId = new Long(o.getPageId());

        if (del) {
            // delete from PageIdToOneMap

            if (m_PageIdToOneMap.containsKey(_PageId)){
                m_PageIdToOneMap.remove(_PageId);
                if (m_debug) m_logger.debug("PageConfig removed from PageIdToMap" + o.getId() + " for [" + _PageId+ "]");
            } else {
                if (m_debug) m_logger.debug("PageConfig not removed from PageIdToMap" + o.getId() + " for [" + _PageId+ "]. Does not exist");
            } 
        }
        else {
            if (m_PageIdToOneMap.containsKey(_PageId)){
                if (m_debug) m_logger.debug("PageConfig repalced PageIdToMap" + o.getId() + " for [" + _PageId+ "]");
            } else {
                if (m_debug) m_logger.debug("PageConfig added to PageIdToMap" + o.getId() + " for [" + _PageId+ "]");
            } 
            m_PageIdToOneMap.put(_PageId, o);
        }
    }













    public static void main(String[] args) throws Exception {

        PageConfigDS ds = new PageConfigDS();
        PageConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static PageConfig createDefault(){

        PageConfig ret = new PageConfig();        
//      ret.setPageId("");           
//      ret.setSortType("");           
//      ret.setArrangeType("");           
//      ret.setPageCss("");           
//      ret.setPageScript("");           
//      ret.setPageCssImports("");           
//      ret.setPageScriptImports("");           
//      ret.setHideMenu("");           
//      ret.setHideMid("");           
//      ret.setHideAd("");           
//      ret.setStyleId("");           
//      ret.setHeaderMeta("");           
//      ret.setHeaderLink("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static PageConfig copy(PageConfig org){

    	PageConfig ret = new PageConfig();

		ret.setPageId(org.getPageId());
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
		ret.setHeaderMeta(org.getHeaderMeta());
		ret.setHeaderLink(org.getHeaderLink());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(PageConfig pageConfig, Logger logger){
		logger.debug("PageConfig [" + pageConfig.getId() + "]" + objectToString(pageConfig));		
    }

	public static String objectToString(PageConfig pageConfig){
		StringBuffer buf = new StringBuffer();
        buf.append("PageConfig=");
		buf.append("Id=").append(pageConfig.getId()).append(", ");
		buf.append("SiteId=").append(pageConfig.getSiteId()).append(", ");
		buf.append("PageId=").append(pageConfig.getPageId()).append(", ");
		return buf.toString();    
    }
}
