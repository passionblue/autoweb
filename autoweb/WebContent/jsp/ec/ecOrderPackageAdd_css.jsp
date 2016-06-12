<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcOrderPackage _EcOrderPackageDefault = new EcOrderPackage();// EcOrderPackageDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_EcOrderPackageDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _order_idValue= (reqParams.get("orderId")==null?WebUtil.display(_EcOrderPackageDefault.getOrderId()):WebUtil.display((String)reqParams.get("orderId")));
    String _num_orderValue= (reqParams.get("numOrder")==null?WebUtil.display(_EcOrderPackageDefault.getNumOrder()):WebUtil.display((String)reqParams.get("numOrder")));
    String _shippedValue= (reqParams.get("shipped")==null?WebUtil.display(_EcOrderPackageDefault.getShipped()):WebUtil.display((String)reqParams.get("shipped")));
    String _time_shippedValue= (reqParams.get("timeShipped")==null?WebUtil.display(_EcOrderPackageDefault.getTimeShipped()):WebUtil.display((String)reqParams.get("timeShipped")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="ecOrderPackageForm_topArea" class="formTopArea"></div>
<div id="ecOrderPackageForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="ecOrderPackageForm" method="get" action="/ecOrderPackageAction.html" >

    <INPUT TYPE="HIDDEN" NAME="orderId" value="<%=WebUtil.display(_order_idValue)%>" />



	<div id="ecOrderPackageForm_numOrder_field">
    <div id="ecOrderPackageForm_numOrder_label" class="formRequiredLabel" >Num Order* </div>
    <div id="ecOrderPackageForm_numOrder_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="numOrder" value="<%=WebUtil.display(_num_orderValue)%>"/> 
    </div>      
	</div><div class="clear"></div>

	<div id="ecOrderPackageForm_shipped_field">
    <div id="ecOrderPackageForm_shipped_label" class="formLabel" >Shipped </div>
    <div id="ecOrderPackageForm_shipped_dropdown" class="formFieldDropDown" >       
        <select name="shipped">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _shippedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _shippedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




        <div id="ecOrderPackageForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecOrderPackageForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="ecOrderPackageForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcOrderPackageDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcOrderPackage _EcOrderPackage = (EcOrderPackage) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcOrderPackage.getId() %> </td>

    <td> <%= WebUtil.display(_EcOrderPackage.getUserId()) %></td>
    <td> <%= WebUtil.display(_EcOrderPackage.getOrderId()) %></td>
    <td> <%= WebUtil.display(_EcOrderPackage.getNumOrder()) %></td>
    <td> <%= WebUtil.display(_EcOrderPackage.getShipped()) %></td>
    <td> <%= WebUtil.display(_EcOrderPackage.getTimeShipped()) %></td>


<td>
<form name="ecOrderPackageForm<%=_EcOrderPackage.getId()%>" method="get" action="/v_ec_order_package_edit.html" >
    <a href="javascript:document.ecOrderPackageForm<%=_EcOrderPackage.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcOrderPackage.getId() %>">
</form>
<form name="ecOrderPackageForm<%=_EcOrderPackage.getId()%>2" method="get" action="/v_ec_order_package_edit2.html" >
    <a href="javascript:document.ecOrderPackageForm<%=_EcOrderPackage.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcOrderPackage.getId() %>">
</form>

</td>
<td>
<a href="/ecOrderPackageAction.html?del=true&id=<%=_EcOrderPackage.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>