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


import com.autosite.db.RequestHistoryDAO;
import com.autosite.db.RequestHistoryDAO2;
import com.autosite.db.RequestHistoryExtentDAO;
import com.autosite.db.RequestHistory;
import com.surveygen.db.BaseMemoryDAO;

public class RequestHistoryDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected boolean m_accessDbIfMiss = true; //If true, this will access DB 

    protected boolean m_cacheEnable = false; //If false, cache will not be utilized 





    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(RequestHistoryDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static RequestHistoryDS m_RequestHistoryDS = new RequestHistoryDSExtent();



    public static RequestHistoryDS getInstance() {
        return m_RequestHistoryDS;
    }

    public static synchronized RequestHistoryDS getInstance(long id) {
        RequestHistoryDS ret = (RequestHistoryDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new RequestHistoryDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_RequestHistoryDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((RequestHistoryDAO2)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("RequestHistory loaded from DB. num=" + m_idToMap.size());
        
    }


    protected RequestHistoryDS() {
        m_dao = new RequestHistoryExtentDAO();
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

    protected RequestHistoryDS(long id) {
        m_dao = new RequestHistoryDAO2();
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

    public RequestHistory getById(Long id) {
        RequestHistory ret = (RequestHistory) m_idToMap.get(id);
        
        if ( ret == null && m_accessDbIfMiss ) {
            return getById(id, true);
        }
        return ret;
    }

    public RequestHistory getById(Long id, boolean forSynchWithDB) {
        if (persistEnable() && forSynchWithDB ) {
            try {
                RequestHistory loaded = (RequestHistory) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return (RequestHistory) m_idToMap.get(id);
    }
    
    public String getEntityClass(){
        return RequestHistory.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        RequestHistory o = (RequestHistory)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("RequestHistory removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("RequestHistory added to DS " + o.getId());
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
        RequestHistory o = (RequestHistory)obj;

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
            if (m_debug) m_logger.debug("RequestHistory removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("RequestHistory added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        RequestHistoryDS ds = new RequestHistoryDS();
        RequestHistory obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static RequestHistory createDefault(){

        RequestHistory _RequestHistory = new RequestHistory();        
	    _RequestHistory = new RequestHistory();// RequestHistoryDS.getInstance().getDeafult();
        return _RequestHistory;
    }

    public static RequestHistory copy(RequestHistory org){

    	RequestHistory ret = new RequestHistory();

		ret.setForwardSiteId(org.getForwardSiteId());
		ret.setIsDropped(org.getIsDropped());
		ret.setIsPageless(org.getIsPageless());
		ret.setIsLogin(org.getIsLogin());
		ret.setIsAjax(org.getIsAjax());
		ret.setIsRobot(org.getIsRobot());
		ret.setUserid(org.getUserid());
		ret.setUserAgent(org.getUserAgent());
		ret.setRefer(org.getRefer());
		ret.setRobot(org.getRobot());
		ret.setRemoteIp(org.getRemoteIp());
		ret.setSiteUrl(org.getSiteUrl());
		ret.setUri(org.getUri());
		ret.setQuery(org.getQuery());
		ret.setRpci(org.getRpci());
		ret.setSessionId(org.getSessionId());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(RequestHistory requestHistory, Logger logger){
		logger.debug("RequestHistory [" + requestHistory.getId() + "]" + objectToString(requestHistory));		
    }

	public static String objectToString(RequestHistory requestHistory){
		StringBuilder buf = new StringBuilder();
        buf.append("RequestHistory=");
		buf.append("Id=").append(requestHistory.getId()).append(", ");
		buf.append("SiteId=").append(requestHistory.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		RequestHistory requestHistory = (RequestHistory)object;
		StringBuilder buf = new StringBuilder();
        buf.append("RequestHistory=");
		buf.append("Id=").append(requestHistory.getId()).append(", ");
		buf.append("SiteId=").append(requestHistory.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
