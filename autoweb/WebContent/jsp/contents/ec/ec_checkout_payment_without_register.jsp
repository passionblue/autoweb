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


	EcCheckoutPaymentWithoutRegister payment = null;

	if (cart != null) {
		payment = EcCheckoutPaymentWithoutRegisterDS.getInstance().getObjectByCartSerial(cart.getSerial());
		if (payment == null){
			payment = new EcCheckoutPaymentWithoutRegister();
			payment.setCartSerial(cart.getSerial());
			payment.setSiteId(site.getId());
			EcCheckoutPaymentWithoutRegisterDS.getInstance().put(payment);
		}
	}	

	WebDebug.getInstance().putDebug(session, "payment",payment);
	WebDebug.getInstance().putDebug(session, "payment",EcCheckoutPaymentWithoutRegisterDS.objectToString(payment));



    Map reqParams = (Map) session.getAttribute("k_reserved_params");
	
    String _cart_serialValue= (reqParams.get("cartSerial")==null?payment.getCartSerial():WebUtil.display((String)reqParams.get("cartSerial")));
    String _payment_typeValue= (reqParams.get("paymentType")==null?payment.getPaymentType():WebUtil.display((String)reqParams.get("paymentType")));
    String _card_typeValue= (reqParams.get("cardType")==null?payment.getCardType():WebUtil.display((String)reqParams.get("cardType")));
    String _payment_numValue= (reqParams.get("paymentNum")==null?payment.getPaymentType():WebUtil.display((String)reqParams.get("paymentNum")));
    String _expire_monthValue= (reqParams.get("expireMonth")==null?payment.getExpireMonth():WebUtil.display((String)reqParams.get("expireMonth")));
    String _expire_yearValue= (reqParams.get("expireYear")==null?payment.getExpireYear():WebUtil.display((String)reqParams.get("expireYear")));
    String _ccvValue= (reqParams.get("ccv")==null?payment.getCcv():WebUtil.display((String)reqParams.get("ccv")));

    String _wpId = WebProcManager.registerWebProcess();

	
%> 
<%
	if (cart == null || cart.getCartItems().size() == 0){
%>
	<h3 style="color: red"> Shopping cart is empty.</h3>
<%
	}
%>

<br>
<div id="ecCheckoutPaymentWithoutRegisterForm_topArea" class="formTopArea"></div>
<div id="ecCheckoutPaymentWithoutRegisterForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="ecCheckoutPaymentWithoutRegisterForm" method="get" action="/ecCheckoutPaymentWithoutRegisterAction.html" >

    <INPUT TYPE="HIDDEN" NAME="cartSerial" value="<%=WebUtil.display(_cart_serialValue)%>" />


	<div id="ecCheckoutPaymentWithoutRegisterForm_paymentType_field">
    <div id="ecCheckoutPaymentWithoutRegisterForm_paymentType_label" class="formLabel" >Payment Type </div>
    <div id="ecCheckoutPaymentWithoutRegisterForm_paymentType_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="paymentType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _payment_typeValue)%>>XX</option-->
        <option value="Credit Card" <%=HtmlUtil.getOptionSelect("Credit Card", _payment_typeValue)%>>Credit Card</option>
        <option value="Paypal" <%=HtmlUtil.getOptionSelect("Paypal", _payment_typeValue)%>>Paypal</option>
        <option value="Check" <%=HtmlUtil.getOptionSelect("Check", _payment_typeValue)%>>Check</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutPaymentWithoutRegisterForm_cardType_field">
    <div id="ecCheckoutPaymentWithoutRegisterForm_cardType_label" class="formLabel" >Card Type </div>
    <div id="ecCheckoutPaymentWithoutRegisterForm_cardType_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="cardType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _card_typeValue)%>>XX</option-->
        <option value="AMEX" <%=HtmlUtil.getOptionSelect("AMEX", _card_typeValue)%>>AMEX</option>
        <option value="VISA" <%=HtmlUtil.getOptionSelect("VISA", _card_typeValue)%>>VISA</option>
        <option value="MASTER" <%=HtmlUtil.getOptionSelect("MASTER", _card_typeValue)%>>MASTER</option>
        <option value="DISCOVER" <%=HtmlUtil.getOptionSelect("DISCOVER", _card_typeValue)%>>DISCOVER</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecCheckoutPaymentWithoutRegisterForm_paymentNum_field">
    <div id="ecCheckoutPaymentWithoutRegisterForm_paymentNum_label" class="formRequiredLabel" >Payment Num* </div>
    <div id="ecCheckoutPaymentWithoutRegisterForm_paymentNum_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="paymentNum" value="<%=WebUtil.display(_payment_numValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutPaymentWithoutRegisterForm_expireMonth_field">
    <div id="ecCheckoutPaymentWithoutRegisterForm_expireMonth_label" class="formLabel" >Expire Month </div>
    <div id="ecCheckoutPaymentWithoutRegisterForm_expireMonth_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="expireMonth">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _expire_monthValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _expire_monthValue)%>>Jan</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _expire_monthValue)%>>Feb</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _expire_monthValue)%>>Mar</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _expire_monthValue)%>>Apr</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _expire_monthValue)%>>May</option>
        <option value="6" <%=HtmlUtil.getOptionSelect("6", _expire_monthValue)%>>Jun</option>
        <option value="7" <%=HtmlUtil.getOptionSelect("7", _expire_monthValue)%>>Jul</option>
        <option value="8" <%=HtmlUtil.getOptionSelect("8", _expire_monthValue)%>>Aug</option>
        <option value="9" <%=HtmlUtil.getOptionSelect("9", _expire_monthValue)%>>Sep</option>
        <option value="10" <%=HtmlUtil.getOptionSelect("10", _expire_monthValue)%>>Oct</option>
        <option value="11" <%=HtmlUtil.getOptionSelect("11", _expire_monthValue)%>>Nov</option>
        <option value="12" <%=HtmlUtil.getOptionSelect("12", _expire_monthValue)%>>Dec</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutPaymentWithoutRegisterForm_expireYear_field">
    <div id="ecCheckoutPaymentWithoutRegisterForm_expireYear_label" class="formLabel" >Expire Year </div>
    <div id="ecCheckoutPaymentWithoutRegisterForm_expireYear_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="expireYear">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _expire_yearValue)%>>XX</option-->
        
<%
	int yearStart = 2009;
	for(int i = 0; i<10;i++){
	
		String year =  ""+(yearStart + i);
%>        
        <option value="<%=year %>" <%=HtmlUtil.getOptionSelect(year, _expire_yearValue)%>><%=year %></option>
<%
	}
%>        
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecCheckoutPaymentWithoutRegisterForm_ccv_field">
    <div id="ecCheckoutPaymentWithoutRegisterForm_ccv_label" class="formRequiredLabel" >Ccv* </div>
    <div id="ecCheckoutPaymentWithoutRegisterForm_ccv_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="ccv" value="<%=WebUtil.display(_ccvValue)%>"/> 
    </div>      
	</div><div class="clear"></div>

        <div id="ecCheckoutPaymentWithoutRegisterForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecCheckoutPaymentWithoutRegisterForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="cartSerial" value="<%= cart.getSerial() %>">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="ecCheckoutPaymentWithoutRegisterForm_bottomArea" class="formBottomArea"></div>


