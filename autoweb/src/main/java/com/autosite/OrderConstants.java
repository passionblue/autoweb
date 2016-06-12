package com.autosite;

public class OrderConstants {
    
    public final static int OrderStatusInvalid = -1;
    public final static int OrderStatusReceived = 0;
    public final static int OrderStatusApproved = 1;
    public final static int OrderStatusInProcess = 2;
    public final static int OrderStatusFulfilled = 3;
    public final static int OrderStatusShipped = 4;
    public final static int OrderStatusHalt = 11;
    public final static int OrderStatusCancelled = 12;
    public final static int OrderStatusReturned = 13;

    public final static String OrderStatusStrReceived = "Received";
    public final static String OrderStatusStrApproved = "Approved";
    public final static String OrderStatusStrInProcess = "InProcess";
    public final static String OrderStatusStrFulfilled = "Fulfilled";
    public final static String OrderStatusStrShipped = "Shipped";
    public final static String OrderStatusStrHalt = "Halt";
    public final static String OrderStatusStrCancelled = "Cancelled";
    public final static String OrderStatusStrReturned = "Returned";
    
    public final static int PaymentTypeCreditCard = 0;
    public final static int PaymentTypePaypal = 1;
    public final static int PaymentTypeCheck = 2;
    
    public final static String PaymentTypeStrCreditCard = "Credit Card";
    public final static String PaymentTypeStrPaypal = "Paypal";
    public final static String PaymentTypeStrCheck = "Check";
    
}
