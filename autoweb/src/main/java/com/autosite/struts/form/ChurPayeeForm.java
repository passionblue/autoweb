package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChurPayeeForm extends ActionForm {

	private String _Title;             
	private String _Remark;             

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String Title) {
        this._Title = Title;
    }    

    public String getRemark() {
        return _Remark;
    }

    public void setRemark(String Remark) {
        this._Remark = Remark;
    }    

}