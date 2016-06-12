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


import com.autosite.db.AutositeRemoteDeviceDAO;
import com.autosite.db.AutositeRemoteDeviceExtentDAO;
import com.autosite.db.AutositeRemoteDevice;
import com.surveygen.db.BaseMemoryDAO;

public class AutositeRemoteDeviceDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;





    protected Map m_SiteIdToDeviceIdToOneMap;

    private static Logger m_logger = Logger.getLogger(AutositeRemoteDeviceDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static AutositeRemoteDeviceDS m_AutositeRemoteDeviceDS = new AutositeRemoteDeviceDSExtent();



    public static AutositeRemoteDeviceDS getInstance() {
        return m_AutositeRemoteDeviceDS;
    }

    public static synchronized AutositeRemoteDeviceDS getInstance(long id) {
        AutositeRemoteDeviceDS ret = (AutositeRemoteDeviceDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new AutositeRemoteDeviceDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_AutositeRemoteDeviceDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((AutositeRemoteDeviceDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("AutositeRemoteDevice loaded from DB. num=" + m_idToMap.size());
        
    }


    protected AutositeRemoteDeviceDS() {
        m_dao = new AutositeRemoteDeviceExtentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToDeviceIdToOneMap = new ConcurrentHashMap();
 
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected AutositeRemoteDeviceDS(long id) {
        m_dao = new AutositeRemoteDeviceDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToDeviceIdToOneMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public AutositeRemoteDevice getById(Long id) {
        return (AutositeRemoteDevice) m_idToMap.get(id);
    }

    public AutositeRemoteDevice getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                AutositeRemoteDevice loaded = (AutositeRemoteDevice) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return AutositeRemoteDevice.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        AutositeRemoteDevice o = (AutositeRemoteDevice)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("AutositeRemoteDevice removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("AutositeRemoteDevice added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdToDeviceIdOneMap(obj, del);
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
        AutositeRemoteDevice o = (AutositeRemoteDevice)obj;

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
            if (m_debug) m_logger.debug("AutositeRemoteDevice removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("AutositeRemoteDevice added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    









	// listOneToOneStringToSiteIdKey - DeviceId

    public  AutositeRemoteDevice getBySiteIdToDeviceId(long siteId, String DeviceId) {

        Long keySiteIdTo  = new Long(siteId);
        if (m_SiteIdToDeviceIdToOneMap.containsKey(keySiteIdTo)) {
            
            Map mapSiteIdTo = (Map)m_SiteIdToDeviceIdToOneMap.get(keySiteIdTo);

     	    String keyDeviceId = DeviceId;
            
            if ( keyDeviceId!= null && mapSiteIdTo.containsKey(keyDeviceId)){
                return ( AutositeRemoteDevice)mapSiteIdTo.get(keyDeviceId);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    public List getByOrderDeviceId(){
        List ret = new ArrayList();
    
        List contentsByDeviceId = new ArrayList( m_SiteIdToDeviceIdToOneMap.values());
        Map map  = new TreeMap();
        
        for (Iterator iterator = contentsByDeviceId.iterator(); iterator.hasNext();) {
            AutositeRemoteDevice obj = (AutositeRemoteDevice) iterator.next();
            if ( obj.getDeviceId() == null ) continue;
            map.put(obj.getDeviceId(), obj);
        }
        
        return new ArrayList(map.values());
    }

    private void updateSiteIdToDeviceIdOneMap(Object obj, boolean del) {
        AutositeRemoteDevice o = (AutositeRemoteDevice)obj;

   	    String keyDeviceId = o.getDeviceId();

		if ( keyDeviceId == null || keyDeviceId.isEmpty() ) {
			m_logger.warn("Passed key is null in updateSiteIdToDeviceIdOneMap for the AutositeRemoteDevice object. ABORTED id = " + o.getId());
            return;
        }

        if (del) {
            // delete from PollIdDeviceIdToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToDeviceIdToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo != null ) {
                if (mapSiteIdTo.containsKey(keyDeviceId)){
                    mapSiteIdTo.remove(keyDeviceId);
                }
            }
            m_logger.debug("removed AutositeRemoteDevice from m_SiteIdToDeviceIdToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getDeviceId());
        }
        else {
            
            // add to SiteIdToDeviceIdToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToDeviceIdToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo == null ) {
                mapSiteIdTo = new TreeMap();
                m_SiteIdToDeviceIdToOneMap.put(new Long(o.getSiteId()), mapSiteIdTo);
                if (m_debug) m_logger.debug("created new   mapSiteIdTo for " + o.getSiteId());
            }
            
			AutositeRemoteDevice replaced = (AutositeRemoteDevice) mapSiteIdTo.put(keyDeviceId,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdToDeviceIdOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("AutositeRemoteDevice added to SiteIdToDeviceIdToOneMap " + o.getId() + " to " + o.getSiteId());
        }
    }    


	//

    public static void main(String[] args) throws Exception {

        AutositeRemoteDeviceDS ds = new AutositeRemoteDeviceDS();
        AutositeRemoteDevice obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static AutositeRemoteDevice createDefault(){

        AutositeRemoteDevice _AutositeRemoteDevice = new AutositeRemoteDevice();        
	    _AutositeRemoteDevice = new AutositeRemoteDevice();// AutositeRemoteDeviceDS.getInstance().getDeafult();
        return _AutositeRemoteDevice;
    }

    public static AutositeRemoteDevice copy(AutositeRemoteDevice org){

    	AutositeRemoteDevice ret = new AutositeRemoteDevice();

		ret.setDeviceId(org.getDeviceId());
		ret.setDeviceType(org.getDeviceType());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(AutositeRemoteDevice autositeRemoteDevice, Logger logger){
		logger.debug("AutositeRemoteDevice [" + autositeRemoteDevice.getId() + "]" + objectToString(autositeRemoteDevice));		
    }

	public static String objectToString(AutositeRemoteDevice autositeRemoteDevice){
		StringBuilder buf = new StringBuilder();
        buf.append("AutositeRemoteDevice=");
		buf.append("Id=").append(autositeRemoteDevice.getId()).append(", ");
		buf.append("SiteId=").append(autositeRemoteDevice.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		AutositeRemoteDevice autositeRemoteDevice = (AutositeRemoteDevice)object;
		StringBuilder buf = new StringBuilder();
        buf.append("AutositeRemoteDevice=");
		buf.append("Id=").append(autositeRemoteDevice.getId()).append(", ");
		buf.append("SiteId=").append(autositeRemoteDevice.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
