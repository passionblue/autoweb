<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	Page _Page = PageDS.getInstance().getById(id);

	if ( _Page == null ) {
		return;
	}

	String _menu_panel_idValue=  WebUtil.display(_Page.getMenuPanelId());
	String _parent_idValue=  WebUtil.display(_Page.getParentId());
	String _page_nameValue=  WebUtil.display(_Page.getPageName());
	String _page_menu_titleValue=  WebUtil.display(_Page.getPageMenuTitle());
	String _hideValue=  WebUtil.display(_Page.getHide());
	String _created_timeValue=  WebUtil.display(_Page.getCreatedTime());
	String _site_urlValue=  WebUtil.display(_Page.getSiteUrl());
	String _page_col_countValue=  WebUtil.display(_Page.getPageColCount());
	String _page_keywordsValue=  WebUtil.display(_Page.getPageKeywords());
	String _page_view_typeValue=  WebUtil.display(_Page.getPageViewType());
	String _underlying_pageValue=  WebUtil.display(_Page.getUnderlyingPage());
	String _header_pageValue=  WebUtil.display(_Page.getHeaderPage());
%> 

<br>
<form name="pageFormEdit" method="post" action="/pageAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

	
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Menu Panel Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="menuPanelId" value="<%=WebUtil.display(_menu_panel_idValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Parent Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="parentId" value="<%=WebUtil.display(_parent_idValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Page Name</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="pageName" value="<%=WebUtil.display(_page_nameValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Page Menu Title</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="pageMenuTitle" value="<%=WebUtil.display(_page_menu_titleValue)%>"/></TD>
    </TR>
			    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Hide</b> &nbsp;</TD>
        <TD>&nbsp;<select name="hide">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
            </select>
    </TD>
    </TR>
		
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Site Url</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="siteUrl" value="<%=WebUtil.display(_site_urlValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Page Col Count</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="pageColCount" value="<%=WebUtil.display(_page_col_countValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Page Keywords</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="pageKeywords" value="<%=WebUtil.display(_page_keywordsValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Page View Type</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="pageViewType" value="<%=WebUtil.display(_page_view_typeValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Underlying Page</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="underlyingPage" value="<%=WebUtil.display(_underlying_pageValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Header Page</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="headerPage" value="<%=WebUtil.display(_header_pageValue)%>"/></TD>
    </TR>
		    <TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.pageFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Page.getId()%>">
</form>
