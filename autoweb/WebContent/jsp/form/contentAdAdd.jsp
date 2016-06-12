<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ContentAd _ContentAdDefault = new ContentAd();// ContentAdDS.getInstance().getDeafult();
    
    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_ContentAdDefault.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _position_codeValue= (reqParams.get("positionCode")==null?WebUtil.display(_ContentAdDefault.getPositionCode()):WebUtil.display((String)reqParams.get("positionCode")));
    String _ad_contentValue= (reqParams.get("adContent")==null?WebUtil.display(_ContentAdDefault.getAdContent()):WebUtil.display((String)reqParams.get("adContent")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_ContentAdDefault.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentAdDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_ContentAdDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="contentAdForm" method="post" action="/contentAdAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Content Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="contentId" value="<%=WebUtil.display(_content_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Position Code</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="positionCode" value="<%=WebUtil.display(_position_codeValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Ad Content</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="adContent" value="<%=WebUtil.display(_ad_contentValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Hide</b> &nbsp;</TD>
        <TD>&nbsp;<select name="hide">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.contentAdForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ContentAdDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentAd _ContentAd = (ContentAd) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentAd.getId() %> </td>

    <td> <%= WebUtil.display(_ContentAd.getContentId()) %></td>
    <td> <%= WebUtil.display(_ContentAd.getPositionCode()) %></td>
    <td> <%= WebUtil.display(_ContentAd.getAdContent()) %></td>
    <td> <%= WebUtil.display(_ContentAd.getHide()) %></td>
    <td> <%= WebUtil.display(_ContentAd.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_ContentAd.getTimeUpdated()) %></td>


<td>
<form name="contentAdForm<%=_ContentAd.getId()%>" method="post" action="/v_content_ad_edit.html" >
    <a href="javascript:document.contentAdForm<%=_ContentAd.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentAd.getId() %>">
</form>
</td>
<td>
<a href="/contentAdAction.html?del=true&id=<%=_ContentAd.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>