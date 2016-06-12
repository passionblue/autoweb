<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    PollVote _PollVote = PollVoteDS.getInstance().getById(id);

    if ( _PollVote == null ) {
        return;
    }

    String _poll_idValue=  WebUtil.display(_PollVote.getPollId());
    String _user_idValue=  WebUtil.display(_PollVote.getUserId());
    String _answerValue=  WebUtil.display(_PollVote.getAnswer());
    String _multiple_answerValue=  WebUtil.display(_PollVote.getMultipleAnswer());
    String _by_guestValue=  WebUtil.display(_PollVote.getByGuest());
    String _ip_addressValue=  WebUtil.display(_PollVote.getIpAddress());
    String _pcidValue=  WebUtil.display(_PollVote.getPcid());
    String _dup_check_keyValue=  WebUtil.display(_PollVote.getDupCheckKey());
    String _noteValue=  WebUtil.display(_PollVote.getNote());
    String _time_createdValue=  WebUtil.display(_PollVote.getTimeCreated());
%> 

<br>
<form name="pollVoteFormEdit" method="post" action="/pollVoteAction.html" >
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
            <b><a href="javascript:document.pollVoteFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollVote.getId()%>">
</form>
