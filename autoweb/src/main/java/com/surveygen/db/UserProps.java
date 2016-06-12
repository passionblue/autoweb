package com.surveygen.db;

/**
 * UserProps entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UserProps implements java.io.Serializable {

    // Fields

    private long userPropsId;
    private long userId;
    private String propKey;
    private String propValue;

    // Constructors

    /** default constructor */
    public UserProps() {
    }

    /** minimal constructor */
    public UserProps(long userId, String propKey) {
        this.userId = userId;
        this.propKey = propKey;
    }

    /** full constructor */
    public UserProps(long userId, String propKey, String propValue) {
        this.userId = userId;
        this.propKey = propKey;
        this.propValue = propValue;
    }

    // Property accessors

    public long getUserPropsId() {
        return this.userPropsId;
    }

    public void setUserPropsId(long userPropsId) {
        this.userPropsId = userPropsId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPropKey() {
        return this.propKey;
    }

    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    public String getPropValue() {
        return this.propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

}
