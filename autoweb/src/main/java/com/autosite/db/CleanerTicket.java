package com.autosite.db;

import java.sql.Timestamp;

/**
 * CleanerTicket entity. @author MyEclipse Persistence Tools
 */

public class CleanerTicket extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String serial;
    private long parentTicketId;
    private long customerId;
    private long enterUserId;
    private long locationId;
    private String note;
    private int completed;
    private int onhold;
    private long originalTicketId;
    private int returned;
    private String returnedReasonText;
    private String returnedNote;
    private double totalCharge;
    private double finalCharge;
    private long discountId;
    private double discountAmount;
    private String discountNote;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;
    private Timestamp timeCompleted;
    private Timestamp timeOnhold;

    // Constructors

    /** default constructor */
    public CleanerTicket() {
    }

    /** minimal constructor */
    public CleanerTicket(long siteId, String serial, long customerId, long enterUserId, long locationId, int completed, int onhold, long originalTicketId, int returned, Timestamp timeCreated,
            Timestamp timeUpdated) {
        this.siteId = siteId;
        this.serial = serial;
        this.customerId = customerId;
        this.enterUserId = enterUserId;
        this.locationId = locationId;
        this.completed = completed;
        this.onhold = onhold;
        this.originalTicketId = originalTicketId;
        this.returned = returned;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public CleanerTicket(long siteId, String serial, long parentTicketId, long customerId, long enterUserId, long locationId, String note, int completed, int onhold, long originalTicketId,
            int returned, String returnedReasonText, String returnedNote, double totalCharge, double finalCharge, long discountId, double discountAmount, String discountNote, Timestamp timeCreated,
            Timestamp timeUpdated, Timestamp timeCompleted, Timestamp timeOnhold) {
        this.siteId = siteId;
        this.serial = serial;
        this.parentTicketId = parentTicketId;
        this.customerId = customerId;
        this.enterUserId = enterUserId;
        this.locationId = locationId;
        this.note = note;
        this.completed = completed;
        this.onhold = onhold;
        this.originalTicketId = originalTicketId;
        this.returned = returned;
        this.returnedReasonText = returnedReasonText;
        this.returnedNote = returnedNote;
        this.totalCharge = totalCharge;
        this.finalCharge = finalCharge;
        this.discountId = discountId;
        this.discountAmount = discountAmount;
        this.discountNote = discountNote;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.timeCompleted = timeCompleted;
        this.timeOnhold = timeOnhold;
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

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public long getParentTicketId() {
        return this.parentTicketId;
    }

    public void setParentTicketId(long parentTicketId) {
        this.parentTicketId = parentTicketId;
    }

    public long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getEnterUserId() {
        return this.enterUserId;
    }

    public void setEnterUserId(long enterUserId) {
        this.enterUserId = enterUserId;
    }

    public long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCompleted() {
        return this.completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getOnhold() {
        return this.onhold;
    }

    public void setOnhold(int onhold) {
        this.onhold = onhold;
    }

    public long getOriginalTicketId() {
        return this.originalTicketId;
    }

    public void setOriginalTicketId(long originalTicketId) {
        this.originalTicketId = originalTicketId;
    }

    public int getReturned() {
        return this.returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    public String getReturnedReasonText() {
        return this.returnedReasonText;
    }

    public void setReturnedReasonText(String returnedReasonText) {
        this.returnedReasonText = returnedReasonText;
    }

    public String getReturnedNote() {
        return this.returnedNote;
    }

    public void setReturnedNote(String returnedNote) {
        this.returnedNote = returnedNote;
    }

    public double getTotalCharge() {
        return this.totalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public double getFinalCharge() {
        return this.finalCharge;
    }

    public void setFinalCharge(double finalCharge) {
        this.finalCharge = finalCharge;
    }

    public long getDiscountId() {
        return this.discountId;
    }

    public void setDiscountId(long discountId) {
        this.discountId = discountId;
    }

    public double getDiscountAmount() {
        return this.discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountNote() {
        return this.discountNote;
    }

    public void setDiscountNote(String discountNote) {
        this.discountNote = discountNote;
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

    public Timestamp getTimeCompleted() {
        return this.timeCompleted;
    }

    public void setTimeCompleted(Timestamp timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public Timestamp getTimeOnhold() {
        return this.timeOnhold;
    }

    public void setTimeOnhold(Timestamp timeOnhold) {
        this.timeOnhold = timeOnhold;
    }

}
