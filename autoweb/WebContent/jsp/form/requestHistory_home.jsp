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
	RequestHistoryDS ds = RequestHistoryDS.getInstance();    

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
<script type="text/javascript" src="/requestHistoryAction.html?ajxr=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_request_history_form.html?prv_returnPage=request_history_home"> Add New </a> |
            <a href="v_request_history_list.html?"> List Page </a> |
            <a href="v_request_history_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/requestHistoryAction.html?ajxr=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form (custom field list)</a> |
			<a href="/requestHistoryAction.html?ajxr=getmodalform" 			rel="facebox"> open form</a> |
			<a href="/requestHistoryAction.html?ajxr=getlisthtml"  			rel="facebox"> getlisthtml</a> |
			<a href="/requestHistoryAction.html?ajxr=getlistjson"  			rel="facebox"> getlistjson</a> |
			<a href="/requestHistoryAction.html?ajxr=getjson&ajaxOutArg=first" rel="facebox"> getjson first</a> |
			<a href="/requestHistoryAction.html?ajxr=getjson&ajaxOutArg=last" 	rel="facebox"> getjson last</a> |
			<a href="/requestHistoryAction.html?ajxr=getlistdata" 				rel="facebox"> getlistdata</a> |

		</TD>        
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="requestHistoryForm_forwardSiteId_label" style="font-size: normal normal bold 10px verdana;" >Forward Site Id </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_isDropped_label" style="font-size: normal normal bold 10px verdana;" >Is Dropped </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_isPageless_label" style="font-size: normal normal bold 10px verdana;" >Is Pageless </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_isLogin_label" style="font-size: normal normal bold 10px verdana;" >Is Login </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_isAjax_label" style="font-size: normal normal bold 10px verdana;" >Is Ajax </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_isRobot_label" style="font-size: normal normal bold 10px verdana;" >Is Robot </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_userid_label" style="font-size: normal normal bold 10px verdana;" >Userid </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_userAgent_label" style="font-size: normal normal bold 10px verdana;" >User Agent </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_refer_label" style="font-size: normal normal bold 10px verdana;" >Refer </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_robot_label" style="font-size: normal normal bold 10px verdana;" >Robot </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_remoteIp_label" style="font-size: normal normal bold 10px verdana;" >Remote Ip </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_siteUrl_label" style="font-size: normal normal bold 10px verdana;" >Site Url </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_uri_label" style="font-size: normal normal bold 10px verdana;" >Uri </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_query_label" style="font-size: normal normal bold 10px verdana;" >Query </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_rpci_label" style="font-size: normal normal bold 10px verdana;" >Rpci </div>
    </td> 
    <td> 
	    <div id="requestHistoryForm_sessionId_label" style="font-size: normal normal bold 10px verdana;" >Session Id </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        RequestHistory _RequestHistory = (RequestHistory) iter.next();
		//TODO 
        fieldString += "\"" +  _RequestHistory.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _RequestHistory.getId() %> </td>


    <td> 
	<form name="requestHistoryFormEditField_ForwardSiteId_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_forwardSiteId_field">
	    <div id="requestHistoryForm_forwardSiteId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="forwardSiteId" value="<%=WebUtil.display(_RequestHistory.getForwardSiteId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="forwardSiteId_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getForwardSiteId() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_ForwardSiteId_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'forwardSiteId', '<%=_RequestHistory.getForwardSiteId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="forwardSiteId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="forwardSiteId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="forwardSiteId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_IsDropped_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_isDropped_field">
	    <div id="requestHistoryForm_isDropped_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="isDropped" value="<%=WebUtil.display(_RequestHistory.getIsDropped())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isDropped_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getIsDropped() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_IsDropped_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'isDropped', '<%=_RequestHistory.getIsDropped()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isDropped">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isDropped">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isDropped">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_IsPageless_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_isPageless_field">
	    <div id="requestHistoryForm_isPageless_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="isPageless" value="<%=WebUtil.display(_RequestHistory.getIsPageless())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isPageless_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getIsPageless() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_IsPageless_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'isPageless', '<%=_RequestHistory.getIsPageless()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isPageless">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isPageless">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isPageless">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_IsLogin_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_isLogin_field">
	    <div id="requestHistoryForm_isLogin_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="isLogin" value="<%=WebUtil.display(_RequestHistory.getIsLogin())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isLogin_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getIsLogin() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_IsLogin_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'isLogin', '<%=_RequestHistory.getIsLogin()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isLogin">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isLogin">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isLogin">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_IsAjax_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_isAjax_field">
	    <div id="requestHistoryForm_isAjax_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="isAjax" value="<%=WebUtil.display(_RequestHistory.getIsAjax())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isAjax_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getIsAjax() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_IsAjax_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'isAjax', '<%=_RequestHistory.getIsAjax()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isAjax">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isAjax">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isAjax">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_IsRobot_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_isRobot_field">
	    <div id="requestHistoryForm_isRobot_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="isRobot" value="<%=WebUtil.display(_RequestHistory.getIsRobot())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isRobot_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getIsRobot() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_IsRobot_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'isRobot', '<%=_RequestHistory.getIsRobot()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isRobot">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isRobot">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isRobot">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_Userid_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_userid_field">
	    <div id="requestHistoryForm_userid_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="userid" value="<%=WebUtil.display(_RequestHistory.getUserid())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="userid_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getUserid() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_Userid_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'userid', '<%=_RequestHistory.getUserid()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="userid">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="userid">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="userid">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_UserAgent_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_userAgent_field">
	    <div id="requestHistoryForm_userAgent_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="userAgent" value="<%=WebUtil.display(_RequestHistory.getUserAgent())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="userAgent_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getUserAgent() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_UserAgent_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'userAgent', '<%=_RequestHistory.getUserAgent()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="userAgent">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="userAgent">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="userAgent">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_Refer_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_refer_field">
	    <div id="requestHistoryForm_refer_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="refer" value="<%=WebUtil.display(_RequestHistory.getRefer())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="refer_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getRefer() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_Refer_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'refer', '<%=_RequestHistory.getRefer()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="refer">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="refer">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="refer">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_Robot_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_robot_field">
	    <div id="requestHistoryForm_robot_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="robot" value="<%=WebUtil.display(_RequestHistory.getRobot())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="robot_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getRobot() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_Robot_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'robot', '<%=_RequestHistory.getRobot()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="robot">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="robot">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="robot">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_RemoteIp_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_remoteIp_field">
	    <div id="requestHistoryForm_remoteIp_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="remoteIp" value="<%=WebUtil.display(_RequestHistory.getRemoteIp())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="remoteIp_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getRemoteIp() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_RemoteIp_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'remoteIp', '<%=_RequestHistory.getRemoteIp()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="remoteIp">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="remoteIp">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="remoteIp">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_SiteUrl_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_siteUrl_field">
	    <div id="requestHistoryForm_siteUrl_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="siteUrl" value="<%=WebUtil.display(_RequestHistory.getSiteUrl())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="siteUrl_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getSiteUrl() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_SiteUrl_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'siteUrl', '<%=_RequestHistory.getSiteUrl()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="siteUrl">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="siteUrl">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="siteUrl">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_Uri_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_uri_field">
	    <div id="requestHistoryForm_uri_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="uri" value="<%=WebUtil.display(_RequestHistory.getUri())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="uri_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getUri() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_Uri_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'uri', '<%=_RequestHistory.getUri()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="uri">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="uri">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="uri">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_Query_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_query_field">
	    <div id="requestHistoryForm_query_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="query" value="<%=WebUtil.display(_RequestHistory.getQuery())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="query_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getQuery() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_Query_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'query', '<%=_RequestHistory.getQuery()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="query">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="query">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="query">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_Rpci_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_rpci_field">
	    <div id="requestHistoryForm_rpci_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="rpci" value="<%=WebUtil.display(_RequestHistory.getRpci())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="rpci_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getRpci() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_Rpci_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'rpci', '<%=_RequestHistory.getRpci()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="rpci">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="rpci">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="rpci">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>

    <td> 
	<form name="requestHistoryFormEditField_SessionId_<%=_RequestHistory.getId()%>" method="get" action="/requestHistoryAction.html" >


		<div id="requestHistoryForm_sessionId_field">
	    <div id="requestHistoryForm_sessionId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="sessionId" value="<%=WebUtil.display(_RequestHistory.getSessionId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="sessionId_<%= _RequestHistory.getId()%>"><%=_RequestHistory.getSessionId() %></div>
            <a id="formSubmit" href="javascript:document.requestHistoryFormEditField_SessionId_<%=_RequestHistory.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_RequestHistory.getId()%>', 'sessionId', '<%=_RequestHistory.getSessionId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="sessionId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="sessionId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="sessionId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RequestHistory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="request_history_home">
	</form>
    
    
    </td>


    <td>
    <form name="requestHistoryForm<%=_RequestHistory.getId()%>2" method="get" action="/v_request_history_form.html" >
        <a href="javascript:document.requestHistoryForm<%=_RequestHistory.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _RequestHistory.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="request_history_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_request_history_form('<%=_RequestHistory.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/requestHistoryAction.html?del=true&id=<%=_RequestHistory.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_request_history('<%=_RequestHistory.getId()%>');">DeleteWConfirm</a>
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




	function edit_request_history_form(target){
		location.href='/v_request_history_form.html?cmd=edit&prv_returnPage=request_history_home&id=' + target;
	}

	function confirm_request_history(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_request_history(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/requestHistoryAction.html?del=true&id="+target;
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
						location.href="/requestHistoryAction.html?editfield=true&returnPage=cleaner_pickup_home&id="+targetId+"&"+targetField +"=" +f.myname;
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
		   		url: "/requestHistoryAction.html?editfield=true&ajxr=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
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
		   		url: "/requestHistoryAction.html?editfield=true&ajaxRequest=true&ajaxOut=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
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
		   		url: "/requestHistoryAction.html?editfield=true&ajxr=get2field&id="+_id+"&"+_fieldName+"="+ _val,
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
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-request-history",
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
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-request-history",
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