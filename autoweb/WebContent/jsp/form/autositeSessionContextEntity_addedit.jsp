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
    AutositeSessionContextEntity _AutositeSessionContextEntity = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_AutositeSessionContextEntity = AutositeSessionContextEntityDS.getInstance().getById(id);
		if ( _AutositeSessionContextEntity == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _AutositeSessionContextEntity = new AutositeSessionContextEntity();// AutositeSessionContextEntityDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _AutositeSessionContextEntity == null) _AutositeSessionContextEntity = new AutositeSessionContextEntity();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "autosite_session_context_entity_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _serialValue= (reqParams.get("serial")==null?WebUtil.display(_AutositeSessionContextEntity.getSerial()):WebUtil.display((String)reqParams.get("serial")));
    String _is_loginValue= (reqParams.get("isLogin")==null?WebUtil.display(_AutositeSessionContextEntity.getIsLogin()):WebUtil.display((String)reqParams.get("isLogin")));
    String _time_loginValue= (reqParams.get("timeLogin")==null?WebUtil.display(_AutositeSessionContextEntity.getTimeLogin()):WebUtil.display((String)reqParams.get("timeLogin")));
    String _time_last_accessValue= (reqParams.get("timeLastAccess")==null?WebUtil.display(_AutositeSessionContextEntity.getTimeLastAccess()):WebUtil.display((String)reqParams.get("timeLastAccess")));
    String _login_user_idValue= (reqParams.get("loginUserId")==null?WebUtil.display(_AutositeSessionContextEntity.getLoginUserId()):WebUtil.display((String)reqParams.get("loginUserId")));
    String _session_typeValue= (reqParams.get("sessionType")==null?WebUtil.display(_AutositeSessionContextEntity.getSessionType()):WebUtil.display((String)reqParams.get("sessionType")));
    String _remote_device_idValue= (reqParams.get("remoteDeviceId")==null?WebUtil.display(_AutositeSessionContextEntity.getRemoteDeviceId()):WebUtil.display((String)reqParams.get("remoteDeviceId")));
    String _remote_ipValue= (reqParams.get("remoteIp")==null?WebUtil.display(_AutositeSessionContextEntity.getRemoteIp()):WebUtil.display((String)reqParams.get("remoteIp")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_AutositeSessionContextEntity.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_AutositeSessionContextEntity.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String pagestamp = "autosite_session_context_entity_" + System.nanoTime();
%> 

<br>
<div id="autositeSessionContextEntityForm" class="formFrame">
<div id="autositeSessionContextEntityFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="autositeSessionContextEntityForm_Form" method="POST" action="/autositeSessionContextEntityAction.html" id="autositeSessionContextEntityForm_Form">





	<div id="autositeSessionContextEntityForm_serial_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_serial_label" class="formLabel" >Serial </div>
    <div id="autositeSessionContextEntityForm_serial_text" class="formFieldText" >       
        <input id="serial" class="field" type="text" size="70" name="serial" value="<%=WebUtil.display(_serialValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_isLogin_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_isLogin_label" class="formLabel" >Is Login </div>
    <div id="autositeSessionContextEntityForm_isLogin_text" class="formFieldText" >       
        <input id="isLogin" class="field" type="text" size="70" name="isLogin" value="<%=WebUtil.display(_is_loginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_timeLogin_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_timeLogin_label" class="formLabel" >Time Login </div>
    <div id="autositeSessionContextEntityForm_timeLogin_text" class="formFieldText" >       
        <input id="timeLogin" class="field" type="text" size="70" name="timeLogin" value="<%=WebUtil.display(_time_loginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_timeLastAccess_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_timeLastAccess_label" class="formLabel" >Time Last Access </div>
    <div id="autositeSessionContextEntityForm_timeLastAccess_text" class="formFieldText" >       
        <input id="timeLastAccess" class="field" type="text" size="70" name="timeLastAccess" value="<%=WebUtil.display(_time_last_accessValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_loginUserId_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_loginUserId_label" class="formLabel" >Login User Id </div>
    <div id="autositeSessionContextEntityForm_loginUserId_text" class="formFieldText" >       
        <input id="loginUserId" class="field" type="text" size="70" name="loginUserId" value="<%=WebUtil.display(_login_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_sessionType_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_sessionType_label" class="formLabel" >Session Type </div>
    <div id="autositeSessionContextEntityForm_sessionType_text" class="formFieldText" >       
        <input id="sessionType" class="field" type="text" size="70" name="sessionType" value="<%=WebUtil.display(_session_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_remoteDeviceId_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_remoteDeviceId_label" class="formLabel" >Remote Device Id </div>
    <div id="autositeSessionContextEntityForm_remoteDeviceId_text" class="formFieldText" >       
        <input id="remoteDeviceId" class="field" type="text" size="70" name="remoteDeviceId" value="<%=WebUtil.display(_remote_device_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_remoteIp_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_remoteIp_label" class="formLabel" >Remote Ip </div>
    <div id="autositeSessionContextEntityForm_remoteIp_text" class="formFieldText" >       
        <input id="remoteIp" class="field" type="text" size="70" name="remoteIp" value="<%=WebUtil.display(_remote_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







<div class="submitFrame">

    <div id="autositeSessionContextEntityForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.autositeSessionContextEntityForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="autositeSessionContextEntityForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="autositeSessionContextEntityForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="autositeSessionContextEntityForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="autositeSessionContextEntityForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="autositeSessionContextEntityForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeSessionContextEntity.getId()%>">

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
<a href="/v_autosite_session_context_entity_home.html">home</a> | <a href="/v_autosite_session_context_entity_home.html">home</a> | <a href="/v_autosite_session_context_entity_home.html">home</a>
<br/>
<br/>



<%
	List list_AutositeSessionContextEntity = new ArrayList();
	AutositeSessionContextEntityDS ds_AutositeSessionContextEntity = AutositeSessionContextEntityDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_AutositeSessionContextEntity = ds_AutositeSessionContextEntity.getAll();
	else		
    	list_AutositeSessionContextEntity = ds_AutositeSessionContextEntity.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_AutositeSessionContextEntity, numDisplayInPage, listPage);

	list_AutositeSessionContextEntity = PagingUtil.getPagedList(pagingInfo, list_AutositeSessionContextEntity);
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

    <td class="columnTitle">  Serial </td> 
    <td class="columnTitle">  Is Login </td> 
    <td class="columnTitle">  Time Login </td> 
    <td class="columnTitle">  Time Last Access </td> 
    <td class="columnTitle">  Login User Id </td> 
    <td class="columnTitle">  Session Type </td> 
    <td class="columnTitle">  Remote Device Id </td> 
    <td class="columnTitle">  Remote Ip </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_AutositeSessionContextEntity.iterator();iter.hasNext();) {
        AutositeSessionContextEntity o_AutositeSessionContextEntity = (AutositeSessionContextEntity) iter.next();
%>

<TR id="tableRow<%= o_AutositeSessionContextEntity.getId()%>">
    <td> <%= o_AutositeSessionContextEntity.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_AutositeSessionContextEntity.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_AutositeSessionContextEntity.getSerial()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getIsLogin()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getTimeLogin()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getTimeLastAccess()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getLoginUserId()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getSessionType()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getRemoteDeviceId()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getRemoteIp()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getTimeCreated()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_autosite_session_context_entity_form('<%=o_AutositeSessionContextEntity.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/autositeSessionContextEntityAction.html?del=true&id=<%=o_AutositeSessionContextEntity.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_AutositeSessionContextEntity.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_autosite_session_context_entity_form('<%=o_AutositeSessionContextEntity.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_autosite_session_context_entity_form(target){
		location.href='/v_autosite_session_context_entity_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_autosite_session_context_entity_form(target){
		javascript:sendFormAjaxSimple('/autositeSessionContextEntityAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/autositeSessionContextEntityAction.html?ajxr=hb",
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
