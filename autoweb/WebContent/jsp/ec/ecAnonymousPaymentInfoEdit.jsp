<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = EcAnonymousPaymentInfoDS.getInstance().getById(id);

    if ( _EcAnonymousPaymentInfo == null ) {
        return;
    }

    String _anonymous_user_idValue=  WebUtil.display(_EcAnonymousPaymentInfo.getAnonymousUserId());
    String _first_nameValue=  WebUtil.display(_EcAnonymousPaymentInfo.getFirstName());
    String _middle_initialValue=  WebUtil.display(_EcAnonymousPaymentInfo.getMiddleInitial());
    String _last_nameValue=  WebUtil.display(_EcAnonymousPaymentInfo.getLastName());
    String _address1Value=  WebUtil.display(_EcAnonymousPaymentInfo.getAddress1());
    String _address2Value=  WebUtil.display(_EcAnonymousPaymentInfo.getAddress2());
    String _cityValue=  WebUtil.display(_EcAnonymousPaymentInfo.getCity());
    String _stateValue=  WebUtil.display(_EcAnonymousPaymentInfo.getState());
    String _zipValue=  WebUtil.display(_EcAnonymousPaymentInfo.getZip());
    String _countryValue=  WebUtil.display(_EcAnonymousPaymentInfo.getCountry());
    String _payment_typeValue=  WebUtil.display(_EcAnonymousPaymentInfo.getPaymentType());
    String _payment_numValue=  WebUtil.display(_EcAnonymousPaymentInfo.getPaymentNum());
    String _payment_expire_monthValue=  WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExpireMonth());
    String _payment_expire_yearValue=  WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExpireYear());
    String _payment_extra_numValue=  WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExtraNum());
    String _time_createdValue=  WebUtil.display(_EcAnonymousPaymentInfo.getTimeCreated());
%> 

<br>
<form name="ecAnonymousPaymentInfoFormEdit" method="post" action="/ecAnonymousPaymentInfoAction.html" >
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
            <b><a href="javascript:document.ecAnonymousPaymentInfoFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcAnonymousPaymentInfo.getId()%>">
</form>
