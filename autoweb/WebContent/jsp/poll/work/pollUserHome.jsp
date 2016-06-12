<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%-- @page import="sun.security.jca.GetInstance"--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	PollDS ds = PollDS.getInstance();    
    list = ds.getBySiteId(site.getId());

    String _wpId = WebProcManager.registerWebProcess();

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	System.out.println("====================== " + sessionContext.getUserObject().getId());

%> 

<a href="/v_poll_create.html"> Create Poll</a><br/><br/>


<br/><br/>

<%
	AutositeUser user = sessionContext.getUserObject();
	List pollsForThisSite = PollDS.getInstance().getBySiteIdToUserIdList(site.getId(), user.getId());
    for(Iterator iter = pollsForThisSite.iterator();iter.hasNext();) {
        Poll _Poll = (Poll) iter.next();
        
        PollConfig pollConfig = PollConfigDS.getInstance().getObjectByPollId(_Poll.getId());
		long pollConfigId = (pollConfig == null? 0: pollConfig.getId());
%> 
	<a href="/poll/<%=_Poll.getSerial() %>.html"> <%= _Poll.getQuestion() %></a>&nbsp; 
	<a href="/v_poll_create.html?cmd=edit&id=<%=_Poll.getId() %>">[Edit]</a> &nbsp;

	<a href="/v_poll_config_form.html?id=<%=pollConfigId%>&cmd=edit&prv_pollId=<%=_Poll.getId() %>&prv_returnPage=poll_center">[Edit Config]</a> &nbsp;

	<a href="/pollAction.html?del=true&id=<%=_Poll.getId()%>&returnPage=poll_center"> [Del] </a>&nbsp;
	<a href="/t_poll_result_single.html?prv_serial=<%=_Poll.getSerial()%>">[Results]</a>&nbsp;
	
	<br/>
<%
	}
%>
<br/><br/>

<TABLE class="mytable2">

<TR >
    <td> ID </td>

    <td> 
	    <div id="pollForm_userId_label" style="font-size: normal normal bold 10px verdana;" >User Id </div>
    </td> 
    <td> 
	    <div id="pollForm_type_label" style="font-size: normal normal bold 10px verdana;" >Type </div>
    </td> 
    <td> 
	    <div id="pollForm_category_label" style="font-size: normal normal bold 10px verdana;" >Category </div>
    </td> 
    <td> 
	    <div id="pollForm_title_label" style="font-size: normal normal bold 10px verdana;" >Title </div>
    </td> 
    <td> 
	    <div id="pollForm_question_label" style="font-size: normal normal bold 10px verdana;" >Question </div>
    </td> 
    <td> 
	    <div id="pollForm_hide_label" style="font-size: normal normal bold 10px verdana;" >Hide </div>
    </td> 
    <td> 
	    <div id="pollForm_disable_label" style="font-size: normal normal bold 10px verdana;" >Disable </div>
    </td> 
    <td> 
	    <div id="pollForm_timeCreated_label" style="font-size: normal normal bold 10px verdana;" >Time Created </div>
    </td> 
    <td> 
	    <div id="pollForm_timeUpdated_label" style="font-size: normal normal bold 10px verdana;" >Time Updated </div>
    </td> 
    <td> 
	    <div id="pollForm_timeExpired_label" style="font-size: normal normal bold 10px verdana;" >Time Expired </div>
    </td> 
</TR>
<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        Poll _Poll = (Poll) iter.next();
%>

<TR bgcolor="#ffffff" valign="top">
    <td> <%= _Poll.getId() %> </td>
    
	<td> <%= _Poll.getUserId()  %> </td>
	<td> <%= _Poll.getType()  %> </td>
	<td> <%= _Poll.getCategory()  %> </td>
	<td> <%= _Poll.getTitle()  %> </td>
	<td> <%= _Poll.getQuestion()  %> </td>
	<td> <%= _Poll.getHide()  %> </td>
	<td> <%= _Poll.getDisable()  %> </td>
	<td> <%= _Poll.getTimeCreated()  %> </td>
	<td> <%= _Poll.getTimeUpdated()  %> </td>
	<td> <%= _Poll.getTimeExpired()  %> </td>
</TR>

<%
    }
%>
</TABLE>

<% 
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	list = new ArrayList();

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 

<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  User Id </td> 
    <td class="columnTitle">  Serial </td> 
    <td class="columnTitle">  Type </td> 
    <td class="columnTitle">  Category </td> 
    <td class="columnTitle">  Title </td> 
    <td class="columnTitle">  Description </td> 
    <td class="columnTitle">  Question </td> 
    <td class="columnTitle">  Published </td> 
    <td class="columnTitle">  Hide </td> 
    <td class="columnTitle">  Disable </td> 
    <td class="columnTitle">  Allow Multiple </td> 
    <td class="columnTitle">  Random Answer </td> 
    <td class="columnTitle">  Hide Comments </td> 
    <td class="columnTitle">  Hide Results </td> 
    <td class="columnTitle">  Use Cookie For Dup </td> 
    <td class="columnTitle">  Repeat Every Day </td> 
    <td class="columnTitle">  Max Repeat Vote </td> 
    <td class="columnTitle">  Num Days Open </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
    <td class="columnTitle">  Time Expired </td> 
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        Poll _Poll = (Poll) iter.next();
%>

<TR>
    <td> <%= _Poll.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _Poll.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _Poll.getUserId()  %> </td>
	<td> <%= _Poll.getSerial()  %> </td>
	<td> <%= _Poll.getType()  %> </td>
	<td> <%= _Poll.getCategory()  %> </td>
	<td> <%= _Poll.getTitle()  %> </td>
	<td> <%= _Poll.getDescription()  %> </td>
	<td> <%= _Poll.getQuestion()  %> </td>
	<td> <%= _Poll.getPublished()  %> </td>
	<td> <%= _Poll.getHide()  %> </td>
	<td> <%= _Poll.getDisable()  %> </td>
	<td> <%= _Poll.getAllowMultiple()  %> </td>
	<td> <%= _Poll.getRandomAnswer()  %> </td>
	<td> <%= _Poll.getHideComments()  %> </td>
	<td> <%= _Poll.getHideResults()  %> </td>
	<td> <%= _Poll.getUseCookieForDup()  %> </td>
	<td> <%= _Poll.getRepeatEveryDay()  %> </td>
	<td> <%= _Poll.getMaxRepeatVote()  %> </td>
	<td> <%= _Poll.getNumDaysOpen()  %> </td>
	<td> <%= _Poll.getTimeCreated()  %> </td>
	<td> <%= _Poll.getTimeUpdated()  %> </td>
	<td> <%= _Poll.getTimeExpired()  %> </td>
</TR>

<%
    }
%>
</TABLE>

