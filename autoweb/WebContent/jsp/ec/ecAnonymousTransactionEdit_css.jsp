<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    EcAnonymousTransaction _EcAnonymousTransaction = EcAnonymousTransactionDS.getInstance().getById(id);

    if ( _EcAnonymousTransaction == null ) {
        return;
    }

    String _anonymous_user_idValue=  WebUtil.display(_EcAnonymousTransaction.getAnonymousUserId());
    String _order_idValue=  WebUtil.display(_EcAnonymousTransaction.getOrderId());
    String _payment_info_idValue=  WebUtil.display(_EcAnonymousTransaction.getPaymentInfoId());
    String _amountValue=  WebUtil.display(_EcAnonymousTransaction.getAmount());
    String _transaction_typeValue=  WebUtil.display(_EcAnonymousTransaction.getTransactionType());
    String _resultValue=  WebUtil.display(_EcAnonymousTransaction.getResult());
    String _time_processedValue=  WebUtil.display(_EcAnonymousTransaction.getTimeProcessed());
    String _return_codeValue=  WebUtil.display(_EcAnonymousTransaction.getReturnCode());
    String _return_msgValue=  WebUtil.display(_EcAnonymousTransaction.getReturnMsg());
%> 

<br>
<div id="ecAnonymousTransactionForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="ecAnonymousTransactionFormEdit" method="get" action="/ecAnonymousTransactionAction.html" >




	<div id="ecAnonymousTransactionForm_anonymousUserId_field">
    <div id="ecAnonymousTransactionForm_anonymousUserId_label" class="formLabel" >Anonymous User Id </div>
    <div id="ecAnonymousTransactionForm_anonymousUserId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="anonymousUserId" value="<%=WebUtil.display(_anonymous_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousTransactionForm_orderId_field">
    <div id="ecAnonymousTransactionForm_orderId_label" class="formLabel" >Order Id </div>
    <div id="ecAnonymousTransactionForm_orderId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="orderId" value="<%=WebUtil.display(_order_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousTransactionForm_paymentInfoId_field">
    <div id="ecAnonymousTransactionForm_paymentInfoId_label" class="formLabel" >Payment Info Id </div>
    <div id="ecAnonymousTransactionForm_paymentInfoId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="paymentInfoId" value="<%=WebUtil.display(_payment_info_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousTransactionForm_amount_field">
    <div id="ecAnonymousTransactionForm_amount_label" class="formLabel" >Amount </div>
    <div id="ecAnonymousTransactionForm_amount_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="amount" value="<%=WebUtil.display(_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="ecAnonymousTransactionForm_transactionType_field">
    <div id="ecAnonymousTransactionForm_transactionType_label" class="formLabel" >Transaction Type </div>
    <div id="ecAnonymousTransactionForm_transactionType_dropdown" class="formFieldDropDown" >       
        <select id="field" name="transactionType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _transaction_typeValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousTransactionForm_result_field">
    <div id="ecAnonymousTransactionForm_result_label" class="formLabel" >Result </div>
    <div id="ecAnonymousTransactionForm_result_dropdown" class="formFieldDropDown" >       
        <select id="field" name="result">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _resultValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>






	<div id="ecAnonymousTransactionForm_returnCode_field">
    <div id="ecAnonymousTransactionForm_returnCode_label" class="formLabel" >Return Code </div>
    <div id="ecAnonymousTransactionForm_returnCode_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="returnCode" value="<%=WebUtil.display(_return_codeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousTransactionForm_returnMsg_field">
    <div id="ecAnonymousTransactionForm_returnMsg_label" class="formLabel" >Return Msg </div>
    <div id="ecAnonymousTransactionForm_returnMsg_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="returnMsg" value="<%=WebUtil.display(_return_msgValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="ecAnonymousTransactionFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecAnonymousTransactionFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcAnonymousTransaction.getId()%>">
</form>
</div> <!-- form -->
