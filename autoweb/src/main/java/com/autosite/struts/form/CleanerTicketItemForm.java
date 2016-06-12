package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerTicketItemForm extends ActionForm {

	private String _TicketId;             
	private String _ParentTicketId;             
	private String _ProductId;             
	private String _SubtotalAmount;             
	private String _TotalAmount;             
	private String _DiscountId;             
	private String _TotalDiscountAmount;             
	private String _SpecialDiscountAmount;             
	private String _Note;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getTicketId() {
        return _TicketId;
    }

    public void setTicketId(String TicketId) {
        this._TicketId = TicketId;
    }    

    public String getParentTicketId() {
        return _ParentTicketId;
    }

    public void setParentTicketId(String ParentTicketId) {
        this._ParentTicketId = ParentTicketId;
    }    

    public String getProductId() {
        return _ProductId;
    }

    public void setProductId(String ProductId) {
        this._ProductId = ProductId;
    }    

    public String getSubtotalAmount() {
        return _SubtotalAmount;
    }

    public void setSubtotalAmount(String SubtotalAmount) {
        this._SubtotalAmount = SubtotalAmount;
    }    

    public String getTotalAmount() {
        return _TotalAmount;
    }

    public void setTotalAmount(String TotalAmount) {
        this._TotalAmount = TotalAmount;
    }    

    public String getDiscountId() {
        return _DiscountId;
    }

    public void setDiscountId(String DiscountId) {
        this._DiscountId = DiscountId;
    }    

    public String getTotalDiscountAmount() {
        return _TotalDiscountAmount;
    }

    public void setTotalDiscountAmount(String TotalDiscountAmount) {
        this._TotalDiscountAmount = TotalDiscountAmount;
    }    

    public String getSpecialDiscountAmount() {
        return _SpecialDiscountAmount;
    }

    public void setSpecialDiscountAmount(String SpecialDiscountAmount) {
        this._SpecialDiscountAmount = SpecialDiscountAmount;
    }    

    public String getNote() {
        return _Note;
    }

    public void setNote(String Note) {
        this._Note = Note;
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