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
    CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerPickupDeliveryRecur = CleanerPickupDeliveryRecurDS.getInstance().getById(id);
		if ( _CleanerPickupDeliveryRecur == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerPickupDeliveryRecur = new CleanerPickupDeliveryRecur();// CleanerPickupDeliveryRecurDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_pickup_delivery_recur_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _customer_idValue= (reqParams.get("customerId")==null?WebUtil.display(_CleanerPickupDeliveryRecur.getCustomerId()):WebUtil.display((String)reqParams.get("customerId")));
    String _weekdayValue= (reqParams.get("weekday")==null?WebUtil.display(_CleanerPickupDeliveryRecur.getWeekday()):WebUtil.display((String)reqParams.get("weekday")));
    String _time_hhddValue= (reqParams.get("timeHhdd")==null?WebUtil.display(_CleanerPickupDeliveryRecur.getTimeHhdd()):WebUtil.display((String)reqParams.get("timeHhdd")));

    String pagestamp = "cleaner_pickup_delivery_recur_" + System.nanoTime();
%> 

<br>
<div id="cleanerPickupDeliveryRecurForm" class="formFrame">
<div id="cleanerPickupDeliveryRecurFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerPickupDeliveryRecurForm_Form" method="POST" action="/cleanerPickupDeliveryRecurAction.html" id="cleanerPickupDeliveryRecurForm_Form">





	<div id="cleanerPickupDeliveryRecurForm_customerId_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryRecurForm_customerId_label" class="formLabel" >Customer Id </div>
    <div id="cleanerPickupDeliveryRecurForm_customerId_text" class="formFieldText" >       
        <input id="customerId" class="field" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryRecurForm_weekday_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryRecurForm_weekday_label" class="formLabel" >Weekday </div>
    <div id="cleanerPickupDeliveryRecurForm_weekday_text" class="formFieldText" >       
        <input id="weekday" class="field" type="text" size="70" name="weekday" value="<%=WebUtil.display(_weekdayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryRecurForm_timeHhdd_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryRecurForm_timeHhdd_label" class="formLabel" >Time Hhdd </div>
    <div id="cleanerPickupDeliveryRecurForm_timeHhdd_text" class="formFieldText" >       
        <input id="timeHhdd" class="field" type="text" size="70" name="timeHhdd" value="<%=WebUtil.display(_time_hhddValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="cleanerPickupDeliveryRecurForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerPickupDeliveryRecurForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerPickupDeliveryRecurForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerPickupDeliveryRecurForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerPickupDeliveryRecurForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerPickupDeliveryRecurForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerPickupDeliveryRecurForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDeliveryRecur.getId()%>">

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
<a href="/v_cleaner_pickup_delivery_recur_home.html">home</a> | <a href="/v_cleaner_pickup_delivery_recur_home.html">home</a> | <a href="/v_cleaner_pickup_delivery_recur_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerPickupDeliveryRecur = new ArrayList();
	CleanerPickupDeliveryRecurDS ds_CleanerPickupDeliveryRecur = CleanerPickupDeliveryRecurDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerPickupDeliveryRecur = ds_CleanerPickupDeliveryRecur.getAll();
	else		
    	list_CleanerPickupDeliveryRecur = ds_CleanerPickupDeliveryRecur.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerPickupDeliveryRecur, numDisplayInPage, listPage);

	list_CleanerPickupDeliveryRecur = PagingUtil.getPagedList(pagingInfo, list_CleanerPickupDeliveryRecur);
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
    <td class="columnTitle">  Weekday </td> 
    <td class="columnTitle">  Time Hhdd </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerPickupDeliveryRecur.iterator();iter.hasNext();) {
        CleanerPickupDeliveryRecur o_CleanerPickupDeliveryRecur = (CleanerPickupDeliveryRecur) iter.next();
%>

<TR id="tableRow<%= o_CleanerPickupDeliveryRecur.getId()%>">
    <td> <%= o_CleanerPickupDeliveryRecur.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerPickupDeliveryRecur.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerPickupDeliveryRecur.getCustomerId()  %> </td>
	<td> <%= o_CleanerPickupDeliveryRecur.getWeekday()  %> </td>
	<td> <%= o_CleanerPickupDeliveryRecur.getTimeHhdd()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_pickup_delivery_recur_form('<%=o_CleanerPickupDeliveryRecur.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerPickupDeliveryRecurAction.html?del=true&id=<%=o_CleanerPickupDeliveryRecur.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerPickupDeliveryRecur.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_pickup_delivery_recur_form('<%=o_CleanerPickupDeliveryRecur.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_pickup_delivery_recur_form(target){
		location.href='/v_cleaner_pickup_delivery_recur_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_pickup_delivery_recur_form(target){
		javascript:sendFormAjaxSimple('/cleanerPickupDeliveryRecurAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerPickupDeliveryRecurAction.html?ajxr=hb",
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
