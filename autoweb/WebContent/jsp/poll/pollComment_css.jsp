<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    PollComment _PollComment = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_PollComment = PollCommentDS.getInstance().getById(id);
		if ( _PollComment == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
		}
		isEdit = true;

        
	} else {

	    _PollComment = new PollComment();// PollCommentDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    

    String _poll_idValue= (reqParams.get("pollId")==null?WebUtil.display(_PollComment.getPollId()):WebUtil.display((String)reqParams.get("pollId")));
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_PollComment.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _commentValue= (reqParams.get("comment")==null?WebUtil.display(_PollComment.getComment()):WebUtil.display((String)reqParams.get("comment")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_PollComment.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PollComment.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
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

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollComment.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>


<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
