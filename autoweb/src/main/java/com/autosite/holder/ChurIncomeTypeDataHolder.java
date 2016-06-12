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

import com.autosite.db.ChurIncomeType;


public class ChurIncomeTypeDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("incomeType", new Integer(0));
    }


	ChurIncomeType m_ChurIncomeType;

	public ChurIncomeTypeDataHolder(ChurIncomeType _ChurIncomeType){
		m_ChurIncomeType =  _ChurIncomeType; 	
	}
	public  BaseAutositeDataObject getDataObject(){
		return 	m_ChurIncomeType;
	}

	public ChurIncomeTypeDataHolder(){
		m_ChurIncomeType =  new ChurIncomeType(); 	
	}

	public long getId(){
		return 	m_ChurIncomeType.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_ChurIncomeType.getSiteId();
	}
	public void setSiteId(long sid){
		m_ChurIncomeType.setSiteId(sid);
	}


	public String getIncomeType(){
		return m_ChurIncomeType.getIncomeType();
	}

	public void setIncomeType(String _incomeType ){
		m_ChurIncomeType.setIncomeType(_incomeType);
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

        if ( colNum == 0) return getIncomeType() == null?"":getIncomeType().toString();
		return "";
    }
}