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
<script type="text/javascript" src="/styleConfigAction.html?ajxr=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_style_config_form.html?prv_returnPage=style_config_home"> Add New </a> |
            <a href="v_style_config_list.html?"> List Page </a> |
            <a href="v_style_config_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/styleConfigAction.html?ajxr=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form (custom field list)</a> |
			<a href="/styleConfigAction.html?ajxr=getmodalform" 			rel="facebox"> open form</a> |
			<a href="/styleConfigAction.html?ajxr=getlisthtml"  			rel="facebox"> getlisthtml</a> |
			<a href="/styleConfigAction.html?ajxr=getlistjson"  			rel="facebox"> getlistjson</a> |
			<a href="/styleConfigAction.html?ajxr=getjson&ajaxOutArg=first" rel="facebox"> getjson first</a> |
			<a href="/styleConfigAction.html?ajxr=getjson&ajaxOutArg=last" 	rel="facebox"> getjson last</a> |
			<a href="/styleConfigAction.html?ajxr=getlistdata" 				rel="facebox"> getlistdata</a> |

		</TD>        
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="styleConfigForm_styleKey_label" style="font-size: normal normal bold 10px verdana;" >Style Key </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_styleUse_label" style="font-size: normal normal bold 10px verdana;" >Style Use </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_isGlobal_label" style="font-size: normal normal bold 10px verdana;" >Is Global </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_idClass_label" style="font-size: normal normal bold 10px verdana;" >Id Class </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_isId_label" style="font-size: normal normal bold 10px verdana;" >Is Id </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_color_label" style="font-size: normal normal bold 10px verdana;" >Color </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_bgColor_label" style="font-size: normal normal bold 10px verdana;" >Bg Color </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_bgImage_label" style="font-size: normal normal bold 10px verdana;" >Bg Image </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_bgRepeat_label" style="font-size: normal normal bold 10px verdana;" >Bg Repeat </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_bgAttach_label" style="font-size: normal normal bold 10px verdana;" >Bg Attach </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_bgPosition_label" style="font-size: normal normal bold 10px verdana;" >Bg Position </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_textAlign_label" style="font-size: normal normal bold 10px verdana;" >Text Align </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_fontFamily_label" style="font-size: normal normal bold 10px verdana;" >Font Family </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_fontSize_label" style="font-size: normal normal bold 10px verdana;" >Font Size </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_fontStyle_label" style="font-size: normal normal bold 10px verdana;" >Font Style </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_fontVariant_label" style="font-size: normal normal bold 10px verdana;" >Font Variant </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_fontWeight_label" style="font-size: normal normal bold 10px verdana;" >Font Weight </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_borderDirection_label" style="font-size: normal normal bold 10px verdana;" >Border Direction </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_borderWidth_label" style="font-size: normal normal bold 10px verdana;" >Border Width </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_borderStyle_label" style="font-size: normal normal bold 10px verdana;" >Border Style </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_borderColor_label" style="font-size: normal normal bold 10px verdana;" >Border Color </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_margin_label" style="font-size: normal normal bold 10px verdana;" >Margin </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_padding_label" style="font-size: normal normal bold 10px verdana;" >Padding </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_listStyleType_label" style="font-size: normal normal bold 10px verdana;" >List Style Type </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_listStylePosition_label" style="font-size: normal normal bold 10px verdana;" >List Style Position </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_listStyleImage_label" style="font-size: normal normal bold 10px verdana;" >List Style Image </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_floating_label" style="font-size: normal normal bold 10px verdana;" >Floating </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_extraStyleStr_label" style="font-size: normal normal bold 10px verdana;" >Extra Style Str </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_itemStyleStr_label" style="font-size: normal normal bold 10px verdana;" >Item Style Str </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_link_label" style="font-size: normal normal bold 10px verdana;" >Link </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_linkHover_label" style="font-size: normal normal bold 10px verdana;" >Link Hover </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_linkActive_label" style="font-size: normal normal bold 10px verdana;" >Link Active </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_height_label" style="font-size: normal normal bold 10px verdana;" >Height </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_width_label" style="font-size: normal normal bold 10px verdana;" >Width </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_isTable_label" style="font-size: normal normal bold 10px verdana;" >Is Table </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_borderCollapse_label" style="font-size: normal normal bold 10px verdana;" >Border Collapse </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_borderSpacing_label" style="font-size: normal normal bold 10px verdana;" >Border Spacing </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_trStyleIds_label" style="font-size: normal normal bold 10px verdana;" >Tr Style Ids </div>
    </td> 
    <td> 
	    <div id="styleConfigForm_tdStyleIds_label" style="font-size: normal normal bold 10px verdana;" >Td Style Ids </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleConfig _StyleConfig = (StyleConfig) iter.next();
		//TODO 
        fieldString += "\"" +  _StyleConfig.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _StyleConfig.getId() %> </td>


    <td> 
	<form name="styleConfigFormEditField_StyleKey_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_styleKey_field">
	    <div id="styleConfigForm_styleKey_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="styleKey" value="<%=WebUtil.display(_StyleConfig.getStyleKey())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="styleKey_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getStyleKey() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_StyleKey_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'styleKey', '<%=_StyleConfig.getStyleKey()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="styleKey">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="styleKey">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="styleKey">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_StyleUse_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >

		<div id="styleConfigForm_styleUse_field">
	    <div id="styleConfigForm_styleUse_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="styleUse">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _StyleConfig.getStyleUse())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="styleUse_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getStyleUse() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_StyleUse_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'styleUse', '<%=_StyleConfig.getStyleUse()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="styleUse">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="styleUse">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="styleUse">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_IsGlobal_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_isGlobal_field">
	    <div id="styleConfigForm_isGlobal_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="isGlobal" value="<%=WebUtil.display(_StyleConfig.getIsGlobal())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isGlobal_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getIsGlobal() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_IsGlobal_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'isGlobal', '<%=_StyleConfig.getIsGlobal()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isGlobal">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isGlobal">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isGlobal">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_IdClass_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_idClass_field">
	    <div id="styleConfigForm_idClass_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="idClass" value="<%=WebUtil.display(_StyleConfig.getIdClass())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="idClass_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getIdClass() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_IdClass_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'idClass', '<%=_StyleConfig.getIdClass()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="idClass">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="idClass">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="idClass">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_IsId_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_isId_field">
	    <div id="styleConfigForm_isId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="isId" value="<%=WebUtil.display(_StyleConfig.getIsId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isId_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getIsId() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_IsId_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'isId', '<%=_StyleConfig.getIsId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_Color_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_color_field">
	    <div id="styleConfigForm_color_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="color" value="<%=WebUtil.display(_StyleConfig.getColor())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="color_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getColor() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_Color_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'color', '<%=_StyleConfig.getColor()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="color">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="color">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="color">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_BgColor_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_bgColor_field">
	    <div id="styleConfigForm_bgColor_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="bgColor" value="<%=WebUtil.display(_StyleConfig.getBgColor())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="bgColor_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getBgColor() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_BgColor_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'bgColor', '<%=_StyleConfig.getBgColor()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="bgColor">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="bgColor">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="bgColor">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_BgImage_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_bgImage_field">
	    <div id="styleConfigForm_bgImage_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="bgImage" value="<%=WebUtil.display(_StyleConfig.getBgImage())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="bgImage_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getBgImage() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_BgImage_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'bgImage', '<%=_StyleConfig.getBgImage()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="bgImage">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="bgImage">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="bgImage">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_BgRepeat_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_bgRepeat_field">
	    <div id="styleConfigForm_bgRepeat_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="bgRepeat" value="<%=WebUtil.display(_StyleConfig.getBgRepeat())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="bgRepeat_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getBgRepeat() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_BgRepeat_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'bgRepeat', '<%=_StyleConfig.getBgRepeat()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="bgRepeat">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="bgRepeat">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="bgRepeat">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_BgAttach_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_bgAttach_field">
	    <div id="styleConfigForm_bgAttach_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="bgAttach" value="<%=WebUtil.display(_StyleConfig.getBgAttach())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="bgAttach_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getBgAttach() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_BgAttach_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'bgAttach', '<%=_StyleConfig.getBgAttach()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="bgAttach">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="bgAttach">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="bgAttach">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_BgPosition_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_bgPosition_field">
	    <div id="styleConfigForm_bgPosition_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="bgPosition" value="<%=WebUtil.display(_StyleConfig.getBgPosition())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="bgPosition_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getBgPosition() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_BgPosition_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'bgPosition', '<%=_StyleConfig.getBgPosition()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="bgPosition">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="bgPosition">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="bgPosition">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_TextAlign_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_textAlign_field">
	    <div id="styleConfigForm_textAlign_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="textAlign" value="<%=WebUtil.display(_StyleConfig.getTextAlign())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="textAlign_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getTextAlign() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_TextAlign_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'textAlign', '<%=_StyleConfig.getTextAlign()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="textAlign">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="textAlign">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="textAlign">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_FontFamily_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_fontFamily_field">
	    <div id="styleConfigForm_fontFamily_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="fontFamily" value="<%=WebUtil.display(_StyleConfig.getFontFamily())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="fontFamily_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getFontFamily() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_FontFamily_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'fontFamily', '<%=_StyleConfig.getFontFamily()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="fontFamily">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="fontFamily">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="fontFamily">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_FontSize_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_fontSize_field">
	    <div id="styleConfigForm_fontSize_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="fontSize" value="<%=WebUtil.display(_StyleConfig.getFontSize())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="fontSize_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getFontSize() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_FontSize_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'fontSize', '<%=_StyleConfig.getFontSize()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="fontSize">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="fontSize">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="fontSize">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_FontStyle_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_fontStyle_field">
	    <div id="styleConfigForm_fontStyle_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="fontStyle" value="<%=WebUtil.display(_StyleConfig.getFontStyle())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="fontStyle_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getFontStyle() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_FontStyle_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'fontStyle', '<%=_StyleConfig.getFontStyle()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="fontStyle">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="fontStyle">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="fontStyle">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_FontVariant_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_fontVariant_field">
	    <div id="styleConfigForm_fontVariant_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="fontVariant" value="<%=WebUtil.display(_StyleConfig.getFontVariant())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="fontVariant_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getFontVariant() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_FontVariant_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'fontVariant', '<%=_StyleConfig.getFontVariant()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="fontVariant">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="fontVariant">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="fontVariant">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_FontWeight_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_fontWeight_field">
	    <div id="styleConfigForm_fontWeight_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="fontWeight" value="<%=WebUtil.display(_StyleConfig.getFontWeight())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="fontWeight_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getFontWeight() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_FontWeight_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'fontWeight', '<%=_StyleConfig.getFontWeight()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="fontWeight">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="fontWeight">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="fontWeight">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_BorderDirection_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_borderDirection_field">
	    <div id="styleConfigForm_borderDirection_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="borderDirection" value="<%=WebUtil.display(_StyleConfig.getBorderDirection())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="borderDirection_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getBorderDirection() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_BorderDirection_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'borderDirection', '<%=_StyleConfig.getBorderDirection()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="borderDirection">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="borderDirection">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="borderDirection">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_BorderWidth_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_borderWidth_field">
	    <div id="styleConfigForm_borderWidth_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="borderWidth" value="<%=WebUtil.display(_StyleConfig.getBorderWidth())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="borderWidth_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getBorderWidth() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_BorderWidth_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'borderWidth', '<%=_StyleConfig.getBorderWidth()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="borderWidth">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="borderWidth">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="borderWidth">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_BorderStyle_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_borderStyle_field">
	    <div id="styleConfigForm_borderStyle_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="borderStyle" value="<%=WebUtil.display(_StyleConfig.getBorderStyle())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="borderStyle_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getBorderStyle() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_BorderStyle_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'borderStyle', '<%=_StyleConfig.getBorderStyle()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="borderStyle">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="borderStyle">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="borderStyle">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_BorderColor_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_borderColor_field">
	    <div id="styleConfigForm_borderColor_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="borderColor" value="<%=WebUtil.display(_StyleConfig.getBorderColor())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="borderColor_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getBorderColor() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_BorderColor_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'borderColor', '<%=_StyleConfig.getBorderColor()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="borderColor">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="borderColor">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="borderColor">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_Margin_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_margin_field">
	    <div id="styleConfigForm_margin_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="margin" value="<%=WebUtil.display(_StyleConfig.getMargin())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="margin_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getMargin() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_Margin_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'margin', '<%=_StyleConfig.getMargin()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="margin">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="margin">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="margin">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_Padding_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_padding_field">
	    <div id="styleConfigForm_padding_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="padding" value="<%=WebUtil.display(_StyleConfig.getPadding())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="padding_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getPadding() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_Padding_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'padding', '<%=_StyleConfig.getPadding()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="padding">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="padding">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="padding">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_ListStyleType_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_listStyleType_field">
	    <div id="styleConfigForm_listStyleType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="listStyleType" value="<%=WebUtil.display(_StyleConfig.getListStyleType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="listStyleType_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getListStyleType() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_ListStyleType_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'listStyleType', '<%=_StyleConfig.getListStyleType()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="listStyleType">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="listStyleType">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="listStyleType">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_ListStylePosition_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_listStylePosition_field">
	    <div id="styleConfigForm_listStylePosition_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="listStylePosition" value="<%=WebUtil.display(_StyleConfig.getListStylePosition())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="listStylePosition_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getListStylePosition() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_ListStylePosition_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'listStylePosition', '<%=_StyleConfig.getListStylePosition()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="listStylePosition">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="listStylePosition">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="listStylePosition">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_ListStyleImage_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_listStyleImage_field">
	    <div id="styleConfigForm_listStyleImage_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="listStyleImage" value="<%=WebUtil.display(_StyleConfig.getListStyleImage())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="listStyleImage_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getListStyleImage() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_ListStyleImage_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'listStyleImage', '<%=_StyleConfig.getListStyleImage()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="listStyleImage">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="listStyleImage">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="listStyleImage">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_Floating_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_floating_field">
	    <div id="styleConfigForm_floating_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="floating" value="<%=WebUtil.display(_StyleConfig.getFloating())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="floating_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getFloating() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_Floating_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'floating', '<%=_StyleConfig.getFloating()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="floating">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="floating">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="floating">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_ExtraStyleStr_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_extraStyleStr_field">
	    <div id="styleConfigForm_extraStyleStr_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="extraStyleStr" value="<%=WebUtil.display(_StyleConfig.getExtraStyleStr())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="extraStyleStr_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getExtraStyleStr() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_ExtraStyleStr_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'extraStyleStr', '<%=_StyleConfig.getExtraStyleStr()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="extraStyleStr">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="extraStyleStr">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="extraStyleStr">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_ItemStyleStr_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_itemStyleStr_field">
	    <div id="styleConfigForm_itemStyleStr_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="itemStyleStr" value="<%=WebUtil.display(_StyleConfig.getItemStyleStr())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="itemStyleStr_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getItemStyleStr() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_ItemStyleStr_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'itemStyleStr', '<%=_StyleConfig.getItemStyleStr()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="itemStyleStr">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="itemStyleStr">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="itemStyleStr">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_Link_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_link_field">
	    <div id="styleConfigForm_link_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="link" value="<%=WebUtil.display(_StyleConfig.getLink())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="link_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getLink() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_Link_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'link', '<%=_StyleConfig.getLink()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="link">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="link">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="link">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_LinkHover_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_linkHover_field">
	    <div id="styleConfigForm_linkHover_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="linkHover" value="<%=WebUtil.display(_StyleConfig.getLinkHover())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="linkHover_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getLinkHover() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_LinkHover_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'linkHover', '<%=_StyleConfig.getLinkHover()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="linkHover">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="linkHover">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="linkHover">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_LinkActive_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_linkActive_field">
	    <div id="styleConfigForm_linkActive_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="linkActive" value="<%=WebUtil.display(_StyleConfig.getLinkActive())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="linkActive_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getLinkActive() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_LinkActive_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'linkActive', '<%=_StyleConfig.getLinkActive()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="linkActive">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="linkActive">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="linkActive">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_Height_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_height_field">
	    <div id="styleConfigForm_height_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="height" value="<%=WebUtil.display(_StyleConfig.getHeight())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="height_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getHeight() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_Height_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'height', '<%=_StyleConfig.getHeight()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="height">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="height">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="height">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_Width_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_width_field">
	    <div id="styleConfigForm_width_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="width" value="<%=WebUtil.display(_StyleConfig.getWidth())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="width_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getWidth() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_Width_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'width', '<%=_StyleConfig.getWidth()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="width">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="width">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="width">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_IsTable_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_isTable_field">
	    <div id="styleConfigForm_isTable_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="isTable" value="<%=WebUtil.display(_StyleConfig.getIsTable())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isTable_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getIsTable() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_IsTable_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'isTable', '<%=_StyleConfig.getIsTable()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isTable">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isTable">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isTable">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_BorderCollapse_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_borderCollapse_field">
	    <div id="styleConfigForm_borderCollapse_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="borderCollapse" value="<%=WebUtil.display(_StyleConfig.getBorderCollapse())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="borderCollapse_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getBorderCollapse() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_BorderCollapse_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'borderCollapse', '<%=_StyleConfig.getBorderCollapse()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="borderCollapse">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="borderCollapse">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="borderCollapse">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_BorderSpacing_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_borderSpacing_field">
	    <div id="styleConfigForm_borderSpacing_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="borderSpacing" value="<%=WebUtil.display(_StyleConfig.getBorderSpacing())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="borderSpacing_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getBorderSpacing() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_BorderSpacing_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'borderSpacing', '<%=_StyleConfig.getBorderSpacing()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="borderSpacing">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="borderSpacing">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="borderSpacing">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_TrStyleIds_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_trStyleIds_field">
	    <div id="styleConfigForm_trStyleIds_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="trStyleIds" value="<%=WebUtil.display(_StyleConfig.getTrStyleIds())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="trStyleIds_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getTrStyleIds() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_TrStyleIds_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'trStyleIds', '<%=_StyleConfig.getTrStyleIds()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="trStyleIds">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="trStyleIds">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="trStyleIds">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleConfigFormEditField_TdStyleIds_<%=_StyleConfig.getId()%>" method="get" action="/styleConfigAction.html" >


		<div id="styleConfigForm_tdStyleIds_field">
	    <div id="styleConfigForm_tdStyleIds_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="tdStyleIds" value="<%=WebUtil.display(_StyleConfig.getTdStyleIds())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="tdStyleIds_<%= _StyleConfig.getId()%>"><%=_StyleConfig.getTdStyleIds() %></div>
            <a id="formSubmit" href="javascript:document.styleConfigFormEditField_TdStyleIds_<%=_StyleConfig.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_StyleConfig.getId()%>', 'tdStyleIds', '<%=_StyleConfig.getTdStyleIds()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="tdStyleIds">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="tdStyleIds">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="tdStyleIds">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_config_home">
	</form>
    
    
    </td>



    <td>
    <form name="styleConfigForm<%=_StyleConfig.getId()%>2" method="get" action="/v_style_config_form.html" >
        <a href="javascript:document.styleConfigForm<%=_StyleConfig.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _StyleConfig.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="style_config_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_style_config_form('<%=_StyleConfig.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/styleConfigAction.html?del=true&id=<%=_StyleConfig.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_style_config('<%=_StyleConfig.getId()%>');">DeleteWConfirm</a>
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




	function edit_style_config_form(target){
		location.href='/v_style_config_form.html?cmd=edit&prv_returnPage=style_config_home&id=' + target;
	}

	function confirm_style_config(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_style_config(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/styleConfigAction.html?del=true&id="+target;
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
						location.href="/styleConfigAction.html?editfield=true&returnPage=cleaner_pickup_home&id="+targetId+"&"+targetField +"=" +f.myname;
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
		   		url: "/styleConfigAction.html?editfield=true&ajxr=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
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
		   		url: "/styleConfigAction.html?editfield=true&ajaxRequest=true&ajaxOut=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
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
		   		url: "/styleConfigAction.html?editfield=true&ajxr=get2field&id="+_id+"&"+_fieldName+"="+ _val,
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
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-style-config",
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
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-style-config",
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