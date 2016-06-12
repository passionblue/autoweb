<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _parent_idValue= "";
    String _page_idValue= "";
    String _category_nameValue= "";
    String _image_urlValue= "";
    String _category_descriptionValue= "";
    String _alt1Value= "";
    String _alt2Value= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_ec_category_add.html"> Add New</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

    List list = EcCategoryDS.getInstance().getBySiteId(site.getId());
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcCategory _EcCategory = (EcCategory) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcCategory.getId() %> </td>

    <td> <%= WebUtil.display(_EcCategory.getParentId()) %></td>
    <td> <%= WebUtil.display(_EcCategory.getPageId()) %></td>
    <td> <%= WebUtil.display(_EcCategory.getCategoryName()) %></td>
    <td> <%= WebUtil.display(_EcCategory.getImageUrl()) %></td>
    <td> <%= WebUtil.display(_EcCategory.getCategoryDescription()) %></td>
    <td> <%= WebUtil.display(_EcCategory.getAlt1()) %></td>
    <td> <%= WebUtil.display(_EcCategory.getAlt2()) %></td>

    <td>
    <form name="ecCategoryForm<%=_EcCategory.getId()%>" method="post" action="/v_ec_category_edit.html" >
        <a href="javascript:document.ecCategoryForm<%=_EcCategory.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcCategory.getId() %>">
    </form>
    </td>

    <td>
    <a href="/ecCategoryAction.html?del=true&id=<%=_EcCategory.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>