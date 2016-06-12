<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcAnonymousPaymentInfo _EcAnonymousPaymentInfoDefault = new EcAnonymousPaymentInfo();// EcAnonymousPaymentInfoDS.getInstance().getDeafult();
    
    String _anonymous_user_idValue= (reqParams.get("anonymousUserId")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getAnonymousUserId()):WebUtil.display((String)reqParams.get("anonymousUserId")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _middle_initialValue= (reqParams.get("middleInitial")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getMiddleInitial()):WebUtil.display((String)reqParams.get("middleInitial")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _address1Value= (reqParams.get("address1")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getAddress1()):WebUtil.display((String)reqParams.get("address1")));
    String _address2Value= (reqParams.get("address2")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getAddress2()):WebUtil.display((String)reqParams.get("address2")));
    String _cityValue= (reqParams.get("city")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getCity()):WebUtil.display((String)reqParams.get("city")));
    String _stateValue= (reqParams.get("state")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getState()):WebUtil.display((String)reqParams.get("state")));
    String _zipValue= (reqParams.get("zip")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getZip()):WebUtil.display((String)reqParams.get("zip")));
    String _countryValue= (reqParams.get("country")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getCountry()):WebUtil.display((String)reqParams.get("country")));
    String _payment_typeValue= (reqParams.get("paymentType")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getPaymentType()):WebUtil.display((String)reqParams.get("paymentType")));
    String _payment_numValue= (reqParams.get("paymentNum")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getPaymentNum()):WebUtil.display((String)reqParams.get("paymentNum")));
    String _payment_expire_monthValue= (reqParams.get("paymentExpireMonth")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getPaymentExpireMonth()):WebUtil.display((String)reqParams.get("paymentExpireMonth")));
    String _payment_expire_yearValue= (reqParams.get("paymentExpireYear")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getPaymentExpireYear()):WebUtil.display((String)reqParams.get("paymentExpireYear")));
    String _payment_extra_numValue= (reqParams.get("paymentExtraNum")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getPaymentExtraNum()):WebUtil.display((String)reqParams.get("paymentExtraNum")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="ecAnonymousPaymentInfoForm" method="post" action="/ecAnonymousPaymentInfoAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Anonymous User Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="anonymousUserId" value="<%=WebUtil.display(_anonymous_user_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>First Name</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Middle Initial</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="middleInitial" value="<%=WebUtil.display(_middle_initialValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Last Name</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Address1</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="address1" value="<%=WebUtil.display(_address1Value)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Address2</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="address2" value="<%=WebUtil.display(_address2Value)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>City</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="city" value="<%=WebUtil.display(_cityValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>State</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="state" value="<%=WebUtil.display(_stateValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Zip</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="zip" value="<%=WebUtil.display(_zipValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Country</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="country" value="<%=WebUtil.display(_countryValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Payment Type</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="paymentType" value="<%=WebUtil.display(_payment_typeValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Payment Num</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="paymentNum" value="<%=WebUtil.display(_payment_numValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Payment Expire Month</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="paymentExpireMonth" value="<%=WebUtil.display(_payment_expire_monthValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Payment Expire Year</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="paymentExpireYear" value="<%=WebUtil.display(_payment_expire_yearValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Payment Extra Num</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="paymentExtraNum" value="<%=WebUtil.display(_payment_extra_numValue)%>"/></TD>
	    </TR>
	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecAnonymousPaymentInfoForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcAnonymousPaymentInfoDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = (EcAnonymousPaymentInfo) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcAnonymousPaymentInfo.getId() %> </td>

    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getAnonymousUserId()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getFirstName()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getMiddleInitial()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getLastName()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getAddress1()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getAddress2()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getCity()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getState()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getZip()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getCountry()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentType()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentNum()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExpireMonth()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExpireYear()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExtraNum()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getTimeCreated()) %></td>


<td>
<form name="ecAnonymousPaymentInfoForm<%=_EcAnonymousPaymentInfo.getId()%>" method="post" action="/v_ec_anonymous_payment_info_edit.html" >
    <a href="javascript:document.ecAnonymousPaymentInfoForm<%=_EcAnonymousPaymentInfo.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousPaymentInfo.getId() %>">
</form>
</td>
<td>
<a href="/ecAnonymousPaymentInfoAction.html?del=true&id=<%=_EcAnonymousPaymentInfo.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>