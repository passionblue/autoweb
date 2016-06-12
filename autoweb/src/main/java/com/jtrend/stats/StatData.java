package com.jtrend.stats;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatData {

    protected long m_statId;
    protected Map m_httpRequestParams;
    protected Map m_headerAttributes;
    
    protected String m_rpcid;
    protected String m_actionName;
    protected String m_cookieUser;
    protected String m_loggedinUser;
    protected String m_sessionId;
    
    protected boolean m_loginBefore = false;
    protected boolean m_loginNow = false;
    protected boolean m_login = false;
    protected boolean m_ajax = false;
    protected boolean m_isRobot = false;
    protected boolean m_blocked = false;
    protected boolean m_pageLess = false;
    protected String m_blockReason; 

    //Processing results
    protected Exception m_excpetion;
    protected String m_pageAlias;
    protected long m_totalTime;
    protected boolean m_dropped = false;
    protected String m_dropReason; 
        
    protected String m_remoteAddress;
    protected String m_remoteHost;
    protected String m_headerHost;
    protected String m_requestUrl;
    protected String m_requestUri;
    protected String m_servletPath;
    protected String m_serverName;
    protected String m_queryString;
    protected String m_paramString;
    protected String m_paramKeys;
    
    protected String m_referer;
    protected String m_userAgent;
    protected String m_robotBrand;
    protected String m_method;
    
    protected Date m_statDate = new Date();
    
    
    public StatData() {
        m_headerAttributes = new HashMap();
    }
    
    public Map getHeaderAttributes() {
        return m_headerAttributes;
    }
    public void setHeaderAttributes(Map headerAttributes) {
        m_headerAttributes = headerAttributes;
    }
    public String getRemoteHost() {
        return m_remoteHost;
    }
    public void setRemoteHost(String remoteHost) {
        m_remoteHost = remoteHost;
    }
    public String getRequestUri() {
        return m_requestUri;
    }
    public void setRequestUri(String requestUri) {
        m_requestUri = requestUri;
    }
    public String getServerName() {
        return m_serverName;
    }
    public void setServerName(String serverName) {
        m_serverName = serverName;
    }
    public String getQueryString() {
        return m_queryString;
    }
    public void setQueryString(String queryString) {
        m_queryString = queryString;
    }
    public String getRequestUrl() {
        return m_requestUrl;
    }
    public void setRequestUrl(String requestUrl) {
        m_requestUrl = requestUrl;
    }
    public Map getHttpRequestParams() {
        return m_httpRequestParams;
    }
    public void setHttpRequestParams(Map httpRequestParams) {
        m_httpRequestParams = httpRequestParams;
    }
    public String getRemoteAddress() {
        return m_remoteAddress;
    }
    public void setRemoteAddress(String remoteAddress) {
        m_remoteAddress = remoteAddress;
    }
    public long getStatId() {
        return m_statId;
    }
    public void setStatId(long statId) {
        m_statId = statId;
    }
    public Map getHttpRequest() {
        return m_httpRequestParams;
    }
    public void setHttpRequest(Map requestParams) {
        m_httpRequestParams = requestParams;
    }
    public String getActionName() {
        return m_actionName;
    }
    public void setActionName(String actionName) {
        m_actionName = actionName;
    }
    public String getCookieUser() {
        return m_cookieUser;
    }
    public void setCookieUser(String cookieUser) {
        m_cookieUser = cookieUser;
    }
    public boolean isLoginBefore() {
        return m_loginBefore;
    }
    public void setLoginBefore(boolean loginBefore) {
        m_loginBefore = loginBefore;
    }
    public boolean isLoginNow() {
        return m_loginNow;
    }
    public void setLoginNow(boolean loginNow) {
        m_loginNow = loginNow;
    }
    public Exception getExcpetion() {
        return m_excpetion;
    }
    public void setExcpetion(Exception excpetion) {
        m_excpetion = excpetion;
    }
    public String getPageAlias() {
        return m_pageAlias;
    }
    public void setPageAlias(String pageAlias) {
        m_pageAlias = pageAlias;
    }
    public long getTotalTime() {
        return m_totalTime;
    }
    public void setTotalTime(long totalTime) {
        m_totalTime = totalTime;
    }
    
    public String toString() {
        return "";
    }
    public Date getStatDate() {
        return m_statDate;
    }
    public void setStatDate(Date statDate) {
        m_statDate = statDate;
    }

    public String getParamString() {
        return m_paramString;
    }

    public void setParamString(String paramString) {
        m_paramString = paramString;
    }

    public String getRpcid() {
        return m_rpcid;
    }

    public void setRpcid(String rpcid) {
        m_rpcid = rpcid;
    }

    public String getLoggedinUser() {
        return m_loggedinUser;
    }

    public void setLoggedinUser(String loggedinUser) {
        m_loggedinUser = loggedinUser;
    }

    public boolean isLogin() {
        return m_login;
    }

    public void setLogin(boolean login) {
        m_login = login;
    }

    public String getReferer() {
        return m_referer;
    }

    public void setReferer(String referer) {
        m_referer = referer;
    }

    public boolean isDropped() {
        return m_dropped;
    }

    public void setDropped(boolean dropped) {
        m_dropped = dropped;
    }

    public boolean isAjax() {
        return m_ajax;
    }

    public void setAjax(boolean ajax) {
        m_ajax = ajax;
    }

    public String getServletPath() {
        return m_servletPath;
    }

    public void setServletPath(String servletPath) {
        m_servletPath = servletPath;
    }

    public String getHeaderHost() {
        return m_headerHost;
    }

    public void setHeaderHost(String headerHost) {
        m_headerHost = headerHost;
    }

    public boolean isRobot() {
        return m_isRobot;
    }

    public void setRobot(boolean isRobot) {
        m_isRobot = isRobot;
    }

    public String getDropReason() {
        return m_dropReason;
    }

    public void setDropReason(String dropReason) {
        m_dropReason = dropReason;
    }

    public String getRobotBrand() {
        return m_robotBrand;
    }

    public void setRobotBrand(String robotBrand) {
        m_robotBrand = robotBrand;
    }

    public String getSessionId() {
        return m_sessionId;
    }

    public void setSessionId(String sessionId) {
        m_sessionId = sessionId;
    }

    public boolean isBlocked() {
        return m_blocked;
    }

    public void setBlocked(boolean blocked) {
        m_blocked = blocked;
    }

    public String getBlockReason() {
        return m_blockReason;
    }

    public void setBlockReason(String blockReason) {
        m_blockReason = blockReason;
    }

    public boolean isPageLess() {
        return m_pageLess;
    }

    public void setPageLess(boolean pageLess) {
        m_pageLess = pageLess;
    }

    public String getParamKeys() {
        return m_paramKeys;
    }

    public void setParamKeys(String paramKeys) {
        m_paramKeys = paramKeys;
    }

    public String getUserAgent() {
        return m_userAgent;
    }

    public void setUserAgent(String userAgent) {
        m_userAgent = userAgent;
    }

    public String getMethod() {
        return m_method;
    }

    public void setMethod(String method) {
        m_method = method;
    }    

    
     
}
