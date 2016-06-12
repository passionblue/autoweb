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
    SiteConfigStyle _SiteConfigStyle = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_SiteConfigStyle = SiteConfigStyleDS.getInstance().getById(id);
		if ( _SiteConfigStyle == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _SiteConfigStyle = new SiteConfigStyle();// SiteConfigStyleDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_config_style_home";

    String _theme_idValue= (reqParams.get("themeId")==null?WebUtil.display(_SiteConfigStyle.getThemeId()):WebUtil.display((String)reqParams.get("themeId")));
    String _css_indexValue= (reqParams.get("cssIndex")==null?WebUtil.display(_SiteConfigStyle.getCssIndex()):WebUtil.display((String)reqParams.get("cssIndex")));
    String _css_importValue= (reqParams.get("cssImport")==null?WebUtil.display(_SiteConfigStyle.getCssImport()):WebUtil.display((String)reqParams.get("cssImport")));
    String _layout_indexValue= (reqParams.get("layoutIndex")==null?WebUtil.display(_SiteConfigStyle.getLayoutIndex()):WebUtil.display((String)reqParams.get("layoutIndex")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SiteConfigStyle.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SiteConfigStyle.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="siteConfigStyleForm" class="formFrame">
<div id="siteConfigStyleFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="siteConfigStyleForm_Form" method="POST" action="/siteConfigStyleAction.html" id="siteConfigStyleForm_Form">





	<div id="siteConfigStyleForm_themeId_field" class="formFieldFrame">
    <div id="siteConfigStyleForm_themeId_label" class="formLabel" >Theme Id </div>
    <div id="siteConfigStyleForm_themeId_text" class="formFieldText" >       
        <input id="themeId" class="field" type="text" size="70" name="themeId" value="<%=WebUtil.display(_theme_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="siteConfigStyleForm_cssIndex_field" class="formFieldFrame">
    <div id="siteConfigStyleForm_cssIndex_label" class="formLabel" >Css Index </div>
    <div id="siteConfigStyleForm_cssIndex_text" class="formFieldText" >       
        <input id="cssIndex" class="field" type="text" size="70" name="cssIndex" value="<%=WebUtil.display(_css_indexValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="siteConfigStyleForm_cssImport_field" class="formFieldFrame">
    <div id="siteConfigStyleForm_cssImport_label" class="formLabel" >Css Import </div>
    <div id="siteConfigStyleForm_cssImport_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="cssImport" class="field" NAME="cssImport" COLS="50" ROWS="8" ><%=WebUtil.display(_css_importValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>





	<div id="siteConfigStyleForm_layoutIndex_field" class="formFieldFrame">
    <div id="siteConfigStyleForm_layoutIndex_label" class="formLabel" >Layout Index </div>
    <div id="siteConfigStyleForm_layoutIndex_text" class="formFieldText" >       
        <input id="layoutIndex" class="field" type="text" size="70" name="layoutIndex" value="<%=WebUtil.display(_layout_indexValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div class="submitFrame">

        <div id="siteConfigStyleForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.siteConfigStyleForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="siteConfigStyleForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="siteConfigStyleForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="siteConfigStyleForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="siteConfigStyleForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteConfigStyle.getId()%>">

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
    <td class="columnTitle">  Theme Id </td> 
    <td class="columnTitle">  Css Index </td> 
    <td class="columnTitle">  Css Import </td> 
    <td class="columnTitle">  Layout Index </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> DEL </td>
	<td class="columnTitle"> EDIT </td>
</TR>

<%
   	List list = SiteConfigStyleDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SiteConfigStyle _oSiteConfigStyle = (SiteConfigStyle) iter.next();
%>

<TR id="tableRow<%= _oSiteConfigStyle.getId()%>" >
    <td> <%= _oSiteConfigStyle.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oSiteConfigStyle.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oSiteConfigStyle.getThemeId()  %> </td>
	<td> <%= _oSiteConfigStyle.getCssIndex()  %> </td>
	<td> <%= _oSiteConfigStyle.getCssImport()  %> </td>
	<td> <%= _oSiteConfigStyle.getLayoutIndex()  %> </td>
	<td> <%= _oSiteConfigStyle.getTimeCreated()  %> </td>
	<td> <%= _oSiteConfigStyle.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/siteConfigStyleAction.html?del=true&id=<%=_oSiteConfigStyle.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_oSiteConfigStyle.getId()%> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
	<td>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_site_config_style_form('<%=_oSiteConfigStyle.getId()%>');">Edit</a>
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
	function edit_site_config_style_form(target){
		location.href='/v_site_config_style_form.html?cmd=edit&prv_returnPage=site_config_style_home&id=' + target;
	}
</script>