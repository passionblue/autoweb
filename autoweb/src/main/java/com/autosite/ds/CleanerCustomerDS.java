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


import com.autosite.db.CleanerCustomerDAO;
import com.autosite.db.CleanerCustomerDAO2;
import com.autosite.db.CleanerCustomerExtentDAO;
import com.autosite.db.CleanerCustomer;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerCustomerDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerCustomerDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerCustomerDS m_CleanerCustomerDS = new CleanerCustomerDSExtent();



    public static CleanerCustomerDS getInstance() {
        return m_CleanerCustomerDS;
    }

    public static synchronized CleanerCustomerDS getInstance(long id) {
        CleanerCustomerDS ret = (CleanerCustomerDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerCustomerDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerCustomerDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerCustomerDAO2)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerCustomer loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerCustomerDS() {
        m_dao = new CleanerCustomerExtentDAO();
        m_idToMap = new ConcurrentHashMap();

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

    protected CleanerCustomerDS(long id) {
        m_dao = new CleanerCustomerDAO2();
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

    public CleanerCustomer getById(Long id) {
        return (CleanerCustomer) m_idToMap.get(id);
    }

    public CleanerCustomer getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                CleanerCustomer loaded = (CleanerCustomer) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return CleanerCustomer.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerCustomer o = (CleanerCustomer)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerCustomer removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerCustomer added to DS " + o.getId());
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
            if ( m_aggregateAllSites) 
                return new ArrayList(((Map)m_SiteIdToMap.get(new Long(0))).values() ); //m_aggregateAllSites=true
            else 
                return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        CleanerCustomer o = (CleanerCustomer)obj;

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
            if (m_debug) m_logger.debug("CleanerCustomer removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerCustomer added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        CleanerCustomerDS ds = new CleanerCustomerDS();
        CleanerCustomer obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerCustomer createDefault(){

        CleanerCustomer _CleanerCustomer = new CleanerCustomer();        
	    _CleanerCustomer = new CleanerCustomer();// CleanerCustomerDS.getInstance().getDeafult();
        return _CleanerCustomer;
    }

    public static CleanerCustomer copy(CleanerCustomer org){

    	CleanerCustomer ret = new CleanerCustomer();

		ret.setAutositeUserId(org.getAutositeUserId());
		ret.setEmail(org.getEmail());
		ret.setPhone(org.getPhone());
		ret.setPhone2(org.getPhone2());
		ret.setPhonePreferred(org.getPhonePreferred());
		ret.setTitle(org.getTitle());
		ret.setLastName(org.getLastName());
		ret.setFirstName(org.getFirstName());
		ret.setAddress(org.getAddress());
		ret.setApt(org.getApt());
		ret.setCity(org.getCity());
		ret.setState(org.getState());
		ret.setZip(org.getZip());
		ret.setCustomerType(org.getCustomerType());
		ret.setPayType(org.getPayType());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setRemoteIp(org.getRemoteIp());
		ret.setNote(org.getNote());
		ret.setPickupNote(org.getPickupNote());
		ret.setDeliveryNote(org.getDeliveryNote());
		ret.setDisabled(org.getDisabled());
		ret.setTimeUpdated(org.getTimeUpdated());
		ret.setPickupDeliveryDisallowed(org.getPickupDeliveryDisallowed());
		ret.setHandleInst(org.getHandleInst());

        return ret;
    }

	public static void objectToLog(CleanerCustomer cleanerCustomer, Logger logger){
		logger.debug("CleanerCustomer [" + cleanerCustomer.getId() + "]" + objectToString(cleanerCustomer));		
    }

	public static String objectToString(CleanerCustomer cleanerCustomer){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerCustomer=");
		buf.append("Id=").append(cleanerCustomer.getId()).append(", ");
		buf.append("SiteId=").append(cleanerCustomer.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerCustomer cleanerCustomer = (CleanerCustomer)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerCustomer=");
		buf.append("Id=").append(cleanerCustomer.getId()).append(", ");
		buf.append("SiteId=").append(cleanerCustomer.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
