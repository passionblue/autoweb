<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	SweepPasswordDS ds = SweepPasswordDS.getInstance();    

%> 

<!-- =================== PAGING =================== -->
<%
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), 20);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_sweep_password_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_sweep_password_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_sweep_password_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_sweep_password_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
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
            <a href="t_sweep_password_add2.html?prv_returnPage=sweep_password_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="sweepPasswordForm_sendPasswordEmail_label" style="font-size: normal normal bold 10px verdana;" >Send Password Email </div>
    </td> 
    <td> 
	    <div id="sweepPasswordForm_oldPassword_label" style="font-size: normal normal bold 10px verdana;" >Old Password </div>
    </td> 
    <td> 
	    <div id="sweepPasswordForm_newPassword_label" style="font-size: normal normal bold 10px verdana;" >New Password </div>
    </td> 
    <td> 
	    <div id="sweepPasswordForm_passwordRetype_label" style="font-size: normal normal bold 10px verdana;" >Password Retype </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepPassword _SweepPassword = (SweepPassword) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SweepPassword.getId() %> </td>


    <td> 
	<form name="sweepPasswordFormEditField_SendPasswordEmail_<%=_SweepPassword.getId()%>" method="get" action="/sweepPasswordAction.html" >


		<div id="sweepPasswordForm_sendPasswordEmail_field">
	    <div id="sweepPasswordForm_sendPasswordEmail_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="sendPasswordEmail" value="<%=WebUtil.display(_SweepPassword.getSendPasswordEmail())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepPasswordFormEditField_SendPasswordEmail_<%=_SweepPassword.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepPassword.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_password_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepPasswordFormEditField_OldPassword_<%=_SweepPassword.getId()%>" method="get" action="/sweepPasswordAction.html" >


		<div id="sweepPasswordForm_oldPassword_field">
	    <div id="sweepPasswordForm_oldPassword_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="oldPassword" value="<%=WebUtil.display(_SweepPassword.getOldPassword())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepPasswordFormEditField_OldPassword_<%=_SweepPassword.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepPassword.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_password_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepPasswordFormEditField_NewPassword_<%=_SweepPassword.getId()%>" method="get" action="/sweepPasswordAction.html" >


		<div id="sweepPasswordForm_newPassword_field">
	    <div id="sweepPasswordForm_newPassword_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="newPassword" value="<%=WebUtil.display(_SweepPassword.getNewPassword())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepPasswordFormEditField_NewPassword_<%=_SweepPassword.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepPassword.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_password_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepPasswordFormEditField_PasswordRetype_<%=_SweepPassword.getId()%>" method="get" action="/sweepPasswordAction.html" >


		<div id="sweepPasswordForm_passwordRetype_field">
	    <div id="sweepPasswordForm_passwordRetype_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="passwordRetype" value="<%=WebUtil.display(_SweepPassword.getPasswordRetype())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepPasswordFormEditField_PasswordRetype_<%=_SweepPassword.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepPassword.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_password_home">
	</form>
    
    
    </td>

    <td>
    <form name="sweepPasswordForm<%=_SweepPassword.getId()%>" method="get" action="/v__edit.html" >
        <a href="javascript:document.sweepPasswordForm<%=_SweepPassword.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepPassword.getId() %>">
    </form>
    <form name="sweepPasswordForm<%=_SweepPassword.getId()%>2" method="get" action="/v__edit2.html" >
        <a href="javascript:document.sweepPasswordForm<%=_SweepPassword.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepPassword.getId() %>">
    </form>
    </td>

    <td>
    <a href="/sweepPasswordAction.html?del=true&id=<%=_SweepPassword.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>