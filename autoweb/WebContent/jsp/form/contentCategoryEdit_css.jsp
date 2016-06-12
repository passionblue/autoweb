<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    ContentCategory _ContentCategory = ContentCategoryDS.getInstance().getById(id);

    if ( _ContentCategory == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _page_idValue=  WebUtil.display(_ContentCategory.getPageId());
    String _categoryValue=  WebUtil.display(_ContentCategory.getCategory());
    String _image_urlValue=  WebUtil.display(_ContentCategory.getImageUrl());
    String _time_createdValue=  WebUtil.display(_ContentCategory.getTimeCreated());
%> 

<br>
<div id="contentCategoryForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="contentCategoryFormEdit" method="get" action="/contentCategoryAction.html" >




	<div id="contentCategoryForm_pageId_field">
    <div id="contentCategoryForm_pageId_label" class="formLabel" >Page Id </div>
    <div id="contentCategoryForm_pageId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="pageId" value="<%=WebUtil.display(_page_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="contentCategoryForm_category_field">
    <div id="contentCategoryForm_category_label" class="formRequiredLabel" >Category* </div>
    <div id="contentCategoryForm_category_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="category" value="<%=WebUtil.display(_categoryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="contentCategoryForm_imageUrl_field">
    <div id="contentCategoryForm_imageUrl_label" class="formLabel" >Image Url </div>
    <div id="contentCategoryForm_imageUrl_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="imageUrl" value="<%=WebUtil.display(_image_urlValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="contentCategoryFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentCategoryFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentCategory.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
