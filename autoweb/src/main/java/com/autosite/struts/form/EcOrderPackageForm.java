package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcOrderPackageForm extends ActionForm {

	private String _UserId;             
	private String _OrderId;             
	private String _NumOrder;             
	private String _Shipped;             
	private String _TimeShipped;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getOrderId() {
        return _OrderId;
    }

    public void setOrderId(String OrderId) {
        this._OrderId = OrderId;
    }    

    public String getNumOrder() {
        return _NumOrder;
    }

    public void setNumOrder(String NumOrder) {
        this._NumOrder = NumOrder;
    }    

    public String getShipped() {
        return _Shipped;
    }

    public void setShipped(String Shipped) {
        this._Shipped = Shipped;
    }    

    public String getTimeShipped() {
        return _TimeShipped;
    }

    public void setTimeShipped(String TimeShipped) {
        this._TimeShipped = TimeShipped;
    }    

}