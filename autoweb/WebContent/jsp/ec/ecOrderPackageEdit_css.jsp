<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
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
<div id="ecOrderPackageForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="ecOrderPackageFormEdit" method="get" action="/ecOrderPackageAction.html" >


    <INPUT TYPE="HIDDEN" NAME="orderId" value="<%=WebUtil.display(_order_idValue)%>" />



	<div id="ecOrderPackageForm_numOrder_field">
    <div id="ecOrderPackageForm_numOrder_label" class="formRequiredLabel" >Num Order* </div>
    <div id="ecOrderPackageForm_numOrder_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="numOrder" value="<%=WebUtil.display(_num_orderValue)%>"/> <span></span>
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





        <div id="ecOrderPackageFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecOrderPackageFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcOrderPackage.getId()%>">
</form>
</div> <!-- form -->
