package com.autosite.db;

import java.sql.Timestamp;

import org.apache.solr.client.solrj.beans.Field;

/**
 * CleanerPickupDelivery entity. @author MyEclipse Persistence Tools
 */

public class CleanerPickupDelivery extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long locationId;
    private long customerId;
    private long ticketId;
    private String ticketUid;

    @Field("PICKUP_TICKET")
    private String pickupTicket;
    private String checkinTicketForDelivery;
    private int isDeliveryRequest;
    private int isWebRequest;
    private int isRecurringRequest;
    private int isReceiveReady;
    private int isReceiveComplete;
    private long recurId;
    private int cancelled;
    private int completed;
    private String customerName;
    private String address;
    private String aptNumber;
    private String phone;
    private String email;
    private int ackReceiveMethod;
    private String customerInstruction;
    private int pickupDeliveryByDay;
    private String pickupDeliveryByTime;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;
    private Timestamp timeAcked;
    private long ackedByUserId;
    private Timestamp timeReady;
    private Timestamp timeNotified;
    private Timestamp timeCompleted;
    private String note;
    private String pickupNote;
    private String deliveryNote;

    // Constructors

    /** default constructor */
    public CleanerPickupDelivery() {
    }

    /** minimal constructor */
    public CleanerPickupDelivery(long siteId, long locationId, long customerId, long ticketId, String pickupTicket, int isDeliveryRequest, int isWebRequest, int isRecurringRequest,
            int isReceiveReady, int isReceiveComplete, String customerName, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.locationId = locationId;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.pickupTicket = pickupTicket;
        this.isDeliveryRequest = isDeliveryRequest;
        this.isWebRequest = isWebRequest;
        this.isRecurringRequest = isRecurringRequest;
        this.isReceiveReady = isReceiveReady;
        this.isReceiveComplete = isReceiveComplete;
        this.customerName = customerName;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public CleanerPickupDelivery(long siteId, long locationId, long customerId, long ticketId, String ticketUid, String pickupTicket, String checkinTicketForDelivery, int isDeliveryRequest,
            int isWebRequest, int isRecurringRequest, int isReceiveReady, int isReceiveComplete, long recurId, int cancelled, int completed, String customerName, String address, String aptNumber,
            String phone, String email, int ackReceiveMethod, String customerInstruction, int pickupDeliveryByDay, String pickupDeliveryByTime, Timestamp timeCreated, Timestamp timeUpdated,
            Timestamp timeAcked, long ackedByUserId, Timestamp timeReady, Timestamp timeNotified, Timestamp timeCompleted, String note, String pickupNote, String deliveryNote) {
        this.siteId = siteId;
        this.locationId = locationId;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.ticketUid = ticketUid;
        this.pickupTicket = pickupTicket;
        this.checkinTicketForDelivery = checkinTicketForDelivery;
        this.isDeliveryRequest = isDeliveryRequest;
        this.isWebRequest = isWebRequest;
        this.isRecurringRequest = isRecurringRequest;
        this.isReceiveReady = isReceiveReady;
        this.isReceiveComplete = isReceiveComplete;
        this.recurId = recurId;
        this.cancelled = cancelled;
        this.completed = completed;
        this.customerName = customerName;
        this.address = address;
        this.aptNumber = aptNumber;
        this.phone = phone;
        this.email = email;
        this.ackReceiveMethod = ackReceiveMethod;
        this.customerInstruction = customerInstruction;
        this.pickupDeliveryByDay = pickupDeliveryByDay;
        this.pickupDeliveryByTime = pickupDeliveryByTime;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.timeAcked = timeAcked;
        this.ackedByUserId = ackedByUserId;
        this.timeReady = timeReady;
        this.timeNotified = timeNotified;
        this.timeCompleted = timeCompleted;
        this.note = note;
        this.pickupNote = pickupNote;
        this.deliveryNote = deliveryNote;
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

    public long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketUid() {
        return this.ticketUid;
    }

    public void setTicketUid(String ticketUid) {
        this.ticketUid = ticketUid;
    }

    public String getPickupTicket() {
        return this.pickupTicket;
    }

    public void setPickupTicket(String pickupTicket) {
        this.pickupTicket = pickupTicket;
    }

    public String getCheckinTicketForDelivery() {
        return this.checkinTicketForDelivery;
    }

    public void setCheckinTicketForDelivery(String checkinTicketForDelivery) {
        this.checkinTicketForDelivery = checkinTicketForDelivery;
    }

    public int getIsDeliveryRequest() {
        return this.isDeliveryRequest;
    }

    public void setIsDeliveryRequest(int isDeliveryRequest) {
        this.isDeliveryRequest = isDeliveryRequest;
    }

    public int getIsWebRequest() {
        return this.isWebRequest;
    }

    public void setIsWebRequest(int isWebRequest) {
        this.isWebRequest = isWebRequest;
    }

    public int getIsRecurringRequest() {
        return this.isRecurringRequest;
    }

    public void setIsRecurringRequest(int isRecurringRequest) {
        this.isRecurringRequest = isRecurringRequest;
    }

    public int getIsReceiveReady() {
        return this.isReceiveReady;
    }

    public void setIsReceiveReady(int isReceiveReady) {
        this.isReceiveReady = isReceiveReady;
    }

    public int getIsReceiveComplete() {
        return this.isReceiveComplete;
    }

    public void setIsReceiveComplete(int isReceiveComplete) {
        this.isReceiveComplete = isReceiveComplete;
    }

    public long getRecurId() {
        return this.recurId;
    }

    public void setRecurId(long recurId) {
        this.recurId = recurId;
    }

    public int getCancelled() {
        return this.cancelled;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = cancelled;
    }

    public int getCompleted() {
        return this.completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAptNumber() {
        return this.aptNumber;
    }

    public void setAptNumber(String aptNumber) {
        this.aptNumber = aptNumber;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAckReceiveMethod() {
        return this.ackReceiveMethod;
    }

    public void setAckReceiveMethod(int ackReceiveMethod) {
        this.ackReceiveMethod = ackReceiveMethod;
    }

    public String getCustomerInstruction() {
        return this.customerInstruction;
    }

    public void setCustomerInstruction(String customerInstruction) {
        this.customerInstruction = customerInstruction;
    }

    public int getPickupDeliveryByDay() {
        return this.pickupDeliveryByDay;
    }

    public void setPickupDeliveryByDay(int pickupDeliveryByDay) {
        this.pickupDeliveryByDay = pickupDeliveryByDay;
    }

    public String getPickupDeliveryByTime() {
        return this.pickupDeliveryByTime;
    }

    public void setPickupDeliveryByTime(String pickupDeliveryByTime) {
        this.pickupDeliveryByTime = pickupDeliveryByTime;
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

    public Timestamp getTimeAcked() {
        return this.timeAcked;
    }

    public void setTimeAcked(Timestamp timeAcked) {
        this.timeAcked = timeAcked;
    }

    public long getAckedByUserId() {
        return this.ackedByUserId;
    }

    public void setAckedByUserId(long ackedByUserId) {
        this.ackedByUserId = ackedByUserId;
    }

    public Timestamp getTimeReady() {
        return this.timeReady;
    }

    public void setTimeReady(Timestamp timeReady) {
        this.timeReady = timeReady;
    }

    public Timestamp getTimeNotified() {
        return this.timeNotified;
    }

    public void setTimeNotified(Timestamp timeNotified) {
        this.timeNotified = timeNotified;
    }

    public Timestamp getTimeCompleted() {
        return this.timeCompleted;
    }

    public void setTimeCompleted(Timestamp timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPickupNote() {
        return this.pickupNote;
    }

    public void setPickupNote(String pickupNote) {
        this.pickupNote = pickupNote;
    }

    public String getDeliveryNote() {
        return this.deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

}
