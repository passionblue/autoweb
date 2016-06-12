<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
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
	ChurMemberDS ds = ChurMemberDS.getInstance();    

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

<h3> form displayed by script (request type getscriptform or getmodalform2 </h3>
<script type="text/javascript" src="/churMemberAction.html?ajxr=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_chur_member_form.html?prv_returnPage=chur_member_home"> Add New </a> |
            <a href="v_chur_member_list.html?"> List Page </a> |
            <a href="v_chur_member_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/churMemberAction.html?ajxr=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form (custom field list)</a> |
			<a href="/churMemberAction.html?ajxr=getmodalform" 			rel="facebox"> open form</a> |
			<a href="/churMemberAction.html?ajxr=getlisthtml"  			rel="facebox"> getlisthtml</a> |
			<a href="/churMemberAction.html?ajxr=getlistjson"  			rel="facebox"> getlistjson</a> |
			<a href="/churMemberAction.html?ajxr=getjson&ajaxOutArg=first" rel="facebox"> getjson first</a> |
			<a href="/churMemberAction.html?ajxr=getjson&ajaxOutArg=last" 	rel="facebox"> getjson last</a> |
			<a href="/churMemberAction.html?ajxr=getlistdata" 				rel="facebox"> getlistdata</a> |

		</TD>        
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="churMemberForm_fullName_label" style="font-size: normal normal bold 10px verdana;" >Full Name </div>
    </td> 
    <td> 
	    <div id="churMemberForm_firstName_label" style="font-size: normal normal bold 10px verdana;" >First Name </div>
    </td> 
    <td> 
	    <div id="churMemberForm_lastName_label" style="font-size: normal normal bold 10px verdana;" >Last Name </div>
    </td> 
    <td> 
	    <div id="churMemberForm_title_label" style="font-size: normal normal bold 10px verdana;" >Title </div>
    </td> 
    <td> 
	    <div id="churMemberForm_otherName_label" style="font-size: normal normal bold 10px verdana;" >Other Name </div>
    </td> 
    <td> 
	    <div id="churMemberForm_household_label" style="font-size: normal normal bold 10px verdana;" >Household </div>
    </td> 
    <td> 
	    <div id="churMemberForm_householdId_label" style="font-size: normal normal bold 10px verdana;" >Household Id </div>
    </td> 
    <td> 
	    <div id="churMemberForm_isGroup_label" style="font-size: normal normal bold 10px verdana;" >Is Group </div>
    </td> 
    <td> 
	    <div id="churMemberForm_isGuest_label" style="font-size: normal normal bold 10px verdana;" >Is Guest </div>
    </td> 
    <td> 
	    <div id="churMemberForm_isSpeaker_label" style="font-size: normal normal bold 10px verdana;" >Is Speaker </div>
    </td> 
    <td> 
	    <div id="churMemberForm_listIndex_label" style="font-size: normal normal bold 10px verdana;" >List Index </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurMember _ChurMember = (ChurMember) iter.next();
		//TODO 
        fieldString += "\"" +  _ChurMember.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurMember.getId() %> </td>


    <td> 
	<form name="churMemberFormEditField_FullName_<%=_ChurMember.getId()%>" method="get" action="/churMemberAction.html" >


		<div id="churMemberForm_fullName_field">
	    <div id="churMemberForm_fullName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="fullName" value="<%=WebUtil.display(_ChurMember.getFullName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="fullName_<%= _ChurMember.getId()%>"><%=_ChurMember.getFullName() %></div>
            <a id="formSubmit" href="javascript:document.churMemberFormEditField_FullName_<%=_ChurMember.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_ChurMember.getId()%>', 'fullName', '<%=_ChurMember.getFullName()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="fullName">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="fullName">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="fullName">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_member_home">
	</form>
    
    
    </td>

    <td> 
	<form name="churMemberFormEditField_FirstName_<%=_ChurMember.getId()%>" method="get" action="/churMemberAction.html" >


		<div id="churMemberForm_firstName_field">
	    <div id="churMemberForm_firstName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="firstName" value="<%=WebUtil.display(_ChurMember.getFirstName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="firstName_<%= _ChurMember.getId()%>"><%=_ChurMember.getFirstName() %></div>
            <a id="formSubmit" href="javascript:document.churMemberFormEditField_FirstName_<%=_ChurMember.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_ChurMember.getId()%>', 'firstName', '<%=_ChurMember.getFirstName()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="firstName">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="firstName">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="firstName">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_member_home">
	</form>
    
    
    </td>

    <td> 
	<form name="churMemberFormEditField_LastName_<%=_ChurMember.getId()%>" method="get" action="/churMemberAction.html" >


		<div id="churMemberForm_lastName_field">
	    <div id="churMemberForm_lastName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="lastName" value="<%=WebUtil.display(_ChurMember.getLastName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="lastName_<%= _ChurMember.getId()%>"><%=_ChurMember.getLastName() %></div>
            <a id="formSubmit" href="javascript:document.churMemberFormEditField_LastName_<%=_ChurMember.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_ChurMember.getId()%>', 'lastName', '<%=_ChurMember.getLastName()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="lastName">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="lastName">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="lastName">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_member_home">
	</form>
    
    
    </td>

    <td> 
	<form name="churMemberFormEditField_Title_<%=_ChurMember.getId()%>" method="get" action="/churMemberAction.html" >


		<div id="churMemberForm_title_field">
	    <div id="churMemberForm_title_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="title" value="<%=WebUtil.display(_ChurMember.getTitle())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="title_<%= _ChurMember.getId()%>"><%=_ChurMember.getTitle() %></div>
            <a id="formSubmit" href="javascript:document.churMemberFormEditField_Title_<%=_ChurMember.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_ChurMember.getId()%>', 'title', '<%=_ChurMember.getTitle()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="title">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="title">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="title">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_member_home">
	</form>
    
    
    </td>

    <td> 
	<form name="churMemberFormEditField_OtherName_<%=_ChurMember.getId()%>" method="get" action="/churMemberAction.html" >


		<div id="churMemberForm_otherName_field">
	    <div id="churMemberForm_otherName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="otherName" value="<%=WebUtil.display(_ChurMember.getOtherName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="otherName_<%= _ChurMember.getId()%>"><%=_ChurMember.getOtherName() %></div>
            <a id="formSubmit" href="javascript:document.churMemberFormEditField_OtherName_<%=_ChurMember.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_ChurMember.getId()%>', 'otherName', '<%=_ChurMember.getOtherName()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="otherName">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="otherName">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="otherName">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_member_home">
	</form>
    
    
    </td>

    <td> 
	<form name="churMemberFormEditField_Household_<%=_ChurMember.getId()%>" method="get" action="/churMemberAction.html" >


		<div id="churMemberForm_household_field">
	    <div id="churMemberForm_household_dropdown" class="formFieldDropDown" >       
	        <select name="household">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _ChurMember.getHousehold())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _ChurMember.getHousehold())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="household_<%= _ChurMember.getId()%>"><%=_ChurMember.getHousehold() %></div>
            <a id="formSubmit" href="javascript:document.churMemberFormEditField_Household_<%=_ChurMember.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_ChurMember.getId()%>', 'household', '<%=_ChurMember.getHousehold()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="household">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="household">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="household">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_member_home">
	</form>
    
    
    </td>

    <td> 
	<form name="churMemberFormEditField_HouseholdId_<%=_ChurMember.getId()%>" method="get" action="/churMemberAction.html" >


		<div id="churMemberForm_householdId_field">
	    <div id="churMemberForm_householdId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="householdId" value="<%=WebUtil.display(_ChurMember.getHouseholdId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="householdId_<%= _ChurMember.getId()%>"><%=_ChurMember.getHouseholdId() %></div>
            <a id="formSubmit" href="javascript:document.churMemberFormEditField_HouseholdId_<%=_ChurMember.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_ChurMember.getId()%>', 'householdId', '<%=_ChurMember.getHouseholdId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="householdId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="householdId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="householdId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_member_home">
	</form>
    
    
    </td>

    <td> 
	<form name="churMemberFormEditField_IsGroup_<%=_ChurMember.getId()%>" method="get" action="/churMemberAction.html" >


		<div id="churMemberForm_isGroup_field">
	    <div id="churMemberForm_isGroup_dropdown" class="formFieldDropDown" >       
	        <select name="isGroup">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _ChurMember.getIsGroup())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _ChurMember.getIsGroup())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isGroup_<%= _ChurMember.getId()%>"><%=_ChurMember.getIsGroup() %></div>
            <a id="formSubmit" href="javascript:document.churMemberFormEditField_IsGroup_<%=_ChurMember.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_ChurMember.getId()%>', 'isGroup', '<%=_ChurMember.getIsGroup()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isGroup">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isGroup">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isGroup">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_member_home">
	</form>
    
    
    </td>

    <td> 
	<form name="churMemberFormEditField_IsGuest_<%=_ChurMember.getId()%>" method="get" action="/churMemberAction.html" >


		<div id="churMemberForm_isGuest_field">
	    <div id="churMemberForm_isGuest_dropdown" class="formFieldDropDown" >       
	        <select name="isGuest">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _ChurMember.getIsGuest())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _ChurMember.getIsGuest())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isGuest_<%= _ChurMember.getId()%>"><%=_ChurMember.getIsGuest() %></div>
            <a id="formSubmit" href="javascript:document.churMemberFormEditField_IsGuest_<%=_ChurMember.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_ChurMember.getId()%>', 'isGuest', '<%=_ChurMember.getIsGuest()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isGuest">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isGuest">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isGuest">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_member_home">
	</form>
    
    
    </td>

    <td> 
	<form name="churMemberFormEditField_IsSpeaker_<%=_ChurMember.getId()%>" method="get" action="/churMemberAction.html" >


		<div id="churMemberForm_isSpeaker_field">
	    <div id="churMemberForm_isSpeaker_dropdown" class="formFieldDropDown" >       
	        <select name="isSpeaker">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _ChurMember.getIsSpeaker())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _ChurMember.getIsSpeaker())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isSpeaker_<%= _ChurMember.getId()%>"><%=_ChurMember.getIsSpeaker() %></div>
            <a id="formSubmit" href="javascript:document.churMemberFormEditField_IsSpeaker_<%=_ChurMember.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_ChurMember.getId()%>', 'isSpeaker', '<%=_ChurMember.getIsSpeaker()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isSpeaker">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isSpeaker">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isSpeaker">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_member_home">
	</form>
    
    
    </td>


    <td> 
	<form name="churMemberFormEditField_ListIndex_<%=_ChurMember.getId()%>" method="get" action="/churMemberAction.html" >


		<div id="churMemberForm_listIndex_field">
	    <div id="churMemberForm_listIndex_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="listIndex" value="<%=WebUtil.display(_ChurMember.getListIndex())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="listIndex_<%= _ChurMember.getId()%>"><%=_ChurMember.getListIndex() %></div>
            <a id="formSubmit" href="javascript:document.churMemberFormEditField_ListIndex_<%=_ChurMember.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_ChurMember.getId()%>', 'listIndex', '<%=_ChurMember.getListIndex()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="listIndex">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="listIndex">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="listIndex">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_member_home">
	</form>
    
    
    </td>

    <td>
    <form name="churMemberForm<%=_ChurMember.getId()%>2" method="get" action="/v_chur_member_form.html" >
        <a href="javascript:document.churMemberForm<%=_ChurMember.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurMember.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="chur_member_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_chur_member_form('<%=_ChurMember.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/churMemberAction.html?del=true&id=<%=_ChurMember.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_chur_member('<%=_ChurMember.getId()%>');">DeleteWConfirm</a>
    </td>
</TR>

<%
    }
	if ( fieldString != null && fieldString.length() > 0 )
	fieldString = fieldString.substring(0, fieldString.length()-1);

