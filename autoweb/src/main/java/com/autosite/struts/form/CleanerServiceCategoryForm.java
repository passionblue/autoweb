package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerServiceCategoryForm extends ActionForm {

	private String _Title;             
	private String _ImagePath;             
	private String _ImagePathLocal;             

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

}