<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

	//>>START<<
	SiteRegStore siteRegStore = (SiteRegStore) session.getAttribute(SiteRegStore.getSessionKey());
	
	if ( siteRegStore == null) {
		String nextJSP = "/jsp/form/sitereg/siteRegStart.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		return;
	} 
    SiteRegPaymentInfo info = siteRegStore.getPayInfo();
	if (info == null) info = new SiteRegPaymentInfo();

	//>>END<<

    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    
    String _term_agreeValue= (reqParams.get("termAgree")==null?"":WebUtil.display((String)reqParams.get("termAgree")));
	String _target_domainValue = siteRegStore.getStart().getTargetDomain();

    String _wpId = WebProcManager.registerWebProcess();
%> 

<br>
<div id="siteRegConfirmForm_topArea" class="formTopArea"></div>
<div id="siteRegConfirmForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="siteRegConfirmForm" method="get" action="/siteRegConfirmAction.html" >

	<div id="siteRegConfirmForm_termAgree_field">
    <div id="siteRegConfirmForm_termAgree_label" class="formLabel" >Term Agreement </div>
    <div id="siteRegConfirmForm_termAgree_checkbox" class="formFieldCheckbox" > <span></span>      
        <input id="requiredField" type="checkbox" name="termAgree" <%=HtmlUtil.getCheckedBoxValue(_term_agreeValue)%> />
    </div>      
	</div><div class="clear"></div>

        <div id="siteRegConfirmForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.siteRegConfirmForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="targetDomain" value="<%=WebUtil.display(_target_domainValue)%>" />
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="siteRegConfirmForm_bottomArea" class="formBottomArea"></div>


