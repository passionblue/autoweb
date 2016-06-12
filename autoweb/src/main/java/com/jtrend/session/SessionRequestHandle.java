package com.jtrend.session;

import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jtrend.util.RequestUtil;
import com.jtrend.util.StringIDUtil;

public class SessionRequestHandle implements Serializable {

    public static final String REQ_HANDLE_KEY = "_reqhid";
    public static final String REQ_HANDLE_ID_KEY = "r_handle_id";
    
    private final String  m_handleId;
    
    private String  m_prevHandleId;
    private Map   m_attributes;
    private Map   m_parameters; // all params
    private Map   m_preservedParameters; // reserved
    
    private Map   m_followUpParameters; // all params
    
    private boolean     m_requestRedirected;
    private boolean     m_redirectReceived;
    private boolean     m_hasFollowupAction;
    
    private String      m_uri;
    private String      m_query;
    private long        m_timestamp;
    private PageView    m_pageView;
    private SessionRequestHandle m_redirectedToHandle;

    public SessionRequestHandle(HttpServletRequest request ){
        m_handleId = StringIDUtil.getSystemTimeStringId();
        request.setAttribute(REQ_HANDLE_ID_KEY, m_handleId);
        m_timestamp = System.currentTimeMillis();
        m_uri = request.getRequestURI();
        m_query = request.getQueryString();

        m_attributes = RequestUtil.getAttributes(request);
        m_preservedParameters =  RequestUtil.getPreservedParameters(request);
        m_parameters = RequestUtil.getParameters(request);
        //copyAllAttributes(request);
    }
    
    public boolean isRequestRedirected() {
        return m_requestRedirected;
    }

    public void setRequestRedirected(boolean requestRedirected) {
        m_requestRedirected = requestRedirected;
    }

    public String getId() {
        return m_handleId;
    }

    // Copy and save main attributes to handle. Mainly for the redirected request because attributes might be valid through the next request
    // e.g. error text
    public void copyKeyAttributesFrom(HttpServletRequest request){
        Enumeration enumer = request.getAttributeNames();
        while(enumer.hasMoreElements()){
            String name = (String) enumer.nextElement();
            m_attributes.put(name, request.getAttribute(name));
        }
        
        HttpSession session = request.getSession();
        
        if ( session.getAttribute("k_top_error_text") != null) m_attributes.put("k_top_error_text", session.getAttribute("k_top_error_text"));
        if ( session.getAttribute("k_top_text") != null) m_attributes.put("k_top_text", session.getAttribute("k_top_text"));
        if ( session.getAttribute("k_error_occurred") != null) m_attributes.put("k_error_occurred", session.getAttribute("k_error_occurred"));
    }

    public void copyKeyAttributesTo(HttpServletRequest request){
        
        if ( m_attributes.containsKey("k_top_error_text"))  request.setAttribute("k_top_error_text", m_attributes.get("k_top_error_text"));
        if ( m_attributes.containsKey("k_top_text"))        request.setAttribute("k_top_text",       m_attributes.get("k_top_text"));
        if ( m_attributes.containsKey("k_error_occurred"))  request.setAttribute("k_error_occurred", m_attributes.get("k_error_occurred"));

        HttpSession session = request.getSession();

        if ( m_attributes.containsKey("k_top_error_text"))  session.setAttribute("k_top_error_text", m_attributes.get("k_top_error_text"));
        if ( m_attributes.containsKey("k_top_text"))        session.setAttribute("k_top_text",       m_attributes.get("k_top_text"));
        if ( m_attributes.containsKey("k_error_occurred"))  session.setAttribute("k_error_occurred", m_attributes.get("k_error_occurred"));
    }

    
    
    public void saveAllParameters(HttpServletRequest request){
        Enumeration enumer = request.getParameterNames();
        while(enumer.hasMoreElements()){
            String name = (String) enumer.nextElement();
            m_attributes.put(name, request.getParameter(name));
        }
    }
    
    public void setAttribute(String key, Object val){
        m_attributes.put(key, val);
    }
    
    public Object getAttribute(String key){
        return m_attributes.get(key);
    }

    public Iterator getAttributeNames() {
        return m_attributes.keySet().iterator();
    }
    
    public boolean isRedirectReceived() {
        return m_redirectReceived;
    }

    public void setRedirectReceived(boolean redirectReceived) {
        m_redirectReceived = redirectReceived;
    }

    // Attache own handle id to url and returns the modified URL 
    // This is needed when the page tries to forward internally to redirect to other page. 
    // So that it will tells this is internally redirected.
    public String attachIdToUrl(String url){
        if ( !url.endsWith(".html") && url.indexOf("?") >= 0 ) {
            return url + "&" + REQ_HANDLE_KEY + "=" + m_handleId;
        }
        return url + "?" + REQ_HANDLE_KEY + "=" + m_handleId;
    }
    
    public String getUri() {
        return m_uri;
    }

    public void setUri(String uri) {
        m_uri = uri;
    }

    public String getQuery() {
        return m_query;
    }

    public void setQuery(String query) {
        m_query = query;
    }

    public long getTimestamp() {
        return m_timestamp;
    }

    public String toString() {
        return "hid=" + m_handleId   + " redirected=" + isRequestRedirected()+ ",time:" + new Date(getTimestamp()) + " URL: " + m_uri + "?" + m_query;// + " attributes=" + m_attributes;
    }

    public Map getParameters() {
        return m_parameters;
    }

    public Map getPreservedParameters() {
        return m_preservedParameters;
    }

    public String getPrevHandleId() {
        return m_prevHandleId;
    }

    public void setPrevHandleId(String prevHandleId) {
        m_prevHandleId = prevHandleId;
    }

    public PageView getPageView() {
        return m_pageView;
    }

    public void setPageView(PageView pageView) {
        m_pageView = pageView;
    }

    public Map getFollowUpParameters() {
        return m_followUpParameters;
    }

    public void setFollowUpParameters(Map followUpParameters) {
        m_followUpParameters = followUpParameters;
    }

    public boolean isHasFollowupAction() {
        return m_hasFollowupAction;
    }

    public void setHasFollowupAction(boolean hasFollowupAction) {
        m_hasFollowupAction = hasFollowupAction;
    }
    
    
    
}
