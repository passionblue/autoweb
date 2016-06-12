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

import com.autosite.db.SiteHeader;


public class SiteHeaderDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("asIs", new Integer(0));
        fieldsMap.put("includeType", new Integer(1));
        fieldsMap.put("includeText", new Integer(2));
        fieldsMap.put("timeCreated", new Integer(3));
        fieldsMap.put("timeUpdated", new Integer(4));
    }


	SiteHeader m_SiteHeader;

	public SiteHeaderDataHolder(SiteHeader _SiteHeader){
		m_SiteHeader =  _SiteHeader; 	
	}
	public  BaseAutositeDataObject getDataObject(){
		return 	m_SiteHeader;
	}

	public SiteHeaderDataHolder(){
		m_SiteHeader =  new SiteHeader(); 	
	}

	public long getId(){
		return 	m_SiteHeader.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_SiteHeader.getSiteId();
	}
	public void setSiteId(long sid){
		m_SiteHeader.setSiteId(sid);
	}


	public int getAsIs(){
		return m_SiteHeader.getAsIs();
	}
	public int getIncludeType(){
		return m_SiteHeader.getIncludeType();
	}
	public String getIncludeText(){
		return m_SiteHeader.getIncludeText();
	}
	public Timestamp getTimeCreated(){
		return m_SiteHeader.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_SiteHeader.getTimeUpdated();
	}

	public void setAsIs(int _asIs ){
		m_SiteHeader.setAsIs(_asIs);
	}
	public void setIncludeType(int _includeType ){
		m_SiteHeader.setIncludeType(_includeType);
	}
	public void setIncludeText(String _includeText ){
		m_SiteHeader.setIncludeText(_includeText);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_SiteHeader.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_SiteHeader.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return String.valueOf(getAsIs());
        if ( colNum == 1) return String.valueOf(getIncludeType());
        if ( colNum == 2) return getIncludeText() == null?"":getIncludeText().toString();
        if ( colNum == 3) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 4) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }
}