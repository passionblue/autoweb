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

import com.autosite.db.RegisterSimple;


public class RegisterSimpleDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("firstName", new Integer(0));
        fieldsMap.put("lastName", new Integer(1));
        fieldsMap.put("email", new Integer(2));
        fieldsMap.put("username", new Integer(3));
        fieldsMap.put("password", new Integer(4));
        fieldsMap.put("birthYear", new Integer(5));
        fieldsMap.put("birthMonth", new Integer(6));
        fieldsMap.put("birthDay", new Integer(7));
        fieldsMap.put("timeCreated", new Integer(8));
        fieldsMap.put("timeUpdated", new Integer(9));
    }


	RegisterSimple m_registerSimple;

	public RegisterSimpleDataHolder(RegisterSimple _RegisterSimple){
		m_registerSimple =  _RegisterSimple; 	
	}

	public RegisterSimpleDataHolder(){
		m_registerSimple =  new RegisterSimple(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_registerSimple;
	}


	public long getId(){
		return 	m_registerSimple.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_registerSimple.getSiteId();
	}
	public void setSiteId(long sid){
		m_registerSimple.setSiteId(sid);
	}


	public String getFirstName(){
		return m_registerSimple.getFirstName();
	}
	public String getLastName(){
		return m_registerSimple.getLastName();
	}
	public String getEmail(){
		return m_registerSimple.getEmail();
	}
	public String getUsername(){
		return m_registerSimple.getUsername();
	}
	public String getPassword(){
		return m_registerSimple.getPassword();
	}
	public int getBirthYear(){
		return m_registerSimple.getBirthYear();
	}
	public int getBirthMonth(){
		return m_registerSimple.getBirthMonth();
	}
	public int getBirthDay(){
		return m_registerSimple.getBirthDay();
	}
	public Timestamp getTimeCreated(){
		return m_registerSimple.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_registerSimple.getTimeUpdated();
	}

	public void setFirstName(String _firstName ){
		m_registerSimple.setFirstName(_firstName);
	}
	public void setLastName(String _lastName ){
		m_registerSimple.setLastName(_lastName);
	}
	public void setEmail(String _email ){
		m_registerSimple.setEmail(_email);
	}
	public void setUsername(String _username ){
		m_registerSimple.setUsername(_username);
	}
	public void setPassword(String _password ){
		m_registerSimple.setPassword(_password);
	}
	public void setBirthYear(int _birthYear ){
		m_registerSimple.setBirthYear(_birthYear);
	}
	public void setBirthMonth(int _birthMonth ){
		m_registerSimple.setBirthMonth(_birthMonth);
	}
	public void setBirthDay(int _birthDay ){
		m_registerSimple.setBirthDay(_birthDay);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_registerSimple.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_registerSimple.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return getFirstName() == null?"":getFirstName().toString();
        if ( colNum == 1) return getLastName() == null?"":getLastName().toString();
        if ( colNum == 2) return getEmail() == null?"":getEmail().toString();
        if ( colNum == 3) return getUsername() == null?"":getUsername().toString();
        if ( colNum == 4) return getPassword() == null?"":getPassword().toString();
        if ( colNum == 5) return String.valueOf(getBirthYear());
        if ( colNum == 6) return String.valueOf(getBirthMonth());
        if ( colNum == 7) return String.valueOf(getBirthDay());
        if ( colNum == 8) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 9) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }
}