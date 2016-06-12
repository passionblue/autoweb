<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    ContentCategory _ContentCategory = ContentCategoryDS.getInstance().getById(id);

    if ( _ContentCategory == null ) {
        return;
    }

    String _page_idValue=  WebUtil.display(_ContentCategory.getPageId());
    String _categoryValue=  WebUtil.display(_ContentCategory.getCategory());
    String _image_urlValue=  WebUtil.display(_ContentCategory.getImageUrl());
    String _time_createdValue=  WebUtil.display(_ContentCategory.getTimeCreated());
%> 

<br>
<form name="contentCategoryFormEdit" method="post" action="/contentCategoryAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Page Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="pageId" value="<%=WebUtil.display(_page_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Category</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="category" value="<%=WebUtil.display(_categoryValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Image Url</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="imageUrl" value="<%=WebUtil.display(_image_urlValue)%>"/></TD>
    </TR>
            
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.contentCategoryFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentCategory.getId()%>">
</form>
