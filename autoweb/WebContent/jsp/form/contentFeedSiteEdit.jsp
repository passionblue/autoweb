<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    ContentFeedSite _ContentFeedSite = ContentFeedSiteDS.getInstance().getById(id);

    if ( _ContentFeedSite == null ) {
        return;
    }

    String _content_feed_idValue=  WebUtil.display(_ContentFeedSite.getContentFeedId());
    String _display_typeValue=  WebUtil.display(_ContentFeedSite.getDisplayType());
    String _time_createdValue=  WebUtil.display(_ContentFeedSite.getTimeCreated());
%> 

<br>
<form name="contentFeedSiteFormEdit" method="post" action="/contentFeedSiteAction.html" >
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
            <b><a href="javascript:document.contentFeedSiteFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedSite.getId()%>">
</form>
