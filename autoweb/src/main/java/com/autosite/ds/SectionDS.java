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
import com.jtrend.service.DomainStore;

import com.autosite.holder.SectionDataHolder;

import com.autosite.db.SectionDAO;
import com.autosite.db.Section;
import com.surveygen.db.BaseMemoryDAO;

public class SectionDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(SectionDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static SectionDS m_SectionDS = new SectionDSExtent();



    public static SectionDS getInstance() {
        return m_SectionDS;
    }

    public static synchronized SectionDS getInstance(long id) {
        SectionDS ret = (SectionDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SectionDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SectionDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SectionDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SectionDataHolder loaded from DB. num=" + m_idToMap.size());
        
    }


    protected SectionDS() {
        m_dao = new SectionDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SectionDS(long id) {
        m_dao = new SectionDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SectionDataHolder getById(Long id) {
        return (SectionDataHolder) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        SectionDataHolder o = (SectionDataHolder)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SectionDataHolder removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SectionDataHolder added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
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
        SectionDataHolder o = (SectionDataHolder)obj;

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
            if (m_debug) m_logger.debug("SectionDataHolder removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("SectionDataHolder added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    












    public static void main(String[] args) throws Exception {

        SectionDS ds = new SectionDS();
        SectionDataHolder obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static Section createDefault(){

        Section _Section = new Section();        
	    _Section = new Section();// SectionDS.getInstance().getDeafult();
        return _Section;
    }

    public static Section copy(Section org){

    	Section ret = new Section();

		ret.setTitle(org.getTitle());
		ret.setMainPageId(org.getMainPageId());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(Section section, Logger logger){
		logger.debug("Section [" + section.getId() + "]" + objectToString(section));		
    }


	public static String objectToString(Section section){
		StringBuilder buf = new StringBuilder();
        buf.append("Section=");
		buf.append("Id=").append(section.getId()).append(", ");
		buf.append("SiteId=").append(section.getSiteId()).append(", ");
		return buf.toString();    
    }


    public boolean usingHolderObject() {
        return true;
    }
    public static void objectToLog(SectionDataHolder section, Logger logger){
    }
	public static String objectToString(SectionDataHolder section){
		StringBuilder buf = new StringBuilder();
        buf.append("Section=");
		buf.append("Id=").append(section.getId()).append(", ");
		buf.append("SiteId=").append(section.getSiteId()).append(", ");
		return buf.toString();    
	}



	// Empty methods for 

}
