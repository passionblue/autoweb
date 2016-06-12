<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	List list = new ArrayList();
	SiteDS ds = SiteDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getAll();
	SiteDS siteDS = SiteDS.getInstance();

%> 

<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin) optionQueryStr += "&listAllByAdmin=true";

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	list = PagingUtil.getPagedList(pagingInfo, list);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>

<!-- =================== END PAGING =================== -->

<h3> form displayed by script (request type getscriptform or getmodalform2 </h3>
<script type="text/javascript" src="/siteObjectAction.html?ajxr=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_site_object_form.html?prv_returnPage=site_object_home"> Add New </a> |
            <a href="v_site_object_list.html?"> List Page </a> |
            <a href="v_site_object_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/siteObjectAction.html?ajxr=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form (custom field list)</a> |
			<a href="/siteObjectAction.html?ajxr=getmodalform" 			rel="facebox"> open form</a> |
			<a href="/siteObjectAction.html?ajxr=getlisthtml"  			rel="facebox"> getlisthtml</a> |
			<a href="/siteObjectAction.html?ajxr=getlistjson"  			rel="facebox"> getlistjson</a> |
			<a href="/siteObjectAction.html?ajxr=getjson&ajaxOutArg=first" rel="facebox"> getjson first</a> |
			<a href="/siteObjectAction.html?ajxr=getjson&ajaxOutArg=last" 	rel="facebox"> getjson last</a> |
			<a href="/siteObjectAction.html?ajxr=getlistdata" 				rel="facebox"> getlistdata</a> |

		</TD>        
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="siteObjectForm_siteUrl_label" style="font-size: normal normal bold 10px verdana;" >Site Url </div>
    </td> 
    <td> 
	    <div id="siteObjectForm_accountId_label" style="font-size: normal normal bold 10px verdana;" >Account Id </div>
    </td> 
    <td> 
	    <div id="siteObjectForm_siteGroup_label" style="font-size: normal normal bold 10px verdana;" >Site Group </div>
    </td> 
    <td> 
	    <div id="siteObjectForm_registered_label" style="font-size: normal normal bold 10px verdana;" >Registered </div>
    </td> 
    <td> 
	    <div id="siteObjectForm_onSale_label" style="font-size: normal normal bold 10px verdana;" >On Sale </div>
    </td> 
    <td> 
	    <div id="siteObjectForm_superAdminEnable_label" style="font-size: normal normal bold 10px verdana;" >Super Admin Enable </div>
    </td> 
    <td> 
	    <div id="siteObjectForm_siteRegisterEnable_label" style="font-size: normal normal bold 10px verdana;" >Site Register Enable </div>
    </td> 
    <td> 
	    <div id="siteObjectForm_subdomainEnable_label" style="font-size: normal normal bold 10px verdana;" >Subdomain Enable </div>
    </td> 
    <td> 
	    <div id="siteObjectForm_siteRegisterSite_label" style="font-size: normal normal bold 10px verdana;" >Site Register Site </div>
    </td> 
    <td> 
	    <div id="siteObjectForm_baseSiteId_label" style="font-size: normal normal bold 10px verdana;" >Base Site Id </div>
    </td> 
    <td> 
	    <div id="siteObjectForm_subsite_label" style="font-size: normal normal bold 10px verdana;" >Subsite </div>
    </td> 
    <td> 
	    <div id="siteObjectForm_disabled_label" style="font-size: normal normal bold 10px verdana;" >Disabled </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        Site _Site = (Site) iter.next();
		//TODO 
        fieldString += "\"" +  _Site.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _Site.getId() %> </td>


    <td> 
	<form name="siteObjectFormEditField_SiteUrl_<%=_Site.getId()%>" method="get" action="/siteObjectAction.html" >


		<div id="siteObjectForm_siteUrl_field">
	    <div id="siteObjectForm_siteUrl_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="siteUrl" value="<%=WebUtil.display(_Site.getSiteUrl())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="siteUrl_<%= _Site.getId()%>"><%=_Site.getSiteUrl() %></div>
            <a id="formSubmit" href="javascript:document.siteObjectFormEditField_SiteUrl_<%=_Site.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_Site.getId()%>', 'siteUrl', '<%=_Site.getSiteUrl()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="siteUrl">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="siteUrl">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="siteUrl">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_object_home">
	</form>
    
    
    </td>

    <td> 
	<form name="siteObjectFormEditField_AccountId_<%=_Site.getId()%>" method="get" action="/siteObjectAction.html" >


		<div id="siteObjectForm_accountId_field">
	    <div id="siteObjectForm_accountId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="accountId" value="<%=WebUtil.display(_Site.getAccountId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="accountId_<%= _Site.getId()%>"><%=_Site.getAccountId() %></div>
            <a id="formSubmit" href="javascript:document.siteObjectFormEditField_AccountId_<%=_Site.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_Site.getId()%>', 'accountId', '<%=_Site.getAccountId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="accountId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="accountId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="accountId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_object_home">
	</form>
    
    
    </td>


    <td> 
	<form name="siteObjectFormEditField_SiteGroup_<%=_Site.getId()%>" method="get" action="/siteObjectAction.html" >


		<div id="siteObjectForm_siteGroup_field">
	    <div id="siteObjectForm_siteGroup_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="siteGroup" value="<%=WebUtil.display(_Site.getSiteGroup())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="siteGroup_<%= _Site.getId()%>"><%=_Site.getSiteGroup() %></div>
            <a id="formSubmit" href="javascript:document.siteObjectFormEditField_SiteGroup_<%=_Site.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_Site.getId()%>', 'siteGroup', '<%=_Site.getSiteGroup()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="siteGroup">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="siteGroup">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="siteGroup">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_object_home">
	</form>
    
    
    </td>

    <td> 
	<form name="siteObjectFormEditField_Registered_<%=_Site.getId()%>" method="get" action="/siteObjectAction.html" >


		<div id="siteObjectForm_registered_field">
	    <div id="siteObjectForm_registered_dropdown" class="formFieldDropDown" >       
	        <select name="registered">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Site.getRegistered())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Site.getRegistered())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="registered_<%= _Site.getId()%>"><%=_Site.getRegistered() %></div>
            <a id="formSubmit" href="javascript:document.siteObjectFormEditField_Registered_<%=_Site.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_Site.getId()%>', 'registered', '<%=_Site.getRegistered()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="registered">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="registered">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="registered">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_object_home">
	</form>
    
    
    </td>

    <td> 
	<form name="siteObjectFormEditField_OnSale_<%=_Site.getId()%>" method="get" action="/siteObjectAction.html" >


		<div id="siteObjectForm_onSale_field">
	    <div id="siteObjectForm_onSale_dropdown" class="formFieldDropDown" >       
	        <select name="onSale">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Site.getOnSale())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Site.getOnSale())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="onSale_<%= _Site.getId()%>"><%=_Site.getOnSale() %></div>
            <a id="formSubmit" href="javascript:document.siteObjectFormEditField_OnSale_<%=_Site.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_Site.getId()%>', 'onSale', '<%=_Site.getOnSale()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="onSale">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="onSale">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="onSale">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_object_home">
	</form>
    
    
    </td>

    <td> 
	<form name="siteObjectFormEditField_SuperAdminEnable_<%=_Site.getId()%>" method="get" action="/siteObjectAction.html" >


		<div id="siteObjectForm_superAdminEnable_field">
	    <div id="siteObjectForm_superAdminEnable_dropdown" class="formFieldDropDown" >       
	        <select name="superAdminEnable">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Site.getSuperAdminEnable())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Site.getSuperAdminEnable())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="superAdminEnable_<%= _Site.getId()%>"><%=_Site.getSuperAdminEnable() %></div>
            <a id="formSubmit" href="javascript:document.siteObjectFormEditField_SuperAdminEnable_<%=_Site.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_Site.getId()%>', 'superAdminEnable', '<%=_Site.getSuperAdminEnable()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="superAdminEnable">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="superAdminEnable">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="superAdminEnable">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_object_home">
	</form>
    
    
    </td>

    <td> 
	<form name="siteObjectFormEditField_SiteRegisterEnable_<%=_Site.getId()%>" method="get" action="/siteObjectAction.html" >


		<div id="siteObjectForm_siteRegisterEnable_field">
	    <div id="siteObjectForm_siteRegisterEnable_dropdown" class="formFieldDropDown" >       
	        <select name="siteRegisterEnable">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Site.getSiteRegisterEnable())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Site.getSiteRegisterEnable())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="siteRegisterEnable_<%= _Site.getId()%>"><%=_Site.getSiteRegisterEnable() %></div>
            <a id="formSubmit" href="javascript:document.siteObjectFormEditField_SiteRegisterEnable_<%=_Site.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_Site.getId()%>', 'siteRegisterEnable', '<%=_Site.getSiteRegisterEnable()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="siteRegisterEnable">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="siteRegisterEnable">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="siteRegisterEnable">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_object_home">
	</form>
    
    
    </td>

    <td> 
	<form name="siteObjectFormEditField_SubdomainEnable_<%=_Site.getId()%>" method="get" action="/siteObjectAction.html" >


		<div id="siteObjectForm_subdomainEnable_field">
	    <div id="siteObjectForm_subdomainEnable_dropdown" class="formFieldDropDown" >       
	        <select name="subdomainEnable">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Site.getSubdomainEnable())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Site.getSubdomainEnable())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="subdomainEnable_<%= _Site.getId()%>"><%=_Site.getSubdomainEnable() %></div>
            <a id="formSubmit" href="javascript:document.siteObjectFormEditField_SubdomainEnable_<%=_Site.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_Site.getId()%>', 'subdomainEnable', '<%=_Site.getSubdomainEnable()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="subdomainEnable">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="subdomainEnable">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="subdomainEnable">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_object_home">
	</form>
    
    
    </td>

    <td> 
	<form name="siteObjectFormEditField_SiteRegisterSite_<%=_Site.getId()%>" method="get" action="/siteObjectAction.html" >


		<div id="siteObjectForm_siteRegisterSite_field">
	    <div id="siteObjectForm_siteRegisterSite_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="siteRegisterSite" value="<%=WebUtil.display(_Site.getSiteRegisterSite())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="siteRegisterSite_<%= _Site.getId()%>"><%=_Site.getSiteRegisterSite() %></div>
            <a id="formSubmit" href="javascript:document.siteObjectFormEditField_SiteRegisterSite_<%=_Site.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_Site.getId()%>', 'siteRegisterSite', '<%=_Site.getSiteRegisterSite()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="siteRegisterSite">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="siteRegisterSite">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="siteRegisterSite">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_object_home">
	</form>
    
    
    </td>

    <td> 
	<form name="siteObjectFormEditField_BaseSiteId_<%=_Site.getId()%>" method="get" action="/siteObjectAction.html" >


		<div id="siteObjectForm_baseSiteId_field">
	    <div id="siteObjectForm_baseSiteId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="baseSiteId" value="<%=WebUtil.display(_Site.getBaseSiteId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="baseSiteId_<%= _Site.getId()%>"><%=_Site.getBaseSiteId() %></div>
            <a id="formSubmit" href="javascript:document.siteObjectFormEditField_BaseSiteId_<%=_Site.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_Site.getId()%>', 'baseSiteId', '<%=_Site.getBaseSiteId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="baseSiteId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="baseSiteId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="baseSiteId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_object_home">
	</form>
    
    
    </td>

    <td> 
	<form name="siteObjectFormEditField_Subsite_<%=_Site.getId()%>" method="get" action="/siteObjectAction.html" >


		<div id="siteObjectForm_subsite_field">
	    <div id="siteObjectForm_subsite_dropdown" class="formFieldDropDown" >       
	        <select name="subsite">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Site.getSubsite())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Site.getSubsite())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="subsite_<%= _Site.getId()%>"><%=_Site.getSubsite() %></div>
            <a id="formSubmit" href="javascript:document.siteObjectFormEditField_Subsite_<%=_Site.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_Site.getId()%>', 'subsite', '<%=_Site.getSubsite()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="subsite">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="subsite">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="subsite">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_object_home">
	</form>
    
    
    </td>

    <td> 
	<form name="siteObjectFormEditField_Disabled_<%=_Site.getId()%>" method="get" action="/siteObjectAction.html" >


		<div id="siteObjectForm_disabled_field">
	    <div id="siteObjectForm_disabled_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="disabled" value="<%=WebUtil.display(_Site.getDisabled())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="disabled_<%= _Site.getId()%>"><%=_Site.getDisabled() %></div>
            <a id="formSubmit" href="javascript:document.siteObjectFormEditField_Disabled_<%=_Site.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_Site.getId()%>', 'disabled', '<%=_Site.getDisabled()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="disabled">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="disabled">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="disabled">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_object_home">
	</form>
    
    
    </td>

    <td>
    <form name="siteObjectForm<%=_Site.getId()%>2" method="get" action="/v_site_object_form.html" >
        <a href="javascript:document.siteObjectForm<%=_Site.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Site.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="site_object_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_site_object_form('<%=_Site.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/siteObjectAction.html?del=true&id=<%=_Site.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_site_object('<%=_Site.getId()%>');">DeleteWConfirm</a>
    </td>
</TR>

<%
    }
	if ( fieldString != null && fieldString.length() > 0 )
	fieldString = fieldString.substring(0, fieldString.length()-1);

