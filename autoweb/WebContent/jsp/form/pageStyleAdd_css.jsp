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

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="pageStyleForm_topArea" class="formTopArea"></div>
<div id="pageStyleForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="pageStyleForm" method="POST" action="/pageStyleAction.html" >




	<div id="pageStyleForm_pageId_field">
    <div id="pageStyleForm_pageId_label" class="formLabel" >Page Id </div>
    <div id="pageStyleForm_pageId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="pageId" value="<%=WebUtil.display(_page_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pageStyleForm_styleId_field">
    <div id="pageStyleForm_styleId_label" class="formLabel" >Style Id </div>
    <div id="pageStyleForm_styleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="styleId" value="<%=WebUtil.display(_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="pageStyleForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pageStyleForm.submit();">Submit</a>
        </div>      

        <div id="pageStyleForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.pageStyleForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="pageStyleForm_bottomArea" class="formBottomArea"></div>


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
<form name="pageStyleForm<%=_PageStyle.getId()%>" method="get" action="/v_page_style_edit.html" >
    <a href="javascript:document.pageStyleForm<%=_PageStyle.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageStyle.getId() %>">
</form>
<form name="pageStyleForm<%=_PageStyle.getId()%>2" method="get" action="/v_page_style_edit2.html" >
    <a href="javascript:document.pageStyleForm<%=_PageStyle.getId()%>2.submit();">Edit2</a>           
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