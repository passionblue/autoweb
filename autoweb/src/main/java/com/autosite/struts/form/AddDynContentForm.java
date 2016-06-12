/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.autosite.struts.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 03-24-2008
 * 
 * XDoclet definition:
 * @struts.form name="addDynContentForm"
 */
public class AddDynContentForm extends ActionForm {
    /*
     * Generated fields
     */

    /** title property */
    private String title;

    /** content property */
    private String contentAbstract;
    private String content;
    private String panelId;
    private String cat;
    private String categoryId;
    private String sourceName;
    private String sourceUrl;
    private String contentType;
    private String imageUrl;
    private String imageHeight;
    private String imageWidth;
    private String inHtml;
    private String tags;
    private String extraKeywords;
    private String shortcutUrl;
    private String columnNum;
    
    private String abstractGenerate;
    private String abstractNo;
    
    
    
    
    /*
     * Generated Methods
     */

    public String getAbstractGenerate() {
        return abstractGenerate;
    }

    public void setAbstractGenerate(String abstractGenerate) {
        this.abstractGenerate = abstractGenerate;
    }

    public String getAbstractNo() {
        return abstractNo;
    }

    public void setAbstractNo(String abstractNo) {
        this.abstractNo = abstractNo;
    }

    public String getContentAbstract() {
        return contentAbstract;
    }

    public void setContentAbstract(String contentAbstract) {
        this.contentAbstract = contentAbstract;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public String getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(String columnNum) {
        this.columnNum = columnNum;
    }

    public String getInHtml() {
        return inHtml;
    }

    public void setInHtml(String inHtml) {
        this.inHtml = inHtml;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getExtraKeywords() {
        return extraKeywords;
    }

    public void setExtraKeywords(String extraKeywords) {
        this.extraKeywords = extraKeywords;
    }

    public String getShortcutUrl() {
        return shortcutUrl;
    }

    public void setShortcutUrl(String shortcutUrl) {
        this.shortcutUrl = shortcutUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

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
     * Returns the title.
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /** 
     * Set the title.
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** 
     * Returns the content.
     * @return String
     */
    public String getContent() {
        return content;
    }

    /** 
     * Set the content.
     * @param content The content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
}
