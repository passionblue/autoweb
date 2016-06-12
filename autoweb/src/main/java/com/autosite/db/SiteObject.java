package com.autosite.db;

import java.sql.Timestamp;

/**
 * Site entity. @author MyEclipse Persistence Tools
 */

public class SiteObject extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private String siteUrl;
    private long accountId;
    private Timestamp createdTime;
    private String siteGroup;
    private int registered;
    private int onSale;
    private int superAdminEnable;
    private int siteRegisterEnable;
    private int subdomainEnable;
    private String siteRegisterSite;
    private long baseSiteId;
    private int subsite;
    private int disabled;

    // Constructors

    /** default constructor */
    public SiteObject() {
    }

    /** minimal constructor */
    public SiteObject(String siteUrl, long accountId, Timestamp createdTime) {
        this.siteUrl = siteUrl;
        this.accountId = accountId;
        this.createdTime = createdTime;
    }

    /** full constructor */
    public SiteObject(String siteUrl, long accountId, Timestamp createdTime, String siteGroup, int registered, int onSale, int superAdminEnable, int siteRegisterEnable, int subdomainEnable,
            String siteRegisterSite, long baseSiteId, int subsite, int disabled) {
        this.siteUrl = siteUrl;
        this.accountId = accountId;
        this.createdTime = createdTime;
        this.siteGroup = siteGroup;
        this.registered = registered;
        this.onSale = onSale;
        this.superAdminEnable = superAdminEnable;
        this.siteRegisterEnable = siteRegisterEnable;
        this.subdomainEnable = subdomainEnable;
        this.siteRegisterSite = siteRegisterSite;
        this.baseSiteId = baseSiteId;
        this.subsite = subsite;
        this.disabled = disabled;
    }

    // Property accessors

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSiteUrl() {
        return this.siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Timestamp getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getSiteGroup() {
        return this.siteGroup;
    }

    public void setSiteGroup(String siteGroup) {
        this.siteGroup = siteGroup;
    }

    public int getRegistered() {
        return this.registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    public int getOnSale() {
        return this.onSale;
    }

    public void setOnSale(int onSale) {
        this.onSale = onSale;
    }

    public int getSuperAdminEnable() {
        return this.superAdminEnable;
    }

    public void setSuperAdminEnable(int superAdminEnable) {
        this.superAdminEnable = superAdminEnable;
    }

    public int getSiteRegisterEnable() {
        return this.siteRegisterEnable;
    }

    public void setSiteRegisterEnable(int siteRegisterEnable) {
        this.siteRegisterEnable = siteRegisterEnable;
    }

    public int getSubdomainEnable() {
        return this.subdomainEnable;
    }

    public void setSubdomainEnable(int subdomainEnable) {
        this.subdomainEnable = subdomainEnable;
    }

    public String getSiteRegisterSite() {
        return this.siteRegisterSite;
    }

    public void setSiteRegisterSite(String siteRegisterSite) {
        this.siteRegisterSite = siteRegisterSite;
    }

    public long getBaseSiteId() {
        return this.baseSiteId;
    }

    public void setBaseSiteId(long baseSiteId) {
        this.baseSiteId = baseSiteId;
    }

    public int getSubsite() {
        return this.subsite;
    }

    public void setSubsite(int subsite) {
        this.subsite = subsite;
    }

    public int getDisabled() {
        return this.disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

}
