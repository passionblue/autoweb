/*
 * Created on Nov 22, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.view.viewhelper;

import java.util.List;

public class DomainView {

    private String m_category;
    private String m_domain;
    private String m_fullLink;
    private String m_enteredDate;
    private String m_userDomainId;

    private RanksViewMap m_ranksViewMap;
    private RanksViewMap m_yahooRanksViewMap;
    private RanksViewMap m_msnRanksViewMap;

    private int m_rank;
    
    public DomainView(String domain) {
        m_domain = domain;
        m_ranksViewMap = new RanksViewMap(m_domain);
        m_yahooRanksViewMap = new RanksViewMap(m_domain);
        m_msnRanksViewMap = new RanksViewMap(m_domain);
    }
    
    public DomainView(String category, String domain, String fullLink, String enteredDate, int rank) {
        m_category = category;
        m_domain = domain;
        m_fullLink = fullLink;
        m_enteredDate = enteredDate;
        m_rank = rank;
    }
    public String getCategory() {
        return m_category;
    }
    public void setCategory(String category) {
        m_category = category;
    }
    public String getDomain() {
        return m_domain;
    }
    public void setDomain(String domain) {
        m_domain = domain;
    }
    public String getEnteredDate() {
        return m_enteredDate;
    }
    public void setEnteredDate(String enteredDate) {
        m_enteredDate = enteredDate;
    }
    public String getFullLink() {
        return m_fullLink;
    }
    public void setFullLink(String fullLink) {
        m_fullLink = fullLink;
    }
    public int getRank() {
        return m_rank;
    }
    public void setRank(int rank) {
        m_rank = rank;
    }
    public String getUserDomainId() {
        return m_userDomainId;
    }
    public void setUserDomainId(String userDomainId) {
        m_userDomainId = userDomainId;
    }

    public RanksView getRanksView(String keyword) {
        return m_ranksViewMap.getRanksView(keyword);
    }
    public void addRanksView(String keyword, RanksView ranksView) {
        m_ranksViewMap.addRanksView(keyword, ranksView);
    }
    
    public List getAllRanksView() {
        return m_ranksViewMap.getAllRanksView();
    }

    
    public String toString() {
        return "Domain=" + m_domain + ",Entered=" + m_enteredDate;
    }
    
}
