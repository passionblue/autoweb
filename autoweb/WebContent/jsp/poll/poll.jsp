<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
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
	PollDS ds = PollDS.getInstance();    

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
            <a href="t_poll_add2.html?prv_returnPage=poll_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="pollForm_type_label" style="font-size: normal normal bold 10px verdana;" >Type </div>
    </td> 
    <td> 
	    <div id="pollForm_category_label" style="font-size: normal normal bold 10px verdana;" >Category </div>
    </td> 
    <td> 
	    <div id="pollForm_title_label" style="font-size: normal normal bold 10px verdana;" >Title </div>
    </td> 
    <td> 
	    <div id="pollForm_question_label" style="font-size: normal normal bold 10px verdana;" >Question </div>
    </td> 
    <td> 
	    <div id="pollForm_hide_label" style="font-size: normal normal bold 10px verdana;" >Hide </div>
    </td> 
    <td> 
	    <div id="pollForm_disable_label" style="font-size: normal normal bold 10px verdana;" >Disable </div>
    </td> 
    <td> 
	    <div id="pollForm_allowMultiple_label" style="font-size: normal normal bold 10px verdana;" >Allow Multiple </div>
    </td> 
    <td> 
	    <div id="pollForm_randomAnswer_label" style="font-size: normal normal bold 10px verdana;" >Random Answer </div>
    </td> 
    <td> 
	    <div id="pollForm_hideComments_label" style="font-size: normal normal bold 10px verdana;" >Hide Comments </div>
    </td> 
    <td> 
	    <div id="pollForm_hideResults_label" style="font-size: normal normal bold 10px verdana;" >Hide Results </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        Poll _Poll = (Poll) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _Poll.getId() %> </td>




    <td> 
	<form name="pollFormEditField_Type_<%=_Poll.getId()%>" method="get" action="/pollAction.html" >

		<div id="pollForm_type_field">
	    <div id="pollForm_type_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="type">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _Poll.getType())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollFormEditField_Type_<%=_Poll.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollFormEditField_Category_<%=_Poll.getId()%>" method="get" action="/pollAction.html" >


		<div id="pollForm_category_field">
	    <div id="pollForm_category_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="category" value="<%=WebUtil.display(_Poll.getCategory())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollFormEditField_Category_<%=_Poll.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollFormEditField_Title_<%=_Poll.getId()%>" method="get" action="/pollAction.html" >


		<div id="pollForm_title_field">
	    <div id="pollForm_title_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="title" value="<%=WebUtil.display(_Poll.getTitle())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollFormEditField_Title_<%=_Poll.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollFormEditField_Question_<%=_Poll.getId()%>" method="get" action="/pollAction.html" >


		<div id="pollForm_question_field">
	    <div id="pollForm_question_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="question" value="<%=WebUtil.display(_Poll.getQuestion())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollFormEditField_Question_<%=_Poll.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollFormEditField_Hide_<%=_Poll.getId()%>" method="get" action="/pollAction.html" >


		<div id="pollForm_hide_field">
	    <div id="pollForm_hide_dropdown" class="formFieldDropDown" >       
	        <select name="hide">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Poll.getHide())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Poll.getHide())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollFormEditField_Hide_<%=_Poll.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollFormEditField_Disable_<%=_Poll.getId()%>" method="get" action="/pollAction.html" >


		<div id="pollForm_disable_field">
	    <div id="pollForm_disable_dropdown" class="formFieldDropDown" >       
	        <select name="disable">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Poll.getDisable())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Poll.getDisable())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollFormEditField_Disable_<%=_Poll.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollFormEditField_AllowMultiple_<%=_Poll.getId()%>" method="get" action="/pollAction.html" >


		<div id="pollForm_allowMultiple_field">
	    <div id="pollForm_allowMultiple_checkbox" class="formFieldCheckbox" >       
	        <input type="checkbox" name="allowMultiple" <%=HtmlUtil.getCheckedBoxValue(_Poll.getAllowMultiple())%> />
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollFormEditField_AllowMultiple_<%=_Poll.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollFormEditField_RandomAnswer_<%=_Poll.getId()%>" method="get" action="/pollAction.html" >


		<div id="pollForm_randomAnswer_field">
	    <div id="pollForm_randomAnswer_checkbox" class="formFieldCheckbox" >       
	        <input type="checkbox" name="randomAnswer" <%=HtmlUtil.getCheckedBoxValue(_Poll.getRandomAnswer())%> />
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollFormEditField_RandomAnswer_<%=_Poll.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollFormEditField_HideComments_<%=_Poll.getId()%>" method="get" action="/pollAction.html" >


		<div id="pollForm_hideComments_field">
	    <div id="pollForm_hideComments_checkbox" class="formFieldCheckbox" >       
	        <input type="checkbox" name="hideComments" <%=HtmlUtil.getCheckedBoxValue(_Poll.getHideComments())%> />
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollFormEditField_HideComments_<%=_Poll.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollFormEditField_HideResults_<%=_Poll.getId()%>" method="get" action="/pollAction.html" >


		<div id="pollForm_hideResults_field">
	    <div id="pollForm_hideResults_checkbox" class="formFieldCheckbox" >       
	        <input type="checkbox" name="hideResults" <%=HtmlUtil.getCheckedBoxValue(_Poll.getHideResults())%> />
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollFormEditField_HideResults_<%=_Poll.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_home">
	</form>
    
    
    </td>




    <td>
    <form name="pollForm<%=_Poll.getId()%>" method="get" action="/v_poll_edit.html" >
        <a href="javascript:document.pollForm<%=_Poll.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Poll.getId() %>">
    </form>
    <form name="pollForm<%=_Poll.getId()%>2" method="get" action="/v_poll_edit2.html" >
        <a href="javascript:document.pollForm<%=_Poll.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Poll.getId() %>">
    </form>
    </td>

    <td>
    <a href="/pollAction.html?del=true&id=<%=_Poll.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>