/*
 * Created on Dec 7, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools;

import com.seox.db.Rank;

public class RankEntry {
    
    private int m_timeNum;
    private String m_domain;
    private String m_link;
    private boolean m_needUpdate;
    private Rank m_rankObj;

    public RankEntry(int timeNum, String domain, String link, Rank rankObj) {
        m_timeNum = timeNum;
        m_domain = domain;
        m_link = link;
        m_rankObj = rankObj;
    }
    
    public Rank getRankObj() {
        return m_rankObj;
    }
  
    public boolean isNeedUpdate() {
        return m_needUpdate;
    }
    public void setNeedUpdate(boolean needUpdate) {
        m_needUpdate = needUpdate;
    }
    
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((m_domain == null) ? 0 : m_domain.hashCode());
        result = PRIME * result + ((m_link == null) ? 0 : m_link.hashCode());
        return result;
    }
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final RankEntry other = (RankEntry) obj;
        if (m_domain == null) {
            if (other.m_domain != null)
                return false;
        }
        else if (!m_domain.equals(other.m_domain))
            return false;
        if (m_link == null) {
            if (other.m_link != null)
                return false;
        }
        else if (!m_link.equals(other.m_link))
            return false;
        return true;
    }
    public String getDomain() {
        return m_domain;
    }
    
    public String getLink() {
        return m_link;
    }
    
    public int getTimeNum() {
        return m_timeNum;
    }

    public void setTimeNum(int timeNum) {
        m_timeNum = timeNum;
    }

    public String toString() {
        return  m_timeNum + "|" + m_domain  + "|" +m_rankObj.getRank() + "|"+ m_link + "\n";
    }
}
