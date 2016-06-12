<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcPageProductRel _EcPageProductRelDefault = new EcPageProductRel();// EcPageProductRelDS.getInstance().getDeafult();
    
    String _product_idValue= (reqParams.get("productId")==null?WebUtil.display(_EcPageProductRelDefault.getProductId()):WebUtil.display((String)reqParams.get("productId")));
    String _category_idValue= (reqParams.get("categoryId")==null?WebUtil.display(_EcPageProductRelDefault.getCategoryId()):WebUtil.display((String)reqParams.get("categoryId")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_EcPageProductRelDefault.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EcPageProductRelDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="ecPageProductRelForm_topArea" class="formTopArea"></div>
<div id="ecPageProductRelForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="ecPageProductRelForm" method="get" action="/ecPageProductRelAction.html" >


	<div id="ecPageProductRelForm_productId_field">
    <div id="ecPageProductRelForm_productId_label" class="formRequiredLabel" >Product ID* </div>
    <div id="ecPageProductRelForm_productId_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="productId" value="<%=WebUtil.display(_product_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecPageProductRelForm_categoryId_field">
    <div id="ecPageProductRelForm_categoryId_label" class="formLabel" >Category Id </div>
    <div id="ecPageProductRelForm_categoryId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="categoryId" value="<%=WebUtil.display(_category_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="ecPageProductRelForm_hide_field">
    <div id="ecPageProductRelForm_hide_label" class="formLabel" >Hide </div>
    <div id="ecPageProductRelForm_hide_dropdown" class="formFieldDropDown" >       
        <select name="hide">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




        <div id="ecPageProductRelForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecPageProductRelForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="ecPageProductRelForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcPageProductRelDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcPageProductRel _EcPageProductRel = (EcPageProductRel) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcPageProductRel.getId() %> </td>

    <td> <%= WebUtil.display(_EcPageProductRel.getProductId()) %></td>
    <td> <%= WebUtil.display(_EcPageProductRel.getCategoryId()) %></td>
    <td> <%= WebUtil.display(_EcPageProductRel.getHide()) %></td>
    <td> <%= WebUtil.display(_EcPageProductRel.getTimeCreated()) %></td>


<td>
<form name="ecPageProductRelForm<%=_EcPageProductRel.getId()%>" method="get" action="/v_ec_page_product_rel_edit.html" >
    <a href="javascript:document.ecPageProductRelForm<%=_EcPageProductRel.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcPageProductRel.getId() %>">
</form>
<form name="ecPageProductRelForm<%=_EcPageProductRel.getId()%>2" method="get" action="/v_ec_page_product_rel_edit2.html" >
    <a href="javascript:document.ecPageProductRelForm<%=_EcPageProductRel.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcPageProductRel.getId() %>">
</form>

</td>
<td>
<a href="/ecPageProductRelAction.html?del=true&id=<%=_EcPageProductRel.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>