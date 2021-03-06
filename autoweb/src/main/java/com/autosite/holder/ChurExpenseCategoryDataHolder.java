package com.autosite.holder;

import java.sql.Timestamp;
import java.sql.Date;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.util.html.HtmlGenRow;
import java.util.ArrayList;
import java.util.List;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.autosite.db.ChurExpenseCategory;


public class ChurExpenseCategoryDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("expenseCategory", new Integer(0));
        fieldsMap.put("display", new Integer(1));
    }


	ChurExpenseCategory m_ChurExpenseCategory;

	public ChurExpenseCategoryDataHolder(ChurExpenseCategory _ChurExpenseCategory){
		m_ChurExpenseCategory =  _ChurExpenseCategory; 	
	}
	public  BaseAutositeDataObject getDataObject(){
		return 	m_ChurExpenseCategory;
	}

	public ChurExpenseCategoryDataHolder(){
		m_ChurExpenseCategory =  new ChurExpenseCategory(); 	
	}

	public long getId(){
		return 	m_ChurExpenseCategory.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_ChurExpenseCategory.getSiteId();
	}
	public void setSiteId(long sid){
		m_ChurExpenseCategory.setSiteId(sid);
	}


	public String getExpenseCategory(){
		return m_ChurExpenseCategory.getExpenseCategory();
	}
	public String getDisplay(){
		return m_ChurExpenseCategory.getDisplay();
	}

	public void setExpenseCategory(String _expenseCategory ){
		m_ChurExpenseCategory.setExpenseCategory(_expenseCategory);
	}
	public void setDisplay(String _display ){
		m_ChurExpenseCategory.setDisplay(_display);
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
        if ( colNum == 1) return getDisplay() == null?"":getDisplay().toString();
		return "";
    }
}