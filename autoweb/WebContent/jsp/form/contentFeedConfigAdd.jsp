<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ContentFeedConfig _ContentFeedConfigDefault = new ContentFeedConfig();// ContentFeedConfigDS.getInstance().getDeafult();
    
    String _feed_categoryValue= (reqParams.get("feedCategory")==null?WebUtil.display(_ContentFeedConfigDefault.getFeedCategory()):WebUtil.display((String)reqParams.get("feedCategory")));
    String _feed_typeValue= (reqParams.get("feedType")==null?WebUtil.display(_ContentFeedConfigDefault.getFeedType()):WebUtil.display((String)reqParams.get("feedType")));
    String _display_typeValue= (reqParams.get("displayType")==null?WebUtil.display(_ContentFeedConfigDefault.getDisplayType()):WebUtil.display((String)reqParams.get("displayType")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentFeedConfigDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="contentFeedConfigForm" method="post" action="/contentFeedConfigAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Feed Category</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="feedCategory" value="<%=WebUtil.display(_feed_categoryValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Feed Type</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="feedType" value="<%=WebUtil.display(_feed_typeValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Display Type</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="displayType" value="<%=WebUtil.display(_display_typeValue)%>"/></TD>
	    </TR>
	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.contentFeedConfigForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ContentFeedConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentFeedConfig _ContentFeedConfig = (ContentFeedConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentFeedConfig.getId() %> </td>

    <td> <%= WebUtil.display(_ContentFeedConfig.getFeedCategory()) %></td>
    <td> <%= WebUtil.display(_ContentFeedConfig.getFeedType()) %></td>
    <td> <%= WebUtil.display(_ContentFeedConfig.getDisplayType()) %></td>
    <td> <%= WebUtil.display(_ContentFeedConfig.getTimeCreated()) %></td>


<td>
<form name="contentFeedConfigForm<%=_ContentFeedConfig.getId()%>" method="post" action="/v_content_feed_config_edit.html" >
    <a href="javascript:document.contentFeedConfigForm<%=_ContentFeedConfig.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedConfig.getId() %>">
</form>
</td>
<td>
<a href="/contentFeedConfigAction.html?del=true&id=<%=_ContentFeedConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>