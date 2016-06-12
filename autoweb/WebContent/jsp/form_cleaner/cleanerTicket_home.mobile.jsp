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
	CleanerTicketDS ds = CleanerTicketDS.getInstance();    

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
<script type="text/javascript" src="/cleanerTicketAction.html?ajxr=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_cleaner_ticket_form.html?prv_returnPage=cleaner_ticket_home"> Add New </a> |
            <a href="v_cleaner_ticket_list.html?"> List Page </a> |
            <a href="v_cleaner_ticket_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/cleanerTicketAction.html?ajxr=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form (custom field list)</a> |
			<a href="/cleanerTicketAction.html?ajxr=getmodalform" 			rel="facebox"> open form</a> |
			<a href="/cleanerTicketAction.html?ajxr=getlisthtml"  			rel="facebox"> getlisthtml</a> |
			<a href="/cleanerTicketAction.html?ajxr=getlistjson"  			rel="facebox"> getlistjson</a> |
			<a href="/cleanerTicketAction.html?ajxr=getjson&ajaxOutArg=first" rel="facebox"> getjson first</a> |
			<a href="/cleanerTicketAction.html?ajxr=getjson&ajaxOutArg=last" 	rel="facebox"> getjson last</a> |
			<a href="/cleanerTicketAction.html?ajxr=getlistdata" 				rel="facebox"> getlistdata</a> |

		</TD>        
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="cleanerTicketForm_serial_label" style="font-size: normal normal bold 10px verdana;" >Serial </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_parentTicketId_label" style="font-size: normal normal bold 10px verdana;" >Parent Ticket Id </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_customerId_label" style="font-size: normal normal bold 10px verdana;" >Customer Id </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_enterUserId_label" style="font-size: normal normal bold 10px verdana;" >Enter User Id </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_locationId_label" style="font-size: normal normal bold 10px verdana;" >Location Id </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_note_label" style="font-size: normal normal bold 10px verdana;" >Note </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_completed_label" style="font-size: normal normal bold 10px verdana;" >Completed </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_onhold_label" style="font-size: normal normal bold 10px verdana;" >Onhold </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_originalTicketId_label" style="font-size: normal normal bold 10px verdana;" >Original Ticket Id </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_returned_label" style="font-size: normal normal bold 10px verdana;" >Returned </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_returnedReasonText_label" style="font-size: normal normal bold 10px verdana;" >Returned Reason Text </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_returnedNote_label" style="font-size: normal normal bold 10px verdana;" >Returned Note </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_totalCharge_label" style="font-size: normal normal bold 10px verdana;" >Total Charge </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_finalCharge_label" style="font-size: normal normal bold 10px verdana;" >Final Charge </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_discountId_label" style="font-size: normal normal bold 10px verdana;" >Discount Id </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_discountAmount_label" style="font-size: normal normal bold 10px verdana;" >Discount Amount </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_discountNote_label" style="font-size: normal normal bold 10px verdana;" >Discount Note </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_timeCompleted_label" style="font-size: normal normal bold 10px verdana;" >Time Completed </div>
    </td> 
    <td> 
	    <div id="cleanerTicketForm_timeOnhold_label" style="font-size: normal normal bold 10px verdana;" >Time Onhold </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        CleanerTicket _CleanerTicket = (CleanerTicket) iter.next();
		//TODO 
        fieldString += "\"" +  _CleanerTicket.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _CleanerTicket.getId() %> </td>


    <td> 
	<form name="cleanerTicketFormEditField_Serial_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_serial_field">
	    <div id="cleanerTicketForm_serial_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="serial" value="<%=WebUtil.display(_CleanerTicket.getSerial())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="serial_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getSerial() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_Serial_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'serial', '<%=_CleanerTicket.getSerial()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="serial">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="serial">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="serial">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_ParentTicketId_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_parentTicketId_field">
	    <div id="cleanerTicketForm_parentTicketId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="parentTicketId" value="<%=WebUtil.display(_CleanerTicket.getParentTicketId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="parentTicketId_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getParentTicketId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_ParentTicketId_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'parentTicketId', '<%=_CleanerTicket.getParentTicketId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="parentTicketId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="parentTicketId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="parentTicketId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_CustomerId_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_customerId_field">
	    <div id="cleanerTicketForm_customerId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="customerId" value="<%=WebUtil.display(_CleanerTicket.getCustomerId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="customerId_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getCustomerId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_CustomerId_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'customerId', '<%=_CleanerTicket.getCustomerId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="customerId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="customerId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="customerId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_EnterUserId_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_enterUserId_field">
	    <div id="cleanerTicketForm_enterUserId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="enterUserId" value="<%=WebUtil.display(_CleanerTicket.getEnterUserId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="enterUserId_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getEnterUserId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_EnterUserId_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'enterUserId', '<%=_CleanerTicket.getEnterUserId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="enterUserId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="enterUserId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="enterUserId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_LocationId_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_locationId_field">
	    <div id="cleanerTicketForm_locationId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="locationId" value="<%=WebUtil.display(_CleanerTicket.getLocationId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="locationId_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getLocationId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_LocationId_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'locationId', '<%=_CleanerTicket.getLocationId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="locationId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="locationId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="locationId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_Note_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_note_field">
	    <div id="cleanerTicketForm_note_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="note" value="<%=WebUtil.display(_CleanerTicket.getNote())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="note_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getNote() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_Note_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'note', '<%=_CleanerTicket.getNote()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="note">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="note">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="note">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_Completed_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_completed_field">
	    <div id="cleanerTicketForm_completed_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="completed" value="<%=WebUtil.display(_CleanerTicket.getCompleted())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="completed_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getCompleted() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_Completed_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'completed', '<%=_CleanerTicket.getCompleted()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="completed">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="completed">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="completed">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_Onhold_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_onhold_field">
	    <div id="cleanerTicketForm_onhold_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="onhold" value="<%=WebUtil.display(_CleanerTicket.getOnhold())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="onhold_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getOnhold() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_Onhold_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'onhold', '<%=_CleanerTicket.getOnhold()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="onhold">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="onhold">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="onhold">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_OriginalTicketId_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_originalTicketId_field">
	    <div id="cleanerTicketForm_originalTicketId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="originalTicketId" value="<%=WebUtil.display(_CleanerTicket.getOriginalTicketId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="originalTicketId_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getOriginalTicketId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_OriginalTicketId_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'originalTicketId', '<%=_CleanerTicket.getOriginalTicketId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="originalTicketId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="originalTicketId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="originalTicketId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_Returned_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_returned_field">
	    <div id="cleanerTicketForm_returned_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="returned" value="<%=WebUtil.display(_CleanerTicket.getReturned())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="returned_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getReturned() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_Returned_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'returned', '<%=_CleanerTicket.getReturned()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="returned">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="returned">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="returned">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_ReturnedReasonText_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_returnedReasonText_field">
	    <div id="cleanerTicketForm_returnedReasonText_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="returnedReasonText" value="<%=WebUtil.display(_CleanerTicket.getReturnedReasonText())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="returnedReasonText_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getReturnedReasonText() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_ReturnedReasonText_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'returnedReasonText', '<%=_CleanerTicket.getReturnedReasonText()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="returnedReasonText">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="returnedReasonText">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="returnedReasonText">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_ReturnedNote_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_returnedNote_field">
	    <div id="cleanerTicketForm_returnedNote_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="returnedNote" value="<%=WebUtil.display(_CleanerTicket.getReturnedNote())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="returnedNote_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getReturnedNote() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_ReturnedNote_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'returnedNote', '<%=_CleanerTicket.getReturnedNote()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="returnedNote">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="returnedNote">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="returnedNote">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_TotalCharge_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_totalCharge_field">
	    <div id="cleanerTicketForm_totalCharge_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="totalCharge" value="<%=WebUtil.display(_CleanerTicket.getTotalCharge())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="totalCharge_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getTotalCharge() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_TotalCharge_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'totalCharge', '<%=_CleanerTicket.getTotalCharge()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="totalCharge">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="totalCharge">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="totalCharge">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_FinalCharge_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_finalCharge_field">
	    <div id="cleanerTicketForm_finalCharge_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="finalCharge" value="<%=WebUtil.display(_CleanerTicket.getFinalCharge())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="finalCharge_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getFinalCharge() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_FinalCharge_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'finalCharge', '<%=_CleanerTicket.getFinalCharge()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="finalCharge">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="finalCharge">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="finalCharge">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_DiscountId_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_discountId_field">
	    <div id="cleanerTicketForm_discountId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="discountId" value="<%=WebUtil.display(_CleanerTicket.getDiscountId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="discountId_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getDiscountId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_DiscountId_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'discountId', '<%=_CleanerTicket.getDiscountId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="discountId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="discountId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="discountId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_DiscountAmount_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_discountAmount_field">
	    <div id="cleanerTicketForm_discountAmount_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="discountAmount" value="<%=WebUtil.display(_CleanerTicket.getDiscountAmount())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="discountAmount_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getDiscountAmount() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_DiscountAmount_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'discountAmount', '<%=_CleanerTicket.getDiscountAmount()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="discountAmount">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="discountAmount">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="discountAmount">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_DiscountNote_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_discountNote_field">
	    <div id="cleanerTicketForm_discountNote_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="discountNote" value="<%=WebUtil.display(_CleanerTicket.getDiscountNote())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="discountNote_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getDiscountNote() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_DiscountNote_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'discountNote', '<%=_CleanerTicket.getDiscountNote()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="discountNote">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="discountNote">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="discountNote">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>



    <td> 
	<form name="cleanerTicketFormEditField_TimeCompleted_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_timeCompleted_field">
	    <div id="cleanerTicketForm_timeCompleted_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="timeCompleted" value="<%=WebUtil.display(_CleanerTicket.getTimeCompleted())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="timeCompleted_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getTimeCompleted() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_TimeCompleted_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'timeCompleted', '<%=_CleanerTicket.getTimeCompleted()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="timeCompleted">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="timeCompleted">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="timeCompleted">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerTicketFormEditField_TimeOnhold_<%=_CleanerTicket.getId()%>" method="get" action="/cleanerTicketAction.html" >


		<div id="cleanerTicketForm_timeOnhold_field">
	    <div id="cleanerTicketForm_timeOnhold_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="timeOnhold" value="<%=WebUtil.display(_CleanerTicket.getTimeOnhold())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="timeOnhold_<%= _CleanerTicket.getId()%>"><%=_CleanerTicket.getTimeOnhold() %></div>
            <a id="formSubmit" href="javascript:document.cleanerTicketFormEditField_TimeOnhold_<%=_CleanerTicket.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerTicket.getId()%>', 'timeOnhold', '<%=_CleanerTicket.getTimeOnhold()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="timeOnhold">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="timeOnhold">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="timeOnhold">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerTicket.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_ticket_home">
	</form>
    
    
    </td>

    <td>
    <form name="cleanerTicketForm<%=_CleanerTicket.getId()%>2" method="get" action="/v_cleaner_ticket_form.html" >
        <a href="javascript:document.cleanerTicketForm<%=_CleanerTicket.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _CleanerTicket.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="cleaner_ticket_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_cleaner_ticket_form('<%=_CleanerTicket.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/cleanerTicketAction.html?del=true&id=<%=_CleanerTicket.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_cleaner_ticket('<%=_CleanerTicket.getId()%>');">DeleteWConfirm</a>
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




	function edit_cleaner_ticket_form(target){
		location.href='/v_cleaner_ticket_form.html?cmd=edit&prv_returnPage=cleaner_ticket_home&id=' + target;
	}

	function confirm_cleaner_ticket(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_cleaner_ticket(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/cleanerTicketAction.html?del=true&id="+target;
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
						location.href="/cleanerTicketAction.html?editfield=true&returnPage=cleaner_pickup_home&id="+targetId+"&"+targetField +"=" +f.myname;
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
		   		url: "/cleanerTicketAction.html?editfield=true&ajxr=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
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
		   		url: "/cleanerTicketAction.html?editfield=true&ajaxRequest=true&ajaxOut=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
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
		   		url: "/cleanerTicketAction.html?editfield=true&ajxr=get2field&id="+_id+"&"+_fieldName+"="+ _val,
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
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-cleaner-ticket",
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
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-cleaner-ticket",
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