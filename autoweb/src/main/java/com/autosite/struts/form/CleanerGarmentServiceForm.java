package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerGarmentServiceForm extends ActionForm {

	private String _Title;             
	private String _Note;             
	private String _ImagePath;             

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String Title) {
        this._Title = Title;
    }    

    public String getNote() {
        return _Note;
    }

    public void setNote(String Note) {
        this._Note = Note;
    }    

    public String getImagePath() {
        return _ImagePath;
    }

    public void setImagePath(String ImagePath) {
        this._ImagePath = ImagePath;
    }    

}