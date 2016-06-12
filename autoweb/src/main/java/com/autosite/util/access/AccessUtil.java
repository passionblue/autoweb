package com.autosite.util.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.Constants;
import com.autosite.db.AutositeUser;
import com.autosite.util.access.AccessDef.SystemRole;

public class AccessUtil {
    private static Logger m_logger = LoggerFactory.getLogger(AccessUtil.class);
    
    public static boolean hasAccess(AutositeUser user, SystemRole roleForThis){
        if ( user == null ) {
            if ( roleForThis.level() == AccessDef.SystemRole.Anonymous.level() ) {
                m_logger.info("ACCESS-PASS: Not logged in but Anonymous roles artifact ");
                return true;
            } else {
                m_logger.info("ACCESS-EXCEPTION: Not logged in but Not Anonymous roles artifact " + roleForThis);
                return false;
            }
        }
        
        if (user.getUserType() == Constants.UserSuperAdmin ) {
            m_logger.info("ACCESS-PASS: SUPER User has access " + user.getUsername() + " access " + roleForThis);
            return true;
        }

        m_logger.info("Checking Access for " + user.getUsername() + "(" + user.getUserType() + ") against " + roleForThis);
        if ( roleForThis.level() <= user.getUserType()) {
            m_logger.debug("User has access " + user.getUsername() + " access level " + user.getUserType()  + " against " + roleForThis.level());
            return true;
        }
        
        m_logger.warn("User has NO access " + user.getUsername() + " access level " + user.getUserType()  + " against " + roleForThis.level());
        
        return false;

    }
}
