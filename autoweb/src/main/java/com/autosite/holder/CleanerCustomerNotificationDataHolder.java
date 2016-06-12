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
import com.jtrend.util.JSONValueFormatter;

import com.autosite.db.CleanerCustomerNotification;


public class CleanerCustomerNotificationDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("customerId", new Integer(0));
        fieldsMap.put("reasonforId", new Integer(1));
        fieldsMap.put("reasonforTarget", new Integer(2));
        fieldsMap.put("notificationType", new Integer(3));
        fieldsMap.put("sourceType", new Integer(4));
        fieldsMap.put("triggerType", new Integer(5));
        fieldsMap.put("isRetransmit", new Integer(6));
        fieldsMap.put("methodType", new Integer(7));
        fieldsMap.put("templateType", new Integer(8));
        fieldsMap.put("content", new Integer(9));
        fieldsMap.put("destination", new Integer(10));
        fieldsMap.put("reference", new Integer(11));
        fieldsMap.put("timeScheduled", new Integer(12));
        fieldsMap.put("timeCreated", new Integer(13));
        fieldsMap.put("timeSent", new Integer(14));
    }


	CleanerCustomerNotification m_cleanerCustomerNotification;

	public CleanerCustomerNotificationDataHolder(CleanerCustomerNotification _CleanerCustomerNotification){
		m_cleanerCustomerNotification =  _CleanerCustomerNotification; 	
	}

	public CleanerCustomerNotificationDataHolder(){
		m_cleanerCustomerNotification =  new CleanerCustomerNotification(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerCustomerNotification;
	}


	public long getId(){
		return 	m_cleanerCustomerNotification.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerCustomerNotification.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerCustomerNotification.setSiteId(sid);
	}


	public long getCustomerId(){
		return m_cleanerCustomerNotification.getCustomerId();
	}
	public long getReasonforId(){
		return m_cleanerCustomerNotification.getReasonforId();
	}
	public String getReasonforTarget(){
		return m_cleanerCustomerNotification.getReasonforTarget();
	}
	public int getNotificationType(){
		return m_cleanerCustomerNotification.getNotificationType();
	}
	public int getSourceType(){
		return m_cleanerCustomerNotification.getSourceType();
	}
	public int getTriggerType(){
		return m_cleanerCustomerNotification.getTriggerType();
	}
	public int getIsRetransmit(){
		return m_cleanerCustomerNotification.getIsRetransmit();
	}
	public int getMethodType(){
		return m_cleanerCustomerNotification.getMethodType();
	}
	public String getTemplateType(){
		return m_cleanerCustomerNotification.getTemplateType();
	}
	public String getContent(){
		return m_cleanerCustomerNotification.getContent();
	}
	public String getDestination(){
		return m_cleanerCustomerNotification.getDestination();
	}
	public String getReference(){
		return m_cleanerCustomerNotification.getReference();
	}
	public Timestamp getTimeScheduled(){
		return m_cleanerCustomerNotification.getTimeScheduled();
	}
	public Timestamp getTimeCreated(){
		return m_cleanerCustomerNotification.getTimeCreated();
	}
	public Timestamp getTimeSent(){
		return m_cleanerCustomerNotification.getTimeSent();
	}

	public void setCustomerId(long _customerId ){
		m_cleanerCustomerNotification.setCustomerId(_customerId);
	}
	public void setReasonforId(long _reasonforId ){
		m_cleanerCustomerNotification.setReasonforId(_reasonforId);
	}
	public void setReasonforTarget(String _reasonforTarget ){
		m_cleanerCustomerNotification.setReasonforTarget(_reasonforTarget);
	}
	public void setNotificationType(int _notificationType ){
		m_cleanerCustomerNotification.setNotificationType(_notificationType);
	}
	public void setSourceType(int _sourceType ){
		m_cleanerCustomerNotification.setSourceType(_sourceType);
	}
	public void setTriggerType(int _triggerType ){
		m_cleanerCustomerNotification.setTriggerType(_triggerType);
	}
	public void setIsRetransmit(int _isRetransmit ){
		m_cleanerCustomerNotification.setIsRetransmit(_isRetransmit);
	}
	public void setMethodType(int _methodType ){
		m_cleanerCustomerNotification.setMethodType(_methodType);
	}
	public void setTemplateType(String _templateType ){
		m_cleanerCustomerNotification.setTemplateType(_templateType);
	}
	public void setContent(String _content ){
		m_cleanerCustomerNotification.setContent(_content);
	}
	public void setDestination(String _destination ){
		m_cleanerCustomerNotification.setDestination(_destination);
	}
	public void setReference(String _reference ){
		m_cleanerCustomerNotification.setReference(_reference);
	}
	public void setTimeScheduled(Timestamp _timeScheduled ){
		m_cleanerCustomerNotification.setTimeScheduled(_timeScheduled);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_cleanerCustomerNotification.setTimeCreated(_timeCreated);
	}
	public void setTimeSent(Timestamp _timeSent ){
		m_cleanerCustomerNotification.setTimeSent(_timeSent);
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
        if ( colNum == 1) return String.valueOf(getReasonforId());
        if ( colNum == 2) return getReasonforTarget() == null?"":getReasonforTarget().toString();
        if ( colNum == 3) return String.valueOf(getNotificationType());
        if ( colNum == 4) return String.valueOf(getSourceType());
        if ( colNum == 5) return String.valueOf(getTriggerType());
        if ( colNum == 6) return String.valueOf(getIsRetransmit());
        if ( colNum == 7) return String.valueOf(getMethodType());
        if ( colNum == 8) return getTemplateType() == null?"":getTemplateType().toString();
        if ( colNum == 9) return getContent() == null?"":getContent().toString();
        if ( colNum == 10) return getDestination() == null?"":getDestination().toString();
        if ( colNum == 11) return getReference() == null?"":getReference().toString();
        if ( colNum == 12) return getTimeScheduled() == null?"":getTimeScheduled().toString();
        if ( colNum == 13) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 14) return getTimeSent() == null?"":getTimeSent().toString();
		return "";
    }


    public static JSONObject convertToJSON(CleanerCustomerNotification _CleanerCustomerNotification, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_CleanerCustomerNotification, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(CleanerCustomerNotification _CleanerCustomerNotification, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _CleanerCustomerNotification == null) return json;

        json.put("id", ""+_CleanerCustomerNotification.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonCustomerId = new JSONObject();
		            jsonCustomerId.put("name", "customerId");
		            if ( useJsonDataType ) 
	                    jsonCustomerId.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getCustomerId()));
		            else
		                jsonCustomerId.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getCustomerId()));
		            array.put(jsonCustomerId);
		            JSONObject jsonReasonforId = new JSONObject();
		            jsonReasonforId.put("name", "reasonforId");
		            if ( useJsonDataType ) 
	                    jsonReasonforId.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getReasonforId()));
		            else
		                jsonReasonforId.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getReasonforId()));
		            array.put(jsonReasonforId);
		            JSONObject jsonReasonforTarget = new JSONObject();
		            jsonReasonforTarget.put("name", "reasonforTarget");
		            if ( useJsonDataType ) 
	                    jsonReasonforTarget.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getReasonforTarget()));
		            else
		                jsonReasonforTarget.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getReasonforTarget()));
		            array.put(jsonReasonforTarget);
		            JSONObject jsonNotificationType = new JSONObject();
		            jsonNotificationType.put("name", "notificationType");
		            if ( useJsonDataType ) 
	                    jsonNotificationType.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getNotificationType()));
		            else
		                jsonNotificationType.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getNotificationType()));
		            array.put(jsonNotificationType);
		            JSONObject jsonSourceType = new JSONObject();
		            jsonSourceType.put("name", "sourceType");
		            if ( useJsonDataType ) 
	                    jsonSourceType.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getSourceType()));
		            else
		                jsonSourceType.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getSourceType()));
		            array.put(jsonSourceType);
		            JSONObject jsonTriggerType = new JSONObject();
		            jsonTriggerType.put("name", "triggerType");
		            if ( useJsonDataType ) 
	                    jsonTriggerType.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getTriggerType()));
		            else
		                jsonTriggerType.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getTriggerType()));
		            array.put(jsonTriggerType);
		            JSONObject jsonIsRetransmit = new JSONObject();
		            jsonIsRetransmit.put("name", "isRetransmit");
		            if ( useJsonDataType ) 
	                    jsonIsRetransmit.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getIsRetransmit()));
		            else
		                jsonIsRetransmit.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getIsRetransmit()));
		            array.put(jsonIsRetransmit);
		            JSONObject jsonMethodType = new JSONObject();
		            jsonMethodType.put("name", "methodType");
		            if ( useJsonDataType ) 
	                    jsonMethodType.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getMethodType()));
		            else
		                jsonMethodType.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getMethodType()));
		            array.put(jsonMethodType);
		            JSONObject jsonTemplateType = new JSONObject();
		            jsonTemplateType.put("name", "templateType");
		            if ( useJsonDataType ) 
	                    jsonTemplateType.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getTemplateType()));
		            else
		                jsonTemplateType.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getTemplateType()));
		            array.put(jsonTemplateType);
		            JSONObject jsonContent = new JSONObject();
		            jsonContent.put("name", "content");
		            if ( useJsonDataType ) 
	                    jsonContent.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getContent()));
		            else
		                jsonContent.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getContent()));
		            array.put(jsonContent);
		            JSONObject jsonDestination = new JSONObject();
		            jsonDestination.put("name", "destination");
		            if ( useJsonDataType ) 
	                    jsonDestination.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getDestination()));
		            else
		                jsonDestination.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getDestination()));
		            array.put(jsonDestination);
		            JSONObject jsonReference = new JSONObject();
		            jsonReference.put("name", "reference");
		            if ( useJsonDataType ) 
	                    jsonReference.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getReference()));
		            else
		                jsonReference.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getReference()));
		            array.put(jsonReference);
		            JSONObject jsonTimeScheduled = new JSONObject();
		            jsonTimeScheduled.put("name", "timeScheduled");
		            if ( useJsonDataType ) 
	                    jsonTimeScheduled.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getTimeScheduled()));
		            else
		                jsonTimeScheduled.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getTimeScheduled()));
		            array.put(jsonTimeScheduled);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeSent = new JSONObject();
		            jsonTimeSent.put("name", "timeSent");
		            if ( useJsonDataType ) 
	                    jsonTimeSent.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomerNotification.getTimeSent()));
		            else
		                jsonTimeSent.put("value", JSONValueFormatter.toStringValue(_CleanerCustomerNotification.getTimeSent()));
		            array.put(jsonTimeSent);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("customerId")) 
	            json.put("customerId", ""+_CleanerCustomerNotification.getCustomerId());
            if ( ignoreFieldSet || fieldSet.contains("reasonforId")) 
	            json.put("reasonforId", ""+_CleanerCustomerNotification.getReasonforId());
            if ( ignoreFieldSet || fieldSet.contains("reasonforTarget")) 
	            json.put("reasonforTarget", ""+_CleanerCustomerNotification.getReasonforTarget());
            if ( ignoreFieldSet || fieldSet.contains("notificationType")) 
	            json.put("notificationType", ""+_CleanerCustomerNotification.getNotificationType());
            if ( ignoreFieldSet || fieldSet.contains("sourceType")) 
	            json.put("sourceType", ""+_CleanerCustomerNotification.getSourceType());
            if ( ignoreFieldSet || fieldSet.contains("triggerType")) 
	            json.put("triggerType", ""+_CleanerCustomerNotification.getTriggerType());
            if ( ignoreFieldSet || fieldSet.contains("isRetransmit")) 
	            json.put("isRetransmit", ""+_CleanerCustomerNotification.getIsRetransmit());
            if ( ignoreFieldSet || fieldSet.contains("methodType")) 
	            json.put("methodType", ""+_CleanerCustomerNotification.getMethodType());
            if ( ignoreFieldSet || fieldSet.contains("templateType")) 
	            json.put("templateType", ""+_CleanerCustomerNotification.getTemplateType());
            if ( ignoreFieldSet || fieldSet.contains("content")) 
	            json.put("content", ""+_CleanerCustomerNotification.getContent());
            if ( ignoreFieldSet || fieldSet.contains("destination")) 
	            json.put("destination", ""+_CleanerCustomerNotification.getDestination());
            if ( ignoreFieldSet || fieldSet.contains("reference")) 
	            json.put("reference", ""+_CleanerCustomerNotification.getReference());
            if ( ignoreFieldSet || fieldSet.contains("timeScheduled")) 
	            json.put("timeScheduled", ""+_CleanerCustomerNotification.getTimeScheduled());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_CleanerCustomerNotification.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("timeSent")) 
	            json.put("timeSent", ""+_CleanerCustomerNotification.getTimeSent());

		}
		return json;

	}

}