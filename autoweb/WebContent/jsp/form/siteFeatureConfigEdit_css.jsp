<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    SiteFeatureConfig _SiteFeatureConfig = SiteFeatureConfigDS.getInstance().getById(id);

    if ( _SiteFeatureConfig == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_feature_config_home";

    String _user_register_enabledValue=  WebUtil.display(_SiteFeatureConfig.getUserRegisterEnabled());
    String _ec_enabledValue=  WebUtil.display(_SiteFeatureConfig.getEcEnabled());
    String _email_subs_enabledValue=  WebUtil.display(_SiteFeatureConfig.getEmailSubsEnabled());
    String _time_createdValue=  WebUtil.display(_SiteFeatureConfig.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_SiteFeatureConfig.getTimeUpdated());
%> 

<br>
<div id="siteFeatureConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="siteFeatureConfigFormEdit" method="POST" action="/siteFeatureConfigAction.html" >



	<div id="siteFeatureConfigForm_userRegisterEnabled_field">
    <div id="siteFeatureConfigForm_userRegisterEnabled_label" class="formLabel" >User Register Enabled </div>
    <div id="siteFeatureConfigForm_userRegisterEnabled_dropdown" class="formFieldDropDown" >       
        <select name="userRegisterEnabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _user_register_enabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _user_register_enabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="siteFeatureConfigForm_ecEnabled_field">
    <div id="siteFeatureConfigForm_ecEnabled_label" class="formLabel" >Ec Enabled </div>
    <div id="siteFeatureConfigForm_ecEnabled_dropdown" class="formFieldDropDown" >       
        <select name="ecEnabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _ec_enabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _ec_enabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="siteFeatureConfigForm_emailSubsEnabled_field">
    <div id="siteFeatureConfigForm_emailSubsEnabled_label" class="formLabel" >Email Subs Enabled </div>
    <div id="siteFeatureConfigForm_emailSubsEnabled_dropdown" class="formFieldDropDown" >       
        <select name="emailSubsEnabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _email_subs_enabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _email_subs_enabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>







        <div id="siteFeatureConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.siteFeatureConfigFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteFeatureConfig.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
