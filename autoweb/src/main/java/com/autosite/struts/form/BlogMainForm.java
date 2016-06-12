package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BlogMainForm extends ActionForm {

	private String _UserId;             
	private String _BlogName;             
	private String _BlogTitle;             
	private String _TimeCreated;             
	private String _MainDesignSelect;             
	private String _UseCustomDesign;             
	private String _CustomDesignFile;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getBlogName() {
        return _BlogName;
    }

    public void setBlogName(String BlogName) {
        this._BlogName = BlogName;
    }    

    public String getBlogTitle() {
        return _BlogTitle;
    }

    public void setBlogTitle(String BlogTitle) {
        this._BlogTitle = BlogTitle;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getMainDesignSelect() {
        return _MainDesignSelect;
    }

    public void setMainDesignSelect(String MainDesignSelect) {
        this._MainDesignSelect = MainDesignSelect;
    }    

    public String getUseCustomDesign() {
        return _UseCustomDesign;
    }

    public void setUseCustomDesign(String UseCustomDesign) {
        this._UseCustomDesign = UseCustomDesign;
    }    

    public String getCustomDesignFile() {
        return _CustomDesignFile;
    }

    public void setCustomDesignFile(String CustomDesignFile) {
        this._CustomDesignFile = CustomDesignFile;
    }    

}