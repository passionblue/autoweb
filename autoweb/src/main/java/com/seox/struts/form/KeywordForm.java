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
 * Creation date: 11-15-2006
 * 
 * XDoclet definition:
 * @struts.form name="keywordform"
 */
public class KeywordForm extends ActionForm {
    /*
     * Generated fields
     */

    /** cmd property */
    private String cmd;

    /** keyword property */
    private String keyword;

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
     * Returns the cmd.
     * @return String
     */
    public String getCmd() {
        return cmd;
    }

    /** 
     * Set the cmd.
     * @param cmd The cmd to set
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /** 
     * Returns the keyword.
     * @return String
     */
    public String getKeyword() {
        return keyword;
    }

    /** 
     * Set the keyword.
     * @param keyword The keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}