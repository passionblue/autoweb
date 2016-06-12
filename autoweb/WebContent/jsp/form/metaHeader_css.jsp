<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    MetaHeader _MetaHeader = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_MetaHeader = MetaHeaderDS.getInstance().getById(id);
		if ( _MetaHeader == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _MetaHeader = new MetaHeader();// MetaHeaderDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "meta_header_home";

    String _sourceValue= (reqParams.get("source")==null?WebUtil.display(_MetaHeader.getSource()):WebUtil.display((String)reqParams.get("source")));
    String _detail_idValue= (reqParams.get("detailId")==null?WebUtil.display(_MetaHeader.getDetailId()):WebUtil.display((String)reqParams.get("detailId")));
    String _nameValue= (reqParams.get("name")==null?WebUtil.display(_MetaHeader.getName()):WebUtil.display((String)reqParams.get("name")));
    String _valueValue= (reqParams.get("value")==null?WebUtil.display(_MetaHeader.getValue()):WebUtil.display((String)reqParams.get("value")));
    String _http_equivValue= (reqParams.get("httpEquiv")==null?WebUtil.display(_MetaHeader.getHttpEquiv()):WebUtil.display((String)reqParams.get("httpEquiv")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_MetaHeader.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
%> 

<br>
<div id="metaHeaderForm" class="formFrame${classSuffix}">
<div id="metaHeaderFormInnerFrame" class="formInnerFrame${classSuffix}">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="metaHeaderForm_Form" method="POST" action="/metaHeaderAction.html" id="metaHeaderForm_Form">



	<div id="metaHeaderForm_source_field" class="formFieldFrame${classSuffix}">
    <div id="metaHeaderForm_source_label" class="formLabel${classSuffix}" >Source </div>
    <div id="metaHeaderForm_source_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="requiredField" name="source" id="source">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _sourceValue)%>>XX</option-->
        <option value="page" <%=HtmlUtil.getOptionSelect("page", _sourceValue)%>>Page</option>
        <option value="content" <%=HtmlUtil.getOptionSelect("content", _sourceValue)%>>Content</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>





	<div id="metaHeaderForm_detailId_field" class="formFieldFrame${classSuffix}">
    <div id="metaHeaderForm_detailId_label" class="formLabel${classSuffix}" >Detail Id </div>
    <div id="metaHeaderForm_detailId_text" class="formFieldText${classSuffix}" >       
        <input id="detailId" class="field" type="text" size="70" name="detailId" value="<%=WebUtil.display(_detail_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="metaHeaderForm_name_field" class="formFieldFrame${classSuffix}">
    <div id="metaHeaderForm_name_label" class="formRequiredLabel${classSuffix}" >Name* </div>
    <div id="metaHeaderForm_name_text" class="formFieldTex${classSuffix}t" >       
        <input id="name" class="requiredField" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="metaHeaderForm_value_field" class="formFieldFrame${classSuffix}">
    <div id="metaHeaderForm_value_label" class="formLabel${classSuffix}" >Value </div>
    <div id="metaHeaderForm_value_text" class="formFieldText${classSuffix}" >       
        <input id="value" class="field" type="text" size="70" name="value" value="<%=WebUtil.display(_valueValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="metaHeaderForm_httpEquiv_field" class="formFieldFrame${classSuffix}">
    <div id="metaHeaderForm_httpEquiv_label" class="formLabel${classSuffix}" >Http Equiv </div>
    <div id="metaHeaderForm_httpEquiv_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select name="httpEquiv">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _http_equivValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _http_equivValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div class="submitFrame">

        <div id="metaHeaderForm_submit" class="formSubmit${classSuffix}" >       
            <a id="formSubmit2" href="javascript:document.metaHeaderForm_Form.submit();">Submit</a>
        </div>      

        <div id="metaHeaderForm_submit_cancel" class="formCancel${classSuffix}" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="metaHeaderForm_submit_ext" class="formSubmitExt${classSuffix}" >       
            <a href="#">Ext</a>
        </div>      
	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MetaHeader.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">

<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> 
</div> <!-- form -->


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Source </td> 
    <td class="columnTitle">  Detail Id </td> 
    <td class="columnTitle">  Name </td> 
    <td class="columnTitle">  Value </td> 
    <td class="columnTitle">  Http Equiv </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = MetaHeaderDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        MetaHeader _oMetaHeader = (MetaHeader) iter.next();
%>

<TR>
    <td> <%= _oMetaHeader.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oMetaHeader.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oMetaHeader.getSource()  %> </td>
	<td> <%= _oMetaHeader.getDetailId()  %> </td>
	<td> <%= _oMetaHeader.getName()  %> </td>
	<td> <%= _oMetaHeader.getValue()  %> </td>
	<td> <%= _oMetaHeader.getHttpEquiv()  %> </td>
	<td> <%= _oMetaHeader.getTimeCreated()  %> </td>
	<td> <a  href="javascript:sendFormAjaxSimple('/metaHeaderAction.html?del=true&id=<%=_oMetaHeader.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
	$(document).ready(function(){
		$("img#deleteRow").click(function () {
		    $(this).parent().parent().parent().fadeTo(1000, 0, function () { 
		        $(this).remove();
		    });
		});
	});	
</script>