package com.autosite.struts.action.core;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.Constants;
import com.autosite.db.AutositeUser;
import com.autosite.db.Site;
import com.autosite.session.AutositeSessionContext;
import com.autosite.util.UserUtil;
import com.autosite.util.access.AccessConfigManager2;
import com.autosite.util.access.AccessConfigManager3;
import com.autosite.util.access.AccessControl;
import com.autosite.util.access.AccessDef;
import com.autosite.util.access.AccessDef.ActionType;
import com.autosite.util.access.AccessDef.SystemRole;
import com.autosite.util.access.AccessManager;
import com.jtrend.session.SessionContext;

public class AutositeAccessAction extends AutositeBaseAction {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeAccessAction.class);
    
    protected AccessControl m_accessControl; //Action control for the Action
    protected Map m_accessControlBySite = new ConcurrentHashMap();
    protected AccessManager m_accessManager;

    public AutositeAccessAction() {
        super();
        m_accessManager = AccessConfigManager3.getInstance();
    }
    
    protected boolean setSuperLogin(HttpSession session) throws Exception{
        AutositeUser user = UserUtil.getSuperAdminObject();
        return setLogin(session, user);
    }

    protected boolean setLogin(HttpSession session, Object u) throws Exception{
        return setLogin(session, u, false);
    }    
    
    // if forceLoginWithCurrentWithoutException is set, setLogin() will just process login even this session is already logged in before. 
    // If not, it will go through some extra checking. 
    protected boolean setLogin(HttpSession session, Object u, boolean forceLoginWithCurrentWithoutException) throws Exception{
        
        AutositeUser user = (AutositeUser) u;
        String username = user.getUsername();
        
        AutositeSessionContext context = (AutositeSessionContext)getSessionContext(session); 
        
        if ( context.isLogin() && !forceLoginWithCurrentWithoutException ){

            if (!username.equals(context.getUsername())){
                AutositeUser existinguser = context.getUserObject();
                context.setLogin(false);
                context.setUsername(null);
                context.setUserObject(null);
                m_logger.error("This session alreay logged in with different use " + context.getUsername());
                throw new Exception("Session alreay logged in with user " + existinguser + " but attempted with " + username);
            }

            m_logger.info("User " + username + " already logged in");
            
        } else {
            context.setLogin(true);
            context.setUsername(username);
            context.setUserObject(user);
            context.setLoginTime(new Date());
        }
        
        m_logger.info("user " + username + " logged in ");
        
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
        
        
        AccessManager siteSpecific = AccessConfigManager3.getInstance(context.getServer());

        SystemRole roleForThis = null;
        
        if ( siteSpecific != null)
            roleForThis = siteSpecific.getSystemRoleByCommand(getActionGroupName(), getActionName(), cmd, getDefaultSystemRole());
        else 
            roleForThis = m_accessManager.getSystemRoleByCommand(getActionGroupName(),getActionName(), cmd, getDefaultSystemRole());
        
        boolean resultByDefault = userHasAccess(autoUser, roleForThis);
        
        return resultByDefault;
    }    

    protected boolean haveAccessToActionType(HttpSession session, ActionType actionType){

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser            autoUser = context.getUserObject();
        
        
        AccessManager siteSpecific = AccessConfigManager3.getInstance(context.getServer());

        SystemRole roleForThis = null;
        
        if ( siteSpecific != null)
            roleForThis = siteSpecific.getSystemRoleByActionType(getActionGroupName(), getActionName(), actionType, getDefaultSystemRole());
        else 
            roleForThis = m_accessManager.getSystemRoleByActionType(getActionGroupName(), getActionName(), actionType, getDefaultSystemRole());
        
        boolean resultByDefault = userHasAccess(autoUser, roleForThis);
        
        return resultByDefault;
    
    }
    
    protected boolean haveAccessToPage(HttpSession session, String pageAlias){

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser            autoUser = context.getUserObject();
        
        
        AccessManager siteSpecific = AccessConfigManager3.getInstance(context.getServer());

        SystemRole roleForThis = null;
        
        if ( siteSpecific != null)
            roleForThis = siteSpecific.getSystemRoleByPage(getActionGroupName(), getActionName(), pageAlias, getDefaultSystemRole());
        else 
            roleForThis = m_accessManager.getSystemRoleByPage(getActionGroupName(), getActionName(), pageAlias, getDefaultSystemRole());
        
        boolean resultByDefault = userHasAccess(autoUser, roleForThis);
        
        return resultByDefault;
    }    
    
    
    protected boolean haveAccessToUpdate(HttpSession session){
        return haveAccessToActionType(session, ActionType.Update);
    }

    protected boolean haveAccessToRead(HttpSession session){
        return haveAccessToActionType(session, ActionType.Read);
    }

    protected boolean haveAccessToPageAccess(HttpSession session){
        return haveAccessToActionType(session, ActionType.PageAccess);
    }
    
    
