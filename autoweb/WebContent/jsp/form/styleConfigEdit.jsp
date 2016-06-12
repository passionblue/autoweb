<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    Map reqParams = (Map) session.getAttribute("k_reserved_params");
	String retPage = (String) reqParams.get("returnPage");    

	long id = Long.parseLong(idStr);

	StyleConfig _StyleConfig = StyleConfigDS.getInstance().getById(id);

	if ( _StyleConfig == null ) {
		return;
	}

    String _style_keyValue=  WebUtil.display(_StyleConfig.getStyleKey());
    String _style_useValue=  WebUtil.display(_StyleConfig.getStyleUse());
    String _is_globalValue=  WebUtil.display(_StyleConfig.getIsGlobal());
    String _id_classValue=  WebUtil.display(_StyleConfig.getIdClass());
    String _is_idValue=  WebUtil.display(_StyleConfig.getIsId());
    String _bg_colorValue=  WebUtil.display(_StyleConfig.getBgColor());
    String _bg_imageValue=  WebUtil.display(_StyleConfig.getBgImage());
    String _bg_repeatValue=  WebUtil.display(_StyleConfig.getBgRepeat());
    String _bg_attachValue=  WebUtil.display(_StyleConfig.getBgAttach());
    String _bg_positionValue=  WebUtil.display(_StyleConfig.getBgPosition());
    String _text_alignValue=  WebUtil.display(_StyleConfig.getTextAlign());
    String _font_familyValue=  WebUtil.display(_StyleConfig.getFontFamily());
    String _font_sizeValue=  WebUtil.display(_StyleConfig.getFontSize());
    String _font_styleValue=  WebUtil.display(_StyleConfig.getFontStyle());
    String _font_variantValue=  WebUtil.display(_StyleConfig.getFontVariant());
    String _font_weightValue=  WebUtil.display(_StyleConfig.getFontWeight());
    String _border_directionValue=  WebUtil.display(_StyleConfig.getBorderDirection());
    String _border_widthValue=  WebUtil.display(_StyleConfig.getBorderWidth());
    String _border_styleValue=  WebUtil.display(_StyleConfig.getBorderStyle());
    String _border_colorValue=  WebUtil.display(_StyleConfig.getBorderColor());
    String _marginValue=  WebUtil.display(_StyleConfig.getMargin());
    String _paddingValue=  WebUtil.display(_StyleConfig.getPadding());
    String _list_style_typeValue=  WebUtil.display(_StyleConfig.getListStyleType());
    String _list_style_positionValue=  WebUtil.display(_StyleConfig.getListStylePosition());
    String _list_style_imageValue=  WebUtil.display(_StyleConfig.getListStyleImage());
    String _floatingValue=  WebUtil.display(_StyleConfig.getFloating());
    String _extra_style_strValue=  WebUtil.display(_StyleConfig.getExtraStyleStr());
    String _item_style_strValue=  WebUtil.display(_StyleConfig.getItemStyleStr());
    String _linkValue=  WebUtil.display(_StyleConfig.getLink());
    String _link_hoverValue=  WebUtil.display(_StyleConfig.getLinkHover());
    String _link_activeValue=  WebUtil.display(_StyleConfig.getLinkActive());
    String _heightValue=  WebUtil.display(_StyleConfig.getHeight());
    String _widthValue=  WebUtil.display(_StyleConfig.getWidth());
    
    String _time_createdValue=  WebUtil.display(_StyleConfig.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_StyleConfig.getTimeUpdated());
	
	//>>START<<
	
	List panelsForThis = PanelStyleDS.getInstance().getByStyleId(id);
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
		PanelStyle panelStyle = (PanelStyle) iter.next();
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
<div id="styleConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="styleConfigFormEdit" method="POST" action="/styleConfigAction.html" >




	<div id="styleConfigForm_styleKey_field">
    <div id="styleConfigForm_styleKey_label" class="formLabel" >Style Key </div>
    <div id="styleConfigForm_styleKey_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="styleKey" value="<%=WebUtil.display(_style_keyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="styleConfigForm_styleUse_field">
    <div id="styleConfigForm_styleUse_label" class="formLabel" >Style Use </div>
    <div id="styleConfigForm_styleUse_dropdown" class="formFieldDropDown" >       
        <select id="field" name="styleUse">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _style_useValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _style_useValue)%>>Default</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _style_useValue)%>>Custom</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _style_useValue)%>>TBD</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _style_useValue)%>>TBD</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _style_useValue)%>>TBD</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="styleConfigForm_isGlobal_field">
    <div id="styleConfigForm_isGlobal_label" class="formLabel" >Is Global </div>
    <div id="styleConfigForm_isGlobal_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="isGlobal" value="<%=WebUtil.display(_is_globalValue)%>"/>
    </div>      
	</div><div class="clear"></div>

	<div id="styleConfigForm_idClass_field">
    <div id="styleConfigForm_idClass_label" class="formLabel" >Id Class </div>
    <div id="styleConfigForm_idClass_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="idClass" value="<%=WebUtil.display(_id_classValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleConfigForm_isId_field">
    <div id="styleConfigForm_isId_label" class="formLabel" >Is Id </div>
    <div id="styleConfigForm_isId_dropdown" class="formFieldDropDown" >       
        <select name="isId">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_idValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_idValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="styleConfigForm_bgColor_field">
    <div id="styleConfigForm_bgColor_label" class="formLabel" >Bg Color </div>
    <div id="styleConfigForm_bgColor_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="bgColor" value="<%=WebUtil.display(_bg_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_bgImage_field">
    <div id="styleConfigForm_bgImage_label" class="formLabel" >Bg Image </div>
    <div id="styleConfigForm_bgImage_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="bgImage" value="<%=WebUtil.display(_bg_imageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_bgRepeat_field">
    <div id="styleConfigForm_bgRepeat_label" class="formLabel" >Bg Repeat </div>
    <div id="styleConfigForm_bgRepeat_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="bgRepeat" value="<%=WebUtil.display(_bg_repeatValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_bgAttach_field">
    <div id="styleConfigForm_bgAttach_label" class="formLabel" >Bg Attach </div>
    <div id="styleConfigForm_bgAttach_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="bgAttach" value="<%=WebUtil.display(_bg_attachValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_bgPosition_field">
    <div id="styleConfigForm_bgPosition_label" class="formLabel" >Bg Position </div>
    <div id="styleConfigForm_bgPosition_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="bgPosition" value="<%=WebUtil.display(_bg_positionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_textAlign_field">
    <div id="styleConfigForm_textAlign_label" class="formLabel" >Text Align </div>
    <div id="styleConfigForm_textAlign_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="textAlign" value="<%=WebUtil.display(_text_alignValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_fontFamily_field">
    <div id="styleConfigForm_fontFamily_label" class="formLabel" >Font Family </div>
    <div id="styleConfigForm_fontFamily_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="fontFamily" value="<%=WebUtil.display(_font_familyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_fontSize_field">
    <div id="styleConfigForm_fontSize_label" class="formLabel" >Font Size </div>
    <div id="styleConfigForm_fontSize_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="fontSize" value="<%=WebUtil.display(_font_sizeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_fontStyle_field">
    <div id="styleConfigForm_fontStyle_label" class="formLabel" >Font Style </div>
    <div id="styleConfigForm_fontStyle_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="fontStyle" value="<%=WebUtil.display(_font_styleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_fontVariant_field">
    <div id="styleConfigForm_fontVariant_label" class="formLabel" >Font Variant </div>
    <div id="styleConfigForm_fontVariant_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="fontVariant" value="<%=WebUtil.display(_font_variantValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_fontWeight_field">
    <div id="styleConfigForm_fontWeight_label" class="formLabel" >Font Weight </div>
    <div id="styleConfigForm_fontWeight_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="fontWeight" value="<%=WebUtil.display(_font_weightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_borderDirection_field">
    <div id="styleConfigForm_borderDirection_label" class="formLabel" >Border Direction </div>
    <div id="styleConfigForm_borderDirection_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="borderDirection" value="<%=WebUtil.display(_border_directionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_borderWidth_field">
    <div id="styleConfigForm_borderWidth_label" class="formLabel" >Border Width </div>
    <div id="styleConfigForm_borderWidth_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="borderWidth" value="<%=WebUtil.display(_border_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_borderStyle_field">
    <div id="styleConfigForm_borderStyle_label" class="formLabel" >Border Style </div>
    <div id="styleConfigForm_borderStyle_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="borderStyle" value="<%=WebUtil.display(_border_styleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_borderColor_field">
    <div id="styleConfigForm_borderColor_label" class="formLabel" >Border Color </div>
    <div id="styleConfigForm_borderColor_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="borderColor" value="<%=WebUtil.display(_border_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_margin_field">
    <div id="styleConfigForm_margin_label" class="formLabel" >Margin </div>
    <div id="styleConfigForm_margin_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="margin" value="<%=WebUtil.display(_marginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_padding_field">
    <div id="styleConfigForm_padding_label" class="formLabel" >Padding </div>
    <div id="styleConfigForm_padding_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="padding" value="<%=WebUtil.display(_paddingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_listStyleType_field">
    <div id="styleConfigForm_listStyleType_label" class="formLabel" >List Style Type </div>
    <div id="styleConfigForm_listStyleType_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="listStyleType" value="<%=WebUtil.display(_list_style_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_listStylePosition_field">
    <div id="styleConfigForm_listStylePosition_label" class="formLabel" >List Style Position </div>
    <div id="styleConfigForm_listStylePosition_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="listStylePosition" value="<%=WebUtil.display(_list_style_positionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_listStyleImage_field">
    <div id="styleConfigForm_listStyleImage_label" class="formLabel" >List Style Image </div>
    <div id="styleConfigForm_listStyleImage_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="listStyleImage" value="<%=WebUtil.display(_list_style_imageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_floating_field">
    <div id="styleConfigForm_floating_label" class="formLabel" >Floating </div>
    <div id="styleConfigForm_floating_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="floating" value="<%=WebUtil.display(_floatingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_extraStyleStr_field">
    <div id="styleConfigForm_extraStyleStr_label" class="formLabel" >Extra Style Str </div>
    <div id="styleConfigForm_extraStyleStr_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="extraStyleStr" value="<%=WebUtil.display(_extra_style_strValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_itemStyleStr_field">
    <div id="styleConfigForm_itemStyleStr_label" class="formLabel" >Item Style Str </div>
    <div id="styleConfigForm_itemStyleStr_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="itemStyleStr" value="<%=WebUtil.display(_item_style_strValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_link_field">
    <div id="styleConfigForm_link_label" class="formLabel" >Link </div>
    <div id="styleConfigForm_link_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="link" value="<%=WebUtil.display(_linkValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_linkHover_field">
    <div id="styleConfigForm_linkHover_label" class="formLabel" >Link Hover </div>
    <div id="styleConfigForm_linkHover_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="linkHover" value="<%=WebUtil.display(_link_hoverValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_linkActive_field">
    <div id="styleConfigForm_linkActive_label" class="formLabel" >Link Active </div>
    <div id="styleConfigForm_linkActive_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="linkActive" value="<%=WebUtil.display(_link_activeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="styleConfigForm_height_field">
    <div id="styleConfigForm_height_label" class="formLabel" >Height </div>
    <div id="styleConfigForm_height_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="height" value="<%=WebUtil.display(_heightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleConfigForm_width_field">
    <div id="styleConfigForm_width_label" class="formLabel" >Width </div>
    <div id="styleConfigForm_width_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="width" value="<%=WebUtil.display(_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




        <div id="styleConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.styleConfigFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
