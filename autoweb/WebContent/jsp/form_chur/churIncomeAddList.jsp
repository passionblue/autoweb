<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
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
	ChurIncomeAddDS ds = ChurIncomeAddDS.getInstance();    

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

<a href="t_chur_income_add_form.html?prv_returnPage=chur_income_add_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Chur Member Id </td> 
    <td class="columnTitle">  Tithe </td> 
    <td class="columnTitle">  Weekly </td> 
    <td class="columnTitle">  Thanks </td> 
    <td class="columnTitle">  Mission </td> 
    <td class="columnTitle">  Construction </td> 
    <td class="columnTitle">  Other </td> 
    <td class="columnTitle">  Week </td> 
    <td class="columnTitle">  Year </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurIncomeAddDataHolder _ChurIncomeAdd = (ChurIncomeAddDataHolder) iter.next();
%>

<TR id="tableRow<%= _ChurIncomeAdd.getId()%>">
    <td> <%= _ChurIncomeAdd.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _ChurIncomeAdd.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _ChurIncomeAdd.getChurMemberId()  %> </td>
	<td> <%= _ChurIncomeAdd.getTithe()  %> </td>
	<td> <%= _ChurIncomeAdd.getWeekly()  %> </td>
	<td> <%= _ChurIncomeAdd.getThanks()  %> </td>
	<td> <%= _ChurIncomeAdd.getMission()  %> </td>
	<td> <%= _ChurIncomeAdd.getConstruction()  %> </td>
	<td> <%= _ChurIncomeAdd.getOther()  %> </td>
	<td> <%= _ChurIncomeAdd.getWeek()  %> </td>
	<td> <%= _ChurIncomeAdd.getYear()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/churIncomeAddAction.html?del=true&id=<%=_ChurIncomeAdd.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_ChurIncomeAdd.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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
