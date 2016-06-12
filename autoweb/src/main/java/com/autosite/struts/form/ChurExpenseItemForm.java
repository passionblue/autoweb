package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChurExpenseItemForm extends ActionForm {

	private String _CategoryId;             
	private String _ExpenseItem;             
	private String _Display;             

    public String getCategoryId() {
        return _CategoryId;
    }

    public void setCategoryId(String CategoryId) {
        this._CategoryId = CategoryId;
    }    

    public String getExpenseItem() {
        return _ExpenseItem;
    }

    public void setExpenseItem(String ExpenseItem) {
        this._ExpenseItem = ExpenseItem;
    }    

    public String getDisplay() {
        return _Display;
    }

    public void setDisplay(String Display) {
        this._Display = Display;
    }    

}