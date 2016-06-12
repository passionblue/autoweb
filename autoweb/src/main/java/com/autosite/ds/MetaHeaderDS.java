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
import com.autosite.db.MetaHeader;
import com.jtrend.service.DomainStore;

import com.autosite.db.MetaHeaderDAO;

public class MetaHeaderDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;

	protected Map m_DetailIdSourceToMap;






    private static Logger m_logger = Logger.getLogger(MetaHeaderDS.class);
    private static MetaHeaderDS m_MetaHeaderDS = new MetaHeaderDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static MetaHeaderDS getInstance() {
        return m_MetaHeaderDS;
    }

    public static synchronized MetaHeaderDS getInstance(long id) {
        MetaHeaderDS ret = (MetaHeaderDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new MetaHeaderDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_MetaHeaderDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((MetaHeaderDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("MetaHeader loaded from DB. num=" + m_idToMap.size());
        
    }

    protected MetaHeaderDS() {
        m_dao = new MetaHeaderDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
		m_DetailIdSourceToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected MetaHeaderDS(long id) {
        m_dao = new MetaHeaderDAO();
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

    public MetaHeader getById(Long id) {
        return (MetaHeader) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        MetaHeader o = (MetaHeader)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("MetaHeader removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("MetaHeader added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
		updateDetailIdSourceMap(obj, del);
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
        MetaHeader o = (MetaHeader)obj;

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
            if (m_debug) m_logger.debug("MetaHeader removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("MetaHeader added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    




        
    public List getByDetailIdSource(long DetailId, String Source) {

        Long keyDetailId  = new Long(DetailId);
        if (m_DetailIdSourceToMap.containsKey(keyDetailId)) {
            
            Map mapDetailId = (Map)m_DetailIdSourceToMap.get(keyDetailId);

     	    String keySource =  Source;
			if (  keySource == null || keySource.isEmpty()) return new ArrayList();
            
            if ( mapDetailId.containsKey(keySource)){
                return new ArrayList( ((Map)mapDetailId.get(keySource)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapByDetailIdSource(long DetailId, String Source) {

        Long keyDetailId  = new Long(DetailId);
        if (m_DetailIdSourceToMap.containsKey(keyDetailId)) {
            
            Map mapDetailId = (Map)m_DetailIdSourceToMap.get(keyDetailId);

     	    String keySource =  Source;
			if (  keySource == null || keySource.isEmpty()) return new HashMap();
            
            if ( mapDetailId.containsKey(keySource)){
                return (Map)mapDetailId.get(keySource);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateDetailIdSourceMap(Object obj, boolean del) {
        MetaHeader o = (MetaHeader)obj;

   	    String keySource =  o.getSource();

		if (  keySource == null || keySource.isEmpty()) return;

        if (del) {
            // delete from DetailIdSourceToMap
            Map mapDetailId  = (Map) m_DetailIdSourceToMap.get(new Long(o.getDetailId()));
            if ( mapDetailId != null ) {
                Map mapSource = (Map) mapDetailId.get(keySource);
                if (mapSource != null){
                    mapSource.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed MetaHeader from m_DetailIdSourceToMap" + o.getId() + " from " + o.getDetailId() + " # " + o.getSource());
        }
        else {
            
            // add to DetailIdSourceToMap
            Map mapDetailId  = (Map) m_DetailIdSourceToMap.get(new Long(o.getDetailId()));
            if ( mapDetailId == null ) {
                mapDetailId = new TreeMap();
                m_DetailIdSourceToMap.put(new Long(o.getDetailId()), mapDetailId);
                if (m_debug) m_logger.debug("created new   mapDetailId for " + o.getDetailId());
            }
            Map mapSource = (Map) mapDetailId.get(keySource);
            
            if ( mapSource == null) {
                mapSource = new TreeMap();
                mapDetailId.put(keySource, mapSource);
                if (m_debug) m_logger.debug("created new   mapDetailId for " + o.getDetailId());
            }
            mapSource.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to DetailIdSourceToMap " + o.getId() + " to " + o.getDetailId());
        }
        
    }    
        










    public static void main(String[] args) throws Exception {

        MetaHeaderDS ds = new MetaHeaderDS();
        MetaHeader obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static MetaHeader createDefault(){

        MetaHeader ret = new MetaHeader();        
//      ret.setSource("");           
//      ret.setDetailId("");           
//      ret.setName("");           
//      ret.setValue("");           
//      ret.setHttpEquiv("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static MetaHeader copy(MetaHeader org){

    	MetaHeader ret = new MetaHeader();

		ret.setSource(org.getSource());
		ret.setDetailId(org.getDetailId());
		ret.setName(org.getName());
		ret.setValue(org.getValue());
		ret.setHttpEquiv(org.getHttpEquiv());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(MetaHeader metaHeader, Logger logger){
		logger.debug("MetaHeader [" + metaHeader.getId() + "]" + objectToString(metaHeader));		
    }

	public static String objectToString(MetaHeader metaHeader){
		StringBuffer buf = new StringBuffer();
        buf.append("MetaHeader=");
		buf.append("Id=").append(metaHeader.getId()).append(", ");
		buf.append("SiteId=").append(metaHeader.getSiteId()).append(", ");
		buf.append("Source=").append(metaHeader.getSource()).append(", ");
		buf.append("DetailId=").append(metaHeader.getDetailId()).append(", ");
		buf.append("Name=").append(metaHeader.getName()).append(", ");
		buf.append("Value=").append(metaHeader.getValue()).append(", ");
		return buf.toString();    
    }
}
