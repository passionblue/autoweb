package com.autosite.util.access;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.autosite.ds.ChurIncomeDS;
import com.autosite.util.access.AccessDef.ActionType;
import com.autosite.util.access.AccessDef.SystemRole;
import com.jtrend.util.FileUtil;
import com.seox.util.PropertiesLoader;





/*
 * This class currently reads config file and uses pre-defined static user/action type level. well later added cmd. but needs to upgrade to custom level access control
 * for certain applicaiton. not now.
 * @see AccessConfigManager2 
 */

@Deprecated

public class AccessConfigManager {

    public static final String WILDCARD_ACTION      = "*";
    public static final String DEFAULT_CMD          = "DEFAULT";
    public static final String DEFAULT_PAGEALIAS    ="DEFAULT";
    
    // actions name  -> action type (read/update) -> roles for that
    Map<String , Map> m_mapActionToMap = new ConcurrentHashMap<String , Map>();
    
    // actions name  -> command -> roles for that
    Map<String , Map> m_mapActionToCmdsMap = new ConcurrentHashMap<String , Map>();
    
    // actions name  -> page Alias -> roles for that
    Map<String , Map> m_mapActionToPagesAliasMap = new ConcurrentHashMap<String , Map>();

    private static Logger m_logger = Logger.getLogger(AccessConfigManager.class);
    private static AccessConfigManager m_instance = new AccessConfigManager();

    private static Map<String, AccessConfigManager> m_siteInstances = new ConcurrentHashMap<String, AccessConfigManager>();
    
    public static AccessConfigManager getInstance() {
        return m_instance;
    }
    
    // site extensions for the access controls that overrides the default comon ones
    // files are under config/sites/<site.uril>.access.....
    // for example confg/sites/uxsx.com.accessconfig
    public static AccessConfigManager getInstance(String siteUrlKey) {
        
        if (siteUrlKey == null) return m_instance;

        if ( m_siteInstances.containsKey(siteUrlKey)) {
            return m_siteInstances.get(siteUrlKey);
        }

        String configFile = PropertiesLoader.getInstance().getProperty("app.cfg.accessconfig");
        AccessConfigManager mgr = new AccessConfigManager("conf_sites/" + siteUrlKey + configFile);
        m_siteInstances.put(siteUrlKey, mgr);
        
        return mgr;
    }

    private AccessConfigManager() {
        String configFile = PropertiesLoader.getInstance().getProperty("app.cfg.accessconfig");
        loadConfigs(configFile);
    }    
    
    private AccessConfigManager(String configFile) {
        loadConfigs(configFile);
    } 
    
