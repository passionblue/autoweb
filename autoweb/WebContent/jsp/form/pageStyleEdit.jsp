<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    PageStyle _PageStyle = PageStyleDS.getInstance().getById(id);

    if ( _PageStyle == null ) {
        return;
    }

    String _page_idValue=  WebUtil.display(_PageStyle.getPageId());
    String _style_idValue=  WebUtil.display(_PageStyle.getStyleId());
%> 

<br>
<form name="pageStyleFormEdit" method="post" action="/pageStyleAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Page Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="pageId" value="<%=WebUtil.display(_page_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Style Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="styleId" value="<%=WebUtil.display(_style_idValue)%>"/></TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.pageStyleFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStyle.getId()%>">
</form>
