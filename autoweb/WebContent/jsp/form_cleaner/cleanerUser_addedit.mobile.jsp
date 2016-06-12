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
    CleanerUser _CleanerUser = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerUser = CleanerUserDS.getInstance().getById(id);
		if ( _CleanerUser == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerUser = new CleanerUser();// CleanerUserDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _CleanerUser == null) _CleanerUser = new CleanerUser();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_user_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _autosite_user_idValue= (reqParams.get("autositeUserId")==null?WebUtil.display(_CleanerUser.getAutositeUserId()):WebUtil.display((String)reqParams.get("autositeUserId")));
    String _inactivatedValue= (reqParams.get("inactivated")==null?WebUtil.display(_CleanerUser.getInactivated()):WebUtil.display((String)reqParams.get("inactivated")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_CleanerUser.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_CleanerUser.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String pagestamp = "cleaner_user_" + System.nanoTime();
%> 

<br>
<div id="cleanerUserForm" class="formFrame">
<div id="cleanerUserFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerUserForm_Form" method="POST" action="/cleanerUserAction.html" id="cleanerUserForm_Form">





	<div id="cleanerUserForm_autositeUserId_field" class="formFieldFrame">
    <div id="cleanerUserForm_autositeUserId_label" class="formLabel" >Autosite User Id </div>
    <div id="cleanerUserForm_autositeUserId_text" class="formFieldText" >       
        <input id="autositeUserId" class="field" type="text" size="70" name="autositeUserId" value="<%=WebUtil.display(_autosite_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerUserForm_inactivated_field" class="formFieldFrame">
    <div id="cleanerUserForm_inactivated_label" class="formLabel" >Inactivated </div>
    <div id="cleanerUserForm_inactivated_text" class="formFieldText" >       
        <input id="inactivated" class="field" type="text" size="70" name="inactivated" value="<%=WebUtil.display(_inactivatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







<div class="submitFrame">

    <div id="cleanerUserForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerUserForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerUserForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerUserForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerUserForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerUserForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerUserForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerUser.getId()%>">

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
<a href="/v_cleaner_user_home.html">home</a> | <a href="/v_cleaner_user_home.html">home</a> | <a href="/v_cleaner_user_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerUser = new ArrayList();
	CleanerUserDS ds_CleanerUser = CleanerUserDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerUser = ds_CleanerUser.getAll();
	else		
    	list_CleanerUser = ds_CleanerUser.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerUser, numDisplayInPage, listPage);

	list_CleanerUser = PagingUtil.getPagedList(pagingInfo, list_CleanerUser);
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

    <td class="columnTitle">  Autosite User Id </td> 
    <td class="columnTitle">  Inactivated </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerUser.iterator();iter.hasNext();) {
        CleanerUser o_CleanerUser = (CleanerUser) iter.next();
%>

<TR id="tableRow<%= o_CleanerUser.getId()%>">
    <td> <%= o_CleanerUser.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerUser.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerUser.getAutositeUserId()  %> </td>
	<td> <%= o_CleanerUser.getInactivated()  %> </td>
	<td> <%= o_CleanerUser.getTimeCreated()  %> </td>
	<td> <%= o_CleanerUser.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_user_form('<%=o_CleanerUser.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerUserAction.html?del=true&id=<%=o_CleanerUser.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerUser.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_user_form('<%=o_CleanerUser.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_user_form(target){
		location.href='/v_cleaner_user_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_user_form(target){
		javascript:sendFormAjaxSimple('/cleanerUserAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerUserAction.html?ajxr=hb",
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
