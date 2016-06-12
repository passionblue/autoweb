/* 
Template last modification history:
03152015 - Mongo repository was added to DS class. Repository is proxy based, hard to use base class handling. So each repository is implemented here. 
(1) repostory init (2) put/delete/update added here. 

Source Generated: Sun Mar 15 13:54:44 EDT 2015
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


import com.autosite.db.CleanerPickupDeliveryDAO;
import com.autosite.db.CleanerPickupDeliveryDAO2;
import com.autosite.db.CleanerPickupDeliveryExtentDAO;
import com.autosite.db.CleanerPickupDelivery;
import com.surveygen.db.BaseMemoryDAO;

import org.springframework.data.repository.CrudRepository;

import com.autosite.repository.mongo.AutositeMongoRepositoryFactory;
import com.autosite.repository.mongo.core.CleanerPickupDeliveryRepository;


public class CleanerPickupDeliveryDS extends AbstractDS implements DomainStore {

	// This flag is set during the generation by aggregateAllSites switch ( in AutoGen config). 
	// This flag is set, DS will invalidates site ID while putting into memory cache so that caller gets access to all items
    protected boolean m_aggregateAllSites = false; 

    protected boolean m_accessDbIfMiss = true; //If true, this will access DB 


	// This will be controlled by code gen config  XXX.dsCacheEnable=true/false
	// 0512 this was implemented for a situation for DS that needs to write only 
	// No need for read in the future. 
    protected boolean m_cacheEnable = true; //If false, cache will not be utilized 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerPickupDeliveryDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerPickupDeliveryDS m_CleanerPickupDeliveryDS = new CleanerPickupDeliveryDSExtent();

    //Mongo Repository
    protected CleanerPickupDeliveryRepository m_repository;

    public static CleanerPickupDeliveryDS getInstance() {
        return m_CleanerPickupDeliveryDS;
    }

    public static synchronized CleanerPickupDeliveryDS getInstance(long id) {
        CleanerPickupDeliveryDS ret = (CleanerPickupDeliveryDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerPickupDeliveryDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerPickupDeliveryDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerPickupDeliveryDAO2)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerPickupDelivery loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerPickupDeliveryDS() {
        m_dao = new CleanerPickupDeliveryExtentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
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

        //Mongo
        m_repository = (CleanerPickupDeliveryRepository) AutositeMongoRepositoryFactory.getInstance().getRepository(CleanerPickupDeliveryRepository.class);        
    }

    //Mongo
    protected CrudRepository getRepository() {
        return (CrudRepository) m_repository;
    }

    protected CleanerPickupDeliveryDS(long id) {
        m_dao = new CleanerPickupDeliveryDAO2();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public CleanerPickupDelivery getById(Long id) {
        CleanerPickupDelivery ret = (CleanerPickupDelivery) m_idToMap.get(id);
        
        if ( ret == null && m_accessDbIfMiss ) {
            return getById(id, true);
        }
        return ret;
    }

    public CleanerPickupDelivery getById(Long id, boolean forSynchWithDB) {
        if (persistEnable() && forSynchWithDB ) {
            try {
                CleanerPickupDelivery loaded = (CleanerPickupDelivery) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return (CleanerPickupDelivery) m_idToMap.get(id);
    }
    
    public String getEntityClass(){
        return CleanerPickupDelivery.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        CleanerPickupDelivery o = (CleanerPickupDelivery)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerPickupDelivery removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerPickupDelivery added to DS " + o.getId());
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
            if ( m_aggregateAllSites && m_SiteIdToMap.containsKey(new Long(0))) 
                return new ArrayList(((Map)m_SiteIdToMap.get(new Long(0))).values() ); //m_aggregateAllSites=true
            else 
                return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        CleanerPickupDelivery o = (CleanerPickupDelivery)obj;

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
            if (m_debug) m_logger.debug("CleanerPickupDelivery removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerPickupDelivery added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











    //

    @Override
    public boolean delete(Object obj) {
        Object target = obj;
        boolean result = super.delete(target);
        if (m_repository!= null && persistEnable()) {
            if (usingHolderObject()) {
                target = ((DataHolderObject)obj).getDataObject();
            }           
            m_repository.delete((CleanerPickupDelivery)target);
        }
        return result;
    }

    @Override
    public boolean put(Object obj) {
        Object target = obj;
        boolean result = super.put(target);
        if (m_repository!= null && persistEnable()) { 
            if (usingHolderObject()) {
                target = ((DataHolderObject)obj).getDataObject();
            }           
            m_repository.save((CleanerPickupDelivery)target);
        }
        return result;
    }

    @Override
    public boolean update(Object obj) {
        Object target = obj;
        boolean result = super.update(obj);
        if (m_repository!= null && persistEnable()) { 
            if (usingHolderObject()) {
                target = ((DataHolderObject)obj).getDataObject();
            }           
            m_repository.save((CleanerPickupDelivery)target);
        }
        return result;
    }


	//

    public static void main(String[] args) throws Exception {

        CleanerPickupDeliveryDS ds = new CleanerPickupDeliveryDS();
        CleanerPickupDelivery obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerPickupDelivery createDefault(){

        CleanerPickupDelivery _CleanerPickupDelivery = new CleanerPickupDelivery();        
	    _CleanerPickupDelivery = new CleanerPickupDelivery();// CleanerPickupDeliveryDS.getInstance().getDeafult();
        return _CleanerPickupDelivery;
    }

    public static CleanerPickupDelivery copy(CleanerPickupDelivery org){

    	CleanerPickupDelivery ret = new CleanerPickupDelivery();

		ret.setLocationId(org.getLocationId());
		ret.setCustomerId(org.getCustomerId());
		ret.setTicketId(org.getTicketId());
		ret.setTicketUid(org.getTicketUid());
		ret.setPickupTicket(org.getPickupTicket());
		ret.setCheckinTicketForDelivery(org.getCheckinTicketForDelivery());
		ret.setIsDeliveryRequest(org.getIsDeliveryRequest());
		ret.setIsWebRequest(org.getIsWebRequest());
		ret.setIsRecurringRequest(org.getIsRecurringRequest());
		ret.setIsReceiveReady(org.getIsReceiveReady());
		ret.setIsReceiveComplete(org.getIsReceiveComplete());
		ret.setRecurId(org.getRecurId());
		ret.setCancelled(org.getCancelled());
		ret.setCompleted(org.getCompleted());
		ret.setCustomerName(org.getCustomerName());
		ret.setAddress(org.getAddress());
		ret.setAptNumber(org.getAptNumber());
		ret.setPhone(org.getPhone());
		ret.setEmail(org.getEmail());
		ret.setAckReceiveMethod(org.getAckReceiveMethod());
		ret.setCustomerInstruction(org.getCustomerInstruction());
		ret.setPickupDeliveryByDay(org.getPickupDeliveryByDay());
		ret.setPickupDeliveryByTime(org.getPickupDeliveryByTime());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());
		ret.setTimeAcked(org.getTimeAcked());
		ret.setAckedByUserId(org.getAckedByUserId());
		ret.setTimeReady(org.getTimeReady());
		ret.setTimeNotified(org.getTimeNotified());
		ret.setTimeCompleted(org.getTimeCompleted());
		ret.setNote(org.getNote());
		ret.setPickupNote(org.getPickupNote());
		ret.setDeliveryNote(org.getDeliveryNote());

        return ret;
    }

	public static void objectToLog(CleanerPickupDelivery cleanerPickupDelivery, Logger logger){
		logger.debug("CleanerPickupDelivery [" + cleanerPickupDelivery.getId() + "]" + objectToString(cleanerPickupDelivery));		
    }

	public static String objectToString(CleanerPickupDelivery cleanerPickupDelivery){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerPickupDelivery=");
		buf.append("Id=").append(cleanerPickupDelivery.getId()).append(", ");
		buf.append("SiteId=").append(cleanerPickupDelivery.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerPickupDelivery cleanerPickupDelivery = (CleanerPickupDelivery)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerPickupDelivery=");
		buf.append("Id=").append(cleanerPickupDelivery.getId()).append(", ");
		buf.append("SiteId=").append(cleanerPickupDelivery.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
