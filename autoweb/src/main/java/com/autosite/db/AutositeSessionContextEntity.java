package com.autosite.db;

import java.sql.Timestamp;

/**
 * AutositeSessionContextEntity entity. @author MyEclipse Persistence Tools
 */

public class AutositeSessionContextEntity extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String serial;
    private int isLogin;
    private Timestamp timeLogin;
    private Timestamp timeLastAccess;
    private long loginUserId;
    private int sessionType;
    private long remoteDeviceId;
    private String remoteIp;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public AutositeSessionContextEntity() {
    }

    /** minimal constructor */
    public AutositeSessionContextEntity(long siteId, String serial, long remoteDeviceId, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.serial = serial;
        this.remoteDeviceId = remoteDeviceId;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public AutositeSessionContextEntity(long siteId, String serial, int isLogin, Timestamp timeLogin, Timestamp timeLastAccess, long loginUserId, int sessionType, long remoteDeviceId,
            String remoteIp, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.serial = serial;
        this.isLogin = isLogin;
        this.timeLogin = timeLogin;
        this.timeLastAccess = timeLastAccess;
        this.loginUserId = loginUserId;
        this.sessionType = sessionType;
        this.remoteDeviceId = remoteDeviceId;
        this.remoteIp = remoteIp;
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

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public int getIsLogin() {
        return this.isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    public Timestamp getTimeLogin() {
        return this.timeLogin;
    }

    public void setTimeLogin(Timestamp timeLogin) {
        this.timeLogin = timeLogin;
    }

    public Timestamp getTimeLastAccess() {
        return this.timeLastAccess;
    }

    public void setTimeLastAccess(Timestamp timeLastAccess) {
        this.timeLastAccess = timeLastAccess;
    }

    public long getLoginUserId() {
        return this.loginUserId;
    }

    public void setLoginUserId(long loginUserId) {
        this.loginUserId = loginUserId;
    }

    public int getSessionType() {
        return this.sessionType;
    }

    public void setSessionType(int sessionType) {
        this.sessionType = sessionType;
    }

    public long getRemoteDeviceId() {
        return this.remoteDeviceId;
    }

    public void setRemoteDeviceId(long remoteDeviceId) {
        this.remoteDeviceId = remoteDeviceId;
    }

    public String getRemoteIp() {
        return this.remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
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
