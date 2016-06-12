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
	MetaHeaderDS ds = MetaHeaderDS.getInstance();    

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

<a href="t_meta_header_form.html?prv_returnPage=meta_header_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Source </td> 
    <td class="columnTitle">  Detail Id </td> 
    <td class="columnTitle">  Name </td> 
    <td class="columnTitle">  Value </td> 
    <td class="columnTitle">  Http Equiv </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        MetaHeader _MetaHeader = (MetaHeader) iter.next();
%>

<TR>
    <td> <%= _MetaHeader.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _MetaHeader.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _MetaHeader.getSource()  %> </td>
	<td> <%= _MetaHeader.getDetailId()  %> </td>
	<td> <%= _MetaHeader.getName()  %> </td>
	<td> <%= _MetaHeader.getValue()  %> </td>
	<td> <%= _MetaHeader.getHttpEquiv()  %> </td>
	<td> <%= _MetaHeader.getTimeCreated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/metaHeaderAction.html?del=true&id=<%=_MetaHeader.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
</script>
