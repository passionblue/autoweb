<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _theme_nameValue= "";
    String _layout_pageValue= "";
    String _css_indexValue= "";
    String _theme_style_idValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";

%>

<div id="partitionFormFrame_theme_aggregator_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /ThemeAggregator_artition.jsp -->

	<script type="text/javascript">
		function sendForm_theme_aggregator_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="themeAggregatorForm_themeName_field" class="formFieldFrame">
    <div id="themeAggregatorForm_themeName_label" class="formLabel" >Theme Name </div>
    <div id="themeAggregatorForm_themeName_text" class="formFieldText" >       
        <input id="themeName" class="field" type="text" size="70" name="themeName" value="<%=WebUtil.display(_theme_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeAggregatorForm_layoutPage_field" class="formFieldFrame">
    <div id="themeAggregatorForm_layoutPage_label" class="formLabel" >Layout Page </div>
    <div id="themeAggregatorForm_layoutPage_text" class="formFieldText" >       
        <input id="layoutPage" class="field" type="text" size="70" name="layoutPage" value="<%=WebUtil.display(_layout_pageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeAggregatorForm_cssIndex_field" class="formFieldFrame">
    <div id="themeAggregatorForm_cssIndex_label" class="formLabel" >Css Index </div>
    <div id="themeAggregatorForm_cssIndex_text" class="formFieldText" >       
        <input id="cssIndex" class="field" type="text" size="70" name="cssIndex" value="<%=WebUtil.display(_css_indexValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


  	<div id="themeAggregatorForm_themeStyleId_field" class="formFieldFrame">
    <div id="themeAggregatorForm_themeStyleId_label" class="formLabel" >Theme Style Id </div>
    <div id="themeAggregatorForm_themeStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="themeStyleId" id="themeStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listThemeStyles_themeStyleId = ThemeStylesDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listThemeStyles_themeStyleId.iterator(); iter.hasNext();){
		ThemeStyles _obj = (ThemeStyles) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_theme_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getId() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeAggregatorForm_timeCreated_field" class="formFieldFrame">
    <div id="themeAggregatorForm_timeCreated_label" class="formLabel" >Time Created </div>
    <div id="themeAggregatorForm_timeCreated_text" class="formFieldText" >       
        <input id="timeCreated" class="field" type="text" size="70" name="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeAggregatorForm_timeUpdated_field" class="formFieldFrame">
    <div id="themeAggregatorForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="themeAggregatorForm_timeUpdated_text" class="formFieldText" >       
        <input id="timeUpdated" class="field" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_theme_aggregator_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
