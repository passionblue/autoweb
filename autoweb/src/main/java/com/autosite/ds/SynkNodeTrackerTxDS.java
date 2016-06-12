/* 
Template last modification history:


Source Generated: Sat Feb 14 00:12:25 EST 2015
*/

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
import com.jtrend.util.AggregatedIdMap;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.db.AutositeDataObject;
import com.jtrend.service.DomainStore;
import com.autosite.holder.DataHolderObject;


import com.autosite.db.SynkNodeTrackerTxDAO;
import com.autosite.db.SynkNodeTrackerTxDAO2;
import com.autosite.db.SynkNodeTrackerTxExtentDAO;
import com.autosite.db.SynkNodeTrackerTx;
import com.surveygen.db.BaseMemoryDAO;

public class SynkNodeTrackerTxDS extends AbstractDS implements DomainStore {

	// This flag is set during the generation by aggregateAllSites switch ( in AutoGen config). 
	// This flag is set, DS will invalidates site ID while putting into memory cache so that caller gets access to all items
    protected boolean m_aggregateAllSites = false; 

    protected boolean m_accessDbIfMiss = true; //If true, this will access DB 


	// This will be controlled by code gen config  XXX.dsCacheEnable=true/false
	// 0512 this was implemented for a situation for DS that needs to write only 
	// No need for read in the future. 
    protected boolean m_cacheEnable = true; //If false, cache will not be utilized 

    protected Map m_SiteIdToMap;



    protected Map m_SiteIdToNamespaceToMap;



