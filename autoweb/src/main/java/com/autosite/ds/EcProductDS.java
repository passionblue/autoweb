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
import com.autosite.db.EcProduct;
import com.autosite.db.EcProductDAO;
import com.jtrend.service.DomainStore;

public class EcProductDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_CategoryIdToMap;

	protected Map m_SiteIdCategoryIdToMap;

    private static Logger m_logger = Logger.getLogger(EcProductDS.class);
    private static EcProductDS m_EcProductDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized EcProductDS getInstance() {
        if (m_EcProductDS == null) {
            m_EcProductDS = new EcProductDS();
        }
        return m_EcProductDS;
    }

    public static synchronized EcProductDS getInstance(long id) {
        EcProductDS ret = (EcProductDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcProductDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcProductDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcProductDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcProductDS() {
        m_dao = new EcProductDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_CategoryIdToMap = new ConcurrentHashMap();
		m_SiteIdCategoryIdToMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcProductDS(long id) {
        m_loadById = id;
        m_dao = new EcProductDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_CategoryIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcProduct getById(Long id) {
        return (EcProduct) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcProduct o = (EcProduct)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcProduct removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcProduct added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateCategoryIdMap(obj, del);

		updateSiteIdCategoryIdMap(obj, del);

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
        EcProduct o = (EcProduct)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcProduct removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcProduct added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }

    public List getByCategoryId(long CategoryId) {
        
        Long _CategoryId  = new Long(CategoryId);
        if (m_CategoryIdToMap.containsKey(_CategoryId)) {
            return new ArrayList( ((Map)m_CategoryIdToMap.get(_CategoryId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateCategoryIdMap(Object obj, boolean del) {
        EcProduct o = (EcProduct)obj;

        if (del) {

            // delete from CategoryIdToMap
            Map map  = (Map) m_CategoryIdToMap.get(new Long(o.getCategoryId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcProduct removed from CategoryIdToMap" + o.getId() + " from " + o.getCategoryId());
        }
        else {
            
            // add to CategoryIdToMap
            Map map  = (Map) m_CategoryIdToMap.get(new Long(o.getCategoryId()));
            if ( map == null ) {
                map = new TreeMap();
                m_CategoryIdToMap.put(new Long(o.getCategoryId()), map);
                if (m_debug) m_logger.debug("created new   CategoryIdToMap for " + o.getCategoryId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("EcProduct added to CategoryIdToMap " + o.getId() + " to " + o.getCategoryId());
        }
    }


    


        
    public List getBySiteIdCategoryId(long SiteId, long CategoryId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdCategoryIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdCategoryIdToMap.get(keySiteId);

     	    Long keyCategoryId = new Long(CategoryId);
            
            if ( mapSiteId.containsKey(keyCategoryId)){
                return new ArrayList( ((Map)mapSiteId.get(keyCategoryId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    private void updateSiteIdCategoryIdMap(Object obj, boolean del) {
        EcProduct o = (EcProduct)obj;

     	    Long keyCategoryId = new Long(o.getCategoryId());

        if (del) {
            // delete from SiteIdCategoryIdToMap
            Map mapSiteId  = (Map) m_SiteIdCategoryIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapCategoryId = (Map) mapSiteId.get(keyCategoryId);
                if (mapCategoryId != null){
                    mapCategoryId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed from m_SiteIdCategoryIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getCategoryId());
        }
        else {
            
            // add to SiteIdCategoryIdToMap
            Map mapSiteId  = (Map) m_SiteIdCategoryIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdCategoryIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapCategoryId = (Map) mapSiteId.get(keyCategoryId);
            
            if ( mapCategoryId == null) {
                mapCategoryId = new TreeMap();
                mapSiteId.put(keyCategoryId, mapCategoryId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapCategoryId.put(new Long(o.getId()), o);            
            
            m_logger.debug("Panel added to SiteIdCategoryIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        



    public static void main(String[] args) throws Exception {

        EcProductDS ds = new EcProductDS();
        EcProduct obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcProduct createDefault(){

        EcProduct ret = new EcProduct();        
//      ret.setCategoryId("");           
//      ret.setFactorySku("");           
//      ret.setSiteSku("");           
//      ret.setAltSku("");           
//      ret.setItemCode("");           
//      ret.setMaker("");           
//      ret.setBrand("");           
//      ret.setName("");           
//      ret.setSubName("");           
//      ret.setShortDescription("");           
//      ret.setDescription("");           
//      ret.setDescription2("");           
//      ret.setImageUrl("");           
//      ret.setColor("");           
//      ret.setSize("");           
//      ret.setMsrp("");           
//      ret.setMsrpCurrency("");           
//      ret.setSalePrice("");           
//      ret.setSaleEnds("");           
//      ret.setSoldout("");           
//      ret.setHide("");           
//      ret.setPromoNote("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static EcProduct copy(EcProduct org){

    	EcProduct ret = new EcProduct();

		ret.setCategoryId(org.getCategoryId());
		ret.setFactorySku(org.getFactorySku());
		ret.setSiteSku(org.getSiteSku());
		ret.setAltSku(org.getAltSku());
		ret.setItemCode(org.getItemCode());
		ret.setMaker(org.getMaker());
		ret.setBrand(org.getBrand());
		ret.setName(org.getName());
		ret.setSubName(org.getSubName());
		ret.setShortDescription(org.getShortDescription());
		ret.setDescription(org.getDescription());
		ret.setDescription2(org.getDescription2());
		ret.setImageUrl(org.getImageUrl());
		ret.setColor(org.getColor());
		ret.setSize(org.getSize());
		ret.setMsrp(org.getMsrp());
		ret.setMsrpCurrency(org.getMsrpCurrency());
		ret.setSalePrice(org.getSalePrice());
		ret.setSaleEnds(org.getSaleEnds());
		ret.setSoldout(org.getSoldout());
		ret.setHide(org.getHide());
		ret.setPromoNote(org.getPromoNote());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(EcProduct ecProduct, Logger logger){
		logger.debug("EcProduct [" + ecProduct.getId() + "]" + objectToString(ecProduct));		
    }

	public static String objectToString(EcProduct ecProduct){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(ecProduct.getId()).append(", ");
		buf.append("SiteId=").append(ecProduct.getSiteId()).append(", ");
		buf.append("Maker=").append(ecProduct.getMaker()).append(", ");
		buf.append("Name=").append(ecProduct.getName()).append(", ");
		buf.append("SiteSku=").append(ecProduct.getSiteSku()).append(", ");
		buf.append("Msrp=").append(ecProduct.getMsrp()).append(", ");
		return buf.toString();    
    }
}
