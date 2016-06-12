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

import com.autosite.db.CleanerServiceProcess;


public class CleanerServiceProcessDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("ticketId", new Integer(0));
        fieldsMap.put("processUserId", new Integer(1));
        fieldsMap.put("processType", new Integer(2));
        fieldsMap.put("timeStarted", new Integer(3));
        fieldsMap.put("timeEnded", new Integer(4));
        fieldsMap.put("note", new Integer(5));
    }


	CleanerServiceProcess m_cleanerServiceProcess;

	public CleanerServiceProcessDataHolder(CleanerServiceProcess _CleanerServiceProcess){
		m_cleanerServiceProcess =  _CleanerServiceProcess; 	
	}

	public CleanerServiceProcessDataHolder(){
		m_cleanerServiceProcess =  new CleanerServiceProcess(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerServiceProcess;
	}


	public long getId(){
		return 	m_cleanerServiceProcess.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerServiceProcess.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerServiceProcess.setSiteId(sid);
	}


	public long getTicketId(){
		return m_cleanerServiceProcess.getTicketId();
	}
	public long getProcessUserId(){
		return m_cleanerServiceProcess.getProcessUserId();
	}
	public long getProcessType(){
		return m_cleanerServiceProcess.getProcessType();
	}
	public Timestamp getTimeStarted(){
		return m_cleanerServiceProcess.getTimeStarted();
	}
	public Timestamp getTimeEnded(){
		return m_cleanerServiceProcess.getTimeEnded();
	}
	public String getNote(){
		return m_cleanerServiceProcess.getNote();
	}

	public void setTicketId(long _ticketId ){
		m_cleanerServiceProcess.setTicketId(_ticketId);
	}
	public void setProcessUserId(long _processUserId ){
		m_cleanerServiceProcess.setProcessUserId(_processUserId);
	}
	public void setProcessType(long _processType ){
		m_cleanerServiceProcess.setProcessType(_processType);
	}
	public void setTimeStarted(Timestamp _timeStarted ){
		m_cleanerServiceProcess.setTimeStarted(_timeStarted);
	}
	public void setTimeEnded(Timestamp _timeEnded ){
		m_cleanerServiceProcess.setTimeEnded(_timeEnded);
	}
	public void setNote(String _note ){
		m_cleanerServiceProcess.setNote(_note);
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

        if ( colNum == 0) return String.valueOf(getTicketId());
        if ( colNum == 1) return String.valueOf(getProcessUserId());
        if ( colNum == 2) return String.valueOf(getProcessType());
        if ( colNum == 3) return getTimeStarted() == null?"":getTimeStarted().toString();
        if ( colNum == 4) return getTimeEnded() == null?"":getTimeEnded().toString();
        if ( colNum == 5) return getNote() == null?"":getNote().toString();
		return "";
    }
}