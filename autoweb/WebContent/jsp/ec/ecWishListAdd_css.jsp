<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcWishList _EcWishListDefault = new EcWishList();// EcWishListDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_EcWishListDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _product_idValue= (reqParams.get("productId")==null?WebUtil.display(_EcWishListDefault.getProductId()):WebUtil.display((String)reqParams.get("productId")));
    String _size_variationValue= (reqParams.get("sizeVariation")==null?WebUtil.display(_EcWishListDefault.getSizeVariation()):WebUtil.display((String)reqParams.get("sizeVariation")));
    String _color_variationValue= (reqParams.get("colorVariation")==null?WebUtil.display(_EcWishListDefault.getColorVariation()):WebUtil.display((String)reqParams.get("colorVariation")));
    String _saved_priceValue= (reqParams.get("savedPrice")==null?WebUtil.display(_EcWishListDefault.getSavedPrice()):WebUtil.display((String)reqParams.get("savedPrice")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EcWishListDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="ecWishListForm_topArea" class="formTopArea"></div>
<div id="ecWishListForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="ecWishListForm" method="get" action="/ecWishListAction.html" >

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



        <div id="ecWishListForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecWishListForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="ecWishListForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcWishListDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcWishList _EcWishList = (EcWishList) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcWishList.getId() %> </td>

    <td> <%= WebUtil.display(_EcWishList.getUserId()) %></td>
    <td> <%= WebUtil.display(_EcWishList.getProductId()) %></td>
    <td> <%= WebUtil.display(_EcWishList.getSizeVariation()) %></td>
    <td> <%= WebUtil.display(_EcWishList.getColorVariation()) %></td>
    <td> <%= WebUtil.display(_EcWishList.getSavedPrice()) %></td>
    <td> <%= WebUtil.display(_EcWishList.getTimeCreated()) %></td>


<td>
<form name="ecWishListForm<%=_EcWishList.getId()%>" method="get" action="/v_ec_wish_list_edit.html" >
    <a href="javascript:document.ecWishListForm<%=_EcWishList.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcWishList.getId() %>">
</form>
<form name="ecWishListForm<%=_EcWishList.getId()%>2" method="get" action="/v_ec_wish_list_edit2.html" >
    <a href="javascript:document.ecWishListForm<%=_EcWishList.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcWishList.getId() %>">
</form>

</td>
<td>
<a href="/ecWishListAction.html?del=true&id=<%=_EcWishList.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>