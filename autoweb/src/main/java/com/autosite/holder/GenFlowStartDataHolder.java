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



public class GenFlowStartDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields
	private String m_yourName;
	private String m_email;

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("yourName", new Integer(0));
        fieldsMap.put("email", new Integer(1));
    }



	private long m_id = 0;
	private long m_siteId = 0;

	public GenFlowStartDataHolder(){
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


	public String getYourName(){
		return m_yourName;
	}
	public String getEmail(){
		return m_email;
	}

	public void setYourName(String _yourName ){
		m_yourName = _yourName;
	}
	public void setEmail(String _email ){
		m_email = _email;
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

        if ( colNum == 0) return getYourName() == null?"":getYourName().toString();
        if ( colNum == 1) return getEmail() == null?"":getEmail().toString();
		return "";
    }
}