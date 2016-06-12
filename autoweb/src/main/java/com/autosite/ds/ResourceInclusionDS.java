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


import com.autosite.db.ResourceInclusionDAO;
import com.autosite.db.ResourceInclusion;
import com.surveygen.db.BaseMemoryDAO;

public class ResourceInclusionDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;





    protected Map m_SiteIdToNameToOneMap;

    private static Logger m_logger = Logger.getLogger(ResourceInclusionDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static ResourceInclusionDS m_ResourceInclusionDS = new ResourceInclusionDSExtent();



    public static ResourceInclusionDS getInstance() {
        return m_ResourceInclusionDS;
    }

    public static synchronized ResourceInclusionDS getInstance(long id) {
        ResourceInclusionDS ret = (ResourceInclusionDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ResourceInclusionDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ResourceInclusionDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ResourceInclusionDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ResourceInclusion loaded from DB. num=" + m_idToMap.size());
        
    }


    protected ResourceInclusionDS() {
        m_dao = new ResourceInclusionDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToNameToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ResourceInclusionDS(long id) {
        m_dao = new ResourceInclusionDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToNameToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ResourceInclusion getById(Long id) {
        return (ResourceInclusion) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        ResourceInclusion o = (ResourceInclusion)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ResourceInclusion removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ResourceInclusion added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdToNameOneMap(obj, del);
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
        ResourceInclusion o = (ResourceInclusion)obj;

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
            if (m_debug) m_logger.debug("ResourceInclusion removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ResourceInclusion added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    









	// listOneToOneStringToSiteIdKey - Name

    public  ResourceInclusion getBySiteIdToName(long siteId, String Name) {

        Long keySiteIdTo  = new Long(siteId);
        if (m_SiteIdToNameToOneMap.containsKey(keySiteIdTo)) {
            
            Map mapSiteIdTo = (Map)m_SiteIdToNameToOneMap.get(keySiteIdTo);

     	    String keyName = Name;
            
            if ( keyName!= null && mapSiteIdTo.containsKey(keyName)){
                return ( ResourceInclusion)mapSiteIdTo.get(keyName);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    public List getByOrderName(){
        List ret = new ArrayList();
    
        List contentsByName = new ArrayList( m_SiteIdToNameToOneMap.values());
        Map map  = new TreeMap();
        
        for (Iterator iterator = contentsByName.iterator(); iterator.hasNext();) {
            ResourceInclusion obj = (ResourceInclusion) iterator.next();
            if ( obj.getName() == null ) continue;
            map.put(obj.getName(), obj);
        }
        
        return new ArrayList(map.values());
    }

    private void updateSiteIdToNameOneMap(Object obj, boolean del) {
        ResourceInclusion o = (ResourceInclusion)obj;

   	    String keyName = o.getName();

		if ( keyName == null || keyName.isEmpty() ) {
			m_logger.warn("Passed key is null in updateSiteIdToNameOneMap for the ResourceInclusion object. ABORTED id = " + o.getId());
            return;
        }

        if (del) {
            // delete from PollIdNameToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToNameToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo != null ) {
                if (mapSiteIdTo.containsKey(keyName)){
                    mapSiteIdTo.remove(keyName);
                }
            }
            m_logger.debug("removed ResourceInclusion from m_SiteIdToNameToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getName());
        }
        else {
            
            // add to SiteIdToNameToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToNameToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo == null ) {
                mapSiteIdTo = new TreeMap();
                m_SiteIdToNameToOneMap.put(new Long(o.getSiteId()), mapSiteIdTo);
                if (m_debug) m_logger.debug("created new   mapSiteIdTo for " + o.getSiteId());
            }
            
			ResourceInclusion replaced = (ResourceInclusion) mapSiteIdTo.put(keyName,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdToNameOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("ResourceInclusion added to SiteIdToNameToOneMap " + o.getId() + " to " + o.getSiteId());
        }
    }    


	//

    public static void main(String[] args) throws Exception {

        ResourceInclusionDS ds = new ResourceInclusionDS();
        ResourceInclusion obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static ResourceInclusion createDefault(){

        ResourceInclusion _ResourceInclusion = new ResourceInclusion();        
	    _ResourceInclusion = new ResourceInclusion();// ResourceInclusionDS.getInstance().getDeafult();
        return _ResourceInclusion;
    }

    public static ResourceInclusion copy(ResourceInclusion org){

    	ResourceInclusion ret = new ResourceInclusion();

		ret.setName(org.getName());
		ret.setInclude(org.getInclude());
		ret.setResourceType(org.getResourceType());

        return ret;
    }

	public static void objectToLog(ResourceInclusion resourceInclusion, Logger logger){
		logger.debug("ResourceInclusion [" + resourceInclusion.getId() + "]" + objectToString(resourceInclusion));		
    }

	public static String objectToString(ResourceInclusion resourceInclusion){
		StringBuilder buf = new StringBuilder();
        buf.append("ResourceInclusion=");
		buf.append("Id=").append(resourceInclusion.getId()).append(", ");
		buf.append("SiteId=").append(resourceInclusion.getSiteId()).append(", ");
		buf.append("Name=").append(resourceInclusion.getName()).append(", ");
		buf.append("Include=").append(resourceInclusion.getInclude()).append(", ");
		buf.append("ResourceType=").append(resourceInclusion.getResourceType()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		ResourceInclusion resourceInclusion = (ResourceInclusion)object;
		StringBuilder buf = new StringBuilder();
        buf.append("ResourceInclusion=");
		buf.append("Id=").append(resourceInclusion.getId()).append(", ");
		buf.append("SiteId=").append(resourceInclusion.getSiteId()).append(", ");
		buf.append("Name=").append(resourceInclusion.getName()).append(", ");
		buf.append("Include=").append(resourceInclusion.getInclude()).append(", ");
		buf.append("ResourceType=").append(resourceInclusion.getResourceType()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 
    public boolean getData(String arg, int arg2) {
		return true;
	}
    public void getHyunjoo() {
	}

}
