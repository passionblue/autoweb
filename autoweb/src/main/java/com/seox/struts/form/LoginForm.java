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
 * @struts.form name="loginform"
 */
public class LoginForm extends ActionForm {
    /*
     * Generated fields
     */

    /** password property */
    private String password;

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
