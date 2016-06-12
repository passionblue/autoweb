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
	AutositeUserDS ds = AutositeUserDS.getInstance();    

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
<%	
	if (showListAllByAdmin) {
%>
    <td> 
	    <div id="autositeUserForm_username_label" style="font-size: normal normal bold 10px verdana;" >Site </div>
    </td> 
	
<% 	} %>

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
	    <div id="autositeUserForm_timeCreated_label" style="font-size: normal normal bold 10px verdana;" >Time Created </div>
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
</TR>
<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        AutositeUser _AutositeUser = (AutositeUser) iter.next();
%>

<TR bgcolor="#ffffff" valign="top">
    <td> <%= _AutositeUser.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _AutositeUser.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td> 
		<%= siteName %>	    
    </td> 
	
<% 	} %>
    
	<td> <%= _AutositeUser.getUsername()  %> </td>
	<td> <%= _AutositeUser.getPassword()  %> </td>
	<td> <%= _AutositeUser.getEmail()  %> </td>
	<td> <%= _AutositeUser.getUserType()  %> </td>
	<td> <%= _AutositeUser.getFirstName()  %> </td>
	<td> <%= _AutositeUser.getLastName()  %> </td>
	<td> <%= _AutositeUser.getNickname()  %> </td>
	<td> <%= _AutositeUser.getTimeCreated()  %> </td>
	<td> <%= _AutositeUser.getTimeUpdated()  %> </td>
	<td> <%= _AutositeUser.getDisabled()  %> </td>
	<td> <%= _AutositeUser.getTimeDisabled()  %> </td>
	<td> <%= _AutositeUser.getConfirmed()  %> </td>
	<td> <%= _AutositeUser.getTimeConfirmed()  %> </td>
	<td> <%= _AutositeUser.getOpt1()  %> </td>
	<td> <%= _AutositeUser.getOpt2()  %> </td>
</TR>

<%
    }
%>
</TABLE>