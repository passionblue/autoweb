package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class VelocityMainForm extends ActionForm {

    private String _Data;             
    private String _Data2;             
    private String _Title;             
    private String _Age;             

    public String getData() {
        return _Data;
    }

    public void setData(String Data) {
        this._Data = Data;
    }    

    public String getData2() {
        return _Data2;
    }

    public void setData2(String Data2) {
        this._Data2 = Data2;
    }    

    public String getTitle() {
        return _Title;
    }

    public void setTitle(String Title) {
        this._Title = Title;
    }    

    public String getAge() {
        return _Age;
    }

    public void setAge(String Age) {
        this._Age = Age;
    }    

}