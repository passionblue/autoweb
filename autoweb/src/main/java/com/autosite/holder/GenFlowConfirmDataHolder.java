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



public class GenFlowConfirmDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields
	private int m_agree;

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("agree", new Integer(0));
    }



	private long m_id = 0;
	private long m_siteId = 0;

	public GenFlowConfirmDataHolder(){
	}

	public long getId(){
		return m_id;
	}
	public void setId(long id){
		m_id = id;
	}
	public long getSiteId(){
		return m_siteId;
	}
	public void setSiteId(long sid){
		m_siteId = sid;
	}

	public  BaseAutositeDataObject getDataObject(){
		return null;
	}


	public int getAgree(){
		return m_agree;
	}

	public void setAgree(int _agree ){
		m_agree = _agree;
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

        if ( colNum == 0) return String.valueOf(getAgree());
		return "";
    }
}