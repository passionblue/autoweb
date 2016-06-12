<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.seox.util.*,com.autosite.db.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<% 
	JspUtil.getLogger().info("----- layout-include-head.jsp ------------------------------------------------------------------------------------------------");

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
	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = (pageName == null? null:PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName));
	String pageId = (dynPage == null?"0":""+dynPage.getId());

	if (dynPage != null ) {
	    if (dynPage.getPageMenuTitle().equalsIgnoreCase("XHOME"))
		    pageTitle += " - Home";
	    else 
		    pageTitle += " - " + dynPage.getPageMenuTitle();
	} else if ( pageView != null){
	    
	    if ( pageView.getPageTitle() != null)
		    pageTitle += " - " + pageView.getPageTitle();
	    else	        
		    pageTitle += " (" + pageView.getAlias().toUpperCase().replaceAll("_", "-") + ")";
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

<!-- layout-header -->
<head>
<title><%= pageTitle %></title>
<link REL="SHORTCUT ICON" HREF="<%=siteIconUrl %>"/>
 
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta name="keywords" content="<%=keywords%>,<%=(dynPage==null||dynPage.getPageKeywords()==null)?"": dynPage.getPageKeywords()%>"/>
<meta name="description" content=""/> 
<meta name="http-equiv" content="Content-type: text/html; charset=UTF-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%
	//==================================================================================
	// Meta Headers MetaHeaderDS
	//==================================================================================
	
	Map metaHeaders = (Map) session.getAttribute("k_meta_headers");
	
	if (metaHeaders!= null) {
		for(Iterator iter = metaHeaders.keySet().iterator();iter.hasNext();){
			String name = (String) iter.next();
%>
<meta name="<%=name %>" content="<%= WebUtil.display((String)metaHeaders.get(name)) %>" />	
<%
		}
	}
%>
<%
	Map metaHeadersHttpEquiv = (Map) session.getAttribute("k_meta_headers_http_equiv");
	
	if (metaHeadersHttpEquiv!= null) {
		for(Iterator iter = metaHeadersHttpEquiv.keySet().iterator();iter.hasNext();){
			String name = (String) iter.next();
%>
<meta http-equiv="<%=name %>" content="<%= WebUtil.display((String)metaHeadersHttpEquiv.get(name)) %>" />	
<%
		}
	}
%>
<%
	//=================== css INDEX SiteStyleConfig =============== 
	String siteCssIndex = "0";	   

	// SiteConfigStyle TODO to be replaced by theme always. 
	SiteConfigStyle siteConfigStyle = SiteConfigStyleDS.getInstance().getObjectBySiteId(site.getId());
	if (siteConfigStyle != null && siteConfigStyle.getCssIndex() > 0 )
	    siteCssIndex = ""+siteConfigStyle.getCssIndex();

	// From Theme 
	ThemeAggregator themeAggregator = StyleThemeUtil.getThemeAggregatorForSite(site.getId());
	if (themeAggregator != null && themeAggregator.getLayoutPage() != null) {
	    siteCssIndex = themeAggregator.getCssIndex();
		JspUtil.getLogger().info("SiteCssIndex set by theme " + siteCssIndex); 
	}	
	
	ResourceInclusionManager includeMgr = ResourceInclusionManager.getInstance();
	
    System.out.println("XXX->" + ResourceInclusionManager.getInstance().get("common-all"));
%>

<% if ( includeMgr.include(site, null, "common-all")) { %>
<%-- ################################## FOR ALL SITES COMMON ###############################################################  --%>
<link rel="stylesheet" type="text/css" href="/customstyles/<%="m"+siteCssIndex %>/main.css"/>
<link rel="stylesheet" type="text/css"  href="/customstyles/<%="m"+siteCssIndex %>/main_forms.css"/>
<link rel="stylesheet" type="text/css" href="/customstyles/<%="m"+siteCssIndex %>/main_table.css"/>
<link rel="stylesheet" type="text/css" href="/customstyles/<%="m"+siteCssIndex %>/layout.css"/>
<link rel="stylesheet" type="text/css" href="/customstyles/<%="m"+siteCssIndex %>/panel.css"/>
<link rel="stylesheet" type="text/css" href="/customstyles/<%="m"+siteCssIndex %>/panel_content.css"/>
<link rel="stylesheet" type="text/css"  href="/customstyles/<%="m"+siteCssIndex %>/main_default_html.css"/>
<link rel="stylesheet" type="text/css"  href="/customstyles/basic_content_trivial.css"/>

<% } %>
<link rel="stylesheet" type="text/css" href="/cscr/main.css?id=<%=cssId%>"/>

<%-- ################################## FOR ALL SITES COMMON ############################################################### --%>
<!-- For  -->
<script type="text/javascript" src="/ckeditor-4.0.1-basic/ckeditor.js"></script>
<!-- script type="text/javascript" src="/ckeditor-4.0.1-basic/ckeditor.js"></script-->

<script type="text/javascript" src="/scripts.js"> </script>
<!-- script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script-->
<!-- script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script-->
<script type="text/javascript" src="/js/jquery-1.8.2.min.js"> </script>

<!-- ############## JQuery tools http://flowplayer.org/index.html ##############  
This is very nice tool but has conflicts so many other libs so should be included on-demand basis.
<script src="http://cdn.jquerytools.org/1.2.3/all/jquery.tools.min.js"></script>
-->

<!-- ############## Sexy DropDown http://www.noupe.com/tutorial/drop-down-menu-jquery-css.html ############# -->

<script type='text/javascript' src='/menuscripts/sexydd/sexydd.js'></script>
<link rel="stylesheet" href="/menuscripts/sexydd/sexydd.css" type="text/css" />

<!-- ############## MLDD http://spicebrains.com/multi-level-drop-down-menu/ ############# -->

<script type="text/javascript" src="/menuscripts/mldd/mlddmenu.js"></script>
<link rel="stylesheet" type="text/css" href="/menuscripts/mldd/mlddmenu.css" />

<% if ( sessionContext.isSuperAdmin() ) { %>

<script type="text/javascript" src="/js/jquery-tools/overlay.js"></script>
<script type="text/javascript" src="/js/jquery-tools/tooltip.js"></script>
<script type="text/javascript" src="/js/jquery-tools/dateinput.js"></script>
<script type="text/javascript" src="/js/jquery-tools/scrollable.js"></script>


<!--  ############## Mega menu http://www.javascriptkit.com/script/script2/jkmegamenu.shtml ############## -->
<link rel="stylesheet" type="text/css" href="/menuscripts/megamenu/jkmegamenu.css" />
<script type="text/javascript" src="/menuscripts/megamenu/jkmegamenu.js">
</script>

<%-- ######################################################################################################## --%>
<!-- ======================== jquery UI ========================

<link type="text/css" href="/js/jquery-ui-flick/jquery-ui-1.8.2.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="/js/jquery-ui-flick/jquery-ui-1.8.2.custom.min.js"></script>
  -->

<%-- ######################################################################################################## --%>
<!-- ======================== Accordion menu Test ======================== -->

<link type="text/css" href="/js/accordion/accordion1.css" rel="stylesheet" />	

<%-- ######################################################################################################## --%>
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
});

