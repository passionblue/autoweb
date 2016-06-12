package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BlockListForm extends ActionForm {

	private String _IpData;             
	private String _RangeCheck;             
	private String _ReasonCode;             
	private String _TimeCreated;             

    public String getIpData() {
        return _IpData;
    }

    public void setIpData(String IpData) {
        this._IpData = IpData;
    }    

    public String getRangeCheck() {
        return _RangeCheck;
    }

    public void setRangeCheck(String RangeCheck) {
        this._RangeCheck = RangeCheck;
    }    

    public String getReasonCode() {
        return _ReasonCode;
    }

    public void setReasonCode(String ReasonCode) {
        this._ReasonCode = ReasonCode;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}