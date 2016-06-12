<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.seox.util.*,com.autosite.db.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<% 
	long startPage = System.currentTimeMillis();
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (sessionContext == null) 
		sessionContext = AutositeSessionContext.create(site.getSiteUrl(), session);

	int userType = (sessionContext != null? sessionContext.getUserType(): Constants.UserAnonymous);
	boolean isLogin = (sessionContext != null? sessionContext.isLogin():false);

    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(site.getId());

	SessionWrapper wrap = SessionWrapper.wrapIt(request, site.getId());

	PageView pageView = wrap.getViewPage(); //(PageView) session.getAttribute("k_view_pageview");
	
	if ( pageView == null) {
		pageView = PageView.getDefaultPageView();
	}

	String topText = (String) session.getAttribute("k_top_text");
	String topErrText = (String) session.getAttribute("k_top_error_text");
	String pageSize = (String) session.getAttribute("k_view_pagesize");
	String pageFull = (String) session.getAttribute("k_view_pagefull");

//	String keywords = (String) session.getAttribute("k_site_config_keywords");

	String keywords = request.getServerName();
	String meta = "";
	String googleTrack = "";
	boolean siteInitialized = false;
	boolean siteRegistered = WebParamUtil.getBooleanValue(site.getRegistered());
	boolean siteOnSale = WebParamUtil.getBooleanValue(site.getOnSale());
	
	if (siteConfig !=null) {	
		keywords += "," + siteConfig.getKeywords();
		meta = siteConfig.getMeta();
		googleTrack = siteConfig.getSiteTrackGoogle();
		siteInitialized = true;
	} else {
		siteConfig = SiteConfigDS.getDefaultSiteConfig(); 
	}

	// Get current page.		
	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = (pageName == null? null:PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName));
	String pageId = (dynPage == null?"0":""+dynPage.getId());


	PageConfig pageConfig = null;
	if (dynPage != null)
		pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());

	//siteConfig = (SiteConfig) session.getAttribute("k_site_config");


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
	
	// Colors
	
	String menuBackColor = "#ffffff";
	if (!WebUtil.isNull(siteConfig.getMenuBackColor()))
		menuBackColor = siteConfig.getMenuBackColor();

	//Panels	
	List panels = PanelDS.getInstance().getBySiteId(site.getId());


	// Debugging pupose
	if (false){
    Enumeration names = session.getAttributeNames();
	System.out.println(">> SESSION KEYS ==================================");            
    while(names.hasMoreElements()){
		String name = (String) names.nextElement();
		Object val = session.getAttribute(name);
		System.out.println(">> " + name + "=" + val);            
     }
	System.out.println(">> END SESSION KEYS ==================================");    
	}
	if (WebUtil.isNull(meta)){
		meta = site.getSiteUrl();
	}        

	// Browser type

	boolean isMSIE = (WebUtil.getUserAgentType(request.getHeader("user-agent")) == WebUtil.USERAGENT_MSIE);
	
	// Page Config 
	

	// css id. need this not to confuse the browser when click on back button. 
	
	String cssId = "" + pageId;
	if ( !wrap.isDynPage2()){
		cssId = wrap.getViewPage().getAlias();
	}

	// Doc type
//<!-- DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"-->
//<!--  DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" -->
	String msDocType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">";
	String ffDocType = "<! DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">";
	
	String docType = (isMSIE?msDocType:ffDocType);
	System.out.println("xxxxxxxxxxxxxxxxxxxx");
%>
<%= docType%>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title><%= meta %></title>
<link REL="SHORTCUT ICON" HREF="/images/fav/<%=site.getId()%>/favicon.ico"/>
<style>
td {font-family: verdana; margin: 0 1px 0 0; font-size: 11px;}
</style> 
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="keywords" content="<%=keywords%>,<%=(dynPage==null||dynPage.getPageKeywords()==null)?"": dynPage.getPageKeywords()%>"/>
<meta http-equiv="description" content=""/>
<link rel="stylesheet" type="text/css" media="screen" href="/main.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="/layout.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="/cscr/main.css?id=<%=cssId%>"/>

<link rel="stylesheet" type="text/css" media="screen" href="/jquery_test.css"/>

<link rel="stylesheet" type="text/css" media="screen" href="/ec_main.css"/>

<link rel="stylesheet" type="text/css" media="screen" href="/blog_main.css"/>

<link rel="stylesheet" type="text/css" media="screen" href="/poll.css"/>



<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/scripts.js"> </script>

<script type="text/javascript" src="/jquery-1.3.2.js"> </script>


<!-- ======================== MLDD ======================== --> 

<script type="text/javascript" src="/menuscripts/mldd/mlddmenu.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="/menuscripts/mldd/mlddmenu.css" />
<link rel="stylesheet" type="text/css" media="all" href="/menuscripts/mldd/default.css" />

<!--  ======================== Mega menu ======================== -->
<link rel="stylesheet" type="text/css" href="/menuscripts/megamenu/jkmegamenu.css" />
<script type="text/javascript" src="/menuscripts/megamenu/jkmegamenu.js">
/***********************************************
* jQuery Mega Menu- by JavaScript Kit (www.javascriptkit.com)
* This notice must stay intact for usage
* Visit JavaScript Kit at http://www.javascriptkit.com/ for full source code
***********************************************/
</script>

<!-- ======================== jquery UI ======================== 

<link type="text/css" href="/js/jquery-ui-flick/jquery-ui-1.7.2.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="/js/jquery-ui-flick/jquery-ui-1.7.2.custom.min.js"></script>
 -->

<!-- ======================== Accordion menu Test ======================== -->

<link type="text/css" href="/js/accordion/accordion1.css" rel="stylesheet" />	

