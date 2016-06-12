<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    
	SiteRegStore siteRegStore = (SiteRegStore) session.getAttribute("k_site_red_store");
	
	if ( siteRegStore == null) {
		String nextJSP = "/jsp/form/sitereg/siteRegStart.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		return;
	}     

    SiteRegAccountServiceInfo info = siteRegStore.getAccServiceInfo();
    if (info == null) 
    	info = new SiteRegAccountServiceInfo();
    
    String _email_retypeValue= "";
    
    String _target_domainValue= (reqParams.get("targetDomain")==null?info.getTargetDomain():WebUtil.display((String)reqParams.get("targetDomain")));
    String _emailValue= (reqParams.get("email")==null?info.getEmail():WebUtil.display((String)reqParams.get("email")));
    String _companyValue= (reqParams.get("company")==null?info.getCompany():WebUtil.display((String)reqParams.get("company")));
    String _first_nameValue= (reqParams.get("firstName")==null?info.getFirstName():WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?info.getLastName():WebUtil.display((String)reqParams.get("lastName")));
    String _phoneValue= (reqParams.get("phone")==null?info.getPhone():WebUtil.display((String)reqParams.get("phone")));

	
	//>> START <<
	

	
%> 


<br>
<div id="siteRegAccountServiceInfoForm_topArea" class="formTopArea"></div>
<div id="siteRegAccountServiceInfoForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="siteRegAccountServiceInfoForm" method="get" action="/siteRegAccountServiceInfoAction.html" >


	<div id="siteRegAccountServiceInfoForm_email_field">
    <div id="siteRegAccountServiceInfoForm_email_label" class="formRequiredLabel" >Email* </div>
    <div id="siteRegAccountServiceInfoForm_email_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="email" value="<%=WebUtil.display(_emailValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegAccountServiceInfoForm_emailRetype_field">
    <div id="siteRegAccountServiceInfoForm_emailRetype_label" class="formRequiredLabel" >Re-enter Email* </div>
    <div id="siteRegAccountServiceInfoForm_emailRetype_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="emailRetype" value="<%=WebUtil.display(_email_retypeValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegAccountServiceInfoForm_company_field">
    <div id="siteRegAccountServiceInfoForm_company_label" class="formLabel" >Company </div>
    <div id="siteRegAccountServiceInfoForm_company_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="company" value="<%=WebUtil.display(_companyValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegAccountServiceInfoForm_firstName_field">
    <div id="siteRegAccountServiceInfoForm_firstName_label" class="formRequiredLabel" >First Name* </div>
    <div id="siteRegAccountServiceInfoForm_firstName_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegAccountServiceInfoForm_lastName_field">
    <div id="siteRegAccountServiceInfoForm_lastName_label" class="formRequiredLabel" >Last Name* </div>
    <div id="siteRegAccountServiceInfoForm_lastName_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegAccountServiceInfoForm_phone_field">
    <div id="siteRegAccountServiceInfoForm_phone_label" class="formLabel" >Phone </div>
    <div id="siteRegAccountServiceInfoForm_phone_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="phone" value="<%=WebUtil.display(_phoneValue)%>"/> 
    </div>      
	</div><div class="clear"></div>

        <div id="siteRegAccountServiceInfoForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.siteRegAccountServiceInfoForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
</form>
</div> <!-- form -->
<div id="siteRegAccountServiceInfoForm_bottomArea" class="formBottomArea"></div>


