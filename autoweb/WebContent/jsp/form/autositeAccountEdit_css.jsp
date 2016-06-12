<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    AutositeAccount _AutositeAccount = AutositeAccountDS.getInstance().getById(id);

    if ( _AutositeAccount == null ) {
        return;
    }

    String _account_numValue=  WebUtil.display(_AutositeAccount.getAccountNum());
    String _enabledValue=  WebUtil.display(_AutositeAccount.getEnabled());
    String _email_confirmedValue=  WebUtil.display(_AutositeAccount.getEmailConfirmed());
    String _time_confirmedValue=  WebUtil.display(_AutositeAccount.getTimeConfirmed());
    String _time_createdValue=  WebUtil.display(_AutositeAccount.getTimeCreated());
%> 

<br>
<div id="autositeAccountForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="autositeAccountFormEdit" method="get" action="/autositeAccountAction.html" >




	<div id="autositeAccountForm_accountNum_field">
    <div id="autositeAccountForm_accountNum_label" class="formLabel" >Account Num </div>
    <div id="autositeAccountForm_accountNum_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="accountNum" value="<%=WebUtil.display(_account_numValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="autositeAccountForm_enabled_field">
    <div id="autositeAccountForm_enabled_label" class="formLabel" >Enabled </div>
    <div id="autositeAccountForm_enabled_dropdown" class="formFieldDropDown" >       
        <select name="enabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _enabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _enabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="autositeAccountForm_emailConfirmed_field">
    <div id="autositeAccountForm_emailConfirmed_label" class="formLabel" >Email Confirmed </div>
    <div id="autositeAccountForm_emailConfirmed_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="emailConfirmed" value="<%=WebUtil.display(_email_confirmedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="autositeAccountFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.autositeAccountFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeAccount.getId()%>">
</form>
</div> <!-- form -->
