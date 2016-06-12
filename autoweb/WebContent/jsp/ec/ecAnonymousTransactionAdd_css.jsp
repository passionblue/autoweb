<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcAnonymousTransaction _EcAnonymousTransactionDefault = new EcAnonymousTransaction();// EcAnonymousTransactionDS.getInstance().getDeafult();
    
    String _anonymous_user_idValue= (reqParams.get("anonymousUserId")==null?WebUtil.display(_EcAnonymousTransactionDefault.getAnonymousUserId()):WebUtil.display((String)reqParams.get("anonymousUserId")));
    String _order_idValue= (reqParams.get("orderId")==null?WebUtil.display(_EcAnonymousTransactionDefault.getOrderId()):WebUtil.display((String)reqParams.get("orderId")));
    String _payment_info_idValue= (reqParams.get("paymentInfoId")==null?WebUtil.display(_EcAnonymousTransactionDefault.getPaymentInfoId()):WebUtil.display((String)reqParams.get("paymentInfoId")));
    String _amountValue= (reqParams.get("amount")==null?WebUtil.display(_EcAnonymousTransactionDefault.getAmount()):WebUtil.display((String)reqParams.get("amount")));
    String _transaction_typeValue= (reqParams.get("transactionType")==null?WebUtil.display(_EcAnonymousTransactionDefault.getTransactionType()):WebUtil.display((String)reqParams.get("transactionType")));
    String _resultValue= (reqParams.get("result")==null?WebUtil.display(_EcAnonymousTransactionDefault.getResult()):WebUtil.display((String)reqParams.get("result")));
    String _time_processedValue= (reqParams.get("timeProcessed")==null?WebUtil.display(_EcAnonymousTransactionDefault.getTimeProcessed()):WebUtil.display((String)reqParams.get("timeProcessed")));
    String _return_codeValue= (reqParams.get("returnCode")==null?WebUtil.display(_EcAnonymousTransactionDefault.getReturnCode()):WebUtil.display((String)reqParams.get("returnCode")));
    String _return_msgValue= (reqParams.get("returnMsg")==null?WebUtil.display(_EcAnonymousTransactionDefault.getReturnMsg()):WebUtil.display((String)reqParams.get("returnMsg")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="ecAnonymousTransactionForm_topArea" class="formTopArea"></div>
<div id="ecAnonymousTransactionForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="ecAnonymousTransactionForm" method="get" action="/ecAnonymousTransactionAction.html" >


	<div id="ecAnonymousTransactionForm_anonymousUserId_field">
    <div id="ecAnonymousTransactionForm_anonymousUserId_label" class="formLabel" >Anonymous User Id </div>
    <div id="ecAnonymousTransactionForm_anonymousUserId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="anonymousUserId" value="<%=WebUtil.display(_anonymous_user_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousTransactionForm_orderId_field">
    <div id="ecAnonymousTransactionForm_orderId_label" class="formLabel" >Order Id </div>
    <div id="ecAnonymousTransactionForm_orderId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="orderId" value="<%=WebUtil.display(_order_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousTransactionForm_paymentInfoId_field">
    <div id="ecAnonymousTransactionForm_paymentInfoId_label" class="formLabel" >Payment Info Id </div>
    <div id="ecAnonymousTransactionForm_paymentInfoId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="paymentInfoId" value="<%=WebUtil.display(_payment_info_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousTransactionForm_amount_field">
    <div id="ecAnonymousTransactionForm_amount_label" class="formLabel" >Amount </div>
    <div id="ecAnonymousTransactionForm_amount_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="amount" value="<%=WebUtil.display(_amountValue)%>"/>
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
    <div id="ecAnonymousTransactionForm_returnCode_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="returnCode" value="<%=WebUtil.display(_return_codeValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousTransactionForm_returnMsg_field">
    <div id="ecAnonymousTransactionForm_returnMsg_label" class="formLabel" >Return Msg </div>
    <div id="ecAnonymousTransactionForm_returnMsg_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="returnMsg" value="<%=WebUtil.display(_return_msgValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="ecAnonymousTransactionForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecAnonymousTransactionForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="ecAnonymousTransactionForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcAnonymousTransactionDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcAnonymousTransaction _EcAnonymousTransaction = (EcAnonymousTransaction) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcAnonymousTransaction.getId() %> </td>

    <td> <%= WebUtil.display(_EcAnonymousTransaction.getAnonymousUserId()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousTransaction.getOrderId()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousTransaction.getPaymentInfoId()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousTransaction.getAmount()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousTransaction.getTransactionType()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousTransaction.getResult()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousTransaction.getTimeProcessed()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousTransaction.getReturnCode()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousTransaction.getReturnMsg()) %></td>


<td>
<form name="ecAnonymousTransactionForm<%=_EcAnonymousTransaction.getId()%>" method="get" action="/v_ec_anonymous_transaction_edit.html" >
    <a href="javascript:document.ecAnonymousTransactionForm<%=_EcAnonymousTransaction.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousTransaction.getId() %>">
</form>
<form name="ecAnonymousTransactionForm<%=_EcAnonymousTransaction.getId()%>2" method="get" action="/v_ec_anonymous_transaction_edit2.html" >
    <a href="javascript:document.ecAnonymousTransactionForm<%=_EcAnonymousTransaction.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousTransaction.getId() %>">
</form>

</td>
<td>
<a href="/ecAnonymousTransactionAction.html?del=true&id=<%=_EcAnonymousTransaction.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>