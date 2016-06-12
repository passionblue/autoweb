package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PageStyleForm extends ActionForm {

	private String _PageId;             
	private String _StyleId;             

    public String getPageId() {
        return _PageId;
    }

    public void setPageId(String PageId) {
        this._PageId = PageId;
    }    

    public String getStyleId() {
        return _StyleId;
    }

    public void setStyleId(String StyleId) {
        this._StyleId = StyleId;
    }    

}