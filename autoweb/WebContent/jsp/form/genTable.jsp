<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
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
	GenTableDS ds = GenTableDS.getInstance();    

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
<script type="text/javascript" src="/genTableAction.html?ajaxRequest=true&ajaxOut=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_gen_table_form.html?prv_returnPage=gen_table_home"> Add New </a> |
            <a href="v_gen_table_list.html?"> List Page </a> |
            <a href="v_gen_table_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/genTableAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form</a> |
			<a href="/genTableAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox"> open form</a> |
			<a href="/genTableAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox"> getlisthtml</a> |
			<a href="/genTableAction.html?ajaxRequest=true&ajaxOut=getlistjson" rel="facebox"> getlistjson</a> |
			<a href="/genTableAction.html?ajaxRequest=true&ajaxOut=getjson&ajaxOutArg=first" rel="facebox">getjson first</a> |
			<a href="/genTableAction.html?ajaxRequest=true&ajaxOut=getjson&ajaxOutArg=last" rel="facebox">getjson last</a> |
			<a href="/genTableAction.html?ajaxRequest=true&ajaxOut=getlistdata" rel="facebox">getlistdata</a> |
		</TD>        

    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="genTableForm_country_label" style="font-size: normal normal bold 10px verdana;" >Country </div>
    </td> 
    <td> 
	    <div id="genTableForm_age_label" style="font-size: normal normal bold 10px verdana;" >Age </div>
    </td> 
    <td> 
	    <div id="genTableForm_disabled_label" style="font-size: normal normal bold 10px verdana;" >Disabled </div>
    </td> 
    <td> 
	    <div id="genTableForm_comments_label" style="font-size: normal normal bold 10px verdana;" >Comments </div>
    </td> 
    <td> 
	    <div id="genTableForm_extString_label" style="font-size: normal normal bold 10px verdana;" >ExtraString </div>
    </td> 
    <td> 
	    <div id="genTableForm_extInt_label" style="font-size: normal normal bold 10px verdana;" >Ext Int </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        GenTableDataHolder _GenTable = (GenTableDataHolder) iter.next();
		//TODO 
        fieldString += "\"" +  _GenTable.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _GenTable.getId() %> </td>


    <td> 
	<form name="genTableFormEditField_Country_<%=_GenTable.getId()%>" method="get" action="/genTableAction.html" >

		<div id="genTableForm_country_field">
	    <div id="genTableForm_country_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="country">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _GenTable.getCountry())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="country_<%= _GenTable.getId()%>"><%=_GenTable.getCountry() %></div>
            <a id="formSubmit" href="javascript:document.genTableFormEditField_Country_<%=_GenTable.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_GenTable.getId()%>', 'country', '<%=_GenTable.getCountry()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="country">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="gen_table_home">
	</form>
    
    
    </td>

    <td> 
	<form name="genTableFormEditField_Age_<%=_GenTable.getId()%>" method="get" action="/genTableAction.html" >

		<div id="genTableForm_age_field">
	    <div id="genTableForm_age_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="age">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _GenTable.getAge())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="age_<%= _GenTable.getId()%>"><%=_GenTable.getAge() %></div>
            <a id="formSubmit" href="javascript:document.genTableFormEditField_Age_<%=_GenTable.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_GenTable.getId()%>', 'age', '<%=_GenTable.getAge()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="age">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="gen_table_home">
	</form>
    
    
    </td>

    <td> 
	<form name="genTableFormEditField_Disabled_<%=_GenTable.getId()%>" method="get" action="/genTableAction.html" >


		<div id="genTableForm_disabled_field">
	    <div id="genTableForm_disabled_dropdown" class="formFieldDropDown" >       
	        <select name="disabled">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _GenTable.getDisabled())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _GenTable.getDisabled())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="disabled_<%= _GenTable.getId()%>"><%=_GenTable.getDisabled() %></div>
            <a id="formSubmit" href="javascript:document.genTableFormEditField_Disabled_<%=_GenTable.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_GenTable.getId()%>', 'disabled', '<%=_GenTable.getDisabled()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="disabled">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="gen_table_home">
	</form>
    
    
    </td>

    <td> 
	<form name="genTableFormEditField_Comments_<%=_GenTable.getId()%>" method="get" action="/genTableAction.html" >


		<div id="genTableForm_comments_field">
	    <div id="genTableForm_comments_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="comments" value="<%=WebUtil.display(_GenTable.getComments())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="comments_<%= _GenTable.getId()%>"><%=_GenTable.getComments() %></div>
            <a id="formSubmit" href="javascript:document.genTableFormEditField_Comments_<%=_GenTable.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_GenTable.getId()%>', 'comments', '<%=_GenTable.getComments()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="comments">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="gen_table_home">
	</form>
    
    
    </td>



    <td> 
	<form name="genTableFormEditField_ExtString_<%=_GenTable.getId()%>" method="get" action="/genTableAction.html" >


		<div id="genTableForm_extString_field">
	    <div id="genTableForm_extString_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="extString" value="<%=WebUtil.display(_GenTable.getExtString())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="extString_<%= _GenTable.getId()%>"><%=_GenTable.getExtString() %></div>
            <a id="formSubmit" href="javascript:document.genTableFormEditField_ExtString_<%=_GenTable.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_GenTable.getId()%>', 'extString', '<%=_GenTable.getExtString()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="extString">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="gen_table_home">
	</form>
    
    
    </td>

    <td> 
	<form name="genTableFormEditField_ExtInt_<%=_GenTable.getId()%>" method="get" action="/genTableAction.html" >


		<div id="genTableForm_extInt_field">
	    <div id="genTableForm_extInt_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="extInt" value="<%=WebUtil.display(_GenTable.getExtInt())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="extInt_<%= _GenTable.getId()%>"><%=_GenTable.getExtInt() %></div>
            <a id="formSubmit" href="javascript:document.genTableFormEditField_ExtInt_<%=_GenTable.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_GenTable.getId()%>', 'extInt', '<%=_GenTable.getExtInt()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="extInt">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="gen_table_home">
	</form>
    
    
    </td>

    <td>
    <form name="genTableForm<%=_GenTable.getId()%>2" method="get" action="/v_gen_table_form.html" >
        <a href="javascript:document.genTableForm<%=_GenTable.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _GenTable.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="gen_table_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_gen_table_form('<%=_GenTable.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/genTableAction.html?del=true&id=<%=_GenTable.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_gen_table('<%=_GenTable.getId()%>');">DeleteWConfirm</a>
    </td>
</TR>

<%
    }
	if ( fieldString != null && fieldString.length() > 0 )
	fieldString = fieldString.substring(0, fieldString.length()-1);

%>
</TABLE>

<script type="text/javascript">

	function edit_gen_table_form(target){
		location.href='/v_gen_table_form.html?cmd=edit&prv_returnPage=gen_table_home&id=' + target;
	}

	function confirm_gen_table(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_gen_table(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/genTableAction.html?del=true&id="+target;
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
						location.href="/genTableAction.html?editfield=true&returnPage=cleaner_pickup_home&id="+targetId+"&"+targetField +"=" +f.myname;
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
	   		url: "/genTableAction.html?editfield=true&ajaxRequest=true&ajaxOut=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
	  		success: function(msg){
	     		alert( "Value Successfully Changed");
	     		$("#" + _fieldName+"_"+_id).text(msg);
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