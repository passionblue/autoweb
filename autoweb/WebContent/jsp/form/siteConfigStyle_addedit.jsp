<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

    Map reqParams = (Map) request.getAttribute("k_reserved_params");

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
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_config_style_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _theme_idValue= (reqParams.get("themeId")==null?WebUtil.display(_SiteConfigStyle.getThemeId()):WebUtil.display((String)reqParams.get("themeId")));
    String _css_indexValue= (reqParams.get("cssIndex")==null?WebUtil.display(_SiteConfigStyle.getCssIndex()):WebUtil.display((String)reqParams.get("cssIndex")));
    String _css_importValue= (reqParams.get("cssImport")==null?WebUtil.display(_SiteConfigStyle.getCssImport()):WebUtil.display((String)reqParams.get("cssImport")));
    String _layout_indexValue= (reqParams.get("layoutIndex")==null?WebUtil.display(_SiteConfigStyle.getLayoutIndex()):WebUtil.display((String)reqParams.get("layoutIndex")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SiteConfigStyle.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SiteConfigStyle.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String pagestamp = "site_config_style_" + System.nanoTime();
%> 

<br>
<div id="siteConfigStyleForm" class="formFrame">
<div id="siteConfigStyleFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="siteConfigStyleForm_Form" method="POST" action="/siteConfigStyleAction.html" id="siteConfigStyleForm_Form">



  	<div id="siteConfigStyleForm_themeId_field" class="formFieldFrame">
    <div id="siteConfigStyleForm_themeId_label" class="formLabel" >Theme Id </div>
    <div id="siteConfigStyleForm_themeId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="themeId" id="themeId">
        <option value="" >- Please Select -</option>
<%
	List _listThemeAggregator_themeId = ThemeAggregatorDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listThemeAggregator_themeId.iterator(); iter.hasNext();){
		ThemeAggregator _obj = (ThemeAggregator) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_theme_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getThemeName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
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
        <input id="cssImport" class="field" type="text" size="70" name="cssImport" value="<%=WebUtil.display(_css_importValue)%>"/> <span></span>
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

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="siteConfigStyleForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="siteConfigStyleForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


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
<INPUT TYPE="HIDDEN" NAME="fromto" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="prv_backPage" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="_reqhid" value="<%= WebUtil.display(SessionWrapper.wrapIt(request, site.getId()).getRequestHandleId()) %>">
</form>
</div> 				 
</div> <!-- form -->

<br/>
<a href="/v_site_config_style_home.html">home</a> | <a href="/v_site_config_style_home.html">home</a> | <a href="/v_site_config_style_home.html">home</a>
<br/>
<br/>



<%
	List list_SiteConfigStyle = new ArrayList();
	SiteConfigStyleDS ds_SiteConfigStyle = SiteConfigStyleDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_SiteConfigStyle = ds_SiteConfigStyle.getAll();
	else		
    	list_SiteConfigStyle = ds_SiteConfigStyle.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_SiteConfigStyle, numDisplayInPage, listPage);

	list_SiteConfigStyle = PagingUtil.getPagedList(pagingInfo, list_SiteConfigStyle);
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

    <td class="columnTitle">  Theme Id </td> 
    <td class="columnTitle">  Css Index </td> 
    <td class="columnTitle">  Css Import </td> 
    <td class="columnTitle">  Layout Index </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_SiteConfigStyle.iterator();iter.hasNext();) {
        SiteConfigStyle o_SiteConfigStyle = (SiteConfigStyle) iter.next();
%>

<TR id="tableRow<%= o_SiteConfigStyle.getId()%>">
    <td> <%= o_SiteConfigStyle.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_SiteConfigStyle.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_SiteConfigStyle.getThemeId()  %> </td>
	<td> <%= o_SiteConfigStyle.getCssIndex()  %> </td>
	<td> <%= o_SiteConfigStyle.getCssImport()  %> </td>
	<td> <%= o_SiteConfigStyle.getLayoutIndex()  %> </td>
	<td> <%= o_SiteConfigStyle.getTimeCreated()  %> </td>
	<td> <%= o_SiteConfigStyle.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_site_config_style_form('<%=o_SiteConfigStyle.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/siteConfigStyleAction.html?del=true&id=<%=o_SiteConfigStyle.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_SiteConfigStyle.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_site_config_style_form('<%=o_SiteConfigStyle.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_site_config_style_form(target){
		location.href='/v_site_config_style_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_site_config_style_form(target){
		javascript:sendFormAjaxSimple('/siteConfigStyleAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/siteConfigStyleAction.html?ajxr=hb",
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
