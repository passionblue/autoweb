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
    CleanerTicketItem _CleanerTicketItem = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerTicketItem = CleanerTicketItemDS.getInstance().getById(id);
		if ( _CleanerTicketItem == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerTicketItem = new CleanerTicketItem();// CleanerTicketItemDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _CleanerTicketItem == null) _CleanerTicketItem = new CleanerTicketItem();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_ticket_item_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _ticket_idValue= (reqParams.get("ticketId")==null?WebUtil.display(_CleanerTicketItem.getTicketId()):WebUtil.display((String)reqParams.get("ticketId")));
    String _parent_ticket_idValue= (reqParams.get("parentTicketId")==null?WebUtil.display(_CleanerTicketItem.getParentTicketId()):WebUtil.display((String)reqParams.get("parentTicketId")));
    String _product_idValue= (reqParams.get("productId")==null?WebUtil.display(_CleanerTicketItem.getProductId()):WebUtil.display((String)reqParams.get("productId")));
    String _subtotal_amountValue= (reqParams.get("subtotalAmount")==null?WebUtil.display(_CleanerTicketItem.getSubtotalAmount()):WebUtil.display((String)reqParams.get("subtotalAmount")));
    String _total_amountValue= (reqParams.get("totalAmount")==null?WebUtil.display(_CleanerTicketItem.getTotalAmount()):WebUtil.display((String)reqParams.get("totalAmount")));
    String _discount_idValue= (reqParams.get("discountId")==null?WebUtil.display(_CleanerTicketItem.getDiscountId()):WebUtil.display((String)reqParams.get("discountId")));
    String _total_discount_amountValue= (reqParams.get("totalDiscountAmount")==null?WebUtil.display(_CleanerTicketItem.getTotalDiscountAmount()):WebUtil.display((String)reqParams.get("totalDiscountAmount")));
    String _special_discount_amountValue= (reqParams.get("specialDiscountAmount")==null?WebUtil.display(_CleanerTicketItem.getSpecialDiscountAmount()):WebUtil.display((String)reqParams.get("specialDiscountAmount")));
    String _noteValue= (reqParams.get("note")==null?WebUtil.display(_CleanerTicketItem.getNote()):WebUtil.display((String)reqParams.get("note")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_CleanerTicketItem.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_CleanerTicketItem.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String pagestamp = "cleaner_ticket_item_" + System.nanoTime();
%> 

<br>
<div id="cleanerTicketItemForm" class="formFrame">
<div id="cleanerTicketItemFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerTicketItemForm_Form" method="POST" action="/cleanerTicketItemAction.html" id="cleanerTicketItemForm_Form">





	<div id="cleanerTicketItemForm_ticketId_field" class="formFieldFrame">
    <div id="cleanerTicketItemForm_ticketId_label" class="formLabel" >Ticket Id </div>
    <div id="cleanerTicketItemForm_ticketId_text" class="formFieldText" >       
        <input id="ticketId" class="field" type="text" size="70" name="ticketId" value="<%=WebUtil.display(_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketItemForm_parentTicketId_field" class="formFieldFrame">
    <div id="cleanerTicketItemForm_parentTicketId_label" class="formLabel" >Parent Ticket Id </div>
    <div id="cleanerTicketItemForm_parentTicketId_text" class="formFieldText" >       
        <input id="parentTicketId" class="field" type="text" size="70" name="parentTicketId" value="<%=WebUtil.display(_parent_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketItemForm_productId_field" class="formFieldFrame">
    <div id="cleanerTicketItemForm_productId_label" class="formLabel" >Product Id </div>
    <div id="cleanerTicketItemForm_productId_text" class="formFieldText" >       
        <input id="productId" class="field" type="text" size="70" name="productId" value="<%=WebUtil.display(_product_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketItemForm_subtotalAmount_field" class="formFieldFrame">
    <div id="cleanerTicketItemForm_subtotalAmount_label" class="formLabel" >Subtotal Amount </div>
    <div id="cleanerTicketItemForm_subtotalAmount_text" class="formFieldText" >       
        <input id="subtotalAmount" class="field" type="text" size="70" name="subtotalAmount" value="<%=WebUtil.display(_subtotal_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketItemForm_totalAmount_field" class="formFieldFrame">
    <div id="cleanerTicketItemForm_totalAmount_label" class="formLabel" >Total Amount </div>
    <div id="cleanerTicketItemForm_totalAmount_text" class="formFieldText" >       
        <input id="totalAmount" class="field" type="text" size="70" name="totalAmount" value="<%=WebUtil.display(_total_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketItemForm_discountId_field" class="formFieldFrame">
    <div id="cleanerTicketItemForm_discountId_label" class="formLabel" >Discount Id </div>
    <div id="cleanerTicketItemForm_discountId_text" class="formFieldText" >       
        <input id="discountId" class="field" type="text" size="70" name="discountId" value="<%=WebUtil.display(_discount_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketItemForm_totalDiscountAmount_field" class="formFieldFrame">
    <div id="cleanerTicketItemForm_totalDiscountAmount_label" class="formLabel" >Total Discount Amount </div>
    <div id="cleanerTicketItemForm_totalDiscountAmount_text" class="formFieldText" >       
        <input id="totalDiscountAmount" class="field" type="text" size="70" name="totalDiscountAmount" value="<%=WebUtil.display(_total_discount_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketItemForm_specialDiscountAmount_field" class="formFieldFrame">
    <div id="cleanerTicketItemForm_specialDiscountAmount_label" class="formLabel" >Special Discount Amount </div>
    <div id="cleanerTicketItemForm_specialDiscountAmount_text" class="formFieldText" >       
        <input id="specialDiscountAmount" class="field" type="text" size="70" name="specialDiscountAmount" value="<%=WebUtil.display(_special_discount_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketItemForm_note_field" class="formFieldFrame">
    <div id="cleanerTicketItemForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerTicketItemForm_note_text" class="formFieldText" >       
        <input id="note" class="field" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







<div class="submitFrame">

    <div id="cleanerTicketItemForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerTicketItemForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerTicketItemForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerTicketItemForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerTicketItemForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerTicketItemForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerTicketItemForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicketItem.getId()%>">

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
<a href="/v_cleaner_ticket_item_home.html">home</a> | <a href="/v_cleaner_ticket_item_home.html">home</a> | <a href="/v_cleaner_ticket_item_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerTicketItem = new ArrayList();
	CleanerTicketItemDS ds_CleanerTicketItem = CleanerTicketItemDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerTicketItem = ds_CleanerTicketItem.getAll();
	else		
    	list_CleanerTicketItem = ds_CleanerTicketItem.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerTicketItem, numDisplayInPage, listPage);

	list_CleanerTicketItem = PagingUtil.getPagedList(pagingInfo, list_CleanerTicketItem);
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

    <td class="columnTitle">  Ticket Id </td> 
    <td class="columnTitle">  Parent Ticket Id </td> 
    <td class="columnTitle">  Product Id </td> 
    <td class="columnTitle">  Subtotal Amount </td> 
    <td class="columnTitle">  Total Amount </td> 
    <td class="columnTitle">  Discount Id </td> 
    <td class="columnTitle">  Total Discount Amount </td> 
    <td class="columnTitle">  Special Discount Amount </td> 
    <td class="columnTitle">  Note </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerTicketItem.iterator();iter.hasNext();) {
        CleanerTicketItem o_CleanerTicketItem = (CleanerTicketItem) iter.next();
%>

<TR id="tableRow<%= o_CleanerTicketItem.getId()%>">
    <td> <%= o_CleanerTicketItem.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerTicketItem.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerTicketItem.getTicketId()  %> </td>
	<td> <%= o_CleanerTicketItem.getParentTicketId()  %> </td>
	<td> <%= o_CleanerTicketItem.getProductId()  %> </td>
	<td> <%= o_CleanerTicketItem.getSubtotalAmount()  %> </td>
	<td> <%= o_CleanerTicketItem.getTotalAmount()  %> </td>
	<td> <%= o_CleanerTicketItem.getDiscountId()  %> </td>
	<td> <%= o_CleanerTicketItem.getTotalDiscountAmount()  %> </td>
	<td> <%= o_CleanerTicketItem.getSpecialDiscountAmount()  %> </td>
	<td> <%= o_CleanerTicketItem.getNote()  %> </td>
	<td> <%= o_CleanerTicketItem.getTimeCreated()  %> </td>
	<td> <%= o_CleanerTicketItem.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_ticket_item_form('<%=o_CleanerTicketItem.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerTicketItemAction.html?del=true&id=<%=o_CleanerTicketItem.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerTicketItem.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_ticket_item_form('<%=o_CleanerTicketItem.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_ticket_item_form(target){
		location.href='/v_cleaner_ticket_item_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_ticket_item_form(target){
		javascript:sendFormAjaxSimple('/cleanerTicketItemAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerTicketItemAction.html?ajxr=hb",
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
