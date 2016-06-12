package com.autosite.db;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Page entity. @author MyEclipse Persistence Tools
 */

public class SiteRegStart extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

	private long _id;
	private String _targetDomain;

	public 	SiteRegStart(){
	}

	public long getId(){
		return _id;
	}
	public void setId(long id){
		_id = id;
	}
	public String getTargetDomain(){
		return _targetDomain;
	}
	public void setTargetDomain(String targetDomain){
		_targetDomain = targetDomain;
	}
}