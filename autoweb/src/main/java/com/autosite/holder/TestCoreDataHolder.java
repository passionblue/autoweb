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

import com.autosite.db.TestCore;


public class TestCoreDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("data", new Integer(0));
        fieldsMap.put("timeCreated", new Integer(1));
    }


	TestCore m_testCore;

	public TestCoreDataHolder(TestCore _TestCore){
		m_testCore =  _TestCore; 	
	}

	public TestCoreDataHolder(){
		m_testCore =  new TestCore(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_testCore;
	}


	public long getId(){
		return 	m_testCore.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_testCore.getSiteId();
	}
	public void setSiteId(long sid){
		m_testCore.setSiteId(sid);
	}


	public String getData(){
		return m_testCore.getData();
	}
	public Timestamp getTimeCreated(){
		return m_testCore.getTimeCreated();
	}

	public void setData(String _data ){
		m_testCore.setData(_data);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_testCore.setTimeCreated(_timeCreated);
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

        if ( colNum == 0) return getData() == null?"":getData().toString();
        if ( colNum == 1) return getTimeCreated() == null?"":getTimeCreated().toString();
		return "";
    }


    public static JSONObject convertToJSON(TestCore _TestCore, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_TestCore, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(TestCore _TestCore, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _TestCore == null) return json;

        json.put("id", ""+_TestCore.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonData = new JSONObject();
		            jsonData.put("name", "data");
		            if ( useJsonDataType ) 
	                    jsonData.put("value", JSONValueFormatter.toJsonTypeValue(_TestCore.getData()));
		            else
		                jsonData.put("value", JSONValueFormatter.toStringValue(_TestCore.getData()));
		            array.put(jsonData);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_TestCore.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_TestCore.getTimeCreated()));
		            array.put(jsonTimeCreated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("data")) 
	            json.put("data", ""+_TestCore.getData());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_TestCore.getTimeCreated());

		}
		return json;

	}

}