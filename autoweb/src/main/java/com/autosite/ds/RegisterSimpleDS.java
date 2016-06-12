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
import com.autosite.db.AutositeDataObject;
import com.jtrend.service.DomainStore;
import com.autosite.holder.DataHolderObject;


import com.autosite.db.RegisterSimpleDAO;
import com.autosite.db.RegisterSimple;
import com.surveygen.db.BaseMemoryDAO;

public class RegisterSimpleDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(RegisterSimpleDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static RegisterSimpleDS m_RegisterSimpleDS = new RegisterSimpleDSExtent();



    public static RegisterSimpleDS getInstance() {
        return m_RegisterSimpleDS;
    }

    public static synchronized RegisterSimpleDS getInstance(long id) {
        RegisterSimpleDS ret = (RegisterSimpleDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new RegisterSimpleDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_RegisterSimpleDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((RegisterSimpleDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("RegisterSimple loaded from DB. num=" + m_idToMap.size());
        
    }


    protected RegisterSimpleDS() {
        m_dao = new RegisterSimpleDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected RegisterSimpleDS(long id) {
        m_dao = new RegisterSimpleDAO();
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

    public RegisterSimple getById(Long id) {
        return (RegisterSimple) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        RegisterSimple o = (RegisterSimple)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("RegisterSimple removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("RegisterSimple added to DS " + o.getId());
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
            return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        RegisterSimple o = (RegisterSimple)obj;

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
            if (m_debug) m_logger.debug("RegisterSimple removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("RegisterSimple added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        RegisterSimpleDS ds = new RegisterSimpleDS();
        RegisterSimple obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static RegisterSimple createDefault(){

        RegisterSimple _RegisterSimple = new RegisterSimple();        
	    _RegisterSimple = new RegisterSimple();// RegisterSimpleDS.getInstance().getDeafult();
        return _RegisterSimple;
    }

    public static RegisterSimple copy(RegisterSimple org){

    	RegisterSimple ret = new RegisterSimple();

		ret.setFirstName(org.getFirstName());
		ret.setLastName(org.getLastName());
		ret.setEmail(org.getEmail());
		ret.setUsername(org.getUsername());
		ret.setPassword(org.getPassword());
		ret.setBirthYear(org.getBirthYear());
		ret.setBirthMonth(org.getBirthMonth());
		ret.setBirthDay(org.getBirthDay());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(RegisterSimple registerSimple, Logger logger){
		logger.debug("RegisterSimple [" + registerSimple.getId() + "]" + objectToString(registerSimple));		
    }

	public static String objectToString(RegisterSimple registerSimple){
		StringBuilder buf = new StringBuilder();
        buf.append("RegisterSimple=");
		buf.append("Id=").append(registerSimple.getId()).append(", ");
		buf.append("SiteId=").append(registerSimple.getSiteId()).append(", ");
		buf.append("Username=").append(registerSimple.getUsername()).append(", ");
		buf.append("Email=").append(registerSimple.getEmail()).append(", ");
		buf.append("FirstName=").append(registerSimple.getFirstName()).append(", ");
		buf.append("LastName=").append(registerSimple.getLastName()).append(", ");
		buf.append("TimeCreated=").append(registerSimple.getTimeCreated()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		RegisterSimple registerSimple = (RegisterSimple)object;
		StringBuilder buf = new StringBuilder();
        buf.append("RegisterSimple=");
		buf.append("Id=").append(registerSimple.getId()).append(", ");
		buf.append("SiteId=").append(registerSimple.getSiteId()).append(", ");
		buf.append("Username=").append(registerSimple.getUsername()).append(", ");
		buf.append("Email=").append(registerSimple.getEmail()).append(", ");
		buf.append("FirstName=").append(registerSimple.getFirstName()).append(", ");
		buf.append("LastName=").append(registerSimple.getLastName()).append(", ");
		buf.append("TimeCreated=").append(registerSimple.getTimeCreated()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
