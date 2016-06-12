<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    PageContentConfig _PageContentConfig = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_PageContentConfig = PageContentConfigDS.getInstance().getById(id);
		if ( _PageContentConfig == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _PageContentConfig = new PageContentConfig();// PageContentConfigDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "page_content_config_home";

    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_PageContentConfig.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _content_typeValue= (reqParams.get("contentType")==null?WebUtil.display(_PageContentConfig.getContentType()):WebUtil.display((String)reqParams.get("contentType")));
    String _sort_typeValue= (reqParams.get("sortType")==null?WebUtil.display(_PageContentConfig.getSortType()):WebUtil.display((String)reqParams.get("sortType")));
    String _arrange_typeValue= (reqParams.get("arrangeType")==null?WebUtil.display(_PageContentConfig.getArrangeType()):WebUtil.display((String)reqParams.get("arrangeType")));
    String _page_cssValue= (reqParams.get("pageCss")==null?WebUtil.display(_PageContentConfig.getPageCss()):WebUtil.display((String)reqParams.get("pageCss")));
    String _page_scriptValue= (reqParams.get("pageScript")==null?WebUtil.display(_PageContentConfig.getPageScript()):WebUtil.display((String)reqParams.get("pageScript")));
    String _page_css_importsValue= (reqParams.get("pageCssImports")==null?WebUtil.display(_PageContentConfig.getPageCssImports()):WebUtil.display((String)reqParams.get("pageCssImports")));
    String _page_script_importsValue= (reqParams.get("pageScriptImports")==null?WebUtil.display(_PageContentConfig.getPageScriptImports()):WebUtil.display((String)reqParams.get("pageScriptImports")));
    String _hide_menuValue= (reqParams.get("hideMenu")==null?WebUtil.display(_PageContentConfig.getHideMenu()):WebUtil.display((String)reqParams.get("hideMenu")));
    String _hide_midValue= (reqParams.get("hideMid")==null?WebUtil.display(_PageContentConfig.getHideMid()):WebUtil.display((String)reqParams.get("hideMid")));
    String _hide_adValue= (reqParams.get("hideAd")==null?WebUtil.display(_PageContentConfig.getHideAd()):WebUtil.display((String)reqParams.get("hideAd")));
    String _style_idValue= (reqParams.get("styleId")==null?WebUtil.display(_PageContentConfig.getStyleId()):WebUtil.display((String)reqParams.get("styleId")));
    String _content_style_set_idValue= (reqParams.get("contentStyleSetId")==null?WebUtil.display(_PageContentConfig.getContentStyleSetId()):WebUtil.display((String)reqParams.get("contentStyleSetId")));
    String _header_metaValue= (reqParams.get("headerMeta")==null?WebUtil.display(_PageContentConfig.getHeaderMeta()):WebUtil.display((String)reqParams.get("headerMeta")));
    String _header_linkValue= (reqParams.get("headerLink")==null?WebUtil.display(_PageContentConfig.getHeaderLink()):WebUtil.display((String)reqParams.get("headerLink")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PageContentConfig.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PageContentConfig.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
%> 

<br>
<div id="pageContentConfigForm" class="formFrame">
<div id="pageContentConfigFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pageContentConfigForm_Form" method="POST" action="/pageContentConfigAction.html" id="pageContentConfigForm_Form">



    <INPUT TYPE="HIDDEN" NAME="contentId" value="<%=WebUtil.display(_content_idValue)%>" />



	<div id="pageContentConfigForm_contentType_field" class="formFieldFrame">
    <div id="pageContentConfigForm_contentType_label" class="formLabel" >Content Type </div>
    <div id="pageContentConfigForm_contentType_dropdown" class="formFieldDropDown" >       
        <select class="field" name="contentType" id="contentType">
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





	<div id="pageContentConfigForm_sortType_field" class="formFieldFrame">
    <div id="pageContentConfigForm_sortType_label" class="formLabel" >Sort Type </div>
    <div id="pageContentConfigForm_sortType_text" class="formFieldText" >       
        <input id="sortType" class="field" type="text" size="70" name="sortType" value="<%=WebUtil.display(_sort_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_arrangeType_field" class="formFieldFrame">
    <div id="pageContentConfigForm_arrangeType_label" class="formLabel" >Arrange Type </div>
    <div id="pageContentConfigForm_arrangeType_text" class="formFieldText" >       
        <input id="arrangeType" class="field" type="text" size="70" name="arrangeType" value="<%=WebUtil.display(_arrange_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="pageContentConfigForm_pageCss_field" class="formFieldFrame">
    <div id="pageContentConfigForm_pageCss_label" class="formLabel" >Page Css </div>
    <div id="pageContentConfigForm_pageCss_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="pageCss" class="field" NAME="pageCss" COLS="50" ROWS="8" ><%=WebUtil.display(_page_cssValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="pageContentConfigForm_pageScript_field" class="formFieldFrame">
    <div id="pageContentConfigForm_pageScript_label" class="formLabel" >Page Script </div>
    <div id="pageContentConfigForm_pageScript_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="pageScript" class="field" NAME="pageScript" COLS="50" ROWS="8" ><%=WebUtil.display(_page_scriptValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>





	<div id="pageContentConfigForm_pageCssImports_field" class="formFieldFrame">
    <div id="pageContentConfigForm_pageCssImports_label" class="formLabel" >Page Css Imports </div>
    <div id="pageContentConfigForm_pageCssImports_text" class="formFieldText" >       
        <input id="pageCssImports" class="field" type="text" size="70" name="pageCssImports" value="<%=WebUtil.display(_page_css_importsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_pageScriptImports_field" class="formFieldFrame">
    <div id="pageContentConfigForm_pageScriptImports_label" class="formLabel" >Page Script Imports </div>
    <div id="pageContentConfigForm_pageScriptImports_text" class="formFieldText" >       
        <input id="pageScriptImports" class="field" type="text" size="70" name="pageScriptImports" value="<%=WebUtil.display(_page_script_importsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageContentConfigForm_hideMenu_field" class="formFieldFrame">
    <div id="pageContentConfigForm_hideMenu_label" class="formLabel" >Hide Menu </div>
    <div id="pageContentConfigForm_hideMenu_dropdown" class="formFieldDropDown" >       
        <select name="hideMenu">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_menuValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_menuValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_hideMid_field" class="formFieldFrame">
    <div id="pageContentConfigForm_hideMid_label" class="formLabel" >Hide Mid </div>
    <div id="pageContentConfigForm_hideMid_dropdown" class="formFieldDropDown" >       
        <select name="hideMid">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_midValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_midValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_hideAd_field" class="formFieldFrame">
    <div id="pageContentConfigForm_hideAd_label" class="formLabel" >Hide Ad </div>
    <div id="pageContentConfigForm_hideAd_dropdown" class="formFieldDropDown" >       
        <select name="hideAd">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_adValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_adValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="pageContentConfigForm_styleId_field" class="formFieldFrame">
    <div id="pageContentConfigForm_styleId_label" class="formLabel" >Style Id </div>
    <div id="pageContentConfigForm_styleId_text" class="formFieldText" >       
        <input id="styleId" class="field" type="text" size="70" name="styleId" value="<%=WebUtil.display(_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="pageContentConfigForm_contentStyleSetId_field" class="formFieldFrame">
    <div id="pageContentConfigForm_contentStyleSetId_label" class="formLabel" >Content Style Set Id </div>
    <div id="pageContentConfigForm_contentStyleSetId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="contentStyleSetId" id="contentStyleSetId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleSetContent_contentStyleSetId = StyleSetContentDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleSetContent_contentStyleSetId.iterator(); iter.hasNext();){
		StyleSetContent _obj = (StyleSetContent) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_content_style_set_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_headerMeta_field" class="formFieldFrame">
    <div id="pageContentConfigForm_headerMeta_label" class="formLabel" >Header Meta </div>
    <div id="pageContentConfigForm_headerMeta_text" class="formFieldText" >       
        <input id="headerMeta" class="field" type="text" size="70" name="headerMeta" value="<%=WebUtil.display(_header_metaValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="pageContentConfigForm_headerLink_field" class="formFieldFrame">
    <div id="pageContentConfigForm_headerLink_label" class="formLabel" >Header Link </div>
    <div id="pageContentConfigForm_headerLink_text" class="formFieldText" >       
        <input id="headerLink" class="field" type="text" size="70" name="headerLink" value="<%=WebUtil.display(_header_linkValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div class="submitFrame">

        <div id="pageContentConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit2" href="javascript:document.pageContentConfigForm_Form.submit();">Submit</a>
        </div>      

        <div id="pageContentConfigForm_submit_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="pageContentConfigForm_submit_ext" class="formSubmitExt" >       
            <a href="#">Ext</a>
        </div>      
	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageContentConfig.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">

<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> 
</div> <!-- form -->


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Content Id </td> 
    <td class="columnTitle">  Content Type </td> 
    <td class="columnTitle">  Sort Type </td> 
    <td class="columnTitle">  Arrange Type </td> 
    <td class="columnTitle">  Page Css </td> 
    <td class="columnTitle">  Page Script </td> 
    <td class="columnTitle">  Page Css Imports </td> 
    <td class="columnTitle">  Page Script Imports </td> 
    <td class="columnTitle">  Hide Menu </td> 
    <td class="columnTitle">  Hide Mid </td> 
    <td class="columnTitle">  Hide Ad </td> 
    <td class="columnTitle">  Style Id </td> 
    <td class="columnTitle">  Content Style Set Id </td> 
    <td class="columnTitle">  Header Meta </td> 
    <td class="columnTitle">  Header Link </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = PageContentConfigDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageContentConfig _oPageContentConfig = (PageContentConfig) iter.next();
%>

<TR>
    <td> <%= _oPageContentConfig.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oPageContentConfig.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oPageContentConfig.getContentId()  %> </td>
	<td> <%= _oPageContentConfig.getContentType()  %> </td>
	<td> <%= _oPageContentConfig.getSortType()  %> </td>
	<td> <%= _oPageContentConfig.getArrangeType()  %> </td>
	<td> <%= _oPageContentConfig.getPageCss()  %> </td>
	<td> <%= _oPageContentConfig.getPageScript()  %> </td>
	<td> <%= _oPageContentConfig.getPageCssImports()  %> </td>
	<td> <%= _oPageContentConfig.getPageScriptImports()  %> </td>
	<td> <%= _oPageContentConfig.getHideMenu()  %> </td>
	<td> <%= _oPageContentConfig.getHideMid()  %> </td>
	<td> <%= _oPageContentConfig.getHideAd()  %> </td>
	<td> <%= _oPageContentConfig.getStyleId()  %> </td>
	<td> <%= _oPageContentConfig.getContentStyleSetId()  %> </td>
	<td> <%= _oPageContentConfig.getHeaderMeta()  %> </td>
	<td> <%= _oPageContentConfig.getHeaderLink()  %> </td>
	<td> <%= _oPageContentConfig.getTimeCreated()  %> </td>
	<td> <%= _oPageContentConfig.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/pageContentConfigAction.html?del=true&id=<%=_oPageContentConfig.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
</script>