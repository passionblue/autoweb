package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChurIncomeCategoryForm extends ActionForm {

	private String _IncomeCategory;             
	private String _Display;             
	private String _TimeCreated;             

    public String getIncomeCategory() {
        return _IncomeCategory;
    }

    public void setIncomeCategory(String IncomeCategory) {
        this._IncomeCategory = IncomeCategory;
    }    

    public String getDisplay() {
        return _Display;
    }

    public void setDisplay(String Display) {
        this._Display = Display;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}