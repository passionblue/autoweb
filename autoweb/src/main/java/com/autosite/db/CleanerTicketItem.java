package com.autosite.db;

import java.sql.Timestamp;

/**
 * CleanerTicketItem entity. @author MyEclipse Persistence Tools
 */

public class CleanerTicketItem extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long ticketId;
    private long parentTicketId;
    private long productId;
    private double subtotalAmount;
    private double totalAmount;
    private long discountId;
    private double totalDiscountAmount;
    private double specialDiscountAmount;
    private String note;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public CleanerTicketItem() {
    }

    /** minimal constructor */
    public CleanerTicketItem(long siteId, long ticketId, long parentTicketId, long productId, double subtotalAmount) {
        this.siteId = siteId;
        this.ticketId = ticketId;
        this.parentTicketId = parentTicketId;
        this.productId = productId;
        this.subtotalAmount = subtotalAmount;
    }

    /** full constructor */
    public CleanerTicketItem(long siteId, long ticketId, long parentTicketId, long productId, double subtotalAmount, double totalAmount, long discountId, double totalDiscountAmount,
            double specialDiscountAmount, String note, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.ticketId = ticketId;
        this.parentTicketId = parentTicketId;
        this.productId = productId;
        this.subtotalAmount = subtotalAmount;
        this.totalAmount = totalAmount;
        this.discountId = discountId;
        this.totalDiscountAmount = totalDiscountAmount;
        this.specialDiscountAmount = specialDiscountAmount;
        this.note = note;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
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

    public long getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public long getParentTicketId() {
        return this.parentTicketId;
    }

    public void setParentTicketId(long parentTicketId) {
        this.parentTicketId = parentTicketId;
    }

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public double getSubtotalAmount() {
        return this.subtotalAmount;
    }

    public void setSubtotalAmount(double subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getDiscountId() {
        return this.discountId;
    }

    public void setDiscountId(long discountId) {
        this.discountId = discountId;
    }

    public double getTotalDiscountAmount() {
        return this.totalDiscountAmount;
    }

    public void setTotalDiscountAmount(double totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public double getSpecialDiscountAmount() {
        return this.specialDiscountAmount;
    }

    public void setSpecialDiscountAmount(double specialDiscountAmount) {
        this.specialDiscountAmount = specialDiscountAmount;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getTimeUpdated() {
        return this.timeUpdated;
    }

    public void setTimeUpdated(Timestamp timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

}
