<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	if (sessionContext == null) 
		sessionContext = AutositeSessionContext.create(session);


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
        <input type="checkbox" id="requiredField" name="agreeTerms" checked="checked" />
    </div>      
	</div><div class="clear"></div>






        <div id="sweepRegisterForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepRegisterForm.submit();">Submit</a>
        </div>      

        <div id="sweepRegisterForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.sweepRegisterForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div style="margin: 50px 100px 0px 160px">
<p style="font: normal normal normal 10px verdana;color:#666">
	&#8226;&nbsp;We use this information only to notify the winner of the sweepstakes.   
	Any personal information entered here will be strongly kept in our server for the Sweepstakes only and never be used for any other purpose. 
</p><br/>
<p style="font: normal normal normal 10px verdana;color:#666">
	&#8226;&nbsp;We don't spam to Sweepstakes participants nor to any invitees.  
</p>
</div>


<div id="sweepRegisterForm_bottomArea" class="formBottomArea"></div>

<%
	if (!sessionContext.isSuperAdmin())
		return; 
%>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = SweepRegisterDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepRegister _SweepRegister = (SweepRegister) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SweepRegister.getId() %> </td>

    <td> <%= WebUtil.display(_SweepRegister.getEmail()) %></td>
    <td> <%= WebUtil.display(_SweepRegister.getPassword()) %></td>
    <td> <%= WebUtil.display(_SweepRegister.getSex()) %></td>
    <td> <%= WebUtil.display(_SweepRegister.getAgeRange()) %></td>
    <td> <%= WebUtil.display(_SweepRegister.getAgreeTerms()) %></td>
    <td> <%= WebUtil.display(_SweepRegister.getTimeCreated()) %></td>


<td>
<form name="sweepRegisterForm<%=_SweepRegister.getId()%>" method="get" action="/v_sweep_register_edit.html" >
    <a href="javascript:document.sweepRegisterForm<%=_SweepRegister.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepRegister.getId() %>">
</form>
<form name="sweepRegisterForm<%=_SweepRegister.getId()%>2" method="get" action="/v_sweep_register_edit2.html" >
    <a href="javascript:document.sweepRegisterForm<%=_SweepRegister.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepRegister.getId() %>">
</form>

</td>
<td>
<a href="/sweepRegisterAction.html?del=true&id=<%=_SweepRegister.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>