    private static Logger m_logger = Logger.getLogger(SynkNodeTrackerTxDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static SynkNodeTrackerTxDS m_SynkNodeTrackerTxDS = new SynkNodeTrackerTxDSExtent();



    public static SynkNodeTrackerTxDS getInstance() {
        return m_SynkNodeTrackerTxDS;
    }

    public static synchronized SynkNodeTrackerTxDS getInstance(long id) {
        SynkNodeTrackerTxDS ret = (SynkNodeTrackerTxDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SynkNodeTrackerTxDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SynkNodeTrackerTxDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SynkNodeTrackerTxDAO2)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SynkNodeTrackerTx loaded from DB. num=" + m_idToMap.size());
        
    }


    protected SynkNodeTrackerTxDS() {
        m_dao = new SynkNodeTrackerTxExtentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToNamespaceToMap = new ConcurrentHashMap();
 
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

		if ( m_cacheEnable ){
            try {
                loadFromDB();
            }
            catch (Exception e) {
                m_logger.error(e, e);
            }
		} else {
		    m_logger.info("Cache **Not** Enabled for: " + this.getClass().getSimpleName());
		}
    }

    protected SynkNodeTrackerTxDS(long id) {
        m_dao = new SynkNodeTrackerTxDAO2();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToNamespaceToMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SynkNodeTrackerTx getById(Long id) {
        SynkNodeTrackerTx ret = (SynkNodeTrackerTx) m_idToMap.get(id);
        
        if ( ret == null && m_accessDbIfMiss ) {
            return getById(id, true);
        }
        return ret;
    }

    public SynkNodeTrackerTx getById(Long id, boolean forSynchWithDB) {
        if (persistEnable() && forSynchWithDB ) {
            try {
                SynkNodeTrackerTx loaded = (SynkNodeTrackerTx) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return (SynkNodeTrackerTx) m_idToMap.get(id);
    }
    
    public String getEntityClass(){
        return SynkNodeTrackerTx.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        SynkNodeTrackerTx o = (SynkNodeTrackerTx)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SynkNodeTrackerTx removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SynkNodeTrackerTx added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdToNamespaceMap(obj, del);
    }
	
    //    //    //
	// ListKeys - SiteId
    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            if ( m_aggregateAllSites && m_SiteIdToMap.containsKey(new Long(0))) 
                return new ArrayList(((Map)m_SiteIdToMap.get(new Long(0))).values() ); //m_aggregateAllSites=true
            else 
                return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        SynkNodeTrackerTx o = (SynkNodeTrackerTx)obj;

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
            if (m_debug) m_logger.debug("SynkNodeTrackerTx removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("SynkNodeTrackerTx added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    






    public List getBySiteIdNamespaceList(long SiteId, String Namespace) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToNamespaceToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToNamespaceToMap.get(keySiteId);

     	    String keyNamespace =  Namespace;
			if (  keyNamespace == null || keyNamespace.isEmpty()) return new ArrayList();
            
            if ( mapSiteId.containsKey(keyNamespace)){
                return new ArrayList( ((Map)mapSiteId.get(keyNamespace)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdNamespaceList(long SiteId, String Namespace) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToNamespaceToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToNamespaceToMap.get(keySiteId);

     	    String keyNamespace =  Namespace;
			if (  keyNamespace == null || keyNamespace.isEmpty()) return new HashMap();
            
            if ( mapSiteId.containsKey(keyNamespace)){
                return (Map)mapSiteId.get(keyNamespace);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToNamespaceMap(Object obj, boolean del) {
        SynkNodeTrackerTx o = (SynkNodeTrackerTx)obj;

   	    String keyNamespace =  o.getNamespace();
		if (  keyNamespace == null || keyNamespace.isEmpty()) return;

        if (del) {
            // delete from SiteIdNamespaceToMap
            Map mapSiteId  = (Map) m_SiteIdToNamespaceToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapNamespace = (Map) mapSiteId.get(keyNamespace);
                if (mapNamespace != null){
                    mapNamespace.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed SynkNodeTrackerTx from m_SiteIdToNamespaceToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getNamespace());
        }
        else {
            
            // add to SiteIdNamespaceToMap
            Map mapSiteId  = (Map) m_SiteIdToNamespaceToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToNamespaceToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapNamespace = (Map) mapSiteId.get(keyNamespace);
            
            if ( mapNamespace == null) {
                mapNamespace = new TreeMap();
                mapSiteId.put(keyNamespace, mapNamespace);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapNamespace.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdNamespaceToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    





	//

    public static void main(String[] args) throws Exception {

        SynkNodeTrackerTxDS ds = new SynkNodeTrackerTxDS();
        SynkNodeTrackerTx obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static SynkNodeTrackerTx createDefault(){

        SynkNodeTrackerTx _SynkNodeTrackerTx = new SynkNodeTrackerTx();        
	    _SynkNodeTrackerTx = new SynkNodeTrackerTx();// SynkNodeTrackerTxDS.getInstance().getDeafult();
        return _SynkNodeTrackerTx;
    }

    public static SynkNodeTrackerTx copy(SynkNodeTrackerTx org){

    	SynkNodeTrackerTx ret = new SynkNodeTrackerTx();

		ret.setNamespace(org.getNamespace());
		ret.setDeviceId(org.getDeviceId());
		ret.setTxToken(org.getTxToken());
		ret.setStampAcked(org.getStampAcked());
		ret.setStampLast(org.getStampLast());
		ret.setNumRecords(org.getNumRecords());
		ret.setIp(org.getIp());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(SynkNodeTrackerTx synkNodeTrackerTx, Logger logger){
		logger.debug("SynkNodeTrackerTx [" + synkNodeTrackerTx.getId() + "]" + objectToString(synkNodeTrackerTx));		
    }

	public static String objectToString(SynkNodeTrackerTx synkNodeTrackerTx){
		StringBuilder buf = new StringBuilder();
        buf.append("SynkNodeTrackerTx=");
		buf.append("Id=").append(synkNodeTrackerTx.getId()).append(", ");
		buf.append("SiteId=").append(synkNodeTrackerTx.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		SynkNodeTrackerTx synkNodeTrackerTx = (SynkNodeTrackerTx)object;
		StringBuilder buf = new StringBuilder();
        buf.append("SynkNodeTrackerTx=");
		buf.append("Id=").append(synkNodeTrackerTx.getId()).append(", ");
		buf.append("SiteId=").append(synkNodeTrackerTx.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
