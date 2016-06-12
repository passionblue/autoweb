<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    Poll _Poll = PollDS.getInstance().getById(id);

    if ( _Poll == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    

    String _user_idValue=  WebUtil.display(_Poll.getUserId());
    String _serialValue=  WebUtil.display(_Poll.getSerial());
    String _typeValue=  WebUtil.display(_Poll.getType());
    String _categoryValue=  WebUtil.display(_Poll.getCategory());
    String _titleValue=  WebUtil.display(_Poll.getTitle());
    String _questionValue=  WebUtil.display(_Poll.getQuestion());
    String _hideValue=  WebUtil.display(_Poll.getHide());
    String _disableValue=  WebUtil.display(_Poll.getDisable());
    String _allow_multipleValue=  WebUtil.display(_Poll.getAllowMultiple());
    String _random_answerValue=  WebUtil.display(_Poll.getRandomAnswer());
    String _hide_commentsValue=  WebUtil.display(_Poll.getHideComments());
    String _hide_resultsValue=  WebUtil.display(_Poll.getHideResults());
    String _time_createdValue=  WebUtil.display(_Poll.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_Poll.getTimeUpdated());
    String _time_expiredValue=  WebUtil.display(_Poll.getTimeExpired());
%> 

<br>
<div id="pollForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pollFormEdit" method="POST" action="/pollAction.html" >


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
    <div id="pollForm_category_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="category" value="<%=WebUtil.display(_categoryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollForm_title_field">
    <div id="pollForm_title_label" class="formLabel" >Title </div>
    <div id="pollForm_title_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollForm_question_field">
    <div id="pollForm_question_label" class="formRequiredLabel" >Question* </div>
    <div id="pollForm_question_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="question" value="<%=WebUtil.display(_questionValue)%>"/> <span></span>
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








        <div id="pollFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
