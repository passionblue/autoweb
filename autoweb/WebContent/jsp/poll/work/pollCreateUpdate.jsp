<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    Poll _Poll = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_Poll = PollDS.getInstance().getById(id);
		if ( _Poll == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
		}
		isEdit = true;

        
	} else {

	    _Poll = new Poll();// PollDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    

    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_Poll.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _serialValue= (reqParams.get("serial")==null?WebUtil.display(_Poll.getSerial()):WebUtil.display((String)reqParams.get("serial")));
    String _typeValue= (reqParams.get("type")==null?WebUtil.display(_Poll.getType()):WebUtil.display((String)reqParams.get("type")));
    String _categoryValue= (reqParams.get("category")==null?WebUtil.display(_Poll.getCategory()):WebUtil.display((String)reqParams.get("category")));
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_Poll.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _descriptionValue= (reqParams.get("description")==null?WebUtil.display(_Poll.getDescription()):WebUtil.display((String)reqParams.get("description")));
    String _questionValue= (reqParams.get("question")==null?WebUtil.display(_Poll.getQuestion()):WebUtil.display((String)reqParams.get("question")));
    String _publishedValue= (reqParams.get("published")==null?WebUtil.display(_Poll.getPublished()):WebUtil.display((String)reqParams.get("published")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_Poll.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _disableValue= (reqParams.get("disable")==null?WebUtil.display(_Poll.getDisable()):WebUtil.display((String)reqParams.get("disable")));
    String _allow_multipleValue= (reqParams.get("allowMultiple")==null?WebUtil.display(_Poll.getAllowMultiple()):WebUtil.display((String)reqParams.get("allowMultiple")));
    String _random_answerValue= (reqParams.get("randomAnswer")==null?WebUtil.display(_Poll.getRandomAnswer()):WebUtil.display((String)reqParams.get("randomAnswer")));
    String _hide_commentsValue= (reqParams.get("hideComments")==null?WebUtil.display(_Poll.getHideComments()):WebUtil.display((String)reqParams.get("hideComments")));
    String _hide_resultsValue= (reqParams.get("hideResults")==null?WebUtil.display(_Poll.getHideResults()):WebUtil.display((String)reqParams.get("hideResults")));
    String _use_cookie_for_dupValue= (reqParams.get("useCookieForDup")==null?WebUtil.display(_Poll.getUseCookieForDup()):WebUtil.display((String)reqParams.get("useCookieForDup")));
    String _repeat_every_dayValue= (reqParams.get("repeatEveryDay")==null?WebUtil.display(_Poll.getRepeatEveryDay()):WebUtil.display((String)reqParams.get("repeatEveryDay")));
    String _max_repeat_voteValue= (reqParams.get("maxRepeatVote")==null?WebUtil.display(_Poll.getMaxRepeatVote()):WebUtil.display((String)reqParams.get("maxRepeatVote")));
    String _num_days_openValue= (reqParams.get("numDaysOpen")==null?WebUtil.display(_Poll.getNumDaysOpen()):WebUtil.display((String)reqParams.get("numDaysOpen")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_Poll.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_Poll.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
    String _time_expiredValue= (reqParams.get("timeExpired")==null?WebUtil.display(_Poll.getTimeExpired()):WebUtil.display((String)reqParams.get("timeExpired")));
%> 

<br>
<div id="pollForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pollFormEdit" method="POST" action="/pollCreateAction.html" >


	<div id="pollForm_type_field">
    <div id="pollForm_type_label" class="formRequiredLabel" >Type </div>
    <div id="pollForm_type_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="type">
        <option value="" >- Please Select -</option>
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _typeValue)%>>Default</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _typeValue)%>>Image Only</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _typeValue)%>>Scale</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _typeValue)%>>Yes/No</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="pollForm_category_field">
    <div id="pollForm_category_label" class="formRequiredLabel" >Category </div>
    <div id="pollForm_category_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="category">
		<option value="" <%=HtmlUtil.getOptionSelect("", _categoryValue)%>>-Select Category-</option> 
<%
	List dropMenus = DropMenuUtil.getDropMenus("PollCategory");
	for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect("" + it.getCompareValue(), _categoryValue)%>><%=it.getDisplay() %></option>
<%} %>
        
  		</select>  <span></span>    	
    </div>      
	</div><div class="clear"></div> 



	<div id="pollForm_title_field">
    <div id="pollForm_title_label" class="formLabel" >Title </div>
    <div id="pollForm_title_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollForm_question_field">
    <div id="pollForm_question_label" class="formRequiredLabel" >Question* </div>
    <div id="pollForm_question_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="question" value="<%=WebUtil.display(_questionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="pollForm_description_field">
    <div id="pollForm_description_label" class="formLabel" >Description </div>
    <div id="pollForm_description_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="description" COLS="50" ROWS="8" ><%=WebUtil.display(_descriptionValue)%></TEXTAREA>
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

	<div id="pollForm_useCookieForDup_field">
    <div id="pollForm_useCookieForDup_label" class="formLabel" >Use Cookie For Dup </div>
    <div id="pollForm_useCookieForDup_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" name="useCookieForDup" <%=HtmlUtil.getCheckedBoxValue(_use_cookie_for_dupValue)%> />
    </div>      
	</div><div class="clear"></div>



	<div id="pollForm_repeatEveryDay_field">
    <div id="pollForm_repeatEveryDay_label" class="formLabel" >Repeat Every Day </div>
    <div id="pollForm_repeatEveryDay_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" name="repeatEveryDay" <%=HtmlUtil.getCheckedBoxValue(_repeat_every_dayValue)%> />
    </div>      
	</div><div class="clear"></div>

	<div id="pollForm_maxRepeatVote_field">
    <div id="pollForm_maxRepeatVote_label" class="formLabel" >Max Repeat Vote </div>
    <div id="pollForm_maxRepeatVote_text" class="formFieldText" >       
        <input id="field" type="text" size="10" name="maxRepeatVote" value="<%=WebUtil.display(_max_repeat_voteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="pollForm_numDaysOpen_field">
    <div id="pollForm_numDaysOpen_label" class="formLabel" >Num Days Open </div>
    <div id="pollForm_numDaysOpen_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="numDaysOpen" value="<%=WebUtil.display(_num_days_openValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


<% if (isEdit) { %>
<%
		List answers = PollAnswerDS.getInstance().getByPollId(_Poll.getId());
		for (Iterator iterator = answers.iterator(); iterator.hasNext();) {
	    	PollAnswer ans = (PollAnswer) iterator.next();    
%>

	<div id="pollForm_answer_field">
    <div id="pollForm_answer_label" class="formLabel" >Answer <%=ans.getAnswerNum()%></div>
    <div id="pollForm_answer_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="50" name="a<%=ans.getAnswerNum()%>" value="<%=WebUtil.display(ans.getText())%>"/> <span></span>
        <input id="field" type="text" size="30" name="m<%=ans.getAnswerNum()%>" value="<%=WebUtil.display(ans.getImageUrl())%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<%		} %>

<% 	} else { %>
	<div id="empty"></div>	

	<div id="pollForm_answer_field">
    <div id="pollForm_answer_label" class="formLabel" >&nbsp;</div>
    <div id="pollForm_answer_text" class="formFieldText" >       
		<div id="addEntry"> Add Answer </div> <br/>
    </div>      
	</div><div class="clear"></div>

<% } %>


        <div id="pollFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollFormEdit.submit();">Submit</a>
        </div>      

	    <div id="pollForm_cancel" class="formCancel" >       
	        <a href="#">Preview </a>
	    </div>      

	    <div id="pollForm_cancel" class="formCancel" style="margin-left:10px" >       
	        <a href="/moveTo.html?dest=poll_center">Cancel</a>
	    </div><div class="clear"></div>      



<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
<!-- INPUT TYPE="HIDDEN" NAME="fwdTo" value="/v_poll_create.html?cmd=edit&id=<%=_Poll.getId() %>" -->
<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/v_poll_center.html">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/v_poll_center.html">

<% } %>

</form>
</div> <!-- form -->


		<dr/>
		<dr/>
		

<div id="pollForm_bottomArea" class="formBottomArea"></div> 
