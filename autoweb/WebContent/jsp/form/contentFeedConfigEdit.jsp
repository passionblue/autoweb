<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    ContentFeedConfig _ContentFeedConfig = ContentFeedConfigDS.getInstance().getById(id);

    if ( _ContentFeedConfig == null ) {
        return;
    }

    String _feed_categoryValue=  WebUtil.display(_ContentFeedConfig.getFeedCategory());
    String _feed_typeValue=  WebUtil.display(_ContentFeedConfig.getFeedType());
    String _display_typeValue=  WebUtil.display(_ContentFeedConfig.getDisplayType());
    String _time_createdValue=  WebUtil.display(_ContentFeedConfig.getTimeCreated());
%> 

<br>
<form name="contentFeedConfigFormEdit" method="post" action="/contentFeedConfigAction.html" >
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
            <b><a href="javascript:document.contentFeedConfigFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedConfig.getId()%>">
</form>
