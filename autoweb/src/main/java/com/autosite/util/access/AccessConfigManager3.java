package com.autosite.util.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.ds.ChurIncomeDS;
import com.autosite.util.access.AccessDef.ActionType;
import com.autosite.util.access.AccessDef.SystemRole;
import com.jtrend.util.FileUtil;
import com.seox.util.PropertiesLoader;

/*
 * This class currently reads config file and uses pre-defined static user/action type level. well later added cmd. but needs to upgrade to custom level access control
 * for certain applicaiton. not now.
 * 
 * 2014 0425 changes: one map can't hold the situation for site specific configs that has wider coverage than common configs 
 * for example 
 *  site specific has role def :     *.@add ->  Super
 *  common configs has role def :    Action.@add -> User
 *  
 *  the previous version matches the common config because it has narrow match rule. 
 *  
 *  Site rule always overrides the common configs if there is match. So Added separate set of maps for priority search. 
 * 
 *  affected methods 
 *  

private void loadConfigsForSite(String file, boolean reset, Map<String , Map> mapActionToMap, Map<String , Map> mapActionToCmdsMap, Map<String , Map> mapActionToPagesAliasMap,  Map<String , Map> mapActionToGenericResourceNamesMap) {

public SystemRole getSystemRoleFor2(Map<String , Map> rolesMap,  Map<String , Map> rolesMapForSite,  String actionKey, String artifact, String wildCardValue, SystemRole defaultSystemRole) {
public SystemRole getSystemRoleFor(Map<String , Map> rolesMap,String actionKey, String artifact, String wildCardValue, SystemRole defaultSystemRole, String rolesSournce) {
public SystemRole getSystemRoleFor(Map<String , Map> rolesMap, String actionGroup, String actionKey, String artifact, String wildCardValue, SystemRole defaultSystemRole, String rolesSournce) {


 */

public class AccessConfigManager3 implements AccessManager {

    public static final String WILDCARD_ACTION      = "*";
    public static final String DEFAULT_CMD          = "DEFAULT";
    public static final String DEFAULT_PAGEALIAS    = "DEFAULT";
    public static final String DEFAULT_RESOURCE     = "*"; // default custom resource that can be used. 

    
    private static Logger m_logger = LoggerFactory.getLogger(AccessConfigManager3.class);
    private static AccessConfigManager3 m_instance = new AccessConfigManager3();
    private static Map<String, AccessConfigManager3> m_siteInstances = new ConcurrentHashMap<String, AccessConfigManager3>();
    
    // actions name  -> action type (read/update) -> roles for that
    protected Map<String , Map> m_mapActionToMap = new ConcurrentHashMap<String , Map>();
    
    // actions name  -> command -> roles for that
    protected Map<String , Map> m_mapActionToCmdsMap = new ConcurrentHashMap<String , Map>();
    
    // actions name  -> page Alias -> roles for that
    protected  Map<String , Map> m_mapActionToPagesAliasMap = new ConcurrentHashMap<String , Map>();

    // actions name  -> page Alias -> roles for that
    protected  Map<String , Map> m_mapActionToGenericResourceNamesMap = new ConcurrentHashMap<String , Map>();

    // actions name  -> action type (read/update) -> roles for that
    protected Map<String , Map> m_mapActionToMapSite = new ConcurrentHashMap<String , Map>();
    
    // actions name  -> command -> roles for that
    protected Map<String , Map> m_mapActionToCmdsMapSite = new ConcurrentHashMap<String , Map>();
    
    // actions name  -> page Alias -> roles for that
    protected  Map<String , Map> m_mapActionToPagesAliasMapSite = new ConcurrentHashMap<String , Map>();

