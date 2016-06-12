<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _ticket_idValue= "";
    String _parent_ticket_idValue= "";
    String _product_idValue= "";
    String _subtotal_amountValue= "";
    String _total_amountValue= "";
    String _discount_idValue= "";
    String _total_discount_amountValue= "";
    String _special_discount_amountValue= "";
    String _noteValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";

%>

<div id="partitionFormFrame_cleaner_ticket_item_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerTicketItem_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_ticket_item_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





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






		<!--
		<div class="ajaxFormLabel" style="font-weight:bold;">ExtraString</div>
		<INPUT NAME="extString" type="text" size="3" value=""></INPUT><br />

		<div class="ajaxFormLabel" style="font-weight:bold;">Ext Int</div>
		<INPUT NAME="extInt" type="text" size="70" value=""></INPUT><br /> 
		-->
		<INPUT TYPE="HIDDEN" NAME="ajxr" value="getmodalstatus">
		<INPUT TYPE="HIDDEN" NAME="add" value="true">
		<INPUT TYPE="HIDDEN" NAME="wpid" value="<%=_wpId%>">

	</form>

	<span id="ajaxSubmitResult<%= catchString %>"></span> 
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_ticket_item_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