    private void loadConfigs(String file) {


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

                int pos = line.indexOf("=");
                if (pos < 0) {
                    m_logger.warn("Line corrupted = " + line);
                    continue;
                }

                if (line.trim().startsWith("#")) {
                    continue;
                }
                
                String key = line.substring(0, pos);
                String role = line.substring(pos + 1);

                int posToDot = key.indexOf(".");
                if (posToDot < 0) {
                    m_logger.warn("Line corrupted = " + line);
                    continue;
                }
                
                
                String action = key.substring(0, posToDot);
                String type = key.substring(posToDot + 1);

                // check if it is define action type or commends
                SystemRole systemRole = SystemRole.valueOf(role);
                
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
                        
                        cmdToRolesMap.put( cmd[i].toUpperCase(), systemRole);
                        m_logger.info("action CMD to role " + action + "." +  cmd[i].toUpperCase() + "  -> " + role);
                    }
                } else if ( type.startsWith(">") || type.startsWith("$")  ) {
                    
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
                        
                        pagesToRolesMap.put( pages[i].toUpperCase(), systemRole);
                        m_logger.info("action PAGE to role " + action + "." +  pages[i].toUpperCase() + "  -> " + role);
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

                        actionTypeToRolesMap.put(actionType, systemRole);
                        m_logger.info("action TYPE to role " + action + "." + types[i] + "  -> " + role);
                    }
                }
            }
        }
        catch (IOException e) {
            m_logger.error(e);
        }
    }

    public List getDefinedTypesForAction(String actionKey) {
        
        if (!m_mapActionToMap.containsKey(actionKey)) return new ArrayList();
        return new ArrayList(m_mapActionToMap.get(actionKey).keySet());
    }
    
    public List getConfiguredCommandsForAction(String actionKey) {
        
        if (!m_mapActionToCmdsMap.containsKey(actionKey)) return new ArrayList();
        return new ArrayList(m_mapActionToCmdsMap.get(actionKey).keySet());
    }
    
    public List getConfiguredPagesForAction(String actionKey) {
        
        if (!m_mapActionToPagesAliasMap.containsKey(actionKey)) return new ArrayList();
        return new ArrayList(m_mapActionToPagesAliasMap.get(actionKey).keySet());
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
    
    public SystemRole getSystemRole(String actionKey, String actionType, String defaultSystemRole) {
        return getSystemRole(actionKey, ActionType.valueOf(actionType), SystemRole.valueOf(defaultSystemRole));
    }    
    
    public SystemRole getSystemRole(String actionKey, ActionType actionType, SystemRole defaultSystemRole) {
        
        if (actionKey == null || actionType == null) return defaultSystemRole;
        if (!m_mapActionToMap.containsKey(actionKey)&& !m_mapActionToMap.containsKey(WILDCARD_ACTION))return defaultSystemRole;
        
        Map<ActionType , SystemRole> subMap = m_mapActionToMap.get(actionKey);
        Map<ActionType , SystemRole> wildCardAction = m_mapActionToMap.get(WILDCARD_ACTION);
        
        if (subMap != null && subMap.containsKey(actionType)) {
            m_logger.debug("returns FOUND role for " +actionKey+"." + actionType + " -> " + subMap.get(actionType) );
            return subMap.get(actionType);
        } else if (subMap != null && subMap.containsKey(ActionType.Default)){    
            m_logger.debug("return NOT FOUND role for " +actionKey+"." + actionType + " But ActionType.Default found " + subMap.containsKey(ActionType.Default) );
            return subMap.get(actionType);
        } else if (wildCardAction != null && wildCardAction.containsKey(actionType)){    
            m_logger.debug("returns FOUND WILDCARD role for " +actionKey+"." + actionType + " -> " + wildCardAction.get(actionType) );
            return wildCardAction.get(actionType);
        } else if (wildCardAction != null && wildCardAction.containsKey(ActionType.Default)){    
            m_logger.debug("return NOT FOUND WILDCARD role for " +actionKey+"." + actionType + " But ActionType.Default found " + wildCardAction.containsKey(ActionType.Default) );
            return wildCardAction.get(actionType);
        }
        
        
        m_logger.debug("returns default role for " +actionKey+"." + actionType + " -> " + defaultSystemRole );
        return defaultSystemRole;
    }

    public SystemRole getSystemRoleByCommand(String actionKey, String cmd, SystemRole defaultSystemRole) {
        
        if (actionKey == null || cmd == null) return defaultSystemRole;
        if (!m_mapActionToCmdsMap.containsKey(actionKey)&& !m_mapActionToCmdsMap.containsKey(WILDCARD_ACTION))return defaultSystemRole;
        
        Map<ActionType , SystemRole> subMap = m_mapActionToCmdsMap.get(actionKey);
        Map<ActionType , SystemRole> wildCardAction = m_mapActionToCmdsMap.get(WILDCARD_ACTION);
        
        if (subMap != null && subMap.containsKey(cmd)) {
            m_logger.debug("return FOUND role for " +actionKey+"." + cmd + " -> " + subMap.get(cmd) );
            return subMap.get(cmd);
        } else if (subMap != null && subMap.containsKey(DEFAULT_CMD)){    
            m_logger.debug("return NOT FOUND role for " +actionKey+"." + cmd + " But ActionType.Default found2 " + subMap.containsKey(DEFAULT_CMD) );
            return subMap.get(DEFAULT_CMD);
        } else if (wildCardAction!= null && wildCardAction.containsKey(cmd)){    
            m_logger.debug("return FOUND WILDCARD role for " +actionKey+"." + cmd + " -> " + wildCardAction.get(cmd) );
            return wildCardAction.get(cmd);
        } else if (wildCardAction!= null && wildCardAction.containsKey(DEFAULT_CMD)){    
            m_logger.debug("return NOT FOUND WILDCARD role for " +actionKey+"." + cmd + " But ActionType.Default found " + wildCardAction.containsKey(DEFAULT_CMD) );
            return wildCardAction.get(DEFAULT_CMD);
        } 

        m_logger.debug("return default role for " +actionKey+"." + cmd + " -> " + defaultSystemRole );
        return defaultSystemRole;
    }
    
    
    public SystemRole getSystemRoleByPage(String actionKey, String pageAlias, SystemRole defaultSystemRole) {
        
        if (actionKey == null || pageAlias == null) return defaultSystemRole;
        if (!m_mapActionToPagesAliasMap.containsKey(actionKey)&& !m_mapActionToPagesAliasMap.containsKey(WILDCARD_ACTION))return defaultSystemRole;
        
        Map<ActionType , SystemRole> subMap         = m_mapActionToPagesAliasMap.get(actionKey);
        Map<ActionType , SystemRole> wildCardAction = m_mapActionToPagesAliasMap.get(WILDCARD_ACTION);
        
        if (subMap != null && subMap.containsKey(pageAlias)) {
            m_logger.debug("return FOUND role for " +actionKey+"." + pageAlias + " -> " + subMap.get(pageAlias) );
            return subMap.get(pageAlias);
        } else if (subMap != null && subMap.containsKey(DEFAULT_PAGEALIAS)){    
            m_logger.debug("return NOT FOUND role for " +actionKey+"." + pageAlias + " But ActionType.Default found2 " + subMap.containsKey(DEFAULT_PAGEALIAS) );
            return subMap.get(DEFAULT_PAGEALIAS);
        } else if (wildCardAction!= null && wildCardAction.containsKey(pageAlias)){    
            m_logger.debug("return FOUND WILDCARD role for " +actionKey+"." + pageAlias + " -> " + wildCardAction.get(pageAlias) );
            return wildCardAction.get(pageAlias);
        } else if (wildCardAction!= null && wildCardAction.containsKey(DEFAULT_PAGEALIAS)){    
            m_logger.debug("return NOT FOUND WILDCARD role for " +actionKey+"." + pageAlias + " But ActionType.Default found " + wildCardAction.containsKey(DEFAULT_PAGEALIAS) );
            return wildCardAction.get(DEFAULT_PAGEALIAS);
        } 

        m_logger.debug("return default role for " +actionKey+"." + pageAlias + " -> " + defaultSystemRole );
        return defaultSystemRole;
    }
    
    
    
    public static String getActionName() {
        return "ChurMember";
    }    
    
    public static void main(String[] args) {
        List list = ChurIncomeDS.getInstance().getBySiteIdWeekList(238, "01/15");
        System.out.println(list.size());
        
        SystemRole defaultRole = AccessConfigManager.getInstance().getSystemRole(getActionName(), AccessDef.ActionType.Default, AccessDef.SystemRole.SiteAdmin);
        AccessControl m_accessControl = new AccessControl("ChurMember", AccessDef.SystemRole.User); ;
        
        if ( defaultRole != null ) 
            m_accessControl.setDefaultForType(defaultRole);
        
        List<AccessDef.ActionType> typesDefined =AccessConfigManager.getInstance().getDefinedTypesForAction(getActionName());
        for (AccessDef.ActionType t : typesDefined) {
            AccessDef.SystemRole role = AccessConfigManager.getInstance().getSystemRole(getActionName(), t, defaultRole);
            if ( role != null ) { 
                m_accessControl.setByActionType(t, role);
                m_logger.info(" ACCESS CONTROL " + t + " ---> " + role);
            }
        }
        
    }
    
}
