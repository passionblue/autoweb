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
import com.autosite.db.LinkStyleConfig;
import com.jtrend.service.DomainStore;

import com.autosite.db.LinkStyleConfigDAO;

public class LinkStyleConfigDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_StyleKeyToOneMap;





    protected Map m_SiteIdToStyleKeyToOneMap;


    private static Logger m_logger = Logger.getLogger(LinkStyleConfigDS.class);
    private static LinkStyleConfigDS m_LinkStyleConfigDS = new LinkStyleConfigDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static LinkStyleConfigDS getInstance() {
        return m_LinkStyleConfigDS;
    }

    public static synchronized LinkStyleConfigDS getInstance(long id) {
        LinkStyleConfigDS ret = (LinkStyleConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new LinkStyleConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_LinkStyleConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((LinkStyleConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("LinkStyleConfig loaded from DB. num=" + m_idToMap.size());
        
    }

    protected LinkStyleConfigDS() {
        m_dao = new LinkStyleConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_StyleKeyToOneMap = new ConcurrentHashMap();
        m_SiteIdToStyleKeyToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected LinkStyleConfigDS(long id) {
        m_dao = new LinkStyleConfigDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_StyleKeyToOneMap = new ConcurrentHashMap();
        m_SiteIdToStyleKeyToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public LinkStyleConfig getById(Long id) {
        return (LinkStyleConfig) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        LinkStyleConfig o = (LinkStyleConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("LinkStyleConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("LinkStyleConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateStyleKeyOneMap(obj, del);
        updateSiteIdToStyleKeyOneMap(obj, del);
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
        LinkStyleConfig o = (LinkStyleConfig)obj;

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
            if (m_debug) m_logger.debug("LinkStyleConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("LinkStyleConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneStringKey - StyleKey

    public LinkStyleConfig getObjectByStyleKey(String keyStyleKey) {
    	if (keyStyleKey == null) return null; 
        return (LinkStyleConfig)m_StyleKeyToOneMap.get(keyStyleKey);
    }

    private void updateStyleKeyOneMap(Object obj, boolean del) {
        LinkStyleConfig o = (LinkStyleConfig)obj;
        String _StyleKey =  o.getStyleKey();

		if (  _StyleKey == null || _StyleKey.isEmpty() ) return;

        if (del) {
            // delete from StyleKeyToMap

            if (m_StyleKeyToOneMap.containsKey(_StyleKey)){
                m_StyleKeyToOneMap.remove(_StyleKey);
                 if (m_debug) m_logger.debug("LinkStyleConfig removed from StyleKeyToMap" + o.getId() + " for [" + _StyleKey+ "]");
            } else {
                if (m_debug) m_logger.debug("LinkStyleConfig not removed from StyleKeyToMap" + o.getId() + " for [" + _StyleKey+ "]");
            } 
        }
        else {
            
            if (m_StyleKeyToOneMap.containsKey(_StyleKey)){
                if (m_debug) m_logger.debug("LinkStyleConfig repalced StyleKeyToMap" + o.getId() + " for [" + _StyleKey+ "]");
            } else {
                if (m_debug) m_logger.debug("LinkStyleConfig added to StyleKeyToMap" + o.getId() + " for [" + _StyleKey+ "]");
            } 
            m_StyleKeyToOneMap.put(_StyleKey, o);
        }
    }










	// listOneToOneStringToSiteIdKey - StyleKey

    public  LinkStyleConfig getBySiteIdToStyleKey(long siteId, String StyleKey) {

        Long keySiteIdTo  = new Long(siteId);
        if (m_SiteIdToStyleKeyToOneMap.containsKey(keySiteIdTo)) {
            
            Map mapSiteIdTo = (Map)m_SiteIdToStyleKeyToOneMap.get(keySiteIdTo);

     	    String keyStyleKey = StyleKey;
            
            if ( keyStyleKey!= null && mapSiteIdTo.containsKey(keyStyleKey)){
                return ( LinkStyleConfig)mapSiteIdTo.get(keyStyleKey);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    public List getByOrderStyleKey(){
        List ret = new ArrayList();
    
        List contentsByStyleKey = new ArrayList( m_SiteIdToStyleKeyToOneMap.values());
        Map map  = new TreeMap();
        
        for (Iterator iterator = contentsByStyleKey.iterator(); iterator.hasNext();) {
            LinkStyleConfig obj = (LinkStyleConfig) iterator.next();
            if ( obj.getStyleKey() == null ) continue;
            map.put(obj.getStyleKey(), obj);
        }
        
        return new ArrayList(map.values());
    }

    private void updateSiteIdToStyleKeyOneMap(Object obj, boolean del) {
        LinkStyleConfig o = (LinkStyleConfig)obj;

   	    String keyStyleKey = o.getStyleKey();

		if ( keyStyleKey == null || keyStyleKey.isEmpty() ) {
			m_logger.warn("Passed key is null in updateSiteIdToStyleKeyOneMap for the LinkStyleConfig object. ABORTED id = " + o.getId());
            return;
        }

        if (del) {
            // delete from PollIdStyleKeyToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToStyleKeyToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo != null ) {
                if (mapSiteIdTo.containsKey(keyStyleKey)){
                    mapSiteIdTo.remove(keyStyleKey);
                }
            }
            m_logger.debug("removed LinkStyleConfig from m_SiteIdToStyleKeyToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getStyleKey());
        }
        else {
            
            // add to SiteIdToStyleKeyToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToStyleKeyToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo == null ) {
                mapSiteIdTo = new TreeMap();
                m_SiteIdToStyleKeyToOneMap.put(new Long(o.getSiteId()), mapSiteIdTo);
                if (m_debug) m_logger.debug("created new   mapSiteIdTo for " + o.getSiteId());
            }
            
			LinkStyleConfig replaced = (LinkStyleConfig) mapSiteIdTo.put(keyStyleKey,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdToStyleKeyOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("LinkStyleConfig added to SiteIdToStyleKeyToOneMap " + o.getId() + " to " + o.getSiteId());
        }
    }    




    public static void main(String[] args) throws Exception {

        LinkStyleConfigDS ds = new LinkStyleConfigDS();
        LinkStyleConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static LinkStyleConfig createDefault(){

        LinkStyleConfig ret = new LinkStyleConfig();        
//      ret.setStyleKey("");           
//      ret.setHeight("");           
//      ret.setWidth("");           
//      ret.setDisplay("");           
//      ret.setBorder("");           
//      ret.setBackground("");           
//      ret.setColor("");           
//      ret.setTextDecoration("");           
//      ret.setTextAlign("");           
//      ret.setVerticalAlign("");           
//      ret.setTextIndent("");           
//      ret.setMargin("");           
//      ret.setPadding("");           
//      ret.setExtraStyle("");           
//      ret.setHovHeight("");           
//      ret.setHovWidth("");           
//      ret.setHovDisplay("");           
//      ret.setHovBorder("");           
//      ret.setHovBackground("");           
//      ret.setHovColor("");           
//      ret.setHovTextDecoration("");           
//      ret.setHovTextAlign("");           
//      ret.setHovVerticalAlign("");           
//      ret.setHovTextIndent("");           
//      ret.setHovMargin("");           
//      ret.setHovPadding("");           
//      ret.setHovExtraStyle("");           
//      ret.setActiveUse("");           
//      ret.setActiveBorder("");           
//      ret.setActiveBackground("");           
//      ret.setActiveColor("");           
//      ret.setActiveTextDecoration("");           
//      ret.setActiveExtraStyle("");           
//      ret.setActiveMargin("");           
//      ret.setActivePadding("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static LinkStyleConfig copy(LinkStyleConfig org){

    	LinkStyleConfig ret = new LinkStyleConfig();

		ret.setStyleKey(org.getStyleKey());
		ret.setHeight(org.getHeight());
		ret.setWidth(org.getWidth());
		ret.setDisplay(org.getDisplay());
		ret.setBorder(org.getBorder());
		ret.setBackground(org.getBackground());
		ret.setColor(org.getColor());
		ret.setTextDecoration(org.getTextDecoration());
		ret.setTextAlign(org.getTextAlign());
		ret.setVerticalAlign(org.getVerticalAlign());
		ret.setTextIndent(org.getTextIndent());
		ret.setMargin(org.getMargin());
		ret.setPadding(org.getPadding());
		ret.setExtraStyle(org.getExtraStyle());
		ret.setHovHeight(org.getHovHeight());
		ret.setHovWidth(org.getHovWidth());
		ret.setHovDisplay(org.getHovDisplay());
		ret.setHovBorder(org.getHovBorder());
		ret.setHovBackground(org.getHovBackground());
		ret.setHovColor(org.getHovColor());
		ret.setHovTextDecoration(org.getHovTextDecoration());
		ret.setHovTextAlign(org.getHovTextAlign());
		ret.setHovVerticalAlign(org.getHovVerticalAlign());
		ret.setHovTextIndent(org.getHovTextIndent());
		ret.setHovMargin(org.getHovMargin());
		ret.setHovPadding(org.getHovPadding());
		ret.setHovExtraStyle(org.getHovExtraStyle());
		ret.setActiveUse(org.getActiveUse());
		ret.setActiveBorder(org.getActiveBorder());
		ret.setActiveBackground(org.getActiveBackground());
		ret.setActiveColor(org.getActiveColor());
		ret.setActiveTextDecoration(org.getActiveTextDecoration());
		ret.setActiveExtraStyle(org.getActiveExtraStyle());
		ret.setActiveMargin(org.getActiveMargin());
		ret.setActivePadding(org.getActivePadding());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(LinkStyleConfig linkStyleConfig, Logger logger){
		logger.debug("LinkStyleConfig [" + linkStyleConfig.getId() + "]" + objectToString(linkStyleConfig));		
    }

	public static String objectToString(LinkStyleConfig linkStyleConfig){
		StringBuffer buf = new StringBuffer();
        buf.append("LinkStyleConfig=");
		buf.append("Id=").append(linkStyleConfig.getId()).append(", ");
		buf.append("SiteId=").append(linkStyleConfig.getSiteId()).append(", ");
		buf.append("StyleKey=").append(linkStyleConfig.getStyleKey()).append(", ");
		return buf.toString();    
    }
}
