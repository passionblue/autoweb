package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ForumSectionForm extends ActionForm {

	private String _SectionTitle;             
	private String _TimeCreated;             

	public ForumSectionForm() {
	    System.out.println("###########################################");
	}
	
	
    public String getSectionTitle() {
        return _SectionTitle;
    }

    public void setSectionTitle(String SectionTitle) {
        System.out.println("########################################### " + SectionTitle);
        this._SectionTitle = SectionTitle;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}