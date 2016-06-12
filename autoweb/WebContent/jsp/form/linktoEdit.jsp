<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    Linkto _Linkto = LinktoDS.getInstance().getById(id);

    if ( _Linkto == null ) {
        return;
    }

    String _link_keyValue=  WebUtil.display(_Linkto.getLinkKey());
    String _link_targetValue=  WebUtil.display(_Linkto.getLinkTarget());
    String _disableValue=  WebUtil.display(_Linkto.getDisable());
    String _time_createdValue=  WebUtil.display(_Linkto.getTimeCreated());
%> 

<br>
<form name="linktoFormEdit" method="post" action="/linktoAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Link Key</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="linkKey" value="<%=WebUtil.display(_link_keyValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Link Target</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="linkTarget" value="<%=WebUtil.display(_link_targetValue)%>"/></TD>
    </TR>
                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Disable</b> &nbsp;</TD>
        <TD>&nbsp;<select name="disable">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.linktoFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Linkto.getId()%>">
</form>
