/* 
Template last modification history:


Source Generated: Sat Feb 14 00:17:17 EST 2015
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


import com.autosite.db.SynkNodeTrackerDAO;
import com.autosite.db.SynkNodeTrackerDAO2;
import com.autosite.db.SynkNodeTrackerExtentDAO;
import com.autosite.db.SynkNodeTracker;
import com.surveygen.db.BaseMemoryDAO;

public class SynkNodeTrackerDS extends AbstractDS implements DomainStore {

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
    protected Map m_SiteIdToDeviceIdToMap;



    private static Logger m_logger = Logger.getLogger(SynkNodeTrackerDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static SynkNodeTrackerDS m_SynkNodeTrackerDS = new SynkNodeTrackerDSExtent();



    public static SynkNodeTrackerDS getInstance() {
        return m_SynkNodeTrackerDS;
    }

    public static synchronized SynkNodeTrackerDS getInstance(long id) {
        SynkNodeTrackerDS ret = (SynkNodeTrackerDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SynkNodeTrackerDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SynkNodeTrackerDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SynkNodeTrackerDAO2)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SynkNodeTracker loaded from DB. num=" + m_idToMap.size());
        
    }


    protected SynkNodeTrackerDS() {
        m_dao = new SynkNodeTrackerExtentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToNamespaceToMap = new ConcurrentHashMap();
        m_SiteIdToDeviceIdToMap = new ConcurrentHashMap();
 
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

    protected SynkNodeTrackerDS(long id) {
        m_dao = new SynkNodeTrackerDAO2();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToNamespaceToMap = new ConcurrentHashMap();
        m_SiteIdToDeviceIdToMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SynkNodeTracker getById(Long id) {
        SynkNodeTracker ret = (SynkNodeTracker) m_idToMap.get(id);
        
        if ( ret == null && m_accessDbIfMiss ) {
            return getById(id, true);
        }
        return ret;
    }

    public SynkNodeTracker getById(Long id, boolean forSynchWithDB) {
        if (persistEnable() && forSynchWithDB ) {
            try {
                SynkNodeTracker loaded = (SynkNodeTracker) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return (SynkNodeTracker) m_idToMap.get(id);
    }
    
    public String getEntityClass(){
        return SynkNodeTracker.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        SynkNodeTracker o = (SynkNodeTracker)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SynkNodeTracker removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SynkNodeTracker added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdToNamespaceMap(obj, del);
        updateSiteIdToDeviceIdMap(obj, del);
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
        SynkNodeTracker o = (SynkNodeTracker)obj;

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
            if (m_debug) m_logger.debug("SynkNodeTracker removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("SynkNodeTracker added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        SynkNodeTracker o = (SynkNodeTracker)obj;

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
            m_logger.debug("removed SynkNodeTracker from m_SiteIdToNamespaceToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getNamespace());
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
    public List getBySiteIdDeviceIdList(long SiteId, String DeviceId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToDeviceIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToDeviceIdToMap.get(keySiteId);

     	    String keyDeviceId =  DeviceId;
			if (  keyDeviceId == null || keyDeviceId.isEmpty()) return new ArrayList();
            
            if ( mapSiteId.containsKey(keyDeviceId)){
                return new ArrayList( ((Map)mapSiteId.get(keyDeviceId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdDeviceIdList(long SiteId, String DeviceId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToDeviceIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToDeviceIdToMap.get(keySiteId);

     	    String keyDeviceId =  DeviceId;
			if (  keyDeviceId == null || keyDeviceId.isEmpty()) return new HashMap();
            
            if ( mapSiteId.containsKey(keyDeviceId)){
                return (Map)mapSiteId.get(keyDeviceId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToDeviceIdMap(Object obj, boolean del) {
        SynkNodeTracker o = (SynkNodeTracker)obj;

   	    String keyDeviceId =  o.getDeviceId();
		if (  keyDeviceId == null || keyDeviceId.isEmpty()) return;

        if (del) {
            // delete from SiteIdDeviceIdToMap
            Map mapSiteId  = (Map) m_SiteIdToDeviceIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapDeviceId = (Map) mapSiteId.get(keyDeviceId);
                if (mapDeviceId != null){
                    mapDeviceId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed SynkNodeTracker from m_SiteIdToDeviceIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getDeviceId());
        }
        else {
            
            // add to SiteIdDeviceIdToMap
            Map mapSiteId  = (Map) m_SiteIdToDeviceIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToDeviceIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapDeviceId = (Map) mapSiteId.get(keyDeviceId);
            
            if ( mapDeviceId == null) {
                mapDeviceId = new TreeMap();
                mapSiteId.put(keyDeviceId, mapDeviceId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapDeviceId.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdDeviceIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    





	//

    public static void main(String[] args) throws Exception {

        SynkNodeTrackerDS ds = new SynkNodeTrackerDS();
        SynkNodeTracker obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static SynkNodeTracker createDefault(){

        SynkNodeTracker _SynkNodeTracker = new SynkNodeTracker();        
	    _SynkNodeTracker = new SynkNodeTracker();// SynkNodeTrackerDS.getInstance().getDeafult();
        return _SynkNodeTracker;
    }

    public static SynkNodeTracker copy(SynkNodeTracker org){

    	SynkNodeTracker ret = new SynkNodeTracker();

		ret.setNamespace(org.getNamespace());
		ret.setDeviceId(org.getDeviceId());
		ret.setRemote(org.getRemote());
		ret.setStamp(org.getStamp());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(SynkNodeTracker synkNodeTracker, Logger logger){
		logger.debug("SynkNodeTracker [" + synkNodeTracker.getId() + "]" + objectToString(synkNodeTracker));		
    }

	public static String objectToString(SynkNodeTracker synkNodeTracker){
		StringBuilder buf = new StringBuilder();
        buf.append("SynkNodeTracker=");
		buf.append("Id=").append(synkNodeTracker.getId()).append(", ");
		buf.append("SiteId=").append(synkNodeTracker.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		SynkNodeTracker synkNodeTracker = (SynkNodeTracker)object;
		StringBuilder buf = new StringBuilder();
        buf.append("SynkNodeTracker=");
		buf.append("Id=").append(synkNodeTracker.getId()).append(", ");
		buf.append("SiteId=").append(synkNodeTracker.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
