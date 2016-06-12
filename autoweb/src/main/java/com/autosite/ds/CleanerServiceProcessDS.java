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


import com.autosite.db.CleanerServiceProcessDAO;
import com.autosite.db.CleanerServiceProcess;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerServiceProcessDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerServiceProcessDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerServiceProcessDS m_CleanerServiceProcessDS = new CleanerServiceProcessDSExtent();



    public static CleanerServiceProcessDS getInstance() {
        return m_CleanerServiceProcessDS;
    }

    public static synchronized CleanerServiceProcessDS getInstance(long id) {
        CleanerServiceProcessDS ret = (CleanerServiceProcessDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerServiceProcessDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerServiceProcessDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerServiceProcessDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerServiceProcess loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerServiceProcessDS() {
        m_dao = new CleanerServiceProcessDAO();
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

    protected CleanerServiceProcessDS(long id) {
        m_dao = new CleanerServiceProcessDAO();
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

    public CleanerServiceProcess getById(Long id) {
        return (CleanerServiceProcess) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerServiceProcess o = (CleanerServiceProcess)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerServiceProcess removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerServiceProcess added to DS " + o.getId());
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
        CleanerServiceProcess o = (CleanerServiceProcess)obj;

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
            if (m_debug) m_logger.debug("CleanerServiceProcess removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerServiceProcess added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        CleanerServiceProcessDS ds = new CleanerServiceProcessDS();
        CleanerServiceProcess obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerServiceProcess createDefault(){

        CleanerServiceProcess _CleanerServiceProcess = new CleanerServiceProcess();        
	    _CleanerServiceProcess = new CleanerServiceProcess();// CleanerServiceProcessDS.getInstance().getDeafult();
        return _CleanerServiceProcess;
    }

    public static CleanerServiceProcess copy(CleanerServiceProcess org){

    	CleanerServiceProcess ret = new CleanerServiceProcess();

		ret.setTicketId(org.getTicketId());
		ret.setProcessUserId(org.getProcessUserId());
		ret.setProcessType(org.getProcessType());
		ret.setTimeStarted(org.getTimeStarted());
		ret.setTimeEnded(org.getTimeEnded());
		ret.setNote(org.getNote());

        return ret;
    }

	public static void objectToLog(CleanerServiceProcess cleanerServiceProcess, Logger logger){
		logger.debug("CleanerServiceProcess [" + cleanerServiceProcess.getId() + "]" + objectToString(cleanerServiceProcess));		
    }

	public static String objectToString(CleanerServiceProcess cleanerServiceProcess){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerServiceProcess=");
		buf.append("Id=").append(cleanerServiceProcess.getId()).append(", ");
		buf.append("SiteId=").append(cleanerServiceProcess.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerServiceProcess cleanerServiceProcess = (CleanerServiceProcess)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerServiceProcess=");
		buf.append("Id=").append(cleanerServiceProcess.getId()).append(", ");
		buf.append("SiteId=").append(cleanerServiceProcess.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
