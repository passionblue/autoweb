<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	List list = new ArrayList();
	PollCommentDS ds = PollCommentDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 

<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin) optionQueryStr += "&listAllByAdmin=true";

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	list = PagingUtil.getPagedList(pagingInfo, list);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>

<!-- =================== END PAGING =================== -->


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_poll_comment_add2.html?prv_returnPage=poll_comment_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="pollCommentForm_pollId_label" style="font-size: normal normal bold 10px verdana;" >Poll Id </div>
    </td> 
    <td> 
	    <div id="pollCommentForm_comment_label" style="font-size: normal normal bold 10px verdana;" >Comment </div>
    </td> 
    <td> 
	    <div id="pollCommentForm_hide_label" style="font-size: normal normal bold 10px verdana;" >Hide </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollComment _PollComment = (PollComment) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PollComment.getId() %> </td>


    <td> 
	<form name="pollCommentFormEditField_PollId_<%=_PollComment.getId()%>" method="get" action="/pollCommentAction.html" >


		<div id="pollCommentForm_pollId_field">
	    <div id="pollCommentForm_pollId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pollId" value="<%=WebUtil.display(_PollComment.getPollId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollCommentFormEditField_PollId_<%=_PollComment.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollComment.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_comment_home">
	</form>
    
    
    </td>


    <td> 
	<form name="pollCommentFormEditField_Comment_<%=_PollComment.getId()%>" method="get" action="/pollCommentAction.html" >


		<div id="pollCommentForm_comment_field">
	    <div id="pollCommentForm_comment_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="comment" value="<%=WebUtil.display(_PollComment.getComment())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollCommentFormEditField_Comment_<%=_PollComment.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollComment.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_comment_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollCommentFormEditField_Hide_<%=_PollComment.getId()%>" method="get" action="/pollCommentAction.html" >


		<div id="pollCommentForm_hide_field">
	    <div id="pollCommentForm_hide_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="hide" value="<%=WebUtil.display(_PollComment.getHide())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollCommentFormEditField_Hide_<%=_PollComment.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollComment.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_comment_home">
	</form>
    
    
    </td>


    <td>
    <form name="pollCommentForm<%=_PollComment.getId()%>" method="get" action="/v_poll_comment_edit.html" >
        <a href="javascript:document.pollCommentForm<%=_PollComment.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollComment.getId() %>">
    </form>
    <form name="pollCommentForm<%=_PollComment.getId()%>2" method="get" action="/v_poll_comment_edit2.html" >
        <a href="javascript:document.pollCommentForm<%=_PollComment.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollComment.getId() %>">
    </form>
    </td>

    <td>
    <a href="/pollCommentAction.html?del=true&id=<%=_PollComment.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>