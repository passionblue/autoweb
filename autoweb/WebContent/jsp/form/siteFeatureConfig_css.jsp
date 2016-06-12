<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    SiteFeatureConfig _SiteFeatureConfig = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_SiteFeatureConfig = SiteFeatureConfigDS.getInstance().getById(id);
		if ( _SiteFeatureConfig == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _SiteFeatureConfig = new SiteFeatureConfig();// SiteFeatureConfigDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_feature_config_home";

    String _user_register_enabledValue= (reqParams.get("userRegisterEnabled")==null?WebUtil.display(_SiteFeatureConfig.getUserRegisterEnabled()):WebUtil.display((String)reqParams.get("userRegisterEnabled")));
    String _ec_enabledValue= (reqParams.get("ecEnabled")==null?WebUtil.display(_SiteFeatureConfig.getEcEnabled()):WebUtil.display((String)reqParams.get("ecEnabled")));
    String _email_subs_enabledValue= (reqParams.get("emailSubsEnabled")==null?WebUtil.display(_SiteFeatureConfig.getEmailSubsEnabled()):WebUtil.display((String)reqParams.get("emailSubsEnabled")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SiteFeatureConfig.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SiteFeatureConfig.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
%> 

<br>
<div id="siteFeatureConfigForm" class="formFrame${classSuffix}">
<div id="siteFeatureConfigFormInnerFrame" class="formInnerFrame${classSuffix}">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="siteFeatureConfigForm_Form" method="POST" action="/siteFeatureConfigAction.html" id="siteFeatureConfigForm_Form">




	<div id="siteFeatureConfigForm_userRegisterEnabled_field" class="formFieldFrame${classSuffix}">
    <div id="siteFeatureConfigForm_userRegisterEnabled_label" class="formLabel${classSuffix}" >User Register Enabled </div>
    <div id="siteFeatureConfigForm_userRegisterEnabled_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select name="userRegisterEnabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _user_register_enabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _user_register_enabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="siteFeatureConfigForm_ecEnabled_field" class="formFieldFrame${classSuffix}">
    <div id="siteFeatureConfigForm_ecEnabled_label" class="formLabel${classSuffix}" >Ec Enabled </div>
    <div id="siteFeatureConfigForm_ecEnabled_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select name="ecEnabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _ec_enabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _ec_enabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="siteFeatureConfigForm_emailSubsEnabled_field" class="formFieldFrame${classSuffix}">
    <div id="siteFeatureConfigForm_emailSubsEnabled_label" class="formLabel${classSuffix}" >Email Subs Enabled </div>
    <div id="siteFeatureConfigForm_emailSubsEnabled_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select name="emailSubsEnabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _email_subs_enabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _email_subs_enabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>








	<div class="submitFrame">

        <div id="siteFeatureConfigForm_submit" class="formSubmit${classSuffix}" >       
            <a id="formSubmit2" href="javascript:document.siteFeatureConfigForm_Form.submit();">Submit</a>
        </div>      

        <div id="siteFeatureConfigForm_submit_cancel" class="formCancel${classSuffix}" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="siteFeatureConfigForm_submit_ext" class="formSubmitExt${classSuffix}" >       
            <a href="#">Ext</a>
        </div>      
	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteFeatureConfig.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">

<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> 
</div> <!-- form -->


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  User Register Enabled </td> 
    <td class="columnTitle">  Ec Enabled </td> 
    <td class="columnTitle">  Email Subs Enabled </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = SiteFeatureConfigDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SiteFeatureConfig _oSiteFeatureConfig = (SiteFeatureConfig) iter.next();
%>

<TR>
    <td> <%= _oSiteFeatureConfig.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oSiteFeatureConfig.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oSiteFeatureConfig.getUserRegisterEnabled()  %> </td>
	<td> <%= _oSiteFeatureConfig.getEcEnabled()  %> </td>
	<td> <%= _oSiteFeatureConfig.getEmailSubsEnabled()  %> </td>
	<td> <%= _oSiteFeatureConfig.getTimeCreated()  %> </td>
	<td> <%= _oSiteFeatureConfig.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/siteFeatureConfigAction.html?del=true&id=<%=_oSiteFeatureConfig.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
</script>