</script>

<%-- ######################################################################################################## --%>
<script src="/js/ajaxify/jquery.livequery.pack.js" type="text/javascript"></script>
<script src="/js/ajaxify/jquery.history.fixed.js" type="text/javascript"></script>
<!-- script src="/js/ajaxify/jquery.simplemodal-1.1.1.js" type="text/javascript"></script -->
<script src="/js/ajaxify/jquery.metadata.min.js" type="text/javascript"></script>

<%-- ######################################################################################################## --%>
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

<%-- ######################################################################################################## --%>
<!-- ############## COLORBOX  http://colorpowered.com/colorbox/############# -->

<script type="text/javascript" src="/js/colorbox/jquery.colorbox.js"></script>
<link rel="stylesheet" type="text/css" href="/js/colorbox/colorbox.css" />

<%-- ######################################################################################################## --%>
<!-- ############## CEEEBOX ############# -->


<!-- http://jquery.thewikies.com/swfobject/ 
 <script src="/js/swfobject/jquery.swfobject.1-0-9.js" type="text/javascript" charset="utf-8"></script>
-->

<!--  http://catcubed.com/2008/12/23/ceebox-a-thickboxvideobox-mashup/ -->
<link rel="stylesheet" href="/js/ceebox/ceebox.css" type="text/css" media="screen" />


