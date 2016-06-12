<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _category_idValue= "";
    String _factory_skuValue= "";
    String _site_skuValue= "";
    String _alt_skuValue= "";
    String _item_codeValue= "";
    String _makerValue= "";
    String _brandValue= "";
    String _nameValue= "";
    String _sub_nameValue= "";
    String _short_descriptionValue= "";
    String _descriptionValue= "";
    String _description2Value= "";
    String _image_urlValue= "";
    String _colorValue= "";
    String _sizeValue= "";
    String _msrpValue= "";
    String _msrp_currencyValue= "";
    String _soldoutValue= "";
    String _hideValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_ec_product_add.html"> Add New</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

    List list = EcProductDS.getInstance().getBySiteId(site.getId());
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcProduct _EcProduct = (EcProduct) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcProduct.getId() %> </td>

    <td> <%= WebUtil.display(_EcProduct.getCategoryId()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getFactorySku()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getSiteSku()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getAltSku()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getItemCode()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getMaker()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getBrand()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getName()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getSubName()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getShortDescription()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getDescription()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getDescription2()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getImageUrl()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getColor()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getSize()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getMsrp()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getMsrpCurrency()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getSoldout()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getHide()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getTimeUpdated()) %></td>

    <td>
    <form name="ecProductForm<%=_EcProduct.getId()%>" method="post" action="/v_ec_product_edit.html" >
        <a href="javascript:document.ecProductForm<%=_EcProduct.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcProduct.getId() %>">
    </form>
    </td>

    <td>
    <a href="/ecProductAction.html?del=true&id=<%=_EcProduct.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>