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
	CleanerCustomerDS ds = CleanerCustomerDS.getInstance();    

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
<script type="text/javascript" src="/cleanerCustomerAction.html?ajxr=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_cleaner_customer_form.html?prv_returnPage=cleaner_customer_home"> Add New </a> |
            <a href="v_cleaner_customer_list.html?"> List Page </a> |
            <a href="v_cleaner_customer_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/cleanerCustomerAction.html?ajxr=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form (custom field list)</a> |
			<a href="/cleanerCustomerAction.html?ajxr=getmodalform" 			rel="facebox"> open form</a> |
			<a href="/cleanerCustomerAction.html?ajxr=getlisthtml"  			rel="facebox"> getlisthtml</a> |
			<a href="/cleanerCustomerAction.html?ajxr=getlistjson"  			rel="facebox"> getlistjson</a> |
			<a href="/cleanerCustomerAction.html?ajxr=getjson&ajaxOutArg=first" rel="facebox"> getjson first</a> |
			<a href="/cleanerCustomerAction.html?ajxr=getjson&ajaxOutArg=last" 	rel="facebox"> getjson last</a> |
			<a href="/cleanerCustomerAction.html?ajxr=getlistdata" 				rel="facebox"> getlistdata</a> |

        	<a href="javascript:submit_cmd_synch();">run cmd synch</a>|
		</TD>        
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="cleanerCustomerForm_autositeUserId_label" style="font-size: normal normal bold 10px verdana;" >Autosite User Id </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_email_label" style="font-size: normal normal bold 10px verdana;" >Email </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_phone_label" style="font-size: normal normal bold 10px verdana;" >Phone </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_phone2_label" style="font-size: normal normal bold 10px verdana;" >Phone2 </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_phonePreferred_label" style="font-size: normal normal bold 10px verdana;" >Phone Preferred </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_title_label" style="font-size: normal normal bold 10px verdana;" >Title </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_lastName_label" style="font-size: normal normal bold 10px verdana;" >Last Name </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_firstName_label" style="font-size: normal normal bold 10px verdana;" >First Name </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_address_label" style="font-size: normal normal bold 10px verdana;" >Address </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_apt_label" style="font-size: normal normal bold 10px verdana;" >Apt </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_city_label" style="font-size: normal normal bold 10px verdana;" >City </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_state_label" style="font-size: normal normal bold 10px verdana;" >State </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_zip_label" style="font-size: normal normal bold 10px verdana;" >Zip </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_customerType_label" style="font-size: normal normal bold 10px verdana;" >Customer Type </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_payType_label" style="font-size: normal normal bold 10px verdana;" >Pay Type </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_remoteIp_label" style="font-size: normal normal bold 10px verdana;" >Remote Ip </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_note_label" style="font-size: normal normal bold 10px verdana;" >Note </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_pickupNote_label" style="font-size: normal normal bold 10px verdana;" >Pickup Note </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_deliveryNote_label" style="font-size: normal normal bold 10px verdana;" >Delivery Note </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_disabled_label" style="font-size: normal normal bold 10px verdana;" >Disabled </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_pickupDeliveryDisallowed_label" style="font-size: normal normal bold 10px verdana;" >Pickup Delivery Disallowed </div>
    </td> 
    <td> 
	    <div id="cleanerCustomerForm_handleInst_label" style="font-size: normal normal bold 10px verdana;" >Handle Inst </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        CleanerCustomer _CleanerCustomer = (CleanerCustomer) iter.next();
		//TODO 
        fieldString += "\"" +  _CleanerCustomer.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _CleanerCustomer.getId() %> </td>


    <td> 
	<form name="cleanerCustomerFormEditField_AutositeUserId_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_autositeUserId_field">
	    <div id="cleanerCustomerForm_autositeUserId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="autositeUserId" value="<%=WebUtil.display(_CleanerCustomer.getAutositeUserId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="autositeUserId_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getAutositeUserId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_AutositeUserId_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'autositeUserId', '<%=_CleanerCustomer.getAutositeUserId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="autositeUserId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="autositeUserId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="autositeUserId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_Email_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_email_field">
	    <div id="cleanerCustomerForm_email_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="email" value="<%=WebUtil.display(_CleanerCustomer.getEmail())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="email_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getEmail() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_Email_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'email', '<%=_CleanerCustomer.getEmail()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="email">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="email">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="email">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_Phone_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_phone_field">
	    <div id="cleanerCustomerForm_phone_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="phone" value="<%=WebUtil.display(_CleanerCustomer.getPhone())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="phone_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getPhone() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_Phone_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'phone', '<%=_CleanerCustomer.getPhone()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="phone">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="phone">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="phone">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_Phone2_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_phone2_field">
	    <div id="cleanerCustomerForm_phone2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="phone2" value="<%=WebUtil.display(_CleanerCustomer.getPhone2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="phone2_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getPhone2() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_Phone2_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'phone2', '<%=_CleanerCustomer.getPhone2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="phone2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="phone2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="phone2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_PhonePreferred_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_phonePreferred_field">
	    <div id="cleanerCustomerForm_phonePreferred_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="phonePreferred" value="<%=WebUtil.display(_CleanerCustomer.getPhonePreferred())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="phonePreferred_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getPhonePreferred() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_PhonePreferred_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'phonePreferred', '<%=_CleanerCustomer.getPhonePreferred()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="phonePreferred">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="phonePreferred">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="phonePreferred">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_Title_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_title_field">
	    <div id="cleanerCustomerForm_title_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="title" value="<%=WebUtil.display(_CleanerCustomer.getTitle())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="title_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getTitle() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_Title_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'title', '<%=_CleanerCustomer.getTitle()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="title">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="title">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="title">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_LastName_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_lastName_field">
	    <div id="cleanerCustomerForm_lastName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="lastName" value="<%=WebUtil.display(_CleanerCustomer.getLastName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="lastName_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getLastName() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_LastName_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'lastName', '<%=_CleanerCustomer.getLastName()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="lastName">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="lastName">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="lastName">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_FirstName_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_firstName_field">
	    <div id="cleanerCustomerForm_firstName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="firstName" value="<%=WebUtil.display(_CleanerCustomer.getFirstName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="firstName_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getFirstName() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_FirstName_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'firstName', '<%=_CleanerCustomer.getFirstName()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="firstName">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="firstName">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="firstName">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_Address_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_address_field">
	    <div id="cleanerCustomerForm_address_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="address" value="<%=WebUtil.display(_CleanerCustomer.getAddress())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="address_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getAddress() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_Address_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'address', '<%=_CleanerCustomer.getAddress()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="address">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="address">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="address">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_Apt_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_apt_field">
	    <div id="cleanerCustomerForm_apt_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="apt" value="<%=WebUtil.display(_CleanerCustomer.getApt())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="apt_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getApt() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_Apt_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'apt', '<%=_CleanerCustomer.getApt()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="apt">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="apt">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="apt">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_City_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_city_field">
	    <div id="cleanerCustomerForm_city_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="city" value="<%=WebUtil.display(_CleanerCustomer.getCity())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="city_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getCity() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_City_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'city', '<%=_CleanerCustomer.getCity()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="city">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="city">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="city">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_State_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_state_field">
	    <div id="cleanerCustomerForm_state_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="state" value="<%=WebUtil.display(_CleanerCustomer.getState())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="state_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getState() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_State_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'state', '<%=_CleanerCustomer.getState()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="state">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="state">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="state">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_Zip_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_zip_field">
	    <div id="cleanerCustomerForm_zip_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="zip" value="<%=WebUtil.display(_CleanerCustomer.getZip())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="zip_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getZip() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_Zip_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'zip', '<%=_CleanerCustomer.getZip()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="zip">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="zip">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="zip">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_CustomerType_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_customerType_field">
	    <div id="cleanerCustomerForm_customerType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="customerType" value="<%=WebUtil.display(_CleanerCustomer.getCustomerType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="customerType_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getCustomerType() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_CustomerType_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'customerType', '<%=_CleanerCustomer.getCustomerType()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="customerType">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="customerType">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="customerType">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_PayType_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_payType_field">
	    <div id="cleanerCustomerForm_payType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="payType" value="<%=WebUtil.display(_CleanerCustomer.getPayType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="payType_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getPayType() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_PayType_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'payType', '<%=_CleanerCustomer.getPayType()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="payType">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="payType">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="payType">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>


    <td> 
	<form name="cleanerCustomerFormEditField_RemoteIp_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_remoteIp_field">
	    <div id="cleanerCustomerForm_remoteIp_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="remoteIp" value="<%=WebUtil.display(_CleanerCustomer.getRemoteIp())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="remoteIp_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getRemoteIp() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_RemoteIp_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'remoteIp', '<%=_CleanerCustomer.getRemoteIp()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="remoteIp">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="remoteIp">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="remoteIp">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_Note_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_note_field">
	    <div id="cleanerCustomerForm_note_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="note" value="<%=WebUtil.display(_CleanerCustomer.getNote())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="note_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getNote() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_Note_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'note', '<%=_CleanerCustomer.getNote()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="note">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="note">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="note">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_PickupNote_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_pickupNote_field">
	    <div id="cleanerCustomerForm_pickupNote_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pickupNote" value="<%=WebUtil.display(_CleanerCustomer.getPickupNote())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="pickupNote_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getPickupNote() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_PickupNote_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'pickupNote', '<%=_CleanerCustomer.getPickupNote()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="pickupNote">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="pickupNote">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="pickupNote">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_DeliveryNote_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_deliveryNote_field">
	    <div id="cleanerCustomerForm_deliveryNote_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="deliveryNote" value="<%=WebUtil.display(_CleanerCustomer.getDeliveryNote())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="deliveryNote_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getDeliveryNote() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_DeliveryNote_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'deliveryNote', '<%=_CleanerCustomer.getDeliveryNote()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="deliveryNote">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="deliveryNote">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="deliveryNote">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_Disabled_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_disabled_field">
	    <div id="cleanerCustomerForm_disabled_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="disabled" value="<%=WebUtil.display(_CleanerCustomer.getDisabled())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="disabled_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getDisabled() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_Disabled_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'disabled', '<%=_CleanerCustomer.getDisabled()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="disabled">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="disabled">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="disabled">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>


    <td> 
	<form name="cleanerCustomerFormEditField_PickupDeliveryDisallowed_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_pickupDeliveryDisallowed_field">
	    <div id="cleanerCustomerForm_pickupDeliveryDisallowed_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pickupDeliveryDisallowed" value="<%=WebUtil.display(_CleanerCustomer.getPickupDeliveryDisallowed())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="pickupDeliveryDisallowed_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getPickupDeliveryDisallowed() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_PickupDeliveryDisallowed_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'pickupDeliveryDisallowed', '<%=_CleanerCustomer.getPickupDeliveryDisallowed()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="pickupDeliveryDisallowed">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="pickupDeliveryDisallowed">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="pickupDeliveryDisallowed">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerCustomerFormEditField_HandleInst_<%=_CleanerCustomer.getId()%>" method="get" action="/cleanerCustomerAction.html" >


		<div id="cleanerCustomerForm_handleInst_field">
	    <div id="cleanerCustomerForm_handleInst_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="handleInst" value="<%=WebUtil.display(_CleanerCustomer.getHandleInst())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="handleInst_<%= _CleanerCustomer.getId()%>"><%=_CleanerCustomer.getHandleInst() %></div>
            <a id="formSubmit" href="javascript:document.cleanerCustomerFormEditField_HandleInst_<%=_CleanerCustomer.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerCustomer.getId()%>', 'handleInst', '<%=_CleanerCustomer.getHandleInst()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="handleInst">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="handleInst">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="handleInst">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_customer_home">
	</form>
    
    
    </td>

    <td>
    <form name="cleanerCustomerForm<%=_CleanerCustomer.getId()%>2" method="get" action="/v_cleaner_customer_form.html" >
        <a href="javascript:document.cleanerCustomerForm<%=_CleanerCustomer.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _CleanerCustomer.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="cleaner_customer_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_cleaner_customer_form('<%=_CleanerCustomer.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/cleanerCustomerAction.html?del=true&id=<%=_CleanerCustomer.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_cleaner_customer('<%=_CleanerCustomer.getId()%>');">DeleteWConfirm</a>
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

	function submit_cmd_synch(){
		location.href='/cleanerCustomerAction.html?synch=true<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}



	function edit_cleaner_customer_form(target){
		location.href='/v_cleaner_customer_form.html?cmd=edit&prv_returnPage=cleaner_customer_home&id=' + target;
	}

	function confirm_cleaner_customer(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_cleaner_customer(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/cleanerCustomerAction.html?del=true&id="+target;
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
						location.href="/cleanerCustomerAction.html?editfield=true&returnPage=cleaner_pickup_home&id="+targetId+"&"+targetField +"=" +f.myname;
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
		   		url: "/cleanerCustomerAction.html?editfield=true&ajxr=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
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
		   		url: "/cleanerCustomerAction.html?editfield=true&ajaxRequest=true&ajaxOut=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
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
		   		url: "/cleanerCustomerAction.html?editfield=true&ajxr=get2field&id="+_id+"&"+_fieldName+"="+ _val,
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
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-cleaner-customer",
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
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-cleaner-customer",
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