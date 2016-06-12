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

    PageStaticAlt _PageStaticAlt = PageStaticAltDS.getInstance().getById(id);

    if ( _PageStaticAlt == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _page_aliasValue=  WebUtil.display(_PageStaticAlt.getPageAlias());
    String _page_urlValue=  WebUtil.display(_PageStaticAlt.getPageUrl());
    String _menu_pageValue=  WebUtil.display(_PageStaticAlt.getMenuPage());
    String _error_pageValue=  WebUtil.display(_PageStaticAlt.getErrorPage());
    String _login_requiredValue=  WebUtil.display(_PageStaticAlt.getLoginRequired());
    String _view_procValue=  WebUtil.display(_PageStaticAlt.getViewProc());
    String _dynamic_contentValue=  WebUtil.display(_PageStaticAlt.getDynamicContent());
    String _hide_menuValue=  WebUtil.display(_PageStaticAlt.getHideMenu());
    String _hide_midValue=  WebUtil.display(_PageStaticAlt.getHideMid());
    String _hide_adValue=  WebUtil.display(_PageStaticAlt.getHideAd());
    String _time_createdValue=  WebUtil.display(_PageStaticAlt.getTimeCreated());
%> 

<br>
<div id="pageStaticAltForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pageStaticAltFormEdit" method="get" action="/pageStaticAltAction.html" >




	<div id="pageStaticAltForm_pageAlias_field">
    <div id="pageStaticAltForm_pageAlias_label" class="formRequiredLabel" >Page Alias* </div>
    <div id="pageStaticAltForm_pageAlias_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="pageAlias" value="<%=WebUtil.display(_page_aliasValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageStaticAltForm_pageUrl_field">
    <div id="pageStaticAltForm_pageUrl_label" class="formRequiredLabel" >Page Url* </div>
    <div id="pageStaticAltForm_pageUrl_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="pageUrl" value="<%=WebUtil.display(_page_urlValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageStaticAltForm_menuPage_field">
    <div id="pageStaticAltForm_menuPage_label" class="formLabel" >Menu Page </div>
    <div id="pageStaticAltForm_menuPage_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="menuPage" value="<%=WebUtil.display(_menu_pageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageStaticAltForm_errorPage_field">
    <div id="pageStaticAltForm_errorPage_label" class="formLabel" >Error Page </div>
    <div id="pageStaticAltForm_errorPage_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="errorPage" value="<%=WebUtil.display(_error_pageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageStaticAltForm_loginRequired_field">
    <div id="pageStaticAltForm_loginRequired_label" class="formLabel" >Login Required </div>
    <div id="pageStaticAltForm_loginRequired_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="loginRequired" value="<%=WebUtil.display(_login_requiredValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageStaticAltForm_viewProc_field">
    <div id="pageStaticAltForm_viewProc_label" class="formLabel" >View Proc </div>
    <div id="pageStaticAltForm_viewProc_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="viewProc" value="<%=WebUtil.display(_view_procValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageStaticAltForm_dynamicContent_field">
    <div id="pageStaticAltForm_dynamicContent_label" class="formLabel" >Dynamic Content </div>
    <div id="pageStaticAltForm_dynamicContent_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="dynamicContent" value="<%=WebUtil.display(_dynamic_contentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageStaticAltForm_hideMenu_field">
    <div id="pageStaticAltForm_hideMenu_label" class="formLabel" >Hide Menu </div>
    <div id="pageStaticAltForm_hideMenu_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="hideMenu" value="<%=WebUtil.display(_hide_menuValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageStaticAltForm_hideMid_field">
    <div id="pageStaticAltForm_hideMid_label" class="formLabel" >Hide Mid </div>
    <div id="pageStaticAltForm_hideMid_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="hideMid" value="<%=WebUtil.display(_hide_midValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageStaticAltForm_hideAd_field">
    <div id="pageStaticAltForm_hideAd_label" class="formLabel" >Hide Ad </div>
    <div id="pageStaticAltForm_hideAd_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="hideAd" value="<%=WebUtil.display(_hide_adValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="pageStaticAltFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pageStaticAltFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticAlt.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
