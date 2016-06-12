package com.autosite.struts.action.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.Constants;
import com.autosite.db.AutositeUser;
import com.autosite.session.AutositeSessionContext;
import com.autosite.util.UserUtil;
import com.autosite.util.access.AccessConfigManager;
import com.autosite.util.access.AccessControl;
import com.autosite.util.access.AccessDef;
import com.autosite.util.access.AccessDef.ActionType;
import com.autosite.util.access.AccessDef.SystemRole;
import com.jtrend.session.SessionContext;
import com.seox.util.PropertiesLoader;

public class CopyOfAutositeAccessAction extends AutositeBaseAction {

    private static Logger m_logger = LoggerFactory.getLogger(CopyOfAutositeAccessAction.class);
    
    protected AccessControl m_accessControl; //Action control for the Action
    protected Map m_accessControlBySite = new ConcurrentHashMap();
    
    public CopyOfAutositeAccessAction() {
        super();
        initializeSystemRolesBasedAccessControl();
    }
    
    protected boolean setSuperLogin(HttpSession session) throws Exception{
        AutositeUser user = UserUtil.getSuperAdminObject();
        return setLogin(session, user);
    }
    
    protected boolean setLogin(HttpSession session, Object u) throws Exception{
        
        AutositeUser user = (AutositeUser) u;
        String username = user.getUsername();
        
        AutositeSessionContext context = (AutositeSessionContext)getSessionContext(session); 
        
        if ( context.isLogin()){

            if (!username.equals(context.getUsername())){
                context.setLogin(false);
                throw new Exception("Currently different user already logged in in this session. Please try again");
            }

            m_logger.info("User " + username + " already logged in");
        } else {
            context.setLogin(true);
            context.setUsername(username);
            context.setUserObject(user);
        }
        
        
        m_logger.info("user " + username + " logged in");
        
        return true;
    }

    protected boolean setLogout(HttpSession session){
        
        SessionContext context = getSessionContext(session); 
        
        if (context.isLogin()) {
            context.setLogin(false);
        }
         context.setUsername(null);
        return true;
    }
    
    //==================================================================================================================================
    // Access and Roles
    //==================================================================================================================================
    
    
    protected boolean isSuperAdmin(HttpSession session){
        return UserUtil.isSuperAdmin(session);
    }

    public AccessControl getAccessControl(String actionName){
        if (m_accessControl != null) return m_accessControl;
        return getDeafultAccessControl(actionName);
    }
    
    public AccessControl getDeafultAccessControl(String actionName){
        return new AccessControl(actionName, AccessDef.SystemRole.User); 
    }
    
    
    
    // Access control is contorl object bound to action that gets configured from the file and default setting
    // It contains system role based access control setting, which to be used when user access to it. 

    
    
    protected boolean haveAccessToCommand(HttpSession session, String cmd){

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser            autoUser = context.getUserObject();
        
        boolean resultByDefault = haveAccess(autoUser, m_accessControl, cmd);
                
        if ( m_accessControlBySite.containsKey(context.getServer())){
            m_logger.debug("Access controls done by site specific configurations for " + context.getServer() + " action=" + getActionName());
            AccessControl siteAccess = (AccessControl)m_accessControlBySite.get(context.getServer());
            return haveAccess(autoUser, siteAccess, cmd);
        }
        
        return resultByDefault;
    }    

    protected boolean haveAccessToActionType(HttpSession session, ActionType actionType){
        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser            autoUser = context.getUserObject();
        
        boolean resultByDefault = haveAccess(autoUser, m_accessControl, actionType);
        
        if ( m_accessControlBySite.containsKey(context.getServer())){
            m_logger.debug("Access controls done by site specific configurations for " + context.getServer() + " action=" + getActionName());
            AccessControl siteAccess = (AccessControl)m_accessControlBySite.get(context.getServer());
            return haveAccess(autoUser, siteAccess, actionType);
        }
        
        return resultByDefault;
    }
    