    // actions name  -> page Alias -> roles for that
    protected  Map<String , Map> m_mapActionToGenericResourceNamesMapSite = new ConcurrentHashMap<String , Map>();

    
    public static AccessConfigManager3 getInstance() {
        m_logger.info("Created Default Instance of AccessConfigManager2");
        return m_instance;
    }

    
    // site extensions for the access controls that overrides the default comon ones
    // files are under config/sites/<site.uril>.access.....
    // for example confg/sites/uxsx.com.accessconfig
    public static AccessConfigManager3 getInstance(String siteUrlKey) {
        
        if (siteUrlKey == null) return m_instance;

        if ( m_siteInstances.containsKey(siteUrlKey)) {
            return m_siteInstances.get(siteUrlKey);
        }

        String configFile = PropertiesLoader.getInstance().getProperty("app.cfg.accessconfig");
        AccessConfigManager3 mgr = new AccessConfigManager3(configFile, "conf_sites/" + siteUrlKey + configFile);
        m_siteInstances.put(siteUrlKey, mgr);
        mgr.setSite(siteUrlKey);
        m_logger.info("Created instance of AccessConfigManager2 with siteUrlKey ["+ siteUrlKey + "]");
        
        return mgr;
    }


    public static AccessConfigManager3 getInstanceForCustom(String title) {
        return m_siteInstances.get(title);
    }    
    
    public static AccessConfigManager3 registerInstanceForCustomTitle(String title, String fileName) {
        
        if ( m_siteInstances.containsKey(title)) {
            return m_siteInstances.get(title);
        }        
        
        AccessConfigManager3 mgr = new AccessConfigManager3(fileName);
        mgr.setSite(title);

        m_logger.info("Created instance of AccessConfigManager2 with title ["+ title + "]");
        m_siteInstances.put(title, mgr);
        return mgr;
    }
    
    protected String m_site = "";

    
    
    public String getSite() {
        return m_site;
    }

    public void setSite(String site) {
        m_site = site;
    }

    private AccessConfigManager3() {
        String configFile = PropertiesLoader.getInstance().getProperty("app.cfg.accessconfig");
        loadConfigs(configFile);
    }    
    
    private AccessConfigManager3(String configFile) {
        loadConfigsForSite(configFile, false, m_mapActionToMapSite, m_mapActionToCmdsMapSite, m_mapActionToPagesAliasMapSite, m_mapActionToGenericResourceNamesMapSite);
    } 

    private AccessConfigManager3(String configbase, String configFile) {
        loadConfigs(configbase);
        if ( configFile != null && !configFile.trim().isEmpty())
            loadConfigsForSite(configFile, false, m_mapActionToMapSite, m_mapActionToCmdsMapSite, m_mapActionToPagesAliasMapSite, m_mapActionToGenericResourceNamesMapSite);
    } 
    
    public boolean hasForAction(String actionKey){
        
        return  m_mapActionToCmdsMap.containsKey(actionKey) || 
                m_mapActionToCmdsMap.containsKey(WILDCARD_ACTION) || 
                m_mapActionToMap.containsKey(actionKey) || 
                m_mapActionToMap.containsKey(WILDCARD_ACTION) ||
                m_mapActionToPagesAliasMap.containsKey(actionKey) || 
                m_mapActionToPagesAliasMap.containsKey(WILDCARD_ACTION) 
                ;
    }

    public SystemRole getSystemRoleByActionType(String actionKey, String actionType, SystemRole defaultSystemRole) {
        return getSystemRoleByActionType(actionKey, ActionType.valueOf(actionType), defaultSystemRole);
    }    

    public SystemRole getSystemRoleByActionType(String actionGroup, String actionKey, String actionType, SystemRole defaultSystemRole) {
        return getSystemRoleByActionType(actionGroup, actionKey, ActionType.valueOf(actionType), defaultSystemRole);
    }    

    public SystemRole getSystemRoleByActionType(String actionKey, ActionType actionType, SystemRole defaultSystemRole) {
        return getSystemRoleByActionType(null, actionKey, actionType, defaultSystemRole);
    }
    
    public SystemRole getSystemRoleByActionType(String actionGroup, String actionKey, ActionType actionType, SystemRole defaultSystemRole) {
        m_logger.debug("getSystemRoleBy [ActionType] {} default {} ", actionKey+" to do "+actionType, defaultSystemRole.name() );

        return getSystemRoleFor2(m_mapActionToMap, m_mapActionToMapSite,  actionKey, actionType.name(), ActionType.Default.name().toUpperCase(), defaultSystemRole);
    }

