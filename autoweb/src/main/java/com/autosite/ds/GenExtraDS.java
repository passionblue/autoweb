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

import com.autosite.db.GenExtra;
import com.autosite.db.GenExtraDAO;
import com.jtrend.service.DomainStore;

public class GenExtraDS extends AbstractDS implements DomainStore {

    protected Map m_MainIdToMap;
    protected Map m_SubIdToMap;






    private static Logger m_logger = Logger.getLogger(GenExtraDS.class);
    private static GenExtraDS m_GenExtraDS = null;

    public static synchronized GenExtraDS getInstance() {
        if (m_GenExtraDS == null) {
            m_GenExtraDS = new GenExtraDS();
        }
        return m_GenExtraDS;
    }

    public static synchronized GenExtraDS getInstance(long id) {
        GenExtraDS ret = (GenExtraDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new GenExtraDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_GenExtraDS;
    }



    private static Map m_dsMap = new HashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

        } else {
            super.loadFromDB();
        }
    }


    protected GenExtraDS() {
        m_dao = new GenExtraDAO();
        m_idToMap = new HashMap();

        m_MainIdToMap = new HashMap();
        m_SubIdToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected GenExtraDS(long id) {
		m_loadById = id;
        m_dao = new GenExtraDAO();
        m_idToMap = new HashMap();

        m_MainIdToMap = new HashMap();
        m_SubIdToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }



    public GenExtra getById(Long id) {
        return (GenExtra) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        GenExtra o = (GenExtra)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            m_logger.debug("GenExtra removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            m_logger.debug("GenExtra added to DS " + o.getId());
        }

		updateMainIdMap(obj, del);
		updateSubIdMap(obj, del);
    }


    public List getByMainId(long MainId) {
        
        Long _MainId  = new Long(MainId);
        if (m_MainIdToMap.containsKey(_MainId)) {
            return new ArrayList( ((Map)m_MainIdToMap.get(_MainId)).values() );
        } else {
            return new ArrayList();
        }
    }

	private void updateMainIdMap(Object obj, boolean del) {
        GenExtra o = (GenExtra)obj;

        if (del) {

            // delete from MainIdToMap
            Map map  = (Map) m_MainIdToMap.get(new Long(o.getMainId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("GenExtra removed from MainIdToMap" + o.getId() + " from " + o.getMainId());
        }
        else {
            
            // add to MainIdToMap
            Map map  = (Map) m_MainIdToMap.get(new Long(o.getMainId()));
            if ( map == null ) {
                map = new TreeMap();
                m_MainIdToMap.put(new Long(o.getMainId()), map);
            	m_logger.debug("created new   MainIdToMap for " + o.getMainId());

            }
            map.put(new Long(o.getId()), o);            
            m_logger.debug("GenExtra added to MainIdToMap " + o.getId() + " to " + o.getMainId());
        }
	}

    public List getBySubId(long SubId) {
        
        Long _SubId  = new Long(SubId);
        if (m_SubIdToMap.containsKey(_SubId)) {
            return new ArrayList( ((Map)m_SubIdToMap.get(_SubId)).values() );
        } else {
            return new ArrayList();
        }
    }

	private void updateSubIdMap(Object obj, boolean del) {
        GenExtra o = (GenExtra)obj;

        if (del) {

            // delete from SubIdToMap
            Map map  = (Map) m_SubIdToMap.get(new Long(o.getSubId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("GenExtra removed from SubIdToMap" + o.getId() + " from " + o.getSubId());
        }
        else {
            
            // add to SubIdToMap
            Map map  = (Map) m_SubIdToMap.get(new Long(o.getSubId()));
            if ( map == null ) {
                map = new TreeMap();
                m_SubIdToMap.put(new Long(o.getSubId()), map);
            	m_logger.debug("created new   SubIdToMap for " + o.getSubId());

            }
            map.put(new Long(o.getId()), o);            
            m_logger.debug("GenExtra added to SubIdToMap " + o.getId() + " to " + o.getSubId());
        }
	}


    



    public static void main(String[] args) throws Exception {

        GenExtraDS ds = new GenExtraDS();
        GenExtra obj = ds.getById((long)1);
        System.out.println(obj);

    }

	public static GenExtra createDefault(){

		GenExtra ret = new GenExtra();		
//		ret.setMainId("");			
//		ret.setSubId("");			
//		ret.setExtraValue("");			
//		ret.setExtraData("");			
//		ret.setTimeCreated("");			
//		ret.setTimeUpdated("");			
		return ret;
	}
}
