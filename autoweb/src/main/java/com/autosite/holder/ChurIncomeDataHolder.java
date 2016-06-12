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

import com.autosite.db.ChurIncome;


public class ChurIncomeDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("year", new Integer(0));
        fieldsMap.put("week", new Integer(1));
        fieldsMap.put("churMemberId", new Integer(2));
        fieldsMap.put("incomeItemId", new Integer(3));
        fieldsMap.put("ammount", new Integer(4));
        fieldsMap.put("timeCreated", new Integer(5));
    }


	ChurIncome m_churIncome;

	public ChurIncomeDataHolder(ChurIncome _ChurIncome){
		m_churIncome =  _ChurIncome; 	
	}

	public ChurIncomeDataHolder(){
		m_churIncome =  new ChurIncome(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_churIncome;
	}


	public long getId(){
		return 	m_churIncome.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_churIncome.getSiteId();
	}
	public void setSiteId(long sid){
		m_churIncome.setSiteId(sid);
	}


	public int getYear(){
		return m_churIncome.getYear();
	}
	public String getWeek(){
		return m_churIncome.getWeek();
	}
	public long getChurMemberId(){
		return m_churIncome.getChurMemberId();
	}
	public long getIncomeItemId(){
		return m_churIncome.getIncomeItemId();
	}
	public double getAmmount(){
		return m_churIncome.getAmmount();
	}
	public Timestamp getTimeCreated(){
		return m_churIncome.getTimeCreated();
	}

	public void setYear(int _year ){
		m_churIncome.setYear(_year);
	}
	public void setWeek(String _week ){
		m_churIncome.setWeek(_week);
	}
	public void setChurMemberId(long _churMemberId ){
		m_churIncome.setChurMemberId(_churMemberId);
	}
	public void setIncomeItemId(long _incomeItemId ){
		m_churIncome.setIncomeItemId(_incomeItemId);
	}
	public void setAmmount(double _ammount ){
		m_churIncome.setAmmount(_ammount);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_churIncome.setTimeCreated(_timeCreated);
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

        if ( colNum == 0) return String.valueOf(getYear());
        if ( colNum == 1) return getWeek() == null?"":getWeek().toString();
        if ( colNum == 2) return String.valueOf(getChurMemberId());
        if ( colNum == 3) return String.valueOf(getIncomeItemId());
        if ( colNum == 4) return String.valueOf(getAmmount());
        if ( colNum == 5) return getTimeCreated() == null?"":getTimeCreated().toString();
		return "";
    }


    public static JSONObject convertToJSON(ChurIncomeDataHolder _ChurIncome, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_ChurIncome, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(ChurIncomeDataHolder _ChurIncome, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _ChurIncome == null) return json;

        json.put("id", ""+_ChurIncome.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonYear = new JSONObject();
		            jsonYear.put("name", "year");
		            if ( useJsonDataType ) 
	                    jsonYear.put("value", JSONValueFormatter.toJsonTypeValue(_ChurIncome.getYear()));
		            else
		                jsonYear.put("value", JSONValueFormatter.toStringValue(_ChurIncome.getYear()));
		            array.put(jsonYear);
		            JSONObject jsonWeek = new JSONObject();
		            jsonWeek.put("name", "week");
		            if ( useJsonDataType ) 
	                    jsonWeek.put("value", JSONValueFormatter.toJsonTypeValue(_ChurIncome.getWeek()));
		            else
		                jsonWeek.put("value", JSONValueFormatter.toStringValue(_ChurIncome.getWeek()));
		            array.put(jsonWeek);
		            JSONObject jsonChurMemberId = new JSONObject();
		            jsonChurMemberId.put("name", "churMemberId");
		            if ( useJsonDataType ) 
	                    jsonChurMemberId.put("value", JSONValueFormatter.toJsonTypeValue(_ChurIncome.getChurMemberId()));
		            else
		                jsonChurMemberId.put("value", JSONValueFormatter.toStringValue(_ChurIncome.getChurMemberId()));
		            array.put(jsonChurMemberId);
		            JSONObject jsonIncomeItemId = new JSONObject();
		            jsonIncomeItemId.put("name", "incomeItemId");
		            if ( useJsonDataType ) 
	                    jsonIncomeItemId.put("value", JSONValueFormatter.toJsonTypeValue(_ChurIncome.getIncomeItemId()));
		            else
		                jsonIncomeItemId.put("value", JSONValueFormatter.toStringValue(_ChurIncome.getIncomeItemId()));
		            array.put(jsonIncomeItemId);
		            JSONObject jsonAmmount = new JSONObject();
		            jsonAmmount.put("name", "ammount");
		            if ( useJsonDataType ) 
	                    jsonAmmount.put("value", JSONValueFormatter.toJsonTypeValue(_ChurIncome.getAmmount()));
		            else
		                jsonAmmount.put("value", JSONValueFormatter.toStringValue(_ChurIncome.getAmmount()));
		            array.put(jsonAmmount);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_ChurIncome.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_ChurIncome.getTimeCreated()));
		            array.put(jsonTimeCreated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("year")) 
	            json.put("year", ""+_ChurIncome.getYear());
            if ( ignoreFieldSet || fieldSet.contains("week")) 
	            json.put("week", ""+_ChurIncome.getWeek());
            if ( ignoreFieldSet || fieldSet.contains("churMemberId")) 
	            json.put("churMemberId", ""+_ChurIncome.getChurMemberId());
            if ( ignoreFieldSet || fieldSet.contains("incomeItemId")) 
	            json.put("incomeItemId", ""+_ChurIncome.getIncomeItemId());
            if ( ignoreFieldSet || fieldSet.contains("ammount")) 
	            json.put("ammount", ""+_ChurIncome.getAmmount());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_ChurIncome.getTimeCreated());

		}
		return json;

	}

}