    public SystemRole getSystemRoleByCommand(String actionKey, String cmd, SystemRole defaultSystemRole) {
        return getSystemRoleByCommand(null, actionKey, cmd, defaultSystemRole);
    }
    public SystemRole getSystemRoleByCommand(String actionGroup, String actionKey, String cmd, SystemRole defaultSystemRole) {
        m_logger.debug("getSystemRoleBy [Command] {} default {} ", actionKey+" to do "+cmd, defaultSystemRole.name() );

        return getSystemRoleFor2(m_mapActionToCmdsMap, m_mapActionToCmdsMapSite,actionKey, cmd, DEFAULT_CMD, defaultSystemRole);
    }
    
    public SystemRole getSystemRoleByPage(String actionKey, String pageAlias, SystemRole defaultSystemRole) {
        return getSystemRoleByPage(null, actionKey, pageAlias, defaultSystemRole);
        
    }
    public SystemRole getSystemRoleByPage(String actionGroup, String actionKey, String pageAlias, SystemRole defaultSystemRole) {
        m_logger.debug("getSystemRole [PAGE] {} default {} ", actionKey+" to view "+pageAlias, defaultSystemRole.name() );

        return getSystemRoleFor2(m_mapActionToPagesAliasMap, m_mapActionToPagesAliasMapSite,  actionKey, pageAlias, DEFAULT_PAGEALIAS, defaultSystemRole);
    }
    public SystemRole getSystemRoleByResource(String actionKey, String resourName, SystemRole defaultSystemRole) {
        return getSystemRoleByResource(null, actionKey, resourName, defaultSystemRole);
    }
    public SystemRole getSystemRoleByResource(String actionGroup, String actionKey, String resourName, SystemRole defaultSystemRole) {
        m_logger.debug("getSystemRole [Resource Name] {} default {} ", actionKey+" to access "+resourName, defaultSystemRole.name() );

        return getSystemRoleFor2(m_mapActionToGenericResourceNamesMap, m_mapActionToGenericResourceNamesMapSite, actionKey, resourName, DEFAULT_RESOURCE, defaultSystemRole);
    }
    
    
    //================ THIS  IS MAIN =======================================================================
    
    public SystemRole getSystemRoleFor2(Map<String , Map> rolesMap,  Map<String , Map> rolesMapForSite,  String actionKey, String artifact, String wildCardValue, SystemRole defaultSystemRole) {
        
        SystemRole retSystemRole = getSystemRoleFor(rolesMapForSite, actionKey, artifact, wildCardValue, null, m_site);
        if ( retSystemRole != null) return retSystemRole;
        
        retSystemRole = getSystemRoleFor(rolesMap, actionKey, artifact, wildCardValue, null, "COMMON");
        if ( retSystemRole != null) return retSystemRole;
        
        m_logger.debug("DEFAULT: NoMatch-NoMatch returns default role for " +actionKey+"." + artifact + " -> " + defaultSystemRole );
        return defaultSystemRole;
    }
    
    public SystemRole getSystemRoleFor(Map<String , Map> rolesMap,String actionKey, String artifact, String wildCardValue, SystemRole defaultSystemRole, String rolesSournce) {
        return getSystemRoleFor(rolesMap, null, actionKey, artifact, wildCardValue, defaultSystemRole, rolesSournce);
    }

