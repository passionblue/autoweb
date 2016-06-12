<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    PageStyle _PageStyleDefault = new PageStyle();// PageStyleDS.getInstance().getDeafult();
    
    String _page_idValue= (reqParams.get("pageId")==null?WebUtil.display(_PageStyleDefault.getPageId()):WebUtil.display((String)reqParams.get("pageId")));
    String _style_idValue= (reqParams.get("styleId")==null?WebUtil.display(_PageStyleDefault.getStyleId()):WebUtil.display((String)reqParams.get("styleId")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="pageStyleForm" method="post" action="/pageStyleAction.html" >
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
            <b><a href="javascript:document.pageStyleForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PageStyleDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageStyle _PageStyle = (PageStyle) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PageStyle.getId() %> </td>

    <td> <%= WebUtil.display(_PageStyle.getPageId()) %></td>
    <td> <%= WebUtil.display(_PageStyle.getStyleId()) %></td>


<td>
<form name="pageStyleForm<%=_PageStyle.getId()%>" method="post" action="/v_page_style_edit.html" >
    <a href="javascript:document.pageStyleForm<%=_PageStyle.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageStyle.getId() %>">
</form>
</td>
<td>
<a href="/pageStyleAction.html?del=true&id=<%=_PageStyle.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>