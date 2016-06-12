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
	PollConfigDS ds = PollConfigDS.getInstance();    

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

<a href="t_poll_config_form.html?prv_returnPage=poll_config_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Poll Id </td> 
    <td class="columnTitle">  Image Thumb Height </td> 
    <td class="columnTitle">  Image Thumb Width </td> 
    <td class="columnTitle">  Image Align Vertical </td> 
    <td class="columnTitle">  Background </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollConfig _PollConfig = (PollConfig) iter.next();
%>

<TR id="tableRow<%= _PollConfig.getId()%>">
    <td> <%= _PollConfig.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _PollConfig.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _PollConfig.getPollId()  %> </td>
	<td> <%= _PollConfig.getImageThumbHeight()  %> </td>
	<td> <%= _PollConfig.getImageThumbWidth()  %> </td>
	<td> <%= _PollConfig.getImageAlignVertical()  %> </td>
	<td> <%= _PollConfig.getBackground()  %> </td>
	<td> <%= _PollConfig.getTimeCreated()  %> </td>
	<td> <%= _PollConfig.getTimeUpdated()  %> </td>
	<td>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_poll_config_form('<%=_PollConfig.getId()%>');">Edit</a>
	</td>
	<td> <a href="javascript:sendFormAjaxSimple('/pollConfigAction.html?del=true&id=<%=_PollConfig.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_PollConfig.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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
	function edit_poll_config_form(target){
		location.href='/v_poll_config_form.html?cmd=edit&prv_returnPage=poll_config_home&id=' + target;
	}

</script>