    public SystemRole getSystemRoleFor(Map<String , Map> rolesMap, String actionGroup, String actionKey, String artifact, String wildCardValue, SystemRole defaultSystemRole, String rolesSournce) {
        
        if ((actionKey == null && actionGroup == null) || artifact == null) {
            //if ( defaultSystemRole == null) throw new RuntimeException("Default SystemRole not defined (1)");
            return defaultSystemRole;
        }
        if (!rolesMap.containsKey(actionKey)&& !rolesMap.containsKey(actionGroup==null?"":actionGroup) && !rolesMap.containsKey(WILDCARD_ACTION)   ) {
            //m_logger.debug(m_site + ":NoAction, NoWildAction return default role for " +actionKey+"." + artifact + " -> " + defaultSystemRole );
            //if ( defaultSystemRole == null) throw new RuntimeException("Default SystemRole not defined (1)");
            return defaultSystemRole;
        }
        
        artifact = artifact.toUpperCase();
        
        Map<ActionType , SystemRole> subMap         = rolesMap.get(actionKey);
        //Map<ActionType , SystemRole> groupMap       = (actionGroup == null? null: rolesMap.get(actionGroup));
        Map<ActionType , SystemRole> wildCardAction = rolesMap.get(WILDCARD_ACTION);
        
        if (subMap != null && subMap.containsKey(artifact)) {
            
            m_logger.debug(rolesSournce + ":return Matched-Matched role for " +actionKey+"." + artifact + " -> " + subMap.get(artifact) );
            return subMap.get(artifact);
            
        } else if (subMap != null && subMap.containsKey(wildCardValue)){    
            
            m_logger.debug(rolesSournce + ":return Matched-Wild role for " +actionKey+"." + artifact + " -> " + subMap.get(wildCardValue) );
            return subMap.get(wildCardValue);
            
        } else if (wildCardAction!= null && wildCardAction.containsKey(artifact)){    
            
            m_logger.debug(rolesSournce + ":return Wild-Matched role for " +actionKey+"." + artifact + " -> " + wildCardAction.get(artifact) );
            return wildCardAction.get(artifact);
            
        } else if (wildCardAction!= null && wildCardAction.containsKey(wildCardValue)){    
            
            m_logger.debug(rolesSournce + ":return Wild-Wild role for " +actionKey+"." + artifact + " -> " + wildCardAction.get(wildCardValue) );
            return wildCardAction.get(wildCardValue);
        } 

        //m_logger.debug(m_site + ":return NO-MATCH so DEFAULT role for " +actionKey+"." + artifact + " -> " + defaultSystemRole );
        //if ( defaultSystemRole == null) throw new RuntimeException("Default SystemRole not defined (1)");
        return defaultSystemRole;
    }

    //======================================================================================
    // checks whether it has match on wildcard action. THis is needed for partition page access
    // see GetPartitionAction.hasAccess()
    // check page only for now. no time damn!
    public boolean hasMatchForPage(String artifact) {
        return hasMatchFor(m_mapActionToPagesAliasMap,artifact, null);
    }
    
    private boolean hasMatchFor(Map<String , Map> rolesMap,String artifact, String wildCardValue) {
        if ( artifact == null) return false;
        artifact = artifact.toUpperCase();
        
        Map<ActionType , SystemRole> wildCardAction = rolesMap.get(WILDCARD_ACTION);
        
        if (wildCardAction!= null){    
            return wildCardAction.containsKey(artifact);
        } 

        return false;
    }
    
    
    //======================================================================================
    private void loadConfigs(String file) {
        loadConfigs(file, false);
    }    
    
