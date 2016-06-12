package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChurIncomeAddForm extends ActionForm {

	private String _ChurMemberId;             
	private String _Tithe;             
	private String _Weekly;             
	private String _Thanks;             
	private String _Mission;             
	private String _Construction;             
	private String _Other;             
	private String _Week;             
	private String _Year;             

    public String getChurMemberId() {
        return _ChurMemberId;
    }

    public void setChurMemberId(String ChurMemberId) {
        this._ChurMemberId = ChurMemberId;
    }    

    public String getTithe() {
        return _Tithe;
    }

    public void setTithe(String Tithe) {
        this._Tithe = Tithe;
    }    

    public String getWeekly() {
        return _Weekly;
    }

    public void setWeekly(String Weekly) {
        this._Weekly = Weekly;
    }    

    public String getThanks() {
        return _Thanks;
    }

    public void setThanks(String Thanks) {
        this._Thanks = Thanks;
    }    

    public String getMission() {
        return _Mission;
    }

    public void setMission(String Mission) {
        this._Mission = Mission;
    }    

    public String getConstruction() {
        return _Construction;
    }

    public void setConstruction(String Construction) {
        this._Construction = Construction;
    }    

    public String getOther() {
        return _Other;
    }

    public void setOther(String Other) {
        this._Other = Other;
    }    

    public String getWeek() {
        return _Week;
    }

    public void setWeek(String Week) {
        this._Week = Week;
    }    

    public String getYear() {
        return _Year;
    }

    public void setYear(String Year) {
        this._Year = Year;
    }    

}