<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    PollComment _PollComment = PollCommentDS.getInstance().getById(id);

    if ( _PollComment == null ) {
        return;
    }

    String _poll_idValue=  WebUtil.display(_PollComment.getPollId());
    String _user_idValue=  WebUtil.display(_PollComment.getUserId());
    String _commentValue=  WebUtil.display(_PollComment.getComment());
    String _hideValue=  WebUtil.display(_PollComment.getHide());
    String _time_createdValue=  WebUtil.display(_PollComment.getTimeCreated());
%> 

<br>
<form name="pollCommentFormEdit" method="post" action="/pollCommentAction.html" >
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
            <b><a href="javascript:document.pollCommentFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollComment.getId()%>">
</form>
