package com.jtrend.session;

public class ViewContext extends AbstractSessionStorable
{
    private int m_channelType; // HTTP, WebService, Wireless
    private int m_viewset;
    
    static public String getSessionKey()
    {
        return "userContext";
    }
    
    static public UserContext create()
    {
        return new UserContext();
    }

}
