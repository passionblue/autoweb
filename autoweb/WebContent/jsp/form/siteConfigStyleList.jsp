<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
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
	SiteConfigStyleDS ds = SiteConfigStyleDS.getInstance();    

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

<a href="t_site_config_style_form.html?prv_returnPage=site_config_style_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Theme Id </td> 
    <td class="columnTitle">  Css Index </td> 
    <td class="columnTitle">  Css Import </td> 
    <td class="columnTitle">  Layout Index </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SiteConfigStyle _SiteConfigStyle = (SiteConfigStyle) iter.next();
%>

<TR id="tableRow<%= _SiteConfigStyle.getId()%>">
    <td> <%= _SiteConfigStyle.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _SiteConfigStyle.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _SiteConfigStyle.getThemeId()  %> </td>
	<td> <%= _SiteConfigStyle.getCssIndex()  %> </td>
	<td> <%= _SiteConfigStyle.getCssImport()  %> </td>
	<td> <%= _SiteConfigStyle.getLayoutIndex()  %> </td>
	<td> <%= _SiteConfigStyle.getTimeCreated()  %> </td>
	<td> <%= _SiteConfigStyle.getTimeUpdated()  %> </td>
	<td>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_site_config_style_form('<%=_SiteConfigStyle.getId()%>');">Edit</a>
	</td>
	<td> <a href="javascript:sendFormAjaxSimple('/siteConfigStyleAction.html?del=true&id=<%=_SiteConfigStyle.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_SiteConfigStyle.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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
	function edit_site_config_style_form(target){
		location.href='/v_site_config_style_form.html?cmd=edit&prv_returnPage=site_config_style_home&id=' + target;
	}

</script>
