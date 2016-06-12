<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	Map reqParams = (Map) session.getAttribute("k_reserved_params");

	StyleConfig _StyleConfigDefault = StyleConfigUtil.getDefault(); 

    String _style_keyValue= (reqParams.get("styleKey")==null?WebUtil.display(_StyleConfigDefault.getStyleKey()):WebUtil.display((String)reqParams.get("styleKey")));
    String _style_useValue= (reqParams.get("styleUse")==null?WebUtil.display(_StyleConfigDefault.getStyleUse()):WebUtil.display((String)reqParams.get("styleUse")));
    String _is_globalValue= (reqParams.get("isGlobal")==null?WebUtil.display(_StyleConfigDefault.getIsGlobal()):WebUtil.display((String)reqParams.get("isGlobal")));
    String _id_classValue= (reqParams.get("idClass")==null?WebUtil.display(_StyleConfigDefault.getIdClass()):WebUtil.display((String)reqParams.get("idClass")));
    String _is_idValue= (reqParams.get("isId")==null?WebUtil.display(_StyleConfigDefault.getIsId()):WebUtil.display((String)reqParams.get("isId")));
    String _bg_colorValue= (reqParams.get("bgColor")==null?WebUtil.display(_StyleConfigDefault.getBgColor()):WebUtil.display((String)reqParams.get("bgColor")));
    String _bg_imageValue= (reqParams.get("bgImage")==null?WebUtil.display(_StyleConfigDefault.getBgImage()):WebUtil.display((String)reqParams.get("bgImage")));
    String _bg_repeatValue= (reqParams.get("bgRepeat")==null?WebUtil.display(_StyleConfigDefault.getBgRepeat()):WebUtil.display((String)reqParams.get("bgRepeat")));
    String _bg_attachValue= (reqParams.get("bgAttach")==null?WebUtil.display(_StyleConfigDefault.getBgAttach()):WebUtil.display((String)reqParams.get("bgAttach")));
    String _bg_positionValue= (reqParams.get("bgPosition")==null?WebUtil.display(_StyleConfigDefault.getBgPosition()):WebUtil.display((String)reqParams.get("bgPosition")));
    String _text_alignValue= (reqParams.get("textAlign")==null?WebUtil.display(_StyleConfigDefault.getTextAlign()):WebUtil.display((String)reqParams.get("textAlign")));
    String _font_familyValue= (reqParams.get("fontFamily")==null?WebUtil.display(_StyleConfigDefault.getFontFamily()):WebUtil.display((String)reqParams.get("fontFamily")));
    String _font_sizeValue= (reqParams.get("fontSize")==null?WebUtil.display(_StyleConfigDefault.getFontSize()):WebUtil.display((String)reqParams.get("fontSize")));
    String _font_styleValue= (reqParams.get("fontStyle")==null?WebUtil.display(_StyleConfigDefault.getFontStyle()):WebUtil.display((String)reqParams.get("fontStyle")));
    String _font_variantValue= (reqParams.get("fontVariant")==null?WebUtil.display(_StyleConfigDefault.getFontVariant()):WebUtil.display((String)reqParams.get("fontVariant")));
    String _font_weightValue= (reqParams.get("fontWeight")==null?WebUtil.display(_StyleConfigDefault.getFontWeight()):WebUtil.display((String)reqParams.get("fontWeight")));
    String _border_directionValue= (reqParams.get("borderDirection")==null?WebUtil.display(_StyleConfigDefault.getBorderDirection()):WebUtil.display((String)reqParams.get("borderDirection")));
    String _border_widthValue= (reqParams.get("borderWidth")==null?WebUtil.display(_StyleConfigDefault.getBorderWidth()):WebUtil.display((String)reqParams.get("borderWidth")));
    String _border_styleValue= (reqParams.get("borderStyle")==null?WebUtil.display(_StyleConfigDefault.getBorderStyle()):WebUtil.display((String)reqParams.get("borderStyle")));
    String _border_colorValue= (reqParams.get("borderColor")==null?WebUtil.display(_StyleConfigDefault.getBorderColor()):WebUtil.display((String)reqParams.get("borderColor")));
    String _marginValue= (reqParams.get("margin")==null?WebUtil.display(_StyleConfigDefault.getMargin()):WebUtil.display((String)reqParams.get("margin")));
    String _paddingValue= (reqParams.get("padding")==null?WebUtil.display(_StyleConfigDefault.getPadding()):WebUtil.display((String)reqParams.get("padding")));
    String _list_style_typeValue= (reqParams.get("listStyleType")==null?WebUtil.display(_StyleConfigDefault.getListStyleType()):WebUtil.display((String)reqParams.get("listStyleType")));
    String _list_style_positionValue= (reqParams.get("listStylePosition")==null?WebUtil.display(_StyleConfigDefault.getListStylePosition()):WebUtil.display((String)reqParams.get("listStylePosition")));
    String _list_style_imageValue= (reqParams.get("listStyleImage")==null?WebUtil.display(_StyleConfigDefault.getListStyleImage()):WebUtil.display((String)reqParams.get("listStyleImage")));
    String _floatingValue= (reqParams.get("floating")==null?WebUtil.display(_StyleConfigDefault.getFloating()):WebUtil.display((String)reqParams.get("floating")));
    String _extra_style_strValue= (reqParams.get("extraStyleStr")==null?WebUtil.display(_StyleConfigDefault.getExtraStyleStr()):WebUtil.display((String)reqParams.get("extraStyleStr")));
    String _item_style_strValue= (reqParams.get("itemStyleStr")==null?WebUtil.display(_StyleConfigDefault.getItemStyleStr()):WebUtil.display((String)reqParams.get("itemStyleStr")));
    String _linkValue= (reqParams.get("link")==null?WebUtil.display(_StyleConfigDefault.getLink()):WebUtil.display((String)reqParams.get("link")));
    String _link_hoverValue= (reqParams.get("linkHover")==null?WebUtil.display(_StyleConfigDefault.getLinkHover()):WebUtil.display((String)reqParams.get("linkHover")));
    String _link_activeValue= (reqParams.get("linkActive")==null?WebUtil.display(_StyleConfigDefault.getLinkActive()):WebUtil.display((String)reqParams.get("linkActive")));
    String _heightValue= (reqParams.get("height")==null?WebUtil.display(_StyleConfigDefault.getHeight()):WebUtil.display((String)reqParams.get("height")));
    String _widthValue= (reqParams.get("width")==null?WebUtil.display(_StyleConfigDefault.getWidth()):WebUtil.display((String)reqParams.get("width")));
	String _time_createdValue= "";
	String _time_updatedValue= "";

	String _wpId = WebProcManager.registerWebProcess();
	String retPage = (String) reqParams.get("returnPage");    

	
	//>>START<<
	//===============
	
	String panelId =  (String) reqParams.get("panel_id");

	if (WebUtil.isNull(_style_keyValue)) {
		//_style_keyValue = "" + System.currentTimeMillis();
		_style_keyValue = panelId;
		_style_keyValue = "PanelStyle-" + site.getId() + ":" + _style_keyValue+ ":" + System.currentTimeMillis();
//		System.out.println(_style_keyValue);
	}

	// Check if whther it is site default	
	String cmd =  (String) request.getParameter("cmd");

	boolean isSiteDefaultStyleRequest = false;
	boolean isSiteDefaultLinkStyleRequest = false;
	
	if (WebUtil.checkValue(cmd, "siteDefaultStyle")) { 
		isSiteDefaultStyleRequest = true;
		_style_keyValue = "SiteDefault:"+site.getId();
	}
	
	
	//>>END<<
		 
