<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ContentFeedSite _ContentFeedSiteDefault = new ContentFeedSite();// ContentFeedSiteDS.getInstance().getDeafult();
    
    String _content_feed_idValue= (reqParams.get("contentFeedId")==null?WebUtil.display(_ContentFeedSiteDefault.getContentFeedId()):WebUtil.display((String)reqParams.get("contentFeedId")));
    String _display_typeValue= (reqParams.get("displayType")==null?WebUtil.display(_ContentFeedSiteDefault.getDisplayType()):WebUtil.display((String)reqParams.get("displayType")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentFeedSiteDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="contentFeedSiteForm" method="post" action="/contentFeedSiteAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Content Feed Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="contentFeedId" value="<%=WebUtil.display(_content_feed_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Display Type</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="displayType" value="<%=WebUtil.display(_display_typeValue)%>"/></TD>
	    </TR>
	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.contentFeedSiteForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ContentFeedSiteDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentFeedSite _ContentFeedSite = (ContentFeedSite) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentFeedSite.getId() %> </td>

    <td> <%= WebUtil.display(_ContentFeedSite.getContentFeedId()) %></td>
    <td> <%= WebUtil.display(_ContentFeedSite.getDisplayType()) %></td>
    <td> <%= WebUtil.display(_ContentFeedSite.getTimeCreated()) %></td>


<td>
<form name="contentFeedSiteForm<%=_ContentFeedSite.getId()%>" method="post" action="/v_content_feed_site_edit.html" >
    <a href="javascript:document.contentFeedSiteForm<%=_ContentFeedSite.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedSite.getId() %>">
</form>
</td>
<td>
<a href="/contentFeedSiteAction.html?del=true&id=<%=_ContentFeedSite.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>