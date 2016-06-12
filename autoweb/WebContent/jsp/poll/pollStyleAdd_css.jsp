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

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="pollStyleForm_topArea" class="formTopArea"></div>
<div id="pollStyleForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="pollStyleForm" method="POST" action="/pollStyleAction.html" >




	<div id="pollStyleForm_userId_field">
    <div id="pollStyleForm_userId_label" class="formLabel" >User Id </div>
    <div id="pollStyleForm_userId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="userId" value="<%=WebUtil.display(_user_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_pollId_field">
    <div id="pollStyleForm_pollId_label" class="formLabel" >Poll Id </div>
    <div id="pollStyleForm_pollId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="pollId" value="<%=WebUtil.display(_poll_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_color_field">
    <div id="pollStyleForm_color_label" class="formLabel" >Color </div>
    <div id="pollStyleForm_color_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="color" value="<%=WebUtil.display(_colorValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_background_field">
    <div id="pollStyleForm_background_label" class="formLabel" >Background </div>
    <div id="pollStyleForm_background_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="80" name="background" value="<%=WebUtil.display(_backgroundValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_border_field">
    <div id="pollStyleForm_border_label" class="formLabel" >Border </div>
    <div id="pollStyleForm_border_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="border" value="<%=WebUtil.display(_borderValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_font_field">
    <div id="pollStyleForm_font_label" class="formLabel" >Font </div>
    <div id="pollStyleForm_font_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="font" value="<%=WebUtil.display(_fontValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_margin_field">
    <div id="pollStyleForm_margin_label" class="formLabel" >Margin </div>
    <div id="pollStyleForm_margin_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="margin" value="<%=WebUtil.display(_marginValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_padding_field">
    <div id="pollStyleForm_padding_label" class="formLabel" >Padding </div>
    <div id="pollStyleForm_padding_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="padding" value="<%=WebUtil.display(_paddingValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_floating_field">
    <div id="pollStyleForm_floating_label" class="formLabel" >Floating </div>
    <div id="pollStyleForm_floating_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="floating" value="<%=WebUtil.display(_floatingValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_textAlign_field">
    <div id="pollStyleForm_textAlign_label" class="formLabel" >Text Align </div>
    <div id="pollStyleForm_textAlign_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="textAlign" value="<%=WebUtil.display(_text_alignValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_textIndent_field">
    <div id="pollStyleForm_textIndent_label" class="formLabel" >Text Indent </div>
    <div id="pollStyleForm_textIndent_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="textIndent" value="<%=WebUtil.display(_text_indentValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_height_field">
    <div id="pollStyleForm_height_label" class="formLabel" >Height </div>
    <div id="pollStyleForm_height_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="height" value="<%=WebUtil.display(_heightValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_width_field">
    <div id="pollStyleForm_width_label" class="formLabel" >Width </div>
    <div id="pollStyleForm_width_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="width" value="<%=WebUtil.display(_widthValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollStyleForm_extra_field">
    <div id="pollStyleForm_extra_label" class="formLabel" >Extra </div>
    <div id="pollStyleForm_extra_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="80" name="extra" value="<%=WebUtil.display(_extraValue)%>"/>
    </div>      
	</div><div class="clear"></div>









        <div id="pollStyleForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollStyleForm.submit();">Submit</a>
        </div>      

        <div id="pollStyleForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.pollStyleForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="pollStyleForm_bottomArea" class="formBottomArea"></div>


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
<form name="pollStyleForm<%=_PollStyle.getId()%>" method="get" action="/v_poll_style_edit.html" >
    <a href="javascript:document.pollStyleForm<%=_PollStyle.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollStyle.getId() %>">
</form>
<form name="pollStyleForm<%=_PollStyle.getId()%>2" method="get" action="/v_poll_style_edit2.html" >
    <a href="javascript:document.pollStyleForm<%=_PollStyle.getId()%>2.submit();">Edit2</a>           
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