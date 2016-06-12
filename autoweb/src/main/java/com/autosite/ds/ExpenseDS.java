/* 
Template last modification history:
03152015 - Mongo repository was added to DS class. Repository is proxy based, hard to use base class handling. So each repository is implemented here. 
(1) repostory init (2) put/delete/update added here. 

Source Generated: Sun Jul 12 20:40:22 EDT 2015
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


import com.autosite.db.ExpenseDAO;
import com.autosite.db.ExpenseDAO2;
import com.autosite.db.ExpenseExtentDAO;
import com.autosite.db.Expense;
import com.surveygen.db.BaseMemoryDAO;

import org.springframework.data.repository.CrudRepository;
import com.autosite.repository.mongo.AutositeMongoRepositoryFactory;
import com.autosite.repository.mongo.core.ExpenseRepository;

public class ExpenseDS extends AbstractDS implements DomainStore {

	// This flag is set during the generation by aggregateAllSites switch ( in AutoGen config). 
	// This flag is set, DS will invalidates site ID while putting into memory cache so that caller gets access to all items
    protected boolean m_aggregateAllSites = false; 

    protected boolean m_accessDbIfMiss = true; //If true, this will access DB 


	// This will be controlled by code gen config  XXX.dsCacheEnable=true/false
	// 0512 this was implemented for a situation for DS that needs to write only 
	// No need for read in the future. 
    protected boolean m_cacheEnable = true; //If false, cache will not be utilized 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(ExpenseDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static ExpenseDS m_ExpenseDS = new ExpenseDSExtent();

    //Mongo Repository
    protected ExpenseRepository m_repository;

    public static ExpenseDS getInstance() {
        return m_ExpenseDS;
    }

    public static synchronized ExpenseDS getInstance(long id) {
        ExpenseDS ret = (ExpenseDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ExpenseDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ExpenseDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ExpenseDAO2)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("Expense loaded from DB. num=" + m_idToMap.size());
        
    }


    protected ExpenseDS() {
        m_dao = new ExpenseExtentDAO();
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
        m_repository = (ExpenseRepository) AutositeMongoRepositoryFactory.getInstance().getRepository(ExpenseRepository.class);        
    }

    //Mongo
    protected CrudRepository getRepository() {
        return (CrudRepository) m_repository;
    }

    protected ExpenseDS(long id) {
        m_dao = new ExpenseDAO2();
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

    public Expense getById(Long id) {
        Expense ret = (Expense) m_idToMap.get(id);
        
        if ( ret == null && m_accessDbIfMiss ) {
            return getById(id, true);
        }
        return ret;
    }

    public Expense getById(Long id, boolean forSynchWithDB) {
        if (persistEnable() && forSynchWithDB ) {
            try {
                Expense loaded = (Expense) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return (Expense) m_idToMap.get(id);
    }
    
    public String getEntityClass(){
        return Expense.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        Expense o = (Expense)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("Expense removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("Expense added to DS " + o.getId());
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
        Expense o = (Expense)obj;

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
            if (m_debug) m_logger.debug("Expense removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("Expense added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











    //

    @Override
    public boolean delete(Object obj) {
        Object target = obj;
        boolean result = super.delete(target);
        if (m_repository!= null ) {
            if (usingHolderObject() && persistEnable()) {
                target = ((DataHolderObject)obj).getDataObject();
            }   
            try{        
                m_repository.delete((Expense)target);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
            }            
        }
        return result;
    }

    @Override
    public boolean put(Object obj) {
        Object target = obj;
        boolean result = super.put(target);
        if (m_repository!= null ) { 
            if (usingHolderObject() && persistEnable()) {
                target = ((DataHolderObject)obj).getDataObject();
            }           
            try{        
                m_repository.save((Expense)target);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
            }            
        }
        return result;
    }

    @Override
    public boolean update(Object obj) {
        Object target = obj;
        boolean result = super.update(obj);
        if (m_repository!= null) { 
            if (usingHolderObject()  && persistEnable()) {
                target = ((DataHolderObject)obj).getDataObject();
            }           
            try{        
                m_repository.save((Expense)target);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
            }            
        }
        return result;
    }


	//

    public static void main(String[] args) throws Exception {

        ExpenseDS ds = new ExpenseDS();
        Expense obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static Expense createDefault(){

        Expense _Expense = new Expense();        
	    _Expense = new Expense();// ExpenseDS.getInstance().getDeafult();
        return _Expense;
    }

    public static Expense copy(Expense org){

    	Expense ret = new Expense();

		ret.setExpenseItemId(org.getExpenseItemId());
		ret.setAmount(org.getAmount());
		ret.setDescription(org.getDescription());
		ret.setPayMethod(org.getPayMethod());
		ret.setNotExpense(org.getNotExpense());
		ret.setReference(org.getReference());
		ret.setDateExpense(org.getDateExpense());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(Expense expense, Logger logger){
		logger.debug("Expense [" + expense.getId() + "]" + objectToString(expense));		
    }

	public static String objectToString(Expense expense){
		StringBuilder buf = new StringBuilder();
        buf.append("Expense=");
		buf.append("Id=").append(expense.getId()).append(", ");
		buf.append("SiteId=").append(expense.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		Expense expense = (Expense)object;
		StringBuilder buf = new StringBuilder();
        buf.append("Expense=");
		buf.append("Id=").append(expense.getId()).append(", ");
		buf.append("SiteId=").append(expense.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
