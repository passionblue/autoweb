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
    CleanerCustomerNotification _CleanerCustomerNotification = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerCustomerNotification = CleanerCustomerNotificationDS.getInstance().getById(id);
		if ( _CleanerCustomerNotification == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerCustomerNotification = new CleanerCustomerNotification();// CleanerCustomerNotificationDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _CleanerCustomerNotification == null) _CleanerCustomerNotification = new CleanerCustomerNotification();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_customer_notification_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _customer_idValue= (reqParams.get("customerId")==null?WebUtil.display(_CleanerCustomerNotification.getCustomerId()):WebUtil.display((String)reqParams.get("customerId")));
    String _reasonfor_idValue= (reqParams.get("reasonforId")==null?WebUtil.display(_CleanerCustomerNotification.getReasonforId()):WebUtil.display((String)reqParams.get("reasonforId")));
    String _reasonfor_targetValue= (reqParams.get("reasonforTarget")==null?WebUtil.display(_CleanerCustomerNotification.getReasonforTarget()):WebUtil.display((String)reqParams.get("reasonforTarget")));
    String _notification_typeValue= (reqParams.get("notificationType")==null?WebUtil.display(_CleanerCustomerNotification.getNotificationType()):WebUtil.display((String)reqParams.get("notificationType")));
    String _source_typeValue= (reqParams.get("sourceType")==null?WebUtil.display(_CleanerCustomerNotification.getSourceType()):WebUtil.display((String)reqParams.get("sourceType")));
    String _trigger_typeValue= (reqParams.get("triggerType")==null?WebUtil.display(_CleanerCustomerNotification.getTriggerType()):WebUtil.display((String)reqParams.get("triggerType")));
    String _is_retransmitValue= (reqParams.get("isRetransmit")==null?WebUtil.display(_CleanerCustomerNotification.getIsRetransmit()):WebUtil.display((String)reqParams.get("isRetransmit")));
    String _method_typeValue= (reqParams.get("methodType")==null?WebUtil.display(_CleanerCustomerNotification.getMethodType()):WebUtil.display((String)reqParams.get("methodType")));
    String _template_typeValue= (reqParams.get("templateType")==null?WebUtil.display(_CleanerCustomerNotification.getTemplateType()):WebUtil.display((String)reqParams.get("templateType")));
    String _contentValue= (reqParams.get("content")==null?WebUtil.display(_CleanerCustomerNotification.getContent()):WebUtil.display((String)reqParams.get("content")));
    String _destinationValue= (reqParams.get("destination")==null?WebUtil.display(_CleanerCustomerNotification.getDestination()):WebUtil.display((String)reqParams.get("destination")));
    String _referenceValue= (reqParams.get("reference")==null?WebUtil.display(_CleanerCustomerNotification.getReference()):WebUtil.display((String)reqParams.get("reference")));
    String _time_scheduledValue= (reqParams.get("timeScheduled")==null?WebUtil.display(_CleanerCustomerNotification.getTimeScheduled()):WebUtil.display((String)reqParams.get("timeScheduled")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_CleanerCustomerNotification.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_sentValue= (reqParams.get("timeSent")==null?WebUtil.display(_CleanerCustomerNotification.getTimeSent()):WebUtil.display((String)reqParams.get("timeSent")));

    String pagestamp = "cleaner_customer_notification_" + System.nanoTime();
%> 

<br>
<div id="cleanerCustomerNotificationForm" class="formFrame">
<div id="cleanerCustomerNotificationFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerCustomerNotificationForm_Form" method="POST" action="/cleanerCustomerNotificationAction.html" id="cleanerCustomerNotificationForm_Form">





	<div id="cleanerCustomerNotificationForm_customerId_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_customerId_label" class="formLabel" >Customer Id </div>
    <div id="cleanerCustomerNotificationForm_customerId_text" class="formFieldText" >       
        <input id="customerId" class="field" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_reasonforId_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_reasonforId_label" class="formLabel" >Reasonfor Id </div>
    <div id="cleanerCustomerNotificationForm_reasonforId_text" class="formFieldText" >       
        <input id="reasonforId" class="field" type="text" size="70" name="reasonforId" value="<%=WebUtil.display(_reasonfor_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_reasonforTarget_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_reasonforTarget_label" class="formLabel" >Reasonfor Target </div>
    <div id="cleanerCustomerNotificationForm_reasonforTarget_text" class="formFieldText" >       
        <input id="reasonforTarget" class="field" type="text" size="70" name="reasonforTarget" value="<%=WebUtil.display(_reasonfor_targetValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_notificationType_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_notificationType_label" class="formLabel" >Notification Type </div>
    <div id="cleanerCustomerNotificationForm_notificationType_text" class="formFieldText" >       
        <input id="notificationType" class="field" type="text" size="70" name="notificationType" value="<%=WebUtil.display(_notification_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_sourceType_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_sourceType_label" class="formLabel" >Source Type </div>
    <div id="cleanerCustomerNotificationForm_sourceType_text" class="formFieldText" >       
        <input id="sourceType" class="field" type="text" size="70" name="sourceType" value="<%=WebUtil.display(_source_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_triggerType_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_triggerType_label" class="formLabel" >Trigger Type </div>
    <div id="cleanerCustomerNotificationForm_triggerType_text" class="formFieldText" >       
        <input id="triggerType" class="field" type="text" size="70" name="triggerType" value="<%=WebUtil.display(_trigger_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_isRetransmit_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_isRetransmit_label" class="formLabel" >Is Retransmit </div>
    <div id="cleanerCustomerNotificationForm_isRetransmit_text" class="formFieldText" >       
        <input id="isRetransmit" class="field" type="text" size="70" name="isRetransmit" value="<%=WebUtil.display(_is_retransmitValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_methodType_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_methodType_label" class="formLabel" >Method Type </div>
    <div id="cleanerCustomerNotificationForm_methodType_text" class="formFieldText" >       
        <input id="methodType" class="field" type="text" size="70" name="methodType" value="<%=WebUtil.display(_method_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_templateType_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_templateType_label" class="formLabel" >Template Type </div>
    <div id="cleanerCustomerNotificationForm_templateType_text" class="formFieldText" >       
        <input id="templateType" class="field" type="text" size="70" name="templateType" value="<%=WebUtil.display(_template_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_content_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_content_label" class="formLabel" >Content </div>
    <div id="cleanerCustomerNotificationForm_content_text" class="formFieldText" >       
        <input id="content" class="field" type="text" size="70" name="content" value="<%=WebUtil.display(_contentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_destination_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_destination_label" class="formLabel" >Destination </div>
    <div id="cleanerCustomerNotificationForm_destination_text" class="formFieldText" >       
        <input id="destination" class="field" type="text" size="70" name="destination" value="<%=WebUtil.display(_destinationValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_reference_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_reference_label" class="formLabel" >Reference </div>
    <div id="cleanerCustomerNotificationForm_reference_text" class="formFieldText" >       
        <input id="reference" class="field" type="text" size="70" name="reference" value="<%=WebUtil.display(_referenceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_timeScheduled_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_timeScheduled_label" class="formLabel" >Time Scheduled </div>
    <div id="cleanerCustomerNotificationForm_timeScheduled_text" class="formFieldText" >       
        <input id="timeScheduled" class="field" type="text" size="70" name="timeScheduled" value="<%=WebUtil.display(_time_scheduledValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div id="cleanerCustomerNotificationForm_timeSent_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_timeSent_label" class="formLabel" >Time Sent </div>
    <div id="cleanerCustomerNotificationForm_timeSent_text" class="formFieldText" >       
        <input id="timeSent" class="field" type="text" size="70" name="timeSent" value="<%=WebUtil.display(_time_sentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="cleanerCustomerNotificationForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerCustomerNotificationForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerCustomerNotificationForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerCustomerNotificationForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerCustomerNotificationForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerCustomerNotificationForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerCustomerNotificationForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    

    <div id="cleanerCustomerNotificationForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();"><%= WebUtil.capitalize("back") %></a>
    </div>      
    <div id="cleanerCustomerNotificationForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_ext_<%=pagestamp%>();"><%= WebUtil.capitalize("ext") %></a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">

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
<a href="/v_cleaner_customer_notification_home.html">home</a> | <a href="/v_cleaner_customer_notification_home.html">home</a> | <a href="/v_cleaner_customer_notification_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerCustomerNotification = new ArrayList();
	CleanerCustomerNotificationDS ds_CleanerCustomerNotification = CleanerCustomerNotificationDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerCustomerNotification = ds_CleanerCustomerNotification.getAll();
	else		
    	list_CleanerCustomerNotification = ds_CleanerCustomerNotification.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerCustomerNotification, numDisplayInPage, listPage);

	list_CleanerCustomerNotification = PagingUtil.getPagedList(pagingInfo, list_CleanerCustomerNotification);
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

    <td class="columnTitle">  Customer Id </td> 
    <td class="columnTitle">  Reasonfor Id </td> 
    <td class="columnTitle">  Reasonfor Target </td> 
    <td class="columnTitle">  Notification Type </td> 
    <td class="columnTitle">  Source Type </td> 
    <td class="columnTitle">  Trigger Type </td> 
    <td class="columnTitle">  Is Retransmit </td> 
    <td class="columnTitle">  Method Type </td> 
    <td class="columnTitle">  Template Type </td> 
    <td class="columnTitle">  Content </td> 
    <td class="columnTitle">  Destination </td> 
    <td class="columnTitle">  Reference </td> 
    <td class="columnTitle">  Time Scheduled </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Sent </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerCustomerNotification.iterator();iter.hasNext();) {
        CleanerCustomerNotification o_CleanerCustomerNotification = (CleanerCustomerNotification) iter.next();
%>

<TR id="tableRow<%= o_CleanerCustomerNotification.getId()%>">
    <td> <%= o_CleanerCustomerNotification.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerCustomerNotification.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerCustomerNotification.getCustomerId()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getReasonforId()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getReasonforTarget()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getNotificationType()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getSourceType()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getTriggerType()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getIsRetransmit()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getMethodType()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getTemplateType()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getContent()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getDestination()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getReference()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getTimeScheduled()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getTimeCreated()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getTimeSent()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_customer_notification_form('<%=o_CleanerCustomerNotification.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerCustomerNotificationAction.html?del=true&id=<%=o_CleanerCustomerNotification.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerCustomerNotification.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_customer_notification_form('<%=o_CleanerCustomerNotification.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_customer_notification_form(target){
		location.href='/v_cleaner_customer_notification_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_customer_notification_form(target){
		javascript:sendFormAjaxSimple('/cleanerCustomerNotificationAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerCustomerNotificationAction.html?ajxr=hb",
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