%> 
<br>
<div id="styleConfigForm_topArea" class="formTopArea"></div>
<div id="styleConfigForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="styleConfigForm" method="POST" action="/styleConfigAction.html" >




	<div id="styleConfigForm_styleKey_field">
    <div id="styleConfigForm_styleKey_label" class="formLabel" >Style Key </div>
    <div id="styleConfigForm_styleKey_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="styleKey" value="<%=WebUtil.display(_style_keyValue)%>"/>
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
    <div id="styleConfigForm_idClass_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="idClass" value="<%=WebUtil.display(_id_classValue)%>"/>
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
    <div id="styleConfigForm_bgColor_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="bgColor" value="<%=WebUtil.display(_bg_colorValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_bgImage_field">
    <div id="styleConfigForm_bgImage_label" class="formLabel" >Bg Image </div>
    <div id="styleConfigForm_bgImage_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="bgImage" value="<%=WebUtil.display(_bg_imageValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_bgRepeat_field">
    <div id="styleConfigForm_bgRepeat_label" class="formLabel" >Bg Repeat </div>
    <div id="styleConfigForm_bgRepeat_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="bgRepeat" value="<%=WebUtil.display(_bg_repeatValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_bgAttach_field">
    <div id="styleConfigForm_bgAttach_label" class="formLabel" >Bg Attach </div>
    <div id="styleConfigForm_bgAttach_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="bgAttach" value="<%=WebUtil.display(_bg_attachValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_bgPosition_field">
    <div id="styleConfigForm_bgPosition_label" class="formLabel" >Bg Position </div>
    <div id="styleConfigForm_bgPosition_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="bgPosition" value="<%=WebUtil.display(_bg_positionValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_textAlign_field">
    <div id="styleConfigForm_textAlign_label" class="formLabel" >Text Align </div>
    <div id="styleConfigForm_textAlign_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="textAlign" value="<%=WebUtil.display(_text_alignValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_fontFamily_field">
    <div id="styleConfigForm_fontFamily_label" class="formLabel" >Font Family </div>
    <div id="styleConfigForm_fontFamily_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="fontFamily" value="<%=WebUtil.display(_font_familyValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_fontSize_field">
    <div id="styleConfigForm_fontSize_label" class="formLabel" >Font Size </div>
    <div id="styleConfigForm_fontSize_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="fontSize" value="<%=WebUtil.display(_font_sizeValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_fontStyle_field">
    <div id="styleConfigForm_fontStyle_label" class="formLabel" >Font Style </div>
    <div id="styleConfigForm_fontStyle_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="fontStyle" value="<%=WebUtil.display(_font_styleValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_fontVariant_field">
    <div id="styleConfigForm_fontVariant_label" class="formLabel" >Font Variant </div>
    <div id="styleConfigForm_fontVariant_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="fontVariant" value="<%=WebUtil.display(_font_variantValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_fontWeight_field">
    <div id="styleConfigForm_fontWeight_label" class="formLabel" >Font Weight </div>
    <div id="styleConfigForm_fontWeight_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="fontWeight" value="<%=WebUtil.display(_font_weightValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_borderDirection_field">
    <div id="styleConfigForm_borderDirection_label" class="formLabel" >Border Direction </div>
    <div id="styleConfigForm_borderDirection_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="borderDirection" value="<%=WebUtil.display(_border_directionValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_borderWidth_field">
    <div id="styleConfigForm_borderWidth_label" class="formLabel" >Border Width </div>
    <div id="styleConfigForm_borderWidth_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="borderWidth" value="<%=WebUtil.display(_border_widthValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_borderStyle_field">
    <div id="styleConfigForm_borderStyle_label" class="formLabel" >Border Style </div>
    <div id="styleConfigForm_borderStyle_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="borderStyle" value="<%=WebUtil.display(_border_styleValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_borderColor_field">
    <div id="styleConfigForm_borderColor_label" class="formLabel" >Border Color </div>
    <div id="styleConfigForm_borderColor_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="borderColor" value="<%=WebUtil.display(_border_colorValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_margin_field">
    <div id="styleConfigForm_margin_label" class="formLabel" >Margin </div>
    <div id="styleConfigForm_margin_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="margin" value="<%=WebUtil.display(_marginValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_padding_field">
    <div id="styleConfigForm_padding_label" class="formLabel" >Padding </div>
    <div id="styleConfigForm_padding_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="padding" value="<%=WebUtil.display(_paddingValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_listStyleType_field">
    <div id="styleConfigForm_listStyleType_label" class="formLabel" >List Style Type </div>
    <div id="styleConfigForm_listStyleType_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="listStyleType" value="<%=WebUtil.display(_list_style_typeValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_listStylePosition_field">
    <div id="styleConfigForm_listStylePosition_label" class="formLabel" >List Style Position </div>
    <div id="styleConfigForm_listStylePosition_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="listStylePosition" value="<%=WebUtil.display(_list_style_positionValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_listStyleImage_field">
    <div id="styleConfigForm_listStyleImage_label" class="formLabel" >List Style Image </div>
    <div id="styleConfigForm_listStyleImage_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="listStyleImage" value="<%=WebUtil.display(_list_style_imageValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_floating_field">
    <div id="styleConfigForm_floating_label" class="formLabel" >Floating </div>
    <div id="styleConfigForm_floating_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="floating" value="<%=WebUtil.display(_floatingValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_extraStyleStr_field">
    <div id="styleConfigForm_extraStyleStr_label" class="formLabel" >Extra Style Str </div>
    <div id="styleConfigForm_extraStyleStr_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="extraStyleStr" value="<%=WebUtil.display(_extra_style_strValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_itemStyleStr_field">
    <div id="styleConfigForm_itemStyleStr_label" class="formLabel" >Item Style Str </div>
    <div id="styleConfigForm_itemStyleStr_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="itemStyleStr" value="<%=WebUtil.display(_item_style_strValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_link_field">
    <div id="styleConfigForm_link_label" class="formLabel" >Link </div>
    <div id="styleConfigForm_link_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="link" value="<%=WebUtil.display(_linkValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_linkHover_field">
    <div id="styleConfigForm_linkHover_label" class="formLabel" >Link Hover </div>
    <div id="styleConfigForm_linkHover_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="linkHover" value="<%=WebUtil.display(_link_hoverValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_linkActive_field">
    <div id="styleConfigForm_linkActive_label" class="formLabel" >Link Active </div>
    <div id="styleConfigForm_linkActive_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="linkActive" value="<%=WebUtil.display(_link_activeValue)%>"/>
    </div>      
	</div><div class="clear"></div>

	<div id="styleConfigForm_height_field">
    <div id="styleConfigForm_height_label" class="formLabel" >Height </div>
    <div id="styleConfigForm_height_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="height" value="<%=WebUtil.display(_heightValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_width_field">
    <div id="styleConfigForm_width_label" class="formLabel" >Width </div>
    <div id="styleConfigForm_width_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="width" value="<%=WebUtil.display(_widthValue)%>"/>
    </div>      
	</div><div class="clear"></div>








        <div id="styleConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.styleConfigForm.submit();">Submit</a>
        </div>      

        <div id="styleConfigForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.styleConfigForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="styleConfigForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = StyleConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleConfig _StyleConfig = (StyleConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _StyleConfig.getId() %> </td>

    <td> <%= WebUtil.display(_StyleConfig.getStyleKey()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getStyleUse()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getIdClass()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getIsId()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBgColor()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBgImage()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBgRepeat()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBgAttach()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBgPosition()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getTextAlign()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getFontFamily()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getFontSize()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getFontStyle()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getFontVariant()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getFontWeight()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBorderDirection()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBorderWidth()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBorderStyle()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBorderColor()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getMargin()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getPadding()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getListStyleType()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getListStylePosition()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getListStyleImage()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getFloating()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getExtraStyleStr()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getItemStyleStr()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getLink()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getLinkHover()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getLinkActive()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getTimeUpdated()) %></td>


<td>
<form name="styleConfigForm<%=_StyleConfig.getId()%>2" method="get" action="/v_style_config_edit2.html" >
    <a href="javascript:document.styleConfigForm<%=_StyleConfig.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _StyleConfig.getId() %>">
</form>

</td>
<td>
<a href="/styleConfigAction.html?del=true&id=<%=_StyleConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>