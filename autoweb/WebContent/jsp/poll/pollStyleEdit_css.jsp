<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    PollStyle _PollStyle = PollStyleDS.getInstance().getById(id);

    if ( _PollStyle == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    

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
<div id="pollStyleForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pollStyleFormEdit" method="POST" action="/pollStyleAction.html" >




	<div id="pollStyleForm_userId_field">
    <div id="pollStyleForm_userId_label" class="formLabel" >User Id </div>
    <div id="pollStyleForm_userId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="userId" value="<%=WebUtil.display(_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_pollId_field">
    <div id="pollStyleForm_pollId_label" class="formLabel" >Poll Id </div>
    <div id="pollStyleForm_pollId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="pollId" value="<%=WebUtil.display(_poll_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_color_field">
    <div id="pollStyleForm_color_label" class="formLabel" >Color </div>
    <div id="pollStyleForm_color_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="color" value="<%=WebUtil.display(_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_background_field">
    <div id="pollStyleForm_background_label" class="formLabel" >Background </div>
    <div id="pollStyleForm_background_text" class="formFieldText" >       
        <input id="field" type="text" size="80" name="background" value="<%=WebUtil.display(_backgroundValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_border_field">
    <div id="pollStyleForm_border_label" class="formLabel" >Border </div>
    <div id="pollStyleForm_border_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="border" value="<%=WebUtil.display(_borderValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_font_field">
    <div id="pollStyleForm_font_label" class="formLabel" >Font </div>
    <div id="pollStyleForm_font_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="font" value="<%=WebUtil.display(_fontValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_margin_field">
    <div id="pollStyleForm_margin_label" class="formLabel" >Margin </div>
    <div id="pollStyleForm_margin_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="margin" value="<%=WebUtil.display(_marginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_padding_field">
    <div id="pollStyleForm_padding_label" class="formLabel" >Padding </div>
    <div id="pollStyleForm_padding_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="padding" value="<%=WebUtil.display(_paddingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_floating_field">
    <div id="pollStyleForm_floating_label" class="formLabel" >Floating </div>
    <div id="pollStyleForm_floating_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="floating" value="<%=WebUtil.display(_floatingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_textAlign_field">
    <div id="pollStyleForm_textAlign_label" class="formLabel" >Text Align </div>
    <div id="pollStyleForm_textAlign_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="textAlign" value="<%=WebUtil.display(_text_alignValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_textIndent_field">
    <div id="pollStyleForm_textIndent_label" class="formLabel" >Text Indent </div>
    <div id="pollStyleForm_textIndent_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="textIndent" value="<%=WebUtil.display(_text_indentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_height_field">
    <div id="pollStyleForm_height_label" class="formLabel" >Height </div>
    <div id="pollStyleForm_height_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="height" value="<%=WebUtil.display(_heightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_width_field">
    <div id="pollStyleForm_width_label" class="formLabel" >Width </div>
    <div id="pollStyleForm_width_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="width" value="<%=WebUtil.display(_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollStyleForm_extra_field">
    <div id="pollStyleForm_extra_label" class="formLabel" >Extra </div>
    <div id="pollStyleForm_extra_text" class="formFieldText" >       
        <input id="field" type="text" size="80" name="extra" value="<%=WebUtil.display(_extraValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="pollStyleFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollStyleFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
