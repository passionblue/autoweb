 <%@page import="java.sql.Wrapper"%>
<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.poll.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String serial = null;

	if ( serial == null) {
    	Map reqParams = (Map) session.getAttribute("k_reserved_params");
    	if (reqParams!= null)
    		serial = (String) reqParams.get("serial");
    }
    
	SessionWrapper wrap = SessionWrapper.wrapIt(request, site.getId());
	
%> 

<%
	Poll poll = PollDS.getInstance().getObjectBySerial(serial);
	if (poll == null) 
		poll = PollDS.getInstance().getById(WebParamUtil.getLongValue(serial));
	
	if (poll == null) {
%>
	<h2> Poll not found for the display. Please try again.</h2>
<%
		return;
	}

	PollWrapper pollWrap = PollWrapper.getWrapper(poll, request, sessionContext.getUserObject());
%>

<div class="pollResultSingle">

<div class="pollResultSingleQuestion"> <%= "Q. " + poll.getQuestion() %> </div>

<p>created <%= DateUtil.dateFormat(poll.getTimeCreated()) %></p><br/>


<%
	PollResult pollResult = PollResult.getPollResult(poll.getId());
	List results = pollResult.getResultsByAnswerNum();	
	
	for(Iterator iter = results.iterator(); iter.hasNext();){
		PollResult.ResultEntry entry = (PollResult.ResultEntry) iter.next();	
%>
	<div class="pollResultSingleDetailLine">
		<div class="pollResultSingleDetailCount">
			<%= entry.getCount() %>
		</div>
		<div class="pollResultSingleDetailText">
			<%=entry.getAnswerObj().getText() %>
		</div> <div class="clear"></div>
	</div>
<% } %>
<br/>


Number Votes :<%= pollResult.getNumVotes()%>

<br/>
<br/>

<!-- ==================== COMMENTS ================================ -->
<%
	// Check if this user has voted.
	// boolean voted = PollUtil.votedAlready(poll , wrap.getSessionCtx().getUserObject(), request.getRemoteAddr(), CookieUtil.getRpcId(request));
%>

<%
	if (pollWrap.isVoted()){
%>

<%	}else{ %>
	You can still vote on this poll <a id="formSubmit" href="/poll/<%=poll.getSerial() %>.html"> go back to the poll </a>

<%	} %>

</div>


<!-- Comments -->

<div id="pollComments">

<table >
	<tr >
		<td class="pollCommHeader">Comments</td>
		<td class="pollCommHeader">Time</td>
	</tr>
<%	if (true){ 
		
		AutositeUser user = sessionContext.getUserObject();
		// replaced by Wrapper List comments = PollCommentDS.getInstance().getByPollId(poll.getId());
		List comments = pollWrap.getComments();
		for(Iterator iter = comments.iterator(); iter.hasNext();){
			PollComment com = (PollComment) iter.next();

			if (com.getHide() == 1) continue;	
%>
	<tr>
		<td class="pollCommText">
			<%= com.getComment() %>
		 
<%
			if (user!= null && com.getUserId() == user.getId()){ 
%>
			
<%			} %>			
			
			<a href="/pollCommentAction.html?del=true&id=<%=com.getId()%>&fwdTo=/v_poll_result_single.html?prv_serial=<%=serial %>"> [Del] </a> &nbsp;
		</td>
		<td class="pollCommTime">			
			<%= com.getTimeCreated() %>
		</td>			
	</tr>
<%	
		}
	} 
%>


</table>
</div>



<br/>
<br/>


<div class="pollCommentListFrame">
<%
	if ( sessionContext.isLogin() ){
%>

<br/>

<form name="pollCommentFormEdit" method="POST" action="/pollCommentAction.html" >
    <div> Comment </div>
	<TEXTAREA NAME="comment" COLS="50" ROWS="8" ></TEXTAREA>

	<INPUT TYPE="HIDDEN" NAME="add" value="true">
	<INPUT TYPE="HIDDEN" NAME="pollId" value="<%=poll.getId() %>">
	<INPUT TYPE="HIDDEN" NAME="fwdToErr" value="/v_poll_result_single.html?prv_serial=<%=poll.getSerial()%>">
	<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/v_poll_result_single.html?prv_serial=<%=poll.getSerial()%>">
</form>

<a id="formSubmit" href="javascript:document.pollCommentFormEdit.submit();">Submit</a>



<%	
	} else { 
%>

<h4> Commenting is currently disabled in the Beta version.</h4>
	
<%	
	}  
%>
</div>