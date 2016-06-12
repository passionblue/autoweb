package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChurIncomeTypeForm extends ActionForm {

	private String _IncomeType;             

    public String getIncomeType() {
        return _IncomeType;
    }

    public void setIncomeType(String IncomeType) {
        this._IncomeType = IncomeType;
    }    

}