<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    PageConfig _PageConfigDefault = new PageConfig();// PageConfigDS.getInstance().getDeafult();
    
    String _page_idValue= (reqParams.get("pageId")==null?WebUtil.display(_PageConfigDefault.getPageId()):WebUtil.display((String)reqParams.get("pageId")));
    String _sort_typeValue= (reqParams.get("sortType")==null?WebUtil.display(_PageConfigDefault.getSortType()):WebUtil.display((String)reqParams.get("sortType")));
    String _arrange_typeValue= (reqParams.get("arrangeType")==null?WebUtil.display(_PageConfigDefault.getArrangeType()):WebUtil.display((String)reqParams.get("arrangeType")));
    String _page_cssValue= (reqParams.get("pageCss")==null?WebUtil.display(_PageConfigDefault.getPageCss()):WebUtil.display((String)reqParams.get("pageCss")));
    String _page_scriptValue= (reqParams.get("pageScript")==null?WebUtil.display(_PageConfigDefault.getPageScript()):WebUtil.display((String)reqParams.get("pageScript")));
    String _page_css_importsValue= (reqParams.get("pageCssImports")==null?WebUtil.display(_PageConfigDefault.getPageCssImports()):WebUtil.display((String)reqParams.get("pageCssImports")));
    String _page_script_importsValue= (reqParams.get("pageScriptImports")==null?WebUtil.display(_PageConfigDefault.getPageScriptImports()):WebUtil.display((String)reqParams.get("pageScriptImports")));
    String _hide_menuValue= (reqParams.get("hideMenu")==null?WebUtil.display(_PageConfigDefault.getHideMenu()):WebUtil.display((String)reqParams.get("hideMenu")));
    String _hide_midValue= (reqParams.get("hideMid")==null?WebUtil.display(_PageConfigDefault.getHideMid()):WebUtil.display((String)reqParams.get("hideMid")));
    String _hide_adValue= (reqParams.get("hideAd")==null?WebUtil.display(_PageConfigDefault.getHideAd()):WebUtil.display((String)reqParams.get("hideAd")));
    String _style_idValue= (reqParams.get("styleId")==null?WebUtil.display(_PageConfigDefault.getStyleId()):WebUtil.display((String)reqParams.get("styleId")));
    String _content_style_set_idValue= (reqParams.get("contentStyleSetId")==null?WebUtil.display(_PageConfigDefault.getContentStyleSetId()):WebUtil.display((String)reqParams.get("contentStyleSetId")));
    String _header_metaValue= (reqParams.get("headerMeta")==null?WebUtil.display(_PageConfigDefault.getHeaderMeta()):WebUtil.display((String)reqParams.get("headerMeta")));
    String _header_linkValue= (reqParams.get("headerLink")==null?WebUtil.display(_PageConfigDefault.getHeaderLink()):WebUtil.display((String)reqParams.get("headerLink")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PageConfigDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PageConfigDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="pageConfigForm_topArea" class="formTopArea"></div>
<div id="pageConfigForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="pageConfigForm" method="POST" action="/pageConfigAction.html" >



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
    <div id="pageConfigForm_pageCss_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="pageCss" COLS="50" ROWS="8" ><%=WebUtil.display(_page_cssValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




	<div id="pageConfigForm_pageScript_field">
    <div id="pageConfigForm_pageScript_label" class="formLabel" >Page Script </div>
    <div id="pageConfigForm_pageScript_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="pageScript" COLS="50" ROWS="8" ><%=WebUtil.display(_page_scriptValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>





	<div id="pageConfigForm_pageCssImports_field">
    <div id="pageConfigForm_pageCssImports_label" class="formLabel" >Page Css Imports </div>
    <div id="pageConfigForm_pageCssImports_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="pageCssImports" value="<%=WebUtil.display(_page_css_importsValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageConfigForm_pageScriptImports_field">
    <div id="pageConfigForm_pageScriptImports_label" class="formLabel" >Page Script Imports </div>
    <div id="pageConfigForm_pageScriptImports_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="pageScriptImports" value="<%=WebUtil.display(_page_script_importsValue)%>"/>
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
    <div id="pageConfigForm_styleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="styleId" value="<%=WebUtil.display(_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageConfigForm_contentStyleSetId_field">
    <div id="pageConfigForm_contentStyleSetId_label" class="formLabel" >Content Style Set Id </div>
    <div id="pageConfigForm_contentStyleSetId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="contentStyleSetId" value="<%=WebUtil.display(_content_style_set_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageConfigForm_headerMeta_field">
    <div id="pageConfigForm_headerMeta_label" class="formLabel" >Header Meta </div>
    <div id="pageConfigForm_headerMeta_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="headerMeta" value="<%=WebUtil.display(_header_metaValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageConfigForm_headerLink_field">
    <div id="pageConfigForm_headerLink_label" class="formLabel" >Header Link </div>
    <div id="pageConfigForm_headerLink_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="headerLink" value="<%=WebUtil.display(_header_linkValue)%>"/>
    </div>      
	</div><div class="clear"></div>









        <div id="pageConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pageConfigForm.submit();">Submit</a>
        </div>      

        <div id="pageConfigForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.pageConfigForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="pageConfigForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PageConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageConfig _PageConfig = (PageConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PageConfig.getId() %> </td>

    <td> <%= WebUtil.display(_PageConfig.getPageId()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getSortType()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getArrangeType()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getPageCss()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getPageScript()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getPageCssImports()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getPageScriptImports()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getHideMenu()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getHideMid()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getHideAd()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getStyleId()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getContentStyleSetId()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getHeaderMeta()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getHeaderLink()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getTimeUpdated()) %></td>


<td>
<form name="pageConfigForm<%=_PageConfig.getId()%>2" method="get" action="/v_page_config_edit2.html" >
    <a href="javascript:document.pageConfigForm<%=_PageConfig.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageConfig.getId() %>">
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