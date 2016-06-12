<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

    List list = BlogPostDS.getInstance().getByBlogMainId(blogMainId);


%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_blog_category_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="blogCategoryForm_blogMainId_label" style="font-size: normal normal bold 10px verdana;" >Blog Main Id </div>
    </td> 
    <td> 
	    <div id="blogCategoryForm_parentCategoryId_label" style="font-size: normal normal bold 10px verdana;" >Parent Category Id </div>
    </td> 
    <td> 
	    <div id="blogCategoryForm_title_label" style="font-size: normal normal bold 10px verdana;" >Title </div>
    </td> 
    <td> 
	    <div id="blogCategoryForm_hide_label" style="font-size: normal normal bold 10px verdana;" >Hide </div>
    </td> 
    <td> 
	    <div id="blogCategoryForm_imageUrl1_label" style="font-size: normal normal bold 10px verdana;" >Image Url1 </div>
    </td> 
    <td> 
	    <div id="blogCategoryForm_imageUrl2_label" style="font-size: normal normal bold 10px verdana;" >Image Url2 </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogCategory _BlogCategory = (BlogCategory) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogCategory.getId() %> </td>


    <td> 
	<form name="blogCategoryFormEditField_BlogMainId_<%=_BlogCategory.getId()%>" method="get" action="/blogCategoryAction.html" >


		<div id="blogCategoryForm_blogMainId_field">
	    <div id="blogCategoryForm_blogMainId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="blogMainId" value="<%=WebUtil.display(_BlogCategory.getBlogMainId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCategoryFormEditField_BlogMainId_<%=_BlogCategory.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogCategory.getId()%>">
	</form>
    
    
    </td>

    <td> 
	<form name="blogCategoryFormEditField_ParentCategoryId_<%=_BlogCategory.getId()%>" method="get" action="/blogCategoryAction.html" >


		<div id="blogCategoryForm_parentCategoryId_field">
	    <div id="blogCategoryForm_parentCategoryId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="parentCategoryId" value="<%=WebUtil.display(_BlogCategory.getParentCategoryId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCategoryFormEditField_ParentCategoryId_<%=_BlogCategory.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogCategory.getId()%>">
	</form>
    
    
    </td>


    <td> 
	<form name="blogCategoryFormEditField_Title_<%=_BlogCategory.getId()%>" method="get" action="/blogCategoryAction.html" >


		<div id="blogCategoryForm_title_field">
	    <div id="blogCategoryForm_title_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="title" value="<%=WebUtil.display(_BlogCategory.getTitle())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCategoryFormEditField_Title_<%=_BlogCategory.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogCategory.getId()%>">
	</form>
    
    
    </td>

    <td> 
	<form name="blogCategoryFormEditField_Hide_<%=_BlogCategory.getId()%>" method="get" action="/blogCategoryAction.html" >


		<div id="blogCategoryForm_hide_field">
	    <div id="blogCategoryForm_hide_dropdown" class="formFieldDropDown" >       
	        <select name="hide">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _BlogCategory.getHide())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _BlogCategory.getHide())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCategoryFormEditField_Hide_<%=_BlogCategory.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogCategory.getId()%>">
	</form>
    
    
    </td>

    <td> 
	<form name="blogCategoryFormEditField_ImageUrl1_<%=_BlogCategory.getId()%>" method="get" action="/blogCategoryAction.html" >


		<div id="blogCategoryForm_imageUrl1_field">
	    <div id="blogCategoryForm_imageUrl1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="imageUrl1" value="<%=WebUtil.display(_BlogCategory.getImageUrl1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCategoryFormEditField_ImageUrl1_<%=_BlogCategory.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogCategory.getId()%>">
	</form>
    
    
    </td>

    <td> 
	<form name="blogCategoryFormEditField_ImageUrl2_<%=_BlogCategory.getId()%>" method="get" action="/blogCategoryAction.html" >


		<div id="blogCategoryForm_imageUrl2_field">
	    <div id="blogCategoryForm_imageUrl2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="imageUrl2" value="<%=WebUtil.display(_BlogCategory.getImageUrl2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCategoryFormEditField_ImageUrl2_<%=_BlogCategory.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogCategory.getId()%>">
	</form>
    
    
    </td>


    <td>
    <form name="blogCategoryForm<%=_BlogCategory.getId()%>" method="get" action="/v_blog_category_edit.html" >
        <a href="javascript:document.blogCategoryForm<%=_BlogCategory.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogCategory.getId() %>">
    </form>
    <form name="blogCategoryForm<%=_BlogCategory.getId()%>2" method="get" action="/v_blog_category_edit2.html" >
        <a href="javascript:document.blogCategoryForm<%=_BlogCategory.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogCategory.getId() %>">
    </form>
    </td>

    <td>
    <a href="/blogCategoryAction.html?del=true&id=<%=_BlogCategory.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>