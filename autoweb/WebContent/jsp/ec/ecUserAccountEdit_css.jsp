<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    EcUserAccount _EcUserAccount = EcUserAccountDS.getInstance().getById(id);

    if ( _EcUserAccount == null ) {
        return;
    }

    String _user_idValue=  WebUtil.display(_EcUserAccount.getUserId());
    String _first_nameValue=  WebUtil.display(_EcUserAccount.getFirstName());
    String _last_nameValue=  WebUtil.display(_EcUserAccount.getLastName());
%> 

<br>
<div id="ecUserAccountForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="ecUserAccountFormEdit" method="get" action="/ecUserAccountAction.html" >




	<div id="ecUserAccountForm_userId_field">
    <div id="ecUserAccountForm_userId_label" class="formLabel" >User Id </div>
    <div id="ecUserAccountForm_userId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="userId" value="<%=WebUtil.display(_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecUserAccountForm_firstName_field">
    <div id="ecUserAccountForm_firstName_label" class="formRequiredLabel" >First Name* </div>
    <div id="ecUserAccountForm_firstName_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecUserAccountForm_lastName_field">
    <div id="ecUserAccountForm_lastName_label" class="formRequiredLabel" >Last Name* </div>
    <div id="ecUserAccountForm_lastName_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="ecUserAccountFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecUserAccountFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcUserAccount.getId()%>">
</form>
</div> <!-- form -->
