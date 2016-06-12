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

import com.autosite.db.GenSimple;


public class GenSimpleDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields
	private String m_extString;
	private int m_extInt;

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("data", new Integer(0));
        fieldsMap.put("active", new Integer(1));
        fieldsMap.put("extString", new Integer(2));
        fieldsMap.put("extInt", new Integer(3));
    }


	GenSimple m_genSimple;

	public GenSimpleDataHolder(GenSimple _GenSimple){
		m_genSimple =  _GenSimple; 	
	}

	public GenSimpleDataHolder(){
		m_genSimple =  new GenSimple(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_genSimple;
	}


	public long getId(){
		return 	m_genSimple.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_genSimple.getSiteId();
	}
	public void setSiteId(long sid){
		m_genSimple.setSiteId(sid);
	}


	public String getData(){
		return m_genSimple.getData();
	}
	public int getActive(){
		return m_genSimple.getActive();
	}
	public String getExtString(){
		return m_extString;
	}
	public int getExtInt(){
		return m_extInt;
	}

	public void setData(String _data ){
		m_genSimple.setData(_data);
	}
	public void setActive(int _active ){
		m_genSimple.setActive(_active);
	}
	public void setExtString(String _extString ){
		m_extString = _extString;
	}
	public void setExtInt(int _extInt ){
		m_extInt = _extInt;
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

        if ( colNum == 0) return getData() == null?"":getData().toString();
        if ( colNum == 1) return String.valueOf(getActive());
        if ( colNum == 2) return getExtString() == null?"":getExtString().toString();
        if ( colNum == 3) return String.valueOf(getExtInt());
		return "";
    }
}