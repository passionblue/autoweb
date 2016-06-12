<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcPageProductRel _EcPageProductRelDefault = new EcPageProductRel();// EcPageProductRelDS.getInstance().getDeafult();
    
    String _product_idValue= (reqParams.get("productId")==null?WebUtil.display(_EcPageProductRelDefault.getProductId()):WebUtil.display((String)reqParams.get("productId")));
    String _category_idValue= (reqParams.get("categoryId")==null?WebUtil.display(_EcPageProductRelDefault.getCategoryId()):WebUtil.display((String)reqParams.get("categoryId")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_EcPageProductRelDefault.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EcPageProductRelDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="ecPageProductRelForm" method="post" action="/ecPageProductRelAction.html" >
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
            <b><a href="javascript:document.ecPageProductRelForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

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
<form name="ecPageProductRelForm<%=_EcPageProductRel.getId()%>" method="post" action="/v_ec_page_product_rel_edit.html" >
    <a href="javascript:document.ecPageProductRelForm<%=_EcPageProductRel.getId()%>.submit();">Edit</a>           
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