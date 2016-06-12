package com.autosite.holder;

import java.sql.Timestamp;
import java.sql.Date;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.util.html.HtmlGenRow;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Set;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.autosite.db.CleanerGarmentService;


public class CleanerGarmentServiceDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("title", new Integer(0));
        fieldsMap.put("note", new Integer(1));
        fieldsMap.put("imagePath", new Integer(2));
    }


	CleanerGarmentService m_cleanerGarmentService;

	public CleanerGarmentServiceDataHolder(CleanerGarmentService _CleanerGarmentService){
		m_cleanerGarmentService =  _CleanerGarmentService; 	
	}

	public CleanerGarmentServiceDataHolder(){
		m_cleanerGarmentService =  new CleanerGarmentService(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerGarmentService;
	}


	public long getId(){
		return 	m_cleanerGarmentService.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerGarmentService.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerGarmentService.setSiteId(sid);
	}


	public String getTitle(){
		return m_cleanerGarmentService.getTitle();
	}
	public String getNote(){
		return m_cleanerGarmentService.getNote();
	}
	public String getImagePath(){
		return m_cleanerGarmentService.getImagePath();
	}

	public void setTitle(String _title ){
		m_cleanerGarmentService.setTitle(_title);
	}
	public void setNote(String _note ){
		m_cleanerGarmentService.setNote(_note);
	}
	public void setImagePath(String _imagePath ){
		m_cleanerGarmentService.setImagePath(_imagePath);
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

        if ( colNum == 0) return getTitle() == null?"":getTitle().toString();
        if ( colNum == 1) return getNote() == null?"":getNote().toString();
        if ( colNum == 2) return getImagePath() == null?"":getImagePath().toString();
		return "";
    }


    public static JSONObject convertToJSON(CleanerGarmentService _CleanerGarmentService, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        
        JSONObject json = new JSONObject();
        if ( _CleanerGarmentService == null) return json;

        json.put("id", ""+_CleanerGarmentService.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonTitle = new JSONObject();
		            jsonTitle.put("name", "title");
		            jsonTitle.put("value", ""+_CleanerGarmentService.getTitle());
		            array.put(jsonTitle);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerGarmentService.getNote());
		            array.put(jsonNote);
		            JSONObject jsonImagePath = new JSONObject();
		            jsonImagePath.put("name", "imagePath");
		            jsonImagePath.put("value", ""+_CleanerGarmentService.getImagePath());
		            array.put(jsonImagePath);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("title")) 
	            json.put("title", ""+_CleanerGarmentService.getTitle());
            if ( ignoreFieldSet || fieldSet.contains("note")) 
	            json.put("note", ""+_CleanerGarmentService.getNote());
            if ( ignoreFieldSet || fieldSet.contains("imagePath")) 
	            json.put("imagePath", ""+_CleanerGarmentService.getImagePath());

		}
		return json;

	}

}