<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	long pollId = WebParamUtil.getLongValue((String) request.getParameter("_pollId"));

%> 

<%
	Poll poll = PollDS.getInstance().getById(pollId);
	if (poll == null) {
%>
	<h2> Poll not set for the display</h2>
<%
		return;
	}
%>

<div id="poll<%=poll.getId()%>" class="pollDispFrameInside">
<div class="pollDispCategory"> <%= WebUtil.display(poll.getCategory()) %></div>

<form name="pollVoteForm<%=poll.getId() %>" method="get" action="/pollVoteAction.html" >
	<div class="pollDispQuestion"> <%=	"Q. " + poll.getQuestion() %> </div>

<%
	boolean voted = PollUtil.votedAlready(poll , sessionContext.getUserObject(), request.getRemoteAddr(), CookieUtil.getRpcId(request));
	List answers = PollAnswerDS.getInstance().getByPollId(poll.getId());
	for (Iterator iterator = answers.iterator(); iterator.hasNext();) {
	    PollAnswer ans = (PollAnswer) iterator.next();    
%>
	<div id="ans-<%=poll.getId()%>-<%=ans.getAnswerNum() %>" class="pollDispAnswerScale">

		<div class="pollDispAnswerControl" style="display:none;">
			<input id="ans-<%=poll.getId()%>-<%=ans.getAnswerNum() %>-input" type="radio" name="answer" value="<%=ans.getAnswerNum()%>" /> 
		</div>

		<div id="ans-<%=poll.getId()%>-<%=ans.getAnswerNum() %>-input" class="pollDispAnswerScaleText">
			<%= ans.getText() %>
		</div><div class="clear"></div>

		<span style="display:none"><%=poll.getId()%></span>
		<span style="display:none"><%=answers.size() %></span>
	</div>
<%	    
	}
%>
	<div class="clear"></div>
	<span id="poll<%=poll.getId()%>" style="display:none;"><%=poll.getId()%></span> 
	<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/v_poll_result_single.html?prv_serial=<%=poll.getSerial()%>">
	<INPUT TYPE="HIDDEN" NAME="pollId" value="<%=poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">

</form>
<%
	// Check if this user has voted.
	if (voted){
%>
	<p style="margin-left: 10px;font: normal normal normal 10px verdana"> *You have already voted on this poll.</p>
<%	}%>


<div class="pollDispButton">
<%
	// Check if this user has voted.
	if (voted){
%>
<%	} else { %>
	<!-- a class="voteSubmit" href="javascript:document.pollVoteForm<%=poll.getId() %>.submit();">VOTE</a-->
	<a id="voteSubmitAjax" ref="<%= poll.getId() %>" rel="pollId=<%=poll.getId()%>&ajaxRequest=true&add=true&ajaxOut=getResultJson">VOTE</a>
<%	} %>
	<!-- a class="viewResults" href="/t_poll_result_single.html?prv_serial=<%=poll.getSerial()%>">View Result</a-->
	<a id="voteResultAjax" class="viewResults" ref="<%= poll.getId() %>" rel="pollId=<%=poll.getId()%>&ajaxRequest=true&ajaxOut=getResultJson"">View Result</a>
</div>	
</div><div class="clear"></div>

<br/><br/>
