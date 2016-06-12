<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _anonymous_user_idValue= "";
    String _order_idValue= "";
    String _payment_info_idValue= "";
    String _amountValue= "";
    String _transaction_typeValue= "";
    String _resultValue= "";
    String _time_processedValue= "";
    String _return_codeValue= "";
    String _return_msgValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_ec_anonymous_transaction_add.html"> Add New</a>
            <a href="t_ec_anonymous_transaction_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

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