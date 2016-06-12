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

    PollVote _PollVote = PollVoteDS.getInstance().getById(id);

    if ( _PollVote == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _poll_idValue=  WebUtil.display(_PollVote.getPollId());
    String _user_idValue=  WebUtil.display(_PollVote.getUserId());
    String _answerValue=  WebUtil.display(_PollVote.getAnswer());
    String _multiple_answerValue=  WebUtil.display(_PollVote.getMultipleAnswer());
    String _by_guestValue=  WebUtil.display(_PollVote.getByGuest());
    String _ip_addressValue=  WebUtil.display(_PollVote.getIpAddress());
    String _pcidValue=  WebUtil.display(_PollVote.getPcid());
    String _dup_check_keyValue=  WebUtil.display(_PollVote.getDupCheckKey());
    String _noteValue=  WebUtil.display(_PollVote.getNote());
    String _time_createdValue=  WebUtil.display(_PollVote.getTimeCreated());
%> 

<br>
<div id="pollVoteForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pollVoteFormEdit" method="get" action="/pollVoteAction.html" >




	<div id="pollVoteForm_pollId_field">
    <div id="pollVoteForm_pollId_label" class="formLabel" >Poll Id </div>
    <div id="pollVoteForm_pollId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="pollId" value="<%=WebUtil.display(_poll_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollVoteForm_answer_field">
    <div id="pollVoteForm_answer_label" class="formLabel" >Answer </div>
    <div id="pollVoteForm_answer_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="answer" value="<%=WebUtil.display(_answerValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollVoteForm_multipleAnswer_field">
    <div id="pollVoteForm_multipleAnswer_label" class="formLabel" >Multiple Answer </div>
    <div id="pollVoteForm_multipleAnswer_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="multipleAnswer" value="<%=WebUtil.display(_multiple_answerValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="pollVoteForm_byGuest_field">
    <div id="pollVoteForm_byGuest_label" class="formLabel" >By Guest </div>
    <div id="pollVoteForm_byGuest_dropdown" class="formFieldDropDown" >       
        <select name="byGuest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _by_guestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _by_guestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="pollVoteForm_ipAddress_field">
    <div id="pollVoteForm_ipAddress_label" class="formLabel" >Ip Address </div>
    <div id="pollVoteForm_ipAddress_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="ipAddress" value="<%=WebUtil.display(_ip_addressValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollVoteForm_pcid_field">
    <div id="pollVoteForm_pcid_label" class="formLabel" >Pcid </div>
    <div id="pollVoteForm_pcid_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="pcid" value="<%=WebUtil.display(_pcidValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollVoteForm_dupCheckKey_field">
    <div id="pollVoteForm_dupCheckKey_label" class="formLabel" >Dup Check Key </div>
    <div id="pollVoteForm_dupCheckKey_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="dupCheckKey" value="<%=WebUtil.display(_dup_check_keyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollVoteForm_note_field">
    <div id="pollVoteForm_note_label" class="formLabel" >Note </div>
    <div id="pollVoteForm_note_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="pollVoteFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollVoteFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollVote.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
