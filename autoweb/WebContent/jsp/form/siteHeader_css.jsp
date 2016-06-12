<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    SiteHeader _SiteHeader = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_SiteHeader = SiteHeaderDS.getInstance().getById(id);
		if ( _SiteHeader == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _SiteHeader = new SiteHeader();// SiteHeaderDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_header_home";

    String _as_isValue= (reqParams.get("asIs")==null?WebUtil.display(_SiteHeader.getAsIs()):WebUtil.display((String)reqParams.get("asIs")));
    String _include_typeValue= (reqParams.get("includeType")==null?WebUtil.display(_SiteHeader.getIncludeType()):WebUtil.display((String)reqParams.get("includeType")));
    String _include_textValue= (reqParams.get("includeText")==null?WebUtil.display(_SiteHeader.getIncludeText()):WebUtil.display((String)reqParams.get("includeText")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SiteHeader.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SiteHeader.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="siteHeaderForm" class="formFrame">
<div id="siteHeaderFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="siteHeaderForm_Form" method="POST" action="/siteHeaderAction.html" id="siteHeaderForm_Form">




	<div id="siteHeaderForm_asIs_field" class="formFieldFrame">
    <div id="siteHeaderForm_asIs_label" class="formLabel" >As Is </div>
    <div id="siteHeaderForm_asIs_dropdown" class="formFieldDropDown" >       
        <select name="asIs">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _as_isValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _as_isValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>



	<div id="siteHeaderForm_includeType_field" class="formFieldFrame">
    <div id="siteHeaderForm_includeType_label" class="formLabel" >Include Type </div>
    <div id="siteHeaderForm_includeType_dropdown" class="formFieldDropDown" >       
        <select class="field" name="includeType" id="includeType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _include_typeValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _include_typeValue)%>>Default</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _include_typeValue)%>>ScriptLink</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _include_typeValue)%>>ScriptText</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _include_typeValue)%>>StyleLink</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _include_typeValue)%>>StyleText</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="siteHeaderForm_includeText_field" class="formFieldFrame">
    <div id="siteHeaderForm_includeText_label" class="formLabel" >Include Text </div>
    <div id="siteHeaderForm_includeText_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="includeText" class="field" NAME="includeText" COLS="50" ROWS="8" ><%=WebUtil.display(_include_textValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>








	<div class="submitFrame">

        <div id="siteHeaderForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.siteHeaderForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="siteHeaderForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="siteHeaderForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="siteHeaderForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="siteHeaderForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteHeader.getId()%>">

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

<TR  >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  As Is </td> 
    <td class="columnTitle">  Include Type </td> 
    <td class="columnTitle">  Include Text </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> DEL </td>
	<td class="columnTitle"> EDIT </td>
</TR>

<%
   	List list = SiteHeaderDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SiteHeader _oSiteHeader = (SiteHeader) iter.next();
%>

<TR id="tableRow<%= _oSiteHeader.getId()%>" >
    <td> <%= _oSiteHeader.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oSiteHeader.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oSiteHeader.getAsIs()  %> </td>
	<td> <%= _oSiteHeader.getIncludeType()  %> </td>
	<td> <%= _oSiteHeader.getIncludeText()  %> </td>
	<td> <%= _oSiteHeader.getTimeCreated()  %> </td>
	<td> <%= _oSiteHeader.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/siteHeaderAction.html?del=true&id=<%=_oSiteHeader.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_oSiteHeader.getId()%> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
	<td>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_site_header_form('<%=_oSiteHeader.getId()%>');">Edit</a>
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
	function submit_cancel_<%=pagestamp%>(){
		location.href='/moveTo.html?dest=<%=cancelPage%>';
	}	
	function submit_extent_<%=pagestamp%>(){
	}
	function edit_site_header_form(target){
		location.href='/v_site_header_form.html?cmd=edit&prv_returnPage=site_header_home&id=' + target;
	}
</script>