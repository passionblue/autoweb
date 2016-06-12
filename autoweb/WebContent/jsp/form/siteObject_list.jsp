<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
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
	SiteDS ds = SiteDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getAll();
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_site_object_form.html?prv_returnPage=site_object_home"> Add New 2</a> <br>
<br/>
<a href="/v_site_object_home.html">home</a> | <a href="/v_site_object_home.html">home</a> | <a href="/v_site_object_home.html">home</a>
<br/>
<br/>



<%
	List list_Site = new ArrayList();
	SiteDS ds_Site = SiteDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_Site = ds_Site.getAll();
	else		
    	list_Site = ds_Site.getAll();

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_Site, numDisplayInPage, listPage);

	list_Site = PagingUtil.getPagedList(pagingInfo, list_Site);
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

    <td class="columnTitle">  Site Url </td> 
    <td class="columnTitle">  Account Id </td> 
    <td class="columnTitle">  Created Time </td> 
    <td class="columnTitle">  Site Group </td> 
    <td class="columnTitle">  Registered </td> 
    <td class="columnTitle">  On Sale </td> 
    <td class="columnTitle">  Super Admin Enable </td> 
    <td class="columnTitle">  Site Register Enable </td> 
    <td class="columnTitle">  Subdomain Enable </td> 
    <td class="columnTitle">  Site Register Site </td> 
    <td class="columnTitle">  Base Site Id </td> 
    <td class="columnTitle">  Subsite </td> 
    <td class="columnTitle">  Disabled </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_Site.iterator();iter.hasNext();) {
        Site o_Site = (Site) iter.next();
%>

<TR id="tableRow<%= o_Site.getId()%>">
    <td> <%= o_Site.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_Site.getId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_Site.getSiteUrl()  %> </td>
	<td> <%= o_Site.getAccountId()  %> </td>
	<td> <%= o_Site.getCreatedTime()  %> </td>
	<td> <%= o_Site.getSiteGroup()  %> </td>
	<td> <%= o_Site.getRegistered()  %> </td>
	<td> <%= o_Site.getOnSale()  %> </td>
	<td> <%= o_Site.getSuperAdminEnable()  %> </td>
	<td> <%= o_Site.getSiteRegisterEnable()  %> </td>
	<td> <%= o_Site.getSubdomainEnable()  %> </td>
	<td> <%= o_Site.getSiteRegisterSite()  %> </td>
	<td> <%= o_Site.getBaseSiteId()  %> </td>
	<td> <%= o_Site.getSubsite()  %> </td>
	<td> <%= o_Site.getDisabled()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_site_object_form('<%=o_Site.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/siteObjectAction.html?del=true&id=<%=o_Site.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_Site.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_site_object_form('<%=o_Site.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_site_object_form(target){
		location.href='/v_site_object_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_site_object_form(target){
		javascript:sendFormAjaxSimple('/siteObjectAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/siteObjectAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

