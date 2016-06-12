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
	UserManageDS ds = UserManageDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_user_manage_form.html?prv_returnPage=user_manage_home"> Add New 2</a> <br>
<br/>
<a href="/v_user_manage_home.html">home</a> | <a href="/v_user_manage_home.html">home</a> | <a href="/v_user_manage_home.html">home</a>
<br/>
<br/>



<%
	List list_UserManage = new ArrayList();
	UserManageDS ds_UserManage = UserManageDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_UserManage = ds_UserManage.getAll();
	else		
    	list_UserManage = ds_UserManage.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_UserManage, numDisplayInPage, listPage);

	list_UserManage = PagingUtil.getPagedList(pagingInfo, list_UserManage);
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

    <td class="columnTitle">  ExtraString </td> 
    <td class="columnTitle">  Ext Int </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_UserManage.iterator();iter.hasNext();) {
        UserManageDataHolder o_UserManage = (UserManageDataHolder) iter.next();
%>

<TR id="tableRow<%= o_UserManage.getId()%>">
    <td> <%= o_UserManage.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_UserManage.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_UserManage.getExtString()  %> </td>
	<td> <%= o_UserManage.getExtInt()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_user_manage_form('<%=o_UserManage.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/userManageAction.html?del=true&id=<%=o_UserManage.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_UserManage.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_user_manage_form('<%=o_UserManage.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_user_manage_form(target){
		location.href='/v_user_manage_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_user_manage_form(target){
		javascript:sendFormAjaxSimple('/userManageAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/userManageAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

