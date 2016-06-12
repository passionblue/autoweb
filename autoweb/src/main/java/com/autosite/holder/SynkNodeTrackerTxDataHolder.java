/* 
Template last modification history:


Source Generated: Sat Feb 14 00:12:25 EST 2015
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

import com.autosite.db.SynkNodeTrackerTx;


public class SynkNodeTrackerTxDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("namespace", new Integer(0));
        fieldsMap.put("deviceId", new Integer(1));
        fieldsMap.put("txToken", new Integer(2));
        fieldsMap.put("stampAcked", new Integer(3));
        fieldsMap.put("stampLast", new Integer(4));
        fieldsMap.put("numRecords", new Integer(5));
        fieldsMap.put("ip", new Integer(6));
        fieldsMap.put("timeCreated", new Integer(7));
    }


	SynkNodeTrackerTx m_synkNodeTrackerTx;

	public SynkNodeTrackerTxDataHolder(SynkNodeTrackerTx _SynkNodeTrackerTx){
		m_synkNodeTrackerTx =  _SynkNodeTrackerTx; 	
	}

	public SynkNodeTrackerTxDataHolder(){
		m_synkNodeTrackerTx =  new SynkNodeTrackerTx(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_synkNodeTrackerTx;
	}


	public long getId(){
		return 	m_synkNodeTrackerTx.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_synkNodeTrackerTx.getSiteId();
	}
	public void setSiteId(long sid){
		m_synkNodeTrackerTx.setSiteId(sid);
	}


	public String getNamespace(){
		return m_synkNodeTrackerTx.getNamespace();
	}
	public String getDeviceId(){
		return m_synkNodeTrackerTx.getDeviceId();
	}
	public String getTxToken(){
		return m_synkNodeTrackerTx.getTxToken();
	}
	public long getStampAcked(){
		return m_synkNodeTrackerTx.getStampAcked();
	}
	public long getStampLast(){
		return m_synkNodeTrackerTx.getStampLast();
	}
	public int getNumRecords(){
		return m_synkNodeTrackerTx.getNumRecords();
	}
	public String getIp(){
		return m_synkNodeTrackerTx.getIp();
	}
	public Timestamp getTimeCreated(){
		return m_synkNodeTrackerTx.getTimeCreated();
	}

	public void setNamespace(String _namespace ){
		m_synkNodeTrackerTx.setNamespace(_namespace);
	}
	public void setDeviceId(String _deviceId ){
		m_synkNodeTrackerTx.setDeviceId(_deviceId);
	}
	public void setTxToken(String _txToken ){
		m_synkNodeTrackerTx.setTxToken(_txToken);
	}
	public void setStampAcked(long _stampAcked ){
		m_synkNodeTrackerTx.setStampAcked(_stampAcked);
	}
	public void setStampLast(long _stampLast ){
		m_synkNodeTrackerTx.setStampLast(_stampLast);
	}
	public void setNumRecords(int _numRecords ){
		m_synkNodeTrackerTx.setNumRecords(_numRecords);
	}
	public void setIp(String _ip ){
		m_synkNodeTrackerTx.setIp(_ip);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_synkNodeTrackerTx.setTimeCreated(_timeCreated);
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
        if ( colNum == 2) return getTxToken() == null?"":getTxToken().toString();
        if ( colNum == 3) return String.valueOf(getStampAcked());
        if ( colNum == 4) return String.valueOf(getStampLast());
        if ( colNum == 5) return String.valueOf(getNumRecords());
        if ( colNum == 6) return getIp() == null?"":getIp().toString();
        if ( colNum == 7) return getTimeCreated() == null?"":getTimeCreated().toString();
		return "";
    }


    public static JSONObject convertToJSON(SynkNodeTrackerTx _SynkNodeTrackerTx, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_SynkNodeTrackerTx, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(SynkNodeTrackerTx _SynkNodeTrackerTx, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _SynkNodeTrackerTx == null) return json;

        json.put("id", ""+_SynkNodeTrackerTx.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonNamespace = new JSONObject();
		            jsonNamespace.put("name", "namespace");
		            if ( useJsonDataType ) 
	                    jsonNamespace.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTrackerTx.getNamespace()));
		            else
		                jsonNamespace.put("value", JSONValueFormatter.toStringValue(_SynkNodeTrackerTx.getNamespace()));
		            array.put(jsonNamespace);
		            JSONObject jsonDeviceId = new JSONObject();
		            jsonDeviceId.put("name", "deviceId");
		            if ( useJsonDataType ) 
	                    jsonDeviceId.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTrackerTx.getDeviceId()));
		            else
		                jsonDeviceId.put("value", JSONValueFormatter.toStringValue(_SynkNodeTrackerTx.getDeviceId()));
		            array.put(jsonDeviceId);
		            JSONObject jsonTxToken = new JSONObject();
		            jsonTxToken.put("name", "txToken");
		            if ( useJsonDataType ) 
	                    jsonTxToken.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTrackerTx.getTxToken()));
		            else
		                jsonTxToken.put("value", JSONValueFormatter.toStringValue(_SynkNodeTrackerTx.getTxToken()));
		            array.put(jsonTxToken);
		            JSONObject jsonStampAcked = new JSONObject();
		            jsonStampAcked.put("name", "stampAcked");
		            if ( useJsonDataType ) 
	                    jsonStampAcked.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTrackerTx.getStampAcked()));
		            else
		                jsonStampAcked.put("value", JSONValueFormatter.toStringValue(_SynkNodeTrackerTx.getStampAcked()));
		            array.put(jsonStampAcked);
		            JSONObject jsonStampLast = new JSONObject();
		            jsonStampLast.put("name", "stampLast");
		            if ( useJsonDataType ) 
	                    jsonStampLast.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTrackerTx.getStampLast()));
		            else
		                jsonStampLast.put("value", JSONValueFormatter.toStringValue(_SynkNodeTrackerTx.getStampLast()));
		            array.put(jsonStampLast);
		            JSONObject jsonNumRecords = new JSONObject();
		            jsonNumRecords.put("name", "numRecords");
		            if ( useJsonDataType ) 
	                    jsonNumRecords.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTrackerTx.getNumRecords()));
		            else
		                jsonNumRecords.put("value", JSONValueFormatter.toStringValue(_SynkNodeTrackerTx.getNumRecords()));
		            array.put(jsonNumRecords);
		            JSONObject jsonIp = new JSONObject();
		            jsonIp.put("name", "ip");
		            if ( useJsonDataType ) 
	                    jsonIp.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTrackerTx.getIp()));
		            else
		                jsonIp.put("value", JSONValueFormatter.toStringValue(_SynkNodeTrackerTx.getIp()));
		            array.put(jsonIp);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_SynkNodeTrackerTx.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_SynkNodeTrackerTx.getTimeCreated()));
		            array.put(jsonTimeCreated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("namespace")) 
	            json.put("namespace", ""+_SynkNodeTrackerTx.getNamespace());
            if ( ignoreFieldSet || fieldSet.contains("deviceId")) 
	            json.put("deviceId", ""+_SynkNodeTrackerTx.getDeviceId());
            if ( ignoreFieldSet || fieldSet.contains("txToken")) 
	            json.put("txToken", ""+_SynkNodeTrackerTx.getTxToken());
            if ( ignoreFieldSet || fieldSet.contains("stampAcked")) 
	            json.put("stampAcked", ""+_SynkNodeTrackerTx.getStampAcked());
            if ( ignoreFieldSet || fieldSet.contains("stampLast")) 
	            json.put("stampLast", ""+_SynkNodeTrackerTx.getStampLast());
            if ( ignoreFieldSet || fieldSet.contains("numRecords")) 
	            json.put("numRecords", ""+_SynkNodeTrackerTx.getNumRecords());
            if ( ignoreFieldSet || fieldSet.contains("ip")) 
	            json.put("ip", ""+_SynkNodeTrackerTx.getIp());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_SynkNodeTrackerTx.getTimeCreated());

		}
		return json;

	}

}