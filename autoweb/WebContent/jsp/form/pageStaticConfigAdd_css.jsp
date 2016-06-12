<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    PageStaticConfig _PageStaticConfigDefault = new PageStaticConfig();// PageStaticConfigDS.getInstance().getDeafult();
    
    String _page_aliasValue= (reqParams.get("pageAlias")==null?WebUtil.display(_PageStaticConfigDefault.getPageAlias()):WebUtil.display((String)reqParams.get("pageAlias")));
    String _page_cssValue= (reqParams.get("pageCss")==null?WebUtil.display(_PageStaticConfigDefault.getPageCss()):WebUtil.display((String)reqParams.get("pageCss")));
    String _page_scriptValue= (reqParams.get("pageScript")==null?WebUtil.display(_PageStaticConfigDefault.getPageScript()):WebUtil.display((String)reqParams.get("pageScript")));
    String _page_css_importsValue= (reqParams.get("pageCssImports")==null?WebUtil.display(_PageStaticConfigDefault.getPageCssImports()):WebUtil.display((String)reqParams.get("pageCssImports")));
    String _page_script_importsValue= (reqParams.get("pageScriptImports")==null?WebUtil.display(_PageStaticConfigDefault.getPageScriptImports()):WebUtil.display((String)reqParams.get("pageScriptImports")));
    String _hide_menuValue= (reqParams.get("hideMenu")==null?WebUtil.display(_PageStaticConfigDefault.getHideMenu()):WebUtil.display((String)reqParams.get("hideMenu")));
    String _hide_midValue= (reqParams.get("hideMid")==null?WebUtil.display(_PageStaticConfigDefault.getHideMid()):WebUtil.display((String)reqParams.get("hideMid")));
    String _hide_adValue= (reqParams.get("hideAd")==null?WebUtil.display(_PageStaticConfigDefault.getHideAd()):WebUtil.display((String)reqParams.get("hideAd")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PageStaticConfigDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PageStaticConfigDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="pageStaticConfigForm_topArea" class="formTopArea"></div>
<div id="pageStaticConfigForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="pageStaticConfigForm" method="get" action="/pageStaticConfigAction.html" >




	<div id="pageStaticConfigForm_pageAlias_field">
    <div id="pageStaticConfigForm_pageAlias_label" class="formRequiredLabel" >Page Alias* </div>
    <div id="pageStaticConfigForm_pageAlias_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="pageAlias" value="<%=WebUtil.display(_page_aliasValue)%>"/> 
    </div>      
	</div><div class="clear"></div>



	<div id="pageStaticConfigForm_pageCss_field">
    <div id="pageStaticConfigForm_pageCss_label" class="formLabel" >Page Css </div>
    <div id="pageStaticConfigForm_pageCss_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="pageCss" COLS="50" ROWS="8" ><%=WebUtil.display(_page_cssValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticConfigForm_pageScript_field">
    <div id="pageStaticConfigForm_pageScript_label" class="formLabel" >Page Script </div>
    <div id="pageStaticConfigForm_pageScript_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="pageScript" COLS="50" ROWS="8" ><%=WebUtil.display(_page_scriptValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>





	<div id="pageStaticConfigForm_pageCssImports_field">
    <div id="pageStaticConfigForm_pageCssImports_label" class="formLabel" >Page Css Imports </div>
    <div id="pageStaticConfigForm_pageCssImports_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="pageCssImports" value="<%=WebUtil.display(_page_css_importsValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticConfigForm_pageScriptImports_field">
    <div id="pageStaticConfigForm_pageScriptImports_label" class="formLabel" >Page Script Imports </div>
    <div id="pageStaticConfigForm_pageScriptImports_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="pageScriptImports" value="<%=WebUtil.display(_page_script_importsValue)%>"/>
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










        <div id="pageStaticConfigForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.pageStaticConfigForm.submit();">Cancel</a>
        </div>      

        <div id="pageStaticConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pageStaticConfigForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="pageStaticConfigForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PageStaticConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageStaticConfig _PageStaticConfig = (PageStaticConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PageStaticConfig.getId() %> </td>

    <td> <%= WebUtil.display(_PageStaticConfig.getPageAlias()) %></td>
    <td> <%= WebUtil.display(_PageStaticConfig.getPageCss()) %></td>
    <td> <%= WebUtil.display(_PageStaticConfig.getPageScript()) %></td>
    <td> <%= WebUtil.display(_PageStaticConfig.getPageCssImports()) %></td>
    <td> <%= WebUtil.display(_PageStaticConfig.getPageScriptImports()) %></td>
    <td> <%= WebUtil.display(_PageStaticConfig.getHideMenu()) %></td>
    <td> <%= WebUtil.display(_PageStaticConfig.getHideMid()) %></td>
    <td> <%= WebUtil.display(_PageStaticConfig.getHideAd()) %></td>
    <td> <%= WebUtil.display(_PageStaticConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_PageStaticConfig.getTimeUpdated()) %></td>


<td>
<form name="pageStaticConfigForm<%=_PageStaticConfig.getId()%>" method="get" action="/v_page_static_config_edit.html" >
    <a href="javascript:document.pageStaticConfigForm<%=_PageStaticConfig.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageStaticConfig.getId() %>">
</form>
<form name="pageStaticConfigForm<%=_PageStaticConfig.getId()%>2" method="get" action="/v_page_static_config_edit2.html" >
    <a href="javascript:document.pageStaticConfigForm<%=_PageStaticConfig.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageStaticConfig.getId() %>">
</form>

</td>
<td>
<a href="/pageStaticConfigAction.html?del=true&id=<%=_PageStaticConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>