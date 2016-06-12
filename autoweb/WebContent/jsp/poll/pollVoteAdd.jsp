<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    PollVote _PollVoteDefault = new PollVote();// PollVoteDS.getInstance().getDeafult();
    
    String _poll_idValue= (reqParams.get("pollId")==null?WebUtil.display(_PollVoteDefault.getPollId()):WebUtil.display((String)reqParams.get("pollId")));
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_PollVoteDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _answerValue= (reqParams.get("answer")==null?WebUtil.display(_PollVoteDefault.getAnswer()):WebUtil.display((String)reqParams.get("answer")));
    String _multiple_answerValue= (reqParams.get("multipleAnswer")==null?WebUtil.display(_PollVoteDefault.getMultipleAnswer()):WebUtil.display((String)reqParams.get("multipleAnswer")));
    String _by_guestValue= (reqParams.get("byGuest")==null?WebUtil.display(_PollVoteDefault.getByGuest()):WebUtil.display((String)reqParams.get("byGuest")));
    String _ip_addressValue= (reqParams.get("ipAddress")==null?WebUtil.display(_PollVoteDefault.getIpAddress()):WebUtil.display((String)reqParams.get("ipAddress")));
    String _pcidValue= (reqParams.get("pcid")==null?WebUtil.display(_PollVoteDefault.getPcid()):WebUtil.display((String)reqParams.get("pcid")));
    String _dup_check_keyValue= (reqParams.get("dupCheckKey")==null?WebUtil.display(_PollVoteDefault.getDupCheckKey()):WebUtil.display((String)reqParams.get("dupCheckKey")));
    String _noteValue= (reqParams.get("note")==null?WebUtil.display(_PollVoteDefault.getNote()):WebUtil.display((String)reqParams.get("note")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PollVoteDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="pollVoteForm" method="post" action="/pollVoteAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Poll Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="pollId" value="<%=WebUtil.display(_poll_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>User Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="userId" value="<%=WebUtil.display(_user_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Answer</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="answer" value="<%=WebUtil.display(_answerValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Multiple Answer</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="multipleAnswer" value="<%=WebUtil.display(_multiple_answerValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>By Guest</b> &nbsp;</TD>
        <TD>&nbsp;<select name="byGuest">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _by_guestValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _by_guestValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Ip Address</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="ipAddress" value="<%=WebUtil.display(_ip_addressValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Pcid</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="pcid" value="<%=WebUtil.display(_pcidValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Dup Check Key</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="dupCheckKey" value="<%=WebUtil.display(_dup_check_keyValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Note</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="note" value="<%=WebUtil.display(_noteValue)%>"/></TD>
	    </TR>
	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.pollVoteForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PollVoteDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollVote _PollVote = (PollVote) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PollVote.getId() %> </td>

    <td> <%= WebUtil.display(_PollVote.getPollId()) %></td>
    <td> <%= WebUtil.display(_PollVote.getUserId()) %></td>
    <td> <%= WebUtil.display(_PollVote.getAnswer()) %></td>
    <td> <%= WebUtil.display(_PollVote.getMultipleAnswer()) %></td>
    <td> <%= WebUtil.display(_PollVote.getByGuest()) %></td>
    <td> <%= WebUtil.display(_PollVote.getIpAddress()) %></td>
    <td> <%= WebUtil.display(_PollVote.getPcid()) %></td>
    <td> <%= WebUtil.display(_PollVote.getDupCheckKey()) %></td>
    <td> <%= WebUtil.display(_PollVote.getNote()) %></td>
    <td> <%= WebUtil.display(_PollVote.getTimeCreated()) %></td>


<td>
<form name="pollVoteForm<%=_PollVote.getId()%>" method="post" action="/v_poll_vote_edit.html" >
    <a href="javascript:document.pollVoteForm<%=_PollVote.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollVote.getId() %>">
</form>
</td>
<td>
<a href="/pollVoteAction.html?del=true&id=<%=_PollVote.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>