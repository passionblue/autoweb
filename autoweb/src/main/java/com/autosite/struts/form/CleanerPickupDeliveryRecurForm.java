package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerPickupDeliveryRecurForm extends ActionForm {

	private String _CustomerId;             
	private String _Weekday;             
	private String _TimeHhdd;             

    public String getCustomerId() {
        return _CustomerId;
    }

    public void setCustomerId(String CustomerId) {
        this._CustomerId = CustomerId;
    }    

    public String getWeekday() {
        return _Weekday;
    }

    public void setWeekday(String Weekday) {
        this._Weekday = Weekday;
    }    

    public String getTimeHhdd() {
        return _TimeHhdd;
    }

    public void setTimeHhdd(String TimeHhdd) {
        this._TimeHhdd = TimeHhdd;
    }    

}