<!-- ======================== DDAccordion menu Test ======================== -->


<link type="text/css" href="/js/accordion/ddaccordion.css" rel="stylesheet" />	

<script type="text/javascript" src="/js/accordion/ddaccordion.js">

/***********************************************
* Accordion Content script- (c) Dynamic Drive DHTML code library (www.dynamicdrive.com)
* Visit http://www.dynamicDrive.com for hundreds of DHTML scripts
* This notice must stay intact for legal use
***********************************************/

</script>


<script type="text/javascript">

ddaccordion.init({
	headerclass: "submenuheader", //Shared CSS class name of headers group
	contentclass: "submenu", //Shared CSS class name of contents group
	revealtype: "click", //Reveal content when user clicks or onmouseover the header? Valid value: "click", "clickgo", or "mouseover"
	mouseoverdelay: 200, //if revealtype="mouseover", set delay in milliseconds before header expands onMouseover
	collapseprev: true, //Collapse previous content (so only one open at any time)? true/false 
	defaultexpanded: [], //index of content(s) open by default [index1, index2, etc] [] denotes no content
	onemustopen: false, //Specify whether at least one header should be open always (so never all headers closed)
	animatedefault: false, //Should contents open by default be animated into view?
	persiststate: true, //persist state of opened contents within browser session?
	toggleclass: ["", ""], //Two CSS classes to be applied to the header when it's collapsed and expanded, respectively ["class1", "class2"]
	togglehtml: ["suffix", "<img src='/js/accordion/plus.gif' class='statusicon' />", "<img src='/js/accordion/minus.gif' class='statusicon' />"], //Additional HTML added to the header when it's collapsed and expanded, respectively  ["position", "html1", "html2"] (see docs)
	animatespeed: "fast", //speed of animation: integer in milliseconds (ie: 200), or keywords "fast", "normal", or "slow"
	oninit:function(headers, expandedindices){ //custom code to run when headers have initalized
		//do nothing
	},
	onopenclose:function(header, index, state, isuseractivated){ //custom code to run whenever a header is opened or closed
		//do nothing
	}
})


</script>

<!-- ======================== Ajaxify ======================== -->
<script type="text/javascript" src="/js/ajaxify/jquery.ajaxify.js"></script> 

<script src="/js/ajaxify/jquery.livequery.pack.js" type="text/javascript"></script>
<script src="/js/ajaxify/jquery.history.fixed.js" type="text/javascript"></script>
<script src="/js/ajaxify/jquery.simplemodal-1.1.1.js" type="text/javascript"></script>
<script src="/js/ajaxify/jquery.metadata.min.js" type="text/javascript"></script>


<!-- ======================== JQuery tools http://flowplayer.org/index.html ========================  

<script src="js/jquery-tools/min/jquery.tools.1.1.2.js"></script>
-->

<!-- ======================== Flex popup ======================== 
<link rel="stylesheet" type="text/css" href="/menuscripts/flexpopup/popupmenu.css" />

<script type="text/javascript" src="/menuscripts/flexpopup/popupmenu.js">

/***********************************************
* Flex Level Popup Menu- (c) Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/dynamicindex1/popupmenu.htm for this script and 100s more
***********************************************/

</script>
-->

<!-- ======================== jquery ui ======================== --> 

<link type="text/css" href="/js/jquery-ui-flick/jquery-ui-1.7.2.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="/js/jquery-ui-flick/jquery-ui-1.7.2.custom.min.js"></script>


<script type="text/javascript" src="/jquery_test.js"> </script>
<script type="text/javascript" src="/ec_main.js"> </script>
<script type="text/javascript" src="/blog_main.js"> </script>
<script type="text/javascript" src="/poll.js"> </script>


<!-- ############## Dynamic Script Imports ############## -->

<% if (pageConfig != null && !WebUtil.isNull(pageConfig.getPageCss())) { %>
<link rel="stylesheet" type="text/css" media="screen" href="/pscr/pageManual.css?id=<%= pageId%>"/>
<% } %>

<% if (pageConfig != null && pageConfig.getStyleId() >0) { %>
<link rel="stylesheet" type="text/css" media="screen" href="/pscr/pageStyle.css?id=<%= pageId%>"/>

<% } %>

<% if (pageConfig != null && !WebUtil.isNull(pageConfig.getPageScript())) { %>
<script type="text/javascript" src="/pscr/page.js?id=<%= pageId%>"></script>
<% } %>


<%= StyleConfigUtil.getPageImports(pageConfig) %>

<% if (site.getId() == 29){ %>
<script type="text/javascript" src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAd8xPFM_iKUWkK1lxTFucLxSJ_ahltiIPNC3Zy7kdx45wbNXxvRTbtNchT2FR6CSGJ9Pzms3RMt2qBw"></script>
<% } %>

</head>

<body topmargin="0" bgcolor="#ffffff" padding="0" >

<div id="outFrame" style=""> 

<div id="leftSpace" style="width:50px;background-color:transparent ;float:left;height:100%"> &nbsp;</div>
<div id="wrapper" style="margin: 0 0 0 0;float:left" class="wrapper">

<%
//if (WebUtil.isTrue(siteConfig.getFrontDisplayFeed())){
//	if ( !sessionContext.isSuperAdmin() )
//		contentPage = "/jsp/contents/feed/contentFeedDisplay.jsp";
//} else {
//	if ( !sessionContext.isSuperAdmin() )
//		contentPage = "/jsp/layout/home_unregister.jsp";
//}

	if ( !siteRegistered ){
%>
	<jsp:include page="/jsp/layout/introl_header.jsp" />
<%
	}
%>
<h3>If you are interested in this domain, please contact us via passionbluedirect@gmail.com</h3>
</div>
</div>

</body>
</html>