    protected boolean haveAccessToPage(HttpSession session, String pageAlias){

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser            autoUser = context.getUserObject();
        
        boolean resultByDefault = haveAccessOnPage(autoUser, m_accessControl, pageAlias);
                
        if ( m_accessControlBySite.containsKey(context.getServer())){
            m_logger.debug("Access controls done by site specific configurations for " + context.getServer() + " action=" + getActionName());
            AccessControl siteAccess = (AccessControl)m_accessControlBySite.get(context.getServer());
            return haveAccessOnPage(autoUser, siteAccess, pageAlias);
        }
        
        return resultByDefault;
    }    
    
    
    protected boolean haveAccessToUpdate(HttpSession session){
        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser            autoUser = context.getUserObject();

        ActionType actionType = ActionType.Update;
        
        boolean resultByDefault = haveAccess(autoUser, m_accessControl, actionType);
        
        if ( m_accessControlBySite.containsKey(context.getServer())){
            m_logger.debug("Access controls done by site specific configurations for " + context.getServer() + " action=" + getActionName());
            AccessControl siteAccess = (AccessControl)m_accessControlBySite.get(context.getServer());
            return haveAccess(autoUser, siteAccess, actionType);
        }
        
        return resultByDefault;
    }

    protected boolean haveAccessToRead(HttpSession session){
        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser            autoUser = context.getUserObject();

        ActionType actionType = ActionType.Read;
        
        boolean resultByDefault = haveAccess(autoUser, m_accessControl, actionType);
        
        if ( m_accessControlBySite.containsKey(context.getServer())){
            m_logger.debug("Access controls done by site specific configurations for " + context.getServer() + " action=" + getActionName());
            AccessControl siteAccess = (AccessControl)m_accessControlBySite.get(context.getServer());
            return haveAccess(autoUser, siteAccess, actionType);
        }
        
        return resultByDefault;
    }

    protected boolean haveAccessToPageAccess(HttpSession session){
        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser            autoUser = context.getUserObject();

        ActionType actionType = ActionType.PageAccess;
        
        boolean resultByDefault = haveAccess(autoUser, m_accessControl, actionType);
        
        if ( m_accessControlBySite.containsKey(context.getServer())){
            m_logger.debug("Access controls done by site specific configurations for " + context.getServer() + " action=" + getActionName());
            AccessControl siteAccess = (AccessControl)m_accessControlBySite.get(context.getServer());
            return haveAccess(autoUser, siteAccess, actionType);
        }
        
        return resultByDefault;
    }
    
    
//    protected boolean haveAccess(HttpSession session, AccessControl accessControl){
//        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
//        AutositeUser            autoUser = context.getUserObject();
//        ActionType              actionType = ActionType.Update;
//        
//        return haveAccess(autoUser, accessControl, actionType);
//    }
    
