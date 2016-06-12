<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

    String _site_urlValue= "";
    String _account_idValue= "";
    String _created_timeValue= "";
    String _site_groupValue= "";
    String _registeredValue= "";
    String _on_saleValue= "";
    String _super_admin_enableValue= "";
    String _site_register_enableValue= "";
    String _site_register_siteValue= "";
    
    if ( ! sessionContext.isSuperAdmin()) return;
%> 

<TABLE class="mytable1">
    <TR>
        <TD>
            <a href="t_site_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE class="mytable1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="siteForm_siteUrl_label" style="font-size: normal normal bold 10px verdana;" >Site Url </div>
    </td> 
    <td> 
	    <div id="siteForm_accountId_label" style="font-size: normal normal bold 10px verdana;" >Account Id </div>
    </td> 
    <td> 
	    <div id="siteForm_registered_label" style="font-size: normal normal bold 10px verdana;" >Registered </div>
    </td> 
    <td> 
	    <div id="siteForm_onSale_label" style="font-size: normal normal bold 10px verdana;" >On Sale </div>
    </td> 
    <td> 
	    <div id="siteForm_superAdminEnable_label" style="font-size: normal normal bold 10px verdana;" >Super Admin Enable </div>
    </td> 
    <td> 
	    <div id="siteForm_siteRegisterEnable_label" style="font-size: normal normal bold 10px verdana;" >Site Register Enable </div>
    </td> 
    <td> 
	    <div id="siteForm_subdomainEnable_label" style="font-size: normal normal bold 10px verdana;" >Subdomain Enable </div>
    </td> 
    <td> 
	    <div id="siteForm_siteRegisterSite_label" style="font-size: normal normal bold 10px verdana;" >Site Register Site </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    List list = SiteDS.getInstance().getAll();
    for(Iterator iter = list.iterator();iter.hasNext();) {
        Site _Site = (Site) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _Site.getId() %> </td>


    <td> 
	<form name="siteFormEditField_SiteUrl_<%=_Site.getId()%>" method="get" action="/siteAction.html" >


		<div id="siteForm_siteUrl_field">
	    <div id="siteForm_siteUrl_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="siteUrl" value="<%=WebUtil.display(_Site.getSiteUrl())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.siteFormEditField_SiteUrl_<%=_Site.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	</form>
    
    
    </td>

    <td> 
	<form name="siteFormEditField_AccountId_<%=_Site.getId()%>" method="get" action="/siteAction.html" >


		<div id="siteForm_accountId_field">
	    <div id="siteForm_accountId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="accountId" value="<%=WebUtil.display(_Site.getAccountId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.siteFormEditField_AccountId_<%=_Site.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	</form>
    
    
    </td>



    <td> 
	<form name="siteFormEditField_Registered_<%=_Site.getId()%>" method="get" action="/siteAction.html" >


		<div id="siteForm_registered_field">
	    <div id="siteForm_registered_dropdown" class="formFieldDropDown" >       
	        <select name="registered">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Site.getRegistered())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Site.getRegistered())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.siteFormEditField_Registered_<%=_Site.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	</form>
    
    
    </td>

    <td> 
	<form name="siteFormEditField_OnSale_<%=_Site.getId()%>" method="get" action="/siteAction.html" >


		<div id="siteForm_onSale_field">
	    <div id="siteForm_onSale_dropdown" class="formFieldDropDown" >       
	        <select name="onSale">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Site.getOnSale())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Site.getOnSale())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.siteFormEditField_OnSale_<%=_Site.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	</form>
    
    
    </td>

    <td> 
	<form name="siteFormEditField_SuperAdminEnable_<%=_Site.getId()%>" method="get" action="/siteAction.html" >


		<div id="siteForm_superAdminEnable_field">
	    <div id="siteForm_superAdminEnable_dropdown" class="formFieldDropDown" >       
	        <select name="superAdminEnable">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Site.getSuperAdminEnable())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Site.getSuperAdminEnable())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.siteFormEditField_SuperAdminEnable_<%=_Site.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	</form>
    
    
    </td>

    <td> 
	<form name="siteFormEditField_SiteRegisterEnable_<%=_Site.getId()%>" method="get" action="/siteAction.html" >


		<div id="siteForm_siteRegisterEnable_field">
	    <div id="siteForm_siteRegisterEnable_dropdown" class="formFieldDropDown" >       
	        <select name="siteRegisterEnable">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Site.getSiteRegisterEnable())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Site.getSiteRegisterEnable())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.siteFormEditField_SiteRegisterEnable_<%=_Site.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	</form>
    
    
    </td>
    <td> 
	<form name="siteFormEditField_SubdomainEnable_<%=_Site.getId()%>" method="get" action="/siteAction.html" >


		<div id="siteForm_subdomainEnable_field">
	    <div id="siteForm_subdomainEnable_dropdown" class="formFieldDropDown" >       
	        <select name="subdomainEnable">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Site.getSubdomainEnable())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Site.getSubdomainEnable())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.siteFormEditField_SubdomainEnable_<%=_Site.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/site_home">
	</form>
    
    
    </td>

    <td> 
	<form name="siteFormEditField_SiteRegisterSite_<%=_Site.getId()%>" method="get" action="/siteAction.html" >


		<div id="siteForm_siteRegisterSite_field">
	    <div id="siteForm_siteRegisterSite_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="siteRegisterSite" value="<%=WebUtil.display(_Site.getSiteRegisterSite())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.siteFormEditField_SiteRegisterSite_<%=_Site.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	</form>
    
    
    </td>

    <td>
    <form name="siteForm<%=_Site.getId()%>" method="get" action="/v_site_edit.html" >
        <a href="javascript:document.siteForm<%=_Site.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Site.getId() %>">
    </form>
    <form name="siteForm<%=_Site.getId()%>2" method="get" action="/v_site_edit2.html" >
        <a href="javascript:document.siteForm<%=_Site.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Site.getId() %>">
    </form>
    </td>

    <td>
    <a href="/siteAction.html?del=true&id=<%=_Site.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>