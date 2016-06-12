package com.autosite.content;

public class Content {

    private String m_site;
    private String m_content;
    private String m_id;
    private boolean m_valid = true;
    
    public String getId() {
        return m_id;
    }

    public void setId(String id) {
        m_id = id;
    }

    public Content() {
        
    }
 
    public Content(String id, String site, String content) {
        m_id = id;
        m_site = site;
        m_content = content;
    }

    public String getSite() {
        return m_site;
    }

    public void setSite(String site) {
        m_site = site;
    }

    public String getContent() {
        return m_content;
    }

    public void setContent(String content) {
        m_content = content;
    }

    public boolean isValid() {
        return m_valid;
    }

    public void setValid(boolean valid) {
        m_valid = valid;
    }
    
    public String toString() {
        return "id=" + getId() + "|site=" + getSite() + "|valid=" + isValid()+"|content=" + getContent();
    }
                              
    
}
