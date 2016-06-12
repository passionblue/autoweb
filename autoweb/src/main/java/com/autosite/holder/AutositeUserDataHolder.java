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

import com.autosite.db.AutositeUser;


public class AutositeUserDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("username", new Integer(0));
        fieldsMap.put("password", new Integer(1));
        fieldsMap.put("email", new Integer(2));
        fieldsMap.put("userType", new Integer(3));
        fieldsMap.put("firstName", new Integer(4));
        fieldsMap.put("lastName", new Integer(5));
        fieldsMap.put("nickname", new Integer(6));
        fieldsMap.put("timeCreated", new Integer(7));
        fieldsMap.put("timeUpdated", new Integer(8));
        fieldsMap.put("disabled", new Integer(9));
        fieldsMap.put("timeDisabled", new Integer(10));
        fieldsMap.put("confirmed", new Integer(11));
        fieldsMap.put("timeConfirmed", new Integer(12));
        fieldsMap.put("pagelessSession", new Integer(13));
        fieldsMap.put("opt1", new Integer(14));
        fieldsMap.put("opt2", new Integer(15));
    }


	AutositeUser m_autositeUser;

	public AutositeUserDataHolder(AutositeUser _AutositeUser){
		m_autositeUser =  _AutositeUser; 	
	}

	public AutositeUserDataHolder(){
		m_autositeUser =  new AutositeUser(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_autositeUser;
	}


	public long getId(){
		return 	m_autositeUser.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_autositeUser.getSiteId();
	}
	public void setSiteId(long sid){
		m_autositeUser.setSiteId(sid);
	}


	public String getUsername(){
		return m_autositeUser.getUsername();
	}
	public String getPassword(){
		return m_autositeUser.getPassword();
	}
	public String getEmail(){
		return m_autositeUser.getEmail();
	}
	public int getUserType(){
		return m_autositeUser.getUserType();
	}
	public String getFirstName(){
		return m_autositeUser.getFirstName();
	}
	public String getLastName(){
		return m_autositeUser.getLastName();
	}
	public String getNickname(){
		return m_autositeUser.getNickname();
	}
	public Timestamp getTimeCreated(){
		return m_autositeUser.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_autositeUser.getTimeUpdated();
	}
	public int getDisabled(){
		return m_autositeUser.getDisabled();
	}
	public Timestamp getTimeDisabled(){
		return m_autositeUser.getTimeDisabled();
	}
	public int getConfirmed(){
		return m_autositeUser.getConfirmed();
	}
	public Timestamp getTimeConfirmed(){
		return m_autositeUser.getTimeConfirmed();
	}
	public int getPagelessSession(){
		return m_autositeUser.getPagelessSession();
	}
	public int getOpt1(){
		return m_autositeUser.getOpt1();
	}
	public String getOpt2(){
		return m_autositeUser.getOpt2();
	}

	public void setUsername(String _username ){
		m_autositeUser.setUsername(_username);
	}
	public void setPassword(String _password ){
		m_autositeUser.setPassword(_password);
	}
	public void setEmail(String _email ){
		m_autositeUser.setEmail(_email);
	}
	public void setUserType(int _userType ){
		m_autositeUser.setUserType(_userType);
	}
	public void setFirstName(String _firstName ){
		m_autositeUser.setFirstName(_firstName);
	}
	public void setLastName(String _lastName ){
		m_autositeUser.setLastName(_lastName);
	}
	public void setNickname(String _nickname ){
		m_autositeUser.setNickname(_nickname);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_autositeUser.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_autositeUser.setTimeUpdated(_timeUpdated);
	}
	public void setDisabled(int _disabled ){
		m_autositeUser.setDisabled(_disabled);
	}
	public void setTimeDisabled(Timestamp _timeDisabled ){
		m_autositeUser.setTimeDisabled(_timeDisabled);
	}
	public void setConfirmed(int _confirmed ){
		m_autositeUser.setConfirmed(_confirmed);
	}
	public void setTimeConfirmed(Timestamp _timeConfirmed ){
		m_autositeUser.setTimeConfirmed(_timeConfirmed);
	}
	public void setPagelessSession(int _pagelessSession ){
		m_autositeUser.setPagelessSession(_pagelessSession);
	}
	public void setOpt1(int _opt1 ){
		m_autositeUser.setOpt1(_opt1);
	}
	public void setOpt2(String _opt2 ){
		m_autositeUser.setOpt2(_opt2);
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

        if ( colNum == 0) return getUsername() == null?"":getUsername().toString();
        if ( colNum == 1) return getPassword() == null?"":getPassword().toString();
        if ( colNum == 2) return getEmail() == null?"":getEmail().toString();
        if ( colNum == 3) return String.valueOf(getUserType());
        if ( colNum == 4) return getFirstName() == null?"":getFirstName().toString();
        if ( colNum == 5) return getLastName() == null?"":getLastName().toString();
        if ( colNum == 6) return getNickname() == null?"":getNickname().toString();
        if ( colNum == 7) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 8) return getTimeUpdated() == null?"":getTimeUpdated().toString();
        if ( colNum == 9) return String.valueOf(getDisabled());
        if ( colNum == 10) return getTimeDisabled() == null?"":getTimeDisabled().toString();
        if ( colNum == 11) return String.valueOf(getConfirmed());
        if ( colNum == 12) return getTimeConfirmed() == null?"":getTimeConfirmed().toString();
        if ( colNum == 13) return String.valueOf(getPagelessSession());
        if ( colNum == 14) return String.valueOf(getOpt1());
        if ( colNum == 15) return getOpt2() == null?"":getOpt2().toString();
		return "";
    }
}