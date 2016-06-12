<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	PollAnswerDS ds = PollAnswerDS.getInstance();    
    

%> 

<!-- =================== PAGING =================== -->
<%
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), 20);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_poll_answer_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_poll_answer_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_poll_answer_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_poll_answer_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
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
            <a href="t_poll_answer_add2.html?prv_returnPage=poll_answer_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="pollAnswerForm_answerNum_label" style="font-size: normal normal bold 10px verdana;" >Answer Num </div>
    </td> 
    <td> 
	    <div id="pollAnswerForm_text_label" style="font-size: normal normal bold 10px verdana;" >Text </div>
    </td> 
    <td> 
	    <div id="pollAnswerForm_imageUrl_label" style="font-size: normal normal bold 10px verdana;" >Image Url </div>
    </td> 
    <td> 
	    <div id="pollAnswerForm_imageOnly_label" style="font-size: normal normal bold 10px verdana;" >Image Only </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollAnswer _PollAnswer = (PollAnswer) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PollAnswer.getId() %> </td>



    <td> 
	<form name="pollAnswerFormEditField_AnswerNum_<%=_PollAnswer.getId()%>" method="get" action="/pollAnswerAction.html" >


		<div id="pollAnswerForm_answerNum_field">
	    <div id="pollAnswerForm_answerNum_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="answerNum" value="<%=WebUtil.display(_PollAnswer.getAnswerNum())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollAnswerFormEditField_AnswerNum_<%=_PollAnswer.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollAnswer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_answer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollAnswerFormEditField_Text_<%=_PollAnswer.getId()%>" method="get" action="/pollAnswerAction.html" >


		<div id="pollAnswerForm_text_field">
	    <div id="pollAnswerForm_text_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="text" value="<%=WebUtil.display(_PollAnswer.getText())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollAnswerFormEditField_Text_<%=_PollAnswer.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollAnswer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_answer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollAnswerFormEditField_ImageUrl_<%=_PollAnswer.getId()%>" method="get" action="/pollAnswerAction.html" >


		<div id="pollAnswerForm_imageUrl_field">
	    <div id="pollAnswerForm_imageUrl_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="imageUrl" value="<%=WebUtil.display(_PollAnswer.getImageUrl())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollAnswerFormEditField_ImageUrl_<%=_PollAnswer.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollAnswer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_answer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollAnswerFormEditField_ImageOnly_<%=_PollAnswer.getId()%>" method="get" action="/pollAnswerAction.html" >


		<div id="pollAnswerForm_imageOnly_field">
	    <div id="pollAnswerForm_imageOnly_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="imageOnly" value="<%=WebUtil.display(_PollAnswer.getImageOnly())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollAnswerFormEditField_ImageOnly_<%=_PollAnswer.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollAnswer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_answer_home">
	</form>
    
    
    </td>

    <td>
    <form name="pollAnswerForm<%=_PollAnswer.getId()%>" method="get" action="/v_poll_answer_edit.html" >
        <a href="javascript:document.pollAnswerForm<%=_PollAnswer.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollAnswer.getId() %>">
    </form>
    <form name="pollAnswerForm<%=_PollAnswer.getId()%>2" method="get" action="/v_poll_answer_edit2.html" >
        <a href="javascript:document.pollAnswerForm<%=_PollAnswer.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollAnswer.getId() %>">
    </form>
    </td>

    <td>
    <a href="/pollAnswerAction.html?del=true&id=<%=_PollAnswer.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>