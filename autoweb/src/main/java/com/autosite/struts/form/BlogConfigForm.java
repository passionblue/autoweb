package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BlogConfigForm extends ActionForm {

	private String _BlogMainId;             

    public String getBlogMainId() {
        return _BlogMainId;
    }

    public void setBlogMainId(String BlogMainId) {
        this._BlogMainId = BlogMainId;
    }    

}