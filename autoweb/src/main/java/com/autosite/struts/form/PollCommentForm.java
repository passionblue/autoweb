package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PollCommentForm extends ActionForm {

	private String _PollId;             
	private String _UserId;             
	private String _Comment;             
	private String _Hide;             
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

    public String getComment() {
        return _Comment;
    }

    public void setComment(String Comment) {
        this._Comment = Comment;
    }    

    public String getHide() {
        return _Hide;
    }

    public void setHide(String Hide) {
        this._Hide = Hide;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}