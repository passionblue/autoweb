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
	ContentDataDS ds = ContentDataDS.getInstance();    

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
            <a href="v_content_data_form.html?prv_returnPage=content_data_home"> Add New </a> |
            <a href="v_content_data_list.html?"> ListPage </a> |
            <a href="v_content_data_ajax.html?"> Ajax Sampler </a> |
			<a href="/contentDataAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/contentDataAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |
			<a href="/contentDataAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Ajax List</a> |
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="contentDataForm_contentId_label" style="font-size: normal normal bold 10px verdana;" >Content Id </div>
    </td> 
    <td> 
	    <div id="contentDataForm_data_label" style="font-size: normal normal bold 10px verdana;" >Data </div>
    </td> 
    <td> 
	    <div id="contentDataForm_option1_label" style="font-size: normal normal bold 10px verdana;" >Option1 </div>
    </td> 
    <td> 
	    <div id="contentDataForm_option2_label" style="font-size: normal normal bold 10px verdana;" >Option2 </div>
    </td> 
    <td> 
	    <div id="contentDataForm_option3_label" style="font-size: normal normal bold 10px verdana;" >Option3 </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentData _ContentData = (ContentData) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentData.getId() %> </td>


    <td> 
	<form name="contentDataFormEditField_ContentId_<%=_ContentData.getId()%>" method="get" action="/contentDataAction.html" >


		<div id="contentDataForm_contentId_field">
	    <div id="contentDataForm_contentId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentId" value="<%=WebUtil.display(_ContentData.getContentId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentDataFormEditField_ContentId_<%=_ContentData.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentData.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_data_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentDataFormEditField_Data_<%=_ContentData.getId()%>" method="get" action="/contentDataAction.html" >


		<div id="contentDataForm_data_field">
	    <div id="contentDataForm_data_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="data" value="<%=WebUtil.display(_ContentData.getData())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentDataFormEditField_Data_<%=_ContentData.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentData.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_data_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentDataFormEditField_Option1_<%=_ContentData.getId()%>" method="get" action="/contentDataAction.html" >


		<div id="contentDataForm_option1_field">
	    <div id="contentDataForm_option1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="option1" value="<%=WebUtil.display(_ContentData.getOption1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentDataFormEditField_Option1_<%=_ContentData.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentData.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_data_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentDataFormEditField_Option2_<%=_ContentData.getId()%>" method="get" action="/contentDataAction.html" >


		<div id="contentDataForm_option2_field">
	    <div id="contentDataForm_option2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="option2" value="<%=WebUtil.display(_ContentData.getOption2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentDataFormEditField_Option2_<%=_ContentData.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentData.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_data_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentDataFormEditField_Option3_<%=_ContentData.getId()%>" method="get" action="/contentDataAction.html" >


		<div id="contentDataForm_option3_field">
	    <div id="contentDataForm_option3_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="option3" value="<%=WebUtil.display(_ContentData.getOption3())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentDataFormEditField_Option3_<%=_ContentData.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentData.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_data_home">
	</form>
    
    
    </td>



    <td>
    <form name="contentDataForm<%=_ContentData.getId()%>2" method="get" action="/v_content_data_form.html" >
        <a href="javascript:document.contentDataForm<%=_ContentData.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentData.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="content_data_home">
    </form>
    </td>

    <td>
    <a href="/contentDataAction.html?del=true&id=<%=_ContentData.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>