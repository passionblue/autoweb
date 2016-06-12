/*
 * Created on Dec 20, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.view.viewhelper;

public class RankView {

    private String m_keyword;
    private int m_rank;
    private int m_timeNum;
    private String m_domain;
    private String m_link;
    public String getDomain() {
        return m_domain;
    }
    public void setDomain(String domain) {
        m_domain = domain;
    }
    public String getKeyword() {
        return m_keyword;
    }
    public void setKeyword(String keyword) {
        m_keyword = keyword;
    }
    public String getLink() {
        return m_link;
    }
    public void setLink(String link) {
        m_link = link;
    }
    public int getRank() {
        return m_rank;
    }
    public void setRank(int rank) {
        m_rank = rank;
    }
    public int getTimeNum() {
        return m_timeNum;
    }
    public void setTimeNum(int timeNum) {
        m_timeNum = timeNum;
    }
    
}
