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


import com.autosite.db.SweepWorldcup2014DAO;
import com.autosite.db.SweepWorldcup2014DAO2;
import com.autosite.db.SweepWorldcup2014ExtentDAO;
import com.autosite.db.SweepWorldcup2014;
import com.surveygen.db.BaseMemoryDAO;

public class SweepWorldcup2014DS extends AbstractDS implements DomainStore {

	// This flag is set during the generation by aggregateAllSites switch ( in AutoGen config). 
	// This flag is set, DS will invalidates site ID while putting into memory cache so that caller gets access to all items
    protected boolean m_aggregateAllSites = false; 

    protected boolean m_accessDbIfMiss = true; //If true, this will access DB 


	// This will be controlled by code gen config  XXX.dsCacheEnable=true/false
	// 0512 this was implemented for a situation for DS that needs to write only 
	// No need for read in the future. 
    protected boolean m_cacheEnable = true; //If false, cache will not be utilized 

    protected Map m_SiteIdToMap;
    protected Map m_PlayerToMap;






    private static Logger m_logger = Logger.getLogger(SweepWorldcup2014DS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static SweepWorldcup2014DS m_SweepWorldcup2014DS = new SweepWorldcup2014DSExtent();



    public static SweepWorldcup2014DS getInstance() {
        return m_SweepWorldcup2014DS;
    }

    public static synchronized SweepWorldcup2014DS getInstance(long id) {
        SweepWorldcup2014DS ret = (SweepWorldcup2014DS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SweepWorldcup2014DS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SweepWorldcup2014DS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SweepWorldcup2014DAO2)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SweepWorldcup2014 loaded from DB. num=" + m_idToMap.size());
        
    }


    protected SweepWorldcup2014DS() {
        m_dao = new SweepWorldcup2014ExtentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PlayerToMap = new ConcurrentHashMap();
 
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

    protected SweepWorldcup2014DS(long id) {
        m_dao = new SweepWorldcup2014DAO2();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PlayerToMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SweepWorldcup2014 getById(Long id) {
        SweepWorldcup2014 ret = (SweepWorldcup2014) m_idToMap.get(id);
        
        if ( ret == null && m_accessDbIfMiss ) {
            return getById(id, true);
        }
        return ret;
    }

    public SweepWorldcup2014 getById(Long id, boolean forSynchWithDB) {
        if (persistEnable() && forSynchWithDB ) {
            try {
                SweepWorldcup2014 loaded = (SweepWorldcup2014) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return (SweepWorldcup2014) m_idToMap.get(id);
    }
    
    public String getEntityClass(){
        return SweepWorldcup2014.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        if (!m_cacheEnable) return;
        SweepWorldcup2014 o = (SweepWorldcup2014)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SweepWorldcup2014 removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SweepWorldcup2014 added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updatePlayerMap(obj, del);
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
        SweepWorldcup2014 o = (SweepWorldcup2014)obj;

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
            if (m_debug) m_logger.debug("SweepWorldcup2014 removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("SweepWorldcup2014 added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


	// ListStringKeys - Player

    public List getByPlayer(String keyPlayer) {
        
        if (m_PlayerToMap.containsKey(keyPlayer)) {
            return new ArrayList( ((Map)m_PlayerToMap.get(keyPlayer)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updatePlayerMap(Object obj, boolean del) {
        SweepWorldcup2014 o = (SweepWorldcup2014)obj;

        if (del) {

            // delete from PlayerToMap
            Map map  = (Map) m_PlayerToMap.get(o.getPlayer());
            if ( map != null ) {
                map.remove(new Long(o.getId()));
                if (m_debug) m_logger.debug("SweepWorldcup2014 removed from PlayerToMap" + o.getId() + " from [" + o.getPlayer() + "]");
            } else {
                if (m_debug) m_logger.debug("SweepWorldcup2014 NOT removed from PlayerToMap because no map found for [" + o.getPlayer() + "]");
            }
        }
        else {
            
            // add to PlayerToMap
            Map map  = (Map) m_PlayerToMap.get(o.getPlayer());
            if ( map == null ) {
                map = new TreeMap();
                m_PlayerToMap.put(o.getPlayer(), map);
                if (m_debug) m_logger.debug("created new   PlayerToMap for [" + o.getPlayer() + "]");
            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("SweepWorldcup2014 added to PlayerToMap " + o.getId() + " to [" + o.getPlayer() + "]");
        }
    }

    











	//

    public static void main(String[] args) throws Exception {

        SweepWorldcup2014DS ds = new SweepWorldcup2014DS();
        SweepWorldcup2014 obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static SweepWorldcup2014 createDefault(){

        SweepWorldcup2014 _SweepWorldcup2014 = new SweepWorldcup2014();        
	    _SweepWorldcup2014 = new SweepWorldcup2014();// SweepWorldcup2014DS.getInstance().getDeafult();
        return _SweepWorldcup2014;
    }

    public static SweepWorldcup2014 copy(SweepWorldcup2014 org){

    	SweepWorldcup2014 ret = new SweepWorldcup2014();

		ret.setPlayer(org.getPlayer());
		ret.setGame1(org.getGame1());
		ret.setGame1Score(org.getGame1Score());
		ret.setGame1ScoreOpp(org.getGame1ScoreOpp());
		ret.setGame2(org.getGame2());
		ret.setGame2Score(org.getGame2Score());
		ret.setGame2ScoreOpp(org.getGame2ScoreOpp());
		ret.setGame3(org.getGame3());
		ret.setGame3Score(org.getGame3Score());
		ret.setGame3ScoreOpp(org.getGame3ScoreOpp());
		ret.setAdvance(org.getAdvance());
		ret.setTeam16A1(org.getTeam16A1());
		ret.setTeam16A2(org.getTeam16A2());
		ret.setTeam16B1(org.getTeam16B1());
		ret.setTeam16B2(org.getTeam16B2());
		ret.setTeam16C1(org.getTeam16C1());
		ret.setTeam16C2(org.getTeam16C2());
		ret.setTeam16D1(org.getTeam16D1());
		ret.setTeam16D2(org.getTeam16D2());
		ret.setTeam16E1(org.getTeam16E1());
		ret.setTeam16E2(org.getTeam16E2());
		ret.setTeam16F1(org.getTeam16F1());
		ret.setTeam16F2(org.getTeam16F2());
		ret.setTeam16G1(org.getTeam16G1());
		ret.setTeam16G2(org.getTeam16G2());
		ret.setTeam16H1(org.getTeam16H1());
		ret.setTeam16H2(org.getTeam16H2());
		ret.setQuarterFinal1(org.getQuarterFinal1());
		ret.setQuarterFinal2(org.getQuarterFinal2());
		ret.setQuarterFinal3(org.getQuarterFinal3());
		ret.setQuarterFinal4(org.getQuarterFinal4());
		ret.setQuarterFinal5(org.getQuarterFinal5());
		ret.setQuarterFinal6(org.getQuarterFinal6());
		ret.setQuarterFinal7(org.getQuarterFinal7());
		ret.setQuarterFinal8(org.getQuarterFinal8());
		ret.setSemiFinal1(org.getSemiFinal1());
		ret.setSemiFinal2(org.getSemiFinal2());
		ret.setSemiFinal3(org.getSemiFinal3());
		ret.setSemiFinal4(org.getSemiFinal4());
		ret.setFinal1(org.getFinal1());
		ret.setFinal2(org.getFinal2());
		ret.setChampion(org.getChampion());
		ret.setFinalScoreWin(org.getFinalScoreWin());
		ret.setFinalScoreLose(org.getFinalScoreLose());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(SweepWorldcup2014 sweepWorldcup2014, Logger logger){
		logger.debug("SweepWorldcup2014 [" + sweepWorldcup2014.getId() + "]" + objectToString(sweepWorldcup2014));		
    }

	public static String objectToString(SweepWorldcup2014 sweepWorldcup2014){
		StringBuilder buf = new StringBuilder();
        buf.append("SweepWorldcup2014=");
		buf.append("Id=").append(sweepWorldcup2014.getId()).append(", ");
		buf.append("SiteId=").append(sweepWorldcup2014.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		SweepWorldcup2014 sweepWorldcup2014 = (SweepWorldcup2014)object;
		StringBuilder buf = new StringBuilder();
        buf.append("SweepWorldcup2014=");
		buf.append("Id=").append(sweepWorldcup2014.getId()).append(", ");
		buf.append("SiteId=").append(sweepWorldcup2014.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
