<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	SweepRegisterDS ds = SweepRegisterDS.getInstance();    
    list = ds.getBySiteId(site.getId());

%> 

<!-- =================== PAGING =================== -->
<%
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), 20);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_sweep_register_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_sweep_register_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_sweep_register_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_sweep_register_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
	}

	List pageList = new ArrayList();
	for(int i = pagingInfo.getBeginIdx() ; i < pagingInfo.getEndIdx();i++){
		pageList.add(list.get(i));
	}
	list = pageList;
%>
	<%= prevLinkStr %>
<%
	for(int p = 0 ; p< pageIndexShortcut.length; p++){
%>
	<%=pageIndexShortcut[p] + (p>=pageIndexShortcut.length-1?"":"/")%>
<%
	}
%>	
	<%= nextLinkStr %>
<!-- =================== END PAGING =================== -->


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_sweep_register_add2.html?prv_returnPage=sweep_register_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="sweepRegisterForm_email_label" style="font-size: normal normal bold 10px verdana;" >Email </div>
    </td> 
    <td> 
	    <div id="sweepRegisterForm_password_label" style="font-size: normal normal bold 10px verdana;" >Password </div>
    </td> 
    <td> 
	    <div id="sweepRegisterForm_sex_label" style="font-size: normal normal bold 10px verdana;" >Gender </div>
    </td> 
    <td> 
	    <div id="sweepRegisterForm_ageRange_label" style="font-size: normal normal bold 10px verdana;" >Age Range </div>
    </td> 
    <td> 
	    <div id="sweepRegisterForm_agreeTerms_label" style="font-size: normal normal bold 10px verdana;" >Agree Terms </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepRegister _SweepRegister = (SweepRegister) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SweepRegister.getId() %> </td>


    <td> 
	<form name="sweepRegisterFormEditField_Email_<%=_SweepRegister.getId()%>" method="get" action="/sweepRegisterAction.html" >


		<div id="sweepRegisterForm_email_field">
	    <div id="sweepRegisterForm_email_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="email" value="<%=WebUtil.display(_SweepRegister.getEmail())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepRegisterFormEditField_Email_<%=_SweepRegister.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepRegister.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_register_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepRegisterFormEditField_Password_<%=_SweepRegister.getId()%>" method="get" action="/sweepRegisterAction.html" >


		<div id="sweepRegisterForm_password_field">
	    <div id="sweepRegisterForm_password_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="password" value="<%=WebUtil.display(_SweepRegister.getPassword())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepRegisterFormEditField_Password_<%=_SweepRegister.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepRegister.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_register_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepRegisterFormEditField_Sex_<%=_SweepRegister.getId()%>" method="get" action="/sweepRegisterAction.html" >

		<div id="sweepRegisterForm_sex_field">
	    <div id="sweepRegisterForm_sex_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="sex">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _SweepRegister.getSex())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepRegisterFormEditField_Sex_<%=_SweepRegister.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepRegister.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_register_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepRegisterFormEditField_AgeRange_<%=_SweepRegister.getId()%>" method="get" action="/sweepRegisterAction.html" >

		<div id="sweepRegisterForm_ageRange_field">
	    <div id="sweepRegisterForm_ageRange_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="ageRange">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _SweepRegister.getAgeRange())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepRegisterFormEditField_AgeRange_<%=_SweepRegister.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepRegister.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_register_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepRegisterFormEditField_AgreeTerms_<%=_SweepRegister.getId()%>" method="get" action="/sweepRegisterAction.html" >


		<div id="sweepRegisterForm_agreeTerms_field">
	    <div id="sweepRegisterForm_agreeTerms_checkbox" class="formFieldCheckbox" >       
	        <input type="checkbox" name="agreeTerms" <%=HtmlUtil.getCheckedBoxValue(_SweepRegister.getAgreeTerms())%> />
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepRegisterFormEditField_AgreeTerms_<%=_SweepRegister.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepRegister.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_register_home">
	</form>
    
    
    </td>


    <td>
    <form name="sweepRegisterForm<%=_SweepRegister.getId()%>" method="get" action="/v_sweep_register_edit.html" >
        <a href="javascript:document.sweepRegisterForm<%=_SweepRegister.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepRegister.getId() %>">
    </form>
    <form name="sweepRegisterForm<%=_SweepRegister.getId()%>2" method="get" action="/v_sweep_register_edit2.html" >
        <a href="javascript:document.sweepRegisterForm<%=_SweepRegister.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepRegister.getId() %>">
    </form>
    </td>

    <td>
    <a href="/sweepRegisterAction.html?del=true&id=<%=_SweepRegister.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>