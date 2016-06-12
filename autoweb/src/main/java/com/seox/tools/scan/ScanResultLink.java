package com.seox.tools.scan;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;



public class ScanResultLink {
    
    private int m_timeNum;
    private int m_rank;
    private URL m_linkUrl;
    private String m_domain;
    private String m_page;
    private String m_keywordStr;
    private int m_engine; // Google, Yahoo, MSN
    private Set m_keywords;
    private int m_totalReturn;
    
    private static Set m_singleDomains = new HashSet();
    private static Set m_countryDomains = new HashSet();
    
    static
    {
        m_singleDomains.add("com");
        m_singleDomains.add("org");
        m_singleDomains.add("info");
        m_singleDomains.add("net");
        m_singleDomains.add("biz");
        m_singleDomains.add("tv");
        m_singleDomains.add("ws");
        m_singleDomains.add("name");
        m_singleDomains.add("mobi");
        m_singleDomains.add("gov");
        m_singleDomains.add("us");
    }
    
    
    public URL getLinkUrl() {
        return m_linkUrl;
    }

    public void setLinkUrl(URL linkUrl) {
        m_linkUrl = linkUrl;
    }

    public ScanResultLink() {
        this(1);
    }

    public ScanResultLink(int engine) {
        m_keywords = new HashSet();
        m_engine = engine;
    }

    public ScanResultLink(int engine, String link) {
        m_keywords = new HashSet();
        m_engine = engine;
        
        setLinkString(link);
    }

    public Set getKeywords() {
        return m_keywords;
    }

    public void setKeywords(Set keywords) {
        m_keywords = keywords;
    }

    public String getKeywordStr() {
        return m_keywordStr;
    }

    public void setKeywordStr(String keywordStr) {
        m_keywordStr = keywordStr;
        StringTokenizer tok = new StringTokenizer(keywordStr, " ");
        while (tok.hasMoreTokens()) {
            m_keywords.add(tok.nextToken());
        }
    }

    public String getLinkString() {
        return m_linkUrl.toString();
    }

    public void setLinkString(String linkStr) {
        try {
            m_linkUrl = new URL(linkStr);
            String host = m_linkUrl.getHost();

            int pos = host.lastIndexOf(".");
            
            String dom = host.substring(pos+1);

            if ( m_singleDomains.contains(dom) ) {
                pos = host.lastIndexOf(".", pos-1);
                m_domain = host.substring(pos +1);
            } else {
                pos = host.lastIndexOf(".", pos-1);
                pos = host.lastIndexOf(".", pos-1);
                m_domain = host.substring(pos +1);
            }
        }
        catch (MalformedURLException e) {
        }
    }

    public String getPage() {
        return m_page;
    }

    public void setPage(String page) {
        m_page = page;
    }

    public int getRank() {
        return m_rank;
    }

    public void setRank(int rank) {
        m_rank = rank;
    }

    public String toString() {
        return "[" + m_timeNum + "]" + m_keywordStr + " (" + m_rank + ") " + getLinkString();
    }

    public String getDomain() {
        return m_domain;
    }

    public void setDomain(String domain) {
        m_domain = domain;
    }

    public int getEngine() {
        return m_engine;
    }

    public void setEngine(int engine) {
        m_engine = engine;
    }

    public int getTotalReturn() {
        return m_totalReturn;
    }

    public void setTotalReturn(int totalReturn) {
        m_totalReturn = totalReturn;
    }
    
    public void initTopDomainMaps() {
        
    }

    public int getTimeNum() {
        return m_timeNum;
    }

    public void setTimeNum(int timeNum) {
        m_timeNum = timeNum;
    }

}
