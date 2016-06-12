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



public class CleanerRegisterStartDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields
	private String m_siteTitle;
	private String m_siteName;
	private String m_username;
	private String m_email;
	private String m_password;
	private String m_passwordRepeat;
	private String m_location;
	private String m_createdSiteUrl;

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("siteTitle", new Integer(0));
        fieldsMap.put("siteName", new Integer(1));
        fieldsMap.put("username", new Integer(2));
        fieldsMap.put("email", new Integer(3));
        fieldsMap.put("password", new Integer(4));
        fieldsMap.put("passwordRepeat", new Integer(5));
        fieldsMap.put("location", new Integer(6));
        fieldsMap.put("createdSiteUrl", new Integer(7));
    }



	private long m_id = 0;
	private long m_siteId = 0;

	public CleanerRegisterStartDataHolder(){
	}

	public long getId(){
		return m_id;
	}
	public void setId(long id){
		m_id = id;
	}
	public long getSiteId(){
		return m_siteId;
	}
	public void setSiteId(long sid){
		m_siteId = sid;
	}

	public  BaseAutositeDataObject getDataObject(){
		return null;
	}


	public String getSiteTitle(){
		return m_siteTitle;
	}
	public String getSiteName(){
		return m_siteName;
	}
	public String getUsername(){
		return m_username;
	}
	public String getEmail(){
		return m_email;
	}
	public String getPassword(){
		return m_password;
	}
	public String getPasswordRepeat(){
		return m_passwordRepeat;
	}
	public String getLocation(){
		return m_location;
	}
	public String getCreatedSiteUrl(){
		return m_createdSiteUrl;
	}

	public void setSiteTitle(String _siteTitle ){
		m_siteTitle = _siteTitle;
	}
	public void setSiteName(String _siteName ){
		m_siteName = _siteName;
	}
	public void setUsername(String _username ){
		m_username = _username;
	}
	public void setEmail(String _email ){
		m_email = _email;
	}
	public void setPassword(String _password ){
		m_password = _password;
	}
	public void setPasswordRepeat(String _passwordRepeat ){
		m_passwordRepeat = _passwordRepeat;
	}
	public void setLocation(String _location ){
		m_location = _location;
	}
	public void setCreatedSiteUrl(String _createdSiteUrl ){
		m_createdSiteUrl = _createdSiteUrl;
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

        if ( colNum == 0) return getSiteTitle() == null?"":getSiteTitle().toString();
        if ( colNum == 1) return getSiteName() == null?"":getSiteName().toString();
        if ( colNum == 2) return getUsername() == null?"":getUsername().toString();
        if ( colNum == 3) return getEmail() == null?"":getEmail().toString();
        if ( colNum == 4) return getPassword() == null?"":getPassword().toString();
        if ( colNum == 5) return getPasswordRepeat() == null?"":getPasswordRepeat().toString();
        if ( colNum == 6) return getLocation() == null?"":getLocation().toString();
        if ( colNum == 7) return getCreatedSiteUrl() == null?"":getCreatedSiteUrl().toString();
		return "";
    }


    public static JSONObject convertToJSON(CleanerRegisterStartDataHolder _CleanerRegisterStart, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_CleanerRegisterStart, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(CleanerRegisterStartDataHolder _CleanerRegisterStart, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _CleanerRegisterStart == null) return json;

        json.put("id", ""+_CleanerRegisterStart.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonSiteTitle = new JSONObject();
		            jsonSiteTitle.put("name", "siteTitle");
		            if ( useJsonDataType ) 
	                    jsonSiteTitle.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerRegisterStart.getSiteTitle()));
		            else
		                jsonSiteTitle.put("value", JSONValueFormatter.toStringValue(_CleanerRegisterStart.getSiteTitle()));
		            array.put(jsonSiteTitle);
		            JSONObject jsonSiteName = new JSONObject();
		            jsonSiteName.put("name", "siteName");
		            if ( useJsonDataType ) 
	                    jsonSiteName.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerRegisterStart.getSiteName()));
		            else
		                jsonSiteName.put("value", JSONValueFormatter.toStringValue(_CleanerRegisterStart.getSiteName()));
		            array.put(jsonSiteName);
		            JSONObject jsonUsername = new JSONObject();
		            jsonUsername.put("name", "username");
		            if ( useJsonDataType ) 
	                    jsonUsername.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerRegisterStart.getUsername()));
		            else
		                jsonUsername.put("value", JSONValueFormatter.toStringValue(_CleanerRegisterStart.getUsername()));
		            array.put(jsonUsername);
		            JSONObject jsonEmail = new JSONObject();
		            jsonEmail.put("name", "email");
		            if ( useJsonDataType ) 
	                    jsonEmail.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerRegisterStart.getEmail()));
		            else
		                jsonEmail.put("value", JSONValueFormatter.toStringValue(_CleanerRegisterStart.getEmail()));
		            array.put(jsonEmail);
		            JSONObject jsonPassword = new JSONObject();
		            jsonPassword.put("name", "password");
		            if ( useJsonDataType ) 
	                    jsonPassword.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerRegisterStart.getPassword()));
		            else
		                jsonPassword.put("value", JSONValueFormatter.toStringValue(_CleanerRegisterStart.getPassword()));
		            array.put(jsonPassword);
		            JSONObject jsonPasswordRepeat = new JSONObject();
		            jsonPasswordRepeat.put("name", "passwordRepeat");
		            if ( useJsonDataType ) 
	                    jsonPasswordRepeat.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerRegisterStart.getPasswordRepeat()));
		            else
		                jsonPasswordRepeat.put("value", JSONValueFormatter.toStringValue(_CleanerRegisterStart.getPasswordRepeat()));
		            array.put(jsonPasswordRepeat);
		            JSONObject jsonLocation = new JSONObject();
		            jsonLocation.put("name", "location");
		            if ( useJsonDataType ) 
	                    jsonLocation.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerRegisterStart.getLocation()));
		            else
		                jsonLocation.put("value", JSONValueFormatter.toStringValue(_CleanerRegisterStart.getLocation()));
		            array.put(jsonLocation);
		            JSONObject jsonCreatedSiteUrl = new JSONObject();
		            jsonCreatedSiteUrl.put("name", "createdSiteUrl");
		            if ( useJsonDataType ) 
	                    jsonCreatedSiteUrl.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerRegisterStart.getCreatedSiteUrl()));
		            else
		                jsonCreatedSiteUrl.put("value", JSONValueFormatter.toStringValue(_CleanerRegisterStart.getCreatedSiteUrl()));
		            array.put(jsonCreatedSiteUrl);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("siteTitle")) 
	            json.put("siteTitle", ""+_CleanerRegisterStart.getSiteTitle());
            if ( ignoreFieldSet || fieldSet.contains("siteName")) 
	            json.put("siteName", ""+_CleanerRegisterStart.getSiteName());
            if ( ignoreFieldSet || fieldSet.contains("username")) 
	            json.put("username", ""+_CleanerRegisterStart.getUsername());
            if ( ignoreFieldSet || fieldSet.contains("email")) 
	            json.put("email", ""+_CleanerRegisterStart.getEmail());
            if ( ignoreFieldSet || fieldSet.contains("password")) 
	            json.put("password", ""+_CleanerRegisterStart.getPassword());
            if ( ignoreFieldSet || fieldSet.contains("passwordRepeat")) 
	            json.put("passwordRepeat", ""+_CleanerRegisterStart.getPasswordRepeat());
            if ( ignoreFieldSet || fieldSet.contains("location")) 
	            json.put("location", ""+_CleanerRegisterStart.getLocation());
            if ( ignoreFieldSet || fieldSet.contains("createdSiteUrl")) 
	            json.put("createdSiteUrl", ""+_CleanerRegisterStart.getCreatedSiteUrl());

		}
		return json;

	}

}