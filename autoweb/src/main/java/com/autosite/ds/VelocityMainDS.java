package com.autosite.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.autosite.db.VelocityMain;
import com.autosite.db.VelocityMainDAO;
import com.jtrend.service.DomainStore;

public class VelocityMainDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PageIdToMap;


    protected Map m_DataToMap;


    private static Logger m_logger = Logger.getLogger(VelocityMainDS.class);
    private static VelocityMainDS m_VelocityMainDS = null;

    public static synchronized VelocityMainDS getInstance() {
        if (m_VelocityMainDS == null) {
            m_VelocityMainDS = new VelocityMainDS();
        }
        return m_VelocityMainDS;
    }

    protected VelocityMainDS() {
        m_dao = new VelocityMainDAO();
        m_idToMap = new HashMap();

        m_SiteIdToMap = new HashMap();
        m_PageIdToMap = new HashMap();
        m_DataToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e);
        }
    }


    public VelocityMain getById(Long id) {
        return (VelocityMain) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        VelocityMain o = (VelocityMain)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            m_logger.debug("VelocityMain removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            m_logger.debug("VelocityMain added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updatePageIdMap(obj, del);
        updateDataMap(obj, del);
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
        VelocityMain o = (VelocityMain)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("VelocityMain removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
        }
        else {
            
            // add to SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map == null ) {
                map = new TreeMap();
                m_SiteIdToMap.put(new Long(o.getSiteId()), map);
                m_logger.debug("created new   SiteIdToMap for " + o.getSiteId());

            }
            map.put(new Long(o.getId()), o);            
            m_logger.debug("VelocityMain added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }

    public List getByPageId(long PageId) {
        
        Long _PageId  = new Long(PageId);
        if (m_PageIdToMap.containsKey(_PageId)) {
            return new ArrayList( ((Map)m_PageIdToMap.get(_PageId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updatePageIdMap(Object obj, boolean del) {
        VelocityMain o = (VelocityMain)obj;

        if (del) {

            // delete from PageIdToMap
            Map map  = (Map) m_PageIdToMap.get(new Long(o.getPageId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("VelocityMain removed from PageIdToMap" + o.getId() + " from " + o.getPageId());
        }
        else {
            
            // add to PageIdToMap
            Map map  = (Map) m_PageIdToMap.get(new Long(o.getPageId()));
            if ( map == null ) {
                map = new TreeMap();
                m_PageIdToMap.put(new Long(o.getPageId()), map);
                m_logger.debug("created new   PageIdToMap for " + o.getPageId());

            }
            map.put(new Long(o.getId()), o);            
            m_logger.debug("VelocityMain added to PageIdToMap " + o.getId() + " to " + o.getPageId());
        }
    }


    public List getByData(String keyData) {
        
        if (m_DataToMap.containsKey(keyData)) {
            return new ArrayList( ((Map)m_DataToMap.get(keyData)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateDataMap(Object obj, boolean del) {
        VelocityMain o = (VelocityMain)obj;

        if (del) {

            // delete from DataToMap
            Map map  = (Map) m_DataToMap.get(o.getData());
            if ( map != null ) {
                map.remove(new Long(o.getId()));
                m_logger.debug("VelocityMain removed from DataToMap" + o.getId() + " from [" + o.getData() + "]");
            } else {
                m_logger.debug("VelocityMain NOT removed from DataToMap because no map found for [" + o.getData() + "]");
            }
        }
        else {
            
            // add to DataToMap
            Map map  = (Map) m_DataToMap.get(o.getData());
            if ( map == null ) {
                map = new TreeMap();
                m_DataToMap.put(o.getData(), map);
                m_logger.debug("created new   DataToMap for [" + o.getData() + "]");
            }
            map.put(new Long(o.getId()), o);            
            m_logger.debug("VelocityMain added to DataToMap " + o.getId() + " to [" + o.getData() + "]");
        }
    }

    

    public static void main(String[] args) throws Exception {

        VelocityMainDS ds = new VelocityMainDS();
        VelocityMain obj = ds.getById((long)1);
        System.out.println(obj);

        List listSiteId = ds.getBySiteId((long)3);
        System.out.println(listSiteId);
        List listPageId = ds.getByPageId((long)3);
        System.out.println(listPageId);
    }
}
