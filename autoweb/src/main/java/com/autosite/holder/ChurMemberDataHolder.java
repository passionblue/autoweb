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

import com.autosite.db.ChurMember;


public class ChurMemberDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("fullName", new Integer(0));
        fieldsMap.put("firstName", new Integer(1));
        fieldsMap.put("lastName", new Integer(2));
        fieldsMap.put("title", new Integer(3));
        fieldsMap.put("otherName", new Integer(4));
        fieldsMap.put("household", new Integer(5));
        fieldsMap.put("householdId", new Integer(6));
        fieldsMap.put("isGroup", new Integer(7));
        fieldsMap.put("isGuest", new Integer(8));
        fieldsMap.put("isSpeaker", new Integer(9));
        fieldsMap.put("timeCreated", new Integer(10));
        fieldsMap.put("listIndex", new Integer(11));
    }


	ChurMember m_churMember;

	public ChurMemberDataHolder(ChurMember _ChurMember){
		m_churMember =  _ChurMember; 	
	}

	public ChurMemberDataHolder(){
		m_churMember =  new ChurMember(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_churMember;
	}


	public long getId(){
		return 	m_churMember.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_churMember.getSiteId();
	}
	public void setSiteId(long sid){
		m_churMember.setSiteId(sid);
	}


	public String getFullName(){
		return m_churMember.getFullName();
	}
	public String getFirstName(){
		return m_churMember.getFirstName();
	}
	public String getLastName(){
		return m_churMember.getLastName();
	}
	public String getTitle(){
		return m_churMember.getTitle();
	}
	public String getOtherName(){
		return m_churMember.getOtherName();
	}
	public int getHousehold(){
		return m_churMember.getHousehold();
	}
	public long getHouseholdId(){
		return m_churMember.getHouseholdId();
	}
	public int getIsGroup(){
		return m_churMember.getIsGroup();
	}
	public int getIsGuest(){
		return m_churMember.getIsGuest();
	}
	public int getIsSpeaker(){
		return m_churMember.getIsSpeaker();
	}
	public Timestamp getTimeCreated(){
		return m_churMember.getTimeCreated();
	}
	public int getListIndex(){
		return m_churMember.getListIndex();
	}

	public void setFullName(String _fullName ){
		m_churMember.setFullName(_fullName);
	}
	public void setFirstName(String _firstName ){
		m_churMember.setFirstName(_firstName);
	}
	public void setLastName(String _lastName ){
		m_churMember.setLastName(_lastName);
	}
	public void setTitle(String _title ){
		m_churMember.setTitle(_title);
	}
	public void setOtherName(String _otherName ){
		m_churMember.setOtherName(_otherName);
	}
	public void setHousehold(int _household ){
		m_churMember.setHousehold(_household);
	}
	public void setHouseholdId(long _householdId ){
		m_churMember.setHouseholdId(_householdId);
	}
	public void setIsGroup(int _isGroup ){
		m_churMember.setIsGroup(_isGroup);
	}
	public void setIsGuest(int _isGuest ){
		m_churMember.setIsGuest(_isGuest);
	}
	public void setIsSpeaker(int _isSpeaker ){
		m_churMember.setIsSpeaker(_isSpeaker);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_churMember.setTimeCreated(_timeCreated);
	}
	public void setListIndex(int _listIndex ){
		m_churMember.setListIndex(_listIndex);
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

        if ( colNum == 0) return getFullName() == null?"":getFullName().toString();
        if ( colNum == 1) return getFirstName() == null?"":getFirstName().toString();
        if ( colNum == 2) return getLastName() == null?"":getLastName().toString();
        if ( colNum == 3) return getTitle() == null?"":getTitle().toString();
        if ( colNum == 4) return getOtherName() == null?"":getOtherName().toString();
        if ( colNum == 5) return String.valueOf(getHousehold());
        if ( colNum == 6) return String.valueOf(getHouseholdId());
        if ( colNum == 7) return String.valueOf(getIsGroup());
        if ( colNum == 8) return String.valueOf(getIsGuest());
        if ( colNum == 9) return String.valueOf(getIsSpeaker());
        if ( colNum == 10) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 11) return String.valueOf(getListIndex());
		return "";
    }


    public static JSONObject convertToJSON(ChurMember _ChurMember, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_ChurMember, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(ChurMember _ChurMember, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _ChurMember == null) return json;

        json.put("id", ""+_ChurMember.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonFullName = new JSONObject();
		            jsonFullName.put("name", "fullName");
		            if ( useJsonDataType ) 
	                    jsonFullName.put("value", JSONValueFormatter.toJsonTypeValue(_ChurMember.getFullName()));
		            else
		                jsonFullName.put("value", JSONValueFormatter.toStringValue(_ChurMember.getFullName()));
		            array.put(jsonFullName);
		            JSONObject jsonFirstName = new JSONObject();
		            jsonFirstName.put("name", "firstName");
		            if ( useJsonDataType ) 
	                    jsonFirstName.put("value", JSONValueFormatter.toJsonTypeValue(_ChurMember.getFirstName()));
		            else
		                jsonFirstName.put("value", JSONValueFormatter.toStringValue(_ChurMember.getFirstName()));
		            array.put(jsonFirstName);
		            JSONObject jsonLastName = new JSONObject();
		            jsonLastName.put("name", "lastName");
		            if ( useJsonDataType ) 
	                    jsonLastName.put("value", JSONValueFormatter.toJsonTypeValue(_ChurMember.getLastName()));
		            else
		                jsonLastName.put("value", JSONValueFormatter.toStringValue(_ChurMember.getLastName()));
		            array.put(jsonLastName);
		            JSONObject jsonTitle = new JSONObject();
		            jsonTitle.put("name", "title");
		            if ( useJsonDataType ) 
	                    jsonTitle.put("value", JSONValueFormatter.toJsonTypeValue(_ChurMember.getTitle()));
		            else
		                jsonTitle.put("value", JSONValueFormatter.toStringValue(_ChurMember.getTitle()));
		            array.put(jsonTitle);
		            JSONObject jsonOtherName = new JSONObject();
		            jsonOtherName.put("name", "otherName");
		            if ( useJsonDataType ) 
	                    jsonOtherName.put("value", JSONValueFormatter.toJsonTypeValue(_ChurMember.getOtherName()));
		            else
		                jsonOtherName.put("value", JSONValueFormatter.toStringValue(_ChurMember.getOtherName()));
		            array.put(jsonOtherName);
		            JSONObject jsonHousehold = new JSONObject();
		            jsonHousehold.put("name", "household");
		            if ( useJsonDataType ) 
	                    jsonHousehold.put("value", JSONValueFormatter.toJsonTypeValue(_ChurMember.getHousehold()));
		            else
		                jsonHousehold.put("value", JSONValueFormatter.toStringValue(_ChurMember.getHousehold()));
		            array.put(jsonHousehold);
		            JSONObject jsonHouseholdId = new JSONObject();
		            jsonHouseholdId.put("name", "householdId");
		            if ( useJsonDataType ) 
	                    jsonHouseholdId.put("value", JSONValueFormatter.toJsonTypeValue(_ChurMember.getHouseholdId()));
		            else
		                jsonHouseholdId.put("value", JSONValueFormatter.toStringValue(_ChurMember.getHouseholdId()));
		            array.put(jsonHouseholdId);
		            JSONObject jsonIsGroup = new JSONObject();
		            jsonIsGroup.put("name", "isGroup");
		            if ( useJsonDataType ) 
	                    jsonIsGroup.put("value", JSONValueFormatter.toJsonTypeValue(_ChurMember.getIsGroup()));
		            else
		                jsonIsGroup.put("value", JSONValueFormatter.toStringValue(_ChurMember.getIsGroup()));
		            array.put(jsonIsGroup);
		            JSONObject jsonIsGuest = new JSONObject();
		            jsonIsGuest.put("name", "isGuest");
		            if ( useJsonDataType ) 
	                    jsonIsGuest.put("value", JSONValueFormatter.toJsonTypeValue(_ChurMember.getIsGuest()));
		            else
		                jsonIsGuest.put("value", JSONValueFormatter.toStringValue(_ChurMember.getIsGuest()));
		            array.put(jsonIsGuest);
		            JSONObject jsonIsSpeaker = new JSONObject();
		            jsonIsSpeaker.put("name", "isSpeaker");
		            if ( useJsonDataType ) 
	                    jsonIsSpeaker.put("value", JSONValueFormatter.toJsonTypeValue(_ChurMember.getIsSpeaker()));
		            else
		                jsonIsSpeaker.put("value", JSONValueFormatter.toStringValue(_ChurMember.getIsSpeaker()));
		            array.put(jsonIsSpeaker);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_ChurMember.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_ChurMember.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonListIndex = new JSONObject();
		            jsonListIndex.put("name", "listIndex");
		            if ( useJsonDataType ) 
	                    jsonListIndex.put("value", JSONValueFormatter.toJsonTypeValue(_ChurMember.getListIndex()));
		            else
		                jsonListIndex.put("value", JSONValueFormatter.toStringValue(_ChurMember.getListIndex()));
		            array.put(jsonListIndex);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("fullName")) 
	            json.put("fullName", ""+_ChurMember.getFullName());
            if ( ignoreFieldSet || fieldSet.contains("firstName")) 
	            json.put("firstName", ""+_ChurMember.getFirstName());
            if ( ignoreFieldSet || fieldSet.contains("lastName")) 
	            json.put("lastName", ""+_ChurMember.getLastName());
            if ( ignoreFieldSet || fieldSet.contains("title")) 
	            json.put("title", ""+_ChurMember.getTitle());
            if ( ignoreFieldSet || fieldSet.contains("otherName")) 
	            json.put("otherName", ""+_ChurMember.getOtherName());
            if ( ignoreFieldSet || fieldSet.contains("household")) 
	            json.put("household", ""+_ChurMember.getHousehold());
            if ( ignoreFieldSet || fieldSet.contains("householdId")) 
	            json.put("householdId", ""+_ChurMember.getHouseholdId());
            if ( ignoreFieldSet || fieldSet.contains("isGroup")) 
	            json.put("isGroup", ""+_ChurMember.getIsGroup());
            if ( ignoreFieldSet || fieldSet.contains("isGuest")) 
	            json.put("isGuest", ""+_ChurMember.getIsGuest());
            if ( ignoreFieldSet || fieldSet.contains("isSpeaker")) 
	            json.put("isSpeaker", ""+_ChurMember.getIsSpeaker());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_ChurMember.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("listIndex")) 
	            json.put("listIndex", ""+_ChurMember.getListIndex());

		}
		return json;

	}

}