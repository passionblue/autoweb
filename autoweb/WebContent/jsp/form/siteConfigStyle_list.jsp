<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
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


<a href="t_site_config_style_form.html?prv_returnPage=site_config_style_home"> Add New 2</a> <br>
<br/>
<a href="/v_site_config_style_home.html">home</a> | <a href="/v_site_config_style_home.html">home</a> | <a href="/v_site_config_style_home.html">home</a>
<br/>
<br/>



<%
	List list_SiteConfigStyle = new ArrayList();
	SiteConfigStyleDS ds_SiteConfigStyle = SiteConfigStyleDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_SiteConfigStyle = ds_SiteConfigStyle.getAll();
	else		
    	list_SiteConfigStyle = ds_SiteConfigStyle.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_SiteConfigStyle, numDisplayInPage, listPage);

	list_SiteConfigStyle = PagingUtil.getPagedList(pagingInfo, list_SiteConfigStyle);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>
<!-- =================== END PAGING =================== -->

 
<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
//	if (showListAllByAdmin) {
	if (true) {
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

    for(Iterator iter = list_SiteConfigStyle.iterator();iter.hasNext();) {
        SiteConfigStyle o_SiteConfigStyle = (SiteConfigStyle) iter.next();
%>

<TR id="tableRow<%= o_SiteConfigStyle.getId()%>">
    <td> <%= o_SiteConfigStyle.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_SiteConfigStyle.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_SiteConfigStyle.getThemeId()  %> </td>
	<td> <%= o_SiteConfigStyle.getCssIndex()  %> </td>
	<td> <%= o_SiteConfigStyle.getCssImport()  %> </td>
	<td> <%= o_SiteConfigStyle.getLayoutIndex()  %> </td>
	<td> <%= o_SiteConfigStyle.getTimeCreated()  %> </td>
	<td> <%= o_SiteConfigStyle.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_site_config_style_form('<%=o_SiteConfigStyle.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/siteConfigStyleAction.html?del=true&id=<%=o_SiteConfigStyle.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_SiteConfigStyle.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_site_config_style_form('<%=o_SiteConfigStyle.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
	</td>
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
	function open_edit_site_config_style_form(target){
		location.href='/v_site_config_style_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_site_config_style_form(target){
		javascript:sendFormAjaxSimple('/siteConfigStyleAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/siteConfigStyleAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

