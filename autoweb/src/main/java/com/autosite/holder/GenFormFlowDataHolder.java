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



public class GenFormFlowDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields
	private String m_extString;
	private int m_extInt;

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("extString", new Integer(0));
        fieldsMap.put("extInt", new Integer(1));
    }



	private long m_id = 0;
	private long m_siteId = 0;

	public GenFormFlowDataHolder(){
	}

	public long getId(){
		return m_id;
	}
	public void setId(long id){
		m_id = id;
	}
	public long getSiteId(){
		return m_siteId;
	}
	public void setSiteId(long sid){
		m_siteId = sid;
	}

	public  BaseAutositeDataObject getDataObject(){
		return null;
	}


	public String getExtString(){
		return m_extString;
	}
	public int getExtInt(){
		return m_extInt;
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

        if ( colNum == 0) return getExtString() == null?"":getExtString().toString();
        if ( colNum == 1) return String.valueOf(getExtInt());
		return "";
    }


    public static JSONObject convertToJSON(GenFormFlowDataHolder _GenFormFlow, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_GenFormFlow, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(GenFormFlowDataHolder _GenFormFlow, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _GenFormFlow == null) return json;

        json.put("id", ""+_GenFormFlow.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonExtString = new JSONObject();
		            jsonExtString.put("name", "extString");
		            if ( useJsonDataType ) 
	                    jsonExtString.put("value", JSONValueFormatter.toJsonTypeValue(_GenFormFlow.getExtString()));
		            else
		                jsonExtString.put("value", JSONValueFormatter.toStringValue(_GenFormFlow.getExtString()));
		            array.put(jsonExtString);
		            JSONObject jsonExtInt = new JSONObject();
		            jsonExtInt.put("name", "extInt");
		            if ( useJsonDataType ) 
	                    jsonExtInt.put("value", JSONValueFormatter.toJsonTypeValue(_GenFormFlow.getExtInt()));
		            else
		                jsonExtInt.put("value", JSONValueFormatter.toStringValue(_GenFormFlow.getExtInt()));
		            array.put(jsonExtInt);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("extString")) 
	            json.put("extString", ""+_GenFormFlow.getExtString());
            if ( ignoreFieldSet || fieldSet.contains("extInt")) 
	            json.put("extInt", ""+_GenFormFlow.getExtInt());

		}
		return json;

	}

}