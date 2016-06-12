package com.autosite.db;

import java.sql.Timestamp;

/**
 * CleanerCustomerNotification entity. @author MyEclipse Persistence Tools
 */

public class CleanerCustomerNotification extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long customerId;
    private long reasonforId;
    private String reasonforTarget;
    private int notificationType;
    private int sourceType;
    private int triggerType;
    private int isRetransmit;
    private int methodType;
    private String templateType;
    private String content;
    private String destination;
    private String reference;
    private Timestamp timeScheduled;
    private Timestamp timeCreated;
    private Timestamp timeSent;

    // Constructors

    /** default constructor */
    public CleanerCustomerNotification() {
    }

    /** minimal constructor */
    public CleanerCustomerNotification(long siteId, long customerId, long reasonforId, int notificationType, int sourceType, int triggerType, int isRetransmit, int methodType, String content,
            Timestamp timeCreated) {
        this.siteId = siteId;
        this.customerId = customerId;
        this.reasonforId = reasonforId;
        this.notificationType = notificationType;
        this.sourceType = sourceType;
        this.triggerType = triggerType;
        this.isRetransmit = isRetransmit;
        this.methodType = methodType;
        this.content = content;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public CleanerCustomerNotification(long siteId, long customerId, long reasonforId, String reasonforTarget, int notificationType, int sourceType, int triggerType, int isRetransmit, int methodType,
            String templateType, String content, String destination, String reference, Timestamp timeScheduled, Timestamp timeCreated, Timestamp timeSent) {
        this.siteId = siteId;
        this.customerId = customerId;
        this.reasonforId = reasonforId;
        this.reasonforTarget = reasonforTarget;
        this.notificationType = notificationType;
        this.sourceType = sourceType;
        this.triggerType = triggerType;
        this.isRetransmit = isRetransmit;
        this.methodType = methodType;
        this.templateType = templateType;
        this.content = content;
        this.destination = destination;
        this.reference = reference;
        this.timeScheduled = timeScheduled;
        this.timeCreated = timeCreated;
        this.timeSent = timeSent;
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

    public long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getReasonforId() {
        return this.reasonforId;
    }

    public void setReasonforId(long reasonforId) {
        this.reasonforId = reasonforId;
    }

    public String getReasonforTarget() {
        return this.reasonforTarget;
    }

    public void setReasonforTarget(String reasonforTarget) {
        this.reasonforTarget = reasonforTarget;
    }

    public int getNotificationType() {
        return this.notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public int getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public int getTriggerType() {
        return this.triggerType;
    }

    public void setTriggerType(int triggerType) {
        this.triggerType = triggerType;
    }

    public int getIsRetransmit() {
        return this.isRetransmit;
    }

    public void setIsRetransmit(int isRetransmit) {
        this.isRetransmit = isRetransmit;
    }

    public int getMethodType() {
        return this.methodType;
    }

    public void setMethodType(int methodType) {
        this.methodType = methodType;
    }

    public String getTemplateType() {
        return this.templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Timestamp getTimeScheduled() {
        return this.timeScheduled;
    }

    public void setTimeScheduled(Timestamp timeScheduled) {
        this.timeScheduled = timeScheduled;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getTimeSent() {
        return this.timeSent;
    }

    public void setTimeSent(Timestamp timeSent) {
        this.timeSent = timeSent;
    }

}
