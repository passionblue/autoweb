<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SiteFeatureConfig _SiteFeatureConfigDefault = new SiteFeatureConfig();// SiteFeatureConfigDS.getInstance().getDeafult();
    
    String _user_register_enabledValue= (reqParams.get("userRegisterEnabled")==null?WebUtil.display(_SiteFeatureConfigDefault.getUserRegisterEnabled()):WebUtil.display((String)reqParams.get("userRegisterEnabled")));
    String _ec_enabledValue= (reqParams.get("ecEnabled")==null?WebUtil.display(_SiteFeatureConfigDefault.getEcEnabled()):WebUtil.display((String)reqParams.get("ecEnabled")));
    String _email_subs_enabledValue= (reqParams.get("emailSubsEnabled")==null?WebUtil.display(_SiteFeatureConfigDefault.getEmailSubsEnabled()):WebUtil.display((String)reqParams.get("emailSubsEnabled")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SiteFeatureConfigDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SiteFeatureConfigDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_feature_config_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="siteFeatureConfigForm_topArea" class="formTopArea"></div>
<div id="siteFeatureConfigForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="siteFeatureConfigForm" method="POST" action="/siteFeatureConfigAction.html" >



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










        <div id="siteFeatureConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.siteFeatureConfigForm.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      
            

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="siteFeatureConfigForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = SiteFeatureConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SiteFeatureConfig _SiteFeatureConfig = (SiteFeatureConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SiteFeatureConfig.getId() %> </td>

    <td> <%= WebUtil.display(_SiteFeatureConfig.getUserRegisterEnabled()) %></td>
    <td> <%= WebUtil.display(_SiteFeatureConfig.getEcEnabled()) %></td>
    <td> <%= WebUtil.display(_SiteFeatureConfig.getEmailSubsEnabled()) %></td>
    <td> <%= WebUtil.display(_SiteFeatureConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_SiteFeatureConfig.getTimeUpdated()) %></td>


<td>
<form name="siteFeatureConfigForm<%=_SiteFeatureConfig.getId()%>2" method="get" action="/v_site_feature_config_edit2.html" >
    <a href="javascript:document.siteFeatureConfigForm<%=_SiteFeatureConfig.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SiteFeatureConfig.getId() %>">
</form>

</td>
<td>
<a href="/siteFeatureConfigAction.html?del=true&id=<%=_SiteFeatureConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>