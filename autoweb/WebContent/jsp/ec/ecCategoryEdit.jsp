<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    EcCategory _EcCategory = EcCategoryDS.getInstance().getById(id);

    if ( _EcCategory == null ) {
        return;
    }

    String _parent_idValue=  WebUtil.display(_EcCategory.getParentId());
    String _page_idValue=  WebUtil.display(_EcCategory.getPageId());
    String _category_nameValue=  WebUtil.display(_EcCategory.getCategoryName());
    String _image_urlValue=  WebUtil.display(_EcCategory.getImageUrl());
    String _category_descriptionValue=  WebUtil.display(_EcCategory.getCategoryDescription());
    String _alt1Value=  WebUtil.display(_EcCategory.getAlt1());
    String _alt2Value=  WebUtil.display(_EcCategory.getAlt2());
%> 

<br>
<form name="ecCategoryFormEdit" method="post" action="/ecCategoryAction.html" >
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
            <b><a href="javascript:document.ecCategoryFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcCategory.getId()%>">
</form>
