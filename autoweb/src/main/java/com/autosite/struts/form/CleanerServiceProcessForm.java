package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerServiceProcessForm extends ActionForm {

	private String _TicketId;             
	private String _ProcessUserId;             
	private String _ProcessType;             
	private String _TimeStarted;             
	private String _TimeEnded;             
	private String _Note;             

    public String getTicketId() {
        return _TicketId;
    }

    public void setTicketId(String TicketId) {
        this._TicketId = TicketId;
    }    

    public String getProcessUserId() {
        return _ProcessUserId;
    }

    public void setProcessUserId(String ProcessUserId) {
        this._ProcessUserId = ProcessUserId;
    }    

    public String getProcessType() {
        return _ProcessType;
    }

    public void setProcessType(String ProcessType) {
        this._ProcessType = ProcessType;
    }    

    public String getTimeStarted() {
        return _TimeStarted;
    }

    public void setTimeStarted(String TimeStarted) {
        this._TimeStarted = TimeStarted;
    }    

    public String getTimeEnded() {
        return _TimeEnded;
    }

    public void setTimeEnded(String TimeEnded) {
        this._TimeEnded = TimeEnded;
    }    

    public String getNote() {
        return _Note;
    }

    public void setNote(String Note) {
        this._Note = Note;
    }    

}