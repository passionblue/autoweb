<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%-- @page import="sun.security.jca.GetInstance" --%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	PollDS ds = PollDS.getInstance();    
    list = ds.getBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");


   	Map reqParams = (Map) session.getAttribute("k_reserved_params");
	String category = (String) reqParams.get("category");

	long pollId = 0;
	
	if ( WebUtil.isNotNull((String)reqParams.get("pollId"))){
		pollId = WebParamUtil.getLongValue((String)reqParams.get("pollId"));
	} else {
		pollId = WebParamUtil.getLongValue((String)request.getAttribute("k_pollId"));
	}
	boolean singleDisp = WebParamUtil.getBooleanValue((String)reqParams.get("singleDisp"));
	
%> 


<%
	AutositeUser user = sessionContext.getUserObject();

	List pollsForThisSite = null; // Main list of polls

	Poll pollById = PollDS.getInstance().getById(pollId);
	if (  pollById != null) {
		pollsForThisSite = new ArrayList();
		pollsForThisSite.add(pollById);
	} else {
	
		if (WebUtil.isNull(category)) {
			pollsForThisSite = PollDS.getInstance().getBySiteId(site.getId());
		} else {
			pollsForThisSite = PollDS.getInstance().getBySiteIdCategoryList(site.getId(), category);
		}
	}
	
	// Single Display was turned on by request. So discard others if there are more than one poll in the list. 
	if (singleDisp && pollsForThisSite.size() > 1 ){
			List temp = pollsForThisSite;
			pollsForThisSite = new ArrayList();
			pollsForThisSite.add(temp.get(0));
	}
%>

<%	
    for(Iterator iter = pollsForThisSite.iterator();iter.hasNext();) {
        Poll _Poll = (Poll) iter.next();
        
		// Process PollConfig for the poll
        PollConfig pollConfig = PollConfigDS.getInstance().getObjectByPollId(_Poll.getId());
        String frameBackgroundCfg = "";
        if ( pollConfig != null && WebUtil.isNotNull(pollConfig.getBackground())) {
        	frameBackgroundCfg = "style=\"background: " + pollConfig.getBackground()+ ";\"";
        } 

        boolean closed = PollUtil.pollClosed(_Poll);

%> 

<%--
<pollSingleFrame>
	<pollSectionFront>
		<pollSectionMenu>
		<pollDispFrame>
			<-- include -->
		<pollRefFrame>	
	<resultSectionFront>
		<pollResultTitle>
		<pollResultDetail>
		<pollResultSummary>
--%>


<div class="pollSingleFrame">
	<div class="pollSectionFront" >

<% if (sessionContext.isSiteAdmin()){%>

		<div class="pollSendctionMenu" >
			<a href="/v_poll_create.html?cmd=edit&id=<%= _Poll.getId() %>"> Edit Poll</a>|
			<a href="/v_poll_config_form.html?id=<%=pollConfig==null?0:pollConfig.getId()%>&cmd=edit&prv_pollId=<%=_Poll.getId() %>&prv_returnPage=poll_public_home">[Edit Config]</a>
		</div>
<%	} %>

 		<div class="pollDispFrame" <%=frameBackgroundCfg %>>
		<jsp:include page="<%= PollUtil.getPollDisplayPartPage(_Poll.getType()) %>" >
 				<jsp:param name="_pollId" value="<%= _Poll.getId() %>"/>
		</jsp:include>
		
		</div>
		<div class="pollRefFrame" style="padding: 0px 10px 3px 10px; font: normal normal normal 10px verdana;">
			<p style="line-height: 10px; margin: 0;">URL: http://<%=request.getHeader("host")%>/poll/<%= _Poll.getSerial()%>.html</p> 
			<div class="openEmbedCode">Embed:</div><span style="display:none;"><%=_Poll.getId() %></span>
			<textarea style="width:100%;display:none;" id="pollScriptEmbed<%=_Poll.getId() %>" onclick="this.select();" rows="1" wrap="off" readonly>
				<script type="text/javascript" src="http://<%= request.getHeader("host")==null? request.getServerName():request.getHeader("host")%>/pollscr/<%=_Poll.getSerial()%>.poll?dispType=scriptlet&noScript=false">
				</script>
			</textarea>
			<p><a href="/devNoteAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Email(TODO)</a></p>
		</div>
	</div ><!-- pollSectionFront -->

	<div class="resultSectionFront" >
		<div class="pollResultTitle">Poll Result</div>
		<div id="pollResultDetail<%=_Poll.getId()%>" class="pollResultDetail">
		</div>
		<div id="pollResultSummary<%=_Poll.getId()%>" class="pollResultSummary">
		</div>
	</div><!-- resultSectionFront -->
	<div class="clear"></div>	

</div>	

<%
	}
%>
