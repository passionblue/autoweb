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


import com.autosite.db.SiteHeaderDAO;
import com.autosite.db.SiteHeader;
import com.surveygen.db.BaseMemoryDAO;

public class SiteHeaderDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(SiteHeaderDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static SiteHeaderDS m_SiteHeaderDS = new SiteHeaderDSExtent();



    public static SiteHeaderDS getInstance() {
        return m_SiteHeaderDS;
    }

    public static synchronized SiteHeaderDS getInstance(long id) {
        SiteHeaderDS ret = (SiteHeaderDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SiteHeaderDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SiteHeaderDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SiteHeaderDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SiteHeader loaded from DB. num=" + m_idToMap.size());
        
    }


    protected SiteHeaderDS() {
        m_dao = new SiteHeaderDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SiteHeaderDS(long id) {
        m_dao = new SiteHeaderDAO();
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

    public SiteHeader getById(Long id) {
        return (SiteHeader) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        SiteHeader o = (SiteHeader)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SiteHeader removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SiteHeader added to DS " + o.getId());
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
        SiteHeader o = (SiteHeader)obj;

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
            if (m_debug) m_logger.debug("SiteHeader removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("SiteHeader added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    












    public static void main(String[] args) throws Exception {

        SiteHeaderDS ds = new SiteHeaderDS();
        SiteHeader obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static SiteHeader createDefault(){

        SiteHeader _SiteHeader = new SiteHeader();        
	    _SiteHeader = new SiteHeader();// SiteHeaderDS.getInstance().getDeafult();
        return _SiteHeader;
    }

    public static SiteHeader copy(SiteHeader org){

    	SiteHeader ret = new SiteHeader();

		ret.setAsIs(org.getAsIs());
		ret.setIncludeType(org.getIncludeType());
		ret.setIncludeText(org.getIncludeText());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(SiteHeader siteHeader, Logger logger){
		logger.debug("SiteHeader [" + siteHeader.getId() + "]" + objectToString(siteHeader));		
    }


	public static String objectToString(SiteHeader siteHeader){
		StringBuilder buf = new StringBuilder();
        buf.append("SiteHeader=");
		buf.append("Id=").append(siteHeader.getId()).append(", ");
		buf.append("SiteId=").append(siteHeader.getSiteId()).append(", ");
		return buf.toString();    
    }

	public String objectToString2(AutositeDataObject object){
		SiteHeader siteHeader = (SiteHeader)object;
		StringBuilder buf = new StringBuilder();
        buf.append("SiteHeader=");
		buf.append("Id=").append(siteHeader.getId()).append(", ");
		buf.append("SiteId=").append(siteHeader.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
