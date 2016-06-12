package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcOrderForm extends ActionForm {

	private String _UserId;             
	private String _AnonymousUserId;             
	private String _OrderNum;             
	private String _OrderStatus;             
	private String _OrderTotal;             
	private String _TimeReceived;             
	private String _TimeApproved;             
	private String _TimeHalt;             
	private String _TimeCancelled;             
	private String _TimeFulfilled;             
	private String _TimeShipped;             
	private String _TimeReturned;             
	private String _ReProcess;             
	private String _OrgOrderId;             
	private String _ApprovedBy;             
	private String _FulfilledBy;             
	private String _ShippedBy;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getAnonymousUserId() {
        return _AnonymousUserId;
    }

    public void setAnonymousUserId(String AnonymousUserId) {
        this._AnonymousUserId = AnonymousUserId;
    }    

    public String getOrderNum() {
        return _OrderNum;
    }

    public void setOrderNum(String OrderNum) {
        this._OrderNum = OrderNum;
    }    

    public String getOrderStatus() {
        return _OrderStatus;
    }

    public void setOrderStatus(String OrderStatus) {
        this._OrderStatus = OrderStatus;
    }    

    public String getOrderTotal() {
        return _OrderTotal;
    }

    public void setOrderTotal(String OrderTotal) {
        this._OrderTotal = OrderTotal;
    }    

    public String getTimeReceived() {
        return _TimeReceived;
    }

    public void setTimeReceived(String TimeReceived) {
        this._TimeReceived = TimeReceived;
    }    

    public String getTimeApproved() {
        return _TimeApproved;
    }

    public void setTimeApproved(String TimeApproved) {
        this._TimeApproved = TimeApproved;
    }    

    public String getTimeHalt() {
        return _TimeHalt;
    }

    public void setTimeHalt(String TimeHalt) {
        this._TimeHalt = TimeHalt;
    }    

    public String getTimeCancelled() {
        return _TimeCancelled;
    }

    public void setTimeCancelled(String TimeCancelled) {
        this._TimeCancelled = TimeCancelled;
    }    

    public String getTimeFulfilled() {
        return _TimeFulfilled;
    }

    public void setTimeFulfilled(String TimeFulfilled) {
        this._TimeFulfilled = TimeFulfilled;
    }    

    public String getTimeShipped() {
        return _TimeShipped;
    }

    public void setTimeShipped(String TimeShipped) {
        this._TimeShipped = TimeShipped;
    }    

    public String getTimeReturned() {
        return _TimeReturned;
    }

    public void setTimeReturned(String TimeReturned) {
        this._TimeReturned = TimeReturned;
    }    

    public String getReProcess() {
        return _ReProcess;
    }

    public void setReProcess(String ReProcess) {
        this._ReProcess = ReProcess;
    }    

    public String getOrgOrderId() {
        return _OrgOrderId;
    }

    public void setOrgOrderId(String OrgOrderId) {
        this._OrgOrderId = OrgOrderId;
    }    

    public String getApprovedBy() {
        return _ApprovedBy;
    }

    public void setApprovedBy(String ApprovedBy) {
        this._ApprovedBy = ApprovedBy;
    }    

    public String getFulfilledBy() {
        return _FulfilledBy;
    }

    public void setFulfilledBy(String FulfilledBy) {
        this._FulfilledBy = FulfilledBy;
    }    

    public String getShippedBy() {
        return _ShippedBy;
    }

    public void setShippedBy(String ShippedBy) {
        this._ShippedBy = ShippedBy;
    }    

}