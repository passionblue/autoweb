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

import com.autosite.holder.GenFlowFinalDataHolder;

import com.surveygen.db.BaseMemoryDAO;

public class GenFlowFinalDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(GenFlowFinalDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static GenFlowFinalDS m_GenFlowFinalDS = new GenFlowFinalDSExtent();



    public static GenFlowFinalDS getInstance() {
        return m_GenFlowFinalDS;
    }

    public static synchronized GenFlowFinalDS getInstance(long id) {
        GenFlowFinalDS ret = (GenFlowFinalDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new GenFlowFinalDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_GenFlowFinalDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;



    protected GenFlowFinalDS() {
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

    protected GenFlowFinalDS(long id) {
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

    public GenFlowFinalDataHolder getById(Long id) {
        return (GenFlowFinalDataHolder) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(wrapByHolder(obj), del);
    }
    public void updateMaps(Object obj, boolean del) {
        GenFlowFinalDataHolder o = (GenFlowFinalDataHolder)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("GenFlowFinalDataHolder removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("GenFlowFinalDataHolder added to DS " + o.getId());
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
        GenFlowFinalDataHolder o = (GenFlowFinalDataHolder)obj;

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
            if (m_debug) m_logger.debug("GenFlowFinalDataHolder removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("GenFlowFinalDataHolder added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//
    public boolean persistEnable(){
        return false;
    }

    public static void main(String[] args) throws Exception {

        GenFlowFinalDS ds = new GenFlowFinalDS();
        GenFlowFinalDataHolder obj = ds.getById((long)1);
        System.out.println(obj);

    }



    public boolean usingHolderObject() {
        return true;
    }

    public DataHolderObject wrapByHolder(Object obj){
        return new GenFlowFinalDataHolder ();
    }


    public static void objectToLog(GenFlowFinalDataHolder genFlowFinal, Logger logger){
    }


	public static String objectToString(GenFlowFinalDataHolder genFlowFinal){
		StringBuilder buf = new StringBuilder();
        buf.append("GenFlowFinal=");
		buf.append("Id=").append(genFlowFinal.getId()).append(", ");
		return buf.toString();    
	}





	public String objectToString2(AutositeDataObject object){
		GenFlowFinalDataHolder genFlowFinal = (GenFlowFinalDataHolder)object;
		StringBuilder buf = new StringBuilder();
        buf.append("GenFlowFinal=");
		buf.append("Id=").append(genFlowFinal.getId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
