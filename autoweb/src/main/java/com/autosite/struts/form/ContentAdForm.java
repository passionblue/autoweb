package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ContentAdForm extends ActionForm {

	private String _ContentId;             
	private String _PositionCode;             
	private String _AdContent;             
	private String _Hide;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getContentId() {
        return _ContentId;
    }

    public void setContentId(String ContentId) {
        this._ContentId = ContentId;
    }    

    public String getPositionCode() {
        return _PositionCode;
    }

    public void setPositionCode(String PositionCode) {
        this._PositionCode = PositionCode;
    }    

    public String getAdContent() {
        return _AdContent;
    }

    public void setAdContent(String AdContent) {
        this._AdContent = AdContent;
    }    

    public String getHide() {
        return _Hide;
    }

    public void setHide(String Hide) {
        this._Hide = Hide;
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