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
	PollCommentDS ds = PollCommentDS.getInstance();    

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

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_poll_comment_add2.html?prv_returnPage=poll_comment_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">
    <td> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td> 
	    <div id="pollCommentForm_${field.name2}_label" style="font-size: normal normal bold 10px verdana;" >Site </div>
    </td> 
	
<% 	} %>

    <td> 
	    <div id="pollCommentForm_pollId_label" style="font-size: normal normal bold 10px verdana;" >Poll Id </div>
    </td> 
    <td> 
	    <div id="pollCommentForm_userId_label" style="font-size: normal normal bold 10px verdana;" >User Id </div>
    </td> 
    <td> 
	    <div id="pollCommentForm_comment_label" style="font-size: normal normal bold 10px verdana;" >Comment </div>
    </td> 
    <td> 
	    <div id="pollCommentForm_hide_label" style="font-size: normal normal bold 10px verdana;" >Hide </div>
    </td> 
    <td> 
	    <div id="pollCommentForm_timeCreated_label" style="font-size: normal normal bold 10px verdana;" >Time Created </div>
    </td> 
</TR>
<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollComment _PollComment = (PollComment) iter.next();
%>

<TR bgcolor="#ffffff" valign="top">
    <td> <%= _PollComment.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _PollComment.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td> 
		<%= siteName %>	    
    </td> 
	
<% 	} %>
    
	<td> <%= _PollComment.getPollId()  %> </td>
	<td> <%= _PollComment.getUserId()  %> </td>
	<td> <%= _PollComment.getComment()  %> </td>
	<td> <%= _PollComment.getHide()  %> </td>
	<td> <%= _PollComment.getTimeCreated()  %> </td>
</TR>

<%
    }
%>
</TABLE>