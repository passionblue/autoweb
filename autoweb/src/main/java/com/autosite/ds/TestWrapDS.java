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

import com.autosite.holder.TestWrapDataHolder;

import com.autosite.db.TestCoreDAO;
import com.autosite.db.TestCoreDAO2;
import com.autosite.db.TestCoreExtentDAO;
import com.autosite.db.TestCore;
import com.surveygen.db.BaseMemoryDAO;

public class TestWrapDS extends AbstractDS implements DomainStore {

	// This flag is set during the generation by aggregateAllSites switch ( in AutoGen config). 
	// This flag is set, DS will invalidates site ID while putting into memory cache so that caller gets access to all items
    protected boolean m_aggregateAllSites = false; 

    protected boolean m_accessDbIfMiss = true; //If true, this will access DB 


	// This will be controlled by code gen config  XXX.dsCacheEnable=true/false
	// 0512 this was implemented for a situation for DS that needs to write only 
	// No need for read in the future. 
    protected boolean m_cacheEnable = true; //If false, cache will not be utilized 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(TestWrapDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static TestWrapDS m_TestWrapDS = new TestWrapDSExtent();



    public static TestWrapDS getInstance() {
        return m_TestWrapDS;
    }

    public static synchronized TestWrapDS getInstance(long id) {
        TestWrapDS ret = (TestWrapDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new TestWrapDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_TestWrapDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((TestCoreDAO2)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("TestWrapDataHolder loaded from DB. num=" + m_idToMap.size());
        
    }


    protected TestWrapDS() {
        m_dao = new TestCoreExtentDAO();
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
    }

    protected TestWrapDS(long id) {
        m_dao = new TestCoreDAO2();
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

    public TestWrapDataHolder getById(Long id) {
        TestWrapDataHolder ret = (TestWrapDataHolder) m_idToMap.get(id);
        
        if ( ret == null && m_accessDbIfMiss ) {
            return getById(id, true);
        }
        return ret;
    }

    public TestWrapDataHolder getById(Long id, boolean forSynchWithDB) {
        if (persistEnable() && forSynchWithDB ) {
            try {
                TestCore loadedFromDb = (TestCore) synchFromDB(id);
				TestWrapDataHolder loaded = new TestWrapDataHolder(loadedFromDb);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return (TestWrapDataHolder) m_idToMap.get(id);
    }
    
    public String getEntityClass(){
        return TestWrapDataHolder.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        updateMaps(wrapByHolder(obj), del);
    }
    public void updateMaps(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        TestWrapDataHolder o = (TestWrapDataHolder)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("TestWrapDataHolder removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("TestWrapDataHolder added to DS " + o.getId());
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
        TestWrapDataHolder o = (TestWrapDataHolder)obj;

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
            if (m_debug) m_logger.debug("TestWrapDataHolder removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("TestWrapDataHolder added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        TestWrapDS ds = new TestWrapDS();
        TestWrapDataHolder obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static TestCore createDefault(){

        TestCore _TestWrap = new TestCore();        
	    _TestWrap = new TestCore();// TestWrapDataHolderDS.getInstance().getDeafult();
        return _TestWrap;
    }

    public static TestCore copy(TestCore org){

    	TestCore ret = new TestCore();

		ret.setData(org.getData());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(TestCore testCore, Logger logger){
		logger.debug("TestCore [" + testCore.getId() + "]" + objectToString(testCore));		
    }

	public static String objectToString(TestCore testCore){
		StringBuilder buf = new StringBuilder();
        buf.append("TestCore=");
		buf.append("Id=").append(testCore.getId()).append(", ");
		buf.append("SiteId=").append(testCore.getSiteId()).append(", ");
		return buf.toString();    
    }

    public boolean usingHolderObject() {
        return true;
    }

    public DataHolderObject wrapByHolder(Object obj){
        return new TestWrapDataHolder ( (TestCore) obj);
    }


    public static void objectToLog(TestWrapDataHolder testCore, Logger logger){
    }


	public static String objectToString(TestWrapDataHolder testCore){
		StringBuilder buf = new StringBuilder();
        buf.append("TestCore=");
		buf.append("Id=").append(testCore.getId()).append(", ");
		buf.append("SiteId=").append(testCore.getSiteId()).append(", ");
		return buf.toString();    
	}





	public String objectToString2(AutositeDataObject object){
		TestWrapDataHolder testCore = (TestWrapDataHolder)object;
		StringBuilder buf = new StringBuilder();
        buf.append("TestCore=");
		buf.append("Id=").append(testCore.getId()).append(", ");
		buf.append("SiteId=").append(testCore.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
