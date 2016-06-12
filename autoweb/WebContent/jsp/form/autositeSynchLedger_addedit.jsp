<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

	// This is ugrly not matured change. Just added to load previously entered values and put back to the fields. 
	boolean isEdit = false;	
    Map reqParams = (Map) request.getAttribute("k_previous_request_params");
    if ( reqParams == null) {
        reqParams = (Map) request.getAttribute("k_reserved_params");
    } else {
        isEdit = true;
    }

	String command = request.getParameter("cmd");

    String idStr  = "0";
    AutositeSynchLedger _AutositeSynchLedger = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_AutositeSynchLedger = AutositeSynchLedgerDS.getInstance().getById(id);
		if ( _AutositeSynchLedger == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _AutositeSynchLedger = new AutositeSynchLedger();// AutositeSynchLedgerDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _AutositeSynchLedger == null) _AutositeSynchLedger = new AutositeSynchLedger();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "autosite_synch_ledger_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _device_idValue= (reqParams.get("deviceId")==null?WebUtil.display(_AutositeSynchLedger.getDeviceId()):WebUtil.display((String)reqParams.get("deviceId")));
    String _original_ledger_idValue= (reqParams.get("originalLedgerId")==null?WebUtil.display(_AutositeSynchLedger.getOriginalLedgerId()):WebUtil.display((String)reqParams.get("originalLedgerId")));
    String _scopeValue= (reqParams.get("scope")==null?WebUtil.display(_AutositeSynchLedger.getScope()):WebUtil.display((String)reqParams.get("scope")));
    String _targetValue= (reqParams.get("target")==null?WebUtil.display(_AutositeSynchLedger.getTarget()):WebUtil.display((String)reqParams.get("target")));
    String _remote_tokenValue= (reqParams.get("remoteToken")==null?WebUtil.display(_AutositeSynchLedger.getRemoteToken()):WebUtil.display((String)reqParams.get("remoteToken")));
    String _object_idValue= (reqParams.get("objectId")==null?WebUtil.display(_AutositeSynchLedger.getObjectId()):WebUtil.display((String)reqParams.get("objectId")));
    String _synch_idValue= (reqParams.get("synchId")==null?WebUtil.display(_AutositeSynchLedger.getSynchId()):WebUtil.display((String)reqParams.get("synchId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_AutositeSynchLedger.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String pagestamp = "autosite_synch_ledger_" + System.nanoTime();
%> 

<br>
<div id="autositeSynchLedgerForm" class="formFrame">
<div id="autositeSynchLedgerFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="autositeSynchLedgerForm_Form" method="POST" action="/autositeSynchLedgerAction.html" id="autositeSynchLedgerForm_Form">





	<div id="autositeSynchLedgerForm_deviceId_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_deviceId_label" class="formLabel" >Device Id </div>
    <div id="autositeSynchLedgerForm_deviceId_text" class="formFieldText" >       
        <input id="deviceId" class="field" type="text" size="70" name="deviceId" value="<%=WebUtil.display(_device_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSynchLedgerForm_originalLedgerId_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_originalLedgerId_label" class="formLabel" >Original Ledger Id </div>
    <div id="autositeSynchLedgerForm_originalLedgerId_text" class="formFieldText" >       
        <input id="originalLedgerId" class="field" type="text" size="70" name="originalLedgerId" value="<%=WebUtil.display(_original_ledger_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSynchLedgerForm_scope_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_scope_label" class="formLabel" >Scope </div>
    <div id="autositeSynchLedgerForm_scope_text" class="formFieldText" >       
        <input id="scope" class="field" type="text" size="70" name="scope" value="<%=WebUtil.display(_scopeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSynchLedgerForm_target_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_target_label" class="formLabel" >Target </div>
    <div id="autositeSynchLedgerForm_target_text" class="formFieldText" >       
        <input id="target" class="field" type="text" size="70" name="target" value="<%=WebUtil.display(_targetValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSynchLedgerForm_remoteToken_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_remoteToken_label" class="formLabel" >Remote Token </div>
    <div id="autositeSynchLedgerForm_remoteToken_text" class="formFieldText" >       
        <input id="remoteToken" class="field" type="text" size="70" name="remoteToken" value="<%=WebUtil.display(_remote_tokenValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSynchLedgerForm_objectId_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_objectId_label" class="formLabel" >Object Id </div>
    <div id="autositeSynchLedgerForm_objectId_text" class="formFieldText" >       
        <input id="objectId" class="field" type="text" size="70" name="objectId" value="<%=WebUtil.display(_object_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSynchLedgerForm_synchId_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_synchId_label" class="formLabel" >Synch Id </div>
    <div id="autositeSynchLedgerForm_synchId_text" class="formFieldText" >       
        <input id="synchId" class="field" type="text" size="70" name="synchId" value="<%=WebUtil.display(_synch_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




<div class="submitFrame">

    <div id="autositeSynchLedgerForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.autositeSynchLedgerForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="autositeSynchLedgerForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="autositeSynchLedgerForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="autositeSynchLedgerForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="autositeSynchLedgerForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="autositeSynchLedgerForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    

    <div id="autositeSynchLedgerForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();"><%= WebUtil.capitalize("back") %></a>
    </div>      
    <div id="autositeSynchLedgerForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_ext_<%=pagestamp%>();"><%= WebUtil.capitalize("ext") %></a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeSynchLedger.getId()%>">

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
<a href="/v_autosite_synch_ledger_home.html">home</a> | <a href="/v_autosite_synch_ledger_home.html">home</a> | <a href="/v_autosite_synch_ledger_home.html">home</a>
<br/>
<br/>



<%
	List list_AutositeSynchLedger = new ArrayList();
	AutositeSynchLedgerDS ds_AutositeSynchLedger = AutositeSynchLedgerDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_AutositeSynchLedger = ds_AutositeSynchLedger.getAll();
	else		
    	list_AutositeSynchLedger = ds_AutositeSynchLedger.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_AutositeSynchLedger, numDisplayInPage, listPage);

	list_AutositeSynchLedger = PagingUtil.getPagedList(pagingInfo, list_AutositeSynchLedger);
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

    <td class="columnTitle">  Device Id </td> 
    <td class="columnTitle">  Original Ledger Id </td> 
    <td class="columnTitle">  Scope </td> 
    <td class="columnTitle">  Target </td> 
    <td class="columnTitle">  Remote Token </td> 
    <td class="columnTitle">  Object Id </td> 
    <td class="columnTitle">  Synch Id </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_AutositeSynchLedger.iterator();iter.hasNext();) {
        AutositeSynchLedger o_AutositeSynchLedger = (AutositeSynchLedger) iter.next();
%>

<TR id="tableRow<%= o_AutositeSynchLedger.getId()%>">
    <td> <%= o_AutositeSynchLedger.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_AutositeSynchLedger.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_AutositeSynchLedger.getDeviceId()  %> </td>
	<td> <%= o_AutositeSynchLedger.getOriginalLedgerId()  %> </td>
	<td> <%= o_AutositeSynchLedger.getScope()  %> </td>
	<td> <%= o_AutositeSynchLedger.getTarget()  %> </td>
	<td> <%= o_AutositeSynchLedger.getRemoteToken()  %> </td>
	<td> <%= o_AutositeSynchLedger.getObjectId()  %> </td>
	<td> <%= o_AutositeSynchLedger.getSynchId()  %> </td>
	<td> <%= o_AutositeSynchLedger.getTimeCreated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_autosite_synch_ledger_form('<%=o_AutositeSynchLedger.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/autositeSynchLedgerAction.html?del=true&id=<%=o_AutositeSynchLedger.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_AutositeSynchLedger.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_autosite_synch_ledger_form('<%=o_AutositeSynchLedger.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_autosite_synch_ledger_form(target){
		location.href='/v_autosite_synch_ledger_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_autosite_synch_ledger_form(target){
		javascript:sendFormAjaxSimple('/autositeSynchLedgerAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/autositeSynchLedgerAction.html?ajxr=hb",
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

	function submit_back_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function submit_ext_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=ext<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
</script>
