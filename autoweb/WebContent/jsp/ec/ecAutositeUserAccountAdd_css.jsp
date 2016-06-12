<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcAutositeUserAccount _EcAutositeUserAccountDefault = new EcAutositeUserAccount();// EcAutositeUserAccountDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_EcAutositeUserAccountDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_EcAutositeUserAccountDefault.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_EcAutositeUserAccountDefault.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _subscribedValue= (reqParams.get("subscribed")==null?WebUtil.display(_EcAutositeUserAccountDefault.getSubscribed()):WebUtil.display((String)reqParams.get("subscribed")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EcAutositeUserAccountDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="ecAutositeUserAccountForm_topArea" class="formTopArea"></div>
<div id="ecAutositeUserAccountForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="ecAutositeUserAccountForm" method="get" action="/ecAutositeUserAccountAction.html" >


	<div id="ecAutositeUserAccountForm_userId_field">
    <div id="ecAutositeUserAccountForm_userId_label" class="formLabel" >User Id </div>
    <div id="ecAutositeUserAccountForm_userId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="userId" value="<%=WebUtil.display(_user_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAutositeUserAccountForm_firstName_field">
    <div id="ecAutositeUserAccountForm_firstName_label" class="formRequiredLabel" >First Name* </div>
    <div id="ecAutositeUserAccountForm_firstName_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecAutositeUserAccountForm_lastName_field">
    <div id="ecAutositeUserAccountForm_lastName_label" class="formRequiredLabel" >Last Name* </div>
    <div id="ecAutositeUserAccountForm_lastName_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> 
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




        <div id="ecAutositeUserAccountForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecAutositeUserAccountForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="ecAutositeUserAccountForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcAutositeUserAccountDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcAutositeUserAccount _EcAutositeUserAccount = (EcAutositeUserAccount) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcAutositeUserAccount.getId() %> </td>

    <td> <%= WebUtil.display(_EcAutositeUserAccount.getUserId()) %></td>
    <td> <%= WebUtil.display(_EcAutositeUserAccount.getFirstName()) %></td>
    <td> <%= WebUtil.display(_EcAutositeUserAccount.getLastName()) %></td>
    <td> <%= WebUtil.display(_EcAutositeUserAccount.getSubscribed()) %></td>
    <td> <%= WebUtil.display(_EcAutositeUserAccount.getTimeCreated()) %></td>


<td>
<form name="ecAutositeUserAccountForm<%=_EcAutositeUserAccount.getId()%>" method="get" action="/v_ec_autosite_user_account_edit.html" >
    <a href="javascript:document.ecAutositeUserAccountForm<%=_EcAutositeUserAccount.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAutositeUserAccount.getId() %>">
</form>
<form name="ecAutositeUserAccountForm<%=_EcAutositeUserAccount.getId()%>2" method="get" action="/v_ec_autosite_user_account_edit2.html" >
    <a href="javascript:document.ecAutositeUserAccountForm<%=_EcAutositeUserAccount.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAutositeUserAccount.getId() %>">
</form>

</td>
<td>
<a href="/ecAutositeUserAccountAction.html?del=true&id=<%=_EcAutositeUserAccount.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>