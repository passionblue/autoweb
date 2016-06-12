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

import com.autosite.db.CleanerUser;


public class CleanerUserDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("autositeUserId", new Integer(0));
        fieldsMap.put("inactivated", new Integer(1));
        fieldsMap.put("timeCreated", new Integer(2));
        fieldsMap.put("timeUpdated", new Integer(3));
    }


	CleanerUser m_cleanerUser;

	public CleanerUserDataHolder(CleanerUser _CleanerUser){
		m_cleanerUser =  _CleanerUser; 	
	}

	public CleanerUserDataHolder(){
		m_cleanerUser =  new CleanerUser(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerUser;
	}


	public long getId(){
		return 	m_cleanerUser.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerUser.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerUser.setSiteId(sid);
	}


	public long getAutositeUserId(){
		return m_cleanerUser.getAutositeUserId();
	}
	public int getInactivated(){
		return m_cleanerUser.getInactivated();
	}
	public Timestamp getTimeCreated(){
		return m_cleanerUser.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_cleanerUser.getTimeUpdated();
	}

	public void setAutositeUserId(long _autositeUserId ){
		m_cleanerUser.setAutositeUserId(_autositeUserId);
	}
	public void setInactivated(int _inactivated ){
		m_cleanerUser.setInactivated(_inactivated);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_cleanerUser.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_cleanerUser.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return String.valueOf(getAutositeUserId());
        if ( colNum == 1) return String.valueOf(getInactivated());
        if ( colNum == 2) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 3) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }
}