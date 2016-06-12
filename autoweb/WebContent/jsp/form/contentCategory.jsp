<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	ContentCategoryDS ds = ContentCategoryDS.getInstance();    
    


%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_content_category_add2.html?prv_returnPage=content_category_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="contentCategoryForm_pageId_label" style="font-size: normal normal bold 10px verdana;" >Page Id </div>
    </td> 
    <td> 
	    <div id="contentCategoryForm_category_label" style="font-size: normal normal bold 10px verdana;" >Category </div>
    </td> 
    <td> 
	    <div id="contentCategoryForm_imageUrl_label" style="font-size: normal normal bold 10px verdana;" >Image Url </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentCategory _ContentCategory = (ContentCategory) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentCategory.getId() %> </td>


    <td> 
	<form name="contentCategoryFormEditField_PageId_<%=_ContentCategory.getId()%>" method="get" action="/contentCategoryAction.html" >


		<div id="contentCategoryForm_pageId_field">
	    <div id="contentCategoryForm_pageId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageId" value="<%=WebUtil.display(_ContentCategory.getPageId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentCategoryFormEditField_PageId_<%=_ContentCategory.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentCategory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/content_category_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentCategoryFormEditField_Category_<%=_ContentCategory.getId()%>" method="get" action="/contentCategoryAction.html" >


		<div id="contentCategoryForm_category_field">
	    <div id="contentCategoryForm_category_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="category" value="<%=WebUtil.display(_ContentCategory.getCategory())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentCategoryFormEditField_Category_<%=_ContentCategory.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentCategory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/content_category_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentCategoryFormEditField_ImageUrl_<%=_ContentCategory.getId()%>" method="get" action="/contentCategoryAction.html" >


		<div id="contentCategoryForm_imageUrl_field">
	    <div id="contentCategoryForm_imageUrl_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="imageUrl" value="<%=WebUtil.display(_ContentCategory.getImageUrl())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentCategoryFormEditField_ImageUrl_<%=_ContentCategory.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentCategory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/content_category_home">
	</form>
    
    
    </td>


    <td>
    <form name="contentCategoryForm<%=_ContentCategory.getId()%>" method="get" action="/v_content_category_edit.html" >
        <a href="javascript:document.contentCategoryForm<%=_ContentCategory.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentCategory.getId() %>">
    </form>
    <form name="contentCategoryForm<%=_ContentCategory.getId()%>2" method="get" action="/v_content_category_edit2.html" >
        <a href="javascript:document.contentCategoryForm<%=_ContentCategory.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentCategory.getId() %>">
    </form>
    </td>

    <td>
    <a href="/contentCategoryAction.html?del=true&id=<%=_ContentCategory.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>