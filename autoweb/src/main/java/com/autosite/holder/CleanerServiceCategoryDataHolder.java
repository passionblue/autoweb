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

import com.autosite.db.CleanerServiceCategory;


public class CleanerServiceCategoryDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("title", new Integer(0));
        fieldsMap.put("imagePath", new Integer(1));
        fieldsMap.put("imagePathLocal", new Integer(2));
    }


	CleanerServiceCategory m_cleanerServiceCategory;

	public CleanerServiceCategoryDataHolder(CleanerServiceCategory _CleanerServiceCategory){
		m_cleanerServiceCategory =  _CleanerServiceCategory; 	
	}

	public CleanerServiceCategoryDataHolder(){
		m_cleanerServiceCategory =  new CleanerServiceCategory(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerServiceCategory;
	}


	public long getId(){
		return 	m_cleanerServiceCategory.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerServiceCategory.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerServiceCategory.setSiteId(sid);
	}


	public String getTitle(){
		return m_cleanerServiceCategory.getTitle();
	}
	public String getImagePath(){
		return m_cleanerServiceCategory.getImagePath();
	}
	public String getImagePathLocal(){
		return m_cleanerServiceCategory.getImagePathLocal();
	}

	public void setTitle(String _title ){
		m_cleanerServiceCategory.setTitle(_title);
	}
	public void setImagePath(String _imagePath ){
		m_cleanerServiceCategory.setImagePath(_imagePath);
	}
	public void setImagePathLocal(String _imagePathLocal ){
		m_cleanerServiceCategory.setImagePathLocal(_imagePathLocal);
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
        if ( colNum == 2) return getImagePathLocal() == null?"":getImagePathLocal().toString();
		return "";
    }


    public static JSONObject convertToJSON(CleanerServiceCategory _CleanerServiceCategory, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        
        JSONObject json = new JSONObject();
        if ( _CleanerServiceCategory == null) return json;

        json.put("id", ""+_CleanerServiceCategory.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonTitle = new JSONObject();
		            jsonTitle.put("name", "title");
		            jsonTitle.put("value", ""+_CleanerServiceCategory.getTitle());
		            array.put(jsonTitle);
		            JSONObject jsonImagePath = new JSONObject();
		            jsonImagePath.put("name", "imagePath");
		            jsonImagePath.put("value", ""+_CleanerServiceCategory.getImagePath());
		            array.put(jsonImagePath);
		            JSONObject jsonImagePathLocal = new JSONObject();
		            jsonImagePathLocal.put("name", "imagePathLocal");
		            jsonImagePathLocal.put("value", ""+_CleanerServiceCategory.getImagePathLocal());
		            array.put(jsonImagePathLocal);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("title")) 
	            json.put("title", ""+_CleanerServiceCategory.getTitle());
            if ( ignoreFieldSet || fieldSet.contains("imagePath")) 
	            json.put("imagePath", ""+_CleanerServiceCategory.getImagePath());
            if ( ignoreFieldSet || fieldSet.contains("imagePathLocal")) 
	            json.put("imagePathLocal", ""+_CleanerServiceCategory.getImagePathLocal());

		}
		return json;

	}

}