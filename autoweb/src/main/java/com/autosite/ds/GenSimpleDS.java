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

import com.autosite.holder.GenSimpleDataHolder;

import com.autosite.db.GenSimpleDAO;
import com.autosite.db.GenSimple;
import com.surveygen.db.BaseMemoryDAO;

public class GenSimpleDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(GenSimpleDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static GenSimpleDS m_GenSimpleDS = new GenSimpleDSExtent();



    public static GenSimpleDS getInstance() {
        return m_GenSimpleDS;
    }

    public static synchronized GenSimpleDS getInstance(long id) {
        GenSimpleDS ret = (GenSimpleDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new GenSimpleDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_GenSimpleDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((GenSimpleDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("GenSimpleDataHolder loaded from DB. num=" + m_idToMap.size());
        
    }


    protected GenSimpleDS() {
        m_dao = new GenSimpleDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected GenSimpleDS(long id) {
        m_dao = new GenSimpleDAO();
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

    public GenSimpleDataHolder getById(Long id) {
        return (GenSimpleDataHolder) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(wrapByHolder(obj), del);
    }
    public void updateMaps(Object obj, boolean del) {
        GenSimpleDataHolder o = (GenSimpleDataHolder)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("GenSimpleDataHolder removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("GenSimpleDataHolder added to DS " + o.getId());
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
        GenSimpleDataHolder o = (GenSimpleDataHolder)obj;

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
            if (m_debug) m_logger.debug("GenSimpleDataHolder removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("GenSimpleDataHolder added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        GenSimpleDS ds = new GenSimpleDS();
        GenSimpleDataHolder obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static GenSimple createDefault(){

        GenSimple _GenSimple = new GenSimple();        
	    _GenSimple = new GenSimple();// GenSimpleDataHolderDS.getInstance().getDeafult();
        return _GenSimple;
    }

    public static GenSimple copy(GenSimple org){

    	GenSimple ret = new GenSimple();

		ret.setData(org.getData());
		ret.setActive(org.getActive());

        return ret;
    }

	public static void objectToLog(GenSimple genSimple, Logger logger){
		logger.debug("GenSimple [" + genSimple.getId() + "]" + objectToString(genSimple));		
    }

	public static String objectToString(GenSimple genSimple){
		StringBuilder buf = new StringBuilder();
        buf.append("GenSimple=");
		buf.append("Id=").append(genSimple.getId()).append(", ");
		buf.append("SiteId=").append(genSimple.getSiteId()).append(", ");
		return buf.toString();    
    }

    public boolean usingHolderObject() {
        return true;
    }

    public DataHolderObject wrapByHolder(Object obj){
        return new GenSimpleDataHolder ( (GenSimple) obj);
    }


    public static void objectToLog(GenSimpleDataHolder genSimple, Logger logger){
    }


	public static String objectToString(GenSimpleDataHolder genSimple){
		StringBuilder buf = new StringBuilder();
        buf.append("GenSimple=");
		buf.append("Id=").append(genSimple.getId()).append(", ");
		buf.append("SiteId=").append(genSimple.getSiteId()).append(", ");
		return buf.toString();    
	}





	public String objectToString2(AutositeDataObject object){
		GenSimpleDataHolder genSimple = (GenSimpleDataHolder)object;
		StringBuilder buf = new StringBuilder();
        buf.append("GenSimple=");
		buf.append("Id=").append(genSimple.getId()).append(", ");
		buf.append("SiteId=").append(genSimple.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 
    public boolean getData(String arg, int arg2) {
		return true;
	}
    public void getTimestamp() {
	}

}
