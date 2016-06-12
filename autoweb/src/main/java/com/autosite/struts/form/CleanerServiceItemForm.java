package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerServiceItemForm extends ActionForm {

	private String _ServiceId;             
	private String _ServiceItemId;             
	private String _ItemType;             
	private String _Title;             
	private String _ImagePath;             
	private String _ImagePathLocal;             
	private String _BasePrice;             
	private String _Note;             

    public String getServiceId() {
        return _ServiceId;
    }

    public void setServiceId(String ServiceId) {
        this._ServiceId = ServiceId;
    }    

    public String getServiceItemId() {
        return _ServiceItemId;
    }

    public void setServiceItemId(String ServiceItemId) {
        this._ServiceItemId = ServiceItemId;
    }    

    public String getItemType() {
        return _ItemType;
    }

    public void setItemType(String ItemType) {
        this._ItemType = ItemType;
    }    

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String Title) {
        this._Title = Title;
    }    

    public String getImagePath() {
        return _ImagePath;
    }

    public void setImagePath(String ImagePath) {
        this._ImagePath = ImagePath;
    }    

    public String getImagePathLocal() {
        return _ImagePathLocal;
    }

    public void setImagePathLocal(String ImagePathLocal) {
        this._ImagePathLocal = ImagePathLocal;
    }    

    public String getBasePrice() {
        return _BasePrice;
    }

    public void setBasePrice(String BasePrice) {
        this._BasePrice = BasePrice;
    }    

    public String getNote() {
        return _Note;
    }

    public void setNote(String Note) {
        this._Note = Note;
    }    

}