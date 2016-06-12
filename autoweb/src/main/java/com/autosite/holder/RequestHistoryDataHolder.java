package com.autosite.holder;

import java.sql.Timestamp;
import java.sql.Date;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.util.html.HtmlGenRow;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Set;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import com.jtrend.util.JSONValueFormatter;

import com.autosite.db.RequestHistory;


public class RequestHistoryDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("forwardSiteId", new Integer(0));
        fieldsMap.put("isDropped", new Integer(1));
        fieldsMap.put("isPageless", new Integer(2));
        fieldsMap.put("isLogin", new Integer(3));
        fieldsMap.put("isAjax", new Integer(4));
        fieldsMap.put("isRobot", new Integer(5));
        fieldsMap.put("userid", new Integer(6));
        fieldsMap.put("userAgent", new Integer(7));
        fieldsMap.put("refer", new Integer(8));
        fieldsMap.put("robot", new Integer(9));
        fieldsMap.put("remoteIp", new Integer(10));
        fieldsMap.put("siteUrl", new Integer(11));
        fieldsMap.put("uri", new Integer(12));
        fieldsMap.put("query", new Integer(13));
        fieldsMap.put("rpci", new Integer(14));
        fieldsMap.put("sessionId", new Integer(15));
        fieldsMap.put("timeCreated", new Integer(16));
    }


	RequestHistory m_requestHistory;

	public RequestHistoryDataHolder(RequestHistory _RequestHistory){
		m_requestHistory =  _RequestHistory; 	
	}

	public RequestHistoryDataHolder(){
		m_requestHistory =  new RequestHistory(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_requestHistory;
	}


	public long getId(){
		return 	m_requestHistory.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_requestHistory.getSiteId();
	}
	public void setSiteId(long sid){
		m_requestHistory.setSiteId(sid);
	}


	public long getForwardSiteId(){
		return m_requestHistory.getForwardSiteId();
	}
	public int getIsDropped(){
		return m_requestHistory.getIsDropped();
	}
	public int getIsPageless(){
		return m_requestHistory.getIsPageless();
	}
	public int getIsLogin(){
		return m_requestHistory.getIsLogin();
	}
	public int getIsAjax(){
		return m_requestHistory.getIsAjax();
	}
	public int getIsRobot(){
		return m_requestHistory.getIsRobot();
	}
	public String getUserid(){
		return m_requestHistory.getUserid();
	}
	public String getUserAgent(){
		return m_requestHistory.getUserAgent();
	}
	public String getRefer(){
		return m_requestHistory.getRefer();
	}
	public String getRobot(){
		return m_requestHistory.getRobot();
	}
	public String getRemoteIp(){
		return m_requestHistory.getRemoteIp();
	}
	public String getSiteUrl(){
		return m_requestHistory.getSiteUrl();
	}
	public String getUri(){
		return m_requestHistory.getUri();
	}
	public String getQuery(){
		return m_requestHistory.getQuery();
	}
	public String getRpci(){
		return m_requestHistory.getRpci();
	}
	public String getSessionId(){
		return m_requestHistory.getSessionId();
	}
	public Timestamp getTimeCreated(){
		return m_requestHistory.getTimeCreated();
	}

	public void setForwardSiteId(long _forwardSiteId ){
		m_requestHistory.setForwardSiteId(_forwardSiteId);
	}
	public void setIsDropped(int _isDropped ){
		m_requestHistory.setIsDropped(_isDropped);
	}
	public void setIsPageless(int _isPageless ){
		m_requestHistory.setIsPageless(_isPageless);
	}
	public void setIsLogin(int _isLogin ){
		m_requestHistory.setIsLogin(_isLogin);
	}
	public void setIsAjax(int _isAjax ){
		m_requestHistory.setIsAjax(_isAjax);
	}
	public void setIsRobot(int _isRobot ){
		m_requestHistory.setIsRobot(_isRobot);
	}
	public void setUserid(String _userid ){
		m_requestHistory.setUserid(_userid);
	}
	public void setUserAgent(String _userAgent ){
		m_requestHistory.setUserAgent(_userAgent);
	}
	public void setRefer(String _refer ){
		m_requestHistory.setRefer(_refer);
	}
	public void setRobot(String _robot ){
		m_requestHistory.setRobot(_robot);
	}
	public void setRemoteIp(String _remoteIp ){
		m_requestHistory.setRemoteIp(_remoteIp);
	}
	public void setSiteUrl(String _siteUrl ){
		m_requestHistory.setSiteUrl(_siteUrl);
	}
	public void setUri(String _uri ){
		m_requestHistory.setUri(_uri);
	}
	public void setQuery(String _query ){
		m_requestHistory.setQuery(_query);
	}
	public void setRpci(String _rpci ){
		m_requestHistory.setRpci(_rpci);
	}
	public void setSessionId(String _sessionId ){
		m_requestHistory.setSessionId(_sessionId);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_requestHistory.setTimeCreated(_timeCreated);
	}

	// For HtmlGenRow
    public int getColumnCount() {
		return fieldsMap.size();
    }
    public static List getFieldsName(){
        return new ArrayList(fieldsMap.keySet());
    }
    public String getColumnData(String col){
        if(fieldsMap.containsKey(col)){
            return getColumnData(fieldsMap.get(col).intValue());
        } else {
            return null;
        }
    }

    public String getColumnData(int colNum) {

        if ( colNum == 0) return String.valueOf(getForwardSiteId());
        if ( colNum == 1) return String.valueOf(getIsDropped());
        if ( colNum == 2) return String.valueOf(getIsPageless());
        if ( colNum == 3) return String.valueOf(getIsLogin());
        if ( colNum == 4) return String.valueOf(getIsAjax());
        if ( colNum == 5) return String.valueOf(getIsRobot());
        if ( colNum == 6) return getUserid() == null?"":getUserid().toString();
        if ( colNum == 7) return getUserAgent() == null?"":getUserAgent().toString();
        if ( colNum == 8) return getRefer() == null?"":getRefer().toString();
        if ( colNum == 9) return getRobot() == null?"":getRobot().toString();
        if ( colNum == 10) return getRemoteIp() == null?"":getRemoteIp().toString();
        if ( colNum == 11) return getSiteUrl() == null?"":getSiteUrl().toString();
        if ( colNum == 12) return getUri() == null?"":getUri().toString();
        if ( colNum == 13) return getQuery() == null?"":getQuery().toString();
        if ( colNum == 14) return getRpci() == null?"":getRpci().toString();
        if ( colNum == 15) return getSessionId() == null?"":getSessionId().toString();
        if ( colNum == 16) return getTimeCreated() == null?"":getTimeCreated().toString();
		return "";
    }


    public static JSONObject convertToJSON(RequestHistory _RequestHistory, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_RequestHistory, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(RequestHistory _RequestHistory, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _RequestHistory == null) return json;

        json.put("id", ""+_RequestHistory.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonForwardSiteId = new JSONObject();
		            jsonForwardSiteId.put("name", "forwardSiteId");
		            if ( useJsonDataType ) 
	                    jsonForwardSiteId.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getForwardSiteId()));
		            else
		                jsonForwardSiteId.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getForwardSiteId()));
		            array.put(jsonForwardSiteId);
		            JSONObject jsonIsDropped = new JSONObject();
		            jsonIsDropped.put("name", "isDropped");
		            if ( useJsonDataType ) 
	                    jsonIsDropped.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getIsDropped()));
		            else
		                jsonIsDropped.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getIsDropped()));
		            array.put(jsonIsDropped);
		            JSONObject jsonIsPageless = new JSONObject();
		            jsonIsPageless.put("name", "isPageless");
		            if ( useJsonDataType ) 
	                    jsonIsPageless.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getIsPageless()));
		            else
		                jsonIsPageless.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getIsPageless()));
		            array.put(jsonIsPageless);
		            JSONObject jsonIsLogin = new JSONObject();
		            jsonIsLogin.put("name", "isLogin");
		            if ( useJsonDataType ) 
	                    jsonIsLogin.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getIsLogin()));
		            else
		                jsonIsLogin.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getIsLogin()));
		            array.put(jsonIsLogin);
		            JSONObject jsonIsAjax = new JSONObject();
		            jsonIsAjax.put("name", "isAjax");
		            if ( useJsonDataType ) 
	                    jsonIsAjax.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getIsAjax()));
		            else
		                jsonIsAjax.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getIsAjax()));
		            array.put(jsonIsAjax);
		            JSONObject jsonIsRobot = new JSONObject();
		            jsonIsRobot.put("name", "isRobot");
		            if ( useJsonDataType ) 
	                    jsonIsRobot.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getIsRobot()));
		            else
		                jsonIsRobot.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getIsRobot()));
		            array.put(jsonIsRobot);
		            JSONObject jsonUserid = new JSONObject();
		            jsonUserid.put("name", "userid");
		            if ( useJsonDataType ) 
	                    jsonUserid.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getUserid()));
		            else
		                jsonUserid.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getUserid()));
		            array.put(jsonUserid);
		            JSONObject jsonUserAgent = new JSONObject();
		            jsonUserAgent.put("name", "userAgent");
		            if ( useJsonDataType ) 
	                    jsonUserAgent.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getUserAgent()));
		            else
		                jsonUserAgent.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getUserAgent()));
		            array.put(jsonUserAgent);
		            JSONObject jsonRefer = new JSONObject();
		            jsonRefer.put("name", "refer");
		            if ( useJsonDataType ) 
	                    jsonRefer.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getRefer()));
		            else
		                jsonRefer.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getRefer()));
		            array.put(jsonRefer);
		            JSONObject jsonRobot = new JSONObject();
		            jsonRobot.put("name", "robot");
		            if ( useJsonDataType ) 
	                    jsonRobot.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getRobot()));
		            else
		                jsonRobot.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getRobot()));
		            array.put(jsonRobot);
		            JSONObject jsonRemoteIp = new JSONObject();
		            jsonRemoteIp.put("name", "remoteIp");
		            if ( useJsonDataType ) 
	                    jsonRemoteIp.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getRemoteIp()));
		            else
		                jsonRemoteIp.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getRemoteIp()));
		            array.put(jsonRemoteIp);
		            JSONObject jsonSiteUrl = new JSONObject();
		            jsonSiteUrl.put("name", "siteUrl");
		            if ( useJsonDataType ) 
	                    jsonSiteUrl.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getSiteUrl()));
		            else
		                jsonSiteUrl.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getSiteUrl()));
		            array.put(jsonSiteUrl);
		            JSONObject jsonUri = new JSONObject();
		            jsonUri.put("name", "uri");
		            if ( useJsonDataType ) 
	                    jsonUri.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getUri()));
		            else
		                jsonUri.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getUri()));
		            array.put(jsonUri);
		            JSONObject jsonQuery = new JSONObject();
		            jsonQuery.put("name", "query");
		            if ( useJsonDataType ) 
	                    jsonQuery.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getQuery()));
		            else
		                jsonQuery.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getQuery()));
		            array.put(jsonQuery);
		            JSONObject jsonRpci = new JSONObject();
		            jsonRpci.put("name", "rpci");
		            if ( useJsonDataType ) 
	                    jsonRpci.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getRpci()));
		            else
		                jsonRpci.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getRpci()));
		            array.put(jsonRpci);
		            JSONObject jsonSessionId = new JSONObject();
		            jsonSessionId.put("name", "sessionId");
		            if ( useJsonDataType ) 
	                    jsonSessionId.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getSessionId()));
		            else
		                jsonSessionId.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getSessionId()));
		            array.put(jsonSessionId);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_RequestHistory.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_RequestHistory.getTimeCreated()));
		            array.put(jsonTimeCreated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("forwardSiteId")) 
	            json.put("forwardSiteId", ""+_RequestHistory.getForwardSiteId());
            if ( ignoreFieldSet || fieldSet.contains("isDropped")) 
	            json.put("isDropped", ""+_RequestHistory.getIsDropped());
            if ( ignoreFieldSet || fieldSet.contains("isPageless")) 
	            json.put("isPageless", ""+_RequestHistory.getIsPageless());
            if ( ignoreFieldSet || fieldSet.contains("isLogin")) 
	            json.put("isLogin", ""+_RequestHistory.getIsLogin());
            if ( ignoreFieldSet || fieldSet.contains("isAjax")) 
	            json.put("isAjax", ""+_RequestHistory.getIsAjax());
            if ( ignoreFieldSet || fieldSet.contains("isRobot")) 
	            json.put("isRobot", ""+_RequestHistory.getIsRobot());
            if ( ignoreFieldSet || fieldSet.contains("userid")) 
	            json.put("userid", ""+_RequestHistory.getUserid());
            if ( ignoreFieldSet || fieldSet.contains("userAgent")) 
	            json.put("userAgent", ""+_RequestHistory.getUserAgent());
            if ( ignoreFieldSet || fieldSet.contains("refer")) 
	            json.put("refer", ""+_RequestHistory.getRefer());
            if ( ignoreFieldSet || fieldSet.contains("robot")) 
	            json.put("robot", ""+_RequestHistory.getRobot());
            if ( ignoreFieldSet || fieldSet.contains("remoteIp")) 
	            json.put("remoteIp", ""+_RequestHistory.getRemoteIp());
            if ( ignoreFieldSet || fieldSet.contains("siteUrl")) 
	            json.put("siteUrl", ""+_RequestHistory.getSiteUrl());
            if ( ignoreFieldSet || fieldSet.contains("uri")) 
	            json.put("uri", ""+_RequestHistory.getUri());
            if ( ignoreFieldSet || fieldSet.contains("query")) 
	            json.put("query", ""+_RequestHistory.getQuery());
            if ( ignoreFieldSet || fieldSet.contains("rpci")) 
	            json.put("rpci", ""+_RequestHistory.getRpci());
            if ( ignoreFieldSet || fieldSet.contains("sessionId")) 
	            json.put("sessionId", ""+_RequestHistory.getSessionId());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_RequestHistory.getTimeCreated());

		}
		return json;

	}

}