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
import com.autosite.db.SweepWorldcup;
import com.jtrend.service.DomainStore;

import com.autosite.db.SweepWorldcupDAO;

public class SweepWorldcupDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToMap;
    protected Map m_TeamCodeToMap;






    private static Logger m_logger = Logger.getLogger(SweepWorldcupDS.class);
    private static SweepWorldcupDS m_SweepWorldcupDS = new SweepWorldcupDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static SweepWorldcupDS getInstance() {
        return m_SweepWorldcupDS;
    }

    public static synchronized SweepWorldcupDS getInstance(long id) {
        SweepWorldcupDS ret = (SweepWorldcupDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SweepWorldcupDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SweepWorldcupDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SweepWorldcupDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SweepWorldcup loaded from DB. num=" + m_idToMap.size());
        
    }

    protected SweepWorldcupDS() {
        m_dao = new SweepWorldcupDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
        m_TeamCodeToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SweepWorldcupDS(long id) {
        m_dao = new SweepWorldcupDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
        m_TeamCodeToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SweepWorldcup getById(Long id) {
        return (SweepWorldcup) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        SweepWorldcup o = (SweepWorldcup)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SweepWorldcup removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SweepWorldcup added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateUserIdMap(obj, del);
        updateTeamCodeMap(obj, del);
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
        SweepWorldcup o = (SweepWorldcup)obj;

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
            if (m_debug) m_logger.debug("SweepWorldcup removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("SweepWorldcup added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }
	// ListKeys - UserId
    public List getByUserId(long UserId) {
        
        Long _UserId  = new Long(UserId);
        if (m_UserIdToMap.containsKey(_UserId)) {
            return new ArrayList( ((Map)m_UserIdToMap.get(_UserId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateUserIdMap(Object obj, boolean del) {
        SweepWorldcup o = (SweepWorldcup)obj;

		if ( o.getUserId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("SweepWorldcup removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
        }
        else {
            
            // add to UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map == null ) {
                map = new TreeMap();
                m_UserIdToMap.put(new Long(o.getUserId()), map);
                if (m_debug) m_logger.debug("created new   UserIdToMap for " + o.getUserId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("SweepWorldcup added to UserIdToMap " + o.getId() + " to " + o.getUserId());
        }
    }


	// ListStringKeys - TeamCode

    public List getByTeamCode(String keyTeamCode) {
        
        if (m_TeamCodeToMap.containsKey(keyTeamCode)) {
            return new ArrayList( ((Map)m_TeamCodeToMap.get(keyTeamCode)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateTeamCodeMap(Object obj, boolean del) {
        SweepWorldcup o = (SweepWorldcup)obj;

        if (del) {

            // delete from TeamCodeToMap
            Map map  = (Map) m_TeamCodeToMap.get(o.getTeamCode());
            if ( map != null ) {
                map.remove(new Long(o.getId()));
                if (m_debug) m_logger.debug("SweepWorldcup removed from TeamCodeToMap" + o.getId() + " from [" + o.getTeamCode() + "]");
            } else {
                if (m_debug) m_logger.debug("SweepWorldcup NOT removed from TeamCodeToMap because no map found for [" + o.getTeamCode() + "]");
            }
        }
        else {
            
            // add to TeamCodeToMap
            Map map  = (Map) m_TeamCodeToMap.get(o.getTeamCode());
            if ( map == null ) {
                map = new TreeMap();
                m_TeamCodeToMap.put(o.getTeamCode(), map);
                if (m_debug) m_logger.debug("created new   TeamCodeToMap for [" + o.getTeamCode() + "]");
            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("SweepWorldcup added to TeamCodeToMap " + o.getId() + " to [" + o.getTeamCode() + "]");
        }
    }

    











    public static void main(String[] args) throws Exception {

        SweepWorldcupDS ds = new SweepWorldcupDS();
        SweepWorldcup obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static SweepWorldcup createDefault(){

        SweepWorldcup ret = new SweepWorldcup();        
//      ret.setUserId("");           
//      ret.setTeamCode("");           
//      ret.setTeamName("");           
//      ret.setGroupNum("");           
//      ret.setGame1("");           
//      ret.setGame1Score("");           
//      ret.setGame1ScoreOpp("");           
//      ret.setGame2("");           
//      ret.setGame2Score("");           
//      ret.setGame2ScoreOpp("");           
//      ret.setGame3("");           
//      ret.setGame3Score("");           
//      ret.setGame3ScoreOpp("");           
//      ret.setQuarterFinalTeams("");           
//      ret.setSemiFinalTeams("");           
//      ret.setFinalTeams("");           
//      ret.setChampion("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static SweepWorldcup copy(SweepWorldcup org){

    	SweepWorldcup ret = new SweepWorldcup();

		ret.setUserId(org.getUserId());
		ret.setTeamCode(org.getTeamCode());
		ret.setTeamName(org.getTeamName());
		ret.setGroupNum(org.getGroupNum());
		ret.setGame1(org.getGame1());
		ret.setGame1Score(org.getGame1Score());
		ret.setGame1ScoreOpp(org.getGame1ScoreOpp());
		ret.setGame2(org.getGame2());
		ret.setGame2Score(org.getGame2Score());
		ret.setGame2ScoreOpp(org.getGame2ScoreOpp());
		ret.setGame3(org.getGame3());
		ret.setGame3Score(org.getGame3Score());
		ret.setGame3ScoreOpp(org.getGame3ScoreOpp());
		ret.setQuarterFinalTeams(org.getQuarterFinalTeams());
		ret.setSemiFinalTeams(org.getSemiFinalTeams());
		ret.setFinalTeams(org.getFinalTeams());
		ret.setChampion(org.getChampion());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(SweepWorldcup sweepWorldcup, Logger logger){
		logger.debug("SweepWorldcup [" + sweepWorldcup.getId() + "]" + objectToString(sweepWorldcup));		
    }

	public static String objectToString(SweepWorldcup sweepWorldcup){
		StringBuffer buf = new StringBuffer();
        buf.append("SweepWorldcup=");
		buf.append("Id=").append(sweepWorldcup.getId()).append(", ");
		buf.append("SiteId=").append(sweepWorldcup.getSiteId()).append(", ");
		buf.append("UserId=").append(sweepWorldcup.getUserId()).append(", ");
		buf.append("TeamCode=").append(sweepWorldcup.getTeamCode()).append(", ");
		return buf.toString();    
    }
}
