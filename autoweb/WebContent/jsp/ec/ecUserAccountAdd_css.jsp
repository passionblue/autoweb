<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcUserAccount _EcUserAccountDefault = new EcUserAccount();// EcUserAccountDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_EcUserAccountDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_EcUserAccountDefault.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_EcUserAccountDefault.getLastName()):WebUtil.display((String)reqParams.get("lastName")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="ecUserAccountForm_topArea" class="formTopArea"></div>
<div id="ecUserAccountForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="ecUserAccountForm" method="get" action="/ecUserAccountAction.html" >


	<div id="ecUserAccountForm_userId_field">
    <div id="ecUserAccountForm_userId_label" class="formLabel" >User Id </div>
    <div id="ecUserAccountForm_userId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="userId" value="<%=WebUtil.display(_user_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecUserAccountForm_firstName_field">
    <div id="ecUserAccountForm_firstName_label" class="formRequiredLabel" >First Name* </div>
    <div id="ecUserAccountForm_firstName_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecUserAccountForm_lastName_field">
    <div id="ecUserAccountForm_lastName_label" class="formRequiredLabel" >Last Name* </div>
    <div id="ecUserAccountForm_lastName_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>

        <div id="ecUserAccountForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecUserAccountForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="ecUserAccountForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcUserAccountDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcUserAccount _EcUserAccount = (EcUserAccount) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcUserAccount.getId() %> </td>

    <td> <%= WebUtil.display(_EcUserAccount.getUserId()) %></td>
    <td> <%= WebUtil.display(_EcUserAccount.getFirstName()) %></td>
    <td> <%= WebUtil.display(_EcUserAccount.getLastName()) %></td>


<td>
<form name="ecUserAccountForm<%=_EcUserAccount.getId()%>" method="get" action="/v_ec_user_account_edit.html" >
    <a href="javascript:document.ecUserAccountForm<%=_EcUserAccount.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcUserAccount.getId() %>">
</form>
<form name="ecUserAccountForm<%=_EcUserAccount.getId()%>2" method="get" action="/v_ec_user_account_edit2.html" >
    <a href="javascript:document.ecUserAccountForm<%=_EcUserAccount.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcUserAccount.getId() %>">
</form>

</td>
<td>
<a href="/ecUserAccountAction.html?del=true&id=<%=_EcUserAccount.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>