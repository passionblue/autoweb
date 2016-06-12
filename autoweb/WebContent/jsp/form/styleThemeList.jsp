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
	StyleThemeDS ds = StyleThemeDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else {
	    list = ds.getAll();
    }
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

<a href="t_style_theme_form.html?prv_returnPage=style_theme_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Title </td> 
    <td class="columnTitle">  Body Width </td> 
    <td class="columnTitle">  Body Align </td> 
    <td class="columnTitle">  Body Bg Color </td> 
    <td class="columnTitle">  Body Bg Image </td> 
    <td class="columnTitle">  Body Bg Attach </td> 
    <td class="columnTitle">  Body Bg Repeat </td> 
    <td class="columnTitle">  Body Bg Position </td> 
    <td class="columnTitle">  Content Bg Color </td> 
    <td class="columnTitle">  Use Absolute </td> 
    <td class="columnTitle">  Absolute Top </td> 
    <td class="columnTitle">  Absolute Left </td> 
    <td class="columnTitle">  Absolute Right </td> 
    <td class="columnTitle">  Absolute Bottom </td> 
    <td class="columnTitle">  Panel Style Id </td> 
    <td class="columnTitle">  Panel Data Style Id </td> 
    <td class="columnTitle">  Panel Link Style Id </td> 
    <td class="columnTitle">  Panel Title Style Id </td> 
    <td class="columnTitle">  Menu Style Id </td> 
    <td class="columnTitle">  Menu Link Style Id </td> 
    <td class="columnTitle">  Header Menu Style Id </td> 
    <td class="columnTitle">  Header Menu Link Style Id </td> 
    <td class="columnTitle">  List Frame Style Id </td> 
    <td class="columnTitle">  List Subject Style Id </td> 
    <td class="columnTitle">  List Data Style Id </td> 
    <td class="columnTitle">  Subject Style Id </td> 
    <td class="columnTitle">  Data Style Id </td> 
    <td class="columnTitle">  Single Frame Style Id </td> 
    <td class="columnTitle">  Single Subject Style Id </td> 
    <td class="columnTitle">  Single Data Style Id </td> 
    <td class="columnTitle">  Content Panel Style Id </td> 
    <td class="columnTitle">  Content Panel Title Style Id </td> 
    <td class="columnTitle">  Global </td> 
    <td class="columnTitle">  Disable </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleTheme _StyleTheme = (StyleTheme) iter.next();
%>

<TR id="tableRow<%= _StyleTheme.getId()%>">
    <td> <%= _StyleTheme.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _StyleTheme.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _StyleTheme.getTitle()  %> </td>
	<td> <%= _StyleTheme.getBodyWidth()  %> </td>
	<td> <%= _StyleTheme.getBodyAlign()  %> </td>
	<td> <%= _StyleTheme.getBodyBgColor()  %> </td>
	<td> <%= _StyleTheme.getBodyBgImage()  %> </td>
	<td> <%= _StyleTheme.getBodyBgAttach()  %> </td>
	<td> <%= _StyleTheme.getBodyBgRepeat()  %> </td>
	<td> <%= _StyleTheme.getBodyBgPosition()  %> </td>
	<td> <%= _StyleTheme.getContentBgColor()  %> </td>
	<td> <%= _StyleTheme.getUseAbsolute()  %> </td>
	<td> <%= _StyleTheme.getAbsoluteTop()  %> </td>
	<td> <%= _StyleTheme.getAbsoluteLeft()  %> </td>
	<td> <%= _StyleTheme.getAbsoluteRight()  %> </td>
	<td> <%= _StyleTheme.getAbsoluteBottom()  %> </td>
	<td> <%= _StyleTheme.getPanelStyleId()  %> </td>
	<td> <%= _StyleTheme.getPanelDataStyleId()  %> </td>
	<td> <%= _StyleTheme.getPanelLinkStyleId()  %> </td>
	<td> <%= _StyleTheme.getPanelTitleStyleId()  %> </td>
	<td> <%= _StyleTheme.getMenuStyleId()  %> </td>
	<td> <%= _StyleTheme.getMenuLinkStyleId()  %> </td>
	<td> <%= _StyleTheme.getHeaderMenuStyleId()  %> </td>
	<td> <%= _StyleTheme.getHeaderMenuLinkStyleId()  %> </td>
	<td> <%= _StyleTheme.getListFrameStyleId()  %> </td>
	<td> <%= _StyleTheme.getListSubjectStyleId()  %> </td>
	<td> <%= _StyleTheme.getListDataStyleId()  %> </td>
	<td> <%= _StyleTheme.getSubjectStyleId()  %> </td>
	<td> <%= _StyleTheme.getDataStyleId()  %> </td>
	<td> <%= _StyleTheme.getSingleFrameStyleId()  %> </td>
	<td> <%= _StyleTheme.getSingleSubjectStyleId()  %> </td>
	<td> <%= _StyleTheme.getSingleDataStyleId()  %> </td>
	<td> <%= _StyleTheme.getContentPanelStyleId()  %> </td>
	<td> <%= _StyleTheme.getContentPanelTitleStyleId()  %> </td>
	<td> <%= _StyleTheme.getGlobal()  %> </td>
	<td> <%= _StyleTheme.getDisable()  %> </td>
	<td> <%= _StyleTheme.getTimeCreated()  %> </td>
	<td> <%= _StyleTheme.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/styleThemeAction.html?del=true&id=<%=_StyleTheme.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_StyleTheme.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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
