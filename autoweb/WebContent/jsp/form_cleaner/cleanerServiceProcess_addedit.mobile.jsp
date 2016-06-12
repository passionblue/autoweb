<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

    Map reqParams = (Map) request.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    CleanerServiceProcess _CleanerServiceProcess = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerServiceProcess = CleanerServiceProcessDS.getInstance().getById(id);
		if ( _CleanerServiceProcess == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerServiceProcess = new CleanerServiceProcess();// CleanerServiceProcessDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_service_process_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _ticket_idValue= (reqParams.get("ticketId")==null?WebUtil.display(_CleanerServiceProcess.getTicketId()):WebUtil.display((String)reqParams.get("ticketId")));
    String _process_user_idValue= (reqParams.get("processUserId")==null?WebUtil.display(_CleanerServiceProcess.getProcessUserId()):WebUtil.display((String)reqParams.get("processUserId")));
    String _process_typeValue= (reqParams.get("processType")==null?WebUtil.display(_CleanerServiceProcess.getProcessType()):WebUtil.display((String)reqParams.get("processType")));
    String _time_startedValue= (reqParams.get("timeStarted")==null?WebUtil.display(_CleanerServiceProcess.getTimeStarted()):WebUtil.display((String)reqParams.get("timeStarted")));
    String _time_endedValue= (reqParams.get("timeEnded")==null?WebUtil.display(_CleanerServiceProcess.getTimeEnded()):WebUtil.display((String)reqParams.get("timeEnded")));
    String _noteValue= (reqParams.get("note")==null?WebUtil.display(_CleanerServiceProcess.getNote()):WebUtil.display((String)reqParams.get("note")));

    String pagestamp = "cleaner_service_process_" + System.nanoTime();
%> 

<br>
<div id="cleanerServiceProcessForm" class="formFrame">
<div id="cleanerServiceProcessFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerServiceProcessForm_Form" method="POST" action="/cleanerServiceProcessAction.html" id="cleanerServiceProcessForm_Form">





	<div id="cleanerServiceProcessForm_ticketId_field" class="formFieldFrame">
    <div id="cleanerServiceProcessForm_ticketId_label" class="formLabel" >Ticket Id </div>
    <div id="cleanerServiceProcessForm_ticketId_text" class="formFieldText" >       
        <input id="ticketId" class="field" type="text" size="70" name="ticketId" value="<%=WebUtil.display(_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceProcessForm_processUserId_field" class="formFieldFrame">
    <div id="cleanerServiceProcessForm_processUserId_label" class="formLabel" >Process User Id </div>
    <div id="cleanerServiceProcessForm_processUserId_text" class="formFieldText" >       
        <input id="processUserId" class="field" type="text" size="70" name="processUserId" value="<%=WebUtil.display(_process_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceProcessForm_processType_field" class="formFieldFrame">
    <div id="cleanerServiceProcessForm_processType_label" class="formLabel" >Process Type </div>
    <div id="cleanerServiceProcessForm_processType_text" class="formFieldText" >       
        <input id="processType" class="field" type="text" size="70" name="processType" value="<%=WebUtil.display(_process_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceProcessForm_timeStarted_field" class="formFieldFrame">
    <div id="cleanerServiceProcessForm_timeStarted_label" class="formLabel" >Time Started </div>
    <div id="cleanerServiceProcessForm_timeStarted_text" class="formFieldText" >       
        <input id="timeStarted" class="field" type="text" size="70" name="timeStarted" value="<%=WebUtil.display(_time_startedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceProcessForm_timeEnded_field" class="formFieldFrame">
    <div id="cleanerServiceProcessForm_timeEnded_label" class="formLabel" >Time Ended </div>
    <div id="cleanerServiceProcessForm_timeEnded_text" class="formFieldText" >       
        <input id="timeEnded" class="field" type="text" size="70" name="timeEnded" value="<%=WebUtil.display(_time_endedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceProcessForm_note_field" class="formFieldFrame">
    <div id="cleanerServiceProcessForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerServiceProcessForm_note_text" class="formFieldText" >       
        <input id="note" class="field" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="cleanerServiceProcessForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerServiceProcessForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerServiceProcessForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerServiceProcessForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerServiceProcessForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerServiceProcessForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerServiceProcessForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerServiceProcess.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">
<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
<INPUT TYPE="HIDDEN" NAME="fromto" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="prv_backPage" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="_reqhid" value="<%= WebUtil.display(SessionWrapper.wrapIt(request, site.getId()).getRequestHandleId()) %>">
</form>
</div> 				 
</div> <!-- form -->

<br/>
<a href="/v_cleaner_service_process_home.html">home</a> | <a href="/v_cleaner_service_process_home.html">home</a> | <a href="/v_cleaner_service_process_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerServiceProcess = new ArrayList();
	CleanerServiceProcessDS ds_CleanerServiceProcess = CleanerServiceProcessDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerServiceProcess = ds_CleanerServiceProcess.getAll();
	else		
    	list_CleanerServiceProcess = ds_CleanerServiceProcess.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerServiceProcess, numDisplayInPage, listPage);

	list_CleanerServiceProcess = PagingUtil.getPagedList(pagingInfo, list_CleanerServiceProcess);
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

    <td class="columnTitle">  Ticket Id </td> 
    <td class="columnTitle">  Process User Id </td> 
    <td class="columnTitle">  Process Type </td> 
    <td class="columnTitle">  Time Started </td> 
    <td class="columnTitle">  Time Ended </td> 
    <td class="columnTitle">  Note </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerServiceProcess.iterator();iter.hasNext();) {
        CleanerServiceProcess o_CleanerServiceProcess = (CleanerServiceProcess) iter.next();
%>

<TR id="tableRow<%= o_CleanerServiceProcess.getId()%>">
    <td> <%= o_CleanerServiceProcess.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerServiceProcess.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerServiceProcess.getTicketId()  %> </td>
	<td> <%= o_CleanerServiceProcess.getProcessUserId()  %> </td>
	<td> <%= o_CleanerServiceProcess.getProcessType()  %> </td>
	<td> <%= o_CleanerServiceProcess.getTimeStarted()  %> </td>
	<td> <%= o_CleanerServiceProcess.getTimeEnded()  %> </td>
	<td> <%= o_CleanerServiceProcess.getNote()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_service_process_form('<%=o_CleanerServiceProcess.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerServiceProcessAction.html?del=true&id=<%=o_CleanerServiceProcess.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerServiceProcess.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_service_process_form('<%=o_CleanerServiceProcess.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_service_process_form(target){
		location.href='/v_cleaner_service_process_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_service_process_form(target){
		javascript:sendFormAjaxSimple('/cleanerServiceProcessAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerServiceProcessAction.html?ajxr=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 300000);

//		setTimeout(function(){
//		}, 10000);
	});

	function submit_cancel_<%=pagestamp%>(){
		//alert("submit_cancel_");		
		//location.href='/moveTo.html?dest=<%=cancelPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=cancel<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	
	function submit_back_<%=pagestamp%>(){
		//alert("submit_back_");		
		//location.href='/moveTo.html?dest=<%=backPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_extent_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=extent<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

</script>
