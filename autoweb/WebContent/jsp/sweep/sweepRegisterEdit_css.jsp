<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    SweepRegister _SweepRegister = SweepRegisterDS.getInstance().getById(id);

    if ( _SweepRegister == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _emailValue=  WebUtil.display(_SweepRegister.getEmail());
    String _passwordValue=  WebUtil.display(_SweepRegister.getPassword());
    String _sexValue=  WebUtil.display(_SweepRegister.getSex());
    String _age_rangeValue=  WebUtil.display(_SweepRegister.getAgeRange());
    String _agree_termsValue=  WebUtil.display(_SweepRegister.getAgreeTerms());
    String _time_createdValue=  WebUtil.display(_SweepRegister.getTimeCreated());
%> 

<br>
<div id="sweepRegisterForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="sweepRegisterFormEdit" method="get" action="/sweepRegisterAction.html" >




	<div id="sweepRegisterForm_email_field">
    <div id="sweepRegisterForm_email_label" class="formRequiredLabel" >Email* </div>
    <div id="sweepRegisterForm_email_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepRegisterForm_password_field">
    <div id="sweepRegisterForm_password_label" class="formRequiredLabel" >Password* </div>
    <div id="sweepRegisterForm_password_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="sweepRegisterForm_sex_field">
    <div id="sweepRegisterForm_sex_label" class="formLabel" >Gender </div>
    <div id="sweepRegisterForm_sex_dropdown" class="formFieldDropDown" >       
        <select id="field" name="sex">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _sexValue)%>>XX</option-->
        <option value="{displayString=Male, internalValue=Male}" <%=HtmlUtil.getOptionSelect("{displayString=Male, internalValue=Male}", _sexValue)%>>{displayString=Male, internalValue=Male}</option>
        <option value="{displayString=Female, internalValue=Female}" <%=HtmlUtil.getOptionSelect("{displayString=Female, internalValue=Female}", _sexValue)%>>{displayString=Female, internalValue=Female}</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="sweepRegisterForm_ageRange_field">
    <div id="sweepRegisterForm_ageRange_label" class="formLabel" >Age Range </div>
    <div id="sweepRegisterForm_ageRange_dropdown" class="formFieldDropDown" >       
        <select id="field" name="ageRange">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _age_rangeValue)%>>XX</option-->
        <option value="{displayString=Under 20, internalValue=1}" <%=HtmlUtil.getOptionSelect("{displayString=Under 20, internalValue=1}", _age_rangeValue)%>>{displayString=Under 20, internalValue=1}</option>
        <option value="{displayString=20 To 35, internalValue=2}" <%=HtmlUtil.getOptionSelect("{displayString=20 To 35, internalValue=2}", _age_rangeValue)%>>{displayString=20 To 35, internalValue=2}</option>
        <option value="{displayString=36 To 45, internalValue=3}" <%=HtmlUtil.getOptionSelect("{displayString=36 To 45, internalValue=3}", _age_rangeValue)%>>{displayString=36 To 45, internalValue=3}</option>
        <option value="{displayString=46 To 59, internalValue=4}" <%=HtmlUtil.getOptionSelect("{displayString=46 To 59, internalValue=4}", _age_rangeValue)%>>{displayString=46 To 59, internalValue=4}</option>
        <option value="{displayString=Over 60, internalValue=5}" <%=HtmlUtil.getOptionSelect("{displayString=Over 60, internalValue=5}", _age_rangeValue)%>>{displayString=Over 60, internalValue=5}</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepRegisterForm_agreeTerms_field">
    <div id="sweepRegisterForm_agreeTerms_label" class="formLabel" >Agree Terms </div>
    <div id="sweepRegisterForm_agreeTerms_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" name="agreeTerms" <%=HtmlUtil.getCheckedBoxValue(_agree_termsValue)%> />
    </div>      
	</div><div class="clear"></div>




        <div id="sweepRegisterFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepRegisterFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepRegister.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
