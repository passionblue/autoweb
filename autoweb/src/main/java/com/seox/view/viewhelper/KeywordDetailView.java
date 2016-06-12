/*
 * Created on Dec 20, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.view.viewhelper;

import java.util.ArrayList;
import java.util.List;

public class KeywordDetailView {

    String m_keyword;
    List m_ranks = new ArrayList();
    KeywordView m_keywordView;
    
    public String getKeyword() {
        return m_keyword;
    }
    public void setKeyword(String keyword) {
        m_keyword = keyword;
    }
    public List getRanks() {
        return m_ranks;
    }
    public void setRanks(List ranks) {
        m_ranks = ranks;
    }
    public KeywordView getKeywordView() {
        return m_keywordView;
    }
    public void setKeywordView(KeywordView keywordView) {
        m_keywordView = keywordView;
    }
    
}
