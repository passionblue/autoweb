<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _addressValue= "";
    String _city_zipValue= "";
    String _phoneValue= "";
    String _manager_idValue= "";

%>

<div id="partitionFormFrame_cleaner_location_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerLocation_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_location_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">

#macro_jspelements( [{displayInUpper=ADDRESS, isFormIgnore=false, isRadio=false, isBoolean=false, isPassword=false, isImmutable=false, titleInUpper=ADDRESS, type=String, isDropDown=false, title=address, isDropDownAutoLoad=false, isAjaxForm=true, name=Address, isNoPersist=false, isWyswygTextArea=false, isId=false, isAutosetUpdateDate=false, isCheckbox=false, isAutosetDate=false, prop=address, display=Address, isTextArea=false, isDsShortField=false, isAutosetCreateDate=false, isDate=false, displayInLower=address, size=70, isHidden=false, isDbField=true, isRequired=false, javaType=String, javaName=address, name2=address}, {displayInUpper=CITY ZIP, isFormIgnore=false, isRadio=false, isBoolean=false, isPassword=false, isImmutable=false, titleInUpper=CITY_ZIP, type=String, isDropDown=false, title=city_zip, isDropDownAutoLoad=false, isAjaxForm=true, name=CityZip, isNoPersist=false, isWyswygTextArea=false, isId=false, isAutosetUpdateDate=false, isCheckbox=false, isAutosetDate=false, prop=city_zip, display=City Zip, isTextArea=false, isDsShortField=false, isAutosetCreateDate=false, isDate=false, displayInLower=city zip, size=70, isHidden=false, isDbField=true, isRequired=false, javaType=String, javaName=cityZip, name2=cityZip}, {displayInUpper=PHONE, isFormIgnore=false, isRadio=false, isBoolean=false, isPassword=false, isImmutable=false, titleInUpper=PHONE, type=String, isDropDown=false, title=phone, isDropDownAutoLoad=false, isAjaxForm=true, name=Phone, isNoPersist=false, isWyswygTextArea=false, isId=false, isAutosetUpdateDate=false, isCheckbox=false, isAutosetDate=false, prop=phone, display=Phone, isTextArea=false, isDsShortField=false, isAutosetCreateDate=false, isDate=false, displayInLower=phone, size=70, isHidden=false, isDbField=true, isRequired=false, javaType=String, javaName=phone, name2=phone}, {displayInUpper=MANAGER ID, isFormIgnore=false, isRadio=false, isBoolean=false, isPassword=false, isImmutable=false, titleInUpper=MANAGER_ID, type=Long, isDropDown=false, title=manager_id, isDropDownAutoLoad=false, isAjaxForm=true, name=ManagerId, isNoPersist=false, isWyswygTextArea=false, isId=false, isAutosetUpdateDate=false, isCheckbox=false, isAutosetDate=false, prop=manager_id, display=Manager Id, isTextArea=false, isDsShortField=false, isAutosetCreateDate=false, isDate=false, displayInLower=manager id, size=70, isHidden=false, isDbField=true, isRequired=false, javaType=long, javaName=managerId, name2=managerId}] )
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_location_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
