<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcAnonymousShippingInfo _EcAnonymousShippingInfoDefault = new EcAnonymousShippingInfo();// EcAnonymousShippingInfoDS.getInstance().getDeafult();
    
    String _anonymous_user_idValue= (reqParams.get("anonymousUserId")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getAnonymousUserId()):WebUtil.display((String)reqParams.get("anonymousUserId")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _middle_initialValue= (reqParams.get("middleInitial")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getMiddleInitial()):WebUtil.display((String)reqParams.get("middleInitial")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _address1Value= (reqParams.get("address1")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getAddress1()):WebUtil.display((String)reqParams.get("address1")));
    String _address2Value= (reqParams.get("address2")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getAddress2()):WebUtil.display((String)reqParams.get("address2")));
    String _cityValue= (reqParams.get("city")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getCity()):WebUtil.display((String)reqParams.get("city")));
    String _stateValue= (reqParams.get("state")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getState()):WebUtil.display((String)reqParams.get("state")));
    String _zipValue= (reqParams.get("zip")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getZip()):WebUtil.display((String)reqParams.get("zip")));
    String _countryValue= (reqParams.get("country")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getCountry()):WebUtil.display((String)reqParams.get("country")));
    String _special_instructionValue= (reqParams.get("specialInstruction")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getSpecialInstruction()):WebUtil.display((String)reqParams.get("specialInstruction")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="ecAnonymousShippingInfoForm" method="post" action="/ecAnonymousShippingInfoAction.html" >
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
            <b><a href="javascript:document.ecAnonymousShippingInfoForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcAnonymousShippingInfoDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcAnonymousShippingInfo _EcAnonymousShippingInfo = (EcAnonymousShippingInfo) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcAnonymousShippingInfo.getId() %> </td>

    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getAnonymousUserId()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getFirstName()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getMiddleInitial()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getLastName()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getAddress1()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getAddress2()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getCity()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getState()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getZip()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getCountry()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getSpecialInstruction()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getTimeCreated()) %></td>


<td>
<form name="ecAnonymousShippingInfoForm<%=_EcAnonymousShippingInfo.getId()%>" method="post" action="/v_ec_anonymous_shipping_info_edit.html" >
    <a href="javascript:document.ecAnonymousShippingInfoForm<%=_EcAnonymousShippingInfo.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousShippingInfo.getId() %>">
</form>
</td>
<td>
<a href="/ecAnonymousShippingInfoAction.html?del=true&id=<%=_EcAnonymousShippingInfo.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>