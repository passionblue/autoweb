<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    ResourceInclusion _ResourceInclusion = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ResourceInclusion = ResourceInclusionDS.getInstance().getById(id);
		if ( _ResourceInclusion == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ResourceInclusion = new ResourceInclusion();// ResourceInclusionDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "resource_inclusion_home";

    String _nameValue= (reqParams.get("name")==null?WebUtil.display(_ResourceInclusion.getName()):WebUtil.display((String)reqParams.get("name")));
    String _includeValue= (reqParams.get("include")==null?WebUtil.display(_ResourceInclusion.getInclude()):WebUtil.display((String)reqParams.get("include")));
    String _resource_typeValue= (reqParams.get("resourceType")==null?WebUtil.display(_ResourceInclusion.getResourceType()):WebUtil.display((String)reqParams.get("resourceType")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="resourceInclusionForm" class="formFrame">
<div id="resourceInclusionFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="resourceInclusionForm_Form" method="POST" action="/resourceInclusionAction.html" id="resourceInclusionForm_Form">





	<div id="resourceInclusionForm_name_field" class="formFieldFrame">
    <div id="resourceInclusionForm_name_label" class="formLabel" >Name </div>
    <div id="resourceInclusionForm_name_text" class="formFieldText" >       
        <input id="name" class="field" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="resourceInclusionForm_include_field" class="formFieldFrame">
    <div id="resourceInclusionForm_include_label" class="formLabel" >Include </div>
    <div id="resourceInclusionForm_include_dropdown" class="formFieldDropDown" >       
        <select name="include">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _includeValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _includeValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>



  	<div id="resourceInclusionForm_resourceType_field" class="formFieldFrame">
    <div id="resourceInclusionForm_resourceType_label" class="formLabel" >Resource Type </div>
    <div id="resourceInclusionForm_resourceType_dropdown" class="formFieldDropDown" >       
        <select class="field" name="resourceType" id="resourceType">
        <option value="" >- Please Select -</option>
<%
	List dropMenusResourceType = DropMenuUtil.getDropMenus("ResourceInclusionType");
	for(Iterator iterItems = dropMenusResourceType.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _resource_typeValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="resourceInclusionForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.resourceInclusionForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="resourceInclusionForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="resourceInclusionForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="resourceInclusionForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

    <div id="resourceInclusionForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ResourceInclusion.getId()%>">

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


<%
	List list_ResourceInclusion = new ArrayList();
	ResourceInclusionDS ds_ResourceInclusion = ResourceInclusionDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_ResourceInclusion = ds_ResourceInclusion.getAll();
	else		
    	list_ResourceInclusion = ds_ResourceInclusion.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_ResourceInclusion, numDisplayInPage, listPage);

	list_ResourceInclusion = PagingUtil.getPagedList(pagingInfo, list_ResourceInclusion);
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

    <td class="columnTitle">  Name </td> 
    <td class="columnTitle">  Include </td> 
    <td class="columnTitle">  Resource Type </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_ResourceInclusion.iterator();iter.hasNext();) {
        ResourceInclusion o_ResourceInclusion = (ResourceInclusion) iter.next();
%>

<TR id="tableRow<%= o_ResourceInclusion.getId()%>">
    <td> <%= o_ResourceInclusion.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_ResourceInclusion.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_ResourceInclusion.getName()  %> </td>
	<td> <%= o_ResourceInclusion.getInclude()  %> </td>
	<td> <%= o_ResourceInclusion.getResourceType()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_resource_inclusion_form('<%=o_ResourceInclusion.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/resourceInclusionAction.html?del=true&id=<%=o_ResourceInclusion.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_ResourceInclusion.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_resource_inclusion_form('<%=o_ResourceInclusion.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_resource_inclusion_form(target){
		location.href='/v_resource_inclusion_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target;
	}
	function delete_resource_inclusion_form(target){
		javascript:sendFormAjaxSimple('/resourceInclusionAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,target);
	}

</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/resourceInclusionAction.html?ajxr=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>
