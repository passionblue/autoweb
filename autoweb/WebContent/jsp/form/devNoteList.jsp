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
	DevNoteDS ds = DevNoteDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_dev_note_form.html?prv_returnPage=dev_note_home"> Add New 2</a> <br>


<%
	List list_DevNote = new ArrayList();
	DevNoteDS ds_DevNote = DevNoteDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_DevNote = ds_DevNote.getAll();
	else		
    	list_DevNote = ds_DevNote.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_DevNote, numDisplayInPage, listPage);

	list_DevNote = PagingUtil.getPagedList(pagingInfo, list_DevNote);
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

    <td class="columnTitle">  Note Type </td> 
    <td class="columnTitle">  Completed </td> 
    <td class="columnTitle">  Category </td> 
    <td class="columnTitle">  Subject </td> 
    <td class="columnTitle">  Note </td> 
    <td class="columnTitle">  Tags </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_DevNote.iterator();iter.hasNext();) {
        DevNote o_DevNote = (DevNote) iter.next();
%>

<TR id="tableRow<%= o_DevNote.getId()%>">
    <td> <%= o_DevNote.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_DevNote.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_DevNote.getNoteType()  %> </td>
	<td> <%= o_DevNote.getCompleted()  %> </td>
	<td> <%= o_DevNote.getCategory()  %> </td>
	<td> <%= o_DevNote.getSubject()  %> </td>
	<td> <%= o_DevNote.getNote()  %> </td>
	<td> <%= o_DevNote.getTags()  %> </td>
	<td> <%= o_DevNote.getTimeCreated()  %> </td>
	<td> <%= o_DevNote.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_dev_note_form('<%=o_DevNote.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/devNoteAction.html?del=true&id=<%=o_DevNote.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_DevNote.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_dev_note_form('<%=o_DevNote.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_dev_note_form(target){
		location.href='/v_dev_note_form.html?cmd=edit&prv_returnPage=<%=PageViewUtil.getCurrentViewAlias(request)%>&id=' + target;
	}
	function delete_dev_note_form(target){
		javascript:sendFormAjaxSimple('/devNoteAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,target);
	}

</script>


