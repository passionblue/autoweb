<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.poll.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	long pollId = WebParamUtil.getLongValue((String) request.getParameter("_pollId"));
	Poll poll = PollDS.getInstance().getById(pollId);
	
	PollWrapper pollWrap = PollWrapper.getWrapper(poll, request, sessionContext.getUserObject());
//	boolean voted = PollUtil.votedAlready(poll , sessionContext.getUserObject(), request.getRemoteAddr(), CookieUtil.getRpcId(request));
	
%> 

<%
	if (poll == null) {
%>
	<h2> Poll not set for the display</h2>
<%
		return;
	}

%>



<div id="poll<%=poll.getId()%>" class="pollDispFrameInside">
<div class="pollDispCategory"> <%= WebUtil.display(poll.getCategory()) %></div>
<form name="pollVoteForm<%=poll.getId()%>" method="get" action="/pollVoteAction.html" >
	<div class="pollDispQuestion"> <%=	"Q. " + poll.getQuestion() %> </div>

<%
	List answers = PollAnswerDS.getInstance().getByPollId(poll.getId());
	for (Iterator iterator = answers.iterator(); iterator.hasNext();) {
	    PollAnswer ans = (PollAnswer) iterator.next();    
%>

		<div id="radioItem" class="pollImgUnselected" >
			<span style="display:none;"><%=poll.getId()%></span>
			<span id="radioIndex" style="display:none;"><%=ans.getAnswerNum()%></span>
			<img id="radioItemImg" class="pollOptionImg" src="<%=ans.getImageUrl()%>" />
			<div class="ceebox"><a href="<%=ans.getImageUrl()%>" class="{boxColor:'#000',textColor:'#ddd'}" title="<%=poll.getQuestion() + "   option: "%><%=ans.getText()%>">
				<img align="left" src="/images/dev/PNG/Green/18/zoomin.png"/>
			</a> <%=ans.getText()%></div>
			<!-- div id="previewButton" href="<%=ans.getImageUrl()%>" class="pollImgPreviewClick" title="<%=ans.getText()%>">View Large</div -->
		</div>	
		<!--div class="ceebox">&nbsp; <a href="<%=ans.getImageUrl()%>" class="{boxColor:'#000',textColor:'#ddd'}" title="<%=poll.getQuestion() + "   option: "%><%=ans.getText()%>">Large</a> </div-->  
 
<%	    
	}
%>

	<INPUT id="radioAnswer<%=poll.getId()%>" TYPE="HIDDEN" NAME="answer" value="" >

	<span id="poll<%=poll.getId()%>" style="display:none;"><%=poll.getId()%></span> 
	<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/t_poll_result_single.html?prv_serial=<%=poll.getSerial()%>">
	<INPUT TYPE="HIDDEN" NAME="pollId" value="<%=poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">

</form>

<%
	// Check if this user has voted.
	if (pollWrap.isVoted()){
%>
	<p style="margin-left: 10px;font: normal normal normal 10px verdana"> *You have already voted on this poll.</p>
<%	}%>

<div class="pollDispButton">

<%
	// Check if this user has voted.
	if (pollWrap.isVoted()){
%>
<%	} else { %>
	<!--a href="javascript:document.pollVoteForm<%=poll.getId()%>.submit();" >VOTE</a-->
	<a id="voteSubmitAjax" ref="<%= poll.getId() %>" rel="pollId=<%=poll.getId()%>&ajaxRequest=true&add=true&ajaxOut=getResultJson">VOTE</a>
<%	} %>
	<!-- a id="formSubmit" href="/t_poll_result_single.html?prv_serial=<%=poll.getSerial()%>">View Result</a-->
	<a id="voteResultAjax" class="viewResults" ref="<%= poll.getId() %>" rel="pollId=<%=poll.getId()%>&ajaxRequest=true&ajaxOut=getResultJson"">View Result</a>
</div>
</div><div class="clear"></div>

<br/><br/>
