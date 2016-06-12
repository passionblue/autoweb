<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<script type="text/javascript" src="/fckeditor/fckeditor.js"></script>
<script type="text/javascript">
window.onload = function()
{
	// Automatically calculates the editor base path based on the _samples directory.
	// This is usefull only for these samples. A real application should use something like this:
	// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
	//var sBasePath = document.location.href.substring(0,document.location.href.lastIndexOf('_samples')) ;
	var sBasePath = '/fckeditor/' ;

	var oFCKeditor = new FCKeditor( 'description' ) ;
	oFCKeditor.BasePath	= sBasePath ;
	oFCKeditor.Height = '600';
	oFCKeditor.Width = '550';
	oFCKeditor.ReplaceTextarea('mainDesc') ;
	
}
</script>


<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcProduct _EcProductDefault = new EcProduct();// EcProductDS.getInstance().getDeafult();
    
    String _category_idValue= (reqParams.get("categoryId")==null?WebUtil.display(_EcProductDefault.getCategoryId()):WebUtil.display((String)reqParams.get("categoryId")));
    String _factory_skuValue= (reqParams.get("factorySku")==null?WebUtil.display(_EcProductDefault.getFactorySku()):WebUtil.display((String)reqParams.get("factorySku")));
    String _site_skuValue= (reqParams.get("siteSku")==null?WebUtil.display(_EcProductDefault.getSiteSku()):WebUtil.display((String)reqParams.get("siteSku")));
    String _alt_skuValue= (reqParams.get("altSku")==null?WebUtil.display(_EcProductDefault.getAltSku()):WebUtil.display((String)reqParams.get("altSku")));
    String _item_codeValue= (reqParams.get("itemCode")==null?WebUtil.display(_EcProductDefault.getItemCode()):WebUtil.display((String)reqParams.get("itemCode")));
    String _makerValue= (reqParams.get("maker")==null?WebUtil.display(_EcProductDefault.getMaker()):WebUtil.display((String)reqParams.get("maker")));
    String _brandValue= (reqParams.get("brand")==null?WebUtil.display(_EcProductDefault.getBrand()):WebUtil.display((String)reqParams.get("brand")));
    String _nameValue= (reqParams.get("name")==null?WebUtil.display(_EcProductDefault.getName()):WebUtil.display((String)reqParams.get("name")));
    String _sub_nameValue= (reqParams.get("subName")==null?WebUtil.display(_EcProductDefault.getSubName()):WebUtil.display((String)reqParams.get("subName")));
    String _short_descriptionValue= (reqParams.get("shortDescription")==null?WebUtil.display(_EcProductDefault.getShortDescription()):WebUtil.display((String)reqParams.get("shortDescription")));
    String _descriptionValue= (reqParams.get("description")==null?WebUtil.display(_EcProductDefault.getDescription()):WebUtil.display((String)reqParams.get("description")));
    String _description2Value= (reqParams.get("description2")==null?WebUtil.display(_EcProductDefault.getDescription2()):WebUtil.display((String)reqParams.get("description2")));
    String _image_urlValue= (reqParams.get("imageUrl")==null?WebUtil.display(_EcProductDefault.getImageUrl()):WebUtil.display((String)reqParams.get("imageUrl")));
    String _colorValue= (reqParams.get("color")==null?WebUtil.display(_EcProductDefault.getColor()):WebUtil.display((String)reqParams.get("color")));
    String _sizeValue= (reqParams.get("size")==null?WebUtil.display(_EcProductDefault.getSize()):WebUtil.display((String)reqParams.get("size")));
    String _msrpValue= (reqParams.get("msrp")==null?WebUtil.display(_EcProductDefault.getMsrp()):WebUtil.display((String)reqParams.get("msrp")));
    String _msrp_currencyValue= (reqParams.get("msrpCurrency")==null?WebUtil.display(_EcProductDefault.getMsrpCurrency()):WebUtil.display((String)reqParams.get("msrpCurrency")));
    String _sale_priceValue= (reqParams.get("salePrice")==null?WebUtil.display(_EcProductDefault.getSalePrice()):WebUtil.display((String)reqParams.get("salePrice")));
    String _sale_endsValue= (reqParams.get("saleEnds")==null?WebUtil.display(_EcProductDefault.getSaleEnds()):WebUtil.display((String)reqParams.get("saleEnds")));
    String _soldoutValue= (reqParams.get("soldout")==null?WebUtil.display(_EcProductDefault.getSoldout()):WebUtil.display((String)reqParams.get("soldout")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_EcProductDefault.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _promo_noteValue= (reqParams.get("promoNote")==null?WebUtil.display(_EcProductDefault.getPromoNote()):WebUtil.display((String)reqParams.get("promoNote")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EcProductDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_EcProductDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="ecProductForm" method="post" action="/ecProductAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Category Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="10" property="categoryId" value="<%=WebUtil.display(_category_idValue)%>"/>

<%
	List categories = EcCategoryDS.getInstance().getBySiteId(site.getId());
	
	
	for(Iterator iter = categories.iterator(); iter.hasNext();){
		EcCategory cat = (EcCategory) iter.next();
		
		int checked = (cat.getId() == WebParamUtil.getIntValue(_category_idValue)? 1:0);
%>

<%
		if (checked == 1){
%>
		<INPUT TYPE="HIDDEN" name="category_<%=cat.getId() %>" value="on">
<%
		} else {
%>
		<%= cat.getCategoryName() %> (<%=cat.getId() %>): 
		<input type="checkbox" name="category_<%=cat.getId() %>"  />
<%
		}
%>
<%
	}
%>
	</TD>

    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Factory Sku</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="factorySku" value="<%=WebUtil.display(_factory_skuValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Site Sku</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="siteSku" value="<%=WebUtil.display(_site_skuValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Alt Sku</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="altSku" value="<%=WebUtil.display(_alt_skuValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Item Code</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="itemCode" value="<%=WebUtil.display(_item_codeValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Maker</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="maker" value="<%=WebUtil.display(_makerValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Brand</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="brand" value="<%=WebUtil.display(_brandValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Name</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="name" value="<%=WebUtil.display(_nameValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Sub Name</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="subName" value="<%=WebUtil.display(_sub_nameValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Short Description</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="shortDescription" value="<%=WebUtil.display(_short_descriptionValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Description</b> &nbsp;</TD>
        <TD>&nbsp;<TEXTAREA cols="70" class="mainDesc" NAME="description" ></TEXTAREA><br></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Description2</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="description2" value="<%=WebUtil.display(_description2Value)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Image Url</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="imageUrl" value="<%=WebUtil.display(_image_urlValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Color</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="color" value="<%=WebUtil.display(_colorValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Size</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="size" value="<%=WebUtil.display(_sizeValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Msrp</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="msrp" value="<%=WebUtil.display(_msrpValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Msrp Currency</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="msrpCurrency" value="<%=WebUtil.display(_msrp_currencyValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Sale Price</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="salePrice" value="<%=WebUtil.display(_sale_priceValue)%>"/></TD>
    </TR>
                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Sale Ends</b> &nbsp;</TD>
        <TD>&nbsp;<select name="saleEnds">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _sale_endsValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _sale_endsValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD align="right" ><b>Soldout</b> &nbsp;</TD>
        <TD>&nbsp;<select name="soldout">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _soldoutValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _soldoutValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD align="right" ><b>Hide</b> &nbsp;</TD>
        <TD>&nbsp;<select name="hide">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Promo Note</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="promoNote" value="<%=WebUtil.display(_promo_noteValue)%>"/></TD>
    </TR>
                                <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecProductForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

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
    <td> <%= WebUtil.display(_EcProduct.getSalePrice()) %></td>
    <td> <%= WebUtil.display(_EcProduct.getSaleEnds()) %></td>
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