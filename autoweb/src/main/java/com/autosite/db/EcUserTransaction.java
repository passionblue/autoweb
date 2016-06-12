package com.autosite.db;

import java.sql.Timestamp;

/**
 * EcUserTransaction entity. @author MyEclipse Persistence Tools
 */

public class EcUserTransaction extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private long orderId;
    private long paymentInfoId;
    private double amount;
    private int transactionType;
    private int result;
    private Timestamp timeProcessed;

    // Constructors

    /** default constructor */
    public EcUserTransaction() {
    }

    /** minimal constructor */
    public EcUserTransaction(long siteId, long userId, long orderId, long paymentInfoId, double amount, Timestamp timeProcessed) {
        this.siteId = siteId;
        this.userId = userId;
        this.orderId = orderId;
        this.paymentInfoId = paymentInfoId;
        this.amount = amount;
        this.timeProcessed = timeProcessed;
    }

    /** full constructor */
    public EcUserTransaction(long siteId, long userId, long orderId, long paymentInfoId, double amount, int transactionType, int result, Timestamp timeProcessed) {
        this.siteId = siteId;
        this.userId = userId;
        this.orderId = orderId;
        this.paymentInfoId = paymentInfoId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.result = result;
        this.timeProcessed = timeProcessed;
    }

    // Property accessors

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSiteId() {
        return this.siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getPaymentInfoId() {
        return this.paymentInfoId;
    }

    public void setPaymentInfoId(long paymentInfoId) {
        this.paymentInfoId = paymentInfoId;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Timestamp getTimeProcessed() {
        return this.timeProcessed;
    }

    public void setTimeProcessed(Timestamp timeProcessed) {
        this.timeProcessed = timeProcessed;
    }

}
