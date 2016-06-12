<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	List list = new ArrayList();
	PageConfigDS ds = PageConfigDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();


%> 
<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	list = PagingUtil.getPagedList(pagingInfo, list);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>

<!-- =================== END PAGING =================== -->

<a href="t_page_config_form.html?prv_returnPage=page_config_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Page Id </td> 
    <td class="columnTitle">  Sort Type </td> 
    <td class="columnTitle">  Arrange Type </td> 
    <td class="columnTitle">  Page Css </td> 
    <td class="columnTitle">  Page Script </td> 
    <td class="columnTitle">  Page Css Imports </td> 
    <td class="columnTitle">  Page Script Imports </td> 
    <td class="columnTitle">  Hide Menu </td> 
    <td class="columnTitle">  Hide Mid </td> 
    <td class="columnTitle">  Hide Ad </td> 
    <td class="columnTitle">  Style Id </td> 
    <td class="columnTitle">  Content Style Set Id </td> 
    <td class="columnTitle">  Header Meta </td> 
    <td class="columnTitle">  Header Link </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageConfig _PageConfig = (PageConfig) iter.next();
%>

<TR>
    <td> <%= _PageConfig.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _PageConfig.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _PageConfig.getPageId()  %> </td>
	<td> <%= _PageConfig.getSortType()  %> </td>
	<td> <%= _PageConfig.getArrangeType()  %> </td>
	<td> <%= _PageConfig.getPageCss()  %> </td>
	<td> <%= _PageConfig.getPageScript()  %> </td>
	<td> <%= _PageConfig.getPageCssImports()  %> </td>
	<td> <%= _PageConfig.getPageScriptImports()  %> </td>
	<td> <%= _PageConfig.getHideMenu()  %> </td>
	<td> <%= _PageConfig.getHideMid()  %> </td>
	<td> <%= _PageConfig.getHideAd()  %> </td>
	<td> <%= _PageConfig.getStyleId()  %> </td>
	<td> <%= _PageConfig.getContentStyleSetId()  %> </td>
	<td> <%= _PageConfig.getHeaderMeta()  %> </td>
	<td> <%= _PageConfig.getHeaderLink()  %> </td>
	<td> <%= _PageConfig.getTimeCreated()  %> </td>
	<td> <%= _PageConfig.getTimeUpdated()  %> </td>
</TR>

<%
    }
%>
</TABLE>