package com.autosite.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.autosite.db.ResourceList;
import com.autosite.db.ResourceListDAO;
import com.jtrend.service.DomainStore;

public class ResourceListDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;

    protected Map m_UrlToMap;



    private static Logger m_logger = Logger.getLogger(ResourceListDS.class);
    private static ResourceListDS m_ResourceListDS = null;

    public static synchronized ResourceListDS getInstance() {
        if (m_ResourceListDS == null) {
            m_ResourceListDS = new ResourceListDS();
        }
        return m_ResourceListDS;
    }

    public static synchronized ResourceListDS getInstance(long id) {
        ResourceListDS ret = (ResourceListDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ResourceListDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ResourceListDS;
    }



    private static Map m_dsMap = new HashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {
            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ResourceListDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
            
        } else {
            super.loadFromDB();
        }
    }


    protected ResourceListDS() {
        m_dao = new ResourceListDAO();
        m_idToMap = new HashMap();

        m_SiteIdToMap = new HashMap();
        m_UrlToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ResourceListDS(long id) {
		m_loadById = id;
        m_dao = new ResourceListDAO();
        m_idToMap = new HashMap();

        m_SiteIdToMap = new HashMap();
        m_UrlToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }



    public ResourceList getById(Long id) {
        return (ResourceList) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ResourceList o = (ResourceList)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            m_logger.debug("ResourceList removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            m_logger.debug("ResourceList added to DS " + o.getId());
        }

		updateSiteIdMap(obj, del);
		updateUrlMap(obj, del);
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
        ResourceList o = (ResourceList)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("ResourceList removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            m_logger.debug("ResourceList added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
	}


    

    public ResourceList getByUrl(String keyUrl) {
		return (ResourceList)m_UrlToMap.get(keyUrl);
    }

	private void updateUrlMap(Object obj, boolean del) {
        ResourceList o = (ResourceList)obj;
		String _Url = 	o.getUrl();

        if (del) {
            // delete from UrlToMap

			if (m_UrlToMap.containsKey(_Url)){
                m_UrlToMap.remove(_Url);
            	m_logger.debug("ResourceList removed from UrlToMap" + o.getId() + " for [" + _Url+ "]");
			} else {
            	m_logger.debug("ResourceList removed from UrlToMap" + o.getId() + " for [" + _Url+ "]");
			} 
        }
        else {
            
			if (m_UrlToMap.containsKey(_Url)){
            	m_logger.debug("ResourceList repalced UrlToMap" + o.getId() + " for [" + _Url+ "]");
			} else {
            	m_logger.debug("ResourceList added to UrlToMap" + o.getId() + " for [" + _Url+ "]");
			} 
            m_UrlToMap.put(_Url, o);
        }
	}

    public static void main(String[] args) throws Exception {

        ResourceListDS ds = new ResourceListDS();
        ResourceList obj = ds.getById((long)1);
        System.out.println(obj);

    }

	public static ResourceList createDefault(){

		ResourceList ret = new ResourceList();		
		ret.setUrl("");			
		ret.setOriginalName("");			
		return ret;
	}
}
