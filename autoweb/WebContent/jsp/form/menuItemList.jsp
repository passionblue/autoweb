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
	MenuItemDS ds = MenuItemDS.getInstance();    

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

<a href="t_menu_item_form.html?prv_returnPage=menu_item_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Panel Id </td> 
    <td class="columnTitle">  Parent Id </td> 
    <td class="columnTitle">  Title </td> 
    <td class="columnTitle">  Data </td> 
    <td class="columnTitle">  Data2 </td> 
    <td class="columnTitle">  Target Type </td> 
    <td class="columnTitle">  Order Idx </td> 
    <td class="columnTitle">  Page Id </td> 
    <td class="columnTitle">  Content Id </td> 
    <td class="columnTitle">  Hide </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        MenuItem _MenuItem = (MenuItem) iter.next();
%>

<TR>
    <td> <%= _MenuItem.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _MenuItem.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _MenuItem.getPanelId()  %> </td>
	<td> <%= _MenuItem.getParentId()  %> </td>
	<td> <%= _MenuItem.getTitle()  %> </td>
	<td> <%= _MenuItem.getData()  %> </td>
	<td> <%= _MenuItem.getData2()  %> </td>
	<td> <%= _MenuItem.getTargetType()  %> </td>
	<td> <%= _MenuItem.getOrderIdx()  %> </td>
	<td> <%= _MenuItem.getPageId()  %> </td>
	<td> <%= _MenuItem.getContentId()  %> </td>
	<td> <%= _MenuItem.getHide()  %> </td>
	<td> <%= _MenuItem.getTimeCreated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/menuItemAction.html?del=true&id=<%=_MenuItem.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
</script>
