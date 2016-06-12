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


import com.autosite.db.AutositeSynchLedgerDAO;
import com.autosite.db.AutositeSynchLedgerDAO2;
import com.autosite.db.AutositeSynchLedgerExtentDAO;
import com.autosite.db.AutositeSynchLedger;
import com.surveygen.db.BaseMemoryDAO;

public class AutositeSynchLedgerDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected boolean m_accessDbIfMiss = true; //If true, this will access DB 


	// This will be controlled by code gen config  XXX.dsCacheEnable=true/false
	// 0512 this was implemented for a situation for DS that needs to write only 
	// No need for read in the future. 
    protected boolean m_cacheEnable = true; //If false, cache will not be utilized 

    protected Map m_SiteIdToMap;



    protected Map m_SiteIdToTargetToMap;


    protected Map m_SiteIdToRemoteTokenToOneMap;

    private static Logger m_logger = Logger.getLogger(AutositeSynchLedgerDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static AutositeSynchLedgerDS m_AutositeSynchLedgerDS = new AutositeSynchLedgerDSExtent();



    public static AutositeSynchLedgerDS getInstance() {
        return m_AutositeSynchLedgerDS;
    }

    public static synchronized AutositeSynchLedgerDS getInstance(long id) {
        AutositeSynchLedgerDS ret = (AutositeSynchLedgerDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new AutositeSynchLedgerDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_AutositeSynchLedgerDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((AutositeSynchLedgerDAO2)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("AutositeSynchLedger loaded from DB. num=" + m_idToMap.size());
        
    }


    protected AutositeSynchLedgerDS() {
        m_dao = new AutositeSynchLedgerExtentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToTargetToMap = new ConcurrentHashMap();
        m_SiteIdToRemoteTokenToOneMap = new ConcurrentHashMap();
 
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

    protected AutositeSynchLedgerDS(long id) {
        m_dao = new AutositeSynchLedgerDAO2();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToTargetToMap = new ConcurrentHashMap();
        m_SiteIdToRemoteTokenToOneMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public AutositeSynchLedger getById(Long id) {
        AutositeSynchLedger ret = (AutositeSynchLedger) m_idToMap.get(id);
        
        if ( ret == null && m_accessDbIfMiss ) {
            return getById(id, true);
        }
        return ret;
    }

    public AutositeSynchLedger getById(Long id, boolean forSynchWithDB) {
        if (persistEnable() && forSynchWithDB ) {
            try {
                AutositeSynchLedger loaded = (AutositeSynchLedger) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return (AutositeSynchLedger) m_idToMap.get(id);
    }
    
    public String getEntityClass(){
        return AutositeSynchLedger.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        AutositeSynchLedger o = (AutositeSynchLedger)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("AutositeSynchLedger removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("AutositeSynchLedger added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdToTargetMap(obj, del);
        updateSiteIdToRemoteTokenOneMap(obj, del);
    }
	
    //    //    //
	// ListKeys - SiteId
    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            if ( m_aggregateAllSites) 
                return new ArrayList(((Map)m_SiteIdToMap.get(new Long(0))).values() ); //m_aggregateAllSites=true
            else 
                return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        AutositeSynchLedger o = (AutositeSynchLedger)obj;

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
            if (m_debug) m_logger.debug("AutositeSynchLedger removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("AutositeSynchLedger added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    






    public List getBySiteIdTargetList(long SiteId, String Target) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToTargetToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToTargetToMap.get(keySiteId);

     	    String keyTarget =  Target;
			if (  keyTarget == null || keyTarget.isEmpty()) return new ArrayList();
            
            if ( mapSiteId.containsKey(keyTarget)){
                return new ArrayList( ((Map)mapSiteId.get(keyTarget)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdTargetList(long SiteId, String Target) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToTargetToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToTargetToMap.get(keySiteId);

     	    String keyTarget =  Target;
			if (  keyTarget == null || keyTarget.isEmpty()) return new HashMap();
            
            if ( mapSiteId.containsKey(keyTarget)){
                return (Map)mapSiteId.get(keyTarget);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToTargetMap(Object obj, boolean del) {
        AutositeSynchLedger o = (AutositeSynchLedger)obj;

   	    String keyTarget =  o.getTarget();
		if (  keyTarget == null || keyTarget.isEmpty()) return;

        if (del) {
            // delete from SiteIdTargetToMap
            Map mapSiteId  = (Map) m_SiteIdToTargetToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapTarget = (Map) mapSiteId.get(keyTarget);
                if (mapTarget != null){
                    mapTarget.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed AutositeSynchLedger from m_SiteIdToTargetToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getTarget());
        }
        else {
            
            // add to SiteIdTargetToMap
            Map mapSiteId  = (Map) m_SiteIdToTargetToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToTargetToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapTarget = (Map) mapSiteId.get(keyTarget);
            
            if ( mapTarget == null) {
                mapTarget = new TreeMap();
                mapSiteId.put(keyTarget, mapTarget);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapTarget.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdTargetToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    



	// listOneToOneStringToSiteIdKey - RemoteToken

    public  AutositeSynchLedger getBySiteIdToRemoteToken(long siteId, String RemoteToken) {

        Long keySiteIdTo  = new Long(siteId);
        if (m_SiteIdToRemoteTokenToOneMap.containsKey(keySiteIdTo)) {
            
            Map mapSiteIdTo = (Map)m_SiteIdToRemoteTokenToOneMap.get(keySiteIdTo);

     	    String keyRemoteToken = RemoteToken;
            
            if ( keyRemoteToken!= null && mapSiteIdTo.containsKey(keyRemoteToken)){
                return ( AutositeSynchLedger)mapSiteIdTo.get(keyRemoteToken);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    public List getByOrderRemoteToken(){
        List ret = new ArrayList();
    
        List contentsByRemoteToken = new ArrayList( m_SiteIdToRemoteTokenToOneMap.values());
        Map map  = new TreeMap();
        
        for (Iterator iterator = contentsByRemoteToken.iterator(); iterator.hasNext();) {
            AutositeSynchLedger obj = (AutositeSynchLedger) iterator.next();
            if ( obj.getRemoteToken() == null ) continue;
            map.put(obj.getRemoteToken(), obj);
        }
        
        return new ArrayList(map.values());
    }

    private void updateSiteIdToRemoteTokenOneMap(Object obj, boolean del) {
        AutositeSynchLedger o = (AutositeSynchLedger)obj;

   	    String keyRemoteToken = o.getRemoteToken();

		if ( keyRemoteToken == null || keyRemoteToken.isEmpty() ) {
			m_logger.warn("Passed key is null in updateSiteIdToRemoteTokenOneMap for the AutositeSynchLedger object. ABORTED id = " + o.getId());
            return;
        }

        if (del) {
            // delete from PollIdRemoteTokenToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToRemoteTokenToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo != null ) {
                if (mapSiteIdTo.containsKey(keyRemoteToken)){
                    mapSiteIdTo.remove(keyRemoteToken);
                }
            }
            m_logger.debug("removed AutositeSynchLedger from m_SiteIdToRemoteTokenToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getRemoteToken());
        }
        else {
            
            // add to SiteIdToRemoteTokenToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToRemoteTokenToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo == null ) {
                mapSiteIdTo = new TreeMap();
                m_SiteIdToRemoteTokenToOneMap.put(new Long(o.getSiteId()), mapSiteIdTo);
                if (m_debug) m_logger.debug("created new   mapSiteIdTo for " + o.getSiteId());
            }
            
			AutositeSynchLedger replaced = (AutositeSynchLedger) mapSiteIdTo.put(keyRemoteToken,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdToRemoteTokenOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("AutositeSynchLedger added to SiteIdToRemoteTokenToOneMap " + o.getId() + " to " + o.getSiteId());
        }
    }    


	//

    public static void main(String[] args) throws Exception {

        AutositeSynchLedgerDS ds = new AutositeSynchLedgerDS();
        AutositeSynchLedger obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static AutositeSynchLedger createDefault(){

        AutositeSynchLedger _AutositeSynchLedger = new AutositeSynchLedger();        
	    _AutositeSynchLedger = new AutositeSynchLedger();// AutositeSynchLedgerDS.getInstance().getDeafult();
        return _AutositeSynchLedger;
    }

    public static AutositeSynchLedger copy(AutositeSynchLedger org){

    	AutositeSynchLedger ret = new AutositeSynchLedger();

		ret.setDeviceId(org.getDeviceId());
		ret.setOriginalLedgerId(org.getOriginalLedgerId());
		ret.setScope(org.getScope());
		ret.setTarget(org.getTarget());
		ret.setRemoteToken(org.getRemoteToken());
		ret.setObjectId(org.getObjectId());
		ret.setSynchId(org.getSynchId());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(AutositeSynchLedger autositeSynchLedger, Logger logger){
		logger.debug("AutositeSynchLedger [" + autositeSynchLedger.getId() + "]" + objectToString(autositeSynchLedger));		
    }

	public static String objectToString(AutositeSynchLedger autositeSynchLedger){
		StringBuilder buf = new StringBuilder();
        buf.append("AutositeSynchLedger=");
		buf.append("Id=").append(autositeSynchLedger.getId()).append(", ");
		buf.append("SiteId=").append(autositeSynchLedger.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		AutositeSynchLedger autositeSynchLedger = (AutositeSynchLedger)object;
		StringBuilder buf = new StringBuilder();
        buf.append("AutositeSynchLedger=");
		buf.append("Id=").append(autositeSynchLedger.getId()).append(", ");
		buf.append("SiteId=").append(autositeSynchLedger.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
