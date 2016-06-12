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

import com.autosite.db.Section;


public class SectionDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("title", new Integer(0));
        fieldsMap.put("mainPageId", new Integer(1));
        fieldsMap.put("timeCreated", new Integer(2));
    }


	Section m_Section;

	public SectionDataHolder(Section _Section){
		m_Section =  _Section; 	
	}
	public  BaseAutositeDataObject getDataObject(){
		return 	m_Section;
	}

	public SectionDataHolder(){
		m_Section =  new Section(); 	
	}

	public long getId(){
		return 	m_Section.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_Section.getSiteId();
	}
	public void setSiteId(long sid){
		m_Section.setSiteId(sid);
	}


	public String getTitle(){
		return m_Section.getTitle();
	}
	public long getMainPageId(){
		return m_Section.getMainPageId();
	}
	public Timestamp getTimeCreated(){
		return m_Section.getTimeCreated();
	}

	public void setTitle(String _title ){
		m_Section.setTitle(_title);
	}
	public void setMainPageId(long _mainPageId ){
		m_Section.setMainPageId(_mainPageId);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_Section.setTimeCreated(_timeCreated);
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

        if ( colNum == 0) return getTitle() == null?"":getTitle().toString();
        if ( colNum == 1) return String.valueOf(getMainPageId());
        if ( colNum == 2) return getTimeCreated() == null?"":getTimeCreated().toString();
		return "";
    }
}