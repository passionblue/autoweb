<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    BlogCategory _BlogCategory = BlogCategoryDS.getInstance().getById(id);

    if ( _BlogCategory == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "blog_category_home";

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
<div id="blogCategoryForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="blogCategoryFormEdit" method="POST" action="/blogCategoryAction.html" >


    <INPUT TYPE="HIDDEN" NAME="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>" />

    <INPUT TYPE="HIDDEN" NAME="parentCategoryId" value="<%=WebUtil.display(_parent_category_idValue)%>" />



	<div id="blogCategoryForm_title_field">
    <div id="blogCategoryForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="blogCategoryForm_title_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
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
    <div id="blogCategoryForm_imageUrl1_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="imageUrl1" value="<%=WebUtil.display(_image_url1Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="blogCategoryForm_imageUrl2_field">
    <div id="blogCategoryForm_imageUrl2_label" class="formLabel" >Image Url2 </div>
    <div id="blogCategoryForm_imageUrl2_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="imageUrl2" value="<%=WebUtil.display(_image_url2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="blogCategoryFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.blogCategoryFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogCategory.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
