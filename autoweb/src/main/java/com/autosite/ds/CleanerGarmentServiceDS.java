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


import com.autosite.db.CleanerGarmentServiceDAO;
import com.autosite.db.CleanerGarmentServiceExtentDAO;
import com.autosite.db.CleanerGarmentService;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerGarmentServiceDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerGarmentServiceDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerGarmentServiceDS m_CleanerGarmentServiceDS = new CleanerGarmentServiceDSExtent();



    public static CleanerGarmentServiceDS getInstance() {
        return m_CleanerGarmentServiceDS;
    }

    public static synchronized CleanerGarmentServiceDS getInstance(long id) {
        CleanerGarmentServiceDS ret = (CleanerGarmentServiceDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerGarmentServiceDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerGarmentServiceDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerGarmentServiceDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerGarmentService loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerGarmentServiceDS() {
        m_dao = new CleanerGarmentServiceExtentDAO();
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

    protected CleanerGarmentServiceDS(long id) {
        m_dao = new CleanerGarmentServiceDAO();
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

    public CleanerGarmentService getById(Long id) {
        return (CleanerGarmentService) m_idToMap.get(id);
    }

    public CleanerGarmentService getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                CleanerGarmentService loaded = (CleanerGarmentService) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return CleanerGarmentService.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerGarmentService o = (CleanerGarmentService)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerGarmentService removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerGarmentService added to DS " + o.getId());
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
        CleanerGarmentService o = (CleanerGarmentService)obj;

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
            if (m_debug) m_logger.debug("CleanerGarmentService removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerGarmentService added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        CleanerGarmentServiceDS ds = new CleanerGarmentServiceDS();
        CleanerGarmentService obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerGarmentService createDefault(){

        CleanerGarmentService _CleanerGarmentService = new CleanerGarmentService();        
	    _CleanerGarmentService = new CleanerGarmentService();// CleanerGarmentServiceDS.getInstance().getDeafult();
        return _CleanerGarmentService;
    }

    public static CleanerGarmentService copy(CleanerGarmentService org){

    	CleanerGarmentService ret = new CleanerGarmentService();

		ret.setTitle(org.getTitle());
		ret.setNote(org.getNote());
		ret.setImagePath(org.getImagePath());

        return ret;
    }

	public static void objectToLog(CleanerGarmentService cleanerGarmentService, Logger logger){
		logger.debug("CleanerGarmentService [" + cleanerGarmentService.getId() + "]" + objectToString(cleanerGarmentService));		
    }

	public static String objectToString(CleanerGarmentService cleanerGarmentService){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerGarmentService=");
		buf.append("Id=").append(cleanerGarmentService.getId()).append(", ");
		buf.append("SiteId=").append(cleanerGarmentService.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerGarmentService cleanerGarmentService = (CleanerGarmentService)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerGarmentService=");
		buf.append("Id=").append(cleanerGarmentService.getId()).append(", ");
		buf.append("SiteId=").append(cleanerGarmentService.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
