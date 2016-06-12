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

import com.autosite.db.ChurPayee;


public class ChurPayeeDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("title", new Integer(0));
        fieldsMap.put("remark", new Integer(1));
    }


	ChurPayee m_ChurPayee;

	public ChurPayeeDataHolder(ChurPayee _ChurPayee){
		m_ChurPayee =  _ChurPayee; 	
	}
	public  BaseAutositeDataObject getDataObject(){
		return 	m_ChurPayee;
	}

	public ChurPayeeDataHolder(){
		m_ChurPayee =  new ChurPayee(); 	
	}

	public long getId(){
		return 	m_ChurPayee.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_ChurPayee.getSiteId();
	}
	public void setSiteId(long sid){
		m_ChurPayee.setSiteId(sid);
	}


	public String getTitle(){
		return m_ChurPayee.getTitle();
	}
	public String getRemark(){
		return m_ChurPayee.getRemark();
	}

	public void setTitle(String _title ){
		m_ChurPayee.setTitle(_title);
	}
	public void setRemark(String _remark ){
		m_ChurPayee.setRemark(_remark);
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

        if ( colNum == 0) return getTitle() == null?"":getTitle().toString();
        if ( colNum == 1) return getRemark() == null?"":getRemark().toString();
		return "";
    }
}