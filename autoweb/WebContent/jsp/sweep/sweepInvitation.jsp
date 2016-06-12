<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	SweepInvitationDS ds = SweepInvitationDS.getInstance();    
    list = ds.getBySiteId(site.getId());

%> 

<!-- =================== PAGING =================== -->
<%
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), 20);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_sweep_invitation_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_sweep_invitation_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_sweep_invitation_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_sweep_invitation_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
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
	<%=pageIndexShortcut[p] + (p>=pageIndexShortcut.length-1?"":"/")%>
<%
	}
%>	
	<%= nextLinkStr %>
<!-- =================== END PAGING =================== -->


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_sweep_invitation_add2.html?prv_returnPage=sweep_invitation_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="sweepInvitationForm_email_label" style="font-size: normal normal bold 10px verdana;" >Email </div>
    </td> 
    <td> 
	    <div id="sweepInvitationForm_message_label" style="font-size: normal normal bold 10px verdana;" >Message </div>
    </td> 
    <td> 
	    <div id="sweepInvitationForm_invitationSent_label" style="font-size: normal normal bold 10px verdana;" >Invitation Sent </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepInvitation _SweepInvitation = (SweepInvitation) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SweepInvitation.getId() %> </td>



    <td> 
	<form name="sweepInvitationFormEditField_Email_<%=_SweepInvitation.getId()%>" method="get" action="/sweepInvitationAction.html" >


		<div id="sweepInvitationForm_email_field">
	    <div id="sweepInvitationForm_email_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="email" value="<%=WebUtil.display(_SweepInvitation.getEmail())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepInvitationFormEditField_Email_<%=_SweepInvitation.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepInvitation.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_invitation_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepInvitationFormEditField_Message_<%=_SweepInvitation.getId()%>" method="get" action="/sweepInvitationAction.html" >


		<div id="sweepInvitationForm_message_field">
	    <div id="sweepInvitationForm_message_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="message" value="<%=WebUtil.display(_SweepInvitation.getMessage())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepInvitationFormEditField_Message_<%=_SweepInvitation.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepInvitation.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_invitation_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepInvitationFormEditField_InvitationSent_<%=_SweepInvitation.getId()%>" method="get" action="/sweepInvitationAction.html" >


		<div id="sweepInvitationForm_invitationSent_field">
	    <div id="sweepInvitationForm_invitationSent_dropdown" class="formFieldDropDown" >       
	        <select name="invitationSent">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _SweepInvitation.getInvitationSent())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _SweepInvitation.getInvitationSent())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepInvitationFormEditField_InvitationSent_<%=_SweepInvitation.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepInvitation.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_invitation_home">
	</form>
    
    
    </td>



    <td>
    <form name="sweepInvitationForm<%=_SweepInvitation.getId()%>" method="get" action="/v_sweep_invitation_edit.html" >
        <a href="javascript:document.sweepInvitationForm<%=_SweepInvitation.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepInvitation.getId() %>">
    </form>
    <form name="sweepInvitationForm<%=_SweepInvitation.getId()%>2" method="get" action="/v_sweep_invitation_edit2.html" >
        <a href="javascript:document.sweepInvitationForm<%=_SweepInvitation.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepInvitation.getId() %>">
    </form>
    </td>

    <td>
    <a href="/sweepInvitationAction.html?del=true&id=<%=_SweepInvitation.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>