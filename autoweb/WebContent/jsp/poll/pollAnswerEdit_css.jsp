<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    PollAnswer _PollAnswer = PollAnswerDS.getInstance().getById(id);

    if ( _PollAnswer == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _poll_idValue=  WebUtil.display(_PollAnswer.getPollId());
    String _answer_numValue=  WebUtil.display(_PollAnswer.getAnswerNum());
    String _textValue=  WebUtil.display(_PollAnswer.getText());
    String _image_urlValue=  WebUtil.display(_PollAnswer.getImageUrl());
    String _image_onlyValue=  WebUtil.display(_PollAnswer.getImageOnly());
%> 

<br>
<div id="pollAnswerForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pollAnswerFormEdit" method="get" action="/pollAnswerAction.html" >




	<div id="pollAnswerForm_answerNum_field">
    <div id="pollAnswerForm_answerNum_label" class="formLabel" >Answer Num </div>
    <div id="pollAnswerForm_answerNum_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="answerNum" value="<%=WebUtil.display(_answer_numValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollAnswerForm_text_field">
    <div id="pollAnswerForm_text_label" class="formLabel" >Text </div>
    <div id="pollAnswerForm_text_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="text" value="<%=WebUtil.display(_textValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollAnswerForm_imageUrl_field">
    <div id="pollAnswerForm_imageUrl_label" class="formLabel" >Image Url </div>
    <div id="pollAnswerForm_imageUrl_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="imageUrl" value="<%=WebUtil.display(_image_urlValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollAnswerForm_imageOnly_field">
    <div id="pollAnswerForm_imageOnly_label" class="formLabel" >Image Only </div>
    <div id="pollAnswerForm_imageOnly_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="imageOnly" value="<%=WebUtil.display(_image_onlyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="pollAnswerFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollAnswerFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollAnswer.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
