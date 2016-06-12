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
    <td> 
	    <div id="sweepRegisterForm_timeCreated_label" style="font-size: normal normal bold 10px verdana;" >Time Created </div>
    </td> 
</TR>
<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepRegister _SweepRegister = (SweepRegister) iter.next();
%>

<TR bgcolor="#ffffff" valign="top">
    <td> <%= _SweepRegister.getId() %> </td>
    
	<td> <%= _SweepRegister.getEmail()  %> </td>
	<td> <%= _SweepRegister.getPassword()  %> </td>
	<td> <%= _SweepRegister.getSex()  %> </td>
	<td> <%= _SweepRegister.getAgeRange()  %> </td>
	<td> <%= _SweepRegister.getAgreeTerms()  %> </td>
	<td> <%= _SweepRegister.getTimeCreated()  %> </td>
</TR>

<%
    }
%>
</TABLE>