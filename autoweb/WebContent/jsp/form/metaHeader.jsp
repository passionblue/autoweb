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
	MetaHeaderDS ds = MetaHeaderDS.getInstance();    

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
            <a href="v_meta_header_form.html?prv_returnPage=meta_header_home"> Add New </a> |
            <a href="v_meta_header_list.html?"> ListPage </a> |
            <a href="v_meta_header_ajax.html?"> Ajax Sampler </a> |
			<a href="/metaHeaderAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/metaHeaderAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |
			<a href="/metaHeaderAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Ajax List</a> |
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="metaHeaderForm_source_label" style="font-size: normal normal bold 10px verdana;" >Source </div>
    </td> 
    <td> 
	    <div id="metaHeaderForm_detailId_label" style="font-size: normal normal bold 10px verdana;" >Detail Id </div>
    </td> 
    <td> 
	    <div id="metaHeaderForm_name_label" style="font-size: normal normal bold 10px verdana;" >Name </div>
    </td> 
    <td> 
	    <div id="metaHeaderForm_value_label" style="font-size: normal normal bold 10px verdana;" >Value </div>
    </td> 
    <td> 
	    <div id="metaHeaderForm_httpEquiv_label" style="font-size: normal normal bold 10px verdana;" >Http Equiv </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        MetaHeader _MetaHeader = (MetaHeader) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _MetaHeader.getId() %> </td>


    <td> 
	<form name="metaHeaderFormEditField_Source_<%=_MetaHeader.getId()%>" method="get" action="/metaHeaderAction.html" >

		<div id="metaHeaderForm_source_field">
	    <div id="metaHeaderForm_source_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="source">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _MetaHeader.getSource())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.metaHeaderFormEditField_Source_<%=_MetaHeader.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MetaHeader.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="meta_header_home">
	</form>
    
    
    </td>

    <td> 
	<form name="metaHeaderFormEditField_DetailId_<%=_MetaHeader.getId()%>" method="get" action="/metaHeaderAction.html" >


		<div id="metaHeaderForm_detailId_field">
	    <div id="metaHeaderForm_detailId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="detailId" value="<%=WebUtil.display(_MetaHeader.getDetailId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.metaHeaderFormEditField_DetailId_<%=_MetaHeader.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MetaHeader.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="meta_header_home">
	</form>
    
    
    </td>

    <td> 
	<form name="metaHeaderFormEditField_Name_<%=_MetaHeader.getId()%>" method="get" action="/metaHeaderAction.html" >


		<div id="metaHeaderForm_name_field">
	    <div id="metaHeaderForm_name_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="name" value="<%=WebUtil.display(_MetaHeader.getName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.metaHeaderFormEditField_Name_<%=_MetaHeader.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MetaHeader.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="meta_header_home">
	</form>
    
    
    </td>

    <td> 
	<form name="metaHeaderFormEditField_Value_<%=_MetaHeader.getId()%>" method="get" action="/metaHeaderAction.html" >


		<div id="metaHeaderForm_value_field">
	    <div id="metaHeaderForm_value_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="value" value="<%=WebUtil.display(_MetaHeader.getValue())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.metaHeaderFormEditField_Value_<%=_MetaHeader.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MetaHeader.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="meta_header_home">
	</form>
    
    
    </td>

    <td> 
	<form name="metaHeaderFormEditField_HttpEquiv_<%=_MetaHeader.getId()%>" method="get" action="/metaHeaderAction.html" >


		<div id="metaHeaderForm_httpEquiv_field">
	    <div id="metaHeaderForm_httpEquiv_dropdown" class="formFieldDropDown" >       
	        <select name="httpEquiv">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _MetaHeader.getHttpEquiv())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _MetaHeader.getHttpEquiv())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.metaHeaderFormEditField_HttpEquiv_<%=_MetaHeader.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MetaHeader.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="meta_header_home">
	</form>
    
    
    </td>


    <td>
    <form name="metaHeaderForm<%=_MetaHeader.getId()%>2" method="get" action="/v_meta_header_form.html" >
        <a href="javascript:document.metaHeaderForm<%=_MetaHeader.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _MetaHeader.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="meta_header_home">
    </form>
    </td>

    <td>
    <a href="/metaHeaderAction.html?del=true&id=<%=_MetaHeader.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>