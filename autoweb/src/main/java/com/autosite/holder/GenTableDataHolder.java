/* 
Template last modification history:


Source Generated: Sun Mar 15 14:31:01 EDT 2015
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

import com.autosite.db.GenTable;


public class GenTableDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields
	private String m_extString;
	private int m_extInt;

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("country", new Integer(0));
        fieldsMap.put("age", new Integer(1));
        fieldsMap.put("disabled", new Integer(2));
        fieldsMap.put("comments", new Integer(3));
        fieldsMap.put("timeCreated", new Integer(4));
        fieldsMap.put("timeUpdated", new Integer(5));
        fieldsMap.put("extString", new Integer(6));
        fieldsMap.put("extInt", new Integer(7));
    }


	GenTable m_genTable;

	public GenTableDataHolder(GenTable _GenTable){
		m_genTable =  _GenTable; 	
	}

	public GenTableDataHolder(){
		m_genTable =  new GenTable(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_genTable;
	}


	public long getId(){
		return 	m_genTable.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_genTable.getSiteId();
	}
	public void setSiteId(long sid){
		m_genTable.setSiteId(sid);
	}


	public String getCountry(){
		return m_genTable.getCountry();
	}
	public int getAge(){
		return m_genTable.getAge();
	}
	public int getDisabled(){
		return m_genTable.getDisabled();
	}
	public String getComments(){
		return m_genTable.getComments();
	}
	public Timestamp getTimeCreated(){
		return m_genTable.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_genTable.getTimeUpdated();
	}
	public String getExtString(){
		return m_extString;
	}
	public int getExtInt(){
		return m_extInt;
	}

	public void setCountry(String _country ){
		m_genTable.setCountry(_country);
	}
	public void setAge(int _age ){
		m_genTable.setAge(_age);
	}
	public void setDisabled(int _disabled ){
		m_genTable.setDisabled(_disabled);
	}
	public void setComments(String _comments ){
		m_genTable.setComments(_comments);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_genTable.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_genTable.setTimeUpdated(_timeUpdated);
	}
	public void setExtString(String _extString ){
		m_extString = _extString;
	}
	public void setExtInt(int _extInt ){
		m_extInt = _extInt;
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

        if ( colNum == 0) return getCountry() == null?"":getCountry().toString();
        if ( colNum == 1) return String.valueOf(getAge());
        if ( colNum == 2) return String.valueOf(getDisabled());
        if ( colNum == 3) return getComments() == null?"":getComments().toString();
        if ( colNum == 4) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 5) return getTimeUpdated() == null?"":getTimeUpdated().toString();
        if ( colNum == 6) return getExtString() == null?"":getExtString().toString();
        if ( colNum == 7) return String.valueOf(getExtInt());
		return "";
    }


    public static JSONObject convertToJSON(GenTableDataHolder _GenTable, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_GenTable, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(GenTableDataHolder _GenTable, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _GenTable == null) return json;

        json.put("id", ""+_GenTable.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonCountry = new JSONObject();
		            jsonCountry.put("name", "country");
		            if ( useJsonDataType ) 
	                    jsonCountry.put("value", JSONValueFormatter.toJsonTypeValue(_GenTable.getCountry()));
		            else
		                jsonCountry.put("value", JSONValueFormatter.toStringValue(_GenTable.getCountry()));
		            array.put(jsonCountry);
		            JSONObject jsonAge = new JSONObject();
		            jsonAge.put("name", "age");
		            if ( useJsonDataType ) 
	                    jsonAge.put("value", JSONValueFormatter.toJsonTypeValue(_GenTable.getAge()));
		            else
		                jsonAge.put("value", JSONValueFormatter.toStringValue(_GenTable.getAge()));
		            array.put(jsonAge);
		            JSONObject jsonDisabled = new JSONObject();
		            jsonDisabled.put("name", "disabled");
		            if ( useJsonDataType ) 
	                    jsonDisabled.put("value", JSONValueFormatter.toJsonTypeValue(_GenTable.getDisabled()));
		            else
		                jsonDisabled.put("value", JSONValueFormatter.toStringValue(_GenTable.getDisabled()));
		            array.put(jsonDisabled);
		            JSONObject jsonComments = new JSONObject();
		            jsonComments.put("name", "comments");
		            if ( useJsonDataType ) 
	                    jsonComments.put("value", JSONValueFormatter.toJsonTypeValue(_GenTable.getComments()));
		            else
		                jsonComments.put("value", JSONValueFormatter.toStringValue(_GenTable.getComments()));
		            array.put(jsonComments);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_GenTable.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_GenTable.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            if ( useJsonDataType ) 
	                    jsonTimeUpdated.put("value", JSONValueFormatter.toJsonTypeValue(_GenTable.getTimeUpdated()));
		            else
		                jsonTimeUpdated.put("value", JSONValueFormatter.toStringValue(_GenTable.getTimeUpdated()));
		            array.put(jsonTimeUpdated);
		            JSONObject jsonExtString = new JSONObject();
		            jsonExtString.put("name", "extString");
		            if ( useJsonDataType ) 
	                    jsonExtString.put("value", JSONValueFormatter.toJsonTypeValue(_GenTable.getExtString()));
		            else
		                jsonExtString.put("value", JSONValueFormatter.toStringValue(_GenTable.getExtString()));
		            array.put(jsonExtString);
		            JSONObject jsonExtInt = new JSONObject();
		            jsonExtInt.put("name", "extInt");
		            if ( useJsonDataType ) 
	                    jsonExtInt.put("value", JSONValueFormatter.toJsonTypeValue(_GenTable.getExtInt()));
		            else
		                jsonExtInt.put("value", JSONValueFormatter.toStringValue(_GenTable.getExtInt()));
		            array.put(jsonExtInt);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("country")) 
	            json.put("country", ""+_GenTable.getCountry());
            if ( ignoreFieldSet || fieldSet.contains("age")) 
	            json.put("age", ""+_GenTable.getAge());
            if ( ignoreFieldSet || fieldSet.contains("disabled")) 
	            json.put("disabled", ""+_GenTable.getDisabled());
            if ( ignoreFieldSet || fieldSet.contains("comments")) 
	            json.put("comments", ""+_GenTable.getComments());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_GenTable.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
	            json.put("timeUpdated", ""+_GenTable.getTimeUpdated());
            if ( ignoreFieldSet || fieldSet.contains("extString")) 
	            json.put("extString", ""+_GenTable.getExtString());
            if ( ignoreFieldSet || fieldSet.contains("extInt")) 
	            json.put("extInt", ""+_GenTable.getExtInt());

		}
		return json;

	}

}