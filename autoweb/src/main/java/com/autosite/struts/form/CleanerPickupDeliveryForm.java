/* 
Template last modification history:


Source Generated: Sun Mar 15 13:54:44 EDT 2015
*/

package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerPickupDeliveryForm extends ActionForm {

	private String _LocationId;             
	private String _CustomerId;             
	private String _TicketId;             
	private String _TicketUid;             
	private String _PickupTicket;             
	private String _CheckinTicketForDelivery;             
	private String _IsDeliveryRequest;             
	private String _IsWebRequest;             
	private String _IsRecurringRequest;             
	private String _IsReceiveReady;             
	private String _IsReceiveComplete;             
	private String _RecurId;             
	private String _Cancelled;             
	private String _Completed;             
	private String _CustomerName;             
	private String _Address;             
	private String _AptNumber;             
	private String _Phone;             
	private String _Email;             
	private String _AckReceiveMethod;             
	private String _CustomerInstruction;             
	private String _PickupDeliveryByDay;             
	private String _PickupDeliveryByTime;             
	private String _TimeCreated;             
	private String _TimeUpdated;             
	private String _TimeAcked;             
	private String _AckedByUserId;             
	private String _TimeReady;             
	private String _TimeNotified;             
	private String _TimeCompleted;             
	private String _Note;             
	private String _PickupNote;             
	private String _DeliveryNote;             

    public String getLocationId() {
        return _LocationId;
    }

    public void setLocationId(String LocationId) {
        this._LocationId = LocationId;
    }    

    public String getCustomerId() {
        return _CustomerId;
    }

    public void setCustomerId(String CustomerId) {
        this._CustomerId = CustomerId;
    }    

    public String getTicketId() {
        return _TicketId;
    }

    public void setTicketId(String TicketId) {
        this._TicketId = TicketId;
    }    

    public String getTicketUid() {
        return _TicketUid;
    }

    public void setTicketUid(String TicketUid) {
        this._TicketUid = TicketUid;
    }    

    public String getPickupTicket() {
        return _PickupTicket;
    }

    public void setPickupTicket(String PickupTicket) {
        this._PickupTicket = PickupTicket;
    }    

    public String getCheckinTicketForDelivery() {
        return _CheckinTicketForDelivery;
    }

    public void setCheckinTicketForDelivery(String CheckinTicketForDelivery) {
        this._CheckinTicketForDelivery = CheckinTicketForDelivery;
    }    

    public String getIsDeliveryRequest() {
        return _IsDeliveryRequest;
    }

    public void setIsDeliveryRequest(String IsDeliveryRequest) {
        this._IsDeliveryRequest = IsDeliveryRequest;
    }    

    public String getIsWebRequest() {
        return _IsWebRequest;
    }

    public void setIsWebRequest(String IsWebRequest) {
        this._IsWebRequest = IsWebRequest;
    }    

    public String getIsRecurringRequest() {
        return _IsRecurringRequest;
    }

    public void setIsRecurringRequest(String IsRecurringRequest) {
        this._IsRecurringRequest = IsRecurringRequest;
    }    

    public String getIsReceiveReady() {
        return _IsReceiveReady;
    }

    public void setIsReceiveReady(String IsReceiveReady) {
        this._IsReceiveReady = IsReceiveReady;
    }    

    public String getIsReceiveComplete() {
        return _IsReceiveComplete;
    }

    public void setIsReceiveComplete(String IsReceiveComplete) {
        this._IsReceiveComplete = IsReceiveComplete;
    }    

    public String getRecurId() {
        return _RecurId;
    }

    public void setRecurId(String RecurId) {
        this._RecurId = RecurId;
    }    

    public String getCancelled() {
        return _Cancelled;
    }

    public void setCancelled(String Cancelled) {
        this._Cancelled = Cancelled;
    }    

    public String getCompleted() {
        return _Completed;
    }

    public void setCompleted(String Completed) {
        this._Completed = Completed;
    }    

    public String getCustomerName() {
        return _CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this._CustomerName = CustomerName;
    }    

    public String getAddress() {
        return _Address;
    }

    public void setAddress(String Address) {
        this._Address = Address;
    }    

    public String getAptNumber() {
        return _AptNumber;
    }

    public void setAptNumber(String AptNumber) {
        this._AptNumber = AptNumber;
    }    

    public String getPhone() {
        return _Phone;
    }

    public void setPhone(String Phone) {
        this._Phone = Phone;
    }    

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String Email) {
        this._Email = Email;
    }    

    public String getAckReceiveMethod() {
        return _AckReceiveMethod;
    }

    public void setAckReceiveMethod(String AckReceiveMethod) {
        this._AckReceiveMethod = AckReceiveMethod;
    }    

    public String getCustomerInstruction() {
        return _CustomerInstruction;
    }

    public void setCustomerInstruction(String CustomerInstruction) {
        this._CustomerInstruction = CustomerInstruction;
    }    

    public String getPickupDeliveryByDay() {
        return _PickupDeliveryByDay;
    }

    public void setPickupDeliveryByDay(String PickupDeliveryByDay) {
        this._PickupDeliveryByDay = PickupDeliveryByDay;
    }    

    public String getPickupDeliveryByTime() {
        return _PickupDeliveryByTime;
    }

    public void setPickupDeliveryByTime(String PickupDeliveryByTime) {
        this._PickupDeliveryByTime = PickupDeliveryByTime;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getTimeUpdated() {
        return _TimeUpdated;
    }

    public void setTimeUpdated(String TimeUpdated) {
        this._TimeUpdated = TimeUpdated;
    }    

    public String getTimeAcked() {
        return _TimeAcked;
    }

    public void setTimeAcked(String TimeAcked) {
        this._TimeAcked = TimeAcked;
    }    

    public String getAckedByUserId() {
        return _AckedByUserId;
    }

    public void setAckedByUserId(String AckedByUserId) {
        this._AckedByUserId = AckedByUserId;
    }    

    public String getTimeReady() {
        return _TimeReady;
    }

    public void setTimeReady(String TimeReady) {
        this._TimeReady = TimeReady;
    }    

    public String getTimeNotified() {
        return _TimeNotified;
    }

    public void setTimeNotified(String TimeNotified) {
        this._TimeNotified = TimeNotified;
    }    

    public String getTimeCompleted() {
        return _TimeCompleted;
    }

    public void setTimeCompleted(String TimeCompleted) {
        this._TimeCompleted = TimeCompleted;
    }    

    public String getNote() {
        return _Note;
    }

    public void setNote(String Note) {
        this._Note = Note;
    }    

    public String getPickupNote() {
        return _PickupNote;
    }

    public void setPickupNote(String PickupNote) {
        this._PickupNote = PickupNote;
    }    

    public String getDeliveryNote() {
        return _DeliveryNote;
    }

    public void setDeliveryNote(String DeliveryNote) {
        this._DeliveryNote = DeliveryNote;
    }    

}