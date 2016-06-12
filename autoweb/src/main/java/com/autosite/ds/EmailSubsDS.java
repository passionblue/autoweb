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
import com.autosite.db.EmailSubs;
import com.autosite.db.EmailSubsDAO;
import com.jtrend.service.DomainStore;

public class EmailSubsDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_SubjectToMap;

	protected Map m_SiteIdSubjectToMap;

	protected Map m_SiteIdEmailToOneMap;

    private static Logger m_logger = Logger.getLogger(EmailSubsDS.class);
    private static EmailSubsDS m_EmailSubsDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized EmailSubsDS getInstance() {
        if (m_EmailSubsDS == null) {
            m_EmailSubsDS = new EmailSubsDS();
        }
        return m_EmailSubsDS;
    }

    public static synchronized EmailSubsDS getInstance(long id) {
        EmailSubsDS ret = (EmailSubsDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EmailSubsDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EmailSubsDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EmailSubsDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EmailSubsDS() {
        m_dao = new EmailSubsDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SubjectToMap = new ConcurrentHashMap();
		m_SiteIdSubjectToMap = new ConcurrentHashMap();
		m_SiteIdEmailToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EmailSubsDS(long id) {
        m_loadById = id;
        m_dao = new EmailSubsDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SubjectToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EmailSubs getById(Long id) {
        return (EmailSubs) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EmailSubs o = (EmailSubs)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EmailSubs removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EmailSubs added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSubjectMap(obj, del);

		updateSiteIdSubjectMap(obj, del);

		updateSiteIdEmailOneMap(obj, del);

    }


    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        EmailSubs o = (EmailSubs)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EmailSubs removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EmailSubs added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    public List getBySubject(String keySubject) {
        
        if (m_SubjectToMap.containsKey(keySubject)) {
            return new ArrayList( ((Map)m_SubjectToMap.get(keySubject)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateSubjectMap(Object obj, boolean del) {
        EmailSubs o = (EmailSubs)obj;

        if (del) {

            // delete from SubjectToMap
            Map map  = (Map) m_SubjectToMap.get(o.getSubject());
            if ( map != null ) {
                map.remove(new Long(o.getId()));
                if (m_debug) m_logger.debug("EmailSubs removed from SubjectToMap" + o.getId() + " from [" + o.getSubject() + "]");
            } else {
                if (m_debug) m_logger.debug("EmailSubs NOT removed from SubjectToMap because no map found for [" + o.getSubject() + "]");
            }
        }
        else {
            
            // add to SubjectToMap
            Map map  = (Map) m_SubjectToMap.get(o.getSubject());
            if ( map == null ) {
                map = new TreeMap();
                m_SubjectToMap.put(o.getSubject(), map);
                if (m_debug) m_logger.debug("created new   SubjectToMap for [" + o.getSubject() + "]");
            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("EmailSubs added to SubjectToMap " + o.getId() + " to [" + o.getSubject() + "]");
        }
    }

    




        
    public List getBySiteIdSubject(long SiteId, String Subject) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdSubjectToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdSubjectToMap.get(keySiteId);

     	    String keySubject =  Subject;
            
            if ( mapSiteId.containsKey(keySubject)){
                return new ArrayList( ((Map)mapSiteId.get(keySubject)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdSubject(long SiteId, String Subject) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdSubjectToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdSubjectToMap.get(keySiteId);

     	    String keySubject =  Subject;
            
            if ( mapSiteId.containsKey(keySubject)){
                return (Map)mapSiteId.get(keySubject);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdSubjectMap(Object obj, boolean del) {
        EmailSubs o = (EmailSubs)obj;

     	    String keySubject =  o.getSubject();

        if (del) {
            // delete from SiteIdSubjectToMap
            Map mapSiteId  = (Map) m_SiteIdSubjectToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapSubject = (Map) mapSiteId.get(keySubject);
                if (mapSubject != null){
                    mapSubject.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed from m_SiteIdSubjectToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getSubject());
        }
        else {
            
            // add to SiteIdSubjectToMap
            Map mapSiteId  = (Map) m_SiteIdSubjectToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdSubjectToMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapSubject = (Map) mapSiteId.get(keySubject);
            
            if ( mapSubject == null) {
                mapSubject = new TreeMap();
                mapSiteId.put(keySubject, mapSubject);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapSubject.put(new Long(o.getId()), o);            
            
            m_logger.debug("Panel added to SiteIdSubjectToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        

        
    public  EmailSubs getBySiteIdEmail(long SiteId, String Email) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdEmailToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdEmailToOneMap.get(keySiteId);

     	    String keyEmail =  Email;
            
            if ( mapSiteId.containsKey(keyEmail)){
                return ( EmailSubs)mapSiteId.get(keyEmail);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdEmailOneMap(Object obj, boolean del) {
        EmailSubs o = (EmailSubs)obj;

     	    String keyEmail =  o.getEmail();

        if (del) {
            // delete from SiteIdEmailToOneMap
            Map mapSiteId  = (Map) m_SiteIdEmailToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapEmail = (Map) mapSiteId.get(keyEmail);
                if (mapSiteId.containsKey(keyEmail)){
                    mapSiteId.remove(keyEmail);
                }
            }
            m_logger.debug("removed from m_SiteIdEmailToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getEmail());
        }
        else {
            
            // add to SiteIdEmailToOneMap
            Map mapSiteId  = (Map) m_SiteIdEmailToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdEmailToOneMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            
            
			EmailSubs replaced = (EmailSubs) mapSiteId.put(keyEmail,o);			
            if ( replaced != null){
                m_logger.debug("**WARNING**: existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            m_logger.debug("Panel added to SiteIdEmailToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        



    public static void main(String[] args) throws Exception {

        EmailSubsDS ds = new EmailSubsDS();
        EmailSubs obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EmailSubs createDefault(){

        EmailSubs ret = new EmailSubs();        
//      ret.setSubject("");           
//      ret.setEmail("");           
//      ret.setFirstName("");           
//      ret.setLastName("");           
//      ret.setConfirmed("");           
//      ret.setDisabled("");           
//      ret.setCheckOpt1("");           
//      ret.setCheckOpt2("");           
//      ret.setExtraInfo("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static EmailSubs copy(EmailSubs org){

    	EmailSubs ret = new EmailSubs();

		ret.setSubject(org.getSubject());
		ret.setEmail(org.getEmail());
		ret.setFirstName(org.getFirstName());
		ret.setLastName(org.getLastName());
		ret.setConfirmed(org.getConfirmed());
		ret.setDisabled(org.getDisabled());
		ret.setCheckOpt1(org.getCheckOpt1());
		ret.setCheckOpt2(org.getCheckOpt2());
		ret.setExtraInfo(org.getExtraInfo());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(EmailSubs emailSubs, Logger logger){
		logger.debug("EmailSubs [" + emailSubs.getId() + "]" + objectToString(emailSubs));		
    }

	public static String objectToString(EmailSubs emailSubs){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(emailSubs.getId()).append(", ");
		buf.append("SiteId=").append(emailSubs.getSiteId()).append(", ");
		buf.append("Subject=").append(emailSubs.getSubject()).append(", ");
		buf.append("Email=").append(emailSubs.getEmail()).append(", ");
		buf.append("Confirmed=").append(emailSubs.getConfirmed()).append(", ");
		buf.append("TimeCreated=").append(emailSubs.getTimeCreated()).append(", ");
		return buf.toString();    
    }
}
