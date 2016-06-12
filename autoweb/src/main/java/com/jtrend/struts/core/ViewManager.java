package com.jtrend.struts.core;

import java.util.List;

import com.jtrend.session.PageView;

public interface ViewManager {
    void initPageView();
    PageView getPageView(String pageAlias);
    PageView getPageView(String pageAlias, String server);
    List getPageList();
    List getPageObjectList();
    
    boolean registerView(String actionFrom, String viewDefinition);
    boolean registerView(String actionFrom, PageView pageView);
    
}
