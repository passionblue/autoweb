package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcUserProductReviewForm extends ActionForm {

	private String _ProductId;             
	private String _UserId;             
	private String _Rate;             
	private String _Review;             
	private String _TrackBack;             
	private String _NumVoteYes;             
	private String _NumVoteNo;             
	private String _TimeCreated;             

    public String getProductId() {
        return _ProductId;
    }

    public void setProductId(String ProductId) {
        this._ProductId = ProductId;
    }    

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getRate() {
        return _Rate;
    }

    public void setRate(String Rate) {
        this._Rate = Rate;
    }    

    public String getReview() {
        return _Review;
    }

    public void setReview(String Review) {
        this._Review = Review;
    }    

    public String getTrackBack() {
        return _TrackBack;
    }

    public void setTrackBack(String TrackBack) {
        this._TrackBack = TrackBack;
    }    

    public String getNumVoteYes() {
        return _NumVoteYes;
    }

    public void setNumVoteYes(String NumVoteYes) {
        this._NumVoteYes = NumVoteYes;
    }    

    public String getNumVoteNo() {
        return _NumVoteNo;
    }

    public void setNumVoteNo(String NumVoteNo) {
        this._NumVoteNo = NumVoteNo;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}