//    protected boolean haveAccess(HttpSession session, AccessControl accessControl){
//        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
//        AutositeUser            autoUser = context.getUserObject();
//        ActionType              actionType = ActionType.Update;
//        
//        return haveAccess(autoUser, accessControl, actionType);
//    }
    
    protected boolean userHasAccess(AutositeUser user, SystemRole roleForThis){

        if ( user == null ) {
            if ( roleForThis.level() == AccessDef.SystemRole.Anonymous.level() ) {
                m_logger.info("ACCESS-PASS: Not logged in but Anonymous roles artifact ");
                return true;
            } else {
                m_logger.info("**ACCESS-EXCEPTION**: Not logged in but Not Anonymous roles artifact " + roleForThis);
                return false;
            }
        }
        
        if (user.getUserType() == Constants.UserSuperAdmin ) {
            m_logger.info("ACCESS-PASS: SUPER User has access " + user.getUsername() + " access " + roleForThis);
            return true;
        }

        m_logger.info("Checking Access for " + user.getUsername() + "(" + user.getUserType() + ") against " + roleForThis);
        if ( roleForThis.level() <= user.getUserType()) {
            m_logger.debug("ACCESS-PASS:User has access " + user.getUsername() + " access level " + user.getUserType()  + " against " + roleForThis.level());
            return true;
        }
        
        m_logger.warn("**ACCESS-EXCEPTION**:User has NO access " + user.getUsername() + " access level " + user.getUserType()  + " against " + roleForThis.level());
        
        return false;
    }

    
    
    
    // This will be default in case there no default defined in config file
    // This will be overriden by generated method in sub classes. 
    protected SystemRole getDefaultSystemRole(){
        return AccessDef.SystemRole.SiteAdmin;
    }
    

