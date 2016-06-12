package com.autosite.session;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.autosite.db.AutositeUser;

/*
 * created to give automatic login access as a
 * 
 */

public class InstantAccessToken {

    final public static int ACCESS_TYPE_ANY = -1;
    final public static int ACCESS_TYPE_LOGIN = 0;
    final public static int ACCESS_TYPE_PAGE = 1;

    private String m_tokenId = ""+System.nanoTime();
    
    private String m_issueSessionId;
    private String m_issueSiteUrl;
    private Set m_useableSiteUrls;
    private int m_accessType;
    
    private Set m_acceptedSiteUrls;
    private Set m_acceptedSessionIds;
    
    private AutositeUser m_accessUser;
    
    private long m_createdTime = System.currentTimeMillis();
    private long m_life = 60000;
    
    public InstantAccessToken(String issueSessionId, Set useableSiteUrls, int type){
        m_useableSiteUrls = new HashSet();
        m_acceptedSiteUrls = new HashSet();
        m_acceptedSessionIds = new HashSet();
    }
    public InstantAccessToken(String issueSessionId, String useableSiteUrl, int type){
        m_useableSiteUrls = new HashSet();
        m_acceptedSiteUrls = new HashSet();
        m_acceptedSessionIds = new HashSet();
    }
    public String getIssueSessionId() {
        return m_issueSessionId;
    }
    public String getIssueSiteUrl() {
        return m_issueSiteUrl;
    }
    public Set getUseableSiteUrls() {
        return m_useableSiteUrls;
    }
    public int getAccessType() {
        return m_accessType;
    }
    public Set getAcceptedSiteUrls() {
        return m_acceptedSiteUrls;
    }
    public Set getAcceptedSessionIds() {
        return m_acceptedSessionIds;
    }
    public long getCreatedTime() {
        return m_createdTime;
    }
    public long getLife() {
        return m_life;
    }
    public void setIssueSessionId(String issueSessionId) {
        m_issueSessionId = issueSessionId;
    }
    public void setIssueSiteUrl(String issueSiteUrl) {
        m_issueSiteUrl = issueSiteUrl;
    }
    public void setUseableSiteUrls(Set useableSiteUrls) {
        m_useableSiteUrls = useableSiteUrls;
    }
    public void setAccessType(int accessType) {
        m_accessType = accessType;
    }
    public void setAcceptedSiteUrls(Set acceptedSiteUrls) {
        m_acceptedSiteUrls = acceptedSiteUrls;
    }
    public void setAcceptedSessionIds(Set acceptedSessionIds) {
        m_acceptedSessionIds = acceptedSessionIds;
    }
    public void setCreatedTime(long createdTime) {
        m_createdTime = createdTime;
    }
    public void setLife(long life) {
        m_life = life;
    }

    public String getTokenId() {
        return m_tokenId;
    }
    public AutositeUser getAccessUser() {
        return m_accessUser;
    }
    public void setAccessUser(AutositeUser accessUser) {
        m_accessUser = accessUser;
    }
    public boolean expired(){
        if ( m_life <= 0 ) return false;
        return (System.currentTimeMillis() > ( m_life + m_createdTime)); 
    }
    

    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append("m_tokenId=").append(m_tokenId);
        b.append("|m_issueSessionId=").append(m_issueSessionId);
        b.append("|m_issueSiteUrl=").append(m_issueSiteUrl);
        b.append("|m_createdTime=").append(new Date(m_createdTime));
        b.append("|m_accessUser=").append(m_accessUser);

        return b.toString();
    }
    
}
