<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    PageStaticAlt _PageStaticAltDefault = new PageStaticAlt();// PageStaticAltDS.getInstance().getDeafult();
    
    String _page_aliasValue= (reqParams.get("pageAlias")==null?WebUtil.display(_PageStaticAltDefault.getPageAlias()):WebUtil.display((String)reqParams.get("pageAlias")));
    String _page_urlValue= (reqParams.get("pageUrl")==null?WebUtil.display(_PageStaticAltDefault.getPageUrl()):WebUtil.display((String)reqParams.get("pageUrl")));
    String _menu_pageValue= (reqParams.get("menuPage")==null?WebUtil.display(_PageStaticAltDefault.getMenuPage()):WebUtil.display((String)reqParams.get("menuPage")));
    String _error_pageValue= (reqParams.get("errorPage")==null?WebUtil.display(_PageStaticAltDefault.getErrorPage()):WebUtil.display((String)reqParams.get("errorPage")));
    String _login_requiredValue= (reqParams.get("loginRequired")==null?WebUtil.display(_PageStaticAltDefault.getLoginRequired()):WebUtil.display((String)reqParams.get("loginRequired")));
    String _view_procValue= (reqParams.get("viewProc")==null?WebUtil.display(_PageStaticAltDefault.getViewProc()):WebUtil.display((String)reqParams.get("viewProc")));
    String _dynamic_contentValue= (reqParams.get("dynamicContent")==null?WebUtil.display(_PageStaticAltDefault.getDynamicContent()):WebUtil.display((String)reqParams.get("dynamicContent")));
    String _hide_menuValue= (reqParams.get("hideMenu")==null?WebUtil.display(_PageStaticAltDefault.getHideMenu()):WebUtil.display((String)reqParams.get("hideMenu")));
    String _hide_midValue= (reqParams.get("hideMid")==null?WebUtil.display(_PageStaticAltDefault.getHideMid()):WebUtil.display((String)reqParams.get("hideMid")));
    String _hide_adValue= (reqParams.get("hideAd")==null?WebUtil.display(_PageStaticAltDefault.getHideAd()):WebUtil.display((String)reqParams.get("hideAd")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PageStaticAltDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="pageStaticAltForm_topArea" class="formTopArea"></div>
<div id="pageStaticAltForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="pageStaticAltForm" method="get" action="/pageStaticAltAction.html" >




	<div id="pageStaticAltForm_pageAlias_field">
    <div id="pageStaticAltForm_pageAlias_label" class="formRequiredLabel" >Page Alias* </div>
    <div id="pageStaticAltForm_pageAlias_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="pageAlias" value="<%=WebUtil.display(_page_aliasValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticAltForm_pageUrl_field">
    <div id="pageStaticAltForm_pageUrl_label" class="formRequiredLabel" >Page Url* </div>
    <div id="pageStaticAltForm_pageUrl_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="pageUrl" value="<%=WebUtil.display(_page_urlValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticAltForm_menuPage_field">
    <div id="pageStaticAltForm_menuPage_label" class="formLabel" >Menu Page </div>
    <div id="pageStaticAltForm_menuPage_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="menuPage" value="<%=WebUtil.display(_menu_pageValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticAltForm_errorPage_field">
    <div id="pageStaticAltForm_errorPage_label" class="formLabel" >Error Page </div>
    <div id="pageStaticAltForm_errorPage_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="errorPage" value="<%=WebUtil.display(_error_pageValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticAltForm_loginRequired_field">
    <div id="pageStaticAltForm_loginRequired_label" class="formLabel" >Login Required </div>
    <div id="pageStaticAltForm_loginRequired_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="loginRequired" value="<%=WebUtil.display(_login_requiredValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticAltForm_viewProc_field">
    <div id="pageStaticAltForm_viewProc_label" class="formLabel" >View Proc </div>
    <div id="pageStaticAltForm_viewProc_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="viewProc" value="<%=WebUtil.display(_view_procValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticAltForm_dynamicContent_field">
    <div id="pageStaticAltForm_dynamicContent_label" class="formLabel" >Dynamic Content </div>
    <div id="pageStaticAltForm_dynamicContent_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="dynamicContent" value="<%=WebUtil.display(_dynamic_contentValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticAltForm_hideMenu_field">
    <div id="pageStaticAltForm_hideMenu_label" class="formLabel" >Hide Menu </div>
    <div id="pageStaticAltForm_hideMenu_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="hideMenu" value="<%=WebUtil.display(_hide_menuValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticAltForm_hideMid_field">
    <div id="pageStaticAltForm_hideMid_label" class="formLabel" >Hide Mid </div>
    <div id="pageStaticAltForm_hideMid_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="hideMid" value="<%=WebUtil.display(_hide_midValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStaticAltForm_hideAd_field">
    <div id="pageStaticAltForm_hideAd_label" class="formLabel" >Hide Ad </div>
    <div id="pageStaticAltForm_hideAd_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="hideAd" value="<%=WebUtil.display(_hide_adValue)%>"/>
    </div>      
	</div><div class="clear"></div>





        <div id="pageStaticAltForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.pageStaticAltForm.submit();">Cancel</a>
        </div>      

        <div id="pageStaticAltForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pageStaticAltForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="pageStaticAltForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PageStaticAltDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageStaticAlt _PageStaticAlt = (PageStaticAlt) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PageStaticAlt.getId() %> </td>

    <td> <%= WebUtil.display(_PageStaticAlt.getPageAlias()) %></td>
    <td> <%= WebUtil.display(_PageStaticAlt.getPageUrl()) %></td>
    <td> <%= WebUtil.display(_PageStaticAlt.getMenuPage()) %></td>
    <td> <%= WebUtil.display(_PageStaticAlt.getErrorPage()) %></td>
    <td> <%= WebUtil.display(_PageStaticAlt.getLoginRequired()) %></td>
    <td> <%= WebUtil.display(_PageStaticAlt.getViewProc()) %></td>
    <td> <%= WebUtil.display(_PageStaticAlt.getDynamicContent()) %></td>
    <td> <%= WebUtil.display(_PageStaticAlt.getHideMenu()) %></td>
    <td> <%= WebUtil.display(_PageStaticAlt.getHideMid()) %></td>
    <td> <%= WebUtil.display(_PageStaticAlt.getHideAd()) %></td>
    <td> <%= WebUtil.display(_PageStaticAlt.getTimeCreated()) %></td>


<td>
<form name="pageStaticAltForm<%=_PageStaticAlt.getId()%>" method="get" action="/v_page_static_alt_edit.html" >
    <a href="javascript:document.pageStaticAltForm<%=_PageStaticAlt.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageStaticAlt.getId() %>">
</form>
<form name="pageStaticAltForm<%=_PageStaticAlt.getId()%>2" method="get" action="/v_page_static_alt_edit2.html" >
    <a href="javascript:document.pageStaticAltForm<%=_PageStaticAlt.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageStaticAlt.getId() %>">
</form>

</td>
<td>
<a href="/pageStaticAltAction.html?del=true&id=<%=_PageStaticAlt.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>