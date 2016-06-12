package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ForumCommentForm extends ActionForm {

	private String _Comment;             
	private String _TimeCreated;             

    public String getComment() {
        return _Comment;
    }

    public void setComment(String Comment) {
        this._Comment = Comment;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}