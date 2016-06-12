package com.autosite.session;

import com.autosite.db.SiteRegAccountServiceInfo;
import com.autosite.db.SiteRegPaymentInfo;
import com.autosite.db.SiteRegStart;
import com.autosite.session.storable.AutositeSessionObjectStore;

public class SiteRegStore extends AutositeSessionObjectStore {

    String m_domain;
    SiteRegStart m_start;
    SiteRegAccountServiceInfo m_accServiceInfo;
    SiteRegPaymentInfo m_payInfo;
    
    public SiteRegStart getStart() {
        return m_start;
    }
    public void setStart(SiteRegStart start) {
        m_start = start;
    }
    public SiteRegAccountServiceInfo getAccServiceInfo() {
        return m_accServiceInfo;
    }
    public void setAccServiceInfo(SiteRegAccountServiceInfo accServiceInfo) {
        m_accServiceInfo = accServiceInfo;
    }
    public SiteRegPaymentInfo getPayInfo() {
        return m_payInfo;
    }
    public void setPayInfo(SiteRegPaymentInfo payInfo) {
        m_payInfo = payInfo;
    }
    public String getDomain() {
        return m_domain;
    }
    public void setDomain(String domain) {
        m_domain = domain;
    }
    
    
    public static String getSessionKey(){
        return "k_site_red_store";
    }
    
}
