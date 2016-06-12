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
import com.autosite.db.AutositeDataObject;
import com.jtrend.service.DomainStore;
import com.autosite.holder.DataHolderObject;


import com.autosite.db.SiteConfigStyleDAO;
import com.autosite.db.SiteConfigStyle;
import com.surveygen.db.BaseMemoryDAO;

public class SiteConfigStyleDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_SiteIdToOneMap;






    private static Logger m_logger = Logger.getLogger(SiteConfigStyleDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static SiteConfigStyleDS m_SiteConfigStyleDS = new SiteConfigStyleDSExtent();



    public static SiteConfigStyleDS getInstance() {
        return m_SiteConfigStyleDS;
    }

    public static synchronized SiteConfigStyleDS getInstance(long id) {
        SiteConfigStyleDS ret = (SiteConfigStyleDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SiteConfigStyleDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SiteConfigStyleDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SiteConfigStyleDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SiteConfigStyle loaded from DB. num=" + m_idToMap.size());
        
    }


    protected SiteConfigStyleDS() {
        m_dao = new SiteConfigStyleDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SiteConfigStyleDS(long id) {
        m_dao = new SiteConfigStyleDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SiteConfigStyle getById(Long id) {
        return (SiteConfigStyle) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        SiteConfigStyle o = (SiteConfigStyle)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SiteConfigStyle removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SiteConfigStyle added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdOneMap(obj, del);
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
        SiteConfigStyle o = (SiteConfigStyle)obj;

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
            if (m_debug) m_logger.debug("SiteConfigStyle removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("SiteConfigStyle added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneIdKey - SiteId

    public SiteConfigStyle getObjectBySiteId(long keySiteId) {
        return (SiteConfigStyle)m_SiteIdToOneMap.get(new Long(keySiteId));
    }

    private void updateSiteIdOneMap(Object obj, boolean del) {
        SiteConfigStyle o = (SiteConfigStyle)obj;
        Long _SiteId = new Long(o.getSiteId());

        if (del) {
            // delete from SiteIdToOneMap

            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                m_SiteIdToOneMap.remove(_SiteId);
                if (m_debug) m_logger.debug("SiteConfigStyle removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("SiteConfigStyle not removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]. Does not exist");
            } 
        }
        else {
            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                if (m_debug) m_logger.debug("SiteConfigStyle repalced SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("SiteConfigStyle added to SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } 
            m_SiteIdToOneMap.put(_SiteId, o);
        }
    }










	//

    public static void main(String[] args) throws Exception {

        SiteConfigStyleDS ds = new SiteConfigStyleDS();
        SiteConfigStyle obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static SiteConfigStyle createDefault(){

        SiteConfigStyle _SiteConfigStyle = new SiteConfigStyle();        
	    _SiteConfigStyle = new SiteConfigStyle();// SiteConfigStyleDS.getInstance().getDeafult();
        return _SiteConfigStyle;
    }

    public static SiteConfigStyle copy(SiteConfigStyle org){

    	SiteConfigStyle ret = new SiteConfigStyle();

		ret.setThemeId(org.getThemeId());
		ret.setCssIndex(org.getCssIndex());
		ret.setCssImport(org.getCssImport());
		ret.setLayoutIndex(org.getLayoutIndex());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(SiteConfigStyle siteConfigStyle, Logger logger){
		logger.debug("SiteConfigStyle [" + siteConfigStyle.getId() + "]" + objectToString(siteConfigStyle));		
    }

	public static String objectToString(SiteConfigStyle siteConfigStyle){
		StringBuilder buf = new StringBuilder();
        buf.append("SiteConfigStyle=");
		buf.append("Id=").append(siteConfigStyle.getId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		SiteConfigStyle siteConfigStyle = (SiteConfigStyle)object;
		StringBuilder buf = new StringBuilder();
        buf.append("SiteConfigStyle=");
		buf.append("Id=").append(siteConfigStyle.getId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
