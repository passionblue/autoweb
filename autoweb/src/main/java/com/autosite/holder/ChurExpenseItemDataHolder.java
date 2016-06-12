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

import com.autosite.db.ChurExpenseItem;


public class ChurExpenseItemDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("categoryId", new Integer(0));
        fieldsMap.put("expenseItem", new Integer(1));
        fieldsMap.put("display", new Integer(2));
    }


	ChurExpenseItem m_ChurExpenseItem;

	public ChurExpenseItemDataHolder(ChurExpenseItem _ChurExpenseItem){
		m_ChurExpenseItem =  _ChurExpenseItem; 	
	}
	public  BaseAutositeDataObject getDataObject(){
		return 	m_ChurExpenseItem;
	}

	public ChurExpenseItemDataHolder(){
		m_ChurExpenseItem =  new ChurExpenseItem(); 	
	}

	public long getId(){
		return 	m_ChurExpenseItem.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_ChurExpenseItem.getSiteId();
	}
	public void setSiteId(long sid){
		m_ChurExpenseItem.setSiteId(sid);
	}


	public long getCategoryId(){
		return m_ChurExpenseItem.getCategoryId();
	}
	public String getExpenseItem(){
		return m_ChurExpenseItem.getExpenseItem();
	}
	public String getDisplay(){
		return m_ChurExpenseItem.getDisplay();
	}

	public void setCategoryId(long _categoryId ){
		m_ChurExpenseItem.setCategoryId(_categoryId);
	}
	public void setExpenseItem(String _expenseItem ){
		m_ChurExpenseItem.setExpenseItem(_expenseItem);
	}
	public void setDisplay(String _display ){
		m_ChurExpenseItem.setDisplay(_display);
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

        if ( colNum == 0) return String.valueOf(getCategoryId());
        if ( colNum == 1) return getExpenseItem() == null?"":getExpenseItem().toString();
        if ( colNum == 2) return getDisplay() == null?"":getDisplay().toString();
		return "";
    }
}