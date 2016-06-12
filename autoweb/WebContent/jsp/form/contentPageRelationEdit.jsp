<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	ContentPageRelation _ContentPageRelation = ContentPageRelationDS.getInstance().getById(id);

	if ( _ContentPageRelation == null ) {
		return;
	}

	String _content_idValue=  WebUtil.display(_ContentPageRelation.getContentId());
	String _page_idValue=  WebUtil.display(_ContentPageRelation.getPageId());
	String _time_createdValue=  WebUtil.display(_ContentPageRelation.getTimeCreated());
%> 

<br>
<form name="contentPageRelationFormEdit" method="post" action="/contentPageRelationAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

	
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Content Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="contentId" value="<%=WebUtil.display(_content_idValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Page Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="pageId" value="<%=WebUtil.display(_page_idValue)%>"/></TD>
    </TR>
			
		    <TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.contentPageRelationFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentPageRelation.getId()%>">
</form>
