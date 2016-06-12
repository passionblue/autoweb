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

import com.autosite.db.CleanerServiceItem;


public class CleanerServiceItemDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("serviceId", new Integer(0));
        fieldsMap.put("serviceItemId", new Integer(1));
        fieldsMap.put("itemType", new Integer(2));
        fieldsMap.put("title", new Integer(3));
        fieldsMap.put("imagePath", new Integer(4));
        fieldsMap.put("imagePathLocal", new Integer(5));
        fieldsMap.put("basePrice", new Integer(6));
        fieldsMap.put("note", new Integer(7));
    }


	CleanerServiceItem m_cleanerServiceItem;

	public CleanerServiceItemDataHolder(CleanerServiceItem _CleanerServiceItem){
		m_cleanerServiceItem =  _CleanerServiceItem; 	
	}

	public CleanerServiceItemDataHolder(){
		m_cleanerServiceItem =  new CleanerServiceItem(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerServiceItem;
	}


	public long getId(){
		return 	m_cleanerServiceItem.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerServiceItem.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerServiceItem.setSiteId(sid);
	}


	public long getServiceId(){
		return m_cleanerServiceItem.getServiceId();
	}
	public long getServiceItemId(){
		return m_cleanerServiceItem.getServiceItemId();
	}
	public int getItemType(){
		return m_cleanerServiceItem.getItemType();
	}
	public String getTitle(){
		return m_cleanerServiceItem.getTitle();
	}
	public String getImagePath(){
		return m_cleanerServiceItem.getImagePath();
	}
	public String getImagePathLocal(){
		return m_cleanerServiceItem.getImagePathLocal();
	}
	public double getBasePrice(){
		return m_cleanerServiceItem.getBasePrice();
	}
	public String getNote(){
		return m_cleanerServiceItem.getNote();
	}

	public void setServiceId(long _serviceId ){
		m_cleanerServiceItem.setServiceId(_serviceId);
	}
	public void setServiceItemId(long _serviceItemId ){
		m_cleanerServiceItem.setServiceItemId(_serviceItemId);
	}
	public void setItemType(int _itemType ){
		m_cleanerServiceItem.setItemType(_itemType);
	}
	public void setTitle(String _title ){
		m_cleanerServiceItem.setTitle(_title);
	}
	public void setImagePath(String _imagePath ){
		m_cleanerServiceItem.setImagePath(_imagePath);
	}
	public void setImagePathLocal(String _imagePathLocal ){
		m_cleanerServiceItem.setImagePathLocal(_imagePathLocal);
	}
	public void setBasePrice(double _basePrice ){
		m_cleanerServiceItem.setBasePrice(_basePrice);
	}
	public void setNote(String _note ){
		m_cleanerServiceItem.setNote(_note);
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

        if ( colNum == 0) return String.valueOf(getServiceId());
        if ( colNum == 1) return String.valueOf(getServiceItemId());
        if ( colNum == 2) return String.valueOf(getItemType());
        if ( colNum == 3) return getTitle() == null?"":getTitle().toString();
        if ( colNum == 4) return getImagePath() == null?"":getImagePath().toString();
        if ( colNum == 5) return getImagePathLocal() == null?"":getImagePathLocal().toString();
        if ( colNum == 6) return String.valueOf(getBasePrice());
        if ( colNum == 7) return getNote() == null?"":getNote().toString();
		return "";
    }


    public static JSONObject convertToJSON(CleanerServiceItem _CleanerServiceItem, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        
        JSONObject json = new JSONObject();
        if ( _CleanerServiceItem == null) return json;

        json.put("id", ""+_CleanerServiceItem.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonServiceId = new JSONObject();
		            jsonServiceId.put("name", "serviceId");
		            jsonServiceId.put("value", ""+_CleanerServiceItem.getServiceId());
		            array.put(jsonServiceId);
		            JSONObject jsonServiceItemId = new JSONObject();
		            jsonServiceItemId.put("name", "serviceItemId");
		            jsonServiceItemId.put("value", ""+_CleanerServiceItem.getServiceItemId());
		            array.put(jsonServiceItemId);
		            JSONObject jsonItemType = new JSONObject();
		            jsonItemType.put("name", "itemType");
		            jsonItemType.put("value", ""+_CleanerServiceItem.getItemType());
		            array.put(jsonItemType);
		            JSONObject jsonTitle = new JSONObject();
		            jsonTitle.put("name", "title");
		            jsonTitle.put("value", ""+_CleanerServiceItem.getTitle());
		            array.put(jsonTitle);
		            JSONObject jsonImagePath = new JSONObject();
		            jsonImagePath.put("name", "imagePath");
		            jsonImagePath.put("value", ""+_CleanerServiceItem.getImagePath());
		            array.put(jsonImagePath);
		            JSONObject jsonImagePathLocal = new JSONObject();
		            jsonImagePathLocal.put("name", "imagePathLocal");
		            jsonImagePathLocal.put("value", ""+_CleanerServiceItem.getImagePathLocal());
		            array.put(jsonImagePathLocal);
		            JSONObject jsonBasePrice = new JSONObject();
		            jsonBasePrice.put("name", "basePrice");
		            jsonBasePrice.put("value", ""+_CleanerServiceItem.getBasePrice());
		            array.put(jsonBasePrice);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerServiceItem.getNote());
		            array.put(jsonNote);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("serviceId")) 
	            json.put("serviceId", ""+_CleanerServiceItem.getServiceId());
            if ( ignoreFieldSet || fieldSet.contains("serviceItemId")) 
	            json.put("serviceItemId", ""+_CleanerServiceItem.getServiceItemId());
            if ( ignoreFieldSet || fieldSet.contains("itemType")) 
	            json.put("itemType", ""+_CleanerServiceItem.getItemType());
            if ( ignoreFieldSet || fieldSet.contains("title")) 
	            json.put("title", ""+_CleanerServiceItem.getTitle());
            if ( ignoreFieldSet || fieldSet.contains("imagePath")) 
	            json.put("imagePath", ""+_CleanerServiceItem.getImagePath());
            if ( ignoreFieldSet || fieldSet.contains("imagePathLocal")) 
	            json.put("imagePathLocal", ""+_CleanerServiceItem.getImagePathLocal());
            if ( ignoreFieldSet || fieldSet.contains("basePrice")) 
	            json.put("basePrice", ""+_CleanerServiceItem.getBasePrice());
            if ( ignoreFieldSet || fieldSet.contains("note")) 
	            json.put("note", ""+_CleanerServiceItem.getNote());

		}
		return json;

	}

}