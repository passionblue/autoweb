<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

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
<form name="ecAnonymousTransactionFormEdit" method="post" action="/ecAnonymousTransactionAction.html" >
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
            <b><a href="javascript:document.ecAnonymousTransactionFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcAnonymousTransaction.getId()%>">
</form>
