package com.autosite.db;

import java.sql.Timestamp;

/**
 * EcOrder entity. @author MyEclipse Persistence Tools
 */

public class EcOrder extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private long anonymousUserId;
    private String orderNum;
    private int orderStatus;
    private double orderTotal;
    private Timestamp timeReceived;
    private Timestamp timeApproved;
    private Timestamp timeHalt;
    private Timestamp timeCancelled;
    private Timestamp timeFulfilled;
    private Timestamp timeShipped;
    private Timestamp timeReturned;
    private int reProcess;
    private long orgOrderId;
    private String approvedBy;
    private String fulfilledBy;
    private String shippedBy;

    // Constructors

    /** default constructor */
    public EcOrder() {
    }

    /** minimal constructor */
    public EcOrder(long siteId, String orderNum, int orderStatus, double orderTotal, Timestamp timeReceived) {
        this.siteId = siteId;
        this.orderNum = orderNum;
        this.orderStatus = orderStatus;
        this.orderTotal = orderTotal;
        this.timeReceived = timeReceived;
    }

    /** full constructor */
    public EcOrder(long siteId, long userId, long anonymousUserId, String orderNum, int orderStatus, double orderTotal, Timestamp timeReceived, Timestamp timeApproved, Timestamp timeHalt,
            Timestamp timeCancelled, Timestamp timeFulfilled, Timestamp timeShipped, Timestamp timeReturned, int reProcess, long orgOrderId, String approvedBy, String fulfilledBy, String shippedBy) {
        this.siteId = siteId;
        this.userId = userId;
        this.anonymousUserId = anonymousUserId;
        this.orderNum = orderNum;
        this.orderStatus = orderStatus;
        this.orderTotal = orderTotal;
        this.timeReceived = timeReceived;
        this.timeApproved = timeApproved;
        this.timeHalt = timeHalt;
        this.timeCancelled = timeCancelled;
        this.timeFulfilled = timeFulfilled;
        this.timeShipped = timeShipped;
        this.timeReturned = timeReturned;
        this.reProcess = reProcess;
        this.orgOrderId = orgOrderId;
        this.approvedBy = approvedBy;
        this.fulfilledBy = fulfilledBy;
        this.shippedBy = shippedBy;
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

    public long getAnonymousUserId() {
        return this.anonymousUserId;
    }

    public void setAnonymousUserId(long anonymousUserId) {
        this.anonymousUserId = anonymousUserId;
    }

    public String getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getOrderTotal() {
        return this.orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Timestamp getTimeReceived() {
        return this.timeReceived;
    }

    public void setTimeReceived(Timestamp timeReceived) {
        this.timeReceived = timeReceived;
    }

    public Timestamp getTimeApproved() {
        return this.timeApproved;
    }

    public void setTimeApproved(Timestamp timeApproved) {
        this.timeApproved = timeApproved;
    }

    public Timestamp getTimeHalt() {
        return this.timeHalt;
    }

    public void setTimeHalt(Timestamp timeHalt) {
        this.timeHalt = timeHalt;
    }

    public Timestamp getTimeCancelled() {
        return this.timeCancelled;
    }

    public void setTimeCancelled(Timestamp timeCancelled) {
        this.timeCancelled = timeCancelled;
    }

    public Timestamp getTimeFulfilled() {
        return this.timeFulfilled;
    }

    public void setTimeFulfilled(Timestamp timeFulfilled) {
        this.timeFulfilled = timeFulfilled;
    }

    public Timestamp getTimeShipped() {
        return this.timeShipped;
    }

    public void setTimeShipped(Timestamp timeShipped) {
        this.timeShipped = timeShipped;
    }

    public Timestamp getTimeReturned() {
        return this.timeReturned;
    }

    public void setTimeReturned(Timestamp timeReturned) {
        this.timeReturned = timeReturned;
    }

    public int getReProcess() {
        return this.reProcess;
    }

    public void setReProcess(int reProcess) {
        this.reProcess = reProcess;
    }

    public long getOrgOrderId() {
        return this.orgOrderId;
    }

    public void setOrgOrderId(long orgOrderId) {
        this.orgOrderId = orgOrderId;
    }

    public String getApprovedBy() {
        return this.approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getFulfilledBy() {
        return this.fulfilledBy;
    }

    public void setFulfilledBy(String fulfilledBy) {
        this.fulfilledBy = fulfilledBy;
    }

    public String getShippedBy() {
        return this.shippedBy;
    }

    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
    }

}
