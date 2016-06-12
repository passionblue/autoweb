<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());


	Map reqParams = (Map) session.getAttribute("k_request_params");

	String _target_domainValue= WebUtil.display((String)reqParams.get("targetDomain"));;

	if (!WebUtil.isNull(_target_domainValue)){
		_target_domainValue = SiteDS.getCommonUrl(_target_domainValue);
	}
	String _wpId = WebProcManager.registerWebProcess();
	
	
	//>> START <<

	SiteRegStore siteRegStore = (SiteRegStore)SessionStoreUtil.createAndSave(session, SiteRegStore.getSessionKey());
%> 

<%
	if ( !WebUtil.isTrue(site.getSiteRegisterEnable()) ){
%>
	<h3> Site registion not enabled in this site</h3>
<%	
		return;
	} 
%>


<br>
<div id="siteRegStartForm_topArea" class="formTopArea"></div>
<div id="siteRegStartForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="siteRegStartForm" method="get" action="/siteRegStartAction.html" >

	<div id="siteRegStartForm_targetDomain_field">
    <div id="siteRegStartForm_targetDomain_label" class="formLabel" >Target Domain </div>

<%
	if (WebUtil.isNull(_target_domainValue)){
%>
    <div id="siteRegStartForm_targetDomain_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="targetDomain" "/> 
    </div>      
<%
	} else {
%>
    <div id="siteRegStartForm_targetDomain_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="targetDomain">
        <option value="" >- Please Select -</option>
        <option value="<%=_target_domainValue %>" ><%=_target_domainValue %></option>
        </select> <span></span>
    </div>      
<%
	}
%>

	</div><div class="clear"></div>

        <div id="siteRegStartForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.siteRegStartForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="siteRegStartForm_bottomArea" class="formBottomArea"></div>