/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.seox.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 09-09-2006
 * 
 * XDoclet definition:
 * @struts.form name="registerform"
 */
public class RegisterForm extends ActionForm {
    /*
     * Generated fields
     */

    /** lastname property */
    private String lastname;

    /** password property */
    private String password;

    /** email_confirm property */
    private String email_confirm;

    /** firstname property */
    private String firstname;

    /** email property */
    private String email;

    /*
     * Generated Methods
     */

    /** 
     * Method validate
     * @param mapping
     * @param request
     * @return ActionErrors
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    /** 
     * Method reset
     * @param mapping
     * @param request
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        // TODO Auto-generated method stub
    }

    /** 
     * Returns the lastname.
     * @return String
     */
    public String getLastname() {
        return lastname;
    }

    /** 
     * Set the lastname.
     * @param lastname The lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /** 
     * Returns the password.
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /** 
     * Set the password.
     * @param password The password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** 
     * Returns the email_confirm.
     * @return String
     */
    public String getEmail_confirm() {
        return email_confirm;
    }

    /** 
     * Set the email_confirm.
     * @param email_confirm The email_confirm to set
     */
    public void setEmail_confirm(String email_confirm) {
        this.email_confirm = email_confirm;
    }

    /** 
     * Returns the firstname.
     * @return String
     */
    public String getFirstname() {
        return firstname;
    }

    /** 
     * Set the firstname.
     * @param firstname The firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /** 
     * Returns the email.
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /** 
     * Set the email.
     * @param email The email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}