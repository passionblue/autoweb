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

import com.autosite.holder.GenFlowMidDataHolder;

import com.surveygen.db.BaseMemoryDAO;

public class GenFlowMidDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(GenFlowMidDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static GenFlowMidDS m_GenFlowMidDS = new GenFlowMidDSExtent();



    public static GenFlowMidDS getInstance() {
        return m_GenFlowMidDS;
    }

    public static synchronized GenFlowMidDS getInstance(long id) {
        GenFlowMidDS ret = (GenFlowMidDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new GenFlowMidDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_GenFlowMidDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;



    protected GenFlowMidDS() {
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

    protected GenFlowMidDS(long id) {
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

    public GenFlowMidDataHolder getById(Long id) {
        return (GenFlowMidDataHolder) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(wrapByHolder(obj), del);
    }
    public void updateMaps(Object obj, boolean del) {
        GenFlowMidDataHolder o = (GenFlowMidDataHolder)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("GenFlowMidDataHolder removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("GenFlowMidDataHolder added to DS " + o.getId());
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
        GenFlowMidDataHolder o = (GenFlowMidDataHolder)obj;

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
            if (m_debug) m_logger.debug("GenFlowMidDataHolder removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("GenFlowMidDataHolder added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//
    public boolean persistEnable(){
        return false;
    }

    public static void main(String[] args) throws Exception {

        GenFlowMidDS ds = new GenFlowMidDS();
        GenFlowMidDataHolder obj = ds.getById((long)1);
        System.out.println(obj);

    }



    public boolean usingHolderObject() {
        return true;
    }

    public DataHolderObject wrapByHolder(Object obj){
        return new GenFlowMidDataHolder ();
    }


    public static void objectToLog(GenFlowMidDataHolder genFlowMid, Logger logger){
    }


	public static String objectToString(GenFlowMidDataHolder genFlowMid){
		StringBuilder buf = new StringBuilder();
        buf.append("GenFlowMid=");
		return buf.toString();    
	}





	public String objectToString2(AutositeDataObject object){
		GenFlowMidDataHolder genFlowMid = (GenFlowMidDataHolder)object;
		StringBuilder buf = new StringBuilder();
        buf.append("GenFlowMid=");
		return buf.toString();    
    }


	// Empty methods for 

}
