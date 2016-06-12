<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    EcDisplayConfig _EcDisplayConfig = EcDisplayConfigDS.getInstance().getById(id);

    if ( _EcDisplayConfig == null ) {
        return;
    }

    String _column_countValue=  WebUtil.display(_EcDisplayConfig.getColumnCount());
%> 

<br>
<form name="ecDisplayConfigFormEdit" method="post" action="/ecDisplayConfigAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Column Count</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="columnCount" value="<%=WebUtil.display(_column_countValue)%>"/></TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecDisplayConfigFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcDisplayConfig.getId()%>">
</form>
