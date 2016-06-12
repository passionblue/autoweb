<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _site_urlValue= "";
    String _account_idValue= "";
    String _created_timeValue= "";
    String _site_groupValue= "";
    String _registeredValue= "";
    String _on_saleValue= "";
    String _super_admin_enableValue= "";
    String _site_register_enableValue= "";
    String _subdomain_enableValue= "";
    String _site_register_siteValue= "";
    String _base_site_idValue= "";
    String _subsiteValue= "";
    String _disabledValue= "";

%>

<div id="partitionFormFrame_site_object_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /SiteObject_artition.jsp -->

	<script type="text/javascript">
		function sendForm_site_object_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="siteObjectForm_siteUrl_field" class="formFieldFrame">
    <div id="siteObjectForm_siteUrl_label" class="formLabel" >Site Url </div>
    <div id="siteObjectForm_siteUrl_text" class="formFieldText" >       
        <input id="siteUrl" class="field" type="text" size="70" name="siteUrl" value="<%=WebUtil.display(_site_urlValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="siteObjectForm_accountId_field" class="formFieldFrame">
    <div id="siteObjectForm_accountId_label" class="formLabel" >Account Id </div>
    <div id="siteObjectForm_accountId_text" class="formFieldText" >       
        <input id="accountId" class="field" type="text" size="70" name="accountId" value="<%=WebUtil.display(_account_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div id="siteObjectForm_siteGroup_field" class="formFieldFrame">
    <div id="siteObjectForm_siteGroup_label" class="formLabel" >Site Group </div>
    <div id="siteObjectForm_siteGroup_text" class="formFieldText" >       
        <input id="siteGroup" class="field" type="text" size="70" name="siteGroup" value="<%=WebUtil.display(_site_groupValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="siteObjectForm_registered_field" class="formFieldFrame">
    <div id="siteObjectForm_registered_label" class="formLabel" >Registered </div>
    <div id="siteObjectForm_registered_dropdown" class="formFieldDropDown" >       
        <select name="registered">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _registeredValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _registeredValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="siteObjectForm_onSale_field" class="formFieldFrame">
    <div id="siteObjectForm_onSale_label" class="formLabel" >On Sale </div>
    <div id="siteObjectForm_onSale_dropdown" class="formFieldDropDown" >       
        <select name="onSale">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _on_saleValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _on_saleValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="siteObjectForm_superAdminEnable_field" class="formFieldFrame">
    <div id="siteObjectForm_superAdminEnable_label" class="formLabel" >Super Admin Enable </div>
    <div id="siteObjectForm_superAdminEnable_dropdown" class="formFieldDropDown" >       
        <select name="superAdminEnable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _super_admin_enableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _super_admin_enableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="siteObjectForm_siteRegisterEnable_field" class="formFieldFrame">
    <div id="siteObjectForm_siteRegisterEnable_label" class="formLabel" >Site Register Enable </div>
    <div id="siteObjectForm_siteRegisterEnable_dropdown" class="formFieldDropDown" >       
        <select name="siteRegisterEnable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _site_register_enableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _site_register_enableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="siteObjectForm_subdomainEnable_field" class="formFieldFrame">
    <div id="siteObjectForm_subdomainEnable_label" class="formLabel" >Subdomain Enable </div>
    <div id="siteObjectForm_subdomainEnable_dropdown" class="formFieldDropDown" >       
        <select name="subdomainEnable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _subdomain_enableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _subdomain_enableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="siteObjectForm_siteRegisterSite_field" class="formFieldFrame">
    <div id="siteObjectForm_siteRegisterSite_label" class="formLabel" >Site Register Site </div>
    <div id="siteObjectForm_siteRegisterSite_text" class="formFieldText" >       
        <input id="siteRegisterSite" class="field" type="text" size="70" name="siteRegisterSite" value="<%=WebUtil.display(_site_register_siteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="siteObjectForm_baseSiteId_field" class="formFieldFrame">
    <div id="siteObjectForm_baseSiteId_label" class="formLabel" >Base Site Id </div>
    <div id="siteObjectForm_baseSiteId_text" class="formFieldText" >       
        <input id="baseSiteId" class="field" type="text" size="70" name="baseSiteId" value="<%=WebUtil.display(_base_site_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="siteObjectForm_subsite_field" class="formFieldFrame">
    <div id="siteObjectForm_subsite_label" class="formLabel" >Subsite </div>
    <div id="siteObjectForm_subsite_dropdown" class="formFieldDropDown" >       
        <select name="subsite">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _subsiteValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _subsiteValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="siteObjectForm_disabled_field" class="formFieldFrame">
    <div id="siteObjectForm_disabled_label" class="formLabel" >Disabled </div>
    <div id="siteObjectForm_disabled_text" class="formFieldText" >       
        <input id="disabled" class="field" type="text" size="70" name="disabled" value="<%=WebUtil.display(_disabledValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
		<!--
		<div class="ajaxFormLabel" style="font-weight:bold;">ExtraString</div>
		<INPUT NAME="extString" type="text" size="3" value=""></INPUT><br />

		<div class="ajaxFormLabel" style="font-weight:bold;">Ext Int</div>
		<INPUT NAME="extInt" type="text" size="70" value=""></INPUT><br /> 
		-->
		<INPUT TYPE="HIDDEN" NAME="ajxr" value="getmodalstatus">
		<INPUT TYPE="HIDDEN" NAME="add" value="true">
		<INPUT TYPE="HIDDEN" NAME="wpid" value="<%=_wpId%>">

	</form>

	<span id="ajaxSubmitResult<%= catchString %>"></span> 
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_site_object_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
