<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    PollVote _PollVoteDefault = new PollVote();// PollVoteDS.getInstance().getDeafult();
    
    String _poll_idValue= (reqParams.get("pollId")==null?WebUtil.display(_PollVoteDefault.getPollId()):WebUtil.display((String)reqParams.get("pollId")));
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_PollVoteDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _answerValue= (reqParams.get("answer")==null?WebUtil.display(_PollVoteDefault.getAnswer()):WebUtil.display((String)reqParams.get("answer")));
    String _multiple_answerValue= (reqParams.get("multipleAnswer")==null?WebUtil.display(_PollVoteDefault.getMultipleAnswer()):WebUtil.display((String)reqParams.get("multipleAnswer")));
    String _by_guestValue= (reqParams.get("byGuest")==null?WebUtil.display(_PollVoteDefault.getByGuest()):WebUtil.display((String)reqParams.get("byGuest")));
    String _ip_addressValue= (reqParams.get("ipAddress")==null?WebUtil.display(_PollVoteDefault.getIpAddress()):WebUtil.display((String)reqParams.get("ipAddress")));
    String _pcidValue= (reqParams.get("pcid")==null?WebUtil.display(_PollVoteDefault.getPcid()):WebUtil.display((String)reqParams.get("pcid")));
    String _dup_check_keyValue= (reqParams.get("dupCheckKey")==null?WebUtil.display(_PollVoteDefault.getDupCheckKey()):WebUtil.display((String)reqParams.get("dupCheckKey")));
    String _noteValue= (reqParams.get("note")==null?WebUtil.display(_PollVoteDefault.getNote()):WebUtil.display((String)reqParams.get("note")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PollVoteDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="pollVoteForm_topArea" class="formTopArea"></div>
<div id="pollVoteForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="pollVoteForm" method="get" action="/pollVoteAction.html" >




	<div id="pollVoteForm_pollId_field">
    <div id="pollVoteForm_pollId_label" class="formLabel" >Poll Id </div>
    <div id="pollVoteForm_pollId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="pollId" value="<%=WebUtil.display(_poll_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>






	<div id="pollVoteForm_answer_field">
    <div id="pollVoteForm_answer_label" class="formLabel" >Answer </div>
    <div id="pollVoteForm_answer_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="answer" value="<%=WebUtil.display(_answerValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollVoteForm_multipleAnswer_field">
    <div id="pollVoteForm_multipleAnswer_label" class="formLabel" >Multiple Answer </div>
    <div id="pollVoteForm_multipleAnswer_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="multipleAnswer" value="<%=WebUtil.display(_multiple_answerValue)%>"/>
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
    <div id="pollVoteForm_ipAddress_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="ipAddress" value="<%=WebUtil.display(_ip_addressValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollVoteForm_pcid_field">
    <div id="pollVoteForm_pcid_label" class="formLabel" >Pcid </div>
    <div id="pollVoteForm_pcid_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="pcid" value="<%=WebUtil.display(_pcidValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollVoteForm_dupCheckKey_field">
    <div id="pollVoteForm_dupCheckKey_label" class="formLabel" >Dup Check Key </div>
    <div id="pollVoteForm_dupCheckKey_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="dupCheckKey" value="<%=WebUtil.display(_dup_check_keyValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollVoteForm_note_field">
    <div id="pollVoteForm_note_label" class="formLabel" >Note </div>
    <div id="pollVoteForm_note_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="note" value="<%=WebUtil.display(_noteValue)%>"/>
    </div>      
	</div><div class="clear"></div>





        <div id="pollVoteForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.pollVoteForm.submit();">Cancel</a>
        </div>      

        <div id="pollVoteForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollVoteForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="pollVoteForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PollVoteDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollVote _PollVote = (PollVote) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PollVote.getId() %> </td>

    <td> <%= WebUtil.display(_PollVote.getPollId()) %></td>
    <td> <%= WebUtil.display(_PollVote.getUserId()) %></td>
    <td> <%= WebUtil.display(_PollVote.getAnswer()) %></td>
    <td> <%= WebUtil.display(_PollVote.getMultipleAnswer()) %></td>
    <td> <%= WebUtil.display(_PollVote.getByGuest()) %></td>
    <td> <%= WebUtil.display(_PollVote.getIpAddress()) %></td>
    <td> <%= WebUtil.display(_PollVote.getPcid()) %></td>
    <td> <%= WebUtil.display(_PollVote.getDupCheckKey()) %></td>
    <td> <%= WebUtil.display(_PollVote.getNote()) %></td>
    <td> <%= WebUtil.display(_PollVote.getTimeCreated()) %></td>


<td>
<form name="pollVoteForm<%=_PollVote.getId()%>" method="get" action="/v_poll_vote_edit.html" >
    <a href="javascript:document.pollVoteForm<%=_PollVote.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollVote.getId() %>">
</form>
<form name="pollVoteForm<%=_PollVote.getId()%>2" method="get" action="/v_poll_vote_edit2.html" >
    <a href="javascript:document.pollVoteForm<%=_PollVote.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollVote.getId() %>">
</form>

</td>
<td>
<a href="/pollVoteAction.html?del=true&id=<%=_PollVote.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>