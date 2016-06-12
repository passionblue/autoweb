<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.SessionContext,com.autosite.*,com.autosite.util.*,java.util.*,com.autosite.servlet.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	SessionContext sessionContext = (SessionContext) session.getAttribute("k_session_context");
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
	
	
	EcCategory cat = EcCategoryDS.getInstance().getObjectByPageId(dynPage.getId());
	
	List products = new ArrayList();
	if ( cat!= null) {
		products = EcProductDS.getInstance().getBySiteIdCategoryId(site.getId(), cat.getId());
	}
	
	Page path[] =DynMenuManager.getInstance().getPagePath(dynPage.getId());
	
	// numColumns in display
	
	int width = StyleUtil.calculateContentWidth(siteConfig);
	int colCount = EcDisplayUtil.DefaultColumnCount;
	
	if (ecDisplayConfig != null) 
		colCount = ecDisplayConfig.getColumnCount();
	
%>	                    

<!-- ########################################################################################## -->

<div id="categoryChain">
	<div id="categoryChainItem">
	<a href="/">Home</a>&nbsp;
	</div>
<%
	if (path.length > 0) {	
	for (int i = 0; i < path.length;i++){
		EcCategory pageCat = EcCategoryDS.getInstance().getObjectByPageId(path[i].getId());
		
		String categoryString = (WebUtil.isNull(pageCat.getCategoryName())? pageCat.getCategoryName() : path[i].getPageMenuTitle());
		
%>
	<div id="categoryChainItem">
	<a href="/m_<%=path[i].getPageName()%>.html"><%= categoryString %></a>&nbsp;
	</div>
<%
	}
	}
%>
</div><div class="clear"></div>

<!-- ########################################################################################## -->

<div id="listFrame">


<%


	int idx = 0;
	for (Iterator iter = products.iterator();iter.hasNext();){
		EcProduct ecProduct = (EcProduct) iter.next();

		String imageUrl = "/images/ec/sample.jpg";
		
		if (!WebUtil.isNull(ecProduct.getImageUrl())){
			imageUrl = ecProduct.getImageUrl();
		}


%>

<%
		if (idx%colCount == 0){
%>
		<div id="listRowFrame">		
<%
		}
%>
		
			<div id="listProdFrame" onclick="document.location='/t_ec_product_single.html?id=<%=ecProduct.getId()%>';">
				<div id="listDescSection">
					<div id="listBrand" class="labelBrand"> <%= ecProduct.getBrand() %></div>
					<div id="listName" class="labelName"> <%= ecProduct.getName() %></div><br/>

<%	if ( !WebUtil.isNull(ecProduct.getSalePrice())  && !WebUtil.isTrue(ecProduct.getSaleEnds())){%>

					<div id="listPrice" class="labelMsrpWithSale"> Regular Price : <%= WebUtil.displayMoney(ecProduct.getMsrp()) %></div>
					<div id="listPrice" class="labelSale"> Sale : <%= WebUtil.displayMoney(ecProduct.getSalePrice()) %></div>
<% } else { %>
					<div id="listPrice" class="labelMsrpNoSale"> Regular Price : <%= WebUtil.displayMoney(ecProduct.getMsrp()) %></div>
<% } %>

				</div>
				<div id="listImage"> 
					<img src="<%=imageUrl %>"/>
				</div>				
			</div>

<%
		if (idx%colCount == (colCount-1)|| idx == products.size()-1){
%>
		</div><div class="clear"> </div>		
<%
		}
		idx++;
%>


<%
}
%>

</div>

<% if (isLogin) { %>
<br class="clear"/>	
<div class="pageMenu" style="float:none;">
<jsp:include page="/jsp/sidemenus/page_control_menu.jsp" />
</div>
<% } %>
&nbsp;&nbsp;<br/>

