/* 
Template last modification history:


Source Generated: Sun Mar 15 13:54:44 EDT 2015
*/

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

import com.autosite.db.CleanerPickupDelivery;


public class CleanerPickupDeliveryDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("locationId", new Integer(0));
        fieldsMap.put("customerId", new Integer(1));
        fieldsMap.put("ticketId", new Integer(2));
        fieldsMap.put("ticketUid", new Integer(3));
        fieldsMap.put("pickupTicket", new Integer(4));
        fieldsMap.put("checkinTicketForDelivery", new Integer(5));
        fieldsMap.put("isDeliveryRequest", new Integer(6));
        fieldsMap.put("isWebRequest", new Integer(7));
        fieldsMap.put("isRecurringRequest", new Integer(8));
        fieldsMap.put("isReceiveReady", new Integer(9));
        fieldsMap.put("isReceiveComplete", new Integer(10));
        fieldsMap.put("recurId", new Integer(11));
        fieldsMap.put("cancelled", new Integer(12));
        fieldsMap.put("completed", new Integer(13));
        fieldsMap.put("customerName", new Integer(14));
        fieldsMap.put("address", new Integer(15));
        fieldsMap.put("aptNumber", new Integer(16));
        fieldsMap.put("phone", new Integer(17));
        fieldsMap.put("email", new Integer(18));
        fieldsMap.put("ackReceiveMethod", new Integer(19));
        fieldsMap.put("customerInstruction", new Integer(20));
        fieldsMap.put("pickupDeliveryByDay", new Integer(21));
        fieldsMap.put("pickupDeliveryByTime", new Integer(22));
        fieldsMap.put("timeCreated", new Integer(23));
        fieldsMap.put("timeUpdated", new Integer(24));
        fieldsMap.put("timeAcked", new Integer(25));
        fieldsMap.put("ackedByUserId", new Integer(26));
        fieldsMap.put("timeReady", new Integer(27));
        fieldsMap.put("timeNotified", new Integer(28));
        fieldsMap.put("timeCompleted", new Integer(29));
        fieldsMap.put("note", new Integer(30));
        fieldsMap.put("pickupNote", new Integer(31));
        fieldsMap.put("deliveryNote", new Integer(32));
    }


	CleanerPickupDelivery m_cleanerPickupDelivery;

	public CleanerPickupDeliveryDataHolder(CleanerPickupDelivery _CleanerPickupDelivery){
		m_cleanerPickupDelivery =  _CleanerPickupDelivery; 	
	}

	public CleanerPickupDeliveryDataHolder(){
		m_cleanerPickupDelivery =  new CleanerPickupDelivery(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerPickupDelivery;
	}


	public long getId(){
		return 	m_cleanerPickupDelivery.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerPickupDelivery.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerPickupDelivery.setSiteId(sid);
	}


	public long getLocationId(){
		return m_cleanerPickupDelivery.getLocationId();
	}
	public long getCustomerId(){
		return m_cleanerPickupDelivery.getCustomerId();
	}
	public long getTicketId(){
		return m_cleanerPickupDelivery.getTicketId();
	}
	public String getTicketUid(){
		return m_cleanerPickupDelivery.getTicketUid();
	}
	public String getPickupTicket(){
		return m_cleanerPickupDelivery.getPickupTicket();
	}
	public String getCheckinTicketForDelivery(){
		return m_cleanerPickupDelivery.getCheckinTicketForDelivery();
	}
	public int getIsDeliveryRequest(){
		return m_cleanerPickupDelivery.getIsDeliveryRequest();
	}
	public int getIsWebRequest(){
		return m_cleanerPickupDelivery.getIsWebRequest();
	}
	public int getIsRecurringRequest(){
		return m_cleanerPickupDelivery.getIsRecurringRequest();
	}
	public int getIsReceiveReady(){
		return m_cleanerPickupDelivery.getIsReceiveReady();
	}
	public int getIsReceiveComplete(){
		return m_cleanerPickupDelivery.getIsReceiveComplete();
	}
	public long getRecurId(){
		return m_cleanerPickupDelivery.getRecurId();
	}
	public int getCancelled(){
		return m_cleanerPickupDelivery.getCancelled();
	}
	public int getCompleted(){
		return m_cleanerPickupDelivery.getCompleted();
	}
	public String getCustomerName(){
		return m_cleanerPickupDelivery.getCustomerName();
	}
	public String getAddress(){
		return m_cleanerPickupDelivery.getAddress();
	}
	public String getAptNumber(){
		return m_cleanerPickupDelivery.getAptNumber();
	}
	public String getPhone(){
		return m_cleanerPickupDelivery.getPhone();
	}
	public String getEmail(){
		return m_cleanerPickupDelivery.getEmail();
	}
	public int getAckReceiveMethod(){
		return m_cleanerPickupDelivery.getAckReceiveMethod();
	}
	public String getCustomerInstruction(){
		return m_cleanerPickupDelivery.getCustomerInstruction();
	}
	public int getPickupDeliveryByDay(){
		return m_cleanerPickupDelivery.getPickupDeliveryByDay();
	}
	public String getPickupDeliveryByTime(){
		return m_cleanerPickupDelivery.getPickupDeliveryByTime();
	}
	public Timestamp getTimeCreated(){
		return m_cleanerPickupDelivery.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_cleanerPickupDelivery.getTimeUpdated();
	}
	public Timestamp getTimeAcked(){
		return m_cleanerPickupDelivery.getTimeAcked();
	}
	public long getAckedByUserId(){
		return m_cleanerPickupDelivery.getAckedByUserId();
	}
	public Timestamp getTimeReady(){
		return m_cleanerPickupDelivery.getTimeReady();
	}
	public Timestamp getTimeNotified(){
		return m_cleanerPickupDelivery.getTimeNotified();
	}
	public Timestamp getTimeCompleted(){
		return m_cleanerPickupDelivery.getTimeCompleted();
	}
	public String getNote(){
		return m_cleanerPickupDelivery.getNote();
	}
	public String getPickupNote(){
		return m_cleanerPickupDelivery.getPickupNote();
	}
	public String getDeliveryNote(){
		return m_cleanerPickupDelivery.getDeliveryNote();
	}

	public void setLocationId(long _locationId ){
		m_cleanerPickupDelivery.setLocationId(_locationId);
	}
	public void setCustomerId(long _customerId ){
		m_cleanerPickupDelivery.setCustomerId(_customerId);
	}
	public void setTicketId(long _ticketId ){
		m_cleanerPickupDelivery.setTicketId(_ticketId);
	}
	public void setTicketUid(String _ticketUid ){
		m_cleanerPickupDelivery.setTicketUid(_ticketUid);
	}
	public void setPickupTicket(String _pickupTicket ){
		m_cleanerPickupDelivery.setPickupTicket(_pickupTicket);
	}
	public void setCheckinTicketForDelivery(String _checkinTicketForDelivery ){
		m_cleanerPickupDelivery.setCheckinTicketForDelivery(_checkinTicketForDelivery);
	}
	public void setIsDeliveryRequest(int _isDeliveryRequest ){
		m_cleanerPickupDelivery.setIsDeliveryRequest(_isDeliveryRequest);
	}
	public void setIsWebRequest(int _isWebRequest ){
		m_cleanerPickupDelivery.setIsWebRequest(_isWebRequest);
	}
	public void setIsRecurringRequest(int _isRecurringRequest ){
		m_cleanerPickupDelivery.setIsRecurringRequest(_isRecurringRequest);
	}
	public void setIsReceiveReady(int _isReceiveReady ){
		m_cleanerPickupDelivery.setIsReceiveReady(_isReceiveReady);
	}
	public void setIsReceiveComplete(int _isReceiveComplete ){
		m_cleanerPickupDelivery.setIsReceiveComplete(_isReceiveComplete);
	}
	public void setRecurId(long _recurId ){
		m_cleanerPickupDelivery.setRecurId(_recurId);
	}
	public void setCancelled(int _cancelled ){
		m_cleanerPickupDelivery.setCancelled(_cancelled);
	}
	public void setCompleted(int _completed ){
		m_cleanerPickupDelivery.setCompleted(_completed);
	}
	public void setCustomerName(String _customerName ){
		m_cleanerPickupDelivery.setCustomerName(_customerName);
	}
	public void setAddress(String _address ){
		m_cleanerPickupDelivery.setAddress(_address);
	}
	public void setAptNumber(String _aptNumber ){
		m_cleanerPickupDelivery.setAptNumber(_aptNumber);
	}
	public void setPhone(String _phone ){
		m_cleanerPickupDelivery.setPhone(_phone);
	}
	public void setEmail(String _email ){
		m_cleanerPickupDelivery.setEmail(_email);
	}
	public void setAckReceiveMethod(int _ackReceiveMethod ){
		m_cleanerPickupDelivery.setAckReceiveMethod(_ackReceiveMethod);
	}
	public void setCustomerInstruction(String _customerInstruction ){
		m_cleanerPickupDelivery.setCustomerInstruction(_customerInstruction);
	}
	public void setPickupDeliveryByDay(int _pickupDeliveryByDay ){
		m_cleanerPickupDelivery.setPickupDeliveryByDay(_pickupDeliveryByDay);
	}
	public void setPickupDeliveryByTime(String _pickupDeliveryByTime ){
		m_cleanerPickupDelivery.setPickupDeliveryByTime(_pickupDeliveryByTime);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_cleanerPickupDelivery.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_cleanerPickupDelivery.setTimeUpdated(_timeUpdated);
	}
	public void setTimeAcked(Timestamp _timeAcked ){
		m_cleanerPickupDelivery.setTimeAcked(_timeAcked);
	}
	public void setAckedByUserId(long _ackedByUserId ){
		m_cleanerPickupDelivery.setAckedByUserId(_ackedByUserId);
	}
	public void setTimeReady(Timestamp _timeReady ){
		m_cleanerPickupDelivery.setTimeReady(_timeReady);
	}
	public void setTimeNotified(Timestamp _timeNotified ){
		m_cleanerPickupDelivery.setTimeNotified(_timeNotified);
	}
	public void setTimeCompleted(Timestamp _timeCompleted ){
		m_cleanerPickupDelivery.setTimeCompleted(_timeCompleted);
	}
	public void setNote(String _note ){
		m_cleanerPickupDelivery.setNote(_note);
	}
	public void setPickupNote(String _pickupNote ){
		m_cleanerPickupDelivery.setPickupNote(_pickupNote);
	}
	public void setDeliveryNote(String _deliveryNote ){
		m_cleanerPickupDelivery.setDeliveryNote(_deliveryNote);
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

        if ( colNum == 0) return String.valueOf(getLocationId());
        if ( colNum == 1) return String.valueOf(getCustomerId());
        if ( colNum == 2) return String.valueOf(getTicketId());
        if ( colNum == 3) return getTicketUid() == null?"":getTicketUid().toString();
        if ( colNum == 4) return getPickupTicket() == null?"":getPickupTicket().toString();
        if ( colNum == 5) return getCheckinTicketForDelivery() == null?"":getCheckinTicketForDelivery().toString();
        if ( colNum == 6) return String.valueOf(getIsDeliveryRequest());
        if ( colNum == 7) return String.valueOf(getIsWebRequest());
        if ( colNum == 8) return String.valueOf(getIsRecurringRequest());
        if ( colNum == 9) return String.valueOf(getIsReceiveReady());
        if ( colNum == 10) return String.valueOf(getIsReceiveComplete());
        if ( colNum == 11) return String.valueOf(getRecurId());
        if ( colNum == 12) return String.valueOf(getCancelled());
        if ( colNum == 13) return String.valueOf(getCompleted());
        if ( colNum == 14) return getCustomerName() == null?"":getCustomerName().toString();
        if ( colNum == 15) return getAddress() == null?"":getAddress().toString();
        if ( colNum == 16) return getAptNumber() == null?"":getAptNumber().toString();
        if ( colNum == 17) return getPhone() == null?"":getPhone().toString();
        if ( colNum == 18) return getEmail() == null?"":getEmail().toString();
        if ( colNum == 19) return String.valueOf(getAckReceiveMethod());
        if ( colNum == 20) return getCustomerInstruction() == null?"":getCustomerInstruction().toString();
        if ( colNum == 21) return String.valueOf(getPickupDeliveryByDay());
        if ( colNum == 22) return getPickupDeliveryByTime() == null?"":getPickupDeliveryByTime().toString();
        if ( colNum == 23) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 24) return getTimeUpdated() == null?"":getTimeUpdated().toString();
        if ( colNum == 25) return getTimeAcked() == null?"":getTimeAcked().toString();
        if ( colNum == 26) return String.valueOf(getAckedByUserId());
        if ( colNum == 27) return getTimeReady() == null?"":getTimeReady().toString();
        if ( colNum == 28) return getTimeNotified() == null?"":getTimeNotified().toString();
        if ( colNum == 29) return getTimeCompleted() == null?"":getTimeCompleted().toString();
        if ( colNum == 30) return getNote() == null?"":getNote().toString();
        if ( colNum == 31) return getPickupNote() == null?"":getPickupNote().toString();
        if ( colNum == 32) return getDeliveryNote() == null?"":getDeliveryNote().toString();
		return "";
    }


    public static JSONObject convertToJSON(CleanerPickupDelivery _CleanerPickupDelivery, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_CleanerPickupDelivery, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(CleanerPickupDelivery _CleanerPickupDelivery, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _CleanerPickupDelivery == null) return json;

        json.put("id", ""+_CleanerPickupDelivery.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonLocationId = new JSONObject();
		            jsonLocationId.put("name", "locationId");
		            if ( useJsonDataType ) 
	                    jsonLocationId.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getLocationId()));
		            else
		                jsonLocationId.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getLocationId()));
		            array.put(jsonLocationId);
		            JSONObject jsonCustomerId = new JSONObject();
		            jsonCustomerId.put("name", "customerId");
		            if ( useJsonDataType ) 
	                    jsonCustomerId.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getCustomerId()));
		            else
		                jsonCustomerId.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getCustomerId()));
		            array.put(jsonCustomerId);
		            JSONObject jsonTicketId = new JSONObject();
		            jsonTicketId.put("name", "ticketId");
		            if ( useJsonDataType ) 
	                    jsonTicketId.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getTicketId()));
		            else
		                jsonTicketId.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getTicketId()));
		            array.put(jsonTicketId);
		            JSONObject jsonTicketUid = new JSONObject();
		            jsonTicketUid.put("name", "ticketUid");
		            if ( useJsonDataType ) 
	                    jsonTicketUid.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getTicketUid()));
		            else
		                jsonTicketUid.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getTicketUid()));
		            array.put(jsonTicketUid);
		            JSONObject jsonPickupTicket = new JSONObject();
		            jsonPickupTicket.put("name", "pickupTicket");
		            if ( useJsonDataType ) 
	                    jsonPickupTicket.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getPickupTicket()));
		            else
		                jsonPickupTicket.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getPickupTicket()));
		            array.put(jsonPickupTicket);
		            JSONObject jsonCheckinTicketForDelivery = new JSONObject();
		            jsonCheckinTicketForDelivery.put("name", "checkinTicketForDelivery");
		            if ( useJsonDataType ) 
	                    jsonCheckinTicketForDelivery.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getCheckinTicketForDelivery()));
		            else
		                jsonCheckinTicketForDelivery.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getCheckinTicketForDelivery()));
		            array.put(jsonCheckinTicketForDelivery);
		            JSONObject jsonIsDeliveryRequest = new JSONObject();
		            jsonIsDeliveryRequest.put("name", "isDeliveryRequest");
		            if ( useJsonDataType ) 
	                    jsonIsDeliveryRequest.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getIsDeliveryRequest()));
		            else
		                jsonIsDeliveryRequest.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getIsDeliveryRequest()));
		            array.put(jsonIsDeliveryRequest);
		            JSONObject jsonIsWebRequest = new JSONObject();
		            jsonIsWebRequest.put("name", "isWebRequest");
		            if ( useJsonDataType ) 
	                    jsonIsWebRequest.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getIsWebRequest()));
		            else
		                jsonIsWebRequest.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getIsWebRequest()));
		            array.put(jsonIsWebRequest);
		            JSONObject jsonIsRecurringRequest = new JSONObject();
		            jsonIsRecurringRequest.put("name", "isRecurringRequest");
		            if ( useJsonDataType ) 
	                    jsonIsRecurringRequest.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getIsRecurringRequest()));
		            else
		                jsonIsRecurringRequest.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getIsRecurringRequest()));
		            array.put(jsonIsRecurringRequest);
		            JSONObject jsonIsReceiveReady = new JSONObject();
		            jsonIsReceiveReady.put("name", "isReceiveReady");
		            if ( useJsonDataType ) 
	                    jsonIsReceiveReady.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getIsReceiveReady()));
		            else
		                jsonIsReceiveReady.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getIsReceiveReady()));
		            array.put(jsonIsReceiveReady);
		            JSONObject jsonIsReceiveComplete = new JSONObject();
		            jsonIsReceiveComplete.put("name", "isReceiveComplete");
		            if ( useJsonDataType ) 
	                    jsonIsReceiveComplete.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getIsReceiveComplete()));
		            else
		                jsonIsReceiveComplete.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getIsReceiveComplete()));
		            array.put(jsonIsReceiveComplete);
		            JSONObject jsonRecurId = new JSONObject();
		            jsonRecurId.put("name", "recurId");
		            if ( useJsonDataType ) 
	                    jsonRecurId.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getRecurId()));
		            else
		                jsonRecurId.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getRecurId()));
		            array.put(jsonRecurId);
		            JSONObject jsonCancelled = new JSONObject();
		            jsonCancelled.put("name", "cancelled");
		            if ( useJsonDataType ) 
	                    jsonCancelled.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getCancelled()));
		            else
		                jsonCancelled.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getCancelled()));
		            array.put(jsonCancelled);
		            JSONObject jsonCompleted = new JSONObject();
		            jsonCompleted.put("name", "completed");
		            if ( useJsonDataType ) 
	                    jsonCompleted.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getCompleted()));
		            else
		                jsonCompleted.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getCompleted()));
		            array.put(jsonCompleted);
		            JSONObject jsonCustomerName = new JSONObject();
		            jsonCustomerName.put("name", "customerName");
		            if ( useJsonDataType ) 
	                    jsonCustomerName.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getCustomerName()));
		            else
		                jsonCustomerName.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getCustomerName()));
		            array.put(jsonCustomerName);
		            JSONObject jsonAddress = new JSONObject();
		            jsonAddress.put("name", "address");
		            if ( useJsonDataType ) 
	                    jsonAddress.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getAddress()));
		            else
		                jsonAddress.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getAddress()));
		            array.put(jsonAddress);
		            JSONObject jsonAptNumber = new JSONObject();
		            jsonAptNumber.put("name", "aptNumber");
		            if ( useJsonDataType ) 
	                    jsonAptNumber.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getAptNumber()));
		            else
		                jsonAptNumber.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getAptNumber()));
		            array.put(jsonAptNumber);
		            JSONObject jsonPhone = new JSONObject();
		            jsonPhone.put("name", "phone");
		            if ( useJsonDataType ) 
	                    jsonPhone.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getPhone()));
		            else
		                jsonPhone.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getPhone()));
		            array.put(jsonPhone);
		            JSONObject jsonEmail = new JSONObject();
		            jsonEmail.put("name", "email");
		            if ( useJsonDataType ) 
	                    jsonEmail.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getEmail()));
		            else
		                jsonEmail.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getEmail()));
		            array.put(jsonEmail);
		            JSONObject jsonAckReceiveMethod = new JSONObject();
		            jsonAckReceiveMethod.put("name", "ackReceiveMethod");
		            if ( useJsonDataType ) 
	                    jsonAckReceiveMethod.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getAckReceiveMethod()));
		            else
		                jsonAckReceiveMethod.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getAckReceiveMethod()));
		            array.put(jsonAckReceiveMethod);
		            JSONObject jsonCustomerInstruction = new JSONObject();
		            jsonCustomerInstruction.put("name", "customerInstruction");
		            if ( useJsonDataType ) 
	                    jsonCustomerInstruction.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getCustomerInstruction()));
		            else
		                jsonCustomerInstruction.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getCustomerInstruction()));
		            array.put(jsonCustomerInstruction);
		            JSONObject jsonPickupDeliveryByDay = new JSONObject();
		            jsonPickupDeliveryByDay.put("name", "pickupDeliveryByDay");
		            if ( useJsonDataType ) 
	                    jsonPickupDeliveryByDay.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getPickupDeliveryByDay()));
		            else
		                jsonPickupDeliveryByDay.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getPickupDeliveryByDay()));
		            array.put(jsonPickupDeliveryByDay);
		            JSONObject jsonPickupDeliveryByTime = new JSONObject();
		            jsonPickupDeliveryByTime.put("name", "pickupDeliveryByTime");
		            if ( useJsonDataType ) 
	                    jsonPickupDeliveryByTime.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getPickupDeliveryByTime()));
		            else
		                jsonPickupDeliveryByTime.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getPickupDeliveryByTime()));
		            array.put(jsonPickupDeliveryByTime);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            if ( useJsonDataType ) 
	                    jsonTimeUpdated.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getTimeUpdated()));
		            else
		                jsonTimeUpdated.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getTimeUpdated()));
		            array.put(jsonTimeUpdated);
		            JSONObject jsonTimeAcked = new JSONObject();
		            jsonTimeAcked.put("name", "timeAcked");
		            if ( useJsonDataType ) 
	                    jsonTimeAcked.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getTimeAcked()));
		            else
		                jsonTimeAcked.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getTimeAcked()));
		            array.put(jsonTimeAcked);
		            JSONObject jsonAckedByUserId = new JSONObject();
		            jsonAckedByUserId.put("name", "ackedByUserId");
		            if ( useJsonDataType ) 
	                    jsonAckedByUserId.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getAckedByUserId()));
		            else
		                jsonAckedByUserId.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getAckedByUserId()));
		            array.put(jsonAckedByUserId);
		            JSONObject jsonTimeReady = new JSONObject();
		            jsonTimeReady.put("name", "timeReady");
		            if ( useJsonDataType ) 
	                    jsonTimeReady.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getTimeReady()));
		            else
		                jsonTimeReady.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getTimeReady()));
		            array.put(jsonTimeReady);
		            JSONObject jsonTimeNotified = new JSONObject();
		            jsonTimeNotified.put("name", "timeNotified");
		            if ( useJsonDataType ) 
	                    jsonTimeNotified.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getTimeNotified()));
		            else
		                jsonTimeNotified.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getTimeNotified()));
		            array.put(jsonTimeNotified);
		            JSONObject jsonTimeCompleted = new JSONObject();
		            jsonTimeCompleted.put("name", "timeCompleted");
		            if ( useJsonDataType ) 
	                    jsonTimeCompleted.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getTimeCompleted()));
		            else
		                jsonTimeCompleted.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getTimeCompleted()));
		            array.put(jsonTimeCompleted);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            if ( useJsonDataType ) 
	                    jsonNote.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getNote()));
		            else
		                jsonNote.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getNote()));
		            array.put(jsonNote);
		            JSONObject jsonPickupNote = new JSONObject();
		            jsonPickupNote.put("name", "pickupNote");
		            if ( useJsonDataType ) 
	                    jsonPickupNote.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getPickupNote()));
		            else
		                jsonPickupNote.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getPickupNote()));
		            array.put(jsonPickupNote);
		            JSONObject jsonDeliveryNote = new JSONObject();
		            jsonDeliveryNote.put("name", "deliveryNote");
		            if ( useJsonDataType ) 
	                    jsonDeliveryNote.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerPickupDelivery.getDeliveryNote()));
		            else
		                jsonDeliveryNote.put("value", JSONValueFormatter.toStringValue(_CleanerPickupDelivery.getDeliveryNote()));
		            array.put(jsonDeliveryNote);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("locationId")) 
	            json.put("locationId", ""+_CleanerPickupDelivery.getLocationId());
            if ( ignoreFieldSet || fieldSet.contains("customerId")) 
	            json.put("customerId", ""+_CleanerPickupDelivery.getCustomerId());
            if ( ignoreFieldSet || fieldSet.contains("ticketId")) 
	            json.put("ticketId", ""+_CleanerPickupDelivery.getTicketId());
            if ( ignoreFieldSet || fieldSet.contains("ticketUid")) 
	            json.put("ticketUid", ""+_CleanerPickupDelivery.getTicketUid());
            if ( ignoreFieldSet || fieldSet.contains("pickupTicket")) 
	            json.put("pickupTicket", ""+_CleanerPickupDelivery.getPickupTicket());
            if ( ignoreFieldSet || fieldSet.contains("checkinTicketForDelivery")) 
	            json.put("checkinTicketForDelivery", ""+_CleanerPickupDelivery.getCheckinTicketForDelivery());
            if ( ignoreFieldSet || fieldSet.contains("isDeliveryRequest")) 
	            json.put("isDeliveryRequest", ""+_CleanerPickupDelivery.getIsDeliveryRequest());
            if ( ignoreFieldSet || fieldSet.contains("isWebRequest")) 
	            json.put("isWebRequest", ""+_CleanerPickupDelivery.getIsWebRequest());
            if ( ignoreFieldSet || fieldSet.contains("isRecurringRequest")) 
	            json.put("isRecurringRequest", ""+_CleanerPickupDelivery.getIsRecurringRequest());
            if ( ignoreFieldSet || fieldSet.contains("isReceiveReady")) 
	            json.put("isReceiveReady", ""+_CleanerPickupDelivery.getIsReceiveReady());
            if ( ignoreFieldSet || fieldSet.contains("isReceiveComplete")) 
	            json.put("isReceiveComplete", ""+_CleanerPickupDelivery.getIsReceiveComplete());
            if ( ignoreFieldSet || fieldSet.contains("recurId")) 
	            json.put("recurId", ""+_CleanerPickupDelivery.getRecurId());
            if ( ignoreFieldSet || fieldSet.contains("cancelled")) 
	            json.put("cancelled", ""+_CleanerPickupDelivery.getCancelled());
            if ( ignoreFieldSet || fieldSet.contains("completed")) 
	            json.put("completed", ""+_CleanerPickupDelivery.getCompleted());
            if ( ignoreFieldSet || fieldSet.contains("customerName")) 
	            json.put("customerName", ""+_CleanerPickupDelivery.getCustomerName());
            if ( ignoreFieldSet || fieldSet.contains("address")) 
	            json.put("address", ""+_CleanerPickupDelivery.getAddress());
            if ( ignoreFieldSet || fieldSet.contains("aptNumber")) 
	            json.put("aptNumber", ""+_CleanerPickupDelivery.getAptNumber());
            if ( ignoreFieldSet || fieldSet.contains("phone")) 
	            json.put("phone", ""+_CleanerPickupDelivery.getPhone());
            if ( ignoreFieldSet || fieldSet.contains("email")) 
	            json.put("email", ""+_CleanerPickupDelivery.getEmail());
            if ( ignoreFieldSet || fieldSet.contains("ackReceiveMethod")) 
	            json.put("ackReceiveMethod", ""+_CleanerPickupDelivery.getAckReceiveMethod());
            if ( ignoreFieldSet || fieldSet.contains("customerInstruction")) 
	            json.put("customerInstruction", ""+_CleanerPickupDelivery.getCustomerInstruction());
            if ( ignoreFieldSet || fieldSet.contains("pickupDeliveryByDay")) 
	            json.put("pickupDeliveryByDay", ""+_CleanerPickupDelivery.getPickupDeliveryByDay());
            if ( ignoreFieldSet || fieldSet.contains("pickupDeliveryByTime")) 
	            json.put("pickupDeliveryByTime", ""+_CleanerPickupDelivery.getPickupDeliveryByTime());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_CleanerPickupDelivery.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
	            json.put("timeUpdated", ""+_CleanerPickupDelivery.getTimeUpdated());
            if ( ignoreFieldSet || fieldSet.contains("timeAcked")) 
	            json.put("timeAcked", ""+_CleanerPickupDelivery.getTimeAcked());
            if ( ignoreFieldSet || fieldSet.contains("ackedByUserId")) 
	            json.put("ackedByUserId", ""+_CleanerPickupDelivery.getAckedByUserId());
            if ( ignoreFieldSet || fieldSet.contains("timeReady")) 
	            json.put("timeReady", ""+_CleanerPickupDelivery.getTimeReady());
            if ( ignoreFieldSet || fieldSet.contains("timeNotified")) 
	            json.put("timeNotified", ""+_CleanerPickupDelivery.getTimeNotified());
            if ( ignoreFieldSet || fieldSet.contains("timeCompleted")) 
	            json.put("timeCompleted", ""+_CleanerPickupDelivery.getTimeCompleted());
            if ( ignoreFieldSet || fieldSet.contains("note")) 
	            json.put("note", ""+_CleanerPickupDelivery.getNote());
            if ( ignoreFieldSet || fieldSet.contains("pickupNote")) 
	            json.put("pickupNote", ""+_CleanerPickupDelivery.getPickupNote());
            if ( ignoreFieldSet || fieldSet.contains("deliveryNote")) 
	            json.put("deliveryNote", ""+_CleanerPickupDelivery.getDeliveryNote());

		}
		return json;

	}

}