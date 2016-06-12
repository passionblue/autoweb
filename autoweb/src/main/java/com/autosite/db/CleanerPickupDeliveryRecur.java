package com.autosite.db;

/**
 * CleanerPickupDeliveryRecur entity. @author MyEclipse Persistence Tools
 */

public class CleanerPickupDeliveryRecur extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long customerId;
    private int weekday;
    private String timeHhdd;

    // Constructors

    /** default constructor */
    public CleanerPickupDeliveryRecur() {
    }

    /** minimal constructor */
    public CleanerPickupDeliveryRecur(long siteId) {
        this.siteId = siteId;
    }

    /** full constructor */
    public CleanerPickupDeliveryRecur(long siteId, long customerId, int weekday, String timeHhdd) {
        this.siteId = siteId;
        this.customerId = customerId;
        this.weekday = weekday;
        this.timeHhdd = timeHhdd;
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

    public int getWeekday() {
        return this.weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getTimeHhdd() {
        return this.timeHhdd;
    }

    public void setTimeHhdd(String timeHhdd) {
        this.timeHhdd = timeHhdd;
    }

}
