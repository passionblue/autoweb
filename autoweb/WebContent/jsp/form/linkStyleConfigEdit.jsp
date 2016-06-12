<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	LinkStyleConfig _LinkStyleConfig = LinkStyleConfigDS.getInstance().getById(id);

	if ( _LinkStyleConfig == null ) {
		return;
	}

	Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    

    String _style_keyValue=  WebUtil.display(_LinkStyleConfig.getStyleKey());
    String _is_globalValue=  WebUtil.display(_LinkStyleConfig.getIsGlobal());
    String _id_classValue=  WebUtil.display(_LinkStyleConfig.getIdClass());
    String _is_idValue=  WebUtil.display(_LinkStyleConfig.getIsId());
    String _heightValue=  WebUtil.display(_LinkStyleConfig.getHeight());
    String _widthValue=  WebUtil.display(_LinkStyleConfig.getWidth());
    String _displayValue=  WebUtil.display(_LinkStyleConfig.getDisplay());
    String _borderValue=  WebUtil.display(_LinkStyleConfig.getBorder());
    String _backgroundValue=  WebUtil.display(_LinkStyleConfig.getBackground());
    String _colorValue=  WebUtil.display(_LinkStyleConfig.getColor());
    String _text_decorationValue=  WebUtil.display(_LinkStyleConfig.getTextDecoration());
    String _text_alignValue=  WebUtil.display(_LinkStyleConfig.getTextAlign());
    String _vertical_alignValue=  WebUtil.display(_LinkStyleConfig.getVerticalAlign());
    String _text_indentValue=  WebUtil.display(_LinkStyleConfig.getTextIndent());
    String _marginValue=  WebUtil.display(_LinkStyleConfig.getMargin());
    String _paddingValue=  WebUtil.display(_LinkStyleConfig.getPadding());
    String _fontValue=  WebUtil.display(_LinkStyleConfig.getFont());
    String _extra_styleValue=  WebUtil.display(_LinkStyleConfig.getExtraStyle());
    String _hov_heightValue=  WebUtil.display(_LinkStyleConfig.getHovHeight());
    String _hov_widthValue=  WebUtil.display(_LinkStyleConfig.getHovWidth());
    String _hov_displayValue=  WebUtil.display(_LinkStyleConfig.getHovDisplay());
    String _hov_borderValue=  WebUtil.display(_LinkStyleConfig.getHovBorder());
    String _hov_backgroundValue=  WebUtil.display(_LinkStyleConfig.getHovBackground());
    String _hov_colorValue=  WebUtil.display(_LinkStyleConfig.getHovColor());
    String _hov_text_decorationValue=  WebUtil.display(_LinkStyleConfig.getHovTextDecoration());
    String _hov_text_alignValue=  WebUtil.display(_LinkStyleConfig.getHovTextAlign());
    String _hov_vertical_alignValue=  WebUtil.display(_LinkStyleConfig.getHovVerticalAlign());
    String _hov_text_indentValue=  WebUtil.display(_LinkStyleConfig.getHovTextIndent());
    String _hov_marginValue=  WebUtil.display(_LinkStyleConfig.getHovMargin());
    String _hov_paddingValue=  WebUtil.display(_LinkStyleConfig.getHovPadding());
    String _hov_fontValue=  WebUtil.display(_LinkStyleConfig.getHovFont());
    String _hov_extra_styleValue=  WebUtil.display(_LinkStyleConfig.getHovExtraStyle());
    String _active_useValue=  WebUtil.display(_LinkStyleConfig.getActiveUse());
    String _active_borderValue=  WebUtil.display(_LinkStyleConfig.getActiveBorder());
    String _active_backgroundValue=  WebUtil.display(_LinkStyleConfig.getActiveBackground());
    String _active_colorValue=  WebUtil.display(_LinkStyleConfig.getActiveColor());
    String _active_text_decorationValue=  WebUtil.display(_LinkStyleConfig.getActiveTextDecoration());
    String _active_extra_styleValue=  WebUtil.display(_LinkStyleConfig.getActiveExtraStyle());
    String _active_marginValue=  WebUtil.display(_LinkStyleConfig.getActiveMargin());
    String _active_paddingValue=  WebUtil.display(_LinkStyleConfig.getActivePadding());
    String _time_createdValue=  WebUtil.display(_LinkStyleConfig.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_LinkStyleConfig.getTimeUpdated());

	//>>START<<
	List panelsForThis = PanelLinkStyleDS.getInstance().getByStyleId(id);
	
	//>>END<<
