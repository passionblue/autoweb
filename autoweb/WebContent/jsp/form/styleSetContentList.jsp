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
	StyleSetContentDS ds = StyleSetContentDS.getInstance();    

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

<a href="t_style_set_content_form.html?prv_returnPage=style_set_content_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Name </td> 
    <td class="columnTitle">  Id Prefix </td> 
    <td class="columnTitle">  List Frame Style Id </td> 
    <td class="columnTitle">  List Subject Style Id </td> 
    <td class="columnTitle">  List Data Style Id </td> 
    <td class="columnTitle">  Frame Style Id </td> 
    <td class="columnTitle">  Subject Style Id </td> 
    <td class="columnTitle">  Data Style Id </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleSetContent _StyleSetContent = (StyleSetContent) iter.next();
%>

<TR id="tableRow<%= _StyleSetContent.getId()%>">
    <td> <%= _StyleSetContent.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _StyleSetContent.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _StyleSetContent.getName()  %> </td>
	<td> <%= _StyleSetContent.getIdPrefix()  %> </td>
	<td> <%= _StyleSetContent.getListFrameStyleId()  %> </td>
	<td> <%= _StyleSetContent.getListSubjectStyleId()  %> </td>
	<td> <%= _StyleSetContent.getListDataStyleId()  %> </td>
	<td> <%= _StyleSetContent.getFrameStyleId()  %> </td>
	<td> <%= _StyleSetContent.getSubjectStyleId()  %> </td>
	<td> <%= _StyleSetContent.getDataStyleId()  %> </td>
	<td> <%= _StyleSetContent.getTimeCreated()  %> </td>
	<td> <%= _StyleSetContent.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/styleSetContentAction.html?del=true&id=<%=_StyleSetContent.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_StyleSetContent.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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
