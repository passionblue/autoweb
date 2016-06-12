package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcCheckoutPaymentWithoutRegisterForm extends ActionForm {

	private String _UserId;             
	private String _CartSerial;             
	private String _PaymentType;             
	private String _CardType;             
	private String _PaymentNum;             
	private String _ExpireMonth;             
	private String _ExpireYear;             
	private String _Ccv;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getCartSerial() {
        return _CartSerial;
    }

    public void setCartSerial(String CartSerial) {
        this._CartSerial = CartSerial;
    }    

    public String getPaymentType() {
        return _PaymentType;
    }

    public void setPaymentType(String PaymentType) {
        this._PaymentType = PaymentType;
    }    

    public String getCardType() {
        return _CardType;
    }

    public void setCardType(String CardType) {
        this._CardType = CardType;
    }    

    public String getPaymentNum() {
        return _PaymentNum;
    }

    public void setPaymentNum(String PaymentNum) {
        this._PaymentNum = PaymentNum;
    }    

    public String getExpireMonth() {
        return _ExpireMonth;
    }

    public void setExpireMonth(String ExpireMonth) {
        this._ExpireMonth = ExpireMonth;
    }    

    public String getExpireYear() {
        return _ExpireYear;
    }

    public void setExpireYear(String ExpireYear) {
        this._ExpireYear = ExpireYear;
    }    

    public String getCcv() {
        return _Ccv;
    }

    public void setCcv(String Ccv) {
        this._Ccv = Ccv;
    }    

}