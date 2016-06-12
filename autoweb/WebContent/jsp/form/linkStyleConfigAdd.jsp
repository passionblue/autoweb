<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	Map reqParams = (Map) session.getAttribute("k_reserved_params");

	LinkStyleConfig _LinkStyleConfigDefault = StyleConfigUtil.getDefaultLinkStyle();
	
    String _style_keyValue= (reqParams.get("styleKey")==null?WebUtil.display(_LinkStyleConfigDefault.getStyleKey()):WebUtil.display((String)reqParams.get("styleKey")));
    String _is_globalValue= (reqParams.get("isGlobal")==null?WebUtil.display(_LinkStyleConfigDefault.getIsGlobal()):WebUtil.display((String)reqParams.get("isGlobal")));
    String _id_classValue= (reqParams.get("idClass")==null?WebUtil.display(_LinkStyleConfigDefault.getIdClass()):WebUtil.display((String)reqParams.get("idClass")));
    String _is_idValue= (reqParams.get("isId")==null?WebUtil.display(_LinkStyleConfigDefault.getIsId()):WebUtil.display((String)reqParams.get("isId")));
    String _heightValue= (reqParams.get("height")==null?WebUtil.display(_LinkStyleConfigDefault.getHeight()):WebUtil.display((String)reqParams.get("height")));
    String _widthValue= (reqParams.get("width")==null?WebUtil.display(_LinkStyleConfigDefault.getWidth()):WebUtil.display((String)reqParams.get("width")));
    String _displayValue= (reqParams.get("display")==null?WebUtil.display(_LinkStyleConfigDefault.getDisplay()):WebUtil.display((String)reqParams.get("display")));
    String _borderValue= (reqParams.get("border")==null?WebUtil.display(_LinkStyleConfigDefault.getBorder()):WebUtil.display((String)reqParams.get("border")));
    String _backgroundValue= (reqParams.get("background")==null?WebUtil.display(_LinkStyleConfigDefault.getBackground()):WebUtil.display((String)reqParams.get("background")));
    String _colorValue= (reqParams.get("color")==null?WebUtil.display(_LinkStyleConfigDefault.getColor()):WebUtil.display((String)reqParams.get("color")));
    String _text_decorationValue= (reqParams.get("textDecoration")==null?WebUtil.display(_LinkStyleConfigDefault.getTextDecoration()):WebUtil.display((String)reqParams.get("textDecoration")));
    String _text_alignValue= (reqParams.get("textAlign")==null?WebUtil.display(_LinkStyleConfigDefault.getTextAlign()):WebUtil.display((String)reqParams.get("textAlign")));
    String _vertical_alignValue= (reqParams.get("verticalAlign")==null?WebUtil.display(_LinkStyleConfigDefault.getVerticalAlign()):WebUtil.display((String)reqParams.get("verticalAlign")));
    String _text_indentValue= (reqParams.get("textIndent")==null?WebUtil.display(_LinkStyleConfigDefault.getTextIndent()):WebUtil.display((String)reqParams.get("textIndent")));
    String _marginValue= (reqParams.get("margin")==null?WebUtil.display(_LinkStyleConfigDefault.getMargin()):WebUtil.display((String)reqParams.get("margin")));
    String _paddingValue= (reqParams.get("padding")==null?WebUtil.display(_LinkStyleConfigDefault.getPadding()):WebUtil.display((String)reqParams.get("padding")));
    String _fontValue= (reqParams.get("font")==null?WebUtil.display(_LinkStyleConfigDefault.getFont()):WebUtil.display((String)reqParams.get("font")));
    String _extra_styleValue= (reqParams.get("extraStyle")==null?WebUtil.display(_LinkStyleConfigDefault.getExtraStyle()):WebUtil.display((String)reqParams.get("extraStyle")));
    String _hov_heightValue= (reqParams.get("hovHeight")==null?WebUtil.display(_LinkStyleConfigDefault.getHovHeight()):WebUtil.display((String)reqParams.get("hovHeight")));
    String _hov_widthValue= (reqParams.get("hovWidth")==null?WebUtil.display(_LinkStyleConfigDefault.getHovWidth()):WebUtil.display((String)reqParams.get("hovWidth")));
    String _hov_displayValue= (reqParams.get("hovDisplay")==null?WebUtil.display(_LinkStyleConfigDefault.getHovDisplay()):WebUtil.display((String)reqParams.get("hovDisplay")));
    String _hov_borderValue= (reqParams.get("hovBorder")==null?WebUtil.display(_LinkStyleConfigDefault.getHovBorder()):WebUtil.display((String)reqParams.get("hovBorder")));
    String _hov_backgroundValue= (reqParams.get("hovBackground")==null?WebUtil.display(_LinkStyleConfigDefault.getHovBackground()):WebUtil.display((String)reqParams.get("hovBackground")));
    String _hov_colorValue= (reqParams.get("hovColor")==null?WebUtil.display(_LinkStyleConfigDefault.getHovColor()):WebUtil.display((String)reqParams.get("hovColor")));
    String _hov_text_decorationValue= (reqParams.get("hovTextDecoration")==null?WebUtil.display(_LinkStyleConfigDefault.getHovTextDecoration()):WebUtil.display((String)reqParams.get("hovTextDecoration")));
    String _hov_text_alignValue= (reqParams.get("hovTextAlign")==null?WebUtil.display(_LinkStyleConfigDefault.getHovTextAlign()):WebUtil.display((String)reqParams.get("hovTextAlign")));
    String _hov_vertical_alignValue= (reqParams.get("hovVerticalAlign")==null?WebUtil.display(_LinkStyleConfigDefault.getHovVerticalAlign()):WebUtil.display((String)reqParams.get("hovVerticalAlign")));
    String _hov_text_indentValue= (reqParams.get("hovTextIndent")==null?WebUtil.display(_LinkStyleConfigDefault.getHovTextIndent()):WebUtil.display((String)reqParams.get("hovTextIndent")));
    String _hov_marginValue= (reqParams.get("hovMargin")==null?WebUtil.display(_LinkStyleConfigDefault.getHovMargin()):WebUtil.display((String)reqParams.get("hovMargin")));
    String _hov_paddingValue= (reqParams.get("hovPadding")==null?WebUtil.display(_LinkStyleConfigDefault.getHovPadding()):WebUtil.display((String)reqParams.get("hovPadding")));
    String _hov_fontValue= (reqParams.get("hovFont")==null?WebUtil.display(_LinkStyleConfigDefault.getHovFont()):WebUtil.display((String)reqParams.get("hovFont")));
    String _hov_extra_styleValue= (reqParams.get("hovExtraStyle")==null?WebUtil.display(_LinkStyleConfigDefault.getHovExtraStyle()):WebUtil.display((String)reqParams.get("hovExtraStyle")));
    String _active_useValue= (reqParams.get("activeUse")==null?WebUtil.display(_LinkStyleConfigDefault.getActiveUse()):WebUtil.display((String)reqParams.get("activeUse")));
    String _active_borderValue= (reqParams.get("activeBorder")==null?WebUtil.display(_LinkStyleConfigDefault.getActiveBorder()):WebUtil.display((String)reqParams.get("activeBorder")));
    String _active_backgroundValue= (reqParams.get("activeBackground")==null?WebUtil.display(_LinkStyleConfigDefault.getActiveBackground()):WebUtil.display((String)reqParams.get("activeBackground")));
    String _active_colorValue= (reqParams.get("activeColor")==null?WebUtil.display(_LinkStyleConfigDefault.getActiveColor()):WebUtil.display((String)reqParams.get("activeColor")));
    String _active_text_decorationValue= (reqParams.get("activeTextDecoration")==null?WebUtil.display(_LinkStyleConfigDefault.getActiveTextDecoration()):WebUtil.display((String)reqParams.get("activeTextDecoration")));
    String _active_extra_styleValue= (reqParams.get("activeExtraStyle")==null?WebUtil.display(_LinkStyleConfigDefault.getActiveExtraStyle()):WebUtil.display((String)reqParams.get("activeExtraStyle")));
    String _active_marginValue= (reqParams.get("activeMargin")==null?WebUtil.display(_LinkStyleConfigDefault.getActiveMargin()):WebUtil.display((String)reqParams.get("activeMargin")));
    String _active_paddingValue= (reqParams.get("activePadding")==null?WebUtil.display(_LinkStyleConfigDefault.getActivePadding()):WebUtil.display((String)reqParams.get("activePadding")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_LinkStyleConfigDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_LinkStyleConfigDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));



	String _wpId = WebProcManager.registerWebProcess();
	String retPage = (String) reqParams.get("returnPage");    
	
	//>>START<<
	
	String panelId =  (String) reqParams.get("panel_id");

	if (WebUtil.isNull(_style_keyValue)) {
		_style_keyValue = panelId;
		_style_keyValue = "PanelLinkStyle-" + site.getId() + ":" + _style_keyValue + ":" + System.currentTimeMillis();
	}

	// Site Default Setting
	String cmd =  (String) request.getParameter("cmd");
	
	boolean isSiteDefaultLinkStyleRequest = false;
	boolean isSiteDefaultHorizontalMenuStyleRequest = false;
	boolean isSiteDefaultVerticalMenuStyleRequest = false;
	
	if (WebUtil.checkValue(cmd, "siteDefaultLinkStyle")) { 
		_style_keyValue = "SiteDefaultLink:"+site.getId();
	}
	if (WebUtil.checkValue(cmd, "siteDefaultHMenuStyle")) { 
		_style_keyValue = "SiteDefaulHMenu:"+site.getId();
	}
	if (WebUtil.checkValue(cmd, "siteDefaultVMenuStyle")) { 
		_style_keyValue = "SiteDefaultVMenu:"+site.getId();
	}
	
	//>>END<<
