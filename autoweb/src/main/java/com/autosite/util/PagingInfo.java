package com.autosite.util;

public class PagingInfo {

    
    protected int m_curDisplayPage;
    
    protected int m_beginIdx;
    protected int m_endIdx; // exclusive
    
    protected boolean m_hasPrev;
    protected boolean m_hasNext;
    
    protected int m_totalNumPages;
    
    public int getTotalNumPages() {
        return m_totalNumPages;
    }
    public void setTotalNumPages(int totalNumPages) {
        m_totalNumPages = totalNumPages;
    }
    public int getCurDisplayPage() {
        return m_curDisplayPage;
    }
    public void setCurDisplayPage(int curDisplayPage) {
        m_curDisplayPage = curDisplayPage;
    }
    public int getBeginIdx() {
        return m_beginIdx;
    }
    public void setBeginIdx(int beginIdx) {
        m_beginIdx = beginIdx;
    }
    public int getEndIdx() {
        return m_endIdx;
    }
    public void setEndIdx(int endIdx) {
        m_endIdx = endIdx;
    }
    public boolean isHasPrev() {
        return m_hasPrev;
    }
    public void setHasPrev(boolean hasPrev) {
        m_hasPrev = hasPrev;
    }
    public boolean isHasNext() {
        return m_hasNext;
    }
    public void setHasNext(boolean hasNext) {
        m_hasNext = hasNext;
    }
    
    
    
}
