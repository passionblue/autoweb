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


import com.autosite.db.CleanerCustomerNotificationDAO;
import com.autosite.db.CleanerCustomerNotificationExtentDAO;
import com.autosite.db.CleanerCustomerNotification;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerCustomerNotificationDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerCustomerNotificationDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerCustomerNotificationDS m_CleanerCustomerNotificationDS = new CleanerCustomerNotificationDSExtent();



    public static CleanerCustomerNotificationDS getInstance() {
        return m_CleanerCustomerNotificationDS;
    }

    public static synchronized CleanerCustomerNotificationDS getInstance(long id) {
        CleanerCustomerNotificationDS ret = (CleanerCustomerNotificationDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerCustomerNotificationDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerCustomerNotificationDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerCustomerNotificationDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerCustomerNotification loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerCustomerNotificationDS() {
        m_dao = new CleanerCustomerNotificationExtentDAO();
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

    protected CleanerCustomerNotificationDS(long id) {
        m_dao = new CleanerCustomerNotificationDAO();
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

    public CleanerCustomerNotification getById(Long id) {
        return (CleanerCustomerNotification) m_idToMap.get(id);
    }

    public CleanerCustomerNotification getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                CleanerCustomerNotification loaded = (CleanerCustomerNotification) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return CleanerCustomerNotification.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerCustomerNotification o = (CleanerCustomerNotification)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerCustomerNotification removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerCustomerNotification added to DS " + o.getId());
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
        CleanerCustomerNotification o = (CleanerCustomerNotification)obj;

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
            if (m_debug) m_logger.debug("CleanerCustomerNotification removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerCustomerNotification added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        CleanerCustomerNotificationDS ds = new CleanerCustomerNotificationDS();
        CleanerCustomerNotification obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerCustomerNotification createDefault(){

        CleanerCustomerNotification _CleanerCustomerNotification = new CleanerCustomerNotification();        
	    _CleanerCustomerNotification = new CleanerCustomerNotification();// CleanerCustomerNotificationDS.getInstance().getDeafult();
        return _CleanerCustomerNotification;
    }

    public static CleanerCustomerNotification copy(CleanerCustomerNotification org){

    	CleanerCustomerNotification ret = new CleanerCustomerNotification();

		ret.setCustomerId(org.getCustomerId());
		ret.setReasonforId(org.getReasonforId());
		ret.setReasonforTarget(org.getReasonforTarget());
		ret.setNotificationType(org.getNotificationType());
		ret.setSourceType(org.getSourceType());
		ret.setTriggerType(org.getTriggerType());
		ret.setIsRetransmit(org.getIsRetransmit());
		ret.setMethodType(org.getMethodType());
		ret.setTemplateType(org.getTemplateType());
		ret.setContent(org.getContent());
		ret.setDestination(org.getDestination());
		ret.setReference(org.getReference());
		ret.setTimeScheduled(org.getTimeScheduled());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeSent(org.getTimeSent());

        return ret;
    }

	public static void objectToLog(CleanerCustomerNotification cleanerCustomerNotification, Logger logger){
		logger.debug("CleanerCustomerNotification [" + cleanerCustomerNotification.getId() + "]" + objectToString(cleanerCustomerNotification));		
    }

	public static String objectToString(CleanerCustomerNotification cleanerCustomerNotification){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerCustomerNotification=");
		buf.append("Id=").append(cleanerCustomerNotification.getId()).append(", ");
		buf.append("SiteId=").append(cleanerCustomerNotification.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerCustomerNotification cleanerCustomerNotification = (CleanerCustomerNotification)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerCustomerNotification=");
		buf.append("Id=").append(cleanerCustomerNotification.getId()).append(", ");
		buf.append("SiteId=").append(cleanerCustomerNotification.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
