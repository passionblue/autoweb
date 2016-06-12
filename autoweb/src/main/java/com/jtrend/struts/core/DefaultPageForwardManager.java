package com.jtrend.struts.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.log4j.Logger;

import com.jtrend.util.FileUtil;
import com.seox.util.PropertiesLoader;

public class DefaultPageForwardManager {

    Map<String , Map> m_pageMap = new ConcurrentHashMap<String , Map>();
    Map<String , Set> m_pageToForwardToMap = new ConcurrentHashMap<String , Set>();
    
    Map<String , PageForwardConfig> m_pageCofnig2Map = new ConcurrentHashMap<String , PageForwardConfig>();

    private String serverId;
    
    private static Logger m_logger = Logger.getLogger(DefaultPageForwardManager.class);
    private static DefaultPageForwardManager m_instance = new DefaultPageForwardManager();

    private static Map<String , DefaultPageForwardManager> m_siteInstanceMap = new ConcurrentHashMap<String , DefaultPageForwardManager>();
    
    public static DefaultPageForwardManager getInstance() {
        return m_instance;
    }

    // Not synchronized becuase it is okay. 
    public static DefaultPageForwardManager getInstance(String serverId) {

        m_logger.info("Created instance for serverId = " + serverId);
        if ( serverId == null) return m_instance;

        serverId = serverId.replaceAll("\\.", "_");
        
        if ( !m_siteInstanceMap.containsKey(serverId) ) {
            DefaultPageForwardManager newInst = new DefaultPageForwardManager(serverId, m_instance);
            m_siteInstanceMap.put(serverId, newInst);
        }
            
        return m_siteInstanceMap.get(serverId);
    }

    private DefaultPageForwardManager(String serverId, DefaultPageForwardManager basicInstance) {
        this.serverId = serverId;
        this.m_pageCofnig2Map = basicInstance.m_pageCofnig2Map;
        this.m_pageToForwardToMap = basicInstance.m_pageToForwardToMap;
        this.m_pageMap = basicInstance.m_pageMap;
    }    
    
