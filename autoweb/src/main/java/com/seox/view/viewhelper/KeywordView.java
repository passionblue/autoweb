/*
 * Created on Nov 22, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.view.viewhelper;

import java.util.List;

public class KeywordView {

    private String m_category;
    private String m_keyword;
    private String m_keywordId;
    private String m_enteredDate;

    private RanksViewMap m_ranksViewMap;
    private RanksViewMap m_yahooRanksViewMap;
    private RanksViewMap m_msnRanksViewMap;

    public KeywordView(String keyword) {
        m_keyword = keyword;
        m_ranksViewMap = new RanksViewMap(m_keyword);
        m_yahooRanksViewMap = new RanksViewMap(m_keyword);
        m_msnRanksViewMap = new RanksViewMap(m_keyword);
    }

    public String getKeywordId() {
        return m_keywordId;
    }

    public void setKeywordId(String keywordId) {
        m_keywordId = keywordId;
    }

    public KeywordView(String category, String keyword, String enteredDate) {
        m_category = category;
        m_keyword = keyword;
        m_enteredDate = enteredDate;
    }

    public RanksView getRanksView(String domain) {
        return m_ranksViewMap.getRanksView(domain);
    }
    public void addRanksView(String domain, RanksView ranksView) {
        m_ranksViewMap.addRanksView(domain, ranksView);
    }
    
    public List getAllRanksView() {
        return m_ranksViewMap.getAllRanksView();
    }

    public String getCategory() {
        return m_category;
    }
    public void setCategory(String category) {
        m_category = category;
    }
    public String getEnteredDate() {
        return m_enteredDate;
    }
    public void setEnteredDate(String enteredDate) {
        m_enteredDate = enteredDate;
    }
    public String getKeyword() {
        return m_keyword;
    }
    public void setKeyword(String keyword) {
        m_keyword = keyword;
    }
}
