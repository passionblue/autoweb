<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.ec.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	PageDS pageDS = PageDS.getInstance();

	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = pageDS.getBySiteIdPageName(site.getId(), pageName);
	                    
	// Confiture site config	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	} 
	
	PageConfig pageConfig = null;
	if (dynPage != null)
		pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());

	boolean isLogin = (sessionContext != null && sessionContext.isLogin());

    String rpcId = (String) session.getAttribute("k_RPCI");
    EcCart cart = EcCartManager.getCartMakeSure(sessionContext, rpcId, site.getId());

	List items = new ArrayList();

	if (cart == null) {
		WebLog.debug(session, "Cart is null");
		return;
	} 

	items = cart.getCartItems();
	EcCheckoutAccountInfo accountInfo = EcCheckoutAccountInfoDS.getInstance().getObjectByCartSerial(cart.getSerial());
	EcCheckoutPaymentWithoutRegister payment = EcCheckoutPaymentWithoutRegisterDS.getInstance().getObjectByCartSerial(cart.getSerial());
	
	WebLog.debug(session, EcCheckoutAccountInfoDS.objectToString(accountInfo));
	WebLog.debug(session, EcCheckoutPaymentWithoutRegisterDS.objectToString(payment));
	
	if (items.size() == 0 ||payment == null || accountInfo == null) {
		// What can I do here. Happens when session expires. 
		System.out.println("XXXXXXXXXXXXX");
		
		
	}
		
    String _wpId = WebProcManager.registerWebProcess(cart.getSerial());
		
	
%> 

<table cellpadding="5" cellspacing="1" width="100%" border="0" bgcolor="orange">

<%

	for(Iterator iter = items.iterator();iter.hasNext();){
		EcCartItem item = (EcCartItem) iter.next();
%>
<tr>
	<td bgcolor="#ffffff" align="center">
		<%= item.getCount() %>
	</td>
	<td bgcolor="#ffffff" align="center">
			<img class="thumbSize" src="<%=item.getProduct().getImageUrl()%>">  
	</td>
	<td bgcolor="#ffffff" valign="top">
			<a href="/t_ec_product_single.html?id=<%= item.getProduct().getId() %>&sku=<%=item.getProduct().getSiteSku() %>">
			<%= item.getProduct().getName() %><br/>
			 <%= WebUtil.display(item.getProduct().getSiteSku()) %></a>
			
	</td>
	<td bgcolor="#ffffff" valign="top">
		 <%= item.getSize() %><br/>
		 <%= WebUtil.display(item.getColor()) %><br/>
	</td>
	<td bgcolor="#ffffff" valign="middle" align="right">
		<div id="cartListPrice" style="font: normal normal bold 14px inherited;color: blue">
		<%= WebUtil.displayMoney(item.getOrderPirce()) %>
		</div>
	</td>
	<td bgcolor="#ffffff" valign="middle" align="right">
		<div id="cartListPrice" style="font: normal normal bold 14px inherited;color: blue">
		<%= WebUtil.displayMoney(item.getOrderPirce()*item.getCount()) %>
		</div>
	</td>
</tr>
<%
	}

%>
</table>	

<%= accountInfo.getFirstName() %>&nbsp;
<%= accountInfo.getLastName() %><br/>

<%= accountInfo.getAddress1() %><br/>

<%= accountInfo.getCity() %><br/>

<%= accountInfo.getUseBilling() %><br/>

<%= payment.getCardType() %><br/>



<form name="/ecCheckoutConfirmOrderWithoutRegisterForm" method="get" action="/ecCheckoutConfirmOrderWithoutRegisterAction.html" >
		<div style="font:normal normal bold 20px verdana;border: 3px double orange;padding: 10px; -moz-border-radius: 5px; width:120px;text-align: center" >
	    	<a id="formSubmitLogin" href="javascript:document.ecCheckoutConfirmOrderWithoutRegisterForm.submit();">Place Order</a>
		</div>
<INPUT TYPE="HIDDEN" NAME="cartSerial" value="<%= cart.getSerial() %>">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<a id="formSubmitLogin" href="/ecCheckoutConfirmOrderWithoutRegisterAction.html?cartSerial=<%= cart.getSerial() %>&wpid=<%= _wpId %>">Place Order</a>


</form>





