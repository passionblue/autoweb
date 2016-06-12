<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    PageConfig _PageConfig = PageConfigDS.getInstance().getById(id);

    if ( _PageConfig == null ) {
        return;
    }

    String _page_idValue=  WebUtil.display(_PageConfig.getPageId());
    String _sort_typeValue=  WebUtil.display(_PageConfig.getSortType());
    String _arrange_typeValue=  WebUtil.display(_PageConfig.getArrangeType());
    String _page_cssValue=  WebUtil.display(_PageConfig.getPageCss());
    String _page_scriptValue=  WebUtil.display(_PageConfig.getPageScript());
    String _page_css_importsValue=  WebUtil.display(_PageConfig.getPageCssImports());
    String _page_script_importsValue=  WebUtil.display(_PageConfig.getPageScriptImports());
    String _hide_menuValue=  WebUtil.display(_PageConfig.getHideMenu());
    String _hide_midValue=  WebUtil.display(_PageConfig.getHideMid());
    String _hide_adValue=  WebUtil.display(_PageConfig.getHideAd());
    String _style_idValue=  WebUtil.display(_PageConfig.getStyleId());
    String _header_metaValue=  WebUtil.display(_PageConfig.getHeaderMeta());
    String _header_linkValue=  WebUtil.display(_PageConfig.getHeaderLink());
    String _time_createdValue=  WebUtil.display(_PageConfig.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_PageConfig.getTimeUpdated());
%> 

<br>
<form name="pageConfigFormEdit" method="post" action="/pageConfigAction.html" >
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
            <b><a href="javascript:document.pageConfigFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageConfig.getId()%>">
</form>