//    private void initializeSystemRolesBasedAccessControl(){
//        
//        m_accessControl = getDeafultAccessControl(getActionName());
//        initializeSystemRolesBasedAccessControl(m_accessControl, null);
//        
//        String sitesToLoadAccessConfig = PropertiesLoader.getInstance().getProperty("app.cfg.accessconfig_sites");
//        
//        if ( sitesToLoadAccessConfig != null) {
//            String sites[] = sitesToLoadAccessConfig.split(",");
//
//            for (int i = 0; i < sites.length; i++) {
//                String siteName = sites[i];
//                
//                AccessControl accessControl = getDeafultAccessControl(getActionName());
//                if ( initializeSystemRolesBasedAccessControl(accessControl, siteName) )
//                    m_accessControlBySite.put(siteName, accessControl);
//            }
//        }
//    }
//
//    // false, if accessControl not loaded for this action
//    private boolean initializeSystemRolesBasedAccessControl(AccessControl accessControl, String site){
//        
//        if ( !AccessConfigManager.getInstance(site).hasForAction(getActionName())) {
//            m_logger.info(" No access control for " + getActionName());
//            return false;
//        }
//        
//        // System Roles related Access Control 
//        m_logger.info("===================== initializeSystemRolesBasedAccessControl for {} Action {} ==================================", site, getActionName());
//    
//        // 1. check Default actions which applies to all. then apply to all. 
//        //=================================================================================================================================
//        SystemRole defaultRole = AccessConfigManager.getInstance(site).getSystemRole(getActionName(), AccessDef.ActionType.Default, getDefaultSystemRole());
//        
//        if ( defaultRole != null ) 
//            accessControl.setDefaultForType(defaultRole);
//        m_logger.info(" DEFAULT ACCESS CONTROL  for Ttype---> " + defaultRole);
//        
//        List<AccessDef.ActionType> typesDefined = AccessConfigManager.getInstance(site).getDefinedTypesForAction(getActionName());
//        for (AccessDef.ActionType t : typesDefined) {
//            AccessDef.SystemRole role = AccessConfigManager.getInstance(site).getSystemRole(getActionName(), t, defaultRole);
//            if ( role != null ) { 
//                accessControl.setByActionType(t, role);
//                m_logger.info(" CONFIG ACCESS CONTROL By Type" + t + " ---> " + role);
//            }
//        }
//        
//        //=================================================================================================================================
//        SystemRole defaultCmdRole = AccessConfigManager.getInstance(site).getSystemRoleByCommand(getActionName(), AccessConfigManager.DEFAULT_CMD, getDefaultSystemRole());
//        
//        if ( defaultCmdRole != null ) 
//            accessControl.setDefaultForCmd(defaultCmdRole);
//        m_logger.info(" DEFAULT ACCESS CONTROL  for defaultCmdRole---> " + defaultCmdRole);
//        
//        // page Alias
//        List<String> cmdsDefined = AccessConfigManager.getInstance(site).getConfiguredCommandsForAction(getActionName());
//        for (String c : cmdsDefined) {
//            AccessDef.SystemRole role = AccessConfigManager.getInstance(site).getSystemRoleByCommand(getActionName(), c, defaultRole);
//            if ( role != null ) { 
//                accessControl.setByActionCmd(c, role);
//                m_logger.info(" CONFIG ACCESS CONTROL By Cmd" + c + " ---> " + role);
//            }
//        }
//
//        
//        //=================================================================================================================================
//        SystemRole defaultPageRole = AccessConfigManager.getInstance(site).getSystemRoleByPage(getActionName(), AccessConfigManager.DEFAULT_PAGEALIAS, getDefaultSystemRole());
//        
//        if ( defaultPageRole != null ) 
//            accessControl.setDefaultForCmd(defaultPageRole);
//        m_logger.info(" DEFAULT ACCESS CONTROL  for defaultPageRole ---> " + defaultPageRole);
//        
//        // page Alias
//        List<String> pagesDefined = AccessConfigManager.getInstance(site).getConfiguredPagesForAction(getActionName());
//        for (String c : pagesDefined) {
//            AccessDef.SystemRole role = AccessConfigManager.getInstance(site).getSystemRoleByPage(getActionName(), c, defaultRole);
//            if ( role != null ) { 
//                accessControl.setByActionPageAlias(c, role);
//                m_logger.info(" CONFIG ACCESS CONTROL By Page" + c + " ---> " + role);
//            }
//        }
//        
//        m_logger.debug("ACCESS CONTROL FOR " + accessControl);
//        m_logger.info("................................. END initializeSystemRolesBasedAccessControl for {} ........................................", getActionName());
//        
//        return true;
//    }    
    
    
    
    protected AutositeUser dbAuthenticate(Site site, String username, String password) {
        AutositeUser user = UserUtil.findUser(site, username);

        if ( user == null ) {
            m_logger.info("User object not found for " + username);
            return null;
        }
        
        if (password != null && password.equals(user.getPassword())) {
            return user;
        }
        else {
            return null;
        }
    }
}
