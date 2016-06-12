package com.seox.db;

import java.util.Date;


/**
 * DevNote generated by MyEclipse - Hibernate Tools
 */

public class DevNote  implements java.io.Serializable {


    // Fields    

     private long devNoteId;
     private String devNoteStr;
     private Date devNoteEnteredT;


    // Constructors

    /** default constructor */
    public DevNote() {
    }

    
    /** full constructor */
    public DevNote(String devNoteStr, Date devNoteEnteredT) {
        this.devNoteStr = devNoteStr;
        this.devNoteEnteredT = devNoteEnteredT;
    }

   
    // Property accessors

    public long getDevNoteId() {
        return this.devNoteId;
    }
    
    public void setDevNoteId(long devNoteId) {
        this.devNoteId = devNoteId;
    }

    public String getDevNoteStr() {
        return this.devNoteStr;
    }
    
    public void setDevNoteStr(String devNoteStr) {
        this.devNoteStr = devNoteStr;
    }

    public Date getDevNoteEnteredT() {
        return this.devNoteEnteredT;
    }
    
    public void setDevNoteEnteredT(Date devNoteEnteredT) {
        this.devNoteEnteredT = devNoteEnteredT;
    }
   








}