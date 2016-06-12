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

import com.autosite.holder.GenFlowStartDataHolder;

import com.surveygen.db.BaseMemoryDAO;

public class GenFlowStartDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(GenFlowStartDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static GenFlowStartDS m_GenFlowStartDS = new GenFlowStartDSExtent();



    public static GenFlowStartDS getInstance() {
        return m_GenFlowStartDS;
    }

    public static synchronized GenFlowStartDS getInstance(long id) {
        GenFlowStartDS ret = (GenFlowStartDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new GenFlowStartDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_GenFlowStartDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;



    protected GenFlowStartDS() {
        m_dao = new BaseMemoryDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected GenFlowStartDS(long id) {
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

    public GenFlowStartDataHolder getById(Long id) {
        return (GenFlowStartDataHolder) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(wrapByHolder(obj), del);
    }
    public void updateMaps(Object obj, boolean del) {
        GenFlowStartDataHolder o = (GenFlowStartDataHolder)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("GenFlowStartDataHolder removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("GenFlowStartDataHolder added to DS " + o.getId());
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
        GenFlowStartDataHolder o = (GenFlowStartDataHolder)obj;

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
            if (m_debug) m_logger.debug("GenFlowStartDataHolder removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("GenFlowStartDataHolder added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//
    public boolean persistEnable(){
        return false;
    }

    public static void main(String[] args) throws Exception {

        GenFlowStartDS ds = new GenFlowStartDS();
        GenFlowStartDataHolder obj = ds.getById((long)1);
        System.out.println(obj);

    }



    public boolean usingHolderObject() {
        return true;
    }

    public DataHolderObject wrapByHolder(Object obj){
        return new GenFlowStartDataHolder ();
    }


    public static void objectToLog(GenFlowStartDataHolder genFlowStart, Logger logger){
    }


	public static String objectToString(GenFlowStartDataHolder genFlowStart){
		StringBuilder buf = new StringBuilder();
        buf.append("GenFlowStart=");
		buf.append("Id=").append(genFlowStart.getId()).append(", ");
		buf.append("SiteId=").append(genFlowStart.getSiteId()).append(", ");
		return buf.toString();    
	}





	public String objectToString2(AutositeDataObject object){
		GenFlowStartDataHolder genFlowStart = (GenFlowStartDataHolder)object;
		StringBuilder buf = new StringBuilder();
        buf.append("GenFlowStart=");
		buf.append("Id=").append(genFlowStart.getId()).append(", ");
		buf.append("SiteId=").append(genFlowStart.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
