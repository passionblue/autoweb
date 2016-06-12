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
	CleanerCustomerNotificationDS ds = CleanerCustomerNotificationDS.getInstance();    

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
<script type="text/javascript" src="/cleanerCustomerNotificationAction.html?ajxr=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_cleaner_customer_notification_form.html?prv_returnPage=cleaner_customer_notification_home"> Add New </a> |
            <a href="v_cleaner_customer_notification_list.html?"> List Page </a> |
            <a href="v_cleaner_customer_notification_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/cleanerCustomerNotificationAction.html?ajxr=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form (custom field list)</a> |
			<a href="/cleanerCustomerNotificationAction.html?ajxr=getmodalform" 			rel="facebox"> open form</a> |
			<a href="/cleanerCustomerNotificationAction.html?ajxr=getlisthtml"  			rel="facebox"> getlisthtml</a> |
			<a href="/cleanerCustomerNotificationAction.html?ajxr=getlistjson"  			rel="facebox"> getlistjson</a> |
			<a href="/cleanerCustomerNotificationAction.html?ajxr=getjson&ajaxOutArg=first" rel="facebox"> getjson first</a> |
			<a href="/cleanerCustomerNotificationAction.html?ajxr=getjson&ajaxOutArg=last" 	rel="facebox"> getjson last</a> |
			<a href="/cleanerCustomerNotificationAction.html?ajxr=getlistdata" 				rel="facebox"> getlistdata</a> |

		</TD>        
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="cleanerCustomerNotificationForm_customerId_label" style="font-size: normal normal bold 10px verdana;" >Customer Id </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_reasonforId_label" style="font-size: normal normal bold 10px verdana;" >Reasonfor Id </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_reasonforTarget_label" style="font-size: normal normal bold 10px verdana;" >Reasonfor Target </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_notificationType_label" style="font-size: normal normal bold 10px verdana;" >Notification Type </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_sourceType_label" style="font-size: normal normal bold 10px verdana;" >Source Type </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_triggerType_label" style="font-size: normal normal bold 10px verdana;" >Trigger Type </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_isRetransmit_label" style="font-size: normal normal bold 10px verdana;" >Is Retransmit </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_methodType_label" style="font-size: normal normal bold 10px verdana;" >Method Type </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_templateType_label" style="font-size: normal normal bold 10px verdana;" >Template Type </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_content_label" style="font-size: normal normal bold 10px verdana;" >Content </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_destination_label" style="font-size: normal normal bold 10px verdana;" >Destination </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_reference_label" style="font-size: normal normal bold 10px verdana;" >Reference </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_timeScheduled_label" style="font-size: normal normal bold 10px verdana;" >Time Scheduled </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerNotificationForm_timeSent_label" style="font-size: normal normal bold 10px verdana;" >Time Sent </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        CleanerCustomerNotification _CleanerCustomerNotification = (CleanerCustomerNotification) iter.next();
		//TODO 
        fieldString += "\"" +  _CleanerCustomerNotification.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _CleanerCustomerNotification.getId() %> </td>


    <td> 
	<form name="cleanerCustomerNotificationFormEditField_CustomerId_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_customerId_field">
	    <div id="cleanerCustomerNotificationForm_customerId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="customerId" value="<%=WebUtil.display(_CleanerCustomerNotification.getCustomerId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="customerId_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getCustomerId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_CustomerId_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'customerId', '<%=_CleanerCustomerNotification.getCustomerId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="customerId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="customerId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="customerId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerNotificationFormEditField_ReasonforId_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_reasonforId_field">
	    <div id="cleanerCustomerNotificationForm_reasonforId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="reasonforId" value="<%=WebUtil.display(_CleanerCustomerNotification.getReasonforId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="reasonforId_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getReasonforId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_ReasonforId_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'reasonforId', '<%=_CleanerCustomerNotification.getReasonforId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="reasonforId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="reasonforId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="reasonforId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerNotificationFormEditField_ReasonforTarget_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_reasonforTarget_field">
	    <div id="cleanerCustomerNotificationForm_reasonforTarget_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="reasonforTarget" value="<%=WebUtil.display(_CleanerCustomerNotification.getReasonforTarget())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="reasonforTarget_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getReasonforTarget() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_ReasonforTarget_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'reasonforTarget', '<%=_CleanerCustomerNotification.getReasonforTarget()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="reasonforTarget">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="reasonforTarget">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="reasonforTarget">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerNotificationFormEditField_NotificationType_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_notificationType_field">
	    <div id="cleanerCustomerNotificationForm_notificationType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="notificationType" value="<%=WebUtil.display(_CleanerCustomerNotification.getNotificationType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="notificationType_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getNotificationType() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_NotificationType_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'notificationType', '<%=_CleanerCustomerNotification.getNotificationType()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="notificationType">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="notificationType">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="notificationType">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerNotificationFormEditField_SourceType_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_sourceType_field">
	    <div id="cleanerCustomerNotificationForm_sourceType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="sourceType" value="<%=WebUtil.display(_CleanerCustomerNotification.getSourceType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="sourceType_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getSourceType() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_SourceType_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'sourceType', '<%=_CleanerCustomerNotification.getSourceType()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="sourceType">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="sourceType">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="sourceType">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerNotificationFormEditField_TriggerType_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_triggerType_field">
	    <div id="cleanerCustomerNotificationForm_triggerType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="triggerType" value="<%=WebUtil.display(_CleanerCustomerNotification.getTriggerType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="triggerType_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getTriggerType() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_TriggerType_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'triggerType', '<%=_CleanerCustomerNotification.getTriggerType()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="triggerType">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="triggerType">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="triggerType">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerNotificationFormEditField_IsRetransmit_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_isRetransmit_field">
	    <div id="cleanerCustomerNotificationForm_isRetransmit_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="isRetransmit" value="<%=WebUtil.display(_CleanerCustomerNotification.getIsRetransmit())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isRetransmit_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getIsRetransmit() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_IsRetransmit_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'isRetransmit', '<%=_CleanerCustomerNotification.getIsRetransmit()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isRetransmit">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isRetransmit">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isRetransmit">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerNotificationFormEditField_MethodType_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_methodType_field">
	    <div id="cleanerCustomerNotificationForm_methodType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="methodType" value="<%=WebUtil.display(_CleanerCustomerNotification.getMethodType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="methodType_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getMethodType() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_MethodType_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'methodType', '<%=_CleanerCustomerNotification.getMethodType()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="methodType">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="methodType">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="methodType">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerNotificationFormEditField_TemplateType_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_templateType_field">
	    <div id="cleanerCustomerNotificationForm_templateType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="templateType" value="<%=WebUtil.display(_CleanerCustomerNotification.getTemplateType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="templateType_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getTemplateType() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_TemplateType_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'templateType', '<%=_CleanerCustomerNotification.getTemplateType()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="templateType">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="templateType">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="templateType">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerNotificationFormEditField_Content_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_content_field">
	    <div id="cleanerCustomerNotificationForm_content_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="content" value="<%=WebUtil.display(_CleanerCustomerNotification.getContent())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="content_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getContent() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_Content_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'content', '<%=_CleanerCustomerNotification.getContent()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="content">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="content">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="content">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerNotificationFormEditField_Destination_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_destination_field">
	    <div id="cleanerCustomerNotificationForm_destination_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="destination" value="<%=WebUtil.display(_CleanerCustomerNotification.getDestination())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="destination_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getDestination() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_Destination_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'destination', '<%=_CleanerCustomerNotification.getDestination()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="destination">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="destination">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="destination">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerNotificationFormEditField_Reference_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_reference_field">
	    <div id="cleanerCustomerNotificationForm_reference_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="reference" value="<%=WebUtil.display(_CleanerCustomerNotification.getReference())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="reference_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getReference() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_Reference_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'reference', '<%=_CleanerCustomerNotification.getReference()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="reference">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="reference">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="reference">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerNotificationFormEditField_TimeScheduled_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_timeScheduled_field">
	    <div id="cleanerCustomerNotificationForm_timeScheduled_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="timeScheduled" value="<%=WebUtil.display(_CleanerCustomerNotification.getTimeScheduled())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="timeScheduled_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getTimeScheduled() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_TimeScheduled_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'timeScheduled', '<%=_CleanerCustomerNotification.getTimeScheduled()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="timeScheduled">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="timeScheduled">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="timeScheduled">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>


    <td> 
	<form name="cleanerCustomerNotificationFormEditField_TimeSent_<%=_CleanerCustomerNotification.getId()%>" method="get" action="/cleanerCustomerNotificationAction.html" >


		<div id="cleanerCustomerNotificationForm_timeSent_field">
	    <div id="cleanerCustomerNotificationForm_timeSent_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="timeSent" value="<%=WebUtil.display(_CleanerCustomerNotification.getTimeSent())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="timeSent_<%= _CleanerCustomerNotification.getId()%>"><%=_CleanerCustomerNotification.getTimeSent() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerNotificationFormEditField_TimeSent_<%=_CleanerCustomerNotification.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomerNotification.getId()%>', 'timeSent', '<%=_CleanerCustomerNotification.getTimeSent()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="timeSent">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="timeSent">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="timeSent">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomerNotification.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_notification_home">
	</form>
    
    
    </td>

    <td>
    <form name="cleanerCustomerNotificationForm<%=_CleanerCustomerNotification.getId()%>2" method="get" action="/v_cleaner_customer_notification_form.html" >
        <a href="javascript:document.cleanerCustomerNotificationForm<%=_CleanerCustomerNotification.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _CleanerCustomerNotification.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="cleaner_customer_notification_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_cleaner_customer_notification_form('<%=_CleanerCustomerNotification.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/cleanerCustomerNotificationAction.html?del=true&id=<%=_CleanerCustomerNotification.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_cleaner_customer_notification('<%=_CleanerCustomerNotification.getId()%>');">DeleteWConfirm</a>
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




	function edit_cleaner_customer_notification_form(target){
		location.href='/v_cleaner_customer_notification_form.html?cmd=edit&prv_returnPage=cleaner_customer_notification_home&id=' + target;
	}

	function confirm_cleaner_customer_notification(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_cleaner_customer_notification(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/cleanerCustomerNotificationAction.html?del=true&id="+target;
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
						location.href="/cleanerCustomerNotificationAction.html?editfield=true&returnPage=cleaner_pickup_home&id="+targetId+"&"+targetField +"=" +f.myname;
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
		   		url: "/cleanerCustomerNotificationAction.html?editfield=true&ajxr=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
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
		   		url: "/cleanerCustomerNotificationAction.html?editfield=true&ajaxRequest=true&ajaxOut=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
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
		   		url: "/cleanerCustomerNotificationAction.html?editfield=true&ajxr=get2field&id="+_id+"&"+_fieldName+"="+ _val,
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
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-cleaner-customer-notification",
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
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-cleaner-customer-notification",
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