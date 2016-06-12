package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChurIncomeForm extends ActionForm {

	private String _Year;             
	private String _Week;             
	private String _ChurMemberId;             
	private String _IncomeItemId;             
	private String _Ammount;             
	private String _TimeCreated;             

    public String getYear() {
        return _Year;
    }

    public void setYear(String Year) {
        this._Year = Year;
    }    

    public String getWeek() {
        return _Week;
    }

    public void setWeek(String Week) {
        this._Week = Week;
    }    

    public String getChurMemberId() {
        return _ChurMemberId;
    }

    public void setChurMemberId(String ChurMemberId) {
        this._ChurMemberId = ChurMemberId;
    }    

    public String getIncomeItemId() {
        return _IncomeItemId;
    }

    public void setIncomeItemId(String IncomeItemId) {
        this._IncomeItemId = IncomeItemId;
    }    

    public String getAmmount() {
        return _Ammount;
    }

    public void setAmmount(String Ammount) {
        this._Ammount = Ammount;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}