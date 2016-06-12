<%@page language="java" import="java.util.*,com.jtrend.session.*,com.autosite.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<br/><br/>
<div id="sweepRegisterForm_topArea" class="formTopArea"></div>
<h3> Login if you have registered before</h3>
<div id="registerSimpleForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="loginFormSubmitForm" method="get" action="/loginFormSubmit.html" >

	<div id="registerSimpleForm_username_field">
    <div id="registerSimpleForm_username_label" class="formRequiredLabel" >Username* </div>
    <div id="registerSimpleForm_username_text" class="formFieldText" > <span></span>     
        <input id="requiredField" type="text" style="width: 150px;" name="username" value=""/> 
    </div>      
	</div><div class="clear"></div>


	<div id="registerSimpleForm_password_field">
    <div id="registerSimpleForm_password_label" class="formRequiredLabel" >Password* </div>
    <div id="registerSimpleForm_password_text" class="formFieldText" >  <span></span>      
        <input id="requiredField" type="password" style="width: 150px;" name="password" value=""/>
    </div>      
	</div><div class="clear"></div>

        <div id="registerSimpleForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.loginFormSubmitForm.submit();">Submit</a>
        </div>

        <div style="float:left;margin:15px 0px 0px 160px;font-size: 12px">       
            <a href="/v_requestPassword.html" > Forgot your password?</a>
        </div>      
              
</form>
</div> <!-- form -->

<br/><br/>
 
<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SweepRegister _SweepRegisterDefault = new SweepRegister();// SweepRegisterDS.getInstance().getDeafult();
    
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_SweepRegisterDefault.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _passwordValue= (reqParams.get("password")==null?WebUtil.display(_SweepRegisterDefault.getPassword()):WebUtil.display((String)reqParams.get("password")));
    String _sexValue= (reqParams.get("sex")==null?WebUtil.display(_SweepRegisterDefault.getSex()):WebUtil.display((String)reqParams.get("sex")));
    String _age_rangeValue= (reqParams.get("ageRange")==null?WebUtil.display(_SweepRegisterDefault.getAgeRange()):WebUtil.display((String)reqParams.get("ageRange")));
    String _agree_termsValue= (reqParams.get("agreeTerms")==null?WebUtil.display(_SweepRegisterDefault.getAgreeTerms()):WebUtil.display((String)reqParams.get("agreeTerms")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SweepRegisterDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="sweepRegisterForm_topArea" class="formTopArea"></div>
<h3> Register your email</h3>

<div id="sweepRegisterForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="sweepRegisterForm" method="get" action="/sweepRegisterAction.html" >




	<div id="sweepRegisterForm_email_field">
    <div id="sweepRegisterForm_email_label" class="formRequiredLabel" >Email* </div>
    <div id="sweepRegisterForm_email_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="email" value="<%=WebUtil.display(_emailValue)%>"/> 
    </div>      
	</div><div class="clear"></div>



	<div id="sweepRegisterForm_password_field">
    <div id="sweepRegisterForm_password_label" class="formRequiredLabel" >Password* </div>
    <div id="sweepRegisterForm_password_password" class="formFieldPassword" > <span></span>      
        <input id="requiredField" type="password" size="30" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="sweepRegisterForm_sex_field">
    <div id="sweepRegisterForm_sex_label" class="formLabel" >Gender </div>
    <div id="sweepRegisterForm_sex_dropdown" class="formFieldDropDown" >       
        <select id="field" name="sex">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _sexValue)%>>XX</option-->
        <option value="Male" <%=HtmlUtil.getOptionSelect("Male", _sexValue)%>>Male</option>
        <option value="Female" <%=HtmlUtil.getOptionSelect("Female", _sexValue)%>>Female</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepRegisterForm_ageRange_field">
    <div id="sweepRegisterForm_ageRange_label" class="formLabel" >Age Range </div>
    <div id="sweepRegisterForm_ageRange_dropdown" class="formFieldDropDown" >       
        <select id="field" name="ageRange">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _age_rangeValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _age_rangeValue)%>>Under 20</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _age_rangeValue)%>>20 To 35</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _age_rangeValue)%>>36 To 45</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _age_rangeValue)%>>46 To 59</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _age_rangeValue)%>>Over 60</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepRegisterForm_agreeTerms_field">
    <div id="sweepRegisterForm_agreeTerms_label" class="formRequiredLabel" >Agree Terms* </div>
    <div id="sweepRegisterForm_agreeTerms_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" id="requiredField" name="agreeTerms" checked="on" />
    </div>      
	</div><div class="clear"></div>






        <div id="sweepRegisterForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepRegisterForm.submit();">Submit</a>
        </div>      

        <div id="sweepRegisterForm_cancel" class="formCancel" >       
            <a href="/home.html">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="sweepRegisterForm_bottomArea" class="formBottomArea"></div>
	