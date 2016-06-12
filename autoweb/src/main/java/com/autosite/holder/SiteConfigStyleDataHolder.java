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

import com.autosite.db.SiteConfigStyle;


public class SiteConfigStyleDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("themeId", new Integer(0));
        fieldsMap.put("cssIndex", new Integer(1));
        fieldsMap.put("cssImport", new Integer(2));
        fieldsMap.put("layoutIndex", new Integer(3));
        fieldsMap.put("timeCreated", new Integer(4));
        fieldsMap.put("timeUpdated", new Integer(5));
    }


	SiteConfigStyle m_siteConfigStyle;

	public SiteConfigStyleDataHolder(SiteConfigStyle _SiteConfigStyle){
		m_siteConfigStyle =  _SiteConfigStyle; 	
	}

	public SiteConfigStyleDataHolder(){
		m_siteConfigStyle =  new SiteConfigStyle(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_siteConfigStyle;
	}


	public long getId(){
		return 	m_siteConfigStyle.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_siteConfigStyle.getSiteId();
	}
	public void setSiteId(long sid){
		m_siteConfigStyle.setSiteId(sid);
	}


	public long getThemeId(){
		return m_siteConfigStyle.getThemeId();
	}
	public int getCssIndex(){
		return m_siteConfigStyle.getCssIndex();
	}
	public String getCssImport(){
		return m_siteConfigStyle.getCssImport();
	}
	public int getLayoutIndex(){
		return m_siteConfigStyle.getLayoutIndex();
	}
	public Timestamp getTimeCreated(){
		return m_siteConfigStyle.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_siteConfigStyle.getTimeUpdated();
	}

	public void setThemeId(long _themeId ){
		m_siteConfigStyle.setThemeId(_themeId);
	}
	public void setCssIndex(int _cssIndex ){
		m_siteConfigStyle.setCssIndex(_cssIndex);
	}
	public void setCssImport(String _cssImport ){
		m_siteConfigStyle.setCssImport(_cssImport);
	}
	public void setLayoutIndex(int _layoutIndex ){
		m_siteConfigStyle.setLayoutIndex(_layoutIndex);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_siteConfigStyle.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_siteConfigStyle.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return String.valueOf(getThemeId());
        if ( colNum == 1) return String.valueOf(getCssIndex());
        if ( colNum == 2) return getCssImport() == null?"":getCssImport().toString();
        if ( colNum == 3) return String.valueOf(getLayoutIndex());
        if ( colNum == 4) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 5) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }
}