package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChurExpenseForm extends ActionForm {

	private String _Year;             
	private String _Week;             
	private String _ExpenseItemId;             
	private String _PayeeId;             
	private String _Amount;             
	private String _IsCash;             
	private String _CheckNumber;             
	private String _CheckCleared;             
	private String _Comment;             
	private String _Cancelled;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getYear() {
        return _Year;
    }

    public void setYear(String Year) {
        this._Year = Year;
    }    

    public String getWeek() {
        return _Week;
    }

    public void setWeek(String Week) {
        this._Week = Week;
    }    

    public String getExpenseItemId() {
        return _ExpenseItemId;
    }

    public void setExpenseItemId(String ExpenseItemId) {
        this._ExpenseItemId = ExpenseItemId;
    }    

    public String getPayeeId() {
        return _PayeeId;
    }

    public void setPayeeId(String PayeeId) {
        this._PayeeId = PayeeId;
    }    

    public String getAmount() {
        return _Amount;
    }

    public void setAmount(String Amount) {
        this._Amount = Amount;
    }    

    public String getIsCash() {
        return _IsCash;
    }

    public void setIsCash(String IsCash) {
        this._IsCash = IsCash;
    }    

    public String getCheckNumber() {
        return _CheckNumber;
    }

    public void setCheckNumber(String CheckNumber) {
        this._CheckNumber = CheckNumber;
    }    

    public String getCheckCleared() {
        return _CheckCleared;
    }

    public void setCheckCleared(String CheckCleared) {
        this._CheckCleared = CheckCleared;
    }    

    public String getComment() {
        return _Comment;
    }

    public void setComment(String Comment) {
        this._Comment = Comment;
    }    

    public String getCancelled() {
        return _Cancelled;
    }

    public void setCancelled(String Cancelled) {
        this._Cancelled = Cancelled;
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