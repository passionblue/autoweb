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

import com.autosite.holder.TestFacadeDataHolder;

import com.autosite.db.TestCoreDAO;
import com.autosite.db.TestCoreDAO2;
import com.autosite.db.TestCoreExtentDAO;
import com.autosite.db.TestCore;
import com.surveygen.db.BaseMemoryDAO;

public class TestFacadeDS extends AbstractDS implements DomainStore {

	// This flag is set during the generation by aggregateAllSites switch ( in AutoGen config). 
	// This flag is set, DS will invalidates site ID while putting into memory cache so that caller gets access to all items
    protected boolean m_aggregateAllSites = false; 

    protected boolean m_accessDbIfMiss = true; //If true, this will access DB 


	// This will be controlled by code gen config  XXX.dsCacheEnable=true/false
	// 0512 this was implemented for a situation for DS that needs to write only 
	// No need for read in the future. 
    protected boolean m_cacheEnable = true; //If false, cache will not be utilized 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(TestFacadeDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static TestFacadeDS m_TestFacadeDS = new TestFacadeDSExtent();



    public static TestFacadeDS getInstance() {
        return m_TestFacadeDS;
    }

    public static synchronized TestFacadeDS getInstance(long id) {
        TestFacadeDS ret = (TestFacadeDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new TestFacadeDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_TestFacadeDS;
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

        m_logger.info("TestFacadeDataHolder loaded from DB. num=" + m_idToMap.size());
        
    }


    protected TestFacadeDS() {
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

    protected TestFacadeDS(long id) {
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

    public TestFacadeDataHolder getById(Long id) {
        TestFacadeDataHolder ret = (TestFacadeDataHolder) m_idToMap.get(id);
        
        if ( ret == null && m_accessDbIfMiss ) {
            return getById(id, true);
        }
        return ret;
    }

    public TestFacadeDataHolder getById(Long id, boolean forSynchWithDB) {
        if (persistEnable() && forSynchWithDB ) {
            try {
                TestCore loadedFromDb = (TestCore) synchFromDB(id);
				TestFacadeDataHolder loaded = new TestFacadeDataHolder(loadedFromDb);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return (TestFacadeDataHolder) m_idToMap.get(id);
    }
    
    public String getEntityClass(){
        return TestFacadeDataHolder.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        updateMaps(wrapByHolder(obj), del);
    }
    public void updateMaps(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        TestFacadeDataHolder o = (TestFacadeDataHolder)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("TestFacadeDataHolder removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("TestFacadeDataHolder added to DS " + o.getId());
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
        TestFacadeDataHolder o = (TestFacadeDataHolder)obj;

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
            if (m_debug) m_logger.debug("TestFacadeDataHolder removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("TestFacadeDataHolder added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        TestFacadeDS ds = new TestFacadeDS();
        TestFacadeDataHolder obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static TestCore createDefault(){

        TestCore _TestFacade = new TestCore();        
	    _TestFacade = new TestCore();// TestFacadeDataHolderDS.getInstance().getDeafult();
        return _TestFacade;
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
        return new TestFacadeDataHolder ( (TestCore) obj);
    }


    public static void objectToLog(TestFacadeDataHolder testCore, Logger logger){
    }


	public static String objectToString(TestFacadeDataHolder testCore){
		StringBuilder buf = new StringBuilder();
        buf.append("TestCore=");
		buf.append("Id=").append(testCore.getId()).append(", ");
		buf.append("SiteId=").append(testCore.getSiteId()).append(", ");
		return buf.toString();    
	}





	public String objectToString2(AutositeDataObject object){
		TestFacadeDataHolder testCore = (TestFacadeDataHolder)object;
		StringBuilder buf = new StringBuilder();
        buf.append("TestCore=");
		buf.append("Id=").append(testCore.getId()).append(", ");
		buf.append("SiteId=").append(testCore.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
