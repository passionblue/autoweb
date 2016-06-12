<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
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
	ChurExpenseCategoryDS ds = ChurExpenseCategoryDS.getInstance();    

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
<script type="text/javascript" src="/churExpenseCategoryAction.html?ajaxRequest=true&ajaxOut=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_chur_expense_category_form.html?prv_returnPage=chur_expense_category_home"> Add New </a> |
            <a href="v_chur_expense_category_list.html?"> List Page </a> |
            <a href="v_chur_expense_category_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/churExpenseCategoryAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form</a> |
			<a href="/churExpenseCategoryAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox"> open form</a> |
			<a href="/churExpenseCategoryAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox"> getlisthtml</a> |
			<a href="/churExpenseCategoryAction.html?ajaxRequest=true&ajaxOut=getlistjson" rel="facebox"> getlistjson</a> |
			<a href="/churExpenseCategoryAction.html?ajaxRequest=true&ajaxOut=getjson&ajaxOutArg=first" rel="facebox">getjson first</a> |
			<a href="/churExpenseCategoryAction.html?ajaxRequest=true&ajaxOut=getjson&ajaxOutArg=last" rel="facebox">getjson last</a> |
			<a href="/churExpenseCategoryAction.html?ajaxRequest=true&ajaxOut=getlistdata" rel="facebox">getlistdata</a> |
		</TD>        

    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="churExpenseCategoryForm_expenseCategory_label" style="font-size: normal normal bold 10px verdana;" >Expense Category </div>
    </td> 
    <td> 
	    <div id="churExpenseCategoryForm_display_label" style="font-size: normal normal bold 10px verdana;" >Display </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurExpenseCategory _ChurExpenseCategory = (ChurExpenseCategory) iter.next();
		//TODO 
        fieldString += "\"" +  _ChurExpenseCategory.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurExpenseCategory.getId() %> </td>


    <td> 
	<form name="churExpenseCategoryFormEditField_ExpenseCategory_<%=_ChurExpenseCategory.getId()%>" method="get" action="/churExpenseCategoryAction.html" >


		<div id="churExpenseCategoryForm_expenseCategory_field">
	    <div id="churExpenseCategoryForm_expenseCategory_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="expenseCategory" value="<%=WebUtil.display(_ChurExpenseCategory.getExpenseCategory())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.churExpenseCategoryFormEditField_ExpenseCategory_<%=_ChurExpenseCategory.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurExpenseCategory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_expense_category_home">
	</form>
    
    
    </td>

    <td> 
	<form name="churExpenseCategoryFormEditField_Display_<%=_ChurExpenseCategory.getId()%>" method="get" action="/churExpenseCategoryAction.html" >


		<div id="churExpenseCategoryForm_display_field">
	    <div id="churExpenseCategoryForm_display_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="display" value="<%=WebUtil.display(_ChurExpenseCategory.getDisplay())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.churExpenseCategoryFormEditField_Display_<%=_ChurExpenseCategory.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurExpenseCategory.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_expense_category_home">
	</form>
    
    
    </td>

    <td>
    <form name="churExpenseCategoryForm<%=_ChurExpenseCategory.getId()%>2" method="get" action="/v_chur_expense_category_form.html" >
        <a href="javascript:document.churExpenseCategoryForm<%=_ChurExpenseCategory.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurExpenseCategory.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="chur_expense_category_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_chur_expense_category_form('<%=_ChurExpenseCategory.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/churExpenseCategoryAction.html?del=true&id=<%=_ChurExpenseCategory.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_chur_expense_category('<%=_ChurExpenseCategory.getId()%>');">DeleteWConfirm</a>
    </td>
</TR>

<%
    }
	if ( fieldString != null && fieldString.length() > 0 )
	fieldString = fieldString.substring(0, fieldString.length()-1);

%>
</TABLE>

<script type="text/javascript">

	function edit_chur_expense_category_form(target){
		location.href='/v_chur_expense_category_form.html?cmd=edit&prv_returnPage=chur_expense_category_home&id=' + target;
	}

	function confirm_chur_expense_category(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_chur_expense_category(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/churExpenseCategoryAction.html?del=true&id="+target;
				}
			}
		});
	}
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