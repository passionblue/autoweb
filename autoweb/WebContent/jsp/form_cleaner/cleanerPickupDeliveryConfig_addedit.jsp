<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
/* 
Template last modification history:


Source Generated: Sun Mar 15 13:54:46 EDT 2015
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
    CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerPickupDeliveryConfig = CleanerPickupDeliveryConfigDS.getInstance().getById(id);
		if ( _CleanerPickupDeliveryConfig == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerPickupDeliveryConfig = new CleanerPickupDeliveryConfig();// CleanerPickupDeliveryConfigDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _CleanerPickupDeliveryConfig == null) _CleanerPickupDeliveryConfig = new CleanerPickupDeliveryConfig();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_pickup_delivery_config_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _location_idValue= (reqParams.get("locationId")==null?WebUtil.display(_CleanerPickupDeliveryConfig.getLocationId()):WebUtil.display((String)reqParams.get("locationId")));
    String _apply_all_locationsValue= (reqParams.get("applyAllLocations")==null?WebUtil.display(_CleanerPickupDeliveryConfig.getApplyAllLocations()):WebUtil.display((String)reqParams.get("applyAllLocations")));
    String _disable_web_requestValue= (reqParams.get("disableWebRequest")==null?WebUtil.display(_CleanerPickupDeliveryConfig.getDisableWebRequest()):WebUtil.display((String)reqParams.get("disableWebRequest")));
    String _disallow_anonymous_requestValue= (reqParams.get("disallowAnonymousRequest")==null?WebUtil.display(_CleanerPickupDeliveryConfig.getDisallowAnonymousRequest()):WebUtil.display((String)reqParams.get("disallowAnonymousRequest")));
    String _require_customer_registerValue= (reqParams.get("requireCustomerRegister")==null?WebUtil.display(_CleanerPickupDeliveryConfig.getRequireCustomerRegister()):WebUtil.display((String)reqParams.get("requireCustomerRegister")));
    String _require_customer_loginValue= (reqParams.get("requireCustomerLogin")==null?WebUtil.display(_CleanerPickupDeliveryConfig.getRequireCustomerLogin()):WebUtil.display((String)reqParams.get("requireCustomerLogin")));

    String pagestamp = "cleaner_pickup_delivery_config_" + System.nanoTime();
%> 

<br>
<div id="cleanerPickupDeliveryConfigForm" class="formFrame">
<div id="cleanerPickupDeliveryConfigFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerPickupDeliveryConfigForm_Form" method="POST" action="/cleanerPickupDeliveryConfigAction.html" id="cleanerPickupDeliveryConfigForm_Form">





	<div id="cleanerPickupDeliveryConfigForm_locationId_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryConfigForm_locationId_label" class="formLabel" >Location Id </div>
    <div id="cleanerPickupDeliveryConfigForm_locationId_text" class="formFieldText" >       
        <input id="locationId" class="field" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="cleanerPickupDeliveryConfigForm_applyAllLocations_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryConfigForm_applyAllLocations_label" class="formLabel" >Apply All Locations </div>
    <div id="cleanerPickupDeliveryConfigForm_applyAllLocations_dropdown" class="formFieldDropDown" >       
        <select name="applyAllLocations">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _apply_all_locationsValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _apply_all_locationsValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryConfigForm_disableWebRequest_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryConfigForm_disableWebRequest_label" class="formLabel" >Disable Web Request </div>
    <div id="cleanerPickupDeliveryConfigForm_disableWebRequest_dropdown" class="formFieldDropDown" >       
        <select name="disableWebRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disable_web_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disable_web_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryConfigForm_disallowAnonymousRequest_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryConfigForm_disallowAnonymousRequest_label" class="formLabel" >Disallow Anonymous Request </div>
    <div id="cleanerPickupDeliveryConfigForm_disallowAnonymousRequest_dropdown" class="formFieldDropDown" >       
        <select name="disallowAnonymousRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disallow_anonymous_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disallow_anonymous_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryConfigForm_requireCustomerRegister_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryConfigForm_requireCustomerRegister_label" class="formLabel" >Require Customer Register </div>
    <div id="cleanerPickupDeliveryConfigForm_requireCustomerRegister_dropdown" class="formFieldDropDown" >       
        <select name="requireCustomerRegister">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _require_customer_registerValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _require_customer_registerValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryConfigForm_requireCustomerLogin_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryConfigForm_requireCustomerLogin_label" class="formLabel" >Require Customer Login </div>
    <div id="cleanerPickupDeliveryConfigForm_requireCustomerLogin_dropdown" class="formFieldDropDown" >       
        <select name="requireCustomerLogin">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _require_customer_loginValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _require_customer_loginValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>


<div class="submitFrame">

    <div id="cleanerPickupDeliveryConfigForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerPickupDeliveryConfigForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerPickupDeliveryConfigForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerPickupDeliveryConfigForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerPickupDeliveryConfigForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerPickupDeliveryConfigForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerPickupDeliveryConfigForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDeliveryConfig.getId()%>">

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
<a href="/v_cleaner_pickup_delivery_config_home.html">home</a> | <a href="/v_cleaner_pickup_delivery_config_home.html">home</a> | <a href="/v_cleaner_pickup_delivery_config_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerPickupDeliveryConfig = new ArrayList();
	CleanerPickupDeliveryConfigDS ds_CleanerPickupDeliveryConfig = CleanerPickupDeliveryConfigDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerPickupDeliveryConfig = ds_CleanerPickupDeliveryConfig.getAll();
	else		
    	list_CleanerPickupDeliveryConfig = ds_CleanerPickupDeliveryConfig.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerPickupDeliveryConfig, numDisplayInPage, listPage);

	list_CleanerPickupDeliveryConfig = PagingUtil.getPagedList(pagingInfo, list_CleanerPickupDeliveryConfig);
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

    <td class="columnTitle">  Location Id </td> 
    <td class="columnTitle">  Apply All Locations </td> 
    <td class="columnTitle">  Disable Web Request </td> 
    <td class="columnTitle">  Disallow Anonymous Request </td> 
    <td class="columnTitle">  Require Customer Register </td> 
    <td class="columnTitle">  Require Customer Login </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerPickupDeliveryConfig.iterator();iter.hasNext();) {
        CleanerPickupDeliveryConfig o_CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig) iter.next();
%>

<TR id="tableRow<%= o_CleanerPickupDeliveryConfig.getId()%>">
    <td> <%= o_CleanerPickupDeliveryConfig.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerPickupDeliveryConfig.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerPickupDeliveryConfig.getLocationId()  %> </td>
	<td> <%= o_CleanerPickupDeliveryConfig.getApplyAllLocations()  %> </td>
	<td> <%= o_CleanerPickupDeliveryConfig.getDisableWebRequest()  %> </td>
	<td> <%= o_CleanerPickupDeliveryConfig.getDisallowAnonymousRequest()  %> </td>
	<td> <%= o_CleanerPickupDeliveryConfig.getRequireCustomerRegister()  %> </td>
	<td> <%= o_CleanerPickupDeliveryConfig.getRequireCustomerLogin()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_pickup_delivery_config_form('<%=o_CleanerPickupDeliveryConfig.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerPickupDeliveryConfigAction.html?del=true&id=<%=o_CleanerPickupDeliveryConfig.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerPickupDeliveryConfig.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_pickup_delivery_config_form('<%=o_CleanerPickupDeliveryConfig.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_pickup_delivery_config_form(target){
		location.href='/v_cleaner_pickup_delivery_config_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_pickup_delivery_config_form(target){
		javascript:sendFormAjaxSimple('/cleanerPickupDeliveryConfigAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerPickupDeliveryConfigAction.html?ajxr=hb",
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
