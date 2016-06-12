<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

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
<form name="ecPageProductRelFormEdit" method="post" action="/ecPageProductRelAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Product ID</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="productId" value="<%=WebUtil.display(_product_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Category Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="categoryId" value="<%=WebUtil.display(_category_idValue)%>"/></TD>
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
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecPageProductRelFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcPageProductRel.getId()%>">
</form>
