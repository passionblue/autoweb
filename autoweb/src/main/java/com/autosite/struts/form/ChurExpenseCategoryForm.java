package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChurExpenseCategoryForm extends ActionForm {

	private String _ExpenseCategory;             
	private String _Display;             

    public String getExpenseCategory() {
        return _ExpenseCategory;
    }

    public void setExpenseCategory(String ExpenseCategory) {
        this._ExpenseCategory = ExpenseCategory;
    }    

    public String getDisplay() {
        return _Display;
    }

    public void setDisplay(String Display) {
        this._Display = Display;
    }    

}