%>
</TABLE>

<a id="partition_test_ajax" href="#" rel="extInt">	Partition Test </a><br>
<a id="partition_test_ajax2" href="#" rel="extInt">	Partition Test2 </a><br>

<div id="partitionTestResult" style="border:1px solid #666666; "> Partition test to be loaded here </div> <br>


<script type="text/javascript">




	function edit_chur_member_form(target){
		location.href='/v_chur_member_form.html?cmd=edit&prv_returnPage=chur_member_home&id=' + target;
	}

	function confirm_chur_member(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_chur_member(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/churMemberAction.html?del=true&id="+target;
				}
			}
		});
	}
	// 20120226 
	// On the list, added a little stupid fuction to prompt the change of values
	function update_cleaner_pickup_field_dialog(targetId, targetField, targetValue){
		var txt = 'Change field value for'+targetField +':<br/> <input type="text" id="alertName" name="myname" value="'+ targetValue +'" />';
		$ .prompt(txt,{ 
			buttons:{Submit:true, Cancel:false},
			callback: function(v,m,f){
				if (v){
					if (f.myname == "") {
						alert("Enter");
						return false;
					} else {
						location.href="/churMemberAction.html?editfield=true&returnPage=cleaner_pickup_home&id="+targetId+"&"+targetField +"=" +f.myname;
					}
				}
				return true;
			}
		});
	}

	// Functions to update field in the list via ajax
	// This is primitive but field "update_field_by_ajax" should be right next level of form.
	// Because it uses parent to access to id and field name 20120226
	$(document).ready(function(){

		$("a#update_field_by_ajax").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			$ .ajax({
			   type: "GET",
		   		url: "/churMemberAction.html?editfield=true&ajxr=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		     		$("#" + _fieldName+"_"+_id).text(msg);
		   		}
	 		});
			
			return false;
		});

		$("a#update_field_by_ajax_open_reply").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			 $(this).css("background-color", "red");

	    	$ .ajax({
			   type: "GET",
		   		url: "/churMemberAction.html?editfield=true&ajaxRequest=true&ajaxOut=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
				beforeSend: function(jqXHR, settings){
					//alert("sending");
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		$("#" + _fieldName+"_"+_id).text(msg);
		    		$ .prompt("Value updated Success fully",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		},
		   		error: function(msg){
		    		$ .prompt("Request error",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		}
	 		});
			
			return false;
		});
		
		$("a#update_field_by_ajax_get2field").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			 $(this).css("background-color", "red");

	    	$ .ajax({
			   type: "GET",
		   		url: "/churMemberAction.html?editfield=true&ajxr=get2field&id="+_id+"&"+_fieldName+"="+ _val,
				beforeSend: function(jqXHR, settings){
					//alert("sending");
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		$("#" + _fieldName+"_"+_id).text(msg); // Update the field
		     		var displayMsg = "return from server-> " + msg + "<br>" + "result of getSuccessData()-> "+ getSuccessData(msg);
		    		$ .prompt(displayMsg,{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		},
		   		error: function(msg){
		    		$ .prompt("Request error",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		}
	 		});
			
			return false;
		});


		$("a#partition_test_ajax").click(function () {
			
			$ .ajax({
			   type: "GET",
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-chur-member",
		  		success: function(msg){ 
		     		alert(msg);
		     		$("#partitionTestResult").html(msg);
		   		}
	 		});
			
			return false;
		});		

		// Display loader 
		$("a#partition_test_ajax2").click(function () {
			
			$ .ajax({
			   type: "GET",
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-chur-member",
		   		beforeSend: function(jqXHR, settings){
		   			
					// 1 just display loader img on the target div 		   			
		   			// $("#partitionTestResult").html("<img src=\"/images/loader/arrows32.gif\"/>");

					
					//2 
					$("#partitionTestResult").css("height","100px").html("<img src=\"/images/loader/arrows32.gif\"/>");
					
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		alert(msg);
		     		$("#partitionTestResult").remove("height").html(msg);
		   		}
	 		});
			return false;
 		});
	});
