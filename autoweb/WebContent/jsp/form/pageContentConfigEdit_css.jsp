<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    PageContentConfig _PageContentConfig = PageContentConfigDS.getInstance().getById(id);

    if ( _PageContentConfig == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "page_content_config_home";

    String _content_idValue=  WebUtil.display(_PageContentConfig.getContentId());
    String _content_typeValue=  WebUtil.display(_PageContentConfig.getContentType());
    String _sort_typeValue=  WebUtil.display(_PageContentConfig.getSortType());
    String _arrange_typeValue=  WebUtil.display(_PageContentConfig.getArrangeType());
    String _page_cssValue=  WebUtil.display(_PageContentConfig.getPageCss());
    String _page_scriptValue=  WebUtil.display(_PageContentConfig.getPageScript());
    String _page_css_importsValue=  WebUtil.display(_PageContentConfig.getPageCssImports());
    String _page_script_importsValue=  WebUtil.display(_PageContentConfig.getPageScriptImports());
    String _hide_menuValue=  WebUtil.display(_PageContentConfig.getHideMenu());
    String _hide_midValue=  WebUtil.display(_PageContentConfig.getHideMid());
    String _hide_adValue=  WebUtil.display(_PageContentConfig.getHideAd());
    String _style_idValue=  WebUtil.display(_PageContentConfig.getStyleId());
    String _content_style_set_idValue=  WebUtil.display(_PageContentConfig.getContentStyleSetId());
    String _header_metaValue=  WebUtil.display(_PageContentConfig.getHeaderMeta());
    String _header_linkValue=  WebUtil.display(_PageContentConfig.getHeaderLink());
    String _time_createdValue=  WebUtil.display(_PageContentConfig.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_PageContentConfig.getTimeUpdated());
%> 

<br>
<div id="pageContentConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pageContentConfigFormEdit" method="POST" action="/pageContentConfigAction.html" >


    <INPUT TYPE="HIDDEN" NAME="contentId" value="<%=WebUtil.display(_content_idValue)%>" />


	<div id="pageContentConfigForm_contentType_field">
    <div id="pageContentConfigForm_contentType_label" class="formLabel" >Content Type </div>
    <div id="pageContentConfigForm_contentType_dropdown" class="formFieldDropDown" >       
        <select id="field" name="contentType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _content_typeValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _content_typeValue)%>>Default</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _content_typeValue)%>>Blog</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _content_typeValue)%>>Forum Content</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _content_typeValue)%>>TBD</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _content_typeValue)%>>TBD</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_sortType_field">
    <div id="pageContentConfigForm_sortType_label" class="formLabel" >Sort Type </div>
    <div id="pageContentConfigForm_sortType_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="sortType" value="<%=WebUtil.display(_sort_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageContentConfigForm_arrangeType_field">
    <div id="pageContentConfigForm_arrangeType_label" class="formLabel" >Arrange Type </div>
    <div id="pageContentConfigForm_arrangeType_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="arrangeType" value="<%=WebUtil.display(_arrange_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="pageContentConfigForm_pageCss_field">
    <div id="pageContentConfigForm_pageCss_label" class="formLabel" >Page Css </div>
    <div id="pageContentConfigForm_pageCss_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="pageCss" COLS="50" ROWS="8" ><%=WebUtil.display(_page_cssValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>

	<div id="pageContentConfigForm_pageScript_field">
    <div id="pageContentConfigForm_pageScript_label" class="formLabel" >Page Script </div>
    <div id="pageContentConfigForm_pageScript_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="pageScript" COLS="50" ROWS="8" ><%=WebUtil.display(_page_scriptValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="pageContentConfigForm_pageCssImports_field">
    <div id="pageContentConfigForm_pageCssImports_label" class="formLabel" >Page Css Imports </div>
    <div id="pageContentConfigForm_pageCssImports_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="pageCssImports" value="<%=WebUtil.display(_page_css_importsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageContentConfigForm_pageScriptImports_field">
    <div id="pageContentConfigForm_pageScriptImports_label" class="formLabel" >Page Script Imports </div>
    <div id="pageContentConfigForm_pageScriptImports_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="pageScriptImports" value="<%=WebUtil.display(_page_script_importsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="pageContentConfigForm_hideMenu_field">
    <div id="pageContentConfigForm_hideMenu_label" class="formLabel" >Hide Menu </div>
    <div id="pageContentConfigForm_hideMenu_dropdown" class="formFieldDropDown" >       
        <select name="hideMenu">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_menuValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_menuValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_hideMid_field">
    <div id="pageContentConfigForm_hideMid_label" class="formLabel" >Hide Mid </div>
    <div id="pageContentConfigForm_hideMid_dropdown" class="formFieldDropDown" >       
        <select name="hideMid">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_midValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_midValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_hideAd_field">
    <div id="pageContentConfigForm_hideAd_label" class="formLabel" >Hide Ad </div>
    <div id="pageContentConfigForm_hideAd_dropdown" class="formFieldDropDown" >       
        <select name="hideAd">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_adValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_adValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="pageContentConfigForm_styleId_field">
    <div id="pageContentConfigForm_styleId_label" class="formLabel" >Style Id </div>
    <div id="pageContentConfigForm_styleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="styleId" value="<%=WebUtil.display(_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageContentConfigForm_contentStyleSetId_field">
    <div id="pageContentConfigForm_contentStyleSetId_label" class="formLabel" >Content Style Set Id </div>
    <div id="pageContentConfigForm_contentStyleSetId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="contentStyleSetId" value="<%=WebUtil.display(_content_style_set_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageContentConfigForm_headerMeta_field">
    <div id="pageContentConfigForm_headerMeta_label" class="formLabel" >Header Meta </div>
    <div id="pageContentConfigForm_headerMeta_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="headerMeta" value="<%=WebUtil.display(_header_metaValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageContentConfigForm_headerLink_field">
    <div id="pageContentConfigForm_headerLink_label" class="formLabel" >Header Link </div>
    <div id="pageContentConfigForm_headerLink_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="headerLink" value="<%=WebUtil.display(_header_linkValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="pageContentConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pageContentConfigFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
