<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    ContentAd _ContentAd = ContentAdDS.getInstance().getById(id);

    if ( _ContentAd == null ) {
        return;
    }

    String _content_idValue=  WebUtil.display(_ContentAd.getContentId());
    String _position_codeValue=  WebUtil.display(_ContentAd.getPositionCode());
    String _ad_contentValue=  WebUtil.display(_ContentAd.getAdContent());
    String _hideValue=  WebUtil.display(_ContentAd.getHide());
    String _time_createdValue=  WebUtil.display(_ContentAd.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_ContentAd.getTimeUpdated());
%> 

<br>
<form name="contentAdFormEdit" method="post" action="/contentAdAction.html" >
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
            <b><a href="javascript:document.contentAdFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentAd.getId()%>">
</form>
