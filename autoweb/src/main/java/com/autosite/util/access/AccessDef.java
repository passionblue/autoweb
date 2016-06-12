package com.autosite.util.access;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccessDef {

    // System defined roles to control detail resources like jsp page, action, ajax and so on. 
    // created by sourcegen and can be defined from file loading.
    
    // SYNCH WITH Constants.UserXXX number
    
//    public final static int UserAnonymous = 0;
//    public final static int UserSiteSubscriber = 1;
//    public final static int UserSiteUser = 2;
//    public final static int UserSiteAdmin = 11;
//    
//    public final static int UserSuperUser = 98;
//    public final static int UserSuperAdmin = 99;
    
    public static class SystemRole  {
        
        private static Map<String, SystemRole> s_defined = new ConcurrentHashMap(100);

        public final static SystemRole Anonymous = new SystemRole("Anonymous",0);
        public final static SystemRole User = new SystemRole("User",2);
        public final static SystemRole SiteAdmin = new SystemRole("SiteAdmin",11);
        public final static SystemRole Super = new SystemRole("Super",99);
        
        private int _level; // same as type of UserObject
        private String _name;
        
        public SystemRole(String name, int lvl) {
            this._level = lvl;
            this._name = name;

            if ( !s_defined.containsKey(name)) {
                s_defined.put(name, this);
            }
        }
        
        public int level(){
            return _level;
        }
        public String name(){
            return _name;
        }

        public String toString(){
            return "SystemRole:"+ _name + "(" + _level + ")";
        }
        
        public static SystemRole valueOf(String name){
            return  s_defined.get(name);
        }
        
    };
    
    // Action type
    public enum ActionType {
        Default(0),
        Read(1),
        Update(2),
        PageAccess(3),
        PageRead(4);
        
        private int _val;
        
        private ActionType(int value) {
                this._val = value;
        }
        public int val(){
            return _val;
        }
    };
}
