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
    CleanerTicket _CleanerTicket = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerTicket = CleanerTicketDS.getInstance().getById(id);
		if ( _CleanerTicket == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerTicket = new CleanerTicket();// CleanerTicketDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _CleanerTicket == null) _CleanerTicket = new CleanerTicket();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_ticket_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _serialValue= (reqParams.get("serial")==null?WebUtil.display(_CleanerTicket.getSerial()):WebUtil.display((String)reqParams.get("serial")));
    String _parent_ticket_idValue= (reqParams.get("parentTicketId")==null?WebUtil.display(_CleanerTicket.getParentTicketId()):WebUtil.display((String)reqParams.get("parentTicketId")));
    String _customer_idValue= (reqParams.get("customerId")==null?WebUtil.display(_CleanerTicket.getCustomerId()):WebUtil.display((String)reqParams.get("customerId")));
    String _enter_user_idValue= (reqParams.get("enterUserId")==null?WebUtil.display(_CleanerTicket.getEnterUserId()):WebUtil.display((String)reqParams.get("enterUserId")));
    String _location_idValue= (reqParams.get("locationId")==null?WebUtil.display(_CleanerTicket.getLocationId()):WebUtil.display((String)reqParams.get("locationId")));
    String _noteValue= (reqParams.get("note")==null?WebUtil.display(_CleanerTicket.getNote()):WebUtil.display((String)reqParams.get("note")));
    String _completedValue= (reqParams.get("completed")==null?WebUtil.display(_CleanerTicket.getCompleted()):WebUtil.display((String)reqParams.get("completed")));
    String _onholdValue= (reqParams.get("onhold")==null?WebUtil.display(_CleanerTicket.getOnhold()):WebUtil.display((String)reqParams.get("onhold")));
    String _original_ticket_idValue= (reqParams.get("originalTicketId")==null?WebUtil.display(_CleanerTicket.getOriginalTicketId()):WebUtil.display((String)reqParams.get("originalTicketId")));
    String _returnedValue= (reqParams.get("returned")==null?WebUtil.display(_CleanerTicket.getReturned()):WebUtil.display((String)reqParams.get("returned")));
    String _returned_reason_textValue= (reqParams.get("returnedReasonText")==null?WebUtil.display(_CleanerTicket.getReturnedReasonText()):WebUtil.display((String)reqParams.get("returnedReasonText")));
    String _returned_noteValue= (reqParams.get("returnedNote")==null?WebUtil.display(_CleanerTicket.getReturnedNote()):WebUtil.display((String)reqParams.get("returnedNote")));
    String _total_chargeValue= (reqParams.get("totalCharge")==null?WebUtil.display(_CleanerTicket.getTotalCharge()):WebUtil.display((String)reqParams.get("totalCharge")));
    String _final_chargeValue= (reqParams.get("finalCharge")==null?WebUtil.display(_CleanerTicket.getFinalCharge()):WebUtil.display((String)reqParams.get("finalCharge")));
    String _discount_idValue= (reqParams.get("discountId")==null?WebUtil.display(_CleanerTicket.getDiscountId()):WebUtil.display((String)reqParams.get("discountId")));
    String _discount_amountValue= (reqParams.get("discountAmount")==null?WebUtil.display(_CleanerTicket.getDiscountAmount()):WebUtil.display((String)reqParams.get("discountAmount")));
    String _discount_noteValue= (reqParams.get("discountNote")==null?WebUtil.display(_CleanerTicket.getDiscountNote()):WebUtil.display((String)reqParams.get("discountNote")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_CleanerTicket.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_CleanerTicket.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
    String _time_completedValue= (reqParams.get("timeCompleted")==null?WebUtil.display(_CleanerTicket.getTimeCompleted()):WebUtil.display((String)reqParams.get("timeCompleted")));
    String _time_onholdValue= (reqParams.get("timeOnhold")==null?WebUtil.display(_CleanerTicket.getTimeOnhold()):WebUtil.display((String)reqParams.get("timeOnhold")));

    String pagestamp = "cleaner_ticket_" + System.nanoTime();
%> 

<br>
<div id="cleanerTicketForm" class="formFrame">
<div id="cleanerTicketFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerTicketForm_Form" method="POST" action="/cleanerTicketAction.html" id="cleanerTicketForm_Form">





	<div id="cleanerTicketForm_serial_field" class="formFieldFrame">
    <div id="cleanerTicketForm_serial_label" class="formLabel" >Serial </div>
    <div id="cleanerTicketForm_serial_text" class="formFieldText" >       
        <input id="serial" class="field" type="text" size="70" name="serial" value="<%=WebUtil.display(_serialValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_parentTicketId_field" class="formFieldFrame">
    <div id="cleanerTicketForm_parentTicketId_label" class="formLabel" >Parent Ticket Id </div>
    <div id="cleanerTicketForm_parentTicketId_text" class="formFieldText" >       
        <input id="parentTicketId" class="field" type="text" size="70" name="parentTicketId" value="<%=WebUtil.display(_parent_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_customerId_field" class="formFieldFrame">
    <div id="cleanerTicketForm_customerId_label" class="formLabel" >Customer Id </div>
    <div id="cleanerTicketForm_customerId_text" class="formFieldText" >       
        <input id="customerId" class="field" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_enterUserId_field" class="formFieldFrame">
    <div id="cleanerTicketForm_enterUserId_label" class="formLabel" >Enter User Id </div>
    <div id="cleanerTicketForm_enterUserId_text" class="formFieldText" >       
        <input id="enterUserId" class="field" type="text" size="70" name="enterUserId" value="<%=WebUtil.display(_enter_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_locationId_field" class="formFieldFrame">
    <div id="cleanerTicketForm_locationId_label" class="formLabel" >Location Id </div>
    <div id="cleanerTicketForm_locationId_text" class="formFieldText" >       
        <input id="locationId" class="field" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_note_field" class="formFieldFrame">
    <div id="cleanerTicketForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerTicketForm_note_text" class="formFieldText" >       
        <input id="note" class="field" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_completed_field" class="formFieldFrame">
    <div id="cleanerTicketForm_completed_label" class="formLabel" >Completed </div>
    <div id="cleanerTicketForm_completed_text" class="formFieldText" >       
        <input id="completed" class="field" type="text" size="70" name="completed" value="<%=WebUtil.display(_completedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_onhold_field" class="formFieldFrame">
    <div id="cleanerTicketForm_onhold_label" class="formLabel" >Onhold </div>
    <div id="cleanerTicketForm_onhold_text" class="formFieldText" >       
        <input id="onhold" class="field" type="text" size="70" name="onhold" value="<%=WebUtil.display(_onholdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_originalTicketId_field" class="formFieldFrame">
    <div id="cleanerTicketForm_originalTicketId_label" class="formLabel" >Original Ticket Id </div>
    <div id="cleanerTicketForm_originalTicketId_text" class="formFieldText" >       
        <input id="originalTicketId" class="field" type="text" size="70" name="originalTicketId" value="<%=WebUtil.display(_original_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_returned_field" class="formFieldFrame">
    <div id="cleanerTicketForm_returned_label" class="formLabel" >Returned </div>
    <div id="cleanerTicketForm_returned_text" class="formFieldText" >       
        <input id="returned" class="field" type="text" size="70" name="returned" value="<%=WebUtil.display(_returnedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_returnedReasonText_field" class="formFieldFrame">
    <div id="cleanerTicketForm_returnedReasonText_label" class="formLabel" >Returned Reason Text </div>
    <div id="cleanerTicketForm_returnedReasonText_text" class="formFieldText" >       
        <input id="returnedReasonText" class="field" type="text" size="70" name="returnedReasonText" value="<%=WebUtil.display(_returned_reason_textValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_returnedNote_field" class="formFieldFrame">
    <div id="cleanerTicketForm_returnedNote_label" class="formLabel" >Returned Note </div>
    <div id="cleanerTicketForm_returnedNote_text" class="formFieldText" >       
        <input id="returnedNote" class="field" type="text" size="70" name="returnedNote" value="<%=WebUtil.display(_returned_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_totalCharge_field" class="formFieldFrame">
    <div id="cleanerTicketForm_totalCharge_label" class="formLabel" >Total Charge </div>
    <div id="cleanerTicketForm_totalCharge_text" class="formFieldText" >       
        <input id="totalCharge" class="field" type="text" size="70" name="totalCharge" value="<%=WebUtil.display(_total_chargeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_finalCharge_field" class="formFieldFrame">
    <div id="cleanerTicketForm_finalCharge_label" class="formLabel" >Final Charge </div>
    <div id="cleanerTicketForm_finalCharge_text" class="formFieldText" >       
        <input id="finalCharge" class="field" type="text" size="70" name="finalCharge" value="<%=WebUtil.display(_final_chargeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_discountId_field" class="formFieldFrame">
    <div id="cleanerTicketForm_discountId_label" class="formLabel" >Discount Id </div>
    <div id="cleanerTicketForm_discountId_text" class="formFieldText" >       
        <input id="discountId" class="field" type="text" size="70" name="discountId" value="<%=WebUtil.display(_discount_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_discountAmount_field" class="formFieldFrame">
    <div id="cleanerTicketForm_discountAmount_label" class="formLabel" >Discount Amount </div>
    <div id="cleanerTicketForm_discountAmount_text" class="formFieldText" >       
        <input id="discountAmount" class="field" type="text" size="70" name="discountAmount" value="<%=WebUtil.display(_discount_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_discountNote_field" class="formFieldFrame">
    <div id="cleanerTicketForm_discountNote_label" class="formLabel" >Discount Note </div>
    <div id="cleanerTicketForm_discountNote_text" class="formFieldText" >       
        <input id="discountNote" class="field" type="text" size="70" name="discountNote" value="<%=WebUtil.display(_discount_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>










	<div id="cleanerTicketForm_timeCompleted_field" class="formFieldFrame">
    <div id="cleanerTicketForm_timeCompleted_label" class="formLabel" >Time Completed </div>
    <div id="cleanerTicketForm_timeCompleted_text" class="formFieldText" >       
        <input id="timeCompleted" class="field" type="text" size="70" name="timeCompleted" value="<%=WebUtil.display(_time_completedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_timeOnhold_field" class="formFieldFrame">
    <div id="cleanerTicketForm_timeOnhold_label" class="formLabel" >Time Onhold </div>
    <div id="cleanerTicketForm_timeOnhold_text" class="formFieldText" >       
        <input id="timeOnhold" class="field" type="text" size="70" name="timeOnhold" value="<%=WebUtil.display(_time_onholdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="cleanerTicketForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerTicketForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerTicketForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerTicketForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerTicketForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerTicketForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerTicketForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">

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
<a href="/v_cleaner_ticket_home.html">home</a> | <a href="/v_cleaner_ticket_home.html">home</a> | <a href="/v_cleaner_ticket_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerTicket = new ArrayList();
	CleanerTicketDS ds_CleanerTicket = CleanerTicketDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerTicket = ds_CleanerTicket.getAll();
	else		
    	list_CleanerTicket = ds_CleanerTicket.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerTicket, numDisplayInPage, listPage);

	list_CleanerTicket = PagingUtil.getPagedList(pagingInfo, list_CleanerTicket);
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
    <td class="columnTitle">  Parent Ticket Id </td> 
    <td class="columnTitle">  Customer Id </td> 
    <td class="columnTitle">  Enter User Id </td> 
    <td class="columnTitle">  Location Id </td> 
    <td class="columnTitle">  Note </td> 
    <td class="columnTitle">  Completed </td> 
    <td class="columnTitle">  Onhold </td> 
    <td class="columnTitle">  Original Ticket Id </td> 
    <td class="columnTitle">  Returned </td> 
    <td class="columnTitle">  Returned Reason Text </td> 
    <td class="columnTitle">  Returned Note </td> 
    <td class="columnTitle">  Total Charge </td> 
    <td class="columnTitle">  Final Charge </td> 
    <td class="columnTitle">  Discount Id </td> 
    <td class="columnTitle">  Discount Amount </td> 
    <td class="columnTitle">  Discount Note </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
    <td class="columnTitle">  Time Completed </td> 
    <td class="columnTitle">  Time Onhold </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerTicket.iterator();iter.hasNext();) {
        CleanerTicket o_CleanerTicket = (CleanerTicket) iter.next();
%>

<TR id="tableRow<%= o_CleanerTicket.getId()%>">
    <td> <%= o_CleanerTicket.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerTicket.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerTicket.getSerial()  %> </td>
	<td> <%= o_CleanerTicket.getParentTicketId()  %> </td>
	<td> <%= o_CleanerTicket.getCustomerId()  %> </td>
	<td> <%= o_CleanerTicket.getEnterUserId()  %> </td>
	<td> <%= o_CleanerTicket.getLocationId()  %> </td>
	<td> <%= o_CleanerTicket.getNote()  %> </td>
	<td> <%= o_CleanerTicket.getCompleted()  %> </td>
	<td> <%= o_CleanerTicket.getOnhold()  %> </td>
	<td> <%= o_CleanerTicket.getOriginalTicketId()  %> </td>
	<td> <%= o_CleanerTicket.getReturned()  %> </td>
	<td> <%= o_CleanerTicket.getReturnedReasonText()  %> </td>
	<td> <%= o_CleanerTicket.getReturnedNote()  %> </td>
	<td> <%= o_CleanerTicket.getTotalCharge()  %> </td>
	<td> <%= o_CleanerTicket.getFinalCharge()  %> </td>
	<td> <%= o_CleanerTicket.getDiscountId()  %> </td>
	<td> <%= o_CleanerTicket.getDiscountAmount()  %> </td>
	<td> <%= o_CleanerTicket.getDiscountNote()  %> </td>
	<td> <%= o_CleanerTicket.getTimeCreated()  %> </td>
	<td> <%= o_CleanerTicket.getTimeUpdated()  %> </td>
	<td> <%= o_CleanerTicket.getTimeCompleted()  %> </td>
	<td> <%= o_CleanerTicket.getTimeOnhold()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_ticket_form('<%=o_CleanerTicket.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerTicketAction.html?del=true&id=<%=o_CleanerTicket.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerTicket.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_ticket_form('<%=o_CleanerTicket.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_ticket_form(target){
		location.href='/v_cleaner_ticket_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_ticket_form(target){
		javascript:sendFormAjaxSimple('/cleanerTicketAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerTicketAction.html?ajxr=hb",
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
