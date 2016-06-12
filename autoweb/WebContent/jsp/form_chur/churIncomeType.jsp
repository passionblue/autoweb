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
	ChurIncomeTypeDS ds = ChurIncomeTypeDS.getInstance();    

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
<script type="text/javascript" src="/churIncomeTypeAction.html?ajaxRequest=true&ajaxOut=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_chur_income_type_form.html?prv_returnPage=chur_income_type_home"> Add New </a> |
            <a href="v_chur_income_type_list.html?"> List Page </a> |
            <a href="v_chur_income_type_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/churIncomeTypeAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form</a> |
			<a href="/churIncomeTypeAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox"> open form</a> |
			<a href="/churIncomeTypeAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox"> getlisthtml</a> |
			<a href="/churIncomeTypeAction.html?ajaxRequest=true&ajaxOut=getlistjson" rel="facebox"> getlistjson</a> |
			<a href="/churIncomeTypeAction.html?ajaxRequest=true&ajaxOut=getjson&ajaxOutArg=first" rel="facebox">getjson first</a> |
			<a href="/churIncomeTypeAction.html?ajaxRequest=true&ajaxOut=getjson&ajaxOutArg=last" rel="facebox">getjson last</a> |
			<a href="/churIncomeTypeAction.html?ajaxRequest=true&ajaxOut=getlistdata" rel="facebox">getlistdata</a> |
		</TD>        

    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="churIncomeTypeForm_incomeType_label" style="font-size: normal normal bold 10px verdana;" >Income Type </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurIncomeType _ChurIncomeType = (ChurIncomeType) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurIncomeType.getId() %> </td>


    <td> 
	<form name="churIncomeTypeFormEditField_IncomeType_<%=_ChurIncomeType.getId()%>" method="get" action="/churIncomeTypeAction.html" >


		<div id="churIncomeTypeForm_incomeType_field">
	    <div id="churIncomeTypeForm_incomeType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="incomeType" value="<%=WebUtil.display(_ChurIncomeType.getIncomeType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.churIncomeTypeFormEditField_IncomeType_<%=_ChurIncomeType.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncomeType.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_income_type_home">
	</form>
    
    
    </td>

    <td>
    <form name="churIncomeTypeForm<%=_ChurIncomeType.getId()%>2" method="get" action="/v_chur_income_type_form.html" >
        <a href="javascript:document.churIncomeTypeForm<%=_ChurIncomeType.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurIncomeType.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="chur_income_type_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_chur_income_type_form('<%=_ChurIncomeType.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/churIncomeTypeAction.html?del=true&id=<%=_ChurIncomeType.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_chur_income_type('<%=_ChurIncomeType.getId()%>');">DeleteWConfirm</a>
    </td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">

	function edit_chur_income_type_form(target){
		location.href='/v_chur_income_type_form.html?cmd=edit&prv_returnPage=chur_income_type_home&id=' + target;
	}

	function confirm_chur_income_type(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_chur_income_type(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/churIncomeTypeAction.html?del=true&id="+target;
				}
			}
		});
	}
</script>