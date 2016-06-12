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

import com.autosite.db.CleanerProduct;


public class CleanerProductDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("garmentTypeId", new Integer(0));
        fieldsMap.put("garmentServiceId", new Integer(1));
        fieldsMap.put("regularPrice", new Integer(2));
        fieldsMap.put("note", new Integer(3));
    }


	CleanerProduct m_cleanerProduct;

	public CleanerProductDataHolder(CleanerProduct _CleanerProduct){
		m_cleanerProduct =  _CleanerProduct; 	
	}

	public CleanerProductDataHolder(){
		m_cleanerProduct =  new CleanerProduct(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerProduct;
	}


	public long getId(){
		return 	m_cleanerProduct.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerProduct.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerProduct.setSiteId(sid);
	}


	public long getGarmentTypeId(){
		return m_cleanerProduct.getGarmentTypeId();
	}
	public long getGarmentServiceId(){
		return m_cleanerProduct.getGarmentServiceId();
	}
	public double getRegularPrice(){
		return m_cleanerProduct.getRegularPrice();
	}
	public String getNote(){
		return m_cleanerProduct.getNote();
	}

	public void setGarmentTypeId(long _garmentTypeId ){
		m_cleanerProduct.setGarmentTypeId(_garmentTypeId);
	}
	public void setGarmentServiceId(long _garmentServiceId ){
		m_cleanerProduct.setGarmentServiceId(_garmentServiceId);
	}
	public void setRegularPrice(double _regularPrice ){
		m_cleanerProduct.setRegularPrice(_regularPrice);
	}
	public void setNote(String _note ){
		m_cleanerProduct.setNote(_note);
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

        if ( colNum == 0) return String.valueOf(getGarmentTypeId());
        if ( colNum == 1) return String.valueOf(getGarmentServiceId());
        if ( colNum == 2) return String.valueOf(getRegularPrice());
        if ( colNum == 3) return getNote() == null?"":getNote().toString();
		return "";
    }


    public static JSONObject convertToJSON(CleanerProduct _CleanerProduct, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        
        JSONObject json = new JSONObject();
        if ( _CleanerProduct == null) return json;

        json.put("id", ""+_CleanerProduct.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonGarmentTypeId = new JSONObject();
		            jsonGarmentTypeId.put("name", "garmentTypeId");
		            jsonGarmentTypeId.put("value", ""+_CleanerProduct.getGarmentTypeId());
		            array.put(jsonGarmentTypeId);
		            JSONObject jsonGarmentServiceId = new JSONObject();
		            jsonGarmentServiceId.put("name", "garmentServiceId");
		            jsonGarmentServiceId.put("value", ""+_CleanerProduct.getGarmentServiceId());
		            array.put(jsonGarmentServiceId);
		            JSONObject jsonRegularPrice = new JSONObject();
		            jsonRegularPrice.put("name", "regularPrice");
		            jsonRegularPrice.put("value", ""+_CleanerProduct.getRegularPrice());
		            array.put(jsonRegularPrice);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerProduct.getNote());
		            array.put(jsonNote);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("garmentTypeId")) 
	            json.put("garmentTypeId", ""+_CleanerProduct.getGarmentTypeId());
            if ( ignoreFieldSet || fieldSet.contains("garmentServiceId")) 
	            json.put("garmentServiceId", ""+_CleanerProduct.getGarmentServiceId());
            if ( ignoreFieldSet || fieldSet.contains("regularPrice")) 
	            json.put("regularPrice", ""+_CleanerProduct.getRegularPrice());
            if ( ignoreFieldSet || fieldSet.contains("note")) 
	            json.put("note", ""+_CleanerProduct.getNote());

		}
		return json;

	}

}