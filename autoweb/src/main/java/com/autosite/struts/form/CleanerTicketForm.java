package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerTicketForm extends ActionForm {

	private String _Serial;             
	private String _ParentTicketId;             
	private String _CustomerId;             
	private String _EnterUserId;             
	private String _LocationId;             
	private String _Note;             
	private String _Completed;             
	private String _Onhold;             
	private String _OriginalTicketId;             
	private String _Returned;             
	private String _ReturnedReasonText;             
	private String _ReturnedNote;             
	private String _TotalCharge;             
	private String _FinalCharge;             
	private String _DiscountId;             
	private String _DiscountAmount;             
	private String _DiscountNote;             
	private String _TimeCreated;             
	private String _TimeUpdated;             
	private String _TimeCompleted;             
	private String _TimeOnhold;             

    public String getSerial() {
        return _Serial;
    }

    public void setSerial(String Serial) {
        this._Serial = Serial;
    }    

    public String getParentTicketId() {
        return _ParentTicketId;
    }

    public void setParentTicketId(String ParentTicketId) {
        this._ParentTicketId = ParentTicketId;
    }    

    public String getCustomerId() {
        return _CustomerId;
    }

    public void setCustomerId(String CustomerId) {
        this._CustomerId = CustomerId;
    }    

    public String getEnterUserId() {
        return _EnterUserId;
    }

    public void setEnterUserId(String EnterUserId) {
        this._EnterUserId = EnterUserId;
    }    

    public String getLocationId() {
        return _LocationId;
    }

    public void setLocationId(String LocationId) {
        this._LocationId = LocationId;
    }    

    public String getNote() {
        return _Note;
    }

    public void setNote(String Note) {
        this._Note = Note;
    }    

    public String getCompleted() {
        return _Completed;
    }

    public void setCompleted(String Completed) {
        this._Completed = Completed;
    }    

    public String getOnhold() {
        return _Onhold;
    }

    public void setOnhold(String Onhold) {
        this._Onhold = Onhold;
    }    

    public String getOriginalTicketId() {
        return _OriginalTicketId;
    }

    public void setOriginalTicketId(String OriginalTicketId) {
        this._OriginalTicketId = OriginalTicketId;
    }    

    public String getReturned() {
        return _Returned;
    }

    public void setReturned(String Returned) {
        this._Returned = Returned;
    }    

    public String getReturnedReasonText() {
        return _ReturnedReasonText;
    }

    public void setReturnedReasonText(String ReturnedReasonText) {
        this._ReturnedReasonText = ReturnedReasonText;
    }    

    public String getReturnedNote() {
        return _ReturnedNote;
    }

    public void setReturnedNote(String ReturnedNote) {
        this._ReturnedNote = ReturnedNote;
    }    

    public String getTotalCharge() {
        return _TotalCharge;
    }

    public void setTotalCharge(String TotalCharge) {
        this._TotalCharge = TotalCharge;
    }    

    public String getFinalCharge() {
        return _FinalCharge;
    }

    public void setFinalCharge(String FinalCharge) {
        this._FinalCharge = FinalCharge;
    }    

    public String getDiscountId() {
        return _DiscountId;
    }

    public void setDiscountId(String DiscountId) {
        this._DiscountId = DiscountId;
    }    

    public String getDiscountAmount() {
        return _DiscountAmount;
    }

    public void setDiscountAmount(String DiscountAmount) {
        this._DiscountAmount = DiscountAmount;
    }    

    public String getDiscountNote() {
        return _DiscountNote;
    }

    public void setDiscountNote(String DiscountNote) {
        this._DiscountNote = DiscountNote;
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

    public String getTimeCompleted() {
        return _TimeCompleted;
    }

    public void setTimeCompleted(String TimeCompleted) {
        this._TimeCompleted = TimeCompleted;
    }    

    public String getTimeOnhold() {
        return _TimeOnhold;
    }

    public void setTimeOnhold(String TimeOnhold) {
        this._TimeOnhold = TimeOnhold;
    }    

}