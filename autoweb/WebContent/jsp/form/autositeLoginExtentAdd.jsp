<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    AutositeLoginExtent _AutositeLoginExtentDefault = new AutositeLoginExtent();// AutositeLoginExtentDS.getInstance().getDeafult();
    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="autositeLoginExtentForm" method="post" action="/autositeLoginExtentAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.autositeLoginExtentForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = AutositeLoginExtentDS.getInstance().getAll();
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        AutositeLoginExtent _AutositeLoginExtent = (AutositeLoginExtent) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _AutositeLoginExtent.getId() %> </td>



<td>
<form name="autositeLoginExtentForm<%=_AutositeLoginExtent.getId()%>" method="post" action="/v_${useDbTable}_edit.html" >
    <a href="javascript:document.autositeLoginExtentForm<%=_AutositeLoginExtent.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _AutositeLoginExtent.getId() %>">
</form>
</td>
<td>
<a href="/autositeLoginExtentAction.html?del=true&id=<%=_AutositeLoginExtent.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>