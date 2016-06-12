package com.autosite.db;

import java.sql.Timestamp;

/**
 * RequestHistory entity. @author MyEclipse Persistence Tools
 */

public class RequestHistory extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long forwardSiteId;
    private int isDropped;
    private int isPageless;
    private int isLogin;
    private int isAjax;
    private int isRobot;
    private String userid;
    private String userAgent;
    private String refer;
    private String robot;
    private String remoteIp;
    private String siteUrl;
    private String uri;
    private String query;
    private String rpci;
    private String sessionId;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public RequestHistory() {
    }

    /** minimal constructor */
    public RequestHistory(long siteId, Timestamp timeCreated) {
        this.siteId = siteId;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public RequestHistory(long siteId, long forwardSiteId, int isDropped, int isPageless, int isLogin, int isAjax, int isRobot, String userid, String userAgent, String refer, String robot,
            String remoteIp, String siteUrl, String uri, String query, String rpci, String sessionId, Timestamp timeCreated) {
        this.siteId = siteId;
        this.forwardSiteId = forwardSiteId;
        this.isDropped = isDropped;
        this.isPageless = isPageless;
        this.isLogin = isLogin;
        this.isAjax = isAjax;
        this.isRobot = isRobot;
        this.userid = userid;
        this.userAgent = userAgent;
        this.refer = refer;
        this.robot = robot;
        this.remoteIp = remoteIp;
        this.siteUrl = siteUrl;
        this.uri = uri;
        this.query = query;
        this.rpci = rpci;
        this.sessionId = sessionId;
        this.timeCreated = timeCreated;
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

    public long getForwardSiteId() {
        return this.forwardSiteId;
    }

    public void setForwardSiteId(long forwardSiteId) {
        this.forwardSiteId = forwardSiteId;
    }

    public int getIsDropped() {
        return this.isDropped;
    }

    public void setIsDropped(int isDropped) {
        this.isDropped = isDropped;
    }

    public int getIsPageless() {
        return this.isPageless;
    }

    public void setIsPageless(int isPageless) {
        this.isPageless = isPageless;
    }

    public int getIsLogin() {
        return this.isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    public int getIsAjax() {
        return this.isAjax;
    }

    public void setIsAjax(int isAjax) {
        this.isAjax = isAjax;
    }

    public int getIsRobot() {
        return this.isRobot;
    }

    public void setIsRobot(int isRobot) {
        this.isRobot = isRobot;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRefer() {
        return this.refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getRobot() {
        return this.robot;
    }

    public void setRobot(String robot) {
        this.robot = robot;
    }

    public String getRemoteIp() {
        return this.remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getSiteUrl() {
        return this.siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getRpci() {
        return this.rpci;
    }

    public void setRpci(String rpci) {
        this.rpci = rpci;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

}