<script src="/js/ceebox/jquery.swfobject.js" type="text/javascript" charset="utf-8"></script>
<script src="/js/ceebox/jquery.metadata.js" type="text/javascript" charset="utf-8"></script>
<script src="/js/ceebox/jquery.color.js" type="text/javascript" charset="utf-8"></script>
<script src="/js/ceebox/jquery.ceebox.js" type="text/javascript" charset="utf-8"></script>

<%-- ######################################################################################################## --%>
<!-- ############## pop-it menu #############

<script type='text/javascript' src='/js/popit/popit.js'></script>
<link rel="stylesheet" href="/js/popit/popit.css" type="text/css" />
 -->
<!-- ############## anylink menu http://www.dynamicdrive.com/dynamicindex1/dropmenuindex.htm ############# -->

<link rel="stylesheet" type="text/css" href="/js/anylinkmenu/anylinkmenu.css" />
<script type="text/javascript" src="/js/anylinkmenu/menucontents.js"></script>
<script type="text/javascript" src="/pscr/anylinkMenuCnt.js"></script>
<script type="text/javascript" src="/js/anylinkmenu/anylinkmenu.js"></script>

<script type="text/javascript">
	//anylinkmenu.init("menu_anchors_class") //Pass in the CSS class of anchor links (that contain a sub menu)
	anylinkmenu.init("menuanchorclass");
</script>

<!-- ############## BOXY http://onehackoranother.com/projects/jquery/boxy/#manual-labour ################## -->

<script type='text/javascript' src='/js/boxy/jquery.boxy.js'></script>
<link rel="stylesheet" href="/js/boxy/boxy.css" type="text/css" />

<!-- ############## jqModal http://dev.iceburg.net/jquery/jqModal ######################################### -->

<script type='text/javascript' src='/js/jqmodal/jqModal.js'></script>
<link rel="stylesheet" href="/js/jqmodal/jqModal.css" type="text/css" />

<!-- ############## impromptu http://trentrichardson.com/Impromptu/index.php ############################## -->

