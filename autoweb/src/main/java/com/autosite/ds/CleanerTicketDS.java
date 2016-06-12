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


import com.autosite.db.CleanerTicketDAO;
import com.autosite.db.CleanerTicketExtentDAO;
import com.autosite.db.CleanerTicket;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerTicketDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;



    protected Map m_SiteIdToSerialToMap;


    protected Map m_SiteIdToSerialToOneMap;

    private static Logger m_logger = Logger.getLogger(CleanerTicketDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerTicketDS m_CleanerTicketDS = new CleanerTicketDSExtent();



    public static CleanerTicketDS getInstance() {
        return m_CleanerTicketDS;
    }

    public static synchronized CleanerTicketDS getInstance(long id) {
        CleanerTicketDS ret = (CleanerTicketDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerTicketDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerTicketDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerTicketDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerTicket loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerTicketDS() {
        m_dao = new CleanerTicketExtentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToSerialToMap = new ConcurrentHashMap();
        m_SiteIdToSerialToOneMap = new ConcurrentHashMap();
 
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected CleanerTicketDS(long id) {
        m_dao = new CleanerTicketDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToSerialToMap = new ConcurrentHashMap();
        m_SiteIdToSerialToOneMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public CleanerTicket getById(Long id) {
        return (CleanerTicket) m_idToMap.get(id);
    }

    public CleanerTicket getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                CleanerTicket loaded = (CleanerTicket) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return CleanerTicket.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerTicket o = (CleanerTicket)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerTicket removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerTicket added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdToSerialMap(obj, del);
        updateSiteIdToSerialOneMap(obj, del);
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
        CleanerTicket o = (CleanerTicket)obj;

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
            if (m_debug) m_logger.debug("CleanerTicket removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerTicket added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    






    public List getBySiteIdSerialList(long SiteId, String Serial) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToSerialToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToSerialToMap.get(keySiteId);

     	    String keySerial =  Serial;
			if (  keySerial == null || keySerial.isEmpty()) return new ArrayList();
            
            if ( mapSiteId.containsKey(keySerial)){
                return new ArrayList( ((Map)mapSiteId.get(keySerial)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdSerialList(long SiteId, String Serial) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToSerialToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToSerialToMap.get(keySiteId);

     	    String keySerial =  Serial;
			if (  keySerial == null || keySerial.isEmpty()) return new HashMap();
            
            if ( mapSiteId.containsKey(keySerial)){
                return (Map)mapSiteId.get(keySerial);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToSerialMap(Object obj, boolean del) {
        CleanerTicket o = (CleanerTicket)obj;

   	    String keySerial =  o.getSerial();
		if (  keySerial == null || keySerial.isEmpty()) return;

        if (del) {
            // delete from SiteIdSerialToMap
            Map mapSiteId  = (Map) m_SiteIdToSerialToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapSerial = (Map) mapSiteId.get(keySerial);
                if (mapSerial != null){
                    mapSerial.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed CleanerTicket from m_SiteIdToSerialToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getSerial());
        }
        else {
            
            // add to SiteIdSerialToMap
            Map mapSiteId  = (Map) m_SiteIdToSerialToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToSerialToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapSerial = (Map) mapSiteId.get(keySerial);
            
            if ( mapSerial == null) {
                mapSerial = new TreeMap();
                mapSiteId.put(keySerial, mapSerial);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapSerial.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdSerialToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    



	// listOneToOneStringToSiteIdKey - Serial

    public  CleanerTicket getBySiteIdToSerial(long siteId, String Serial) {

        Long keySiteIdTo  = new Long(siteId);
        if (m_SiteIdToSerialToOneMap.containsKey(keySiteIdTo)) {
            
            Map mapSiteIdTo = (Map)m_SiteIdToSerialToOneMap.get(keySiteIdTo);

     	    String keySerial = Serial;
            
            if ( keySerial!= null && mapSiteIdTo.containsKey(keySerial)){
                return ( CleanerTicket)mapSiteIdTo.get(keySerial);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    public List getByOrderSerial(){
        List ret = new ArrayList();
    
        List contentsBySerial = new ArrayList( m_SiteIdToSerialToOneMap.values());
        Map map  = new TreeMap();
        
        for (Iterator iterator = contentsBySerial.iterator(); iterator.hasNext();) {
            CleanerTicket obj = (CleanerTicket) iterator.next();
            if ( obj.getSerial() == null ) continue;
            map.put(obj.getSerial(), obj);
        }
        
        return new ArrayList(map.values());
    }

    private void updateSiteIdToSerialOneMap(Object obj, boolean del) {
        CleanerTicket o = (CleanerTicket)obj;

   	    String keySerial = o.getSerial();

		if ( keySerial == null || keySerial.isEmpty() ) {
			m_logger.warn("Passed key is null in updateSiteIdToSerialOneMap for the CleanerTicket object. ABORTED id = " + o.getId());
            return;
        }

        if (del) {
            // delete from PollIdSerialToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToSerialToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo != null ) {
                if (mapSiteIdTo.containsKey(keySerial)){
                    mapSiteIdTo.remove(keySerial);
                }
            }
            m_logger.debug("removed CleanerTicket from m_SiteIdToSerialToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getSerial());
        }
        else {
            
            // add to SiteIdToSerialToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToSerialToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo == null ) {
                mapSiteIdTo = new TreeMap();
                m_SiteIdToSerialToOneMap.put(new Long(o.getSiteId()), mapSiteIdTo);
                if (m_debug) m_logger.debug("created new   mapSiteIdTo for " + o.getSiteId());
            }
            
			CleanerTicket replaced = (CleanerTicket) mapSiteIdTo.put(keySerial,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdToSerialOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("CleanerTicket added to SiteIdToSerialToOneMap " + o.getId() + " to " + o.getSiteId());
        }
    }    


	//

    public static void main(String[] args) throws Exception {

        CleanerTicketDS ds = new CleanerTicketDS();
        CleanerTicket obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerTicket createDefault(){

        CleanerTicket _CleanerTicket = new CleanerTicket();        
	    _CleanerTicket = new CleanerTicket();// CleanerTicketDS.getInstance().getDeafult();
        return _CleanerTicket;
    }

    public static CleanerTicket copy(CleanerTicket org){

    	CleanerTicket ret = new CleanerTicket();

		ret.setSerial(org.getSerial());
		ret.setParentTicketId(org.getParentTicketId());
		ret.setCustomerId(org.getCustomerId());
		ret.setEnterUserId(org.getEnterUserId());
		ret.setLocationId(org.getLocationId());
		ret.setNote(org.getNote());
		ret.setCompleted(org.getCompleted());
		ret.setOnhold(org.getOnhold());
		ret.setOriginalTicketId(org.getOriginalTicketId());
		ret.setReturned(org.getReturned());
		ret.setReturnedReasonText(org.getReturnedReasonText());
		ret.setReturnedNote(org.getReturnedNote());
		ret.setTotalCharge(org.getTotalCharge());
		ret.setFinalCharge(org.getFinalCharge());
		ret.setDiscountId(org.getDiscountId());
		ret.setDiscountAmount(org.getDiscountAmount());
		ret.setDiscountNote(org.getDiscountNote());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());
		ret.setTimeCompleted(org.getTimeCompleted());
		ret.setTimeOnhold(org.getTimeOnhold());

        return ret;
    }

	public static void objectToLog(CleanerTicket cleanerTicket, Logger logger){
		logger.debug("CleanerTicket [" + cleanerTicket.getId() + "]" + objectToString(cleanerTicket));		
    }

	public static String objectToString(CleanerTicket cleanerTicket){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerTicket=");
		buf.append("Id=").append(cleanerTicket.getId()).append(", ");
		buf.append("SiteId=").append(cleanerTicket.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerTicket cleanerTicket = (CleanerTicket)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerTicket=");
		buf.append("Id=").append(cleanerTicket.getId()).append(", ");
		buf.append("SiteId=").append(cleanerTicket.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
