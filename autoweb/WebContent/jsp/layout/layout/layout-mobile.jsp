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

	boolean displayMenuSection = false; // !siteConfigTrans.isHideMenu();
	boolean displayMidSection  = false; // !siteConfigTrans.isHideMid();
	boolean displayAdSection   = false; // !siteConfigTrans.isHideAd();

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
	<jsp:include page="/jsp/layout/layout/layout-mobile-include-header.jsp" />

<%-- ####################################### BODY  #################################################################### --%>
<body topmargin="0" bgcolor="#ffffff" > 
<% 
	//============================== Super Admin Control Menu ============================================
	long bodyStart = System.currentTimeMillis();
%>
<%-- ####################################### ADMIN MENUS ############################################################## --%>

<%-- ####################################### CONTENT-WRAPPER ######################################################### --%>
<div id="content-wrapper" class="content-wrapper">
<% if (displayMenuSection) { %>
<%-- ############################################################################################################# --%>
<%-- ####################################### MENU-SECTION ######################################################### --%>
<%-- ############################################################################################################# --%>
	<div id="menu-section-frame" class="menu-section-frame">
	<div id="menu-section" class="menu-section">
	
<%
		// Side menu associated with the jsp page.
		//System.out.println("sideMenu=" +  pageView.getSideMenu());
		if (pageView != null && pageView.getSideMenu() != null){
%>	
				<jsp:include page="<%=pageView.getSideMenu() %>" />
<%			
		}
%>	
<%
		//##################### SIDEMENU PANELS (1) ########################
		int numShow = 0;
		List panelColumn1 = PanelDS.getInstance().getBySiteIdColumnNum(site.getId(), 1);
		for (Iterator iter = panelColumn1.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();

			if (panel.getHide() == 0) numShow++;
		
			if ( panel.getColumnNum() == 1 && panel.getSiteId() == site.getId()) {
%>			
				<% session.setAttribute("p_current_panel", panel); %>
				<jsp:include page="<%=PanelUtil.getPanelPage(panel) %>" /> 
				<% session.removeAttribute("p_current_panel"); %>
<% 			
			}
		}
%>

<%	if (sessionContext.isSuperAdminLogin()) { %>
		[<a href="t_panel_add.html?prv_columnNum=1&prv_topSpace=15"><b>1+</b></a>]
<% } else if (numShow == 0) { %>
<% } %>
	&nbsp;
	</div><!-- menu-section -->		
	</div><!-- menu-section-frame -->		
	<div class="vertical-divider">&nbsp;</div>
<% 
	} // END of Menu section
%>

<%-- ############################################################################################################# --%>
<%-- ####################################### Header Low Panel ####################################################### --%>
<%-- ############################################################################################################# --%>
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



