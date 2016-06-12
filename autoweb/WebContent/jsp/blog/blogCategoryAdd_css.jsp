<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    BlogCategory _BlogCategoryDefault = new BlogCategory();// BlogCategoryDS.getInstance().getDeafult();
    
    String _blog_main_idValue= (reqParams.get("blogMainId")==null?WebUtil.display(_BlogCategoryDefault.getBlogMainId()):WebUtil.display((String)reqParams.get("blogMainId")));
    String _parent_category_idValue= (reqParams.get("parentCategoryId")==null?WebUtil.display(_BlogCategoryDefault.getParentCategoryId()):WebUtil.display((String)reqParams.get("parentCategoryId")));
    String _root_category_idValue= (reqParams.get("rootCategoryId")==null?WebUtil.display(_BlogCategoryDefault.getRootCategoryId()):WebUtil.display((String)reqParams.get("rootCategoryId")));
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_BlogCategoryDefault.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_BlogCategoryDefault.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _image_url1Value= (reqParams.get("imageUrl1")==null?WebUtil.display(_BlogCategoryDefault.getImageUrl1()):WebUtil.display((String)reqParams.get("imageUrl1")));
    String _image_url2Value= (reqParams.get("imageUrl2")==null?WebUtil.display(_BlogCategoryDefault.getImageUrl2()):WebUtil.display((String)reqParams.get("imageUrl2")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_BlogCategoryDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="blogCategoryForm_topArea" class="formTopArea"></div>
<div id="blogCategoryForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="blogCategoryForm" method="get" action="/blogCategoryAction.html" >



    <INPUT TYPE="HIDDEN" NAME="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>" />




    <INPUT TYPE="HIDDEN" NAME="parentCategoryId" value="<%=WebUtil.display(_parent_category_idValue)%>" />







	<div id="blogCategoryForm_title_field">
    <div id="blogCategoryForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="blogCategoryForm_title_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="title" value="<%=WebUtil.display(_titleValue)%>"/> 
    </div>      
	</div><div class="clear"></div>



	<div id="blogCategoryForm_hide_field">
    <div id="blogCategoryForm_hide_label" class="formLabel" >Hide </div>
    <div id="blogCategoryForm_hide_dropdown" class="formFieldDropDown" >       
        <select name="hide">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="blogCategoryForm_imageUrl1_field">
    <div id="blogCategoryForm_imageUrl1_label" class="formLabel" >Image Url1 </div>
    <div id="blogCategoryForm_imageUrl1_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="imageUrl1" value="<%=WebUtil.display(_image_url1Value)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="blogCategoryForm_imageUrl2_field">
    <div id="blogCategoryForm_imageUrl2_label" class="formLabel" >Image Url2 </div>
    <div id="blogCategoryForm_imageUrl2_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="imageUrl2" value="<%=WebUtil.display(_image_url2Value)%>"/>
    </div>      
	</div><div class="clear"></div>





        <div id="blogCategoryForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.blogCategoryForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="blogCategoryForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = BlogCategoryDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogCategory _BlogCategory = (BlogCategory) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogCategory.getId() %> </td>

    <td> <%= WebUtil.display(_BlogCategory.getBlogMainId()) %></td>
    <td> <%= WebUtil.display(_BlogCategory.getParentCategoryId()) %></td>
    <td> <%= WebUtil.display(_BlogCategory.getRootCategoryId()) %></td>
    <td> <%= WebUtil.display(_BlogCategory.getTitle()) %></td>
    <td> <%= WebUtil.display(_BlogCategory.getHide()) %></td>
    <td> <%= WebUtil.display(_BlogCategory.getImageUrl1()) %></td>
    <td> <%= WebUtil.display(_BlogCategory.getImageUrl2()) %></td>
    <td> <%= WebUtil.display(_BlogCategory.getTimeCreated()) %></td>


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