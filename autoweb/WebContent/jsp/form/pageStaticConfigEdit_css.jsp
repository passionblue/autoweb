<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    PageStaticConfig _PageStaticConfig = PageStaticConfigDS.getInstance().getById(id);

    if ( _PageStaticConfig == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _page_aliasValue=  WebUtil.display(_PageStaticConfig.getPageAlias());
    String _page_cssValue=  WebUtil.display(_PageStaticConfig.getPageCss());
    String _page_scriptValue=  WebUtil.display(_PageStaticConfig.getPageScript());
    String _page_css_importsValue=  WebUtil.display(_PageStaticConfig.getPageCssImports());
    String _page_script_importsValue=  WebUtil.display(_PageStaticConfig.getPageScriptImports());
    String _hide_menuValue=  WebUtil.display(_PageStaticConfig.getHideMenu());
    String _hide_midValue=  WebUtil.display(_PageStaticConfig.getHideMid());
    String _hide_adValue=  WebUtil.display(_PageStaticConfig.getHideAd());
    String _time_createdValue=  WebUtil.display(_PageStaticConfig.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_PageStaticConfig.getTimeUpdated());
%> 

<br>
<div id="pageStaticConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pageStaticConfigFormEdit" method="get" action="/pageStaticConfigAction.html" >




	<div id="pageStaticConfigForm_pageAlias_field">
    <div id="pageStaticConfigForm_pageAlias_label" class="formRequiredLabel" >Page Alias* </div>
    <div id="pageStaticConfigForm_pageAlias_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="pageAlias" value="<%=WebUtil.display(_page_aliasValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="pageStaticConfigForm_pageCss_field">
    <div id="pageStaticConfigForm_pageCss_label" class="formLabel" >Page Css </div>
    <div id="pageStaticConfigForm_pageCss_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="pageCss" COLS="50" ROWS="8" ><%=WebUtil.display(_page_cssValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>


	<div id="pageStaticConfigForm_pageScript_field">
    <div id="pageStaticConfigForm_pageScript_label" class="formLabel" >Page Script </div>
    <div id="pageStaticConfigForm_pageScript_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="pageScript" COLS="50" ROWS="8" ><%=WebUtil.display(_page_scriptValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticConfigForm_pageCssImports_field">
    <div id="pageStaticConfigForm_pageCssImports_label" class="formLabel" >Page Css Imports </div>
    <div id="pageStaticConfigForm_pageCssImports_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="pageCssImports" value="<%=WebUtil.display(_page_css_importsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageStaticConfigForm_pageScriptImports_field">
    <div id="pageStaticConfigForm_pageScriptImports_label" class="formLabel" >Page Script Imports </div>
    <div id="pageStaticConfigForm_pageScriptImports_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="pageScriptImports" value="<%=WebUtil.display(_page_script_importsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="pageStaticConfigForm_hideMenu_field">
    <div id="pageStaticConfigForm_hideMenu_label" class="formLabel" >Hide Menu </div>
    <div id="pageStaticConfigForm_hideMenu_dropdown" class="formFieldDropDown" >       
        <select name="hideMenu">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_menuValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_menuValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticConfigForm_hideMid_field">
    <div id="pageStaticConfigForm_hideMid_label" class="formLabel" >Hide Mid </div>
    <div id="pageStaticConfigForm_hideMid_dropdown" class="formFieldDropDown" >       
        <select name="hideMid">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_midValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_midValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticConfigForm_hideAd_field">
    <div id="pageStaticConfigForm_hideAd_label" class="formLabel" >Hide Ad </div>
    <div id="pageStaticConfigForm_hideAd_dropdown" class="formFieldDropDown" >       
        <select name="hideAd">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_adValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_adValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>







        <div id="pageStaticConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pageStaticConfigFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticConfig.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
