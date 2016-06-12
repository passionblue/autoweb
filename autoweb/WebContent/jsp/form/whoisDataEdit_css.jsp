<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    WhoisData _WhoisData = WhoisDataDS.getInstance().getById(id);

    if ( _WhoisData == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "whois_data_home";

    String _ipValue=  WebUtil.display(_WhoisData.getIp());
    String _whois_dataValue=  WebUtil.display(_WhoisData.getWhoisData());
    String _serverValue=  WebUtil.display(_WhoisData.getServer());
    String _force_requestValue=  WebUtil.display(_WhoisData.getForceRequest());
    String _time_createdValue=  WebUtil.display(_WhoisData.getTimeCreated());
    String _time_expiredValue=  WebUtil.display(_WhoisData.getTimeExpired());
%> 

<br>
<div id="whoisDataForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="whoisDataFormEdit" method="POST" action="/whoisDataAction.html" >




	<div id="whoisDataForm_ip_field">
    <div id="whoisDataForm_ip_label" class="formRequiredLabel" >Ip* </div>
    <div id="whoisDataForm_ip_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="ip" value="<%=WebUtil.display(_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="whoisDataForm_whoisData_field">
    <div id="whoisDataForm_whoisData_label" class="formLabel" >Whois Data </div>
    <div id="whoisDataForm_whoisData_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="whoisData" COLS="50" ROWS="8" ><%=WebUtil.display(_whois_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="whoisDataForm_server_field">
    <div id="whoisDataForm_server_label" class="formRequiredLabel" >Server* </div>
    <div id="whoisDataForm_server_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="server" value="<%=WebUtil.display(_serverValue)%>"/> <span></span>
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
            <a id="formSubmit" href="javascript:document.whoisDataFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_WhoisData.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
