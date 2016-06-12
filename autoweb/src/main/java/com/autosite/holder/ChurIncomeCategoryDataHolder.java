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

import com.autosite.db.ChurIncomeCategory;


public class ChurIncomeCategoryDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("incomeCategory", new Integer(0));
        fieldsMap.put("display", new Integer(1));
        fieldsMap.put("timeCreated", new Integer(2));
    }


	ChurIncomeCategory m_ChurIncomeCategory;

	public ChurIncomeCategoryDataHolder(ChurIncomeCategory _ChurIncomeCategory){
		m_ChurIncomeCategory =  _ChurIncomeCategory; 	
	}
	public  BaseAutositeDataObject getDataObject(){
		return 	m_ChurIncomeCategory;
	}

	public ChurIncomeCategoryDataHolder(){
		m_ChurIncomeCategory =  new ChurIncomeCategory(); 	
	}

	public long getId(){
		return 	m_ChurIncomeCategory.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_ChurIncomeCategory.getSiteId();
	}
	public void setSiteId(long sid){
		m_ChurIncomeCategory.setSiteId(sid);
	}


	public String getIncomeCategory(){
		return m_ChurIncomeCategory.getIncomeCategory();
	}
	public String getDisplay(){
		return m_ChurIncomeCategory.getDisplay();
	}
	public Timestamp getTimeCreated(){
		return m_ChurIncomeCategory.getTimeCreated();
	}

	public void setIncomeCategory(String _incomeCategory ){
		m_ChurIncomeCategory.setIncomeCategory(_incomeCategory);
	}
	public void setDisplay(String _display ){
		m_ChurIncomeCategory.setDisplay(_display);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_ChurIncomeCategory.setTimeCreated(_timeCreated);
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

        if ( colNum == 0) return getIncomeCategory() == null?"":getIncomeCategory().toString();
        if ( colNum == 1) return getDisplay() == null?"":getDisplay().toString();
        if ( colNum == 2) return getTimeCreated() == null?"":getTimeCreated().toString();
		return "";
    }
}