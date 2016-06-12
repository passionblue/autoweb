<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*, com.autosite.util.chur.*"%>
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
	ChurIncomeDS ds = ChurIncomeDS.getInstance();    
	String _selectedWeek = ChurWebUtil.getSelectedWeek(request );
   	int _selectedYear = ChurWebUtil.getSelectedYear(request );

	list = ChurReportUtil.getIncomeList(site.getId(),_selectedYear, _selectedWeek );
	SiteDS siteDS = SiteDS.getInstance();
	
	long _selectedMemberId = WebParamUtil.getLongValue(request.getParameter("memberSelect"));
	
%> 

<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

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

<script type="text/javascript">
function selectMemberFilter()
{
	var w = document.getElementById( 'churMemberFilter' ).selectedIndex;
	var url_add =document.getElementById( 'churMemberFilter' ).options[w].value;
	window.location.href = url_add;
}
</script>

<!-- =================== END PAGING =================== -->
        <select class="field" id="churMemberFilter" onchange="selectMemberFilter()" >
        <option value="" >- Please Select Member To Filter -</option>
<%
	List _membersList = ChurMemberDS.getInstance().getBySiteId(site.getId());
	for(Iterator it = _membersList.iterator(); it.hasNext();){
		ChurMember _obj = (ChurMember) it.next();
%>		
            <option value="/v_chur_income_update.html?memberSelect=<%=_obj.getId()%>" ><%=_obj.getFullName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_chur_income_form.html?prv_returnPage=chur_income_update"> Add New </a> |
            <a href="v_chur_income_list.html?"> List Page </a> |
            <a href="v_chur_income_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/churIncomeAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form</a> |
			<a href="/churIncomeAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox"> open form</a> |
			<a href="/churIncomeAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox"> getlisthtml</a> |
			<a href="/churIncomeAction.html?ajaxRequest=true&ajaxOut=getlistjson" rel="facebox"> getlistjson</a> |
			<a href="/churIncomeAction.html?ajaxRequest=true&ajaxOut=getjson&ajaxOutArg=first" rel="facebox">getjson first</a> |
			<a href="/churIncomeAction.html?ajaxRequest=true&ajaxOut=getjson&ajaxOutArg=last" rel="facebox">getjson last</a> |
			<a href="/churIncomeAction.html?ajaxRequest=true&ajaxOut=getlistdata" rel="facebox">getlistdata</a> |
		</TD>        

    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="churIncomeForm_week_label" style="font-size: normal normal bold 10px verdana;" >Week </div>
    </td> 
    <td> 
	    <div id="churIncomeForm_churMemberId_label" style="font-size: normal normal bold 10px verdana;" >Chur Member Id </div>
    </td> 
    <td> 
	    <div id="churIncomeForm_incomeItemId_label" style="font-size: normal normal bold 10px verdana;" >Income Item Id </div>
    </td> 
    <td> 
	    <div id="churIncomeForm_ammount_label" style="font-size: normal normal bold 10px verdana;" >Ammount </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurIncome _ChurIncome = (ChurIncome) iter.next();
		//TODO 
        fieldString += "\"" +  _ChurIncome.getId() + "\",";
		if (_selectedMemberId !=0 && _selectedMemberId != _ChurIncome.getChurMemberId())
		    continue;

%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurIncome.getId() %> </td>


    <td> 
	<form name="churIncomeFormEditField_Week_<%=_ChurIncome.getId()%>" method="get" action="/churIncomeAction.html" >


		<div id="churIncomeForm_week_field">
	    <div id="churIncomeForm_week_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="week" value="<%=WebUtil.display(_ChurIncome.getWeek())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.churIncomeFormEditField_Week_<%=_ChurIncome.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncome.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_income_update">
	</form>
    
    
    </td>

    <td> 
	<form name="churIncomeFormEditField_ChurMemberId_<%=_ChurIncome.getId()%>" method="get" action="/churIncomeAction.html" >


		<div id="churIncomeForm_churMemberId_field">
	    <div id="churIncomeForm_churMemberId_text" class="formFieldText" >       
	        <!-- input id="field" type="text" size="20" name="churMemberId" value="<%=WebUtil.display(_ChurIncome.getChurMemberId())%>"/> <span></span-->
        <select class="field" name="churMemberId" id="churMemberId">
        <option value="" >- Please Select -</option>
<%
	List _listChurMember_churMemberId = ChurMemberDS.getInstance().getBySiteId(site.getId());
	for(Iterator it = _listChurMember_churMemberId.iterator(); it.hasNext();){
		ChurMember _obj = (ChurMember) it.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_ChurIncome.getChurMemberId(), _obj.getId())%> ><%=_obj.getFullName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
	        
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.churIncomeFormEditField_ChurMemberId_<%=_ChurIncome.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncome.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_income_update">
	</form>
    
    
    </td>

    <td> 
	<form name="churIncomeFormEditField_IncomeItemId_<%=_ChurIncome.getId()%>" method="get" action="/churIncomeAction.html" >


		<div id="churIncomeForm_incomeItemId_field">
	    <div id="churIncomeForm_incomeItemId_text" class="formFieldText" >       
	        <!-- input id="field" type="text" size="20" name="incomeItemId" value="<%=WebUtil.display(_ChurIncome.getIncomeItemId())%>"/> <span></span-->
        <select class="field" name="incomeItemId" id="incomeItemId">
        <option value="" >- Please Select -</option>
<%
	List _listChurIncomeItem_incomeItemId = ChurIncomeItemDS.getInstance().getBySiteId(site.getId());
	for(Iterator it = _listChurIncomeItem_incomeItemId.iterator(); it.hasNext();){
		ChurIncomeItem _obj = (ChurIncomeItem) it.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_ChurIncome.getIncomeItemId(), _obj.getId())%> ><%=_obj.getDisplay() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
	        
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.churIncomeFormEditField_IncomeItemId_<%=_ChurIncome.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncome.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_income_update">
	</form>
    
    
    </td>

    <td> 
	<form name="churIncomeFormEditField_Ammount_<%=_ChurIncome.getId()%>" method="get" action="/churIncomeAction.html" >


		<div id="churIncomeForm_ammount_field">
	    <div id="churIncomeForm_ammount_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="ammount" value="<%=WebUtil.display(_ChurIncome.getAmmount())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.churIncomeFormEditField_Ammount_<%=_ChurIncome.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncome.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="chur_income_update">
	</form>
    
    
    </td>


    <td>
    <form name="churIncomeForm<%=_ChurIncome.getId()%>2" method="get" action="/v_chur_income_form.html" >
        <a href="javascript:document.churIncomeForm<%=_ChurIncome.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurIncome.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="chur_income_update">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_chur_income_form('<%=_ChurIncome.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/churIncomeAction.html?del=true&id=<%=_ChurIncome.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_chur_income('<%=_ChurIncome.getId()%>');">DeleteWConfirm</a>
    </td>
</TR>

<%
    }
	if ( fieldString != null && fieldString.length() > 0 )
	fieldString = fieldString.substring(0, fieldString.length()-1);

%>
</TABLE>

<script type="text/javascript">

	function edit_chur_income_form(target){
		location.href='/v_chur_income_form.html?cmd=edit&prv_returnPage=chur_income_update&id=' + target;
	}

	function confirm_chur_income(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_chur_income(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/churIncomeAction.html?del=true&id="+target;
				}
			}
		});
	}
</script>

