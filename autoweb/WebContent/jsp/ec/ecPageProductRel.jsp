<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _product_idValue= "";
    String _category_idValue= "";
    String _hideValue= "";
    String _time_createdValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_ec_page_product_rel_add.html"> Add New</a>
            <a href="t_ec_page_product_rel_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

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