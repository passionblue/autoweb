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

import com.autosite.db.CleanerLocationConfig;


public class CleanerLocationConfigDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("locationId", new Integer(0));
        fieldsMap.put("openHourWeekday", new Integer(1));
        fieldsMap.put("closeHourWeekday", new Integer(2));
        fieldsMap.put("openHourSat", new Integer(3));
        fieldsMap.put("closeHourSat", new Integer(4));
        fieldsMap.put("openHourSun", new Integer(5));
        fieldsMap.put("closeHourSun", new Integer(6));
        fieldsMap.put("timeCreated", new Integer(7));
        fieldsMap.put("timeUpdated", new Integer(8));
    }


	CleanerLocationConfig m_cleanerLocationConfig;

	public CleanerLocationConfigDataHolder(CleanerLocationConfig _CleanerLocationConfig){
		m_cleanerLocationConfig =  _CleanerLocationConfig; 	
	}

	public CleanerLocationConfigDataHolder(){
		m_cleanerLocationConfig =  new CleanerLocationConfig(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerLocationConfig;
	}


	public long getId(){
		return 	m_cleanerLocationConfig.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerLocationConfig.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerLocationConfig.setSiteId(sid);
	}


	public long getLocationId(){
		return m_cleanerLocationConfig.getLocationId();
	}
	public String getOpenHourWeekday(){
		return m_cleanerLocationConfig.getOpenHourWeekday();
	}
	public String getCloseHourWeekday(){
		return m_cleanerLocationConfig.getCloseHourWeekday();
	}
	public String getOpenHourSat(){
		return m_cleanerLocationConfig.getOpenHourSat();
	}
	public String getCloseHourSat(){
		return m_cleanerLocationConfig.getCloseHourSat();
	}
	public String getOpenHourSun(){
		return m_cleanerLocationConfig.getOpenHourSun();
	}
	public String getCloseHourSun(){
		return m_cleanerLocationConfig.getCloseHourSun();
	}
	public Timestamp getTimeCreated(){
		return m_cleanerLocationConfig.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_cleanerLocationConfig.getTimeUpdated();
	}

	public void setLocationId(long _locationId ){
		m_cleanerLocationConfig.setLocationId(_locationId);
	}
	public void setOpenHourWeekday(String _openHourWeekday ){
		m_cleanerLocationConfig.setOpenHourWeekday(_openHourWeekday);
	}
	public void setCloseHourWeekday(String _closeHourWeekday ){
		m_cleanerLocationConfig.setCloseHourWeekday(_closeHourWeekday);
	}
	public void setOpenHourSat(String _openHourSat ){
		m_cleanerLocationConfig.setOpenHourSat(_openHourSat);
	}
	public void setCloseHourSat(String _closeHourSat ){
		m_cleanerLocationConfig.setCloseHourSat(_closeHourSat);
	}
	public void setOpenHourSun(String _openHourSun ){
		m_cleanerLocationConfig.setOpenHourSun(_openHourSun);
	}
	public void setCloseHourSun(String _closeHourSun ){
		m_cleanerLocationConfig.setCloseHourSun(_closeHourSun);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_cleanerLocationConfig.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_cleanerLocationConfig.setTimeUpdated(_timeUpdated);
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
        if ( colNum == 1) return getOpenHourWeekday() == null?"":getOpenHourWeekday().toString();
        if ( colNum == 2) return getCloseHourWeekday() == null?"":getCloseHourWeekday().toString();
        if ( colNum == 3) return getOpenHourSat() == null?"":getOpenHourSat().toString();
        if ( colNum == 4) return getCloseHourSat() == null?"":getCloseHourSat().toString();
        if ( colNum == 5) return getOpenHourSun() == null?"":getOpenHourSun().toString();
        if ( colNum == 6) return getCloseHourSun() == null?"":getCloseHourSun().toString();
        if ( colNum == 7) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 8) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }


    public static JSONObject convertToJSON(CleanerLocationConfig _CleanerLocationConfig, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        
        JSONObject json = new JSONObject();
        if ( _CleanerLocationConfig == null) return json;

        json.put("id", ""+_CleanerLocationConfig.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonLocationId = new JSONObject();
		            jsonLocationId.put("name", "locationId");
		            jsonLocationId.put("value", JSONValueFormatter.toStringValue(_CleanerLocationConfig.getLocationId()));
		            array.put(jsonLocationId);
		            JSONObject jsonOpenHourWeekday = new JSONObject();
		            jsonOpenHourWeekday.put("name", "openHourWeekday");
		            jsonOpenHourWeekday.put("value", JSONValueFormatter.toStringValue(_CleanerLocationConfig.getOpenHourWeekday()));
		            array.put(jsonOpenHourWeekday);
		            JSONObject jsonCloseHourWeekday = new JSONObject();
		            jsonCloseHourWeekday.put("name", "closeHourWeekday");
		            jsonCloseHourWeekday.put("value", JSONValueFormatter.toStringValue(_CleanerLocationConfig.getCloseHourWeekday()));
		            array.put(jsonCloseHourWeekday);
		            JSONObject jsonOpenHourSat = new JSONObject();
		            jsonOpenHourSat.put("name", "openHourSat");
		            jsonOpenHourSat.put("value", JSONValueFormatter.toStringValue(_CleanerLocationConfig.getOpenHourSat()));
		            array.put(jsonOpenHourSat);
		            JSONObject jsonCloseHourSat = new JSONObject();
		            jsonCloseHourSat.put("name", "closeHourSat");
		            jsonCloseHourSat.put("value", JSONValueFormatter.toStringValue(_CleanerLocationConfig.getCloseHourSat()));
		            array.put(jsonCloseHourSat);
		            JSONObject jsonOpenHourSun = new JSONObject();
		            jsonOpenHourSun.put("name", "openHourSun");
		            jsonOpenHourSun.put("value", JSONValueFormatter.toStringValue(_CleanerLocationConfig.getOpenHourSun()));
		            array.put(jsonOpenHourSun);
		            JSONObject jsonCloseHourSun = new JSONObject();
		            jsonCloseHourSun.put("name", "closeHourSun");
		            jsonCloseHourSun.put("value", JSONValueFormatter.toStringValue(_CleanerLocationConfig.getCloseHourSun()));
		            array.put(jsonCloseHourSun);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_CleanerLocationConfig.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            jsonTimeUpdated.put("value", JSONValueFormatter.toStringValue(_CleanerLocationConfig.getTimeUpdated()));
		            array.put(jsonTimeUpdated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("locationId")) 
	            json.put("locationId", ""+_CleanerLocationConfig.getLocationId());
            if ( ignoreFieldSet || fieldSet.contains("openHourWeekday")) 
	            json.put("openHourWeekday", ""+_CleanerLocationConfig.getOpenHourWeekday());
            if ( ignoreFieldSet || fieldSet.contains("closeHourWeekday")) 
	            json.put("closeHourWeekday", ""+_CleanerLocationConfig.getCloseHourWeekday());
            if ( ignoreFieldSet || fieldSet.contains("openHourSat")) 
	            json.put("openHourSat", ""+_CleanerLocationConfig.getOpenHourSat());
            if ( ignoreFieldSet || fieldSet.contains("closeHourSat")) 
	            json.put("closeHourSat", ""+_CleanerLocationConfig.getCloseHourSat());
            if ( ignoreFieldSet || fieldSet.contains("openHourSun")) 
	            json.put("openHourSun", ""+_CleanerLocationConfig.getOpenHourSun());
            if ( ignoreFieldSet || fieldSet.contains("closeHourSun")) 
	            json.put("closeHourSun", ""+_CleanerLocationConfig.getCloseHourSun());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_CleanerLocationConfig.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
	            json.put("timeUpdated", ""+_CleanerLocationConfig.getTimeUpdated());

		}
		return json;

	}

}