<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
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
	StyleThemeDS ds = StyleThemeDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else {
	    list = ds.getAll();
    }
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


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_style_theme_form.html?prv_returnPage=style_theme_home"> Add New </a> |
            <a href="v_style_theme_list.html?"> ListPage </a> |
            <a href="v_style_theme_ajax.html?"> Ajax Sampler </a> |
			<a href="/styleThemeAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/styleThemeAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |
			<a href="/styleThemeAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Ajax List</a> |
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="styleThemeForm_title_label" style="font-size: normal normal bold 10px verdana;" >Title </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_bodyWidth_label" style="font-size: normal normal bold 10px verdana;" >Body Width </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_bodyAlign_label" style="font-size: normal normal bold 10px verdana;" >Body Align </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_bodyBgColor_label" style="font-size: normal normal bold 10px verdana;" >Body Bg Color </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_bodyBgImage_label" style="font-size: normal normal bold 10px verdana;" >Body Bg Image </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_bodyBgAttach_label" style="font-size: normal normal bold 10px verdana;" >Body Bg Attach </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_bodyBgRepeat_label" style="font-size: normal normal bold 10px verdana;" >Body Bg Repeat </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_bodyBgPosition_label" style="font-size: normal normal bold 10px verdana;" >Body Bg Position </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_contentBgColor_label" style="font-size: normal normal bold 10px verdana;" >Content Bg Color </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_useAbsolute_label" style="font-size: normal normal bold 10px verdana;" >Use Absolute </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_absoluteTop_label" style="font-size: normal normal bold 10px verdana;" >Absolute Top </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_absoluteLeft_label" style="font-size: normal normal bold 10px verdana;" >Absolute Left </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_absoluteRight_label" style="font-size: normal normal bold 10px verdana;" >Absolute Right </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_absoluteBottom_label" style="font-size: normal normal bold 10px verdana;" >Absolute Bottom </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_panelStyleId_label" style="font-size: normal normal bold 10px verdana;" >Panel Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_panelDataStyleId_label" style="font-size: normal normal bold 10px verdana;" >Panel Data Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_panelLinkStyleId_label" style="font-size: normal normal bold 10px verdana;" >Panel Link Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_panelTitleStyleId_label" style="font-size: normal normal bold 10px verdana;" >Panel Title Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_menuStyleId_label" style="font-size: normal normal bold 10px verdana;" >Menu Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_menuLinkStyleId_label" style="font-size: normal normal bold 10px verdana;" >Menu Link Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_headerMenuStyleId_label" style="font-size: normal normal bold 10px verdana;" >Header Menu Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_headerMenuLinkStyleId_label" style="font-size: normal normal bold 10px verdana;" >Header Menu Link Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_listFrameStyleId_label" style="font-size: normal normal bold 10px verdana;" >List Frame Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_listSubjectStyleId_label" style="font-size: normal normal bold 10px verdana;" >List Subject Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_listDataStyleId_label" style="font-size: normal normal bold 10px verdana;" >List Data Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_subjectStyleId_label" style="font-size: normal normal bold 10px verdana;" >Subject Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_dataStyleId_label" style="font-size: normal normal bold 10px verdana;" >Data Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_singleFrameStyleId_label" style="font-size: normal normal bold 10px verdana;" >Single Frame Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_singleSubjectStyleId_label" style="font-size: normal normal bold 10px verdana;" >Single Subject Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_singleDataStyleId_label" style="font-size: normal normal bold 10px verdana;" >Single Data Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_contentPanelStyleId_label" style="font-size: normal normal bold 10px verdana;" >Content Panel Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_contentPanelTitleStyleId_label" style="font-size: normal normal bold 10px verdana;" >Content Panel Title Style Id </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_global_label" style="font-size: normal normal bold 10px verdana;" >Global </div>
    </td> 
    <td> 
	    <div id="styleThemeForm_disable_label" style="font-size: normal normal bold 10px verdana;" >Disable </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleTheme _StyleTheme = (StyleTheme) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _StyleTheme.getId() %> </td>


    <td> 
	<form name="styleThemeFormEditField_Title_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_title_field">
	    <div id="styleThemeForm_title_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="title" value="<%=WebUtil.display(_StyleTheme.getTitle())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_Title_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_BodyWidth_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_bodyWidth_field">
	    <div id="styleThemeForm_bodyWidth_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="bodyWidth" value="<%=WebUtil.display(_StyleTheme.getBodyWidth())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_BodyWidth_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_BodyAlign_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >

		<div id="styleThemeForm_bodyAlign_field">
	    <div id="styleThemeForm_bodyAlign_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="bodyAlign">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _StyleTheme.getBodyAlign())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_BodyAlign_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_BodyBgColor_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_bodyBgColor_field">
	    <div id="styleThemeForm_bodyBgColor_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="bodyBgColor" value="<%=WebUtil.display(_StyleTheme.getBodyBgColor())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_BodyBgColor_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_BodyBgImage_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_bodyBgImage_field">
	    <div id="styleThemeForm_bodyBgImage_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="bodyBgImage" value="<%=WebUtil.display(_StyleTheme.getBodyBgImage())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_BodyBgImage_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_BodyBgAttach_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_bodyBgAttach_field">
	    <div id="styleThemeForm_bodyBgAttach_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="bodyBgAttach" value="<%=WebUtil.display(_StyleTheme.getBodyBgAttach())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_BodyBgAttach_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_BodyBgRepeat_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_bodyBgRepeat_field">
	    <div id="styleThemeForm_bodyBgRepeat_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="bodyBgRepeat" value="<%=WebUtil.display(_StyleTheme.getBodyBgRepeat())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_BodyBgRepeat_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_BodyBgPosition_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_bodyBgPosition_field">
	    <div id="styleThemeForm_bodyBgPosition_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="bodyBgPosition" value="<%=WebUtil.display(_StyleTheme.getBodyBgPosition())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_BodyBgPosition_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_ContentBgColor_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_contentBgColor_field">
	    <div id="styleThemeForm_contentBgColor_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentBgColor" value="<%=WebUtil.display(_StyleTheme.getContentBgColor())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_ContentBgColor_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_UseAbsolute_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_useAbsolute_field">
	    <div id="styleThemeForm_useAbsolute_dropdown" class="formFieldDropDown" >       
	        <select name="useAbsolute">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _StyleTheme.getUseAbsolute())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _StyleTheme.getUseAbsolute())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_UseAbsolute_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_AbsoluteTop_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_absoluteTop_field">
	    <div id="styleThemeForm_absoluteTop_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="absoluteTop" value="<%=WebUtil.display(_StyleTheme.getAbsoluteTop())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_AbsoluteTop_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_AbsoluteLeft_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_absoluteLeft_field">
	    <div id="styleThemeForm_absoluteLeft_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="absoluteLeft" value="<%=WebUtil.display(_StyleTheme.getAbsoluteLeft())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_AbsoluteLeft_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_AbsoluteRight_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_absoluteRight_field">
	    <div id="styleThemeForm_absoluteRight_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="absoluteRight" value="<%=WebUtil.display(_StyleTheme.getAbsoluteRight())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_AbsoluteRight_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_AbsoluteBottom_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_absoluteBottom_field">
	    <div id="styleThemeForm_absoluteBottom_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="absoluteBottom" value="<%=WebUtil.display(_StyleTheme.getAbsoluteBottom())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_AbsoluteBottom_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_PanelStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_panelStyleId_field">
	    <div id="styleThemeForm_panelStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="panelStyleId" value="<%=WebUtil.display(_StyleTheme.getPanelStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_PanelStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_PanelDataStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_panelDataStyleId_field">
	    <div id="styleThemeForm_panelDataStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="panelDataStyleId" value="<%=WebUtil.display(_StyleTheme.getPanelDataStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_PanelDataStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_PanelLinkStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_panelLinkStyleId_field">
	    <div id="styleThemeForm_panelLinkStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="panelLinkStyleId" value="<%=WebUtil.display(_StyleTheme.getPanelLinkStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_PanelLinkStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_PanelTitleStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_panelTitleStyleId_field">
	    <div id="styleThemeForm_panelTitleStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="panelTitleStyleId" value="<%=WebUtil.display(_StyleTheme.getPanelTitleStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_PanelTitleStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_MenuStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_menuStyleId_field">
	    <div id="styleThemeForm_menuStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="menuStyleId" value="<%=WebUtil.display(_StyleTheme.getMenuStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_MenuStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_MenuLinkStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_menuLinkStyleId_field">
	    <div id="styleThemeForm_menuLinkStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="menuLinkStyleId" value="<%=WebUtil.display(_StyleTheme.getMenuLinkStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_MenuLinkStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_HeaderMenuStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_headerMenuStyleId_field">
	    <div id="styleThemeForm_headerMenuStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="headerMenuStyleId" value="<%=WebUtil.display(_StyleTheme.getHeaderMenuStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_HeaderMenuStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_HeaderMenuLinkStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_headerMenuLinkStyleId_field">
	    <div id="styleThemeForm_headerMenuLinkStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="headerMenuLinkStyleId" value="<%=WebUtil.display(_StyleTheme.getHeaderMenuLinkStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_HeaderMenuLinkStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_ListFrameStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_listFrameStyleId_field">
	    <div id="styleThemeForm_listFrameStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="listFrameStyleId" value="<%=WebUtil.display(_StyleTheme.getListFrameStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_ListFrameStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_ListSubjectStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_listSubjectStyleId_field">
	    <div id="styleThemeForm_listSubjectStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="listSubjectStyleId" value="<%=WebUtil.display(_StyleTheme.getListSubjectStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_ListSubjectStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_ListDataStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_listDataStyleId_field">
	    <div id="styleThemeForm_listDataStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="listDataStyleId" value="<%=WebUtil.display(_StyleTheme.getListDataStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_ListDataStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_SubjectStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_subjectStyleId_field">
	    <div id="styleThemeForm_subjectStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="subjectStyleId" value="<%=WebUtil.display(_StyleTheme.getSubjectStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_SubjectStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_DataStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_dataStyleId_field">
	    <div id="styleThemeForm_dataStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="dataStyleId" value="<%=WebUtil.display(_StyleTheme.getDataStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_DataStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_SingleFrameStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_singleFrameStyleId_field">
	    <div id="styleThemeForm_singleFrameStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="singleFrameStyleId" value="<%=WebUtil.display(_StyleTheme.getSingleFrameStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_SingleFrameStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_SingleSubjectStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_singleSubjectStyleId_field">
	    <div id="styleThemeForm_singleSubjectStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="singleSubjectStyleId" value="<%=WebUtil.display(_StyleTheme.getSingleSubjectStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_SingleSubjectStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_SingleDataStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_singleDataStyleId_field">
	    <div id="styleThemeForm_singleDataStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="singleDataStyleId" value="<%=WebUtil.display(_StyleTheme.getSingleDataStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_SingleDataStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_ContentPanelStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_contentPanelStyleId_field">
	    <div id="styleThemeForm_contentPanelStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentPanelStyleId" value="<%=WebUtil.display(_StyleTheme.getContentPanelStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_ContentPanelStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_ContentPanelTitleStyleId_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_contentPanelTitleStyleId_field">
	    <div id="styleThemeForm_contentPanelTitleStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentPanelTitleStyleId" value="<%=WebUtil.display(_StyleTheme.getContentPanelTitleStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_ContentPanelTitleStyleId_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_Global_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_global_field">
	    <div id="styleThemeForm_global_dropdown" class="formFieldDropDown" >       
	        <select name="global">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _StyleTheme.getGlobal())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _StyleTheme.getGlobal())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_Global_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleThemeFormEditField_Disable_<%=_StyleTheme.getId()%>" method="get" action="/styleThemeAction.html" >


		<div id="styleThemeForm_disable_field">
	    <div id="styleThemeForm_disable_dropdown" class="formFieldDropDown" >       
	        <select name="disable">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _StyleTheme.getDisable())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _StyleTheme.getDisable())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleThemeFormEditField_Disable_<%=_StyleTheme.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_theme_home">
	</form>
    
    
    </td>



    <td>
    <form name="styleThemeForm<%=_StyleTheme.getId()%>2" method="get" action="/v_style_theme_form.html" >
        <a href="javascript:document.styleThemeForm<%=_StyleTheme.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _StyleTheme.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="style_theme_home">
    </form>
    </td>

    <td>
    <a href="/styleThemeAction.html?del=true&id=<%=_StyleTheme.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>