package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class StyleSetContentForm extends ActionForm {

	private String _Name;             
	private String _IdPrefix;             
	private String _ListFrameStyleId;             
	private String _ListSubjectStyleId;             
	private String _ListDataStyleId;             
	private String _FrameStyleId;             
	private String _SubjectStyleId;             
	private String _DataStyleId;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getName() {
        return _Name;
    }

    public void setName(String Name) {
        this._Name = Name;
    }    

    public String getIdPrefix() {
        return _IdPrefix;
    }

    public void setIdPrefix(String IdPrefix) {
        this._IdPrefix = IdPrefix;
    }    

    public String getListFrameStyleId() {
        return _ListFrameStyleId;
    }

    public void setListFrameStyleId(String ListFrameStyleId) {
        this._ListFrameStyleId = ListFrameStyleId;
    }    

    public String getListSubjectStyleId() {
        return _ListSubjectStyleId;
    }

    public void setListSubjectStyleId(String ListSubjectStyleId) {
        this._ListSubjectStyleId = ListSubjectStyleId;
    }    

    public String getListDataStyleId() {
        return _ListDataStyleId;
    }

    public void setListDataStyleId(String ListDataStyleId) {
        this._ListDataStyleId = ListDataStyleId;
    }    

    public String getFrameStyleId() {
        return _FrameStyleId;
    }

    public void setFrameStyleId(String FrameStyleId) {
        this._FrameStyleId = FrameStyleId;
    }    

    public String getSubjectStyleId() {
        return _SubjectStyleId;
    }

    public void setSubjectStyleId(String SubjectStyleId) {
        this._SubjectStyleId = SubjectStyleId;
    }    

    public String getDataStyleId() {
        return _DataStyleId;
    }

    public void setDataStyleId(String DataStyleId) {
        this._DataStyleId = DataStyleId;
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

}