    protected boolean haveAccess(AutositeUser user, AccessControl accessControl, ActionType actionType){
        
        SystemRole roleForThis = accessControl.getByActionType(actionType);

        if ( user == null ) {
            if ( roleForThis.level() == AccessDef.SystemRole.Anonymous.level() ) {
                m_logger.info("ACCESS-PASS: Anonymous access granted to {} action type {} ", accessControl.getName(), actionType.name());
                return true;
            } else {
                m_logger.info("ACCESS-EXCEPTION: No user/Anonymous action type {} leve {}", actionType.name(), roleForThis.level()+"");
                return false;
            }
        }
        
        if (user.getUserType() == Constants.UserSuperAdmin ) {
            m_logger.info("ACCESS-PASS: SUPER User has access " + user.getUsername() + " access " + actionType.name() + " level " + roleForThis.level() + " userLevel " + user.getUserType() + " actionName " + accessControl.getName());
            return true;
        }

        m_logger.info("Checking Access for " + user.getUsername() + "(" + user.getUserType() + ") against " + accessControl + " action " + actionType.name());
        if ( roleForThis.level() <= user.getUserType()) {
            m_logger.warn("User has access " + user.getUsername() + " access " + actionType.name() + " level " + roleForThis.level() + " userLevel " + user.getUserType() + " actionName " + accessControl.getName());
            return true;
        }
        
        m_logger.warn("User has NO access " + user.getUsername() + " access " + actionType.name() + " level " + roleForThis.level() + " userLevel " + user.getUserType() + " actionName " + accessControl.getName());
        
        return false;
    }

    
    protected boolean haveAccess(AutositeUser user, AccessControl accessControl, String  cmd){
        
        if (cmd == null) return false;

        cmd = cmd.trim().toUpperCase();
        
        SystemRole roleForThis = accessControl.getByActionCmd(cmd.trim());
        
        if ( user == null ) {
            if ( roleForThis.level() == AccessDef.SystemRole.Anonymous.level() ) {
                m_logger.info("ACCESS-PASS: Anonymous access granted to {} CMD {} ", accessControl.getName(), cmd);
                return true;
            } else {
                m_logger.info("ACCESS-EXCEPTION: No user/Anonymous CMD {} leve {}", cmd, roleForThis.level()+"");
                return false;
            }
        }
        
        if (user.getUserType() == Constants.UserSuperAdmin ) {
            m_logger.info("ACCESS-PASS: SUPER User has access " + user.getUsername() + " access " + cmd + " level " + roleForThis.level() + " userLevel " + user.getUserType() + " actionName " + accessControl.getName());
            return true;
        }

        m_logger.info("Checking Access for " + user.getUsername() + "(" + user.getUserType() + ") against " + accessControl + " action " + cmd);
        if ( roleForThis.level() <= user.getUserType()) {
            m_logger.warn("ACCESS-PASS: User has access " + user.getUsername() + " access " + cmd+ " level " + roleForThis.level() + " userLevel " + user.getUserType() + " actionName " + accessControl.getName());
            return true;
        }
        
        m_logger.warn("ACCESS-EXCEPTION: User has NO access " + user.getUsername() + " access " + cmd + " level " + roleForThis.level() + " userLevel " + user.getUserType() + " actionName " + accessControl.getName());
        
        return false;
    }
    
    protected boolean haveAccessOnPage(AutositeUser user, AccessControl accessControl, String  pageAlias){
        
        if (pageAlias == null) return false;

        pageAlias = pageAlias.trim().toUpperCase();
        
        SystemRole roleForThis = accessControl.getByActionPageAlias(pageAlias.trim());
        
        if ( user == null ) {
            if ( roleForThis.level() == AccessDef.SystemRole.Anonymous.level() ) {
                m_logger.info("ACCESS-PASS: Anonymous access granted to {} PAGE {} ", accessControl.getName(), pageAlias);
                return true;
            } else {
                m_logger.info("ACCESS-EXCEPTION: No user/Anonymous PAGE {} level {}", pageAlias, roleForThis.level()+"");
                return false;
            }
        }
        
        if (user.getUserType() == Constants.UserSuperAdmin ) {
            m_logger.info("ACCESS-PASS: SUPER User has access " + user.getUsername() + " access " + pageAlias + " level " + roleForThis.level() + " userLevel " + user.getUserType() + " actionName " + accessControl.getName());
            return true;
        }

        m_logger.info("Checking Access for " + user.getUsername() + "(" + user.getUserType() + ") against " + accessControl + " action " + pageAlias);
        if ( roleForThis.level() <= user.getUserType()) {
            m_logger.warn("ACCESS-PASS: User has access " + user.getUsername() + " access " + pageAlias+ " level " + roleForThis.level() + " userLevel " + user.getUserType() + " actionName " + accessControl.getName());
            return true;
        }
        
        m_logger.warn("ACCESS-EXCEPTION: User has NO access " + user.getUsername() + " access " + pageAlias + " level " + roleForThis.level() + " userLevel " + user.getUserType() + " actionName " + accessControl.getName());
        
        return false;
    }
    
    
    // This will be default in case there no default defined in config file
    // This will be overriden by generated method in sub classes. 
    protected SystemRole getDefaultSystemRole(){
        return AccessDef.SystemRole.SiteAdmin;
    }
    

