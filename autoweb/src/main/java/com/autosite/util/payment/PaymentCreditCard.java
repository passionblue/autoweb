package com.autosite.util.payment;

import com.autosite.OrderConstants;
import com.autosite.db.EcPaymentInfo;

public class PaymentCreditCard extends Payment {

    @Override
    public String getPaymentName() {
        return OrderConstants.PaymentTypeStrCreditCard;
    }

    @Override
    public int getPaymentType() {
        return OrderConstants.PaymentTypeCreditCard;
    }

    @Override
    public PaymentResult auth(EcPaymentInfo paymentInfo, double amount) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PaymentResult cancel(EcPaymentInfo paymentInfo, double amount, String txCode) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PaymentResult charge(EcPaymentInfo paymentInfo, double amount) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    
    
    
}
