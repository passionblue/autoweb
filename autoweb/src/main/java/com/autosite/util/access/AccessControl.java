package com.autosite.util.access;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.autosite.util.access.AccessDef.ActionType;
import com.autosite.util.access.AccessDef.SystemRole;

public class AccessControl {
    
    protected String m_ownerName;
    
    protected SystemRole defaultCmdRole = null;
    protected SystemRole defaultPageRole = null;
    
    protected SystemRole m_accessArray[] = new SystemRole[100]; //Idx is actionType Number
    protected Map<String, SystemRole>   m_accessByCmd   = new ConcurrentHashMap(); 
    protected Map<String, SystemRole>   m_accessByPageAlias   = new ConcurrentHashMap(); 

    public AccessControl(String owner, SystemRole role ) {
        m_ownerName = owner;
        setDefaultForType(role);
        setDefaultForCmd(role);
        setDefaultForPageAlias(role);
    }

    public void setDefaultForType(SystemRole role){
        for (int i = 0; i < m_accessArray.length; i++) {
            m_accessArray[i] = role;
        }
//        defaultRole = role;
    }

    public void setDefaultForCmd(SystemRole role){
        defaultCmdRole = role;
    }

    public void setDefaultForPageAlias(SystemRole role){
        defaultPageRole = role;
    }

    
    
    public void setByActionType(ActionType  actionType, SystemRole role){
        m_accessArray[actionType.val()] = role;
    }
    
    public SystemRole getByActionType(ActionType  actionType){
        return m_accessArray[actionType.val()];
    }

    public void setByActionCmd(String  cmd, SystemRole role){
        m_accessByCmd.put(cmd, role);
    }
    
    public SystemRole getByActionCmd(String  cmd){
        if ( cmd == null || !m_accessByCmd.containsKey(cmd))
            return defaultCmdRole;
        return m_accessByCmd.get(cmd);
    }
    
    public void setByActionPageAlias(String  pageAlias, SystemRole role){
        m_accessByPageAlias.put(pageAlias, role);
    }
    
    public SystemRole getByActionPageAlias(String  pageAlias){
        if ( pageAlias == null || !m_accessByPageAlias.containsKey(pageAlias))
            return defaultPageRole;
        return m_accessByPageAlias.get(pageAlias);
    }
    
    
    public SystemRole getUpdate() {
        return getByActionType(ActionType.Update);
    }

    public SystemRole getRead() {
        return getByActionType(ActionType.Read);
    }

    public SystemRole getPageAccess() {
        return getByActionType(ActionType.PageAccess);
    }

    public SystemRole getPageRead() {
        return getByActionType(ActionType.PageRead);
    }
    
    public String getName(){
        return m_ownerName;
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(m_ownerName).append("'s AccessControl //TODO :");
        builder.append(0).append("-").append(m_accessArray[0].name());
        builder.append(1).append("-").append(m_accessArray[1].name());
        builder.append(2).append("-").append(m_accessArray[2].name());
        builder.append(3).append("-").append(m_accessArray[3].name());
        builder.append(4).append("-").append(m_accessArray[4].name());
        return builder.toString();
    }
    
}