%> 
<h3>Panels that use this configuration</h3>
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
	<TR bgcolor="#ffffff">
		<TD align="left" ><b>ID</b></TD>
		<TD align="left" ><b>Title</b></TD>
		<TD align="left" ><b>Position</b></TD>
		<TD align="left" ><b>Type</b></TD>
	</TR>
<%
	PanelDS panelDS = PanelDS.getInstance();
	for(Iterator iter = panelsForThis.iterator();iter.hasNext();){
		PanelLinkStyle panelStyle = (PanelLinkStyle) iter.next();
		Panel panel = panelDS.getById(panelStyle.getPanelId());
		if (panel == null) continue;
%>
	<TR bgcolor="#ffffff">
		<TD align="left" ><b><%= panel.getId() %></b></TD>
		<TD align="left" ><b><%= WebUtil.display(panel.getPanelTitle()) %></b></TD>
		<TD align="left" ><b><%= panel.getColumnNum() %></b></TD>
		<TD align="left" ><b><%= panel.getPanelType() %></b></TD>
	</TR>
<% } %>
</TABLE>

<br>
<div id="linkStyleConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="linkStyleConfigFormEdit" method="POST" action="/linkStyleConfigAction.html" >




	<div id="linkStyleConfigForm_styleKey_field">
    <div id="linkStyleConfigForm_styleKey_label" class="formLabel" >Style Key </div>
    <div id="linkStyleConfigForm_styleKey_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="styleKey" value="<%=WebUtil.display(_style_keyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="linkStyleConfigForm_isGlobal_field">
    <div id="linkStyleConfigForm_isGlobal_label" class="formLabel" >Is Global </div>
    <div id="linkStyleConfigForm_isGlobal_dropdown" class="formFieldDropDown" >       
        <select name="isGlobal">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_globalValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_globalValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="linkStyleConfigForm_idClass_field">
    <div id="linkStyleConfigForm_idClass_label" class="formLabel" >Id Class </div>
    <div id="linkStyleConfigForm_idClass_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="idClass" value="<%=WebUtil.display(_id_classValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="linkStyleConfigForm_isId_field">
    <div id="linkStyleConfigForm_isId_label" class="formLabel" >Is Id </div>
    <div id="linkStyleConfigForm_isId_dropdown" class="formFieldDropDown" >       
        <select name="isId">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_idValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_idValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="linkStyleConfigForm_height_field">
    <div id="linkStyleConfigForm_height_label" class="formLabel" >Height </div>
    <div id="linkStyleConfigForm_height_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="height" value="<%=WebUtil.display(_heightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_width_field">
    <div id="linkStyleConfigForm_width_label" class="formLabel" >Width </div>
    <div id="linkStyleConfigForm_width_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="width" value="<%=WebUtil.display(_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_display_field">
    <div id="linkStyleConfigForm_display_label" class="formLabel" >Display </div>
    <div id="linkStyleConfigForm_display_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_border_field">
    <div id="linkStyleConfigForm_border_label" class="formLabel" >Border </div>
    <div id="linkStyleConfigForm_border_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="border" value="<%=WebUtil.display(_borderValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_background_field">
    <div id="linkStyleConfigForm_background_label" class="formLabel" >Background </div>
    <div id="linkStyleConfigForm_background_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="background" value="<%=WebUtil.display(_backgroundValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_color_field">
    <div id="linkStyleConfigForm_color_label" class="formLabel" >Color </div>
    <div id="linkStyleConfigForm_color_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="color" value="<%=WebUtil.display(_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_textDecoration_field">
    <div id="linkStyleConfigForm_textDecoration_label" class="formLabel" >Text Decoration </div>
    <div id="linkStyleConfigForm_textDecoration_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="textDecoration" value="<%=WebUtil.display(_text_decorationValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_textAlign_field">
    <div id="linkStyleConfigForm_textAlign_label" class="formLabel" >Text Align </div>
    <div id="linkStyleConfigForm_textAlign_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="textAlign" value="<%=WebUtil.display(_text_alignValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_verticalAlign_field">
    <div id="linkStyleConfigForm_verticalAlign_label" class="formLabel" >Vertical Align </div>
    <div id="linkStyleConfigForm_verticalAlign_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="verticalAlign" value="<%=WebUtil.display(_vertical_alignValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_textIndent_field">
    <div id="linkStyleConfigForm_textIndent_label" class="formLabel" >Text Indent </div>
    <div id="linkStyleConfigForm_textIndent_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="textIndent" value="<%=WebUtil.display(_text_indentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_margin_field">
    <div id="linkStyleConfigForm_margin_label" class="formLabel" >Margin </div>
    <div id="linkStyleConfigForm_margin_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="margin" value="<%=WebUtil.display(_marginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_padding_field">
    <div id="linkStyleConfigForm_padding_label" class="formLabel" >Padding </div>
    <div id="linkStyleConfigForm_padding_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="padding" value="<%=WebUtil.display(_paddingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_font_field">
    <div id="linkStyleConfigForm_font_label" class="formLabel" >Font </div>
    <div id="linkStyleConfigForm_font_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="font" value="<%=WebUtil.display(_fontValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_extraStyle_field">
    <div id="linkStyleConfigForm_extraStyle_label" class="formLabel" >Extra Style </div>
    <div id="linkStyleConfigForm_extraStyle_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="extraStyle" value="<%=WebUtil.display(_extra_styleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovHeight_field">
    <div id="linkStyleConfigForm_hovHeight_label" class="formLabel" >Hov Height </div>
    <div id="linkStyleConfigForm_hovHeight_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovHeight" value="<%=WebUtil.display(_hov_heightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovWidth_field">
    <div id="linkStyleConfigForm_hovWidth_label" class="formLabel" >Hov Width </div>
    <div id="linkStyleConfigForm_hovWidth_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovWidth" value="<%=WebUtil.display(_hov_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovDisplay_field">
    <div id="linkStyleConfigForm_hovDisplay_label" class="formLabel" >Hov Display </div>
    <div id="linkStyleConfigForm_hovDisplay_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovDisplay" value="<%=WebUtil.display(_hov_displayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovBorder_field">
    <div id="linkStyleConfigForm_hovBorder_label" class="formLabel" >Hov Border </div>
    <div id="linkStyleConfigForm_hovBorder_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovBorder" value="<%=WebUtil.display(_hov_borderValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovBackground_field">
    <div id="linkStyleConfigForm_hovBackground_label" class="formLabel" >Hov Background </div>
    <div id="linkStyleConfigForm_hovBackground_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovBackground" value="<%=WebUtil.display(_hov_backgroundValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovColor_field">
    <div id="linkStyleConfigForm_hovColor_label" class="formLabel" >Hov Color </div>
    <div id="linkStyleConfigForm_hovColor_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovColor" value="<%=WebUtil.display(_hov_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovTextDecoration_field">
    <div id="linkStyleConfigForm_hovTextDecoration_label" class="formLabel" >Hov Text Decoration </div>
    <div id="linkStyleConfigForm_hovTextDecoration_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovTextDecoration" value="<%=WebUtil.display(_hov_text_decorationValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovTextAlign_field">
    <div id="linkStyleConfigForm_hovTextAlign_label" class="formLabel" >Hov Text Align </div>
    <div id="linkStyleConfigForm_hovTextAlign_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovTextAlign" value="<%=WebUtil.display(_hov_text_alignValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovVerticalAlign_field">
    <div id="linkStyleConfigForm_hovVerticalAlign_label" class="formLabel" >Hov Vertical Align </div>
    <div id="linkStyleConfigForm_hovVerticalAlign_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovVerticalAlign" value="<%=WebUtil.display(_hov_vertical_alignValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovTextIndent_field">
    <div id="linkStyleConfigForm_hovTextIndent_label" class="formLabel" >Hov Text Indent </div>
    <div id="linkStyleConfigForm_hovTextIndent_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovTextIndent" value="<%=WebUtil.display(_hov_text_indentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovMargin_field">
    <div id="linkStyleConfigForm_hovMargin_label" class="formLabel" >Hov Margin </div>
    <div id="linkStyleConfigForm_hovMargin_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovMargin" value="<%=WebUtil.display(_hov_marginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovPadding_field">
    <div id="linkStyleConfigForm_hovPadding_label" class="formLabel" >Hov Padding </div>
    <div id="linkStyleConfigForm_hovPadding_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovPadding" value="<%=WebUtil.display(_hov_paddingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovFont_field">
    <div id="linkStyleConfigForm_hovFont_label" class="formLabel" >Hov Font </div>
    <div id="linkStyleConfigForm_hovFont_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovFont" value="<%=WebUtil.display(_hov_fontValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_hovExtraStyle_field">
    <div id="linkStyleConfigForm_hovExtraStyle_label" class="formLabel" >Hov Extra Style </div>
    <div id="linkStyleConfigForm_hovExtraStyle_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="hovExtraStyle" value="<%=WebUtil.display(_hov_extra_styleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="linkStyleConfigForm_activeUse_field">
    <div id="linkStyleConfigForm_activeUse_label" class="formLabel" >Active Use </div>
    <div id="linkStyleConfigForm_activeUse_dropdown" class="formFieldDropDown" >       
        <select name="activeUse">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _active_useValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _active_useValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="linkStyleConfigForm_activeBorder_field">
    <div id="linkStyleConfigForm_activeBorder_label" class="formLabel" >Active Border </div>
    <div id="linkStyleConfigForm_activeBorder_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="activeBorder" value="<%=WebUtil.display(_active_borderValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_activeBackground_field">
    <div id="linkStyleConfigForm_activeBackground_label" class="formLabel" >Active Background </div>
    <div id="linkStyleConfigForm_activeBackground_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="activeBackground" value="<%=WebUtil.display(_active_backgroundValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_activeColor_field">
    <div id="linkStyleConfigForm_activeColor_label" class="formLabel" >Active Color </div>
    <div id="linkStyleConfigForm_activeColor_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="activeColor" value="<%=WebUtil.display(_active_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_activeTextDecoration_field">
    <div id="linkStyleConfigForm_activeTextDecoration_label" class="formLabel" >Active Text Decoration </div>
    <div id="linkStyleConfigForm_activeTextDecoration_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="activeTextDecoration" value="<%=WebUtil.display(_active_text_decorationValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_activeExtraStyle_field">
    <div id="linkStyleConfigForm_activeExtraStyle_label" class="formLabel" >Active Extra Style </div>
    <div id="linkStyleConfigForm_activeExtraStyle_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="activeExtraStyle" value="<%=WebUtil.display(_active_extra_styleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_activeMargin_field">
    <div id="linkStyleConfigForm_activeMargin_label" class="formLabel" >Active Margin </div>
    <div id="linkStyleConfigForm_activeMargin_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="activeMargin" value="<%=WebUtil.display(_active_marginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linkStyleConfigForm_activePadding_field">
    <div id="linkStyleConfigForm_activePadding_label" class="formLabel" >Active Padding </div>
    <div id="linkStyleConfigForm_activePadding_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="activePadding" value="<%=WebUtil.display(_active_paddingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


        <div id="linkStyleConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.linkStyleConfigFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_LinkStyleConfig.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
