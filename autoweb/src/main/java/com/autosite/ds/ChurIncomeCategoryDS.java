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

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.jtrend.service.DomainStore;
import com.autosite.holder.DataHolderObject;


import com.autosite.db.ChurIncomeCategoryDAO;
import com.autosite.db.ChurIncomeCategory;
import com.surveygen.db.BaseMemoryDAO;

public class ChurIncomeCategoryDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(ChurIncomeCategoryDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static ChurIncomeCategoryDS m_ChurIncomeCategoryDS = new ChurIncomeCategoryDSExtent();



    public static ChurIncomeCategoryDS getInstance() {
        return m_ChurIncomeCategoryDS;
    }

    public static synchronized ChurIncomeCategoryDS getInstance(long id) {
        ChurIncomeCategoryDS ret = (ChurIncomeCategoryDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ChurIncomeCategoryDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ChurIncomeCategoryDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ChurIncomeCategoryDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ChurIncomeCategory loaded from DB. num=" + m_idToMap.size());
        
    }


    protected ChurIncomeCategoryDS() {
        m_dao = new ChurIncomeCategoryDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ChurIncomeCategoryDS(long id) {
        m_dao = new ChurIncomeCategoryDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ChurIncomeCategory getById(Long id) {
        return (ChurIncomeCategory) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        ChurIncomeCategory o = (ChurIncomeCategory)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ChurIncomeCategory removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ChurIncomeCategory added to DS " + o.getId());
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
            return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        ChurIncomeCategory o = (ChurIncomeCategory)obj;

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
            if (m_debug) m_logger.debug("ChurIncomeCategory removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ChurIncomeCategory added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    












    public static void main(String[] args) throws Exception {

        ChurIncomeCategoryDS ds = new ChurIncomeCategoryDS();
        ChurIncomeCategory obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static ChurIncomeCategory createDefault(){

        ChurIncomeCategory _ChurIncomeCategory = new ChurIncomeCategory();        
	    _ChurIncomeCategory = new ChurIncomeCategory();// ChurIncomeCategoryDS.getInstance().getDeafult();
        return _ChurIncomeCategory;
    }

    public static ChurIncomeCategory copy(ChurIncomeCategory org){

    	ChurIncomeCategory ret = new ChurIncomeCategory();

		ret.setIncomeCategory(org.getIncomeCategory());
		ret.setDisplay(org.getDisplay());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(ChurIncomeCategory churIncomeCategory, Logger logger){
		logger.debug("ChurIncomeCategory [" + churIncomeCategory.getId() + "]" + objectToString(churIncomeCategory));		
    }


	public static String objectToString(ChurIncomeCategory churIncomeCategory){
		StringBuilder buf = new StringBuilder();
        buf.append("ChurIncomeCategory=");
		buf.append("Id=").append(churIncomeCategory.getId()).append(", ");
		buf.append("SiteId=").append(churIncomeCategory.getSiteId()).append(", ");
		return buf.toString();    
    }





	// Empty methods for 

}
