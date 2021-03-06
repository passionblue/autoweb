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
	DevTableDS ds = DevTableDS.getInstance();    

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

<a href="t_dev_table_form.html?prv_returnPage=dev_table_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Dev Note Id </td> 
    <td class="columnTitle">  Title </td> 
    <td class="columnTitle">  Subject </td> 
    <td class="columnTitle">  Data </td> 
    <td class="columnTitle">  Type </td> 
    <td class="columnTitle">  Disable </td> 
    <td class="columnTitle">  Radio Value </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        DevTable _DevTable = (DevTable) iter.next();
%>

<TR id="tableRow<%= _DevTable.getId()%>">
    <td> <%= _DevTable.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _DevTable.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _DevTable.getDevNoteId()  %> </td>
	<td> <%= _DevTable.getTitle()  %> </td>
	<td> <%= _DevTable.getSubject()  %> </td>
	<td> <%= _DevTable.getData()  %> </td>
	<td> <%= _DevTable.getType()  %> </td>
	<td> <%= _DevTable.getDisable()  %> </td>
	<td> <%= _DevTable.getRadioValue()  %> </td>
	<td> <%= _DevTable.getTimeCreated()  %> </td>
	<td> <%= _DevTable.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/devTableAction.html?del=true&id=<%=_DevTable.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_DevTable.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
		if ($("#tableRow" + msg)) {
			$("#tableRow" + msg).fadeOut(1000);
		}
	}
</script>
