/* 
Template last modification history:


Source Generated: Sun Jul 12 20:19:43 EDT 2015
*/

package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ExpenseItemForm extends ActionForm {

	private String _ExpenseCategoryId;             
	private String _ExpenseItem;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getExpenseCategoryId() {
        return _ExpenseCategoryId;
    }

    public void setExpenseCategoryId(String ExpenseCategoryId) {
        this._ExpenseCategoryId = ExpenseCategoryId;
    }    

    public String getExpenseItem() {
        return _ExpenseItem;
    }

    public void setExpenseItem(String ExpenseItem) {
        this._ExpenseItem = ExpenseItem;
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