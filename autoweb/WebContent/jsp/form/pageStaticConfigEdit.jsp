<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    PageStaticConfig _PageStaticConfig = PageStaticConfigDS.getInstance().getById(id);

    if ( _PageStaticConfig == null ) {
        return;
    }

    String _page_aliasValue=  WebUtil.display(_PageStaticConfig.getPageAlias());
    String _page_cssValue=  WebUtil.display(_PageStaticConfig.getPageCss());
    String _page_scriptValue=  WebUtil.display(_PageStaticConfig.getPageScript());
    String _page_css_importsValue=  WebUtil.display(_PageStaticConfig.getPageCssImports());
    String _page_script_importsValue=  WebUtil.display(_PageStaticConfig.getPageScriptImports());
    String _hide_menuValue=  WebUtil.display(_PageStaticConfig.getHideMenu());
    String _hide_midValue=  WebUtil.display(_PageStaticConfig.getHideMid());
    String _hide_adValue=  WebUtil.display(_PageStaticConfig.getHideAd());
    String _time_createdValue=  WebUtil.display(_PageStaticConfig.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_PageStaticConfig.getTimeUpdated());
%> 

<br>
<form name="pageStaticConfigFormEdit" method="post" action="/pageStaticConfigAction.html" >
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
            <b><a href="javascript:document.pageStaticConfigFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticConfig.getId()%>">
</form>
