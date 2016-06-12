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

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="pollCommentForm" method="post" action="/pollCommentAction.html" >
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
	        <TD align="right" ><b>Comment</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="comment" value="<%=WebUtil.display(_commentValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Hide</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="hide" value="<%=WebUtil.display(_hideValue)%>"/></TD>
	    </TR>
	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.pollCommentForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


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
<form name="pollCommentForm<%=_PollComment.getId()%>" method="post" action="/v_poll_comment_edit.html" >
    <a href="javascript:document.pollCommentForm<%=_PollComment.getId()%>.submit();">Edit</a>           
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