<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
/* 
Template last modification history:


Source Generated: Sat Feb 14 00:17:16 EST 2015
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
    SynkNamespaceRecord _SynkNamespaceRecord = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_SynkNamespaceRecord = SynkNamespaceRecordDS.getInstance().getById(id);
		if ( _SynkNamespaceRecord == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _SynkNamespaceRecord = new SynkNamespaceRecord();// SynkNamespaceRecordDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _SynkNamespaceRecord == null) _SynkNamespaceRecord = new SynkNamespaceRecord();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "synk_namespace_record_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _namespaceValue= (reqParams.get("namespace")==null?WebUtil.display(_SynkNamespaceRecord.getNamespace()):WebUtil.display((String)reqParams.get("namespace")));
    String _record_idValue= (reqParams.get("recordId")==null?WebUtil.display(_SynkNamespaceRecord.getRecordId()):WebUtil.display((String)reqParams.get("recordId")));
    String _stampValue= (reqParams.get("stamp")==null?WebUtil.display(_SynkNamespaceRecord.getStamp()):WebUtil.display((String)reqParams.get("stamp")));
    String _org_stampValue= (reqParams.get("orgStamp")==null?WebUtil.display(_SynkNamespaceRecord.getOrgStamp()):WebUtil.display((String)reqParams.get("orgStamp")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SynkNamespaceRecord.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SynkNamespaceRecord.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String pagestamp = "synk_namespace_record_" + System.nanoTime();
%> 

<br>
<div id="synkNamespaceRecordForm" class="formFrame">
<div id="synkNamespaceRecordFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="synkNamespaceRecordForm_Form" method="POST" action="/synkNamespaceRecordAction.html" id="synkNamespaceRecordForm_Form">





	<div id="synkNamespaceRecordForm_namespace_field" class="formFieldFrame">
    <div id="synkNamespaceRecordForm_namespace_label" class="formLabel" >Namespace </div>
    <div id="synkNamespaceRecordForm_namespace_text" class="formFieldText" >       
        <input id="namespace" class="field" type="text" size="70" name="namespace" value="<%=WebUtil.display(_namespaceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNamespaceRecordForm_recordId_field" class="formFieldFrame">
    <div id="synkNamespaceRecordForm_recordId_label" class="formLabel" >Record Id </div>
    <div id="synkNamespaceRecordForm_recordId_text" class="formFieldText" >       
        <input id="recordId" class="field" type="text" size="70" name="recordId" value="<%=WebUtil.display(_record_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNamespaceRecordForm_stamp_field" class="formFieldFrame">
    <div id="synkNamespaceRecordForm_stamp_label" class="formLabel" >Stamp </div>
    <div id="synkNamespaceRecordForm_stamp_text" class="formFieldText" >       
        <input id="stamp" class="field" type="text" size="70" name="stamp" value="<%=WebUtil.display(_stampValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNamespaceRecordForm_orgStamp_field" class="formFieldFrame">
    <div id="synkNamespaceRecordForm_orgStamp_label" class="formLabel" >Org Stamp </div>
    <div id="synkNamespaceRecordForm_orgStamp_text" class="formFieldText" >       
        <input id="orgStamp" class="field" type="text" size="70" name="orgStamp" value="<%=WebUtil.display(_org_stampValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







<div class="submitFrame">

    <div id="synkNamespaceRecordForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.synkNamespaceRecordForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="synkNamespaceRecordForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="synkNamespaceRecordForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="synkNamespaceRecordForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="synkNamespaceRecordForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="synkNamespaceRecordForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    

    <div id="synkNamespaceRecordForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();"><%= WebUtil.capitalize("back") %></a>
    </div>      
    <div id="synkNamespaceRecordForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_ext_<%=pagestamp%>();"><%= WebUtil.capitalize("ext") %></a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SynkNamespaceRecord.getId()%>">

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
<a href="/v_synk_namespace_record_home.html">home</a> | <a href="/v_synk_namespace_record_home.html">home</a> | <a href="/v_synk_namespace_record_home.html">home</a>
<br/>
<br/>



<%
	List list_SynkNamespaceRecord = new ArrayList();
	SynkNamespaceRecordDS ds_SynkNamespaceRecord = SynkNamespaceRecordDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_SynkNamespaceRecord = ds_SynkNamespaceRecord.getAll();
	else		
    	list_SynkNamespaceRecord = ds_SynkNamespaceRecord.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_SynkNamespaceRecord, numDisplayInPage, listPage);

	list_SynkNamespaceRecord = PagingUtil.getPagedList(pagingInfo, list_SynkNamespaceRecord);
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
    <td class="columnTitle">  Record Id </td> 
    <td class="columnTitle">  Stamp </td> 
    <td class="columnTitle">  Org Stamp </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_SynkNamespaceRecord.iterator();iter.hasNext();) {
        SynkNamespaceRecord o_SynkNamespaceRecord = (SynkNamespaceRecord) iter.next();
%>

<TR id="tableRow<%= o_SynkNamespaceRecord.getId()%>">
    <td> <%= o_SynkNamespaceRecord.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_SynkNamespaceRecord.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_SynkNamespaceRecord.getNamespace()  %> </td>
	<td> <%= o_SynkNamespaceRecord.getRecordId()  %> </td>
	<td> <%= o_SynkNamespaceRecord.getStamp()  %> </td>
	<td> <%= o_SynkNamespaceRecord.getOrgStamp()  %> </td>
	<td> <%= o_SynkNamespaceRecord.getTimeCreated()  %> </td>
	<td> <%= o_SynkNamespaceRecord.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_synk_namespace_record_form('<%=o_SynkNamespaceRecord.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/synkNamespaceRecordAction.html?del=true&id=<%=o_SynkNamespaceRecord.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_SynkNamespaceRecord.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_synk_namespace_record_form('<%=o_SynkNamespaceRecord.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_synk_namespace_record_form(target){
		location.href='/v_synk_namespace_record_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_synk_namespace_record_form(target){
		javascript:sendFormAjaxSimple('/synkNamespaceRecordAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/synkNamespaceRecordAction.html?ajxr=hb",
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
