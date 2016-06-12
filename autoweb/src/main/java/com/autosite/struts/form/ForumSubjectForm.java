package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ForumSubjectForm extends ActionForm {

	private String _Subject;             
	private String _TimeCreated;             

    public String getSubject() {
        return _Subject;
    }

    public void setSubject(String Subject) {
        this._Subject = Subject;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}