<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ChurMember _ChurMemberDefault = new ChurMember();// ChurMemberDS.getInstance().getDeafult();
    
    String _full_nameValue= (reqParams.get("fullName")==null?WebUtil.display(_ChurMemberDefault.getFullName()):WebUtil.display((String)reqParams.get("fullName")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_ChurMemberDefault.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_ChurMemberDefault.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_ChurMemberDefault.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _other_nameValue= (reqParams.get("otherName")==null?WebUtil.display(_ChurMemberDefault.getOtherName()):WebUtil.display((String)reqParams.get("otherName")));
    String _householdValue= (reqParams.get("household")==null?WebUtil.display(_ChurMemberDefault.getHousehold()):WebUtil.display((String)reqParams.get("household")));
    String _household_idValue= (reqParams.get("householdId")==null?WebUtil.display(_ChurMemberDefault.getHouseholdId()):WebUtil.display((String)reqParams.get("householdId")));
    String _is_groupValue= (reqParams.get("isGroup")==null?WebUtil.display(_ChurMemberDefault.getIsGroup()):WebUtil.display((String)reqParams.get("isGroup")));
    String _is_guestValue= (reqParams.get("isGuest")==null?WebUtil.display(_ChurMemberDefault.getIsGuest()):WebUtil.display((String)reqParams.get("isGuest")));
    String _is_speakerValue= (reqParams.get("isSpeaker")==null?WebUtil.display(_ChurMemberDefault.getIsSpeaker()):WebUtil.display((String)reqParams.get("isSpeaker")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ChurMemberDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _list_indexValue= (reqParams.get("listIndex")==null?WebUtil.display(_ChurMemberDefault.getListIndex()):WebUtil.display((String)reqParams.get("listIndex")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_member_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="churMemberForm_topArea" class="formTopArea"></div>
<div id="churMemberForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="churMemberForm" method="POST" action="/churMemberAction.html" >




	<div id="churMemberForm_fullName_field">
    <div id="churMemberForm_fullName_label" class="formLabel" >Full Name </div>
    <div id="churMemberForm_fullName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="fullName" value="<%=WebUtil.display(_full_nameValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churMemberForm_firstName_field">
    <div id="churMemberForm_firstName_label" class="formLabel" >First Name </div>
    <div id="churMemberForm_firstName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churMemberForm_lastName_field">
    <div id="churMemberForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="churMemberForm_lastName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churMemberForm_title_field">
    <div id="churMemberForm_title_label" class="formLabel" >Title </div>
    <div id="churMemberForm_title_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churMemberForm_otherName_field">
    <div id="churMemberForm_otherName_label" class="formLabel" >Other Name </div>
    <div id="churMemberForm_otherName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="otherName" value="<%=WebUtil.display(_other_nameValue)%>"/>
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
    <div id="churMemberForm_listIndex_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="listIndex" value="<%=WebUtil.display(_list_indexValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="churMemberForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churMemberForm.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      
            

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="churMemberForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ChurMemberDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurMember _ChurMember = (ChurMember) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurMember.getId() %> </td>

    <td> <%= WebUtil.display(_ChurMember.getFullName()) %></td>
    <td> <%= WebUtil.display(_ChurMember.getFirstName()) %></td>
    <td> <%= WebUtil.display(_ChurMember.getLastName()) %></td>
    <td> <%= WebUtil.display(_ChurMember.getTitle()) %></td>
    <td> <%= WebUtil.display(_ChurMember.getOtherName()) %></td>
    <td> <%= WebUtil.display(_ChurMember.getHousehold()) %></td>
    <td> <%= WebUtil.display(_ChurMember.getHouseholdId()) %></td>
    <td> <%= WebUtil.display(_ChurMember.getIsGroup()) %></td>
    <td> <%= WebUtil.display(_ChurMember.getIsGuest()) %></td>
    <td> <%= WebUtil.display(_ChurMember.getIsSpeaker()) %></td>
    <td> <%= WebUtil.display(_ChurMember.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_ChurMember.getListIndex()) %></td>


<td>
<form name="churMemberForm<%=_ChurMember.getId()%>2" method="get" action="/v_chur_member_edit2.html" >
    <a href="javascript:document.churMemberForm<%=_ChurMember.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurMember.getId() %>">
</form>

</td>
<td>
<a href="/churMemberAction.html?del=true&id=<%=_ChurMember.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>