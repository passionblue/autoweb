<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.ec.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*"%>
<%@page import="com.autosite.Constants"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	String pageName = (String) session.getAttribute("k_page_name");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	List pages = PageDS.getInstance().getBySiteId(site.getId());

    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	} 
	
	if (pages == null) {
		return;
	}

	boolean isLogin = (sessionContext != null && sessionContext.isLogin());

%>

<div id="dynMenuTopFrame">
		<div id="dynMenuTop_Home" class="dynMenuTopItem"><a href="/home.html" > Home</a> </div>
		
<%
	if (siteConfig.getEcEnabled() == Constants.TRUE){
	
	    String rpcId = (String) session.getAttribute("k_RPCI");
    	EcCart cart = EcCartManager.getCartMakeSure(sessionContext, rpcId, site.getId());
		if (cart != null ) {
%>		
		<div id="dynMenuTopItemDivider">&#149; </div>   	<div id="dynMenuTop_ShoppingCart" class="dynMenuTopItem"><a href="/t_ec_cart_main.html" > Shopping Cart(Item <%=cart.getNumCartItem() %>: Total:<%=WebUtil.displayMoney(cart.getTotalPrice()) %>)</a></div>
		<div id="dynMenuTopItemDivider">&#149;  </div>   <div id="dynMenuTop_WishList" class="dynMenuTopItem"><a href="/t_ec_wish_list_main.html" > Wish List</a></div>
<%
		}
	}
%>		
		
	<%
		for(Iterator iter = pages.iterator();iter.hasNext();) {
		    Page p = (Page) iter.next();
		    
		    if ( p.getPageName().equals("XHOME")) 
		    	continue;
		    	
		    if ( p.getHide() == 1 ) 
		    	continue; 	

            if ( p.getHeaderPage() == 0 ) 
                continue;   		    
		    // set different color for selected menu
		    
			String link = "";  
		    if ( pageName != null && pageName.trim().equals(p.getPageName())) {
		    	link = "<div id=\"dynMenuTop_"+p.getPageName()+"\" class=\"dynMenuTopItemSelected\"><a href=\"/m_" + p.getPageName() + ".html\" > " + p.getPageMenuTitle() +" </a></div>";
		    } else {
				link = "<div id=\"dynMenuTop_"+p.getPageName()+"\" class=\"dynMenuTopItem\"        ><a href=\"/m_" + p.getPageName() + ".html\" > " + p.getPageMenuTitle() +" </a></div>";  
		    }
		    
	 %>
	
		  <div id="dynMenuTopItemDivider">&nbsp;</div> <%= link %> 
		
	<%
	}
	%>

	<%
	if (isLogin) {
	%>	
	<div id="dynMenuTopItemDivider">&nbsp; </div><div id="dynMenuTop_Logout" class="dynMenuTopItem" ><a href="/logout.html" style="color:red"> Logout </a></div>
	<%
	} else {
	%>
	<div id="dynMenuTopItemDivider">&nbsp;</div><div id="dynMenuTop_Login" class="dynMenuTopItem" style="color:orange"><a href="/t_login_form.html" style="color:orange"> Login </a></div>
	<div id="dynMenuTopItemDivider">&nbsp;</div><div id="dynMenuTop_Register" class="dynMenuTopItem" style="color:orange"><a href="/t_register_simple_add.html" style="color:blue"> Register</a></div>
	<%
	}
	%>

	<div id="dynMenuTopItemDivider">&nbsp;</div><div id="dynMenuTop_Register" class="dynMenuTopItem"><a href="<%= WebUtil.getPrintableRequest(request) %>" target="_blank"> Print</a></div>

	<%
	if (isLogin) {
	%>	
	<div id="dynMenuTopItemDivider">&nbsp;</div><div id="dynMenuTop_Register" class="dynMenuTopItem">Username:<%= sessionContext.getUserObject().getUsername() %></div>
	<%
	}
	%>

	
</div><div class="clear"></div>



