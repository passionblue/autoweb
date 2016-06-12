<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    PollAnswer _PollAnswerDefault = new PollAnswer();// PollAnswerDS.getInstance().getDeafult();
    
    String _poll_idValue= (reqParams.get("pollId")==null?WebUtil.display(_PollAnswerDefault.getPollId()):WebUtil.display((String)reqParams.get("pollId")));
    String _answer_numValue= (reqParams.get("answerNum")==null?WebUtil.display(_PollAnswerDefault.getAnswerNum()):WebUtil.display((String)reqParams.get("answerNum")));
    String _textValue= (reqParams.get("text")==null?WebUtil.display(_PollAnswerDefault.getText()):WebUtil.display((String)reqParams.get("text")));
    String _image_urlValue= (reqParams.get("imageUrl")==null?WebUtil.display(_PollAnswerDefault.getImageUrl()):WebUtil.display((String)reqParams.get("imageUrl")));
    String _image_onlyValue= (reqParams.get("imageOnly")==null?WebUtil.display(_PollAnswerDefault.getImageOnly()):WebUtil.display((String)reqParams.get("imageOnly")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="pollAnswerForm" method="post" action="/pollAnswerAction.html" >
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
            <b><a href="javascript:document.pollAnswerForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PollAnswerDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollAnswer _PollAnswer = (PollAnswer) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PollAnswer.getId() %> </td>

    <td> <%= WebUtil.display(_PollAnswer.getPollId()) %></td>
    <td> <%= WebUtil.display(_PollAnswer.getAnswerNum()) %></td>
    <td> <%= WebUtil.display(_PollAnswer.getText()) %></td>
    <td> <%= WebUtil.display(_PollAnswer.getImageUrl()) %></td>
    <td> <%= WebUtil.display(_PollAnswer.getImageOnly()) %></td>


<td>
<form name="pollAnswerForm<%=_PollAnswer.getId()%>" method="post" action="/v_poll_answer_edit.html" >
    <a href="javascript:document.pollAnswerForm<%=_PollAnswer.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollAnswer.getId() %>">
</form>
</td>
<td>
<a href="/pollAnswerAction.html?del=true&id=<%=_PollAnswer.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>