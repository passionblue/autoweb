<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.seox.util.*,com.autosite.db.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<% 
	long startPage = System.currentTimeMillis();
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	if (sessionContext == null) 
		sessionContext = AutositeSessionContext.create(session);


	int userType = (sessionContext != null? sessionContext.getUserType(): Constants.UserAnonymous);
	boolean isLogin = (sessionContext != null? sessionContext.isLogin():false);

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
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
	

	
	
	
	String id = request.getParameter("id");
	ChurExpense churExpense = ChurExpenseDS.getInstance().getById(WebParamUtil.getLongValue(id));
	if (churExpense == null) return;

	String _selectedWeek = ChurWebUtil.getSelectedWeek(request );
	int _selectedYear = ChurWebUtil.getSelectedYear(request);
	
%>

<%= docType%>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<!-- jsp:include page="/jsp/layout/layout-header.jsp" /-->
<body topmargin="0" bgcolor="#ffffff" padding="0" >

<style type="text/css">
		table.printable-table						{ border: 1px black solid; border-collapse: collapse; width: 100%; }
		table.printable-table 	td					{ border: 1px black solid; border-collapse: collapse;font: normal normal normal 12px tahoma; padding: 2px 0px 2px 10px }
		table.printable-table 	td.printable-header	{ border: 1px black solid; border-collapse: collapse;font: normal normal bold 12px tahoma; padding: 2px 0px 2px 10px }
		table.printable-table 	td.container	{ border: 1px black solid; border-collapse: collapse;font: normal normal bold 12px tahoma; padding: 0px}
		table.printable-table-inside			{ border: 0px black solid; border-collapse: collapse; width: 100%; }
		table.printable-table-inside 	td					{ border: 1px black solid; border-collapse: collapse;font: normal normal normal 12px tahoma; padding: 2px 0px 2px 10px }
		h4 { text-align: center;}
</style>

<br>
<br>
<h1> 지출결의서</h1>
<br>
<br>

<table class="printable-table" >
	<tr>
		<td width="200px" valign="top">일자<h4><%=_selectedWeek %></h4></td>
		<td width="200px" valign="top">등재일<h4><%=_selectedWeek %></h4></td>
		<td width="100px" valign="top">결재</td>
		<td class="container">
			<table class="printable-table-inside">
				<tr>
					<td>회계</td>
					<td>재정부장</td>
					<td>당회장</td>
				</tr>		
				<tr height="50px" >
					<td width="100px"></td>
					<td width="100px"></td>
					<td width="100px"></td>
				</tr>		
			</table>
		</td>
	</tr>

	<tr valign="top" height="50px" >
		<td >금액<h4><%=WebUtil.displayMoney(churExpense.getAmount()) %></h4></td>
		<td >수표번호<h4><%=WebUtil.isTrue(churExpense.getIsCash())&& churExpense.getCheckNumber() != null?"" :churExpense.getCheckNumber() %></h4></td>
		<td >현금<h4><%= WebUtil.isTrue(churExpense.getIsCash())?WebUtil.displayMoney(churExpense.getAmount()) :"" %></h4></td>
		<td rowspan="3" > 비고 </td>
	</tr>

	<tr height="50px" valign="top">
		<td>지출내역</td>
		<td colspan="2" valign="middle"><h4><%= ChurExpenseItemDS.getInstance().getById(churExpense.getExpenseItemId()).getExpenseItem() %></h4></td>
	</tr>

	<tr height="50px" valign="top">
		<td>수령인내역</td>
		<td colspan="2"  valign="middle"><h4><%= ChurPayeeDS.getInstance().getById(churExpense.getPayeeId()).getTitle() %></h4></td>
	</tr>


	<tr height="300px" valign="top">
		<td colspan="4"  >메모</td>
	</tr>


</table>

<%=(googleTrack==null?"":googleTrack) %>

</body>
</html>