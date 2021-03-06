package com.seox.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Keyword generated by MyEclipse - Hibernate Tools
 */

public class Keyword  implements java.io.Serializable {


    // Fields    

     private long keywordId;
     private String keyword;
     private Date enteredT;
     private int lastWeekNum;
     private int lastDayNum;
     private Set ranks = new HashSet(0);


    // Constructors

    /** default constructor */
    public Keyword() {
    }

	/** minimal constructor */
    public Keyword(long keywordId, String keyword, Date enteredT) {
        this.keywordId = keywordId;
        this.keyword = keyword;
        this.enteredT = enteredT;
    }
    
    /** full constructor */
    public Keyword(long keywordId, String keyword, Date enteredT, int lastWeekNum, int lastDayNum, Set ranks) {
        this.keywordId = keywordId;
        this.keyword = keyword;
        this.enteredT = enteredT;
        this.lastWeekNum = lastWeekNum;
        this.lastDayNum = lastDayNum;
        this.ranks = ranks;
    }

   
    // Property accessors

    public long getKeywordId() {
        return this.keywordId;
    }
    
    public void setKeywordId(long keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeyword() {
        return this.keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getEnteredT() {
        return this.enteredT;
    }
    
    public void setEnteredT(Date enteredT) {
        this.enteredT = enteredT;
    }

    public int getLastWeekNum() {
        return this.lastWeekNum;
    }
    
    public void setLastWeekNum(int lastWeekNum) {
        this.lastWeekNum = lastWeekNum;
    }

    public int getLastDayNum() {
        return this.lastDayNum;
    }
    
    public void setLastDayNum(int lastDayNum) {
        this.lastDayNum = lastDayNum;
    }

    public Set getRanks() {
        return this.ranks;
    }
    
    public void setRanks(Set ranks) {
        this.ranks = ranks;
    }
   








}