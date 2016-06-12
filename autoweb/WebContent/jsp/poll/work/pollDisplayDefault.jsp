<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	String serial = (String) session.getAttribute("k_poll_display_serial");

	if ( serial == null) {
    	Map reqParams = (Map) session.getAttribute("k_reserved_params");
    	if (reqParams!= null)
    		serial = (String) reqParams.get("serial");
    }

    String _wpId = WebProcManager.registerWebProcess();

%> 

<%
	Poll poll = PollDS.getInstance().getObjectBySerial(serial);
	if (serial ==null || poll == null) {
%>
	<h2> Poll not set for the display</h2>
<%
		return;
	}
%>

<h3> <%=	poll.getQuestion() %> </h3>
<form name="pollVoteForm" method="get" action="/pollVoteAction.html" >

	<div id="poll<%=poll.getId()%>">

<%
	List answers = PollAnswerDS.getInstance().getByPollId(poll.getId());
	for (Iterator iterator = answers.iterator(); iterator.hasNext();) {
	    PollAnswer ans = (PollAnswer) iterator.next();    
%>

<%
		if (poll.getType() != 2) {
		if ( WebParamUtil.getBooleanValue(poll.getAllowMultiple())) {
%>
		<input type="checkbox" name="answer_<%=ans.getAnswerNum()%>"  /> <%= ans.getText() %><br/>
<%
		} else {
%>
		<input type="radio" name="answer" value="<%=ans.getAnswerNum()%>"> <%= ans.getText() %> <br/>
<%	    
		}
		}
%>

<%
		if ( poll.getType() == 2 && !WebUtil.isNull(ans.getImageUrl())){
%>
		<div id="radioItem" class="option-unselected" style="float:left;" >
			<span id="radioIndex" style="display:none;"><%=ans.getAnswerNum()%></span>
			<img width="100" height="100" src="<%=ans.getImageUrl()%>"/> 
			<div id="previewButton" href="<%=ans.getImageUrl()%>" class="pollImgPreviewClick" title="<%=ans.getText()%>">Click</div>
		</div>	
		
		
<%		} %>	


<%	    
	}
%>
	</div><div class="clear"></div>
	

<%
		if ( poll.getType() == 2 ){
%>
	<INPUT id="radioAnswer<%=poll.getId()%>" TYPE="HIDDEN" NAME="answer" value="">
<%	    
	}
%>
	<span id="poll<%=poll.getId()%>" style="display:none;"><%=poll.getId()%></span> 
	<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/t_poll_result_single.html?prv_serial=<%=poll.getSerial()%>">
	<INPUT TYPE="HIDDEN" NAME="pollId" value="<%=poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

</form>

<%
	// Check if this user has voted.
	boolean voted = PollUtil.votedAlready(poll , sessionContext.getUserObject(), request.getRemoteAddr(), CookieUtil.getRpcId(request));
	if (voted){
%>
	You have voted.
<%	} else { %>
	<a id="formSubmit" href="javascript:document.pollVoteForm.submit();">VOTE</a>
<%	} %>

<a id="formSubmit" href="/t_poll_result_single.html?prv_serial=<%=poll.getSerial()%>">View Result</a>

<br/><br/>
