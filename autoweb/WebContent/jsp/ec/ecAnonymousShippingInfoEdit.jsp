<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    EcAnonymousShippingInfo _EcAnonymousShippingInfo = EcAnonymousShippingInfoDS.getInstance().getById(id);

    if ( _EcAnonymousShippingInfo == null ) {
        return;
    }

    String _anonymous_user_idValue=  WebUtil.display(_EcAnonymousShippingInfo.getAnonymousUserId());
    String _first_nameValue=  WebUtil.display(_EcAnonymousShippingInfo.getFirstName());
    String _middle_initialValue=  WebUtil.display(_EcAnonymousShippingInfo.getMiddleInitial());
    String _last_nameValue=  WebUtil.display(_EcAnonymousShippingInfo.getLastName());
    String _address1Value=  WebUtil.display(_EcAnonymousShippingInfo.getAddress1());
    String _address2Value=  WebUtil.display(_EcAnonymousShippingInfo.getAddress2());
    String _cityValue=  WebUtil.display(_EcAnonymousShippingInfo.getCity());
    String _stateValue=  WebUtil.display(_EcAnonymousShippingInfo.getState());
    String _zipValue=  WebUtil.display(_EcAnonymousShippingInfo.getZip());
    String _countryValue=  WebUtil.display(_EcAnonymousShippingInfo.getCountry());
    String _special_instructionValue=  WebUtil.display(_EcAnonymousShippingInfo.getSpecialInstruction());
    String _time_createdValue=  WebUtil.display(_EcAnonymousShippingInfo.getTimeCreated());
%> 

<br>
<form name="ecAnonymousShippingInfoFormEdit" method="post" action="/ecAnonymousShippingInfoAction.html" >
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
        <TD align="right" ><b>Special Instruction</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="specialInstruction" value="<%=WebUtil.display(_special_instructionValue)%>"/></TD>
    </TR>
            
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecAnonymousShippingInfoFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcAnonymousShippingInfo.getId()%>">
</form>
