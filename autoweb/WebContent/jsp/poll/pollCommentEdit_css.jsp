<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    PollComment _PollComment = PollCommentDS.getInstance().getById(id);

    if ( _PollComment == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    

    String _poll_idValue=  WebUtil.display(_PollComment.getPollId());
    String _user_idValue=  WebUtil.display(_PollComment.getUserId());
    String _commentValue=  WebUtil.display(_PollComment.getComment());
    String _hideValue=  WebUtil.display(_PollComment.getHide());
    String _time_createdValue=  WebUtil.display(_PollComment.getTimeCreated());
%> 

<br>
<div id="pollCommentForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pollCommentFormEdit" method="POST" action="/pollCommentAction.html" >




	<div id="pollCommentForm_pollId_field">
    <div id="pollCommentForm_pollId_label" class="formLabel" >Poll Id </div>
    <div id="pollCommentForm_pollId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="pollId" value="<%=WebUtil.display(_poll_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollCommentForm_comment_field">
    <div id="pollCommentForm_comment_label" class="formRequiredLabel" >Comment* </div>
    <div id="pollCommentForm_comment_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="comment" value="<%=WebUtil.display(_commentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollCommentForm_hide_field">
    <div id="pollCommentForm_hide_label" class="formLabel" >Hide </div>
    <div id="pollCommentForm_hide_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="hide" value="<%=WebUtil.display(_hideValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="pollCommentFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollCommentFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollComment.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
