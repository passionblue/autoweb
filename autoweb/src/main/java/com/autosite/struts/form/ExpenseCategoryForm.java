/* 
Template last modification history:


Source Generated: Sun Jul 12 20:19:41 EDT 2015
*/

package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ExpenseCategoryForm extends ActionForm {

	private String _ExpenseCategory;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getExpenseCategory() {
        return _ExpenseCategory;
    }

    public void setExpenseCategory(String ExpenseCategory) {
        this._ExpenseCategory = ExpenseCategory;
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