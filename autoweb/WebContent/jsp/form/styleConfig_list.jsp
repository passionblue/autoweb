<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
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
	StyleConfigDS ds = StyleConfigDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_style_config_form.html?prv_returnPage=style_config_home"> Add New 2</a> <br>
<br/>
<a href="/v_style_config_home.html">home</a> | <a href="/v_style_config_home.html">home</a> | <a href="/v_style_config_home.html">home</a>
<br/>
<br/>



<%
	List list_StyleConfig = new ArrayList();
	StyleConfigDS ds_StyleConfig = StyleConfigDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_StyleConfig = ds_StyleConfig.getAll();
	else		
    	list_StyleConfig = ds_StyleConfig.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_StyleConfig, numDisplayInPage, listPage);

	list_StyleConfig = PagingUtil.getPagedList(pagingInfo, list_StyleConfig);
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

    <td class="columnTitle">  Style Key </td> 
    <td class="columnTitle">  Style Use </td> 
    <td class="columnTitle">  Is Global </td> 
    <td class="columnTitle">  Id Class </td> 
    <td class="columnTitle">  Is Id </td> 
    <td class="columnTitle">  Color </td> 
    <td class="columnTitle">  Bg Color </td> 
    <td class="columnTitle">  Bg Image </td> 
    <td class="columnTitle">  Bg Repeat </td> 
    <td class="columnTitle">  Bg Attach </td> 
    <td class="columnTitle">  Bg Position </td> 
    <td class="columnTitle">  Text Align </td> 
    <td class="columnTitle">  Font Family </td> 
    <td class="columnTitle">  Font Size </td> 
    <td class="columnTitle">  Font Style </td> 
    <td class="columnTitle">  Font Variant </td> 
    <td class="columnTitle">  Font Weight </td> 
    <td class="columnTitle">  Border Direction </td> 
    <td class="columnTitle">  Border Width </td> 
    <td class="columnTitle">  Border Style </td> 
    <td class="columnTitle">  Border Color </td> 
    <td class="columnTitle">  Margin </td> 
    <td class="columnTitle">  Padding </td> 
    <td class="columnTitle">  List Style Type </td> 
    <td class="columnTitle">  List Style Position </td> 
    <td class="columnTitle">  List Style Image </td> 
    <td class="columnTitle">  Floating </td> 
    <td class="columnTitle">  Extra Style Str </td> 
    <td class="columnTitle">  Item Style Str </td> 
    <td class="columnTitle">  Link </td> 
    <td class="columnTitle">  Link Hover </td> 
    <td class="columnTitle">  Link Active </td> 
    <td class="columnTitle">  Height </td> 
    <td class="columnTitle">  Width </td> 
    <td class="columnTitle">  Is Table </td> 
    <td class="columnTitle">  Border Collapse </td> 
    <td class="columnTitle">  Border Spacing </td> 
    <td class="columnTitle">  Tr Style Ids </td> 
    <td class="columnTitle">  Td Style Ids </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_StyleConfig.iterator();iter.hasNext();) {
        StyleConfig o_StyleConfig = (StyleConfig) iter.next();
%>

<TR id="tableRow<%= o_StyleConfig.getId()%>">
    <td> <%= o_StyleConfig.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_StyleConfig.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_StyleConfig.getStyleKey()  %> </td>
	<td> <%= o_StyleConfig.getStyleUse()  %> </td>
	<td> <%= o_StyleConfig.getIsGlobal()  %> </td>
	<td> <%= o_StyleConfig.getIdClass()  %> </td>
	<td> <%= o_StyleConfig.getIsId()  %> </td>
	<td> <%= o_StyleConfig.getColor()  %> </td>
	<td> <%= o_StyleConfig.getBgColor()  %> </td>
	<td> <%= o_StyleConfig.getBgImage()  %> </td>
	<td> <%= o_StyleConfig.getBgRepeat()  %> </td>
	<td> <%= o_StyleConfig.getBgAttach()  %> </td>
	<td> <%= o_StyleConfig.getBgPosition()  %> </td>
	<td> <%= o_StyleConfig.getTextAlign()  %> </td>
	<td> <%= o_StyleConfig.getFontFamily()  %> </td>
	<td> <%= o_StyleConfig.getFontSize()  %> </td>
	<td> <%= o_StyleConfig.getFontStyle()  %> </td>
	<td> <%= o_StyleConfig.getFontVariant()  %> </td>
	<td> <%= o_StyleConfig.getFontWeight()  %> </td>
	<td> <%= o_StyleConfig.getBorderDirection()  %> </td>
	<td> <%= o_StyleConfig.getBorderWidth()  %> </td>
	<td> <%= o_StyleConfig.getBorderStyle()  %> </td>
	<td> <%= o_StyleConfig.getBorderColor()  %> </td>
	<td> <%= o_StyleConfig.getMargin()  %> </td>
	<td> <%= o_StyleConfig.getPadding()  %> </td>
	<td> <%= o_StyleConfig.getListStyleType()  %> </td>
	<td> <%= o_StyleConfig.getListStylePosition()  %> </td>
	<td> <%= o_StyleConfig.getListStyleImage()  %> </td>
	<td> <%= o_StyleConfig.getFloating()  %> </td>
	<td> <%= o_StyleConfig.getExtraStyleStr()  %> </td>
	<td> <%= o_StyleConfig.getItemStyleStr()  %> </td>
	<td> <%= o_StyleConfig.getLink()  %> </td>
	<td> <%= o_StyleConfig.getLinkHover()  %> </td>
	<td> <%= o_StyleConfig.getLinkActive()  %> </td>
	<td> <%= o_StyleConfig.getHeight()  %> </td>
	<td> <%= o_StyleConfig.getWidth()  %> </td>
	<td> <%= o_StyleConfig.getIsTable()  %> </td>
	<td> <%= o_StyleConfig.getBorderCollapse()  %> </td>
	<td> <%= o_StyleConfig.getBorderSpacing()  %> </td>
	<td> <%= o_StyleConfig.getTrStyleIds()  %> </td>
	<td> <%= o_StyleConfig.getTdStyleIds()  %> </td>
	<td> <%= o_StyleConfig.getTimeCreated()  %> </td>
	<td> <%= o_StyleConfig.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_style_config_form('<%=o_StyleConfig.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/styleConfigAction.html?del=true&id=<%=o_StyleConfig.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_StyleConfig.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_style_config_form('<%=o_StyleConfig.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_style_config_form(target){
		location.href='/v_style_config_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_style_config_form(target){
		javascript:sendFormAjaxSimple('/styleConfigAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/styleConfigAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

