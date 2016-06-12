package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcDisplayConfigForm extends ActionForm {

	private String _ColumnCount;             

    public String getColumnCount() {
        return _ColumnCount;
    }

    public void setColumnCount(String ColumnCount) {
        this._ColumnCount = ColumnCount;
    }    

}