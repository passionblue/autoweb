<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	List list = new ArrayList();
	BlogCommentDS ds = BlogCommentDS.getInstance();    

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

<a href="t_blog_comment_form.html?prv_returnPage=blog_comment_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Blog Main Id </td> 
    <td class="columnTitle">  Blog Post Id </td> 
    <td class="columnTitle">  Comment </td> 
    <td class="columnTitle">  Rating </td> 
    <td class="columnTitle">  Ipaddress </td> 
    <td class="columnTitle">  Name </td> 
    <td class="columnTitle">  Password </td> 
    <td class="columnTitle">  Website </td> 
    <td class="columnTitle">  Email </td> 
    <td class="columnTitle">  Time Created </td> 
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogComment _BlogComment = (BlogComment) iter.next();
%>

<TR>
    <td> <%= _BlogComment.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _BlogComment.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _BlogComment.getBlogMainId()  %> </td>
	<td> <%= _BlogComment.getBlogPostId()  %> </td>
	<td> <%= _BlogComment.getComment()  %> </td>
	<td> <%= _BlogComment.getRating()  %> </td>
	<td> <%= _BlogComment.getIpaddress()  %> </td>
	<td> <%= _BlogComment.getName()  %> </td>
	<td> <%= _BlogComment.getPassword()  %> </td>
	<td> <%= _BlogComment.getWebsite()  %> </td>
	<td> <%= _BlogComment.getEmail()  %> </td>
	<td> <%= _BlogComment.getTimeCreated()  %> </td>
</TR>

<%
    }
%>
</TABLE>