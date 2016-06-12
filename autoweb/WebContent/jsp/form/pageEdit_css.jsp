<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    Page _Page = PageDS.getInstance().getById(id);

    if ( _Page == null ) {
        return;
    }

    String _menu_panel_idValue=  WebUtil.display(_Page.getMenuPanelId());
    String _parent_idValue=  WebUtil.display(_Page.getParentId());
    String _page_nameValue=  WebUtil.display(_Page.getPageName());
    String _page_menu_titleValue=  WebUtil.display(_Page.getPageMenuTitle());
    String _hideValue=  WebUtil.display(_Page.getHide());
    String _created_timeValue=  WebUtil.display(_Page.getCreatedTime());
    String _site_urlValue=  WebUtil.display(_Page.getSiteUrl());
    String _page_col_countValue=  WebUtil.display(_Page.getPageColCount());
    String _page_keywordsValue=  WebUtil.display(_Page.getPageKeywords());
    String _page_view_typeValue=  WebUtil.display(_Page.getPageViewType());
    String _underlying_pageValue=  WebUtil.display(_Page.getUnderlyingPage());
    String _header_pageValue=  WebUtil.display(_Page.getHeaderPage());
    String _show_page_exclusive_onlyValue=  WebUtil.display(_Page.getShowPageExclusiveOnly());
    String _alt1Value=  WebUtil.display(_Page.getAlt1());
    String _alt2Value=  WebUtil.display(_Page.getAlt2());
    String _alt3Value=  WebUtil.display(_Page.getAlt3());
    
    //>>START<<
    
    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    
	String retPage = (String) reqParams.get("returnPage");    
    
    
    //>>END<<
%> 

<br>
<div id="pageForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pageFormEdit" method="get" action="/pageAction.html" >




	<div id="pageForm_menuPanelId_field">
    <div id="pageForm_menuPanelId_label" class="formLabel" >Menu Panel Id </div>
    <div id="pageForm_menuPanelId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="menuPanelId" value="<%=WebUtil.display(_menu_panel_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageForm_parentId_field">
    <div id="pageForm_parentId_label" class="formLabel" >Parent Id </div>
    <div id="pageForm_parentId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="parentId" value="<%=WebUtil.display(_parent_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageForm_pageName_field">
    <div id="pageForm_pageName_label" class="formLabel" >Page Name </div>
    <div id="pageForm_pageName_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="pageName" value="<%=WebUtil.display(_page_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageForm_pageMenuTitle_field">
    <div id="pageForm_pageMenuTitle_label" class="formLabel" >Page Menu Title </div>
    <div id="pageForm_pageMenuTitle_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="pageMenuTitle" value="<%=WebUtil.display(_page_menu_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="pageForm_hide_field">
    <div id="pageForm_hide_label" class="formLabel" >Hide </div>
    <div id="pageForm_hide_dropdown" class="formFieldDropDown" >       
        <select name="hide">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>







	<div id="pageForm_siteUrl_field">
    <div id="pageForm_siteUrl_label" class="formLabel" >Site Url </div>
    <div id="pageForm_siteUrl_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="siteUrl" value="<%=WebUtil.display(_site_urlValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageForm_pageColCount_field">
    <div id="pageForm_pageColCount_label" class="formLabel" >Page Col Count </div>
    <div id="pageForm_pageColCount_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="pageColCount" value="<%=WebUtil.display(_page_col_countValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageForm_pageKeywords_field">
    <div id="pageForm_pageKeywords_label" class="formLabel" >Page Keywords </div>
    <div id="pageForm_pageKeywords_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="pageKeywords" value="<%=WebUtil.display(_page_keywordsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="pageForm_pageViewType_field">
    <div id="pageForm_pageViewType_label" class="formLabel" >Page View Type </div>
    <div id="pageForm_pageViewType_dropdown" class="formFieldDropDown" >       
        <select id="field" name="pageViewType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _page_view_typeValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _page_view_typeValue)%>>0</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _page_view_typeValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _page_view_typeValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _page_view_typeValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _page_view_typeValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _page_view_typeValue)%>>5</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="pageForm_underlyingPage_field">
    <div id="pageForm_underlyingPage_label" class="formLabel" >Underlying Page </div>
    <div id="pageForm_underlyingPage_dropdown" class="formFieldDropDown" >       
        <select id="field" name="underlyingPage">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _underlying_pageValue)%>>XX</option-->
		<option value="dynamic_menu_content" <%=HtmlUtil.getOptionSelect("", _underlying_pageValue)%>>Default</option>
		<option value="dynamic_menu_content_two" <%=HtmlUtil.getOptionSelect("dynamic_menu_content_two", _underlying_pageValue)%>>Dynamic Two</option>
		<option value="dynamic_menu_content_three" <%=HtmlUtil.getOptionSelect("dynamic_menu_content_three", _underlying_pageValue)%>>Dynamic Tree</option>
		<option value="dynamic_menu_content_single_content" <%=HtmlUtil.getOptionSelect("dynamic_menu_content_single_content", _underlying_pageValue)%>>Single Content page</option>
		<option value="ec_product_display" <%=HtmlUtil.getOptionSelect("ec_product_display", _underlying_pageValue)%>>EC Product Display</option>
        
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageForm_headerPage_field">
    <div id="pageForm_headerPage_label" class="formLabel" >Header Page </div>
    <div id="pageForm_headerPage_dropdown" class="formFieldDropDown" >       
        <select name="headerPage">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _header_pageValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _header_pageValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="pageForm_showPageExclusiveOnly_field">
    <div id="pageForm_showPageExclusiveOnly_label" class="formLabel" >Show Page Exclusive Only </div>
    <div id="pageForm_showPageExclusiveOnly_dropdown" class="formFieldDropDown" >       
        <select name="showPageExclusiveOnly">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _show_page_exclusive_onlyValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _show_page_exclusive_onlyValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="pageForm_alt1_field">
    <div id="pageForm_alt1_label" class="formLabel" >Alt1 </div>
    <div id="pageForm_alt1_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="alt1" value="<%=WebUtil.display(_alt1Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageForm_alt2_field">
    <div id="pageForm_alt2_label" class="formLabel" >Alt2 </div>
    <div id="pageForm_alt2_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="alt2" value="<%=WebUtil.display(_alt2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageForm_alt3_field">
    <div id="pageForm_alt3_label" class="formLabel" >Alt3 </div>
    <div id="pageForm_alt3_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="alt3" value="<%=WebUtil.display(_alt3Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="pageFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pageFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Page.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
