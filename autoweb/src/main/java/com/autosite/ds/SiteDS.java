package com.autosite.ds;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.db.SiteDAO;
import com.jtrend.service.DomainStore;
import com.jtrend.util.WebUtil;

public class SiteDS extends AbstractDS implements DomainStore {

    protected Map m_urlToMap;
    protected Map m_siteRawStringCache;
    protected Map m_BaseSiteIdToMap;

    private static Logger m_logger = Logger.getLogger(SiteDS.class);
    private static SiteDS m_SiteDS = null;

    
    public static synchronized SiteDS getInstance() {
        if (m_SiteDS == null) {
            m_SiteDS = new SiteDS();
        }
        return m_SiteDS;
    }

    protected SiteDS() {
        m_dao = new SiteDAO();
        m_idToMap = new ConcurrentHashMap();
        m_urlToMap = new ConcurrentHashMap();
        m_siteRawStringCache = new ConcurrentHashMap();
        m_BaseSiteIdToMap = new ConcurrentHashMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e);
        }
    }
    
    public synchronized void updateMaps(Object obj, boolean del) {
        Site site = (Site)obj;
        
        if (del) {
            m_idToMap.remove(new Long(site.getId()));
            m_urlToMap.remove(site.getSiteUrl());
            m_logger.debug("Site removed from DS " + site.getId() + ",url=" + site.getSiteUrl());

        }
        else {
            m_idToMap.put(new Long(site.getId()), site);
            m_urlToMap.put(site.getSiteUrl(), site);
            m_logger.debug("Site added to DS " + site.getId() + ",url=" + site.getSiteUrl());
        }
        
        updateBaseSiteIdMap(obj, del);
    }

    //    //    //
    // ListKeys - BaseSiteId
    public List getByBaseSiteId(long BaseSiteId) {
        
        Long _BaseSiteId  = new Long(BaseSiteId);
        if (m_BaseSiteIdToMap.containsKey(_BaseSiteId)) {
            return new ArrayList( ((Map)m_BaseSiteIdToMap.get(_BaseSiteId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateBaseSiteIdMap(Object obj, boolean del) {
        Site o = (Site)obj;

        if ( o.getBaseSiteId() == 0 ){
            m_logger.debug("key id is 0. not putting in the map"); 
            return;
        }

        if (del) {

            // delete from BaseSiteIdToMap
            Map map  = (Map) m_BaseSiteIdToMap.get(new Long(o.getBaseSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("Site removed from BaseSiteIdToMap" + o.getId() + " from " + o.getBaseSiteId());
        }
        else {
            
            // add to BaseSiteIdToMap
            Map map  = (Map) m_BaseSiteIdToMap.get(new Long(o.getBaseSiteId()));
            if ( map == null ) {
                map = new TreeMap();
                m_BaseSiteIdToMap.put(new Long(o.getBaseSiteId()), map);
                m_logger.debug("created new   BaseSiteIdToMap for " + o.getBaseSiteId());

            }
            map.put(new Long(o.getId()), o);            
            m_logger.debug("Site added to BaseSiteIdToMap " + o.getId() + " to " + o.getBaseSiteId());
        }
    }
    
    public Site getById(long siteId) {
        return (Site)getById(""+ siteId);
    }
    public Site getSiteByUrl(String url) {
        if (url == null) return null;
        url = getCommonUrl(url);
        return (Site) m_urlToMap.get(url);
    }

    // Add new site if no site found in DB/Mem
    public Site registerSite(String url) {
        return registerSite(url, true);
    }
    
    // Add new site if no site found in DB/Mem
    public Site registerSite(String url, boolean returnContentFowardSite) {
        return registerSite(url, returnContentFowardSite, false);
    }

    public Site getContentForwardSite(String url) {
        return this.getContentForwardSite(getSiteByUrl(url));
    }    
    
    public Site getContentForwardSite(Site site) {
        
        if ( site == null) return null;
        
        SiteConfig config = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
        
        if ( config != null) {

            String contentForwardUrl = getCommonUrl(config.getContentForwardSite());  
            m_logger.debug("Content forward site config set to " + contentForwardUrl);
    
            Site retContentFoward = (Site) getSiteByUrl(contentForwardUrl);
            if ( retContentFoward != null) {
                
                if ( retContentFoward.getId() != site.getId()){
                    return retContentFoward;
                }
            } 
        }
        
        return null;
    }
    
    public Site registerSite(String url, boolean returnContentFowardSite, boolean throwExceptionIfBaseDontAllowSub) {
        if (url == null ) return null;
        
        //Find the cache
        //But this doesnwork if siteconfig changes........... DAMN..
        //1/16/2013 so disable for now. This one has to be optimized. 
        //1/17 some work done
        if ( m_siteRawStringCache.containsKey(url) && returnContentFowardSite ) {

            Site cached  = (Site) m_siteRawStringCache.get(url);

            //Check content forward site, because this should be checked everytime. 
            SiteConfig config = SiteConfigDS.getInstance().getSiteConfigBySiteId(cached.getId());
            if ( config != null) {

                String contentForwardUrl = getCommonUrl(config.getContentForwardSite());  
                m_logger.debug("Content forward site config set to " + contentForwardUrl);
        
                Site retContentFoward = (Site) getSiteByUrl(contentForwardUrl);
                if ( retContentFoward != null) {
                    m_logger.info("Site object found for the content foward site. " + url +" <=== " + retContentFoward.getSiteUrl() + "/" + retContentFoward.getId());
                    
                    if ( retContentFoward.getId() != cached.getId()){
                        m_siteRawStringCache.put(url, retContentFoward);
                        return retContentFoward;
                    }
                    
                } else {
                    //m_logger.error("***ContentForward site not found by config " + contentForwardUrl);
                }
            }
            
            return cached;
        }
        
        String formmatted = getCommonUrl(url);
        
        Site ret = (Site) getSiteByUrl(formmatted);
        
        if (ret == null) {
            
            // If this site was not found, check first if this is basedomain. If so, create new one. 
            // User will see the site blocked, because subdomain only activated manually. 
            
            String baseDomain = getBaseDomain(formmatted);
            
            ret = (Site) getSiteByUrl(baseDomain);
            if ( ret != null) {
                if (WebUtil.isTrue(ret.getSubdomainEnable())) {
                    
                    //create a sub site and return this
                    Site subsite = new Site();
                    subsite.setSiteUrl(formmatted);
                    subsite.setCreatedTime(new Timestamp(System.currentTimeMillis()));
                    subsite.setSubsite(1);
                    subsite.setRegistered(1);
                    subsite.setSiteRegisterSite(ret.getSiteUrl());
                    subsite.setBaseSiteId(ret.getId());
                    put(subsite);
                    
                    ret = subsite;
                    m_logger.info("This is SUBDOMAIN of existing domain. Created but need to be activated " + formmatted);
                } else {
                    if (throwExceptionIfBaseDontAllowSub) 
                        throw new IllegalArgumentException("Site " + ret.getSiteUrl() + " doesn't allow sub-domain sites");
                }
                
            } else {
                ret = new Site();
                ret.setSiteUrl(baseDomain);
                ret.setCreatedTime(new Timestamp(System.currentTimeMillis()));
                put(ret);
                m_logger.info("This is base domain of requested. Only base domain was created " + baseDomain + " whole " + formmatted);
            }
        
        } else {
            // Domain found. Should be okay as is. 
        }
        
        SiteConfig config = SiteConfigDS.getInstance().getSiteConfigBySiteId(ret.getId());
        
        if (!returnContentFowardSite || config == null || config.getContentForwardSite() == null || config.getContentForwardSite().equals("")) {
            // Nothing
        } else {

            String contentForwardUrl = getCommonUrl(config.getContentForwardSite());  
            m_logger.info("Content forward site config set to " + contentForwardUrl);
    
            Site retContentFoward = (Site) getSiteByUrl(contentForwardUrl);
            if ( retContentFoward != null) {
                ret = retContentFoward;
                m_logger.info("Site object found for the content foward site. " + formmatted +" <=== " + retContentFoward.getSiteUrl() + "/" + retContentFoward.getId());
            }
        }

        if ( returnContentFowardSite)
            m_siteRawStringCache.put(url, ret);
        return ret;
    }

    // Add new site if no site found in DB/Mem
    public Site findSite(String url) {
        if (url == null ) return null;
        
        url = getCommonUrl(url);
        
        Site ret = (Site) getSiteByUrl(url);
        
        return ret;
    }
    
    
    public List getAllSite() {
        return new ArrayList(m_idToMap.values());
    }
    
    
    public static String getCommonUrl(String url) {
        
        if (url == null || url.trim().isEmpty()) return null;

        String retUrl = url.toLowerCase();
        
        //remove default sub or protocol
        if (retUrl.indexOf("www.") > -1 ) {
            retUrl =  retUrl.substring("www.".length()).toLowerCase();
        } else if (retUrl.indexOf("http://www.") > -1 ){
            retUrl =  retUrl.substring("http://www.".length()).toLowerCase();
        }
        
        retUrl = StringUtils.stripEnd(retUrl, ".");
        
//        if ( retUrl.charAt(retUrl.length()-1) == '.') {
//            retUrl = retUrl.substring(0, retUrl.length()-1);
//        }
        
        return retUrl; 
    }

    public static String getBaseDomain(String urlString) {
        
        if (urlString == null) return null;
        
        int lastIdx = urlString.lastIndexOf(".");
        if (lastIdx <0 ) return null;
        lastIdx = urlString.substring(0, lastIdx).lastIndexOf(".");
        if (lastIdx <0) return urlString;
        
        return urlString.substring(lastIdx+1);
    }
    
    public static String objectToString(Site site){
        StringBuffer buf = new StringBuffer();
        buf.append("Site=");
        buf.append("Id=").append(site.getId()).append(", ");
        buf.append("SiteUrl=").append(site.getSiteUrl()).append(", ");
        return buf.toString();    
    }
    
    public static void main(String[] args) throws Exception {

        String org = "www.xx.com";
        System.out.println(getBaseDomain(org));
        org = "xx.com";
        System.out.println(getBaseDomain(org));
    }
    
}
