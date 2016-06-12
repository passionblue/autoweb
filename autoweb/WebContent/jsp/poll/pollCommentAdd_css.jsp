<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    PollComment _PollCommentDefault = new PollComment();// PollCommentDS.getInstance().getDeafult();
    
    String _poll_idValue= (reqParams.get("pollId")==null?WebUtil.display(_PollCommentDefault.getPollId()):WebUtil.display((String)reqParams.get("pollId")));
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_PollCommentDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _commentValue= (reqParams.get("comment")==null?WebUtil.display(_PollCommentDefault.getComment()):WebUtil.display((String)reqParams.get("comment")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_PollCommentDefault.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PollCommentDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="pollCommentForm_topArea" class="formTopArea"></div>
<div id="pollCommentForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="pollCommentForm" method="POST" action="/pollCommentAction.html" >




	<div id="pollCommentForm_pollId_field">
    <div id="pollCommentForm_pollId_label" class="formLabel" >Poll Id </div>
    <div id="pollCommentForm_pollId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="pollId" value="<%=WebUtil.display(_poll_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>






	<div id="pollCommentForm_comment_field">
    <div id="pollCommentForm_comment_label" class="formRequiredLabel" >Comment* </div>
    <div id="pollCommentForm_comment_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="comment" value="<%=WebUtil.display(_commentValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="pollCommentForm_hide_field">
    <div id="pollCommentForm_hide_label" class="formLabel" >Hide </div>
    <div id="pollCommentForm_hide_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="hide" value="<%=WebUtil.display(_hideValue)%>"/>
    </div>      
	</div><div class="clear"></div>





        <div id="pollCommentForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollCommentForm.submit();">Submit</a>
        </div>      

        <div id="pollCommentForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.pollCommentForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="pollCommentForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PollCommentDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollComment _PollComment = (PollComment) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PollComment.getId() %> </td>

    <td> <%= WebUtil.display(_PollComment.getPollId()) %></td>
    <td> <%= WebUtil.display(_PollComment.getUserId()) %></td>
    <td> <%= WebUtil.display(_PollComment.getComment()) %></td>
    <td> <%= WebUtil.display(_PollComment.getHide()) %></td>
    <td> <%= WebUtil.display(_PollComment.getTimeCreated()) %></td>


<td>
<form name="pollCommentForm<%=_PollComment.getId()%>" method="get" action="/v_poll_comment_edit.html" >
    <a href="javascript:document.pollCommentForm<%=_PollComment.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollComment.getId() %>">
</form>
<form name="pollCommentForm<%=_PollComment.getId()%>2" method="get" action="/v_poll_comment_edit2.html" >
    <a href="javascript:document.pollCommentForm<%=_PollComment.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollComment.getId() %>">
</form>

</td>
<td>
<a href="/pollCommentAction.html?del=true&id=<%=_PollComment.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>