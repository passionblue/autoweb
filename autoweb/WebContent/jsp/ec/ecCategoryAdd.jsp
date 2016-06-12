<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcCategory _EcCategoryDefault = new EcCategory();// EcCategoryDS.getInstance().getDeafult();
    
    String _parent_idValue= (reqParams.get("parentId")==null?WebUtil.display(_EcCategoryDefault.getParentId()):WebUtil.display((String)reqParams.get("parentId")));
    String _page_idValue= (reqParams.get("pageId")==null?WebUtil.display(_EcCategoryDefault.getPageId()):WebUtil.display((String)reqParams.get("pageId")));
    String _category_nameValue= (reqParams.get("categoryName")==null?WebUtil.display(_EcCategoryDefault.getCategoryName()):WebUtil.display((String)reqParams.get("categoryName")));
    String _image_urlValue= (reqParams.get("imageUrl")==null?WebUtil.display(_EcCategoryDefault.getImageUrl()):WebUtil.display((String)reqParams.get("imageUrl")));
    String _category_descriptionValue= (reqParams.get("categoryDescription")==null?WebUtil.display(_EcCategoryDefault.getCategoryDescription()):WebUtil.display((String)reqParams.get("categoryDescription")));
    String _alt1Value= (reqParams.get("alt1")==null?WebUtil.display(_EcCategoryDefault.getAlt1()):WebUtil.display((String)reqParams.get("alt1")));
    String _alt2Value= (reqParams.get("alt2")==null?WebUtil.display(_EcCategoryDefault.getAlt2()):WebUtil.display((String)reqParams.get("alt2")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="ecCategoryForm" method="post" action="/ecCategoryAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

            <TR bgcolor="#ffffff">
        <TD align="right" ><b>Parent Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="parentId" value="<%=WebUtil.display(_parent_idValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Page Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="pageId" value="<%=WebUtil.display(_page_idValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Category Name</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="categoryName" value="<%=WebUtil.display(_category_nameValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Image Url</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="imageUrl" value="<%=WebUtil.display(_image_urlValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Category Description</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="categoryDescription" value="<%=WebUtil.display(_category_descriptionValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Alt1</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="alt1" value="<%=WebUtil.display(_alt1Value)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Alt2</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="alt2" value="<%=WebUtil.display(_alt2Value)%>"/></TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecCategoryForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

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