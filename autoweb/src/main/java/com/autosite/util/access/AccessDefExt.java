package com.autosite.util.access;

import com.autosite.util.access.AccessDef.SystemRole;

public class AccessDefExt {

    
    public enum SystemRoleExt implements SystemRoleInterface {
        Anonymous(0),
        
        User(2),
        SiteAdmin(11),
        Super(99);

        private int _level;
        private SystemRoleExt(int l) {
                this._level = l;
        }
        public int level(){
            return _level;
        }
        
    };
    
}
