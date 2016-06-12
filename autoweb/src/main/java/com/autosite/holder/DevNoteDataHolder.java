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

import com.autosite.db.DevNote;


public class DevNoteDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("noteType", new Integer(0));
        fieldsMap.put("completed", new Integer(1));
        fieldsMap.put("category", new Integer(2));
        fieldsMap.put("subject", new Integer(3));
        fieldsMap.put("note", new Integer(4));
        fieldsMap.put("tags", new Integer(5));
        fieldsMap.put("timeCreated", new Integer(6));
        fieldsMap.put("timeUpdated", new Integer(7));
    }


	DevNote m_devNote;

	public DevNoteDataHolder(DevNote _DevNote){
		m_devNote =  _DevNote; 	
	}

	public DevNoteDataHolder(){
		m_devNote =  new DevNote(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_devNote;
	}


	public long getId(){
		return 	m_devNote.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_devNote.getSiteId();
	}
	public void setSiteId(long sid){
		m_devNote.setSiteId(sid);
	}


	public int getNoteType(){
		return m_devNote.getNoteType();
	}
	public int getCompleted(){
		return m_devNote.getCompleted();
	}
	public String getCategory(){
		return m_devNote.getCategory();
	}
	public String getSubject(){
		return m_devNote.getSubject();
	}
	public String getNote(){
		return m_devNote.getNote();
	}
	public String getTags(){
		return m_devNote.getTags();
	}
	public Timestamp getTimeCreated(){
		return m_devNote.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_devNote.getTimeUpdated();
	}

	public void setNoteType(int _noteType ){
		m_devNote.setNoteType(_noteType);
	}
	public void setCompleted(int _completed ){
		m_devNote.setCompleted(_completed);
	}
	public void setCategory(String _category ){
		m_devNote.setCategory(_category);
	}
	public void setSubject(String _subject ){
		m_devNote.setSubject(_subject);
	}
	public void setNote(String _note ){
		m_devNote.setNote(_note);
	}
	public void setTags(String _tags ){
		m_devNote.setTags(_tags);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_devNote.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_devNote.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return String.valueOf(getNoteType());
        if ( colNum == 1) return String.valueOf(getCompleted());
        if ( colNum == 2) return getCategory() == null?"":getCategory().toString();
        if ( colNum == 3) return getSubject() == null?"":getSubject().toString();
        if ( colNum == 4) return getNote() == null?"":getNote().toString();
        if ( colNum == 5) return getTags() == null?"":getTags().toString();
        if ( colNum == 6) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 7) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }
}