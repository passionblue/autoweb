package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcAnonymousTransactionForm extends ActionForm {

	private String _AnonymousUserId;             
	private String _OrderId;             
	private String _PaymentInfoId;             
	private String _Amount;             
	private String _TransactionType;             
	private String _Result;             
	private String _TimeProcessed;             
	private String _ReturnCode;             
	private String _ReturnMsg;             

    public String getAnonymousUserId() {
        return _AnonymousUserId;
    }

    public void setAnonymousUserId(String AnonymousUserId) {
        this._AnonymousUserId = AnonymousUserId;
    }    

    public String getOrderId() {
        return _OrderId;
    }

    public void setOrderId(String OrderId) {
        this._OrderId = OrderId;
    }    

    public String getPaymentInfoId() {
        return _PaymentInfoId;
    }

    public void setPaymentInfoId(String PaymentInfoId) {
        this._PaymentInfoId = PaymentInfoId;
    }    

    public String getAmount() {
        return _Amount;
    }

    public void setAmount(String Amount) {
        this._Amount = Amount;
    }    

    public String getTransactionType() {
        return _TransactionType;
    }

    public void setTransactionType(String TransactionType) {
        this._TransactionType = TransactionType;
    }    

    public String getResult() {
        return _Result;
    }

    public void setResult(String Result) {
        this._Result = Result;
    }    

    public String getTimeProcessed() {
        return _TimeProcessed;
    }

    public void setTimeProcessed(String TimeProcessed) {
        this._TimeProcessed = TimeProcessed;
    }    

    public String getReturnCode() {
        return _ReturnCode;
    }

    public void setReturnCode(String ReturnCode) {
        this._ReturnCode = ReturnCode;
    }    

    public String getReturnMsg() {
        return _ReturnMsg;
    }

    public void setReturnMsg(String ReturnMsg) {
        this._ReturnMsg = ReturnMsg;
    }    

}