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

import com.autosite.db.GenSub;
import com.autosite.db.GenSubDAO;
import com.jtrend.service.DomainStore;

public class GenSubDS extends AbstractDS implements DomainStore {

    protected Map m_MainIdToMap;



    protected Map m_StrKeyToMap;



    private static Logger m_logger = Logger.getLogger(GenSubDS.class);
    private static GenSubDS m_GenSubDS = null;

    public static synchronized GenSubDS getInstance() {
        if (m_GenSubDS == null) {
            m_GenSubDS = new GenSubDS();
        }
        return m_GenSubDS;
    }

    public static synchronized GenSubDS getInstance(long id) {
        GenSubDS ret = (GenSubDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new GenSubDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_GenSubDS;
    }



    private static Map m_dsMap = new HashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

        } else {
            super.loadFromDB();
        }
    }


    protected GenSubDS() {
        m_dao = new GenSubDAO();
        m_idToMap = new HashMap();

        m_MainIdToMap = new HashMap();
        m_StrKeyToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected GenSubDS(long id) {
		m_loadById = id;
        m_dao = new GenSubDAO();
        m_idToMap = new HashMap();

        m_MainIdToMap = new HashMap();
        m_StrKeyToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }



    public GenSub getById(Long id) {
        return (GenSub) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        GenSub o = (GenSub)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            m_logger.debug("GenSub removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            m_logger.debug("GenSub added to DS " + o.getId());
        }

		updateMainIdMap(obj, del);
		updateStrKeyMap(obj, del);
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
        GenSub o = (GenSub)obj;

        if (del) {

            // delete from MainIdToMap
            Map map  = (Map) m_MainIdToMap.get(new Long(o.getMainId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("GenSub removed from MainIdToMap" + o.getId() + " from " + o.getMainId());
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
            m_logger.debug("GenSub added to MainIdToMap " + o.getId() + " to " + o.getMainId());
        }
	}


    

    public GenSub getByStrKey(String keyStrKey) {
		return (GenSub)m_StrKeyToMap.get(keyStrKey);
    }

	private void updateStrKeyMap(Object obj, boolean del) {
        GenSub o = (GenSub)obj;
		String _StrKey = 	o.getStrKey();

        if (del) {
            // delete from StrKeyToMap

			if (m_StrKeyToMap.containsKey(_StrKey)){
                m_StrKeyToMap.remove(_StrKey);
            	m_logger.debug("GenSub removed from StrKeyToMap" + o.getId() + " for [" + _StrKey+ "]");
			} else {
            	m_logger.debug("GenSub not removed from StrKeyToMap" + o.getId() + " for [" + _StrKey+ "]");
			} 
        }
        else {
            
			if (m_StrKeyToMap.containsKey(_StrKey)){
            	m_logger.debug("GenSub repalced StrKeyToMap" + o.getId() + " for [" + _StrKey+ "]");
			} else {
            	m_logger.debug("GenSub added to StrKeyToMap" + o.getId() + " for [" + _StrKey+ "]");
			} 
            m_StrKeyToMap.put(_StrKey, o);
        }
	}



    public static void main(String[] args) throws Exception {

        GenSubDS ds = new GenSubDS();
        GenSub obj = ds.getById((long)1);
        System.out.println(obj);

    }

	public static GenSub createDefault(){

		GenSub ret = new GenSub();		
//		ret.setMainId("");			
//		ret.setStrKey("");			
//		ret.setSubData("");			
//		ret.setTimeCreated("");			
//		ret.setTimeUpdated("");			
		return ret;
	}
}
