package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SectionForm extends ActionForm {

	private String _Title;             
	private String _MainPageId;             
	private String _TimeCreated;             

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String Title) {
        this._Title = Title;
    }    

    public String getMainPageId() {
        return _MainPageId;
    }

    public void setMainPageId(String MainPageId) {
        this._MainPageId = MainPageId;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}