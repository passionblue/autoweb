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
	TestDataDepotDS ds = TestDataDepotDS.getInstance();    

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
            <a href="v_test_data_depot_form.html?prv_returnPage=test_data_depot_home"> Add New </a> |
            <a href="v_test_data_depot_list.html?"> ListPage </a> |
            <a href="v_test_data_depot_ajax.html?"> Ajax Sampler </a> |
			<a href="/testDataDepotAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/testDataDepotAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |
			<a href="/testDataDepotAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Ajax List</a> |
			<a href="/testDataDepotAction.html?ajaxRequest=true&ajaxOut=gethtml&ajaxArg=9&fieldlist=data" rel="facebox">Get Single Data</a> |
			<a href="/testDataDepotAction.html?ajaxRequest=true&ajaxOut=getlistdata&fieldlist=data&listClass=testDataDepot-ajax-frame" rel="facebox">Get Single Data</a> |
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="testDataDepotForm_title_label" style="font-size: normal normal bold 10px verdana;" >Title </div>
    </td> 
    <td> 
	    <div id="testDataDepotForm_data_label" style="font-size: normal normal bold 10px verdana;" >Data </div>
    </td> 
    <td> 
	    <div id="testDataDepotForm_type_label" style="font-size: normal normal bold 10px verdana;" >Type </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        TestDataDepot _TestDataDepot = (TestDataDepot) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _TestDataDepot.getId() %> </td>


    <td> 
	<form name="testDataDepotFormEditField_Title_<%=_TestDataDepot.getId()%>" method="get" action="/testDataDepotAction.html" >


		<div id="testDataDepotForm_title_field">
	    <div id="testDataDepotForm_title_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="title" value="<%=WebUtil.display(_TestDataDepot.getTitle())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.testDataDepotFormEditField_Title_<%=_TestDataDepot.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_TestDataDepot.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="test_data_depot_home">
	</form>
    
    
    </td>

    <td> 
	<form name="testDataDepotFormEditField_Data_<%=_TestDataDepot.getId()%>" method="get" action="/testDataDepotAction.html" >


		<div id="testDataDepotForm_data_field">
	    <div id="testDataDepotForm_data_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="data" value="<%=WebUtil.display(_TestDataDepot.getData())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.testDataDepotFormEditField_Data_<%=_TestDataDepot.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_TestDataDepot.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="test_data_depot_home">
	</form>
    
    
    </td>

    <td> 
	<form name="testDataDepotFormEditField_Type_<%=_TestDataDepot.getId()%>" method="get" action="/testDataDepotAction.html" >

		<div id="testDataDepotForm_type_field">
	    <div id="testDataDepotForm_type_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="type">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _TestDataDepot.getType())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.testDataDepotFormEditField_Type_<%=_TestDataDepot.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_TestDataDepot.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="test_data_depot_home">
	</form>
    
    
    </td>



    <td>
    <form name="testDataDepotForm<%=_TestDataDepot.getId()%>2" method="get" action="/v_test_data_depot_form.html" >
        <a href="javascript:document.testDataDepotForm<%=_TestDataDepot.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _TestDataDepot.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="test_data_depot_home">
    </form>
    </td>

    <td>
    <a href="/testDataDepotAction.html?del=true&id=<%=_TestDataDepot.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>