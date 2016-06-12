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

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="pageStaticAltForm" method="post" action="/pageStaticAltAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Page Alias</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="pageAlias" value="<%=WebUtil.display(_page_aliasValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Page Url</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="pageUrl" value="<%=WebUtil.display(_page_urlValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Menu Page</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="menuPage" value="<%=WebUtil.display(_menu_pageValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Error Page</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="errorPage" value="<%=WebUtil.display(_error_pageValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Login Required</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="loginRequired" value="<%=WebUtil.display(_login_requiredValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>View Proc</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="viewProc" value="<%=WebUtil.display(_view_procValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Dynamic Content</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="dynamicContent" value="<%=WebUtil.display(_dynamic_contentValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Hide Menu</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="hideMenu" value="<%=WebUtil.display(_hide_menuValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Hide Mid</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="hideMid" value="<%=WebUtil.display(_hide_midValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Hide Ad</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="hideAd" value="<%=WebUtil.display(_hide_adValue)%>"/></TD>
	    </TR>
	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.pageStaticAltForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


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
<form name="pageStaticAltForm<%=_PageStaticAlt.getId()%>" method="post" action="/v_page_static_alt_edit.html" >
    <a href="javascript:document.pageStaticAltForm<%=_PageStaticAlt.getId()%>.submit();">Edit</a>           
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