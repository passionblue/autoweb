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


import com.autosite.db.ChurMemberDAO;
import com.autosite.db.ChurMemberDAO2;
import com.autosite.db.ChurMemberExtentDAO;
import com.autosite.db.ChurMember;
import com.surveygen.db.BaseMemoryDAO;

public class ChurMemberDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(ChurMemberDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static ChurMemberDS m_ChurMemberDS = new ChurMemberDSExtent();



    public static ChurMemberDS getInstance() {
        return m_ChurMemberDS;
    }

    public static synchronized ChurMemberDS getInstance(long id) {
        ChurMemberDS ret = (ChurMemberDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ChurMemberDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ChurMemberDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ChurMemberDAO2)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ChurMember loaded from DB. num=" + m_idToMap.size());
        
    }


    protected ChurMemberDS() {
        m_dao = new ChurMemberExtentDAO();
        m_idToMap = new ConcurrentHashMap();

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

    protected ChurMemberDS(long id) {
        m_dao = new ChurMemberDAO2();
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

    public ChurMember getById(Long id) {
        return (ChurMember) m_idToMap.get(id);
    }

    public ChurMember getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                ChurMember loaded = (ChurMember) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return ChurMember.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        ChurMember o = (ChurMember)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ChurMember removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ChurMember added to DS " + o.getId());
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
        ChurMember o = (ChurMember)obj;

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
            if (m_debug) m_logger.debug("ChurMember removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ChurMember added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        ChurMemberDS ds = new ChurMemberDS();
        ChurMember obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static ChurMember createDefault(){

        ChurMember _ChurMember = new ChurMember();        
	    _ChurMember = new ChurMember();// ChurMemberDS.getInstance().getDeafult();
        return _ChurMember;
    }

    public static ChurMember copy(ChurMember org){

    	ChurMember ret = new ChurMember();

		ret.setFullName(org.getFullName());
		ret.setFirstName(org.getFirstName());
		ret.setLastName(org.getLastName());
		ret.setTitle(org.getTitle());
		ret.setOtherName(org.getOtherName());
		ret.setHousehold(org.getHousehold());
		ret.setHouseholdId(org.getHouseholdId());
		ret.setIsGroup(org.getIsGroup());
		ret.setIsGuest(org.getIsGuest());
		ret.setIsSpeaker(org.getIsSpeaker());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setListIndex(org.getListIndex());

        return ret;
    }

	public static void objectToLog(ChurMember churMember, Logger logger){
		logger.debug("ChurMember [" + churMember.getId() + "]" + objectToString(churMember));		
    }

	public static String objectToString(ChurMember churMember){
		StringBuilder buf = new StringBuilder();
        buf.append("ChurMember=");
		buf.append("Id=").append(churMember.getId()).append(", ");
		buf.append("SiteId=").append(churMember.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		ChurMember churMember = (ChurMember)object;
		StringBuilder buf = new StringBuilder();
        buf.append("ChurMember=");
		buf.append("Id=").append(churMember.getId()).append(", ");
		buf.append("SiteId=").append(churMember.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
