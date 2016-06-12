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

import com.autosite.db.CleanerGarmentType;


public class CleanerGarmentTypeDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("title", new Integer(0));
        fieldsMap.put("imagePath", new Integer(1));
    }


	CleanerGarmentType m_cleanerGarmentType;

	public CleanerGarmentTypeDataHolder(CleanerGarmentType _CleanerGarmentType){
		m_cleanerGarmentType =  _CleanerGarmentType; 	
	}

	public CleanerGarmentTypeDataHolder(){
		m_cleanerGarmentType =  new CleanerGarmentType(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerGarmentType;
	}


	public long getId(){
		return 	m_cleanerGarmentType.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerGarmentType.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerGarmentType.setSiteId(sid);
	}


	public String getTitle(){
		return m_cleanerGarmentType.getTitle();
	}
	public String getImagePath(){
		return m_cleanerGarmentType.getImagePath();
	}

	public void setTitle(String _title ){
		m_cleanerGarmentType.setTitle(_title);
	}
	public void setImagePath(String _imagePath ){
		m_cleanerGarmentType.setImagePath(_imagePath);
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
        if ( colNum == 1) return getImagePath() == null?"":getImagePath().toString();
		return "";
    }


    public static JSONObject convertToJSON(CleanerGarmentType _CleanerGarmentType, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        
        JSONObject json = new JSONObject();
        if ( _CleanerGarmentType == null) return json;

        json.put("id", ""+_CleanerGarmentType.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonTitle = new JSONObject();
		            jsonTitle.put("name", "title");
		            jsonTitle.put("value", ""+_CleanerGarmentType.getTitle());
		            array.put(jsonTitle);
		            JSONObject jsonImagePath = new JSONObject();
		            jsonImagePath.put("name", "imagePath");
		            jsonImagePath.put("value", ""+_CleanerGarmentType.getImagePath());
		            array.put(jsonImagePath);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("title")) 
	            json.put("title", ""+_CleanerGarmentType.getTitle());
            if ( ignoreFieldSet || fieldSet.contains("imagePath")) 
	            json.put("imagePath", ""+_CleanerGarmentType.getImagePath());

		}
		return json;

	}

}