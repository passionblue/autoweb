/* 
Template last modification history:


Source Generated: Sun Jul 12 20:19:43 EDT 2015
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

import com.autosite.db.ExpenseItem;


public class ExpenseItemDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("expenseCategoryId", new Integer(0));
        fieldsMap.put("expenseItem", new Integer(1));
        fieldsMap.put("timeCreated", new Integer(2));
        fieldsMap.put("timeUpdated", new Integer(3));
    }


	ExpenseItem m_expenseItem;

	public ExpenseItemDataHolder(ExpenseItem _ExpenseItem){
		m_expenseItem =  _ExpenseItem; 	
	}

	public ExpenseItemDataHolder(){
		m_expenseItem =  new ExpenseItem(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_expenseItem;
	}


	public long getId(){
		return 	m_expenseItem.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_expenseItem.getSiteId();
	}
	public void setSiteId(long sid){
		m_expenseItem.setSiteId(sid);
	}


	public long getExpenseCategoryId(){
		return m_expenseItem.getExpenseCategoryId();
	}
	public String getExpenseItem(){
		return m_expenseItem.getExpenseItem();
	}
	public Timestamp getTimeCreated(){
		return m_expenseItem.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_expenseItem.getTimeUpdated();
	}

	public void setExpenseCategoryId(long _expenseCategoryId ){
		m_expenseItem.setExpenseCategoryId(_expenseCategoryId);
	}
	public void setExpenseItem(String _expenseItem ){
		m_expenseItem.setExpenseItem(_expenseItem);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_expenseItem.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_expenseItem.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return String.valueOf(getExpenseCategoryId());
        if ( colNum == 1) return getExpenseItem() == null?"":getExpenseItem().toString();
        if ( colNum == 2) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 3) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }


    public static JSONObject convertToJSON(ExpenseItem _ExpenseItem, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_ExpenseItem, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(ExpenseItem _ExpenseItem, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _ExpenseItem == null) return json;

        json.put("id", ""+_ExpenseItem.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonExpenseCategoryId = new JSONObject();
		            jsonExpenseCategoryId.put("name", "expenseCategoryId");
		            if ( useJsonDataType ) 
	                    jsonExpenseCategoryId.put("value", JSONValueFormatter.toJsonTypeValue(_ExpenseItem.getExpenseCategoryId()));
		            else
		                jsonExpenseCategoryId.put("value", JSONValueFormatter.toStringValue(_ExpenseItem.getExpenseCategoryId()));
		            array.put(jsonExpenseCategoryId);
		            JSONObject jsonExpenseItem = new JSONObject();
		            jsonExpenseItem.put("name", "expenseItem");
		            if ( useJsonDataType ) 
	                    jsonExpenseItem.put("value", JSONValueFormatter.toJsonTypeValue(_ExpenseItem.getExpenseItem()));
		            else
		                jsonExpenseItem.put("value", JSONValueFormatter.toStringValue(_ExpenseItem.getExpenseItem()));
		            array.put(jsonExpenseItem);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_ExpenseItem.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_ExpenseItem.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            if ( useJsonDataType ) 
	                    jsonTimeUpdated.put("value", JSONValueFormatter.toJsonTypeValue(_ExpenseItem.getTimeUpdated()));
		            else
		                jsonTimeUpdated.put("value", JSONValueFormatter.toStringValue(_ExpenseItem.getTimeUpdated()));
		            array.put(jsonTimeUpdated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("expenseCategoryId")) 
	            json.put("expenseCategoryId", ""+_ExpenseItem.getExpenseCategoryId());
            if ( ignoreFieldSet || fieldSet.contains("expenseItem")) 
	            json.put("expenseItem", ""+_ExpenseItem.getExpenseItem());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_ExpenseItem.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
	            json.put("timeUpdated", ""+_ExpenseItem.getTimeUpdated());

		}
		return json;

	}

}