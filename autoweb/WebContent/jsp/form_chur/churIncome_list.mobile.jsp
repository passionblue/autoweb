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
	ChurIncomeDS ds = ChurIncomeDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_chur_income_form.html?prv_returnPage=chur_income_home"> Add New 2</a> <br>
<br/>
<a href="/v_chur_income_home.html">home</a> | <a href="/v_chur_income_home.html">home</a> | <a href="/v_chur_income_home.html">home</a>
<br/>
<br/>



<%
	List list_ChurIncome = new ArrayList();
	ChurIncomeDS ds_ChurIncome = ChurIncomeDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_ChurIncome = ds_ChurIncome.getAll();
	else		
    	list_ChurIncome = ds_ChurIncome.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_ChurIncome, numDisplayInPage, listPage);

	list_ChurIncome = PagingUtil.getPagedList(pagingInfo, list_ChurIncome);
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

    <td class="columnTitle">  Year </td> 
    <td class="columnTitle">  Week </td> 
    <td class="columnTitle">  Chur Member Id </td> 
    <td class="columnTitle">  Income Item Id </td> 
    <td class="columnTitle">  Ammount </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_ChurIncome.iterator();iter.hasNext();) {
        ChurIncomeDataHolder o_ChurIncome = (ChurIncomeDataHolder) iter.next();
%>

<TR id="tableRow<%= o_ChurIncome.getId()%>">
    <td> <%= o_ChurIncome.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_ChurIncome.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_ChurIncome.getYear()  %> </td>
	<td> <%= o_ChurIncome.getWeek()  %> </td>
	<td> <%= o_ChurIncome.getChurMemberId()  %> </td>
	<td> <%= o_ChurIncome.getIncomeItemId()  %> </td>
	<td> <%= o_ChurIncome.getAmmount()  %> </td>
	<td> <%= o_ChurIncome.getTimeCreated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_chur_income_form('<%=o_ChurIncome.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/churIncomeAction.html?del=true&id=<%=o_ChurIncome.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_ChurIncome.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_chur_income_form('<%=o_ChurIncome.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_chur_income_form(target){
		location.href='/v_chur_income_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_chur_income_form(target){
		javascript:sendFormAjaxSimple('/churIncomeAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/churIncomeAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

