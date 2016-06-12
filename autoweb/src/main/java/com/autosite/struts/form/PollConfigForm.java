package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PollConfigForm extends ActionForm {

	private String _PollId;             
	private String _ImageThumbHeight;             
	private String _ImageThumbWidth;             
	private String _ImageAlignVertical;             
	private String _Background;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getPollId() {
        return _PollId;
    }

    public void setPollId(String PollId) {
        this._PollId = PollId;
    }    

    public String getImageThumbHeight() {
        return _ImageThumbHeight;
    }

    public void setImageThumbHeight(String ImageThumbHeight) {
        this._ImageThumbHeight = ImageThumbHeight;
    }    

    public String getImageThumbWidth() {
        return _ImageThumbWidth;
    }

    public void setImageThumbWidth(String ImageThumbWidth) {
        this._ImageThumbWidth = ImageThumbWidth;
    }    

    public String getImageAlignVertical() {
        return _ImageAlignVertical;
    }

    public void setImageAlignVertical(String ImageAlignVertical) {
        this._ImageAlignVertical = ImageAlignVertical;
    }    

    public String getBackground() {
        return _Background;
    }

    public void setBackground(String Background) {
        this._Background = Background;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getTimeUpdated() {
        return _TimeUpdated;
    }

    public void setTimeUpdated(String TimeUpdated) {
        this._TimeUpdated = TimeUpdated;
    }    

}