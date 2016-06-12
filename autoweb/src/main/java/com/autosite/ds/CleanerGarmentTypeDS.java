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


import com.autosite.db.CleanerGarmentTypeDAO;
import com.autosite.db.CleanerGarmentTypeExtentDAO;
import com.autosite.db.CleanerGarmentType;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerGarmentTypeDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerGarmentTypeDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerGarmentTypeDS m_CleanerGarmentTypeDS = new CleanerGarmentTypeDSExtent();



    public static CleanerGarmentTypeDS getInstance() {
        return m_CleanerGarmentTypeDS;
    }

    public static synchronized CleanerGarmentTypeDS getInstance(long id) {
        CleanerGarmentTypeDS ret = (CleanerGarmentTypeDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerGarmentTypeDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerGarmentTypeDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerGarmentTypeDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerGarmentType loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerGarmentTypeDS() {
        m_dao = new CleanerGarmentTypeExtentDAO();
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

    protected CleanerGarmentTypeDS(long id) {
        m_dao = new CleanerGarmentTypeDAO();
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

    public CleanerGarmentType getById(Long id) {
        return (CleanerGarmentType) m_idToMap.get(id);
    }

    public CleanerGarmentType getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                CleanerGarmentType loaded = (CleanerGarmentType) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return CleanerGarmentType.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerGarmentType o = (CleanerGarmentType)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerGarmentType removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerGarmentType added to DS " + o.getId());
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
        CleanerGarmentType o = (CleanerGarmentType)obj;

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
            if (m_debug) m_logger.debug("CleanerGarmentType removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerGarmentType added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        CleanerGarmentTypeDS ds = new CleanerGarmentTypeDS();
        CleanerGarmentType obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerGarmentType createDefault(){

        CleanerGarmentType _CleanerGarmentType = new CleanerGarmentType();        
	    _CleanerGarmentType = new CleanerGarmentType();// CleanerGarmentTypeDS.getInstance().getDeafult();
        return _CleanerGarmentType;
    }

    public static CleanerGarmentType copy(CleanerGarmentType org){

    	CleanerGarmentType ret = new CleanerGarmentType();

		ret.setTitle(org.getTitle());
		ret.setImagePath(org.getImagePath());

        return ret;
    }

	public static void objectToLog(CleanerGarmentType cleanerGarmentType, Logger logger){
		logger.debug("CleanerGarmentType [" + cleanerGarmentType.getId() + "]" + objectToString(cleanerGarmentType));		
    }

	public static String objectToString(CleanerGarmentType cleanerGarmentType){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerGarmentType=");
		buf.append("Id=").append(cleanerGarmentType.getId()).append(", ");
		buf.append("SiteId=").append(cleanerGarmentType.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerGarmentType cleanerGarmentType = (CleanerGarmentType)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerGarmentType=");
		buf.append("Id=").append(cleanerGarmentType.getId()).append(", ");
		buf.append("SiteId=").append(cleanerGarmentType.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
