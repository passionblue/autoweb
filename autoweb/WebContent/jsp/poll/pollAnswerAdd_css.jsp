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

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="pollAnswerForm_topArea" class="formTopArea"></div>
<div id="pollAnswerForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="pollAnswerForm" method="get" action="/pollAnswerAction.html" >






	<div id="pollAnswerForm_answerNum_field">
    <div id="pollAnswerForm_answerNum_label" class="formLabel" >Answer Num </div>
    <div id="pollAnswerForm_answerNum_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="answerNum" value="<%=WebUtil.display(_answer_numValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollAnswerForm_text_field">
    <div id="pollAnswerForm_text_label" class="formLabel" >Text </div>
    <div id="pollAnswerForm_text_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="text" value="<%=WebUtil.display(_textValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollAnswerForm_imageUrl_field">
    <div id="pollAnswerForm_imageUrl_label" class="formLabel" >Image Url </div>
    <div id="pollAnswerForm_imageUrl_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="imageUrl" value="<%=WebUtil.display(_image_urlValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollAnswerForm_imageOnly_field">
    <div id="pollAnswerForm_imageOnly_label" class="formLabel" >Image Only </div>
    <div id="pollAnswerForm_imageOnly_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="imageOnly" value="<%=WebUtil.display(_image_onlyValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="pollAnswerForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.pollAnswerForm.submit();">Cancel</a>
        </div>      

        <div id="pollAnswerForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollAnswerForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="pollAnswerForm_bottomArea" class="formBottomArea"></div>


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
<form name="pollAnswerForm<%=_PollAnswer.getId()%>" method="get" action="/v_poll_answer_edit.html" >
    <a href="javascript:document.pollAnswerForm<%=_PollAnswer.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollAnswer.getId() %>">
</form>
<form name="pollAnswerForm<%=_PollAnswer.getId()%>2" method="get" action="/v_poll_answer_edit2.html" >
    <a href="javascript:document.pollAnswerForm<%=_PollAnswer.getId()%>2.submit();">Edit2</a>           
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