<script type='text/javascript' src='/js/impromptu/jquery-impromptu.3.1.min.js'></script>
<style type="text/css">			
	.jqifade{ position: absolute; background-color: #aaaaaa; }
	div.jqi{ width: 400px; font-family: Verdana, Geneva, Arial, Helvetica, sans-serif; position: absolute; background-color: #ffffff; font-size: 11px; text-align: left; border: solid 1px #eeeeee; -moz-border-radius: 10px; -webkit-border-radius: 10px; padding: 7px; }
	div.jqi .jqicontainer{ font-weight: bold; }
	div.jqi .jqiclose{ position: absolute; top: 4px; right: -2px; width: 18px; cursor: default; color: #bbbbbb; font-weight: bold; }
	div.jqi .jqimessage{ padding: 10px; line-height: 20px; color: #444444; }
	div.jqi .jqibuttons{ text-align: right; padding: 5px 0 5px 0; border: solid 1px #eeeeee; background-color: #f4f4f4; }
	div.jqi button{ padding: 3px 10px; margin: 0 10px; background-color: #2F6073; border: solid 1px #f4f4f4; color: #ffffff; font-weight: bold; font-size: 12px; }
	div.jqi button:hover{ background-color: #728A8C; }
	div.jqi button.jqidefaultbutton{ background-color: #BF5E26; }
	.jqiwarning .jqi .jqibuttons{ background-color: #BF5E26; }
</style>

<!-- ############## easy tooltip ######################################################################### -->

<script type='text/javascript' src='/js/easytooltip/imgview.js'></script>
<link rel="stylesheet" href="/js/easytooltip/imgview.css" type="text/css" />

<!-- ############## panel popup menu based on easy tooltip ############################################### -->

<script type='text/javascript' src='/js/panel_popup_menu.js'></script>
<link rel="stylesheet" href="/js/panel_popup_menu.css" type="text/css" />


<!-- ############## cluetip http://plugins.learningjquery.com/cluetip/demo/ ############################## -->

<script src="/js/cluetip/jquery.bgiframe.min.js" type="text/javascript"></script> <!-- optional -->
<script src="/js/cluetip/jquery.hoverIntent.js" type="text/javascript"></script> <!-- optional -->
<script src="/js/cluetip/jquery.cluetip.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="/js/cluetip/jquery.cluetip.css"/>

<!-- ############## ajaxfileuploader1.0 http://www.phpletter.com/Our-Projects/AjaxFileUpload/  ############# --> 

<link href="/js/ajaxfileuploader/ajaxfileupload.css" media="screen" rel="stylesheet" type="text/css"/>
<script src="/js/ajaxfileuploader/ajaxfileupload.js" type="text/javascript"></script> 

<!-- ############## uploadify http://www.uploadify.com/  ################################################## -->

<script type="text/javascript" src="/js/uploadify/swfobject.js"></script>
<script type="text/javascript" src="/js/uploadify/jquery.uploadify.v2.1.0.min.js"></script>

<!-- ############## datatables http://www.datatables.net  ################################################# -->

<link href="/js/datatables/css/demo_page.css" media="screen" rel="stylesheet" type="text/css"/>
<link href="/js/datatables/css/demo_table.css" media="screen" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="/js/datatables/jquery.dataTables.min.js"></script>

<!-- ############## flexgrid http://flexigrid.info/  ###################################################### -->
<link href="/js/flexgrid/flexigrid.css" media="screen" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/js/flexgrid/flexigrid.js"></script>

<!-- ############## fancybox http://fancybox.net/howto  #################################################### -->

<script type="text/javascript" src="/js/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
<link href="/js/fancybox/jquery.fancybox-1.3.1.css" media="screen" rel="stylesheet" type="text/css"/>

<!-- ############## jixedbar http://code.google.com/p/jixedbar/ http://jixedbar.rawswift.com/  ############# 
<script type="text/javascript" src="/js/jixedbar/jquery.jixedbar.min.js"></script>
<link type="text/css" href="/js/jixedbar/themes/default/jx.stylesheet.css" rel="stylesheet"/>

<script type="text/javascript">
    $(document).ready(function() {
        //sample bar is located in the bottom
		$("#sample-fixedbar").jixedbar();
    });
</script>
-->
<!-- ############## slimbox2 http://www.digitalia.be/software/slimbox2 ###################################### -->

<script type="text/javascript" src="/js/slimbox/slimbox2.js"></script>
<link rel="stylesheet" href="/js/slimbox/slimbox2.css" type="text/css" media="screen" />

<!-- ############## mouseovertabls http://www.dynamicdrive.com/dynamicindex1/mouseovertabs.htm  ############# -->

<link rel="stylesheet" type="text/css" href="/menuscripts/mouseovertab/mouseovertabs.css" />
<script src="/menuscripts/mouseovertab/mouseovertabs.js" type="text/javascript"></script>

<!-- ############## shaker http://davidwalsh.name/jquery-shake  #############################################

<style type="text/css">
		#shaker			{ position:fixed; top:10px; right:10px; width:299px; height:253px; display:none; }
		#fixed-left		{ position:fixed; top:50px; left:10px; width:100px; height:200px; background: yellow; border: 1px solid red;}
		#fixed-left	.section{ margin: 10px 5px 0px 5px; padding: 2px 2px 2px 2px; border: 1px solid blue;}
		
</style>

<script type="text/javascript">
		function periodical() {
			$('#shaker').effect('shake', { times:3 }, 200);
		}
		$(document).ready(function() {
			$('#shaker').hide().css('display','').fadeIn(600);
			var shake = setInterval('periodical()', 5000);
		});
</script>
 -->

<%-- ############## PERSONALLY made scripts ################################################################# --%>
<%-- ############## PERSONALLY made scripts ################################################################# --%>

<% } %>

<%-- ############## Ajaxify ################################################################################# --%>
<script type="text/javascript" src="/js/ajaxify/jquery.ajaxify.js"></script> 

<%-- ############## facebox http://defunkt.github.com/facebox/ ############################################## --%>
<link href="/js/facebox/facebox.css" media="screen" rel="stylesheet" type="text/css"/>
<script src="/js/facebox/facebox.js" type="text/javascript"></script> 

<%-- ############## AUtocomplete from jquery ################################################################ --%>
<link rel="stylesheet" type="text/css" href="./js/autocomplete/jquery-autocomplete/jquery.autocomplete.css"/>
<script type="text/javascript"          src="./js/autocomplete/jquery-autocomplete/jquery.autocomplete.js"></script>

<%-- ############## Table sorter http://tablesorter.com/docs/ ############################################### --%>

<!-- script type="text/javascript" src="/js/_import/tablesorter-2/jquery-latest.js"></script--> 
<script type="text/javascript"          src="/js/_import/tablesorter-2/jquery.tablesorter.min.js"></script> 
<link rel="stylesheet" type="text/css" href="/js/_import/tablesorter-2/themes/blue/style.css"/>


<link href="/js/ajaxupload/style/style.css" rel="stylesheet" type="text/css" />

<%-- ############## jquery Formhttp://malsup.com/jquery/form/#file-upload ############################################### --%>

<!-- script src="http://malsup.github.com/jquery.form.js"></script -->
<script type="text/javascript"     src="/js/ajaxform/jquery.form.js"></script>


<%-- ######################################################################################################## --%>
<%-- ######################################################################################################## --%>
<%-- ############## PERSONALLY made scripts ################################################################# --%>
<%-- ######################################################################################################## --%>
<%-- ######################################################################################################## --%>

<%-- ####################FOR ALL SITES COMMON ############################################################### --%>
<% if ( includeMgr.include(site, null, "module-all")) { %>
<link rel="stylesheet" type="text/css" href="/feed.css"/>

<link rel="stylesheet" type="text/css" href="/ec_main.css"/>
<script type="text/javascript" src="/ec_main.js"> </script>

<link rel="stylesheet" type="text/css" href="/blog_main.css"/>
<script type="text/javascript" src="/blog_main.js"> </script>

<link rel="stylesheet" type="text/css" href="/poll.css"/>
<script type="text/javascript" src="/poll.js"> </script>

<link rel="stylesheet" type="text/css" href="/sweep_worldcup.css"/>
<script type="text/javascript" src="/sweep_worldcup.js"> </script>

<link rel="stylesheet" type="text/css" href="/resource/chur/chur.css"/>
<script type="text/javascript" src="/resource/chur/chur.js"> </script>

<% } %>

<script type="text/javascript" src="/ajax.js"> </script>
<script type="text/javascript" src="/main.js"> </script>
<script type="text/javascript" src="/panel.js"> </script>
<script type="text/javascript" src="/main_table.js"> </script>

<link rel="stylesheet" type="text/css" media="screen" href="/jsp/lab/jquery_test.css"/>
<script type="text/javascript" src="/jsp/lab/jquery_test.js"> </script>


<!-- ############## Dynamic Script Imports ################################################################## -->

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

<link rel="stylesheet" type="text/css" media="screen" href="/pscr/cntStyle.css?type=cntList"/>
<link rel="stylesheet" type="text/css" media="screen" href="/pscr/cntStyle.css?type=cntSingle"/>
<link rel="stylesheet" type="text/css" media="screen" href="/pscr/cntStyleSets.css?stid=<%=site.getId() %>"/>
<link rel="stylesheet" type="text/css" media="screen" href="/pscr/customStyle.css?stid=<%=site.getId() %>"/>

<%-- ############## Dynamic Script Imports ################################################################## --%>
<%

	List siteHeaders = SiteHeaderDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = siteHeaders.iterator();iter.hasNext();){
		SiteHeader sh = (SiteHeader) iter.next();
%>
	<%=  SiteHeaderUtil.display(sh) %>
<%
	}
%>
<%-- ############## Google Map Testing ################################################################## --%>
<% if (site.getId() == 29){ %>
<script type="text/javascript" src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAd8xPFM_iKUWkK1lxTFucLxSJ_ahltiIPNC3Zy7kdx45wbNXxvRTbtNchT2FR6CSGJ9Pzms3RMt2qBw"></script>
<script type="text/javascript"
    src="http://maps.google.com/maps/api/js?sensor=false">
</script>
<% } %>


	
</head>
<!-- END:layout-header -->
<%
	JspUtil.getLogger().info("----- END layout-include-head.jsp ------------------------------------------------------------------------------------------------");
%>
