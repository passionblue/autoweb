package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PollVoteForm extends ActionForm {

	private String _PollId;             
	private String _UserId;             
	private String _Answer;             
	private String _MultipleAnswer;             
	private String _ByGuest;             
	private String _IpAddress;             
	private String _Pcid;             
	private String _DupCheckKey;             
	private String _Note;             
	private String _OwnAnswer;             
	private String _TimeCreated;             

    public String getPollId() {
        return _PollId;
    }

    public void setPollId(String PollId) {
        this._PollId = PollId;
    }    

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getAnswer() {
        return _Answer;
    }

    public void setAnswer(String Answer) {
        this._Answer = Answer;
    }    

    public String getMultipleAnswer() {
        return _MultipleAnswer;
    }

    public void setMultipleAnswer(String MultipleAnswer) {
        this._MultipleAnswer = MultipleAnswer;
    }    

    public String getByGuest() {
        return _ByGuest;
    }

    public void setByGuest(String ByGuest) {
        this._ByGuest = ByGuest;
    }    

    public String getIpAddress() {
        return _IpAddress;
    }

    public void setIpAddress(String IpAddress) {
        this._IpAddress = IpAddress;
    }    

    public String getPcid() {
        return _Pcid;
    }

    public void setPcid(String Pcid) {
        this._Pcid = Pcid;
    }    

    public String getDupCheckKey() {
        return _DupCheckKey;
    }

    public void setDupCheckKey(String DupCheckKey) {
        this._DupCheckKey = DupCheckKey;
    }    

    public String getNote() {
        return _Note;
    }

    public void setNote(String Note) {
        this._Note = Note;
    }    

    public String getOwnAnswer() {
        return _OwnAnswer;
    }

    public void setOwnAnswer(String OwnAnswer) {
        this._OwnAnswer = OwnAnswer;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}