package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ForumCategoryForm extends ActionForm {

	private String _Category;             
	private String _TimeCreated;             

    public String getCategory() {
        return _Category;
    }

    public void setCategory(String Category) {
        this._Category = Category;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}