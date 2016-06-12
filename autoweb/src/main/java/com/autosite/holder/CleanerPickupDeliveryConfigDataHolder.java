/* 
Template last modification history:


Source Generated: Sun Mar 15 13:54:46 EDT 2015
*/

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

import com.autosite.db.CleanerPickupDeliveryConfig;


public class CleanerPickupDeliveryConfigDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("locationId", new Integer(0));
        fieldsMap.put("applyAllLocations", new Integer(1));
        fieldsMap.put("disableWebRequest", new Integer(2));
        fieldsMap.put("disallowAnonymousRequest", new Integer(3));
        fieldsMap.put("requireCustomerRegister", new Integer(4));
        fieldsMap.put("requireCustomerLogin", new Integer(5));
    }


	CleanerPickupDeliveryConfig m_cleanerPickupDeliveryConfig;

	public CleanerPickupDeliveryConfigDataHolder(CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig){
		m_cleanerPickupDeliveryConfig =  _CleanerPickupDeliveryConfig; 	
	}

	public CleanerPickupDeliveryConfigDataHolder(){
		m_cleanerPickupDeliveryConfig =  new CleanerPickupDeliveryConfig(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerPickupDeliveryConfig;
	}


	public long getId(){
		return 	m_cleanerPickupDeliveryConfig.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerPickupDeliveryConfig.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerPickupDeliveryConfig.setSiteId(sid);
	}


	public long getLocationId(){
		return m_cleanerPickupDeliveryConfig.getLocationId();
	}
	public int getApplyAllLocations(){
		return m_cleanerPickupDeliveryConfig.getApplyAllLocations();
	}
	public int getDisableWebRequest(){
		return m_cleanerPickupDeliveryConfig.getDisableWebRequest();
	}
	public int getDisallowAnonymousRequest(){
		return m_cleanerPickupDeliveryConfig.getDisallowAnonymousRequest();
	}
	public int getRequireCustomerRegister(){
		return m_cleanerPickupDeliveryConfig.getRequireCustomerRegister();
	}
	public int getRequireCustomerLogin(){
		return m_cleanerPickupDeliveryConfig.getRequireCustomerLogin();
	}

	public void setLocationId(long _locationId ){
		m_cleanerPickupDeliveryConfig.setLocationId(_locationId);
	}
	public void setApplyAllLocations(int _applyAllLocations ){
		m_cleanerPickupDeliveryConfig.setApplyAllLocations(_applyAllLocations);
	}
	public void setDisableWebRequest(int _disableWebRequest ){
		m_cleanerPickupDeliveryConfig.setDisableWebRequest(_disableWebRequest);
	}
	public void setDisallowAnonymousRequest(int _disallowAnonymousRequest ){
		m_cleanerPickupDeliveryConfig.setDisallowAnonymousRequest(_disallowAnonymousRequest);
	}
	public void setRequireCustomerRegister(int _requireCustomerRegister ){
		m_cleanerPickupDeliveryConfig.setRequireCustomerRegister(_requireCustomerRegister);
	}
	public void setRequireCustomerLogin(int _requireCustomerLogin ){
		m_cleanerPickupDeliveryConfig.setRequireCustomerLogin(_requireCustomerLogin);
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

        if ( colNum == 0) return String.valueOf(getLocationId());
        if ( colNum == 1) return String.valueOf(getApplyAllLocations());
        if ( colNum == 2) return String.valueOf(getDisableWebRequest());
        if ( colNum == 3) return String.valueOf(getDisallowAnonymousRequest());
        if ( colNum == 4) return String.valueOf(getRequireCustomerRegister());
        if ( colNum == 5) return String.valueOf(getRequireCustomerLogin());
		return "";
    }


    public static JSONObject convertToJSON(CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_CleanerPickupDeliveryConfig, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _CleanerPickupDeliveryConfig == null) return json;

        json.put("id", ""+_CleanerPickupDeliveryConfig.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonLocationId = new JSONObject();
		            jsonLocationId.put("name", "locationId");
		            if ( useJsonDataType ) 
	                    jsonLocationId.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDeliveryConfig.getLocationId()));
		            else
		                jsonLocationId.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDeliveryConfig.getLocationId()));
		            array.put(jsonLocationId);
		            JSONObject jsonApplyAllLocations = new JSONObject();
		            jsonApplyAllLocations.put("name", "applyAllLocations");
		            if ( useJsonDataType ) 
	                    jsonApplyAllLocations.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDeliveryConfig.getApplyAllLocations()));
		            else
		                jsonApplyAllLocations.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDeliveryConfig.getApplyAllLocations()));
		            array.put(jsonApplyAllLocations);
		            JSONObject jsonDisableWebRequest = new JSONObject();
		            jsonDisableWebRequest.put("name", "disableWebRequest");
		            if ( useJsonDataType ) 
	                    jsonDisableWebRequest.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDeliveryConfig.getDisableWebRequest()));
		            else
		                jsonDisableWebRequest.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDeliveryConfig.getDisableWebRequest()));
		            array.put(jsonDisableWebRequest);
		            JSONObject jsonDisallowAnonymousRequest = new JSONObject();
		            jsonDisallowAnonymousRequest.put("name", "disallowAnonymousRequest");
		            if ( useJsonDataType ) 
	                    jsonDisallowAnonymousRequest.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDeliveryConfig.getDisallowAnonymousRequest()));
		            else
		                jsonDisallowAnonymousRequest.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDeliveryConfig.getDisallowAnonymousRequest()));
		            array.put(jsonDisallowAnonymousRequest);
		            JSONObject jsonRequireCustomerRegister = new JSONObject();
		            jsonRequireCustomerRegister.put("name", "requireCustomerRegister");
		            if ( useJsonDataType ) 
	                    jsonRequireCustomerRegister.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDeliveryConfig.getRequireCustomerRegister()));
		            else
		                jsonRequireCustomerRegister.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDeliveryConfig.getRequireCustomerRegister()));
		            array.put(jsonRequireCustomerRegister);
		            JSONObject jsonRequireCustomerLogin = new JSONObject();
		            jsonRequireCustomerLogin.put("name", "requireCustomerLogin");
		            if ( useJsonDataType ) 
	                    jsonRequireCustomerLogin.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDeliveryConfig.getRequireCustomerLogin()));
		            else
		                jsonRequireCustomerLogin.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDeliveryConfig.getRequireCustomerLogin()));
		            array.put(jsonRequireCustomerLogin);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("locationId")) 
	            json.put("locationId", ""+_CleanerPickupDeliveryConfig.getLocationId());
            if ( ignoreFieldSet || fieldSet.contains("applyAllLocations")) 
	            json.put("applyAllLocations", ""+_CleanerPickupDeliveryConfig.getApplyAllLocations());
            if ( ignoreFieldSet || fieldSet.contains("disableWebRequest")) 
	            json.put("disableWebRequest", ""+_CleanerPickupDeliveryConfig.getDisableWebRequest());
            if ( ignoreFieldSet || fieldSet.contains("disallowAnonymousRequest")) 
	            json.put("disallowAnonymousRequest", ""+_CleanerPickupDeliveryConfig.getDisallowAnonymousRequest());
            if ( ignoreFieldSet || fieldSet.contains("requireCustomerRegister")) 
	            json.put("requireCustomerRegister", ""+_CleanerPickupDeliveryConfig.getRequireCustomerRegister());
            if ( ignoreFieldSet || fieldSet.contains("requireCustomerLogin")) 
	            json.put("requireCustomerLogin", ""+_CleanerPickupDeliveryConfig.getRequireCustomerLogin());

		}
		return json;

	}

}