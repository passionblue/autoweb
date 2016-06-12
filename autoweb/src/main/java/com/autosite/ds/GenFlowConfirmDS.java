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

import com.autosite.holder.GenFlowConfirmDataHolder;

import com.surveygen.db.BaseMemoryDAO;

public class GenFlowConfirmDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(GenFlowConfirmDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static GenFlowConfirmDS m_GenFlowConfirmDS = new GenFlowConfirmDSExtent();



    public static GenFlowConfirmDS getInstance() {
        return m_GenFlowConfirmDS;
    }

    public static synchronized GenFlowConfirmDS getInstance(long id) {
        GenFlowConfirmDS ret = (GenFlowConfirmDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new GenFlowConfirmDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_GenFlowConfirmDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;



    protected GenFlowConfirmDS() {
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

    protected GenFlowConfirmDS(long id) {
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

    public GenFlowConfirmDataHolder getById(Long id) {
        return (GenFlowConfirmDataHolder) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(wrapByHolder(obj), del);
    }
    public void updateMaps(Object obj, boolean del) {
        GenFlowConfirmDataHolder o = (GenFlowConfirmDataHolder)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("GenFlowConfirmDataHolder removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("GenFlowConfirmDataHolder added to DS " + o.getId());
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
        GenFlowConfirmDataHolder o = (GenFlowConfirmDataHolder)obj;

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
            if (m_debug) m_logger.debug("GenFlowConfirmDataHolder removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("GenFlowConfirmDataHolder added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//
    public boolean persistEnable(){
        return false;
    }

    public static void main(String[] args) throws Exception {

        GenFlowConfirmDS ds = new GenFlowConfirmDS();
        GenFlowConfirmDataHolder obj = ds.getById((long)1);
        System.out.println(obj);

    }



    public boolean usingHolderObject() {
        return true;
    }

    public DataHolderObject wrapByHolder(Object obj){
        return new GenFlowConfirmDataHolder ();
    }


    public static void objectToLog(GenFlowConfirmDataHolder genFlowConfirm, Logger logger){
    }


	public static String objectToString(GenFlowConfirmDataHolder genFlowConfirm){
		StringBuilder buf = new StringBuilder();
        buf.append("GenFlowConfirm=");
		buf.append("Id=").append(genFlowConfirm.getId()).append(", ");
		buf.append("SiteId=").append(genFlowConfirm.getSiteId()).append(", ");
		return buf.toString();    
	}





	public String objectToString2(AutositeDataObject object){
		GenFlowConfirmDataHolder genFlowConfirm = (GenFlowConfirmDataHolder)object;
		StringBuilder buf = new StringBuilder();
        buf.append("GenFlowConfirm=");
		buf.append("Id=").append(genFlowConfirm.getId()).append(", ");
		buf.append("SiteId=").append(genFlowConfirm.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
