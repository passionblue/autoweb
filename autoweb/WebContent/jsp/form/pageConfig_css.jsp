<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    PageConfig _PageConfig = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_PageConfig = PageConfigDS.getInstance().getById(id);
		if ( _PageConfig == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _PageConfig = new PageConfig();// PageConfigDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    

    String _page_idValue= (reqParams.get("pageId")==null?WebUtil.display(_PageConfig.getPageId()):WebUtil.display((String)reqParams.get("pageId")));
    String _sort_typeValue= (reqParams.get("sortType")==null?WebUtil.display(_PageConfig.getSortType()):WebUtil.display((String)reqParams.get("sortType")));
    String _arrange_typeValue= (reqParams.get("arrangeType")==null?WebUtil.display(_PageConfig.getArrangeType()):WebUtil.display((String)reqParams.get("arrangeType")));
    String _page_cssValue= (reqParams.get("pageCss")==null?WebUtil.display(_PageConfig.getPageCss()):WebUtil.display((String)reqParams.get("pageCss")));
    String _page_scriptValue= (reqParams.get("pageScript")==null?WebUtil.display(_PageConfig.getPageScript()):WebUtil.display((String)reqParams.get("pageScript")));
    String _page_css_importsValue= (reqParams.get("pageCssImports")==null?WebUtil.display(_PageConfig.getPageCssImports()):WebUtil.display((String)reqParams.get("pageCssImports")));
    String _page_script_importsValue= (reqParams.get("pageScriptImports")==null?WebUtil.display(_PageConfig.getPageScriptImports()):WebUtil.display((String)reqParams.get("pageScriptImports")));
    String _hide_menuValue= (reqParams.get("hideMenu")==null?WebUtil.display(_PageConfig.getHideMenu()):WebUtil.display((String)reqParams.get("hideMenu")));
    String _hide_midValue= (reqParams.get("hideMid")==null?WebUtil.display(_PageConfig.getHideMid()):WebUtil.display((String)reqParams.get("hideMid")));
    String _hide_adValue= (reqParams.get("hideAd")==null?WebUtil.display(_PageConfig.getHideAd()):WebUtil.display((String)reqParams.get("hideAd")));
    String _style_idValue= (reqParams.get("styleId")==null?WebUtil.display(_PageConfig.getStyleId()):WebUtil.display((String)reqParams.get("styleId")));
    String _content_style_set_idValue= (reqParams.get("contentStyleSetId")==null?WebUtil.display(_PageConfig.getContentStyleSetId()):WebUtil.display((String)reqParams.get("contentStyleSetId")));
    String _header_metaValue= (reqParams.get("headerMeta")==null?WebUtil.display(_PageConfig.getHeaderMeta()):WebUtil.display((String)reqParams.get("headerMeta")));
    String _header_linkValue= (reqParams.get("headerLink")==null?WebUtil.display(_PageConfig.getHeaderLink()):WebUtil.display((String)reqParams.get("headerLink")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PageConfig.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PageConfig.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
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
        <input id="field" type="text" size="70" name="contentStyleSetId" value="<%=WebUtil.display(_content_style_set_idValue)%>"/> <span></span>
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

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>


<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
