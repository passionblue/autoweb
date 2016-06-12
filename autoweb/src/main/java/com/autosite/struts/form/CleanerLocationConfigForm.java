package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerLocationConfigForm extends ActionForm {

	private String _LocationId;             
	private String _OpenHourWeekday;             
	private String _CloseHourWeekday;             
	private String _OpenHourSat;             
	private String _CloseHourSat;             
	private String _OpenHourSun;             
	private String _CloseHourSun;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getLocationId() {
        return _LocationId;
    }

    public void setLocationId(String LocationId) {
        this._LocationId = LocationId;
    }    

    public String getOpenHourWeekday() {
        return _OpenHourWeekday;
    }

    public void setOpenHourWeekday(String OpenHourWeekday) {
        this._OpenHourWeekday = OpenHourWeekday;
    }    

    public String getCloseHourWeekday() {
        return _CloseHourWeekday;
    }

    public void setCloseHourWeekday(String CloseHourWeekday) {
        this._CloseHourWeekday = CloseHourWeekday;
    }    

    public String getOpenHourSat() {
        return _OpenHourSat;
    }

    public void setOpenHourSat(String OpenHourSat) {
        this._OpenHourSat = OpenHourSat;
    }    

    public String getCloseHourSat() {
        return _CloseHourSat;
    }

    public void setCloseHourSat(String CloseHourSat) {
        this._CloseHourSat = CloseHourSat;
    }    

    public String getOpenHourSun() {
        return _OpenHourSun;
    }

    public void setOpenHourSun(String OpenHourSun) {
        this._OpenHourSun = OpenHourSun;
    }    

    public String getCloseHourSun() {
        return _CloseHourSun;
    }

    public void setCloseHourSun(String CloseHourSun) {
        this._CloseHourSun = CloseHourSun;
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

}