%>
</TABLE>

<a id="partition_test_ajax" href="#" rel="extInt">	Partition Test </a><br>
<a id="partition_test_ajax2" href="#" rel="extInt">	Partition Test2 </a><br>

<div id="partitionTestResult" style="border:1px solid #666666; "> Partition test to be loaded here </div> <br>


<script type="text/javascript">




	function edit_site_object_form(target){
		location.href='/v_site_object_form.html?cmd=edit&prv_returnPage=site_object_home&id=' + target;
	}

	function confirm_site_object(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_site_object(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/siteObjectAction.html?del=true&id="+target;
				}
			}
		});
	}
	// 20120226 
	// On the list, added a little stupid fuction to prompt the change of values
	function update_cleaner_pickup_field_dialog(targetId, targetField, targetValue){
		var txt = 'Change field value for'+targetField +':<br/> <input type="text" id="alertName" name="myname" value="'+ targetValue +'" />';
		$ .prompt(txt,{ 
			buttons:{Submit:true, Cancel:false},
			callback: function(v,m,f){
				if (v){
					if (f.myname == "") {
						alert("Enter");
						return false;
					} else {
						location.href="/siteObjectAction.html?editfield=true&returnPage=cleaner_pickup_home&id="+targetId+"&"+targetField +"=" +f.myname;
					}
				}
				return true;
			}
		});
	}

	// Functions to update field in the list via ajax
	// This is primitive but field "update_field_by_ajax" should be right next level of form.
	// Because it uses parent to access to id and field name 20120226
	$(document).ready(function(){

		$("a#update_field_by_ajax").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			$ .ajax({
			   type: "GET",
		   		url: "/siteObjectAction.html?editfield=true&ajxr=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		     		$("#" + _fieldName+"_"+_id).text(msg);
		   		}
	 		});
			
			return false;
		});

		$("a#update_field_by_ajax_open_reply").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			 $(this).css("background-color", "red");

	    	$ .ajax({
			   type: "GET",
		   		url: "/siteObjectAction.html?editfield=true&ajaxRequest=true&ajaxOut=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
				beforeSend: function(jqXHR, settings){
					//alert("sending");
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		$("#" + _fieldName+"_"+_id).text(msg);
		    		$ .prompt("Value updated Success fully",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		},
		   		error: function(msg){
		    		$ .prompt("Request error",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		}
	 		});
			
			return false;
		});
		
		$("a#update_field_by_ajax_get2field").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			 $(this).css("background-color", "red");

	    	$ .ajax({
			   type: "GET",
		   		url: "/siteObjectAction.html?editfield=true&ajxr=get2field&id="+_id+"&"+_fieldName+"="+ _val,
				beforeSend: function(jqXHR, settings){
					//alert("sending");
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		$("#" + _fieldName+"_"+_id).text(msg); // Update the field
		     		var displayMsg = "return from server-> " + msg + "<br>" + "result of getSuccessData()-> "+ getSuccessData(msg);
		    		$ .prompt(displayMsg,{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		},
		   		error: function(msg){
		    		$ .prompt("Request error",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		}
	 		});
			
			return false;
		});


		$("a#partition_test_ajax").click(function () {
			
			$ .ajax({
			   type: "GET",
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-site-object",
		  		success: function(msg){ 
		     		alert(msg);
		     		$("#partitionTestResult").html(msg);
		   		}
	 		});
			
			return false;
		});		

		// Display loader 
		$("a#partition_test_ajax2").click(function () {
			
			$ .ajax({
			   type: "GET",
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-site-object",
		   		beforeSend: function(jqXHR, settings){
		   			
					// 1 just display loader img on the target div 		   			
		   			// $("#partitionTestResult").html("<img src=\"/images/loader/arrows32.gif\"/>");

					
					//2 
					$("#partitionTestResult").css("height","100px").html("<img src=\"/images/loader/arrows32.gif\"/>");
					
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		alert(msg);
		     		$("#partitionTestResult").remove("height").html(msg);
		   		}
	 		});
			return false;
 		});
	});
