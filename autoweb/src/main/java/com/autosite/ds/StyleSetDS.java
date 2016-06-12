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
import com.autosite.db.StyleSet;
import com.jtrend.service.DomainStore;

import com.autosite.db.StyleSetDAO;

public class StyleSetDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_StyleIdToMap;
    protected Map m_LinkStyleIdToMap;





    protected Map m_SiteIdToNameToOneMap;


    private static Logger m_logger = Logger.getLogger(StyleSetDS.class);
    private static StyleSetDS m_StyleSetDS = new StyleSetDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static StyleSetDS getInstance() {
        return m_StyleSetDS;
    }

    public static synchronized StyleSetDS getInstance(long id) {
        StyleSetDS ret = (StyleSetDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new StyleSetDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_StyleSetDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((StyleSetDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("StyleSet loaded from DB. num=" + m_idToMap.size());
        
    }

    protected StyleSetDS() {
        m_dao = new StyleSetDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_StyleIdToMap = new ConcurrentHashMap();
        m_LinkStyleIdToMap = new ConcurrentHashMap();
        m_SiteIdToNameToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected StyleSetDS(long id) {
        m_dao = new StyleSetDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_StyleIdToMap = new ConcurrentHashMap();
        m_LinkStyleIdToMap = new ConcurrentHashMap();
        m_SiteIdToNameToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public StyleSet getById(Long id) {
        return (StyleSet) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        StyleSet o = (StyleSet)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("StyleSet removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("StyleSet added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateStyleIdMap(obj, del);
        updateLinkStyleIdMap(obj, del);
        updateSiteIdToNameOneMap(obj, del);
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
        StyleSet o = (StyleSet)obj;

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
            if (m_debug) m_logger.debug("StyleSet removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("StyleSet added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }
	// ListKeys - StyleId
    public List getByStyleId(long StyleId) {
        
        Long _StyleId  = new Long(StyleId);
        if (m_StyleIdToMap.containsKey(_StyleId)) {
            return new ArrayList( ((Map)m_StyleIdToMap.get(_StyleId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateStyleIdMap(Object obj, boolean del) {
        StyleSet o = (StyleSet)obj;

		if ( o.getStyleId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from StyleIdToMap
            Map map  = (Map) m_StyleIdToMap.get(new Long(o.getStyleId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("StyleSet removed from StyleIdToMap" + o.getId() + " from " + o.getStyleId());
        }
        else {
            
            // add to StyleIdToMap
            Map map  = (Map) m_StyleIdToMap.get(new Long(o.getStyleId()));
            if ( map == null ) {
                map = new TreeMap();
                m_StyleIdToMap.put(new Long(o.getStyleId()), map);
                if (m_debug) m_logger.debug("created new   StyleIdToMap for " + o.getStyleId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("StyleSet added to StyleIdToMap " + o.getId() + " to " + o.getStyleId());
        }
    }
	// ListKeys - LinkStyleId
    public List getByLinkStyleId(long LinkStyleId) {
        
        Long _LinkStyleId  = new Long(LinkStyleId);
        if (m_LinkStyleIdToMap.containsKey(_LinkStyleId)) {
            return new ArrayList( ((Map)m_LinkStyleIdToMap.get(_LinkStyleId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateLinkStyleIdMap(Object obj, boolean del) {
        StyleSet o = (StyleSet)obj;

		if ( o.getLinkStyleId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from LinkStyleIdToMap
            Map map  = (Map) m_LinkStyleIdToMap.get(new Long(o.getLinkStyleId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("StyleSet removed from LinkStyleIdToMap" + o.getId() + " from " + o.getLinkStyleId());
        }
        else {
            
            // add to LinkStyleIdToMap
            Map map  = (Map) m_LinkStyleIdToMap.get(new Long(o.getLinkStyleId()));
            if ( map == null ) {
                map = new TreeMap();
                m_LinkStyleIdToMap.put(new Long(o.getLinkStyleId()), map);
                if (m_debug) m_logger.debug("created new   LinkStyleIdToMap for " + o.getLinkStyleId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("StyleSet added to LinkStyleIdToMap " + o.getId() + " to " + o.getLinkStyleId());
        }
    }


    










	// listOneToOneStringToSiteIdKey - Name

    public  StyleSet getBySiteIdToName(long siteId, String Name) {

        Long keySiteIdTo  = new Long(siteId);
        if (m_SiteIdToNameToOneMap.containsKey(keySiteIdTo)) {
            
            Map mapSiteIdTo = (Map)m_SiteIdToNameToOneMap.get(keySiteIdTo);

     	    String keyName = Name;
            
            if ( keyName!= null && mapSiteIdTo.containsKey(keyName)){
                return ( StyleSet)mapSiteIdTo.get(keyName);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    public List getByOrderName(){
        List ret = new ArrayList();
    
        List contentsByName = new ArrayList( m_SiteIdToNameToOneMap.values());
        Map map  = new TreeMap();
        
        for (Iterator iterator = contentsByName.iterator(); iterator.hasNext();) {
            StyleSet obj = (StyleSet) iterator.next();
            if ( obj.getName() == null ) continue;
            map.put(obj.getName(), obj);
        }
        
        return new ArrayList(map.values());
    }

    private void updateSiteIdToNameOneMap(Object obj, boolean del) {
        StyleSet o = (StyleSet)obj;

   	    String keyName = o.getName();

		if ( keyName == null || keyName.isEmpty() ) {
			m_logger.warn("Passed key is null in updateSiteIdToNameOneMap for the StyleSet object. ABORTED id = " + o.getId());
            return;
        }

        if (del) {
            // delete from PollIdNameToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToNameToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo != null ) {
                if (mapSiteIdTo.containsKey(keyName)){
                    mapSiteIdTo.remove(keyName);
                }
            }
            m_logger.debug("removed StyleSet from m_SiteIdToNameToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getName());
        }
        else {
            
            // add to SiteIdToNameToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToNameToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo == null ) {
                mapSiteIdTo = new TreeMap();
                m_SiteIdToNameToOneMap.put(new Long(o.getSiteId()), mapSiteIdTo);
                if (m_debug) m_logger.debug("created new   mapSiteIdTo for " + o.getSiteId());
            }
            
			StyleSet replaced = (StyleSet) mapSiteIdTo.put(keyName,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdToNameOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("StyleSet added to SiteIdToNameToOneMap " + o.getId() + " to " + o.getSiteId());
        }
    }    




    public static void main(String[] args) throws Exception {

        StyleSetDS ds = new StyleSetDS();
        StyleSet obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static StyleSet createDefault(){

        StyleSet ret = new StyleSet();        
//      ret.setName("");           
//      ret.setStyleId("");           
//      ret.setDataStyleId("");           
//      ret.setLinkStyleId("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static StyleSet copy(StyleSet org){

    	StyleSet ret = new StyleSet();

		ret.setName(org.getName());
		ret.setStyleId(org.getStyleId());
		ret.setDataStyleId(org.getDataStyleId());
		ret.setLinkStyleId(org.getLinkStyleId());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(StyleSet styleSet, Logger logger){
		logger.debug("StyleSet [" + styleSet.getId() + "]" + objectToString(styleSet));		
    }

	public static String objectToString(StyleSet styleSet){
		StringBuffer buf = new StringBuffer();
        buf.append("StyleSet=");
		buf.append("Id=").append(styleSet.getId()).append(", ");
		buf.append("SiteId=").append(styleSet.getSiteId()).append(", ");
		return buf.toString();    
    }
}