</script>


<script type="text/javascript" charset="utf-8">
// This Javascript is granted to the public domain.

// This is the javascript array holding the function list
// The PrintJavascriptArray ASP function can be used to print this array.
//var functionlist = Array("abs",
//"acos","acosh","addcslashes","addslashes","aggregate","stream_context_create",
//"swf_startbutton","swfmovie.remove","swfmovie.save","swftext.getwidth","swftext.moveto","sybase_fetch_field","sybase_fetch_object","tanh","tempnam",
//"textdomain","time","udm_errno","udm_error",
//"unset","urldecode","urlencode","user_error","usleep","usort","utf8_decode",
//"utf8_encode","var_dump","vpopmail_error","vpopmail_passwd","vpopmail_set_user_quota","vprintf","vsprintf","xml_parser_create","xml_parser_create_ns",
//"xml_parser_free","xmlrpc_server_add_introspection_data","xmlrpc_server_call_method","xmlrpc_server_create","xmlrpc_server_destroy","xmlrpc_server_register_introspection_callback","yaz_connect","yaz_database","yaz_element",
//"yaz_errno","yp_order","zend_logo_guid","zend_version","zip_close","zip_open","zip_read");



var functionlist = Array(<%=fieldString%>);



// This is the function that refreshes the list after a keypress.
// The maximum number to show can be limited to improve performance with
// huge lists (1000s of entries).
// The function clears the list, and then does a linear search through the
// globally defined array and adds the matches back to the list.
function handleKeyUp(maxNumToShow)
{
    var selectObj, textObj, functionListLength;
    var i, searchPattern, numShown;

	if (document.getElementById('auto-complete-input') == null){
		alert("Client side Error occurred. Please try again.");
		return;
	}
    
    // Set references to the form elements
    selectObj = document.getElementById('auto-complete-input').functionselect;
    textObj = document.getElementById('auto-complete-input').functioninput;

    // Remember the function list length for loop speedup
    functionListLength = functionlist.length;

    // Set the search pattern depending
    if(document.getElementById('auto-complete-input').functionradio[0].checked == true)
    {
        searchPattern = "^"+textObj.value;
    }
    else
    {
        searchPattern = textObj.value;
    }

    // Create a regulare expression
    re = new RegExp(searchPattern,"gi");
    // Clear the options list
    selectObj.length = 0;

    // Loop through the array and re-add matching options
    numShown = 0;
    for(i = 0; i < functionListLength; i++)
    {
        if(functionlist[i].search(re) != -1)
        {
            selectObj[numShown] = new Option(functionlist[i],"");
            numShown++;
        }
        // Stop when the number to show is reached
        if(numShown == maxNumToShow)
        {
            break;
        }
    }
    // When options list whittled to one, select that entry
    if(selectObj.length == 1)
    {
        selectObj.options[0].selected = true;
    }
}

