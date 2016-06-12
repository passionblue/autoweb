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
	GenMainDS ds = GenMainDS.getInstance();    

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

<a href="t_gen_main_form.html?prv_returnPage=gen_main_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Active </td> 
    <td class="columnTitle">  Value </td> 
    <td class="columnTitle">  Data </td> 
    <td class="columnTitle">  Required </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        GenMain _GenMain = (GenMain) iter.next();
%>

<TR id="tableRow<%= _GenMain.getId()%>">
    <td> <%= _GenMain.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _GenMain.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _GenMain.getActive()  %> </td>
	<td> <%= _GenMain.getValue()  %> </td>
	<td> <%= _GenMain.getData()  %> </td>
	<td> <%= _GenMain.getRequired()  %> </td>
	<td> <%= _GenMain.getTimeCreated()  %> </td>
	<td> <%= _GenMain.getTimeUpdated()  %> </td>
	<td>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_gen_main_form('<%=_GenMain.getId()%>');">Edit</a>
	</td>
	<td> <a href="javascript:sendFormAjaxSimple('/genMainAction.html?del=true&id=<%=_GenMain.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_GenMain.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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
	function edit_gen_main_form(target){
		location.href='/v_gen_main_form.html?cmd=edit&prv_returnPage=gen_main_home&id=' + target;
	}

</script>
