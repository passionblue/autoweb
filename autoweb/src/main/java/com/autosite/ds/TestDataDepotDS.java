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
import com.autosite.db.TestDataDepot;
import com.jtrend.service.DomainStore;

import com.autosite.db.TestDataDepotDAO;

public class TestDataDepotDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;


	protected Map m_SiteIdTitleToOneMap;





    private static Logger m_logger = Logger.getLogger(TestDataDepotDS.class);
    private static TestDataDepotDS m_TestDataDepotDS = new TestDataDepotDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static TestDataDepotDS getInstance() {
        return m_TestDataDepotDS;
    }

    public static synchronized TestDataDepotDS getInstance(long id) {
        TestDataDepotDS ret = (TestDataDepotDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new TestDataDepotDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_TestDataDepotDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((TestDataDepotDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("TestDataDepot loaded from DB. num=" + m_idToMap.size());
        
    }

    protected TestDataDepotDS() {
        m_dao = new TestDataDepotDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
		m_SiteIdTitleToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected TestDataDepotDS(long id) {
        m_dao = new TestDataDepotDAO();
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

    public TestDataDepot getById(Long id) {
        return (TestDataDepot) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        TestDataDepot o = (TestDataDepot)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("TestDataDepot removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("TestDataDepot added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
		updateSiteIdTitleOneMap(obj, del);
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
        TestDataDepot o = (TestDataDepot)obj;

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
            if (m_debug) m_logger.debug("TestDataDepot removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("TestDataDepot added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    





        
    public  TestDataDepot getBySiteIdTitle(long SiteId, String Title) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdTitleToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdTitleToOneMap.get(keySiteId);

     	    String keyTitle =  Title;
            if (  keyTitle == null || keyTitle.isEmpty()) return null;
            
            if ( mapSiteId.containsKey(keyTitle)){
                return ( TestDataDepot)mapSiteId.get(keyTitle);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdTitleOneMap(Object obj, boolean del) {
        TestDataDepot o = (TestDataDepot)obj;

     	    String keyTitle =  o.getTitle();
            if ( keyTitle == null || keyTitle.isEmpty()) return;

        if (del) {
            // delete from SiteIdTitleToOneMap
            Map mapSiteId  = (Map) m_SiteIdTitleToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                if (mapSiteId.containsKey(keyTitle)){
                    mapSiteId.remove(keyTitle);
                }
            }
            m_logger.debug("removed TestDataDepot from m_SiteIdTitleToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getTitle());
        }
        else {
            
            // add to SiteIdTitleToOneMap
            Map mapSiteId  = (Map) m_SiteIdTitleToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdTitleToOneMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new mapSiteId for " + o.getSiteId());
            }
            
            
			TestDataDepot replaced = (TestDataDepot) mapSiteId.put(keyTitle,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdTitleOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Panel added to SiteIdTitleToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        









    public static void main(String[] args) throws Exception {

        TestDataDepotDS ds = new TestDataDepotDS();
        TestDataDepot obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static TestDataDepot createDefault(){

        TestDataDepot _TestDataDepot = new TestDataDepot();        
	    _TestDataDepot = new TestDataDepot();// TestDataDepotDS.getInstance().getDeafult();
	   _TestDataDepot.setTitle("asdf");
	   _TestDataDepot.setType(2);
        return _TestDataDepot;
    }

    public static TestDataDepot copy(TestDataDepot org){

    	TestDataDepot ret = new TestDataDepot();

		ret.setTitle(org.getTitle());
		ret.setData(org.getData());
		ret.setType(org.getType());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(TestDataDepot testDataDepot, Logger logger){
		logger.debug("TestDataDepot [" + testDataDepot.getId() + "]" + objectToString(testDataDepot));		
    }

	public static String objectToString(TestDataDepot testDataDepot){
		StringBuffer buf = new StringBuffer();
        buf.append("TestDataDepot=");
		buf.append("Id=").append(testDataDepot.getId()).append(", ");
		buf.append("SiteId=").append(testDataDepot.getSiteId()).append(", ");
		return buf.toString();    
    }
}