</script>


<script type="text/javascript" charset="utf-8">
// This Javascript is granted to the public domain.

// This is the javascript array holding the function list
// The PrintJavascriptArray ASP function can be used to print this array.
//var functionlist = Array("abs",
//"acos","acosh","addcslashes","addslashes","aggregate","stream_context_create",
//"swf_startbutton","swfmovie.remove","swfmovie.save","swftext.getwidth","swftext.moveto","sybase_fetch_field","sybase_fetch_object","tanh","tempnam",
//"textdomain","time","udm_errno","udm_error",
//"unset","urldecode","urlencode","user_error","usleep","usort","utf8_decode",
//"utf8_encode","var_dump","vpopmail_error","vpopmail_passwd","vpopmail_set_user_quota","vprintf","vsprintf","xml_parser_create","xml_parser_create_ns",
//"xml_parser_free","xmlrpc_server_add_introspection_data","xmlrpc_server_call_method","xmlrpc_server_create","xmlrpc_server_destroy","xmlrpc_server_register_introspection_callback","yaz_connect","yaz_database","yaz_element",
//"yaz_errno","yp_order","zend_logo_guid","zend_version","zip_close","zip_open","zip_read");



var functionlist = Array(<%=fieldString%>);



// This is the function that refreshes the list after a keypress.
// The maximum number to show can be limited to improve performance with
// huge lists (1000s of entries).
// The function clears the list, and then does a linear search through the
// globally defined array and adds the matches back to the list.
function handleKeyUp(maxNumToShow)
{
    var selectObj, textObj, functionListLength;
    var i, searchPattern, numShown;

	if (document.getElementById('auto-complete-input') == null){
		alert("Client side Error occurred. Please try again.");
		return;
	}
    
    // Set references to the form elements
    selectObj = document.getElementById('auto-complete-input').functionselect;
    textObj = document.getElementById('auto-complete-input').functioninput;

    // Remember the function list length for loop speedup
    functionListLength = functionlist.length;

    // Set the search pattern depending
    if(document.getElementById('auto-complete-input').functionradio[0].checked == true)
    {
        searchPattern = "^"+textObj.value;
    }
    else
    {
        searchPattern = textObj.value;
    }

    // Create a regulare expression
    re = new RegExp(searchPattern,"gi");
    // Clear the options list
    selectObj.length = 0;

    // Loop through the array and re-add matching options
    numShown = 0;
    for(i = 0; i < functionListLength; i++)
    {
        if(functionlist[i].search(re) != -1)
        {
            selectObj[numShown] = new Option(functionlist[i],"");
            numShown++;
        }
        // Stop when the number to show is reached
        if(numShown == maxNumToShow)
        {
            break;
        }
    }
    // When options list whittled to one, select that entry
    if(selectObj.length == 1)
    {
        selectObj.options[0].selected = true;
    }
}

