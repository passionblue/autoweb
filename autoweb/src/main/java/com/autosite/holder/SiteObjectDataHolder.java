package com.autosite.holder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.Site;
import com.autosite.db.SiteObject;
import com.autosite.util.html.HtmlGenRow;
import com.jtrend.util.JSONValueFormatter;


public class SiteObjectDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("siteUrl", new Integer(0));
        fieldsMap.put("accountId", new Integer(1));
        fieldsMap.put("createdTime", new Integer(2));
        fieldsMap.put("siteGroup", new Integer(3));
        fieldsMap.put("registered", new Integer(4));
        fieldsMap.put("onSale", new Integer(5));
        fieldsMap.put("superAdminEnable", new Integer(6));
        fieldsMap.put("siteRegisterEnable", new Integer(7));
        fieldsMap.put("subdomainEnable", new Integer(8));
        fieldsMap.put("siteRegisterSite", new Integer(9));
        fieldsMap.put("baseSiteId", new Integer(10));
        fieldsMap.put("subsite", new Integer(11));
        fieldsMap.put("disabled", new Integer(12));
    }


	SiteObject m_siteObject;

	public SiteObjectDataHolder(SiteObject _SiteObject){
		m_siteObject =  _SiteObject; 	
	}

	public SiteObjectDataHolder(){
		m_siteObject =  new SiteObject(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_siteObject;
	}


	public long getId(){
		return 	m_siteObject.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_siteObject.getId();
	}
	public void setSiteId(long sid){
		m_siteObject.setId(sid);
	}


	public String getSiteUrl(){
		return m_siteObject.getSiteUrl();
	}
	public long getAccountId(){
		return m_siteObject.getAccountId();
	}
	public Timestamp getCreatedTime(){
		return m_siteObject.getCreatedTime();
	}
	public String getSiteGroup(){
		return m_siteObject.getSiteGroup();
	}
	public int getRegistered(){
		return m_siteObject.getRegistered();
	}
	public int getOnSale(){
		return m_siteObject.getOnSale();
	}
	public int getSuperAdminEnable(){
		return m_siteObject.getSuperAdminEnable();
	}
	public int getSiteRegisterEnable(){
		return m_siteObject.getSiteRegisterEnable();
	}
	public int getSubdomainEnable(){
		return m_siteObject.getSubdomainEnable();
	}
	public String getSiteRegisterSite(){
		return m_siteObject.getSiteRegisterSite();
	}
	public long getBaseSiteId(){
		return m_siteObject.getBaseSiteId();
	}
	public int getSubsite(){
		return m_siteObject.getSubsite();
	}
	public int getDisabled(){
		return m_siteObject.getDisabled();
	}

	public void setSiteUrl(String _siteUrl ){
		m_siteObject.setSiteUrl(_siteUrl);
	}
	public void setAccountId(long _accountId ){
		m_siteObject.setAccountId(_accountId);
	}
	public void setCreatedTime(Timestamp _createdTime ){
		m_siteObject.setCreatedTime(_createdTime);
	}
	public void setSiteGroup(String _siteGroup ){
		m_siteObject.setSiteGroup(_siteGroup);
	}
	public void setRegistered(int _registered ){
		m_siteObject.setRegistered(_registered);
	}
	public void setOnSale(int _onSale ){
		m_siteObject.setOnSale(_onSale);
	}
	public void setSuperAdminEnable(int _superAdminEnable ){
		m_siteObject.setSuperAdminEnable(_superAdminEnable);
	}
	public void setSiteRegisterEnable(int _siteRegisterEnable ){
		m_siteObject.setSiteRegisterEnable(_siteRegisterEnable);
	}
	public void setSubdomainEnable(int _subdomainEnable ){
		m_siteObject.setSubdomainEnable(_subdomainEnable);
	}
	public void setSiteRegisterSite(String _siteRegisterSite ){
		m_siteObject.setSiteRegisterSite(_siteRegisterSite);
	}
	public void setBaseSiteId(long _baseSiteId ){
		m_siteObject.setBaseSiteId(_baseSiteId);
	}
	public void setSubsite(int _subsite ){
		m_siteObject.setSubsite(_subsite);
	}
	public void setDisabled(int _disabled ){
		m_siteObject.setDisabled(_disabled);
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

        if ( colNum == 0) return getSiteUrl() == null?"":getSiteUrl().toString();
        if ( colNum == 1) return String.valueOf(getAccountId());
        if ( colNum == 2) return getCreatedTime() == null?"":getCreatedTime().toString();
        if ( colNum == 3) return getSiteGroup() == null?"":getSiteGroup().toString();
        if ( colNum == 4) return String.valueOf(getRegistered());
        if ( colNum == 5) return String.valueOf(getOnSale());
        if ( colNum == 6) return String.valueOf(getSuperAdminEnable());
        if ( colNum == 7) return String.valueOf(getSiteRegisterEnable());
        if ( colNum == 8) return String.valueOf(getSubdomainEnable());
        if ( colNum == 9) return getSiteRegisterSite() == null?"":getSiteRegisterSite().toString();
        if ( colNum == 10) return String.valueOf(getBaseSiteId());
        if ( colNum == 11) return String.valueOf(getSubsite());
        if ( colNum == 12) return String.valueOf(getDisabled());
		return "";
    }


    public static JSONObject convertToJSON(Site _Site, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_Site, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(Site _Site, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _Site == null) return json;

        json.put("id", ""+_Site.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonSiteUrl = new JSONObject();
		            jsonSiteUrl.put("name", "siteUrl");
		            if ( useJsonDataType ) 
	                    jsonSiteUrl.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getSiteUrl()));
		            else
		                jsonSiteUrl.put("value", JSONValueFormatter.toStringValue(_Site.getSiteUrl()));
		            array.put(jsonSiteUrl);
		            JSONObject jsonAccountId = new JSONObject();
		            jsonAccountId.put("name", "accountId");
		            if ( useJsonDataType ) 
	                    jsonAccountId.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getAccountId()));
		            else
		                jsonAccountId.put("value", JSONValueFormatter.toStringValue(_Site.getAccountId()));
		            array.put(jsonAccountId);
		            JSONObject jsonCreatedTime = new JSONObject();
		            jsonCreatedTime.put("name", "createdTime");
		            if ( useJsonDataType ) 
	                    jsonCreatedTime.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getCreatedTime()));
		            else
		                jsonCreatedTime.put("value", JSONValueFormatter.toStringValue(_Site.getCreatedTime()));
		            array.put(jsonCreatedTime);
		            JSONObject jsonSiteGroup = new JSONObject();
		            jsonSiteGroup.put("name", "siteGroup");
		            if ( useJsonDataType ) 
	                    jsonSiteGroup.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getSiteGroup()));
		            else
		                jsonSiteGroup.put("value", JSONValueFormatter.toStringValue(_Site.getSiteGroup()));
		            array.put(jsonSiteGroup);
		            JSONObject jsonRegistered = new JSONObject();
		            jsonRegistered.put("name", "registered");
		            if ( useJsonDataType ) 
	                    jsonRegistered.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getRegistered()));
		            else
		                jsonRegistered.put("value", JSONValueFormatter.toStringValue(_Site.getRegistered()));
		            array.put(jsonRegistered);
		            JSONObject jsonOnSale = new JSONObject();
		            jsonOnSale.put("name", "onSale");
		            if ( useJsonDataType ) 
	                    jsonOnSale.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getOnSale()));
		            else
		                jsonOnSale.put("value", JSONValueFormatter.toStringValue(_Site.getOnSale()));
		            array.put(jsonOnSale);
		            JSONObject jsonSuperAdminEnable = new JSONObject();
		            jsonSuperAdminEnable.put("name", "superAdminEnable");
		            if ( useJsonDataType ) 
	                    jsonSuperAdminEnable.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getSuperAdminEnable()));
		            else
		                jsonSuperAdminEnable.put("value", JSONValueFormatter.toStringValue(_Site.getSuperAdminEnable()));
		            array.put(jsonSuperAdminEnable);
		            JSONObject jsonSiteRegisterEnable = new JSONObject();
		            jsonSiteRegisterEnable.put("name", "siteRegisterEnable");
		            if ( useJsonDataType ) 
	                    jsonSiteRegisterEnable.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getSiteRegisterEnable()));
		            else
		                jsonSiteRegisterEnable.put("value", JSONValueFormatter.toStringValue(_Site.getSiteRegisterEnable()));
		            array.put(jsonSiteRegisterEnable);
		            JSONObject jsonSubdomainEnable = new JSONObject();
		            jsonSubdomainEnable.put("name", "subdomainEnable");
		            if ( useJsonDataType ) 
	                    jsonSubdomainEnable.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getSubdomainEnable()));
		            else
		                jsonSubdomainEnable.put("value", JSONValueFormatter.toStringValue(_Site.getSubdomainEnable()));
		            array.put(jsonSubdomainEnable);
		            JSONObject jsonSiteRegisterSite = new JSONObject();
		            jsonSiteRegisterSite.put("name", "siteRegisterSite");
		            if ( useJsonDataType ) 
	                    jsonSiteRegisterSite.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getSiteRegisterSite()));
		            else
		                jsonSiteRegisterSite.put("value", JSONValueFormatter.toStringValue(_Site.getSiteRegisterSite()));
		            array.put(jsonSiteRegisterSite);
		            JSONObject jsonBaseSiteId = new JSONObject();
		            jsonBaseSiteId.put("name", "baseSiteId");
		            if ( useJsonDataType ) 
	                    jsonBaseSiteId.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getBaseSiteId()));
		            else
		                jsonBaseSiteId.put("value", JSONValueFormatter.toStringValue(_Site.getBaseSiteId()));
		            array.put(jsonBaseSiteId);
		            JSONObject jsonSubsite = new JSONObject();
		            jsonSubsite.put("name", "subsite");
		            if ( useJsonDataType ) 
	                    jsonSubsite.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getSubsite()));
		            else
		                jsonSubsite.put("value", JSONValueFormatter.toStringValue(_Site.getSubsite()));
		            array.put(jsonSubsite);
		            JSONObject jsonDisabled = new JSONObject();
		            jsonDisabled.put("name", "disabled");
		            if ( useJsonDataType ) 
	                    jsonDisabled.put("value", JSONValueFormatter.toJsonTypeValue(_Site.getDisabled()));
		            else
		                jsonDisabled.put("value", JSONValueFormatter.toStringValue(_Site.getDisabled()));
		            array.put(jsonDisabled);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("siteUrl")) 
	            json.put("siteUrl", ""+_Site.getSiteUrl());
            if ( ignoreFieldSet || fieldSet.contains("accountId")) 
	            json.put("accountId", ""+_Site.getAccountId());
            if ( ignoreFieldSet || fieldSet.contains("createdTime")) 
	            json.put("createdTime", ""+_Site.getCreatedTime());
            if ( ignoreFieldSet || fieldSet.contains("siteGroup")) 
	            json.put("siteGroup", ""+_Site.getSiteGroup());
            if ( ignoreFieldSet || fieldSet.contains("registered")) 
	            json.put("registered", ""+_Site.getRegistered());
            if ( ignoreFieldSet || fieldSet.contains("onSale")) 
	            json.put("onSale", ""+_Site.getOnSale());
            if ( ignoreFieldSet || fieldSet.contains("superAdminEnable")) 
	            json.put("superAdminEnable", ""+_Site.getSuperAdminEnable());
            if ( ignoreFieldSet || fieldSet.contains("siteRegisterEnable")) 
	            json.put("siteRegisterEnable", ""+_Site.getSiteRegisterEnable());
            if ( ignoreFieldSet || fieldSet.contains("subdomainEnable")) 
	            json.put("subdomainEnable", ""+_Site.getSubdomainEnable());
            if ( ignoreFieldSet || fieldSet.contains("siteRegisterSite")) 
	            json.put("siteRegisterSite", ""+_Site.getSiteRegisterSite());
            if ( ignoreFieldSet || fieldSet.contains("baseSiteId")) 
	            json.put("baseSiteId", ""+_Site.getBaseSiteId());
            if ( ignoreFieldSet || fieldSet.contains("subsite")) 
	            json.put("subsite", ""+_Site.getSubsite());
            if ( ignoreFieldSet || fieldSet.contains("disabled")) 
	            json.put("disabled", ""+_Site.getDisabled());

		}
		return json;

	}

}