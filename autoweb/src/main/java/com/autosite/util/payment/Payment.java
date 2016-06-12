package com.autosite.util.payment;

import com.autosite.db.EcPaymentInfo;

abstract public class Payment {
    
    abstract public int getPaymentType(); 
    abstract public String getPaymentName();
    
    abstract public PaymentResult auth(EcPaymentInfo paymentInfo, double amount) throws Exception;
    abstract public PaymentResult charge(EcPaymentInfo paymentInfo, double amount) throws Exception;
    abstract public PaymentResult cancel(EcPaymentInfo paymentInfo, double amount, String txCode) throws Exception;
    
}
