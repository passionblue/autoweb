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
import com.autosite.db.StyleSetContent;
import com.jtrend.service.DomainStore;

import com.autosite.db.StyleSetContentDAO;

public class StyleSetContentDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;





    protected Map m_SiteIdToNameToOneMap;


    private static Logger m_logger = Logger.getLogger(StyleSetContentDS.class);
    private static StyleSetContentDS m_StyleSetContentDS = new StyleSetContentDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static StyleSetContentDS getInstance() {
        return m_StyleSetContentDS;
    }

    public static synchronized StyleSetContentDS getInstance(long id) {
        StyleSetContentDS ret = (StyleSetContentDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new StyleSetContentDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_StyleSetContentDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((StyleSetContentDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("StyleSetContent loaded from DB. num=" + m_idToMap.size());
        
    }

    protected StyleSetContentDS() {
        m_dao = new StyleSetContentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToNameToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected StyleSetContentDS(long id) {
        m_dao = new StyleSetContentDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToNameToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public StyleSetContent getById(Long id) {
        return (StyleSetContent) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        StyleSetContent o = (StyleSetContent)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("StyleSetContent removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("StyleSetContent added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
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
        StyleSetContent o = (StyleSetContent)obj;

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
            if (m_debug) m_logger.debug("StyleSetContent removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("StyleSetContent added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    










	// listOneToOneStringToSiteIdKey - Name

    public  StyleSetContent getBySiteIdToName(long siteId, String Name) {

        Long keySiteIdTo  = new Long(siteId);
        if (m_SiteIdToNameToOneMap.containsKey(keySiteIdTo)) {
            
            Map mapSiteIdTo = (Map)m_SiteIdToNameToOneMap.get(keySiteIdTo);

     	    String keyName = Name;
            
            if ( keyName!= null && mapSiteIdTo.containsKey(keyName)){
                return ( StyleSetContent)mapSiteIdTo.get(keyName);
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
            StyleSetContent obj = (StyleSetContent) iterator.next();
            if ( obj.getName() == null ) continue;
            map.put(obj.getName(), obj);
        }
        
        return new ArrayList(map.values());
    }

    private void updateSiteIdToNameOneMap(Object obj, boolean del) {
        StyleSetContent o = (StyleSetContent)obj;

   	    String keyName = o.getName();

		if ( keyName == null || keyName.isEmpty() ) {
			m_logger.warn("Passed key is null in updateSiteIdToNameOneMap for the StyleSetContent object. ABORTED id = " + o.getId());
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
            m_logger.debug("removed StyleSetContent from m_SiteIdToNameToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getName());
        }
        else {
            
            // add to SiteIdToNameToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToNameToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo == null ) {
                mapSiteIdTo = new TreeMap();
                m_SiteIdToNameToOneMap.put(new Long(o.getSiteId()), mapSiteIdTo);
                if (m_debug) m_logger.debug("created new   mapSiteIdTo for " + o.getSiteId());
            }
            
			StyleSetContent replaced = (StyleSetContent) mapSiteIdTo.put(keyName,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdToNameOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("StyleSetContent added to SiteIdToNameToOneMap " + o.getId() + " to " + o.getSiteId());
        }
    }    




    public static void main(String[] args) throws Exception {

        StyleSetContentDS ds = new StyleSetContentDS();
        StyleSetContent obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static StyleSetContent createDefault(){

        StyleSetContent ret = new StyleSetContent();        
//      ret.setName("");           
//      ret.setIdPrefix("");           
//      ret.setListSubjectStyleId("");           
//      ret.setListDataStyleId("");           
//      ret.setSubjectStyleId("");           
//      ret.setDataStyleId("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static StyleSetContent copy(StyleSetContent org){

    	StyleSetContent ret = new StyleSetContent();

		ret.setName(org.getName());
		ret.setIdPrefix(org.getIdPrefix());
		ret.setListSubjectStyleId(org.getListSubjectStyleId());
		ret.setListDataStyleId(org.getListDataStyleId());
		ret.setSubjectStyleId(org.getSubjectStyleId());
		ret.setDataStyleId(org.getDataStyleId());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(StyleSetContent styleSetContent, Logger logger){
		logger.debug("StyleSetContent [" + styleSetContent.getId() + "]" + objectToString(styleSetContent));		
    }

	public static String objectToString(StyleSetContent styleSetContent){
		StringBuffer buf = new StringBuffer();
        buf.append("StyleSetContent=");
		buf.append("Id=").append(styleSetContent.getId()).append(", ");
		buf.append("SiteId=").append(styleSetContent.getSiteId()).append(", ");
		return buf.toString();    
    }
}
