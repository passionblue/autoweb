<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ContentCategory _ContentCategoryDefault = new ContentCategory();// ContentCategoryDS.getInstance().getDeafult();
    
    String _page_idValue= (reqParams.get("pageId")==null?WebUtil.display(_ContentCategoryDefault.getPageId()):WebUtil.display((String)reqParams.get("pageId")));
    String _categoryValue= (reqParams.get("category")==null?WebUtil.display(_ContentCategoryDefault.getCategory()):WebUtil.display((String)reqParams.get("category")));
    String _image_urlValue= (reqParams.get("imageUrl")==null?WebUtil.display(_ContentCategoryDefault.getImageUrl()):WebUtil.display((String)reqParams.get("imageUrl")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentCategoryDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="contentCategoryForm" method="post" action="/contentCategoryAction.html" >
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
            <b><a href="javascript:document.contentCategoryForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ContentCategoryDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentCategory _ContentCategory = (ContentCategory) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentCategory.getId() %> </td>

    <td> <%= WebUtil.display(_ContentCategory.getPageId()) %></td>
    <td> <%= WebUtil.display(_ContentCategory.getCategory()) %></td>
    <td> <%= WebUtil.display(_ContentCategory.getImageUrl()) %></td>
    <td> <%= WebUtil.display(_ContentCategory.getTimeCreated()) %></td>


<td>
<form name="contentCategoryForm<%=_ContentCategory.getId()%>" method="post" action="/v_content_category_edit.html" >
    <a href="javascript:document.contentCategoryForm<%=_ContentCategory.getId()%>.submit();">Edit</a>           
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