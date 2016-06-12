package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcWishListForm extends ActionForm {

	private String _UserId;             
	private String _ProductId;             
	private String _SizeVariation;             
	private String _ColorVariation;             
	private String _SavedPrice;             
	private String _TimeCreated;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getProductId() {
        return _ProductId;
    }

    public void setProductId(String ProductId) {
        this._ProductId = ProductId;
    }    

    public String getSizeVariation() {
        return _SizeVariation;
    }

    public void setSizeVariation(String SizeVariation) {
        this._SizeVariation = SizeVariation;
    }    

    public String getColorVariation() {
        return _ColorVariation;
    }

    public void setColorVariation(String ColorVariation) {
        this._ColorVariation = ColorVariation;
    }    

    public String getSavedPrice() {
        return _SavedPrice;
    }

    public void setSavedPrice(String SavedPrice) {
        this._SavedPrice = SavedPrice;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}