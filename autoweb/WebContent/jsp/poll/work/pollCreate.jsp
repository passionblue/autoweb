<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    Poll _PollDefault = new Poll();// PollDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_PollDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
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
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="pollForm" method="get" action="/pollCreateAction.html" >


	<div id="pollForm_type_field">
    <div id="pollForm_type_label" class="formLabel" >Type </div>
    <div id="pollForm_type_dropdown" class="formFieldDropDown" >       
        <select id="field" name="type">
        <option value="" >- Please Select -</option>
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _typeValue)%>>Default</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _typeValue)%>>Image Buttons</option>
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
        <input type="checkbox" name="allowMultiple" <%=HtmlUtil.getCheckedBoxValue(_allow_multipleValue)%> />
    </div>      
	</div><div class="clear"></div>




	<div id="pollForm_randomAnswer_field">
    <div id="pollForm_randomAnswer_label" class="formLabel" >Random Answer </div>
    <div id="pollForm_randomAnswer_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" name="randomAnswer" <%=HtmlUtil.getCheckedBoxValue(_random_answerValue)%> />
    </div>      
	</div><div class="clear"></div>


	<div id="pollForm_hideComments_field">
    <div id="pollForm_hideComments_label" class="formLabel" >Hide Comments </div>
    <div id="pollForm_hideComments_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" name="hideComments" <%=HtmlUtil.getCheckedBoxValue(_hide_commentsValue)%> />
    </div>      
	</div><div class="clear"></div>


	<div id="pollForm_hideResults_field">
    <div id="pollForm_hideResults_label" class="formLabel" >Hide Results </div>
    <div id="pollForm_hideResults_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" name="hideResults" <%=HtmlUtil.getCheckedBoxValue(_hide_resultsValue)%> />
    </div>      
	</div><div class="clear"></div>

	<div id="empty"></div>

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>

<a href="#" id="addEntry"> Add Answer </a> <br/>
</div>

    <div id="pollForm_cancel" class="formCancel" >       
        <a id="formSubmit" href="javascript:document.pollForm.submit();">Cancel</a>
    </div>      

    <div id="pollForm_submit" class="formSubmit" >       
        <a id="formSubmit" href="javascript:document.pollForm.submit();">Submit</a>
    </div>      

    <div id="pollForm_cancel" class="formCancel" >       
        <a id="formSubmit" href="javascript:document.pollForm.submit();">Clear Fields</a>
    </div>      


</div> <!-- form -->
<div id="pollForm_bottomArea" class="formBottomArea"></div>
