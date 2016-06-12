<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

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
<form name="ecWishListFormEdit" method="post" action="/ecWishListAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>User Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="userId" value="<%=WebUtil.display(_user_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Product Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="productId" value="<%=WebUtil.display(_product_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Size Variation</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="sizeVariation" value="<%=WebUtil.display(_size_variationValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Color Variation</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="colorVariation" value="<%=WebUtil.display(_color_variationValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Saved Price</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="savedPrice" value="<%=WebUtil.display(_saved_priceValue)%>"/></TD>
    </TR>
            
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecWishListFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcWishList.getId()%>">
</form>
