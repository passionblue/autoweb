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

import com.autosite.db.CleanerLocation;


public class CleanerLocationDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("address", new Integer(0));
        fieldsMap.put("cityZip", new Integer(1));
        fieldsMap.put("phone", new Integer(2));
        fieldsMap.put("managerId", new Integer(3));
    }


	CleanerLocation m_cleanerLocation;

	public CleanerLocationDataHolder(CleanerLocation _CleanerLocation){
		m_cleanerLocation =  _CleanerLocation; 	
	}

	public CleanerLocationDataHolder(){
		m_cleanerLocation =  new CleanerLocation(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerLocation;
	}


	public long getId(){
		return 	m_cleanerLocation.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerLocation.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerLocation.setSiteId(sid);
	}


	public String getAddress(){
		return m_cleanerLocation.getAddress();
	}
	public String getCityZip(){
		return m_cleanerLocation.getCityZip();
	}
	public String getPhone(){
		return m_cleanerLocation.getPhone();
	}
	public long getManagerId(){
		return m_cleanerLocation.getManagerId();
	}

	public void setAddress(String _address ){
		m_cleanerLocation.setAddress(_address);
	}
	public void setCityZip(String _cityZip ){
		m_cleanerLocation.setCityZip(_cityZip);
	}
	public void setPhone(String _phone ){
		m_cleanerLocation.setPhone(_phone);
	}
	public void setManagerId(long _managerId ){
		m_cleanerLocation.setManagerId(_managerId);
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

        if ( colNum == 0) return getAddress() == null?"":getAddress().toString();
        if ( colNum == 1) return getCityZip() == null?"":getCityZip().toString();
        if ( colNum == 2) return getPhone() == null?"":getPhone().toString();
        if ( colNum == 3) return String.valueOf(getManagerId());
		return "";
    }
}