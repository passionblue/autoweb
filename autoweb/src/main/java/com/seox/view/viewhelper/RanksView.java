/*
 * Created on Nov 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.view.viewhelper;

//Contains rank information for view
public class RanksView {

    private String m_domain;
    private String m_keyword;

    public RanksView(String domain, String keyword) {
        m_domain = domain;
        m_keyword = keyword;
    }
    
    private int m_googleWeeklyRanks [] = new int[100];
    private int m_yahooWeeklyRanks [] = new int[100];
    private int m_msnWeeklyRanks [] = new int[100];
    private int m_googleDaylyRanks [] = new int[100];
    private int m_yahooDaylyRanks [] = new int[100];
    private int m_msnDaylyRanks [] = new int[100];
    
    public int[] getGoogleDaylyRanks() {
        return m_googleDaylyRanks;
    }
    public int[] getGoogleWeeklyRanks() {
        return m_googleWeeklyRanks;
    }
    public int[] getMsnDaylyRanks() {
        return m_msnDaylyRanks;
    }
    public int[] getMsnWeeklyRanks() {
        return m_msnWeeklyRanks;
    }
    public int[] getYahooDaylyRanks() {
        return m_yahooDaylyRanks;
    }
    public int[] getYahooWeeklyRanks() {
        return m_yahooWeeklyRanks;
    }

    public int[] getRanks() {
        return getRanks(1, true);
    }
    
    public int[] getRanks(int engine, boolean weekly) {
     
        switch(engine) {
        case 1: return (weekly ? m_googleWeeklyRanks: m_googleDaylyRanks); 
        case 2: return (weekly ? m_yahooWeeklyRanks: m_googleDaylyRanks); 
        case 3: return (weekly ? m_msnWeeklyRanks: m_googleDaylyRanks); 
        default: return (weekly ? m_googleWeeklyRanks: m_googleDaylyRanks);
        }
    }

    
    
    public String getRanks(int engine, boolean weekly, int histIdx) {
        
        int[] ranks = getRanks(engine, weekly);
        
        if (histIdx >= ranks.length || ranks[histIdx] == 0) {
            return "-";
        }
        return String.valueOf(ranks[histIdx]);
    }

    public int getRanksDiff(int engine, boolean weekly, int histIdx) {
        int cur = getRanks(engine, weekly)[histIdx];
        int previous = getRanks(engine, weekly)[histIdx+1];
        
        if (cur == 0 ) {
            if (previous == 0 ) {
                return 0;
            } else {
                return -1*(100-previous);
            }
        } else {
            if (previous == 0 ) {
                return (100-cur);
            } else {
                return previous - cur;
            }
        }
    }    
    
    public String getRanksDiffStr(int engine, boolean weekly, int histIdx) {
        
        int cur = getRanks(engine, weekly)[histIdx];
        int previous = getRanks(engine, weekly)[histIdx+1];
        
        if (cur == 0 ) {
            if (previous == 0 ) {
                return "(-)";
            } else {
                return "(-)";
            }
        } else {
            if (previous == 0 ) {
                return "(+)";
            } else {
                int move = (previous-cur);
                if (move >0)  
                    return "<font color=\"green\">(+" +  (previous-cur) + ")</font>";
                else if (move <0)
                    return "<font color=\"red\">(" +  (previous-cur) + ")</font>";
                else 
                    return "<font color=\"black\">(" +  (previous-cur) + ")</font>";
            }
        }
    }
    
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
    
    
}
