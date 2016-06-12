<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    EcPageProductRel _EcPageProductRel = EcPageProductRelDS.getInstance().getById(id);

    if ( _EcPageProductRel == null ) {
        return;
    }

    String _product_idValue=  WebUtil.display(_EcPageProductRel.getProductId());
    String _category_idValue=  WebUtil.display(_EcPageProductRel.getCategoryId());
    String _hideValue=  WebUtil.display(_EcPageProductRel.getHide());
    String _time_createdValue=  WebUtil.display(_EcPageProductRel.getTimeCreated());
%> 

<br>
<div id="ecPageProductRelForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="ecPageProductRelFormEdit" method="get" action="/ecPageProductRelAction.html" >




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





        <div id="ecPageProductRelFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecPageProductRelFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcPageProductRel.getId()%>">
</form>
</div> <!-- form -->
