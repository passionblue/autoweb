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



public class ChurIncomeAddDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields
	private long m_churMemberId;
	private String m_tithe;
	private String m_weekly;
	private String m_thanks;
	private String m_mission;
	private String m_construction;
	private String m_other;
	private String m_week;
	private String m_year;

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("churMemberId", new Integer(0));
        fieldsMap.put("tithe", new Integer(1));
        fieldsMap.put("weekly", new Integer(2));
        fieldsMap.put("thanks", new Integer(3));
        fieldsMap.put("mission", new Integer(4));
        fieldsMap.put("construction", new Integer(5));
        fieldsMap.put("other", new Integer(6));
        fieldsMap.put("week", new Integer(7));
        fieldsMap.put("year", new Integer(8));
    }



	private long m_id = 0;
	private long m_siteId = 0;

	public ChurIncomeAddDataHolder(){
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


	public long getChurMemberId(){
		return m_churMemberId;
	}
	public String getTithe(){
		return m_tithe;
	}
	public String getWeekly(){
		return m_weekly;
	}
	public String getThanks(){
		return m_thanks;
	}
	public String getMission(){
		return m_mission;
	}
	public String getConstruction(){
		return m_construction;
	}
	public String getOther(){
		return m_other;
	}
	public String getWeek(){
		return m_week;
	}
	public String getYear(){
		return m_year;
	}

	public void setChurMemberId(long _churMemberId ){
		m_churMemberId = _churMemberId;
	}
	public void setTithe(String _tithe ){
		m_tithe = _tithe;
	}
	public void setWeekly(String _weekly ){
		m_weekly = _weekly;
	}
	public void setThanks(String _thanks ){
		m_thanks = _thanks;
	}
	public void setMission(String _mission ){
		m_mission = _mission;
	}
	public void setConstruction(String _construction ){
		m_construction = _construction;
	}
	public void setOther(String _other ){
		m_other = _other;
	}
	public void setWeek(String _week ){
		m_week = _week;
	}
	public void setYear(String _year ){
		m_year = _year;
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

        if ( colNum == 0) return String.valueOf(getChurMemberId());
        if ( colNum == 1) return getTithe() == null?"":getTithe().toString();
        if ( colNum == 2) return getWeekly() == null?"":getWeekly().toString();
        if ( colNum == 3) return getThanks() == null?"":getThanks().toString();
        if ( colNum == 4) return getMission() == null?"":getMission().toString();
        if ( colNum == 5) return getConstruction() == null?"":getConstruction().toString();
        if ( colNum == 6) return getOther() == null?"":getOther().toString();
        if ( colNum == 7) return getWeek() == null?"":getWeek().toString();
        if ( colNum == 8) return getYear() == null?"":getYear().toString();
		return "";
    }
}