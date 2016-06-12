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

import com.autosite.db.CleanerTicketItem;


public class CleanerTicketItemDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("ticketId", new Integer(0));
        fieldsMap.put("parentTicketId", new Integer(1));
        fieldsMap.put("productId", new Integer(2));
        fieldsMap.put("subtotalAmount", new Integer(3));
        fieldsMap.put("totalAmount", new Integer(4));
        fieldsMap.put("discountId", new Integer(5));
        fieldsMap.put("totalDiscountAmount", new Integer(6));
        fieldsMap.put("specialDiscountAmount", new Integer(7));
        fieldsMap.put("note", new Integer(8));
        fieldsMap.put("timeCreated", new Integer(9));
        fieldsMap.put("timeUpdated", new Integer(10));
    }


	CleanerTicketItem m_cleanerTicketItem;

	public CleanerTicketItemDataHolder(CleanerTicketItem _CleanerTicketItem){
		m_cleanerTicketItem =  _CleanerTicketItem; 	
	}

	public CleanerTicketItemDataHolder(){
		m_cleanerTicketItem =  new CleanerTicketItem(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerTicketItem;
	}


	public long getId(){
		return 	m_cleanerTicketItem.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerTicketItem.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerTicketItem.setSiteId(sid);
	}


	public long getTicketId(){
		return m_cleanerTicketItem.getTicketId();
	}
	public long getParentTicketId(){
		return m_cleanerTicketItem.getParentTicketId();
	}
	public long getProductId(){
		return m_cleanerTicketItem.getProductId();
	}
	public double getSubtotalAmount(){
		return m_cleanerTicketItem.getSubtotalAmount();
	}
	public double getTotalAmount(){
		return m_cleanerTicketItem.getTotalAmount();
	}
	public long getDiscountId(){
		return m_cleanerTicketItem.getDiscountId();
	}
	public double getTotalDiscountAmount(){
		return m_cleanerTicketItem.getTotalDiscountAmount();
	}
	public double getSpecialDiscountAmount(){
		return m_cleanerTicketItem.getSpecialDiscountAmount();
	}
	public String getNote(){
		return m_cleanerTicketItem.getNote();
	}
	public Timestamp getTimeCreated(){
		return m_cleanerTicketItem.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_cleanerTicketItem.getTimeUpdated();
	}

	public void setTicketId(long _ticketId ){
		m_cleanerTicketItem.setTicketId(_ticketId);
	}
	public void setParentTicketId(long _parentTicketId ){
		m_cleanerTicketItem.setParentTicketId(_parentTicketId);
	}
	public void setProductId(long _productId ){
		m_cleanerTicketItem.setProductId(_productId);
	}
	public void setSubtotalAmount(double _subtotalAmount ){
		m_cleanerTicketItem.setSubtotalAmount(_subtotalAmount);
	}
	public void setTotalAmount(double _totalAmount ){
		m_cleanerTicketItem.setTotalAmount(_totalAmount);
	}
	public void setDiscountId(long _discountId ){
		m_cleanerTicketItem.setDiscountId(_discountId);
	}
	public void setTotalDiscountAmount(double _totalDiscountAmount ){
		m_cleanerTicketItem.setTotalDiscountAmount(_totalDiscountAmount);
	}
	public void setSpecialDiscountAmount(double _specialDiscountAmount ){
		m_cleanerTicketItem.setSpecialDiscountAmount(_specialDiscountAmount);
	}
	public void setNote(String _note ){
		m_cleanerTicketItem.setNote(_note);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_cleanerTicketItem.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_cleanerTicketItem.setTimeUpdated(_timeUpdated);
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
        if ( colNum == 1) return String.valueOf(getParentTicketId());
        if ( colNum == 2) return String.valueOf(getProductId());
        if ( colNum == 3) return String.valueOf(getSubtotalAmount());
        if ( colNum == 4) return String.valueOf(getTotalAmount());
        if ( colNum == 5) return String.valueOf(getDiscountId());
        if ( colNum == 6) return String.valueOf(getTotalDiscountAmount());
        if ( colNum == 7) return String.valueOf(getSpecialDiscountAmount());
        if ( colNum == 8) return getNote() == null?"":getNote().toString();
        if ( colNum == 9) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 10) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }


    public static JSONObject convertToJSON(CleanerTicketItem _CleanerTicketItem, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        
        JSONObject json = new JSONObject();
        if ( _CleanerTicketItem == null) return json;

        json.put("id", ""+_CleanerTicketItem.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonTicketId = new JSONObject();
		            jsonTicketId.put("name", "ticketId");
		            jsonTicketId.put("value", ""+_CleanerTicketItem.getTicketId());
		            array.put(jsonTicketId);
		            JSONObject jsonParentTicketId = new JSONObject();
		            jsonParentTicketId.put("name", "parentTicketId");
		            jsonParentTicketId.put("value", ""+_CleanerTicketItem.getParentTicketId());
		            array.put(jsonParentTicketId);
		            JSONObject jsonProductId = new JSONObject();
		            jsonProductId.put("name", "productId");
		            jsonProductId.put("value", ""+_CleanerTicketItem.getProductId());
		            array.put(jsonProductId);
		            JSONObject jsonSubtotalAmount = new JSONObject();
		            jsonSubtotalAmount.put("name", "subtotalAmount");
		            jsonSubtotalAmount.put("value", ""+_CleanerTicketItem.getSubtotalAmount());
		            array.put(jsonSubtotalAmount);
		            JSONObject jsonTotalAmount = new JSONObject();
		            jsonTotalAmount.put("name", "totalAmount");
		            jsonTotalAmount.put("value", ""+_CleanerTicketItem.getTotalAmount());
		            array.put(jsonTotalAmount);
		            JSONObject jsonDiscountId = new JSONObject();
		            jsonDiscountId.put("name", "discountId");
		            jsonDiscountId.put("value", ""+_CleanerTicketItem.getDiscountId());
		            array.put(jsonDiscountId);
		            JSONObject jsonTotalDiscountAmount = new JSONObject();
		            jsonTotalDiscountAmount.put("name", "totalDiscountAmount");
		            jsonTotalDiscountAmount.put("value", ""+_CleanerTicketItem.getTotalDiscountAmount());
		            array.put(jsonTotalDiscountAmount);
		            JSONObject jsonSpecialDiscountAmount = new JSONObject();
		            jsonSpecialDiscountAmount.put("name", "specialDiscountAmount");
		            jsonSpecialDiscountAmount.put("value", ""+_CleanerTicketItem.getSpecialDiscountAmount());
		            array.put(jsonSpecialDiscountAmount);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerTicketItem.getNote());
		            array.put(jsonNote);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            jsonTimeCreated.put("value", ""+_CleanerTicketItem.getTimeCreated());
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            jsonTimeUpdated.put("value", ""+_CleanerTicketItem.getTimeUpdated());
		            array.put(jsonTimeUpdated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("ticketId")) 
	            json.put("ticketId", ""+_CleanerTicketItem.getTicketId());
            if ( ignoreFieldSet || fieldSet.contains("parentTicketId")) 
	            json.put("parentTicketId", ""+_CleanerTicketItem.getParentTicketId());
            if ( ignoreFieldSet || fieldSet.contains("productId")) 
	            json.put("productId", ""+_CleanerTicketItem.getProductId());
            if ( ignoreFieldSet || fieldSet.contains("subtotalAmount")) 
	            json.put("subtotalAmount", ""+_CleanerTicketItem.getSubtotalAmount());
            if ( ignoreFieldSet || fieldSet.contains("totalAmount")) 
	            json.put("totalAmount", ""+_CleanerTicketItem.getTotalAmount());
            if ( ignoreFieldSet || fieldSet.contains("discountId")) 
	            json.put("discountId", ""+_CleanerTicketItem.getDiscountId());
            if ( ignoreFieldSet || fieldSet.contains("totalDiscountAmount")) 
	            json.put("totalDiscountAmount", ""+_CleanerTicketItem.getTotalDiscountAmount());
            if ( ignoreFieldSet || fieldSet.contains("specialDiscountAmount")) 
	            json.put("specialDiscountAmount", ""+_CleanerTicketItem.getSpecialDiscountAmount());
            if ( ignoreFieldSet || fieldSet.contains("note")) 
	            json.put("note", ""+_CleanerTicketItem.getNote());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_CleanerTicketItem.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
	            json.put("timeUpdated", ""+_CleanerTicketItem.getTimeUpdated());

		}
		return json;

	}

}