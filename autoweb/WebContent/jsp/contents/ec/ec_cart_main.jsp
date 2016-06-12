<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.ec.*,java.util.*,com.autosite.util.*"%>
 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<% 
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	if (sessionContext == null) 
		sessionContext = AutositeSessionContext.create(session);

	int userType = (sessionContext != null? sessionContext.getUserType(): Constants.UserAnonymous);
	boolean isLogin = (sessionContext != null? sessionContext.isLogin():false);

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	PageView pageView = (PageView) session.getAttribute("k_view_pageview");
	if ( pageView == null) {
		pageView = PageView.getDefaultPageView();
	}
	
	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);

	String continueShoppingLink = "";
	if (dynPage == null) {
		continueShoppingLink = "home.html";
	} else {
		continueShoppingLink = "m_" + dynPage.getPageName() + ".html"; 
	}
%>

<%
    String rpcId = (String) session.getAttribute("k_RPCI");
    EcCart cart = EcCartManager.getCartMakeSure(sessionContext, rpcId, site.getId());

	List items = new ArrayList();
    if (cart == null) {
%>
	<h3> Cart Error Occurred. Please try again. Sorry for the incovenience</h3>
<%		
    } else {
		System.out.println("Cart Found =" + cart);
		items = cart.getCartItems();
	}
%>

<div id="cartContinueShopping">
	<a href="<%=continueShoppingLink%>">CONTINUE SHOPPING</a>
</div>

<br/>
<div id="cartFrame" >


<table cellpadding="5" cellspacing="1" width="100%" border="0" bgcolor="orange">

<tr>
	<td bgcolor="#ffffff" align="center">
		Quantity
	</td>
	<td bgcolor="#ffffff" align="center">
	</td>
	<td bgcolor="#ffffff" align="center">
	Product Description
	</td>
	<td bgcolor="#ffffff" align="center">
	Product Details
	</td>
	<td bgcolor="#ffffff" align="center">
	Unit Price
	</td>
	<td bgcolor="#ffffff" align="center">
		Item Total
	</td>

</tr>

<%
	int formIdx = 0;
	for(Iterator iter = items.iterator();iter.hasNext();){
		EcCartItem item = (EcCartItem) iter.next();
		String _wpId = WebProcManager.registerWebProcess();
%>
<tr>
	<td bgcolor="#ffffff" align="center">
		<form name="cartUpdateForm<%=formIdx %>" method="GET" action="/ecCartSubmitAction.html" style="margin-bottom:-10px;">
			<input size="1" type="text" name="qty" value="<%= item.getCount() %>" style="margin:0"></input><br/>
			<INPUT TYPE="HIDDEN" NAME="edit" value="true"><br/>
			<INPUT TYPE="HIDDEN" NAME="itemId" value="<%= item.getSerial() %>"  >
			<INPUT TYPE="HIDDEN" NAME="siteSku" value="<%= item.getProduct().getSiteSku() %>"  >
			<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>"  >
			<a id="addToCart" href="javascript:document.cartUpdateForm<%=formIdx %>.submit();">update</a>
		</form>
		<form name="cartItemDeleteForm<%=formIdx %>" method="GET" action="/ecCartSubmitAction.html" style="margin:0">
			<INPUT TYPE="HIDDEN" NAME="edit" value="true"><br/>
			<INPUT TYPE="HIDDEN" NAME="itemId" value="<%= item.getSerial() %>"  >
			<INPUT TYPE="HIDDEN" NAME="siteSku" value="<%= item.getProduct().getSiteSku() %>"  >
			<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>"  >
			<a id="addToCart" href="javascript:document.cartItemDeleteForm<%=formIdx %>.submit();">remove</a>
		</form>		
		
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
		formIdx++;
	}
%>
<tr>
	<td bgcolor="#ffffff" align="right" colspan="5">
		Total
	</td>
	<td bgcolor="#ffffff" align="right">
		<div id="cartListPrice" style="font: normal normal bold 14px inherited;color: blue">
			<%= cart.getTotalPrice() %>
		</div>
	</td>

</tr>
</table>	
</div>

<%
	if (isLogin) {
%>

<table cellpadding="5" cellspacing="1" width="100%" border="0" bgcolor="white">
<tr>
	<td align="right">
		<img src="http://www.splashglass.co.nz/assets/images/verisign_logo.gif"/>
	</td>
	<td align="right">
		<div style="font:normal normal bold 20px verdana;border: 3px double orange;padding: 10px; -moz-border-radius: 5px; width:120px;text-align: center" ><a href="/v_ec_checkout_payment_return_customer.html">check out</a></div>
	</td>
	
</tr>
</table>
<%
	} else {
%>
<table cellpadding="5" cellspacing="1" width="100%" border="0" bgcolor="white">
<tr>
	<td align="right">
		<img src="http://www.splashglass.co.nz/assets/images/verisign_logo.gif"/>
	</td>
	<td align="right">
		<div style="font:normal normal bold 20px verdana;border: 3px double orange;padding: 10px; -moz-border-radius: 5px; width:120px;text-align: center" ><a href="/t_ec_checkout_account_info.html">check out</a></div>
	</td>
	
</tr>
</table>

<%
	}
%>

