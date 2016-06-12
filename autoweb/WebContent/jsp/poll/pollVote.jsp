<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	PollVoteDS ds = PollVoteDS.getInstance();    
    

%> 

<!-- =================== PAGING =================== -->
<%
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), 20);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_poll_vote_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_poll_vote_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_poll_vote_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_poll_vote_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
	}

	List pageList = new ArrayList();
	for(int i = pagingInfo.getBeginIdx() ; i < pagingInfo.getEndIdx();i++){
		pageList.add(list.get(i));
	}
	list = pageList;
%>
	<%= prevLinkStr %>
<%
	for(int p = 0 ; p< pageIndexShortcut.length; p++){
%>
	<%=pageIndexShortcut[p] %> /
<%
	}
%>	
	<%= nextLinkStr %>
<!-- =================== END PAGING =================== -->


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_poll_vote_add2.html?prv_returnPage=poll_vote_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="pollVoteForm_pollId_label" style="font-size: normal normal bold 10px verdana;" >Poll Id </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_answer_label" style="font-size: normal normal bold 10px verdana;" >Answer </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_multipleAnswer_label" style="font-size: normal normal bold 10px verdana;" >Multiple Answer </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_byGuest_label" style="font-size: normal normal bold 10px verdana;" >By Guest </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_ipAddress_label" style="font-size: normal normal bold 10px verdana;" >Ip Address </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_pcid_label" style="font-size: normal normal bold 10px verdana;" >Pcid </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_dupCheckKey_label" style="font-size: normal normal bold 10px verdana;" >Dup Check Key </div>
    </td> 
    <td> 
	    <div id="pollVoteForm_note_label" style="font-size: normal normal bold 10px verdana;" >Note </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollVote _PollVote = (PollVote) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PollVote.getId() %> </td>


    <td> 
	<form name="pollVoteFormEditField_PollId_<%=_PollVote.getId()%>" method="get" action="/pollVoteAction.html" >


		<div id="pollVoteForm_pollId_field">
	    <div id="pollVoteForm_pollId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pollId" value="<%=WebUtil.display(_PollVote.getPollId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollVoteFormEditField_PollId_<%=_PollVote.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollVote.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_vote_home">
	</form>
    
    
    </td>


    <td> 
	<form name="pollVoteFormEditField_Answer_<%=_PollVote.getId()%>" method="get" action="/pollVoteAction.html" >


		<div id="pollVoteForm_answer_field">
	    <div id="pollVoteForm_answer_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="answer" value="<%=WebUtil.display(_PollVote.getAnswer())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollVoteFormEditField_Answer_<%=_PollVote.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollVote.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_vote_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollVoteFormEditField_MultipleAnswer_<%=_PollVote.getId()%>" method="get" action="/pollVoteAction.html" >


		<div id="pollVoteForm_multipleAnswer_field">
	    <div id="pollVoteForm_multipleAnswer_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="multipleAnswer" value="<%=WebUtil.display(_PollVote.getMultipleAnswer())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollVoteFormEditField_MultipleAnswer_<%=_PollVote.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollVote.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_vote_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollVoteFormEditField_ByGuest_<%=_PollVote.getId()%>" method="get" action="/pollVoteAction.html" >


		<div id="pollVoteForm_byGuest_field">
	    <div id="pollVoteForm_byGuest_dropdown" class="formFieldDropDown" >       
	        <select name="byGuest">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _PollVote.getByGuest())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _PollVote.getByGuest())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollVoteFormEditField_ByGuest_<%=_PollVote.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollVote.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_vote_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollVoteFormEditField_IpAddress_<%=_PollVote.getId()%>" method="get" action="/pollVoteAction.html" >


		<div id="pollVoteForm_ipAddress_field">
	    <div id="pollVoteForm_ipAddress_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="ipAddress" value="<%=WebUtil.display(_PollVote.getIpAddress())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollVoteFormEditField_IpAddress_<%=_PollVote.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollVote.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_vote_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollVoteFormEditField_Pcid_<%=_PollVote.getId()%>" method="get" action="/pollVoteAction.html" >


		<div id="pollVoteForm_pcid_field">
	    <div id="pollVoteForm_pcid_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pcid" value="<%=WebUtil.display(_PollVote.getPcid())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollVoteFormEditField_Pcid_<%=_PollVote.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollVote.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_vote_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollVoteFormEditField_DupCheckKey_<%=_PollVote.getId()%>" method="get" action="/pollVoteAction.html" >


		<div id="pollVoteForm_dupCheckKey_field">
	    <div id="pollVoteForm_dupCheckKey_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="dupCheckKey" value="<%=WebUtil.display(_PollVote.getDupCheckKey())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollVoteFormEditField_DupCheckKey_<%=_PollVote.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollVote.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_vote_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollVoteFormEditField_Note_<%=_PollVote.getId()%>" method="get" action="/pollVoteAction.html" >


		<div id="pollVoteForm_note_field">
	    <div id="pollVoteForm_note_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="note" value="<%=WebUtil.display(_PollVote.getNote())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollVoteFormEditField_Note_<%=_PollVote.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollVote.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_vote_home">
	</form>
    
    
    </td>


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