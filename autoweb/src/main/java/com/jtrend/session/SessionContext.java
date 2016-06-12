/*
 * Created on Dec 31, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.jtrend.session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jtrend.util.WebUtil;

// 
public class SessionContext extends AbstractSessionStorable
{
    
    public static final int SESSION_TYPE_NOTSET = -1;
    public static final int SESSION_TYPE_DEFAULT = 0; //PC mostly
    public static final int SESSION_TYPE_MOBILE_PHONE = 1; // by iphone browser
    public static final int SESSION_TYPE_MOBILE_TABLET = 2; // by ipad browser
    public static final int SESSION_TYPE_MOBILE_PAGELESS = 3; // mostly for mobile applications

    
    public static final int SESSION_STATUS_NOT_INIT = -1;
    public static final int SESSION_STATUS_NOT_INVALID_LOGIN_REQUIRED = -2;
    public static final int SESSION_STATUS_NORMAL = 0;
    
    private static Logger m_logger = LoggerFactory.getLogger(SessionContext.class);
    
    static private Map sessionIdToContext = new ConcurrentHashMap();
    static private Map sessionContextSerialToContext = new ConcurrentHashMap();
    
    
	protected String m_serial;
	protected boolean m_login;
	protected String m_username;
	
	protected long m_loginTime;
	protected long m_lastAccess;
	
	protected String m_server;
	protected String m_remoteIp;
    protected String m_sessionId;

    protected Map m_props;
    protected List m_requestHandls = new java.util.concurrent.CopyOnWriteArrayList();
    
    protected String m_sourceDeviceId; // if there is. 
    protected int m_defaultSessionType  = SESSION_TYPE_DEFAULT; // PC/Mobile/Tablet
    protected int m_selectedSessionType = SESSION_TYPE_NOTSET; // PC/Mobile/Tablet
    
    protected int m_sessionStatus;
    
    protected boolean m_persisted;

    public SessionContext() {
        this("DEFAULT");
    }

    public SessionContext(String str)
    {
        super();
        DateFormat dt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        m_props  = new ConcurrentHashMap();
        m_serial = UUID.randomUUID() + ":" + str +":"+ dt.format(new Date());  
    }
    
    
    
	public boolean isPersisted() {
        return m_persisted;
    }

    public void setPersisted(boolean persisted) {
        m_persisted = persisted;
    }

    public Date getLoginTime() {
        return new Date(m_loginTime);
    }

    public void setLoginTime(Date loginTime) {
        m_loginTime = loginTime.getTime();
    }

    public String getUsername() {
        return m_username;
    }

    public void setUsername(String username) {
        m_username = username;
    }
    
    public String getCanonicalUserId(){
        return ""+m_username.hashCode(); // Wrong. sub class should return unique id
    }
	
    static public String getSessionKey()
    {
        return "k_session_context";
    }
    
	public boolean isLogin()
	{
		return m_login;
	}

	public void setLogin(boolean login)
	{
		m_login = login;
		if (login)
		    m_loginTime = System.currentTimeMillis();
	}

	public String getSerial()
	{
		return m_serial;
	}

    public long getLastAccess() {
        return m_lastAccess;
    }

    public void resetLastAccess() {
        m_lastAccess = System.currentTimeMillis();
    }
    
    public String toString() {
        return "type=[" + m_defaultSessionType + "/" + m_selectedSessionType +"]|serial=" + m_serial +"|time=" + m_loginTime + "|login=" + m_login+"|last=" + m_lastAccess+"|user=" + m_username+ "|server=" + m_server + "|remote=" + m_remoteIp;
    }
    
    public Object getProps(String key) {
        return m_props.get(key);
    }
    
    public void putProps(String key, Object obj) {
        m_props.put(key, obj);
    }

    public String getServer() {
        return m_server;
    }

    public void setServer(String server) {
        m_server = server;
    }

    public String getRemoteIp() {
        return m_remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        m_remoteIp = remoteIp;
    }

    public static Map getSessionIdToContext() {
        return sessionIdToContext;
    }

    public static void setSessionIdToContext(Map sessionIdToContext) {
        SessionContext.sessionIdToContext = sessionIdToContext;
    }

    public Map getProps() {
        return m_props;
    }

    public void setProps(Map props) {
        m_props = props;
    }

    public void setSerial(String serial) {
        m_serial = serial;
    }

    public void setLoginTime(long loginTime) {
        m_loginTime = loginTime;
    }

    public void setLastAccess(long lastAccess) {
        m_lastAccess = lastAccess;
    }
    
    public String getSessionId() {
        return m_sessionId;
    }

    public void setSessionId(String sessionId) {
        m_sessionId = sessionId;
    }

    public void setSessionInfo(HttpSession session) {

    
    }

    public void addRequestHandle(SessionRequestHandle handle){
        if (handle != null) {
            m_requestHandls.add(handle);
        }
    }

    public List getRequestHandles(){
        return new ArrayList(m_requestHandls);
    }

    
    
    public String getSourceDeviceId() {
        return m_sourceDeviceId;
    }

    public void setSourceDeviceId(String sourceDeviceId) {
        m_sourceDeviceId = sourceDeviceId;
    }

    public int getDefaultSessionType() {
        return m_defaultSessionType;
    }

    public void setDefaultSessionType(int defaultSessionType) {
        m_defaultSessionType = defaultSessionType;
    }

    public int getSelectedSessionType() {
        return m_selectedSessionType;
    }

    public void setSelectedSessionType(int selectedSessionType) {
        m_selectedSessionType = selectedSessionType;
    }
    
    /**
     * this is derived from both of 
     */
    public int getSessionType(){
        
        if ( m_selectedSessionType != SESSION_TYPE_NOTSET)
            return m_selectedSessionType;
        
        return m_defaultSessionType;
    }
    
    
    
    
    //==========================================================================================================
    


    public int getSessionStatus() {
        return m_sessionStatus;
    }

    public void setSessionStatus(int sessionStatus) {
        m_sessionStatus = sessionStatus;
    }

    static public SessionContext create(HttpSession session)
    {
        SessionContext ctx = new SessionContext();
        ctx.setSessionId(session.getId());
        registerSessionContext(session, ctx);
        return ctx;
    }

    static public SessionContext findBySerial(String serial){
        SessionContext context =  (SessionContext) sessionContextSerialToContext.get(serial);
        return context;
    }

    //==========================================================================================================

    static public void registerSessionContext(HttpSession session, SessionContext sessionCtx)
    {
    
        if ( sessionIdToContext.containsKey(sessionCtx.getSerial()))
                throw new RuntimeException("Duplicate session context serial to register " + sessionCtx.getSerial());
        
        //TODO duplicate exception
        sessionIdToContext.put(session.getId(), sessionCtx);
        sessionContextSerialToContext.put(sessionCtx.getSerial(), sessionCtx);
        
        m_logger.debug("Registerd Context " + sessionCtx.getSerial() + " for " + session.getId() + " " + new Date());
    
    }
    
    static public List getSessionContexts(){
        return new ArrayList(sessionContextSerialToContext.values());
    }

    // called when the new session is created. 
    static public int getSessionContextTypeForRequest(HttpServletRequest request){
        
        if ( WebUtil.isTablet(request))
            return SessionContext.SESSION_TYPE_MOBILE_TABLET;
        
        if ( WebUtil.isMobilePhone(request))
            return SessionContext.SESSION_TYPE_MOBILE_PHONE;
        
        //PageLess session type is only set at PagelessSessionManageAction.ex() when pageless session request comes in. 
        return SessionContext.SESSION_TYPE_DEFAULT;
    }
    
    
    public static boolean isPagelessSession(SessionContext sessionContext){
        
        return  sessionContext.getSessionType() == SESSION_TYPE_MOBILE_PHONE || 
                sessionContext.getSessionType() == SESSION_TYPE_MOBILE_TABLET || 
                sessionContext.getSessionType() == SESSION_TYPE_MOBILE_PAGELESS
                ;
    }
    
}
