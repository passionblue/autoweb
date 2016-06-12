<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    PollStyle _PollStyle = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_PollStyle = PollStyleDS.getInstance().getById(id);
		if ( _PollStyle == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
		}
		isEdit = true;

        
	} else {

	    _PollStyle = new PollStyle();// PollStyleDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    

    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_PollStyle.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _poll_idValue= (reqParams.get("pollId")==null?WebUtil.display(_PollStyle.getPollId()):WebUtil.display((String)reqParams.get("pollId")));
    String _colorValue= (reqParams.get("color")==null?WebUtil.display(_PollStyle.getColor()):WebUtil.display((String)reqParams.get("color")));
    String _backgroundValue= (reqParams.get("background")==null?WebUtil.display(_PollStyle.getBackground()):WebUtil.display((String)reqParams.get("background")));
    String _borderValue= (reqParams.get("border")==null?WebUtil.display(_PollStyle.getBorder()):WebUtil.display((String)reqParams.get("border")));
    String _fontValue= (reqParams.get("font")==null?WebUtil.display(_PollStyle.getFont()):WebUtil.display((String)reqParams.get("font")));
    String _marginValue= (reqParams.get("margin")==null?WebUtil.display(_PollStyle.getMargin()):WebUtil.display((String)reqParams.get("margin")));
    String _paddingValue= (reqParams.get("padding")==null?WebUtil.display(_PollStyle.getPadding()):WebUtil.display((String)reqParams.get("padding")));
    String _floatingValue= (reqParams.get("floating")==null?WebUtil.display(_PollStyle.getFloating()):WebUtil.display((String)reqParams.get("floating")));
    String _text_alignValue= (reqParams.get("textAlign")==null?WebUtil.display(_PollStyle.getTextAlign()):WebUtil.display((String)reqParams.get("textAlign")));
    String _text_indentValue= (reqParams.get("textIndent")==null?WebUtil.display(_PollStyle.getTextIndent()):WebUtil.display((String)reqParams.get("textIndent")));
    String _heightValue= (reqParams.get("height")==null?WebUtil.display(_PollStyle.getHeight()):WebUtil.display((String)reqParams.get("height")));
    String _widthValue= (reqParams.get("width")==null?WebUtil.display(_PollStyle.getWidth()):WebUtil.display((String)reqParams.get("width")));
    String _extraValue= (reqParams.get("extra")==null?WebUtil.display(_PollStyle.getExtra()):WebUtil.display((String)reqParams.get("extra")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PollStyle.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PollStyle.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
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

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>


<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
