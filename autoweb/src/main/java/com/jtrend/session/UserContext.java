package com.jtrend.session;

import java.util.Date;
import java.util.Map;

public class UserContext extends AbstractSessionStorable
{
	private String  m_serial;
    private String 	m_createdHost;
    private boolean m_login;
    private Date    m_loginTime;
    private String  m_loginHost;
    private String 	m_userid;		// This can be set from cookie without loggin.
    private Map		m_properties;
    private boolean m_valid;		// Wrong userid not in the system.
    
    public UserContext()
    {
        super();
        m_serial = ""+System.currentTimeMillis();
    }
    public UserContext(String userid)
    {
    	this();
    	m_userid = userid;
    }

    static public String getSessionKey()
    {
        return "userContext";
    }
    
    static public UserContext create()
    {
        return new UserContext();
    }

    public String getCreatedHost()
    {
        return m_createdHost;
    }

    public boolean isLogin()
    {
        return m_login;
    }

    public void setLogin(boolean login)
    {
        m_login = login;
        m_loginTime = new Date();
    }

    public String getUserid()
    {
    	if (m_userid != null && m_userid.length() == 0) return null; 
    	return m_userid;
    }

    public void setUserid(String userid)
    {
        m_userid = userid;
    }

	public Date getLoginTime()
	{
		return m_loginTime;
	}

	public void setLoginTime(Date loginTime)
	{
		m_loginTime = loginTime;
	}

	public String getSerial()
	{
		return m_serial;
	}

	public void setSerial(String id)
	{
		m_serial = id;
	}
	public boolean isValid()
	{
		return m_valid;
	}

}
