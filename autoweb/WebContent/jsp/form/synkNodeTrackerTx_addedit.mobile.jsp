<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
/* 
Template last modification history:


Source Generated: Sat Feb 14 00:12:25 EST 2015
*/

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
    SynkNodeTrackerTx _SynkNodeTrackerTx = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_SynkNodeTrackerTx = SynkNodeTrackerTxDS.getInstance().getById(id);
		if ( _SynkNodeTrackerTx == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _SynkNodeTrackerTx = new SynkNodeTrackerTx();// SynkNodeTrackerTxDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _SynkNodeTrackerTx == null) _SynkNodeTrackerTx = new SynkNodeTrackerTx();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "synk_node_tracker_tx_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _namespaceValue= (reqParams.get("namespace")==null?WebUtil.display(_SynkNodeTrackerTx.getNamespace()):WebUtil.display((String)reqParams.get("namespace")));
    String _device_idValue= (reqParams.get("deviceId")==null?WebUtil.display(_SynkNodeTrackerTx.getDeviceId()):WebUtil.display((String)reqParams.get("deviceId")));
    String _tx_tokenValue= (reqParams.get("txToken")==null?WebUtil.display(_SynkNodeTrackerTx.getTxToken()):WebUtil.display((String)reqParams.get("txToken")));
    String _stamp_ackedValue= (reqParams.get("stampAcked")==null?WebUtil.display(_SynkNodeTrackerTx.getStampAcked()):WebUtil.display((String)reqParams.get("stampAcked")));
    String _stamp_lastValue= (reqParams.get("stampLast")==null?WebUtil.display(_SynkNodeTrackerTx.getStampLast()):WebUtil.display((String)reqParams.get("stampLast")));
    String _num_recordsValue= (reqParams.get("numRecords")==null?WebUtil.display(_SynkNodeTrackerTx.getNumRecords()):WebUtil.display((String)reqParams.get("numRecords")));
    String _ipValue= (reqParams.get("ip")==null?WebUtil.display(_SynkNodeTrackerTx.getIp()):WebUtil.display((String)reqParams.get("ip")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SynkNodeTrackerTx.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String pagestamp = "synk_node_tracker_tx_" + System.nanoTime();
%> 

<br>
<div id="synkNodeTrackerTxForm" class="formFrame">
<div id="synkNodeTrackerTxFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="synkNodeTrackerTxForm_Form" method="POST" action="/synkNodeTrackerTxAction.html" id="synkNodeTrackerTxForm_Form">





	<div id="synkNodeTrackerTxForm_namespace_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_namespace_label" class="formLabel" >Namespace </div>
    <div id="synkNodeTrackerTxForm_namespace_text" class="formFieldText" >       
        <input id="namespace" class="field" type="text" size="70" name="namespace" value="<%=WebUtil.display(_namespaceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_deviceId_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_deviceId_label" class="formLabel" >Device Id </div>
    <div id="synkNodeTrackerTxForm_deviceId_text" class="formFieldText" >       
        <input id="deviceId" class="field" type="text" size="70" name="deviceId" value="<%=WebUtil.display(_device_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_txToken_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_txToken_label" class="formLabel" >Tx Token </div>
    <div id="synkNodeTrackerTxForm_txToken_text" class="formFieldText" >       
        <input id="txToken" class="field" type="text" size="70" name="txToken" value="<%=WebUtil.display(_tx_tokenValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_stampAcked_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_stampAcked_label" class="formLabel" >Stamp Acked </div>
    <div id="synkNodeTrackerTxForm_stampAcked_text" class="formFieldText" >       
        <input id="stampAcked" class="field" type="text" size="70" name="stampAcked" value="<%=WebUtil.display(_stamp_ackedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_stampLast_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_stampLast_label" class="formLabel" >Stamp Last </div>
    <div id="synkNodeTrackerTxForm_stampLast_text" class="formFieldText" >       
        <input id="stampLast" class="field" type="text" size="70" name="stampLast" value="<%=WebUtil.display(_stamp_lastValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_numRecords_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_numRecords_label" class="formLabel" >Num Records </div>
    <div id="synkNodeTrackerTxForm_numRecords_text" class="formFieldText" >       
        <input id="numRecords" class="field" type="text" size="70" name="numRecords" value="<%=WebUtil.display(_num_recordsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_ip_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_ip_label" class="formLabel" >Ip </div>
    <div id="synkNodeTrackerTxForm_ip_text" class="formFieldText" >       
        <input id="ip" class="field" type="text" size="70" name="ip" value="<%=WebUtil.display(_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_timeCreated_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_timeCreated_label" class="formLabel" >Time Created </div>
    <div id="synkNodeTrackerTxForm_timeCreated_text" class="formFieldText" >       
        <input id="timeCreated" class="field" type="text" size="70" name="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="synkNodeTrackerTxForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.synkNodeTrackerTxForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="synkNodeTrackerTxForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="synkNodeTrackerTxForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="synkNodeTrackerTxForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="synkNodeTrackerTxForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="synkNodeTrackerTxForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    

    <div id="synkNodeTrackerTxForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();"><%= WebUtil.capitalize("back") %></a>
    </div>      
    <div id="synkNodeTrackerTxForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_ext_<%=pagestamp%>();"><%= WebUtil.capitalize("ext") %></a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SynkNodeTrackerTx.getId()%>">

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
<a href="/v_synk_node_tracker_tx_home.html">home</a> | <a href="/v_synk_node_tracker_tx_home.html">home</a> | <a href="/v_synk_node_tracker_tx_home.html">home</a>
<br/>
<br/>



<%
	List list_SynkNodeTrackerTx = new ArrayList();
	SynkNodeTrackerTxDS ds_SynkNodeTrackerTx = SynkNodeTrackerTxDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_SynkNodeTrackerTx = ds_SynkNodeTrackerTx.getAll();
	else		
    	list_SynkNodeTrackerTx = ds_SynkNodeTrackerTx.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_SynkNodeTrackerTx, numDisplayInPage, listPage);

	list_SynkNodeTrackerTx = PagingUtil.getPagedList(pagingInfo, list_SynkNodeTrackerTx);
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

    <td class="columnTitle">  Namespace </td> 
    <td class="columnTitle">  Device Id </td> 
    <td class="columnTitle">  Tx Token </td> 
    <td class="columnTitle">  Stamp Acked </td> 
    <td class="columnTitle">  Stamp Last </td> 
    <td class="columnTitle">  Num Records </td> 
    <td class="columnTitle">  Ip </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_SynkNodeTrackerTx.iterator();iter.hasNext();) {
        SynkNodeTrackerTx o_SynkNodeTrackerTx = (SynkNodeTrackerTx) iter.next();
%>

<TR id="tableRow<%= o_SynkNodeTrackerTx.getId()%>">
    <td> <%= o_SynkNodeTrackerTx.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_SynkNodeTrackerTx.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_SynkNodeTrackerTx.getNamespace()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getDeviceId()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getTxToken()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getStampAcked()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getStampLast()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getNumRecords()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getIp()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getTimeCreated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_synk_node_tracker_tx_form('<%=o_SynkNodeTrackerTx.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/synkNodeTrackerTxAction.html?del=true&id=<%=o_SynkNodeTrackerTx.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_SynkNodeTrackerTx.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_synk_node_tracker_tx_form('<%=o_SynkNodeTrackerTx.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_synk_node_tracker_tx_form(target){
		location.href='/v_synk_node_tracker_tx_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_synk_node_tracker_tx_form(target){
		javascript:sendFormAjaxSimple('/synkNodeTrackerTxAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/synkNodeTrackerTxAction.html?ajxr=hb",
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
