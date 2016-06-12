<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    StyleSet _StyleSetDefault = new StyleSet();// StyleSetDS.getInstance().getDeafult();
    
    String _nameValue= (reqParams.get("name")==null?WebUtil.display(_StyleSetDefault.getName()):WebUtil.display((String)reqParams.get("name")));
    String _style_idValue= (reqParams.get("styleId")==null?WebUtil.display(_StyleSetDefault.getStyleId()):WebUtil.display((String)reqParams.get("styleId")));
    String _data_style_idValue= (reqParams.get("dataStyleId")==null?WebUtil.display(_StyleSetDefault.getDataStyleId()):WebUtil.display((String)reqParams.get("dataStyleId")));
    String _link_style_idValue= (reqParams.get("linkStyleId")==null?WebUtil.display(_StyleSetDefault.getLinkStyleId()):WebUtil.display((String)reqParams.get("linkStyleId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_StyleSetDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_StyleSetDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="styleSetForm" method="post" action="/styleSetAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Name</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="name" value="<%=WebUtil.display(_nameValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Style Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="styleId" value="<%=WebUtil.display(_style_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Data Style Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="dataStyleId" value="<%=WebUtil.display(_data_style_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Link Style Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="linkStyleId" value="<%=WebUtil.display(_link_style_idValue)%>"/></TD>
	    </TR>
	            	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.styleSetForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = StyleSetDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleSet _StyleSet = (StyleSet) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _StyleSet.getId() %> </td>

    <td> <%= WebUtil.display(_StyleSet.getName()) %></td>
    <td> <%= WebUtil.display(_StyleSet.getStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleSet.getDataStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleSet.getLinkStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleSet.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_StyleSet.getTimeUpdated()) %></td>


<td>
<form name="styleSetForm<%=_StyleSet.getId()%>" method="post" action="/v_style_set_edit.html" >
    <a href="javascript:document.styleSetForm<%=_StyleSet.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _StyleSet.getId() %>">
</form>
</td>
<td>
<a href="/styleSetAction.html?del=true&id=<%=_StyleSet.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>