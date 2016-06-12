/* 
Template last modification history:


Source Generated: Sat Feb 14 00:17:16 EST 2015
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

import com.autosite.db.SynkNamespaceRecord;


public class SynkNamespaceRecordDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("namespace", new Integer(0));
        fieldsMap.put("recordId", new Integer(1));
        fieldsMap.put("stamp", new Integer(2));
        fieldsMap.put("orgStamp", new Integer(3));
        fieldsMap.put("timeCreated", new Integer(4));
        fieldsMap.put("timeUpdated", new Integer(5));
    }


	SynkNamespaceRecord m_synkNamespaceRecord;

	public SynkNamespaceRecordDataHolder(SynkNamespaceRecord _SynkNamespaceRecord){
		m_synkNamespaceRecord =  _SynkNamespaceRecord; 	
	}

	public SynkNamespaceRecordDataHolder(){
		m_synkNamespaceRecord =  new SynkNamespaceRecord(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_synkNamespaceRecord;
	}


	public long getId(){
		return 	m_synkNamespaceRecord.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_synkNamespaceRecord.getSiteId();
	}
	public void setSiteId(long sid){
		m_synkNamespaceRecord.setSiteId(sid);
	}


	public String getNamespace(){
		return m_synkNamespaceRecord.getNamespace();
	}
	public long getRecordId(){
		return m_synkNamespaceRecord.getRecordId();
	}
	public long getStamp(){
		return m_synkNamespaceRecord.getStamp();
	}
	public long getOrgStamp(){
		return m_synkNamespaceRecord.getOrgStamp();
	}
	public Timestamp getTimeCreated(){
		return m_synkNamespaceRecord.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_synkNamespaceRecord.getTimeUpdated();
	}

	public void setNamespace(String _namespace ){
		m_synkNamespaceRecord.setNamespace(_namespace);
	}
	public void setRecordId(long _recordId ){
		m_synkNamespaceRecord.setRecordId(_recordId);
	}
	public void setStamp(long _stamp ){
		m_synkNamespaceRecord.setStamp(_stamp);
	}
	public void setOrgStamp(long _orgStamp ){
		m_synkNamespaceRecord.setOrgStamp(_orgStamp);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_synkNamespaceRecord.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_synkNamespaceRecord.setTimeUpdated(_timeUpdated);
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
        if ( colNum == 1) return String.valueOf(getRecordId());
        if ( colNum == 2) return String.valueOf(getStamp());
        if ( colNum == 3) return String.valueOf(getOrgStamp());
        if ( colNum == 4) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 5) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }


    public static JSONObject convertToJSON(SynkNamespaceRecord _SynkNamespaceRecord, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_SynkNamespaceRecord, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(SynkNamespaceRecord _SynkNamespaceRecord, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _SynkNamespaceRecord == null) return json;

        json.put("id", ""+_SynkNamespaceRecord.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonNamespace = new JSONObject();
		            jsonNamespace.put("name", "namespace");
		            if ( useJsonDataType ) 
	                    jsonNamespace.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNamespaceRecord.getNamespace()));
		            else
		                jsonNamespace.put("value", JSONValueFormatter.toStringValue(_SynkNamespaceRecord.getNamespace()));
		            array.put(jsonNamespace);
		            JSONObject jsonRecordId = new JSONObject();
		            jsonRecordId.put("name", "recordId");
		            if ( useJsonDataType ) 
	                    jsonRecordId.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNamespaceRecord.getRecordId()));
		            else
		                jsonRecordId.put("value", JSONValueFormatter.toStringValue(_SynkNamespaceRecord.getRecordId()));
		            array.put(jsonRecordId);
		            JSONObject jsonStamp = new JSONObject();
		            jsonStamp.put("name", "stamp");
		            if ( useJsonDataType ) 
	                    jsonStamp.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNamespaceRecord.getStamp()));
		            else
		                jsonStamp.put("value", JSONValueFormatter.toStringValue(_SynkNamespaceRecord.getStamp()));
		            array.put(jsonStamp);
		            JSONObject jsonOrgStamp = new JSONObject();
		            jsonOrgStamp.put("name", "orgStamp");
		            if ( useJsonDataType ) 
	                    jsonOrgStamp.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNamespaceRecord.getOrgStamp()));
		            else
		                jsonOrgStamp.put("value", JSONValueFormatter.toStringValue(_SynkNamespaceRecord.getOrgStamp()));
		            array.put(jsonOrgStamp);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNamespaceRecord.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_SynkNamespaceRecord.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            if ( useJsonDataType ) 
	                    jsonTimeUpdated.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNamespaceRecord.getTimeUpdated()));
		            else
		                jsonTimeUpdated.put("value", JSONValueFormatter.toStringValue(_SynkNamespaceRecord.getTimeUpdated()));
		            array.put(jsonTimeUpdated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("namespace")) 
	            json.put("namespace", ""+_SynkNamespaceRecord.getNamespace());
            if ( ignoreFieldSet || fieldSet.contains("recordId")) 
	            json.put("recordId", ""+_SynkNamespaceRecord.getRecordId());
            if ( ignoreFieldSet || fieldSet.contains("stamp")) 
	            json.put("stamp", ""+_SynkNamespaceRecord.getStamp());
            if ( ignoreFieldSet || fieldSet.contains("orgStamp")) 
	            json.put("orgStamp", ""+_SynkNamespaceRecord.getOrgStamp());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_SynkNamespaceRecord.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
	            json.put("timeUpdated", ""+_SynkNamespaceRecord.getTimeUpdated());

		}
		return json;

	}

}