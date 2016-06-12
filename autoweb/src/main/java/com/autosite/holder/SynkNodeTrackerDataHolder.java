/* 
Template last modification history:


Source Generated: Sat Feb 14 00:17:17 EST 2015
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

import com.autosite.db.SynkNodeTracker;


public class SynkNodeTrackerDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("namespace", new Integer(0));
        fieldsMap.put("deviceId", new Integer(1));
        fieldsMap.put("remote", new Integer(2));
        fieldsMap.put("stamp", new Integer(3));
        fieldsMap.put("timeCreated", new Integer(4));
        fieldsMap.put("timeUpdated", new Integer(5));
    }


	SynkNodeTracker m_synkNodeTracker;

	public SynkNodeTrackerDataHolder(SynkNodeTracker _SynkNodeTracker){
		m_synkNodeTracker =  _SynkNodeTracker; 	
	}

	public SynkNodeTrackerDataHolder(){
		m_synkNodeTracker =  new SynkNodeTracker(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_synkNodeTracker;
	}


	public long getId(){
		return 	m_synkNodeTracker.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_synkNodeTracker.getSiteId();
	}
	public void setSiteId(long sid){
		m_synkNodeTracker.setSiteId(sid);
	}


	public String getNamespace(){
		return m_synkNodeTracker.getNamespace();
	}
	public String getDeviceId(){
		return m_synkNodeTracker.getDeviceId();
	}
	public int getRemote(){
		return m_synkNodeTracker.getRemote();
	}
	public long getStamp(){
		return m_synkNodeTracker.getStamp();
	}
	public Timestamp getTimeCreated(){
		return m_synkNodeTracker.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_synkNodeTracker.getTimeUpdated();
	}

	public void setNamespace(String _namespace ){
		m_synkNodeTracker.setNamespace(_namespace);
	}
	public void setDeviceId(String _deviceId ){
		m_synkNodeTracker.setDeviceId(_deviceId);
	}
	public void setRemote(int _remote ){
		m_synkNodeTracker.setRemote(_remote);
	}
	public void setStamp(long _stamp ){
		m_synkNodeTracker.setStamp(_stamp);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_synkNodeTracker.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_synkNodeTracker.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return getNamespace() == null?"":getNamespace().toString();
        if ( colNum == 1) return getDeviceId() == null?"":getDeviceId().toString();
        if ( colNum == 2) return String.valueOf(getRemote());
        if ( colNum == 3) return String.valueOf(getStamp());
        if ( colNum == 4) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 5) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }


    public static JSONObject convertToJSON(SynkNodeTracker _SynkNodeTracker, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_SynkNodeTracker, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(SynkNodeTracker _SynkNodeTracker, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _SynkNodeTracker == null) return json;

        json.put("id", ""+_SynkNodeTracker.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonNamespace = new JSONObject();
		            jsonNamespace.put("name", "namespace");
		            if ( useJsonDataType ) 
	                    jsonNamespace.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTracker.getNamespace()));
		            else
		                jsonNamespace.put("value", JSONValueFormatter.toStringValue(_SynkNodeTracker.getNamespace()));
		            array.put(jsonNamespace);
		            JSONObject jsonDeviceId = new JSONObject();
		            jsonDeviceId.put("name", "deviceId");
		            if ( useJsonDataType ) 
	                    jsonDeviceId.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTracker.getDeviceId()));
		            else
		                jsonDeviceId.put("value", JSONValueFormatter.toStringValue(_SynkNodeTracker.getDeviceId()));
		            array.put(jsonDeviceId);
		            JSONObject jsonRemote = new JSONObject();
		            jsonRemote.put("name", "remote");
		            if ( useJsonDataType ) 
	                    jsonRemote.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTracker.getRemote()));
		            else
		                jsonRemote.put("value", JSONValueFormatter.toStringValue(_SynkNodeTracker.getRemote()));
		            array.put(jsonRemote);
		            JSONObject jsonStamp = new JSONObject();
		            jsonStamp.put("name", "stamp");
		            if ( useJsonDataType ) 
	                    jsonStamp.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTracker.getStamp()));
		            else
		                jsonStamp.put("value", JSONValueFormatter.toStringValue(_SynkNodeTracker.getStamp()));
		            array.put(jsonStamp);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTracker.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_SynkNodeTracker.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            if ( useJsonDataType ) 
	                    jsonTimeUpdated.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTracker.getTimeUpdated()));
		            else
		                jsonTimeUpdated.put("value", JSONValueFormatter.toStringValue(_SynkNodeTracker.getTimeUpdated()));
		            array.put(jsonTimeUpdated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("namespace")) 
	            json.put("namespace", ""+_SynkNodeTracker.getNamespace());
            if ( ignoreFieldSet || fieldSet.contains("deviceId")) 
	            json.put("deviceId", ""+_SynkNodeTracker.getDeviceId());
            if ( ignoreFieldSet || fieldSet.contains("remote")) 
	            json.put("remote", ""+_SynkNodeTracker.getRemote());
            if ( ignoreFieldSet || fieldSet.contains("stamp")) 
	            json.put("stamp", ""+_SynkNodeTracker.getStamp());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_SynkNodeTracker.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
	            json.put("timeUpdated", ""+_SynkNodeTracker.getTimeUpdated());

		}
		return json;

	}

}