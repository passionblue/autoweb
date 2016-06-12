<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    EcAutositeUserAccount _EcAutositeUserAccount = EcAutositeUserAccountDS.getInstance().getById(id);

    if ( _EcAutositeUserAccount == null ) {
        return;
    }

    String _user_idValue=  WebUtil.display(_EcAutositeUserAccount.getUserId());
    String _first_nameValue=  WebUtil.display(_EcAutositeUserAccount.getFirstName());
    String _last_nameValue=  WebUtil.display(_EcAutositeUserAccount.getLastName());
    String _subscribedValue=  WebUtil.display(_EcAutositeUserAccount.getSubscribed());
    String _time_createdValue=  WebUtil.display(_EcAutositeUserAccount.getTimeCreated());
%> 

<br>
<div id="ecAutositeUserAccountForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="ecAutositeUserAccountFormEdit" method="get" action="/ecAutositeUserAccountAction.html" >




	<div id="ecAutositeUserAccountForm_userId_field">
    <div id="ecAutositeUserAccountForm_userId_label" class="formLabel" >User Id </div>
    <div id="ecAutositeUserAccountForm_userId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="userId" value="<%=WebUtil.display(_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAutositeUserAccountForm_firstName_field">
    <div id="ecAutositeUserAccountForm_firstName_label" class="formRequiredLabel" >First Name* </div>
    <div id="ecAutositeUserAccountForm_firstName_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAutositeUserAccountForm_lastName_field">
    <div id="ecAutositeUserAccountForm_lastName_label" class="formRequiredLabel" >Last Name* </div>
    <div id="ecAutositeUserAccountForm_lastName_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAutositeUserAccountForm_subscribed_field">
    <div id="ecAutositeUserAccountForm_subscribed_label" class="formLabel" >Subscribed </div>
    <div id="ecAutositeUserAccountForm_subscribed_dropdown" class="formFieldDropDown" >       
        <select name="subscribed">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _subscribedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _subscribedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





        <div id="ecAutositeUserAccountFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecAutositeUserAccountFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcAutositeUserAccount.getId()%>">
</form>
</div> <!-- form -->
