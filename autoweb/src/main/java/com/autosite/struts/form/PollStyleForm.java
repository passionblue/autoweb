package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PollStyleForm extends ActionForm {

	private String _UserId;             
	private String _PollId;             
	private String _Color;             
	private String _Background;             
	private String _Border;             
	private String _Font;             
	private String _Margin;             
	private String _Padding;             
	private String _Floating;             
	private String _TextAlign;             
	private String _TextIndent;             
	private String _Height;             
	private String _Width;             
	private String _Extra;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getPollId() {
        return _PollId;
    }

    public void setPollId(String PollId) {
        this._PollId = PollId;
    }    

    public String getColor() {
        return _Color;
    }

    public void setColor(String Color) {
        this._Color = Color;
    }    

    public String getBackground() {
        return _Background;
    }

    public void setBackground(String Background) {
        this._Background = Background;
    }    

    public String getBorder() {
        return _Border;
    }

    public void setBorder(String Border) {
        this._Border = Border;
    }    

    public String getFont() {
        return _Font;
    }

    public void setFont(String Font) {
        this._Font = Font;
    }    

    public String getMargin() {
        return _Margin;
    }

    public void setMargin(String Margin) {
        this._Margin = Margin;
    }    

    public String getPadding() {
        return _Padding;
    }

    public void setPadding(String Padding) {
        this._Padding = Padding;
    }    

    public String getFloating() {
        return _Floating;
    }

    public void setFloating(String Floating) {
        this._Floating = Floating;
    }    

    public String getTextAlign() {
        return _TextAlign;
    }

    public void setTextAlign(String TextAlign) {
        this._TextAlign = TextAlign;
    }    

    public String getTextIndent() {
        return _TextIndent;
    }

    public void setTextIndent(String TextIndent) {
        this._TextIndent = TextIndent;
    }    

    public String getHeight() {
        return _Height;
    }

    public void setHeight(String Height) {
        this._Height = Height;
    }    

    public String getWidth() {
        return _Width;
    }

    public void setWidth(String Width) {
        this._Width = Width;
    }    

    public String getExtra() {
        return _Extra;
    }

    public void setExtra(String Extra) {
        this._Extra = Extra;
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