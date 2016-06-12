package com.autosite.holder;

import java.sql.Timestamp;
import java.sql.Date;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.util.html.HtmlGenRow;
import java.util.ArrayList;
import java.util.List;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.autosite.db.ChurExpense;


public class ChurExpenseDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("year", new Integer(0));
        fieldsMap.put("week", new Integer(1));
        fieldsMap.put("expenseItemId", new Integer(2));
        fieldsMap.put("payeeId", new Integer(3));
        fieldsMap.put("amount", new Integer(4));
        fieldsMap.put("isCash", new Integer(5));
        fieldsMap.put("checkNumber", new Integer(6));
        fieldsMap.put("checkCleared", new Integer(7));
        fieldsMap.put("comment", new Integer(8));
        fieldsMap.put("cancelled", new Integer(9));
        fieldsMap.put("timeCreated", new Integer(10));
        fieldsMap.put("timeUpdated", new Integer(11));
    }


	ChurExpense m_ChurExpense;

	public ChurExpenseDataHolder(ChurExpense _ChurExpense){
		m_ChurExpense =  _ChurExpense; 	
	}
	public  BaseAutositeDataObject getDataObject(){
		return 	m_ChurExpense;
	}

	public ChurExpenseDataHolder(){
		m_ChurExpense =  new ChurExpense(); 	
	}

	public long getId(){
		return 	m_ChurExpense.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_ChurExpense.getSiteId();
	}
	public void setSiteId(long sid){
		m_ChurExpense.setSiteId(sid);
	}


	public int getYear(){
		return m_ChurExpense.getYear();
	}
	public String getWeek(){
		return m_ChurExpense.getWeek();
	}
	public long getExpenseItemId(){
		return m_ChurExpense.getExpenseItemId();
	}
	public long getPayeeId(){
		return m_ChurExpense.getPayeeId();
	}
	public double getAmount(){
		return m_ChurExpense.getAmount();
	}
	public int getIsCash(){
		return m_ChurExpense.getIsCash();
	}
	public String getCheckNumber(){
		return m_ChurExpense.getCheckNumber();
	}
	public int getCheckCleared(){
		return m_ChurExpense.getCheckCleared();
	}
	public String getComment(){
		return m_ChurExpense.getComment();
	}
	public int getCancelled(){
		return m_ChurExpense.getCancelled();
	}
	public Timestamp getTimeCreated(){
		return m_ChurExpense.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_ChurExpense.getTimeUpdated();
	}

	public void setYear(int _year ){
		m_ChurExpense.setYear(_year);
	}
	public void setWeek(String _week ){
		m_ChurExpense.setWeek(_week);
	}
	public void setExpenseItemId(long _expenseItemId ){
		m_ChurExpense.setExpenseItemId(_expenseItemId);
	}
	public void setPayeeId(long _payeeId ){
		m_ChurExpense.setPayeeId(_payeeId);
	}
	public void setAmount(double _amount ){
		m_ChurExpense.setAmount(_amount);
	}
	public void setIsCash(int _isCash ){
		m_ChurExpense.setIsCash(_isCash);
	}
	public void setCheckNumber(String _checkNumber ){
		m_ChurExpense.setCheckNumber(_checkNumber);
	}
	public void setCheckCleared(int _checkCleared ){
		m_ChurExpense.setCheckCleared(_checkCleared);
	}
	public void setComment(String _comment ){
		m_ChurExpense.setComment(_comment);
	}
	public void setCancelled(int _cancelled ){
		m_ChurExpense.setCancelled(_cancelled);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_ChurExpense.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_ChurExpense.setTimeUpdated(_timeUpdated);
	}

	// For HtmlGenRow
    public int getColumnCount() {
		return fieldsMap.size();
    }
    public static List getFieldsName(){
        return new ArrayList(fieldsMap.keySet());
    }
    public String getColumnData(String col){
        if(fieldsMap.containsKey(col)){
            return getColumnData(fieldsMap.get(col).intValue());
        } else {
            return null;
        }
    }

    public String getColumnData(int colNum) {

        if ( colNum == 0) return String.valueOf(getYear());
        if ( colNum == 1) return getWeek() == null?"":getWeek().toString();
        if ( colNum == 2) return String.valueOf(getExpenseItemId());
        if ( colNum == 3) return String.valueOf(getPayeeId());
        if ( colNum == 4) return String.valueOf(getAmount());
        if ( colNum == 5) return String.valueOf(getIsCash());
        if ( colNum == 6) return getCheckNumber() == null?"":getCheckNumber().toString();
        if ( colNum == 7) return String.valueOf(getCheckCleared());
        if ( colNum == 8) return getComment() == null?"":getComment().toString();
        if ( colNum == 9) return String.valueOf(getCancelled());
        if ( colNum == 10) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 11) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }
}