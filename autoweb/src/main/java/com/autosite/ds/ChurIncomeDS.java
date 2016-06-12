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

import com.autosite.holder.ChurIncomeDataHolder;

import com.autosite.db.ChurIncomeDAO;
import com.autosite.db.ChurIncomeDAO2;
import com.autosite.db.ChurIncomeExtentDAO;
import com.autosite.db.ChurIncome;
import com.surveygen.db.BaseMemoryDAO;

public class ChurIncomeDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;

	protected Map m_SiteIdYearToMap;


    protected Map m_SiteIdToChurMemberIdToMap;
    protected Map m_SiteIdToWeekToMap;



    private static Logger m_logger = Logger.getLogger(ChurIncomeDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static ChurIncomeDS m_ChurIncomeDS = new ChurIncomeDSExtent();



    public static ChurIncomeDS getInstance() {
        return m_ChurIncomeDS;
    }

    public static synchronized ChurIncomeDS getInstance(long id) {
        ChurIncomeDS ret = (ChurIncomeDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ChurIncomeDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ChurIncomeDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ChurIncomeDAO2)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ChurIncomeDataHolder loaded from DB. num=" + m_idToMap.size());
        
    }


    protected ChurIncomeDS() {
        m_dao = new ChurIncomeExtentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
		m_SiteIdYearToMap = new ConcurrentHashMap();
        m_SiteIdToChurMemberIdToMap = new ConcurrentHashMap();
        m_SiteIdToWeekToMap = new ConcurrentHashMap();
 
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ChurIncomeDS(long id) {
        m_dao = new ChurIncomeDAO2();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToChurMemberIdToMap = new ConcurrentHashMap();
        m_SiteIdToWeekToMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ChurIncomeDataHolder getById(Long id) {
        return (ChurIncomeDataHolder) m_idToMap.get(id);
    }

    public ChurIncomeDataHolder getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                ChurIncomeDataHolder loaded = (ChurIncomeDataHolder) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return ChurIncomeDataHolder.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(wrapByHolder(obj), del);
    }
    public void updateMaps(Object obj, boolean del) {
        ChurIncomeDataHolder o = (ChurIncomeDataHolder)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ChurIncomeDataHolder removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ChurIncomeDataHolder added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
		updateSiteIdYearMap(obj, del);
        updateSiteIdToChurMemberIdMap(obj, del);
        updateSiteIdToWeekMap(obj, del);
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
        ChurIncomeDataHolder o = (ChurIncomeDataHolder)obj;

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
            if (m_debug) m_logger.debug("ChurIncomeDataHolder removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ChurIncomeDataHolder added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        ChurIncomeDataHolder o = (ChurIncomeDataHolder)obj;

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
            m_logger.debug("removed ChurIncomeDataHolder from m_SiteIdYearToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getYear());
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
        


    public List getBySiteIdToChurMemberIdList (long SiteId, long ChurMemberId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToChurMemberIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToChurMemberIdToMap.get(keySiteId);

     	    Long keyChurMemberId = ChurMemberId;
            
            if ( mapSiteId.containsKey(keyChurMemberId)){
                return new ArrayList( ((Map)mapSiteId.get(keyChurMemberId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdChurMemberId(long SiteId, long ChurMemberId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToChurMemberIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToChurMemberIdToMap.get(keySiteId);

     	    Long keyChurMemberId = new Long(ChurMemberId);
            
            if ( mapSiteId.containsKey(keyChurMemberId)){
                return (Map)mapSiteId.get(keyChurMemberId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToChurMemberIdMap(Object obj, boolean del) {
        ChurIncomeDataHolder o = (ChurIncomeDataHolder)obj;

   	    Long keyChurMemberId = new Long(o.getChurMemberId());

        if (del) {
            // delete from SiteIdChurMemberIdToMap
            Map mapSiteId  = (Map) m_SiteIdToChurMemberIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapChurMemberId = (Map) mapSiteId.get(keyChurMemberId);
                if (mapChurMemberId != null){
                    mapChurMemberId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed ChurIncomeDataHolder from m_SiteIdToChurMemberIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getChurMemberId());
        }
        else {
            
            // add to SiteIdChurMemberIdToMap
            Map mapSiteId  = (Map) m_SiteIdToChurMemberIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToChurMemberIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapChurMemberId = (Map) mapSiteId.get(keyChurMemberId);
            
            if ( mapChurMemberId == null) {
                mapChurMemberId = new TreeMap();
                mapSiteId.put(keyChurMemberId, mapChurMemberId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapChurMemberId.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdChurMemberIdToMap " + o.getId() + " to " + o.getSiteId());
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
        ChurIncomeDataHolder o = (ChurIncomeDataHolder)obj;

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
            m_logger.debug("removed ChurIncomeDataHolder from m_SiteIdToWeekToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getWeek());
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





	//

    public static void main(String[] args) throws Exception {

        ChurIncomeDS ds = new ChurIncomeDS();
        ChurIncomeDataHolder obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static ChurIncome createDefault(){

        ChurIncome _ChurIncome = new ChurIncome();        
	    _ChurIncome = new ChurIncome();// ChurIncomeDataHolderDS.getInstance().getDeafult();
        return _ChurIncome;
    }

    public static ChurIncome copy(ChurIncome org){

    	ChurIncome ret = new ChurIncome();

		ret.setYear(org.getYear());
		ret.setWeek(org.getWeek());
		ret.setChurMemberId(org.getChurMemberId());
		ret.setIncomeItemId(org.getIncomeItemId());
		ret.setAmmount(org.getAmmount());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(ChurIncome churIncome, Logger logger){
		logger.debug("ChurIncome [" + churIncome.getId() + "]" + objectToString(churIncome));		
    }

	public static String objectToString(ChurIncome churIncome){
		StringBuilder buf = new StringBuilder();
        buf.append("ChurIncome=");
		buf.append("Id=").append(churIncome.getId()).append(", ");
		buf.append("SiteId=").append(churIncome.getSiteId()).append(", ");
		return buf.toString();    
    }

    public boolean usingHolderObject() {
        return true;
    }

    public DataHolderObject wrapByHolder(Object obj){
        return new ChurIncomeDataHolder ( (ChurIncome) obj);
    }


    public static void objectToLog(ChurIncomeDataHolder churIncome, Logger logger){
    }


	public static String objectToString(ChurIncomeDataHolder churIncome){
		StringBuilder buf = new StringBuilder();
        buf.append("ChurIncome=");
		buf.append("Id=").append(churIncome.getId()).append(", ");
		buf.append("SiteId=").append(churIncome.getSiteId()).append(", ");
		return buf.toString();    
	}





	public String objectToString2(AutositeDataObject object){
		ChurIncomeDataHolder churIncome = (ChurIncomeDataHolder)object;
		StringBuilder buf = new StringBuilder();
        buf.append("ChurIncome=");
		buf.append("Id=").append(churIncome.getId()).append(", ");
		buf.append("SiteId=").append(churIncome.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
