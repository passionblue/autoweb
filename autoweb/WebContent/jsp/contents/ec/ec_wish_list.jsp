<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.session.*,com.jtrend.session.SessionContext,com.autosite.*,com.autosite.util.*,java.util.*,com.autosite.servlet.*"%>
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
	
	PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());

	boolean isLogin = (sessionContext != null && sessionContext.isLogin());


	EcDisplayConfig ecDisplayConfig = EcDisplayConfigDS.getInstance().getObjectBySiteId(site.getId());

	// Get the list of products	
	//List products = EcProductDS.getInstance().getBySiteId(site.getId());

	List wishList = new ArrayList();
	if (sessionContext.isLogin()){
		long userId = sessionContext.getUserObject().getId();
		wishList = EcWishListDS.getInstance().getBySiteIdUserId(site.getId(), userId);
	}
	
	
	int numDisplayInPage = 3;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	
%>	                    

<%= wishList.size() %>

<div id="wishListFrame">
<%
	// figuring out what to display and display page index.
	
/*	
	int totalDisplayPage = wishList.size()/numDisplayInPage + 1;
	if ( listPage > totalDisplayPage) {
		listPage = totalDisplayPage;
	}	
	
	if ( listPage < 1) listPage = 1;

	int beginIdx = (listPage-1)*numDisplayInPage;
	int endIdx = beginIdx + numDisplayInPage;
	if ( endIdx > wishList.size()) endIdx = wishList.size();
	boolean hasPrevious  = (listPage >1 ); 
	boolean hasNext  = (listPage < totalDisplayPage ); 
*/


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(wishList, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"t_ec_wish_list_main.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"t_ec_wish_list_main.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"t_ec_wish_list_main.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"t_ec_wish_list_main.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
	}

%>
	<%= prevLinkStr %>
<%
	for(int p = 0 ; p< pageIndexShortcut.length; p++){
%>

	<%=pageIndexShortcut[p] %> /
<%
	}
%>	
	<%= nextLinkStr %>

<%
	
	EcProductDS productDS = EcProductDS.getInstance();
	for(int i = pagingInfo.getBeginIdx() ; i < pagingInfo.getEndIdx();i++){

		EcWishList wish = (EcWishList)wishList.get(i);
		EcProduct product = productDS.getById(wish.getProductId());
		
		if (product == null) {
			System.out.println("Product " + wish.getProductId() + " not found");
			continue;
		}
		String imageUrl = "/images/ec/sample.jpg";
		
		
%>

		<div id="wishListItem">
			<div id="wishListProductImage">
				<img src="<%=imageUrl %>"/>
			</div>
			<div id="wishListProductDesc">
				<%= i+1 %> <br/>
				<%= wish.getId() %> <br/>
				<%= product.getName() %> <br/>
				<%= product.getSiteSku() %>
			</div>
		</div><div class="clear"></div>
<%
	}
%>
</div>