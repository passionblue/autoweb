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
 * Creation date: 10-29-2006
 * 
 * XDoclet definition:
 * @struts.form name="keywordaddform"
 */
public class KeywordAddForm extends ActionForm {
    /*
     * Generated fields
     */

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