// this function gets the selected value and loads the appropriate
// php reference page in the display frame
// it can be modified to perform whatever action is needed, or nothing
function handleSelectClick()
{
    selectObj = document.getElementById('auto-complete-input').functionselect;
    textObj = document.getElementById('auto-complete-input').functioninput;

    selectedValue = selectObj.options[selectObj.selectedIndex].text;

    selectedValue = selectedValue.replace(/_/g, '-') ;
    document.location.href = 
	"http://www.php.net/manual/en/function."+selectedValue+".php";

}
function encode_utf8( string )
{
	string = string.replace(/\r\n/g,"\n");
	var utftext = "";

	for (var n = 0; n < string.length; n++) {

		var c = string.charCodeAt(n);

		if (c < 128) {
			utftext += String.fromCharCode(c);
		}
		else if((c > 127) && (c < 2048)) {
			utftext += String.fromCharCode((c >> 6) | 192);
			utftext += String.fromCharCode((c & 63) | 128);
		}
		else {
			utftext += String.fromCharCode((c >> 12) | 224);
			utftext += String.fromCharCode(((c >> 6) & 63) | 128);
			utftext += String.fromCharCode((c & 63) | 128);
		}

	}

	return utftext;
}

function decode_utf8( s )
{
  return decodeURIComponent( escape( s ) );
}
</script>

<table style="margin:auto;">
<tr>
	<td valign="top">
		<b>Search For Function Name</b>
		
		<form onSubmit="handleSelectClick();return false;" action="#" id='auto-complete-input'>

			<input type="radio" name="functionradio" checked>Starting With<br>
			<input type="radio" name="functionradio">Containing<br>
			<input  onKeyUp="handleKeyUp(20);" type="text" name="functioninput" VALUE="" style="font-size:10pt;width:34ex;"><br>
		
			<select onClick="handleSelectClick();" name="functionselect" size="20" style="font-size:10pt;width:34ex;">
			</select>
			<br>
			<input type="button" onClick="handleKeyUp(9999999);" value="Load All Matches">
		</form>
	</td>
</tr>

<tr>
	<td valign="top">
		<select>
		  <option>Volvo</option>
		  <option>Saab</option>
		  <option>Mercedes</option>
		  <option>Audi</option>
		</select>
	</td>
</tr>

</table>