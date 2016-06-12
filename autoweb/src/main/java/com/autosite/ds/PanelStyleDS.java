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

import com.autosite.db.PanelStyle;
import com.autosite.db.PanelStyleDAO;
import com.jtrend.service.DomainStore;

public class PanelStyleDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PanelIdToMap;
    protected Map m_StyleIdToMap;





    private static boolean m_debug = false;
    private static Logger m_logger = Logger.getLogger(PanelStyleDS.class);
    private static PanelStyleDS m_PanelStyleDS = null;

    public static synchronized PanelStyleDS getInstance() {
        if (m_PanelStyleDS == null) {
            m_PanelStyleDS = new PanelStyleDS();
        }
        return m_PanelStyleDS;
    }

    public static synchronized PanelStyleDS getInstance(long id) {
        PanelStyleDS ret = (PanelStyleDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PanelStyleDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PanelStyleDS;
    }



    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PanelStyleDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }


    protected PanelStyleDS() {
        m_dao = new PanelStyleDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PanelIdToMap = new ConcurrentHashMap();
        m_StyleIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PanelStyleDS(long id) {
		m_loadById = id;
        m_dao = new PanelStyleDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PanelIdToMap = new ConcurrentHashMap();
        m_StyleIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }



    public PanelStyle getById(Long id) {
        return (PanelStyle) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        PanelStyle o = (PanelStyle)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PanelStyle removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            m_logger.debug("PanelStyle added to DS " + o.getId());
        }

		updateSiteIdMap(obj, del);
		updatePanelIdMap(obj, del);
		updateStyleIdMap(obj, del);
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
        PanelStyle o = (PanelStyle)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("PanelStyle removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            m_logger.debug("PanelStyle added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
	}

    public List getByPanelId(long PanelId) {
        
        Long _PanelId  = new Long(PanelId);
        if (m_PanelIdToMap.containsKey(_PanelId)) {
            return new ArrayList( ((Map)m_PanelIdToMap.get(_PanelId)).values() );
        } else {
            return new ArrayList();
        }
    }

	private void updatePanelIdMap(Object obj, boolean del) {
        PanelStyle o = (PanelStyle)obj;

        if (del) {

            // delete from PanelIdToMap
            Map map  = (Map) m_PanelIdToMap.get(new Long(o.getPanelId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("PanelStyle removed from PanelIdToMap" + o.getId() + " from " + o.getPanelId());
        }
        else {
            
            // add to PanelIdToMap
            Map map  = (Map) m_PanelIdToMap.get(new Long(o.getPanelId()));
            if ( map == null ) {
                map = new TreeMap();
                m_PanelIdToMap.put(new Long(o.getPanelId()), map);
            	m_logger.debug("created new   PanelIdToMap for " + o.getPanelId());

            }
            map.put(new Long(o.getId()), o);            
            m_logger.debug("PanelStyle added to PanelIdToMap " + o.getId() + " to " + o.getPanelId());
        }
	}

    public List getByStyleId(long StyleId) {
        
        Long _StyleId  = new Long(StyleId);
        if (m_StyleIdToMap.containsKey(_StyleId)) {
            return new ArrayList( ((Map)m_StyleIdToMap.get(_StyleId)).values() );
        } else {
            return new ArrayList();
        }
    }

	private void updateStyleIdMap(Object obj, boolean del) {
        PanelStyle o = (PanelStyle)obj;

        if (del) {

            // delete from StyleIdToMap
            Map map  = (Map) m_StyleIdToMap.get(new Long(o.getStyleId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("PanelStyle removed from StyleIdToMap" + o.getId() + " from " + o.getStyleId());
        }
        else {
            
            // add to StyleIdToMap
            Map map  = (Map) m_StyleIdToMap.get(new Long(o.getStyleId()));
            if ( map == null ) {
                map = new TreeMap();
                m_StyleIdToMap.put(new Long(o.getStyleId()), map);
            	m_logger.debug("created new   StyleIdToMap for " + o.getStyleId());

            }
            map.put(new Long(o.getId()), o);            
            m_logger.debug("PanelStyle added to StyleIdToMap " + o.getId() + " to " + o.getStyleId());
        }
	}


    



    public static void main(String[] args) throws Exception {

        PanelStyleDS ds = new PanelStyleDS();
        PanelStyle obj = ds.getById((long)1);
        System.out.println(obj);

    }

	public static PanelStyle createDefault(){

		PanelStyle ret = new PanelStyle();		
//		ret.setPanelId("");			
//		ret.setStyleId("");			
		return ret;
	}
}