    private void initializeSystemRolesBasedAccessControl(){
        
        m_accessControl = getDeafultAccessControl(getActionName());
        initializeSystemRolesBasedAccessControl(m_accessControl, null);
        
        String sitesToLoadAccessConfig = PropertiesLoader.getInstance().getProperty("app.cfg.accessconfig_sites");
        
        if ( sitesToLoadAccessConfig != null) {
            String sites[] = sitesToLoadAccessConfig.split(",");

            for (int i = 0; i < sites.length; i++) {
                String siteName = sites[i];
                
                AccessControl accessControl = getDeafultAccessControl(getActionName());
                if ( initializeSystemRolesBasedAccessControl(accessControl, siteName) )
                    m_accessControlBySite.put(siteName, accessControl);
            }
        }
    }

    // false, if accessControl not loaded for this action
    private boolean initializeSystemRolesBasedAccessControl(AccessControl accessControl, String site){
        
        if ( !AccessConfigManager.getInstance(site).hasForAction(getActionName())) {
            m_logger.info(" No access control for " + getActionName());
            return false;
        }
        
        // System Roles related Access Control 
        m_logger.info("===================== initializeSystemRolesBasedAccessControl for {} Action {} ==================================", site, getActionName());
    
        // 1. check Default actions which applies to all. then apply to all. 
        //=================================================================================================================================
        SystemRole defaultRole = AccessConfigManager.getInstance(site).getSystemRole(getActionName(), AccessDef.ActionType.Default, getDefaultSystemRole());
        
        if ( defaultRole != null ) 
            accessControl.setDefaultForType(defaultRole);
        m_logger.info(" DEFAULT ACCESS CONTROL  for Ttype---> " + defaultRole);
        
        List<AccessDef.ActionType> typesDefined = AccessConfigManager.getInstance(site).getDefinedTypesForAction(getActionName());
        for (AccessDef.ActionType t : typesDefined) {
            AccessDef.SystemRole role = AccessConfigManager.getInstance(site).getSystemRole(getActionName(), t, defaultRole);
            if ( role != null ) { 
                accessControl.setByActionType(t, role);
                m_logger.info(" CONFIG ACCESS CONTROL By Type" + t + " ---> " + role);
            }
        }
        
        //=================================================================================================================================
        SystemRole defaultCmdRole = AccessConfigManager.getInstance(site).getSystemRoleByCommand(getActionName(), AccessConfigManager.DEFAULT_CMD, getDefaultSystemRole());
        
        if ( defaultCmdRole != null ) 
            accessControl.setDefaultForCmd(defaultCmdRole);
        m_logger.info(" DEFAULT ACCESS CONTROL  for defaultCmdRole---> " + defaultCmdRole);
        
        // page Alias
        List<String> cmdsDefined = AccessConfigManager.getInstance(site).getConfiguredCommandsForAction(getActionName());
        for (String c : cmdsDefined) {
            AccessDef.SystemRole role = AccessConfigManager.getInstance(site).getSystemRoleByCommand(getActionName(), c, defaultRole);
            if ( role != null ) { 
                accessControl.setByActionCmd(c, role);
                m_logger.info(" CONFIG ACCESS CONTROL By Cmd" + c + " ---> " + role);
            }
        }

        
        //=================================================================================================================================
        SystemRole defaultPageRole = AccessConfigManager.getInstance(site).getSystemRoleByPage(getActionName(), AccessConfigManager.DEFAULT_PAGEALIAS, getDefaultSystemRole());
        
        if ( defaultPageRole != null ) 
            accessControl.setDefaultForCmd(defaultPageRole);
        m_logger.info(" DEFAULT ACCESS CONTROL  for defaultPageRole ---> " + defaultPageRole);
        
        // page Alias
        List<String> pagesDefined = AccessConfigManager.getInstance(site).getConfiguredPagesForAction(getActionName());
        for (String c : pagesDefined) {
            AccessDef.SystemRole role = AccessConfigManager.getInstance(site).getSystemRoleByPage(getActionName(), c, defaultRole);
            if ( role != null ) { 
                accessControl.setByActionPageAlias(c, role);
                m_logger.info(" CONFIG ACCESS CONTROL By Page" + c + " ---> " + role);
            }
        }
        
        m_logger.debug("ACCESS CONTROL FOR " + accessControl);
        m_logger.info("................................. END initializeSystemRolesBasedAccessControl for {} ........................................", getActionName());
        
        return true;
    }    
    
}
