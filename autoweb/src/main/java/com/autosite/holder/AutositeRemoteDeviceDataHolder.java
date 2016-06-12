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

import com.autosite.db.AutositeRemoteDevice;


public class AutositeRemoteDeviceDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("deviceId", new Integer(0));
        fieldsMap.put("deviceType", new Integer(1));
        fieldsMap.put("timeCreated", new Integer(2));
    }


	AutositeRemoteDevice m_autositeRemoteDevice;

	public AutositeRemoteDeviceDataHolder(AutositeRemoteDevice _AutositeRemoteDevice){
		m_autositeRemoteDevice =  _AutositeRemoteDevice; 	
	}

	public AutositeRemoteDeviceDataHolder(){
		m_autositeRemoteDevice =  new AutositeRemoteDevice(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_autositeRemoteDevice;
	}


	public long getId(){
		return 	m_autositeRemoteDevice.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_autositeRemoteDevice.getSiteId();
	}
	public void setSiteId(long sid){
		m_autositeRemoteDevice.setSiteId(sid);
	}


	public String getDeviceId(){
		return m_autositeRemoteDevice.getDeviceId();
	}
	public int getDeviceType(){
		return m_autositeRemoteDevice.getDeviceType();
	}
	public Timestamp getTimeCreated(){
		return m_autositeRemoteDevice.getTimeCreated();
	}

	public void setDeviceId(String _deviceId ){
		m_autositeRemoteDevice.setDeviceId(_deviceId);
	}
	public void setDeviceType(int _deviceType ){
		m_autositeRemoteDevice.setDeviceType(_deviceType);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_autositeRemoteDevice.setTimeCreated(_timeCreated);
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

        if ( colNum == 0) return getDeviceId() == null?"":getDeviceId().toString();
        if ( colNum == 1) return String.valueOf(getDeviceType());
        if ( colNum == 2) return getTimeCreated() == null?"":getTimeCreated().toString();
		return "";
    }


    public static JSONObject convertToJSON(AutositeRemoteDevice _AutositeRemoteDevice, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        
        JSONObject json = new JSONObject();
        if ( _AutositeRemoteDevice == null) return json;

        json.put("id", ""+_AutositeRemoteDevice.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonDeviceId = new JSONObject();
		            jsonDeviceId.put("name", "deviceId");
		            jsonDeviceId.put("value", ""+_AutositeRemoteDevice.getDeviceId());
		            array.put(jsonDeviceId);
		            JSONObject jsonDeviceType = new JSONObject();
		            jsonDeviceType.put("name", "deviceType");
		            jsonDeviceType.put("value", ""+_AutositeRemoteDevice.getDeviceType());
		            array.put(jsonDeviceType);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            jsonTimeCreated.put("value", ""+_AutositeRemoteDevice.getTimeCreated());
		            array.put(jsonTimeCreated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("deviceId")) 
	            json.put("deviceId", ""+_AutositeRemoteDevice.getDeviceId());
            if ( ignoreFieldSet || fieldSet.contains("deviceType")) 
	            json.put("deviceType", ""+_AutositeRemoteDevice.getDeviceType());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_AutositeRemoteDevice.getTimeCreated());

		}
		return json;

	}

}