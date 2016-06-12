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
    RequestHistory _RequestHistory = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_RequestHistory = RequestHistoryDS.getInstance().getById(id);
		if ( _RequestHistory == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _RequestHistory = new RequestHistory();// RequestHistoryDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _RequestHistory == null) _RequestHistory = new RequestHistory();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "request_history_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _forward_site_idValue= (reqParams.get("forwardSiteId")==null?WebUtil.display(_RequestHistory.getForwardSiteId()):WebUtil.display((String)reqParams.get("forwardSiteId")));
    String _is_droppedValue= (reqParams.get("isDropped")==null?WebUtil.display(_RequestHistory.getIsDropped()):WebUtil.display((String)reqParams.get("isDropped")));
    String _is_pagelessValue= (reqParams.get("isPageless")==null?WebUtil.display(_RequestHistory.getIsPageless()):WebUtil.display((String)reqParams.get("isPageless")));
    String _is_loginValue= (reqParams.get("isLogin")==null?WebUtil.display(_RequestHistory.getIsLogin()):WebUtil.display((String)reqParams.get("isLogin")));
    String _is_ajaxValue= (reqParams.get("isAjax")==null?WebUtil.display(_RequestHistory.getIsAjax()):WebUtil.display((String)reqParams.get("isAjax")));
    String _is_robotValue= (reqParams.get("isRobot")==null?WebUtil.display(_RequestHistory.getIsRobot()):WebUtil.display((String)reqParams.get("isRobot")));
    String _useridValue= (reqParams.get("userid")==null?WebUtil.display(_RequestHistory.getUserid()):WebUtil.display((String)reqParams.get("userid")));
    String _user_agentValue= (reqParams.get("userAgent")==null?WebUtil.display(_RequestHistory.getUserAgent()):WebUtil.display((String)reqParams.get("userAgent")));
    String _referValue= (reqParams.get("refer")==null?WebUtil.display(_RequestHistory.getRefer()):WebUtil.display((String)reqParams.get("refer")));
    String _robotValue= (reqParams.get("robot")==null?WebUtil.display(_RequestHistory.getRobot()):WebUtil.display((String)reqParams.get("robot")));
    String _remote_ipValue= (reqParams.get("remoteIp")==null?WebUtil.display(_RequestHistory.getRemoteIp()):WebUtil.display((String)reqParams.get("remoteIp")));
    String _site_urlValue= (reqParams.get("siteUrl")==null?WebUtil.display(_RequestHistory.getSiteUrl()):WebUtil.display((String)reqParams.get("siteUrl")));
    String _uriValue= (reqParams.get("uri")==null?WebUtil.display(_RequestHistory.getUri()):WebUtil.display((String)reqParams.get("uri")));
    String _queryValue= (reqParams.get("query")==null?WebUtil.display(_RequestHistory.getQuery()):WebUtil.display((String)reqParams.get("query")));
    String _rpciValue= (reqParams.get("rpci")==null?WebUtil.display(_RequestHistory.getRpci()):WebUtil.display((String)reqParams.get("rpci")));
    String _session_idValue= (reqParams.get("sessionId")==null?WebUtil.display(_RequestHistory.getSessionId()):WebUtil.display((String)reqParams.get("sessionId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_RequestHistory.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String pagestamp = "request_history_" + System.nanoTime();
%> 

<br>
<div id="requestHistoryForm" class="formFrame">
<div id="requestHistoryFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="requestHistoryForm_Form" method="POST" action="/requestHistoryAction.html" id="requestHistoryForm_Form">





	<div id="requestHistoryForm_forwardSiteId_field" class="formFieldFrame">
    <div id="requestHistoryForm_forwardSiteId_label" class="formLabel" >Forward Site Id </div>
    <div id="requestHistoryForm_forwardSiteId_text" class="formFieldText" >       
        <input id="forwardSiteId" class="field" type="text" size="70" name="forwardSiteId" value="<%=WebUtil.display(_forward_site_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_isDropped_field" class="formFieldFrame">
    <div id="requestHistoryForm_isDropped_label" class="formLabel" >Is Dropped </div>
    <div id="requestHistoryForm_isDropped_text" class="formFieldText" >       
        <input id="isDropped" class="field" type="text" size="70" name="isDropped" value="<%=WebUtil.display(_is_droppedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_isPageless_field" class="formFieldFrame">
    <div id="requestHistoryForm_isPageless_label" class="formLabel" >Is Pageless </div>
    <div id="requestHistoryForm_isPageless_text" class="formFieldText" >       
        <input id="isPageless" class="field" type="text" size="70" name="isPageless" value="<%=WebUtil.display(_is_pagelessValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_isLogin_field" class="formFieldFrame">
    <div id="requestHistoryForm_isLogin_label" class="formLabel" >Is Login </div>
    <div id="requestHistoryForm_isLogin_text" class="formFieldText" >       
        <input id="isLogin" class="field" type="text" size="70" name="isLogin" value="<%=WebUtil.display(_is_loginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_isAjax_field" class="formFieldFrame">
    <div id="requestHistoryForm_isAjax_label" class="formLabel" >Is Ajax </div>
    <div id="requestHistoryForm_isAjax_text" class="formFieldText" >       
        <input id="isAjax" class="field" type="text" size="70" name="isAjax" value="<%=WebUtil.display(_is_ajaxValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_isRobot_field" class="formFieldFrame">
    <div id="requestHistoryForm_isRobot_label" class="formLabel" >Is Robot </div>
    <div id="requestHistoryForm_isRobot_text" class="formFieldText" >       
        <input id="isRobot" class="field" type="text" size="70" name="isRobot" value="<%=WebUtil.display(_is_robotValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_userid_field" class="formFieldFrame">
    <div id="requestHistoryForm_userid_label" class="formLabel" >Userid </div>
    <div id="requestHistoryForm_userid_text" class="formFieldText" >       
        <input id="userid" class="field" type="text" size="70" name="userid" value="<%=WebUtil.display(_useridValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_userAgent_field" class="formFieldFrame">
    <div id="requestHistoryForm_userAgent_label" class="formLabel" >User Agent </div>
    <div id="requestHistoryForm_userAgent_text" class="formFieldText" >       
        <input id="userAgent" class="field" type="text" size="70" name="userAgent" value="<%=WebUtil.display(_user_agentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_refer_field" class="formFieldFrame">
    <div id="requestHistoryForm_refer_label" class="formLabel" >Refer </div>
    <div id="requestHistoryForm_refer_text" class="formFieldText" >       
        <input id="refer" class="field" type="text" size="70" name="refer" value="<%=WebUtil.display(_referValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_robot_field" class="formFieldFrame">
    <div id="requestHistoryForm_robot_label" class="formLabel" >Robot </div>
    <div id="requestHistoryForm_robot_text" class="formFieldText" >       
        <input id="robot" class="field" type="text" size="70" name="robot" value="<%=WebUtil.display(_robotValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_remoteIp_field" class="formFieldFrame">
    <div id="requestHistoryForm_remoteIp_label" class="formLabel" >Remote Ip </div>
    <div id="requestHistoryForm_remoteIp_text" class="formFieldText" >       
        <input id="remoteIp" class="field" type="text" size="70" name="remoteIp" value="<%=WebUtil.display(_remote_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_siteUrl_field" class="formFieldFrame">
    <div id="requestHistoryForm_siteUrl_label" class="formLabel" >Site Url </div>
    <div id="requestHistoryForm_siteUrl_text" class="formFieldText" >       
        <input id="siteUrl" class="field" type="text" size="70" name="siteUrl" value="<%=WebUtil.display(_site_urlValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_uri_field" class="formFieldFrame">
    <div id="requestHistoryForm_uri_label" class="formLabel" >Uri </div>
    <div id="requestHistoryForm_uri_text" class="formFieldText" >       
        <input id="uri" class="field" type="text" size="70" name="uri" value="<%=WebUtil.display(_uriValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_query_field" class="formFieldFrame">
    <div id="requestHistoryForm_query_label" class="formLabel" >Query </div>
    <div id="requestHistoryForm_query_text" class="formFieldText" >       
        <input id="query" class="field" type="text" size="70" name="query" value="<%=WebUtil.display(_queryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_rpci_field" class="formFieldFrame">
    <div id="requestHistoryForm_rpci_label" class="formLabel" >Rpci </div>
    <div id="requestHistoryForm_rpci_text" class="formFieldText" >       
        <input id="rpci" class="field" type="text" size="70" name="rpci" value="<%=WebUtil.display(_rpciValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_sessionId_field" class="formFieldFrame">
    <div id="requestHistoryForm_sessionId_label" class="formLabel" >Session Id </div>
    <div id="requestHistoryForm_sessionId_text" class="formFieldText" >       
        <input id="sessionId" class="field" type="text" size="70" name="sessionId" value="<%=WebUtil.display(_session_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




<div class="submitFrame">

    <div id="requestHistoryForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.requestHistoryForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="requestHistoryForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="requestHistoryForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="requestHistoryForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="requestHistoryForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="requestHistoryForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    

    <div id="requestHistoryForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();"><%= WebUtil.capitalize("back") %></a>
    </div>      
    <div id="requestHistoryForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_ext_<%=pagestamp%>();"><%= WebUtil.capitalize("ext") %></a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">

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
<a href="/v_request_history_home.html">home</a> | <a href="/v_request_history_home.html">home</a> | <a href="/v_request_history_home.html">home</a>
<br/>
<br/>



<%
	List list_RequestHistory = new ArrayList();
	RequestHistoryDS ds_RequestHistory = RequestHistoryDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_RequestHistory = ds_RequestHistory.getAll();
	else		
    	list_RequestHistory = ds_RequestHistory.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_RequestHistory, numDisplayInPage, listPage);

	list_RequestHistory = PagingUtil.getPagedList(pagingInfo, list_RequestHistory);
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

    <td class="columnTitle">  Forward Site Id </td> 
    <td class="columnTitle">  Is Dropped </td> 
    <td class="columnTitle">  Is Pageless </td> 
    <td class="columnTitle">  Is Login </td> 
    <td class="columnTitle">  Is Ajax </td> 
    <td class="columnTitle">  Is Robot </td> 
    <td class="columnTitle">  Userid </td> 
    <td class="columnTitle">  User Agent </td> 
    <td class="columnTitle">  Refer </td> 
    <td class="columnTitle">  Robot </td> 
    <td class="columnTitle">  Remote Ip </td> 
    <td class="columnTitle">  Site Url </td> 
    <td class="columnTitle">  Uri </td> 
    <td class="columnTitle">  Query </td> 
    <td class="columnTitle">  Rpci </td> 
    <td class="columnTitle">  Session Id </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_RequestHistory.iterator();iter.hasNext();) {
        RequestHistory o_RequestHistory = (RequestHistory) iter.next();
%>

<TR id="tableRow<%= o_RequestHistory.getId()%>">
    <td> <%= o_RequestHistory.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_RequestHistory.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_RequestHistory.getForwardSiteId()  %> </td>
	<td> <%= o_RequestHistory.getIsDropped()  %> </td>
	<td> <%= o_RequestHistory.getIsPageless()  %> </td>
	<td> <%= o_RequestHistory.getIsLogin()  %> </td>
	<td> <%= o_RequestHistory.getIsAjax()  %> </td>
	<td> <%= o_RequestHistory.getIsRobot()  %> </td>
	<td> <%= o_RequestHistory.getUserid()  %> </td>
	<td> <%= o_RequestHistory.getUserAgent()  %> </td>
	<td> <%= o_RequestHistory.getRefer()  %> </td>
	<td> <%= o_RequestHistory.getRobot()  %> </td>
	<td> <%= o_RequestHistory.getRemoteIp()  %> </td>
	<td> <%= o_RequestHistory.getSiteUrl()  %> </td>
	<td> <%= o_RequestHistory.getUri()  %> </td>
	<td> <%= o_RequestHistory.getQuery()  %> </td>
	<td> <%= o_RequestHistory.getRpci()  %> </td>
	<td> <%= o_RequestHistory.getSessionId()  %> </td>
	<td> <%= o_RequestHistory.getTimeCreated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_request_history_form('<%=o_RequestHistory.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/requestHistoryAction.html?del=true&id=<%=o_RequestHistory.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_RequestHistory.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_request_history_form('<%=o_RequestHistory.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_request_history_form(target){
		location.href='/v_request_history_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_request_history_form(target){
		javascript:sendFormAjaxSimple('/requestHistoryAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/requestHistoryAction.html?ajxr=hb",
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
