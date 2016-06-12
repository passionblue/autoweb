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
	PageContentConfigDS ds = PageContentConfigDS.getInstance();    

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


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_page_content_config_form.html?prv_returnPage=page_content_config_home"> Add New </a> |
            <a href="v_page_content_config_list.html?"> ListPage </a> |
            <a href="v_page_content_config_ajax.html?"> Ajax Sampler </a> |
			<a href="/pageContentConfigAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/pageContentConfigAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |
			<a href="/pageContentConfigAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Ajax List</a> |
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="pageContentConfigForm_contentId_label" style="font-size: normal normal bold 10px verdana;" >Content Id </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_contentType_label" style="font-size: normal normal bold 10px verdana;" >Content Type </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_sortType_label" style="font-size: normal normal bold 10px verdana;" >Sort Type </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_arrangeType_label" style="font-size: normal normal bold 10px verdana;" >Arrange Type </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_pageCss_label" style="font-size: normal normal bold 10px verdana;" >Page Css </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_pageScript_label" style="font-size: normal normal bold 10px verdana;" >Page Script </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_pageCssImports_label" style="font-size: normal normal bold 10px verdana;" >Page Css Imports </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_pageScriptImports_label" style="font-size: normal normal bold 10px verdana;" >Page Script Imports </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_hideMenu_label" style="font-size: normal normal bold 10px verdana;" >Hide Menu </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_hideMid_label" style="font-size: normal normal bold 10px verdana;" >Hide Mid </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_hideAd_label" style="font-size: normal normal bold 10px verdana;" >Hide Ad </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_styleId_label" style="font-size: normal normal bold 10px verdana;" >Style Id </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_contentStyleSetId_label" style="font-size: normal normal bold 10px verdana;" >Content Style Set Id </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_headerMeta_label" style="font-size: normal normal bold 10px verdana;" >Header Meta </div>
    </td> 
    <td> 
	    <div id="pageContentConfigForm_headerLink_label" style="font-size: normal normal bold 10px verdana;" >Header Link </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageContentConfig _PageContentConfig = (PageContentConfig) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PageContentConfig.getId() %> </td>


    <td> 
	<form name="pageContentConfigFormEditField_ContentId_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_contentId_field">
	    <div id="pageContentConfigForm_contentId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentId" value="<%=WebUtil.display(_PageContentConfig.getContentId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_ContentId_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_ContentType_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >

		<div id="pageContentConfigForm_contentType_field">
	    <div id="pageContentConfigForm_contentType_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="contentType">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _PageContentConfig.getContentType())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_ContentType_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_SortType_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_sortType_field">
	    <div id="pageContentConfigForm_sortType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="sortType" value="<%=WebUtil.display(_PageContentConfig.getSortType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_SortType_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_ArrangeType_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_arrangeType_field">
	    <div id="pageContentConfigForm_arrangeType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="arrangeType" value="<%=WebUtil.display(_PageContentConfig.getArrangeType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_ArrangeType_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_PageCss_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_pageCss_field">
	    <div id="pageContentConfigForm_pageCss_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageCss" value="<%=WebUtil.display(_PageContentConfig.getPageCss())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_PageCss_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_PageScript_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_pageScript_field">
	    <div id="pageContentConfigForm_pageScript_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageScript" value="<%=WebUtil.display(_PageContentConfig.getPageScript())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_PageScript_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_PageCssImports_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_pageCssImports_field">
	    <div id="pageContentConfigForm_pageCssImports_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageCssImports" value="<%=WebUtil.display(_PageContentConfig.getPageCssImports())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_PageCssImports_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_PageScriptImports_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_pageScriptImports_field">
	    <div id="pageContentConfigForm_pageScriptImports_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageScriptImports" value="<%=WebUtil.display(_PageContentConfig.getPageScriptImports())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_PageScriptImports_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_HideMenu_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_hideMenu_field">
	    <div id="pageContentConfigForm_hideMenu_dropdown" class="formFieldDropDown" >       
	        <select name="hideMenu">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _PageContentConfig.getHideMenu())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _PageContentConfig.getHideMenu())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_HideMenu_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_HideMid_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_hideMid_field">
	    <div id="pageContentConfigForm_hideMid_dropdown" class="formFieldDropDown" >       
	        <select name="hideMid">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _PageContentConfig.getHideMid())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _PageContentConfig.getHideMid())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_HideMid_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_HideAd_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_hideAd_field">
	    <div id="pageContentConfigForm_hideAd_dropdown" class="formFieldDropDown" >       
	        <select name="hideAd">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _PageContentConfig.getHideAd())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _PageContentConfig.getHideAd())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_HideAd_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_StyleId_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_styleId_field">
	    <div id="pageContentConfigForm_styleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="styleId" value="<%=WebUtil.display(_PageContentConfig.getStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_StyleId_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_ContentStyleSetId_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_contentStyleSetId_field">
	    <div id="pageContentConfigForm_contentStyleSetId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentStyleSetId" value="<%=WebUtil.display(_PageContentConfig.getContentStyleSetId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_ContentStyleSetId_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_HeaderMeta_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_headerMeta_field">
	    <div id="pageContentConfigForm_headerMeta_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="headerMeta" value="<%=WebUtil.display(_PageContentConfig.getHeaderMeta())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_HeaderMeta_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageContentConfigFormEditField_HeaderLink_<%=_PageContentConfig.getId()%>" method="get" action="/pageContentConfigAction.html" >


		<div id="pageContentConfigForm_headerLink_field">
	    <div id="pageContentConfigForm_headerLink_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="headerLink" value="<%=WebUtil.display(_PageContentConfig.getHeaderLink())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEditField_HeaderLink_<%=_PageContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_content_config_home">
	</form>
    
    
    </td>



    <td>
    <form name="pageContentConfigForm<%=_PageContentConfig.getId()%>2" method="get" action="/v_page_content_config_form.html" >
        <a href="javascript:document.pageContentConfigForm<%=_PageContentConfig.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageContentConfig.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="page_content_config_home">
    </form>
    </td>

    <td>
    <a href="/pageContentConfigAction.html?del=true&id=<%=_PageContentConfig.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>