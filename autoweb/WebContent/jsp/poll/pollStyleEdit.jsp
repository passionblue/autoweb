<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    PollStyle _PollStyle = PollStyleDS.getInstance().getById(id);

    if ( _PollStyle == null ) {
        return;
    }

    String _user_idValue=  WebUtil.display(_PollStyle.getUserId());
    String _poll_idValue=  WebUtil.display(_PollStyle.getPollId());
    String _colorValue=  WebUtil.display(_PollStyle.getColor());
    String _backgroundValue=  WebUtil.display(_PollStyle.getBackground());
    String _borderValue=  WebUtil.display(_PollStyle.getBorder());
    String _fontValue=  WebUtil.display(_PollStyle.getFont());
    String _marginValue=  WebUtil.display(_PollStyle.getMargin());
    String _paddingValue=  WebUtil.display(_PollStyle.getPadding());
    String _floatingValue=  WebUtil.display(_PollStyle.getFloating());
    String _text_alignValue=  WebUtil.display(_PollStyle.getTextAlign());
    String _text_indentValue=  WebUtil.display(_PollStyle.getTextIndent());
    String _heightValue=  WebUtil.display(_PollStyle.getHeight());
    String _widthValue=  WebUtil.display(_PollStyle.getWidth());
    String _extraValue=  WebUtil.display(_PollStyle.getExtra());
    String _time_createdValue=  WebUtil.display(_PollStyle.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_PollStyle.getTimeUpdated());
%> 

<br>
<form name="pollStyleFormEdit" method="post" action="/pollStyleAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>User Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="userId" value="<%=WebUtil.display(_user_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Poll Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="pollId" value="<%=WebUtil.display(_poll_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Color</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="color" value="<%=WebUtil.display(_colorValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Background</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="background" value="<%=WebUtil.display(_backgroundValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Border</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="border" value="<%=WebUtil.display(_borderValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Font</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="font" value="<%=WebUtil.display(_fontValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Margin</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="margin" value="<%=WebUtil.display(_marginValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Padding</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="padding" value="<%=WebUtil.display(_paddingValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Floating</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="floating" value="<%=WebUtil.display(_floatingValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Text Align</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="textAlign" value="<%=WebUtil.display(_text_alignValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Text Indent</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="textIndent" value="<%=WebUtil.display(_text_indentValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Height</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="height" value="<%=WebUtil.display(_heightValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Width</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="width" value="<%=WebUtil.display(_widthValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Extra</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="extra" value="<%=WebUtil.display(_extraValue)%>"/></TD>
    </TR>
            
            
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.pollStyleFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
</form>
