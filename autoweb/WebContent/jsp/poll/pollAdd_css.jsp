<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    Poll _PollDefault = new Poll();// PollDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_PollDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _serialValue= (reqParams.get("serial")==null?WebUtil.display(_PollDefault.getSerial()):WebUtil.display((String)reqParams.get("serial")));
    String _typeValue= (reqParams.get("type")==null?WebUtil.display(_PollDefault.getType()):WebUtil.display((String)reqParams.get("type")));
    String _categoryValue= (reqParams.get("category")==null?WebUtil.display(_PollDefault.getCategory()):WebUtil.display((String)reqParams.get("category")));
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_PollDefault.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _questionValue= (reqParams.get("question")==null?WebUtil.display(_PollDefault.getQuestion()):WebUtil.display((String)reqParams.get("question")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_PollDefault.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _disableValue= (reqParams.get("disable")==null?WebUtil.display(_PollDefault.getDisable()):WebUtil.display((String)reqParams.get("disable")));
    String _allow_multipleValue= (reqParams.get("allowMultiple")==null?WebUtil.display(_PollDefault.getAllowMultiple()):WebUtil.display((String)reqParams.get("allowMultiple")));
    String _random_answerValue= (reqParams.get("randomAnswer")==null?WebUtil.display(_PollDefault.getRandomAnswer()):WebUtil.display((String)reqParams.get("randomAnswer")));
    String _hide_commentsValue= (reqParams.get("hideComments")==null?WebUtil.display(_PollDefault.getHideComments()):WebUtil.display((String)reqParams.get("hideComments")));
    String _hide_resultsValue= (reqParams.get("hideResults")==null?WebUtil.display(_PollDefault.getHideResults()):WebUtil.display((String)reqParams.get("hideResults")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PollDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PollDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
    String _time_expiredValue= (reqParams.get("timeExpired")==null?WebUtil.display(_PollDefault.getTimeExpired()):WebUtil.display((String)reqParams.get("timeExpired")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="pollForm_topArea" class="formTopArea"></div>
<div id="pollForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="pollForm" method="POST" action="/pollAction.html" >







	<div id="pollForm_type_field">
    <div id="pollForm_type_label" class="formLabel" >Type </div>
    <div id="pollForm_type_dropdown" class="formFieldDropDown" >       
        <select id="field" name="type">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _typeValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="pollForm_category_field">
    <div id="pollForm_category_label" class="formLabel" >Category </div>
    <div id="pollForm_category_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="category" value="<%=WebUtil.display(_categoryValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollForm_title_field">
    <div id="pollForm_title_label" class="formLabel" >Title </div>
    <div id="pollForm_title_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="title" value="<%=WebUtil.display(_titleValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollForm_question_field">
    <div id="pollForm_question_label" class="formRequiredLabel" >Question* </div>
    <div id="pollForm_question_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="question" value="<%=WebUtil.display(_questionValue)%>"/> 
    </div>      
	</div><div class="clear"></div>



	<div id="pollForm_hide_field">
    <div id="pollForm_hide_label" class="formLabel" >Hide </div>
    <div id="pollForm_hide_dropdown" class="formFieldDropDown" >       
        <select name="hide">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="pollForm_disable_field">
    <div id="pollForm_disable_label" class="formLabel" >Disable </div>
    <div id="pollForm_disable_dropdown" class="formFieldDropDown" >       
        <select name="disable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="pollForm_allowMultiple_field">
    <div id="pollForm_allowMultiple_label" class="formLabel" >Allow Multiple </div>
    <div id="pollForm_allowMultiple_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" id="field" name="allowMultiple" <%=HtmlUtil.getCheckedBoxValue(_allow_multipleValue)%> />
    </div>      
	</div><div class="clear"></div>




	<div id="pollForm_randomAnswer_field">
    <div id="pollForm_randomAnswer_label" class="formLabel" >Random Answer </div>
    <div id="pollForm_randomAnswer_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" id="field" name="randomAnswer" <%=HtmlUtil.getCheckedBoxValue(_random_answerValue)%> />
    </div>      
	</div><div class="clear"></div>




	<div id="pollForm_hideComments_field">
    <div id="pollForm_hideComments_label" class="formLabel" >Hide Comments </div>
    <div id="pollForm_hideComments_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" id="field" name="hideComments" <%=HtmlUtil.getCheckedBoxValue(_hide_commentsValue)%> />
    </div>      
	</div><div class="clear"></div>




	<div id="pollForm_hideResults_field">
    <div id="pollForm_hideResults_label" class="formLabel" >Hide Results </div>
    <div id="pollForm_hideResults_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" id="field" name="hideResults" <%=HtmlUtil.getCheckedBoxValue(_hide_resultsValue)%> />
    </div>      
	</div><div class="clear"></div>














        <div id="pollForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollForm.submit();">Submit</a>
        </div>      

        <div id="pollForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.pollForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="pollForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PollDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        Poll _Poll = (Poll) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _Poll.getId() %> </td>

    <td> <%= WebUtil.display(_Poll.getUserId()) %></td>
    <td> <%= WebUtil.display(_Poll.getSerial()) %></td>
    <td> <%= WebUtil.display(_Poll.getType()) %></td>
    <td> <%= WebUtil.display(_Poll.getCategory()) %></td>
    <td> <%= WebUtil.display(_Poll.getTitle()) %></td>
    <td> <%= WebUtil.display(_Poll.getQuestion()) %></td>
    <td> <%= WebUtil.display(_Poll.getHide()) %></td>
    <td> <%= WebUtil.display(_Poll.getDisable()) %></td>
    <td> <%= WebUtil.display(_Poll.getAllowMultiple()) %></td>
    <td> <%= WebUtil.display(_Poll.getRandomAnswer()) %></td>
    <td> <%= WebUtil.display(_Poll.getHideComments()) %></td>
    <td> <%= WebUtil.display(_Poll.getHideResults()) %></td>
    <td> <%= WebUtil.display(_Poll.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_Poll.getTimeUpdated()) %></td>
    <td> <%= WebUtil.display(_Poll.getTimeExpired()) %></td>


<td>
<form name="pollForm<%=_Poll.getId()%>" method="get" action="/v_poll_edit.html" >
    <a href="javascript:document.pollForm<%=_Poll.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Poll.getId() %>">
</form>
<form name="pollForm<%=_Poll.getId()%>2" method="get" action="/v_poll_edit2.html" >
    <a href="javascript:document.pollForm<%=_Poll.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Poll.getId() %>">
</form>

</td>
<td>
<a href="/pollAction.html?del=true&id=<%=_Poll.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>