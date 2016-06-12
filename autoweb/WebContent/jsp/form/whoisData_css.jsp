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
    WhoisData _WhoisData = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_WhoisData = WhoisDataDS.getInstance().getById(id);
		if ( _WhoisData == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _WhoisData = new WhoisData();// WhoisDataDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "whois_data_home";

    String _ipValue= (reqParams.get("ip")==null?WebUtil.display(_WhoisData.getIp()):WebUtil.display((String)reqParams.get("ip")));
    String _whois_dataValue= (reqParams.get("whoisData")==null?WebUtil.display(_WhoisData.getWhoisData()):WebUtil.display((String)reqParams.get("whoisData")));
    String _serverValue= (reqParams.get("server")==null?WebUtil.display(_WhoisData.getServer()):WebUtil.display((String)reqParams.get("server")));
    String _force_requestValue= (reqParams.get("forceRequest")==null?WebUtil.display(_WhoisData.getForceRequest()):WebUtil.display((String)reqParams.get("forceRequest")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_WhoisData.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_expiredValue= (reqParams.get("timeExpired")==null?WebUtil.display(_WhoisData.getTimeExpired()):WebUtil.display((String)reqParams.get("timeExpired")));
%> 

<br>
<div id="whoisDataForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="whoisDataFormEdit" method="POST" action="/whoisDataAction.html" >





	<div id="whoisDataForm_ip_field">
    <div id="whoisDataForm_ip_label" class="formRequiredLabel" >Ip* </div>
    <div id="whoisDataForm_ip_text" class="formFieldText" >       
        <input id="ip" class="requiredField" type="text" size="70" name="ip" value="<%=WebUtil.display(_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="whoisDataForm_whoisData_field">
    <div id="whoisDataForm_whoisData_label" class="formLabel" >Whois Data </div>
    <div id="whoisDataForm_whoisData_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="whoisData" class="field" NAME="whoisData" COLS="50" ROWS="8" ><%=WebUtil.display(_whois_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="whoisDataForm_server_field">
    <div id="whoisDataForm_server_label" class="formLabel" >Server </div>
    <div id="whoisDataForm_server_dropdown" class="formFieldDropDown" >       
        <select class="requiredField" name="server" id="server">
        <option value="" >- Please Select -</option>
<%
	List dropMenus = DropMenuUtil.getDropMenus("WhoisDataServerOption");
	for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _serverValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="whoisDataForm_forceRequest_field">
    <div id="whoisDataForm_forceRequest_label" class="formLabel" >Force Request </div>
    <div id="whoisDataForm_forceRequest_dropdown" class="formFieldDropDown" >       
        <select name="forceRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _force_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _force_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>








        <div id="whoisDataFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit2" href="javascript:document.whoisDataFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_WhoisData.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>


<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
