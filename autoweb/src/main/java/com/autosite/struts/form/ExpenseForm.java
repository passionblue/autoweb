/* 
Template last modification history:


Source Generated: Sun Jul 12 20:40:22 EDT 2015
*/

package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ExpenseForm extends ActionForm {

	private String _ExpenseItemId;             
	private String _Amount;             
	private String _Description;             
	private String _PayMethod;             
	private String _NotExpense;             
	private String _Reference;             
	private String _DateExpense;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getExpenseItemId() {
        return _ExpenseItemId;
    }

    public void setExpenseItemId(String ExpenseItemId) {
        this._ExpenseItemId = ExpenseItemId;
    }    

    public String getAmount() {
        return _Amount;
    }

    public void setAmount(String Amount) {
        this._Amount = Amount;
    }    

    public String getDescription() {
        return _Description;
    }

    public void setDescription(String Description) {
        this._Description = Description;
    }    

    public String getPayMethod() {
        return _PayMethod;
    }

    public void setPayMethod(String PayMethod) {
        this._PayMethod = PayMethod;
    }    

    public String getNotExpense() {
        return _NotExpense;
    }

    public void setNotExpense(String NotExpense) {
        this._NotExpense = NotExpense;
    }    

    public String getReference() {
        return _Reference;
    }

    public void setReference(String Reference) {
        this._Reference = Reference;
    }    

    public String getDateExpense() {
        return _DateExpense;
    }

    public void setDateExpense(String DateExpense) {
        this._DateExpense = DateExpense;
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