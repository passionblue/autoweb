<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	PollVoteDS ds = PollVoteDS.getInstance();    
    


%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_poll_vote_add2.html?prv_returnPage=poll_vote_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">
    <td> ID </td>

    <td> 
	    <div id="pollVoteForm_pollId_label" style="font-size: normal normal bold 10px verdana;" >Poll Id </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_userId_label" style="font-size: normal normal bold 10px verdana;" >User Id </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_answer_label" style="font-size: normal normal bold 10px verdana;" >Answer </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_multipleAnswer_label" style="font-size: normal normal bold 10px verdana;" >Multiple Answer </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_byGuest_label" style="font-size: normal normal bold 10px verdana;" >By Guest </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_ipAddress_label" style="font-size: normal normal bold 10px verdana;" >Ip Address </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_pcid_label" style="font-size: normal normal bold 10px verdana;" >Pcid </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_dupCheckKey_label" style="font-size: normal normal bold 10px verdana;" >Dup Check Key </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_note_label" style="font-size: normal normal bold 10px verdana;" >Note </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_timeCreated_label" style="font-size: normal normal bold 10px verdana;" >Time Created </div>
    </td> 
</TR>
<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollVote _PollVote = (PollVote) iter.next();
%>

<TR bgcolor="#ffffff" valign="top">
    <td> <%= _PollVote.getId() %> </td>
    
	<td> <%= _PollVote.getPollId()  %> </td>
	<td> <%= _PollVote.getUserId()  %> </td>
	<td> <%= _PollVote.getAnswer()  %> </td>
	<td> <%= _PollVote.getMultipleAnswer()  %> </td>
	<td> <%= _PollVote.getByGuest()  %> </td>
	<td> <%= _PollVote.getIpAddress()  %> </td>
	<td> <%= _PollVote.getPcid()  %> </td>
	<td> <%= _PollVote.getDupCheckKey()  %> </td>
	<td> <%= _PollVote.getNote()  %> </td>
	<td> <%= _PollVote.getTimeCreated()  %> </td>
</TR>

<%
    }
%>
</TABLE>