<%-- ############################################################################################################# --%>
<%-- ####################################### CONTENT-SECTION ######################################################### --%>
<%-- ############################################################################################################# --%>
	<div id="content-section-frame-printable" class="content-section-frame-printable">
	<div id="content-section" class="content-section">

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
	
	//contentPage = "/jsp/contents/home_mobile_phone.jsp";
	
	long contentTime = 0;
	try{
		long beforeContent = System.currentTimeMillis();
%>
	<!-- a href="javascript:window.print()">Print This Page</a -->
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
</div><!-- content-section -->
</div><!-- content-section-frame -->

<% if (displayMidSection) { %>
<div id="vertical-divider"></div>
<%-- ############################################################################################################# --%>
<%-- ####################################### MID-SECTION ######################################################### --%>
<%-- ############################################################################################################# --%>

<div id="mid-section" class="mid-section-frame">	
<div id="mid-section" class="mid-section">	
	<div id="mid-content">
		<jsp:include page="/jsp/contents/dynamic_menu_content_working2_mid_column.jsp" />
	</div>		
<%

		//##################### MID PANELS ########################
		List panelColumn3 = PanelDS.getInstance().getBySiteIdColumnNum(site.getId(), 3);
		for (Iterator iter = panelColumn3.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 3 && panel.getSiteId() == site.getId()) {
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
		[<a href="t_panel_add.html?prv_columnNum=3&prv_topSpace=15"><b>3+</b></a>]
<% 
	}
%>		
</div><!-- mid section -->
</div><!-- mid section-frame -->

<% } // Mid Column%>


<% if (displayAdSection) { %>
<div id="vertical-divider"></div>
<%-- ############################################################################################################# --%>
<%-- ####################################### AD-SECTION ######################################################### --%>
<%-- ############################################################################################################# --%>
<div id="ad-section-frame" class="ad-section-frame">
<div id="ad-section" class="ad-section">

<%
		// Side menu associated with the jsp page. 
		if (pageView != null && !WebUtil.isNull(pageView.getSideAd())){
%>	
				<jsp:include page="<%=pageView.getSideAd() %>" />
<%			
		}
%>	


<%
		//##################### AD PANELS ########################
		List panelColumn4 = PanelDS.getInstance().getBySiteIdColumnNum(site.getId(), 4);

		for (Iterator iter = panelColumn4.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 4 && panel.getSiteId() == site.getId()) {
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
		[<a href="t_panel_add.html?prv_columnNum=4&prv_topSpace=15"><b>4+</b></a>]
<% 
	}
%>
</div><!-- ad section -->
</div><!-- ad section-frame -->
<% } %>

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
</div><!-- footer-wrapper -->

</div><!-- wrapper -->
<!-- div id="rightSpace" style="width:50px;background-color:transparent;float:left;height:100%"> &nbsp;</div-->
</div><!-- out-frame -->
<%-- ####################################### END-MAIN-WRAPPER ######################################################### --%>

<%
long endPage 	= System.currentTimeMillis();
long total 		= endPage - startPage;
long headTime 	= bodyStart - startPage;

if (sessionContext.isSuperAdminLogin()) {
	WebDebug.getInstance().putDebug(session, "Total Time=", new Long(total));	
	WebDebug.getInstance().putDebug(session, "Head Time=", new Long(headTime));	
	WebDebug.getInstance().putDebug(session, "Content Time=", new Long(contentTime));
}	
%>
<%-- ####################################### ENDING-WRAPPER ######################################################### --%>
<!--  
<div id="sample-fixedbar" style="display:none" >
        <ul>
            <li title="Home"><a href="http://jixedbar.rawswift.com/"><img class="jixedbar-img" src="/js/jixedbar/icons/home.png" alt="" /></a></li>
        </ul>
        <span class="jx-separator-left"></span>
		<ul>        
            <li title="Around The Web"><a href="#"><img class="jixedbar-img" src="/js/jixedbar/icons/network.png" alt="Get Social" /></a>
                <ul>
                    <li><a href="http://www.facebook.com/jixedbar"><img src="/js/jixedbar/icons/facebook.png" alt="" />&nbsp;&nbsp;&nbsp;Facebook</a></li>

                    <li><a href="http://twitter.com/jixedbar"><img src="/js/jixedbar/icons/twitter.png" alt="" />&nbsp;&nbsp;&nbsp;Twitter</a></li>
                    <li><a href="http://www.ohloh.net/p/jixedbar"><img src="/js/jixedbar/icons/ohloh.png" alt="" />&nbsp;&nbsp;&nbsp;Ohloh</a></li>
                    <li><a href="http://code.google.com/p/jixedbar/"><img src="/js/jixedbar/icons/google.png" alt="" />&nbsp;&nbsp;&nbsp;Google Code</a></li>
                </ul>
            </li>
        </ul>
        <span class="jx-separator-left"></span>        
		<div class="text-container">Fresh from the oven! <a href="http://fb.me/DIRKLhNu">jixedbar 0.0.3 (Beta)</a></div>

        <span class="jx-separator-left"></span>
		<iframe src="http://www.facebook.com/plugins/like.php?href=http%3A%2F%2Fwww.facebook.com%2Fjixedbar&amp;layout=standard&amp;show_faces=false&amp;width=450&amp;action=like&amp;colorscheme=light&amp;height=30" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:450px; height:30px;" allowTransparency="true"></iframe>
        <ul class="jx-bar-button-right">
			<li title="Bookmark This Site"><a href="#" onclick="bookmark(location.href, 'jixedbar - jQuery fixed bar'); return false;"><img src="/js/jixedbar/icons/bookmark.png" alt="" /></a></li>
        </ul>
        <span class="jx-separator-right"></span>        
        
</div>
-->
<div id="shaker" style="display:none;">
		<a href="/"><img src="/images/mootools-shake.png" alt="David Walsh Blog" /></a>
</div>

<div id="fixed-left" style="display:none;">
	<div class="section">
		<a href="http://www.yahoo.com">Yahoo!</a>
	</div>
	<div class="section">
		<a href="http://www.yahoo.com">Yahoo!</a>
	</div>
	<div class="section">
		<a href="http://www.yahoo.com">Yahoo!</a>
	</div>
	<div class="section">
		<a id="openfield" href="http://www.yahoo.com">Yahoo!</a>
	</div>
	
	<div id="fieldd" style="display:none">
	<form >
		test field
		<input type="text" size="20" name="test" value=""/>
		
		test field
		<textarea name="test" ></textarea>
		
	</form>
	</div>
</div>

<!--  div class="fb-button">
<a name="fb_share" type="box_count" href="http://www.facebook.com/sharer.php">Share</a><script src="http://static.ak.fbcdn.net/connect.php/js/FB.Share" type="text/javascript"></script>
</div -->

<div id="ajaxMenuPageItemFormBlock" style="display:none;">
<form name="ajaxMenuPageItemForm" method="POST"	action="/menuItemAction.html" id="ajaxMenuPageItemForm">
	<INPUT TYPE="HIDDEN" NAME="panelId" value="338">
	<div class="ajaxFormLabel" style="font-weight: bold;">
		Title
	</div>
	<INPUT NAME="title" type="text" size="70" value=""></INPUT>
	<br />
	<div class="ajaxFormLabel" style="font-weight: bold;">
		Data
	</div>
	<INPUT NAME="data" type="text" size="70" value=""></INPUT>
	<br />
	<INPUT TYPE="HIDDEN" NAME="targetType" value="1">
	<INPUT TYPE="HIDDEN" NAME="ajaxRequest" value="true">
	<INPUT TYPE="HIDDEN" NAME="ajaxOut" value="getmodalstatus">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">
	<INPUT TYPE="HIDDEN" NAME="wpid" value="25:1670249869836828">
</form>

<span id="ajaxMenuPageItemSubmitResult"></span>
<a id="ajaxMenuPageItemSubmit"	href="javascript:sendFormAjax('/menuItemAction.html','ajaxMenuPageItemForm', 'ajaxMenuPageItemSubmit', 'ajaxMenuPageItemSubmitResult');">Submit</a>
</div>

<div id="ajaxMenuPageItemFormBlock2" style="display:none;">
<img src="/images/ocean.jpg"/>
</div>

<div id="ajaxMenuPageItemFormBlock3" style="display:none;">
<%
	List pagesInMenu = PageDS.getInstance().getBySiteId(site.getId());

	StringBuffer buf = new StringBuffer();
	
	for(Iterator iter = pagesInMenu.iterator();iter.hasNext();) {

    	Page pg = (Page) iter.next();
		
    	buf.append(pg.getId()).append("/").append(pg.getPageMenuTitle()).append(":");
	}
%>
<%=buf.toString() %>
</div>
</body>
</html>

<%
	JspUtil.getLogger().info("---------------------------------------------------------------------------------------------------");
	JspUtil.getLogger().info("-- layout-css END ---------------------------------------------------------------------------------");
	JspUtil.getLogger().info("---------------------------------------------------------------------------------------------------");
%>



<%!

//NOT WORKING
protected String displayPanels(Site site, int col) {

    String ret = "";
    
	List panels = PanelDS.getInstance().getBySiteIdColumnNum(site.getId(), col);
	for (Iterator iter = panels.iterator();iter.hasNext();){
		Panel panel = (Panel) iter.next();
	
		if ( panel.getColumnNum() == col && panel.getSiteId() == site.getId()) {

			ret += "<jsp:include page=\"" + PanelUtil.getPanelPage(panel) + "\" >";
//			ret += "<jsp:param name=\"p_panel_id\" value=\""+ panel.getId() +"\" />";
			ret += "</jsp:include>";
		}
	}
    
    return ret;
}

%>
