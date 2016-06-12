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

import com.autosite.db.AutositeSessionContextEntity;


public class AutositeSessionContextEntityDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("serial", new Integer(0));
        fieldsMap.put("isLogin", new Integer(1));
        fieldsMap.put("timeLogin", new Integer(2));
        fieldsMap.put("timeLastAccess", new Integer(3));
        fieldsMap.put("loginUserId", new Integer(4));
        fieldsMap.put("sessionType", new Integer(5));
        fieldsMap.put("remoteDeviceId", new Integer(6));
        fieldsMap.put("remoteIp", new Integer(7));
        fieldsMap.put("timeCreated", new Integer(8));
        fieldsMap.put("timeUpdated", new Integer(9));
    }


	AutositeSessionContextEntity m_autositeSessionContextEntity;

	public AutositeSessionContextEntityDataHolder(AutositeSessionContextEntity _AutositeSessionContextEntity){
		m_autositeSessionContextEntity =  _AutositeSessionContextEntity; 	
	}

	public AutositeSessionContextEntityDataHolder(){
		m_autositeSessionContextEntity =  new AutositeSessionContextEntity(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_autositeSessionContextEntity;
	}


	public long getId(){
		return 	m_autositeSessionContextEntity.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_autositeSessionContextEntity.getSiteId();
	}
	public void setSiteId(long sid){
		m_autositeSessionContextEntity.setSiteId(sid);
	}


	public String getSerial(){
		return m_autositeSessionContextEntity.getSerial();
	}
	public int getIsLogin(){
		return m_autositeSessionContextEntity.getIsLogin();
	}
	public Timestamp getTimeLogin(){
		return m_autositeSessionContextEntity.getTimeLogin();
	}
	public Timestamp getTimeLastAccess(){
		return m_autositeSessionContextEntity.getTimeLastAccess();
	}
	public long getLoginUserId(){
		return m_autositeSessionContextEntity.getLoginUserId();
	}
	public int getSessionType(){
		return m_autositeSessionContextEntity.getSessionType();
	}
	public long getRemoteDeviceId(){
		return m_autositeSessionContextEntity.getRemoteDeviceId();
	}
	public String getRemoteIp(){
		return m_autositeSessionContextEntity.getRemoteIp();
	}
	public Timestamp getTimeCreated(){
		return m_autositeSessionContextEntity.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_autositeSessionContextEntity.getTimeUpdated();
	}

	public void setSerial(String _serial ){
		m_autositeSessionContextEntity.setSerial(_serial);
	}
	public void setIsLogin(int _isLogin ){
		m_autositeSessionContextEntity.setIsLogin(_isLogin);
	}
	public void setTimeLogin(Timestamp _timeLogin ){
		m_autositeSessionContextEntity.setTimeLogin(_timeLogin);
	}
	public void setTimeLastAccess(Timestamp _timeLastAccess ){
		m_autositeSessionContextEntity.setTimeLastAccess(_timeLastAccess);
	}
	public void setLoginUserId(long _loginUserId ){
		m_autositeSessionContextEntity.setLoginUserId(_loginUserId);
	}
	public void setSessionType(int _sessionType ){
		m_autositeSessionContextEntity.setSessionType(_sessionType);
	}
	public void setRemoteDeviceId(long _remoteDeviceId ){
		m_autositeSessionContextEntity.setRemoteDeviceId(_remoteDeviceId);
	}
	public void setRemoteIp(String _remoteIp ){
		m_autositeSessionContextEntity.setRemoteIp(_remoteIp);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_autositeSessionContextEntity.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_autositeSessionContextEntity.setTimeUpdated(_timeUpdated);
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

        if ( colNum == 0) return getSerial() == null?"":getSerial().toString();
        if ( colNum == 1) return String.valueOf(getIsLogin());
        if ( colNum == 2) return getTimeLogin() == null?"":getTimeLogin().toString();
        if ( colNum == 3) return getTimeLastAccess() == null?"":getTimeLastAccess().toString();
        if ( colNum == 4) return String.valueOf(getLoginUserId());
        if ( colNum == 5) return String.valueOf(getSessionType());
        if ( colNum == 6) return String.valueOf(getRemoteDeviceId());
        if ( colNum == 7) return getRemoteIp() == null?"":getRemoteIp().toString();
        if ( colNum == 8) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 9) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }
}