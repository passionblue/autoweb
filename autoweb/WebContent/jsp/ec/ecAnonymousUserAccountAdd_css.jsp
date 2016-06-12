<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcAnonymousUserAccount _EcAnonymousUserAccountDefault = new EcAnonymousUserAccount();// EcAnonymousUserAccountDS.getInstance().getDeafult();
    
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_EcAnonymousUserAccountDefault.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _subscribedValue= (reqParams.get("subscribed")==null?WebUtil.display(_EcAnonymousUserAccountDefault.getSubscribed()):WebUtil.display((String)reqParams.get("subscribed")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EcAnonymousUserAccountDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="ecAnonymousUserAccountForm_topArea" class="formTopArea"></div>
<div id="ecAnonymousUserAccountForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="ecAnonymousUserAccountForm" method="get" action="/ecAnonymousUserAccountAction.html" >


	<div id="ecAnonymousUserAccountForm_email_field">
    <div id="ecAnonymousUserAccountForm_email_label" class="formLabel" >Email </div>
    <div id="ecAnonymousUserAccountForm_email_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="email" value="<%=WebUtil.display(_emailValue)%>"/>
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




        <div id="ecAnonymousUserAccountForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecAnonymousUserAccountForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="ecAnonymousUserAccountForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcAnonymousUserAccountDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcAnonymousUserAccount _EcAnonymousUserAccount = (EcAnonymousUserAccount) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcAnonymousUserAccount.getId() %> </td>

    <td> <%= WebUtil.display(_EcAnonymousUserAccount.getEmail()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousUserAccount.getSubscribed()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousUserAccount.getTimeCreated()) %></td>


<td>
<form name="ecAnonymousUserAccountForm<%=_EcAnonymousUserAccount.getId()%>" method="get" action="/v_ec_anonymous_user_account_edit.html" >
    <a href="javascript:document.ecAnonymousUserAccountForm<%=_EcAnonymousUserAccount.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousUserAccount.getId() %>">
</form>
<form name="ecAnonymousUserAccountForm<%=_EcAnonymousUserAccount.getId()%>2" method="get" action="/v_ec_anonymous_user_account_edit2.html" >
    <a href="javascript:document.ecAnonymousUserAccountForm<%=_EcAnonymousUserAccount.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousUserAccount.getId() %>">
</form>

</td>
<td>
<a href="/ecAnonymousUserAccountAction.html?del=true&id=<%=_EcAnonymousUserAccount.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>