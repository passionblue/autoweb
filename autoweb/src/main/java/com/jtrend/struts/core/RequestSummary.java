package com.jtrend.struts.core;

import com.jtrend.session.PageView;

public class RequestSummary {

    private String m_uri;
    private String m_query;
    private PageView m_pageView;
    public String getUri() {
        return m_uri;
    }
    public void setUri(String uri) {
        m_uri = uri;
    }
    public String getQuery() {
        return m_query;
    }
    public void setQuery(String query) {
        m_query = query;
    }
    public PageView getPageView() {
        return m_pageView;
    }
    public void setPageView(PageView pageView) {
        m_pageView = pageView;
    }
    
    
}
