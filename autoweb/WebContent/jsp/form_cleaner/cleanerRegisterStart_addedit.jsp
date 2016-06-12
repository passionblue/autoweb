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
    CleanerRegisterStartDataHolder _CleanerRegisterStart = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerRegisterStart = CleanerRegisterStartDS.getInstance().getById(id);
		if ( _CleanerRegisterStart == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerRegisterStart = new CleanerRegisterStartDataHolder();// CleanerRegisterStartDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _CleanerRegisterStart == null) _CleanerRegisterStart = new CleanerRegisterStartDataHolder();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_register_start_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _site_titleValue= (reqParams.get("siteTitle")==null?WebUtil.display(_CleanerRegisterStart.getSiteTitle()):WebUtil.display((String)reqParams.get("siteTitle")));
    String _site_nameValue= (reqParams.get("siteName")==null?WebUtil.display(_CleanerRegisterStart.getSiteName()):WebUtil.display((String)reqParams.get("siteName")));
    String _usernameValue= (reqParams.get("username")==null?WebUtil.display(_CleanerRegisterStart.getUsername()):WebUtil.display((String)reqParams.get("username")));
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_CleanerRegisterStart.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _passwordValue= (reqParams.get("password")==null?WebUtil.display(_CleanerRegisterStart.getPassword()):WebUtil.display((String)reqParams.get("password")));
    String _password_repeatValue= (reqParams.get("passwordRepeat")==null?WebUtil.display(_CleanerRegisterStart.getPasswordRepeat()):WebUtil.display((String)reqParams.get("passwordRepeat")));
    String _locationValue= (reqParams.get("location")==null?WebUtil.display(_CleanerRegisterStart.getLocation()):WebUtil.display((String)reqParams.get("location")));
    String _created_site_urlValue= (reqParams.get("createdSiteUrl")==null?WebUtil.display(_CleanerRegisterStart.getCreatedSiteUrl()):WebUtil.display((String)reqParams.get("createdSiteUrl")));

    String pagestamp = "cleaner_register_start_" + System.nanoTime();
%> 

<br>
<div id="cleanerRegisterStartForm" class="formFrame">
<div id="cleanerRegisterStartFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerRegisterStartForm_Form" method="POST" action="/cleanerRegisterStartAction.html" id="cleanerRegisterStartForm_Form">





	<div id="cleanerRegisterStartForm_siteTitle_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_siteTitle_label" class="formLabel" >Site Title </div>
    <div id="cleanerRegisterStartForm_siteTitle_text" class="formFieldText" >       
        <input id="siteTitle" class="field" type="text" size="70" name="siteTitle" value="<%=WebUtil.display(_site_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_siteName_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_siteName_label" class="formRequiredLabel" >Site Name* </div>
    <div id="cleanerRegisterStartForm_siteName_text" class="formFieldText" >       
        <input id="siteName" class="requiredField" type="text" size="70" name="siteName" value="<%=WebUtil.display(_site_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_username_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_username_label" class="formLabel" >Username </div>
    <div id="cleanerRegisterStartForm_username_text" class="formFieldText" >       
        <input id="username" class="field" type="text" size="70" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_email_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_email_label" class="formRequiredLabel" >Email* </div>
    <div id="cleanerRegisterStartForm_email_text" class="formFieldText" >       
        <input id="email" class="requiredField" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_password_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_password_label" class="formRequiredLabel" >Password* </div>
    <div id="cleanerRegisterStartForm_password_text" class="formFieldText" >       
        <input id="password" class="requiredField" type="text" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_passwordRepeat_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_passwordRepeat_label" class="formRequiredLabel" >Password Repeat* </div>
    <div id="cleanerRegisterStartForm_passwordRepeat_text" class="formFieldText" >       
        <input id="passwordRepeat" class="requiredField" type="text" size="70" name="passwordRepeat" value="<%=WebUtil.display(_password_repeatValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_location_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_location_label" class="formLabel" >Location </div>
    <div id="cleanerRegisterStartForm_location_text" class="formFieldText" >       
        <input id="location" class="field" type="text" size="70" name="location" value="<%=WebUtil.display(_locationValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_createdSiteUrl_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_createdSiteUrl_label" class="formLabel" >Created Site Url </div>
    <div id="cleanerRegisterStartForm_createdSiteUrl_text" class="formFieldText" >       
        <input id="createdSiteUrl" class="field" type="text" size="70" name="createdSiteUrl" value="<%=WebUtil.display(_created_site_urlValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="cleanerRegisterStartForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerRegisterStartForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerRegisterStartForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerRegisterStartForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerRegisterStartForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerRegisterStartForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerRegisterStartForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerRegisterStart.getId()%>">

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
<a href="/v_cleaner_register_start_home.html">home</a> | <a href="/v_cleaner_register_start_home.html">home</a> | <a href="/v_cleaner_register_start_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerRegisterStart = new ArrayList();
	CleanerRegisterStartDS ds_CleanerRegisterStart = CleanerRegisterStartDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerRegisterStart = ds_CleanerRegisterStart.getAll();
	else		
    	list_CleanerRegisterStart = ds_CleanerRegisterStart.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerRegisterStart, numDisplayInPage, listPage);

	list_CleanerRegisterStart = PagingUtil.getPagedList(pagingInfo, list_CleanerRegisterStart);
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

    <td class="columnTitle">  Site Title </td> 
    <td class="columnTitle">  Site Name </td> 
    <td class="columnTitle">  Username </td> 
    <td class="columnTitle">  Email </td> 
    <td class="columnTitle">  Password </td> 
    <td class="columnTitle">  Password Repeat </td> 
    <td class="columnTitle">  Location </td> 
    <td class="columnTitle">  Created Site Url </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerRegisterStart.iterator();iter.hasNext();) {
        CleanerRegisterStartDataHolder o_CleanerRegisterStart = (CleanerRegisterStartDataHolder) iter.next();
%>

<TR id="tableRow<%= o_CleanerRegisterStart.getId()%>">
    <td> <%= o_CleanerRegisterStart.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerRegisterStart.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerRegisterStart.getSiteTitle()  %> </td>
	<td> <%= o_CleanerRegisterStart.getSiteName()  %> </td>
	<td> <%= o_CleanerRegisterStart.getUsername()  %> </td>
	<td> <%= o_CleanerRegisterStart.getEmail()  %> </td>
	<td> <%= o_CleanerRegisterStart.getPassword()  %> </td>
	<td> <%= o_CleanerRegisterStart.getPasswordRepeat()  %> </td>
	<td> <%= o_CleanerRegisterStart.getLocation()  %> </td>
	<td> <%= o_CleanerRegisterStart.getCreatedSiteUrl()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_register_start_form('<%=o_CleanerRegisterStart.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerRegisterStartAction.html?del=true&id=<%=o_CleanerRegisterStart.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerRegisterStart.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_register_start_form('<%=o_CleanerRegisterStart.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_register_start_form(target){
		location.href='/v_cleaner_register_start_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_register_start_form(target){
		javascript:sendFormAjaxSimple('/cleanerRegisterStartAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerRegisterStartAction.html?ajxr=hb",
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
