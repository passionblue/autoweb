<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    EcOrderPackage _EcOrderPackage = EcOrderPackageDS.getInstance().getById(id);

    if ( _EcOrderPackage == null ) {
        return;
    }

    String _user_idValue=  WebUtil.display(_EcOrderPackage.getUserId());
    String _order_idValue=  WebUtil.display(_EcOrderPackage.getOrderId());
    String _num_orderValue=  WebUtil.display(_EcOrderPackage.getNumOrder());
    String _shippedValue=  WebUtil.display(_EcOrderPackage.getShipped());
    String _time_shippedValue=  WebUtil.display(_EcOrderPackage.getTimeShipped());
%> 

<br>
<form name="ecOrderPackageFormEdit" method="post" action="/ecOrderPackageAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>User Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="userId" value="<%=WebUtil.display(_user_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Order Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="orderId" value="<%=WebUtil.display(_order_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Num Order</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="numOrder" value="<%=WebUtil.display(_num_orderValue)%>"/></TD>
    </TR>
                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Shipped</b> &nbsp;</TD>
        <TD>&nbsp;<select name="shipped">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _shippedValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _shippedValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecOrderPackageFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcOrderPackage.getId()%>">
</form>
