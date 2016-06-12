package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SweepUserConfigForm extends ActionForm {

	private String _UserId;             
	private String _MaxSweepAllowed;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getMaxSweepAllowed() {
        return _MaxSweepAllowed;
    }

    public void setMaxSweepAllowed(String MaxSweepAllowed) {
        this._MaxSweepAllowed = MaxSweepAllowed;
    }    

}