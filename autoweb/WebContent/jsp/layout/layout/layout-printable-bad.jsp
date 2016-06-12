<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.seox.util.*,com.autosite.db.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<% 
	JspUtil.getLogger().info("---------------------------------------------------------------------------------------------------");
	JspUtil.getLogger().info("-- layout-css -------------------------------------------------------------------------------------");
	JspUtil.getLogger().info("---------------------------------------------------------------------------------------------------");

	long startPage = System.currentTimeMillis();
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (sessionContext == null)   
		sessionContext = AutositeSessionContext.create(site.getSiteUrl(), session);

	int userType = (sessionContext != null? sessionContext.getUserType(): Constants.UserAnonymous);

	Site siteForDomain = SiteDS.getInstance().registerSite(request.getServerName(), false);

	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    SiteConfig siteConfigForDomain = siteForDomain ==null?null:SiteConfigDS.getInstance().getSiteConfigBySiteId(siteForDomain.getId()); // To get siteconfig for a site that is not forwarded. 
	SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(site.getId());

	String topText 		= (String) pageContext.findAttribute("k_top_text");
	String topErrText 	= (String) pageContext.findAttribute("k_top_error_text");
	String pageSize 	= (String) pageContext.findAttribute("k_view_pagesize");
	String pageFull 	= (String) pageContext.findAttribute("k_view_pagefull");

	System.out.println("Error text " + topErrText);
	
	String keywords 	= request.getServerName();
	String pageTitle 	= "";
	String googleTrack 	= "";
	boolean siteInitialized = false;
	boolean siteRegistered 	= WebParamUtil.getBooleanValue(site.getRegistered());
	boolean siteOnSale 		= WebParamUtil.getBooleanValue(site.getOnSale());
	
	if (siteConfig !=null) {	
		keywords += "," + siteConfig.getKeywords();
		siteInitialized = true;
	} else {
		siteConfig = SiteConfigDS.getDefaultSiteConfig(); 
	}

	if (siteConfigForDomain != null) {
		keywords += "," + siteConfigForDomain.getKeywords();
		pageTitle = siteConfigForDomain.getMeta();
		googleTrack = siteConfigForDomain.getSiteTrackGoogle();
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
//	String pageName = (String) session.getAttribute("k_page_name");
//	Page dynPage = (pageName == null? null:PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName));
//	String pageName = (String) session.getAttribute("k_page_name");
//	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
	Page dynPage = DynPageUtil.getCurrentPage(request, site.getId());
	
	String pageId = (dynPage == null?"0":""+dynPage.getId());
	
	if (dynPage != null) { 
	    pageTitle += " - " + dynPage.getPageMenuTitle();
	} else if ( pageView != null) {
	    pageTitle += " - " + pageView.getAlias();
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
	
	// Get 1 page view
	JspUtil.getLogger().info("siteRegistered=" + siteRegistered); 
	JspUtil.getLogger().info("isHome=" + wrap.isHome()); 
	
	if (wrap.isHome() && WebUtil.isTrue(siteConfig.getFrontDisplayFeed())) {
	
		PageView feedPageView = com.jtrend.struts.core.DefaultViewManager.getInstance().getPageView("content_feed_display");
		if (feedPageView != null) {
			pageView = feedPageView;
			JspUtil.getLogger().info("pageView changed to Feed Display"); 
		}
	} 
%>
<%= docType%>

<html> 
<%-- ####################################### HEADER INCLUSION ######################################################### --%>
	<jsp:include page="/jsp/layout/layout/layout-include-header.jsp" />

<%-- ####################################### BODY  #################################################################### --%>
<body topmargin="0" bgcolor="#ffffff" style="width:100%" > 

<div id="out-frame-printable" > 
<div id="wrapper-printable" style="margin: 0 0 0 0;float:left" class="wrapper-printable">

<%-- ####################################### HEADER-WRAPPER ######################################################### --%>
<div id="header-wrapper" class="header-wrapper" style="visibility:hidden;">

<div id="header-upper-section" class="header-upper-section">
<!-- #### HEADER PANELS ####### -->
<%
		List panelColumn6 = PanelDS.getInstance().getBySiteIdColumnNum(site.getId(), 6);
		for (Iterator iter = panelColumn6.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 6 && panel.getSiteId() == site.getId()) {
%>			
				<% session.setAttribute("p_current_panel", panel); %>
				<jsp:include page="<%=PanelUtil.getPanelPage(panel) %>"/>
				<% session.removeAttribute("p_current_panel"); %>
<% 			
			}
		}
%>
<% 
	if (sessionContext.isSuperAdminLogin()) {
%>
	[<a href="t_panel_add.html?prv_columnNum=6&prv_align=1&prv_topSpace=0" ><b>6+(header-upper)</b></a>]
<% 
	}
%>
</div> <!-- header-upper-section -->

<%-- ####################################### Header Low Panel ####################################################### --%>
<div id="header-lower-section" class="header-lower-section">
<%
		//##################### HEADER LOW  PANELS ########################
		List panelColumn5 = PanelDS.getInstance().getBySiteIdColumnNum(site.getId(), 5);
		for (Iterator iter = panelColumn5.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 5 && panel.getSiteId() == site.getId()) {
%>			
				<% session.setAttribute("p_current_panel", panel); %>
				<jsp:include page="<%=PanelUtil.getPanelPage(panel) %>" />
				<% session.removeAttribute("p_current_panel"); %>
<% 			
			}
		}
