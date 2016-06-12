package com.autosite.util;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.autosite.Constants;
import com.autosite.db.AutositeUser;
import com.autosite.db.Site;
import com.autosite.ds.AutositeUserDS;
import com.autosite.session.AutositeSessionContext;
import com.jtrend.session.SessionContext;

public class UserUtil {

    public static AutositeUser getUserFromSession(HttpSession session){
        
        AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute(SessionContext.getSessionKey());

        if (sessionContext.isLogin())
            return sessionContext.getUserObject();
        
        return null;
    }
   
    
    
    public static AutositeUser findUser(Site site, String username){
        AutositeUser user = AutositeUserDS.getInstance().getBySiteIdUsername(site.getId(), username);
        return user;
    }
    
    public static AutositeUser getSuperAdminObject(){
        AutositeUser ret = new AutositeUser();
        
        ret.setUsername("superadmin");
        ret.setUserType(Constants.UserSuperAdmin);

        return ret;
    }
    
    public static AutositeUser dbAuthenticate(Site site, String username, String password) {
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
    
    public static void setLogInToSession(HttpSession session, AutositeUser user){
        
        if (user == null) {
            setLogOffToSession(session);
            return;
        }
        
        AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute(SessionContext.getSessionKey());

        sessionContext.setLogin(true);
        sessionContext.setUsername(user.getUsername());
        sessionContext.setUserObject(user);
        
        m_logger.info("User " + user.getUsername() + " logged in as type=" + user.getUserType());

    }
    
    public static void setLogOffToSession(HttpSession session){
        AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute(SessionContext.getSessionKey());
        AutositeUser userLoggedIn = sessionContext.getUserObject();
        sessionContext.setLogin(false);
        sessionContext.setUsername(null);
        sessionContext.setUserObject(null);
        
        if ( userLoggedIn != null)
            m_logger.info("User " + userLoggedIn.getUsername() + " logged OFF as type=" + userLoggedIn.getUserType());
        else
            m_logger.info("User UNKNOWN(NULL) logged off");

    }    
    

    public static boolean isSuperAdmin(HttpSession session) {
        
        AutositeSessionContext  context = (AutositeSessionContext) session.getAttribute(SessionContext.getSessionKey());
        if (context != null && context.isSuperAdmin()) return true;
        return false;
    }

    public static boolean isSiteAdmin(HttpSession session) {
        
        AutositeSessionContext  context = (AutositeSessionContext) session.getAttribute(SessionContext.getSessionKey());
        if (context != null && context.isSiteAdmin()) return true;
        return false;
    }
    
    
    public static void main(String[] args) {
        
    }
    private static Logger m_logger = Logger.getLogger(UserUtil.class);
}
