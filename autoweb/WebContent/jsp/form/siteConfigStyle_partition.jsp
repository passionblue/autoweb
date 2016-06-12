<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _theme_idValue= "";
    String _css_indexValue= "";
    String _css_importValue= "";
    String _layout_indexValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";

%>

<div id="partitionFormFrame_site_config_style_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /SiteConfigStyle_artition.jsp -->

	<script type="text/javascript">
		function sendForm_site_config_style_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">



  	<div id="siteConfigStyleForm_themeId_field" class="formFieldFrame">
    <div id="siteConfigStyleForm_themeId_label" class="formLabel" >Theme Id </div>
    <div id="siteConfigStyleForm_themeId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="themeId" id="themeId">
        <option value="" >- Please Select -</option>
<%
	List _listThemeAggregator_themeId = ThemeAggregatorDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listThemeAggregator_themeId.iterator(); iter.hasNext();){
		ThemeAggregator _obj = (ThemeAggregator) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_theme_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getThemeName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="siteConfigStyleForm_cssIndex_field" class="formFieldFrame">
    <div id="siteConfigStyleForm_cssIndex_label" class="formLabel" >Css Index </div>
    <div id="siteConfigStyleForm_cssIndex_text" class="formFieldText" >       
        <input id="cssIndex" class="field" type="text" size="70" name="cssIndex" value="<%=WebUtil.display(_css_indexValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="siteConfigStyleForm_cssImport_field" class="formFieldFrame">
    <div id="siteConfigStyleForm_cssImport_label" class="formLabel" >Css Import </div>
    <div id="siteConfigStyleForm_cssImport_text" class="formFieldText" >       
        <input id="cssImport" class="field" type="text" size="70" name="cssImport" value="<%=WebUtil.display(_css_importValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="siteConfigStyleForm_layoutIndex_field" class="formFieldFrame">
    <div id="siteConfigStyleForm_layoutIndex_label" class="formLabel" >Layout Index </div>
    <div id="siteConfigStyleForm_layoutIndex_text" class="formFieldText" >       
        <input id="layoutIndex" class="field" type="text" size="70" name="layoutIndex" value="<%=WebUtil.display(_layout_indexValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>






		<!--
		<div class="ajaxFormLabel" style="font-weight:bold;">ExtraString</div>
		<INPUT NAME="extString" type="text" size="3" value=""></INPUT><br />

		<div class="ajaxFormLabel" style="font-weight:bold;">Ext Int</div>
		<INPUT NAME="extInt" type="text" size="70" value=""></INPUT><br /> 
		-->
		<INPUT TYPE="HIDDEN" NAME="ajxr" value="getmodalstatus">
		<INPUT TYPE="HIDDEN" NAME="add" value="true">
		<INPUT TYPE="HIDDEN" NAME="wpid" value="<%=_wpId%>">

	</form>

	<span id="ajaxSubmitResult<%= catchString %>"></span> 
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_site_config_style_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
