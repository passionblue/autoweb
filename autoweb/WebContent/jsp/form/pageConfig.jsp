<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
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
	PageConfigDS ds = PageConfigDS.getInstance();    

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
            <a href="v_page_config_form.html?prv_returnPage=page_config_home"> Add New </a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="pageConfigForm_pageId_label" style="font-size: normal normal bold 10px verdana;" >Page Id </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_sortType_label" style="font-size: normal normal bold 10px verdana;" >Sort Type </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_arrangeType_label" style="font-size: normal normal bold 10px verdana;" >Arrange Type </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_pageCss_label" style="font-size: normal normal bold 10px verdana;" >Page Css </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_pageScript_label" style="font-size: normal normal bold 10px verdana;" >Page Script </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_pageCssImports_label" style="font-size: normal normal bold 10px verdana;" >Page Css Imports </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_pageScriptImports_label" style="font-size: normal normal bold 10px verdana;" >Page Script Imports </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_hideMenu_label" style="font-size: normal normal bold 10px verdana;" >Hide Menu </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_hideMid_label" style="font-size: normal normal bold 10px verdana;" >Hide Mid </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_hideAd_label" style="font-size: normal normal bold 10px verdana;" >Hide Ad </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_styleId_label" style="font-size: normal normal bold 10px verdana;" >Style Id </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_contentStyleSetId_label" style="font-size: normal normal bold 10px verdana;" >Content Style Set Id </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_headerMeta_label" style="font-size: normal normal bold 10px verdana;" >Header Meta </div>
    </td> 
    <td> 
	    <div id="pageConfigForm_headerLink_label" style="font-size: normal normal bold 10px verdana;" >Header Link </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageConfig _PageConfig = (PageConfig) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PageConfig.getId() %> </td>


    <td> 
	<form name="pageConfigFormEditField_PageId_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >


		<div id="pageConfigForm_pageId_field">
	    <div id="pageConfigForm_pageId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageId" value="<%=WebUtil.display(_PageConfig.getPageId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_PageId_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_SortType_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >

		<div id="pageConfigForm_sortType_field">
	    <div id="pageConfigForm_sortType_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="sortType">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _PageConfig.getSortType())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_SortType_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_ArrangeType_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >

		<div id="pageConfigForm_arrangeType_field">
	    <div id="pageConfigForm_arrangeType_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="arrangeType">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _PageConfig.getArrangeType())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_ArrangeType_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_PageCss_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >


		<div id="pageConfigForm_pageCss_field">
	    <div id="pageConfigForm_pageCss_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageCss" value="<%=WebUtil.display(_PageConfig.getPageCss())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_PageCss_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_PageScript_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >


		<div id="pageConfigForm_pageScript_field">
	    <div id="pageConfigForm_pageScript_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageScript" value="<%=WebUtil.display(_PageConfig.getPageScript())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_PageScript_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_PageCssImports_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >


		<div id="pageConfigForm_pageCssImports_field">
	    <div id="pageConfigForm_pageCssImports_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageCssImports" value="<%=WebUtil.display(_PageConfig.getPageCssImports())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_PageCssImports_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_PageScriptImports_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >


		<div id="pageConfigForm_pageScriptImports_field">
	    <div id="pageConfigForm_pageScriptImports_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageScriptImports" value="<%=WebUtil.display(_PageConfig.getPageScriptImports())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_PageScriptImports_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_HideMenu_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >


		<div id="pageConfigForm_hideMenu_field">
	    <div id="pageConfigForm_hideMenu_dropdown" class="formFieldDropDown" >       
	        <select name="hideMenu">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _PageConfig.getHideMenu())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _PageConfig.getHideMenu())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_HideMenu_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_HideMid_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >


		<div id="pageConfigForm_hideMid_field">
	    <div id="pageConfigForm_hideMid_dropdown" class="formFieldDropDown" >       
	        <select name="hideMid">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _PageConfig.getHideMid())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _PageConfig.getHideMid())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_HideMid_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_HideAd_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >


		<div id="pageConfigForm_hideAd_field">
	    <div id="pageConfigForm_hideAd_dropdown" class="formFieldDropDown" >       
	        <select name="hideAd">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _PageConfig.getHideAd())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _PageConfig.getHideAd())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_HideAd_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_StyleId_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >


		<div id="pageConfigForm_styleId_field">
	    <div id="pageConfigForm_styleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="styleId" value="<%=WebUtil.display(_PageConfig.getStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_StyleId_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_ContentStyleSetId_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >


		<div id="pageConfigForm_contentStyleSetId_field">
	    <div id="pageConfigForm_contentStyleSetId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentStyleSetId" value="<%=WebUtil.display(_PageConfig.getContentStyleSetId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_ContentStyleSetId_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_HeaderMeta_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >


		<div id="pageConfigForm_headerMeta_field">
	    <div id="pageConfigForm_headerMeta_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="headerMeta" value="<%=WebUtil.display(_PageConfig.getHeaderMeta())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_HeaderMeta_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageConfigFormEditField_HeaderLink_<%=_PageConfig.getId()%>" method="get" action="/pageConfigAction.html" >


		<div id="pageConfigForm_headerLink_field">
	    <div id="pageConfigForm_headerLink_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="headerLink" value="<%=WebUtil.display(_PageConfig.getHeaderLink())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageConfigFormEditField_HeaderLink_<%=_PageConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_config_home">
	</form>
    
    
    </td>



    <td>
    <form name="pageConfigForm<%=_PageConfig.getId()%>2" method="get" action="/v_page_config_form.html" >
        <a href="javascript:document.pageConfigForm<%=_PageConfig.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageConfig.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="page_config_home">
    </form>
    </td>

    <td>
    <a href="/pageConfigAction.html?del=true&id=<%=_PageConfig.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>