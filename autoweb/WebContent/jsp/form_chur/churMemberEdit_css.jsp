<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
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

    ChurMember _ChurMember = ChurMemberDS.getInstance().getById(id);

    if ( _ChurMember == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_member_home";

    String _full_nameValue=  WebUtil.display(_ChurMember.getFullName());
    String _first_nameValue=  WebUtil.display(_ChurMember.getFirstName());
    String _last_nameValue=  WebUtil.display(_ChurMember.getLastName());
    String _titleValue=  WebUtil.display(_ChurMember.getTitle());
    String _other_nameValue=  WebUtil.display(_ChurMember.getOtherName());
    String _householdValue=  WebUtil.display(_ChurMember.getHousehold());
    String _household_idValue=  WebUtil.display(_ChurMember.getHouseholdId());
    String _is_groupValue=  WebUtil.display(_ChurMember.getIsGroup());
    String _is_guestValue=  WebUtil.display(_ChurMember.getIsGuest());
    String _is_speakerValue=  WebUtil.display(_ChurMember.getIsSpeaker());
    String _time_createdValue=  WebUtil.display(_ChurMember.getTimeCreated());
    String _list_indexValue=  WebUtil.display(_ChurMember.getListIndex());
%> 

<br>
<div id="churMemberForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churMemberFormEdit" method="POST" action="/churMemberAction.html" >




	<div id="churMemberForm_fullName_field">
    <div id="churMemberForm_fullName_label" class="formLabel" >Full Name </div>
    <div id="churMemberForm_fullName_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="fullName" value="<%=WebUtil.display(_full_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churMemberForm_firstName_field">
    <div id="churMemberForm_firstName_label" class="formLabel" >First Name </div>
    <div id="churMemberForm_firstName_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churMemberForm_lastName_field">
    <div id="churMemberForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="churMemberForm_lastName_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churMemberForm_title_field">
    <div id="churMemberForm_title_label" class="formLabel" >Title </div>
    <div id="churMemberForm_title_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churMemberForm_otherName_field">
    <div id="churMemberForm_otherName_label" class="formLabel" >Other Name </div>
    <div id="churMemberForm_otherName_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="otherName" value="<%=WebUtil.display(_other_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="churMemberForm_household_field">
    <div id="churMemberForm_household_label" class="formLabel" >Household </div>
    <div id="churMemberForm_household_dropdown" class="formFieldDropDown" >       
        <select name="household">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _householdValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _householdValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>



    <INPUT TYPE="HIDDEN" NAME="householdId" value="<%=WebUtil.display(_household_idValue)%>" />



	<div id="churMemberForm_isGroup_field">
    <div id="churMemberForm_isGroup_label" class="formLabel" >Is Group </div>
    <div id="churMemberForm_isGroup_dropdown" class="formFieldDropDown" >       
        <select name="isGroup">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_groupValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_groupValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="churMemberForm_isGuest_field">
    <div id="churMemberForm_isGuest_label" class="formLabel" >Is Guest </div>
    <div id="churMemberForm_isGuest_dropdown" class="formFieldDropDown" >       
        <select name="isGuest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_guestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_guestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="churMemberForm_isSpeaker_field">
    <div id="churMemberForm_isSpeaker_label" class="formLabel" >Is Speaker </div>
    <div id="churMemberForm_isSpeaker_dropdown" class="formFieldDropDown" >       
        <select name="isSpeaker">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_speakerValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_speakerValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>







	<div id="churMemberForm_listIndex_field">
    <div id="churMemberForm_listIndex_label" class="formLabel" >List Index </div>
    <div id="churMemberForm_listIndex_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="listIndex" value="<%=WebUtil.display(_list_indexValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="churMemberFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churMemberFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
