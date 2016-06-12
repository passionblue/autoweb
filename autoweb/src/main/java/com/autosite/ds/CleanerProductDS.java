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


import com.autosite.db.CleanerProductDAO;
import com.autosite.db.CleanerProductExtentDAO;
import com.autosite.db.CleanerProduct;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerProductDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerProductDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerProductDS m_CleanerProductDS = new CleanerProductDSExtent();



    public static CleanerProductDS getInstance() {
        return m_CleanerProductDS;
    }

    public static synchronized CleanerProductDS getInstance(long id) {
        CleanerProductDS ret = (CleanerProductDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerProductDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerProductDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerProductDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerProduct loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerProductDS() {
        m_dao = new CleanerProductExtentDAO();
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

    protected CleanerProductDS(long id) {
        m_dao = new CleanerProductDAO();
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

    public CleanerProduct getById(Long id) {
        return (CleanerProduct) m_idToMap.get(id);
    }

    public CleanerProduct getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                CleanerProduct loaded = (CleanerProduct) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return CleanerProduct.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerProduct o = (CleanerProduct)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerProduct removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerProduct added to DS " + o.getId());
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
        CleanerProduct o = (CleanerProduct)obj;

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
            if (m_debug) m_logger.debug("CleanerProduct removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerProduct added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        CleanerProductDS ds = new CleanerProductDS();
        CleanerProduct obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerProduct createDefault(){

        CleanerProduct _CleanerProduct = new CleanerProduct();        
	    _CleanerProduct = new CleanerProduct();// CleanerProductDS.getInstance().getDeafult();
        return _CleanerProduct;
    }

    public static CleanerProduct copy(CleanerProduct org){

    	CleanerProduct ret = new CleanerProduct();

		ret.setGarmentTypeId(org.getGarmentTypeId());
		ret.setGarmentServiceId(org.getGarmentServiceId());
		ret.setRegularPrice(org.getRegularPrice());
		ret.setNote(org.getNote());

        return ret;
    }

	public static void objectToLog(CleanerProduct cleanerProduct, Logger logger){
		logger.debug("CleanerProduct [" + cleanerProduct.getId() + "]" + objectToString(cleanerProduct));		
    }

	public static String objectToString(CleanerProduct cleanerProduct){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerProduct=");
		buf.append("Id=").append(cleanerProduct.getId()).append(", ");
		buf.append("SiteId=").append(cleanerProduct.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerProduct cleanerProduct = (CleanerProduct)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerProduct=");
		buf.append("Id=").append(cleanerProduct.getId()).append(", ");
		buf.append("SiteId=").append(cleanerProduct.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
