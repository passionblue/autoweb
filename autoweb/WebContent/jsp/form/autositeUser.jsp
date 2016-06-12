<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	AutositeUserDS ds = AutositeUserDS.getInstance();    
    list = ds.getBySiteId(site.getId());

%> 

<!-- =================== PAGING =================== -->
<%
	int defaultNumDisplay = 20;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_autosite_user_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_autosite_user_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	prevLinkStr = "<div class=\"pagingNavMove\">" + prevLinkStr + "</div>";
	nextLinkStr = "<div class=\"pagingNavMov\">" + nextLinkStr + "</div>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_autosite_user_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_autosite_user_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
	}

	List pageList = new ArrayList();
	for(int i = pagingInfo.getBeginIdx() ; i < pagingInfo.getEndIdx();i++){
		pageList.add(list.get(i));
	}
	list = pageList;
	
	StringBuffer buf = new StringBuffer();
	buf.append("<div class=\"pagingFrame\">");
	buf.append(prevLinkStr);

	for(int p = 0 ; p< pageIndexShortcut.length; p++){
		buf.append("<div class=\"pagingNavNum\">").append(pageIndexShortcut[p]).append("</div>").append("<div class=\"pagingNavDiv\">").append((p>=pageIndexShortcut.length-1?"":"/")).append("</div>");
	}
	buf.append(nextLinkStr);
	buf.append("</div>");
	
	System.out.println("paging Menu=" + buf);
	
%>	
	<%= buf.toString() %>
<!-- =================== END PAGING =================== -->


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_autosite_user_add2.html?prv_returnPage=autosite_user_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="autositeUserForm_username_label" style="font-size: normal normal bold 10px verdana;" >Username </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_password_label" style="font-size: normal normal bold 10px verdana;" >Password </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_email_label" style="font-size: normal normal bold 10px verdana;" >Email </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_userType_label" style="font-size: normal normal bold 10px verdana;" >User Type </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_firstName_label" style="font-size: normal normal bold 10px verdana;" >First Name </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_lastName_label" style="font-size: normal normal bold 10px verdana;" >Last Name </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_nickname_label" style="font-size: normal normal bold 10px verdana;" >Nickname </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_timeUpdated_label" style="font-size: normal normal bold 10px verdana;" >Time Updated </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_disabled_label" style="font-size: normal normal bold 10px verdana;" >Disabled </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_timeDisabled_label" style="font-size: normal normal bold 10px verdana;" >Time Disabled </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_confirmed_label" style="font-size: normal normal bold 10px verdana;" >Confirmed </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_timeConfirmed_label" style="font-size: normal normal bold 10px verdana;" >Time Confirmed </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_opt1_label" style="font-size: normal normal bold 10px verdana;" >Opt 1 </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_opt2_label" style="font-size: normal normal bold 10px verdana;" >Opt 2 </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        AutositeUser _AutositeUser = (AutositeUser) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _AutositeUser.getId() %> </td>


    <td> 
	<form name="autositeUserFormEditField_Username_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_username_field">
	    <div id="autositeUserForm_username_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="username" value="<%=WebUtil.display(_AutositeUser.getUsername())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Username_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Password_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_password_field">
	    <div id="autositeUserForm_password_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="password" value="<%=WebUtil.display(_AutositeUser.getPassword())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Password_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Email_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_email_field">
	    <div id="autositeUserForm_email_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="email" value="<%=WebUtil.display(_AutositeUser.getEmail())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Email_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_UserType_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_userType_field">
	    <div id="autositeUserForm_userType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="userType" value="<%=WebUtil.display(_AutositeUser.getUserType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_UserType_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_FirstName_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_firstName_field">
	    <div id="autositeUserForm_firstName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="firstName" value="<%=WebUtil.display(_AutositeUser.getFirstName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_FirstName_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_LastName_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_lastName_field">
	    <div id="autositeUserForm_lastName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="lastName" value="<%=WebUtil.display(_AutositeUser.getLastName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_LastName_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Nickname_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_nickname_field">
	    <div id="autositeUserForm_nickname_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="nickname" value="<%=WebUtil.display(_AutositeUser.getNickname())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Nickname_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>


    <td> 
	<form name="autositeUserFormEditField_TimeUpdated_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_timeUpdated_field">
	    <div id="autositeUserForm_timeUpdated_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="timeUpdated" value="<%=WebUtil.display(_AutositeUser.getTimeUpdated())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_TimeUpdated_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Disabled_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_disabled_field">
	    <div id="autositeUserForm_disabled_dropdown" class="formFieldDropDown" >       
	        <select name="disabled">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _AutositeUser.getDisabled())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _AutositeUser.getDisabled())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Disabled_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_TimeDisabled_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_timeDisabled_field">
	    <div id="autositeUserForm_timeDisabled_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="timeDisabled" value="<%=WebUtil.display(_AutositeUser.getTimeDisabled())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_TimeDisabled_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Confirmed_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_confirmed_field">
	    <div id="autositeUserForm_confirmed_dropdown" class="formFieldDropDown" >       
	        <select name="confirmed">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _AutositeUser.getConfirmed())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _AutositeUser.getConfirmed())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Confirmed_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_TimeConfirmed_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_timeConfirmed_field">
	    <div id="autositeUserForm_timeConfirmed_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="timeConfirmed" value="<%=WebUtil.display(_AutositeUser.getTimeConfirmed())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_TimeConfirmed_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Opt1_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_opt1_field">
	    <div id="autositeUserForm_opt1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="opt1" value="<%=WebUtil.display(_AutositeUser.getOpt1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Opt1_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Opt2_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_opt2_field">
	    <div id="autositeUserForm_opt2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="opt2" value="<%=WebUtil.display(_AutositeUser.getOpt2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Opt2_<%=_AutositeUser.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td>
    <form name="autositeUserForm<%=_AutositeUser.getId()%>" method="get" action="/v_autosite_user_edit.html" >
        <a href="javascript:document.autositeUserForm<%=_AutositeUser.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _AutositeUser.getId() %>">
    </form>
    <form name="autositeUserForm<%=_AutositeUser.getId()%>2" method="get" action="/v_autosite_user_edit2.html" >
        <a href="javascript:document.autositeUserForm<%=_AutositeUser.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _AutositeUser.getId() %>">
    </form>
    </td>

    <td>
    <a href="/autositeUserAction.html?del=true&id=<%=_AutositeUser.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>