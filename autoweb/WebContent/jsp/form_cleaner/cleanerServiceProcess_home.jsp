<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
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
	CleanerServiceProcessDS ds = CleanerServiceProcessDS.getInstance();    

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
<script type="text/javascript" src="/cleanerServiceProcessAction.html?ajxr=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_cleaner_service_process_form.html?prv_returnPage=cleaner_service_process_home"> Add New </a> |
            <a href="v_cleaner_service_process_list.html?"> List Page </a> |
            <a href="v_cleaner_service_process_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/cleanerServiceProcessAction.html?ajxr=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form (custom field list)</a> |
			<a href="/cleanerServiceProcessAction.html?ajxr=getmodalform" 			rel="facebox"> open form</a> |
			<a href="/cleanerServiceProcessAction.html?ajxr=getlisthtml"  			rel="facebox"> getlisthtml</a> |
			<a href="/cleanerServiceProcessAction.html?ajxr=getlistjson"  			rel="facebox"> getlistjson</a> |
			<a href="/cleanerServiceProcessAction.html?ajxr=getjson&ajaxOutArg=first" rel="facebox"> getjson first</a> |
			<a href="/cleanerServiceProcessAction.html?ajxr=getjson&ajaxOutArg=last" 	rel="facebox"> getjson last</a> |
			<a href="/cleanerServiceProcessAction.html?ajxr=getlistdata" 				rel="facebox"> getlistdata</a> |

		</TD>        
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="cleanerServiceProcessForm_ticketId_label" style="font-size: normal normal bold 10px verdana;" >Ticket Id </div>
    </td> 
    <td> 
	    <div id="cleanerServiceProcessForm_processUserId_label" style="font-size: normal normal bold 10px verdana;" >Process User Id </div>
    </td> 
    <td> 
	    <div id="cleanerServiceProcessForm_processType_label" style="font-size: normal normal bold 10px verdana;" >Process Type </div>
    </td> 
    <td> 
	    <div id="cleanerServiceProcessForm_timeStarted_label" style="font-size: normal normal bold 10px verdana;" >Time Started </div>
    </td> 
    <td> 
	    <div id="cleanerServiceProcessForm_timeEnded_label" style="font-size: normal normal bold 10px verdana;" >Time Ended </div>
    </td> 
    <td> 
	    <div id="cleanerServiceProcessForm_note_label" style="font-size: normal normal bold 10px verdana;" >Note </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        CleanerServiceProcess _CleanerServiceProcess = (CleanerServiceProcess) iter.next();
		//TODO 
        fieldString += "\"" +  _CleanerServiceProcess.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _CleanerServiceProcess.getId() %> </td>


    <td> 
	<form name="cleanerServiceProcessFormEditField_TicketId_<%=_CleanerServiceProcess.getId()%>" method="get" action="/cleanerServiceProcessAction.html" >


		<div id="cleanerServiceProcessForm_ticketId_field">
	    <div id="cleanerServiceProcessForm_ticketId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="ticketId" value="<%=WebUtil.display(_CleanerServiceProcess.getTicketId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="ticketId_<%= _CleanerServiceProcess.getId()%>"><%=_CleanerServiceProcess.getTicketId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerServiceProcessFormEditField_TicketId_<%=_CleanerServiceProcess.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerServiceProcess.getId()%>', 'ticketId', '<%=_CleanerServiceProcess.getTicketId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="ticketId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="ticketId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="ticketId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerServiceProcess.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_service_process_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerServiceProcessFormEditField_ProcessUserId_<%=_CleanerServiceProcess.getId()%>" method="get" action="/cleanerServiceProcessAction.html" >


		<div id="cleanerServiceProcessForm_processUserId_field">
	    <div id="cleanerServiceProcessForm_processUserId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="processUserId" value="<%=WebUtil.display(_CleanerServiceProcess.getProcessUserId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="processUserId_<%= _CleanerServiceProcess.getId()%>"><%=_CleanerServiceProcess.getProcessUserId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerServiceProcessFormEditField_ProcessUserId_<%=_CleanerServiceProcess.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerServiceProcess.getId()%>', 'processUserId', '<%=_CleanerServiceProcess.getProcessUserId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="processUserId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="processUserId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="processUserId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerServiceProcess.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_service_process_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerServiceProcessFormEditField_ProcessType_<%=_CleanerServiceProcess.getId()%>" method="get" action="/cleanerServiceProcessAction.html" >


		<div id="cleanerServiceProcessForm_processType_field">
	    <div id="cleanerServiceProcessForm_processType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="processType" value="<%=WebUtil.display(_CleanerServiceProcess.getProcessType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="processType_<%= _CleanerServiceProcess.getId()%>"><%=_CleanerServiceProcess.getProcessType() %></div>
            <a id="formSubmit" href="javascript:document.cleanerServiceProcessFormEditField_ProcessType_<%=_CleanerServiceProcess.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerServiceProcess.getId()%>', 'processType', '<%=_CleanerServiceProcess.getProcessType()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="processType">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="processType">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="processType">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerServiceProcess.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_service_process_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerServiceProcessFormEditField_TimeStarted_<%=_CleanerServiceProcess.getId()%>" method="get" action="/cleanerServiceProcessAction.html" >


		<div id="cleanerServiceProcessForm_timeStarted_field">
	    <div id="cleanerServiceProcessForm_timeStarted_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="timeStarted" value="<%=WebUtil.display(_CleanerServiceProcess.getTimeStarted())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="timeStarted_<%= _CleanerServiceProcess.getId()%>"><%=_CleanerServiceProcess.getTimeStarted() %></div>
            <a id="formSubmit" href="javascript:document.cleanerServiceProcessFormEditField_TimeStarted_<%=_CleanerServiceProcess.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerServiceProcess.getId()%>', 'timeStarted', '<%=_CleanerServiceProcess.getTimeStarted()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="timeStarted">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="timeStarted">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="timeStarted">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerServiceProcess.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_service_process_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerServiceProcessFormEditField_TimeEnded_<%=_CleanerServiceProcess.getId()%>" method="get" action="/cleanerServiceProcessAction.html" >


		<div id="cleanerServiceProcessForm_timeEnded_field">
	    <div id="cleanerServiceProcessForm_timeEnded_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="timeEnded" value="<%=WebUtil.display(_CleanerServiceProcess.getTimeEnded())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="timeEnded_<%= _CleanerServiceProcess.getId()%>"><%=_CleanerServiceProcess.getTimeEnded() %></div>
            <a id="formSubmit" href="javascript:document.cleanerServiceProcessFormEditField_TimeEnded_<%=_CleanerServiceProcess.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerServiceProcess.getId()%>', 'timeEnded', '<%=_CleanerServiceProcess.getTimeEnded()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="timeEnded">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="timeEnded">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="timeEnded">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerServiceProcess.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_service_process_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerServiceProcessFormEditField_Note_<%=_CleanerServiceProcess.getId()%>" method="get" action="/cleanerServiceProcessAction.html" >


		<div id="cleanerServiceProcessForm_note_field">
	    <div id="cleanerServiceProcessForm_note_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="note" value="<%=WebUtil.display(_CleanerServiceProcess.getNote())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="note_<%= _CleanerServiceProcess.getId()%>"><%=_CleanerServiceProcess.getNote() %></div>
            <a id="formSubmit" href="javascript:document.cleanerServiceProcessFormEditField_Note_<%=_CleanerServiceProcess.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerServiceProcess.getId()%>', 'note', '<%=_CleanerServiceProcess.getNote()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="note">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="note">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="note">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerServiceProcess.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_service_process_home">
	</form>
    
    
    </td>

    <td>
    <form name="cleanerServiceProcessForm<%=_CleanerServiceProcess.getId()%>2" method="get" action="/v_cleaner_service_process_form.html" >
        <a href="javascript:document.cleanerServiceProcessForm<%=_CleanerServiceProcess.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _CleanerServiceProcess.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="cleaner_service_process_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_cleaner_service_process_form('<%=_CleanerServiceProcess.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/cleanerServiceProcessAction.html?del=true&id=<%=_CleanerServiceProcess.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_cleaner_service_process('<%=_CleanerServiceProcess.getId()%>');">DeleteWConfirm</a>
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




	function edit_cleaner_service_process_form(target){
		location.href='/v_cleaner_service_process_form.html?cmd=edit&prv_returnPage=cleaner_service_process_home&id=' + target;
	}

	function confirm_cleaner_service_process(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_cleaner_service_process(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/cleanerServiceProcessAction.html?del=true&id="+target;
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
						location.href="/cleanerServiceProcessAction.html?editfield=true&returnPage=cleaner_pickup_home&id="+targetId+"&"+targetField +"=" +f.myname;
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
		   		url: "/cleanerServiceProcessAction.html?editfield=true&ajxr=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
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
		   		url: "/cleanerServiceProcessAction.html?editfield=true&ajaxRequest=true&ajaxOut=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
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
		   		url: "/cleanerServiceProcessAction.html?editfield=true&ajxr=get2field&id="+_id+"&"+_fieldName+"="+ _val,
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
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-cleaner-service-process",
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
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-cleaner-service-process",
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