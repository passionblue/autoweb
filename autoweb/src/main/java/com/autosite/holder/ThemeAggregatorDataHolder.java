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

import com.autosite.db.ThemeAggregator;


public class ThemeAggregatorDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("themeName", new Integer(0));
        fieldsMap.put("layoutPage", new Integer(1));
        fieldsMap.put("cssIndex", new Integer(2));
        fieldsMap.put("themeStyleId", new Integer(3));
        fieldsMap.put("timeCreated", new Integer(4));
        fieldsMap.put("timeUpdated", new Integer(5));
    }


	ThemeAggregator m_themeAggregator;

	public ThemeAggregatorDataHolder(ThemeAggregator _ThemeAggregator){
		m_themeAggregator =  _ThemeAggregator; 	
	}

	public ThemeAggregatorDataHolder(){
		m_themeAggregator =  new ThemeAggregator(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_themeAggregator;
	}


	public long getId(){
		return 	m_themeAggregator.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_themeAggregator.getSiteId();
	}
	public void setSiteId(long sid){
		m_themeAggregator.setSiteId(sid);
	}


	public String getThemeName(){
		return m_themeAggregator.getThemeName();
	}
	public String getLayoutPage(){
		return m_themeAggregator.getLayoutPage();
	}
	public String getCssIndex(){
		return m_themeAggregator.getCssIndex();
	}
	public long getThemeStyleId(){
		return m_themeAggregator.getThemeStyleId();
	}
	public Timestamp getTimeCreated(){
		return m_themeAggregator.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_themeAggregator.getTimeUpdated();
	}

	public void setThemeName(String _themeName ){
		m_themeAggregator.setThemeName(_themeName);
	}
	public void setLayoutPage(String _layoutPage ){
		m_themeAggregator.setLayoutPage(_layoutPage);
	}
	public void setCssIndex(String _cssIndex ){
		m_themeAggregator.setCssIndex(_cssIndex);
	}
	public void setThemeStyleId(long _themeStyleId ){
		m_themeAggregator.setThemeStyleId(_themeStyleId);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_themeAggregator.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_themeAggregator.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return getThemeName() == null?"":getThemeName().toString();
        if ( colNum == 1) return getLayoutPage() == null?"":getLayoutPage().toString();
        if ( colNum == 2) return getCssIndex() == null?"":getCssIndex().toString();
        if ( colNum == 3) return String.valueOf(getThemeStyleId());
        if ( colNum == 4) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 5) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }
}