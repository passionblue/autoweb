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

    BlogComment _BlogComment = BlogCommentDS.getInstance().getById(id);

    if ( _BlogComment == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "blog_comment_home";

    String _blog_main_idValue=  WebUtil.display(_BlogComment.getBlogMainId());
    String _blog_post_idValue=  WebUtil.display(_BlogComment.getBlogPostId());
    String _commentValue=  WebUtil.display(_BlogComment.getComment());
    String _ratingValue=  WebUtil.display(_BlogComment.getRating());
    String _ipaddressValue=  WebUtil.display(_BlogComment.getIpaddress());
    String _nameValue=  WebUtil.display(_BlogComment.getName());
    String _passwordValue=  WebUtil.display(_BlogComment.getPassword());
    String _websiteValue=  WebUtil.display(_BlogComment.getWebsite());
    String _emailValue=  WebUtil.display(_BlogComment.getEmail());
    String _time_createdValue=  WebUtil.display(_BlogComment.getTimeCreated());
%> 

<br>
<div id="blogCommentForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="blogCommentFormEdit" method="POST" action="/blogCommentAction.html" >


    <INPUT TYPE="HIDDEN" NAME="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>" />

    <INPUT TYPE="HIDDEN" NAME="blogPostId" value="<%=WebUtil.display(_blog_post_idValue)%>" />

	<div id="blogCommentForm_comment_field">
    <div id="blogCommentForm_comment_label" class="formRequiredLabel" >Comment* </div>
    <div id="blogCommentForm_comment_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="requiredField" NAME="comment" COLS="50" ROWS="8" ><%=WebUtil.display(_commentValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>


	<div id="blogCommentForm_rating_field">
    <div id="blogCommentForm_rating_label" class="formLabel" >Rating </div>
    <div id="blogCommentForm_rating_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="rating">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _ratingValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _ratingValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _ratingValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _ratingValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _ratingValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _ratingValue)%>>5</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="blogCommentForm_name_field">
    <div id="blogCommentForm_name_label" class="formRequiredLabel" >Name* </div>
    <div id="blogCommentForm_name_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="blogCommentForm_password_field">
    <div id="blogCommentForm_password_label" class="formRequiredLabel" >Password* </div>
    <div id="blogCommentForm_password_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="blogCommentForm_website_field">
    <div id="blogCommentForm_website_label" class="formLabel" >Website </div>
    <div id="blogCommentForm_website_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="website" value="<%=WebUtil.display(_websiteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="blogCommentForm_email_field">
    <div id="blogCommentForm_email_label" class="formLabel" >Email </div>
    <div id="blogCommentForm_email_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="blogCommentFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.blogCommentFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogComment.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
