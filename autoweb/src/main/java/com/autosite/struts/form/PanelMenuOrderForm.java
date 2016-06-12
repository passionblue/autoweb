package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PanelMenuOrderForm extends ActionForm {

	private String _PanelId;             
	private String _OrderedIds;             
	private String _Reverse;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getPanelId() {
        return _PanelId;
    }

    public void setPanelId(String PanelId) {
        this._PanelId = PanelId;
    }    

    public String getOrderedIds() {
        return _OrderedIds;
    }

    public void setOrderedIds(String OrderedIds) {
        this._OrderedIds = OrderedIds;
    }    

    public String getReverse() {
        return _Reverse;
    }

    public void setReverse(String Reverse) {
        this._Reverse = Reverse;
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