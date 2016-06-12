package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DevNoteForm extends ActionForm {

	private String _NoteType;             
	private String _Completed;             
	private String _Category;             
	private String _Subject;             
	private String _Note;             
	private String _Tags;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getNoteType() {
        return _NoteType;
    }

    public void setNoteType(String NoteType) {
        this._NoteType = NoteType;
    }    

    public String getCompleted() {
        return _Completed;
    }

    public void setCompleted(String Completed) {
        this._Completed = Completed;
    }    

    public String getCategory() {
        return _Category;
    }

    public void setCategory(String Category) {
        this._Category = Category;
    }    

    public String getSubject() {
        return _Subject;
    }

    public void setSubject(String Subject) {
        this._Subject = Subject;
    }    

    public String getNote() {
        return _Note;
    }

    public void setNote(String Note) {
        this._Note = Note;
    }    

    public String getTags() {
        return _Tags;
    }

    public void setTags(String Tags) {
        this._Tags = Tags;
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