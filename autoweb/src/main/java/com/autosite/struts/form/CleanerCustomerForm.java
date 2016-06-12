package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerCustomerForm extends ActionForm {

	private String _AutositeUserId;             
	private String _Email;             
	private String _Phone;             
	private String _Phone2;             
	private String _PhonePreferred;             
	private String _Title;             
	private String _LastName;             
	private String _FirstName;             
	private String _Address;             
	private String _Apt;             
	private String _City;             
	private String _State;             
	private String _Zip;             
	private String _CustomerType;             
	private String _PayType;             
	private String _TimeCreated;             
	private String _RemoteIp;             
	private String _Note;             
	private String _PickupNote;             
	private String _DeliveryNote;             
	private String _Disabled;             
	private String _TimeUpdated;             
	private String _PickupDeliveryDisallowed;             
	private String _HandleInst;             

    public String getAutositeUserId() {
        return _AutositeUserId;
    }

    public void setAutositeUserId(String AutositeUserId) {
        this._AutositeUserId = AutositeUserId;
    }    

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String Email) {
        this._Email = Email;
    }    

    public String getPhone() {
        return _Phone;
    }

    public void setPhone(String Phone) {
        this._Phone = Phone;
    }    

    public String getPhone2() {
        return _Phone2;
    }

    public void setPhone2(String Phone2) {
        this._Phone2 = Phone2;
    }    

    public String getPhonePreferred() {
        return _PhonePreferred;
    }

    public void setPhonePreferred(String PhonePreferred) {
        this._PhonePreferred = PhonePreferred;
    }    

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String Title) {
        this._Title = Title;
    }    

    public String getLastName() {
        return _LastName;
    }

    public void setLastName(String LastName) {
        this._LastName = LastName;
    }    

    public String getFirstName() {
        return _FirstName;
    }

    public void setFirstName(String FirstName) {
        this._FirstName = FirstName;
    }    

    public String getAddress() {
        return _Address;
    }

    public void setAddress(String Address) {
        this._Address = Address;
    }    

    public String getApt() {
        return _Apt;
    }

    public void setApt(String Apt) {
        this._Apt = Apt;
    }    

    public String getCity() {
        return _City;
    }

    public void setCity(String City) {
        this._City = City;
    }    

    public String getState() {
        return _State;
    }

    public void setState(String State) {
        this._State = State;
    }    

    public String getZip() {
        return _Zip;
    }

    public void setZip(String Zip) {
        this._Zip = Zip;
    }    

    public String getCustomerType() {
        return _CustomerType;
    }

    public void setCustomerType(String CustomerType) {
        this._CustomerType = CustomerType;
    }    

    public String getPayType() {
        return _PayType;
    }

    public void setPayType(String PayType) {
        this._PayType = PayType;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getRemoteIp() {
        return _RemoteIp;
    }

    public void setRemoteIp(String RemoteIp) {
        this._RemoteIp = RemoteIp;
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

    public String getDisabled() {
        return _Disabled;
    }

    public void setDisabled(String Disabled) {
        this._Disabled = Disabled;
    }    

    public String getTimeUpdated() {
        return _TimeUpdated;
    }

    public void setTimeUpdated(String TimeUpdated) {
        this._TimeUpdated = TimeUpdated;
    }    

    public String getPickupDeliveryDisallowed() {
        return _PickupDeliveryDisallowed;
    }

    public void setPickupDeliveryDisallowed(String PickupDeliveryDisallowed) {
        this._PickupDeliveryDisallowed = PickupDeliveryDisallowed;
    }    

    public String getHandleInst() {
        return _HandleInst;
    }

    public void setHandleInst(String HandleInst) {
        this._HandleInst = HandleInst;
    }    

}