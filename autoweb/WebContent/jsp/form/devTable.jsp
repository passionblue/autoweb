<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
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
	DevTableDS ds = DevTableDS.getInstance();    

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


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_dev_table_form.html?prv_returnPage=dev_table_home"> Add New </a> |
            <a href="v_dev_table_list.html?"> ListPage </a> |
            <a href="v_dev_table_ajax.html?"> Ajax Sampler </a> |
			<a href="/devTableAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/devTableAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |
			<a href="/devTableAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Ajax List</a> |
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="devTableForm_devNoteId_label" style="font-size: normal normal bold 10px verdana;" >Dev Note Id </div>
    </td> 
    <td> 
	    <div id="devTableForm_title_label" style="font-size: normal normal bold 10px verdana;" >Title </div>
    </td> 
    <td> 
	    <div id="devTableForm_subject_label" style="font-size: normal normal bold 10px verdana;" >Subject </div>
    </td> 
    <td> 
	    <div id="devTableForm_data_label" style="font-size: normal normal bold 10px verdana;" >Data </div>
    </td> 
    <td> 
	    <div id="devTableForm_type_label" style="font-size: normal normal bold 10px verdana;" >Type </div>
    </td> 
    <td> 
	    <div id="devTableForm_disable_label" style="font-size: normal normal bold 10px verdana;" >Disable </div>
    </td> 
    <td> 
	    <div id="devTableForm_radioValue_label" style="font-size: normal normal bold 10px verdana;" >Radio Value </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        DevTable _DevTable = (DevTable) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _DevTable.getId() %> </td>


    <td> 
	<form name="devTableFormEditField_DevNoteId_<%=_DevTable.getId()%>" method="get" action="/devTableAction.html" >


		<div id="devTableForm_devNoteId_field">
	    <div id="devTableForm_devNoteId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="devNoteId" value="<%=WebUtil.display(_DevTable.getDevNoteId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.devTableFormEditField_DevNoteId_<%=_DevTable.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_DevTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="dev_table_home">
	</form>
    
    
    </td>

    <td> 
	<form name="devTableFormEditField_Title_<%=_DevTable.getId()%>" method="get" action="/devTableAction.html" >


		<div id="devTableForm_title_field">
	    <div id="devTableForm_title_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="title" value="<%=WebUtil.display(_DevTable.getTitle())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.devTableFormEditField_Title_<%=_DevTable.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_DevTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="dev_table_home">
	</form>
    
    
    </td>

    <td> 
	<form name="devTableFormEditField_Subject_<%=_DevTable.getId()%>" method="get" action="/devTableAction.html" >


		<div id="devTableForm_subject_field">
	    <div id="devTableForm_subject_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="subject" value="<%=WebUtil.display(_DevTable.getSubject())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.devTableFormEditField_Subject_<%=_DevTable.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_DevTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="dev_table_home">
	</form>
    
    
    </td>

    <td> 
	<form name="devTableFormEditField_Data_<%=_DevTable.getId()%>" method="get" action="/devTableAction.html" >


		<div id="devTableForm_data_field">
	    <div id="devTableForm_data_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="data" value="<%=WebUtil.display(_DevTable.getData())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.devTableFormEditField_Data_<%=_DevTable.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_DevTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="dev_table_home">
	</form>
    
    
    </td>

    <td> 
	<form name="devTableFormEditField_Type_<%=_DevTable.getId()%>" method="get" action="/devTableAction.html" >

		<div id="devTableForm_type_field">
	    <div id="devTableForm_type_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="type">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _DevTable.getType())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.devTableFormEditField_Type_<%=_DevTable.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_DevTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="dev_table_home">
	</form>
    
    
    </td>

    <td> 
	<form name="devTableFormEditField_Disable_<%=_DevTable.getId()%>" method="get" action="/devTableAction.html" >


		<div id="devTableForm_disable_field">
	    <div id="devTableForm_disable_dropdown" class="formFieldDropDown" >       
	        <select name="disable">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _DevTable.getDisable())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _DevTable.getDisable())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.devTableFormEditField_Disable_<%=_DevTable.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_DevTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="dev_table_home">
	</form>
    
    
    </td>

    <td> 
	<form name="devTableFormEditField_RadioValue_<%=_DevTable.getId()%>" method="get" action="/devTableAction.html" >


		<div id="devTableForm_radioValue_field">
	    <div id="devTableForm_radioValue_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="radioValue" value="<%=WebUtil.display(_DevTable.getRadioValue())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.devTableFormEditField_RadioValue_<%=_DevTable.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_DevTable.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="dev_table_home">
	</form>
    
    
    </td>



    <td>
    <form name="devTableForm<%=_DevTable.getId()%>2" method="get" action="/v_dev_table_form.html" >
        <a href="javascript:document.devTableForm<%=_DevTable.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _DevTable.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="dev_table_home">
    </form>
    </td>

    <td>
    <a href="/devTableAction.html?del=true&id=<%=_DevTable.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>