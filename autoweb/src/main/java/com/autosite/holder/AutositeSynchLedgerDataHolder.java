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

import com.autosite.db.AutositeSynchLedger;


public class AutositeSynchLedgerDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("deviceId", new Integer(0));
        fieldsMap.put("originalLedgerId", new Integer(1));
        fieldsMap.put("scope", new Integer(2));
        fieldsMap.put("target", new Integer(3));
        fieldsMap.put("remoteToken", new Integer(4));
        fieldsMap.put("objectId", new Integer(5));
        fieldsMap.put("synchId", new Integer(6));
        fieldsMap.put("timeCreated", new Integer(7));
    }


	AutositeSynchLedger m_autositeSynchLedger;

	public AutositeSynchLedgerDataHolder(AutositeSynchLedger _AutositeSynchLedger){
		m_autositeSynchLedger =  _AutositeSynchLedger; 	
	}

	public AutositeSynchLedgerDataHolder(){
		m_autositeSynchLedger =  new AutositeSynchLedger(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_autositeSynchLedger;
	}


	public long getId(){
		return 	m_autositeSynchLedger.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_autositeSynchLedger.getSiteId();
	}
	public void setSiteId(long sid){
		m_autositeSynchLedger.setSiteId(sid);
	}


	public long getDeviceId(){
		return m_autositeSynchLedger.getDeviceId();
	}
	public long getOriginalLedgerId(){
		return m_autositeSynchLedger.getOriginalLedgerId();
	}
	public String getScope(){
		return m_autositeSynchLedger.getScope();
	}
	public String getTarget(){
		return m_autositeSynchLedger.getTarget();
	}
	public String getRemoteToken(){
		return m_autositeSynchLedger.getRemoteToken();
	}
	public long getObjectId(){
		return m_autositeSynchLedger.getObjectId();
	}
	public String getSynchId(){
		return m_autositeSynchLedger.getSynchId();
	}
	public Timestamp getTimeCreated(){
		return m_autositeSynchLedger.getTimeCreated();
	}

	public void setDeviceId(long _deviceId ){
		m_autositeSynchLedger.setDeviceId(_deviceId);
	}
	public void setOriginalLedgerId(long _originalLedgerId ){
		m_autositeSynchLedger.setOriginalLedgerId(_originalLedgerId);
	}
	public void setScope(String _scope ){
		m_autositeSynchLedger.setScope(_scope);
	}
	public void setTarget(String _target ){
		m_autositeSynchLedger.setTarget(_target);
	}
	public void setRemoteToken(String _remoteToken ){
		m_autositeSynchLedger.setRemoteToken(_remoteToken);
	}
	public void setObjectId(long _objectId ){
		m_autositeSynchLedger.setObjectId(_objectId);
	}
	public void setSynchId(String _synchId ){
		m_autositeSynchLedger.setSynchId(_synchId);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_autositeSynchLedger.setTimeCreated(_timeCreated);
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

        if ( colNum == 0) return String.valueOf(getDeviceId());
        if ( colNum == 1) return String.valueOf(getOriginalLedgerId());
        if ( colNum == 2) return getScope() == null?"":getScope().toString();
        if ( colNum == 3) return getTarget() == null?"":getTarget().toString();
        if ( colNum == 4) return getRemoteToken() == null?"":getRemoteToken().toString();
        if ( colNum == 5) return String.valueOf(getObjectId());
        if ( colNum == 6) return getSynchId() == null?"":getSynchId().toString();
        if ( colNum == 7) return getTimeCreated() == null?"":getTimeCreated().toString();
		return "";
    }


    public static JSONObject convertToJSON(AutositeSynchLedger _AutositeSynchLedger, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_AutositeSynchLedger, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(AutositeSynchLedger _AutositeSynchLedger, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _AutositeSynchLedger == null) return json;

        json.put("id", ""+_AutositeSynchLedger.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonDeviceId = new JSONObject();
		            jsonDeviceId.put("name", "deviceId");
		            if ( useJsonDataType ) 
	                    jsonDeviceId.put("value", JSONValueFormatter.toJsonTypeValue(_AutositeSynchLedger.getDeviceId()));
		            else
		                jsonDeviceId.put("value", JSONValueFormatter.toStringValue(_AutositeSynchLedger.getDeviceId()));
		            array.put(jsonDeviceId);
		            JSONObject jsonOriginalLedgerId = new JSONObject();
		            jsonOriginalLedgerId.put("name", "originalLedgerId");
		            if ( useJsonDataType ) 
	                    jsonOriginalLedgerId.put("value", JSONValueFormatter.toJsonTypeValue(_AutositeSynchLedger.getOriginalLedgerId()));
		            else
		                jsonOriginalLedgerId.put("value", JSONValueFormatter.toStringValue(_AutositeSynchLedger.getOriginalLedgerId()));
		            array.put(jsonOriginalLedgerId);
		            JSONObject jsonScope = new JSONObject();
		            jsonScope.put("name", "scope");
		            if ( useJsonDataType ) 
	                    jsonScope.put("value", JSONValueFormatter.toJsonTypeValue(_AutositeSynchLedger.getScope()));
		            else
		                jsonScope.put("value", JSONValueFormatter.toStringValue(_AutositeSynchLedger.getScope()));
		            array.put(jsonScope);
		            JSONObject jsonTarget = new JSONObject();
		            jsonTarget.put("name", "target");
		            if ( useJsonDataType ) 
	                    jsonTarget.put("value", JSONValueFormatter.toJsonTypeValue(_AutositeSynchLedger.getTarget()));
		            else
		                jsonTarget.put("value", JSONValueFormatter.toStringValue(_AutositeSynchLedger.getTarget()));
		            array.put(jsonTarget);
		            JSONObject jsonRemoteToken = new JSONObject();
		            jsonRemoteToken.put("name", "remoteToken");
		            if ( useJsonDataType ) 
	                    jsonRemoteToken.put("value", JSONValueFormatter.toJsonTypeValue(_AutositeSynchLedger.getRemoteToken()));
		            else
		                jsonRemoteToken.put("value", JSONValueFormatter.toStringValue(_AutositeSynchLedger.getRemoteToken()));
		            array.put(jsonRemoteToken);
		            JSONObject jsonObjectId = new JSONObject();
		            jsonObjectId.put("name", "objectId");
		            if ( useJsonDataType ) 
	                    jsonObjectId.put("value", JSONValueFormatter.toJsonTypeValue(_AutositeSynchLedger.getObjectId()));
		            else
		                jsonObjectId.put("value", JSONValueFormatter.toStringValue(_AutositeSynchLedger.getObjectId()));
		            array.put(jsonObjectId);
		            JSONObject jsonSynchId = new JSONObject();
		            jsonSynchId.put("name", "synchId");
		            if ( useJsonDataType ) 
	                    jsonSynchId.put("value", JSONValueFormatter.toJsonTypeValue(_AutositeSynchLedger.getSynchId()));
		            else
		                jsonSynchId.put("value", JSONValueFormatter.toStringValue(_AutositeSynchLedger.getSynchId()));
		            array.put(jsonSynchId);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_AutositeSynchLedger.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_AutositeSynchLedger.getTimeCreated()));
		            array.put(jsonTimeCreated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("deviceId")) 
	            json.put("deviceId", ""+_AutositeSynchLedger.getDeviceId());
            if ( ignoreFieldSet || fieldSet.contains("originalLedgerId")) 
	            json.put("originalLedgerId", ""+_AutositeSynchLedger.getOriginalLedgerId());
            if ( ignoreFieldSet || fieldSet.contains("scope")) 
	            json.put("scope", ""+_AutositeSynchLedger.getScope());
            if ( ignoreFieldSet || fieldSet.contains("target")) 
	            json.put("target", ""+_AutositeSynchLedger.getTarget());
            if ( ignoreFieldSet || fieldSet.contains("remoteToken")) 
	            json.put("remoteToken", ""+_AutositeSynchLedger.getRemoteToken());
            if ( ignoreFieldSet || fieldSet.contains("objectId")) 
	            json.put("objectId", ""+_AutositeSynchLedger.getObjectId());
            if ( ignoreFieldSet || fieldSet.contains("synchId")) 
	            json.put("synchId", ""+_AutositeSynchLedger.getSynchId());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_AutositeSynchLedger.getTimeCreated());

		}
		return json;

	}

}