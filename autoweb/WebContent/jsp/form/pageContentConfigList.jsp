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
	PageContentConfigDS ds = PageContentConfigDS.getInstance();    

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

<a href="t_page_content_config_form.html?prv_returnPage=page_content_config_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Content Id </td> 
    <td class="columnTitle">  Content Type </td> 
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
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageContentConfig _PageContentConfig = (PageContentConfig) iter.next();
%>

<TR id="tableRow<%= _PageContentConfig.getId()%>">
    <td> <%= _PageContentConfig.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _PageContentConfig.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _PageContentConfig.getContentId()  %> </td>
	<td> <%= _PageContentConfig.getContentType()  %> </td>
	<td> <%= _PageContentConfig.getSortType()  %> </td>
	<td> <%= _PageContentConfig.getArrangeType()  %> </td>
	<td> <%= _PageContentConfig.getPageCss()  %> </td>
	<td> <%= _PageContentConfig.getPageScript()  %> </td>
	<td> <%= _PageContentConfig.getPageCssImports()  %> </td>
	<td> <%= _PageContentConfig.getPageScriptImports()  %> </td>
	<td> <%= _PageContentConfig.getHideMenu()  %> </td>
	<td> <%= _PageContentConfig.getHideMid()  %> </td>
	<td> <%= _PageContentConfig.getHideAd()  %> </td>
	<td> <%= _PageContentConfig.getStyleId()  %> </td>
	<td> <%= _PageContentConfig.getContentStyleSetId()  %> </td>
	<td> <%= _PageContentConfig.getHeaderMeta()  %> </td>
	<td> <%= _PageContentConfig.getHeaderLink()  %> </td>
	<td> <%= _PageContentConfig.getTimeCreated()  %> </td>
	<td> <%= _PageContentConfig.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/pageContentConfigAction.html?del=true&id=<%=_PageContentConfig.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_PageContentConfig.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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
