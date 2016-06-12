<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.seox.util.*,com.autosite.db.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<% 
	JspUtil.getLogger().info("###################################################################################################");
	JspUtil.getLogger().info("###################################################################################################");

	long startPage = System.currentTimeMillis();
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	if (sessionContext == null)  
		sessionContext = AutositeSessionContext.create(session);

	int userType = (sessionContext != null? sessionContext.getUserType(): Constants.UserAnonymous);

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(site.getId());

	String topText = (String) session.getAttribute("k_top_text");
	String topErrText = (String) session.getAttribute("k_top_error_text");
	String pageSize = (String) session.getAttribute("k_view_pagesize");
	String pageFull = (String) session.getAttribute("k_view_pagefull");

	String keywords = request.getServerName();
	String pageTitle = "";
	String googleTrack = "";
	boolean siteInitialized = false;
	boolean siteRegistered = WebParamUtil.getBooleanValue(site.getRegistered());
	boolean siteOnSale = WebParamUtil.getBooleanValue(site.getOnSale());
	
	if (siteConfig !=null) {	
		keywords += "," + siteConfig.getKeywords();
		pageTitle = siteConfig.getMeta();
		googleTrack = siteConfig.getSiteTrackGoogle();
		siteInitialized = true;
	} else {
		siteConfig = SiteConfigDS.getDefaultSiteConfig(); 
	}

	if (WebUtil.isNull(pageTitle)){
	    pageTitle = site.getSiteUrl();
	}        

	//
	SessionWrapper wrap = SessionWrapper.wrapIt(request, site.getId());

	// Get Page View
	PageView pageView = wrap.getViewPage(); //(PageView) session.getAttribute("k_view_pageview");
	
	if ( pageView == null) {
		pageView = PageView.getDefaultPageView();
	}
	
	// Get current page.		
	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = (pageName == null? null:PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName));
	String pageId = (dynPage == null?"0":""+dynPage.getId());

	if (dynPage != null) { 
	    pageTitle += " - " + dynPage.getPageMenuTitle();
	}
	
	// Get Content if this page is content page then get PageConfig and append the keywords. 
	Content pageContent = (Content)session.getAttribute("k_page_content");
	ContentConfig contentConfig = null; 
	if ( pageContent != null){
	    contentConfig = ContentConfigDS.getInstance().getBySiteIdToContentId(site.getId(), pageContent.getId());
	    
	    if ( contentConfig != null)
	    	keywords += WebUtil.display(contentConfig.getKeywords()) + ",";

	    if (WebUtil.isNotNull(pageContent.getContentSubject())) 
	        pageTitle += " ( " + pageContent.getContentSubject() + " )";
	}
	
	// Page Config
	PageConfig pageConfig = null;
	if (dynPage != null)
		pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());

	// Layout size, hide
	String width= (siteConfig.getWidth()==0?"1000":"" + (siteConfig.getWidth()+ siteConfigTrans.getWidthOffset()));
	
	if ( !width.endsWith("%") ) width +="px"; 
	
	int sideSize = siteConfig.getMenuWidth();
	int adSize = siteConfig.getAdWidth();
	int midColumnSize = siteConfig.getMidColumnWidth();

	boolean displayMenuSection = !siteConfigTrans.isHideMenu();
	boolean displayMidSection = !siteConfigTrans.isHideMid();
	boolean displayAdSection = !siteConfigTrans.isHideAd();

	boolean displayUpperMenu = false;

	if (pageFull != null && pageFull.equals("true") ) {
		width = "100%";
	} 
	
	//Panels	
	List panels = PanelDS.getInstance().getBySiteId(site.getId());

	// Debugging pupose
	WebUtil.printSessionKeys(session);   
	
	// Page Config 
	

	// css id. need this not to confuse the browser when click on back button. 
	
	String cssId = "" + pageId;
	if ( !wrap.isDynPage2()){
		cssId = wrap.getViewPage().getAlias();
	}

	//==================================================================================
	// Doc type 
	//==================================================================================
//<!-- DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"-->
//<!--  DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" -->
	//String msDocType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Strict//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">";
	//String msDocType = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";
	String msDocType = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
	String ffDocType = "<! DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">";

	// Browser type
	boolean isMSIE = (WebUtil.getUserAgentType(request.getHeader("user-agent")) == WebUtil.USERAGENT_MSIE);
	
	String docType = (isMSIE?msDocType:ffDocType);
	
	//Site Icon
	
	String siteIconUrl = "/images/fav/favicon.ico";
	if (!WebUtil.isNull(siteConfig.getSiteIconUrl())){
		siteIconUrl = siteConfig.getSiteIconUrl();
	}
	
	// Get feed page view
	JspUtil.getLogger().info("siteRegistered=" + siteRegistered); 
	JspUtil.getLogger().info("isHome=" + wrap.isHome()); 
	
	if (!siteRegistered && wrap.isHome() && WebUtil.isTrue(siteConfig.getFrontDisplayFeed())) {
	
		PageView feedPageView = com.jtrend.struts.core.DefaultViewManager.getInstance().getPageView("content_feed_display");
		if (feedPageView != null) {
			pageView = feedPageView;
			JspUtil.getLogger().info("pageView changed to Feed Display"); 
		}
	} 
	
%>


<%

	String contentPage = pageView.getContentPage();
	
	if (siteRegistered) {
	
		if (!siteInitialized) {
			if ( !sessionContext.isSuperAdmin() )
				contentPage = "/jsp/layout/home_uninit.jsp";
		}
	
	} else {
			if (WebUtil.isTrue(siteConfig.getFrontDisplayFeed())){
				if ( !sessionContext.isSuperAdmin() )
					contentPage = "/jsp/contents/feed/contentFeedDisplay.jsp";
			} else {
				if ( !sessionContext.isSuperAdmin() )
					contentPage = "/jsp/layout/home_unregister.jsp";
			}
	}
	
	long contentTime = 0;
	try{
		long beforeContent = System.currentTimeMillis();
		
%>
	
	<div id="main-content">
			<jsp:include page="<%=contentPage%>" />
	</div>		

<%
		long afterContent = System.currentTimeMillis();
		contentTime = afterContent - beforeContent;
	}catch(Exception e){
		JspUtil.getLogger().error(e.getMessage(), e);
	}
	
%>
