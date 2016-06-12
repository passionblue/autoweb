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
	PollStyleDS ds = PollStyleDS.getInstance();    

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

<a href="t_poll_style_form.html?prv_returnPage=poll_style_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  User Id </td> 
    <td class="columnTitle">  Poll Id </td> 
    <td class="columnTitle">  Color </td> 
    <td class="columnTitle">  Background </td> 
    <td class="columnTitle">  Border </td> 
    <td class="columnTitle">  Font </td> 
    <td class="columnTitle">  Margin </td> 
    <td class="columnTitle">  Padding </td> 
    <td class="columnTitle">  Floating </td> 
    <td class="columnTitle">  Text Align </td> 
    <td class="columnTitle">  Text Indent </td> 
    <td class="columnTitle">  Height </td> 
    <td class="columnTitle">  Width </td> 
    <td class="columnTitle">  Extra </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollStyle _PollStyle = (PollStyle) iter.next();
%>

<TR>
    <td> <%= _PollStyle.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _PollStyle.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _PollStyle.getUserId()  %> </td>
	<td> <%= _PollStyle.getPollId()  %> </td>
	<td> <%= _PollStyle.getColor()  %> </td>
	<td> <%= _PollStyle.getBackground()  %> </td>
	<td> <%= _PollStyle.getBorder()  %> </td>
	<td> <%= _PollStyle.getFont()  %> </td>
	<td> <%= _PollStyle.getMargin()  %> </td>
	<td> <%= _PollStyle.getPadding()  %> </td>
	<td> <%= _PollStyle.getFloating()  %> </td>
	<td> <%= _PollStyle.getTextAlign()  %> </td>
	<td> <%= _PollStyle.getTextIndent()  %> </td>
	<td> <%= _PollStyle.getHeight()  %> </td>
	<td> <%= _PollStyle.getWidth()  %> </td>
	<td> <%= _PollStyle.getExtra()  %> </td>
	<td> <%= _PollStyle.getTimeCreated()  %> </td>
	<td> <%= _PollStyle.getTimeUpdated()  %> </td>
</TR>

<%
    }
%>
</TABLE>