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
	GenSimpleDS ds = GenSimpleDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_gen_simple_form.html?prv_returnPage=gen_simple_home"> Add New 2</a> <br>


<%
	List list_GenSimple = new ArrayList();
	GenSimpleDS ds_GenSimple = GenSimpleDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_GenSimple = ds_GenSimple.getAll();
	else		
    	list_GenSimple = ds_GenSimple.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_GenSimple, numDisplayInPage, listPage);

	list_GenSimple = PagingUtil.getPagedList(pagingInfo, list_GenSimple);
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

    <td class="columnTitle">  Data </td> 
    <td class="columnTitle">  Active </td> 
    <td class="columnTitle">  ExtraString </td> 
    <td class="columnTitle">  Ext Int </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_GenSimple.iterator();iter.hasNext();) {
        GenSimpleDataHolder o_GenSimple = (GenSimpleDataHolder) iter.next();
%>

<TR id="tableRow<%= o_GenSimple.getId()%>">
    <td> <%= o_GenSimple.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_GenSimple.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_GenSimple.getData()  %> </td>
	<td> <%= o_GenSimple.getActive()  %> </td>
	<td> <%= o_GenSimple.getExtString()  %> </td>
	<td> <%= o_GenSimple.getExtInt()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_gen_simple_form('<%=o_GenSimple.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/genSimpleAction.html?del=true&id=<%=o_GenSimple.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_GenSimple.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_gen_simple_form('<%=o_GenSimple.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_gen_simple_form(target){
		location.href='/v_gen_simple_form.html?cmd=edit&prv_returnPage=<%=PageViewUtil.getCurrentViewAlias(request)%>&id=' + target;
	}
	function delete_gen_simple_form(target){
		javascript:sendFormAjaxSimple('/genSimpleAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,target);
	}

</script>


