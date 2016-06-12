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


import com.autosite.db.AutositeSessionContextEntityDAO;
import com.autosite.db.AutositeSessionContextEntityExtentDAO;
import com.autosite.db.AutositeSessionContextEntity;
import com.surveygen.db.BaseMemoryDAO;

public class AutositeSessionContextEntityDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;
    protected Map m_SerialToOneMap;






    private static Logger m_logger = Logger.getLogger(AutositeSessionContextEntityDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static AutositeSessionContextEntityDS m_AutositeSessionContextEntityDS = new AutositeSessionContextEntityDSExtent();



    public static AutositeSessionContextEntityDS getInstance() {
        return m_AutositeSessionContextEntityDS;
    }

    public static synchronized AutositeSessionContextEntityDS getInstance(long id) {
        AutositeSessionContextEntityDS ret = (AutositeSessionContextEntityDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new AutositeSessionContextEntityDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_AutositeSessionContextEntityDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((AutositeSessionContextEntityDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("AutositeSessionContextEntity loaded from DB. num=" + m_idToMap.size());
        
    }


    protected AutositeSessionContextEntityDS() {
        m_dao = new AutositeSessionContextEntityExtentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SerialToOneMap = new ConcurrentHashMap();
 
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected AutositeSessionContextEntityDS(long id) {
        m_dao = new AutositeSessionContextEntityDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SerialToOneMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public AutositeSessionContextEntity getById(Long id) {
        return (AutositeSessionContextEntity) m_idToMap.get(id);
    }

    public AutositeSessionContextEntity getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                AutositeSessionContextEntity loaded = (AutositeSessionContextEntity) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return AutositeSessionContextEntity.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        AutositeSessionContextEntity o = (AutositeSessionContextEntity)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("AutositeSessionContextEntity removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("AutositeSessionContextEntity added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSerialOneMap(obj, del);
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
        AutositeSessionContextEntity o = (AutositeSessionContextEntity)obj;

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
            if (m_debug) m_logger.debug("AutositeSessionContextEntity removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("AutositeSessionContextEntity added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneStringKey - Serial

    public AutositeSessionContextEntity getObjectBySerial(String keySerial) {
    	if (keySerial == null) return null; 
        return (AutositeSessionContextEntity)m_SerialToOneMap.get(keySerial);
    }

    private void updateSerialOneMap(Object obj, boolean del) {
        AutositeSessionContextEntity o = (AutositeSessionContextEntity)obj;
        String _Serial =  o.getSerial();

		if (  _Serial == null || _Serial.isEmpty() ) return;

        if (del) {
            // delete from SerialToMap

            if (m_SerialToOneMap.containsKey(_Serial)){
                m_SerialToOneMap.remove(_Serial);
                 if (m_debug) m_logger.debug("AutositeSessionContextEntity removed from SerialToMap" + o.getId() + " for [" + _Serial+ "]");
            } else {
                if (m_debug) m_logger.debug("AutositeSessionContextEntity not removed from SerialToMap" + o.getId() + " for [" + _Serial+ "]");
            } 
        }
        else {
            
            if (m_SerialToOneMap.containsKey(_Serial)){
                if (m_debug) m_logger.debug("AutositeSessionContextEntity repalced SerialToMap" + o.getId() + " for [" + _Serial+ "]");
            } else {
                if (m_debug) m_logger.debug("AutositeSessionContextEntity added to SerialToMap" + o.getId() + " for [" + _Serial+ "]");
            } 
            m_SerialToOneMap.put(_Serial, o);
        }
    }











	//

    public static void main(String[] args) throws Exception {

        AutositeSessionContextEntityDS ds = new AutositeSessionContextEntityDS();
        AutositeSessionContextEntity obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static AutositeSessionContextEntity createDefault(){

        AutositeSessionContextEntity _AutositeSessionContextEntity = new AutositeSessionContextEntity();        
	    _AutositeSessionContextEntity = new AutositeSessionContextEntity();// AutositeSessionContextEntityDS.getInstance().getDeafult();
        return _AutositeSessionContextEntity;
    }

    public static AutositeSessionContextEntity copy(AutositeSessionContextEntity org){

    	AutositeSessionContextEntity ret = new AutositeSessionContextEntity();

		ret.setSerial(org.getSerial());
		ret.setIsLogin(org.getIsLogin());
		ret.setTimeLogin(org.getTimeLogin());
		ret.setTimeLastAccess(org.getTimeLastAccess());
		ret.setLoginUserId(org.getLoginUserId());
		ret.setSessionType(org.getSessionType());
		ret.setRemoteDeviceId(org.getRemoteDeviceId());
		ret.setRemoteIp(org.getRemoteIp());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(AutositeSessionContextEntity autositeSessionContextEntity, Logger logger){
		logger.debug("AutositeSessionContextEntity [" + autositeSessionContextEntity.getId() + "]" + objectToString(autositeSessionContextEntity));		
    }

	public static String objectToString(AutositeSessionContextEntity autositeSessionContextEntity){
		StringBuilder buf = new StringBuilder();
        buf.append("AutositeSessionContextEntity=");
		buf.append("Id=").append(autositeSessionContextEntity.getId()).append(", ");
		buf.append("SiteId=").append(autositeSessionContextEntity.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		AutositeSessionContextEntity autositeSessionContextEntity = (AutositeSessionContextEntity)object;
		StringBuilder buf = new StringBuilder();
        buf.append("AutositeSessionContextEntity=");
		buf.append("Id=").append(autositeSessionContextEntity.getId()).append(", ");
		buf.append("SiteId=").append(autositeSessionContextEntity.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
