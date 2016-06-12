<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    MetaHeader _MetaHeaderDefault = new MetaHeader();// MetaHeaderDS.getInstance().getDeafult();
    
    String _sourceValue= (reqParams.get("source")==null?WebUtil.display(_MetaHeaderDefault.getSource()):WebUtil.display((String)reqParams.get("source")));
    String _detail_idValue= (reqParams.get("detailId")==null?WebUtil.display(_MetaHeaderDefault.getDetailId()):WebUtil.display((String)reqParams.get("detailId")));
    String _nameValue= (reqParams.get("name")==null?WebUtil.display(_MetaHeaderDefault.getName()):WebUtil.display((String)reqParams.get("name")));
    String _valueValue= (reqParams.get("value")==null?WebUtil.display(_MetaHeaderDefault.getValue()):WebUtil.display((String)reqParams.get("value")));
    String _http_equivValue= (reqParams.get("httpEquiv")==null?WebUtil.display(_MetaHeaderDefault.getHttpEquiv()):WebUtil.display((String)reqParams.get("httpEquiv")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_MetaHeaderDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "meta_header_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="metaHeaderForm_topArea" class="formTopArea"></div>
<div id="metaHeaderForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="metaHeaderForm" method="POST" action="/metaHeaderAction.html" >



	<div id="metaHeaderForm_source_field">
    <div id="metaHeaderForm_source_label" class="formRequiredLabel" >Source* </div>
    <div id="metaHeaderForm_source_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="source">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _sourceValue)%>>XX</option-->
        <option value="page" <%=HtmlUtil.getOptionSelect("page", _sourceValue)%>>Page</option>
        <option value="content" <%=HtmlUtil.getOptionSelect("content", _sourceValue)%>>Content</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="metaHeaderForm_detailId_field">
    <div id="metaHeaderForm_detailId_label" class="formLabel" >Detail Id </div>
    <div id="metaHeaderForm_detailId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="detailId" value="<%=WebUtil.display(_detail_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="metaHeaderForm_name_field">
    <div id="metaHeaderForm_name_label" class="formRequiredLabel" >Name* </div>
    <div id="metaHeaderForm_name_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="metaHeaderForm_value_field">
    <div id="metaHeaderForm_value_label" class="formLabel" >Value </div>
    <div id="metaHeaderForm_value_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="value" value="<%=WebUtil.display(_valueValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="metaHeaderForm_httpEquiv_field">
    <div id="metaHeaderForm_httpEquiv_label" class="formLabel" >Http Equiv </div>
    <div id="metaHeaderForm_httpEquiv_dropdown" class="formFieldDropDown" >       
        <select name="httpEquiv">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _http_equivValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _http_equivValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>






        <div id="metaHeaderForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.metaHeaderForm.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      
            

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="metaHeaderForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = MetaHeaderDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        MetaHeader _MetaHeader = (MetaHeader) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _MetaHeader.getId() %> </td>

    <td> <%= WebUtil.display(_MetaHeader.getSource()) %></td>
    <td> <%= WebUtil.display(_MetaHeader.getDetailId()) %></td>
    <td> <%= WebUtil.display(_MetaHeader.getName()) %></td>
    <td> <%= WebUtil.display(_MetaHeader.getValue()) %></td>
    <td> <%= WebUtil.display(_MetaHeader.getHttpEquiv()) %></td>
    <td> <%= WebUtil.display(_MetaHeader.getTimeCreated()) %></td>


<td>
<form name="metaHeaderForm<%=_MetaHeader.getId()%>2" method="get" action="/v_meta_header_edit2.html" >
    <a href="javascript:document.metaHeaderForm<%=_MetaHeader.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _MetaHeader.getId() %>">
</form>

</td>
<td>
<a href="/metaHeaderAction.html?del=true&id=<%=_MetaHeader.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>