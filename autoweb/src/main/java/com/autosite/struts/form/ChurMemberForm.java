package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChurMemberForm extends ActionForm {

	private String _FullName;             
	private String _FirstName;             
	private String _LastName;             
	private String _Title;             
	private String _OtherName;             
	private String _Household;             
	private String _HouseholdId;             
	private String _IsGroup;             
	private String _IsGuest;             
	private String _IsSpeaker;             
	private String _TimeCreated;             
	private String _ListIndex;             

    public String getFullName() {
        return _FullName;
    }

    public void setFullName(String FullName) {
        this._FullName = FullName;
    }    

    public String getFirstName() {
        return _FirstName;
    }

    public void setFirstName(String FirstName) {
        this._FirstName = FirstName;
    }    

    public String getLastName() {
        return _LastName;
    }

    public void setLastName(String LastName) {
        this._LastName = LastName;
    }    

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String Title) {
        this._Title = Title;
    }    

    public String getOtherName() {
        return _OtherName;
    }

    public void setOtherName(String OtherName) {
        this._OtherName = OtherName;
    }    

    public String getHousehold() {
        return _Household;
    }

    public void setHousehold(String Household) {
        this._Household = Household;
    }    

    public String getHouseholdId() {
        return _HouseholdId;
    }

    public void setHouseholdId(String HouseholdId) {
        this._HouseholdId = HouseholdId;
    }    

    public String getIsGroup() {
        return _IsGroup;
    }

    public void setIsGroup(String IsGroup) {
        this._IsGroup = IsGroup;
    }    

    public String getIsGuest() {
        return _IsGuest;
    }

    public void setIsGuest(String IsGuest) {
        this._IsGuest = IsGuest;
    }    

    public String getIsSpeaker() {
        return _IsSpeaker;
    }

    public void setIsSpeaker(String IsSpeaker) {
        this._IsSpeaker = IsSpeaker;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getListIndex() {
        return _ListIndex;
    }

    public void setListIndex(String ListIndex) {
        this._ListIndex = ListIndex;
    }    

}