<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SweepRegister _SweepRegisterDefault = new SweepRegister();// SweepRegisterDS.getInstance().getDeafult();
    
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_SweepRegisterDefault.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _passwordValue= (reqParams.get("password")==null?WebUtil.display(_SweepRegisterDefault.getPassword()):WebUtil.display((String)reqParams.get("password")));
    String _sexValue= (reqParams.get("sex")==null?WebUtil.display(_SweepRegisterDefault.getSex()):WebUtil.display((String)reqParams.get("sex")));
    String _age_rangeValue= (reqParams.get("ageRange")==null?WebUtil.display(_SweepRegisterDefault.getAgeRange()):WebUtil.display((String)reqParams.get("ageRange")));
    String _agree_termsValue= (reqParams.get("agreeTerms")==null?WebUtil.display(_SweepRegisterDefault.getAgreeTerms()):WebUtil.display((String)reqParams.get("agreeTerms")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SweepRegisterDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="sweepRegisterForm" method="post" action="/sweepRegisterAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Email</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="email" value="<%=WebUtil.display(_emailValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Password</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="password" value="<%=WebUtil.display(_passwordValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Gender</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="sex" value="<%=WebUtil.display(_sexValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Age Range</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="ageRange" value="<%=WebUtil.display(_age_rangeValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Agree Terms</b> &nbsp;</TD>
        <TD>&nbsp; <input type="checkbox" name="agreeTerms" <%=HtmlUtil.getCheckedBoxValue(_agree_termsValue)%> />
        </TD>
    </TR>
        
        	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.sweepRegisterForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = SweepRegisterDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepRegister _SweepRegister = (SweepRegister) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SweepRegister.getId() %> </td>

    <td> <%= WebUtil.display(_SweepRegister.getEmail()) %></td>
    <td> <%= WebUtil.display(_SweepRegister.getPassword()) %></td>
    <td> <%= WebUtil.display(_SweepRegister.getSex()) %></td>
    <td> <%= WebUtil.display(_SweepRegister.getAgeRange()) %></td>
    <td> <%= WebUtil.display(_SweepRegister.getAgreeTerms()) %></td>
    <td> <%= WebUtil.display(_SweepRegister.getTimeCreated()) %></td>


<td>
<form name="sweepRegisterForm<%=_SweepRegister.getId()%>" method="post" action="/v_sweep_register_edit.html" >
    <a href="javascript:document.sweepRegisterForm<%=_SweepRegister.getId()%>.submit();">Edit</a>           
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