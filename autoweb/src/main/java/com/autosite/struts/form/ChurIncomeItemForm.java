package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChurIncomeItemForm extends ActionForm {

	private String _CategoryId;             
	private String _IncomeItem;             
	private String _Display;             

    public String getCategoryId() {
        return _CategoryId;
    }

    public void setCategoryId(String CategoryId) {
        this._CategoryId = CategoryId;
    }    

    public String getIncomeItem() {
        return _IncomeItem;
    }

    public void setIncomeItem(String IncomeItem) {
        this._IncomeItem = IncomeItem;
    }    

    public String getDisplay() {
        return _Display;
    }

    public void setDisplay(String Display) {
        this._Display = Display;
    }    

}