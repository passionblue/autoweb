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
    BlockList _BlockList = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_BlockList = BlockListDS.getInstance().getById(id);
		if ( _BlockList == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _BlockList = new BlockList();// BlockListDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "block_list_home";

    String _ip_dataValue= (reqParams.get("ipData")==null?WebUtil.display(_BlockList.getIpData()):WebUtil.display((String)reqParams.get("ipData")));
    String _range_checkValue= (reqParams.get("rangeCheck")==null?WebUtil.display(_BlockList.getRangeCheck()):WebUtil.display((String)reqParams.get("rangeCheck")));
    String _reason_codeValue= (reqParams.get("reasonCode")==null?WebUtil.display(_BlockList.getReasonCode()):WebUtil.display((String)reqParams.get("reasonCode")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_BlockList.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
%> 

<br>
<div id="blockListForm" class="formFrame">
<div id="blockListFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="blockListForm_Form" method="POST" action="/blockListAction.html" id="blockListForm_Form">





	<div id="blockListForm_ipData_field" class="formFieldFrame">
    <div id="blockListForm_ipData_label" class="formLabel" >Ip Data </div>
    <div id="blockListForm_ipData_text" class="formFieldText" >       
        <input id="ipData" class="field" type="text" size="70" name="ipData" value="<%=WebUtil.display(_ip_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="blockListForm_rangeCheck_field" class="formFieldFrame">
    <div id="blockListForm_rangeCheck_label" class="formLabel" >Range Check </div>
    <div id="blockListForm_rangeCheck_dropdown" class="formFieldDropDown" >       
        <select name="rangeCheck">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _range_checkValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _range_checkValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>



	<div id="blockListForm_reasonCode_field" class="formFieldFrame">
    <div id="blockListForm_reasonCode_label" class="formLabel" >Reason Code </div>
    <div id="blockListForm_reasonCode_dropdown" class="formFieldDropDown" >       
        <select class="field" name="reasonCode" id="reasonCode">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _reason_codeValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _reason_codeValue)%>>SPAM-AutoBlock</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _reason_codeValue)%>>SPAM-ManualBlock</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _reason_codeValue)%>>Attack-AutoBlock</option>
        <option value="99" <%=HtmlUtil.getOptionSelect("99", _reason_codeValue)%>>Other</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>





	<div class="submitFrame">

        <div id="blockListForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.blockListForm_Form.submit();">Submit</a>
        </div>      

        <div id="blockListForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="blockListForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlockList.getId()%>">

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
    <td class="columnTitle">  Ip Data </td> 
    <td class="columnTitle">  Range Check </td> 
    <td class="columnTitle">  Reason Code </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = BlockListDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlockList _oBlockList = (BlockList) iter.next();
%>

<TR>
    <td> <%= _oBlockList.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oBlockList.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oBlockList.getIpData()  %> </td>
	<td> <%= _oBlockList.getRangeCheck()  %> </td>
	<td> <%= _oBlockList.getReasonCode()  %> </td>
	<td> <%= _oBlockList.getTimeCreated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/blockListAction.html?del=true&id=<%=_oBlockList.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
</script>