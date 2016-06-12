/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.autosite.struts.action;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteDS;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.SiteConfigForm;
import com.jtrend.util.WebParamUtil;

/** 
 * MyEclipse Struts
 * Creation date: 03-29-2008
 * 
 * XDoclet definition:
 * @struts.action path="/siteConfig" name="siteConfigForm" input="/jsp/form/siteConfig.jsp" scope="request" validate="true"
 * @struts.action-forward name="default" path="/jsp/layout/layout.jsp" contextRelative="true"
 */
public class SiteConfigAction extends AutositeCoreAction {
    /*
     * Generated Methods
     */

    /** 
     * Method execute
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SiteConfigForm siteConfigForm = (SiteConfigForm) form;// TODO Auto-generated method stub
        HttpSession session = request.getSession();
        
//        if (isMissing(siteConfigForm.getKeywords()) && isMissing(siteConfigForm.getMeta())) {
//            sessionErrorText(session, "Data missing");
//            setPage(session, "site_config");
//            return mapping.findForward("default");
//        }
        
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName(), false);
        SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
        
        boolean siteConfigFound = true;
        if ( siteConfig == null)  {
            m_logger.debug("Site Config not found for " + site.getSiteUrl() + ",siteID=" + site.getId());
            siteConfig = SiteConfigDS.getDefaultSiteConfig();
            siteConfigFound = false;
        }

        if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long stid = WebParamUtil.getLongValue(request.getParameter("stid"));
            SiteConfig _SiteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(stid);

            if (_SiteConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + stid);
                //Default error page but will be overridden by exception specific error page
                setPage(session, "site_config");
                return mapping.findForward("default");
            }

            if (stid != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SiteConfig.getSiteId()); 
                //Default error page but will be overridden by exception specific error page
                setPage(session, "site_config");
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _SiteConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                setPage(session, "site_config");
            }
            return mapping.findForward("default");
        }        

        //=====================================================================================================================
        // New or Edit
        //=====================================================================================================================
        
        siteConfig.setKeywords(siteConfigForm.getKeywords());
        siteConfig.setMeta(siteConfigForm.getMeta());
        siteConfig.setSiteTrackGoogle(siteConfigForm.getTrack_google());
        siteConfig.setHeaderImage(siteConfigForm.getHeaderImage());

        if (!isMissing(siteConfigForm.getClrBorders())) {
            siteConfig.setColorBorders(siteConfigForm.getClrBorders());
        }

        if (!isMissing(siteConfigForm.getClrSubmenuBg())) {
            siteConfig.setColorSubmenuBg(siteConfigForm.getClrSubmenuBg());
        }
        
        if (!isMissing(siteConfigForm.getColCount())) {
            try {
                int colCount = Integer.valueOf(siteConfigForm.getColCount());
                siteConfig.setHomeColCount(colCount);
            }
            catch (NumberFormatException e) {
                sessionErrorText(session, "Column count number is invalid");
                setPage(session, request.getServerName(), "site_config");
                return mapping.findForward("default");
            }
        }
        
//        if (!isMissing(siteConfigForm.getAdWidth())) {
//            int adWidth = WebParamUtil.getIntValue(siteConfigForm.getAdWidth());
//            if ( adWidth >= 50 && adWidth <= 500)
//                siteConfig.setAdWidth(adWidth);
//        }

//        if (!isMissing(siteConfigForm.getMenuWidth())) {
//            int menuWidth = WebParamUtil.getIntValue(siteConfigForm.getMenuWidth());
//            if ( menuWidth >= 50 && menuWidth <= 500)
//                siteConfig.setMenuWidth(menuWidth);
//        }

        siteConfig.setHeaderImage(siteConfigForm.getHeaderImage());
        siteConfig.setHeaderImageHeight(WebParamUtil.getIntValue(siteConfigForm.getHeaderImageHeight()));
        siteConfig.setHeaderImageWidth(WebParamUtil.getIntValue(siteConfigForm.getHeaderImageWidth()));
        siteConfig.setHeaderAd(siteConfigForm.getHeaderAd());
        siteConfig.setMenuBackColor(siteConfigForm.getMenuBackColor());
        siteConfig.setMenuFrontColor(siteConfigForm.getMenuFrontColor());
        siteConfig.setMenuLineColor(siteConfigForm.getMenuLineColor());
        siteConfig.setMenuReverse(WebParamUtil.getIntValue(siteConfigForm.getMenuReverse()));
        siteConfig.setDisplayAllHome(WebParamUtil.getIntValue(siteConfigForm.getDisplayAllHome()));
        siteConfig.setLayoutPage(siteConfigForm.getLayoutPage());
        siteConfig.setSiteIconUrl(siteConfigForm.getSiteIconUrl());
        siteConfig.setUnderlyingHome(siteConfigForm.getUnderlyingHome());
        siteConfig.setUnderlyingDynamicPage((WebParamUtil.getIntValue(siteConfigForm.getUnderlyingDynamicPage())));
        siteConfig.setContentForwardSite(siteConfigForm.getContentForwardSite());
        siteConfig.setWidth(WebParamUtil.getIntValue(siteConfigForm.getWidth()));

        siteConfig.setShowMidColumn(WebParamUtil.getIntValue(siteConfigForm.getShowMidColumn()));
        siteConfig.setShowMenuColumn(WebParamUtil.getIntValue(siteConfigForm.getShowMenuColumn()));
        siteConfig.setShowAdColumn(WebParamUtil.getIntValue(siteConfigForm.getShowAdColumn()));

        siteConfig.setShowTopMenu(WebParamUtil.getIntValue(siteConfigForm.getShowTopMenu()));
        
        siteConfig.setShowHomeMenu(WebParamUtil.getIntValue(siteConfigForm.getShowHomeMenu()));
        siteConfig.setShowHomeMid(WebParamUtil.getIntValue(siteConfigForm.getShowHomeMid()));
        siteConfig.setShowHomeAd(WebParamUtil.getIntValue(siteConfigForm.getShowHomeAd()));

        
        
        siteConfig.setMenuWidth((WebParamUtil.getIntValue(siteConfigForm.getMenuWidth())));
        siteConfig.setAdWidth((WebParamUtil.getIntValue(siteConfigForm.getAdWidth())));
        siteConfig.setMidColumnWidth((WebParamUtil.getIntValue(siteConfigForm.getMidColumnWidth())));
        siteConfig.setUseWysiwygContent((WebParamUtil.getIntValue(siteConfigForm.getUseWysiwygContent())));
        siteConfig.setUseWysiwygPost((WebParamUtil.getIntValue(siteConfigForm.getUseWysiwygPost())));

        siteConfig.setBackgroundAttach(siteConfigForm.getBackgroundAttach());
        siteConfig.setBackgroundColor(siteConfigForm.getBackgroundColor());
        siteConfig.setBackgroundImage(siteConfigForm.getBackgroundImage());
        siteConfig.setBackgroundPosition(siteConfigForm.getBackgroundPosition());
        siteConfig.setBackgroundRepeat(siteConfigForm.getBackgroundRepeat());
        siteConfig.setContentBgColor(siteConfigForm.getContentBgColor());

        siteConfig.setAbsolutePosition(WebParamUtil.getIntValue(siteConfigForm.getAbsolutePosition()));
        siteConfig.setAbsoluteTop(WebParamUtil.getIntValue(siteConfigForm.getAbsoluteTop()));
        siteConfig.setAbsoluteLeft((WebParamUtil.getIntValue(siteConfigForm.getAbsoluteLeft())));

        siteConfig.setFunctionalType((WebParamUtil.getIntValue(siteConfigForm.getFunctionalType())));
        //siteConfig.setEcEnabled((WebParamUtil.getIntValue(siteConfigForm.getEcEnabled())));
        siteConfig.setEcEnabled(WebParamUtil.getCheckboxValue(siteConfigForm.getEcEnabled()));
        siteConfig.setFrontDisplayFeed(WebParamUtil.getCheckboxValue(siteConfigForm.getFrontDisplayFeed()));
        m_logger.debug("absolute= " + siteConfigForm.getAbsolutePosition());
        m_logger.debug("absolute= " + siteConfig.getAbsolutePosition());
        
        if (siteConfig.getId() > 0 ) {

            siteConfig.setTimeUpdated(new java.sql.Timestamp(System.currentTimeMillis()));
            SiteConfigDS.getInstance().update(siteConfig);
            
            m_logger.debug("absolute= " + siteConfig.getAbsolutePosition());
        } else {
            siteConfig.setTimeCreatd(new java.sql.Timestamp(System.currentTimeMillis()));
            siteConfig.setTimeUpdated(new java.sql.Timestamp(System.currentTimeMillis()));
            siteConfig.setSiteId(site.getId());
            SiteConfigDS.getInstance().put(siteConfig);
        }

        setPage(session, request.getServerName(), "site_config");
        return mapping.findForward("default");
    }
    
    protected void editField(HttpServletRequest request, HttpServletResponse response, SiteConfig _SiteConfig) throws Exception{

//      long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//      SiteConfig _SiteConfig = m_ds.getById(cid);

      if (!isMissing(request.getParameter("timeCreatd"))) {
          m_logger.debug("updating param timeCreatd from " +_SiteConfig.getTimeCreatd() + "->" + request.getParameter("timeCreatd"));
          _SiteConfig.setTimeCreatd(WebParamUtil.getDateValue(request.getParameter("timeCreatd")));
      }
      if (!isMissing(request.getParameter("timeUpdated"))) {
          m_logger.debug("updating param timeUpdated from " +_SiteConfig.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
          _SiteConfig.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
      }
      if (!isMissing(request.getParameter("keywords"))) {
          m_logger.debug("updating param keywords from " +_SiteConfig.getKeywords() + "->" + request.getParameter("keywords"));
          _SiteConfig.setKeywords(WebParamUtil.getStringValue(request.getParameter("keywords")));
      }
      if (!isMissing(request.getParameter("meta"))) {
          m_logger.debug("updating param meta from " +_SiteConfig.getMeta() + "->" + request.getParameter("meta"));
          _SiteConfig.setMeta(WebParamUtil.getStringValue(request.getParameter("meta")));
      }
      if (!isMissing(request.getParameter("width"))) {
          m_logger.debug("updating param width from " +_SiteConfig.getWidth() + "->" + request.getParameter("width"));
          _SiteConfig.setWidth(WebParamUtil.getIntValue(request.getParameter("width")));
      }
      if (!isMissing(request.getParameter("siteTrackGoogle"))) {
          m_logger.debug("updating param siteTrackGoogle from " +_SiteConfig.getSiteTrackGoogle() + "->" + request.getParameter("siteTrackGoogle"));
          _SiteConfig.setSiteTrackGoogle(WebParamUtil.getStringValue(request.getParameter("siteTrackGoogle")));
      }
      if (!isMissing(request.getParameter("homeColCount"))) {
          m_logger.debug("updating param homeColCount from " +_SiteConfig.getHomeColCount() + "->" + request.getParameter("homeColCount"));
          _SiteConfig.setHomeColCount(WebParamUtil.getIntValue(request.getParameter("homeColCount")));
      }
      if (!isMissing(request.getParameter("colorSubmenuBg"))) {
          m_logger.debug("updating param colorSubmenuBg from " +_SiteConfig.getColorSubmenuBg() + "->" + request.getParameter("colorSubmenuBg"));
          _SiteConfig.setColorSubmenuBg(WebParamUtil.getStringValue(request.getParameter("colorSubmenuBg")));
      }
      if (!isMissing(request.getParameter("contactInfo"))) {
          m_logger.debug("updating param contactInfo from " +_SiteConfig.getContactInfo() + "->" + request.getParameter("contactInfo"));
          _SiteConfig.setContactInfo(WebParamUtil.getStringValue(request.getParameter("contactInfo")));
      }
      if (!isMissing(request.getParameter("colorBorders"))) {
          m_logger.debug("updating param colorBorders from " +_SiteConfig.getColorBorders() + "->" + request.getParameter("colorBorders"));
          _SiteConfig.setColorBorders(WebParamUtil.getStringValue(request.getParameter("colorBorders")));
      }
      if (!isMissing(request.getParameter("siteGroup"))) {
          m_logger.debug("updating param siteGroup from " +_SiteConfig.getSiteGroup() + "->" + request.getParameter("siteGroup"));
          _SiteConfig.setSiteGroup(WebParamUtil.getIntValue(request.getParameter("siteGroup")));
      }
      if (!isMissing(request.getParameter("headerImage"))) {
          m_logger.debug("updating param headerImage from " +_SiteConfig.getHeaderImage() + "->" + request.getParameter("headerImage"));
          _SiteConfig.setHeaderImage(WebParamUtil.getStringValue(request.getParameter("headerImage")));
      }
      if (!isMissing(request.getParameter("headerAd"))) {
          m_logger.debug("updating param headerAd from " +_SiteConfig.getHeaderAd() + "->" + request.getParameter("headerAd"));
          _SiteConfig.setHeaderAd(WebParamUtil.getStringValue(request.getParameter("headerAd")));
      }
      if (!isMissing(request.getParameter("menuWidth"))) {
          m_logger.debug("updating param menuWidth from " +_SiteConfig.getMenuWidth() + "->" + request.getParameter("menuWidth"));
          _SiteConfig.setMenuWidth(WebParamUtil.getIntValue(request.getParameter("menuWidth")));
      }
      if (!isMissing(request.getParameter("adWidth"))) {
          m_logger.debug("updating param adWidth from " +_SiteConfig.getAdWidth() + "->" + request.getParameter("adWidth"));
          _SiteConfig.setAdWidth(WebParamUtil.getIntValue(request.getParameter("adWidth")));
      }
      if (!isMissing(request.getParameter("menuFrontColor"))) {
          m_logger.debug("updating param menuFrontColor from " +_SiteConfig.getMenuFrontColor() + "->" + request.getParameter("menuFrontColor"));
          _SiteConfig.setMenuFrontColor(WebParamUtil.getStringValue(request.getParameter("menuFrontColor")));
      }
      if (!isMissing(request.getParameter("menuBackColor"))) {
          m_logger.debug("updating param menuBackColor from " +_SiteConfig.getMenuBackColor() + "->" + request.getParameter("menuBackColor"));
          _SiteConfig.setMenuBackColor(WebParamUtil.getStringValue(request.getParameter("menuBackColor")));
      }
      if (!isMissing(request.getParameter("menuLineColor"))) {
          m_logger.debug("updating param menuLineColor from " +_SiteConfig.getMenuLineColor() + "->" + request.getParameter("menuLineColor"));
          _SiteConfig.setMenuLineColor(WebParamUtil.getStringValue(request.getParameter("menuLineColor")));
      }
      if (!isMissing(request.getParameter("menuReverse"))) {
          m_logger.debug("updating param menuReverse from " +_SiteConfig.getMenuReverse() + "->" + request.getParameter("menuReverse"));
          _SiteConfig.setMenuReverse(WebParamUtil.getIntValue(request.getParameter("menuReverse")));
      }
      if (!isMissing(request.getParameter("displayAllHome"))) {
          m_logger.debug("updating param displayAllHome from " +_SiteConfig.getDisplayAllHome() + "->" + request.getParameter("displayAllHome"));
          _SiteConfig.setDisplayAllHome(WebParamUtil.getIntValue(request.getParameter("displayAllHome")));
      }
      if (!isMissing(request.getParameter("headerImageHeight"))) {
          m_logger.debug("updating param headerImageHeight from " +_SiteConfig.getHeaderImageHeight() + "->" + request.getParameter("headerImageHeight"));
          _SiteConfig.setHeaderImageHeight(WebParamUtil.getIntValue(request.getParameter("headerImageHeight")));
      }
      if (!isMissing(request.getParameter("headerImageWidth"))) {
          m_logger.debug("updating param headerImageWidth from " +_SiteConfig.getHeaderImageWidth() + "->" + request.getParameter("headerImageWidth"));
          _SiteConfig.setHeaderImageWidth(WebParamUtil.getIntValue(request.getParameter("headerImageWidth")));
      }
      if (!isMissing(request.getParameter("layoutPage"))) {
          m_logger.debug("updating param layoutPage from " +_SiteConfig.getLayoutPage() + "->" + request.getParameter("layoutPage"));
          _SiteConfig.setLayoutPage(WebParamUtil.getStringValue(request.getParameter("layoutPage")));
      }
      if (!isMissing(request.getParameter("siteIconUrl"))) {
          m_logger.debug("updating param siteIconUrl from " +_SiteConfig.getSiteIconUrl() + "->" + request.getParameter("siteIconUrl"));
          _SiteConfig.setSiteIconUrl(WebParamUtil.getStringValue(request.getParameter("siteIconUrl")));
      }
      if (!isMissing(request.getParameter("underlyingHome"))) {
          m_logger.debug("updating param underlyingHome from " +_SiteConfig.getUnderlyingHome() + "->" + request.getParameter("underlyingHome"));
          _SiteConfig.setUnderlyingHome(WebParamUtil.getStringValue(request.getParameter("underlyingHome")));
      }
      if (!isMissing(request.getParameter("underlyingDynamicPage"))) {
          m_logger.debug("updating param underlyingDynamicPage from " +_SiteConfig.getUnderlyingDynamicPage() + "->" + request.getParameter("underlyingDynamicPage"));
          _SiteConfig.setUnderlyingDynamicPage(WebParamUtil.getIntValue(request.getParameter("underlyingDynamicPage")));
      }
      if (!isMissing(request.getParameter("contentForwardSite"))) {
          m_logger.debug("updating param contentForwardSite from " +_SiteConfig.getContentForwardSite() + "->" + request.getParameter("contentForwardSite"));
          _SiteConfig.setContentForwardSite(WebParamUtil.getStringValue(request.getParameter("contentForwardSite")));
      }
      if (!isMissing(request.getParameter("showMidColumn"))) {
          m_logger.debug("updating param showMidColumn from " +_SiteConfig.getShowMidColumn() + "->" + request.getParameter("showMidColumn"));
          _SiteConfig.setShowMidColumn(WebParamUtil.getIntValue(request.getParameter("showMidColumn")));
      }
      if (!isMissing(request.getParameter("showMenuColumn"))) {
          m_logger.debug("updating param showMenuColumn from " +_SiteConfig.getShowMenuColumn() + "->" + request.getParameter("showMenuColumn"));
          _SiteConfig.setShowMenuColumn(WebParamUtil.getIntValue(request.getParameter("showMenuColumn")));
      }
      if (!isMissing(request.getParameter("showAdColumn"))) {
          m_logger.debug("updating param showAdColumn from " +_SiteConfig.getShowAdColumn() + "->" + request.getParameter("showAdColumn"));
          _SiteConfig.setShowAdColumn(WebParamUtil.getIntValue(request.getParameter("showAdColumn")));
      }
      if (!isMissing(request.getParameter("showHomeMenu"))) {
          m_logger.debug("updating param showHomeMenu from " +_SiteConfig.getShowHomeMenu() + "->" + request.getParameter("showHomeMenu"));
          _SiteConfig.setShowHomeMenu(WebParamUtil.getIntValue(request.getParameter("showHomeMenu")));
      }
      if (!isMissing(request.getParameter("showHomeMid"))) {
          m_logger.debug("updating param showHomeMid from " +_SiteConfig.getShowHomeMid() + "->" + request.getParameter("showHomeMid"));
          _SiteConfig.setShowHomeMid(WebParamUtil.getIntValue(request.getParameter("showHomeMid")));
      }
      if (!isMissing(request.getParameter("showHomeAd"))) {
          m_logger.debug("updating param showHomeAd from " +_SiteConfig.getShowHomeAd() + "->" + request.getParameter("showHomeAd"));
          _SiteConfig.setShowHomeAd(WebParamUtil.getIntValue(request.getParameter("showHomeAd")));
      }
      if (!isMissing(request.getParameter("showTopMenu"))) {
          m_logger.debug("updating param showTopMenu from " +_SiteConfig.getShowTopMenu() + "->" + request.getParameter("showTopMenu"));
          _SiteConfig.setShowTopMenu(WebParamUtil.getIntValue(request.getParameter("showTopMenu")));
      }
      if (!isMissing(request.getParameter("midColumnWidth"))) {
          m_logger.debug("updating param midColumnWidth from " +_SiteConfig.getMidColumnWidth() + "->" + request.getParameter("midColumnWidth"));
          _SiteConfig.setMidColumnWidth(WebParamUtil.getIntValue(request.getParameter("midColumnWidth")));
      }
      if (!isMissing(request.getParameter("useWysiwygContent"))) {
          m_logger.debug("updating param useWysiwygContent from " +_SiteConfig.getUseWysiwygContent() + "->" + request.getParameter("useWysiwygContent"));
          _SiteConfig.setUseWysiwygContent(WebParamUtil.getIntValue(request.getParameter("useWysiwygContent")));
      }
      if (!isMissing(request.getParameter("useWysiwygPost"))) {
          m_logger.debug("updating param useWysiwygPost from " +_SiteConfig.getUseWysiwygPost() + "->" + request.getParameter("useWysiwygPost"));
          _SiteConfig.setUseWysiwygPost(WebParamUtil.getIntValue(request.getParameter("useWysiwygPost")));
      }
      if (!isMissing(request.getParameter("backgroundImage"))) {
          m_logger.debug("updating param backgroundImage from " +_SiteConfig.getBackgroundImage() + "->" + request.getParameter("backgroundImage"));
          _SiteConfig.setBackgroundImage(WebParamUtil.getStringValue(request.getParameter("backgroundImage")));
      }
      if (!isMissing(request.getParameter("backgroundRepeat"))) {
          m_logger.debug("updating param backgroundRepeat from " +_SiteConfig.getBackgroundRepeat() + "->" + request.getParameter("backgroundRepeat"));
          _SiteConfig.setBackgroundRepeat(WebParamUtil.getStringValue(request.getParameter("backgroundRepeat")));
      }
      if (!isMissing(request.getParameter("backgroundAttach"))) {
          m_logger.debug("updating param backgroundAttach from " +_SiteConfig.getBackgroundAttach() + "->" + request.getParameter("backgroundAttach"));
          _SiteConfig.setBackgroundAttach(WebParamUtil.getStringValue(request.getParameter("backgroundAttach")));
      }
      if (!isMissing(request.getParameter("backgroundColor"))) {
          m_logger.debug("updating param backgroundColor from " +_SiteConfig.getBackgroundColor() + "->" + request.getParameter("backgroundColor"));
          _SiteConfig.setBackgroundColor(WebParamUtil.getStringValue(request.getParameter("backgroundColor")));
      }
      if (!isMissing(request.getParameter("backgroundPosition"))) {
          m_logger.debug("updating param backgroundPosition from " +_SiteConfig.getBackgroundPosition() + "->" + request.getParameter("backgroundPosition"));
          _SiteConfig.setBackgroundPosition(WebParamUtil.getStringValue(request.getParameter("backgroundPosition")));
      }
      if (!isMissing(request.getParameter("contentBgColor"))) {
          m_logger.debug("updating param contentBgColor from " +_SiteConfig.getContentBgColor() + "->" + request.getParameter("contentBgColor"));
          _SiteConfig.setContentBgColor(WebParamUtil.getStringValue(request.getParameter("contentBgColor")));
      }
      if (!isMissing(request.getParameter("absolutePosition"))) {
          m_logger.debug("updating param absolutePosition from " +_SiteConfig.getAbsolutePosition() + "->" + request.getParameter("absolutePosition"));
          _SiteConfig.setAbsolutePosition(WebParamUtil.getIntValue(request.getParameter("absolutePosition")));
      }
      if (!isMissing(request.getParameter("absoluteTop"))) {
          m_logger.debug("updating param absoluteTop from " +_SiteConfig.getAbsoluteTop() + "->" + request.getParameter("absoluteTop"));
          _SiteConfig.setAbsoluteTop(WebParamUtil.getIntValue(request.getParameter("absoluteTop")));
      }
      if (!isMissing(request.getParameter("absoluteLeft"))) {
          m_logger.debug("updating param absoluteLeft from " +_SiteConfig.getAbsoluteLeft() + "->" + request.getParameter("absoluteLeft"));
          _SiteConfig.setAbsoluteLeft(WebParamUtil.getIntValue(request.getParameter("absoluteLeft")));
      }
      if (!isMissing(request.getParameter("functionalType"))) {
          m_logger.debug("updating param functionalType from " +_SiteConfig.getFunctionalType() + "->" + request.getParameter("functionalType"));
          _SiteConfig.setFunctionalType(WebParamUtil.getIntValue(request.getParameter("functionalType")));
      }
      if (!isMissing(request.getParameter("ecEnabled"))) {
          m_logger.debug("updating param ecEnabled from " +_SiteConfig.getEcEnabled() + "->" + request.getParameter("ecEnabled"));
          _SiteConfig.setEcEnabled(WebParamUtil.getIntValue(request.getParameter("ecEnabled")));
      }
      if (!isMissing(request.getParameter("frontDisplayFeed"))) {
          m_logger.debug("updating param frontDisplayFeed from " +_SiteConfig.getFrontDisplayFeed() + "->" + request.getParameter("frontDisplayFeed"));
          _SiteConfig.setFrontDisplayFeed(WebParamUtil.getIntValue(request.getParameter("frontDisplayFeed")));
      }

      SiteConfigDS.getInstance().update(_SiteConfig);
  }
    
    
    protected boolean loginRequired() {
        return true;
    }
    
    private static Logger m_logger = Logger.getLogger(SiteConfigAction.class);
}