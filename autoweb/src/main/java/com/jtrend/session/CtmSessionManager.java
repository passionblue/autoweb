package com.jtrend.session;

//Session manager manages web context for CTM system.
// Temporaray behavorl
// CTM manager is created for each session if user is not registerd (means no user id. 
// userid is sent from 'cookie' or 'login' or 'register'
// temporarily created session manager (w/o userid ) is short-lived and purged in the puture.
// session manager with userid should be persisted for future access.


public class CtmSessionManager
{
	private String			m_userId;
	private String			m_sessionId;
	private UserContext 	m_userContext;
	private SessionContext  m_sessionContext;
	private UserHistory 	m_userHistory;
	private ViewContext		m_viewContext;

	
	public CtmSessionManager()
	{
		this(null);
	}
	
	public CtmSessionManager(String userId)
	{
		m_userContext = new UserContext();
		m_userContext.setUserid(userId);
		m_sessionContext = new SessionContext();
	}
	
	
	public UserContext getUserContext()
	{
		return m_userContext;
	}

	public void setUserContext(UserContext userContext)
	{
		m_userContext = userContext;
	}

	public UserHistory getUserHistory()
	{
		return m_userHistory;
	}

	public void setUserHistory(UserHistory userHistory)
	{
		m_userHistory = userHistory;
	}

	public ViewContext getViewContext()
	{
		return m_viewContext;
	}

	public void setViewContext(ViewContext viewContext)
	{
		m_viewContext = viewContext;
	}

	static public String getSessionKey()
    {
        return "sessionManager";
    }
    
    static public CtmSessionManager create()
    {
        return new CtmSessionManager();
    }

	public SessionContext getSessionContext()
	{
		return m_sessionContext;
	}

	public void setSessionContext(SessionContext sessionContext)
	{
		m_sessionContext = sessionContext;
	}

}
