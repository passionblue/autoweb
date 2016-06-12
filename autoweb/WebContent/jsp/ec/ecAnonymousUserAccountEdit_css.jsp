<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    EcAnonymousUserAccount _EcAnonymousUserAccount = EcAnonymousUserAccountDS.getInstance().getById(id);

    if ( _EcAnonymousUserAccount == null ) {
        return;
    }

    String _emailValue=  WebUtil.display(_EcAnonymousUserAccount.getEmail());
    String _subscribedValue=  WebUtil.display(_EcAnonymousUserAccount.getSubscribed());
    String _time_createdValue=  WebUtil.display(_EcAnonymousUserAccount.getTimeCreated());
%> 

<br>
<div id="ecAnonymousUserAccountForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="ecAnonymousUserAccountFormEdit" method="get" action="/ecAnonymousUserAccountAction.html" >




	<div id="ecAnonymousUserAccountForm_email_field">
    <div id="ecAnonymousUserAccountForm_email_label" class="formLabel" >Email </div>
    <div id="ecAnonymousUserAccountForm_email_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousUserAccountForm_subscribed_field">
    <div id="ecAnonymousUserAccountForm_subscribed_label" class="formLabel" >Subscribed </div>
    <div id="ecAnonymousUserAccountForm_subscribed_dropdown" class="formFieldDropDown" >       
        <select name="subscribed">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _subscribedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _subscribedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





        <div id="ecAnonymousUserAccountFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecAnonymousUserAccountFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcAnonymousUserAccount.getId()%>">
</form>
</div> <!-- form -->