    private DefaultPageForwardManager() {

        String file = PropertiesLoader.getInstance().getProperty("app.cfg.pageforward");
        BufferedReader in = FileUtil.getBufferedReader(file);
        
        try {
            while (true) {

                String line = in.readLine();

                if (line == null)
                    break;

                if (line.trim().startsWith("#")) {
                    continue;
                }

                int pos = line.indexOf("=");
                if (pos < 0) {
                    m_logger.warn("Line corrupted = " + line);
                    continue;
                }

                
                String key = line.substring(0, pos);
                String pageTo = line.substring(pos + 1);

                int posToDot = key.indexOf(".");
                if (posToDot < 0) {
                    m_logger.warn("Line corrupted = " + line);
                    continue;
                }

                // version 2 configs check the configs to see 
                //#Action.cmd.success|error|all.url(.urlForward)
                //#Action.cmd.success|error|all.alias(.urlForward)
                //2.DevNote.dd.success.alias=@dev_note_home   @ means forward to

                if ( key.startsWith("2.")) {
                    
                    String parts[] = key.split("\\.");

//                    m_logger.debug("length " + parts.length);
                    
                    boolean hasServer = false;
                    
                    if ( parts.length == 5 ) {
                    } else if ( parts.length == 6) {
                        hasServer = true;
                    } else {
                        m_logger.debug("Key part length not valid " + parts.length + " should be 5 or 6");
                        continue;
                    }

                    int partsIdx = 0;
                    
                    String prefix       = parts[partsIdx++];
                    String serverId      = hasServer? parts[partsIdx++].trim().toUpperCase(): null; // if not specified, use wild card to be matched by it
                    String ver2Action   = parts[partsIdx++];
                    String ver2Cmd      = parts[partsIdx++]; // add,delete or any
                    String ver2scope    = parts[partsIdx++]; //success error all
                    boolean ver2IsAlias = parts[partsIdx++].equalsIgnoreCase("alias"); // url or alias
                    boolean isInternalForward = pageTo.startsWith("@");
                    String pageToName = pageTo.startsWith("@")? pageTo.substring(1): pageTo;
                    
                    PageForwardConfig config = new PageForwardConfig();
                    
                    config.serverId = serverId;
                    config.action = ver2Action;
                    config.cmd = ver2Cmd;
                    config.scope = ver2scope;
                    config.isAlias = ver2IsAlias;
                    config.isInternalForward = isInternalForward;
                    config.page = pageToName;
                    
                    m_logger.debug("Config forward read : " + config );
                    
                    //In case serverId is specified, use it in the key
                    if ( hasServer) 
                        m_pageCofnig2Map.put(serverId + "." + ver2Action + "." + ver2Cmd + "." + ver2scope, config);
                    else
                        m_pageCofnig2Map.put(ver2Action + "." + ver2Cmd + "." + ver2scope, config);
                    
                    continue;
                }
                
                
                String action = key.substring(0, posToDot);
                String pageKey = key.substring(posToDot + 1);
                
                //=====================================================================================
                // FORWARDTO list processing
                // all listed hard-coded pages will be internally forwarded.
                
                if ( pageKey != null && pageKey.equals("_FORWARD_TO")&& pageTo != null) {
                    m_logger.info("_FORWARD_TO for " + action + " : " + pageTo);
                    String forwardPages [] = pageTo.split(",");
                    
                    Set pagesSet = new ConcurrentSkipListSet<String>(Arrays.asList(forwardPages));
                    m_pageToForwardToMap.put(action, pagesSet);
                    m_logger.info("_FORWARD_TO for " + action + " : " + pagesSet);
                    continue;
                }
                
                
                Map subMap = m_pageMap.get(action);
                
                if (subMap == null) {
                    subMap = new ConcurrentHashMap<String, String>();
                    m_pageMap.put(action, subMap);
                    m_logger.info("ForwardPageTO " + action + "." + pageKey + "  -> " + pageTo);
                }

                subMap.put(pageKey, pageTo);
            }
        }
        catch (IOException e) {
            m_logger.error(e);
        }
    }

    // True if page is setPageForwardTo instaed of setPage, which means internally re-directed
    // 
    public boolean isPageForwardTo(String actionKey, String pageKey) {
        if (actionKey == null || pageKey == null) return false;
        if (!m_pageToForwardToMap.containsKey(actionKey)) return false;
        
        boolean result =  m_pageToForwardToMap.get(actionKey).contains(pageKey.trim());
        m_logger.debug("FORWARD_TO page result: " + result + "for Action: " + actionKey + " pageKey: " + pageKey);

        return result;
    }
    
    //
    // This returns page name from the configs of version 1 
    // config would have the following configs
    // CleanerPickup.action_normal_add_forward_page=cleaner_pickup_thank
    // param will be "CleanerPickup" and "action_normal_add_forward_page" then will return "cleaner_pickup_thank"
    //
    
    public String getPageForwardTo(String actionKey, String pageKey, String defaultPageTo) {
        
        if (actionKey == null || pageKey == null) return defaultPageTo;
        if (!m_pageMap.containsKey(actionKey))return defaultPageTo;
        
        Map<String,String> subMap = m_pageMap.get(actionKey);
        
        if (subMap.containsKey(pageKey)) {
            m_logger.debug("return FOUND page for " +actionKey+"." + pageKey + " -> " + subMap.get(pageKey) );
            return subMap.get(pageKey);
        }
        else {
            m_logger.debug("2page for " +actionKey+"." + pageKey + " -> " + defaultPageTo );
            return defaultPageTo;
        }
    }
    
    // ====================================================================================================
    // ====================================================================================================
    // ====================================================================================================
    // ====================================================================================================

    
    