// this function gets the selected value and loads the appropriate
// php reference page in the display frame
// it can be modified to perform whatever action is needed, or nothing
function handleSelectClick()
{
    selectObj = document.getElementById('auto-complete-input').functionselect;
    textObj = document.getElementById('auto-complete-input').functioninput;

    selectedValue = selectObj.options[selectObj.selectedIndex].text;

    selectedValue = selectedValue.replace(/_/g, '-') ;
    document.location.href = 
	"http://www.php.net/manual/en/function."+selectedValue+".php";

}
function encode_utf8( string )
{
	string = string.replace(/\r\n/g,"\n");
	var utftext = "";

	for (var n = 0; n < string.length; n++) {

		var c = string.charCodeAt(n);

		if (c < 128) {
			utftext += String.fromCharCode(c);
		}
		else if((c > 127) && (c < 2048)) {
			utftext += String.fromCharCode((c >> 6) | 192);
			utftext += String.fromCharCode((c & 63) | 128);
		}
		else {
			utftext += String.fromCharCode((c >> 12) | 224);
			utftext += String.fromCharCode(((c >> 6) & 63) | 128);
			utftext += String.fromCharCode((c & 63) | 128);
		}

	}

	return utftext;
}

function decode_utf8( s )
{
  return decodeURIComponent( escape( s ) );
}
</script>

<table style="margin:auto;">
<tr>
	<td valign="top">
		<b>Search For Function Name</b>
		
		<form onSubmit="handleSelectClick();return false;" action="#" id='auto-complete-input'>

			<input type="radio" name="functionradio" checked>Starting With<br>
			<input type="radio" name="functionradio">Containing<br>
			<input  onKeyUp="handleKeyUp(20);" type="text" name="functioninput" VALUE="" style="font-size:10pt;width:34ex;"><br>
		
			<select onClick="handleSelectClick();" name="functionselect" size="20" style="font-size:10pt;width:34ex;">
			</select>
			<br>
			<input type="button" onClick="handleKeyUp(9999999);" value="Load All Matches">
		</form>
	</td>
</tr>

<tr>
	<td valign="top">
		<select>
		  <option>Volvo</option>
		  <option>Saab</option>
		  <option>Mercedes</option>
		  <option>Audi</option>
		</select>
	</td>
</tr>

</table>