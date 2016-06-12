<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _countryValue= "";
    String _ageValue= "";
    String _disabledValue= "";
    String _commentsValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";
    String _ext_stringValue= "";
    String _ext_intValue= "";

%>

<div id="partitionFormFrame_gen_table_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /GenTable_artition.jsp -->

	<script type="text/javascript">
		function sendForm_gen_table_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">



	<div id="genTableForm_country_field" class="formFieldFrame">
    <div id="genTableForm_country_label" class="formLabel" >Country </div>
    <div id="genTableForm_country_dropdown" class="formFieldDropDown" >       
        <select class="field" name="country" id="country">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _countryValue)%>>XX</option-->
        <option value="usa" <%=HtmlUtil.getOptionSelect("usa", _countryValue)%>>United States</option>
        <option value="korea" <%=HtmlUtil.getOptionSelect("korea", _countryValue)%>>SouthKorea</option>
        <option value="congo" <%=HtmlUtil.getOptionSelect("congo", _countryValue)%>>Congo</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="genTableForm_age_field" class="formFieldFrame">
    <div id="genTableForm_age_label" class="formLabel" >Age </div>
    <div id="genTableForm_age_dropdown" class="formFieldDropDown" >       
        <select class="field" name="age" id="age">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _ageValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="genTableForm_disabled_field" class="formFieldFrame">
    <div id="genTableForm_disabled_label" class="formLabel" >Disabled </div>
    <div id="genTableForm_disabled_dropdown" class="formFieldDropDown" >       
        <select name="disabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>



	<div id="genTableForm_comments_field" class="formFieldFrame">
    <div id="genTableForm_comments_label" class="formLabel" >Comments </div>
    <div id="genTableForm_comments_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="comments" class="field" NAME="comments" COLS="50" ROWS="8" ><%=WebUtil.display(_commentsValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>











	<div id="genTableForm_extString_field" class="formFieldFrame">
    <div id="genTableForm_extString_label" class="formLabel" >ExtraString </div>
    <div id="genTableForm_extString_text" class="formFieldText" >       
        <input id="extString" class="field" type="text" size="3" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="genTableForm_extInt_field" class="formFieldFrame">
    <div id="genTableForm_extInt_label" class="formLabel" >Ext Int </div>
    <div id="genTableForm_extInt_text" class="formFieldText" >       
        <input id="extInt" class="field" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_gen_table_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
