package com.autosite.db;

import java.sql.Timestamp;

/**
 * AutositeUser entity. @author MyEclipse Persistence Tools
 */

public class AutositeUser extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String username;
    private String password;
    private String email;
    private int userType;
    private String firstName;
    private String lastName;
    private String nickname;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;
    private int disabled;
    private Timestamp timeDisabled;
    private int confirmed;
    private Timestamp timeConfirmed;
    private int pagelessSession;
    private int opt1;
    private String opt2;

    // Constructors

    /** default constructor */
    public AutositeUser() {
    }

    /** minimal constructor */
    public AutositeUser(long siteId, String username, String password, String email, Timestamp timeCreated, Timestamp timeUpdated, int pagelessSession) {
        this.siteId = siteId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.pagelessSession = pagelessSession;
    }

    /** full constructor */
    public AutositeUser(long siteId, String username, String password, String email, int userType, String firstName, String lastName, String nickname, Timestamp timeCreated, Timestamp timeUpdated,
            int disabled, Timestamp timeDisabled, int confirmed, Timestamp timeConfirmed, int pagelessSession, int opt1, String opt2) {
        this.siteId = siteId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.disabled = disabled;
        this.timeDisabled = timeDisabled;
        this.confirmed = confirmed;
        this.timeConfirmed = timeConfirmed;
        this.pagelessSession = pagelessSession;
        this.opt1 = opt1;
        this.opt2 = opt2;
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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserType() {
        return this.userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public int getDisabled() {
        return this.disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public Timestamp getTimeDisabled() {
        return this.timeDisabled;
    }

    public void setTimeDisabled(Timestamp timeDisabled) {
        this.timeDisabled = timeDisabled;
    }

    public int getConfirmed() {
        return this.confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public Timestamp getTimeConfirmed() {
        return this.timeConfirmed;
    }

    public void setTimeConfirmed(Timestamp timeConfirmed) {
        this.timeConfirmed = timeConfirmed;
    }

    public int getPagelessSession() {
        return this.pagelessSession;
    }

    public void setPagelessSession(int pagelessSession) {
        this.pagelessSession = pagelessSession;
    }

    public int getOpt1() {
        return this.opt1;
    }

    public void setOpt1(int opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return this.opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

}
