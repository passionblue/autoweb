<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    EcWishList _EcWishList = EcWishListDS.getInstance().getById(id);

    if ( _EcWishList == null ) {
        return;
    }

    String _user_idValue=  WebUtil.display(_EcWishList.getUserId());
    String _product_idValue=  WebUtil.display(_EcWishList.getProductId());
    String _size_variationValue=  WebUtil.display(_EcWishList.getSizeVariation());
    String _color_variationValue=  WebUtil.display(_EcWishList.getColorVariation());
    String _saved_priceValue=  WebUtil.display(_EcWishList.getSavedPrice());
    String _time_createdValue=  WebUtil.display(_EcWishList.getTimeCreated());
%> 

<br>
<div id="ecWishListForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="ecWishListFormEdit" method="get" action="/ecWishListAction.html" >


    <INPUT TYPE="HIDDEN" NAME="productId" value="<%=WebUtil.display(_product_idValue)%>" />



	<div id="ecWishListForm_sizeVariation_field">
    <div id="ecWishListForm_sizeVariation_label" class="formLabel" >Size Variation </div>
    <div id="ecWishListForm_sizeVariation_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="sizeVariation" value="<%=WebUtil.display(_size_variationValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecWishListForm_colorVariation_field">
    <div id="ecWishListForm_colorVariation_label" class="formLabel" >Color Variation </div>
    <div id="ecWishListForm_colorVariation_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="colorVariation" value="<%=WebUtil.display(_color_variationValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecWishListForm_savedPrice_field">
    <div id="ecWishListForm_savedPrice_label" class="formLabel" >Saved Price </div>
    <div id="ecWishListForm_savedPrice_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="savedPrice" value="<%=WebUtil.display(_saved_priceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="ecWishListFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecWishListFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcWishList.getId()%>">
</form>
</div> <!-- form -->
