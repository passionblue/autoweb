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
import com.autosite.db.AutositeDataObject;
import com.jtrend.service.DomainStore;
import com.autosite.holder.DataHolderObject;


import com.autosite.db.ChurExpenseDAO;
import com.autosite.db.ChurExpense;
import com.surveygen.db.BaseMemoryDAO;

public class ChurExpenseDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;

	protected Map m_SiteIdYearToMap;


    protected Map m_SiteIdToPayeeIdToMap;
    protected Map m_SiteIdToWeekToMap;



    private static Logger m_logger = Logger.getLogger(ChurExpenseDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static ChurExpenseDS m_ChurExpenseDS = new ChurExpenseDSExtent();



    public static ChurExpenseDS getInstance() {
        return m_ChurExpenseDS;
    }

    public static synchronized ChurExpenseDS getInstance(long id) {
        ChurExpenseDS ret = (ChurExpenseDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ChurExpenseDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ChurExpenseDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ChurExpenseDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ChurExpense loaded from DB. num=" + m_idToMap.size());
        
    }


    protected ChurExpenseDS() {
        m_dao = new ChurExpenseDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
		m_SiteIdYearToMap = new ConcurrentHashMap();
        m_SiteIdToPayeeIdToMap = new ConcurrentHashMap();
        m_SiteIdToWeekToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ChurExpenseDS(long id) {
        m_dao = new ChurExpenseDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToPayeeIdToMap = new ConcurrentHashMap();
        m_SiteIdToWeekToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ChurExpense getById(Long id) {
        return (ChurExpense) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        ChurExpense o = (ChurExpense)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ChurExpense removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ChurExpense added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
		updateSiteIdYearMap(obj, del);
        updateSiteIdToPayeeIdMap(obj, del);
        updateSiteIdToWeekMap(obj, del);
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
        ChurExpense o = (ChurExpense)obj;

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
            if (m_debug) m_logger.debug("ChurExpense removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ChurExpense added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    



        
    public List getBySiteIdYear(long SiteId, int Year) {

     	    Long keySiteId = new Long(SiteId);

        if (m_SiteIdYearToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdYearToMap.get(keySiteId);

     	    Integer keyYear = new Integer(Year);
            
            if ( mapSiteId.containsKey(keyYear)){
                return new ArrayList( ((Map)mapSiteId.get(keyYear)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdYear(long SiteId, int Year) {

     	    Long keySiteId = new Long(SiteId);

        if (m_SiteIdYearToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdYearToMap.get(keySiteId);

     	    Integer keyYear = new Integer(Year);
            
            if ( mapSiteId.containsKey(keyYear)){
                return (Map)mapSiteId.get(keyYear);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdYearMap(Object obj, boolean del) {
        ChurExpense o = (ChurExpense)obj;

     	    Long keySiteId = new Long(o.getSiteId());

   	    Integer keyYear = new Integer(o.getYear());

        if (del) {
            // delete from SiteIdYearToMap
            Map mapSiteId  = (Map) m_SiteIdYearToMap.get(keySiteId);
            if ( mapSiteId != null ) {
                Map mapYear = (Map) mapSiteId.get(keyYear);
                if (mapYear != null){
                    mapYear.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed ChurExpense from m_SiteIdYearToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getYear());
        }
        else {
            
            // add to SiteIdYearToMap
            Map mapSiteId  = (Map) m_SiteIdYearToMap.get(keySiteId);
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdYearToMap.put(keySiteId, mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapYear = (Map) mapSiteId.get(keyYear);
            
            if ( mapYear == null) {
                mapYear = new TreeMap();
                mapSiteId.put(keyYear, mapYear);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapYear.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdYearToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        


    public List getBySiteIdToPayeeIdList (long SiteId, long PayeeId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToPayeeIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToPayeeIdToMap.get(keySiteId);

     	    Long keyPayeeId = PayeeId;
            
            if ( mapSiteId.containsKey(keyPayeeId)){
                return new ArrayList( ((Map)mapSiteId.get(keyPayeeId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdPayeeId(long SiteId, long PayeeId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToPayeeIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToPayeeIdToMap.get(keySiteId);

     	    Long keyPayeeId = new Long(PayeeId);
            
            if ( mapSiteId.containsKey(keyPayeeId)){
                return (Map)mapSiteId.get(keyPayeeId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToPayeeIdMap(Object obj, boolean del) {
        ChurExpense o = (ChurExpense)obj;

   	    Long keyPayeeId = new Long(o.getPayeeId());

        if (del) {
            // delete from SiteIdPayeeIdToMap
            Map mapSiteId  = (Map) m_SiteIdToPayeeIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapPayeeId = (Map) mapSiteId.get(keyPayeeId);
                if (mapPayeeId != null){
                    mapPayeeId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed ChurExpense from m_SiteIdToPayeeIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getPayeeId());
        }
        else {
            
            // add to SiteIdPayeeIdToMap
            Map mapSiteId  = (Map) m_SiteIdToPayeeIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToPayeeIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapPayeeId = (Map) mapSiteId.get(keyPayeeId);
            
            if ( mapPayeeId == null) {
                mapPayeeId = new TreeMap();
                mapSiteId.put(keyPayeeId, mapPayeeId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapPayeeId.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdPayeeIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    

    public List getBySiteIdWeekList(long SiteId, String Week) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToWeekToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToWeekToMap.get(keySiteId);

     	    String keyWeek =  Week;
			if (  keyWeek == null || keyWeek.isEmpty()) return new ArrayList();
            
            if ( mapSiteId.containsKey(keyWeek)){
                return new ArrayList( ((Map)mapSiteId.get(keyWeek)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdWeekList(long SiteId, String Week) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToWeekToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToWeekToMap.get(keySiteId);

     	    String keyWeek =  Week;
			if (  keyWeek == null || keyWeek.isEmpty()) return new HashMap();
            
            if ( mapSiteId.containsKey(keyWeek)){
                return (Map)mapSiteId.get(keyWeek);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToWeekMap(Object obj, boolean del) {
        ChurExpense o = (ChurExpense)obj;

   	    String keyWeek =  o.getWeek();
		if (  keyWeek == null || keyWeek.isEmpty()) return;

        if (del) {
            // delete from SiteIdWeekToMap
            Map mapSiteId  = (Map) m_SiteIdToWeekToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapWeek = (Map) mapSiteId.get(keyWeek);
                if (mapWeek != null){
                    mapWeek.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed ChurExpense from m_SiteIdToWeekToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getWeek());
        }
        else {
            
            // add to SiteIdWeekToMap
            Map mapSiteId  = (Map) m_SiteIdToWeekToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToWeekToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapWeek = (Map) mapSiteId.get(keyWeek);
            
            if ( mapWeek == null) {
                mapWeek = new TreeMap();
                mapSiteId.put(keyWeek, mapWeek);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapWeek.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdWeekToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    






    public static void main(String[] args) throws Exception {

        ChurExpenseDS ds = new ChurExpenseDS();
        ChurExpense obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static ChurExpense createDefault(){

        ChurExpense _ChurExpense = new ChurExpense();        
	    _ChurExpense = new ChurExpense();// ChurExpenseDS.getInstance().getDeafult();
        return _ChurExpense;
    }

    public static ChurExpense copy(ChurExpense org){

    	ChurExpense ret = new ChurExpense();

		ret.setYear(org.getYear());
		ret.setWeek(org.getWeek());
		ret.setExpenseItemId(org.getExpenseItemId());
		ret.setPayeeId(org.getPayeeId());
		ret.setAmount(org.getAmount());
		ret.setIsCash(org.getIsCash());
		ret.setCheckNumber(org.getCheckNumber());
		ret.setCheckCleared(org.getCheckCleared());
		ret.setComment(org.getComment());
		ret.setCancelled(org.getCancelled());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(ChurExpense churExpense, Logger logger){
		logger.debug("ChurExpense [" + churExpense.getId() + "]" + objectToString(churExpense));		
    }


	public static String objectToString(ChurExpense churExpense){
		StringBuilder buf = new StringBuilder();
        buf.append("ChurExpense=");
		buf.append("Id=").append(churExpense.getId()).append(", ");
		buf.append("SiteId=").append(churExpense.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		ChurExpense churExpense = (ChurExpense)object;
		StringBuilder buf = new StringBuilder();
        buf.append("ChurExpense=");
		buf.append("Id=").append(churExpense.getId()).append(", ");
		buf.append("SiteId=").append(churExpense.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
