/* 
Template last modification history:


Source Generated: Sun Jul 12 20:19:41 EDT 2015
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

import com.autosite.db.ExpenseCategory;


public class ExpenseCategoryDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("expenseCategory", new Integer(0));
        fieldsMap.put("timeCreated", new Integer(1));
        fieldsMap.put("timeUpdated", new Integer(2));
    }


	ExpenseCategory m_expenseCategory;

	public ExpenseCategoryDataHolder(ExpenseCategory _ExpenseCategory){
		m_expenseCategory =  _ExpenseCategory; 	
	}

	public ExpenseCategoryDataHolder(){
		m_expenseCategory =  new ExpenseCategory(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_expenseCategory;
	}


	public long getId(){
		return 	m_expenseCategory.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_expenseCategory.getSiteId();
	}
	public void setSiteId(long sid){
		m_expenseCategory.setSiteId(sid);
	}


	public String getExpenseCategory(){
		return m_expenseCategory.getExpenseCategory();
	}
	public Timestamp getTimeCreated(){
		return m_expenseCategory.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_expenseCategory.getTimeUpdated();
	}

	public void setExpenseCategory(String _expenseCategory ){
		m_expenseCategory.setExpenseCategory(_expenseCategory);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_expenseCategory.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_expenseCategory.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return getExpenseCategory() == null?"":getExpenseCategory().toString();
        if ( colNum == 1) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 2) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }


    public static JSONObject convertToJSON(ExpenseCategory _ExpenseCategory, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_ExpenseCategory, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(ExpenseCategory _ExpenseCategory, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _ExpenseCategory == null) return json;

        json.put("id", ""+_ExpenseCategory.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonExpenseCategory = new JSONObject();
		            jsonExpenseCategory.put("name", "expenseCategory");
		            if ( useJsonDataType ) 
	                    jsonExpenseCategory.put("value", JSONValueFormatter.toJsonTypeValue(_ExpenseCategory.getExpenseCategory()));
		            else
		                jsonExpenseCategory.put("value", JSONValueFormatter.toStringValue(_ExpenseCategory.getExpenseCategory()));
		            array.put(jsonExpenseCategory);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_ExpenseCategory.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_ExpenseCategory.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            if ( useJsonDataType ) 
	                    jsonTimeUpdated.put("value", JSONValueFormatter.toJsonTypeValue(_ExpenseCategory.getTimeUpdated()));
		            else
		                jsonTimeUpdated.put("value", JSONValueFormatter.toStringValue(_ExpenseCategory.getTimeUpdated()));
		            array.put(jsonTimeUpdated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("expenseCategory")) 
	            json.put("expenseCategory", ""+_ExpenseCategory.getExpenseCategory());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_ExpenseCategory.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
	            json.put("timeUpdated", ""+_ExpenseCategory.getTimeUpdated());

		}
		return json;

	}

}