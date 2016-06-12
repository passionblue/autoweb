<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
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

    PageConfig _PageConfig = PageConfigDS.getInstance().getById(id);

    if (_PageConfig == null){
        _PageConfig = PageConfigDS.getInstance().getById(WebParamUtil.getLongValue((String) reqParams.get("id")));
    }
    
    
    if ( _PageConfig == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    

    String _page_idValue=  WebUtil.display(_PageConfig.getPageId());
    String _sort_typeValue=  WebUtil.display(_PageConfig.getSortType());
    String _arrange_typeValue=  WebUtil.display(_PageConfig.getArrangeType());
    String _page_cssValue=  WebUtil.display(_PageConfig.getPageCss());
    String _page_scriptValue=  WebUtil.display(_PageConfig.getPageScript());
    String _page_css_importsValue=  WebUtil.display(_PageConfig.getPageCssImports());
    String _page_script_importsValue=  WebUtil.display(_PageConfig.getPageScriptImports());
    String _hide_menuValue=  WebUtil.display(_PageConfig.getHideMenu());
    String _hide_midValue=  WebUtil.display(_PageConfig.getHideMid());
    String _hide_adValue=  WebUtil.display(_PageConfig.getHideAd());
    String _style_idValue=  WebUtil.display(_PageConfig.getStyleId());
    String _content_style_set_idValue=  WebUtil.display(_PageConfig.getContentStyleSetId());
    String _header_metaValue=  WebUtil.display(_PageConfig.getHeaderMeta());
    String _header_linkValue=  WebUtil.display(_PageConfig.getHeaderLink());
    String _time_createdValue=  WebUtil.display(_PageConfig.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_PageConfig.getTimeUpdated());
%> 

<br>
<div id="pageConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pageConfigFormEdit" method="POST" action="/pageConfigAction.html" >


    <INPUT TYPE="HIDDEN" NAME="pageId" value="<%=WebUtil.display(_page_idValue)%>" />

	<div id="pageConfigForm_sortType_field">
    <div id="pageConfigForm_sortType_label" class="formLabel" >Sort Type </div>
    <div id="pageConfigForm_sortType_dropdown" class="formFieldDropDown" >       
        <select id="field" name="sortType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _sort_typeValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="pageConfigForm_arrangeType_field">
    <div id="pageConfigForm_arrangeType_label" class="formLabel" >Arrange Type </div>
    <div id="pageConfigForm_arrangeType_dropdown" class="formFieldDropDown" >       
        <select id="field" name="arrangeType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _arrange_typeValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="pageConfigForm_pageCss_field">
    <div id="pageConfigForm_pageCss_label" class="formLabel" >Page Css </div>
    <div id="pageConfigForm_pageCss_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="pageCss" COLS="50" ROWS="8" ><%=WebUtil.display(_page_cssValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>


	<div id="pageConfigForm_pageScript_field">
    <div id="pageConfigForm_pageScript_label" class="formLabel" >Page Script </div>
    <div id="pageConfigForm_pageScript_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="pageScript" COLS="50" ROWS="8" ><%=WebUtil.display(_page_scriptValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




	<div id="pageConfigForm_pageCssImports_field">
    <div id="pageConfigForm_pageCssImports_label" class="formLabel" >Page Css Imports </div>
    <div id="pageConfigForm_pageCssImports_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="pageCssImports" value="<%=WebUtil.display(_page_css_importsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageConfigForm_pageScriptImports_field">
    <div id="pageConfigForm_pageScriptImports_label" class="formLabel" >Page Script Imports </div>
    <div id="pageConfigForm_pageScriptImports_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="pageScriptImports" value="<%=WebUtil.display(_page_script_importsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="pageConfigForm_hideMenu_field">
    <div id="pageConfigForm_hideMenu_label" class="formLabel" >Hide Menu </div>
    <div id="pageConfigForm_hideMenu_dropdown" class="formFieldDropDown" >       
        <select name="hideMenu">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_menuValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_menuValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="pageConfigForm_hideMid_field">
    <div id="pageConfigForm_hideMid_label" class="formLabel" >Hide Mid </div>
    <div id="pageConfigForm_hideMid_dropdown" class="formFieldDropDown" >       
        <select name="hideMid">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_midValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_midValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="pageConfigForm_hideAd_field">
    <div id="pageConfigForm_hideAd_label" class="formLabel" >Hide Ad </div>
    <div id="pageConfigForm_hideAd_dropdown" class="formFieldDropDown" >       
        <select name="hideAd">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_adValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_adValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="pageConfigForm_styleId_field">
    <div id="pageConfigForm_styleId_label" class="formLabel" >Style Id </div>
    <div id="pageConfigForm_styleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="styleId" value="<%=WebUtil.display(_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageConfigForm_contentStyleSetId_field">
    <div id="pageConfigForm_contentStyleSetId_label" class="formLabel" >Content Style Set Id </div>
    <div id="pageConfigForm_contentStyleSetId_text" class="formFieldText" >       
        
        <select class="field" name="contentStyleSetId" id="contentStyleSetId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleSetContent_contentStyleSetId = StyleSetContentDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleSetContent_contentStyleSetId.iterator(); iter.hasNext();){
		StyleSetContent _obj = (StyleSetContent) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_content_style_set_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  
        
<%
	StyleSetContent ssc = StyleSetContentDS.getInstance().getById(_PageConfig.getContentStyleSetId());
	if (ssc != null) {

%>        
        <a href="/v_style_config_edit.html?id=<%=ssc.getListSubjectStyleId() %>&prv_returnPage=page_config_edit,id=<%=_PageConfig.getId() %>"> Edit</a> |
        <a href="/v_style_config_edit.html?id=<%=ssc.getListDataStyleId() %>&prv_returnPage=page_config_edit,id=<%=_PageConfig.getId() %>"> Edit</a> |
        <a href="/v_style_config_edit.html?id=<%=ssc.getSubjectStyleId() %>&prv_returnPage=page_config_edit,id=<%=_PageConfig.getId() %>"> Edit</a> |
        <a href="/v_style_config_edit.html?id=<%=ssc.getDataStyleId() %>&prv_returnPage=page_config_edit,id=<%=_PageConfig.getId() %>"> Edit</a>
<%
	}
%>        
        
        <span></span>
        
    </div>      
	</div><div class="clear"></div>



	<div id="pageConfigForm_headerMeta_field">
    <div id="pageConfigForm_headerMeta_label" class="formLabel" >Header Meta </div>
    <div id="pageConfigForm_headerMeta_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="headerMeta" value="<%=WebUtil.display(_header_metaValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageConfigForm_headerLink_field">
    <div id="pageConfigForm_headerLink_label" class="formLabel" >Header Link </div>
    <div id="pageConfigForm_headerLink_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="headerLink" value="<%=WebUtil.display(_header_linkValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="pageConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pageConfigFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
