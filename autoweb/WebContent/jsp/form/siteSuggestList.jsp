<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
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
	SiteSuggestDS ds = SiteSuggestDS.getInstance();    

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

<a href="t_site_suggest_form.html?prv_returnPage=site_suggest_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Category </td> 
    <td class="columnTitle">  Suggest </td> 
    <td class="columnTitle">  Resolved </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SiteSuggest _SiteSuggest = (SiteSuggest) iter.next();
%>

<TR>
    <td> <%= _SiteSuggest.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _SiteSuggest.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _SiteSuggest.getCategory()  %> </td>
	<td> <%= _SiteSuggest.getSuggest()  %> </td>
	<td> <%= _SiteSuggest.getResolved()  %> </td>
	<td> <%= _SiteSuggest.getTimeCreated()  %> </td>
	<td> <%= _SiteSuggest.getTimeUpdated()  %> </td>
</TR>

<%
    }
%>
</TABLE>