%>
</div> <!-- header-lower-section -->
<% 
	if (sessionContext.isSuperAdminLogin()) {
%>
		[<a href="t_panel_add.html?prv_columnNum=5&prv_align=1&prv_topSpace=0" ><b>5+(header-lower)</b></a>]
<% 
	}
%>
</div><!-- END:header-wrapper -->

<%-- ####################################### CONTENT-WRAPPER ######################################################### --%>
<div id="content-wrapper" class="content-wrapper">

<%-- ############################################################################################################# --%>
<%-- ####################################### CONTENT-SECTION ######################################################### --%>
<%-- ############################################################################################################# --%>
	<div id="content-section-frame-printable" class="content-section-frame-printable" style="width:100%">
	<div id="content-section" class="content-section">

<%
	if ( sessionContext != null && sessionContext.isSuperAdminLogin()){
%>
		<jsp:include page="/jsp/sidemenus/page_control_menu.jsp"/>
<%
	} 
%>

<%
		//##################### CONTENT TOP PANELS ########################
		List panelColumn7 = PanelDS.getInstance().getBySiteIdColumnNum(site.getId(), 7);
		for (Iterator iter = panelColumn7.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 7 && panel.getSiteId() == site.getId()) {
%>			
				<% session.setAttribute("p_current_panel", panel); %>
				<jsp:include page="<%=PanelUtil.getPanelPage(panel) %>" />
				<% session.removeAttribute("p_current_panel"); %>				
<% 			
			}
		}
%>

<% 
	if (sessionContext.isSuperAdminLogin()) {
%>
				[<a href="t_panel_add.html?prv_columnNum=7&prv_topSpace=0"><b>7+(content-upper)</b></a>]
<% 
	}
%>

<%
	if ((topText != null && !topText.equals("")) || (topErrText != null && !topErrText.equals("") ))  {
%>
	<jsp:include page="/jsp/layout/toptext.jsp" />
<%
	} 
%>
<%
	String contentPage = pageView.getContentPage();
	
	if (!siteInitialized) {
		if ( !sessionContext.isSuperAdmin() )
			contentPage = "/jsp/layout/home_uninit.jsp";
	}
	
	long contentTime = 0;
	try{
		long beforeContent = System.currentTimeMillis();
%>
	<div id="main-content" class="main-content">
		<jsp:include page="<%=contentPage%>" />
	</div><!-- main-content -->		
<%
		long afterContent = System.currentTimeMillis();
		contentTime = afterContent - beforeContent;
	}catch(Exception e){
		JspUtil.getLogger().error(e.getMessage(), e);
	}
%>
<%
		//##################### CONTENT BOTTOM PANELS ########################
		List panelColumn2 = PanelDS.getInstance().getBySiteIdColumnNum(site.getId(), 2);
		for (Iterator iter = panelColumn2.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 2 && panel.getSiteId() == site.getId()) {
%>			
				<% session.setAttribute("p_current_panel", panel); %>
				<jsp:include page="<%=PanelUtil.getPanelPage(panel) %>" />
				<% session.removeAttribute("p_current_panel"); %>				
<% 			
			}
		}
%>				
<% 
	if (sessionContext.isSuperAdminLogin()) {
%>
				[<a href="t_panel_add.html?prv_columnNum=2&prv_topSpace=15"><b>2+</b></a>]
<% 
	}
%>
</div><!-- content-section -->
</div><!-- content-section-frame -->
</div><!-- content wrapper -->
<div class="clear"></div>

<%-- ####################################### FOOTER-WRAPPER ######################################################### --%>
<div id="footer-wrapper" class="footer-wrapper">
<%
		List panelColumn9 = PanelDS.getInstance().getBySiteIdColumnNum(site.getId(), 9);
		for (Iterator iter = panelColumn9.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 9 && panel.getSiteId() == site.getId()) {
%>			
				<% session.setAttribute("p_current_panel", panel); %>
				<jsp:include page="<%=PanelUtil.getPanelPage(panel) %>" />
				<% session.removeAttribute("p_current_panel"); %>
<% 			
			}
		}
%>
<% 
	if (sessionContext.isSuperAdminLogin()) {
%>
	[<a href="t_panel_add.html?prv_columnNum=9&prv_align=1&prv_topSpace=0" ><b>9+(footer)</b></a>]
<% 
	}
%>
</div><!-- footer-wrapper -->
</div><!-- wrapper -->
</div><!-- out-frame -->
<%-- ####################################### END-MAIN-WRAPPER ######################################################### --%>

</body>
</html>

<%
	JspUtil.getLogger().info("---------------------------------------------------------------------------------------------------");
	JspUtil.getLogger().info("-- layout-css END ---------------------------------------------------------------------------------");
	JspUtil.getLogger().info("---------------------------------------------------------------------------------------------------");
%>

