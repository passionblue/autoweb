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
    String _header_metaValue= (reqParams.get("headerMeta")==null?WebUtil.display(_PageConfigDefault.getHeaderMeta()):WebUtil.display((String)reqParams.get("headerMeta")));
    String _header_linkValue= (reqParams.get("headerLink")==null?WebUtil.display(_PageConfigDefault.getHeaderLink()):WebUtil.display((String)reqParams.get("headerLink")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PageConfigDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PageConfigDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="pageConfigForm" method="post" action="/pageConfigAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Page Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="pageId" value="<%=WebUtil.display(_page_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Sort Type</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="sortType" value="<%=WebUtil.display(_sort_typeValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Arrange Type</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="arrangeType" value="<%=WebUtil.display(_arrange_typeValue)%>"/></TD>
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
	        <TD align="right" ><b>Style Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="styleId" value="<%=WebUtil.display(_style_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Header Meta</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="headerMeta" value="<%=WebUtil.display(_header_metaValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Header Link</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="headerLink" value="<%=WebUtil.display(_header_linkValue)%>"/></TD>
	    </TR>
	            	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.pageConfigForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


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
    <td> <%= WebUtil.display(_PageConfig.getHeaderMeta()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getHeaderLink()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_PageConfig.getTimeUpdated()) %></td>


<td>
<form name="pageConfigForm<%=_PageConfig.getId()%>" method="post" action="/v_page_config_edit.html" >
    <a href="javascript:document.pageConfigForm<%=_PageConfig.getId()%>.submit();">Edit</a>           
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