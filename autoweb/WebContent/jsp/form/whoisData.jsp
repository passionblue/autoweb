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
	WhoisDataDS ds = WhoisDataDS.getInstance();    

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
            <a href="v_whois_data_form.html?prv_returnPage=whois_data_home"> Add New </a> |
            <a href="v_whois_data_list.html?"> ListPage </a> |
            <a href="v_whois_data_ajax.html?"> Ajax Sampler </a> |
			<a href="/whoisDataAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/whoisDataAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |
			<a href="/whoisDataAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Ajax List</a> |
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="whoisDataForm_ip_label" style="font-size: normal normal bold 10px verdana;" >Ip </div>
    </td> 
    <td> 
	    <div id="whoisDataForm_whoisData_label" style="font-size: normal normal bold 10px verdana;" >Whois Data </div>
    </td> 
    <td> 
	    <div id="whoisDataForm_server_label" style="font-size: normal normal bold 10px verdana;" >Server </div>
    </td> 
    <td> 
	    <div id="whoisDataForm_forceRequest_label" style="font-size: normal normal bold 10px verdana;" >Force Request </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        WhoisData _WhoisData = (WhoisData) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _WhoisData.getId() %> </td>


    <td> 
	<form name="whoisDataFormEditField_Ip_<%=_WhoisData.getId()%>" method="get" action="/whoisDataAction.html" >


		<div id="whoisDataForm_ip_field">
	    <div id="whoisDataForm_ip_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="ip" value="<%=WebUtil.display(_WhoisData.getIp())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.whoisDataFormEditField_Ip_<%=_WhoisData.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_WhoisData.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="whois_data_home">
	</form>
    
    
    </td>

    <td> 
	<form name="whoisDataFormEditField_WhoisData_<%=_WhoisData.getId()%>" method="get" action="/whoisDataAction.html" >


		<div id="whoisDataForm_whoisData_field">
	    <div id="whoisDataForm_whoisData_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="whoisData" value="<%=WebUtil.display(_WhoisData.getWhoisData())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.whoisDataFormEditField_WhoisData_<%=_WhoisData.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_WhoisData.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="whois_data_home">
	</form>
    
    
    </td>

    <td> 
	<form name="whoisDataFormEditField_Server_<%=_WhoisData.getId()%>" method="get" action="/whoisDataAction.html" >


		<div id="whoisDataForm_server_field">
	    <div id="whoisDataForm_server_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="server" value="<%=WebUtil.display(_WhoisData.getServer())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.whoisDataFormEditField_Server_<%=_WhoisData.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_WhoisData.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="whois_data_home">
	</form>
    
    
    </td>

    <td> 
	<form name="whoisDataFormEditField_ForceRequest_<%=_WhoisData.getId()%>" method="get" action="/whoisDataAction.html" >


		<div id="whoisDataForm_forceRequest_field">
	    <div id="whoisDataForm_forceRequest_dropdown" class="formFieldDropDown" >       
	        <select name="forceRequest">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _WhoisData.getForceRequest())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _WhoisData.getForceRequest())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.whoisDataFormEditField_ForceRequest_<%=_WhoisData.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_WhoisData.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="whois_data_home">
	</form>
    
    
    </td>



    <td>
    <form name="whoisDataForm<%=_WhoisData.getId()%>2" method="get" action="/v_whois_data_form.html" >
        <a href="javascript:document.whoisDataForm<%=_WhoisData.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _WhoisData.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="whois_data_home">
    </form>
    </td>

    <td>
    <a href="/whoisDataAction.html?del=true&id=<%=_WhoisData.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>