    private void loadConfigs(String file, boolean reset) {

        if (reset){
            this.m_mapActionToMap.clear();
            this.m_mapActionToCmdsMap.clear();
            this.m_mapActionToPagesAliasMap.clear();
            this.m_mapActionToGenericResourceNamesMap.clear();
        }

        m_logger.info("## Acess configs for loading form " + file);
        BufferedReader in = FileUtil.getBufferedReader(file);
        if (in == null) {
            m_logger.debug("Acess configs NOT loading form " + file + ". File not exists");
            return;
        }
        
        try {
            while (true) {

                String line = in.readLine();

                if (line == null)
                    break;
                
                if (line.trim().isEmpty())
                    continue;

                if (line.trim().startsWith("#")) {
                    continue;
                }

                int pos = line.indexOf("=");
                if (pos < 0) {
                    m_logger.warn("Malformatted Config Line Dropped :" + line);
                    continue;
                }

                String key = line.substring(0, pos);
                String roleString = line.substring(pos + 1);

                if ( roleString == null || roleString.trim().isEmpty()) {
                    m_logger.error("Empty role value set to " + key + " will be DROPPED");
                    continue;
                }
                
                int posToDot = key.indexOf(".");
                if (posToDot < 0) {
                    m_logger.warn("Line corrupted = " + line);
                    continue;
                }
                
                
                String action = key.substring(0, posToDot);
                String type = key.substring(posToDot + 1);

                // check if it is define action type or commends

                SystemRole systemRole = null;
                if ( roleString.indexOf(":") > 0 ) {
                    String roleParts[] = roleString.split(":");
                    systemRole = new SystemRole(roleParts[0], Integer.parseInt(roleParts[1]));
                    m_logger.debug("Custom System role was created " + systemRole + " for " + key);
                } else {
                    systemRole = SystemRole.valueOf(roleString);
                    if ( systemRole == null ) 
                        throw new IllegalArgumentException("Invalid role used in config file: " + roleString + " for key " + key);
                }
                
                
                if ( type.startsWith("@") ) { // BY CMD TO UPPER
                    String typeToCmds = type.substring(1);
                    //Cmd string can be separted by "," in one line
                    String cmd[] = typeToCmds.split(",");
                    
                    for (int i = 0; i < cmd.length; i++) {

                        Map cmdToRolesMap = null;
                        
                        cmdToRolesMap = m_mapActionToCmdsMap.get(action);
                        if (cmdToRolesMap == null) {
                            cmdToRolesMap = new ConcurrentHashMap<String , SystemRole>();
                            m_mapActionToCmdsMap.put(action, cmdToRolesMap);
                        }
                        
                        if (isDefaultWildForCmdOrResourceOrPage( cmd[i].toUpperCase())) {
                            cmdToRolesMap.put( DEFAULT_CMD , systemRole);
                            m_logger.info("action *DEFAULT* CMD to role " + action + "." +  DEFAULT_CMD + "  -> " + roleString);
                        } else {
                            cmdToRolesMap.put( cmd[i].toUpperCase(), systemRole);
                            m_logger.info("action CMD to role " + action + "." +  cmd[i].toUpperCase() + "  -> " + roleString);
                        }
                    }
                } else if ( type.startsWith(">") ) { // Page
                    
                    String typeToPages = type.substring(1);
                    //Cmd string can be separted by "," in one line
                    String pages[] = typeToPages.split(",");
                    
                    for (int i = 0; i < pages.length; i++) {

                        Map pagesToRolesMap = null;
                        
                        pagesToRolesMap = m_mapActionToPagesAliasMap.get(action);
                        if (pagesToRolesMap == null) {
                            pagesToRolesMap = new ConcurrentHashMap<String , SystemRole>();
                            m_mapActionToPagesAliasMap.put(action, pagesToRolesMap);
                        }
                    
                        if (isDefaultWildForCmdOrResourceOrPage( pages[i].toUpperCase())) {
                            pagesToRolesMap.put( DEFAULT_PAGEALIAS , systemRole);
                            m_logger.info("action *DEFAULT* PAGE to role " + action + "." + DEFAULT_PAGEALIAS + "  -> " + roleString);
                        } else {
                            pagesToRolesMap.put( pages[i].toUpperCase(), systemRole);
                            m_logger.info("action PAGE to role " + action + "." +  pages[i].toUpperCase() + "  -> " + roleString);
                        }
                    }
                    
                } else if ( type.startsWith("$") ) { // Resource
                    
                    String typeToPages = type.substring(1);
                    //Cmd string can be separted by "," in one line
                    String resources[] = typeToPages.split(",");
                    
                    for (int i = 0; i < resources.length; i++) {

                        Map resourcesToRolesMap = null;
                        
                        resourcesToRolesMap = m_mapActionToGenericResourceNamesMap.get(action);
                        if (resourcesToRolesMap == null) {
                            resourcesToRolesMap = new ConcurrentHashMap<String , SystemRole>();
                            m_mapActionToGenericResourceNamesMap.put(action, resourcesToRolesMap);
                        }
                    
                        if (isDefaultWildForCmdOrResourceOrPage( resources[i].toUpperCase())) {
                            resourcesToRolesMap.put( DEFAULT_RESOURCE , systemRole);
                            m_logger.info("action *DEFAULT* GenericReousrce to role " + action + "." +  DEFAULT_RESOURCE + "  -> " + roleString);
                        } else {
                            resourcesToRolesMap.put( resources[i].toUpperCase(), systemRole);
                            m_logger.info("action GenericReousrce to role " + action + "." +  resources[i].toUpperCase() + "  -> " + roleString);
                        }
                    }
                    
                } else { //BY ActionType defined in enum

                    String types[] = type.split(",");
                    
                    for (int i = 0; i < types.length; i++) {
                        
                        ActionType actionType;
                        try {
                            actionType = ActionType.valueOf(types[i]);
                        }
                        catch (Exception e) {
                            m_logger.error(e.getMessage(),e);
                            continue;
                        }

                        Map actionTypeToRolesMap = null;
                            actionTypeToRolesMap = m_mapActionToMap.get(action);
                            
                        if (actionTypeToRolesMap == null) {
                            actionTypeToRolesMap = new ConcurrentHashMap<ActionType , SystemRole>();
                            m_mapActionToMap.put(action, actionTypeToRolesMap);
                        }

                        if (isDefaultWildForActionType( actionType.name().toUpperCase())) {
                            actionTypeToRolesMap.put(actionType.name().toUpperCase(), systemRole);
                            m_logger.info("action *DEFAULT* TYPE to role " + action + "." + types[i] + "  -> " + roleString);
                        } else {
                            actionTypeToRolesMap.put(actionType.name().toUpperCase(), systemRole);
                            m_logger.info("action TYPE to role " + action + "." + types[i] + "  -> " + roleString);
                        }
                        
                    }
                }
            }
        }
        catch (IOException e) {
            m_logger.error(e.getMessage(), e);
        }
    }
    

