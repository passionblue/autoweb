<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    ContentFeedRel _ContentFeedRel = ContentFeedRelDS.getInstance().getById(id);

    if ( _ContentFeedRel == null ) {
        return;
    }

    String _content_feed_idValue=  WebUtil.display(_ContentFeedRel.getContentFeedId());
    String _content_idValue=  WebUtil.display(_ContentFeedRel.getContentId());
    String _time_createdValue=  WebUtil.display(_ContentFeedRel.getTimeCreated());
%> 

<br>
<form name="contentFeedRelFormEdit" method="post" action="/contentFeedRelAction.html" >
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
            <b><a href="javascript:document.contentFeedRelFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedRel.getId()%>">
</form>
