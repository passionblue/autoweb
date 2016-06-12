<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.*,com.autosite.*,com.autosite.ec.*,com.autosite.util.*,java.util.*,com.autosite.servlet.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	SessionContext sessionContext = (SessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	PageDS pageDS = PageDS.getInstance();

	// Get the list of products	
	String productId = request.getParameter("id");

	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = pageDS.getBySiteIdPageName(site.getId(), pageName);
	if (dynPage == null) {
		if (productId != null) {
			EcProduct prod = (EcProduct) EcProductDS.getInstance().getById(productId);
			if (prod != null) {
				EcCategory cat = EcCategoryDS.getInstance().getById(prod.getCategoryId());
				if (cat != null) 
					dynPage = pageDS.getById(cat.getPageId());
			}
		} else {
			return;
		}
	}
	                    
	// Configure site config	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	} 
	
	PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());

	boolean isLogin = (sessionContext != null && sessionContext.isLogin());



	// How to forward error page to formatted page

	EcProduct product= null;
	if (productId != null) {
		product = (EcProduct) EcProductDS.getInstance().getById(productId);
	}

	String _wpId = WebProcManager.registerWebProcess();
	
	
	String imageUrl = "/images/ec/sample.jpg";
	
	if (!WebUtil.isNull(product.getImageUrl())){
		imageUrl = product.getImageUrl();
	}

%>	                    

<%
	if (product == null){
		session.setAttribute("k_error_page_msg", "Invalid Product");
%>
		<jsp:include page="/jsp/layout/error.jsp"/>
<%
		return;
	}
%>	

	
<!-- ########################################################################################## -->

<div id="prodSingleFrame">

	<div id="prodImageSection">
		<img src="<%=imageUrl %>"/>
	</div>
	<div id="imageDivider">&nbsp;</div>
	
	<div id="prodDescSection">

		<div id="prodBrand" class="labelBrand">
			<%= WebUtil.display(product.getBrand()) %>
		</div>

		<div id="prodName" class="labelName">
			<%= WebUtil.display(product.getName()) %>
		</div>
		
		
		<div id="prodSku" class="labelSku"> SKU#
			<%= WebUtil.display(product.getSiteSku()) %>
		</div>
		
		<br/>

<%	if ( !WebUtil.isNull(product.getSalePrice())  && !WebUtil.isTrue(product.getSaleEnds())){%>
		<div id="prodPrice" class="labelMsrpWithSale"> Regular Price :<%= WebUtil.displayMoney(product.getMsrp()) %></div>
		<div id="prodPrice" class="labelSale">Sale : <%= WebUtil.displayMoney(product.getSalePrice()) %></div>
<% } else { %>
		<div id="prodPrice" class="labelMsrpNoSale">Regular Price :<%= WebUtil.displayMoney(product.getMsrp()) %></div>
<% } %>


<% if (!WebUtil.isNull(product.getPromoNote())){ %>
		<div>Promo Note:<%= WebUtil.display(product.getPromoNote()) %></div>
<%  } %>

		<form name="panelLinkStyleForm" method="post" action="/ecCartSubmitAction.html" >
	      
		<div id="prodVariation">
<%
	EcProductPriceWrapper wrapper = new EcProductPriceWrapper(product);
	if (!WebUtil.isNull(product.getSize())){


		List sizeVarList = wrapper.getSizeVarList();
		if (sizeVarList.size()>0){ 
%>		
		    <div class="sizeColorLabel" >Size <span style="color:red;font-size:13px;" id="sizeLabelInset"></span></div> 
		    <select id="sizeSelect" name="size" STYLE="font-family : verdana; font-size : 7pt; color: #606060; height: 20px; width:150px">
<%
		if (sizeVarList.size() >1){		 
%>
		    	<option value="" > - Please Select - </option>
<%
		}
%>
<%
        for (Iterator iterator = wrapper.getSizeVarList().iterator(); iterator.hasNext();) {
            String string = (String) iterator.next();
			System.out.println(string);
%>		    
            	<option value="<%= string%>"><%= string + "   " + WebUtil.displayMoney(wrapper.getSizeOffset(string))%></option>
<%
		}
%>
		</select>
<%
		}
	}
%>
		</div >
		<div id="prodVariation">
<%
	if (!WebUtil.isNull(product.getColor())){
		String colorList[] = product.getColor().split(",");
		if (colorList.length>0){ 
%>		
		    <div class="sizeColorLabel" >Color <span style="color:red;font-size:13px;" id="colorLabelInset"></span></div> 
		    <select  id="colorSelect" name="color" STYLE="font-family : verdana; font-size : 7pt; color: #606060; height: 20px;; width:150px">

<%
		if (colorList.length >1){		 
%>
		      	<option value=""> - Please Select - </option>
<%
		}
%>
		    
<%
		for(int i = 0; i <colorList.length;i++){
%>		    
            	<option value="<%= colorList[i].trim()%>"><%= colorList[i].trim()%></option>
<%
		}
%>
		</select>
<%
		}
	}
%>
		</div >
		<br/>
		
			Quantity <input size="1" type="text" name="qty" value="1"/>
			
			<br/>
			<br/>
			
			<b><a id="addToCart" href="javascript:document.panelLinkStyleForm.submit();"><img src="/images/ec/addToBag1.gif"/></a> </b><br/>
			<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>"  >
			<INPUT TYPE="HIDDEN" NAME="add" value="true"><br/>
			<INPUT TYPE="HIDDEN" NAME="siteSku" value="<%=product.getSiteSku() %>"><br/>
			<INPUT TYPE="HIDDEN" NAME="id" value="<%=product.getId() %>"><br/>
			
		</form>

		<form name="ecWishListForm" method="get" action="/ecWishListAction.html" >
			<INPUT TYPE="HIDDEN" NAME="productId" value="<%= product.getId()%>">
			<INPUT TYPE="HIDDEN" NAME="id" value="<%= product.getId()%>">
			<INPUT TYPE="HIDDEN" NAME="siteSku" value="<%=product.getSiteSku() %>"><br/>
			<INPUT TYPE="HIDDEN" NAME="name" value="<%=product.getName() %>"><br/>

			<INPUT TYPE="HIDDEN" NAME="returnPage" value="ec_wish_list_home">
			<INPUT TYPE="HIDDEN" NAME="add" value="true">
			<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>"  >
    	    <b><a href="javascript:document.ecWishListForm.submit();">Submit</a> </b>
		</form>

		
		<div id="prodDesc">
			<%= WebUtil.display(product.getDescription()) %>
		</div>

	</div>
	
</div>
<div class="clear"></div>

<a href="/v_ec_product_edit.html?id=<%= product.getId()%>">edit </a>



