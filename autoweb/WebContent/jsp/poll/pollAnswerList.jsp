<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	PollAnswerDS ds = PollAnswerDS.getInstance();    
    


%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_poll_answer_add2.html?prv_returnPage=poll_answer_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">
    <td> ID </td>

    <td> 
	    <div id="pollAnswerForm_pollId_label" style="font-size: normal normal bold 10px verdana;" >Poll Id </div>
    </td> 
    <td> 
	    <div id="pollAnswerForm_answerNum_label" style="font-size: normal normal bold 10px verdana;" >Answer Num </div>
    </td> 
    <td> 
	    <div id="pollAnswerForm_text_label" style="font-size: normal normal bold 10px verdana;" >Text </div>
    </td> 
    <td> 
	    <div id="pollAnswerForm_imageUrl_label" style="font-size: normal normal bold 10px verdana;" >Image Url </div>
    </td> 
    <td> 
	    <div id="pollAnswerForm_imageOnly_label" style="font-size: normal normal bold 10px verdana;" >Image Only </div>
    </td> 
</TR>
<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollAnswer _PollAnswer = (PollAnswer) iter.next();
%>

<TR bgcolor="#ffffff" valign="top">
    <td> <%= _PollAnswer.getId() %> </td>
    
	<td> <%= _PollAnswer.getPollId()  %> </td>
	<td> <%= _PollAnswer.getAnswerNum()  %> </td>
	<td> <%= _PollAnswer.getText()  %> </td>
	<td> <%= _PollAnswer.getImageUrl()  %> </td>
	<td> <%= _PollAnswer.getImageOnly()  %> </td>
</TR>

<%
    }
%>
</TABLE>