<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ContentFeedRel _ContentFeedRelDefault = new ContentFeedRel();// ContentFeedRelDS.getInstance().getDeafult();
    
    String _content_feed_idValue= (reqParams.get("contentFeedId")==null?WebUtil.display(_ContentFeedRelDefault.getContentFeedId()):WebUtil.display((String)reqParams.get("contentFeedId")));
    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_ContentFeedRelDefault.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentFeedRelDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="contentFeedRelForm" method="post" action="/contentFeedRelAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Content Feed Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="contentFeedId" value="<%=WebUtil.display(_content_feed_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Content Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="contentId" value="<%=WebUtil.display(_content_idValue)%>"/></TD>
	    </TR>
	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.contentFeedRelForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ContentFeedRelDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentFeedRel _ContentFeedRel = (ContentFeedRel) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentFeedRel.getId() %> </td>

    <td> <%= WebUtil.display(_ContentFeedRel.getContentFeedId()) %></td>
    <td> <%= WebUtil.display(_ContentFeedRel.getContentId()) %></td>
    <td> <%= WebUtil.display(_ContentFeedRel.getTimeCreated()) %></td>


<td>
<form name="contentFeedRelForm<%=_ContentFeedRel.getId()%>" method="post" action="/v_content_feed_rel_edit.html" >
    <a href="javascript:document.contentFeedRelForm<%=_ContentFeedRel.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedRel.getId() %>">
</form>
</td>
<td>
<a href="/contentFeedRelAction.html?del=true&id=<%=_ContentFeedRel.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>