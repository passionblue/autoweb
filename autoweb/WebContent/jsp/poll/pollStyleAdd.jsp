<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    PollStyle _PollStyleDefault = new PollStyle();// PollStyleDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_PollStyleDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _poll_idValue= (reqParams.get("pollId")==null?WebUtil.display(_PollStyleDefault.getPollId()):WebUtil.display((String)reqParams.get("pollId")));
    String _colorValue= (reqParams.get("color")==null?WebUtil.display(_PollStyleDefault.getColor()):WebUtil.display((String)reqParams.get("color")));
    String _backgroundValue= (reqParams.get("background")==null?WebUtil.display(_PollStyleDefault.getBackground()):WebUtil.display((String)reqParams.get("background")));
    String _borderValue= (reqParams.get("border")==null?WebUtil.display(_PollStyleDefault.getBorder()):WebUtil.display((String)reqParams.get("border")));
    String _fontValue= (reqParams.get("font")==null?WebUtil.display(_PollStyleDefault.getFont()):WebUtil.display((String)reqParams.get("font")));
    String _marginValue= (reqParams.get("margin")==null?WebUtil.display(_PollStyleDefault.getMargin()):WebUtil.display((String)reqParams.get("margin")));
    String _paddingValue= (reqParams.get("padding")==null?WebUtil.display(_PollStyleDefault.getPadding()):WebUtil.display((String)reqParams.get("padding")));
    String _floatingValue= (reqParams.get("floating")==null?WebUtil.display(_PollStyleDefault.getFloating()):WebUtil.display((String)reqParams.get("floating")));
    String _text_alignValue= (reqParams.get("textAlign")==null?WebUtil.display(_PollStyleDefault.getTextAlign()):WebUtil.display((String)reqParams.get("textAlign")));
    String _text_indentValue= (reqParams.get("textIndent")==null?WebUtil.display(_PollStyleDefault.getTextIndent()):WebUtil.display((String)reqParams.get("textIndent")));
    String _heightValue= (reqParams.get("height")==null?WebUtil.display(_PollStyleDefault.getHeight()):WebUtil.display((String)reqParams.get("height")));
    String _widthValue= (reqParams.get("width")==null?WebUtil.display(_PollStyleDefault.getWidth()):WebUtil.display((String)reqParams.get("width")));
    String _extraValue= (reqParams.get("extra")==null?WebUtil.display(_PollStyleDefault.getExtra()):WebUtil.display((String)reqParams.get("extra")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PollStyleDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PollStyleDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="pollStyleForm" method="post" action="/pollStyleAction.html" >
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
            <b><a href="javascript:document.pollStyleForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PollStyleDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollStyle _PollStyle = (PollStyle) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PollStyle.getId() %> </td>

    <td> <%= WebUtil.display(_PollStyle.getUserId()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getPollId()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getColor()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getBackground()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getBorder()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getFont()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getMargin()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getPadding()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getFloating()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getTextAlign()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getTextIndent()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getHeight()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getWidth()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getExtra()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_PollStyle.getTimeUpdated()) %></td>


<td>
<form name="pollStyleForm<%=_PollStyle.getId()%>" method="post" action="/v_poll_style_edit.html" >
    <a href="javascript:document.pollStyleForm<%=_PollStyle.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollStyle.getId() %>">
</form>
</td>
<td>
<a href="/pollStyleAction.html?del=true&id=<%=_PollStyle.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>