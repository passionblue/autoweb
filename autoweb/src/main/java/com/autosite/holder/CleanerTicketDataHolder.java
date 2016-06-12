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

import com.autosite.db.CleanerTicket;


public class CleanerTicketDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("serial", new Integer(0));
        fieldsMap.put("parentTicketId", new Integer(1));
        fieldsMap.put("customerId", new Integer(2));
        fieldsMap.put("enterUserId", new Integer(3));
        fieldsMap.put("locationId", new Integer(4));
        fieldsMap.put("note", new Integer(5));
        fieldsMap.put("completed", new Integer(6));
        fieldsMap.put("onhold", new Integer(7));
        fieldsMap.put("originalTicketId", new Integer(8));
        fieldsMap.put("returned", new Integer(9));
        fieldsMap.put("returnedReasonText", new Integer(10));
        fieldsMap.put("returnedNote", new Integer(11));
        fieldsMap.put("totalCharge", new Integer(12));
        fieldsMap.put("finalCharge", new Integer(13));
        fieldsMap.put("discountId", new Integer(14));
        fieldsMap.put("discountAmount", new Integer(15));
        fieldsMap.put("discountNote", new Integer(16));
        fieldsMap.put("timeCreated", new Integer(17));
        fieldsMap.put("timeUpdated", new Integer(18));
        fieldsMap.put("timeCompleted", new Integer(19));
        fieldsMap.put("timeOnhold", new Integer(20));
    }


	CleanerTicket m_cleanerTicket;

	public CleanerTicketDataHolder(CleanerTicket _CleanerTicket){
		m_cleanerTicket =  _CleanerTicket; 	
	}

	public CleanerTicketDataHolder(){
		m_cleanerTicket =  new CleanerTicket(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_cleanerTicket;
	}


	public long getId(){
		return 	m_cleanerTicket.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_cleanerTicket.getSiteId();
	}
	public void setSiteId(long sid){
		m_cleanerTicket.setSiteId(sid);
	}


	public String getSerial(){
		return m_cleanerTicket.getSerial();
	}
	public long getParentTicketId(){
		return m_cleanerTicket.getParentTicketId();
	}
	public long getCustomerId(){
		return m_cleanerTicket.getCustomerId();
	}
	public long getEnterUserId(){
		return m_cleanerTicket.getEnterUserId();
	}
	public long getLocationId(){
		return m_cleanerTicket.getLocationId();
	}
	public String getNote(){
		return m_cleanerTicket.getNote();
	}
	public int getCompleted(){
		return m_cleanerTicket.getCompleted();
	}
	public int getOnhold(){
		return m_cleanerTicket.getOnhold();
	}
	public long getOriginalTicketId(){
		return m_cleanerTicket.getOriginalTicketId();
	}
	public int getReturned(){
		return m_cleanerTicket.getReturned();
	}
	public String getReturnedReasonText(){
		return m_cleanerTicket.getReturnedReasonText();
	}
	public String getReturnedNote(){
		return m_cleanerTicket.getReturnedNote();
	}
	public double getTotalCharge(){
		return m_cleanerTicket.getTotalCharge();
	}
	public double getFinalCharge(){
		return m_cleanerTicket.getFinalCharge();
	}
	public long getDiscountId(){
		return m_cleanerTicket.getDiscountId();
	}
	public double getDiscountAmount(){
		return m_cleanerTicket.getDiscountAmount();
	}
	public String getDiscountNote(){
		return m_cleanerTicket.getDiscountNote();
	}
	public Timestamp getTimeCreated(){
		return m_cleanerTicket.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_cleanerTicket.getTimeUpdated();
	}
	public Timestamp getTimeCompleted(){
		return m_cleanerTicket.getTimeCompleted();
	}
	public Timestamp getTimeOnhold(){
		return m_cleanerTicket.getTimeOnhold();
	}

	public void setSerial(String _serial ){
		m_cleanerTicket.setSerial(_serial);
	}
	public void setParentTicketId(long _parentTicketId ){
		m_cleanerTicket.setParentTicketId(_parentTicketId);
	}
	public void setCustomerId(long _customerId ){
		m_cleanerTicket.setCustomerId(_customerId);
	}
	public void setEnterUserId(long _enterUserId ){
		m_cleanerTicket.setEnterUserId(_enterUserId);
	}
	public void setLocationId(long _locationId ){
		m_cleanerTicket.setLocationId(_locationId);
	}
	public void setNote(String _note ){
		m_cleanerTicket.setNote(_note);
	}
	public void setCompleted(int _completed ){
		m_cleanerTicket.setCompleted(_completed);
	}
	public void setOnhold(int _onhold ){
		m_cleanerTicket.setOnhold(_onhold);
	}
	public void setOriginalTicketId(long _originalTicketId ){
		m_cleanerTicket.setOriginalTicketId(_originalTicketId);
	}
	public void setReturned(int _returned ){
		m_cleanerTicket.setReturned(_returned);
	}
	public void setReturnedReasonText(String _returnedReasonText ){
		m_cleanerTicket.setReturnedReasonText(_returnedReasonText);
	}
	public void setReturnedNote(String _returnedNote ){
		m_cleanerTicket.setReturnedNote(_returnedNote);
	}
	public void setTotalCharge(double _totalCharge ){
		m_cleanerTicket.setTotalCharge(_totalCharge);
	}
	public void setFinalCharge(double _finalCharge ){
		m_cleanerTicket.setFinalCharge(_finalCharge);
	}
	public void setDiscountId(long _discountId ){
		m_cleanerTicket.setDiscountId(_discountId);
	}
	public void setDiscountAmount(double _discountAmount ){
		m_cleanerTicket.setDiscountAmount(_discountAmount);
	}
	public void setDiscountNote(String _discountNote ){
		m_cleanerTicket.setDiscountNote(_discountNote);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_cleanerTicket.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_cleanerTicket.setTimeUpdated(_timeUpdated);
	}
	public void setTimeCompleted(Timestamp _timeCompleted ){
		m_cleanerTicket.setTimeCompleted(_timeCompleted);
	}
	public void setTimeOnhold(Timestamp _timeOnhold ){
		m_cleanerTicket.setTimeOnhold(_timeOnhold);
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

        if ( colNum == 0) return getSerial() == null?"":getSerial().toString();
        if ( colNum == 1) return String.valueOf(getParentTicketId());
        if ( colNum == 2) return String.valueOf(getCustomerId());
        if ( colNum == 3) return String.valueOf(getEnterUserId());
        if ( colNum == 4) return String.valueOf(getLocationId());
        if ( colNum == 5) return getNote() == null?"":getNote().toString();
        if ( colNum == 6) return String.valueOf(getCompleted());
        if ( colNum == 7) return String.valueOf(getOnhold());
        if ( colNum == 8) return String.valueOf(getOriginalTicketId());
        if ( colNum == 9) return String.valueOf(getReturned());
        if ( colNum == 10) return getReturnedReasonText() == null?"":getReturnedReasonText().toString();
        if ( colNum == 11) return getReturnedNote() == null?"":getReturnedNote().toString();
        if ( colNum == 12) return String.valueOf(getTotalCharge());
        if ( colNum == 13) return String.valueOf(getFinalCharge());
        if ( colNum == 14) return String.valueOf(getDiscountId());
        if ( colNum == 15) return String.valueOf(getDiscountAmount());
        if ( colNum == 16) return getDiscountNote() == null?"":getDiscountNote().toString();
        if ( colNum == 17) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 18) return getTimeUpdated() == null?"":getTimeUpdated().toString();
        if ( colNum == 19) return getTimeCompleted() == null?"":getTimeCompleted().toString();
        if ( colNum == 20) return getTimeOnhold() == null?"":getTimeOnhold().toString();
		return "";
    }
}