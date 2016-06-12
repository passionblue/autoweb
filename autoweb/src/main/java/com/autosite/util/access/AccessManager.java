package com.autosite.util.access;

import com.autosite.util.access.AccessDef.ActionType;
import com.autosite.util.access.AccessDef.SystemRole;

public interface AccessManager {

    SystemRole getSystemRoleByActionType ( String actionName, ActionType actionType, SystemRole defaultSystemRole);
    SystemRole getSystemRoleByActionType ( String actionGroup, String actionName, ActionType actionType, SystemRole defaultSystemRole);
    SystemRole getSystemRoleByActionType ( String actionName, String actionType, SystemRole defaultSystemRole);
    SystemRole getSystemRoleByActionType (  String actionGroup, String actionName, String actionType, SystemRole defaultSystemRole);
    SystemRole getSystemRoleByCommand ( String actionName, String cmd, SystemRole defaultSystemRole);
    SystemRole getSystemRoleByCommand (  String actionGroup, String actionName, String cmd, SystemRole defaultSystemRole);
    SystemRole getSystemRoleByPage ( String actionName, String page, SystemRole defaultSystemRole);
    SystemRole getSystemRoleByPage (  String actionGroup, String actionName, String page, SystemRole defaultSystemRole);
    SystemRole getSystemRoleByResource ( String actionName, String resourceName, SystemRole defaultSystemRole);
    SystemRole getSystemRoleByResource (  String actionGroup, String actionName, String resourceName, SystemRole defaultSystemRole);

    boolean hasMatchForPage(String artifact);
    
}
