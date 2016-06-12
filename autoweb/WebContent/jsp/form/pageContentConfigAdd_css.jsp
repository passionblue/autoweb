<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    PageContentConfig _PageContentConfigDefault = new PageContentConfig();// PageContentConfigDS.getInstance().getDeafult();
    
    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_PageContentConfigDefault.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _content_typeValue= (reqParams.get("contentType")==null?WebUtil.display(_PageContentConfigDefault.getContentType()):WebUtil.display((String)reqParams.get("contentType")));
    String _sort_typeValue= (reqParams.get("sortType")==null?WebUtil.display(_PageContentConfigDefault.getSortType()):WebUtil.display((String)reqParams.get("sortType")));
    String _arrange_typeValue= (reqParams.get("arrangeType")==null?WebUtil.display(_PageContentConfigDefault.getArrangeType()):WebUtil.display((String)reqParams.get("arrangeType")));
    String _page_cssValue= (reqParams.get("pageCss")==null?WebUtil.display(_PageContentConfigDefault.getPageCss()):WebUtil.display((String)reqParams.get("pageCss")));
    String _page_scriptValue= (reqParams.get("pageScript")==null?WebUtil.display(_PageContentConfigDefault.getPageScript()):WebUtil.display((String)reqParams.get("pageScript")));
    String _page_css_importsValue= (reqParams.get("pageCssImports")==null?WebUtil.display(_PageContentConfigDefault.getPageCssImports()):WebUtil.display((String)reqParams.get("pageCssImports")));
    String _page_script_importsValue= (reqParams.get("pageScriptImports")==null?WebUtil.display(_PageContentConfigDefault.getPageScriptImports()):WebUtil.display((String)reqParams.get("pageScriptImports")));
    String _hide_menuValue= (reqParams.get("hideMenu")==null?WebUtil.display(_PageContentConfigDefault.getHideMenu()):WebUtil.display((String)reqParams.get("hideMenu")));
    String _hide_midValue= (reqParams.get("hideMid")==null?WebUtil.display(_PageContentConfigDefault.getHideMid()):WebUtil.display((String)reqParams.get("hideMid")));
    String _hide_adValue= (reqParams.get("hideAd")==null?WebUtil.display(_PageContentConfigDefault.getHideAd()):WebUtil.display((String)reqParams.get("hideAd")));
    String _style_idValue= (reqParams.get("styleId")==null?WebUtil.display(_PageContentConfigDefault.getStyleId()):WebUtil.display((String)reqParams.get("styleId")));
    String _content_style_set_idValue= (reqParams.get("contentStyleSetId")==null?WebUtil.display(_PageContentConfigDefault.getContentStyleSetId()):WebUtil.display((String)reqParams.get("contentStyleSetId")));
    String _header_metaValue= (reqParams.get("headerMeta")==null?WebUtil.display(_PageContentConfigDefault.getHeaderMeta()):WebUtil.display((String)reqParams.get("headerMeta")));
    String _header_linkValue= (reqParams.get("headerLink")==null?WebUtil.display(_PageContentConfigDefault.getHeaderLink()):WebUtil.display((String)reqParams.get("headerLink")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PageContentConfigDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PageContentConfigDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "page_content_config_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="pageContentConfigForm_topArea" class="formTopArea"></div>
<div id="pageContentConfigForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="pageContentConfigForm" method="POST" action="/pageContentConfigAction.html" >



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
    <div id="pageContentConfigForm_sortType_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="sortType" value="<%=WebUtil.display(_sort_typeValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_arrangeType_field">
    <div id="pageContentConfigForm_arrangeType_label" class="formLabel" >Arrange Type </div>
    <div id="pageContentConfigForm_arrangeType_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="arrangeType" value="<%=WebUtil.display(_arrange_typeValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="pageContentConfigForm_pageCss_field">
    <div id="pageContentConfigForm_pageCss_label" class="formLabel" >Page Css </div>
    <div id="pageContentConfigForm_pageCss_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="pageCss" COLS="50" ROWS="8" ><%=WebUtil.display(_page_cssValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_pageScript_field">
    <div id="pageContentConfigForm_pageScript_label" class="formLabel" >Page Script </div>
    <div id="pageContentConfigForm_pageScript_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="pageScript" COLS="50" ROWS="8" ><%=WebUtil.display(_page_scriptValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>





	<div id="pageContentConfigForm_pageCssImports_field">
    <div id="pageContentConfigForm_pageCssImports_label" class="formLabel" >Page Css Imports </div>
    <div id="pageContentConfigForm_pageCssImports_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="pageCssImports" value="<%=WebUtil.display(_page_css_importsValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_pageScriptImports_field">
    <div id="pageContentConfigForm_pageScriptImports_label" class="formLabel" >Page Script Imports </div>
    <div id="pageContentConfigForm_pageScriptImports_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="pageScriptImports" value="<%=WebUtil.display(_page_script_importsValue)%>"/>
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
    <div id="pageContentConfigForm_styleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="styleId" value="<%=WebUtil.display(_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_contentStyleSetId_field">
    <div id="pageContentConfigForm_contentStyleSetId_label" class="formLabel" >Content Style Set Id </div>
    <div id="pageContentConfigForm_contentStyleSetId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="contentStyleSetId" value="<%=WebUtil.display(_content_style_set_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_headerMeta_field">
    <div id="pageContentConfigForm_headerMeta_label" class="formLabel" >Header Meta </div>
    <div id="pageContentConfigForm_headerMeta_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="headerMeta" value="<%=WebUtil.display(_header_metaValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_headerLink_field">
    <div id="pageContentConfigForm_headerLink_label" class="formLabel" >Header Link </div>
    <div id="pageContentConfigForm_headerLink_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="headerLink" value="<%=WebUtil.display(_header_linkValue)%>"/>
    </div>      
	</div><div class="clear"></div>









        <div id="pageContentConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pageContentConfigForm.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      
            

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="pageContentConfigForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PageContentConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageContentConfig _PageContentConfig = (PageContentConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PageContentConfig.getId() %> </td>

    <td> <%= WebUtil.display(_PageContentConfig.getContentId()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getContentType()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getSortType()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getArrangeType()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getPageCss()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getPageScript()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getPageCssImports()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getPageScriptImports()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getHideMenu()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getHideMid()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getHideAd()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getStyleId()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getContentStyleSetId()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getHeaderMeta()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getHeaderLink()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_PageContentConfig.getTimeUpdated()) %></td>


<td>
<form name="pageContentConfigForm<%=_PageContentConfig.getId()%>2" method="get" action="/v_page_content_config_edit2.html" >
    <a href="javascript:document.pageContentConfigForm<%=_PageContentConfig.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageContentConfig.getId() %>">
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