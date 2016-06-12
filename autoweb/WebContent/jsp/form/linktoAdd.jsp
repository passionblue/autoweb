<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    Linkto _LinktoDefault = new Linkto();// LinktoDS.getInstance().getDeafult();
    
    String _link_keyValue= (reqParams.get("linkKey")==null?WebUtil.display(_LinktoDefault.getLinkKey()):WebUtil.display((String)reqParams.get("linkKey")));
    String _link_targetValue= (reqParams.get("linkTarget")==null?WebUtil.display(_LinktoDefault.getLinkTarget()):WebUtil.display((String)reqParams.get("linkTarget")));
    String _disableValue= (reqParams.get("disable")==null?WebUtil.display(_LinktoDefault.getDisable()):WebUtil.display((String)reqParams.get("disable")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_LinktoDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="linktoForm" method="post" action="/linktoAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Link Key</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="linkKey" value="<%=WebUtil.display(_link_keyValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Link Target</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="linkTarget" value="<%=WebUtil.display(_link_targetValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Disable</b> &nbsp;</TD>
        <TD>&nbsp;<select name="disable">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.linktoForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = LinktoDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        Linkto _Linkto = (Linkto) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _Linkto.getId() %> </td>

    <td> <%= WebUtil.display(_Linkto.getLinkKey()) %></td>
    <td> <%= WebUtil.display(_Linkto.getLinkTarget()) %></td>
    <td> <%= WebUtil.display(_Linkto.getDisable()) %></td>
    <td> <%= WebUtil.display(_Linkto.getTimeCreated()) %></td>


<td>
<form name="linktoForm<%=_Linkto.getId()%>" method="post" action="/v_linkto_edit.html" >
    <a href="javascript:document.linktoForm<%=_Linkto.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Linkto.getId() %>">
</form>
</td>
<td>
<a href="/linktoAction.html?del=true&id=<%=_Linkto.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>