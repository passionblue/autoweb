package com.autosite.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import com.autosite.Constants;
import com.autosite.db.AutositeRemoteDevice;
import com.autosite.db.AutositeSessionContextEntity;
import com.autosite.db.AutositeUser;
import com.jtrend.session.SessionContext;

public class AutositeSessionContext extends SessionContext {

    protected AutositeUser          m_userObject;
    protected AutositeRemoteDevice  m_remoteDevice;// For the session
    protected SessionWrapper        m_sessionWrapper;
    
    protected Map m_loginContextPool = new ConcurrentHashMap<String, AutositeLoginContext>();
    
    
    public AutositeSessionContext(boolean persisted, String serial){
        super();
        setSerial(serial);
        setPersisted(persisted);
    }

    public AutositeSessionContext(String str){
        super(str);
    }
    
    public SessionWrapper getSessionWrapper() {
        return m_sessionWrapper;
    }

    public void setSessionWrapper(SessionWrapper sessionWrapper) {
        m_sessionWrapper = sessionWrapper;
    }

    public AutositeUser getUserObject() {
        if ( isLogin() )
            return m_userObject;
        else
            return null;
    }

    public void setUserObject(AutositeUser usrObject) {
        m_userObject = usrObject;
    }

    public int getUserType() {
        if (m_userObject != null)
            return m_userObject.getUserType();

        return Constants.UserAnonymous;
    }

    public String getCanonicalUserId() {
        if (m_userObject != null)
            return "" + m_userObject.getId();
        return null;
    }

    public boolean isSuperAdmin() {
        if (!m_login)
            return false;

        if (m_userObject == null)
            return false;

        if (getUserType() == Constants.UserSuperAdmin)
            return true;

        return false;
    }

    public boolean isSiteAdmin() {
        if (!m_login)
            return false;

        if (m_userObject == null)
            return false;

        if (getUserType() == Constants.UserSuperAdmin || getUserType() == Constants.UserSiteAdmin)
            return true;

        return false;
    }

    public boolean isSiteUser() {
        if (!m_login)
            return false;

        if (m_userObject == null)
            return false;

        if (getUserType() == Constants.UserSuperAdmin || getUserType() == Constants.UserSiteAdmin || getUserType() == Constants.UserSiteUser)
            return true;

        return false;
    }

    public boolean isSuperAdminLogin(){
        if ( !isLogin()) return false;
        if ( !isSuperAdmin() ) return false;
        return true;
    }
    
    public boolean isSiteAdminLogin(){
        if ( !isLogin()) return false;
        if ( !isSiteAdmin() ) return false;
        return true;
    }
    
    
    public AutositeRemoteDevice getRemoteDevice() {
        return m_remoteDevice;
    }

    public void setRemoteDevice(AutositeRemoteDevice remoteDevice) {
        m_remoteDevice = remoteDevice;
    }
    
    
    
    static public AutositeSessionContext create( HttpSession session) {
        return create("", session);
    }    

    static public AutositeSessionContext create(String str, HttpSession session) {
        AutositeSessionContext c = new AutositeSessionContext(str);
        c.setSessionId(session.getId());
        return c;
    }

    static public AutositeSessionContext create(boolean persisted, String serial) {
        AutositeSessionContext c = new AutositeSessionContext(persisted, serial);
        return c;
    }    
    
    static public AutositeSessionContextEntity convertFrom(AutositeSessionContext sessionContext){
        AutositeSessionContextEntity entity = new AutositeSessionContextEntity();
        
        entity.setSerial(sessionContext.getSerial());
        
        return entity; 
    }
}
