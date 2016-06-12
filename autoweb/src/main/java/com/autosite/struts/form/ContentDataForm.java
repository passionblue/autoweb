package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ContentDataForm extends ActionForm {

	private String _ContentId;             
	private String _Data;             
	private String _Option1;             
	private String _Option2;             
	private String _Option3;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getContentId() {
        return _ContentId;
    }

    public void setContentId(String ContentId) {
        this._ContentId = ContentId;
    }    

    public String getData() {
        return _Data;
    }

    public void setData(String Data) {
        this._Data = Data;
    }    

    public String getOption1() {
        return _Option1;
    }

    public void setOption1(String Option1) {
        this._Option1 = Option1;
    }    

    public String getOption2() {
        return _Option2;
    }

    public void setOption2(String Option2) {
        this._Option2 = Option2;
    }    

    public String getOption3() {
        return _Option3;
    }

    public void setOption3(String Option3) {
        this._Option3 = Option3;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getTimeUpdated() {
        return _TimeUpdated;
    }

    public void setTimeUpdated(String TimeUpdated) {
        this._TimeUpdated = TimeUpdated;
    }    

}