    public String getPageForwardToByCommand(String action, String cmd, String scope, String defaultPage) {
        return getPageForwardToByCommand(serverId, action, cmd, scope, defaultPage);
    }    
    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getPageForwardToByCommand(String serverId, String action, String cmd, String scope, String defaultPage) {
        
        PageForwardConfig c = getPageForwardConfig(serverId, action, cmd, scope);
        m_logger.debug("getPageForwardTo2 " + serverId +"/" + action+"/" + cmd + "/scope ==> " + c + " default=" + defaultPage);
        if (c != null) {
            
            if (c.page == null || c.page.equalsIgnoreCase("*")) {
                m_logger.debug("getPageForwardTo2 sets passed defaultPage " + defaultPage);
                return defaultPage;
            }
            return c.page;
        }
        
        return defaultPage;
    }

    // 
    public boolean isInternalForward(String action, String cmd, String scope) {
        return isInternalForward(serverId, action, cmd, scope);
    }
    public boolean isInternalForward(String serverId, String action, String cmd, String scope) {
        PageForwardConfig c = getPageForwardConfig(serverId, action, cmd, scope);
        m_logger.debug("isInternalForward " + serverId +"/"  + action+"/" + cmd + "/scope ==> " + c);
        if (c != null) return c.isInternalForward;
        
        return false;
    }
    
    // true if the return page is alias not URL
    public boolean isPageAlias(String action, String cmd, String scope) {
        return isPageAlias(serverId, action, cmd, scope);
    }
    public boolean isPageAlias(String serverId, String action, String cmd, String scope) {
        PageForwardConfig c = getPageForwardConfig(serverId, action, cmd, scope);
        m_logger.debug("isInternalForward " + serverId +"/"  + action+"/" + cmd + "/scope ==> " + c);
        if (c != null) return c.isAlias;
        
        return true;
    }
    public boolean isPageUrl(String action, String cmd, String scope) {
        return isPageUrl(serverId, action, cmd, scope);
    }
    public boolean isPageUrl(String serverId, String action, String cmd, String scope) {
        PageForwardConfig c = getPageForwardConfig(serverId, action, cmd, scope);
        m_logger.debug("isInternalForward " + serverId +"/"  + action+"/" + cmd + "/scope ==> " + c);
        
        if (c != null) return !c.isAlias;
        
        return false;
    }
    private PageForwardConfig getPageForwardConfig ( String action, String cmd, String scope ) {
        return getPageForwardConfig(serverId, action, cmd, scope);
    }    
    private PageForwardConfig getPageForwardConfig ( String serverId, String action, String cmd, String scope ) {

        if (action == null || cmd == null || scope == null ) return null;

        if (serverId != null) {
            serverId = formatToKey(serverId);
            if ( m_pageCofnig2Map.containsKey(serverId + "." + action+"."+ cmd + "." + scope)) {
                return m_pageCofnig2Map.get(serverId + "." + action+"."+ cmd + "." + scope);
            }
    
            if ( m_pageCofnig2Map.containsKey(serverId + "." + action+"."+ cmd + "." + "*")) {
                return m_pageCofnig2Map.get(serverId + "." + action+"."+ cmd + "." + "*");
            }
    
            if ( m_pageCofnig2Map.containsKey(serverId + "." + action+"."+ "*" + "." + scope)) {
                return m_pageCofnig2Map.get(serverId + "." + action+"."+ "*" + "." + scope);
            }
    
            if ( m_pageCofnig2Map.containsKey(serverId + "." + action+"."+ "*" + "." +  "*")) {
                return m_pageCofnig2Map.get((serverId + "." + action+"."+ "*" + "." +  "*"));
            }
            
            if ( m_pageCofnig2Map.containsKey(serverId + "." + "*"+"."+ cmd + "." + scope)) {
                return m_pageCofnig2Map.get(serverId + "." + "*"+"."+ cmd + "." + scope);
            }
    
            if ( m_pageCofnig2Map.containsKey(serverId + "." + "*"+"."+ cmd + "." + "*")) {
                return m_pageCofnig2Map.get(serverId + "." + "*"+"."+ cmd + "." + "*");
            }
    
            if ( m_pageCofnig2Map.containsKey(serverId + "." + "*"+"."+ "*" + "." + scope)) {
                return m_pageCofnig2Map.get(serverId + "." + "*"+"."+ "*" + "." + scope);
            }
        } 
        
        // Whether serverId is specified or not, this generic logic has to be checked if serverId not found. 
        if ( m_pageCofnig2Map.containsKey(action+"."+ cmd + "." + scope)) {
            return m_pageCofnig2Map.get(action+"."+ cmd + "." + scope);
        }

        if ( m_pageCofnig2Map.containsKey(action+"."+ cmd + "." + "*")) {
            return m_pageCofnig2Map.get(action+"."+ cmd + "." + "*");
        }

        if ( m_pageCofnig2Map.containsKey(action+"."+ "*" + "." + scope)) {
            return m_pageCofnig2Map.get((action+"."+ "*" + "." + scope));
        }

        if ( m_pageCofnig2Map.containsKey(action+"."+ "*" + "." +  "*")) {
            return m_pageCofnig2Map.get((action+"."+ "*" + "." +  "*"));
        }
        
        if ( m_pageCofnig2Map.containsKey("*"+"."+ cmd + "." + scope)) {
            return m_pageCofnig2Map.get("*"+"."+ cmd + "." + scope);
        }

        if ( m_pageCofnig2Map.containsKey("*"+"."+ cmd + "." + "*")) {
            return m_pageCofnig2Map.get("*"+"."+ cmd + "." + "*");
        }

        if ( m_pageCofnig2Map.containsKey("*"+"."+ "*" + "." + scope)) {
            return m_pageCofnig2Map.get("*"+"."+ "*" + "." + scope);
        }
        return null;
    }
    
