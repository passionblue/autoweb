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
import com.jtrend.service.DomainStore;
import com.autosite.holder.DataHolderObject;


import com.autosite.db.ChurPayeeDAO;
import com.autosite.db.ChurPayee;
import com.surveygen.db.BaseMemoryDAO;

public class ChurPayeeDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(ChurPayeeDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static ChurPayeeDS m_ChurPayeeDS = new ChurPayeeDSExtent();



    public static ChurPayeeDS getInstance() {
        return m_ChurPayeeDS;
    }

    public static synchronized ChurPayeeDS getInstance(long id) {
        ChurPayeeDS ret = (ChurPayeeDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ChurPayeeDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ChurPayeeDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ChurPayeeDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ChurPayee loaded from DB. num=" + m_idToMap.size());
        
    }


    protected ChurPayeeDS() {
        m_dao = new ChurPayeeDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ChurPayeeDS(long id) {
        m_dao = new ChurPayeeDAO();
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

    public ChurPayee getById(Long id) {
        return (ChurPayee) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        ChurPayee o = (ChurPayee)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ChurPayee removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ChurPayee added to DS " + o.getId());
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
        ChurPayee o = (ChurPayee)obj;

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
            if (m_debug) m_logger.debug("ChurPayee removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ChurPayee added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    












    public static void main(String[] args) throws Exception {

        ChurPayeeDS ds = new ChurPayeeDS();
        ChurPayee obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static ChurPayee createDefault(){

        ChurPayee _ChurPayee = new ChurPayee();        
	    _ChurPayee = new ChurPayee();// ChurPayeeDS.getInstance().getDeafult();
        return _ChurPayee;
    }

    public static ChurPayee copy(ChurPayee org){

    	ChurPayee ret = new ChurPayee();

		ret.setTitle(org.getTitle());
		ret.setRemark(org.getRemark());

        return ret;
    }

	public static void objectToLog(ChurPayee churPayee, Logger logger){
		logger.debug("ChurPayee [" + churPayee.getId() + "]" + objectToString(churPayee));		
    }


	public static String objectToString(ChurPayee churPayee){
		StringBuilder buf = new StringBuilder();
        buf.append("ChurPayee=");
		buf.append("Id=").append(churPayee.getId()).append(", ");
		buf.append("SiteId=").append(churPayee.getSiteId()).append(", ");
		return buf.toString();    
    }





	// Empty methods for 

}
