<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _user_idValue= "";
    String _product_idValue= "";
    String _size_variationValue= "";
    String _color_variationValue= "";
    String _saved_priceValue= "";
    String _time_createdValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_ec_wish_list_add.html"> Add New</a>
            <a href="t_ec_wish_list_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

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