package com.autosite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SiteConfigTransient {

    protected boolean m_useThisVisibilityConfig;
    protected boolean m_hideMenu;
    protected boolean m_hideMid;
    protected boolean m_hideAd;
    
    protected int m_widthOffset;
    protected int m_displayType;// Regular/Printable/Mobile/No Ads
    
    //===============================================================================
    //===============================================================================
    
    public boolean isUseThisVisibilityConfig() {
        return m_useThisVisibilityConfig;
    }


    public void setUseThisVisibilityConfig(boolean useThisVisibilityConfig) {
        m_useThisVisibilityConfig = useThisVisibilityConfig;
    }


    public boolean isHideMenu() {
        return m_hideMenu;
    }


    public void setHideMenu(boolean hideMenu) {
        m_hideMenu = hideMenu;
    }


    public boolean isHideMid() {
        return m_hideMid;
    }


    public void setHideMid(boolean hideMid) {
        m_hideMid = hideMid;
    }


    public boolean isHideAd() {
        return m_hideAd;
    }


    public void setHideAd(boolean hideAd) {
        m_hideAd = hideAd;
    }

    public int getWidthOffset() {
        return m_widthOffset;
    }


    public void setWidthOffset(int width) {
        m_widthOffset = width;
    }


    public int getDisplayType() {
        return m_displayType;
    }


    public void setDisplayType(int displayType) {
        m_displayType = displayType;
    }
    //===============================================================================
    //===============================================================================



    public static SiteConfigTransient getDefault(){
        SiteConfigTransient ret = new SiteConfigTransient();
        ret.m_useThisVisibilityConfig = false;
        
        return ret;
    }
    
    
    public static SiteConfigTransient getTransientConfig(long siteId){
     
        Long keyId = new Long(siteId);
        if ( !m_map.containsKey(keyId)){
            SiteConfigTransient siteConfingTrans = new SiteConfigTransient();
            m_map.put(keyId, siteConfingTrans);
        }
        return (SiteConfigTransient) m_map.get(keyId);
    }
    
    private static Map m_map = new ConcurrentHashMap();
}
