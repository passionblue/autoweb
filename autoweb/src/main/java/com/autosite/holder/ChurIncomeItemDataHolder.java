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

import com.autosite.db.ChurIncomeItem;


public class ChurIncomeItemDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("categoryId", new Integer(0));
        fieldsMap.put("incomeItem", new Integer(1));
        fieldsMap.put("display", new Integer(2));
    }


	ChurIncomeItem m_ChurIncomeItem;

	public ChurIncomeItemDataHolder(ChurIncomeItem _ChurIncomeItem){
		m_ChurIncomeItem =  _ChurIncomeItem; 	
	}
	public  BaseAutositeDataObject getDataObject(){
		return 	m_ChurIncomeItem;
	}

	public ChurIncomeItemDataHolder(){
		m_ChurIncomeItem =  new ChurIncomeItem(); 	
	}

	public long getId(){
		return 	m_ChurIncomeItem.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_ChurIncomeItem.getSiteId();
	}
	public void setSiteId(long sid){
		m_ChurIncomeItem.setSiteId(sid);
	}


	public long getCategoryId(){
		return m_ChurIncomeItem.getCategoryId();
	}
	public String getIncomeItem(){
		return m_ChurIncomeItem.getIncomeItem();
	}
	public String getDisplay(){
		return m_ChurIncomeItem.getDisplay();
	}

	public void setCategoryId(long _categoryId ){
		m_ChurIncomeItem.setCategoryId(_categoryId);
	}
	public void setIncomeItem(String _incomeItem ){
		m_ChurIncomeItem.setIncomeItem(_incomeItem);
	}
	public void setDisplay(String _display ){
		m_ChurIncomeItem.setDisplay(_display);
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
        if ( colNum == 1) return getIncomeItem() == null?"":getIncomeItem().toString();
        if ( colNum == 2) return getDisplay() == null?"":getDisplay().toString();
		return "";
    }
}