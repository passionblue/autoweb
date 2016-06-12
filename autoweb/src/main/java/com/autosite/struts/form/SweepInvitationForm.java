package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SweepInvitationForm extends ActionForm {

	private String _UserId;             
	private String _Email;             
	private String _Message;             
	private String _InvitationSent;             
	private String _TimeCreated;             
	private String _TimeSent;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String Email) {
        this._Email = Email;
    }    

    public String getMessage() {
        return _Message;
    }

    public void setMessage(String Message) {
        this._Message = Message;
    }    

    public String getInvitationSent() {
        return _InvitationSent;
    }

    public void setInvitationSent(String InvitationSent) {
        this._InvitationSent = InvitationSent;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getTimeSent() {
        return _TimeSent;
    }

    public void setTimeSent(String TimeSent) {
        this._TimeSent = TimeSent;
    }    

}