    private String formatToKey(String siteId){
        if ( siteId != null) {
            return siteId.toUpperCase().replaceAll("\\.", "_");
        }
        return null;
    }
    
    public static void main(String[] args) {
        
        System.out.println(DefaultPageForwardManager.getInstance().isPageForwardTo("ChurMember", "action_normal_edit_forward_page"));

        System.out.println(DefaultPageForwardManager.getInstance().isPageForwardTo("ChurMember", "action_normal_delete_forward_page"));
        
        System.out.println(DefaultPageForwardManager.getInstance().getPageForwardToByCommand("DevNote","dd", "success", "holy"));
        
        System.out.println(DefaultPageForwardManager.getInstance().isInternalForward("DevOthers","delete", "success"));

        System.out.println(DefaultPageForwardManager.getInstance().isInternalForward("DevOthers","add", "success"));
        
        System.out.println(DefaultPageForwardManager.getInstance().isInternalForward("DevNote","add", "success"));

        System.out.println(DefaultPageForwardManager.getInstance().isInternalForward("DevNote","dd", "success"));

        System.out.println(DefaultPageForwardManager.getInstance().isInternalForward("DevNote","dd", "error"));
        
        System.out.println(DefaultPageForwardManager.getInstance().isInternalForward("DevNote","delete", "success"));

        
        System.out.println(DefaultPageForwardManager.getInstance().getPageForwardToByCommand("localhost", "RegisterSimple","add", "success", "holy"));
        System.out.println(DefaultPageForwardManager.getInstance().getPageForwardToByCommand("RegisterSimple","add", "success", "holy"));
        

        System.out.println(DefaultPageForwardManager.getInstance().getPageForwardToByCommand("localhost", "RegisterSimple","add", "success", "holy"));
        System.out.println(DefaultPageForwardManager.getInstance().getPageForwardToByCommand("chur-accounting.com", "RegisterSimple","add", "success", "holy"));
        
        System.out.println("1.1".replaceAll("\\.", "_"));
        
    }
    
    
    public static class PageForwardConfig {

        String serverId;
        String action;
        String cmd;
        String scope; // success error all
        boolean isAlias = true; // true url alias
        boolean isInternalForward = false;
        
        String page;
        
        public String toString() {
            return ">>>>> [site:" + serverId + "]."+ action + "." + cmd + " (" + scope + ") alias : " + isAlias + " internalForward: " + isInternalForward + ">>>" + page;
        }
    }
}