%> 

<br>
<div id="linkStyleConfigForm_topArea" class="formTopArea"></div>
<div id="linkStyleConfigForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="linkStyleConfigForm" method="POST" action="/linkStyleConfigAction.html" >




	<div id="linkStyleConfigForm_styleKey_field">
    <div id="linkStyleConfigForm_styleKey_label" class="formLabel" >Style Key </div>
    <div id="linkStyleConfigForm_styleKey_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="styleKey" value="<%=WebUtil.display(_style_keyValue)%>"/>
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
    <div id="linkStyleConfigForm_idClass_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="idClass" value="<%=WebUtil.display(_id_classValue)%>"/>
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
    <div id="linkStyleConfigForm_height_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="height" value="<%=WebUtil.display(_heightValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_width_field">
    <div id="linkStyleConfigForm_width_label" class="formLabel" >Width </div>
    <div id="linkStyleConfigForm_width_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="width" value="<%=WebUtil.display(_widthValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_display_field">
    <div id="linkStyleConfigForm_display_label" class="formLabel" >Display </div>
    <div id="linkStyleConfigForm_display_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_border_field">
    <div id="linkStyleConfigForm_border_label" class="formLabel" >Border </div>
    <div id="linkStyleConfigForm_border_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="border" value="<%=WebUtil.display(_borderValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_background_field">
    <div id="linkStyleConfigForm_background_label" class="formLabel" >Background </div>
    <div id="linkStyleConfigForm_background_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="background" value="<%=WebUtil.display(_backgroundValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_color_field">
    <div id="linkStyleConfigForm_color_label" class="formLabel" >Color </div>
    <div id="linkStyleConfigForm_color_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="color" value="<%=WebUtil.display(_colorValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_textDecoration_field">
    <div id="linkStyleConfigForm_textDecoration_label" class="formLabel" >Text Decoration </div>
    <div id="linkStyleConfigForm_textDecoration_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="textDecoration" value="<%=WebUtil.display(_text_decorationValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_textAlign_field">
    <div id="linkStyleConfigForm_textAlign_label" class="formLabel" >Text Align </div>
    <div id="linkStyleConfigForm_textAlign_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="textAlign" value="<%=WebUtil.display(_text_alignValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_verticalAlign_field">
    <div id="linkStyleConfigForm_verticalAlign_label" class="formLabel" >Vertical Align </div>
    <div id="linkStyleConfigForm_verticalAlign_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="verticalAlign" value="<%=WebUtil.display(_vertical_alignValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_textIndent_field">
    <div id="linkStyleConfigForm_textIndent_label" class="formLabel" >Text Indent </div>
    <div id="linkStyleConfigForm_textIndent_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="textIndent" value="<%=WebUtil.display(_text_indentValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_margin_field">
    <div id="linkStyleConfigForm_margin_label" class="formLabel" >Margin </div>
    <div id="linkStyleConfigForm_margin_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="margin" value="<%=WebUtil.display(_marginValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_padding_field">
    <div id="linkStyleConfigForm_padding_label" class="formLabel" >Padding </div>
    <div id="linkStyleConfigForm_padding_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="padding" value="<%=WebUtil.display(_paddingValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_font_field">
    <div id="linkStyleConfigForm_font_label" class="formLabel" >Font </div>
    <div id="linkStyleConfigForm_font_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="font" value="<%=WebUtil.display(_fontValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_extraStyle_field">
    <div id="linkStyleConfigForm_extraStyle_label" class="formLabel" >Extra Style </div>
    <div id="linkStyleConfigForm_extraStyle_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="extraStyle" value="<%=WebUtil.display(_extra_styleValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovHeight_field">
    <div id="linkStyleConfigForm_hovHeight_label" class="formLabel" >Hov Height </div>
    <div id="linkStyleConfigForm_hovHeight_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovHeight" value="<%=WebUtil.display(_hov_heightValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovWidth_field">
    <div id="linkStyleConfigForm_hovWidth_label" class="formLabel" >Hov Width </div>
    <div id="linkStyleConfigForm_hovWidth_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovWidth" value="<%=WebUtil.display(_hov_widthValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovDisplay_field">
    <div id="linkStyleConfigForm_hovDisplay_label" class="formLabel" >Hov Display </div>
    <div id="linkStyleConfigForm_hovDisplay_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovDisplay" value="<%=WebUtil.display(_hov_displayValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovBorder_field">
    <div id="linkStyleConfigForm_hovBorder_label" class="formLabel" >Hov Border </div>
    <div id="linkStyleConfigForm_hovBorder_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovBorder" value="<%=WebUtil.display(_hov_borderValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovBackground_field">
    <div id="linkStyleConfigForm_hovBackground_label" class="formLabel" >Hov Background </div>
    <div id="linkStyleConfigForm_hovBackground_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovBackground" value="<%=WebUtil.display(_hov_backgroundValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovColor_field">
    <div id="linkStyleConfigForm_hovColor_label" class="formLabel" >Hov Color </div>
    <div id="linkStyleConfigForm_hovColor_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovColor" value="<%=WebUtil.display(_hov_colorValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovTextDecoration_field">
    <div id="linkStyleConfigForm_hovTextDecoration_label" class="formLabel" >Hov Text Decoration </div>
    <div id="linkStyleConfigForm_hovTextDecoration_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovTextDecoration" value="<%=WebUtil.display(_hov_text_decorationValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovTextAlign_field">
    <div id="linkStyleConfigForm_hovTextAlign_label" class="formLabel" >Hov Text Align </div>
    <div id="linkStyleConfigForm_hovTextAlign_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovTextAlign" value="<%=WebUtil.display(_hov_text_alignValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovVerticalAlign_field">
    <div id="linkStyleConfigForm_hovVerticalAlign_label" class="formLabel" >Hov Vertical Align </div>
    <div id="linkStyleConfigForm_hovVerticalAlign_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovVerticalAlign" value="<%=WebUtil.display(_hov_vertical_alignValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovTextIndent_field">
    <div id="linkStyleConfigForm_hovTextIndent_label" class="formLabel" >Hov Text Indent </div>
    <div id="linkStyleConfigForm_hovTextIndent_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovTextIndent" value="<%=WebUtil.display(_hov_text_indentValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovMargin_field">
    <div id="linkStyleConfigForm_hovMargin_label" class="formLabel" >Hov Margin </div>
    <div id="linkStyleConfigForm_hovMargin_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovMargin" value="<%=WebUtil.display(_hov_marginValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovPadding_field">
    <div id="linkStyleConfigForm_hovPadding_label" class="formLabel" >Hov Padding </div>
    <div id="linkStyleConfigForm_hovPadding_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovPadding" value="<%=WebUtil.display(_hov_paddingValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovFont_field">
    <div id="linkStyleConfigForm_hovFont_label" class="formLabel" >Hov Font </div>
    <div id="linkStyleConfigForm_hovFont_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovFont" value="<%=WebUtil.display(_hov_fontValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_hovExtraStyle_field">
    <div id="linkStyleConfigForm_hovExtraStyle_label" class="formLabel" >Hov Extra Style </div>
    <div id="linkStyleConfigForm_hovExtraStyle_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="hovExtraStyle" value="<%=WebUtil.display(_hov_extra_styleValue)%>"/>
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
    <div id="linkStyleConfigForm_activeBorder_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="activeBorder" value="<%=WebUtil.display(_active_borderValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_activeBackground_field">
    <div id="linkStyleConfigForm_activeBackground_label" class="formLabel" >Active Background </div>
    <div id="linkStyleConfigForm_activeBackground_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="activeBackground" value="<%=WebUtil.display(_active_backgroundValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_activeColor_field">
    <div id="linkStyleConfigForm_activeColor_label" class="formLabel" >Active Color </div>
    <div id="linkStyleConfigForm_activeColor_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="activeColor" value="<%=WebUtil.display(_active_colorValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_activeTextDecoration_field">
    <div id="linkStyleConfigForm_activeTextDecoration_label" class="formLabel" >Active Text Decoration </div>
    <div id="linkStyleConfigForm_activeTextDecoration_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="activeTextDecoration" value="<%=WebUtil.display(_active_text_decorationValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_activeExtraStyle_field">
    <div id="linkStyleConfigForm_activeExtraStyle_label" class="formLabel" >Active Extra Style </div>
    <div id="linkStyleConfigForm_activeExtraStyle_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="activeExtraStyle" value="<%=WebUtil.display(_active_extra_styleValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_activeMargin_field">
    <div id="linkStyleConfigForm_activeMargin_label" class="formLabel" >Active Margin </div>
    <div id="linkStyleConfigForm_activeMargin_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="activeMargin" value="<%=WebUtil.display(_active_marginValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="linkStyleConfigForm_activePadding_field">
    <div id="linkStyleConfigForm_activePadding_label" class="formLabel" >Active Padding </div>
    <div id="linkStyleConfigForm_activePadding_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="activePadding" value="<%=WebUtil.display(_active_paddingValue)%>"/>
    </div>      
	</div><div class="clear"></div>









        <div id="linkStyleConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.linkStyleConfigForm.submit();">Submit</a>
        </div>      

        <div id="linkStyleConfigForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.linkStyleConfigForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="linkStyleConfigForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        LinkStyleConfig _LinkStyleConfig = (LinkStyleConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _LinkStyleConfig.getId() %> </td>

    <td> <%= WebUtil.display(_LinkStyleConfig.getStyleKey()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHeight()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getWidth()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getDisplay()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getBorder()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getBackground()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getColor()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getTextDecoration()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getTextAlign()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getVerticalAlign()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getTextIndent()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getMargin()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getPadding()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getExtraStyle()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovHeight()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovWidth()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovDisplay()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovBorder()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovBackground()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovColor()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovTextDecoration()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovTextAlign()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovVerticalAlign()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovTextIndent()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovMargin()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovPadding()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovExtraStyle()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getActiveUse()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getActiveBorder()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getActiveBackground()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getActiveColor()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getActiveTextDecoration()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getActiveExtraStyle()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getActiveMargin()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getActivePadding()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getTimeUpdated()) %></td>


<td>
<form name="linkStyleConfigForm<%=_LinkStyleConfig.getId()%>2" method="get" action="/v_link_style_config_edit2.html" >
    <a href="javascript:document.linkStyleConfigForm<%=_LinkStyleConfig.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _LinkStyleConfig.getId() %>">
</form>

</td>
<td>
<a href="/linkStyleConfigAction.html?del=true&id=<%=_LinkStyleConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>