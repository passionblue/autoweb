package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerProductForm extends ActionForm {

	private String _GarmentTypeId;             
	private String _GarmentServiceId;             
	private String _RegularPrice;             
	private String _Note;             

    public String getGarmentTypeId() {
        return _GarmentTypeId;
    }

    public void setGarmentTypeId(String GarmentTypeId) {
        this._GarmentTypeId = GarmentTypeId;
    }    

    public String getGarmentServiceId() {
        return _GarmentServiceId;
    }

    public void setGarmentServiceId(String GarmentServiceId) {
        this._GarmentServiceId = GarmentServiceId;
    }    

    public String getRegularPrice() {
        return _RegularPrice;
    }

    public void setRegularPrice(String RegularPrice) {
        this._RegularPrice = RegularPrice;
    }    

    public String getNote() {
        return _Note;
    }

    public void setNote(String Note) {
        this._Note = Note;
    }    

}