    private void loadConfigsForSite(String file, boolean reset, Map<String , Map> mapActionToMap, Map<String , Map> mapActionToCmdsMap, Map<String , Map> mapActionToPagesAliasMap,  Map<String , Map> mapActionToGenericResourceNamesMap) {

        if (reset){
            mapActionToMap.clear();
            mapActionToCmdsMap.clear();
            mapActionToPagesAliasMap.clear();
            mapActionToGenericResourceNamesMap.clear();
        }

        m_logger.info("## Acess configs for loading form " + file);
        BufferedReader in = FileUtil.getBufferedReader(file);
        if (in == null) {
            m_logger.debug("Acess configs NOT loading form " + file + ". File not exists");
            return;
        }
        
        try {
            while (true) {

                String line = in.readLine();

                if (line == null)
                    break;
                
                if (line.trim().isEmpty())
                    continue;

                if (line.trim().startsWith("#")) {
                    continue;
                }

                int pos = line.indexOf("=");
                if (pos < 0) {
                    m_logger.warn("Malformatted Config Line Dropped :" + line);
                    continue;
                }

                String key = line.substring(0, pos);
                String roleString = line.substring(pos + 1);

                if ( roleString == null || roleString.trim().isEmpty()) {
                    m_logger.error("Empty role value set to " + key + " will be DROPPED");
                    continue;
                }
                
                int posToDot = key.indexOf(".");
                if (posToDot < 0) {
                    m_logger.warn("Line corrupted = " + line);
                    continue;
                }
                
                
                String action = key.substring(0, posToDot);
                String type = key.substring(posToDot + 1);

                // check if it is define action type or commends

                SystemRole systemRole = null;
                if ( roleString.indexOf(":") > 0 ) {
                    String roleParts[] = roleString.split(":");
                    systemRole = new SystemRole(roleParts[0], Integer.parseInt(roleParts[1]));
                    m_logger.debug("Custom System role was created " + systemRole + " for " + key);
                } else {
                    systemRole = SystemRole.valueOf(roleString);
                    if ( systemRole == null ) 
                        throw new IllegalArgumentException("Invalid role used in config file: " + roleString + " for key " + key);
                }
                
                
                if ( type.startsWith("@") ) { // BY CMD TO UPPER
                    String typeToCmds = type.substring(1);
                    //Cmd string can be separted by "," in one line
                    String cmd[] = typeToCmds.split(",");
                    
                    for (int i = 0; i < cmd.length; i++) {

                        Map cmdToRolesMap = null;
                        
                        cmdToRolesMap = mapActionToCmdsMap.get(action);
                        if (cmdToRolesMap == null) {
                            cmdToRolesMap = new ConcurrentHashMap<String , SystemRole>();
                            mapActionToCmdsMap.put(action, cmdToRolesMap);
                        }
                        
                        if (isDefaultWildForCmdOrResourceOrPage( cmd[i].toUpperCase())) {
                            cmdToRolesMap.put( DEFAULT_CMD , systemRole);
                            m_logger.info("action *DEFAULT* CMD to role " + action + "." +  DEFAULT_CMD + "  -> " + roleString);
                        } else {
                            cmdToRolesMap.put( cmd[i].toUpperCase(), systemRole);
                            m_logger.info("action CMD to role " + action + "." +  cmd[i].toUpperCase() + "  -> " + roleString);
                        }
                    }
                } else if ( type.startsWith(">") ) { // Page
                    
                    String typeToPages = type.substring(1);
                    //Cmd string can be separted by "," in one line
                    String pages[] = typeToPages.split(",");
                    
                    for (int i = 0; i < pages.length; i++) {

                        Map pagesToRolesMap = null;
                        
                        pagesToRolesMap = mapActionToPagesAliasMap.get(action);
                        if (pagesToRolesMap == null) {
                            pagesToRolesMap = new ConcurrentHashMap<String , SystemRole>();
                            mapActionToPagesAliasMap.put(action, pagesToRolesMap);
                        }
                    
                        if (isDefaultWildForCmdOrResourceOrPage( pages[i].toUpperCase())) {
                            pagesToRolesMap.put( DEFAULT_PAGEALIAS , systemRole);
                            m_logger.info("action *DEFAULT* PAGE to role " + action + "." + DEFAULT_PAGEALIAS + "  -> " + roleString);
                        } else {
                            pagesToRolesMap.put( pages[i].toUpperCase(), systemRole);
                            m_logger.info("action PAGE to role " + action + "." +  pages[i].toUpperCase() + "  -> " + roleString);
                        }
                    }
                    
                } else if ( type.startsWith("$") ) { // Resource
                    
                    String typeToPages = type.substring(1);
                    //Cmd string can be separted by "," in one line
                    String resources[] = typeToPages.split(",");
                    
                    for (int i = 0; i < resources.length; i++) {

                        Map resourcesToRolesMap = null;
                        
                        resourcesToRolesMap = mapActionToGenericResourceNamesMap.get(action);
                        if (resourcesToRolesMap == null) {
                            resourcesToRolesMap = new ConcurrentHashMap<String , SystemRole>();
                            mapActionToGenericResourceNamesMap.put(action, resourcesToRolesMap);
                        }
                    
                        if (isDefaultWildForCmdOrResourceOrPage( resources[i].toUpperCase())) {
                            resourcesToRolesMap.put( DEFAULT_RESOURCE , systemRole);
                            m_logger.info("action *DEFAULT* GenericReousrce to role " + action + "." +  DEFAULT_RESOURCE + "  -> " + roleString);
                        } else {
                            resourcesToRolesMap.put( resources[i].toUpperCase(), systemRole);
                            m_logger.info("action GenericReousrce to role " + action + "." +  resources[i].toUpperCase() + "  -> " + roleString);
                        }
                    }
                    
                } else { //BY ActionType defined in enum

                    String types[] = type.split(",");
                    
                    for (int i = 0; i < types.length; i++) {
                        
                        ActionType actionType;
                        try {
                            actionType = ActionType.valueOf(types[i]);
                        }
                        catch (Exception e) {
                            m_logger.error(e.getMessage(),e);
                            continue;
                        }

                        Map actionTypeToRolesMap = null;
                            actionTypeToRolesMap = mapActionToMap.get(action);
                            
                        if (actionTypeToRolesMap == null) {
                            actionTypeToRolesMap = new ConcurrentHashMap<ActionType , SystemRole>();
                            mapActionToMap.put(action, actionTypeToRolesMap);
                        }

                        if (isDefaultWildForActionType( actionType.name().toUpperCase())) {
                            actionTypeToRolesMap.put(actionType.name().toUpperCase(), systemRole);
                            m_logger.info("action *DEFAULT* TYPE to role " + action + "." + types[i] + "  -> " + roleString);
                        } else {
                            actionTypeToRolesMap.put(actionType.name().toUpperCase(), systemRole);
                            m_logger.info("action TYPE to role " + action + "." + types[i] + "  -> " + roleString);
                        }
                        
                    }
                }
            }
        }
        catch (IOException e) {
            m_logger.error(e.getMessage(), e);
        }
    }
    
        
    
