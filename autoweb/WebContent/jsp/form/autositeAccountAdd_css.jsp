<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    AutositeAccount _AutositeAccountDefault = new AutositeAccount();// AutositeAccountDS.getInstance().getDeafult();
    
    String _account_numValue= (reqParams.get("accountNum")==null?WebUtil.display(_AutositeAccountDefault.getAccountNum()):WebUtil.display((String)reqParams.get("accountNum")));
    String _enabledValue= (reqParams.get("enabled")==null?WebUtil.display(_AutositeAccountDefault.getEnabled()):WebUtil.display((String)reqParams.get("enabled")));
    String _email_confirmedValue= (reqParams.get("emailConfirmed")==null?WebUtil.display(_AutositeAccountDefault.getEmailConfirmed()):WebUtil.display((String)reqParams.get("emailConfirmed")));
    String _time_confirmedValue= (reqParams.get("timeConfirmed")==null?WebUtil.display(_AutositeAccountDefault.getTimeConfirmed()):WebUtil.display((String)reqParams.get("timeConfirmed")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_AutositeAccountDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="autositeAccountForm_topArea" class="formTopArea"></div>
<div id="autositeAccountForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="autositeAccountForm" method="get" action="/autositeAccountAction.html" >


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





        <div id="autositeAccountForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.autositeAccountForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="autositeAccountForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = AutositeAccountDS.getInstance().getAll();
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        AutositeAccount _AutositeAccount = (AutositeAccount) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _AutositeAccount.getId() %> </td>

    <td> <%= WebUtil.display(_AutositeAccount.getAccountNum()) %></td>
    <td> <%= WebUtil.display(_AutositeAccount.getEnabled()) %></td>
    <td> <%= WebUtil.display(_AutositeAccount.getEmailConfirmed()) %></td>
    <td> <%= WebUtil.display(_AutositeAccount.getTimeConfirmed()) %></td>
    <td> <%= WebUtil.display(_AutositeAccount.getTimeCreated()) %></td>


<td>
<form name="autositeAccountForm<%=_AutositeAccount.getId()%>" method="get" action="/v_autosite_account_edit.html" >
    <a href="javascript:document.autositeAccountForm<%=_AutositeAccount.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _AutositeAccount.getId() %>">
</form>
<form name="autositeAccountForm<%=_AutositeAccount.getId()%>2" method="get" action="/v_autosite_account_edit2.html" >
    <a href="javascript:document.autositeAccountForm<%=_AutositeAccount.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _AutositeAccount.getId() %>">
</form>

</td>
<td>
<a href="/autositeAccountAction.html?del=true&id=<%=_AutositeAccount.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>