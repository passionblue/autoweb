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
import com.autosite.db.SweepRegister;
import com.jtrend.service.DomainStore;

import com.autosite.db.SweepRegisterDAO;

public class SweepRegisterDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;

	protected Map m_SiteIdAgeRangeToMap;

	protected Map m_SiteIdEmailToOneMap;




    private static Logger m_logger = Logger.getLogger(SweepRegisterDS.class);
    private static SweepRegisterDS m_SweepRegisterDS = new SweepRegisterDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static SweepRegisterDS getInstance() {
        return m_SweepRegisterDS;
    }

    public static synchronized SweepRegisterDS getInstance(long id) {
        SweepRegisterDS ret = (SweepRegisterDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SweepRegisterDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SweepRegisterDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SweepRegisterDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SweepRegister loaded from DB. num=" + m_idToMap.size());
        
    }

    protected SweepRegisterDS() {
        m_dao = new SweepRegisterDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
		m_SiteIdAgeRangeToMap = new ConcurrentHashMap();
		m_SiteIdEmailToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SweepRegisterDS(long id) {
        m_dao = new SweepRegisterDAO();
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

    public SweepRegister getById(Long id) {
        return (SweepRegister) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        SweepRegister o = (SweepRegister)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SweepRegister removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SweepRegister added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
		updateSiteIdAgeRangeMap(obj, del);
		updateSiteIdEmailOneMap(obj, del);
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
        SweepRegister o = (SweepRegister)obj;

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
            if (m_debug) m_logger.debug("SweepRegister removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("SweepRegister added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    




        
    public List getBySiteIdAgeRange(long SiteId, int AgeRange) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdAgeRangeToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdAgeRangeToMap.get(keySiteId);

     	    Integer keyAgeRange = new Integer(AgeRange);
            
            if ( mapSiteId.containsKey(keyAgeRange)){
                return new ArrayList( ((Map)mapSiteId.get(keyAgeRange)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdAgeRange(long SiteId, int AgeRange) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdAgeRangeToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdAgeRangeToMap.get(keySiteId);

     	    Integer keyAgeRange = new Integer(AgeRange);
            
            if ( mapSiteId.containsKey(keyAgeRange)){
                return (Map)mapSiteId.get(keyAgeRange);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdAgeRangeMap(Object obj, boolean del) {
        SweepRegister o = (SweepRegister)obj;

   	    Integer keyAgeRange = new Integer(o.getAgeRange());

        if (del) {
            // delete from SiteIdAgeRangeToMap
            Map mapSiteId  = (Map) m_SiteIdAgeRangeToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapAgeRange = (Map) mapSiteId.get(keyAgeRange);
                if (mapAgeRange != null){
                    mapAgeRange.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed SweepRegister from m_SiteIdAgeRangeToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getAgeRange());
        }
        else {
            
            // add to SiteIdAgeRangeToMap
            Map mapSiteId  = (Map) m_SiteIdAgeRangeToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdAgeRangeToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapAgeRange = (Map) mapSiteId.get(keyAgeRange);
            
            if ( mapAgeRange == null) {
                mapAgeRange = new TreeMap();
                mapSiteId.put(keyAgeRange, mapAgeRange);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapAgeRange.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdAgeRangeToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        

        
    public  SweepRegister getBySiteIdEmail(long SiteId, String Email) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdEmailToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdEmailToOneMap.get(keySiteId);

     	    String keyEmail =  Email;
            if (  keyEmail == null || keyEmail.isEmpty()) return null;
            
            if ( mapSiteId.containsKey(keyEmail)){
                return ( SweepRegister)mapSiteId.get(keyEmail);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdEmailOneMap(Object obj, boolean del) {
        SweepRegister o = (SweepRegister)obj;

     	    String keyEmail =  o.getEmail();
            if ( keyEmail == null || keyEmail.isEmpty()) return;

        if (del) {
            // delete from SiteIdEmailToOneMap
            Map mapSiteId  = (Map) m_SiteIdEmailToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                if (mapSiteId.containsKey(keyEmail)){
                    mapSiteId.remove(keyEmail);
                }
            }
            m_logger.debug("removed SweepRegister from m_SiteIdEmailToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getEmail());
        }
        else {
            
            // add to SiteIdEmailToOneMap
            Map mapSiteId  = (Map) m_SiteIdEmailToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdEmailToOneMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new mapSiteId for " + o.getSiteId());
            }
            
            
			SweepRegister replaced = (SweepRegister) mapSiteId.put(keyEmail,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdEmailOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Panel added to SiteIdEmailToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        






    public static void main(String[] args) throws Exception {

        SweepRegisterDS ds = new SweepRegisterDS();
        SweepRegister obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static SweepRegister createDefault(){

        SweepRegister ret = new SweepRegister();        
//      ret.setEmail("");           
//      ret.setPassword("");           
//      ret.setSex("");           
//      ret.setAgeRange("");           
//      ret.setAgreeTerms("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static SweepRegister copy(SweepRegister org){

    	SweepRegister ret = new SweepRegister();

		ret.setEmail(org.getEmail());
		ret.setPassword(org.getPassword());
		ret.setSex(org.getSex());
		ret.setAgeRange(org.getAgeRange());
		ret.setAgreeTerms(org.getAgreeTerms());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(SweepRegister sweepRegister, Logger logger){
		logger.debug("SweepRegister [" + sweepRegister.getId() + "]" + objectToString(sweepRegister));		
    }

	public static String objectToString(SweepRegister sweepRegister){
		StringBuffer buf = new StringBuffer();
        buf.append("SweepRegister=");
		buf.append("Id=").append(sweepRegister.getId()).append(", ");
		buf.append("SiteId=").append(sweepRegister.getSiteId()).append(", ");
		buf.append("Email=").append(sweepRegister.getEmail()).append(", ");
		buf.append("TimeCreated=").append(sweepRegister.getTimeCreated()).append(", ");
		return buf.toString();    
    }
}
