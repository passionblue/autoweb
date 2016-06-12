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

import com.autosite.db.CleanerCustomer;


public class CleanerCustomerDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("autositeUserId", new Integer(0));
        fieldsMap.put("email", new Integer(1));
        fieldsMap.put("phone", new Integer(2));
        fieldsMap.put("phone2", new Integer(3));
        fieldsMap.put("phonePreferred", new Integer(4));
        fieldsMap.put("title", new Integer(5));
        fieldsMap.put("lastName", new Integer(6));
        fieldsMap.put("firstName", new Integer(7));
        fieldsMap.put("address", new Integer(8));
        fieldsMap.put("apt", new Integer(9));
        fieldsMap.put("city", new Integer(10));
        fieldsMap.put("state", new Integer(11));
        fieldsMap.put("zip", new Integer(12));
        fieldsMap.put("customerType", new Integer(13));
        fieldsMap.put("payType", new Integer(14));
        fieldsMap.put("timeCreated", new Integer(15));
        fieldsMap.put("remoteIp", new Integer(16));
        fieldsMap.put("note", new Integer(17));
        fieldsMap.put("pickupNote", new Integer(18));
        fieldsMap.put("deliveryNote", new Integer(19));
        fieldsMap.put("disabled", new Integer(20));
        fieldsMap.put("timeUpdated", new Integer(21));
        fieldsMap.put("pickupDeliveryDisallowed", new Integer(22));
        fieldsMap.put("handleInst", new Integer(23));
    }


	CleanerCustomer m_cleanerCustomer;

	public CleanerCustomerDataHolder(CleanerCustomer _CleanerCustomer){
		m_cleanerCustomer =  _CleanerCustomer; 	
	}

	public CleanerCustomerDataHolder(){
		m_cleanerCustomer =  new CleanerCustomer(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerCustomer;
	}


	public long getId(){
		return 	m_cleanerCustomer.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerCustomer.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerCustomer.setSiteId(sid);
	}


	public long getAutositeUserId(){
		return m_cleanerCustomer.getAutositeUserId();
	}
	public String getEmail(){
		return m_cleanerCustomer.getEmail();
	}
	public String getPhone(){
		return m_cleanerCustomer.getPhone();
	}
	public String getPhone2(){
		return m_cleanerCustomer.getPhone2();
	}
	public int getPhonePreferred(){
		return m_cleanerCustomer.getPhonePreferred();
	}
	public String getTitle(){
		return m_cleanerCustomer.getTitle();
	}
	public String getLastName(){
		return m_cleanerCustomer.getLastName();
	}
	public String getFirstName(){
		return m_cleanerCustomer.getFirstName();
	}
	public String getAddress(){
		return m_cleanerCustomer.getAddress();
	}
	public String getApt(){
		return m_cleanerCustomer.getApt();
	}
	public String getCity(){
		return m_cleanerCustomer.getCity();
	}
	public String getState(){
		return m_cleanerCustomer.getState();
	}
	public String getZip(){
		return m_cleanerCustomer.getZip();
	}
	public String getCustomerType(){
		return m_cleanerCustomer.getCustomerType();
	}
	public String getPayType(){
		return m_cleanerCustomer.getPayType();
	}
	public Timestamp getTimeCreated(){
		return m_cleanerCustomer.getTimeCreated();
	}
	public String getRemoteIp(){
		return m_cleanerCustomer.getRemoteIp();
	}
	public String getNote(){
		return m_cleanerCustomer.getNote();
	}
	public String getPickupNote(){
		return m_cleanerCustomer.getPickupNote();
	}
	public String getDeliveryNote(){
		return m_cleanerCustomer.getDeliveryNote();
	}
	public int getDisabled(){
		return m_cleanerCustomer.getDisabled();
	}
	public Timestamp getTimeUpdated(){
		return m_cleanerCustomer.getTimeUpdated();
	}
	public int getPickupDeliveryDisallowed(){
		return m_cleanerCustomer.getPickupDeliveryDisallowed();
	}
	public String getHandleInst(){
		return m_cleanerCustomer.getHandleInst();
	}

	public void setAutositeUserId(long _autositeUserId ){
		m_cleanerCustomer.setAutositeUserId(_autositeUserId);
	}
	public void setEmail(String _email ){
		m_cleanerCustomer.setEmail(_email);
	}
	public void setPhone(String _phone ){
		m_cleanerCustomer.setPhone(_phone);
	}
	public void setPhone2(String _phone2 ){
		m_cleanerCustomer.setPhone2(_phone2);
	}
	public void setPhonePreferred(int _phonePreferred ){
		m_cleanerCustomer.setPhonePreferred(_phonePreferred);
	}
	public void setTitle(String _title ){
		m_cleanerCustomer.setTitle(_title);
	}
	public void setLastName(String _lastName ){
		m_cleanerCustomer.setLastName(_lastName);
	}
	public void setFirstName(String _firstName ){
		m_cleanerCustomer.setFirstName(_firstName);
	}
	public void setAddress(String _address ){
		m_cleanerCustomer.setAddress(_address);
	}
	public void setApt(String _apt ){
		m_cleanerCustomer.setApt(_apt);
	}
	public void setCity(String _city ){
		m_cleanerCustomer.setCity(_city);
	}
	public void setState(String _state ){
		m_cleanerCustomer.setState(_state);
	}
	public void setZip(String _zip ){
		m_cleanerCustomer.setZip(_zip);
	}
	public void setCustomerType(String _customerType ){
		m_cleanerCustomer.setCustomerType(_customerType);
	}
	public void setPayType(String _payType ){
		m_cleanerCustomer.setPayType(_payType);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_cleanerCustomer.setTimeCreated(_timeCreated);
	}
	public void setRemoteIp(String _remoteIp ){
		m_cleanerCustomer.setRemoteIp(_remoteIp);
	}
	public void setNote(String _note ){
		m_cleanerCustomer.setNote(_note);
	}
	public void setPickupNote(String _pickupNote ){
		m_cleanerCustomer.setPickupNote(_pickupNote);
	}
	public void setDeliveryNote(String _deliveryNote ){
		m_cleanerCustomer.setDeliveryNote(_deliveryNote);
	}
	public void setDisabled(int _disabled ){
		m_cleanerCustomer.setDisabled(_disabled);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_cleanerCustomer.setTimeUpdated(_timeUpdated);
	}
	public void setPickupDeliveryDisallowed(int _pickupDeliveryDisallowed ){
		m_cleanerCustomer.setPickupDeliveryDisallowed(_pickupDeliveryDisallowed);
	}
	public void setHandleInst(String _handleInst ){
		m_cleanerCustomer.setHandleInst(_handleInst);
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

        if ( colNum == 0) return String.valueOf(getAutositeUserId());
        if ( colNum == 1) return getEmail() == null?"":getEmail().toString();
        if ( colNum == 2) return getPhone() == null?"":getPhone().toString();
        if ( colNum == 3) return getPhone2() == null?"":getPhone2().toString();
        if ( colNum == 4) return String.valueOf(getPhonePreferred());
        if ( colNum == 5) return getTitle() == null?"":getTitle().toString();
        if ( colNum == 6) return getLastName() == null?"":getLastName().toString();
        if ( colNum == 7) return getFirstName() == null?"":getFirstName().toString();
        if ( colNum == 8) return getAddress() == null?"":getAddress().toString();
        if ( colNum == 9) return getApt() == null?"":getApt().toString();
        if ( colNum == 10) return getCity() == null?"":getCity().toString();
        if ( colNum == 11) return getState() == null?"":getState().toString();
        if ( colNum == 12) return getZip() == null?"":getZip().toString();
        if ( colNum == 13) return getCustomerType() == null?"":getCustomerType().toString();
        if ( colNum == 14) return getPayType() == null?"":getPayType().toString();
        if ( colNum == 15) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 16) return getRemoteIp() == null?"":getRemoteIp().toString();
        if ( colNum == 17) return getNote() == null?"":getNote().toString();
        if ( colNum == 18) return getPickupNote() == null?"":getPickupNote().toString();
        if ( colNum == 19) return getDeliveryNote() == null?"":getDeliveryNote().toString();
        if ( colNum == 20) return String.valueOf(getDisabled());
        if ( colNum == 21) return getTimeUpdated() == null?"":getTimeUpdated().toString();
        if ( colNum == 22) return String.valueOf(getPickupDeliveryDisallowed());
        if ( colNum == 23) return getHandleInst() == null?"":getHandleInst().toString();
		return "";
    }


    public static JSONObject convertToJSON(CleanerCustomer _CleanerCustomer, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_CleanerCustomer, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(CleanerCustomer _CleanerCustomer, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _CleanerCustomer == null) return json;

        json.put("id", ""+_CleanerCustomer.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonAutositeUserId = new JSONObject();
		            jsonAutositeUserId.put("name", "autositeUserId");
		            if ( useJsonDataType ) 
	                    jsonAutositeUserId.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getAutositeUserId()));
		            else
		                jsonAutositeUserId.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getAutositeUserId()));
		            array.put(jsonAutositeUserId);
		            JSONObject jsonEmail = new JSONObject();
		            jsonEmail.put("name", "email");
		            if ( useJsonDataType ) 
	                    jsonEmail.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getEmail()));
		            else
		                jsonEmail.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getEmail()));
		            array.put(jsonEmail);
		            JSONObject jsonPhone = new JSONObject();
		            jsonPhone.put("name", "phone");
		            if ( useJsonDataType ) 
	                    jsonPhone.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getPhone()));
		            else
		                jsonPhone.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getPhone()));
		            array.put(jsonPhone);
		            JSONObject jsonPhone2 = new JSONObject();
		            jsonPhone2.put("name", "phone2");
		            if ( useJsonDataType ) 
	                    jsonPhone2.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getPhone2()));
		            else
		                jsonPhone2.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getPhone2()));
		            array.put(jsonPhone2);
		            JSONObject jsonPhonePreferred = new JSONObject();
		            jsonPhonePreferred.put("name", "phonePreferred");
		            if ( useJsonDataType ) 
	                    jsonPhonePreferred.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getPhonePreferred()));
		            else
		                jsonPhonePreferred.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getPhonePreferred()));
		            array.put(jsonPhonePreferred);
		            JSONObject jsonTitle = new JSONObject();
		            jsonTitle.put("name", "title");
		            if ( useJsonDataType ) 
	                    jsonTitle.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getTitle()));
		            else
		                jsonTitle.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getTitle()));
		            array.put(jsonTitle);
		            JSONObject jsonLastName = new JSONObject();
		            jsonLastName.put("name", "lastName");
		            if ( useJsonDataType ) 
	                    jsonLastName.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getLastName()));
		            else
		                jsonLastName.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getLastName()));
		            array.put(jsonLastName);
		            JSONObject jsonFirstName = new JSONObject();
		            jsonFirstName.put("name", "firstName");
		            if ( useJsonDataType ) 
	                    jsonFirstName.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getFirstName()));
		            else
		                jsonFirstName.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getFirstName()));
		            array.put(jsonFirstName);
		            JSONObject jsonAddress = new JSONObject();
		            jsonAddress.put("name", "address");
		            if ( useJsonDataType ) 
	                    jsonAddress.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getAddress()));
		            else
		                jsonAddress.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getAddress()));
		            array.put(jsonAddress);
		            JSONObject jsonApt = new JSONObject();
		            jsonApt.put("name", "apt");
		            if ( useJsonDataType ) 
	                    jsonApt.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getApt()));
		            else
		                jsonApt.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getApt()));
		            array.put(jsonApt);
		            JSONObject jsonCity = new JSONObject();
		            jsonCity.put("name", "city");
		            if ( useJsonDataType ) 
	                    jsonCity.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getCity()));
		            else
		                jsonCity.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getCity()));
		            array.put(jsonCity);
		            JSONObject jsonState = new JSONObject();
		            jsonState.put("name", "state");
		            if ( useJsonDataType ) 
	                    jsonState.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getState()));
		            else
		                jsonState.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getState()));
		            array.put(jsonState);
		            JSONObject jsonZip = new JSONObject();
		            jsonZip.put("name", "zip");
		            if ( useJsonDataType ) 
	                    jsonZip.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getZip()));
		            else
		                jsonZip.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getZip()));
		            array.put(jsonZip);
		            JSONObject jsonCustomerType = new JSONObject();
		            jsonCustomerType.put("name", "customerType");
		            if ( useJsonDataType ) 
	                    jsonCustomerType.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getCustomerType()));
		            else
		                jsonCustomerType.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getCustomerType()));
		            array.put(jsonCustomerType);
		            JSONObject jsonPayType = new JSONObject();
		            jsonPayType.put("name", "payType");
		            if ( useJsonDataType ) 
	                    jsonPayType.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getPayType()));
		            else
		                jsonPayType.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getPayType()));
		            array.put(jsonPayType);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonRemoteIp = new JSONObject();
		            jsonRemoteIp.put("name", "remoteIp");
		            if ( useJsonDataType ) 
	                    jsonRemoteIp.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getRemoteIp()));
		            else
		                jsonRemoteIp.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getRemoteIp()));
		            array.put(jsonRemoteIp);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            if ( useJsonDataType ) 
	                    jsonNote.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getNote()));
		            else
		                jsonNote.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getNote()));
		            array.put(jsonNote);
		            JSONObject jsonPickupNote = new JSONObject();
		            jsonPickupNote.put("name", "pickupNote");
		            if ( useJsonDataType ) 
	                    jsonPickupNote.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getPickupNote()));
		            else
		                jsonPickupNote.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getPickupNote()));
		            array.put(jsonPickupNote);
		            JSONObject jsonDeliveryNote = new JSONObject();
		            jsonDeliveryNote.put("name", "deliveryNote");
		            if ( useJsonDataType ) 
	                    jsonDeliveryNote.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getDeliveryNote()));
		            else
		                jsonDeliveryNote.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getDeliveryNote()));
		            array.put(jsonDeliveryNote);
		            JSONObject jsonDisabled = new JSONObject();
		            jsonDisabled.put("name", "disabled");
		            if ( useJsonDataType ) 
	                    jsonDisabled.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getDisabled()));
		            else
		                jsonDisabled.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getDisabled()));
		            array.put(jsonDisabled);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            if ( useJsonDataType ) 
	                    jsonTimeUpdated.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getTimeUpdated()));
		            else
		                jsonTimeUpdated.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getTimeUpdated()));
		            array.put(jsonTimeUpdated);
		            JSONObject jsonPickupDeliveryDisallowed = new JSONObject();
		            jsonPickupDeliveryDisallowed.put("name", "pickupDeliveryDisallowed");
		            if ( useJsonDataType ) 
	                    jsonPickupDeliveryDisallowed.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getPickupDeliveryDisallowed()));
		            else
		                jsonPickupDeliveryDisallowed.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getPickupDeliveryDisallowed()));
		            array.put(jsonPickupDeliveryDisallowed);
		            JSONObject jsonHandleInst = new JSONObject();
		            jsonHandleInst.put("name", "handleInst");
		            if ( useJsonDataType ) 
	                    jsonHandleInst.put("value", JSONValueFormatter.toJsonTypeValue(_CleanerCustomer.getHandleInst()));
		            else
		                jsonHandleInst.put("value", JSONValueFormatter.toStringValue(_CleanerCustomer.getHandleInst()));
		            array.put(jsonHandleInst);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("autositeUserId")) 
	            json.put("autositeUserId", ""+_CleanerCustomer.getAutositeUserId());
            if ( ignoreFieldSet || fieldSet.contains("email")) 
	            json.put("email", ""+_CleanerCustomer.getEmail());
            if ( ignoreFieldSet || fieldSet.contains("phone")) 
	            json.put("phone", ""+_CleanerCustomer.getPhone());
            if ( ignoreFieldSet || fieldSet.contains("phone2")) 
	            json.put("phone2", ""+_CleanerCustomer.getPhone2());
            if ( ignoreFieldSet || fieldSet.contains("phonePreferred")) 
	            json.put("phonePreferred", ""+_CleanerCustomer.getPhonePreferred());
            if ( ignoreFieldSet || fieldSet.contains("title")) 
	            json.put("title", ""+_CleanerCustomer.getTitle());
            if ( ignoreFieldSet || fieldSet.contains("lastName")) 
	            json.put("lastName", ""+_CleanerCustomer.getLastName());
            if ( ignoreFieldSet || fieldSet.contains("firstName")) 
	            json.put("firstName", ""+_CleanerCustomer.getFirstName());
            if ( ignoreFieldSet || fieldSet.contains("address")) 
	            json.put("address", ""+_CleanerCustomer.getAddress());
            if ( ignoreFieldSet || fieldSet.contains("apt")) 
	            json.put("apt", ""+_CleanerCustomer.getApt());
            if ( ignoreFieldSet || fieldSet.contains("city")) 
	            json.put("city", ""+_CleanerCustomer.getCity());
            if ( ignoreFieldSet || fieldSet.contains("state")) 
	            json.put("state", ""+_CleanerCustomer.getState());
            if ( ignoreFieldSet || fieldSet.contains("zip")) 
	            json.put("zip", ""+_CleanerCustomer.getZip());
            if ( ignoreFieldSet || fieldSet.contains("customerType")) 
	            json.put("customerType", ""+_CleanerCustomer.getCustomerType());
            if ( ignoreFieldSet || fieldSet.contains("payType")) 
	            json.put("payType", ""+_CleanerCustomer.getPayType());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_CleanerCustomer.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("remoteIp")) 
	            json.put("remoteIp", ""+_CleanerCustomer.getRemoteIp());
            if ( ignoreFieldSet || fieldSet.contains("note")) 
	            json.put("note", ""+_CleanerCustomer.getNote());
            if ( ignoreFieldSet || fieldSet.contains("pickupNote")) 
	            json.put("pickupNote", ""+_CleanerCustomer.getPickupNote());
            if ( ignoreFieldSet || fieldSet.contains("deliveryNote")) 
	            json.put("deliveryNote", ""+_CleanerCustomer.getDeliveryNote());
            if ( ignoreFieldSet || fieldSet.contains("disabled")) 
	            json.put("disabled", ""+_CleanerCustomer.getDisabled());
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
	            json.put("timeUpdated", ""+_CleanerCustomer.getTimeUpdated());
            if ( ignoreFieldSet || fieldSet.contains("pickupDeliveryDisallowed")) 
	            json.put("pickupDeliveryDisallowed", ""+_CleanerCustomer.getPickupDeliveryDisallowed());
            if ( ignoreFieldSet || fieldSet.contains("handleInst")) 
	            json.put("handleInst", ""+_CleanerCustomer.getHandleInst());

		}
		return json;

	}

}