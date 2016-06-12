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

import com.autosite.db.CleanerPickupDeliveryRecur;


public class CleanerPickupDeliveryRecurDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("customerId", new Integer(0));
        fieldsMap.put("weekday", new Integer(1));
        fieldsMap.put("timeHhdd", new Integer(2));
    }


	CleanerPickupDeliveryRecur m_cleanerPickupDeliveryRecur;

	public CleanerPickupDeliveryRecurDataHolder(CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur){
		m_cleanerPickupDeliveryRecur =  _CleanerPickupDeliveryRecur; 	
	}

	public CleanerPickupDeliveryRecurDataHolder(){
		m_cleanerPickupDeliveryRecur =  new CleanerPickupDeliveryRecur(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerPickupDeliveryRecur;
	}


	public long getId(){
		return 	m_cleanerPickupDeliveryRecur.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerPickupDeliveryRecur.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerPickupDeliveryRecur.setSiteId(sid);
	}


	public long getCustomerId(){
		return m_cleanerPickupDeliveryRecur.getCustomerId();
	}
	public int getWeekday(){
		return m_cleanerPickupDeliveryRecur.getWeekday();
	}
	public String getTimeHhdd(){
		return m_cleanerPickupDeliveryRecur.getTimeHhdd();
	}

	public void setCustomerId(long _customerId ){
		m_cleanerPickupDeliveryRecur.setCustomerId(_customerId);
	}
	public void setWeekday(int _weekday ){
		m_cleanerPickupDeliveryRecur.setWeekday(_weekday);
	}
	public void setTimeHhdd(String _timeHhdd ){
		m_cleanerPickupDeliveryRecur.setTimeHhdd(_timeHhdd);
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

        if ( colNum == 0) return String.valueOf(getCustomerId());
        if ( colNum == 1) return String.valueOf(getWeekday());
        if ( colNum == 2) return getTimeHhdd() == null?"":getTimeHhdd().toString();
		return "";
    }
}