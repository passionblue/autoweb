package com.surveygen.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jtrend.session.PageView;
import com.jtrend.struts.core.ViewManager;

public class SurveyGenViewManager implements ViewManager {

    private Map m_pages = new HashMap();
    private static ViewManager m_instance = new SurveyGenViewManager();
    
    public static ViewManager getInstance() {
        return m_instance;
    }
    
    //TODO should load from config file.
    public void initPageView() {

    	// 1. page alias   2. main jsp   3. side menu

        /*
        m_pages.put("home", new PageView("/jsp/layout/home.jsp", "/jsp/sidemenus/sidemenu_home.jsp", null, false));
        m_pages.put("home_benefits", new PageView("/jsp/statics/home_benefits.jsp", "/jsp/sidemenus/sidemenu_home.jsp", null, false));
        m_pages.put("home_whyimade", new PageView("/jsp/statics/home_whyimade.jsp", "/jsp/sidemenus/sidemenu_home.jsp", null, false));
        m_pages.put("home_betaregistration", new PageView("/jsp/statics/home_betaregistration.jsp", "/jsp/sidemenus/sidemenu_home.jsp", null, false));

        m_pages.put("not_found", new PageView("/jsp/layout/not_found.jsp", null, null, false));
        m_pages.put("error", new PageView("/jsp/layout/error.jsp", null, null, false));
        
        m_pages.put("aboutus", new PageView("/jsp/statics/aboutus.jsp", "/jsp/sidemenus/sidemenu_aboutus.jsp", null, false));
        m_pages.put("aboutus_history", new PageView("/jsp/statics/aboutus_history.jsp", "/jsp/sidemenus/sidemenu_aboutus.jsp", null, false));
        m_pages.put("aboutus_howsitemade", new PageView("/jsp/statics/aboutus_howsitemade.jsp", "/jsp/sidemenus/sidemenu_aboutus.jsp", null, false));
        m_pages.put("aboutus_partners", new PageView("/jsp/statics/aboutus_partners.jsp", "/jsp/sidemenus/sidemenu_aboutus.jsp", null, false));
        
        m_pages.put("contact", new PageView("/jsp/statics/contact.jsp", null, null, false));
        m_pages.put("login_form", new PageView("/jsp/statics/login_form.jsp", null, null, false));
        m_pages.put("register_form", new PageView("/jsp/statics/register_form.jsp", null, null, false));
        m_pages.put("register_step", new PageView("/jsp/statics/register_step.jsp", null, null, false));
        m_pages.put("return_display", new PageView("/jsp/statics/return_display.jsp", null, null, false));
        m_pages.put("sample", new PageView("/jsp/statics/sample.jsp", null, null, false));
        
        m_pages.put("account", new PageView("/jsp/login_statics/account.jsp", null, null));
        m_pages.put("category", new PageView("/jsp/login_statics/category.jsp", null, null));
        m_pages.put("competition", new PageView("/jsp/login_statics/competition.jsp", null, null));

        // Domains Pages
        m_pages.put("domain", new PageView("/jsp/login_statics/domain_manage.jsp", "/jsp/sidemenus/sidemenu_domains.jsp", null));
        m_pages.put("domain_manage", new PageView("/jsp/login_statics/domain_manage.jsp", "/jsp/sidemenus/sidemenu_domains.jsp", null));
        m_pages.put("domain_report_all", new PageView("/jsp/login_statics/domain_report_all.jsp", "/jsp/sidemenus/sidemenu_domains.jsp", null));
        m_pages.put("domain_report2", new PageView("/jsp/login_statics/domain_report2.jsp", "/jsp/sidemenus/sidemenu_domains.jsp", null));

        m_pages.put("domain_report_s", new PageView("/jsp/login_statics/domain_report_s.jsp", "/jsp/sidemenus/sidemenu_domains.jsp", null));

        // Keywords Pages
        m_pages.put("keywords", new PageView("/jsp/login_statics/keywords.jsp", "/jsp/sidemenus/sidemenu_keywords.jsp", null));
        
        m_pages.put("keyword_report_s", new PageView("/jsp/login_statics/keyword_report_s.jsp", "/jsp/sidemenus/sidemenu_keywords.jsp", null));
        m_pages.put("keyword_report_tops", new PageView("/jsp/login_statics/keyword_report_tops.jsp", "/jsp/sidemenus/sidemenu_keywords.jsp", null));

        
        m_pages.put("login_summary", new PageView("/jsp/login_statics/login_summary.jsp", null, null));
        m_pages.put("ranking", new PageView("/jsp/login_statics/ranking.jsp", null, null));
        m_pages.put("note", new PageView("/jsp/login_statics/note.jsp", null, null));

        m_pages.put("admin", new PageView("/jsp/login_statics/admin.jsp", "/jsp/sidemenus/sidemenu_admin.jsp", null));
        m_pages.put("admin_logs", new PageView("/jsp/login_statics/admin_logs.jsp", "/jsp/sidemenus/sidemenu_admin.jsp", null));
        m_pages.put("admin_users", new PageView("/jsp/login_statics/admin_users.jsp", "/jsp/sidemenus/sidemenu_admin.jsp", null));
        
        ViewProcFactory.getInstance().registerViewProc("login_summary", new LoginSummaryViewProc());
        
        ViewProcFactory.getInstance().registerViewProc("domain_manage", new DomainsViewProc());
        ViewProcFactory.getInstance().registerViewProc("domain_report_all", new DomainsViewProc());
        ViewProcFactory.getInstance().registerViewProc("domain_report_2", new DomainsViewProc());
        ViewProcFactory.getInstance().registerViewProc("domain_report_s", new DomainDetailViewProc());

        ViewProcFactory.getInstance().registerViewProc("keywords", new KeywordViewProc());
        ViewProcFactory.getInstance().registerViewProc("keyword_report_s", new KeywordDetailViewProc());
        ViewProcFactory.getInstance().registerViewProc("keyword_report_tops", new KeywordTopsViewProc());
        
        ViewProcFactory.getInstance().registerViewProc("note", new NoteViewProc());
        ViewProcFactory.getInstance().registerViewProc("admin", new AdminViewProc());
        ViewProcFactory.getInstance().registerViewProc("admin_logs", new AdminViewProc());
        */

        
        m_pages.put("home", new PageView("home", "/jsp/contents/home_contents.jsp", "/jsp/sidemenus/sidemenu_home.jsp", null, false));
        m_pages.put("login_form", new PageView("login_form", "/jsp/statics/login.jsp", "/jsp/sidemenus/sidemenu_home.jsp", null, false));
        m_pages.put("open_site_list", new PageView("open_site_list", "/jsp/open/open_site_list.jsp", "/jsp/sidemenus/sidemenu_home.jsp", null, false));
        
        
        m_pages.put("add_content", new PageView("add_content", "/jsp/form/addContent.jsp", "/jsp/sidemenus/sidemenu_home.jsp", null, true));
        m_pages.put("site_list", new PageView("site_list", "/jsp/contents/site_list.jsp", "/jsp/sidemenus/sidemenu_home.jsp", null, true));

    }
    
    // Return PageView object for alias page name
    public PageView getPageView(String pageAlias) {
        return (PageView)m_pages.get(pageAlias);
    }

    public List getPageList() {
        // TODO Auto-generated method stub
        return null;
    }

    public PageView getPageView(String pageAlias, String server) {
        // TODO Auto-generated method stub
        return null;
    }

    public List getPageObjectList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean registerView(String actionFrom, String viewDefinition) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean registerView(String actionFrom, PageView pageView) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