    public void mergeFrom( AccessConfigManager3 from, boolean overrideDuplicate) {

        from.m_mapActionToCmdsMap.putAll(this.m_mapActionToCmdsMap);
        from.m_mapActionToMap.putAll(this.m_mapActionToMap);
        from.m_mapActionToPagesAliasMap.putAll(this.m_mapActionToPagesAliasMap);
        from.m_mapActionToGenericResourceNamesMap.putAll(this.m_mapActionToGenericResourceNamesMap);

        m_logger.info("AccesConfigManager merged from " + from.getSite());
    }
    
    private boolean isDefaultWildForCmdOrResourceOrPage(String in){
        if ( in == null) return false;
        
        if ( in.trim().toUpperCase().equalsIgnoreCase("DEFAULT")  || in.trim().toUpperCase().equalsIgnoreCase("*"))
            return true;
        return false;
    }

    private boolean isDefaultWildForActionType(String in){
        if ( in == null) return false;
        
        if ( in.trim().toUpperCase().equalsIgnoreCase("DEFAULT") || in.trim().toUpperCase().equalsIgnoreCase("*"))
            return true;
        return false;
    }

    public static void main3(String[] args) {
        
        AccessManager local = AccessConfigManager3.getInstance("localhost");
        AccessManager def = AccessConfigManager3.getInstance();
        
        System.out.println(local.getSystemRoleByPage("DevNote", "default", SystemRole.Anonymous));
        System.out.println(local.getSystemRoleByPage("DevNote", "dev_note_home", SystemRole.Anonymous));
        System.out.println(local.getSystemRoleByPage("GenNote", "dev_note_home", SystemRole.Anonymous));
        
        System.out.println(def.getSystemRoleByPage("DevNote", "default", SystemRole.Anonymous));
        System.out.println(def.getSystemRoleByPage("DevNote", "dev_note_home", SystemRole.Anonymous));
        System.out.println(def.getSystemRoleByPage("GenNote", "dev_note_home", SystemRole.Anonymous));
        
    }    
    public static void main(String[] args) {
        
        AccessManager am = AccessConfigManager3.getInstance("uxsx.com");
        
        System.out.println(am.getSystemRoleByCommand("DevNote", "default", SystemRole.Anonymous));
        System.out.println(am.getSystemRoleByCommand("DevNote", "add", SystemRole.Anonymous));
        System.out.println(am.getSystemRoleByCommand("GenNote", "default", SystemRole.Anonymous));
        System.out.println(am.getSystemRoleByCommand("GenNote", "add", SystemRole.Anonymous));
        System.out.println(am.getSystemRoleByCommand("GenTable", "edit", SystemRole.Anonymous));

        System.out.println(am.getSystemRoleByActionType("GenNote", "Read", SystemRole.SiteAdmin));
        System.out.println(am.getSystemRoleByActionType("DevNote", "Default", SystemRole.SiteAdmin));

        am = AccessConfigManager3.getInstance();
    
        System.out.println(am.getSystemRoleByCommand("DevNote", "default", SystemRole.Anonymous));
        System.out.println(am.getSystemRoleByCommand("DevNote", "add", SystemRole.Anonymous));
        System.out.println(am.getSystemRoleByCommand("GenNote", "default", SystemRole.Anonymous));
        System.out.println(am.getSystemRoleByCommand("GenNote", "add", SystemRole.Anonymous));
        System.out.println(am.getSystemRoleByCommand("GenTable", "edit", SystemRole.Anonymous));

        System.out.println(am.getSystemRoleByActionType("GenNote", "Read", SystemRole.SiteAdmin));
        System.out.println(am.getSystemRoleByActionType("DevNote", "Default", SystemRole.SiteAdmin));
        System.out.println(am.getSystemRoleByResource("DevNote", "BB", SystemRole.SiteAdmin));
        
        
        AccessConfigManager3 mgr = AccessConfigManager3.registerInstanceForCustomTitle("XXX", "conf/custom_access_config.properties" );
        
        
        System.out.println(mgr.getSystemRoleByActionType("GenTable", "Update", SystemRole.SiteAdmin));

        
        
        AccessConfigManager3 churAccessMgr = AccessConfigManager3.registerInstanceForCustomTitle("ChurApp", "conf/churapp_access_config.properties" );
        
    }
    
}
