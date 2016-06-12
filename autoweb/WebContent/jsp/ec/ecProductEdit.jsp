<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    EcProduct _EcProduct = EcProductDS.getInstance().getById(id);

    if ( _EcProduct == null ) {
        return;
    }

    String _category_idValue=  WebUtil.display(_EcProduct.getCategoryId());
    String _factory_skuValue=  WebUtil.display(_EcProduct.getFactorySku());
    String _site_skuValue=  WebUtil.display(_EcProduct.getSiteSku());
    String _alt_skuValue=  WebUtil.display(_EcProduct.getAltSku());
    String _item_codeValue=  WebUtil.display(_EcProduct.getItemCode());
    String _makerValue=  WebUtil.display(_EcProduct.getMaker());
    String _brandValue=  WebUtil.display(_EcProduct.getBrand());
    String _nameValue=  WebUtil.display(_EcProduct.getName());
    String _sub_nameValue=  WebUtil.display(_EcProduct.getSubName());
    String _short_descriptionValue=  WebUtil.display(_EcProduct.getShortDescription());
    String _descriptionValue=  WebUtil.display(_EcProduct.getDescription());
    String _description2Value=  WebUtil.display(_EcProduct.getDescription2());
    String _image_urlValue=  WebUtil.display(_EcProduct.getImageUrl());
    String _colorValue=  WebUtil.display(_EcProduct.getColor());
    String _sizeValue=  WebUtil.display(_EcProduct.getSize());
    String _msrpValue=  WebUtil.display(_EcProduct.getMsrp());
    String _msrp_currencyValue=  WebUtil.display(_EcProduct.getMsrpCurrency());
    String _sale_priceValue=  WebUtil.display(_EcProduct.getSalePrice());
    String _sale_endsValue=  WebUtil.display(_EcProduct.getSaleEnds());
    String _soldoutValue=  WebUtil.display(_EcProduct.getSoldout());
    String _hideValue=  WebUtil.display(_EcProduct.getHide());
    String _promo_noteValue=  WebUtil.display(_EcProduct.getPromoNote());
    String _time_createdValue=  WebUtil.display(_EcProduct.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_EcProduct.getTimeUpdated());
%> 

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


<br>
<form name="ecProductFormEdit" method="post" action="/ecProductAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" width="100" ><b>Category Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="10" property="categoryId" value="<%=WebUtil.display(_category_idValue)%>"/>
<%
	List categories = EcCategoryDS.getInstance().getBySiteId(site.getId());
	Set catIdSet = EcProductUtil.getAddToCategoryIds(site.getId(), _EcProduct.getId());
	
	System.out.println(site.getId());
	System.out.println(_EcProduct.getCategoryId());
	System.out.println(catIdSet);
	
	for(Iterator iter = categories.iterator(); iter.hasNext();){
		EcCategory cat = (EcCategory) iter.next();

		if (cat.getId() == WebParamUtil.getIntValue(_category_idValue))
			continue;
		
		boolean contained = catIdSet.contains(new Long(cat.getId()));
%>
		<%= cat.getCategoryName() %>(<%= cat.getId() %>) : <input type="checkbox" name="category_<%=cat.getId() %>" <%=HtmlUtil.getCheckedBoxValue(contained)%> />

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
        <TD>&nbsp;<TEXTAREA cols="70" class="mainDesc" NAME="description" ><%=WebUtil.display(_descriptionValue)%></TEXTAREA><br></TD>
        
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
            <b><a href="javascript:document.ecProductFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcProduct.getId()%>">
</form>
