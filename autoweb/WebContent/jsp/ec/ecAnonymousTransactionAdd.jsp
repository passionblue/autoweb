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
<form name="ecAnonymousTransactionForm" method="post" action="/ecAnonymousTransactionAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Anonymous User Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="anonymousUserId" value="<%=WebUtil.display(_anonymous_user_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Order Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="orderId" value="<%=WebUtil.display(_order_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Payment Info Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="paymentInfoId" value="<%=WebUtil.display(_payment_info_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Amount</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="amount" value="<%=WebUtil.display(_amountValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Transaction Type</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="transactionType" value="<%=WebUtil.display(_transaction_typeValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Result</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="result" value="<%=WebUtil.display(_resultValue)%>"/></TD>
	    </TR>
	            	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Return Code</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="returnCode" value="<%=WebUtil.display(_return_codeValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Return Msg</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="returnMsg" value="<%=WebUtil.display(_return_msgValue)%>"/></TD>
	    </TR>
	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecAnonymousTransactionForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


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
<form name="ecAnonymousTransactionForm<%=_EcAnonymousTransaction.getId()%>" method="post" action="/v_ec_anonymous_transaction_edit.html" >
    <a href="javascript:document.ecAnonymousTransactionForm<%=_EcAnonymousTransaction.getId()%>.submit();">Edit</a>           
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