/* 
Template last modification history:


Source Generated: Sun Mar 15 14:31:01 EDT 2015
*/

package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GenFormFlowForm extends ActionForm {

	private String _ExtString;             
	private String _ExtInt;             

    public String getExtString() {
        return _ExtString;
    }

    public void setExtString(String ExtString) {
        this._ExtString = ExtString;
    }    

    public String getExtInt() {
        return _ExtInt;
    }

    public void setExtInt(String ExtInt) {
        this._ExtInt = ExtInt;
    }    

}