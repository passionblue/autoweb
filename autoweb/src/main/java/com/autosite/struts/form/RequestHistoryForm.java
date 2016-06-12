package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class RequestHistoryForm extends ActionForm {

	private String _ForwardSiteId;             
	private String _IsDropped;             
	private String _IsPageless;             
	private String _IsLogin;             
	private String _IsAjax;             
	private String _IsRobot;             
	private String _Userid;             
	private String _UserAgent;             
	private String _Refer;             
	private String _Robot;             
	private String _RemoteIp;             
	private String _SiteUrl;             
	private String _Uri;             
	private String _Query;             
	private String _Rpci;             
	private String _SessionId;             
	private String _TimeCreated;             

    public String getForwardSiteId() {
        return _ForwardSiteId;
    }

    public void setForwardSiteId(String ForwardSiteId) {
        this._ForwardSiteId = ForwardSiteId;
    }    

    public String getIsDropped() {
        return _IsDropped;
    }

    public void setIsDropped(String IsDropped) {
        this._IsDropped = IsDropped;
    }    

    public String getIsPageless() {
        return _IsPageless;
    }

    public void setIsPageless(String IsPageless) {
        this._IsPageless = IsPageless;
    }    

    public String getIsLogin() {
        return _IsLogin;
    }

    public void setIsLogin(String IsLogin) {
        this._IsLogin = IsLogin;
    }    

    public String getIsAjax() {
        return _IsAjax;
    }

    public void setIsAjax(String IsAjax) {
        this._IsAjax = IsAjax;
    }    

    public String getIsRobot() {
        return _IsRobot;
    }

    public void setIsRobot(String IsRobot) {
        this._IsRobot = IsRobot;
    }    

    public String getUserid() {
        return _Userid;
    }

    public void setUserid(String Userid) {
        this._Userid = Userid;
    }    

    public String getUserAgent() {
        return _UserAgent;
    }

    public void setUserAgent(String UserAgent) {
        this._UserAgent = UserAgent;
    }    

    public String getRefer() {
        return _Refer;
    }

    public void setRefer(String Refer) {
        this._Refer = Refer;
    }    

    public String getRobot() {
        return _Robot;
    }

    public void setRobot(String Robot) {
        this._Robot = Robot;
    }    

    public String getRemoteIp() {
        return _RemoteIp;
    }

    public void setRemoteIp(String RemoteIp) {
        this._RemoteIp = RemoteIp;
    }    

    public String getSiteUrl() {
        return _SiteUrl;
    }

    public void setSiteUrl(String SiteUrl) {
        this._SiteUrl = SiteUrl;
    }    

    public String getUri() {
        return _Uri;
    }

    public void setUri(String Uri) {
        this._Uri = Uri;
    }    

    public String getQuery() {
        return _Query;
    }

    public void setQuery(String Query) {
        this._Query = Query;
    }    

    public String getRpci() {
        return _Rpci;
    }

    public void setRpci(String Rpci) {
        this._Rpci = Rpci;
    }    

    public String getSessionId() {
        return _SessionId;
    }

    public void setSessionId(String SessionId) {
        this._SessionId = SessionId;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

}