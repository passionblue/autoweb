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
	FormFieldDS ds = FormFieldDS.getInstance();    

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
            <a href="v_form_field_form.html?prv_returnPage=form_field_home"> Add New </a> |
            <a href="v_form_field_list.html?"> ListPage </a> |
            <a href="v_form_field_ajax.html?"> Ajax Sampler </a> |
			<a href="/formFieldAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/formFieldAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |
			<a href="/formFieldAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Ajax List</a> |
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="formFieldForm_formId_label" style="font-size: normal normal bold 10px verdana;" >Form Id </div>
    </td> 
    <td> 
	    <div id="formFieldForm_fieldText_label" style="font-size: normal normal bold 10px verdana;" >Field Text </div>
    </td> 
    <td> 
	    <div id="formFieldForm_fieldType_label" style="font-size: normal normal bold 10px verdana;" >Field Type </div>
    </td> 
    <td> 
	    <div id="formFieldForm_required_label" style="font-size: normal normal bold 10px verdana;" >Required </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        FormField _FormField = (FormField) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _FormField.getId() %> </td>



    <td> 
	<form name="formFieldFormEditField_FormId_<%=_FormField.getId()%>" method="get" action="/formFieldAction.html" >


		<div id="formFieldForm_formId_field">
	    <div id="formFieldForm_formId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="formId" value="<%=WebUtil.display(_FormField.getFormId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.formFieldFormEditField_FormId_<%=_FormField.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_FormField.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="form_field_home">
	</form>
    
    
    </td>

    <td> 
	<form name="formFieldFormEditField_FieldText_<%=_FormField.getId()%>" method="get" action="/formFieldAction.html" >


		<div id="formFieldForm_fieldText_field">
	    <div id="formFieldForm_fieldText_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="fieldText" value="<%=WebUtil.display(_FormField.getFieldText())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.formFieldFormEditField_FieldText_<%=_FormField.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_FormField.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="form_field_home">
	</form>
    
    
    </td>

    <td> 
	<form name="formFieldFormEditField_FieldType_<%=_FormField.getId()%>" method="get" action="/formFieldAction.html" >

		<div id="formFieldForm_fieldType_field">
	    <div id="formFieldForm_fieldType_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="fieldType">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _FormField.getFieldType())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.formFieldFormEditField_FieldType_<%=_FormField.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_FormField.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="form_field_home">
	</form>
    
    
    </td>

    <td> 
	<form name="formFieldFormEditField_Required_<%=_FormField.getId()%>" method="get" action="/formFieldAction.html" >


		<div id="formFieldForm_required_field">
	    <div id="formFieldForm_required_dropdown" class="formFieldDropDown" >       
	        <select name="required">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _FormField.getRequired())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _FormField.getRequired())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.formFieldFormEditField_Required_<%=_FormField.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_FormField.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="form_field_home">
	</form>
    
    
    </td>


    <td>
    <form name="formFieldForm<%=_FormField.getId()%>2" method="get" action="/v_form_field_form.html" >
        <a href="javascript:document.formFieldForm<%=_FormField.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _FormField.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="form_field_home">
    </form>
    </td>

    <td>
    <a href="/formFieldAction.html?del=true&id=<%=_FormField.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>