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


import com.autosite.db.ChurExpenseCategoryDAO;
import com.autosite.db.ChurExpenseCategory;
import com.surveygen.db.BaseMemoryDAO;

public class ChurExpenseCategoryDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(ChurExpenseCategoryDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static ChurExpenseCategoryDS m_ChurExpenseCategoryDS = new ChurExpenseCategoryDSExtent();



    public static ChurExpenseCategoryDS getInstance() {
        return m_ChurExpenseCategoryDS;
    }

    public static synchronized ChurExpenseCategoryDS getInstance(long id) {
        ChurExpenseCategoryDS ret = (ChurExpenseCategoryDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ChurExpenseCategoryDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ChurExpenseCategoryDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ChurExpenseCategoryDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ChurExpenseCategory loaded from DB. num=" + m_idToMap.size());
        
    }


    protected ChurExpenseCategoryDS() {
        m_dao = new ChurExpenseCategoryDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ChurExpenseCategoryDS(long id) {
        m_dao = new ChurExpenseCategoryDAO();
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

    public ChurExpenseCategory getById(Long id) {
        return (ChurExpenseCategory) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        ChurExpenseCategory o = (ChurExpenseCategory)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ChurExpenseCategory removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ChurExpenseCategory added to DS " + o.getId());
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
        ChurExpenseCategory o = (ChurExpenseCategory)obj;

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
            if (m_debug) m_logger.debug("ChurExpenseCategory removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ChurExpenseCategory added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    












    public static void main(String[] args) throws Exception {

        ChurExpenseCategoryDS ds = new ChurExpenseCategoryDS();
        ChurExpenseCategory obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static ChurExpenseCategory createDefault(){

        ChurExpenseCategory _ChurExpenseCategory = new ChurExpenseCategory();        
	    _ChurExpenseCategory = new ChurExpenseCategory();// ChurExpenseCategoryDS.getInstance().getDeafult();
        return _ChurExpenseCategory;
    }

    public static ChurExpenseCategory copy(ChurExpenseCategory org){

    	ChurExpenseCategory ret = new ChurExpenseCategory();

		ret.setExpenseCategory(org.getExpenseCategory());
		ret.setDisplay(org.getDisplay());

        return ret;
    }

	public static void objectToLog(ChurExpenseCategory churExpenseCategory, Logger logger){
		logger.debug("ChurExpenseCategory [" + churExpenseCategory.getId() + "]" + objectToString(churExpenseCategory));		
    }


	public static String objectToString(ChurExpenseCategory churExpenseCategory){
		StringBuilder buf = new StringBuilder();
        buf.append("ChurExpenseCategory=");
		buf.append("Id=").append(churExpenseCategory.getId()).append(", ");
		buf.append("SiteId=").append(churExpenseCategory.getSiteId()).append(", ");
		return buf.toString();    
    }





	// Empty methods for 

}
