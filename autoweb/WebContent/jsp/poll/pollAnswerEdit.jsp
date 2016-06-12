<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    PollAnswer _PollAnswer = PollAnswerDS.getInstance().getById(id);

    if ( _PollAnswer == null ) {
        return;
    }

    String _poll_idValue=  WebUtil.display(_PollAnswer.getPollId());
    String _answer_numValue=  WebUtil.display(_PollAnswer.getAnswerNum());
    String _textValue=  WebUtil.display(_PollAnswer.getText());
    String _image_urlValue=  WebUtil.display(_PollAnswer.getImageUrl());
    String _image_onlyValue=  WebUtil.display(_PollAnswer.getImageOnly());
%> 

<br>
<form name="pollAnswerFormEdit" method="post" action="/pollAnswerAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Poll Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="pollId" value="<%=WebUtil.display(_poll_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Answer Num</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="answerNum" value="<%=WebUtil.display(_answer_numValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Text</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="text" value="<%=WebUtil.display(_textValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Image Url</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="imageUrl" value="<%=WebUtil.display(_image_urlValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Image Only</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="imageOnly" value="<%=WebUtil.display(_image_onlyValue)%>"/></TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.pollAnswerFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollAnswer.getId()%>">
</form>
