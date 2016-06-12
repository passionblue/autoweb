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

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="pageStaticConfigForm" method="post" action="/pageStaticConfigAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Page Alias</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="pageAlias" value="<%=WebUtil.display(_page_aliasValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Page Css</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="pageCss" value="<%=WebUtil.display(_page_cssValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Page Script</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="pageScript" value="<%=WebUtil.display(_page_scriptValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Page Css Imports</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="pageCssImports" value="<%=WebUtil.display(_page_css_importsValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Page Script Imports</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="pageScriptImports" value="<%=WebUtil.display(_page_script_importsValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Hide Menu</b> &nbsp;</TD>
        <TD>&nbsp;<select name="hideMenu">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_menuValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_menuValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD align="right" ><b>Hide Mid</b> &nbsp;</TD>
        <TD>&nbsp;<select name="hideMid">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_midValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_midValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD align="right" ><b>Hide Ad</b> &nbsp;</TD>
        <TD>&nbsp;<select name="hideAd">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_adValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_adValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.pageStaticConfigForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


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
<form name="pageStaticConfigForm<%=_PageStaticConfig.getId()%>" method="post" action="/v_page_static_config_edit.html" >
    <a href="javascript:document.pageStaticConfigForm<%=_PageStaticConfig.getId()%>.submit();">Edit</a>           
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