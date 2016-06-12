/* 
Template last modification history:


Source Generated: Sun Mar 15 01:49:44 EDT 2015
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

import com.autosite.db.GenMain;


public class GenMainDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("active", new Integer(0));
        fieldsMap.put("value", new Integer(1));
        fieldsMap.put("data", new Integer(2));
        fieldsMap.put("required", new Integer(3));
        fieldsMap.put("timeCreated", new Integer(4));
        fieldsMap.put("timeUpdated", new Integer(5));
    }


	GenMain m_genMain;

	public GenMainDataHolder(GenMain _GenMain){
		m_genMain =  _GenMain; 	
	}

	public GenMainDataHolder(){
		m_genMain =  new GenMain(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_genMain;
	}


	public long getId(){
		return 	m_genMain.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_genMain.getSiteId();
	}
	public void setSiteId(long sid){
		m_genMain.setSiteId(sid);
	}


	public int getActive(){
		return m_genMain.getActive();
	}
	public int getValue(){
		return m_genMain.getValue();
	}
	public String getData(){
		return m_genMain.getData();
	}
	public String getRequired(){
		return m_genMain.getRequired();
	}
	public Timestamp getTimeCreated(){
		return m_genMain.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_genMain.getTimeUpdated();
	}

	public void setActive(int _active ){
		m_genMain.setActive(_active);
	}
	public void setValue(int _value ){
		m_genMain.setValue(_value);
	}
	public void setData(String _data ){
		m_genMain.setData(_data);
	}
	public void setRequired(String _required ){
		m_genMain.setRequired(_required);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_genMain.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_genMain.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return String.valueOf(getActive());
        if ( colNum == 1) return String.valueOf(getValue());
        if ( colNum == 2) return getData() == null?"":getData().toString();
        if ( colNum == 3) return getRequired() == null?"":getRequired().toString();
        if ( colNum == 4) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 5) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }


    public static JSONObject convertToJSON(GenMain _GenMain, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_GenMain, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(GenMain _GenMain, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _GenMain == null) return json;

        json.put("id", ""+_GenMain.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonActive = new JSONObject();
		            jsonActive.put("name", "active");
		            if ( useJsonDataType ) 
	                    jsonActive.put("value", JSONValueFormatter.toJsonTypeValue(_GenMain.getActive()));
		            else
		                jsonActive.put("value", JSONValueFormatter.toStringValue(_GenMain.getActive()));
		            array.put(jsonActive);
		            JSONObject jsonValue = new JSONObject();
		            jsonValue.put("name", "value");
		            if ( useJsonDataType ) 
	                    jsonValue.put("value", JSONValueFormatter.toJsonTypeValue(_GenMain.getValue()));
		            else
		                jsonValue.put("value", JSONValueFormatter.toStringValue(_GenMain.getValue()));
		            array.put(jsonValue);
		            JSONObject jsonData = new JSONObject();
		            jsonData.put("name", "data");
		            if ( useJsonDataType ) 
	                    jsonData.put("value", JSONValueFormatter.toJsonTypeValue(_GenMain.getData()));
		            else
		                jsonData.put("value", JSONValueFormatter.toStringValue(_GenMain.getData()));
		            array.put(jsonData);
		            JSONObject jsonRequired = new JSONObject();
		            jsonRequired.put("name", "required");
		            if ( useJsonDataType ) 
	                    jsonRequired.put("value", JSONValueFormatter.toJsonTypeValue(_GenMain.getRequired()));
		            else
		                jsonRequired.put("value", JSONValueFormatter.toStringValue(_GenMain.getRequired()));
		            array.put(jsonRequired);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_GenMain.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_GenMain.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            if ( useJsonDataType ) 
	                    jsonTimeUpdated.put("value", JSONValueFormatter.toJsonTypeValue(_GenMain.getTimeUpdated()));
		            else
		                jsonTimeUpdated.put("value", JSONValueFormatter.toStringValue(_GenMain.getTimeUpdated()));
		            array.put(jsonTimeUpdated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("active")) 
	            json.put("active", ""+_GenMain.getActive());
            if ( ignoreFieldSet || fieldSet.contains("value")) 
	            json.put("value", ""+_GenMain.getValue());
            if ( ignoreFieldSet || fieldSet.contains("data")) 
	            json.put("data", ""+_GenMain.getData());
            if ( ignoreFieldSet || fieldSet.contains("required")) 
	            json.put("required", ""+_GenMain.getRequired());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_GenMain.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
	            json.put("timeUpdated", ""+_GenMain.getTimeUpdated());

		}
		return json;

	}

}