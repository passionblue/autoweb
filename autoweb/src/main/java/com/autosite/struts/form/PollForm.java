package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PollForm extends ActionForm {

	private String _UserId;             
	private String _Serial;             
	private String _Type;             
	private String _Category;             
	private String _Title;             
	private String _Description;             
	private String _Question;             
	private String _Tags;             
	private String _Published;             
	private String _Hide;             
	private String _Disable;             
	private String _AllowMultiple;             
	private String _AllowOwnAnswer;             
	private String _RandomAnswer;             
	private String _HideComments;             
	private String _HideResults;             
	private String _HideHomeLink;             
	private String _ShowSponsor;             
	private String _UseCookieForDup;             
	private String _RepeatEveryDay;             
	private String _MaxRepeatVote;             
	private String _NumDaysOpen;             
	private String _TimeCreated;             
	private String _TimeUpdated;             
	private String _TimeExpired;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getSerial() {
        return _Serial;
    }

    public void setSerial(String Serial) {
        this._Serial = Serial;
    }    

    public String getType() {
        return _Type;
    }

    public void setType(String Type) {
        this._Type = Type;
    }    

    public String getCategory() {
        return _Category;
    }

    public void setCategory(String Category) {
        this._Category = Category;
    }    

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String Title) {
        this._Title = Title;
    }    

    public String getDescription() {
        return _Description;
    }

    public void setDescription(String Description) {
        this._Description = Description;
    }    

    public String getQuestion() {
        return _Question;
    }

    public void setQuestion(String Question) {
        this._Question = Question;
    }    

    public String getTags() {
        return _Tags;
    }

    public void setTags(String Tags) {
        this._Tags = Tags;
    }    

    public String getPublished() {
        return _Published;
    }

    public void setPublished(String Published) {
        this._Published = Published;
    }    

    public String getHide() {
        return _Hide;
    }

    public void setHide(String Hide) {
        this._Hide = Hide;
    }    

    public String getDisable() {
        return _Disable;
    }

    public void setDisable(String Disable) {
        this._Disable = Disable;
    }    

    public String getAllowMultiple() {
        return _AllowMultiple;
    }

    public void setAllowMultiple(String AllowMultiple) {
        this._AllowMultiple = AllowMultiple;
    }    

    public String getAllowOwnAnswer() {
        return _AllowOwnAnswer;
    }

    public void setAllowOwnAnswer(String AllowOwnAnswer) {
        this._AllowOwnAnswer = AllowOwnAnswer;
    }    

    public String getRandomAnswer() {
        return _RandomAnswer;
    }

    public void setRandomAnswer(String RandomAnswer) {
        this._RandomAnswer = RandomAnswer;
    }    

    public String getHideComments() {
        return _HideComments;
    }

    public void setHideComments(String HideComments) {
        this._HideComments = HideComments;
    }    

    public String getHideResults() {
        return _HideResults;
    }

    public void setHideResults(String HideResults) {
        this._HideResults = HideResults;
    }    

    public String getHideHomeLink() {
        return _HideHomeLink;
    }

    public void setHideHomeLink(String HideHomeLink) {
        this._HideHomeLink = HideHomeLink;
    }    

    public String getShowSponsor() {
        return _ShowSponsor;
    }

    public void setShowSponsor(String ShowSponsor) {
        this._ShowSponsor = ShowSponsor;
    }    

    public String getUseCookieForDup() {
        return _UseCookieForDup;
    }

    public void setUseCookieForDup(String UseCookieForDup) {
        this._UseCookieForDup = UseCookieForDup;
    }    

    public String getRepeatEveryDay() {
        return _RepeatEveryDay;
    }

    public void setRepeatEveryDay(String RepeatEveryDay) {
        this._RepeatEveryDay = RepeatEveryDay;
    }    

    public String getMaxRepeatVote() {
        return _MaxRepeatVote;
    }

    public void setMaxRepeatVote(String MaxRepeatVote) {
        this._MaxRepeatVote = MaxRepeatVote;
    }    

    public String getNumDaysOpen() {
        return _NumDaysOpen;
    }

    public void setNumDaysOpen(String NumDaysOpen) {
        this._NumDaysOpen = NumDaysOpen;
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

    public String getTimeExpired() {
        return _TimeExpired;
    }

    public void setTimeExpired(String TimeExpired) {
        this._TimeExpired = TimeExpired;
    }    

}