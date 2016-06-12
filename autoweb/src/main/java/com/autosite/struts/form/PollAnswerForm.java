package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PollAnswerForm extends ActionForm {

	private String _PollId;             
	private String _AnswerNum;             
	private String _Text;             
	private String _ImageUrl;             
	private String _ImageOnly;             

    public String getPollId() {
        return _PollId;
    }

    public void setPollId(String PollId) {
        this._PollId = PollId;
    }    

    public String getAnswerNum() {
        return _AnswerNum;
    }

    public void setAnswerNum(String AnswerNum) {
        this._AnswerNum = AnswerNum;
    }    

    public String getText() {
        return _Text;
    }

    public void setText(String Text) {
        this._Text = Text;
    }    

    public String getImageUrl() {
        return _ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this._ImageUrl = ImageUrl;
    }    

    public String getImageOnly() {
        return _ImageOnly;
    }

    public void setImageOnly(String ImageOnly) {
        this._ImageOnly = ImageOnly;
    }    

}