<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

	// This is ugrly not matured change. Just added to load previously entered values and put back to the fields. 
	boolean isEdit = false;	
    Map reqParams = (Map) request.getAttribute("k_previous_request_params");
    if ( reqParams == null) {
        reqParams = (Map) request.getAttribute("k_reserved_params");
    } else {
        isEdit = true;
    }

	String command = request.getParameter("cmd");

    String idStr  = "0";
    Site _Site = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_Site = SiteDS.getInstance().getById(id);
		if ( _Site == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _Site = new Site();// SiteDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _Site == null) _Site = new Site();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_object_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _site_urlValue= (reqParams.get("siteUrl")==null?WebUtil.display(_Site.getSiteUrl()):WebUtil.display((String)reqParams.get("siteUrl")));
    String _account_idValue= (reqParams.get("accountId")==null?WebUtil.display(_Site.getAccountId()):WebUtil.display((String)reqParams.get("accountId")));
    String _created_timeValue= (reqParams.get("createdTime")==null?WebUtil.display(_Site.getCreatedTime()):WebUtil.display((String)reqParams.get("createdTime")));
    String _site_groupValue= (reqParams.get("siteGroup")==null?WebUtil.display(_Site.getSiteGroup()):WebUtil.display((String)reqParams.get("siteGroup")));
    String _registeredValue= (reqParams.get("registered")==null?WebUtil.display(_Site.getRegistered()):WebUtil.display((String)reqParams.get("registered")));
    String _on_saleValue= (reqParams.get("onSale")==null?WebUtil.display(_Site.getOnSale()):WebUtil.display((String)reqParams.get("onSale")));
    String _super_admin_enableValue= (reqParams.get("superAdminEnable")==null?WebUtil.display(_Site.getSuperAdminEnable()):WebUtil.display((String)reqParams.get("superAdminEnable")));
    String _site_register_enableValue= (reqParams.get("siteRegisterEnable")==null?WebUtil.display(_Site.getSiteRegisterEnable()):WebUtil.display((String)reqParams.get("siteRegisterEnable")));
    String _subdomain_enableValue= (reqParams.get("subdomainEnable")==null?WebUtil.display(_Site.getSubdomainEnable()):WebUtil.display((String)reqParams.get("subdomainEnable")));
    String _site_register_siteValue= (reqParams.get("siteRegisterSite")==null?WebUtil.display(_Site.getSiteRegisterSite()):WebUtil.display((String)reqParams.get("siteRegisterSite")));
    String _base_site_idValue= (reqParams.get("baseSiteId")==null?WebUtil.display(_Site.getBaseSiteId()):WebUtil.display((String)reqParams.get("baseSiteId")));
    String _subsiteValue= (reqParams.get("subsite")==null?WebUtil.display(_Site.getSubsite()):WebUtil.display((String)reqParams.get("subsite")));
    String _disabledValue= (reqParams.get("disabled")==null?WebUtil.display(_Site.getDisabled()):WebUtil.display((String)reqParams.get("disabled")));

    String pagestamp = "site_object_" + System.nanoTime();
%> 

<br>
<div id="siteObjectForm" class="formFrame">
<div id="siteObjectFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="siteObjectForm_Form" method="POST" action="/siteObjectAction.html" id="siteObjectForm_Form">





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

<div class="submitFrame">

    <div id="siteObjectForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.siteObjectForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="siteObjectForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="siteObjectForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="siteObjectForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="siteObjectForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="siteObjectForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Site.getId()%>">

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
<INPUT TYPE="HIDDEN" NAME="fromto" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="prv_backPage" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="_reqhid" value="<%= WebUtil.display(SessionWrapper.wrapIt(request, site.getId()).getRequestHandleId()) %>">
</form>
</div> 				 
</div> <!-- form -->

<br/>
<a href="/v_site_object_home.html">home</a> | <a href="/v_site_object_home.html">home</a> | <a href="/v_site_object_home.html">home</a>
<br/>
<br/>



<%
	List list_Site = new ArrayList();
	SiteDS ds_Site = SiteDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_Site = ds_Site.getAll();
	else		
    	list_Site = ds_Site.getAll();

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_Site, numDisplayInPage, listPage);

	list_Site = PagingUtil.getPagedList(pagingInfo, list_Site);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>
<!-- =================== END PAGING =================== -->

 
<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
//	if (showListAllByAdmin) {
	if (true) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>

    <td class="columnTitle">  Site Url </td> 
    <td class="columnTitle">  Account Id </td> 
    <td class="columnTitle">  Created Time </td> 
    <td class="columnTitle">  Site Group </td> 
    <td class="columnTitle">  Registered </td> 
    <td class="columnTitle">  On Sale </td> 
    <td class="columnTitle">  Super Admin Enable </td> 
    <td class="columnTitle">  Site Register Enable </td> 
    <td class="columnTitle">  Subdomain Enable </td> 
    <td class="columnTitle">  Site Register Site </td> 
    <td class="columnTitle">  Base Site Id </td> 
    <td class="columnTitle">  Subsite </td> 
    <td class="columnTitle">  Disabled </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_Site.iterator();iter.hasNext();) {
        Site o_Site = (Site) iter.next();
%>

<TR id="tableRow<%= o_Site.getId()%>">
    <td> <%= o_Site.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_Site.getId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_Site.getSiteUrl()  %> </td>
	<td> <%= o_Site.getAccountId()  %> </td>
	<td> <%= o_Site.getCreatedTime()  %> </td>
	<td> <%= o_Site.getSiteGroup()  %> </td>
	<td> <%= o_Site.getRegistered()  %> </td>
	<td> <%= o_Site.getOnSale()  %> </td>
	<td> <%= o_Site.getSuperAdminEnable()  %> </td>
	<td> <%= o_Site.getSiteRegisterEnable()  %> </td>
	<td> <%= o_Site.getSubdomainEnable()  %> </td>
	<td> <%= o_Site.getSiteRegisterSite()  %> </td>
	<td> <%= o_Site.getBaseSiteId()  %> </td>
	<td> <%= o_Site.getSubsite()  %> </td>
	<td> <%= o_Site.getDisabled()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_site_object_form('<%=o_Site.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/siteObjectAction.html?del=true&id=<%=o_Site.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_Site.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_site_object_form('<%=o_Site.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
	</td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
		if ($("#tableRow" + msg)) {
			$("#tableRow" + msg).fadeOut(1000);
		}
	}
	function open_edit_site_object_form(target){
		location.href='/v_site_object_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_site_object_form(target){
		javascript:sendFormAjaxSimple('/siteObjectAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/siteObjectAction.html?ajxr=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 300000);

//		setTimeout(function(){
//		}, 10000);
	});

	function submit_cancel_<%=pagestamp%>(){
		//alert("submit_cancel_");		
		//location.href='/moveTo.html?dest=<%=cancelPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=cancel<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	
	function submit_back_<%=pagestamp%>(){
		//alert("submit_back_");		
		//location.href='/moveTo.html?dest=<%=backPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_extent_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=extent<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

</script>
