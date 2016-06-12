<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    PageStaticAlt _PageStaticAlt = PageStaticAltDS.getInstance().getById(id);

    if ( _PageStaticAlt == null ) {
        return;
    }

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
<form name="pageStaticAltFormEdit" method="post" action="/pageStaticAltAction.html" >
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
            <b><a href="javascript:document.pageStaticAltFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticAlt.getId()%>">
</form>
