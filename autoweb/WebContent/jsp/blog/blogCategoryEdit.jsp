<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    BlogCategory _BlogCategory = BlogCategoryDS.getInstance().getById(id);

    if ( _BlogCategory == null ) {
        return;
    }

    String _blog_main_idValue=  WebUtil.display(_BlogCategory.getBlogMainId());
    String _parent_category_idValue=  WebUtil.display(_BlogCategory.getParentCategoryId());
    String _root_category_idValue=  WebUtil.display(_BlogCategory.getRootCategoryId());
    String _titleValue=  WebUtil.display(_BlogCategory.getTitle());
    String _hideValue=  WebUtil.display(_BlogCategory.getHide());
    String _image_url1Value=  WebUtil.display(_BlogCategory.getImageUrl1());
    String _image_url2Value=  WebUtil.display(_BlogCategory.getImageUrl2());
    String _time_createdValue=  WebUtil.display(_BlogCategory.getTimeCreated());
%> 

<br>
<form name="blogCategoryFormEdit" method="post" action="/blogCategoryAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Blog Main Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Parent Category Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="parentCategoryId" value="<%=WebUtil.display(_parent_category_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Root Category Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="rootCategoryId" value="<%=WebUtil.display(_root_category_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Title</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="title" value="<%=WebUtil.display(_titleValue)%>"/></TD>
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
        <TD align="right" ><b>Image Url1</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="imageUrl1" value="<%=WebUtil.display(_image_url1Value)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Image Url2</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="imageUrl2" value="<%=WebUtil.display(_image_url2Value)%>"/></TD>
    </TR>
            
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.blogCategoryFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogCategory.getId()%>">
</form>
