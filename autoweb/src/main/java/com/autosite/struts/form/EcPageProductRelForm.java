package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcPageProductRelForm extends ActionForm {

	private String _ProductId;             
	private String _CategoryId;             
	private String _Hide;             
	private String _MainCategory;             
	private String _TimeCreated;             

    public String getProductId() {
        return _ProductId;
    }

    public void setProductId(String ProductId) {
        this._ProductId = ProductId;
    }    

    public String getCategoryId() {
        return _CategoryId;
    }

    public void setCategoryId(String CategoryId) {
        this._CategoryId = CategoryId;
    }    

    public String getHide() {
        return _Hide;
    }

    public void setHide(String Hide) {
        this._Hide = Hide;
    }    

    public String getMainCategory() {
        return _MainCategory;
    }

    public void setMainCategory(String MainCategory) {
        this._MainCategory = MainCategory;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}