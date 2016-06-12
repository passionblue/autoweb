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
	BlockListDS ds = BlockListDS.getInstance();    

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
        	PAGES |
            <a href="v_block_list_form.html?prv_returnPage=block_list_home"> Add New </a> |
            <a href="v_block_list_list.html?"> ListPage </a> |
            <a href="v_block_list_ajax.html?"> Ajax Sampler </a> |
        </TD> 
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/blockListAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form</a> |
			<a href="/blockListAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox"> open form</a> |
			<a href="/blockListAction.html?ajaxRequest=true&ajaxOut=getmodalform2" rel="facebox"> open form</a> |
			<a href="/blockListAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox"> getlisthtml</a> |
			<a href="/blockListAction.html?ajaxRequest=true&ajaxOut=getlistjson" rel="facebox"> getlistjson</a> |
			<a href="/blockListAction.html?ajaxRequest=true&ajaxOut=getjson&ajaxOutArg=first" rel="facebox">getjson first</a> |
			<a href="/blockListAction.html?ajaxRequest=true&ajaxOut=getjson&ajaxOutArg=last" rel="facebox">getjson last</a> |
			<a href="/blockListAction.html?ajaxRequest=true&ajaxOut=getlistdata" rel="facebox">getlistdata</a> |
		</TD>        
    </TR>
</TABLE>

<div>

<h3> form displayed by script </h3>
<script type="text/javascript" src="/blockListAction.html?ajaxRequest=true&ajaxOut=getscriptform"></script>

</div>

<br/>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="blockListForm_ipData_label" style="font-size: normal normal bold 10px verdana;" >Ip Data </div>
    </td> 
    <td> 
	    <div id="blockListForm_rangeCheck_label" style="font-size: normal normal bold 10px verdana;" >Range Check </div>
    </td> 
    <td> 
	    <div id="blockListForm_reasonCode_label" style="font-size: normal normal bold 10px verdana;" >Reason Code </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlockList _BlockList = (BlockList) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlockList.getId() %> </td>


    <td> 
	<form name="blockListFormEditField_IpData_<%=_BlockList.getId()%>" method="get" action="/blockListAction.html" >


		<div id="blockListForm_ipData_field">
	    <div id="blockListForm_ipData_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="ipData" value="<%=WebUtil.display(_BlockList.getIpData())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blockListFormEditField_IpData_<%=_BlockList.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlockList.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="block_list_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blockListFormEditField_RangeCheck_<%=_BlockList.getId()%>" method="get" action="/blockListAction.html" >


		<div id="blockListForm_rangeCheck_field">
	    <div id="blockListForm_rangeCheck_dropdown" class="formFieldDropDown" >       
	        <select name="rangeCheck">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _BlockList.getRangeCheck())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _BlockList.getRangeCheck())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blockListFormEditField_RangeCheck_<%=_BlockList.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlockList.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="block_list_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blockListFormEditField_ReasonCode_<%=_BlockList.getId()%>" method="get" action="/blockListAction.html" >

		<div id="blockListForm_reasonCode_field">
	    <div id="blockListForm_reasonCode_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="reasonCode">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _BlockList.getReasonCode())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blockListFormEditField_ReasonCode_<%=_BlockList.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlockList.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="block_list_home">
	</form>
    
    
    </td>


    <td>
    <form name="blockListForm<%=_BlockList.getId()%>2" method="get" action="/v_block_list_form.html" >
        <a href="javascript:document.blockListForm<%=_BlockList.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlockList.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="block_list_home">
    </form>
    </td>

    <td>
    <a href="/blockListAction.html?del=true&id=<%=_BlockList.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>