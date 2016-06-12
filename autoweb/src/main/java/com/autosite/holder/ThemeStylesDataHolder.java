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

import com.autosite.db.ThemeStyles;


public class ThemeStylesDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("bodyWidth", new Integer(0));
        fieldsMap.put("bodyAlign", new Integer(1));
        fieldsMap.put("bodyBackground", new Integer(2));
        fieldsMap.put("timeCreated", new Integer(3));
        fieldsMap.put("timeUpdated", new Integer(4));
    }


	ThemeStyles m_themeStyles;

	public ThemeStylesDataHolder(ThemeStyles _ThemeStyles){
		m_themeStyles =  _ThemeStyles; 	
	}

	public ThemeStylesDataHolder(){
		m_themeStyles =  new ThemeStyles(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_themeStyles;
	}


	public long getId(){
		return 	m_themeStyles.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_themeStyles.getSiteId();
	}
	public void setSiteId(long sid){
		m_themeStyles.setSiteId(sid);
	}


	public int getBodyWidth(){
		return m_themeStyles.getBodyWidth();
	}
	public String getBodyAlign(){
		return m_themeStyles.getBodyAlign();
	}
	public String getBodyBackground(){
		return m_themeStyles.getBodyBackground();
	}
	public Timestamp getTimeCreated(){
		return m_themeStyles.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_themeStyles.getTimeUpdated();
	}

	public void setBodyWidth(int _bodyWidth ){
		m_themeStyles.setBodyWidth(_bodyWidth);
	}
	public void setBodyAlign(String _bodyAlign ){
		m_themeStyles.setBodyAlign(_bodyAlign);
	}
	public void setBodyBackground(String _bodyBackground ){
		m_themeStyles.setBodyBackground(_bodyBackground);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_themeStyles.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_themeStyles.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return String.valueOf(getBodyWidth());
        if ( colNum == 1) return getBodyAlign() == null?"":getBodyAlign().toString();
        if ( colNum == 2) return getBodyBackground() == null?"":getBodyBackground().toString();
        if ( colNum == 3) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 4) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }
}