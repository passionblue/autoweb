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
import com.autosite.db.BlockList;
import com.jtrend.service.DomainStore;

import com.autosite.db.BlockListDAO;

public class BlockListDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(BlockListDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static BlockListDS m_BlockListDS = new BlockListDSExtent();



    public static BlockListDS getInstance() {
        return m_BlockListDS;
    }

    public static synchronized BlockListDS getInstance(long id) {
        BlockListDS ret = (BlockListDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new BlockListDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_BlockListDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((BlockListDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("BlockList loaded from DB. num=" + m_idToMap.size());
        
    }


    protected BlockListDS() {
        m_dao = new BlockListDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected BlockListDS(long id) {
        m_dao = new BlockListDAO();
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

    public BlockList getById(Long id) {
        return (BlockList) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        BlockList o = (BlockList)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("BlockList removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("BlockList added to DS " + o.getId());
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
        BlockList o = (BlockList)obj;

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
            if (m_debug) m_logger.debug("BlockList removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("BlockList added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    













    public static void main(String[] args) throws Exception {

        BlockListDS ds = new BlockListDS();
        BlockList obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static BlockList createDefault(){

        BlockList _BlockList = new BlockList();        
	    _BlockList = new BlockList();// BlockListDS.getInstance().getDeafult();
        return _BlockList;
    }

    public static BlockList copy(BlockList org){

    	BlockList ret = new BlockList();

		ret.setIpData(org.getIpData());
		ret.setRangeCheck(org.getRangeCheck());
		ret.setReasonCode(org.getReasonCode());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(BlockList blockList, Logger logger){
		logger.debug("BlockList [" + blockList.getId() + "]" + objectToString(blockList));		
    }

	public static String objectToString(BlockList blockList){
		StringBuffer buf = new StringBuffer();
        buf.append("BlockList=");
		buf.append("Id=").append(blockList.getId()).append(", ");
		return buf.toString();    
    }

	// Empty methods for 
    public boolean getData(String arg, int arg2) {
		return true;
	}
    public void getHyunjoo() {
	}

}
