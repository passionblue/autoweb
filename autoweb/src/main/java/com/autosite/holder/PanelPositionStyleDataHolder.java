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

import com.autosite.db.PanelPositionStyle;


public class PanelPositionStyleDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("col", new Integer(0));
        fieldsMap.put("styleId", new Integer(1));
        fieldsMap.put("timeCreated", new Integer(2));
    }


	PanelPositionStyle m_panelPositionStyle;

	public PanelPositionStyleDataHolder(PanelPositionStyle _PanelPositionStyle){
		m_panelPositionStyle =  _PanelPositionStyle; 	
	}

	public PanelPositionStyleDataHolder(){
		m_panelPositionStyle =  new PanelPositionStyle(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_panelPositionStyle;
	}


	public long getId(){
		return 	m_panelPositionStyle.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_panelPositionStyle.getSiteId();
	}
	public void setSiteId(long sid){
		m_panelPositionStyle.setSiteId(sid);
	}


	public String getCol(){
		return m_panelPositionStyle.getCol();
	}
	public long getStyleId(){
		return m_panelPositionStyle.getStyleId();
	}
	public Timestamp getTimeCreated(){
		return m_panelPositionStyle.getTimeCreated();
	}

	public void setCol(String _col ){
		m_panelPositionStyle.setCol(_col);
	}
	public void setStyleId(long _styleId ){
		m_panelPositionStyle.setStyleId(_styleId);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_panelPositionStyle.setTimeCreated(_timeCreated);
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

        if ( colNum == 0) return getCol() == null?"":getCol().toString();
        if ( colNum == 1) return String.valueOf(getStyleId());
        if ( colNum == 2) return getTimeCreated() == null?"":getTimeCreated().toString();
		return "";
    }
}