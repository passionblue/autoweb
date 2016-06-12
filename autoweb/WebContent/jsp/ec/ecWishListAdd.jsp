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
<form name="ecWishListForm" method="post" action="/ecWishListAction.html" >
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
            <b><a href="javascript:document.ecWishListForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


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
<form name="ecWishListForm<%=_EcWishList.getId()%>" method="post" action="/v_ec_wish_list_edit.html" >
    <a href="javascript:document.ecWishListForm<%=_EcWishList.getId()%>.submit();">Edit</a>           
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