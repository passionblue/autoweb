/* 
Template last modification history:


Source Generated: Sun Jul 12 20:40:22 EDT 2015
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

import com.autosite.db.Expense;


public class ExpenseDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("expenseItemId", new Integer(0));
        fieldsMap.put("amount", new Integer(1));
        fieldsMap.put("description", new Integer(2));
        fieldsMap.put("payMethod", new Integer(3));
        fieldsMap.put("notExpense", new Integer(4));
        fieldsMap.put("reference", new Integer(5));
        fieldsMap.put("dateExpense", new Integer(6));
        fieldsMap.put("timeCreated", new Integer(7));
        fieldsMap.put("timeUpdated", new Integer(8));
    }


	Expense m_expense;

	public ExpenseDataHolder(Expense _Expense){
		m_expense =  _Expense; 	
	}

	public ExpenseDataHolder(){
		m_expense =  new Expense(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_expense;
	}


	public long getId(){
		return 	m_expense.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_expense.getSiteId();
	}
	public void setSiteId(long sid){
		m_expense.setSiteId(sid);
	}


	public long getExpenseItemId(){
		return m_expense.getExpenseItemId();
	}
	public long getAmount(){
		return m_expense.getAmount();
	}
	public String getDescription(){
		return m_expense.getDescription();
	}
	public String getPayMethod(){
		return m_expense.getPayMethod();
	}
	public int getNotExpense(){
		return m_expense.getNotExpense();
	}
	public String getReference(){
		return m_expense.getReference();
	}
	public String getDateExpense(){
		return m_expense.getDateExpense();
	}
	public Timestamp getTimeCreated(){
		return m_expense.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_expense.getTimeUpdated();
	}

	public void setExpenseItemId(long _expenseItemId ){
		m_expense.setExpenseItemId(_expenseItemId);
	}
	public void setAmount(long _amount ){
		m_expense.setAmount(_amount);
	}
	public void setDescription(String _description ){
		m_expense.setDescription(_description);
	}
	public void setPayMethod(String _payMethod ){
		m_expense.setPayMethod(_payMethod);
	}
	public void setNotExpense(int _notExpense ){
		m_expense.setNotExpense(_notExpense);
	}
	public void setReference(String _reference ){
		m_expense.setReference(_reference);
	}
	public void setDateExpense(String _dateExpense ){
		m_expense.setDateExpense(_dateExpense);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_expense.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_expense.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return String.valueOf(getExpenseItemId());
        if ( colNum == 1) return String.valueOf(getAmount());
        if ( colNum == 2) return getDescription() == null?"":getDescription().toString();
        if ( colNum == 3) return getPayMethod() == null?"":getPayMethod().toString();
        if ( colNum == 4) return String.valueOf(getNotExpense());
        if ( colNum == 5) return getReference() == null?"":getReference().toString();
        if ( colNum == 6) return getDateExpense() == null?"":getDateExpense().toString();
        if ( colNum == 7) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 8) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }


    public static JSONObject convertToJSON(Expense _Expense, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_Expense, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(Expense _Expense, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _Expense == null) return json;

        json.put("id", ""+_Expense.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonExpenseItemId = new JSONObject();
		            jsonExpenseItemId.put("name", "expenseItemId");
		            if ( useJsonDataType ) 
	                    jsonExpenseItemId.put("value", JSONValueFormatter.toJsonTypeValue(_Expense.getExpenseItemId()));
		            else
		                jsonExpenseItemId.put("value", JSONValueFormatter.toStringValue(_Expense.getExpenseItemId()));
		            array.put(jsonExpenseItemId);
		            JSONObject jsonAmount = new JSONObject();
		            jsonAmount.put("name", "amount");
		            if ( useJsonDataType ) 
	                    jsonAmount.put("value", JSONValueFormatter.toJsonTypeValue(_Expense.getAmount()));
		            else
		                jsonAmount.put("value", JSONValueFormatter.toStringValue(_Expense.getAmount()));
		            array.put(jsonAmount);
		            JSONObject jsonDescription = new JSONObject();
		            jsonDescription.put("name", "description");
		            if ( useJsonDataType ) 
	                    jsonDescription.put("value", JSONValueFormatter.toJsonTypeValue(_Expense.getDescription()));
		            else
		                jsonDescription.put("value", JSONValueFormatter.toStringValue(_Expense.getDescription()));
		            array.put(jsonDescription);
		            JSONObject jsonPayMethod = new JSONObject();
		            jsonPayMethod.put("name", "payMethod");
		            if ( useJsonDataType ) 
	                    jsonPayMethod.put("value", JSONValueFormatter.toJsonTypeValue(_Expense.getPayMethod()));
		            else
		                jsonPayMethod.put("value", JSONValueFormatter.toStringValue(_Expense.getPayMethod()));
		            array.put(jsonPayMethod);
		            JSONObject jsonNotExpense = new JSONObject();
		            jsonNotExpense.put("name", "notExpense");
		            if ( useJsonDataType ) 
	                    jsonNotExpense.put("value", JSONValueFormatter.toJsonTypeValue(_Expense.getNotExpense()));
		            else
		                jsonNotExpense.put("value", JSONValueFormatter.toStringValue(_Expense.getNotExpense()));
		            array.put(jsonNotExpense);
		            JSONObject jsonReference = new JSONObject();
		            jsonReference.put("name", "reference");
		            if ( useJsonDataType ) 
	                    jsonReference.put("value", JSONValueFormatter.toJsonTypeValue(_Expense.getReference()));
		            else
		                jsonReference.put("value", JSONValueFormatter.toStringValue(_Expense.getReference()));
		            array.put(jsonReference);
		            JSONObject jsonDateExpense = new JSONObject();
		            jsonDateExpense.put("name", "dateExpense");
		            if ( useJsonDataType ) 
	                    jsonDateExpense.put("value", JSONValueFormatter.toJsonTypeValue(_Expense.getDateExpense()));
		            else
		                jsonDateExpense.put("value", JSONValueFormatter.toStringValue(_Expense.getDateExpense()));
		            array.put(jsonDateExpense);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_Expense.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_Expense.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            if ( useJsonDataType ) 
	                    jsonTimeUpdated.put("value", JSONValueFormatter.toJsonTypeValue(_Expense.getTimeUpdated()));
		            else
		                jsonTimeUpdated.put("value", JSONValueFormatter.toStringValue(_Expense.getTimeUpdated()));
		            array.put(jsonTimeUpdated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("expenseItemId")) 
	            json.put("expenseItemId", ""+_Expense.getExpenseItemId());
            if ( ignoreFieldSet || fieldSet.contains("amount")) 
	            json.put("amount", ""+_Expense.getAmount());
            if ( ignoreFieldSet || fieldSet.contains("description")) 
	            json.put("description", ""+_Expense.getDescription());
            if ( ignoreFieldSet || fieldSet.contains("payMethod")) 
	            json.put("payMethod", ""+_Expense.getPayMethod());
            if ( ignoreFieldSet || fieldSet.contains("notExpense")) 
	            json.put("notExpense", ""+_Expense.getNotExpense());
            if ( ignoreFieldSet || fieldSet.contains("reference")) 
	            json.put("reference", ""+_Expense.getReference());
            if ( ignoreFieldSet || fieldSet.contains("dateExpense")) 
	            json.put("dateExpense", ""+_Expense.getDateExpense());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_Expense.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
	            json.put("timeUpdated", ""+_Expense.getTimeUpdated());

		}
		return json;

	}

}