package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcCategoryForm extends ActionForm {

	private String _ParentId;             
	private String _PageId;             
	private String _CategoryName;             
	private String _ImageUrl;             
	private String _CategoryDescription;             
	private String _Alt1;             
	private String _Alt2;             

    public String getParentId() {
        return _ParentId;
    }

    public void setParentId(String ParentId) {
        this._ParentId = ParentId;
    }    

    public String getPageId() {
        return _PageId;
    }

    public void setPageId(String PageId) {
        this._PageId = PageId;
    }    

    public String getCategoryName() {
        return _CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this._CategoryName = CategoryName;
    }    

    public String getImageUrl() {
        return _ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this._ImageUrl = ImageUrl;
    }    

    public String getCategoryDescription() {
        return _CategoryDescription;
    }

    public void setCategoryDescription(String CategoryDescription) {
        this._CategoryDescription = CategoryDescription;
    }    

    public String getAlt1() {
        return _Alt1;
    }

    public void setAlt1(String Alt1) {
        this._Alt1 = Alt1;
    }    

    public String getAlt2() {
        return _Alt2;
    }

    public void setAlt2(String Alt2) {
        this._Alt2 = Alt2;
    }    

}